package com.github.mybatis.generator.util;


import org.mybatis.generator.api.dom.java.*;

public class GeneratorUtils {
    public GeneratorUtils() {
    }

    public static void addPropertyToClass(TopLevelClass cl, FullyQualifiedJavaType type, String name, boolean hasSetter, boolean hasGetter) {
        Field startField = new Field(name, type);
        startField.setVisibility(JavaVisibility.PRIVATE);
        cl.addField(startField);
        Method getter;
        if (hasSetter) {
            getter = new Method("set" + name.substring(0, 1).toUpperCase() + name.substring(1));
            getter.setReturnType((FullyQualifiedJavaType) null);
            getter.setVisibility(JavaVisibility.PUBLIC);
            getter.addParameter(new Parameter(type, name));
            getter.addBodyLine("this." + name + "=" + name + ";");
            cl.addMethod(getter);
        }

        if (hasGetter) {
            getter = new Method("get" + name.substring(0, 1).toUpperCase() + name.substring(1));
            getter.setReturnType(type);
            getter.setVisibility(JavaVisibility.PUBLIC);
            getter.addBodyLine("return " + name + ";");
            cl.addMethod(getter);
        }

    }
}