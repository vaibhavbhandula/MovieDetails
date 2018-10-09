package com.vaibhav.moviedetails.commons;

/**
 * @author Vaibhav Bhandula on 09/10/18.
 */
public class StringUtils {

    public static String getNonNullString(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }
        return s;
    }
}
