package ${basePackage}.${servicePackage};

import ${basePackage}.${entityPackage}.${className}PO;
import ${basePackage}.${entityPackage}.${className}PagePO;
import com.dongfang.mhh.mhhbase.ResMsg;
import com.dongfang.mhh.mhhbase.ResMsgpage;

public interface ${className}Service {

    ResMsgpage gets${className}(${className}PagePO ${className?uncap_first}PagePO);

    ResMsg get${className}(String lsh);

    ResMsg save${className}(${className}PO ${className?uncap_first}PO);

    ResMsg delete${className}(String lsh);
}