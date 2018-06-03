package com.example.phongle.danangtravel.utils;

public final class ReWriteUrl {
    public static String ipAddressUrl = "10.10.31.221";

    public static String reWriteUrl(String path) {
        return "http://"+ipAddressUrl+":6969/api/images/"+path;
    }
}
