package com.example.phongle.danangtravel.activity.utils;

public final class ReWriteUrl {
    public static String ipAddressUrl= "192.168.3.113";

    public static String reWriteUrl(String url){
        return url.replace("localhost",ipAddressUrl);
    }
}
