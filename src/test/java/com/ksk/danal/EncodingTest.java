package com.ksk.danal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EncodingTest {
    @DisplayName("Danal Phone Test")
    @Test
    void check() {
        // TARGET_URL=/customer_support/api/uas_confirm&UAS_TYPE=UAS_INFO&UAS_MOBILE=
        URLEncoded encode = new URLEncoded(to("TARGET_URL", "/customer_support/api/uas_confirm"), to("UAS_TYPE", "UAS_INFO"), to("UAS_MOBILE", ""));
        assertEquals(encode.build(), "TARGET_URL=%2Fcustomer_support%2Fapi%2Fuas_confirm&UAS_TYPE=UAS_INFO&UAS_MOBILE=");
    }

    private <A, B> Pair<A, B> to(A a, B b) {
        return new Pair<>(a, b);
    }
}
