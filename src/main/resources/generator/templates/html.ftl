<!DOCTYPE html>
<html>
<#if columns??>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="/resources/css/base.css" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/dialog.css">
    <link rel="stylesheet" type="text/css" href="/resources/plugins/css/css2.css"></link>
    <script type="text/javascript" src="/resources/core/ejs_api.min.js"></script>
    <script type="text/javascript" src="/resources/core/plugins/validate/validate2.js"></script>
    <script type="text/javascript" src="pagejs/edit_${className?uncap_first}.js"></script>
</head>
<body>
<div class="content">
    <form class="frm" style="margin: 5px;">
        <div class="frmdiv">
            <div class="form_tit"><span></span></div>
            <table border="1" align="center" style="width: 90%;max-width: 960px;">
                <#list columns as column>
                    <tr>
                        <td><p>${column.remark}</p></td>
                        <td class="tdPadding10">
                            <input type="text" id="${column.field}" name="${column.field}" class="ipt1"/>
                        </td>
                    </tr>
                </#list>
            </table>
            <div style="height: 30px;"></div>
            <div class="nodisplay">

            </div>
        </div>
    </form>
</div>
<div class="operButtons">
    <div class="radibtn"><input id="btnclose" type="button" class="btnCl" value="关闭"/></div>
    <div class="radibtn"><input id="btnsave" type="button" class="btnC" value="保存"/></div>
</div>
</body>
<#else>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="/resources/css/base.css" type="text/css"/>
    <script type="text/javascript" src="/resources/core/ejs_api.min.js"></script>
    <script type="text/javascript" src="pagejs/grid_${className?uncap_first}.js"></script>
</head>
<body>
<div class="content">
    <div class="content-r">
        <div class="content-rTop bordRouT">
            <div class="toolbar-1">
                <label>随访日期</label>
                <input type="text" id="riqi1" name="riqi1" class="input-time timeL" placeholder="开始日期">
                ~
                <input type="text" id="riqi2" name="riqi2" class="input-time timeR" placeholder="结束日期">
                <input type="button" class="input-btn2wd" id="btnSearch" value="查询">
            </div>
            <div class="operButtons">
                <input type="button" id="btnAdd" class="input-btn2wd" value="新增"/>
            </div>
        </div>
        <div class="content-rMid bordRouM">
            <div>
                <table id="tablegrid" class="tciTable"></table>
            </div>
        </div>
    </div>
    <div class="nodisplay">
    </div>
</div>
</body>
</#if>
</html>