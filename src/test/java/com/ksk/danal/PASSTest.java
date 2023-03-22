package com.ksk.danal;

import com.ksk.danal.exception.FailedToRequestException;
import com.ksk.danal.instance.impl.PASS;

public class PASSTest {
	public static void main(String[] args) {
//		var mapper = new ObjectMapper();
		PASS pass = new PASS("홍길동", "010-1234-1234", Carrier.SKT);
		if (!pass.startVerification()) throw new FailedToRequestException("Failed to request: Initialize");

		var solve = pass.solveDaptcha("anycaptcha apikey");
		if (!solve.isSuccess()) throw new FailedToRequestException(solve.getData());
		var solution = solve.getData();

		var request = pass.requestVerification(solution);
		if (!request.isSuccess()) throw new FailedToRequestException(request.getData());

		while (true) {
			try {
				Thread.sleep(10000L);
			} catch (InterruptedException e) {
				//
			}
			var finish = pass.finishVerification();
			if (finish.isSuccess()) {
				System.out.println("Danal Verification finished!");
				break;
			}
			var data = finish.getData();
			switch (Integer.parseInt(data.split(" ")[0])) {
				case 2371: continue;
				case 2376: throw new FailedToRequestException("PASS 인증이 취소되었습니다. 다시 시도해 주십시오.");
				default: throw new FailedToRequestException(data);
			}
		}

		var isVerified = pass.isVerified();
		if (isVerified.isSuccess())
			System.out.println("Danal verification success");
		System.out.println(isVerified.getData());
	}
}
