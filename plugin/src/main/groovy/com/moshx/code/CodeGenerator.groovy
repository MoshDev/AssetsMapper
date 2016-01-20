package com.moshx.code

import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeSpec

import javax.lang.model.element.Modifier

public class CodeGenerator {

    private String className;
    private final List<CodePair> fields = new ArrayList<CodePair>();

    public CodeGenerator() {
    }

    public CodeGenerator(String className) {
        this.className = className;
    }

    public boolean addField(String name, String value) {
        return fields.add(new CodePair(name, value));
    }

    public void setOutputDir(File outputDir) {
        this.outputDir = outputDir;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public TypeSpec.Builder generate(boolean asInterface) {

        TypeSpec.Builder builder
        if (asInterface) {
            builder = TypeSpec.interfaceBuilder(className);
            builder.addModifiers(Modifier.PUBLIC);
        } else {
            builder = TypeSpec.classBuilder(className);
            builder.addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        }

        for (CodePair pair : fields) {
            FieldSpec fieldSpec = createFieldSpec(pair.key, pair.value);
            builder.addField(fieldSpec);
        }

        return builder;
    }

    private static FieldSpec createFieldSpec(String name, String value) {

        FieldSpec resourceField = FieldSpec.builder(String.class, name)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer('$S', value)
                .build();

        return resourceField;

    }
}
