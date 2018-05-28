package com.example.phongle.danangtravel.utils;

public final class ReWriteUrl {
    public static String ipAddressUrl = "192.168.1.157";

    public static String reWriteUrl(String path) {
        return "http://"+ipAddressUrl+":6969/api/images/"+path;
    }
}
