package com.ksk.danal;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.http.HttpRequest.BodyPublishers;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jsoup.Jsoup;

import static com.ksk.danal.Pair.to;

public final class Danal {
	private final ObjectMapper mapper = new ObjectMapper();
	private final String name;
	private final String phone;
	private final String carrier;

	private boolean isPassVerification;
	private String daptchaEncCode;
	private String daptchaHashCode;
	private long daptchaTimestamp;
	private String tid;
	private String sessionId;

	private StartData startData;

	private final HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();

	public Danal(String name, String phone, String carrier) {
		this.name = name;
		this.phone = phone;
		this.carrier = carrier;
	}

	private URI uri(String s) {
		return URI.create(s);
	}

	private static final String MIME_TYPE_URLENCODED = "application/x-www-form-urlencoded";
	private static final String MIME_TYPE_JSON = "application/json";

	private HttpResponse<String> send(HttpRequest r) throws IOException, InterruptedException {
		return client.send(r, HttpResponse.BodyHandlers.ofString());
	}

	public boolean startVerification() {
		try {
			var initDanal = send(HttpRequest.newBuilder(uri("https://www.danalpay.com/customer_support/api/uas_ready"))
				.header("Content-Type", MIME_TYPE_URLENCODED)
				.POST(BodyPublishers.ofString(
					new URLEncoded(to("TARGET_URL", "/customer_support/api/uas_confirm"), to("UAS_TYPE", "UAS_INFO"),
						to("UAS_MOBILE", phone)).build()))
				.build());
			String text = initDanal.body();
			this.tid = Jsoup.parse(text).select("input[name=TID]").attr("value");
			this.sessionId = initDanal.headers()
				.firstValue("Set-Cookie")
				.orElseThrow()
				.replace("session=", "")
				.split(";")[0];

			var startSession = send(HttpRequest.newBuilder(uri("https://wauth.teledit.com/Danal/WebAuth/Web/Start.php"))
				.header("Content-Type", MIME_TYPE_URLENCODED)
				.POST(BodyPublishers.ofString(
					new URLEncoded(to("IsCharSet", "UTF-8"), to("xx_referurl", "https://www.danalplay.com/"),
						to("IsMobileW", "Y"), to("IsDstAddr", phone), to("TID", tid)).build()))
				.build());
			String body = startSession.body();
			String bodyParsed = Jsoup.parse(body)
				.getElementsByTag("script")
				.get(6)
				.data()
				.split("JSON.parse\\('")[1].split("'\\);")[0];
			this.startData = mapper.readValue(bodyParsed, StartData.class);

			var daptcha = send(HttpRequest.newBuilder(
				uri("https://wauth.teledit.com/Danal/WebAuth/Daptcha/daptcha.js.php?key=" + startData.getServerInfo()
					+ "&_=" + c())).GET().build());

			String daptchaData = daptcha.body();

			System.out.println(daptchaData);
			Pattern enccode = Pattern.compile("var DAPTCHA_ENCCODE = \"(.*)\";");
			Pattern hashcode = Pattern.compile("var DAPTCHA_HASHCODE = \"(.*)\";");
			Matcher encMatcher = enccode.matcher(daptchaData);
			Matcher hashMatcher = hashcode.matcher(daptchaData);
			if (encMatcher.find())
				this.daptchaEncCode = encMatcher.group(1);
			if (hashMatcher.find())
				this.daptchaHashCode = hashMatcher.group(1);
			this.daptchaTimestamp = c();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private long c() {
		return System.currentTimeMillis();
	}

	public DaptchaStatus solveDaptcha(String apiKey) {
		try {
			var request = client.send(HttpRequest.newBuilder(
				uri("https://wauth.teledit.com/Danal/WebAuth/Daptcha/daptcha.bmp.php?data=" + daptchaEncCode + "ts="
					+ c())).GET().build(), HttpResponse.BodyHandlers.ofByteArray());
			String imageBase64 = Base64.getEncoder().encodeToString(request.body());

			var createTask = send(HttpRequest.newBuilder(uri("https://api.anycaptcha.com/createTask"))
				.header("Content-Type", MIME_TYPE_JSON)
				.POST(BodyPublishers.ofString(mapper.writeValueAsString(new HashMap<String, Object>() {{
					put("clientKey", apiKey);
					put("task", new HashMap<String, Object>() {{
						put("type", "ImageToTextTask");
						put("data", imageBase64);
					}});
				}})))
				.build());
			var tree = mapper.readTree(createTask.body());
			if (tree.has("errorId") && tree.get("errorId").asInt() > 0)
				return new DaptchaStatus(false, tree.get("errorCode").asText());

			var taskId = tree.get("taskId").asText();
			var body = mapper.writeValueAsString(new HashMap<String, Object>() {{
				put("clientKey", apiKey);
				put("taskId", taskId);
			}});

			while (true) {
				var res = send(HttpRequest.newBuilder(uri("https://api.anycaptcha.com/getTaskResult"))
					.header("Content-Type", MIME_TYPE_JSON)
					.POST(BodyPublishers.ofString(body))
					.build());
				var resJson = mapper.readTree(res.body());
				if (resJson.get("status").asText().equals("ready")) {
					return new DaptchaStatus(true, resJson.get("solution").get("text").asText());
				} else if (resJson.has("errorId") && resJson.get("errorId").asInt() > 0) {
					return new DaptchaStatus(false, resJson.get("errorDescription").asText());
				}
			}
		} catch (Exception e) {
			return new DaptchaStatus(false, e.getMessage());
		}
	}

	public DaptchaStatus requestVerification(String solution, boolean isPassVerification, String iden) {
		try {
			this.isPassVerification = isPassVerification;
			var verificationRequest = send(
				HttpRequest.newBuilder(uri("https://wauth.teledit.com/Danal/WebAuth/Web/api/AJAXDeliver.php"))
					.header("Content-Type", MIME_TYPE_URLENCODED)
					.POST(BodyPublishers.ofString(
						new URLEncoded(
							to("ServerInfo", this.startData.getServerInfo()),
							to("TID", this.tid),
							to("carrier", this.carrier),
							to("agelimit", "0"),
							to("mvnocarrier", "mvnocarrier"),
							to("name", this.name),
							to("phone", this.phone.replaceAll("-", "")),
							to("iden", iden),
							to("captcha", solution),
							to("termagree", "Y"),
							to("notiagree", "N"),
							to("isApp", isPassVerification ? "Y" : "N"),
							to("Device", "Mobile"),
							to("ReferURL", "https://www.danalpay.com/"),
							to("hashcode", this.daptchaHashCode),
							to("UseDSK", "N"),
							to("secure_enc_keyboard", ""),
							to("isSaveInfo", "N")
						).build()))
					.build());
			var tree = mapper.readTree(verificationRequest.body());
			String returnCode = tree.get("RETURNCODE").asText();
			String returnMsg = tree.get("RETURNMSG").asText();
			return new DaptchaStatus("0000".equals(returnCode), returnCode + " " + returnMsg);
		} catch (Exception e) {
			return new DaptchaStatus(false, e.getMessage());
		}
	}

	public DaptchaStatus requestVerification(String solution, boolean isPassVerification) {
		return requestVerification(solution, isPassVerification, "");
	}

	public DaptchaStatus finishVerification(String otp) {
		try {
			URLEncoded encoded;
			if (this.isPassVerification) {
				encoded = new URLEncoded(to("TID", this.tid), to("otp", otp));
			} else {
				encoded = new URLEncoded(to("TID", this.tid));
			}
			var verificationRequest = send(
				HttpRequest.newBuilder(uri("https://wauth.teledit.com/Danal/WebAuth/Web/api/AJAXAppReport.php"))
					.header("Content-Type", MIME_TYPE_URLENCODED)
					.POST(BodyPublishers.ofString(
						encoded.build()))
					.build());
			var tree = mapper.readTree(verificationRequest.body());
			String returnCode = tree.get("RETURNCODE").asText();
			String returnMsg = tree.get("RETURNMSG").asText();
			if (!"0000".equals(returnCode))
				return new DaptchaStatus(false, returnCode + " " + returnMsg);
			var confirmRequest = send(
				HttpRequest.newBuilder(uri("https://www.danalpay.com//customer_support/api/uas_confirm"))
					.header("Content-Type", MIME_TYPE_URLENCODED)
					.header("cookie", "session=" + this.sessionId)
					.POST(BodyPublishers.ofString(new URLEncoded(to("TID", this.tid),
						to("dndata", this.startData.getDnData()),
						to("BackUrl", ""),
						to("IsMobileW", "Y"),
						to("IsCharSet", "UTF-8"),
						to("IsDstAddr", this.phone.replaceAll("-", "")),
						to("IsCarrier", ""),
						to("IsExceptCarrier", "null"),
						to("xx_referurl", "https://www.danalpay.com/")
					).build()))
					.build());
			this.sessionId = confirmRequest.headers().firstValue("Set-Cookie")
				.orElseThrow()
				.replace("session=", "")
				.split(";")[0];
			return new DaptchaStatus(true, "");
		} catch (Exception e) {
			return new DaptchaStatus(false, e.getMessage());
		}
	}

	public DaptchaStatus isVerified() {
		try {
			var transactionList = send(HttpRequest.newBuilder(uri("https://www.danalpay.com/customer_support/api/search_transaction_list"))
					.header("Content-Type", MIME_TYPE_URLENCODED)
					.header("Cookie", "session=" + sessionId)
					.POST(
						BodyPublishers.ofString(
							new URLEncoded(
								to("TYPE", "mobile"),
								to("MOBILE", phone.replace("-", "")),
								to("START_YYMM", ""),
								to("END_YYMM", "")
							).build()
						)
					).build());
			return new DaptchaStatus(transactionList.statusCode() == 200, transactionList.body()); // TODO: change this to json.
		} catch (Exception e) {
			return new DaptchaStatus(false, e.getMessage());
		}
	}
}
