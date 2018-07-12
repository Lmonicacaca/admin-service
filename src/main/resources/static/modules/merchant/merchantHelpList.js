var merchantHelp = function () {
    var index =0;
    var loadData = function () {
        var aoColumns = [
            {"mData": "id"},
            {"mData": "title"},
            {"mData": "content"},
            {"mData": "catName"},
            {"mData": "createUserName"},
            {"mData": "createTime"},
            {"mData": "lang"},
            {"mData": null}
        ];
        var aoColumnDefs = [{
            "aTargets": [2],
            "mRender": function (a, b, c, d) {
               return "<a class=\"edit\" name =\"content\" href=\"javascript:;\">内容</a>"
            }
        },{
            "aTargets": [5],
            "mRender": function (a, b, c, d) {
                if(a==null||a.length==0){
                    return "";
                }else{
                    var date = new Date(a);
                    return date.getFullYear()+"-"+(date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-'+date.getDate() + ' '+date.getHours() + ':'+date.getMinutes() + ':'+date.getSeconds();
                }

            }
        },{
            "aTargets": [7],
            "mRender": function (a, b, c, d) {
                return "<a class=\"edit\" name =\"edit\" href=\"javascript:;\"> 修改 </a><a class=\"red\" name=\"delete\" href=\"javascript:;\"> 删除 </a>";
            }
        }];
        var t = $("#dataTables-example");
        var csrf = $("#csrfId");
        initPageTable(t, "merchantHelp/queryList?"+csrf.attr("name")+"="+csrf.attr("value"), aoColumns, aoColumnDefs, __queryHandler, __initHandler);
    };
    var __queryHandler =function (condition) {
        var titleSearch = $("#titleSearch").val();
        if (assertNotNullStr(titleSearch)) condition.titleSearch = titleSearch;
    };
    var __initHandler =function () {

        //显示文章内容
        $("#dataTables-example tbody").on("click", "a[name='content']", function () {
            var table = $('#dataTables-example').DataTable();
            var d = table.row($(this).parents('tr')).data();
            var content = "";
            $.ajax({
                url: getRootPath() + "/merchantHelp/queryContent",
                type: "get",
                dataType: "text",
                data: "id=" + d.id,
                async: false,
                success: function (data) {
                    content = data;
                }
            })
            content = "<h2 align='center'><strong>标题:" + d.title + "</strong></h2>" + content;
            $("#contentShow").html(content);
            var index = layer.open({
                area: '800px',
                shade: [0.8, '#393D49'],
                title: "内容详情",
                type: 1,
                content: $("#showNews"),
                cancel: function (index, layero) {
                    $(".layui-layer-shade").hide();
                    layer.closeAll();
                },
                success: function (layero) {
                    $(".layui-layer-shade").css("z-index", 1);
                }

            });
        });

        //删除
        $("#dataTables-example tbody").on("click", "a[name='delete']", function () {
            var table = $('#dataTables-example').DataTable();
            var d = table.row($(this).parents('tr')).data();
            var param = {"id": d.id};
            var csrf = $("#csrfId");
            var csrfName = csrf.attr('name');
            layer.confirm("你确定要删除该数据吗？", function (index) {
                $.post("merchantHelp/deleteMerchantHelp?" + csrfName + "=" + csrf.attr("value"), param).then(function (data) {
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
                $.post("sysUser/queryById?" + csrfName + "=" + csrf.attr("value"), param, function (data) {
                    if (data.code == 200) {
                        var d = data.data;
                        $("#username").val(d.user.username);
                        $("#name").val(d.user.name);
                        $("#userId").val(d.user.id);
                        $("#passwordPwd").hide();
                        var option = "<option value='" + d.role.id + "' selected='selected'>" + d.role.roleName + "</option>";
                        $("#roleId").empty();
                        $("#roleId").append(option);
                        layer.open({
                            area: '800px',
                            shade: [0.8, '#393D49'],
                            title: "修改",
                            type: 1,
                            content: $("#addWin"),
                            btn: ['确定'],
                            success: function (layero, index) {
                                loadRole();
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
            $("#userId").val("");
            layer.open({
                area: '800px',
                shade: [0.8, '#393D49'],
                title: "添加用户",
                type: 1,
                content: $("#addWin"),
                btn: ['添加', '关闭'],
                success: function (layero, index) {
                    $("#form")[0].reset();
                    validateForm().resetForm();
                    loadRole();
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
    var loadRole = function () {
        $('#roleId').select2({
            placeholder: "请选择角色",
            allowClear: true,
            ajax: {
                url: "sysUser/querySysRole",
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
                username: {
                    required: true
                },
                name: {
                    required: true
                },
                password: {
                    required: true,
                    minlength: 6,
                    maxlength: 10
                },
                roleId: {
                    required: true
                }
            },
            messages: {
                username: {
                    required: "用户名不能为空!"
                },
                name: {
                    required: "姓名不能为空!"
                },
                password: {
                    required: "密码不能为空!"
                },
                roleId: {
                    required: "角色不能为空!"
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
    function getRootPath(){
        var strFullPath=window.document.location.href;
        var strPath=window.document.location.pathname;
        var pos=strFullPath.indexOf(strPath);
        var prePath=strFullPath.substring(0,pos);
        var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);
        return(prePath+postPath);
    }
    return {
        init:function () {
            loadData();
            add();
            query();
        }
    };
}();