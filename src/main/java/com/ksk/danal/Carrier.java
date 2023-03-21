package com.ksk.danal;

public enum Carrier {
    SKT("SKT"), KT("KT"), LGUPLUS("LG U+");
    private Carrier(String dName) {
        this.dName = dName;
    }
    private final String dName;

    public String getDisplayName() {
        return dName;
    }
}
