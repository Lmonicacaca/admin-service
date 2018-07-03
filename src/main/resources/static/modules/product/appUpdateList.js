var appUpdate = function () {
    var index =0;
    var loadData = function () {
        var aoColumns = [
            {"mData": "id"},
            {"mData": "channel"},
            {"mData": "appUpdateType"},
            {"mData": "url"},
            {"mData": "plistUrl"},
            {"mData": "version"},
            {"mData": "iosLogo"},
            {"mData": "content"},
            {"mData": "force"},
            {"mData": "createTime"},
            {"mData": "updateTime"},
            {"mData": null}
        ];
        var aoColumnDefs = [{
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
                    }else {
                        return "<a class=\"edit\" href='" + a + "' title='" + a + "'>APP下载</a>"
                    }
                }
            },{
            "aTargets": [4],
            "mRender": function (a, b, c, d) {
                if(a==null||a==""){
                    return "";
                }else{
                    return "<a class=\"edit\" href='"+a+"' title='"+a+"'>配置文件下载</a>"
                }

            }
        },{
            "aTargets": [6],
            "mRender": function (a, b, c, d) {
                if(a==null||a==""){
                    return "";
                }else{
                    return "<img src='"+a+"' style='max-height: 50px'/>"
                }

            }
        },
            {
                "aTargets": [9],
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
                "aTargets": [10],
                "mRender": function (a, b, c, d) {
                    if(a==null||a==""){
                        return ""
                    }else{
                        var date = new Date(a);
                        return date.getFullYear()+"-"+(date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-'+date.getDate() + ' '+date.getHours() + ':'+date.getMinutes() + ':'+date.getSeconds();
                    }
                }
            },{
            "aTargets": [11],
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
                        $("#oldImg").val(d.iosLogo);
                        $("#oldPlistUrl").val(d.plistUrl);
                        $("#oldUrl").val(d.url);
                        $("#version").val(d.version);
                        $("#content").val(d.content);
                        $("#url").val("");
                        $("#plistUrl").val("");
                        $("#iosLogo").val("");
                        var time = new Date(d.createTime);
                        $("#createTime").val(time);
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
                        if(d.appUpdateType=="Android"){
                            $("#showPlistUrl").css("display","none")
                            $("#showIosLogo").css("display","none")
                        }
                        if(d.appUpdateType=="IOS"){
                            $("#showPlistUrl").css("display","block")
                            $("#showIosLogo").css("display","block")
                        }
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
                            },
                            yes: function (i, layero) {
                                if ($('#form').valid()) {
                                    $("#form").ajaxSubmit({
                                        success: function (d) {
                                            if (d.code == 200) {
                                                var dataTable = $("#dataTables-example").dataTable();
                                                dataTable.fnReloadAjax();
                                                layer.close(i);
                                            } else {
                                                layer.msg("更新数据失败");
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
            $("#createTime").val(new Date());
            $("#channel").html("");
            $("#appUpdateType").html("");
            $("#url").val("");
            $("#plistUrl").val("");
            $("#version").val("");
            $("#iosLogo").val("");
            $("#content").val("");
            $("#force").html("");
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
                    changeType();
                },
                yes: function (layero, index) {
                    if ($("#form").valid()) {
                        $("#form").ajaxSubmit({
                            success: function (d) {
                                if (d.code == 200) {
                                    layer.msg("添加数据成功");
                                    var dataTable = $("#dataTables-example").dataTable();
                                    dataTable.fnReloadAjax();
                                    layer.closeAll();
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

    var changeType = function(){

        $("#appUpdateType").bind("change",function(){
            if($(this).val()=="Android"){
                $("#showPlistUrl").css("display","none");
                $("#showIosLogo").css("display","none");
            }else{
                $("#showPlistUrl").css("display","block");
                $("#showIosLogo").css("display","block");
            }
        })
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
                url:{
                    required: true
                },
                pListUrl:{
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
                    required: "类型不能为空!"
                },
                url:{
                    required: "安装包不能为空"
                },
                pListUrl:{
                    required: "配置文件不能为空"
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