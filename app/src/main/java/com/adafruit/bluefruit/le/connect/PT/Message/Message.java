package com.adafruit.bluefruit.le.connect.PT.Message;

import com.adafruit.bluefruit.le.connect.PT.Patient;

/**
 * Created by yhuang on 4/4/2017.
 */

public class Message {
    private Patient patient;
    private String content;
    private Boolean seen;

    public Message(Patient patient, String content, Boolean seen) {
        this.patient = patient;
        this.content = content;
        this.seen = seen;
    }

    public String getPatientName() {
        return this.patient.getName();
    }

    public String getContent() {
        return this.content;
    }

    public Boolean getSeen() {
        return this.seen;
    }

}
