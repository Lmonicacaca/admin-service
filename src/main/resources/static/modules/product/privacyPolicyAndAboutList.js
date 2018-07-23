var privacyPolicyAndAbout = function () {
    var index =0;
    var loadData = function () {
        var aoColumns = [
            {"mData": "id"},
            {"mData": "channel"},
            {"mData": "system"},
            {"mData": "type"},
            {"mData": "content"},
            {"mData": "language"},
            {"mData": "createTime"},
            {"mData": null}
        ];
        var aoColumnDefs = [
            {
                "aTargets": [2],
                "mRender": function (a, b, c, d) {
                    if(a==null||a==""){
                        return "";
                    }else{
                        return a;
                    }

                }
            },{
            "aTargets": [3],
            "mRender": function (a, b, c, d) {
                if(a==0){
                    return "隐私协议";
                }else{
                    return "关于我们";
                }

            }
        },
            {
                "aTargets": [4],
                "mRender": function (a, b, c, d) {
                    if(a==null||a==""){
                        return "";
                    }else{
                        return "<a class=\"edit\" name =\"contentShow\" href=\"javascript:;\">内容</a>";
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
                "aTargets": [7],
                "mRender": function (a, b, c, d) {
                    return "<a class=\"edit\" name =\"edit\" href=\"javascript:;\"> 修改 </a><a class=\"red\" name=\"delete\" href=\"javascript:;\"> 删除 </a>";
                }
            }];
        var t = $("#dataTables-example");
        var csrf = $("#csrfId");
        initPageTable(t, "privacyPolicyAndAbout/queryList?"+csrf.attr("name")+"="+csrf.attr("value"), aoColumns, aoColumnDefs, __queryHandler, __initHandler);
    };
    var __queryHandler =function (condition) {
        var channelSearch = $("#channelSearch").val();
        if (assertNotNullStr(channelSearch)) condition.channelSearch = channelSearch;

    };
    var __initHandler =function () {

        //显示内容
        $("a[name='contentShow']").on("click", function () {
            var table = $('#dataTables-example').DataTable();
            var d = table.row($(this).parents('tr')).data();
            var contentShow="";
            $.ajax({
                url:getRootPath()+"/privacyPolicyAndAbout/queryContent",
                type:"get",
                dataType:"text",
                data:"id="+d.id,
                async:false,
                success:function (data) {
                    contentShow = data;
                }
            })
            $("#contentShow").html(contentShow);
            var index=layer.open({
                area: '800px',
                // shade: [0.8, '#393D49'],
                title: "内容详情",
                type: 1,
                content: $("#contentShow"),
                cancel: function (index, layero) {
                    layer.closeAll();
                },
                success:function(layero){
                    // $(".layui-layer-shade").css("z-index",1);
                }


            });

        });

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
        $("a[name='edit']").on("click", function () {
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
                        $("#language").val(d.language);
                        var typeName = "";
                        if(d.type==0){
                            typeName = "隐私协议"
                        }else{
                            typeName = "关于我们"
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
                                loadChannel();
                                loadType();
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
                            },
                            cancel: function (i, layero) {
                                layer.close(i);
                            }
                        });
                    }
                });
        });

    };
    //添加隐私协议
    var add =function () {
        $("#add").bind("click",function () {
            $("#id").val("");
            KindEditor.remove('#editor');
            $("#language").val("");
            $("#channel").html("");
            $("#type").html("");
               layer.open({
                area: '800px',
                shade: [0.8, '#393D49'],
                title: "添加隐私协议",
                type: 1,
                content: $("#addWin"),
                btn: ['添加', '关闭'],
                   offset: '0px',
                zIndex: 1500,
                success: function (layero, index) {
                    loadKindeditor();
                    validateForm().resetForm();
                    loadChannel();
                    loadType();
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
                    KindEditor.remove('#editor');
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
                url: "privacyPolicyAndAbout/queryChannel",
                cache: true,
                processResults: function (data) {
                    return {
                        results: data
                    };
                }
            }
        });
    };
    var loadType = function () {
        $('#type').select2({
            placeholder: "请选择类型",
            allowClear: true,
            ajax: {
                url: "privacyPolicyAndAbout/queryType",
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
                system: {
                    required: true
                },
                content: {
                    required: true
                },
                language: {
                    required: true
                }
            },
            messages: {
                channel: {
                    required: "渠道号不能为空!"
                },
                type: {
                    required: "类型不能为空!"
                },
                content: {
                    required: "内容不能为空!"
                },
                language: {
                    required: "语言不能为空!"
                },
                system: {
                    required: "系统不能为空!"
                },
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
    var loadKindeditor = function kedit() {
        var keditor = KindEditor.create(
            "#editor",

            {

                width: "100%", //编辑器的宽度为70%

                height: "430px", //编辑器的高度为100px

                pasteType:1,

                filterMode: false, //不会过滤HTML代码

                resizeMode: 1,//编辑器只能调整高度

                uploadJson: getRootPath() + '/kindeditor/fileUpload',

                fileManagerJson: getRootPath() + '/kindeditor/fileUpload',

                allowUpload: true,

                allowFileManager: true,

                allowPreviewEmoticons: true,//显示浏览音频远程故武器按钮
                //设置额外的请求参数
                extraFileUploadParams: {
                    _csrf: $("#csrfId").attr("value")
                },

                afterCreate: function () {

                    var self = this;

                    KindEditor.ctrl(document, 13, function () {

                        self.sync();

                        document.forms['example'].submit();

                    });

                    KindEditor.ctrl(self.edit.doc, 13, function () {

                        self.sync();

                        document.forms['example'].submit();

                    });

                },

                items: [

                    'source', '|', 'undo', 'redo', '|', 'preview', 'code', 'cut', 'copy', 'paste',

                    'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',

                    'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',

                    'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',

                    'formatblock', 'fontname', 'fontsize', '|',  'bold',

                    'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|',

                    'table', 'hr', 'pagebreak',

                    'anchor', 'link', 'unlink', '|', 'image', 'media', 'insertfile', 'editImage'

                ],

                afterBlur: function () {
                    this.sync();
                },//和DWZ 的 Ajax onsubmit 冲突,提交表单时 编辑器失去焦点执行填充内容

                newlineTag: "br"

            });


    };
    return {
        init:function () {
            loadData();
            add();
            query();
            // loadKindeditor();
        }
    };
}();