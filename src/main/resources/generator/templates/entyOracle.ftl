package ${basePackage};

<#if columns??>
public class ${className?cap_first}_s_enty {

<#list columns as column>
    <#if (column.type = 'varchar' || column.type = 'text' || column.type = 'uniqueidentifier'
        || column.type = 'varchar2' || column.type = 'nvarchar' || column.type = 'VARCHAR2'
        || column.type = 'VARCHAR'|| column.type = 'CLOB' || column.type = 'char' )>
    private String ${column.field};
    </#if>
    <#if column.type = 'timestamp' || column.type = 'date' || column.type = 'datetime'||column.type = 'TIMESTAMP' || column.type = 'DATE' || column.type = 'DATETIME'>
    private Date ${column.field};
    </#if>
    <#if column.type = 'int' || column.type = 'smallint'>
    private Integer ${column.field};
    </#if>
    <#if column.type = 'double' || column.type = 'number' || column.type = 'NUMBER'>
    private Double ${column.field};
    </#if>
    <#if column.type = 'bigint' || column.type = 'long' || column.type = 'LONG'>
    private Long ${column.field};
    </#if>
</#list>
<#list columns as column>
    <#if (column.type = 'varchar' || column.type = 'text' || column.type = 'uniqueidentifier'
        || column.type = 'varchar2' || column.type = 'nvarchar' || column.type = 'VARCHAR2'
        || column.type = 'VARCHAR'|| column.type = 'CLOB' || column.type = 'char')>
    public String get${column.field}(){
        return ${column.field};
    }

    public void set${column.field}(String ${column.field}){
        this.${column.field} = ${column.field};
    }
    </#if>

    <#if column.type = 'timestamp' || column.type = 'date' || column.type = 'datetime'||column.type = 'TIMESTAMP' || column.type = 'DATE' || column.type = 'DATETIME'>
    public Date get${column.field}(){
        return ${column.field};
    }

    public void set${column.field}(Date ${column.field}){
        this.${column.field} = ${column.field};
    }
    </#if>
    <#if column.type = 'int' || column.type = 'smallint'>
    public Integer get${column.field}(){
        return ${column.field};
    }

    public void set${column.field}(Integer ${column.field}){
        this.${column.field} = ${column.field};
    }

    </#if>
    <#if column.type = 'double' || column.type = 'number' || column.type = 'NUMBER'>
    public Double get${column.field}(){
        return ${column.field};
    }

    public void set${column.field}(Double ${column.field}){
        this.${column.field} = ${column.field};
    }
    </#if>
    <#if column.type = 'bigint' || column.type = 'long' || column.type = 'LONG'>

    public Long get${column.field}(){
        return ${column.field};
    }

    public void set${column.field}(Long ${column.field}){
        this.${column.field} = ${column.field};
    }
    </#if>

</#list>
<#else>
public class ${className?cap_first}_g_enty {

    private int page;
    private int rows;
    private int records;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getRecords(){
        return records;
    }

    public void setRecords(int records){
        this.records = records;
    }
</#if>
}
