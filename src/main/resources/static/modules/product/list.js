var product = function () {
    var index = 0;
    $("#querys .control-label,.col-sm-4").css("width","auto");
    $("#querys").css("height","44px");
    //加载数据
    var loadData = function () {
        //字段映射
        var aoColumns = [
            {"mData": "id"},
            {"mData": "coinName"},
            {"mData": "coinDescription"},
            {"mData": "onlineStatus"},
            {"mData": "chainType"},
            {"mData": "tokenAddress"},
            {"mData": "coinDecimals"},
            {"mData": "isForceShow"},
            {"mData": "merchantShow"},
            {"mData": "orderNo"},
            {"mData": null}

        ];
        //数据的格式化处理: aTargets=表示你要处理的行数,从0开始
        var aoColumnDefs = [{
            //操作按钮
            "aTargets": [10],
            "mRender": function (a, b, c, d) {
                return "<a class=\"detail\" name =\"detail\" href=\"javascript:;\"> 查看 </a> " +
                        "<a class=\"edit\" name =\"edit\" href=\"javascript:;\"> 修改 </a> " +
                        "<a class=\"delete\" name=\"delete\" href=\"javascript:;\"> 删除 </a>";
            }
        },{
            "aTargets": [3],
            "mRender": function (a, b, c, d) {
                if (a==0){
                    return "是";
                }else {
                    return "否";
                }
            }
        },{
            "aTargets": [7],
            "mRender": function (a, b, c, d) {
                if (a==0){
                    return "是";
                }else {
                    return "否";
                }
            }
        },{
            "aTargets": [8],
            "mRender": function (a, b, c, d) {

                if (a){
                    return "显示";
                }else {
                    return "不显示";
                }
            }
        }];

        //发送POST请求所需要的数据
        var t = $("#dataTables-example");
        var csrf = $("#csrfId");
        //发送POST请求加载数据
        initPageTable(t,"product/queryList?"+csrf.attr("name")+"="+csrf.attr("value"), aoColumns, aoColumnDefs, __queryHandler, __initHandler);
    };

    //获取查询条件
    var __queryHandler =function (condition) {

        var coinNameBut = $("#coinNameBut").val();
        if (assertNotNullStr(coinNameBut)) condition.coinNameBut = coinNameBut;
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
                $.post("product/deleteById?" + csrfName + "=" + csrf.attr("value"), param).then(function (data) {

                    if (data.code == 200) {
                        layer.msg("删除成功");
                        var dataTable = $("#dataTables-example").dataTable();
                        dataTable.fnReloadAjax();
                        layer.close(index);
                    }
                });
            });
        });
        //查看
        $("#dataTables-example tbody").on("click", "a[name='detail']", function () {
            if(index == 0) {
                index++;
                var table = $('#dataTables-example').DataTable();
                var d = table.row($(this).parents('tr')).data();
                layer.open({
                    area: ['900px', '800px'],
                    shade: [0.8, '#393D49'],
                    title: "详细信息",
                    type: 1,
                    content: $("#detail"),
                    btn: ['关闭'],
                    success: function (layero, index) {
                        var param = {"id": d.id};
                        var csrf = $("#csrfId");
                        var csrfName = csrf.attr('name');
                        $.post("product/queryById?" + csrfName + "=" + csrf.attr("value"), param).then(function (data) {
                            if (data.code == 200) {
                                var da = data.data;
                                $("#coinNameDetail").text(da.coinName);
                                $("#coinTypeDetail").text(da.coinType == 0 ? "主链币" : "代币");
                                $("#coinAvatarUrlDetail").attr("src", da.coinAvatarUrl);
                                $("#coinDescriptionDetail").text(da.coinDescription);
                                $("#coinErc20Detail").text(da.coinErc20 == "0" ? "否" : "是");
                                $("#onlineStatusDetail").text(da.onlineStatus == "0" ? "是" : "否");
                                $("#chainTypeDetail").text(da.chainType);
                                $("#tokenAddressDetail").text(da.tokenAddress);
                                $("#coinDecimalsDetail").text(da.coinDecimals);
                                $("#orderNoDetail").text(da.orderNo);
                                $("#isForceShowDetail").text(da.isForceShow == "0" ? "是" : "否");
                                $("#merchantShowDetail").text(da.merchantShow ? "是" : "否");
                                $("#createTimeDetail").text(moment(da.createTime).format('YYYY-MM-DD HH:mm:ss'));
                                $("#updateTimeDetail").text(moment(da.updateTime).format('YYYY-MM-DD HH:mm:ss'));
                            }
                        });
                    },
                    yes: function (layero, i) {
                        layer.closeAll();
                        index = 0;
                    }, cancel: function (layero, i) {
                        layer.closeAll();
                        index = 0;
                    }
                });
            }
        });
        
        
        //修改
        $("#dataTables-example tbody").on("click", "a[name='edit']", function () {
                if(index == 0){
                    index ++;
                    var v = validateForms();
                    var table = $('#dataTables-example').DataTable();
                    var d = table.row($(this).parents('tr')).data();

                    layer.open({
                        area: ['900px','800px'],
                        shade: [0.8, '#393D49'],
                        title: "修改",
                        type: 1,
                        content: $("#verdict"),
                        btn: ['确定', '关闭'],
                        success: function (layero, index) {
                            imgset('#coinAvatarUrls',"#coinAvatarUrlEdits");
                            imgset('#coinAvatarUrl1s',"#coinAvatarUrlEdit1s");
                            $("#forms")[0].reset();
                            v.resetForm();

                            $("#coinAvatarUrlEdits").attr('src',d.coinAvatarUrl);
                            $("#coinAvatarUrlEdit1s").attr('src',d.coinAvatarUrl1);

                            $("#coinNames").val(d.coinName);
                            $("#coinDecimalss").val(d.coinDecimals);


                            if(d.onlineStatus==0){
                                $("#onlineStatuss").prop("checked",true)
                            }else{
                                $("#onlineStatuss").prop("checked",false)
                            }
                            $("#coinNames").val(d.coinName);
                            $("#coinDecimalss").val(d.coinDecimals);
                            $("#tokenAddresss").val(d.tokenAddress);
                            $("#withdrawFeeRates").val(d.withdrawFeeRate);
                            $("#transactionFeeRates").val(d.transactionFeeRate);
                            $("#coinTypes").val(d.coinType);
                            $("#chainTypes").val(d.chainType);
                            $("#types").val(d.type);
                            $("#coinDescriptions").val(d.coinDescription);
                        },
                        yes: function (i, layero) {
                            if($("#coinAvatarUrlEdits").attr("src")==""){
                                layer.msg("你的url图标为空！！");
                                return;
                            }else if($("#coinAvatarUrlEdit1s").attr("src")==""){
                                layer.msg("你的url1图标为空！！");
                                return;
                            }

                            if ($('#forms').valid()) {
                                $("#forms").ajaxSubmit({
                                    data:{
                                        "id":d.id,
                                        "coinType":$("#coinTypes").find("option:selected").val(),
                                        "chainType":$("#chainTypes").find("option:selected").val(),
                                        "type":$("#types").find("option:selected").val()
                                    },
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
                        btn2: function (i, layero) {
                            layer.close(i);
                            index = 0;
                        },
                        cancel: function (i, layero) {
                            layer.close(i);
                            index = 0;
                        }
                    });
                }
            }

        );

    };
    //添加
    var add =function () {
        $("#add").bind("click",function () {
            var v = validateForm();
            $("#coinInfoId").val("");
            $("#coinAvatarUrlEdit").attr("src","");
            $("#coinAvatarUrlEdit1").attr("src","");
            layer.open({
                area: ['900px','800px'],
                shade: [0.8, '#393D49'],
                title: "添加用户",
                type: 1,
                content: $("#win"),
                btn: ['添加', '关闭'],
                success: function (layero, index) {
                    imgset('#coinAvatarUrl',"#coinAvatarUrlEdit");
                    imgset('#coinAvatarUrl1',"#coinAvatarUrlEdit1");
                    $("#form")[0].reset();
                    v.resetForm();
                },
                yes: function (layero, i) {
                    if ($("#form").valid()) {
                        $("#form").ajaxSubmit({
                            data: {
                                "coinType":$("#coinType").find("option:selected").val(),
                                "chainType":$("#chainType").find("option:selected").val(),
                                "type":$("#type").find("option:selected").val()
                            },
                            success: function (d) {
                                if (d.code == 200) {
                                    layer.msg("添加数据成功");
                                    var dataTable = $("#dataTables-example").dataTable();
                                    dataTable.fnReloadAjax();
                                    layer.closeAll();
                                } else {
                                    layer.msg(d.msg);
                                }
                            }
                        })
                    }
                },btn2: function (i, layero) {
                    layer.close(i);
                },
                cancel: function (i, layero) {
                    layer.close(i);
                }
            });

        })
    };



    //表单验证
    var validateForm = function () {
        var validate = $('#form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                coinName:{
                    required:true,
                },
                coinDecimals:{
                    required:true,
                    range:[1,20]
                },
                coinAvatarUrl:{
                    required:true,

                },
                coinAvatarUrl1:{
                    required:true,

                },
                withdrawFeeRate:{
                    required:true,
                    max:100
                },
                transactionFeeRate:{
                    required:true,
                    max:100
                },
                coinDescription:{
                    required:true,
                }
            },
            messages: {
                coinName:{
                    required:"货币名称不能为空",
                },
                coinDecimals:{
                    required:"货币精度不能为空",
                    range:"精度必须介于1到20之间"
                },
                coinAvatarUrl:{
                    required:"货币图标URL不能为空",
                },
                coinAvatarUrl1:{
                    required:"货币图标URL1不能为空",
                },
                withdrawFeeRate:{
                    required:"提现手续费不能为空",
                    max:"输入值不能大于100"
                },
                transactionFeeRate:{
                    required:"交易手续费不能为空",
                    max:"输入值不能大于100"
                },
                coinDescription:{
                    required:"货币描述语不能为空",
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

    //修改表单验证
    var validateForms = function () {
        var validates = $('#forms').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                coinName:{
                    required:true,
                },
                coinDecimals:{
                    required:true,
                    range:[1,20]
                },
                withdrawFeeRate:{
                    required:true,
                    max:100
                },
                transactionFeeRate:{
                    required:true,
                    max:100
                },
                coinDescription:{
                    required:true,
                }
            },
            messages: {
                coinName:{
                    required:"货币名称不能为空",
                },
                coinDecimals:{
                    required:"货币精度不能为空",
                    range:"精度必须介于1到20之间"
                },
                withdrawFeeRate:{
                    required:"提现手续费不能为空",
                    max:"输入值不能大于100"
                },
                transactionFeeRate:{
                    required:"交易手续费不能为空",
                    max:"输入值不能大于100"
                },
                coinDescription:{
                    required:"货币描述语不能为空",
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
        return validates;
    };


    //上方查询按钮
    var query = function () {
        //查询按钮
        $("#query").click(function () {
            var dataTable = $("#dataTables-example").dataTable();
            dataTable.fnReloadAjax();
        });
    };



    return {
        init:function () {
            loadData();
            query();
            add();
        }
    };
}();
