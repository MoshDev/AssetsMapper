package com.moshx.utils

class NameGenerator {

    private final static String REPLACE_REGEX = '[^a-zA-Z0-9$_]';

    public static String createFieldName(String filedName, boolean uppercase) {
        String result = filedName.replaceAll(REPLACE_REGEX, "_");
        return (uppercase? result.toUpperCase() : result);

    }

    public static String createClassName(String className) {
        String result = className.replaceAll(REPLACE_REGEX, "_");
        return result.capitalize();

    }

}
