package com.moshx.plugin

import com.moshx.AssetsConfig
import com.moshx.task.AssetsMapperTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class AssetsMapperPlugin implements Plugin<Project> {

    @Override
    void apply(Project target) {
        println("#AssetsMapperPlugin")
        target.extensions.create("mapAssets", AssetsConfig);
        target.tasks.create("mapAssets", AssetsMapperTask);
    }
}