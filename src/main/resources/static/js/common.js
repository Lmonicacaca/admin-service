/**
 * 公有js
 */
(function () {
    window.otcadmin = {
        fieldSelector: '[reg],[url]:not([reg]),[notnull]',
        delayTimeInMillisecond: 5000,
        //动态DIV窗口,支持窗口开窗口
        _dynamicNestDiv: [],
        //文件上传对应的属性
        _uploadfileoptions: {},

        //ajax遮罩
        _layerMask: null,
        _menuDivMask: null,

        menuMask: function () {
            if (!window.otcadmin._menuDivMask) {
                window.otcadmin._menuDivMask = $("<div>").loadmask({
                    targetEl: $(".x-region-west")
                });
            }
            window.otcadmin._menuDivMask.loadmask("open");
        },

        menuUnmask: function () {
            window.otcadmin._menuDivMask.loadmask("close");
        },

        mask: function () {
            otcadmin.unmask();
            window.otcadmin._layerMask = layer.load(1, {
                shade: [0.2,'#fff'] //0.1透明度的白色背景
            });
        },
        unmask: function () {
            if(window.otcadmin._layerMask != null){
                layer.close(window.otcadmin._layerMask)
            }
        },
        /*
         * 首页加载完毕后的取消遮罩
         */
        layoutRender: function () {
            setTimeout(function () {
                $('#main_load_mask').hide(1500);
                $("#main_load_mask").remove();
            }, 500);
        },
        /*
         * 根据选择符返回 jQuery 对象
         * @param selector
         */
        getTargetObj: function (selector) {
            if (typeof selector == 'string') {
                var targetObj = $((selector.indexOf('#') === 0 ? '' : "#") + selector);
                if (targetObj === undefined || targetObj.length === 0) {
                    targetObj = $(selector);
                }
                return targetObj;
            } else {
                return $(selector);
            }
        },
        /*
         *对buttons进行操作，
         * @param buttons  buttons可以接收数组/或字符串,
         * @param status 值："enable"/"disable"
         */
        button: function (buttons, status) {
            if ($.isArray(buttons)) {
                for (var index = 0; index < buttons.length; index++) {
                    otcadmin.getTargetObj(buttons[index]).button(status);
                }
            } else if ($.type(buttons) === "string") {
                otcadmin.getTargetObj(buttons).button(status);
            }
        },
        /*
         * 获取对象宽度
         *
         * @param Object
         */
        getWidth: function (object) {
            var original = otcadmin.getTargetObj(object);
            var docWidth = original.outerWidth();
            if (!$.browser.msie || !docWidth) {
                docWidth = original.width();
            }
            return docWidth;
        },

        /*
         * 获取对象高度
         * @param Object
         */
        getHeight: function (object) {
            var original = otcadmin.getTargetObj(object);
            var docHeight = original.outerHeight();
            if (!$.browser.msie || !outerHeight) {
                docHeight = original.height();
            }
            return docHeight;
        },

        /*
         * 加载HTML到指定DIV中
         * @param targetDiv
         * @param url
         * @param params
         * @param callback
         * @param resetTitleFlag
         */
        loadHtml: function (targetDiv, url, params, callback, resetTitleFlag) {

            otcadmin.mask();
            if (!params) {
                params = {};
            }
            params["_jtype"] = "html";
            otcadmin.getTargetObj(targetDiv).load(url, params, function (data, status) {
                if (resetTitleFlag == undefined || resetTitleFlag + '' != 'false') {
                    // 如果是需要重置标题
                    //otcadmin.resetTitle(targetDiv);
                }
                if (callback) {
                    callback(data, status);
                }
                otcadmin.unmask();
            });

        },
        getTargetHtml : function(url, params, callback){
            otcadmin.loadHtml("mainContent", url, params, callback);
        },
        /*
         * 加载HTML到指定DIV中
         * @param targetDiv
         * @param url
         * @param params
         * @param callback
         * @param resetTitleFlag
         */
        loadHtml: function (targetDiv, url, params, callback, resetTitleFlag) {

            otcadmin.mask();
            if (!params) {
                params = {};
            }
            params["_jtype"] = "html";
            otcadmin.getTargetObj(targetDiv).load(url, params, function (data, status) {
                if (resetTitleFlag == undefined || resetTitleFlag + '' != 'false') {
                    // 如果是需要重置标题
                    // otcadmin.resetTitle(targetDiv);
                }
                if (callback) {
                    callback(data, status);
                }
                otcadmin.unmask();
            });

        },

        /*
         * 加载HTML到指定 mainContent 中
         * @param url
         * @param params
         * @param callback
         * @param resetTitleFlag
         */
        loadMainHtml: function (url, params, callback, resetTitleFlag, oneClick) {
            var csrf = $("#csrfId");
            params[csrf.attr("name")] = csrf.attr("value");

            window.otcadmin.loadHtml("mainContent", url, params, callback, resetTitleFlag, oneClick);
        },

        /*
         * 显示指定对象
         * @param selector
         * @return
         */
        show: function (selector) {
            var ret = otcadmin.getTargetObj(selector).show();
            return ret;
        },

        /*
         * 隐藏指定对象
         * @param selector
         * @return
         */
        hide: function (selector) {
            return otcadmin.getTargetObj(selector).hide();
        },

        /*
         * 获取表单数据
         * @param form
         * @return
         */
        getFormData: function (form) {
            var formData = {};
            $.each(otcadmin.getTargetObj(form).serializeArray(), function () {
                if (this.name.substring(0, 1) == '_') {
                    formData[this.name.replace('_', '')] = this.value;
                } else {
                    var name = this.name;
                    if (formData[name]) {
                        if ($.isArray(formData[name])) {
                            if ($.isArray(formData[name])) {
                                formData[name].push(this.value);
                            } else {
                                var list = [];
                                list.push(formData[name]);
                                list.push(this.value);
                                formData[name] = list;
                            }
                        } else {
                            var list = [];
                            list.push(formData[name]);
                            list.push(this.value);
                            formData[name] = list;
                        }
                    } else {
                        formData[name] = this.value;
                    }
                }
            });

            return formData;
        },

        /*
         * 设置表单数据
         * @param form
         * @param data
         * @param keep
         * @return
         */
        setFormData: function (form, data, keep) {
            var str = [];
            form = otcadmin.getTargetObj(form);
            if (!keep) {
                form.resetForm();
            }
            form.find(':input').each(function () {
                if (data[this.name] !== undefined) {
                    var obj = $(this);
                    switch (this.type) {
                        case 'radio':
                        case 'checkbox':
                            if (obj.val() + '' == data[this.name] + '') {
                                obj.attr('checked', true);
                            }
                            break;
                        default:
                            obj.val(data[this.name]);
                            break;
                    }
                }
            });
            otcadmin.isValid(form);
        },

        /*
         * 由输入元素id匹配，设置表单中输入元素的值
         * @param form 表单id
         * @param data 数据值
         * @param keep 是否重置输入元素
         * @return
         */
        setFormDataById: function (form, data, keep) {
            form = otcadmin.getTargetObj(form);
            if (!keep) {
                form.resetForm();
            }
            form.find(':input').each(function () {
                if (data[this.id] !== undefined) {
                    var obj = $(this);
                    switch (this.type) {
                        case 'radio':
                        case 'checkbox':
                            if (obj.val() + '' == data[this.id] + '') {
                                obj.attr('checked', true);
                            }
                            break;
                        default:
                            obj.val(data[this.id]);
                            break;
                    }
                }
            });
            otcadmin.isValid(form);
        },

        /*
         * 提交表单数据
         * @param form
         * @param url
         * @param callback
         * @param extParam
         * @return
         */
        formSubmit: function (form, url, callback, extParam) {
            if (!otcadmin.isValid(form)) {
                otcadmin.unmask();
                otcadmin.alert("填写内容校验未通过");
                return false;
            }
            otcadmin.mask();
            var postData = window.otcadmin.getFormData(form);
            if (extParam !== undefined) {
                /**
                 * 注:在提交表单附加参数时，若要提交函数为参数值，则只允许通过plainObject的方式提交
                 * 而不允许通过数组方式提交,避免因为绘制流程处对数据对象的扩展而造成的错误
                 * noted by YuanLiang
                 */
                if (!$.isPlainObject(extParam)) {
                    var cleanParam = {};
                    //如果不是用{}或者new object创建的对象
                    for (var property in extParam) {
                        var value = extParam[property];
                        if (!$.isFunction(value)) {
                            //函数类型的值不添加
                            cleanParam[property] = value;
                        }
                    }
                    $.extend(postData, cleanParam);
                } else {
                    $.extend(postData, extParam);
                }
            }
            window.otcadmin.postWithJsonResult(url, postData, function (data, status) {
                otcadmin.unmask();
                if (callback) {
                    callback(data, status);
                }
            });

        },

        /*
         * 重置当前位置
         * @param targetDiv
         */
        resetTitle: function (targetDiv) {
            var tagetObj = otcadmin.getTargetObj(targetDiv);
            var title = $("#uie-location-title", tagetObj).text();
            $('#centerContent').panel("option", "title", this.trimSpace(title));
        },

        /*
         * 去掉左右的空格
         */
        trimSpace: function (vals) {
            try {
                vals = vals.replace(/^\s*/, "");
                vals = vals.replace(/\s*$/, "");
            } catch (e) {

            }
            return vals;
        },

        /*
         * 绑定指定事件到指定对象上
         * @param targetDiv
         * @param targetTopic
         * @param callback
         */
        subscribe: function (targetDiv, targetTopic, callback) {
            otcadmin.getTargetObj(targetDiv).subscribe(targetTopic, callback);
        },

        /*
         * POST提交JSON格式数据
         * @param url
         * @param param
         * @param callback
         */
        postWithJsonResult: function (url, param, callback) {
            otcadmin.mask();
            console.log(param);
            $.post(url, param, function (data, status) {
                otcadmin.unmask();
                if (callback) {
                    callback(data, status);
                }
            }, 'json');
        },

        /*
         * 显示提示对话框
         * @param msg
         * @param title
         * @param callback
         */
        alert: function (msg, title, callback) {
            var _config = $.extend({
                modal: true,
                closeRemove: false,
                closed: false,
                resizable: false,

                icon: "x-icon-info-sign",
                title: title || "系统提示",
                content: msg,
                handle: callback
            }, {});

            $.alert(_config);
        },

        /*
         * 显示信息框
         * @param msg 显示内容
         * @param title 标题
         */
        msg: function (msg, title) {
            $.msg(msg);
        },

        /*
         * 显示操作确认对话框
         * @param msg
         * @param callback
         */
        confirm: function (msg, callback, title) {
            var _config = $.extend({
                modal: true,
                closeRemove: false,
                closed: false,
                resizable: false,

                icon: "x-icon-info-sign",
                title: title || "系统提示",
                content: msg,
                handle: callback
            }, {});
            $.alert.confirm(_config);
        },

        /*
         * 显示mainDiv
         */
        showMainDiv: function () {
            //window.otcadmin.resetTitle("mainDiv");
            otcadmin.hide("#operationDiv");
            otcadmin.show("#mainDiv");
        },

        /*
         * 显示operationDiv
         */
        showOperationDiv: function () {
            // window.otcadmin.resetTitle("operationDiv");
            otcadmin.hide("#mainDiv");
            otcadmin.show("#operationDiv");
        },


        /*
         * 打开窗口
         * @param title
         * @param width
         * @param height
         * @param loadUrl
         * @param callback
         */
        openWin: function (title, width, height, loadUrl, callback, buttons, params, destroyHandler) {
            otcadmin.mask();
            var div = $('<div></div>');
            div.appendTo("#centerContent");
            params = params ? params : {};
            window.otcadmin._dynamicNestDiv.push(div);//将其放入数组
            div.load(loadUrl, params, function (data, status) {
                div.dialog({
                    resizable: true,
                    modal: true,
                    //默认打开
                    closed: false,
                    height: height,
                    width: width,
//    				zIndex:100010,
                    bgiframe: true,
                    title: title || '',
                    tools: 'close',
                    position: ['center', 'middle'],
                    bbar: buttons,
                    onBeforeClose: function () {
                        if ($("#uploadify").length > 0) {
                            $("#uploadify").uploadify("destroy");
                        }
                    },
                    onClose: function () {
                        otcadmin.closeWin(destroyHandler);
                    }
                });
                otcadmin.unmask();
                //判断callback是否function
                if (callback && $.isFunction(callback)) {
                    callback(div);
                }
            });
        },
        /*
         * 打开窗口
         * @param title
         * @param width
         * @param height
         * @param loadUrl
         * @param callback
         */
        openLoadWin: function (id, title, width, height, loadUrl, callback, buttons, params) {
            otcadmin.mask();
            var div = $('#' + id);
            params = params ? params : {};
            div.load(loadUrl, params, function (data, status) {
                div.dialog({
                    resizable: true,
                    modal: true,
                    //默认打开
                    closed: false,
                    height: height,
                    width: width,
//    				zIndex:100010,
                    bgiframe: true,
                    title: title || '',
                    tools: 'close',
                    position: ['center', 'middle'],
                    bbar: buttons
                });
                otcadmin.unmask();
                //判断callback是否function
                if (callback && $.isFunction(callback)) {
                    callback(div);
                }
            });
        },
        loadDivWin: function (id, title, loadUrl, callback, params) {
            otcadmin.mask();
            var div = $('#' + id);
            params = params ? params : {};
            div.load(loadUrl, params, function (data, status) {
                //判断callback是否function
                if (callback && $.isFunction(callback)) {
                    callback(div);
                }
            });
        },
        closeWin: function (destroyHandler) {
            var div = window.otcadmin._dynamicNestDiv[window.otcadmin._dynamicNestDiv.length - 1];
            if (destroyHandler && $.isFunction(destroyHandler)) {
                destroyHandler(div);
            }
            div.dialog("destroy").remove();
            window.otcadmin._dynamicNestDiv.removeObj(div);
        },

        /*
         * 打开窗口
         * @param id
         * @param width
         * @param height
         * @param button
         */
        openDivWin: function (id, title, width, height, buttons, callback) {
            otcadmin.mask();
            var div = $('#' + id);
            div.dialog({
                resizable: true,
                modal: true,
                height: height,
                //默认打开
                closed: false,
                title: title || '',
                width: width,
                bgiframe: true,
                tools: 'close',
                position: ['center', 'middle'],
                bbar: buttons,
                onClose: function () {
                    var div = $('#' + id);
                    if (undefined != div.dialog()) {
                        div.dialog("destroy").remove();
                        window.otcadmin._dynamicNestDiv.removeObj(div);
                        otcadmin.unmask();
                    }
                }
            });
            otcadmin.unmask();
            //判断callback是否function
            if (callback && $.isFunction(callback)) {
                callback(div);
            }
        },
        closeDivWin: function (id) {
            var div = $('#' + id);
            if (undefined != div.dialog()) {
                div.dialog("destroy").remove();
                window.otcadmin._dynamicNestDiv.removeObj(div);
                otcadmin.unmask();
            }
        },

        /**
         * targetObj,为对应的dom对象
         */
        showtip: function (targetObj, loadUrl, width) {
            targetObj.tip({maxwidth: width}, loadUrl);
        },

        /*
         * 文件上传窗口
         * @param title 标题
         * @param _businessclazz 附件所属的业务对象类名simplename
         * @param _businessid 附件所发吧的业务对象ID
         * @param callback 回调方法 function(data)
         *  @param params = {
         *  		_businessclazz :'SysAttachment',
         *  		_businessid : '',
         _attachmenttype : 2,
         _istemp : 0,
         _filetypes : "rar|zip|xml|doc|docx|xls|xlsx",
         _singlefilesize : 100000000,
         _filecounts : 1,
         _submitUrl : "",
         _width: 300,
         _height:200
         }
         */
        openFileWin: function (title, params, callback) {
            if (params._businessclazz == undefined) {
                params._businessclazz = 'SysAttachment';
            }
            if (params._businessid == undefined) {
                params._businessid = '';
            }
            if (params._attachmenttype == undefined) {
                params._attachmenttype = 2;
            }
            if (params._istemp == undefined) {
                params._istemp = 1;
            }
            if (params._filetypes == undefined) {
                params._filetypes = 'rar|zip|xml|doc|docx|xls|xlsx';
            }
            if (params._singlefilesize == undefined) {
                params._singlefilesize = '100000000';
            }
            if (params._filecounts == undefined) {
                params._filecounts = 1;
            }
            if (params._submitUrl == undefined) {
                params._submitUrl = ctx + "/common/postFileUpload.action";
            }
            if (params._width == undefined) {
                params._width = 400;
            }
            if (params._height == undefined) {
                params._height = 300;
            }
            var loadUrl = ctx + "/common/fileWin.action";
            otcadmin.openWin(title, params._width, params._height, loadUrl, function (data) {
                // 初始化文件上传控件-BEGIN
                window.otcadmin._uploadfileoptions = {
                    singleFileSize: params._singlefilesize,
                    accept: params._filetypes,
                    maxLength: params._filecounts,
                    success: callback
                };
                $('#_attachment').MultiFile({
                    max: window.otcadmin._uploadfileoptions.maxLength,
                    accept: window.otcadmin._uploadfileoptions.accept,
                    sizeLimit: window.otcadmin._uploadfileoptions.sizeLimit
                });

                //设置对应的参数值
                $("#_businessclazz").val(params._businessclazz);
                $("#_businessid").val(params._businessid);
                $("#_attachmenttype").val(params._attachmenttype);
                $("#_singlefilesize").val(params._singlefilesize);
                $("#_istemp").val(params._istemp);
                $("#_uploadfileform").attr("action", params._submitUrl);
                $("#acceptFileExtList").html(params._filetypes);
                $("#acceptFileSizeLimit").html(formatNum(params._singlefilesize / 1024 / 1024, 3) + "M");
                $("#acceptFileLengthList").html(params._filecounts);
            }, {
                "确定": function () {
                    var buttons = $(this).next().find("button");
                    var remove = $(this).parent().prev().find("i.x-icon-remove");
                    var controls = [];
                    remove.css("display", "none");
                    $("#attachementDiv").css("display", "none");
                    controls.push(remove);
                    controls.push($("#attachementDiv"));
                    $.each(buttons, function () {
                        $(this).attr("disabled", "disabled");
                        controls.push($(this));
                    });
                    otcadmin.doUploadFile(buttons, controls);
                },
                "关闭": function () {
                    otcadmin.closeWin();
                }
            }, {});
        },
        /*
         * 文件上传FUN
         */
        doUploadFile: function (buttons, controls) {
            try {
                if ($("#_attachment").val() == '') {
                    otcadmin.msg('请选择文件!');
                    if (buttons != undefined) {
                        $.each(buttons, function () {
                            $(this).removeAttr("disabled");
                        });
                    }
                    if (controls != undefined) {
                        $.each(controls, function () {
                            $(this).css("display", "");
                        });
                    }
                    return false;
                }
                $("#_uploadfileform").ajaxSubmit({
                    dataType: 'text',
                    success: function (data, status, xhr, $form) {
                        var result = eval('(' + data + ')');
                        if (result.success) {
                            window.otcadmin.closeWin();
                            if ($.isFunction(window.otcadmin._uploadfileoptions.success)) {
                                window.otcadmin._uploadfileoptions.success(result.result);
                            }
                        } else {
                            if (buttons != undefined) {
                                $.each(buttons, function () {
                                    $(this).removeAttr("disabled");
                                });
                            }
                            if (controls != undefined) {
                                $.each(controls, function () {
                                    $(this).css("display", "");
                                });
                            }
                            otcadmin.msg(result.msg);
                        }
                    }
                });
            } catch (e) {
                otcadmin.msg(e.message);
            }
        },

        /*
         * 打开新窗口, 该窗口在屏幕居中
         */
        commonOpenWin: function (url, width, height, style, callback) {
            if (style == null) {
                style = "resizable=1,directories=0,location=0,menubar=0,scrollbars=1,status=0,titlebar=0,toolbar=0";
            }
            if (height != null) {
                style = "height=" + height + "," + style;
                var top = Math.round((screen.availHeight - height) / 2);
                style = "top=" + top + "," + style;
            }
            if (width != null) {
                style = "width=" + width + "," + style;
                var left = Math.round((screen.availWidth - width) / 2);
                style = "left=" + left + "," + style;
            }
            window.open(url, "", style);
            if ($.isFunction(callback)) {
                callback();
            }
            return;
        },
        /*
         * 在线浏览
         * @param attachmentType　文档类型，直接传入对应的主表类名，如DocDocument
         * @param sid 附件SID
         * @param isTempFile 0/1
         * @param isReadOnly 0/1
         * @param isPrint 0/1
         */
        onlineView: function (attachmentType, sid, isTempFile, isReadOnly, isPrint, isShowTrack, isCreate, isRedhead, isUseSign, histFileUrl) {
            var url = ctx + "/sm/Attachment!onlineView.action?attachmentType=" + attachmentType + "&sid=" + sid + "&isTempFile=" + isTempFile + "&isReadOnly=" + isReadOnly + "&isPrint=" + isPrint + "&isShowTrack=" + isShowTrack;
            if (isCreate == 1) {//是否在线拟稿
                url = url + "&isCreate=1";
            }
            url = url + "&targetTableName=" + attachmentType;
            if (isRedhead == 1) {//是否生成行文及红头
                url = url + "&isRedhead=1";
            }
            if (isUseSign == 1) {//是否用印
                url = url + "&isUseSign=1";
            }
            if (histFileUrl != undefined && histFileUrl != '') {//历史公文正文和行文的查看
                url = url + "&histFileUrl=" + histFileUrl;
            }
            otcadmin.commonOpenWin(url, 1024, 600, null);
        },
        /*
         * 下载
         */
        download: function (url) {
            otcadmin.commonOpenWin(url, 1024, 600, null);
        },
        showHideFun: function (status, title, width, height, loadUrl, callback, buttons, params) {
            if (status == "open") {
                $("#documentContentDiv").each(function (i) {
                    $(this).hide();
                });
                // "ntkosignctl"
                $("#ntkosignctl").each(function (i) {
                    $(this).hide();
                });
            } else {
                $("#documentContentDiv").each(function (i) {
                    $(this).show();
                });
                $("#ntkosignctl").each(function (i) {
                    $(this).show();
                });
            }
            if (status == "closed") {
                $("#documentContentDiv").each(function (i) {
                    $(this).show();
                });
                $("#ntkosignctl").each(function (i) {
                    $(this).show();
                });
            }
            if (status == 'open') {
                otcadmin.openWin(title, width, height, loadUrl, callback, buttons, params);
            } else if (status == 'closed') {
                otcadmin.closeWin();
            }
        },
        skipClick: function (title, menu, obj, allowFlag, moreFlag) {
            if (!allowFlag) {
                otcadmin.msg("没有权限进行该操作");
                return false;
            }
            $.extend(skipObj, obj);
            skipObj['flag'] = true;
            moreClick(title, menu, allowFlag, moreFlag);
        },
        //swf在线预览
        showSwfView: function () {

        },
        /**
         * 打开传递参数的窗口
         * url:打开窗口的url
         * data:需要传递
         */
        openPostWindow: function (url, data) {
            var name = "_blank";
            var tempForm = document.createElement("form");
            tempForm.id = "tempForm1";
            tempForm.method = "post";
            tempForm.action = url;
            tempForm.target = name;
            composeParam(tempForm, data);
            attachEvent(tempForm, "onsubmit", function () {
                openWindow(name);
            });
            document.body.appendChild(tempForm);
            fireEvent("tempForm1", "onsubmit");
            tempForm.submit();
            document.body.removeChild(tempForm);

            function openWindow(name) {
//           	    window.open('');
            }

            function attachEvent(object, method, func) {
                if (!object[method]) {
                    object[method] = func;
                } else {
                    object[method] = object[method].attach(func);
                }
            }

            function fireEvent(controlID, eventName) {
                if (document.all) {//IE
                    eval("document.getElementById(\"" + controlID + "\")." + eventName + "();");
                } else {//fireFox
                    var e = document.createEvent('HTMLEvents');
                    e.initEvent(eventName, false, false);
                    document.getElementById(controlID).dispatchEvent(e);
                }
            }

            function composeParam(object, data) {
                $.each(data, function (key, val) {
                    var hideInput = document.createElement("input");
                    hideInput.type = "hidden";
                    hideInput.name = key
                    hideInput.value = val;
                    object.appendChild(hideInput);
                });
            }
        },
        /**
         * obj转换为字符串
         * 可接收数组、对象、String
         */
        jsonToString: function (obj) {
            var THIS = this;
            switch (typeof(obj)) {
                case 'string':
                    return '"' + obj.replace(/(["\\])/g, '\\$1') + '"';
                case 'array':
                    return '[' + obj.map(THIS.jsonToString).join(',') + ']';
                case 'object':
                    if (obj instanceof Array) {
                        var strArr = [];
                        var len = obj.length;
                        for (var i = 0; i < len; i++) {
                            strArr.push(THIS.jsonToString(obj[i]));
                        }
                        return '[' + strArr.join(',') + ']';
                    } else if (obj == null) {
                        return 'null';

                    } else {
                        var string = [];
                        for (var property in obj) {
                            string.push(THIS.jsonToString(property) + ':' + THIS.jsonToString(obj[property]));
                        }
                        return '{' + string.join(',') + '}';
                    }
                case 'number':
                    return obj;
                case false:
                    return obj;
            }
        },
        /**
         * 字符串转换为json对象
         */
        stringToJson: function (obj) {
            try {
                return eval('(' + obj + ')');
            } catch (e) {
                return new Object();
            }
        },
        /**
         * 打印指定dom元素
         */
        printTarget: function (targetName, remove) {
            if (targetName == '' || targetName == undefined) {
                return;
            }
            if (remove == undefined || remove == "") {
                remove = false;
            }
            windowName = targetName + "_" + remove;
            window.open(ctx + "/common/print.action", windowName);
        },
        replaceController: function (targetName) {
            var targetObj = $(document.getElementById(targetName));
            $.each($("input:not(:hidden)", targetObj), function () {
                var $this = $(this);
                var content = getInputContent($this);
                var tagName = content[0].tagName;
                tagName = tagName || "";
                if (tagName.toLowerCase() == "span") {
                    $this.replaceWith(content);
                }
            })
            $.each($("select:not(:hidden)", targetObj), function () {
                var text = $(this).find('option:selected').text();
                $(this).replaceWith(text == undefined ? "" : text);
            });
            //单独处理textarea
            $.each($("textarea:not(:hidden)", targetObj), function () {
                var value = this.value || "";
                var textArray = value.split("\n"); //对文本进行分行处理
                var html = "";
                for (var i = 0; i < textArray.length; i++) {
                    html += textArray[i] + "<br />";//加上html换行符
                }
                var width = $(this).css("width");
                var height = $(this).css("height");
                var span = $("<span style='display:inline-block'></span>");
                if (width != undefined || width != "") {
                    span.css("width", width);
                }
                if (height != undefined || height != "") {
                    span.css("height", height);
                }
                $(this).replaceWith(span.html(html));
            });

            function getInputContent(source) {
                var type = source.attr("type") || "";
                type = type.toLowerCase();
                if (type == "text") {
                    //设置宽度和高度
                    var width = source.css("width");
                    var height = source.css("height");
                    var span = $("<span style='display:inline-block;word-break:break-all;word-warp:break-word;text-align:center'></span>");
                    if (width != undefined || width != "") {
                        span.css("width", width);
                    }
                    if (height != undefined || height != "") {
                        span.css("height", height);
                    }
                    span.text(source.val())
                    return span;
                } else if (type == "button" || type == "") {
                    return $("<span></span>");
                } else {
                    return source;
                }
            }
        },
        preView: function (cname, sid, isTempFile) {
            otcadmin.commonOpenWin(ctx + "/common/preViewImage.action?sid=" + sid + "&isTempFile=" + isTempFile, 1024, 600, null);
        },

        //加法
        accAdd: function (arg1, arg2) {
            var r1, r2, m;
            try {
                r1 = arg1.toString().split(".")[1].length
            } catch (e) {
                r1 = 0
            }
            try {
                r2 = arg2.toString().split(".")[1].length
            } catch (e) {
                r2 = 0
            }
            m = Math.pow(10, Math.max(r1, r2));
            return (arg1 * m + arg2 * m) / m;
        },
        //减法
        accSub: function (arg1, arg2) {
            return accAdd(arg1, -arg2);
        },
        //乘法
        accMul: function (arg1, arg2) {
            var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
            try {
                m += s1.split(".")[1].length
            } catch (e) {
            }
            try {
                m += s2.split(".")[1].length
            } catch (e) {
            }
            return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
        },
        //除法
        accDiv: function (arg1, arg2) {
            var t1 = 0, t2 = 0, r1, r2;
            try {
                t1 = arg1.toString().split(".")[1].length
            } catch (e) {
            }
            try {
                t2 = arg2.toString().split(".")[1].length
            } catch (e) {
            }
            with (Math) {
                r1 = Number(arg1.toString().replace(".", ""))
                r2 = Number(arg2.toString().replace(".", ""))
                return (r1 / r2) * pow(10, t2 - t1);
            }
        },
        /**
         * 判断是否为空
         * 空 : true
         * 非空 : false
         */
        isEmpty: function (_value) {
            if (_value == null || _value == undefined || _value == "" || _value.length == 0) {
                return true;
            }
            return false;
        }
    };
})();

