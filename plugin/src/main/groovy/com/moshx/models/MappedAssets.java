package com.moshx.models;

import org.gradle.api.Project;

public class MappedAssets {

    String path;
    boolean upperCase = true;
    boolean asInterface = false;
    String nameRegex = "[^a-zA-Z0-9$_]";
    String packageName = "com.moshx.gdxr";
    String sourceSetName = "main";
    boolean relativePath = true;
    String excludePattern = null;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isUpperCase() {
        return upperCase;
    }

    public void setUpperCase(boolean upperCase) {
        this.upperCase = upperCase;
    }

    public boolean isAsInterface() {
        return asInterface;
    }

    public void setAsInterface(boolean asInterface) {
        this.asInterface = asInterface;
    }

    public String getNameRegex() {
        return nameRegex;
    }

    public void setNameRegex(String nameRegex) {
        this.nameRegex = nameRegex;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSourceSetName() {
        return sourceSetName;
    }

    public void setSourceSetName(String sourceSetName) {
        this.sourceSetName = sourceSetName;
    }

    public boolean isRelativePath() {
        return relativePath;
    }

    public void setRelativePath(boolean relativePath) {
        this.relativePath = relativePath;
    }

    public String getExcludePattern() {
        return excludePattern;
    }

    public void setExcludePattern(String excludePattern) {
        this.excludePattern = excludePattern;
    }

    public void process(Project project) {
        new AssetsProcess(this, project).process();
    }
}
