package com.ksk.danal;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DanalTest {

	@DisplayName("Danal Phone Test")
	@Test
	void check() {
		Danal danal = new Danal("홍길동", "010-1234-5678", "SKT");
		assertTrue(danal.startVerification());
		danal.solveDaptcha("");
	}
}
