var auditProduct = function () {
    var index =0;
    var loadData = function () {
        var aoColumns = [
            {"mData": "id"},
            {"mData": "channel"},
            {"mData": "merchantName"},
            {"mData": "coinName"},
            {"mData": "coinDescription"},
            {"mData": "coinDecimals"},
            {"mData": "tokenAddress"},
            {"mData": "status"},
            {"mData": "createTime"},
            {"mData": null}
        ];
        var aoColumnDefs = [{
            "aTargets": [7],
            "mRender": function (a, b, c, d) {
                if(a==0){
                    return "未审核";
                }else if(a==1){
                    return "审核通过";
                }else{
                    return "审核不通过"
                }

            }
        },{
            "aTargets": [8],
            "mRender": function (a, b, c, d) {
                var date = new Date(a);
                return date.getFullYear()+"-"+(date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-'+date.getDate() + ' '+date.getHours() + ':'+date.getMinutes() + ':'+date.getSeconds();
            }
        },{
            "aTargets": [9],
            "mRender": function (a, b, c, d) {
                return "<a class=\"red\" name=\"audit\" href=\"javascript:;\"> 审核 </a>";
            }
        }];
        var t = $("#dataTables-example");
        var csrf = $("#csrfId");
        initPageTable(t, "auditProduct/queryList?"+csrf.attr("name")+"="+csrf.attr("value"), aoColumns, aoColumnDefs, __queryHandler, __initHandler);
    };
    var __queryHandler =function (condition) {
        var channelSearch = $("#channelSearch").val();
        if (assertNotNullStr(channelSearch)) condition.channelSearch = channelSearch;
    };
    var __initHandler =function () {
        // 编辑
        $("#dataTables-example tbody").on("click", "a[name='audit']", function () {
            if(index ==0) {
                index ++;
                var table = $('#dataTables-example').DataTable();
                var d = table.row($(this).parents('tr')).data();
                var csrf = $("#csrfId");
                var csrfName = csrf.attr('name');
                var param = {"id": d.id};
                $.post("auditProduct/queryById?" + csrfName + "=" + csrf.attr("value"), param, function (data) {
                    if (data.code == 200) {
                        var d = data.data;
                        $("#id").val(d.id);
                        $("#coinName").val(d.coinName);
                        $("#coinDescription").val(d.coinDescription);
                        $("#tokenAddress").val(d.tokenAddress);
                        $("#coinDecimals").val(d.coinDecimals);
                        $("#channel").val(d.channel);
                        $("#merchantName").val(d.merchantName);
                        var time = new Date(d.createTime)
                        $("#createTime").val(time);
                        layer.open({
                            area: '800px',
                            shade: [0.8, '#393D49'],
                            title: "审核币种",
                            type: 1,
                            content: $("#addWin"),
                            btn: ['通过','不通过'],
                            success: function (layero, index) {
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
                                                layer.msg("审核失败");
                                            }
                                        }
                                    });
                                }
                                index = 0;
                            },
                            btn2: function (i, layero) {
                                $.ajax({
                                    url:getRootPath()+"/auditProduct/auditFailed",
                                    type:"get",
                                    dataType:"json",
                                    data:"id="+d.id,
                                    success:function (d) {
                                        if (d.code == 200) {
                                            var dataTable = $("#dataTables-example").dataTable();
                                            dataTable.fnReloadAjax();
                                            layer.close(i);
                                        } else {
                                            layer.msg("审核失败");
                                        }
                                    }
                                })
                            }
                        });
                    }
                });
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