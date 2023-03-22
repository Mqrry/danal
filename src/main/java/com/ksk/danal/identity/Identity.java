package com.ksk.danal.identity;

public class Identity {
    private final int birthYear;
    private final int birthMonth;
    private final int birthDay;
    private final Gender gender;
    private final boolean isForeign;

    public Identity(int birthYear, int birthMonth, int birthDay, Gender gender, boolean isForeign) {
        this.birthYear = birthYear;
        this.birthMonth = birthMonth;
        this.birthDay = birthDay;
        this.gender = gender;
        this.isForeign = isForeign;
    }
    public Identity(int birthYear, int birthMonth, int birthDay, Gender gender) {
        this(birthYear, birthMonth, birthDay, gender, false);
    }
    public String build() {
        var isYearOver2000 = birthYear >= 2000;
        var gender = 1;
        if (isYearOver2000) gender += 2;
        if (isForeign) gender += 4;
        if (this.gender == Gender.WOMAN) gender++;
        var yearStr = String.format("%02d", birthYear - (isYearOver2000 ? 2000 : 1900));
        var monthStr = String.format("%02d", birthMonth);
        var dayStr = String.format("%02d", birthDay);
        return yearStr + monthStr + dayStr + gender;
    }
}
