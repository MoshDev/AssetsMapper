package com.moshx.task

import com.moshx.plugin.AssetsConfig
import com.moshx.models.MappedAssets
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class AssetsMapperTask extends DefaultTask {


    @TaskAction
    public void apply() {

        println("#AssetsMapperTask")

        AssetsConfig config = project.extensions.getByType(AssetsConfig);
        if (config == null) {
            println("mapAssets task not defined.")
            return
        }

        List<MappedAssets> mappedAssetsList = config.mappedAssetsList;
        if (mappedAssetsList.empty) {
            println("mapped assets list is empty.")
            return
        }

        mappedAssetsList.each {

            it.process(project)

        }

    }

}
