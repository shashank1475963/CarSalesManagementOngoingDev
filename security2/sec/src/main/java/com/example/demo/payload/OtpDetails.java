package com.example.demo.payload;

public class OtpDetails {

    private String otp;
    private long timeStamp;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public OtpDetails(String otp, long timeStamp) {
        this.otp = otp;
        this.timeStamp = timeStamp;
    }
}
