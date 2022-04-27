package com.example.otpverification;

public class user {

    static private long ID = 0;
    public String UID, username, email, mobile, password, otp, verified;

    user(){

    }

    user(String username, String email, String mobile, String password, String otp, String verified){
        ID = ID+1;
        this.UID = ID+"";
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.otp = otp;
        this.verified = verified;
    }
}
