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
    private boolean suppressAllComments;

    public CommentGenerator() {
    }

    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
        this.suppressAllComments = StringUtility.isTrue(properties.getProperty("suppressAllComments"));
    }

    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (!this.suppressAllComments) {
            StringBuilder sb = new StringBuilder();
            field.addJavaDocLine("/**");
            sb.append(" * This field corresponds to the database column ");
            sb.append(introspectedTable.getFullyQualifiedTable());
            sb.append('.');
            field.addJavaDocLine(sb.toString());
            Scanner scanner = new Scanner(introspectedColumn.getRemarks());

            while (scanner.hasNextLine()) {
                field.addJavaDocLine(" * " + scanner.nextLine());
            }

//            this.addJavadocTag(field, false);
            field.addJavaDocLine(" */");
        }
    }
}
