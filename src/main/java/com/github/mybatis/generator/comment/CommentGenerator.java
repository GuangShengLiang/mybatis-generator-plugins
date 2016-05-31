package com.github.mybatis.generator.comment;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Properties;
import java.util.Scanner;

/**
 * Created by lgs on 16-2-16.
 */
public class CommentGenerator extends DefaultCommentGenerator {
    //是否添加列的注解
    private boolean suppressColumnComments = true;

    public CommentGenerator() {
    }

    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
        if (properties.getProperty("suppressColumnComments") != null) {
            this.suppressColumnComments = StringUtility.isTrue(properties.getProperty("suppressColumnComments"));
        }
    }

    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (this.suppressColumnComments) {
            StringBuilder sb = new StringBuilder();
            field.addJavaDocLine("/**");
            sb.append(" * table ");
            sb.append(introspectedTable.getFullyQualifiedTable());
            sb.append('.');
            sb.append(introspectedColumn.getActualColumnName());
            field.addJavaDocLine(sb.toString());
            field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
//            this.addJavadocTag(field, false);
            field.addJavaDocLine(" */");
        }
    }
}
