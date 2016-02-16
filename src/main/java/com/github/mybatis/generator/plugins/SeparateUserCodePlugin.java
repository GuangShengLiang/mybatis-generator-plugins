package com.github.mybatis.generator.plugins;

import com.github.mybatis.generator.type.FileFullName;
import com.github.mybatis.generator.type.TypeFullName;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgs on 16-2-16.
 */
public class SeparateUserCodePlugin extends PluginAdapter {
    public static final String TARGET_PACKAGE_PROPERTY_NAME = "separateUserCodePlugin.targetPackage";
    public static final String SEARCH_PROPERTY_NAME = "separateUserCodePlugin.search";
    public static final String REPLACE_PROPERTY_NAME = "separateUserCodePlugin.replace";
    public static final String PREFIX_PROPERTY_NAME = "separateUserCodePlugin.prefix";
    public static final String SUFFIX_PROPERTY_NAME = "separateUserCodePlugin.suffix";
    private List<GeneratedJavaFile> generatedJavaFileList = new ArrayList();
    private List<GeneratedXmlFile> generatedXmlFileList = new ArrayList();

    public SeparateUserCodePlugin() {
    }

    public boolean validate(List<String> strings) {
        return true;
    }

    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String userCodePackage = this.context.getJavaClientGeneratorConfiguration().getProperty("separateUserCodePlugin.targetPackage");
        if (userCodePackage == null) {
            userCodePackage = this.context.getJavaClientGeneratorConfiguration().getTargetPackage();
        }

        String userCodeSearch = this.context.getJavaClientGeneratorConfiguration().getProperty("separateUserCodePlugin.search");
        String userCodeReplace = this.context.getJavaClientGeneratorConfiguration().getProperty("separateUserCodePlugin.replace");
        String userCodePrefix = this.context.getJavaClientGeneratorConfiguration().getProperty("separateUserCodePlugin.prefix");
        String userCodeSuffix = this.context.getJavaClientGeneratorConfiguration().getProperty("separateUserCodePlugin.suffix");
        TypeFullName userInterfaceTypeFullName = new TypeFullName(userCodePackage, interfaze.getType().getShortName());
        userInterfaceTypeFullName.replaceTypeShortName(userCodeSearch, userCodeReplace).fixTypeShortName(userCodePrefix, userCodeSuffix);
        String userCodeTargetProject = this.context.getJavaClientGeneratorConfiguration().getTargetProject();
        File userInterfaceFile = new File(userCodeTargetProject + "/" + (new FileFullName(userInterfaceTypeFullName, "java")).getFileFullName());
        if (!userInterfaceFile.exists()) {
            Interface userMapperPackage = new Interface(userInterfaceTypeFullName.getTypeFullName());
            userMapperPackage.addImportedType(interfaze.getType());
            userMapperPackage.addSuperInterface(interfaze.getType());
            userMapperPackage.setVisibility(JavaVisibility.PUBLIC);
            this.generatedJavaFileList.add(new GeneratedJavaFile(userMapperPackage, userCodeTargetProject, this.context.getJavaFormatter()));
        }

        String userMapperPackage1 = this.context.getSqlMapGeneratorConfiguration().getProperty("separateUserCodePlugin.targetPackage");
        if (userMapperPackage1 == null) {
            userMapperPackage1 = this.context.getSqlMapGeneratorConfiguration().getTargetPackage();
        }

        String userMapperSearch = this.context.getSqlMapGeneratorConfiguration().getProperty("separateUserCodePlugin.search");
        String userMapperReplace = this.context.getSqlMapGeneratorConfiguration().getProperty("separateUserCodePlugin.replace");
        String userMapperPrefix = this.context.getSqlMapGeneratorConfiguration().getProperty("separateUserCodePlugin.prefix");
        String userMapperSuffix = this.context.getSqlMapGeneratorConfiguration().getProperty("separateUserCodePlugin.suffix");
        TypeFullName userMapperTypeFullName = new TypeFullName(userMapperPackage1, userInterfaceTypeFullName.getTypeShortName());
        userMapperTypeFullName.replaceTypeShortName(userMapperSearch, userMapperReplace).fixTypeShortName(userMapperPrefix, userMapperSuffix);
        String userMapperTargetProject = this.context.getSqlMapGeneratorConfiguration().getTargetProject();
        File userMapperFile = new File(userMapperTargetProject + "/" + (new FileFullName(userMapperTypeFullName, "xml")).getFileFullName());
        if (!userMapperFile.exists()) {
            Document document = new Document("-//mybatis.org//DTD Mapper 3.0//EN", "http://mybatis.org/dtd/mybatis-3-mapper.dtd");
            XmlElement rootElement = new XmlElement("mapper");
            rootElement.addAttribute(new Attribute("namespace", userInterfaceTypeFullName.getTypeFullName()));
            document.setRootElement(rootElement);
            this.generatedXmlFileList.add(new GeneratedXmlFile(document, (new FileFullName(userMapperTypeFullName, "xml")).getFileShortName(), userMapperPackage1, userMapperTargetProject, false, this.context.getXmlFormatter()));
        }

        return true;
    }

    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
        return this.generatedJavaFileList;
    }

    public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles() {
        return this.generatedXmlFileList;
    }
}
