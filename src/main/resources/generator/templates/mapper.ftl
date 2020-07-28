package ${basePackage}.${mapperPackage};

import ${basePackage}.${entityPackage}.${className}PO;
import ${basePackage}.${entityPackage}.${className}PagePO;
import ${basePackage?substring(0, (basePackage)?last_index_of("."))}.commapper.MyMapper;

public interface ${className}Mapper extends MyMapper<${className}PO> {

    String getCommonLsh();

    List<${className}PagePO> gets${className}(${className}PagePO ${className?uncap_first}PagePO);
}