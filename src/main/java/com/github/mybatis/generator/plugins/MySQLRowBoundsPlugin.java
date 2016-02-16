package com.github.mybatis.generator.plugins;

import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import com.github.mybatis.generator.type.FullyQualifiedJavaTypes;
import com.github.mybatis.generator.type.TypeFullName;
import com.github.mybatis.generator.util.GeneratorUtils;

import java.util.*;

/**
 * Created by lgs on 16-2-16.
 */
public class MySQLRowBoundsPlugin extends PluginAdapter {
    public static final String BASE_EXAMPLE_TYPE_SHORT_NAME = "BaseExample";
    private Map<FullyQualifiedTable, List<XmlElement>> elementsToAdd = new HashMap();

    public MySQLRowBoundsPlugin() {
    }

    public boolean validate(List<String> strings) {
        return true;
    }

    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
        ArrayList javaFiles = new ArrayList(1);
        TopLevelClass baseExampleClass = new TopLevelClass(this.getBaseExampleType());
        baseExampleClass.setVisibility(JavaVisibility.PUBLIC);
        GeneratorUtils.addPropertyToClass(baseExampleClass, FullyQualifiedJavaTypes.LONG, "start", true, true);
        GeneratorUtils.addPropertyToClass(baseExampleClass, FullyQualifiedJavaTypes.LONG, "limit", true, true);
        GeneratedJavaFile generatedJavaFile = new GeneratedJavaFile(baseExampleClass, this.context.getJavaModelGeneratorConfiguration().getTargetProject(), this.context.getJavaFormatter());
        javaFiles.add(generatedJavaFile);
        return javaFiles;
    }

    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.setSuperClass(this.getBaseExampleType());
        return true;
    }

    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        this.generateLimitSql(element, introspectedTable);
        return true;
    }

    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        this.generateLimitSql(element, introspectedTable);
        return true;
    }

    protected void copyAndSaveElement(XmlElement element, FullyQualifiedTable fqt) {
        XmlElement newElement = new XmlElement(element);
        Iterator elements = newElement.getAttributes().iterator();

        while (elements.hasNext()) {
            Attribute attribute = (Attribute) elements.next();
            if ("id".equals(attribute.getName())) {
                elements.remove();
                Attribute newAttribute = new Attribute("id", attribute.getValue() + "WithRowbounds");
                newElement.addAttribute(newAttribute);
                break;
            }
        }

        List<XmlElement> elements1 = (List) this.elementsToAdd.get(fqt);
        if (elements1 == null) {
            elements1 = new ArrayList();
            this.elementsToAdd.put(fqt, elements1);
        }

        ((List) elements1).add(newElement);
    }

    private void generateLimitSql(XmlElement element, IntrospectedTable introspectedTable) {
        XmlElement ifLimitE = new XmlElement("if");
        ifLimitE.addAttribute(new Attribute("test", "limit!=null"));
        XmlElement ifStartE = new XmlElement("if");
        ifStartE.addAttribute(new Attribute("test", "start!=null"));
        ifStartE.addElement(new TextElement("#{start},"));
        ifLimitE.addElement(new TextElement("limit "));
        ifLimitE.addElement(ifStartE);
        ifLimitE.addElement(new TextElement("#{limit}"));
        element.addElement(ifLimitE);
    }

    private FullyQualifiedJavaType getBaseExampleType() {
        TypeFullName baseExampleType = new TypeFullName(this.context.getJavaModelGeneratorConfiguration().getTargetPackage(), "BaseExample");
        String contextSearch = this.context.getProperty("renamePlugin.search");
        String contextReplace = this.context.getProperty("renamePlugin.replace");
        baseExampleType.replaceTypeShortName(contextSearch, contextReplace);
        String modelSearch = this.context.getJavaModelGeneratorConfiguration().getProperty("renamePlugin.search");
        String modelReplace = this.context.getJavaModelGeneratorConfiguration().getProperty("renamePlugin.replace");
        baseExampleType.replaceTypeShortName(modelSearch, modelReplace);
        String modelPrefix = this.context.getJavaModelGeneratorConfiguration().getProperty("renamePlugin.prefix");
        String modelSuffix = this.context.getJavaModelGeneratorConfiguration().getProperty("renamePlugin.suffix");
        if (modelPrefix == null) {
            modelPrefix = this.context.getProperty("renamePlugin.prefix");
        }

        if (modelSuffix == null) {
            modelSuffix = this.context.getProperty("renamePlugin.suffix");
        }

        baseExampleType.fixTypeShortName(modelPrefix, modelSuffix);
        return new FullyQualifiedJavaType(baseExampleType.getTypeFullName());
    }
}
