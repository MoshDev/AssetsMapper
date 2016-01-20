package com.moshx.models

import com.moshx.code.CodeGenerator
import com.moshx.utils.FileUtils
import com.moshx.utils.NameGenerator
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import org.gradle.api.Project

import javax.lang.model.element.Modifier

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
            println(assetsDir.path + " Doesn't exists or not a directory!!")
            return;
        }


        File outputDir = project.sourceSets.getByName(mappedAssets.sourceSetName).java.srcDirs.getAt(0);
        String packageName = mappedAssets.packageName;


        TypeSpec.Builder rooBuilder = processDir(assetsDir, assetsDir.path.concat('/'));

        TypeSpec typeSpec = rooBuilder.build();

        JavaFile javaFile = JavaFile.builder(packageName, typeSpec).build();

        try {
            javaFile.writeTo(outputDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private TypeSpec.Builder processDir(File dir, String excludedPath) {

        String className = NameGenerator.createClassName(dir.getName());

        CodeGenerator codeGenerator = new CodeGenerator(className);

        FileFilter filter = new FileUtils.ChainFilter(new FileUtils.PatternFileFilter(mappedAssets.excludePattern), new FileUtils.HiddenFileFilter(mappedAssets.includeHidden));

        File[] validFiles = dir.listFiles(filter);

        if (validFiles != null && validFiles.length > 0) {

            for (File f : validFiles) {

                String name = NameGenerator.createFieldName(f.name);
                String value = f.path.replaceFirst(excludedPath, "");

                codeGenerator.addField(name, value);
            }
        }

        TypeSpec.Builder builder = codeGenerator.generate();

        File[] subDirs = dir.listFiles(new FileUtils.DirectoryFileFilter());
        if (subDirs && subDirs.length > 0) {
            subDirs.each { File subDir ->
                TypeSpec.Builder subBuilder = processDir(subDir, excludedPath);
                subBuilder.addModifiers(Modifier.PUBLIC, Modifier.STATIC);
                builder.addType(subBuilder.build())
            }
        }

        builder
    }

}
