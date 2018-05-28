var banner = function () {
    var index =0;
    var loadData = function () {
        var aoColumns = [
            {"mData": "id"},
            {"mData": "channel"},
            {"mData": "url"},
            {"mData": "image"},
            {"mData": "type"},
            {"mData": "orderBy"},
            {"mData": "createTime"},
            {"mData": null}
        ];
        var aoColumnDefs = [{
            "aTargets": [3],
            "mRender": function (a, b, c, d) {
                return "<a class=\"edit\" name =\"img\" href=\"javascript:;\">"+a+"</a>";
            }
        },{
            "aTargets": [4],
            "mRender": function (a, b, c, d) {
                if(a==1){
                    return "余额";
                }else if(a==2){
                    return "商家";
                }else{
                    return "支付";
                }

            }
        },{
            "aTargets": [6],
            "mRender": function (a, b, c, d) {
                var date = new Date(a);
                return date.getFullYear()+"-"+(date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-'+date.getDate() + ' '+date.getHours() + ':'+date.getMinutes() + ':'+date.getSeconds();
            }
        },
            {
                //操作按钮
                "aTargets": [7],
                "mRender": function (a, b, c, d) {
                    return "<a class=\"btn btn-success\" name =\"edit\" href=\"javascript:;\"> 修改 </a> " +
                        "<a class=\"btn btn-danger\" name=\"delete\" href=\"javascript:;\"> 删除 </a>";
                }
            }];
        var t = $("#dataTables-example");
        var csrf = $("#csrfId");

        initPageTable(t, "banner/queryList?"+csrf.attr("name")+"="+csrf.attr("value"), aoColumns, aoColumnDefs, __queryHandler, __initHandler);
    };
    //查询条件
    var __queryHandler =function (condition) {

        var url = $("#urlQuery").val();
        if (assertNotNullStr(url)) condition.url = url;
    };

    var __initHandler =function () {

        //查看图片
        $("#dataTables-example tbody").on("click", "a[name='img']", function () {
            var urlImg = "";
            $.ajax({
                url:getRootPath()+"/banner/queryUrl",
                type:"get",
                async : false,
                success:function(data){
                    urlImg = data;
                }
            })
            var img = "<img src='"+urlImg+$(this).html()+"' />";
            layer.open({
                type:1,
                shade:false,
                title:false,
                area:['800px','600px'],
                content:img,
                cancel:function(){

                }
            })
        });

        //删除
        $("#dataTables-example tbody").on("click", "a[name='delete']", function () {
            var table = $('#dataTables-example').DataTable();
            var d = table.row($(this).parents('tr')).data();
            var param = {"id": d.id};
            var csrf = $("#csrfId");
            var csrfName = csrf.attr('name');
            layer.confirm("你确定要删除该数据吗？", function (index) {
                $.post("banner/deleteBanner?" + csrfName + "=" + csrf.attr("value"), param).then(function (data) {
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
                $.post("banner/queryById?" + csrfName + "=" + csrf.attr("value"), param, function (data) {
                    if (data.code == 200) {
                        var d = data.data;
                        $("#url").val(d.url);
                        $("#id").val(d.id);
                        $("#orderBy").val(d.orderBy);
                        // $("#simage").val(d.image);
                       /* var date = new Date(d.createTime)
                        $("#createTime").val(date);*/
                        var typeName="";
                        switch(d.type){
                            case 1:typeName = "余额";break;
                            case 2:typeName = "商家";break;
                            case 3:typeName = "支付";break;
                        }
                        var optionType = "<option value='" + d.type + "' selected='selected'>" + typeName + "</option>";
                        $("#type").empty();
                        $("#type").append(optionType);

                        var optionChannel = "<option value='" + d.channel + "' selected='selected'>" + d.channel + "</option>";
                        $("#channel").empty();
                        $("#channel").append(optionChannel);
                        layer.open({
                            area: '800px',
                            shade: [0.8, '#393D49'],
                            title: "修改",
                            type: 1,
                            content: $("#addWin"),
                            btn: ['确定'],
                            success: function (layero, index) {
                                loadType();
                                loadChannel();
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

    //添加广告
    var add =function () {
        $("#add").bind("click",function () {
            $("#id").val("");
            $("#orderBy").val("");

            layer.open({
                area: '800px',
                shade: [0.8, '#393D49'],
                title: "添加广告",
                type: 1,
                content: $("#addWin"),
                btn: ['添加','关闭'],
                success: function (layero, index) {
                    $("#form")[0].reset();
                    validateForm().resetForm();
                    loadType();
                    loadChannel();
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
                                    layer.msg("添加失败");
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
            placeholder: "请选择类型",
            allowClear: true,
            ajax: {
                url: "banner/queryBannerType",
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
                url: "banner/queryChannel",
                cache: true,
                processResults: function (data) {
                    return {
                        results: data
                    };
                }
            }
        });
    };
    //添加数据时验证
    var validateForm = function () {
        var validate = $('#form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                url: {
                    required:true,
                    url:true
                },
                image: {
                     required:true,
                    accept:"JPEG|PNG|GIF"
                },
                type: {
                    required: true
                },
                channel:{
                    required: true
                }
            },
            messages: {
                url: {
                    required:"url不能为空",
                    url: "url格式有误"
                },
                image: {
                    required:"图片不能为空",
                    accept:"图片格式有误"
                },
                type: {
                    required: "类型不能为空!"
                },
                channel:{
                    required: "渠道号不能为空!"
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