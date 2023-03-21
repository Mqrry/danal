package com.ksk.danal.impl;

import com.ksk.danal.Carrier;
import com.ksk.danal.DaptchaStatus;
import com.ksk.danal.IDanal;
import com.ksk.danal.URLEncoded;

import java.net.http.HttpRequest;

import static com.ksk.danal.Pair.to;

public class PASS extends IDanal {
    public PASS(String name, String phone, Carrier carrier) {
        super(name, phone, carrier);
    }

    @Override
    public DaptchaStatus requestVerification(String solution, String iden) {
        return requestVerification(solution, iden, true);
    }

    public DaptchaStatus finishVerification() {
        try {
            URLEncoded encoded = new URLEncoded(to("TID", this.tid));
            return this._finishVerification(encoded);
        } catch (Exception e) {
            e.printStackTrace();
            return new DaptchaStatus(false, e.getMessage());
        }
    }
}
