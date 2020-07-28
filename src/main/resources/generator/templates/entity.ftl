package ${basePackage}.${entityPackage};

<#if columns??>
import javax.persistence.*;

@Table(name = "${tableName}")
public class ${className}PO {


<#list columns as column>
    /**
    * ${column.remark}
    */
    <#if column.isKey = '1'>
    @Id
    </#if>
    <#if (column.type = 'varchar' || column.type = 'text' || column.type = 'uniqueidentifier'
    || column.type = 'varchar2' || column.type = 'nvarchar' || column.type = 'VARCHAR2'
    || column.type = 'VARCHAR'|| column.type = 'CLOB' || column.type = 'char')>
    private String ${column.field?uncap_first};

    </#if>
    <#if column.type = 'timestamp' || column.type = 'date' || column.type = 'datetime'||column.type = 'TIMESTAMP' || column.type = 'DATE' || column.type = 'DATETIME'>
    private Date ${column.field?uncap_first};

    </#if>
    <#if column.type = 'int' || column.type = 'smallint'>
    private Integer ${column.field?uncap_first};

    </#if>
    <#if column.type = 'double'>
    private Double ${column.field?uncap_first};

    </#if>
    <#if column.type = 'bigint'>
    private Long ${column.field?uncap_first};

    </#if>
    <#if column.type = 'tinyint'>
    private Byte ${column.field?uncap_first};

    </#if>
</#list>

<#list columns as column>
    <#if (column.type = 'varchar' || column.type = 'text' || column.type = 'uniqueidentifier'
    || column.type = 'varchar2' || column.type = 'nvarchar' || column.type = 'VARCHAR2'
    || column.type = 'VARCHAR'|| column.type = 'CLOB' || column.type = 'char')>
    /**
    * 获取${column.remark}
    * @return ${column.field} - ${column.remark}
    */
    public String get${column.field?cap_first}(){
        return ${column.field};
    }

    /**
    * 设置${column.field}
    * @param ${column.field} - ${column.remark}
    */
    public void set${column.field?cap_first}(String ${column.field}){
        this.${column.field} = ${column.field};
    }
    </#if>

    <#if column.type = 'timestamp' || column.type = 'date' || column.type = 'datetime'||column.type = 'TIMESTAMP' || column.type = 'DATE' || column.type = 'DATETIME'>
    /**
    * 获取${column.remark}
    * @return ${column.field} - ${column.remark}
    */
    public Date get${column.field?cap_first}(){
        return ${column.field};
    }

    /**
    * 设置${column.field}
    * @param ${column.field} - ${column.remark}
    */
    public void set${column.field?cap_first}(Date ${column.field}){
        this.${column.field} = ${column.field};
    }
    </#if>
    <#if column.type = 'int' || column.type = 'smallint'>
    /**
    * 获取${column.remark}
    * @return ${column.field} - ${column.remark}
    */
    public Integer get${column.field?cap_first}(){
        return ${column.field};
    }

    /**
    * 设置${column.field}
    * @param ${column.field} - ${column.remark}
    */
    public void set${column.field?cap_first}(Integer ${column.field}){
        this.${column.field} = ${column.field};
    }

    </#if>
    <#if column.type = 'double'>
    /**
    * 获取${column.remark}
    * @return ${column.field} - ${column.remark}
    */
    public Double get${column.field?cap_first}(){
        return ${column.field};
    }

    /**
    * 设置${column.field}
    * @param ${column.field} - ${column.remark}
    */
    public void set${column.field?cap_first}(Double ${column.field}){
        this.${column.field} = ${column.field};
    }
    </#if>
    <#if column.type = 'bigint'>
    /**
    * 获取${column.remark}
    * @return ${column.field} - ${column.remark}
    */
    public Long get${column.field?cap_first}(){
        return ${column.field};
    }

    /**
    * 设置${column.field}
    * @param ${column.field} - ${column.remark}
    */
    public void set${column.field?cap_first}(Long ${column.field}){
        this.${column.field} = ${column.field};
    }
    </#if>
    <#if column.type = 'tinyint'>
    /**
    * 获取${column.remark}
    * @return ${column.field} - ${column.remark}
    */
    public Byte get${column.field?cap_first}(){
        return ${column.field};
    }

    /**
    * 设置${column.field}
    * @param ${column.field} - ${column.remark}
    */
    public void set${column.field?cap_first}(Byte ${column.field}){
        this.${column.field} = ${column.field};
    }
    </#if>

</#list>
<#else>
public class ${className}PagePO {

    private Integer page;
    private Integer rows;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
</#if>
}
