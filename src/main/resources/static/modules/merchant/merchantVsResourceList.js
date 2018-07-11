var merchantVsResource = function () {
    var index =0;
    var loadData = function () {
        var aoColumns = [
            {"mData": "id"},
            {"mData": "channel"},
            {"mData": "merchantId"},
            {"mData": "url"},
            {"mData": "createUserName"},
            {"mData": "createTime"},
            {"mData": "status"},
            {"mData": null}
        ];
        var aoColumnDefs = [{
            "aTargets": [1],
            "mRender": function (a, b, c, d) {
                if(a==null||a.length==0){
                    return "";
                }else{
                    return a;
                }

            }
        },{
            "aTargets": [4],
            "mRender": function (a, b, c, d) {
                if(a==null||a.length==0){
                    return "";
                }else{
                    return a;
                }

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
            "aTargets": [6],
            "mRender": function (a, b, c, d) {
                if(a==0){
                    return "可以访问";
                }else{
                    return "不可访问";
                }

            }
        },{
            "aTargets": [7],
            "mRender": function (a, b, c, d) {
                return "<a class=\"red\" name=\"delete\" href=\"javascript:;\"> 删除 </a>";
            }
        }];
        var t = $("#dataTables-example");
        var csrf = $("#csrfId");
        initPageTable(t, "merchantVsResource/queryList?"+csrf.attr("name")+"="+csrf.attr("value"), aoColumns, aoColumnDefs, __queryHandler, __initHandler);
    };
    var __queryHandler =function (condition) {
        var merchantIdSearch = $("#merchantIdSearch").val();
        if (assertNotNullStr(merchantIdSearch)) condition.merchantIdSearch = merchantIdSearch;
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
                $.post("merchantVsResource/deleteMerchantVsResource?" + csrfName + "=" + csrf.attr("value"), param).then(function (data) {
                    if (data.code == 200) {
                        layer.msg("删除成功");
                        var dataTable = $("#dataTables-example").dataTable();
                        dataTable.fnReloadAjax();
                    }
                });
            });
        });

    };
    //添加权限
    var add =function () {
        $("#add").bind("click",function () {
            $("#id").val("");
            layer.open({
                area: '800px',
                shade: [0.8, '#393D49'],
                title: "添加权限",
                type: 1,
                content: $("#addWin"),
                btn: ['添加', '关闭'],
                success: function (layero, index) {
                    $("#form")[0].reset();
                    validateForm().resetForm();
                    loadUrl();
                    loadChannel();
                    loadMerchantId();
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
    var loadUrl = function () {
        $('#resourceIdList').select2({
            placeholder: "请选择权限",
            allowClear: true,
            ajax: {
                url: "merchantVsResource/queryUrl",
                cache: true,
                processResults: function (data) {
                    return {
                        results: data
                    };
                }
            }
        });
    };
    var loadChannel = function () {
        $('#channel').select2({
            placeholder: "请选择渠道号",
            allowClear: true,
            ajax: {
                url: "merchantVsResource/queryChannel",
                cache: true,
                processResults: function (data) {
                    return {
                        results: data
                    };
                }
            }
        });
    };
    var loadMerchantId = function () {
        $('#merchantId').select2({
            placeholder: "请选择商户号",
            allowClear: true,
            ajax: {
                url: "merchantVsResource/queryMerchantId",
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

    return {
        init:function () {
            loadData();
            add();
            query();
        }
    };
}();