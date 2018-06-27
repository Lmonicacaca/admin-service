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
            {"mData": "coinAvatarUrl"},
            {"mData": "chainType"},
            {"mData": "tokenAddress"},
            {"mData": "coinDecimals"},
            {"mData": "gasLimit"},
            {"mData": null}

        ];
        //数据的格式化处理: aTargets=表示你要处理的行数,从0开始
        var aoColumnDefs = [
            {
                //操作按钮

                "aTargets": [3],
                "mRender": function (a, b, c, d) {
                    if(a==null||a==""){
                        return "";
                    }else{
                        return "<img src='"+a+"' style='max-height: 50px'/>";
                    }
                }
            },{
                //操作按钮

                "aTargets": [7],
                "mRender": function (a, b, c, d) {
                   if(a==null||a==""){
                       return "";
                   }else{
                       return a;
                   }
                }
            }, {
            //操作按钮

            "aTargets": [8],
            "mRender": function (a, b, c, d) {
                return "<a class=\"edit\" name =\"edit\" href=\"javascript:;\"> 修改 </a> " +
                        "<a class=\"delete\" name=\"delete\" href=\"javascript:;\"> 删除 </a>";
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
        
        //修改
        $("#dataTables-example tbody").on("click", "a[name='edit']", function () {
            if (index == 0) {
                index++;
                var v = validateForm();
                var table = $('#dataTables-example').DataTable();
                var d = table.row($(this).parents('tr')).data();
                layer.open({
                    area: '800px',
                    shade: [0.8, '#393D49'],
                    title: "修改",
                    type: 1,
                    content: $("#win"),
                    btn: ['确定', '关闭'],
                    success: function (layero, index) {
                        $("#form")[0].reset();
                        v.resetForm();
                        $("#coinId").val(d.id);
                        $("#coinName").val(d.coinName);
                        $("#coinType").val(d.coinType);
                        $("#coinErc20").val(d.coinErc20);
                        $("#coinDescription").val(d.coinDescription);
                        $("#chainType").val(d.chainType);
                        $("#tokenAddress").val(d.tokenAddress);
                        $("#coinDecimals").val(d.coinDecimals);
                        $("#gasLimit").val(d.gasLimit);
                        $("#oldLogo").val(d.coinAvatarUrl);

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
        });

    };
    //添加
    var add =function () {
            $("#add").bind("click", function () {
                var v = validateForm();
                $("#coinId").val("");
                layer.open({
                    area:  '800px',
                    shade: [0.8, '#393D49'],
                    title: "添加币种",
                    type: 1,
                    content: $("#win"),
                    btn: ['添加', '关闭'],
                    success: function (layero, index) {
                        $("#form")[0].reset();
                        v.resetForm();
                    },
                    yes: function (layero, i) {
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

                    }, btn2: function (i, layero) {
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
                    required:true
                },
                coinDescription:{
                    required:true
                },
                coinDecimals:{
                    required:true

                },
                gasLimit:{
                    required:true
                }
            },
            messages: {
                coinName:{
                    required:"币名称不能为空"
                },
                coinDescription:{
                    required:"币全称不能为空"
                },
                coinDecimals:{
                    required:"币精度不能为空"
                },
                gasLimit:{
                    required:"货币图标URL1不能为空"
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
