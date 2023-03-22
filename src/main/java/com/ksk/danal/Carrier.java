package com.ksk.danal;

public enum Carrier {
    SKT("SKT"), KT("KTF"), LGUPLUS("LGT");
    Carrier(String dName) {
        this.dName = dName;
    }
    private final String dName;

    public String getDisplayName() {
        return dName;
    }
}
