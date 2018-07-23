var addPolicy = function () {
    var loadData = function () {
        loadChannel();
        submitForm();
    };

    var submitForm = function () {
        validateForm().resetForm();
        $("#addPolicy").bind("click",function () {
            if ($("#form").valid()) {
                $("#form").ajaxSubmit({
                    success: function (d) {
                        if (d.code == 200) {
                            alert("添加成功");
                        } else {
                            alert(d.message)
                        }
                    }
                });

            }else{
                return false;
            }

        })
    }

    var loadKindeditor = function kedit() {
        var keditor = KindEditor.create(
            "#editor",

            {

                width: "100%", //编辑器的宽度为70%

                height: "430px", //编辑器的高度为100px

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

                    'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'code', 'cut', 'copy', 'paste',

                    'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',

                    'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',

                    'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',

                    'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',

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

    var loadChannel = function () {
        $('#channel').select2({
            placeholder: "请选择渠道",
            allowClear: true,
            ajax: {
                url: "addPolicy/queryChannel",
                cache: true,
                processResults: function (data) {
                    return {
                        results: data
                    };
                }
            }
        });
    };
    var loadRole = function () {
        $('#roleId').select2({
            placeholder: "请选择角色",
            allowClear: true,
            ajax: {
                url: "sysUser/querySysRole",
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
                channel: {
                    required: "渠道号不能为空!"
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
            loadKindeditor();

        }
    };
}();