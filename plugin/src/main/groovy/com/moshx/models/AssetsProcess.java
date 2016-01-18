package com.moshx.models;

import org.gradle.api.Project;

import java.io.File;

public class AssetsProcess {

    private final MappedAssets mappedAssets;

    public AssetsProcess(MappedAssets mappedAssets) {
        this.mappedAssets = mappedAssets;
    }

    public void process(Project project) {

        File assetsDir;

        if (mappedAssets.isRelativePath()) {
            assetsDir = new File(project.getRootDir(), mappedAssets.getPath());
        } else {
            assetsDir = new File(mappedAssets.getPath());
        }

        if (!assetsDir.exists() || assetsDir.isDirectory()) {
            return;
        }

    }

}
