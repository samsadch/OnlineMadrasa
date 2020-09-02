package com.onlinemadrasa;

public class DeveloperKey {

    public static native String getAPIKey();

    public static String DEVELOPER_KEY = getAPIKey();

    static {
        System.loadLibrary("native-lib");
    }
}
