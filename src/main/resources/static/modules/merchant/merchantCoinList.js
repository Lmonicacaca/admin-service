var merchantCoin = function () {
    var index =0;
    var loadData = function () {
        var aoColumns = [
            {"mData": "id"},
            {"mData": "merchantId"},
            {"mData": "merchantName"},
            {"mData": "coinName"},
            {"mData": "address"},
            {"mData": "tokenAddress"},
            {"mData": "status"},
            {"mData": "createUserName"},
            {"mData": "createTime"},
            {"mData": "channel"},
            {"mData": null}
        ];
        var aoColumnDefs = [
            {
                "aTargets": [9],
                "mRender": function (a, b, c, d) {
                    if(a==null){
                        return "";
                    }else{
                        return a;
                    }

                }
            },{
                "aTargets": [2],
                "mRender": function (a, b, c, d) {
                    if(a==null){
                        return "";
                    }else{
                        return a;
                    }

                }
            },
            {
                "aTargets": [5],
                "mRender": function (a, b, c, d) {
                    if(a==null){
                        return "";
                    }else{
                        return a;
                    }

                }
            },
            {
            "aTargets": [6],
            "mRender": function (a, b, c, d) {
                if(a==0){
                    return "未审核";
                }else if(a==1){
                    return "审核通过";
                }else{
                    return "审核不通过"
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
                    if(a!=null){
                        var date = new Date(a);
                        return date.getFullYear()+"-"+(date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-'+date.getDate() + ' '+date.getHours() + ':'+date.getMinutes() + ':'+date.getSeconds();
                    }else{
                        return "";
                    }

                }
            },
            {
            "aTargets": [10],
            "mRender": function (a, b, c, d) {
                return "<a class=\"edit\" name =\"edit\" href=\"javascript:;\"> 修改 </a><a class=\"red\" name=\"delete\" href=\"javascript:;\"> 删除 </a>";
            }
        }];
        var t = $("#dataTables-example");
        var csrf = $("#csrfId");
        initPageTable(t, "merchantCoin/queryList?"+csrf.attr("name")+"="+csrf.attr("value"), aoColumns, aoColumnDefs, __queryHandler, __initHandler);
    };
    var __queryHandler =function (condition) {
        var merchant_Id = $("#merchant_Id").val();
        if (assertNotNullStr(merchant_Id)) condition.merchant_Id = merchant_Id;
        var nameSearch = $("#nameSearch").val();
        if (assertNotNullStr(nameSearch)) condition.nameSearch = nameSearch;
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
                $.post("merchantCoin/deleteMerchantCoin?" + csrfName + "=" + csrf.attr("value"), param).then(function (data) {
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
                $.post("merchantCoin/queryById?" + csrfName + "=" + csrf.attr("value"), param, function (data) {
                    if (data.code == 200) {
                        var d = data.data;
                        $("#address").val(d.address);
                        $("#merchantId").val(d.merchantId)
                        $("#id").val(d.id)
                        $("#createUserName").val(d.createUserName)
                        var date = new Date(d.createTime)
                        $("#createTime").val(date)
                        var optionChannel = "<option value='" + d.channel + "' selected='selected'>" + d.channel + "</option>";
                        $("#channel").empty();
                        $("#channel").append(optionChannel);
                        var optionCoin = "<option value='" + d.coinId + "' selected='selected'>" + d.coinName + "</option>";
                        $("#coinId").empty();
                        $("#coinId").append(optionCoin);
                        var status = "";
                        if(d.status==0){
                            status = "未审核"
                        }else if(d.status ==1){
                            status = "审核通过"
                        }else{
                            status = "审核不通过"
                        }
                        var optionStatus = "<option value='" + d.status + "' selected='selected'>" + status + "</option>";
                        $("#status").empty();
                        $("#status").append(optionStatus);

                        var optionMerchant = "<option value='" + d.merchantId + "' selected='selected'>" + d.merchantId + "</option>";
                        $("#merchantId").empty();
                        $("#merchantId").append(optionMerchant);
                        layer.open({
                            area: '800px',
                            shade: [0.8, '#393D49'],
                            title: "修改",
                            type: 1,
                            content: $("#addWin"),
                            btn: ['确定'],
                            success: function (layero, index) {
                                loadChannel();
                                loadCoin();
                                loadStatus();
                                loadMerchantId();
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
                            }
                            ,
                            cancel: function (i, layero) {
                                layer.close(i);
                                index = 0;
                            }
                        });
                    }
                });
        });

    };
    //添加数据
    var add =function () {
        $("#add").bind("click",function () {
            $("#address").val("");
            $("#merchantId").val("")
            $("#id").val("")
            $("#status").html("")
            $("#createTime").val(new Date);
            $("#channel").html("")
            $("#coinId").html("")
            $("#createUserName").val("")
            layer.open({
                area: '800px',
                shade: [0.8, '#393D49'],
                title: "添加商户充值信息",
                type: 1,
                content: $("#addWin"),
                btn: ['添加', '关闭'],
                success: function (layero, index) {
                    $("#form")[0].reset();
                    validateForm().resetForm();
                    loadChannel();
                    loadCoin();
                    loadStatus();
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
    var loadChannel = function () {
        $('#channel').select2({
            placeholder: "请选择渠道号",
            allowClear: true,
            ajax: {
                url: "merchantCoin/queryChannel",
                cache: true,
                processResults: function (data) {
                    return {
                        results: data
                    };
                }
            }
        });
    };
    var loadCoin = function () {
        $('#coinId').select2({
            placeholder: "请选择币种",
            allowClear: true,
            ajax: {
                url: "merchantCoin/queryCoin",
                cache: true,
                processResults: function (data) {
                    return {
                        results: data
                    };
                }
            }
        });
    };
    var loadStatus = function () {
        $('#status').select2({
            placeholder: "请选择审核状态",
            allowClear: true,
            ajax: {
                url: "merchantCoin/queryStatus",
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
                url: "merchantCoin/queryMerchantId",
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
                channel:{
                    required: true
                },
                merchantId:{
                    required: true
                },
                merchantName: {
                    required: true
                },
                address: {
                    required: true
                },
                coinId: {
                    required: true,

                }
            },
            messages: {
                channel:{
                    required: "渠道号不能为空!"
                },
                merchantId:{
                    required: "商户id不能为空!"
                },
                merchantName: {
                    required: "商户名不能为空!"
                },
                address: {
                    required: "充值地址不能为空!"
                },
                coinId: {
                    required: "币种不能为空!"
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