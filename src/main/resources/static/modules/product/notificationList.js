var notification = function () {
    var index =0;
    var loadData = function () {
        var aoColumns = [
            {"mData": "id"},
            {"mData": "channel"},
            {"mData": "type"},
            {"mData": "transfer"},
            {"mData": "transactionId"},
            {"mData": "title"},
            {"mData": "deviceId"},
            {"mData": "pushId"},
            {"mData": "createTime"},
            {"mData": null}
        ];
        var aoColumnDefs = [{
            "aTargets": [2],
            "mRender": function (a, b, c, d) {
                if(a==0){
                    return "交易";
                }else if(a==1){
                    return "支付";
                }else{
                    return "公告";
                }
            }
        },
            {
                "aTargets": [3],
                "mRender": function (a, b, c, d) {
                    if(a==0){
                        return "转入";
                    }else if(a==1){
                        return "转出";
                    }else if(a==2){
                        return "提现";
                    }else{
                        return "支付"
                    }
                }
            },
            {
                "aTargets": [4],
                "mRender": function (a, b, c, d) {
                    if(a==null){
                        return "";
                    }else{
                        return a;
                    }
                }
            },{
                "aTargets": [6],
                "mRender": function (a, b, c, d) {
                    if(a==null){
                        return "";
                    }else{
                        return a;
                    }
                }
            },
            {
                "aTargets": [7],
                "mRender": function (a, b, c, d) {
                    if(a==null){
                        return "";
                    }else{
                        return a;
                    }
                }
            },
            {
                "aTargets": [8],
                "mRender": function (a, b, c, d) {
                    if(a!=null||a!=""){
                        var date = new Date(a);
                        return date.getFullYear()+"-"+(date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-'+date.getDate() + ' '+date.getHours() + ':'+date.getMinutes() + ':'+date.getSeconds();
                    }

                }
            },{
            "aTargets": [9],
            "mRender": function (a, b, c, d) {
                return "<a class=\"edit\" name =\"edit\" href=\"javascript:;\"> 修改 </a>";
            }
        }];
        var t = $("#dataTables-example");
        var csrf = $("#csrfId");
        $("#typeSearch").append("<option value='-1'>请选择公告类型</option>");
        $("#typeSearch").append("<option value='0'>交易</option>");
        $("#typeSearch").append("<option value='1'>支付</option>");
        $("#typeSearch").append("<option value='2'>公告</option>");
        $("#dealSearch").append("<option value='-1'>请选择交易类型</option>");
        $("#dealSearch").append("<option value='0'>转入</option>");
        $("#dealSearch").append("<option value='0'>转出</option>");
        $("#dealSearch").append("<option value='0'>提现</option>");
        $("#dealSearch").append("<option value='0'>支付</option>");
        initPageTable(t, "notification/queryList?"+csrf.attr("name")+"="+csrf.attr("value"), aoColumns, aoColumnDefs, __queryHandler, __initHandler);
    };
    var __queryHandler =function (condition) {
        var typeSearch = $("#typeSearch option:selected").val();
        var dealSearch = $("#dealSearch option:selected").val();
        if (assertNotNullStr(typeSearch)) condition.typeSearch = typeSearch;
        if (assertNotNullStr(dealSearch)) condition.dealSearch = dealSearch;
    };
    var __initHandler =function () {

        // 编辑
        $("#dataTables-example tbody").on("click", "a[name='edit']", function () {
            if(index ==0) {
                index ++;
                var table = $('#dataTables-example').DataTable();
                var d = table.row($(this).parents('tr')).data();
                var csrf = $("#csrfId");
                var csrfName = csrf.attr('name');
                var param = {"id": d.id};
                $.post("notification/queryById?" + csrfName + "=" + csrf.attr("value"), param, function (data) {
                    if (data.code == 200) {
                        $("#channelShow").css("block","none");
                        $("#typeShow").css("block","none");
                        $("#transferShow").css("block","none");
                        var d = data.data;
                        $("#id").val(d.id);
                        $("#title").val(d.title);
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
            $("#channelShow").css("display","block");
            $("#typeShow").css("display","block");
            $("#transferShow").css("display","block");
            $("#id").val("");
            $("#title").val("");
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
                    loadChannel();
                    loadType();
                    loadTransfer();
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
    var loadType = function () {
        $('#type').select2({
            placeholder: "请选择公告类型",
            allowClear: true,
            ajax: {
                url: "notification/queryType",
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
                url: "notification/queryChannel",
                cache: true,
                processResults: function (data) {
                    return {
                        results: data
                    };
                }
            }
        });
    };
    var loadTransfer = function () {
        $('#transfer').select2({
            placeholder: "请选择交易类型",
            allowClear: true,
            ajax: {
                url: "notification/queryTransfer",
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
                channel: {
                    required: true
                },
                type: {
                    required: true
                },
                transfer: {
                    required: true,
                },
                title: {
                    required: true
                }
            },
            messages: {
                channel: {
                    required: "渠道号不能为空!"
                },
                type: {
                    required: "公告类型不能为空!"
                },
                transfer: {
                    required: "交易类型不能为空!"
                },
                title: {
                    required: "标题不能为空!"
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