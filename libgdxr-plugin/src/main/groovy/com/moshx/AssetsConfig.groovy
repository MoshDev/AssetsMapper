package com.moshx

class AssetsConfig {

    boolean searchForAssets = true;
    List<String> assetsDirs = new ArrayList<>(0);
    String outputPath = null;
    String packageName = "com.moshx.gdxr";
    String nameRegex = "[^a-zA-Z0-9\$_]";
    boolean asInterfaces = false;
    boolean upperCase = true;


    public void addDir(String path, String name) {
        println "path = [$path], name = [$name]"
    }

}
