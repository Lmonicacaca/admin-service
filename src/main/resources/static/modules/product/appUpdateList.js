var appUpdate = function () {
    var index =0;
    var loadData = function () {
        var aoColumns = [
            {"mData": "id"},
            {"mData": "channel"},
            {"mData": "appUpdateType"},
            {"mData": "appUpdateBuild"},
            {"mData": "url"},
            {"mData": "plistUrl"},
            {"mData": "version"},
            {"mData": "iosLogo"},
            {"mData": "size"},
            {"mData": "content"},
            {"mData": "force"},
            {"mData": "createTime"},
            {"mData": "updateTime"},
            {"mData": null}
        ];
        var aoColumnDefs = [
            {
            "aTargets": [1],
            "mRender": function (a, b, c, d) {
                if(a==null||a==""){
                    return "";
                }else{
                    return a;
                }

            }
        },
            {
                "aTargets": [3],
                "mRender": function (a, b, c, d) {
                    if(a==null||a==""){
                        return "";
                    }else{
                        return a;
                    }

                }
            },
            {
                "aTargets": [4],
                "mRender": function (a, b, c, d) {
                    if(a==null||a==""){
                        return "";
                    }else {
                        return "<a class=\"edit\" href='" + a + "' title='" + a + "'>url下载</a>"
                    }
                }
            },
            {
                "aTargets": [5],
                "mRender": function (a, b, c, d) {
                    if(a==null||a==""){
                        return "";
                    }else {
                        return "<a class=\"edit\" href='" + a + "' title='" + a + "'>plistUrl下载</a>"
                    }
                }
            },{
            "aTargets": [7],
            "mRender": function (a, b, c, d) {
                if(a==null||a==""){
                    return "";
                }else{
                    return "<img src='"+a+"' style='max-height: 50px'/>"
                }

            }
        },{
                "aTargets": [8],
                "mRender": function (a, b, c, d) {
                    if(a==null||a==""){
                        return "";
                    }else{
                        return a;
                    }

                }
            },
            {
                "aTargets": [11],
                "mRender": function (a, b, c, d) {
                    if(a==null||a==""){
                        return ""
                    }else{
                        var date = new Date(a);
                        return date.getFullYear()+"-"+(date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-'+date.getDate() + ' '+date.getHours() + ':'+date.getMinutes() + ':'+date.getSeconds();
                    }

                }
            },
            {
                "aTargets": [12],
                "mRender": function (a, b, c, d) {
                    if(a==null||a==""){
                        return ""
                    }else{
                        var date = new Date(a);
                        return date.getFullYear()+"-"+(date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-'+date.getDate() + ' '+date.getHours() + ':'+date.getMinutes() + ':'+date.getSeconds();
                    }
                }
            },{
            "aTargets": [13],
            "mRender": function (a, b, c, d) {
                return "<a class=\"edit\" name =\"edit\" href=\"javascript:;\"> 修改 </a><a class=\"red\" name=\"delete\" href=\"javascript:;\"> 删除 </a>";
            }
        }];
        var t = $("#dataTables-example");
        var csrf = $("#csrfId");
        initPageTable(t, "appUpdate/queryList?"+csrf.attr("name")+"="+csrf.attr("value"), aoColumns, aoColumnDefs, __queryHandler, __initHandler);
    };
    var __queryHandler =function (condition) {
        var versionSearch = $("#versionSearch").val();
        if (assertNotNullStr(versionSearch)) condition.versionSearch = versionSearch;
    };
    var __initHandler =function () {
        //删除
        $("#dataTables-example tbody").on("click", "a[name='delete']", function () {
            var table = $('#dataTables-example').DataTable();
            var d = table.row($(this).parents('tr')).data();
            var param = {"id": d.id};
            var csrf = $("#csrfId");
            var csrfName = csrf.attr('name');
            layer.confirm("你确定要删除该数据吗？", function (index) {
                $.post("appUpdate/deleteAppUpdate?" + csrfName + "=" + csrf.attr("value"), param).then(function (data) {
                    if (data.code == 200) {
                        layer.msg("删除成功");
                        var dataTable = $("#dataTables-example").dataTable();
                        dataTable.fnReloadAjax();
                    }
                });
            });
        });
        // 编辑
        $("a[name='edit']").on("click", function () {
                var table = $('#dataTables-example').DataTable();
                var d = table.row($(this).parents('tr')).data();
                var csrf = $("#csrfId");
                var csrfName = csrf.attr('name');
                var param = {"id": d.id};
                $.post("appUpdate/queryById?" + csrfName + "=" + csrf.attr("value"), param, function (data) {
                    if (data.code == 200) {
                        var d = data.data;
                        $("#id").val(d.id);
                        $("#oldIosLogo").val(d.iosLogo);
                        $("#oldPlistUrl").val(d.plistUrl);
                        $("#oldUrl").val(d.url);
                        $("#oldSize").val(d.size);
                        $("#version").val(d.version);
                        $("#content").val(d.content);
                        $("#url").val("");
                        $("#plistUrl").val("");
                        $("#iosLogo").val("");
                        var time = new Date(d.createTime);
                        $("#createTime").val(time);
                        $("#updateTime").val("");
                        $("#channel").empty();
                        if(d.channel!=null&&d.channel!=""){
                            var optionChannel = "<option value='" + d.channel + "' selected='selected'>" + d.channel + "</option>";
                            $("#channel").append(optionChannel);
                        }
                        var optionForce = "<option value='" + d.force + "' selected='selected'>" + d.force + "</option>";
                        $("#force").empty();
                        $("#force").append(optionForce);
                        var optionType = "<option value='" + d.appUpdateType + "' selected='selected'>" + d.appUpdateType + "</option>";
                        $("#appUpdateType").empty();
                        $("#appUpdateType").append(optionType);
                        $("#appUpdateType").attr("disabled",true);
                        var optionBuild = "<option value='" + d.appUpdateBuild + "' selected='selected'>" + d.appUpdateBuild + "</option>";
                        $("#appUpdateBuild").empty();
                        $("#appUpdateBuild").append(optionBuild);
                        $("#appUpdateBuild").attr("disabled",true);
                        $("#version").attr("disabled",true);
                        $("#showIosLogo").css("display","none");
                        $("#showUrl").css("display","none");
                        layer.open({
                            area: '800px',
                            shade: [0.8, '#393D49'],
                            title: "修改",
                            type: 1,
                            content: $("#addWin"),
                            btn: ['确定'],
                            success: function (layero, index) {
                                loadChannel();
                                loadAppUpdateType();
                                loadForce();
                                loadAppUpdateBuild();
                            },
                            yes: function (i, layero) {
                                if ($('#form').valid()) {
                                    $("#appUpdateType").attr("disabled",false);
                                    $("#appUpdateBuild").attr("disabled",false);
                                    $("#version").attr("disabled",false);
                                    $("#form").ajaxSubmit({
                                        success: function (d) {
                                            if (d.code == 200) {
                                                layer.msg("更新成功");
                                                var dataTable = $("#dataTables-example").dataTable();
                                                dataTable.fnReloadAjax();
                                                layer.close(i);
                                            } else {
                                                layer.msg(d.message);
                                            }
                                        }
                                    });
                                }
                                index = 0;
                            },
                            cancel: function (i, layero) {
                                layer.close(i);
                                index = 0;
                            }
                        });
                    }
                });
        });
    };
    //添加用户
    var add =function () {
        $("#add").bind("click",function () {
            $("#id").val("");
            $("#createTime").val("");
            $("#updateTime").val("");
            $("#channel").html("");
            $("#appUpdateType").html("");
            $("#appUpdateBuild").html("");
            $("#url").val("");
            $("#version").val("");
            $("#iosLogo").val("");
            $("#content").val("");
            $("#force").html("");
            $("#oldSize").val(0);
            $("#appUpdateType").attr("disabled",false);
            $("#appUpdateBuild").attr("disabled",false);
            $("#version").attr("disabled",false);
            $("#showUrl").css("display","block");
            $("#showIosLogo").css("display","block");
            layer.open({
                area: '800px',
                shade: [0.8, '#393D49'],
                title: "添加更新版本",
                type: 1,
                content: $("#addWin"),
                btn: ['添加', '关闭'],
                success: function (layero, index) {
                    validateForm().resetForm();
                    loadChannel();
                    loadAppUpdateType();
                    loadForce();
                    loadAppUpdateBuild();
                },
                yes: function (i, index) {
                    if ($("#form").valid()) {
                        $("#form").ajaxSubmit({
                            success: function (d) {
                                if (d.code == 200) {
                                    layer.msg("添加成功");
                                    var dataTable = $("#dataTables-example").dataTable();
                                    dataTable.fnReloadAjax();
                                    layer.close(i);
                                } else {
                                    layer.msg(d.message);
                                }
                            }
                        })
                    }
                },
                cancel: function (index, layero) {
                    layer.close(index);
                }
            });

        })
    };
    var query = function () {
        //查询按钮
        $("#query").click(function () {
            var dataTable = $("#dataTables-example").dataTable();
            dataTable.fnReloadAjax();
        });
    }
    var loadChannel = function () {
        $('#channel').select2({
            placeholder: "请选择渠道号",
            allowClear: true,
            ajax: {
                url: "appUpdate/queryChannel",
                cache: true,
                processResults: function (data) {
                    return {
                        results: data
                    };
                }
            }
        });
    };
    var loadAppUpdateType = function () {
        $('#appUpdateType').select2({
            placeholder: "请选择类型",
            allowClear: true,
            ajax: {
                url: "appUpdate/queryType",
                cache: true,
                processResults: function (data) {
                    return {
                        results: data
                    };
                }
            }
        });
    };
    var loadAppUpdateBuild = function () {
        $('#appUpdateBuild').select2({
            placeholder: "请选择",
            allowClear: true,
            ajax: {
                url: "appUpdate/queryBuild",
                cache: true,
                processResults: function (data) {
                    return {
                        results: data
                    };
                }
            }
        });
    };
    var loadForce = function () {
        $('#force').select2({
            placeholder: "请选择是否强制更新",
            allowClear: true,
            ajax: {
                url: "appUpdate/queryForce",
                cache: true,
                processResults: function (data) {
                    return {
                        results: data
                    };
                }
            }
        });
    };


   var validateForm = function () {
        var validate = $('#form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                appUpdateType: {
                    required: true
                },
                appUpdateBuild: {
                    required: true
                },
                url:{
                    required: true
                },
                version: {
                    required: true
                },
                force: {
                    required: true
                }
            },
            messages: {
                appUpdateType: {
                    required: "系统类型不能为空!"
                },
                appUpdateBuild: {
                    required: "build不能为空！"
                },
                url:{
                    required: "安装包不能为空"
                },
                version: {
                    required: "版本号不能为空!"
                },
                force: {
                    required: "是否强制更新不能为空!"
                }
            },
            highlight: function (element) { // hightlight error inputs
                $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
            },
            unhighlight: function (element) { // revert the change done by hightlight
                $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
            },
            success: function (label) {
                label.closest('.form-group').removeClass('has-error');
            },
            errorPlacement: function (error, element) {
                if (element.parent(".input-group").size() > 0) {
                    error.insertAfter(element.parent(".input-group"));
                } else if (element.attr("data-error-container")) {
                    error.appendTo(element.attr("data-error-container"));
                } else if (element.parents('.radio-list').size() > 0) {
                    error.appendTo(element.parents('.radio-list').attr("data-error-container"));
                } else if (element.parents('.radio-inline').size() > 0) {
                    error.appendTo(element.parents('.radio-inline').attr("data-error-container"));
                } else if (element.parents('.checkbox-list').size() > 0) {
                    error.appendTo(element.parents('.checkbox-list').attr("data-error-container"));
                } else if (element.parents('.checkbox-inline').size() > 0) {
                    error.appendTo(element.parents('.checkbox-inline').attr("data-error-container"));
                } else {
                    error.insertAfter(element); // for other inputs, just perform default behavior
                }
            }
        });
        return validate;
    };
   var  assertNotNullStr = function(/**Any Object*/obj) {
        if (( typeof obj == "undefined") || (obj == null) || (obj === "null") || (obj === "undefined") || (obj === ""))
            return false;
        return true;
    };

    return {
        init:function () {
            loadData();
            add();
            query();
        }
    };
}();