package com.github.mybatis.generator.plugins;


import com.github.mybatis.generator.type.FileFullName;
import com.github.mybatis.generator.type.TypeFullName;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.util.List;

/**
 * Created by lgs on 16-2-16.
 */
public class RenamePlugin extends PluginAdapter {
    public static final String SEARCH_PROPERTY_NAME = "renamePlugin.search";
    public static final String REPLACE_PROPERTY_NAME = "renamePlugin.replace";
    public static final String PREFIX_PROPERTY_NAME = "renamePlugin.prefix";
    public static final String SUFFIX_PROPERTY_NAME = "renamePlugin.suffix";

    public RenamePlugin() {
    }

    public boolean validate(List<String> strings) {
        return true;
    }

    public void initialized(IntrospectedTable introspectedTable) {
        String contextSearch = this.context.getProperty("renamePlugin.search");
        String contextReplace = this.context.getProperty("renamePlugin.replace");
        if (contextSearch != null && contextReplace != null) {
            this.replaceContext(introspectedTable, contextSearch, contextReplace);
        }

        String modelSearch = this.context.getJavaModelGeneratorConfiguration().getProperty("renamePlugin.search");
        String modelReplace = this.context.getJavaModelGeneratorConfiguration().getProperty("renamePlugin.replace");
        if (modelSearch != null && modelReplace != null) {
            this.replaceModel(introspectedTable, modelSearch, modelReplace);
        }

        String sqlMapSearch = this.context.getSqlMapGeneratorConfiguration().getProperty("renamePlugin.search");
        String sqlMapReplace = this.context.getSqlMapGeneratorConfiguration().getProperty("renamePlugin.replace");
        if (sqlMapSearch != null && sqlMapReplace != null) {
            this.replaceSqlMap(introspectedTable, sqlMapSearch, sqlMapReplace);
        }

        String clientSearch = this.context.getJavaClientGeneratorConfiguration().getProperty("renamePlugin.search");
        String clientReplace = this.context.getJavaClientGeneratorConfiguration().getProperty("renamePlugin.replace");
        if (clientSearch != null && clientReplace != null) {
            this.replaceClient(introspectedTable, clientSearch, clientReplace);
        }

        String contextPrefix = this.context.getProperty("renamePlugin.prefix");
        String contextSuffix = this.context.getProperty("renamePlugin.suffix");
        String modelPrefix = this.context.getJavaModelGeneratorConfiguration().getProperty("renamePlugin.prefix");
        String sqlMapPrefix = this.context.getSqlMapGeneratorConfiguration().getProperty("renamePlugin.prefix");
        String clientPrefix = this.context.getJavaClientGeneratorConfiguration().getProperty("renamePlugin.prefix");
        modelPrefix = modelPrefix != null ? modelPrefix : (contextPrefix != null ? contextPrefix : "");
        sqlMapPrefix = sqlMapPrefix != null ? sqlMapPrefix : (contextPrefix != null ? contextPrefix : "");
        clientPrefix = clientPrefix != null ? clientPrefix : (contextPrefix != null ? contextPrefix : "");
        String modelSuffix = this.context.getJavaModelGeneratorConfiguration().getProperty("renamePlugin.suffix");
        String sqlMapSuffix = this.context.getSqlMapGeneratorConfiguration().getProperty("renamePlugin.suffix");
        String clientSuffix = this.context.getJavaClientGeneratorConfiguration().getProperty("renamePlugin.suffix");
        modelSuffix = modelSuffix != null ? modelSuffix : (contextSuffix != null ? contextSuffix : "");
        sqlMapSuffix = sqlMapSuffix != null ? sqlMapSuffix : (contextSuffix != null ? contextSuffix : "");
        clientSuffix = clientSuffix != null ? clientSuffix : (contextSuffix != null ? contextSuffix : "");
        this.fixModel(introspectedTable, modelPrefix, modelSuffix);
        this.fixSqlMap(introspectedTable, sqlMapPrefix, sqlMapSuffix);
        this.fixclient(introspectedTable, clientPrefix, clientSuffix);
    }

    private void replaceContext(IntrospectedTable introspectedTable, String search, String replace) {
        this.replaceModel(introspectedTable, search, replace);
        this.replaceSqlMap(introspectedTable, search, replace);
        this.replaceClient(introspectedTable, search, replace);
    }

    private void replaceModel(IntrospectedTable introspectedTable, String search, String replace) {
        introspectedTable.setBaseRecordType((new TypeFullName(introspectedTable.getBaseRecordType())).replaceTypeShortName(search, replace).getTypeFullName());
        introspectedTable.setExampleType((new TypeFullName(introspectedTable.getExampleType())).replaceTypeShortName(search, replace).getTypeFullName());
        introspectedTable.setRecordWithBLOBsType((new TypeFullName(introspectedTable.getRecordWithBLOBsType())).replaceTypeShortName(search, replace).getTypeFullName());
        introspectedTable.setPrimaryKeyType((new TypeFullName(introspectedTable.getPrimaryKeyType())).replaceTypeShortName(search, replace).getTypeFullName());
    }

    private void replaceSqlMap(IntrospectedTable introspectedTable, String search, String replace) {
        introspectedTable.setMyBatis3XmlMapperFileName((new FileFullName(introspectedTable.getMyBatis3XmlMapperFileName())).replaceTypeShortName(search, replace).getFileShortName());
        introspectedTable.setIbatis2SqlMapFileName((new FileFullName(introspectedTable.getIbatis2SqlMapFileName())).replaceTypeShortName(search, replace).getFileShortName());
    }

    private void replaceClient(IntrospectedTable introspectedTable, String search, String replace) {
        introspectedTable.setMyBatis3JavaMapperType((new TypeFullName(introspectedTable.getMyBatis3JavaMapperType())).replaceTypeShortName(search, replace).getTypeFullName());
        introspectedTable.setMyBatis3SqlProviderType((new TypeFullName(introspectedTable.getMyBatis3SqlProviderType())).replaceTypeShortName(search, replace).getTypeFullName());
        introspectedTable.setDAOInterfaceType((new TypeFullName(introspectedTable.getDAOInterfaceType())).replaceTypeShortName(search, replace).getTypeFullName());
        introspectedTable.setDAOImplementationType((new TypeFullName(introspectedTable.getDAOImplementationType())).replaceTypeShortName(search, replace).getTypeFullName());
    }

    private void fixModel(IntrospectedTable introspectedTable, String prefix, String suffix) {
        introspectedTable.setBaseRecordType((new TypeFullName(introspectedTable.getBaseRecordType())).fixTypeShortName(prefix, suffix).getTypeFullName());
        introspectedTable.setExampleType((new TypeFullName(introspectedTable.getExampleType())).fixTypeShortName(prefix, suffix).getTypeFullName());
        introspectedTable.setRecordWithBLOBsType((new TypeFullName(introspectedTable.getRecordWithBLOBsType())).fixTypeShortName(prefix, suffix).getTypeFullName());
        introspectedTable.setPrimaryKeyType((new TypeFullName(introspectedTable.getPrimaryKeyType())).fixTypeShortName(prefix, suffix).getTypeFullName());
    }

    private void fixSqlMap(IntrospectedTable introspectedTable, String prefix, String suffix) {
        introspectedTable.setMyBatis3XmlMapperFileName((new FileFullName(introspectedTable.getMyBatis3XmlMapperFileName())).fixTypeShortName(prefix, suffix).getFileShortName());
        introspectedTable.setIbatis2SqlMapFileName((new FileFullName(introspectedTable.getIbatis2SqlMapFileName())).fixTypeShortName(prefix, suffix).getFileShortName());
    }

    private void fixclient(IntrospectedTable introspectedTable, String prefix, String suffix) {
        introspectedTable.setMyBatis3JavaMapperType((new TypeFullName(introspectedTable.getMyBatis3JavaMapperType())).fixTypeShortName(prefix, suffix).getTypeFullName());
        introspectedTable.setMyBatis3SqlProviderType((new TypeFullName(introspectedTable.getMyBatis3SqlProviderType())).fixTypeShortName(prefix, suffix).getTypeFullName());
        introspectedTable.setDAOInterfaceType((new TypeFullName(introspectedTable.getDAOInterfaceType())).fixTypeShortName(prefix, suffix).getTypeFullName());
        introspectedTable.setDAOImplementationType((new TypeFullName(introspectedTable.getDAOImplementationType())).fixTypeShortName(prefix, suffix).getTypeFullName());
    }
}

