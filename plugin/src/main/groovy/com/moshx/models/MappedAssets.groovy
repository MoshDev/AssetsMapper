package com.moshx.models;

import org.gradle.api.Project;

public class MappedAssets {

    String path;
    boolean upperCase = true;
    boolean asInterface = false;
    String packageName = "com.moshx.gdxr";
    String sourceSetName = "main";
    boolean relativePath = true;
    String excludePattern = ".DS_Store";
    boolean includeHidden = false;

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

    boolean getIncludeHidden() {
        return includeHidden
    }

    void setIncludeHidden(boolean includeHidden) {
        this.includeHidden = includeHidden
    }

    public void process(Project project) {
        new AssetsProcess(this, project).process();
    }
}
