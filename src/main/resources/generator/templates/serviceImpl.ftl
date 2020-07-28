package ${basePackage}.${servicePackage};

import ${basePackage}.${entityPackage}.${className}PO;
import ${basePackage}.${entityPackage}.${className}PagePO;
import ${basePackage}.${mapperPackage}.${className}Mapper;
import ${basePackage}.${servicePackage}.${className}Service;
import com.dongfang.mhh.mhhbase.ResMsg;
import com.dongfang.mhh.mhhbase.ResMsgpage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;


@Service
public class ${className}ServiceImpl implements ${className}Service {

    @Autowired
    private ${className}Mapper ${className?uncap_first}Mapper;

    @Override
    public ResMsgpage gets${className}(${className}PagePO ${className?uncap_first}PagePO) {
        PageHelper.startPage(${className?uncap_first}PagePO.getPage(), ${className?uncap_first}PagePO.getRows());
        // 使用xml文件写sql文件
        List<${className}PagePO> list = ${className?uncap_first}Mapper.gets${className}(${className?uncap_first}PagePO);
        return setterPageRes(list, ${className?uncap_first}PagePO.getPage());
    }

    //分页工具方法
    private static ResMsgpage setterPageRes(List<?> list, Integer page){
        PageInfo<?> pageList = new PageInfo<>(list);
        ResMsgpage resMsgpage = new ResMsgpage();
        resMsgpage.setPage(Long.valueOf(page));
        resMsgpage.setAppcode("0");
        resMsgpage.setDatabuffer("成功");
        resMsgpage.setTotal(Long.valueOf(pageList.getPages()));
        resMsgpage.setRecords(pageList.getTotal());
        resMsgpage.setResultlist(list);
        return resMsgpage;
    }

    @Override
    public ResMsg get${className}(String lsh) {
        return new ResMsg(${className?uncap_first}Mapper.selectByPrimaryKey(lsh));
    }

    @Override
    public ResMsg save${className}(${className}PO ${className?uncap_first}PO) {

        boolean isExists = ${className?uncap_first}Mapper.existsWithPrimaryKey(${className?uncap_first}PO.getLsh());
        if (isExists){
            int upd = ${className?uncap_first}Mapper.updateUseTime(${className?uncap_first}PO);
            if (upd > 0){
                return new ResMsg(${className?uncap_first}PO.getLsh());
            }
        }

        String lsh = ${className?uncap_first}Mapper.getCommonLsh();
        ${className?uncap_first}PO.setLsh(lsh);
        int save = ${className?uncap_first}Mapper.insertUseTime(${className?uncap_first}PO);
        if (save > 0){
            return new ResMsg(${className?uncap_first}PO.getLsh());
        }
        return new ResMsg("-1","失败","");
    }

    @Override
    public ResMsg delete${className}(String lsh) {

        ${className}PO ${className?uncap_first}PO = new ${className}PO();
        ${className?uncap_first}PO.setLsh(lsh);
        ${className?uncap_first}PO.setShanchubz("1");
        int delCount = ${className?uncap_first}Mapper.updateByPrimaryKeySelective(${className?uncap_first}PO);
        if (delCount > 0){
            return new ResMsg(lsh);
        }
        return new ResMsg("-1","失败","");
    }
}
