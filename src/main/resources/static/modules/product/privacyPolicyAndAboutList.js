var privacyPolicyAndAbout = function () {
    var index =0;
    var loadData = function () {
        var aoColumns = [
            {"mData": "id"},
            {"mData": "channel"},
            {"mData": "type"},
            {"mData": "content"},
            {"mData": "language"},
            {"mData": "createTime"},
            {"mData": null}
        ];
        var aoColumnDefs = [{
            "aTargets": [2],
            "mRender": function (a, b, c, d) {
                if(a==0){
                    return "隐私协议";
                }else{
                    return "关于我们";
                }

            }
        },{
            "aTargets": [5],
            "mRender": function (a, b, c, d) {
                var date = new Date(a);
                return date.getFullYear()+"-"+(date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-'+date.getDate() + ' '+date.getHours() + ':'+date.getMinutes() + ':'+date.getSeconds();
            }
        },
            {
                "aTargets": [6],
                "mRender": function (a, b, c, d) {
                    return "<a class=\"edit\" name =\"edit\" href=\"javascript:;\"> 修改 </a><a class=\"red\" name=\"delete\" href=\"javascript:;\"> 删除 </a>";
                }
            }];
        var t = $("#dataTables-example");
        var csrf = $("#csrfId");
        $.ajax({
            url:getRootPath+"/privacyPolicyAndAbout/queryChannel",
            type:"post",
            dataType:"json",
            success:function(data){
                alert(data)
                var channelSearch = $("#channelSearch");
                each(data,function(i,o){
                    var option = "<option value='"+o.id+"'>o.id</option>";
                    channelSearch.append(option);
                })
            }
        })

        initPageTable(t, "privacyPolicyAndAbout/queryList?"+csrf.attr("name")+"="+csrf.attr("value"), aoColumns, aoColumnDefs, __queryHandler, __initHandler);
    };
    var __queryHandler =function (condition) {
        var channelSearch = $("#channelSearch").val();
        if (assertNotNullStr(channelSearch)) condition.channelSearch = channelSearch;

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
                $.post("privacyPolicyAndAbout/deletePrivacyPolicyAndAbout?" + csrfName + "=" + csrf.attr("value"), param).then(function (data) {
                    if (data.code == 200) {
                        layer.msg("删除成功");
                        var dataTable = $("#dataTables-example").dataTable();
                        dataTable.fnReloadAjax();
                    }
                });
            });
        });
        // 编辑
        $("#dataTables-example tbody").on("click", "a[name='edit']", function () {
            if(index ==0) {
                index ++;
                var table = $('#dataTables-example').DataTable();
                var d = table.row($(this).parents('tr')).data();
                var csrf = $("#csrfId");
                var csrfName = csrf.attr('name');
                var param = {"id": d.id};
                $.post("privacyPolicyAndAbout/queryById?" + csrfName + "=" + csrf.attr("value"), param, function (data) {
                    if (data.code == 200) {
                        var d = data.data;
                        $("#id").val(d.id);
                        $("#content").val(d.content);
                        layer.open({
                            area: '800px',
                            shade: [0.8, '#393D49'],
                            title: "修改",
                            type: 1,
                            content: $("#addWin"),
                            btn: ['确定'],
                            success: function (layero, index) {
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
            }
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
                                    layer.msg("当前用户已存在");
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