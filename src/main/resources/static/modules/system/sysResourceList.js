var sysResource = function () {

    var loadData = function () {
        var glyph_opts = {
            map: {
                doc: "glyphicon glyphicon-file",
                docOpen: "glyphicon glyphicon-file",
                checkbox: "glyphicon glyphicon-unchecked",
                checkboxSelected: "glyphicon glyphicon-check",
                checkboxUnknown: "glyphicon glyphicon-share",
                dragHelper: "glyphicon glyphicon-play",
                dropMarker: "glyphicon glyphicon-arrow-right",
                error: "glyphicon glyphicon-warning-sign",
                expanderClosed: "glyphicon glyphicon-menu-right",
                expanderLazy: "glyphicon glyphicon-menu-right",  // glyphicon-plus-sign
                expanderOpen: "glyphicon glyphicon-menu-down",  // glyphicon-collapse-down
                folder: "glyphicon glyphicon-folder-close",
                folderOpen: "glyphicon glyphicon-folder-open",
                loading: "glyphicon glyphicon-refresh glyphicon-spin"
            }
        };
        $("#dataTables-example").fancytree({
            extensions: ["dnd", "edit", "glyph", "table"],
            checkbox: false,
            glyph: glyph_opts,
            source: {url: "resource/queryResources"},
            table: {
                nodeColumnIdx: 0
            },
            dnd: {
                focusOnClick: true
            },
            activate: function(event, data) {
                // console.log(data);
            },
            renderColumns: function(event, data) {
                var d = data.node.data;
                var node = data.node,
                    $tdList = $(node.tr).find(">td");
                $tdList.eq(1).text(d.resourceDesc);
                $tdList.eq(2).text(d.resourcePath);
                $tdList.eq(3).text(d.enable==1?"可用":"不可用");
                $tdList.eq(4).text(d.orderNo);
                $tdList.eq(5).text(d.resourceLevel);
                $tdList.eq(6).text(moment(d.createTime).format('YYYY-MM-DD HH:mm:ss'));
            }
        });
    };

    var add = function () {
        //添加
        $("#add").bind("click", function () {
            var validate = validateForm();
            $("#resource_parentDiv").hide();
            var node = $("#dataTables-example").fancytree("getActiveNode");
            var resource_parent = "";
            if(node==null){
                resource_parent = "0";
            }else{
                resource_parent = node.data.id;
            }
            $("#resource_parent").val(resource_parent);
            layer.open({
                area: '800px',
                shade: [0.8, '#393D49'],
                title: "新增资源",
                type: 1,
                content: $("#addWin"),
                btn: ['添加', '关闭'],
                success: function (layero, index) {
                    $("#form")[0].reset();
                    $("#resourcesId").val("");
                    validate.resetForm();
                },
                yes: function (layero, index) {
                    if ($("#form").valid()) {
                        $("#form").ajaxSubmit({
                            success: function (d) {
                                layer.msg(d.message);
                                if (d.code == 200) {
                                    $.ui.fancytree.getTree("#dataTables-example").reload();
                                    layer.closeAll();
                                } else {
                                }
                            }
                        })
                    }
                },
                cancel: function (index, layero) {
                    layer.close(index);
                }
            });
        });

    };


    var update = function () {
        $("#update").click(function () {
            var node = $("#dataTables-example").fancytree("getActiveNode");
            validateForm().resetForm();
            var csrf = $("#csrfId");
            $.post("resource/queryResourcesById?"+csrf.attr("name")+"="+csrf.attr("value"), {id: node.data.id}).then(function (data) {
                if (data.code == 200) {
                    var d = data.data;
                    layer.open({
                        area: '800px',
                        shade: [0.8, '#393D49'],
                        title: "修改",
                        type: 1,
                        content: $("#addWin"),
                        btn: ['确定', '关闭'],
                        success: function (layero, index) {
                            publishFormData($("#addWin"), d);
                            var option = "";
                            if(d.parentName==null){
                                option = "<option value='0' selected='selected'>根节点</option>";
                            }else{
                                option = "<option value='" + d.resourceParent + "' selected='selected'>" + d.parentName + "</option>";
                            }
                            var p = $("#resource_parentSelect");
                            p.empty();
                            p.append(option);
                        },
                        yes: function (layero, index) {
                            if ($('#form').valid()) {
                                $("#form").ajaxSubmit({
                                    success: function (d) {
                                        layer.msg(d.message);
                                        if (d.code == 200) {
                                            layer.closeAll();
                                            $.ui.fancytree.getTree("#dataTables-example").reload();
                                        }
                                    }
                                })
                            }
                        },
                        cancel: function (index, layero) {
                            layer.close(index);
                        }
                    });
                } else {
                    layer.alert("获取数据失败");
                }

            });
        });
    };

    var validateForm = function () {
        var validate = $('#form').validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            rules: {
                resourceName: {
                    required: true
                },
                resourceDesc: {
                    required: true
                },
                resourcePath: {
                    required: true
                },
                resourceLevel: {
                    required: true,
                    number:true
                },
                orderNo: {
                    number:true
                }
            },
            messages: {
                resourceName: {
                    required: "资源名称不能为空"
                },
                resourceDesc: {
                    required: "资源描述不能为空"
                },
                resourcePath: {
                    required: "资源路径不能为空"
                },
                resourceLevel: {
                    required: "资源等级不能为空",
                    number:"请输入合法的数字"
                },
                orderNo: {
                    number:"请输入合法的数字"
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


    var  publishFormData = function(/**Node*/parentNode, /**Object*/data) {
        if (!parentNode || !data) return;
        for (var pro in data) {
            var childNode = $(parentNode).find("[name=" + pro + "]");
            if (!childNode) childNode = $(parentNode).find("#" + pro);
            if ($(childNode).attr("type") === 'radio') {
                /*dojo.forEach(childNode, function (n, i) {
                    if ($(n).val() == data[pro]) {
                        $(n).attr("checked", "checked");
                    }
                });*/
            } else if($(childNode).attr("type") === 'checkbox'){
                $(childNode).attr("checked",true);
            }else {
                $(childNode).val(data[pro]);
            }
        }
    };
    var deleteRow= function () {

        $("#delete").click(function () {
            var node = $("#dataTables-example").fancytree("getActiveNode");
            if(node==null){
                layer.msg("请选择一条数据");
            }else{
                layer.confirm("你确定要删除该数据吗？", function (index) {
                    var csrf = $("#csrfId");
                    $.post("resource/delete?"+csrf.attr("name")+"="+csrf.attr("value"), {id: node.data.id}).then(function (data) {
                        console.log(data);
                        layer.msg(data.message);
                        if (data.code == 200) {
                            $.ui.fancytree.getTree("#dataTables-example").reload();
                        }
                    });
                });
            }
        })
    };

    return {
        init:function () {
            loadData();
            add();
            update();
            deleteRow();
        }
    };
}();