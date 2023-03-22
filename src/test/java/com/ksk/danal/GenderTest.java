package com.ksk.danal;

import com.ksk.danal.identity.Gender;
import com.ksk.danal.identity.Identity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenderTest {
    @DisplayName("GenderBuilder Test")
    @Test
    void check() {
        var iden = new Identity(1989, 7, 7, Gender.WOMAN, true);
        assertEquals(iden.build(), "8907076");
    }
}
