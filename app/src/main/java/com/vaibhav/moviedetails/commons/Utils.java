package com.vaibhav.moviedetails.commons;

import okhttp3.Headers;

/**
 * @author Vaibhav Bhandula on 09/10/18.
 */
public class Utils {

    public static Headers getHeaders() {
        Headers.Builder builder = new Headers.Builder();
        builder.add("apikey", "8336b2a5");
        return builder.build();
    }
}
