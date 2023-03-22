package com.ksk.danal.instance.impl;

import com.ksk.danal.Carrier;
import com.ksk.danal.DaptchaStatus;
import com.ksk.danal.instance.IDanal;

public class PASS extends IDanal {
    public PASS(String name, String phone, Carrier carrier) {
        super(name, phone, carrier);
    }

    @Override
    public DaptchaStatus requestVerification(String solution) {
        return requestVerification(solution, "", true);
    }

    public DaptchaStatus finishVerification() {
        try {
            return this._finishVerification(true, "");
        } catch (Exception e) {
            e.printStackTrace();
            return new DaptchaStatus(false, e.getMessage());
        }
    }
}
