package com.ksk.danal;

import com.ksk.danal.exception.FailedToRequestException;
import com.ksk.danal.identity.Gender;
import com.ksk.danal.identity.Identity;
import com.ksk.danal.instance.impl.PASS;
import com.ksk.danal.instance.impl.SMS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SMSTest {
	public static void main(String[] args) throws IOException {
//		var mapper = new ObjectMapper();
		SMS pass = new SMS("홍길동", "010-1234-1234", new Identity(1990, 1, 1, Gender.MAN), Carrier.SKT);
		if (!pass.startVerification()) throw new FailedToRequestException("Failed to request: Initialize");

		var solve = pass.solveDaptcha("anycaptcha apikey");
		if (!solve.isSuccess()) throw new FailedToRequestException(solve.getData());
		var solution = solve.getData();

		var request = pass.requestVerification(solution);
		if (!request.isSuccess()) throw new FailedToRequestException(request.getData());

		var	br = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			System.out.print("Please input the verification code: ");
			var finish = pass.finishVerification(br.readLine().trim());
			if (finish.isSuccess()) {
				System.out.println("Danal Verification finished!");
				break;
			}
			var data = finish.getData();
			switch (Integer.parseInt(data.split(" ")[0])) {
				case 9010:
					continue;
				case 1710:
					System.out.println("인증번호 불일치, 다시 시도 해 주세요.");
					continue;
				case 9991:
					throw new FailedToRequestException("인증 시도 횟수 초과로 취소 되었습니다.");
				default:
					throw new FailedToRequestException(data);
			}
		}

		var isVerified = pass.isVerified();
		if (isVerified.isSuccess())
			System.out.println("Danal verification success");
	}
}
