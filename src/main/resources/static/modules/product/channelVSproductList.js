var channelVSproduct = function () {
    var index =0;
    var loadData = function () {
        var aoColumns = [
            {"mData": "id"},
            {"mData": "channel"},
            {"mData": "productId"},
            {"mData": "onlineStatus"},
            {"mData": "orderNo"},
            {"mData": "isForceShow"},
            {"mData": "merchantShow"},
            {"mData": "createTime"},
            {"mData": null}
        ];
        var aoColumnDefs = [
            {
            "aTargets": [3],
            "mRender": function (a, b, c, d) {
                if(a==0){
                    return "可用";
                }else{
                    return "不可用";
                }

            }
        },
            {
                "aTargets": [5],
                "mRender": function (a, b, c, d) {
                    if(a==0){
                        return "是";
                    }else{
                        return "否";
                    }

                }
            },
            {
                "aTargets": [6],
                "mRender": function (a, b, c, d) {
                    if(a==true){
                        return "是";
                    }else{
                        return "否";
                    }

                }
            },
            {
                "aTargets": [7],
                "mRender": function (a, b, c, d) {
                    if(a==null||a==""){
                        return "";
                    }else{
                        var date = new Date(a);
                        return date.getFullYear()+"-"+(date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-'+date.getDate() + ' '+date.getHours() + ':'+date.getMinutes() + ':'+date.getSeconds();
                    }

                }
            },{
            "aTargets": [8],
            "mRender": function (a, b, c, d) {
                return "<a class=\"edit\" name =\"edit\" href=\"javascript:;\"> 修改 </a><a class=\"red\" name=\"delete\" href=\"javascript:;\"> 删除 </a>";
            }
        }];
        var t = $("#dataTables-example");
        var csrf = $("#csrfId");
        initPageTable(t, "productVsChannel/queryList?"+csrf.attr("name")+"="+csrf.attr("value"), aoColumns, aoColumnDefs, __queryHandler, __initHandler);
    };
    var __queryHandler =function (condition) {
        var channelQuery = $("#channelQuery").val();
        var productQuery = $("#productQuery").val();
        if (assertNotNullStr(channelQuery)) condition.channelQuery = channelQuery;
        if (assertNotNullStr(productQuery)) condition.productQuery = productQuery;
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
                $.post("productVsChannel/deleteProductVsChannel?" + csrfName + "=" + csrf.attr("value"), param).then(function (data) {
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
                $.post("productVsChannel/queryById?" + csrfName + "=" + csrf.attr("value"), param, function (data) {
                    if (data.code == 200) {
                        var d = data.data;
                        $("#id").val(d.id);
                        $("#oldChannel").val(d.channel);
                        $("#oldProductId").val(d.productId);
                        var optionChannel = "<option value='" + d.channel + "' selected='selected'>" + d.channel + "</option>";
                        $("#channel").empty();
                        $("#channel").append(optionChannel);
                        var optionProductId = "<option value='" + d.productId + "' selected='selected'>" + d.productId + "</option>";
                        $("#productId").empty();
                        $("#productId").append(optionProductId);
                        var optionChannel = "<option value='" + d.channel + "' selected='selected'>" + d.channel + "</option>";
                        $("#channel").empty();
                        $("#channel").append(optionChannel);
                        var onlineStatus = "";
                        if(d.onlineStatus==0){
                            onlineStatus = "可用";
                        }else {
                            onlineStatus = "不可用";
                        }
                        var optionOnlineStatus = "<option value='" + d.onlineStatus + "' selected='selected'>" + onlineStatus + "</option>";
                        $("#onlineStatus").empty();
                        $("#onlineStatus").append(optionOnlineStatus);

                        var isForceShow = "";
                        if(d.isForceShow==0){
                            isForceShow = "是";
                        }else {
                            isForceShow = "否";
                        }
                        var optionIsForceShow = "<option value='" + d.isForceShow + "' selected='selected'>" + isForceShow + "</option>";
                        $("#isForceShow").empty();
                        $("#isForceShow").append(optionIsForceShow);

                        var merchantShow = "";
                        if(d.merchantShow==true){
                            merchantShow = "是";
                        }else {
                            merchantShow = "否";
                        }
                        var optionMerchantShow = "<option value='" + d.merchantShow + "' selected='selected'>" + merchantShow + "</option>";
                        $("#merchantShow").empty();
                        $("#merchantShow").append(optionMerchantShow);
                        layer.open({
                            area: '800px',
                            shade: [0.8, '#393D49'],
                            title: "修改",
                            type: 1,
                            content: $("#addWin"),
                            btn: ['确定'],
                            success: function (layero, index) {
                                loadChannel();
                                loadProductId();
                                loadOnlineStatus();
                                loadIsForceShow();
                                loadMerchantShow();
                            },
                            yes: function (i, layero) {
                                if ($('#form').valid()) {
                                    $("#form").ajaxSubmit({
                                        success: function (d) {
                                            if (d.code == 200) {
                                                layer.msg("数据更新成功");
                                                var dataTable = $("#dataTables-example").dataTable();
                                                dataTable.fnReloadAjax();
                                                layer.close(i);
                                            } else {
                                                layer.msg(d.message);
                                            }
                                        }
                                    });
                                }
                            },
                            cancel: function (i, layero) {
                                layer.close(i);
                            }
                        });
                    }
                });
        });
    };
    //添加
    var add =function () {
        $("#add").bind("click",function () {
            $("#id").val("");
            $("#channel").html("");
            $("#oldChannel").val("");
            $("#productId").html("");
            $("#oldProductId").val("");
            $("#onlineStatus").html("");
            $("#orderNo").html("");
            $("#isForceShow").html("");
            $("#merchantShow").html("");
            layer.open({
                area: '800px',
                shade: [0.8, '#393D49'],
                title: "添加渠道号VS币",
                type: 1,
                content: $("#addWin"),
                btn: ['添加', '关闭'],
                success: function (layero, index) {
                    $("#form")[0].reset();
                    validateForm().resetForm();
                    loadChannel();
                    loadProductId();
                    loadOnlineStatus();
                    loadIsForceShow();
                    loadMerchantShow();
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
        $('select[name="channel"]').select2({
            placeholder: "请选择渠道号",
            allowClear: true,
            ajax: {
                url: "productVsChannel/queryChannel",
                cache: true,
                processResults: function (data) {
                    return {
                        results: data
                    };
                }
            }
        });
    };
    var loadProductId = function () {
        $('select[name="productId"]').select2({
            placeholder: "请选择币种",
            allowClear: true,
            ajax: {
                url: "productVsChannel/queryProductId",
                cache: true,
                processResults: function (data) {
                    return {
                        results: data
                    };
                }
            }
        });
    };
    var loadOnlineStatus = function () {
        $('select[name="onlineStatus"]').select2({
            placeholder: "请选择状态",
            allowClear: true,
            ajax: {
                url: "productVsChannel/queryOnlineStatus",
                cache: true,
                processResults: function (data) {
                    return {
                        results: data
                    };
                }
            }
        });
    };
    var loadIsForceShow = function () {
        $('select[name="isForceShow"]').select2({
            placeholder: "请选择是否强制显示",
            allowClear: true,
            ajax: {
                url: "productVsChannel/queryIsForceShow",
                cache: true,
                processResults: function (data) {
                    return {
                        results: data
                    };
                }
            }
        });
    };
    var loadMerchantShow = function () {
        $('select[name="merchantShow"]').select2({
            placeholder: "请选择是否显示到商家",
            allowClear: true,
            ajax: {
                url: "productVsChannel/queryMerchantShow",
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
                productId: {
                    required: true
                },
                onlineStatus: {
                    required: true,
                },
                isForceShow: {
                    required: true
                },
                merchantShow: {
                    required: true
                }
            },
            messages: {
                channel: {
                    required: "渠道号不能为空!"
                },
                productId: {
                    required: "币id不能为空!"
                },
                onlineStatus: {
                    required: "状态不能为空!"
                },
                isForceShow: {
                    required: "是否强制显示不能为空!"
                },
                merchantShow: {
                    required: "是否显示到商家不能为空!"
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
            loadChannel();
            loadProductId();
        }
    };
}();