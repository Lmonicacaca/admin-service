var merchantCoinTmp = function () {
    var index =0;
    var loadData = function () {
        var aoColumns = [
            {"mData": "id"},
            {"mData": "channel"},
            {"mData": "merchantId"},
            {"mData": "coinId"},
            {"mData": "coinName"},
            {"mData": "rechargeAddress"},
            {"mData": "withdrawAddress"},
            {"mData": "tokenAddress"},
            {"mData": "status"},
            {"mData": "createTime"},
            {"mData": null}
        ];
        var aoColumnDefs = [
            {
                "aTargets": [1],
                "mRender": function (a, b, c, d) {
                    if(a==null){
                        return "";
                    }else {
                        return a;
                    }

                }
            }, {
                "aTargets": [7],
                "mRender": function (a, b, c, d) {
                    if(a==null){
                        return "";
                    }else {
                        return a;
                    }

                }
            },
            {
            "aTargets": [8],
            "mRender": function (a, b, c, d) {
                if(a==0){
                    return "待审核";
                }else if(a==1){
                    return "通过";
                }else{
                    return "不通过"
                }

            }
        }, {
            "aTargets": [9],
            "mRender": function (a, b, c, d) {
                // if(a!=null){
                //     var date = new Date(a);
                //     return date.getFullYear()+"-"+(date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-'+date.getDate() + ' '+date.getHours() + ':'+date.getMinutes() + ':'+date.getSeconds();
                // }else{
                //     return "";
                // }

                if(a==null||a==""){
                    return ""
                }else{
                    return a;
                }

            }
        },{
            "aTargets": [10],
            "mRender": function (a, b, c, d) {
                return "<a class=\"edit\" name =\"audit\" href=\"javascript:;\"> 审核 </a>";
            }
        }];
        var t = $("#dataTables-example");
        var csrf = $("#csrfId");
        initPageTable(t, "merchantCoinTmp/queryList?"+csrf.attr("name")+"="+csrf.attr("value"), aoColumns, aoColumnDefs, __queryHandler, __initHandler);
    };
    var __queryHandler =function (condition) {
        var merchantIdSearch = $("#merchantIdSearch").val();
        if (assertNotNullStr(merchantIdSearch)) condition.merchantIdSearch = merchantIdSearch;
    };
    var __initHandler =function () {
        // 审核
        $("a[name='audit']").on("click", function () {
                var table = $('#dataTables-example').DataTable();
                var d = table.row($(this).parents('tr')).data();
                var csrf = $("#csrfId");
                var csrfName = csrf.attr('name');
                var param = {"id": d.id};
                $.post("merchantCoinTmp/queryById?" + csrfName + "=" + csrf.attr("value"), param, function (data) {
                    if (data.code == 200) {
                        var d = data.data;
                        $("#id").val(d.id);
                        $("#oldChannel").val(d.channel);
                        $("#coinId").val(d.coinId);
                        $("#merchantId").val(d.merchantId);
                        $("#createUserName").val(d.createUserName);
                        $("#updateUserName").val(d.updateUserName);
                        $("#createTime").val(new Date(d.createTime));
                        $("#status").val(d.status);
                        $("#rechargeAddress").val(d.rechargeAddress);
                        $("#tokenAddress").val(d.tokenAddress);
                        $("#coinName").val(d.coinName);
                        $("#withdrawAddress").val(d.withdrawAddress);
                        var channel =d.channel;
                        if(channel==null){
                            channel = ""
                        }
                        var option = "<option value='" + channel + "' selected='selected'>" + channel + "</option>";
                        $("#channel").empty();
                        $("#channel").append(option);
                        layer.open({
                            area: '800px',
                            shade: [0.8, '#393D49'],
                            title: "修改",
                            type: 1,
                            content: $("#audit"),
                            btn: ['通过','不通过'],
                            success: function (layero, index) {
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
                                                layer.msg(d.message);
                                            }
                                        }
                                    });
                                }
                                index = 0;
                                },
                            btn2: function (i, layero) {
                                alert(222222222)
                                $.ajax({
                                    url:getRootPath()+"/merchantCoinTmp/auditFailed",
                                    type:"get",
                                    data:"id="+d.id,
                                    success:function (d) {
                                        if (d.code == 200) {
                                            var dataTable = $("#dataTables-example").dataTable();
                                            dataTable.fnReloadAjax();
                                            layer.close(i);
                                        } else {
                                            layer.msg(d.message);
                                        }
                                    }

                                })
                    }
                        });
                    }
                });
        });
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
                url: "merchantCoinTmp/queryChannel",
                cache: true,
                processResults: function (data) {
                    return {
                        results: data
                    };
                }
            }
        });
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
            query();
        }
    };
}();