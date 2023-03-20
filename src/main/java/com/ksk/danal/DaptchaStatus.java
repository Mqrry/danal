package com.ksk.danal;

public final class DaptchaStatus {
    private final boolean success;
    private final String data;

    public DaptchaStatus(boolean success, String data) {
        this.success = success;
        this.data = data; // data will be changed to error if success is 'false'
    }

    public String getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }
}
