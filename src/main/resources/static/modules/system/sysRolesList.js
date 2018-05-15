var sysRoles = function () {
    var index = 0;
    var loadData = function () {
        var aoColumns = [
            {"mData": "id"},
            {"mData": "roleName"},
            {"mData": "roleDesc"},
            {"mData": "roleAuth"},
            {"mData": null}
        ];
        var aoColumnDefs = [{
            "aTargets": [4],
            "mRender": function (a, b, c, d) {
                return "<a class=\"edit\" name =\"edit\" href=\"javascript:;\"> 修改 </a>" +
                    " <a class=\"edit\" name=\"settingsAuth\" href=\"javascript:;\"> 设置权限 </a> " +
                    "<a class=\"red\" name=\"delete\" href=\"javascript:;\"> 删除 </a>";
            }
        }];
        var t = $("#dataTables");
        var csrf = $("#csrfId");
        initPageTable(t, "sysRole/queryList?"+csrf.attr("name")+"="+csrf.attr("value"), aoColumns, aoColumnDefs, __queryHandler, __initHandler);
    };
    var __queryHandler =function (condition) {
        var roleName = $("#roleNameQuery").val();
        if (assertNotNullStr(roleName)) condition.roleName = roleName;
    };
    var __initHandler =function () {

        $("#dataTables tbody").on("click", "a[name='delete']", function () {
            var table = $('#dataTables').DataTable();
            var d = table.row($(this).parents('tr')).data();
            var param = {"id": d.id};
            var csrf = $("#csrfId");
            var csrfName = csrf.attr('name');
            layer.confirm("你确定要删除该数据吗？", function (index) {
                $.post("sysRole/delete?" + csrfName + "=" + csrf.attr("value"), param).then(function (data) {
                    if (data.code == 200) {
                        layer.msg("删除成功");
                        var dataTable = $("#dataTables").dataTable();
                        dataTable.fnReloadAjax();
                    }
                });
            });
        });
        $("#dataTables tbody").on("click", "a[name='edit']", function () {
            if(index ==0) {
                index ++;
                var table = $('#dataTables').DataTable();
                var d = table.row($(this).parents('tr')).data();
                var csrf = $("#csrfId");
                var csrfName = csrf.attr('name');
                var param = {"id": d.id};
                $.post("sysRole/getById?" + csrfName + "=" + csrf.attr("value"), param, function (data) {
                    if (data.code == 200) {
                        var d = data.data;
                        $("#role_name").val(d.roleName);
                        $("#roleId").val(d.id);
                        $("#role_auth").val(d.roleAuth);
                        $("#roleDesc").val(d.roleDesc);
                        layer.open({
                            area: '800px',
                            shade: [0.8, '#393D49'],
                            title: "修改",
                            type: 1,
                            content: $("#win"),
                            btn: ['确定'],
                            success: function (layero, index) {
                            },
                            yes: function (i, layero) {
                                if ($('#form').valid()) {
                                    $("#form").ajaxSubmit({
                                        success: function (d) {
                                            if (d.code == 200) {
                                                var dataTable = $("#dataTables").dataTable();
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
            }
        });

        $("#dataTables tbody").on("click", "a[name='settingsAuth']", function () {
            if(index ==0) {
                index++;
                var table = $('#dataTables').DataTable();
                var d = table.row($(this).parents('tr')).data();
                var csrf = $("#csrfId");
                var treeid = "configTree";
                layer.open({
                    area: '800px',
                    shade: [0.8, '#393D49'],
                    title: "设置权限",
                    type: 1,
                    content: $("#treeWin"),
                    btn: ['确定'],
                    success: function (layero, i) {

                        showCheckboxTree("sysRole/getTree?id="+d.id+"&t="+new Date().getTime(),treeid);
                    },
                    yes: function (i, layero) {
                      /*  var ids = getCheckboxTreeSelNode(treeid);
                        var jsonString = JSON.stringify(ids);
                        console.log(jsonString);*/
                        var checkedNodes = $("#"+treeid).jstree("get_checked");
                        console.log(checkedNodes);
                        var jsonString = JSON.stringify(checkedNodes);
                        $.post("sysRole/addRoleReSource?"+csrf.attr("name")+"="+csrf.attr("value"),{"id": d.id,"resourceId":jsonString},function (data) {
                            layer.msg("设置成功");
                            layer.closeAll();
                        },"json");
                        index = 0;
                    },
                    cancel: function (i, layero) {
                        layer.close(i);
                        index = 0;
                    }
                });
            }
        });
    };
    var add =function () {
        $("#add").bind("click",function () {
            $("#roleId").val("");
            layer.open({
                area: '800px',
                shade: [0.8, '#393D49'],
                title: "添加用户",
                type: 1,
                content: $("#win"),
                btn: ['添加', '关闭'],
                success: function (layero, index) {
                    $("#form")[0].reset();
                    validateForm().resetForm();
                },
                yes: function (layero, index) {
                    if ($("#form").valid()) {
                        $("#form").ajaxSubmit({
                            success: function (d) {
                                if (d.code == 200) {
                                    layer.msg("添加数据成功");
                                    var dataTable = $("#dataTables").dataTable();
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
            var dataTable = $("#dataTables").dataTable();
            dataTable.fnReloadAjax();
        });
    };
   var  validateForm=function () {
        var validate = $('#form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                roleName: {
                    required: true
                },
                roleAuth: {
                    required: true
                }
            },
            messages: {
                roleName: {
                    required: "角色名称不能为空!"
                },
                roleAuth: {
                    required: "角色代码不能为空!"
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
    }
    return {
        init:function () {
            loadData();
            add();
            query();
        }
    };
}();