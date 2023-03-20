package com.ksk;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.Gson;

public class Danal {
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

	public Danal(String name, String phone, String carrier) {
		this.name = name;
		this.phone = phone;
		this.carrier = carrier;
	}

	public boolean startVerification() {
		try {
			HttpRequest sessionRequest = HttpRequest.post("https://www.danalpay.com/customer_support/api/uas_ready")
				.contentType("application/x-www-form-urlencoded")
				.send("TARGET_URL=%2Fcustomer_support%2Fapi%2Fuas_confirm&UAS_TYPE=UAS_INFO&UAS_MOBILE=" + this.phone);
			String text = sessionRequest.body();
			this.tid = Jsoup.parse(text).select("input[name=TID]").attr("value");
			this.sessionId = sessionRequest.headers("Set-Cookie")[0].replace("session=", "").split(";")[0];
			HttpRequest startSession = HttpRequest.post("https://wauth.teledit.com/Danal/WebAuth/Web/Start.php")
				.contentType("application/x-www-form-urlencoded")
				.send(
					"IsCharSet=UTF-8&xx_referurl=https%3A%2F%2Fwww.danalpay.com%2F&IsMobileW=Y&IsDstAddr=" + this.phone
						+ "&TID=" + this.tid);
			String body = startSession.body();
			this.startData = new Gson().fromJson(
				Jsoup.parse(body).getElementsByTag("script").get(6).data().split("JSON.parse\\('")[1].split("'\\);")[0],
				StartData.class);
			String daptchaData = HttpRequest.get(
				"https://wauth.teledit.com/Danal/WebAuth/Daptcha/daptcha.js.php?key=" + startData.getServerInfo()
					+ "&_="
					+ System.currentTimeMillis()).body();
			System.out.println(daptchaData);
			Pattern enccode = Pattern.compile("var DAPTCHA_ENCCODE = \"(.*)\";");
			Pattern hashcode = Pattern.compile("var DAPTCHA_HASHCODE = \"(.*)\";");
			Matcher encMatcher = enccode.matcher(daptchaData);
			Matcher hashMatcher = hashcode.matcher(daptchaData);
			if (encMatcher.find()) {
				this.daptchaEncCode = encMatcher.group(1);
			}
			if (hashMatcher.find()) {
				this.daptchaHashCode = hashMatcher.group(1);
			}
			this.daptchaTimestamp = System.currentTimeMillis();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean solveDaptcha(String apiKey) {
		try {
			HttpRequest request = HttpRequest.get(
				"https://wauth.teledit.com/Danal/WebAuth/Daptcha/daptcha.bmp.php?data=" + this.daptchaEncCode + "&ts="
					+ System.currentTimeMillis());
			File file = new File("test.png");
			System.out.println(request.receive(file).code());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
