BF={};
$(function(){
<#if columns??>
    using(["tciTable", "wdatepicker"], function(){
        Vars = {
            tabs: top.window.tabs,
            controllerPath: '/请求地址',
            yonghubh: ejs.rest.getUserid(),
            yonghumc: ejs.rest.getUsermc(),
            fuwujgbh: ejs.rest.getSfwbh(),
            fuwujgmc: ejs.rest.getSfwmc()
        };

        var WdatePicker_Setting = {
            isShowClear: true,   //显示清除按钮
            readOnly: false,     //控件是否只读
            firstDayOfWeek: 1,   //显示每周开始星期
            isShowToday: true,   //是否默认定位到今天
            dateFmt:'yyyyMMdd'  //自定义时间格式
        };

        BF = {
            pageLoad: function(){
                $("#riqi1").val(ejs.util.getCurrentDate("yyyyMM")+"01");
                $("#riqi2").val(ejs.util.getCurrentDate("yyyyMMdd"));
                $("#riqi1, #riqi2").focus(function(event) {
                    WdatePicker(WdatePicker_Setting);
                });
                BF.initTable();
            },
            initTable: function(){
                var row=parseInt((top.baseH-120)/32);
                $("#tablegrid").tciTable({
                    url: Vars.controllerPath + "请求方法",
                    data:{

                    },
                    colNames:[<#list columns as column>'${column.remark}',</#list>],
                    colModel:[<#list columns as column>
                                {name: '${column.remark}', index: '${column.field}', width: 100, align:'left'},
                            </#list>
                    ],
                    rowNum: row,
                    rownumbers: true,
                    showPage: true,
                    showOperBtn: true,
                    showCheckBtn: false,
                    editBtn: true,
                    editFun: function(){
                        var row= $(".tciTable").get(0).getCurRow(this);
                        BF.editData(row.lsh);
                    },
                    delBtn: true,
                    delFun: function(){
                        var row= $(".tciTable").get(0).getCurRow(this);
                        BF.delData(row.lsh);
                    },
                    callback:function(){
                        $(".tciTable").css("min-width", window.screen.availWidth - 220);
                        $(".tci_wrap").css({
                            "width": window.screen.availWidth - 220,
                            "height": top.baseH-130
                        });
                    }
                });
            },
            searchData: function(){
                $(".tciTable").get(0).setTciParam({
                    data:{
                    },
                }).trigger("reloadTciTable");
            },
            // 新增或查看信息
            editData: function(lsh){
                ejs.zDialog.Template.W({
                    Title: '弹窗标题',
                    URL: '弹窗打开的路径',
                    Height: '600px',
                    Width:'1070px',
                    ShowTop: 'top',
                    SelfOKEvent: function(result) {
                        BF.searchData();
                    }
                });
            },
            delData: function(lsh){
                layer.confirm("确定删除该条记录吗？", function(){
                    EDITPAGE.postAjax({
                        url: Vars.controllerPath+"/deleteLaorenzlnlpg/"+lsh,
                        successEvent: function (result) {
                            if (result.appcode == "0") {
                                layer.msg("删除成功",{icon: 1,offset: ['50px']});
                                BF.searchData();
                            } else {
                                layer.msg(result.databuffer,{icon: 2,offset: ['50px', (wWidth()-610)/2]});
                            }
                        }
                    });
                });
            }
        };

        BF.pageLoad();

        $("#btnSearch").bind("click", function(){
            BF.searchData();
        });

        $("#btnAdd").bind("click", function(){
            BF.editData('');
        });
    });
<#else>
    using(["wdatepicker"], function(){
        Vars = {
            tabs: top.window.tabs,
            controllerPath: '/请求地址',
            yonghubh: ejs.rest.getUserid(),
            yonghumc: ejs.rest.getUsermc(),
            fuwujgbh: ejs.rest.getSfwbh(),
            fuwujgmc: ejs.rest.getSfwmc()
        };

        var WdatePicker_Setting = {
            isShowClear: true,   //显示清除按钮
            readOnly: false,     //控件是否只读
            firstDayOfWeek: 1,   //显示每周开始星期
            isShowToday: true,   //是否默认定位到今天
            dateFmt:'yyyyMMdd'  //自定义时间格式
        };

        BF = {
            pageLoad: function(){
                BF.setDefaultData();
            },
            setDefaultData: function(){
                EDITPAGE.bindData({
                    url: Vars.controllerPath+"/请求方法",
                    successEvent: function (result) {

                    }
                });
            },
            save: function(){
                EDITPAGE.postAjax({
                    url: Vars.controllerPath + '/请求方法',
                    successEvent: function(result){
                        if(result.appcode == 0){
                            layer.msg(result.databuffer,{icon: 1,offset: ['50px']});
                            ownerDialog.SelfOKEvent();
                            ownerDialog.close();
                        }else{
                            layer.msg(result.databuffer,{icon: 2,offset: ['50px']});
                        }
                    }
                });
            },
            Close: function(){
                ownerDialog.close();
            }
        };

        BF.pageLoad();

        $(".ipt1").bind("focus", function(){
            $("td").removeAttr("style");
            $(this).parent().css("border","1px solid #0ea6b2");
        });

        var validate=$('input,select').uloveValidate({divClass:".frmdiv",scrollTop:45});
        $("#btnsave").bind("click", function(){
            var vri = validate.verify();
            if(!validate.verify()){
                layer.msg('请填写必填项!',{closeBtn: 1,icon: 2, time: 2000,offset: '50px'});
                return;
            }
            BF.save();
        });

        $("#btnClose").bind("click", function(){
            BF.Close();
        });

    });
</#if>
});