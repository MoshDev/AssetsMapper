package com.moshx

import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import com.squareup.javapoet.TypeSpec.Builder

import javax.lang.model.element.Modifier

class AssetsMapper {


    final InternalConfig config;
    final Map<String, Builder> pendingBuilders = new HashMap<>();

    AssetsMapper(InternalConfig config) {
        this.config = config;
    }

    public boolean generate() {

        for (File dir : config.assetsDirs) {
            if (!dir.directory) {
                continue;
            }

            String clazzName = config.nameGenerator.generateClazzName(dir.name);
            Builder clazzBuilder = pendingBuilders.get(clazzName);
            if (clazzBuilder == null) {
                clazzBuilder = createBuilder(clazzName).addModifiers(Modifier.PUBLIC);
                pendingBuilders.put(clazzName, clazzBuilder);
            }

            String excludedPath = dir.absolutePath + "/";
            processAssetsDir(clazzBuilder, dir, excludedPath);


        }

        pendingBuilders.values().each {
            TypeSpec typeSpec = it.build();

            JavaFile javaFile = JavaFile.builder(config.classPackage, typeSpec).build();

            try {
                javaFile.writeTo(config.outputDir);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return false;
    }


    private void processAssetsDir(Builder parentClazz, File dir, String excludedPath) {


        File[] files = dir.listFiles(new FileFilter() {
            @Override
            boolean accept(File pathname) {
                return pathname.isFile()
            }
        })
        if (files) {
            processAssetsFiles(parentClazz, files, excludedPath)
        }

        //Process sub-directories
        files = dir.listFiles(new FileFilter() {
            @Override
            boolean accept(File pathname) {
                return pathname.isDirectory()
            }
        })

        files?.each {
            String subClazzName = config.nameGenerator.generateClazzName(it.name);

            Builder subClazzBuilder = createBuilder(subClazzName).addModifiers(Modifier.PUBLIC, Modifier.STATIC);

            processAssetsDir(subClazzBuilder, it, excludedPath);

            parentClazz.addType(subClazzBuilder.build())
        }
    }


    private void processAssetsFiles(Builder clazzBuilder, File[] files, String excludedPath) {

        //Process files first

        files?.each {

            String fieldName = config.nameGenerator.generateFieldName(it.name);
            String fieldValue = it.getAbsolutePath().replaceFirst(excludedPath, "");

            FieldSpec resourceField = FieldSpec.builder(String.class, fieldName)
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                    .initializer('$S', fieldValue)
                    .build();

            clazzBuilder.addField(resourceField);

        }

    }

    private Builder createBuilder(String name) {
        if (config.asInterfaces) {
            return TypeSpec.interfaceBuilder(name)
        } else {
            return TypeSpec.classBuilder(name)
        }
    }

}
