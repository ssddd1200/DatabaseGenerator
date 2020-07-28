/*************************************对象单个获取和分页**************************************************/
/*********************************
  * CREATEDATE:
  * COMMENT:
*********************************/
PROCEDURE USP_GET_${className?upper_case}(LSH_IN IN VARCHAR2,
                               APPCODE_OUT OUT NUMBER,
                                DATABUFFER_OUT OUT VARCHAR2,
                                RESULTLIST_OUT OUT RESULTLIST) IS
BEGIN
    APPCODE_OUT := '0';
    DATABUFFER_OUT := '成功';
    OPEN RESULTLIST_OUT FOR
        SELECT * FROM ${tableName} WHERE LSH = LSH_IN;
END USP_GET_${className?upper_case};
/*********************************
    * CREATEDATE:
    * COMMENT:
*********************************/
PROCEDURE USP_G_${className?upper_case}(LSH_IN IN VARCHAR2,
                            YEHAO_IN  IN OUT NUMBER,
                            MAXLINES_IN IN OUT NUMBER,
                            MAXPAGE_OUT IN OUT NUMBER,
                            APPCODE_OUT OUT NUMBER,
                            DATABUFFER_OUT OUT VARCHAR2,
                            RESULTLIST_OUT OUT RESULTLIST) IS
N_YEHAO NUMBER(8);
VSQL VARCHAR2(2000);
VSQLWHERE VARCHAR2(300);
BEGIN
    APPCODE_OUT := '0';
    DATABUFFER_OUT := '成功';
    VSQLWHERE := ' 1 = 1';

    N_YEHAO := YEHAO_IN;
    IF N_YEHAO = 1 THEN
        VSQL := 'SELECT COUNT(*) FROM ${tableName} WHERE ' || VSQLWHERE;
        BEGIN
            EXECUTE IMMEDIATE VSQL INTO MAXPAGE_OUT;
        EXCEPTION WHEN OTHERS THEN
            APPCODE_OUT := -1;
            DATABUFFER_OUT := '获取分页失败' || TO_CHAR(SQLCODE)||':'||SQLERRM;
            RETURN;
        END;
    END IF;
    -- 处理分页SQL
    VSQL := 'SELECT * FROM (SELECT ROWNUM RN, TA.* FROM (' -- 取分页头
         || 'SELECT * FROM ${tableName} WHERE '
         || VSQLWHERE
         || ' ORDER BY LSH DESC) TA WHERE ROWNUM <=:N_YEAHAO) WHERE RN>:MAXLINES_IN';
    OPEN RESULTLIST_OUT FOR VSQL USING N_YEHAO*MAXLINES_IN,(N_YEHAO - 1)* MAXLINES_IN;
END USP_G_${className?upper_case};
/*************************************对象保存和删除**************************************************/
/*********************************
* CREATEDATE:
* COMMENT:
*********************************/
PROCEDURE USP_S_${className?upper_case}(
<#if columns??>
    <#list columns as column>
        ${column.field}_IN IN ${column.type},
    </#list>
    ZHUJIANZ_OUT OUT VARCHAR2,
    APPCODE_OUT OUT NUMBER,
    DATABUFFER_OUT OUT VARCAHR2
</#if>
) IS
V_CNT NUMBER(1) := 0;
V_LSH VARCHAR2 := LSH_IN;
BEGIN
    APPCODE_OUT := 0;
    DATABUFFER_OUT := '成功';
    IF V_LSH>0 THEN
        BEGIN
            SELECT COUNT(*) INTO V_CNT FROM DUAL
            WHERE EXISTS(SELECT 1 FROM ${tableName} WHERE LSH = V_LSH);
        EXCEPTION
            WHEN OTHERS THEN
                APPCODE_OUT := -1;
                DATABUFFER_OUT := '调用信息失败！'||TO_CHAR(SQLCODE)||':'||SQLERRM;
                RETURN;
        END;
    END IF;
    IF V_CNT = 0 THEN
        SELECT 序列名称.NEXTVAL INTO V_LSH FROM DUAL;
        ZHUJIANZ_OUT := V_LSH;
        BEGIN
            INSERT INTO ${tableName}
            (<#list columns as column>${column.field},</#list>)
            VALUES
            (<#list columns as column>${column.field}_IN,</#list>);
        EXCEPTION
            WHEN OTHERS THEN
            APPCODE_OUT := -1;
            DATABUFFER_OUT := '调用信息失败！'||TO_CHAR(SQLCODE)||':'||SQLERRM;
            RETURN;
        END;
    ELSE
        BEGIN
            UPDATE ${tableName}
            SET <#list columns as column>${column.field} = ${column.field}_IN, </#list>
            WHERE LSH = LSH_IN;
        EXCEPTION
            WHEN OTHERS THEN
                APPCODE_OUT := -1;
                DATABUFFER_OUT := '调用信息失败！'||TO_CHAR(SQLCODE)||':'||SQLERRM;
                RETURN;
        END;
    END IF;
    COMMIT;
END USP_S_${className?upper_case};
/*********************************
* CREATEDATE:
* COMMENT:
*********************************/
PROCEDURE USP_D_${className?upper_case}(
    LSH_IN IN VARCHAR2,
    ZHUJIANZ_OUT OUT VARCHAR2,
    APPCODE_OUT OUT NUMBER,
    DATABUFFER_OUT OUT VARCAHR2
) IS
BEGIN
    APPCODE_OUT := '0';
    DATABUFFER_OUT := '成功';
    BEGIN
        UPDATE ${tableName}
        SET SHANCHUBZ = 1
        WHERE LSH = LSH_IN;
    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            APPCODE_OUT := '-1';
            DATABUFFER_OUT := '调用信息失败:'|| TO_CHAR(SQLCODE)||':'||SQLERRM;
            RETURN;
    END;
    COMMIT;
END USP_D_${className?upper_case};