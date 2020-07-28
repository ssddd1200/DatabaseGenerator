package ${basePackage}.${controllerPackage};

import ${basePackage}.${entityPackage}.${className}PO;
import ${basePackage}.${entityPackage}.${className}PagePO;
import ${basePackage}.${servicePackage}.${className}Service;
import com.dongfang.mhh.mhhbase.ResMsgpage;
import com.dongfang.mhh.mhhbase.ResMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/${className?uncap_first}")
public class ${className}Controller {

    @Autowired
    private ${className}Service ${className?uncap_first}Service;

    @PostMapping(value="/gets${className}", produces = {"application/json;charset=UTF-8"})
    public ResMsgpage gets${className}(@RequestBody ${className}PagePO ${className?uncap_first}PagePO){
        return ${className?uncap_first}Service.gets${className}(${className?uncap_first}PagePO);
    }

    @GetMapping(value="/get${className}", produces = {"application/json;charset=UTF-8"})
    public ResMsg get${className}(@RequestParam String lsh) {
        return ${className?uncap_first}Service.get${className}(lsh);
    }

    @PostMapping(value="/save${className}", produces = {"application/json;charset=UTF-8"})
    public ResMsg save${className}(@RequestBody ${className}PO ${className?uncap_first}PO){
        return ${className?uncap_first}Service.save${className}(${className?uncap_first}PO);
    }

    @PostMapping(value="/delete${className}/{lsh}", produces = {"application/json;charset=UTF-8"})
    public ResMsg delete${className}(@PathVariable String lsh) {
        return ${className?uncap_first}Service.delete${className}(lsh);
    }
}