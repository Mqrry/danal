package com.ksk.danal.instance.impl;

import com.ksk.danal.Carrier;
import com.ksk.danal.DaptchaStatus;
import com.ksk.danal.identity.Identity;
import com.ksk.danal.instance.IDanal;

public class SMS extends IDanal {
    private final Identity iden;
    public SMS(String name, String phone, Identity identity, Carrier carrier) {
        super(name, phone, carrier);
        this.iden = identity;
    }

    @Override
    public DaptchaStatus requestVerification(String solution) {
        return requestVerification(solution, iden.build(), false);
    }

    public DaptchaStatus finishVerification(String otp) {
        try {
            return this._finishVerification(false, otp);
        } catch (Exception e) {
            e.printStackTrace();
            return new DaptchaStatus(false, e.getMessage());
        }
    }
}