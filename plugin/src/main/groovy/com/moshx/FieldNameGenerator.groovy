package com.moshx

class FieldNameGenerator {

    private final String nameRegex
    private final boolean upperCase

    FieldNameGenerator(String nameRegex, boolean upperCase) {
        this.nameRegex = nameRegex
        this.upperCase = upperCase;
    }

    public String generateFieldName(String name) {
        String result = name.replaceAll(nameRegex, "_")
        if (upperCase) {
            result = result.toUpperCase();
        }
        return result;
    }

    public String generateClazzName(String name) {
        String result = name.replaceAll(nameRegex, "_")
        return result.capitalize()
    }
}
