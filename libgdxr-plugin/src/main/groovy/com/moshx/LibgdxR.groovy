package com.moshx

import org.gradle.api.Plugin
import org.gradle.api.Project

class LibgdxR implements Plugin<Project> {

    @Override
    void apply(Project target) {
        target.extensions.create("mapAssets", AssetsConfig);
        target.tasks.create("mapAssets", AssetsMapperTask);
    }
}
