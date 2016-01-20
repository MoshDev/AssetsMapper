package com.moshx.models;

import org.gradle.api.Project;

import java.io.File;

public class AssetsProcess {

    private final MappedAssets mappedAssets;
    private final Project project;

    public AssetsProcess(MappedAssets mappedAssets, Project project) {
        this.mappedAssets = mappedAssets;
        this.project = project;
    }

    public void process() {

        File assetsDir;

        if (mappedAssets.isRelativePath()) {
            assetsDir = new File(project.getRootDir(), mappedAssets.getPath());
        } else {
            assetsDir = new File(mappedAssets.getPath());
        }

        if (!assetsDir.exists() || !assetsDir.isDirectory()) {
            return;
        }

        File[] validFiles = com.moshx.utils.FileUtils.listFiles(assetsDir, mappedAssets.excludePattern);

    }

}
