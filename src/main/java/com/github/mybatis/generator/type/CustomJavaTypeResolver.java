package com.github.mybatis.generator.type;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;
import org.mybatis.generator.internal.types.JdbcTypeNameTranslator;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CustomJavaTypeResolver extends JavaTypeResolverDefaultImpl {
    private static final String CUSTOM_TYPE_MAP_PROPERTY_NAME = "customTypeMap";
    protected Map<Integer, FullyQualifiedJavaType> customTypeMap = new HashMap();

    public CustomJavaTypeResolver() {
    }

    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
        String p = this.properties.getProperty("customTypeMap");
        if (p != null) {
            String[] mapStrings = p.split(",");
            if (mapStrings.length > 0) {
                String[] arr$ = mapStrings;
                int len$ = mapStrings.length;

                for (int i$ = 0; i$ < len$; ++i$) {
                    String mapString = arr$[i$];
                    String[] jdbcAndJava = mapString.split(":");
                    if (jdbcAndJava.length >= 2) {
                        this.customTypeMap.put(Integer.valueOf(JdbcTypeNameTranslator.getJdbcType(jdbcAndJava[0].trim())), new FullyQualifiedJavaType(jdbcAndJava[1].trim()));
                    }
                }
            }
        }

    }

    public FullyQualifiedJavaType calculateJavaType(IntrospectedColumn introspectedColumn) {
        FullyQualifiedJavaType answer = (FullyQualifiedJavaType) this.customTypeMap.get(Integer.valueOf(introspectedColumn.getJdbcType()));
        return answer != null ? answer : super.calculateJavaType(introspectedColumn);
    }
}