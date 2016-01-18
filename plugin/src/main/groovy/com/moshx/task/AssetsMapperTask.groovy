package com.moshx.task

import com.moshx.Assets
import com.moshx.AssetsConfig
import com.moshx.AssetsMapper
import com.moshx.FieldNameGenerator
import com.moshx.InternalConfig
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class AssetsMapperTask extends DefaultTask {


    @TaskAction
    public void apply() {

        println("#AssetsMapperTask")

        AssetsConfig config = project.extensions.getByType(AssetsConfig);
        if (config == null) {
            config = new AssetsConfig();
        }

        println(Arrays.toString(config.getMappedAssetsList().toArray()));

        List<File> assetsDirs = new ArrayList<>();

        List<File> excludedBuildDirs = new ArrayList<>();
        project.rootProject.allprojects.each {
            excludedBuildDirs.add(it.buildDir)
        }

        if (config.searchForAssets) {
            Assets assets = new Assets(project.rootProject.rootDir, excludedBuildDirs);
            assetsDirs.addAll(assets.assetsDirs);
        }

        if (!config.assetsDirs.empty) {
            File rootDir = project.rootProject.rootDir;
            for (String path : config.assetsDirs) {
                File aFile = new File(rootDir, path);
                assetsDirs += aFile;
            }
        }

        assetsDirs.each { println(it) }


        File outputDir;

        if (config.outputPath == null) {
            Set<File> sourceSets = project.sourceSets.main.allJava.srcDirs
            sourceSets = sourceSets.sort()
            outputDir = sourceSets[0]
        } else {
            outputDir = new File(project.rootDir, config.outputPath);

        }

        FieldNameGenerator nameGenerator = new FieldNameGenerator(config.nameRegex, config.upperCase);

        InternalConfig internalConfig = new InternalConfig();
        internalConfig.classPackage = config.packageName;
        internalConfig.assetsDirs = assetsDirs;
        internalConfig.outputDir = outputDir;
        internalConfig.nameGenerator = nameGenerator;
        internalConfig.asInterfaces = config.asInterface;


        AssetsMapper assetsMapper = new AssetsMapper(internalConfig);
        assetsMapper.generate();

    }

}
