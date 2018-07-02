var merchantInfo = function () {
    var index =0;
    var loadData = function () {
        var aoColumns = [
            {"mData": "id"},
            {"mData": "id"},
            {"mData": "name"},
            {"mData": "logoBill"},
            {"mData": "website"},
            {"mData": "rsaPublic"},
            {"mData": "rsaPrivate"},
            {"mData": "isShow"},
            {"mData": "status"},
            {"mData": "createTime"},
            {"mData": "channel"},
            {"mData": null}
        ];
        var aoColumnDefs = [
            {
                "aTargets": [10],
                "mRender": function (a, b, c, d) {
                    if(a==null){
                        return "";
                    }else{
                        return a;
                    }
                }
            },
            {
                "aTargets": [3],
                "mRender": function (a, b, c, d) {
                    if(a==null){
                        return "";
                    }else{
                        return "<img src='"+a+"' style='max-height: 50px'/>";
                    }
                }
            },
            {
                "aTargets": [4],
                "mRender": function (a, b, c, d) {
                    if(a==null){
                        return "";
                    }else{
                        return "<a href='"+a+"' target='_blank'>网站</a>";
                    }
                }
            },
            {
                "aTargets": [5],
                "mRender": function (a, b, c, d) {
                    if(a!=null&&a!=""){
                        return "<a class=\"edit\" name =\"rsaPublic\" href=\"javascript:;\">公钥</a>";
                    }else{
                        return "";
                    }

                }
            },
            {
                "aTargets": [6],
                "mRender": function (a, b, c, d) {
                    if(a!=null&&a!=""){
                        return "<a class=\"edit\" name =\"rsaPrivate\" href=\"javascript:;\">私钥</a>";
                    }else{
                        return ""
                    }

                }
            },
            {
                "aTargets": [7],
                "mRender": function (a, b, c, d) {
                    if(a==0){
                        return "是";
                    }else{
                        return "否"
                    }

                }
            },
            {
                "aTargets": [8],
                "mRender": function (a, b, c, d) {
                    if(a==0){
                        return "未审核"
                    }else if(a==1){
                        return "审核通过"
                    }else{
                        return "审核不通过"
                    }
                }
            },
            {
                "aTargets": [9],
                "mRender": function (a, b, c, d) {
                    var date = new Date(a);
                    return date.getFullYear()+"-"+(date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-'+date.getDate() + ' '+date.getHours() + ':'+date.getMinutes() + ':'+date.getSeconds();
                }
            },
            {
            "aTargets": [11],
            "mRender": function (a, b, c, d) {
                return "<a class=\"edit\" name =\"edit\" href=\"javascript:;\"> 修改 </a><a class=\"red\" name=\"delete\" href=\"javascript:;\"> 删除 </a>";
            }
        }];
        var t = $("#dataTables-example");
        var csrf = $("#csrfId");
        initPageTable(t, "merchantInfo/queryList?"+csrf.attr("name")+"="+csrf.attr("value"), aoColumns, aoColumnDefs, __queryHandler, __initHandler);
    };
    var __queryHandler =function (condition) {
        var nameSearch = $("#nameSearch").val();
        if (assertNotNullStr(nameSearch)) condition.nameSearch = nameSearch;
        var idSearch = $("#idSearch").val();
        if (assertNotNullStr(idSearch)) condition.idSearch = idSearch;
    };
    var __initHandler =function () {

        $("#dataTables-example tbody").on("click", "a[name='rsaPublic']", function () {
            $("#showRsa").val("");
            var table = $('#dataTables-example').DataTable();
            var d = table.row($(this).parents('tr')).data();
            var rsa = "";
            $.ajax({
                url:getRootPath()+"/merchantInfo/queryRsaPublic",
                type:"get",
                async:false,
                data:"id="+d.id,
                success:function(data){
                    rsa = data;
                }
            })

            $("#rsa").val(rsa);
            layer.open({
                area: '800px',
                shade: [0.8, '#393D49'],
                title: "公钥",
                type: 1,
                content: $("#showRsa")
            })
        })


        $("#dataTables-example tbody").on("click", "a[name='rsaPrivate']", function () {
            $("#showRsa").val("");
            var table = $('#dataTables-example').DataTable();
            var d = table.row($(this).parents('tr')).data();
            var rsa = "";
            $.ajax({
                url:getRootPath()+"/merchantInfo/queryRsaPrivate",
                type:"get",
                async:false,
                data:"id="+d.id,
                success:function(data){
                    rsa = data;
                }
            })

            $("#rsa").val(rsa);
            layer.open({
                area: '800px',
                shade: [0.8, '#393D49'],
                title: "私钥",
                type: 1,
                content: $("#showRsa")
            })
        })



        //删除
        $("#dataTables-example tbody").on("click", "a[name='delete']", function () {
            var table = $('#dataTables-example').DataTable();
            var d = table.row($(this).parents('tr')).data();
            var param = {"id": d.id};
            var csrf = $("#csrfId");
            var csrfName = csrf.attr('name');
            layer.confirm("你确定要删除该数据吗？", function (index) {
                $.post("merchantInfo/deleteMerchant?" + csrfName + "=" + csrf.attr("value"), param).then(function (data) {
                    if (data.code == 200) {
                        layer.msg("删除成功");
                        var dataTable = $("#dataTables-example").dataTable();
                        dataTable.fnReloadAjax();
                    }
                });
            });
        });
    };

    // 编辑
    $("#dataTables-example tbody").on("click", "a[name='edit']", function () {
        if(index ==0) {
            index ++;
            var table = $('#dataTables-example').DataTable();
            var d = table.row($(this).parents('tr')).data();
            var csrf = $("#csrfId");
            var csrfName = csrf.attr('name');
            var param = {"id": d.id};
            $.post("merchantInfo/queryById?" + csrfName + "=" + csrf.attr("value"), param, function (data) {
                if (data.code == 200) {
                    var d = data.data;
                    $("#id").val(d.id);
                    $("#oldImage").val(d.logoBill);
                    $("#file").val("");
                    $("#name").val(d.name);
                    $("#description").val(d.name);
                    $("#address").val(d.address);
                    $("#website").val(d.website);
                    $("#rsaPublic").val(d.rsaPublic);
                    $("#rsaPrivate").val(d.rsaPrivate);
                    $("#createUserName").val(d.createUserName);
                    var time = d.createTime;
                    $("#createTime").val(new Date(time));
                    var optionChannel = "<option value='" + d.channel + "' selected='selected'>" + d.channel + "</option>";
                    $("#channel").empty();
                    $("#channel").append(optionChannel);
                    var isShow = "";
                    if(d.isShow==0){
                        isShow = "是"
                    }else{
                        isShow = "否"
                    }
                    var optionIsShow = "<option value='" + d.isShow + "' selected='selected'>" + isShow + "</option>";
                    $("#isShow").empty();
                    $("#isShow").append(optionIsShow);

                    var status = "";
                    if(status==0){
                        status = "未审核"
                    }else if(status==1){
                        status = "审核通过"
                    }else{
                        status = "审核不通过"
                    }
                    var optionStatus = "<option value='" + d.status + "' selected='selected'>" + status + "</option>";
                    $("#status").empty();
                    $("#status").append(optionStatus);
                    layer.open({
                        area: '800px',
                        shade: [0.8, '#393D49'],
                        title: "修改",
                        type: 1,
                        content: $("#addWin"),
                        btn: ['确定'],
                        success: function (layero, index) {
                            loadChannel();
                            loadisShow();
                            loadStatus();
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
        }
    });
    //添加商户
    var add =function () {
        $("#add").bind("click",function () {
            $("#id").val("");
            $("#name").val("");
            $("#oldImage").val("");
            $("#channel").html("");
            $("#website").val("");
            $("#rsaPublic").val("");
            $("#rsaPrivate").val("");
            $("#isShow").html("");
            $("#status").val("");
            $("#createUserName").val("");
            $("#createTime").val(new Date());
            $("#file").val("");
            layer.open({
                area: '800px',
                shade: [0.8, '#393D49'],
                title: "添加商户",
                type: 1,
                content: $("#addWin"),
                btn: ['添加', '关闭'],
                success: function (layero, index) {
                    $("#form")[0].reset();
                    validateForm().resetForm();
                    loadChannel();
                    loadisShow();
                    loadStatus();
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
    var loadChannel = function () {
        $('#channel').select2({
            placeholder: "请选择渠道号",
            allowClear: true,
            ajax: {
                url: "merchantInfo/queryChannel",
                cache: true,
                processResults: function (data) {
                    return {
                        results: data
                    };
                }
            }
        });
    };
    var loadisShow = function () {
        $('#isShow').select2({
            placeholder: "请选择是否显示在APP",
            allowClear: true,
            ajax: {
                url: "merchantInfo/queryIsShow",
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
                url: "merchantInfo/queryStatus",
                cache: true,
                processResults: function (data) {
                    return {
                        results: data
                    };
                }
            }
        });
    };
    var query = function () {
        //查询按钮
        $("#query").click(function () {
            var dataTable = $("#dataTables-example").dataTable();
            dataTable.fnReloadAjax();
        });
    }
    var validateForm = function () {
        var validate = $('#form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                name: {
                    required: true
                },
                channel: {
                    required: true
                },
                rsaPublic: {
                    required: true
                }
            },
            messages: {
                name: {
                    required: "商户名不能为空!"
                },
                channel: {
                    required: "渠道号不能为空!"
                },
                rsaPublic: {
                    required: "商户公钥不能为空!"
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