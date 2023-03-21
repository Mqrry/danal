package com.ksk.danal.impl;

import com.ksk.danal.Carrier;
import com.ksk.danal.DaptchaStatus;
import com.ksk.danal.IDanal;
import com.ksk.danal.URLEncoded;

import java.net.http.HttpRequest;

import static com.ksk.danal.Pair.to;

public class SMS extends IDanal {
    public SMS(String name, String phone, Carrier carrier) {
        super(name, phone, carrier);
    }

    @Override
    public DaptchaStatus requestVerification(String solution, String iden) {
        return requestVerification(solution, iden, false);
    }

    public DaptchaStatus finishVerification(String otp) {
        try {
            URLEncoded encoded = new URLEncoded(to("TID", this.tid), to("otp", otp));
            return this._finishVerification(encoded);
        } catch (Exception e) {
            e.printStackTrace();
            return new DaptchaStatus(false, e.getMessage());
        }
    }
}