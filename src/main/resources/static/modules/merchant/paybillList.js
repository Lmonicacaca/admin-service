var paybillList = function () {
    var index =0;
    var loadData = function () {
        var aoColumns = [
            {"mData": "id"},
            {"mData": "channel"},
            {"mData": "merchantId"},
            {"mData": "refBizNo"},
            {"mData": "coinId"},
            {"mData": "amount"},
            {"mData": "fromAddr"},
            {"mData": "toAddr"},
            {"mData": "tokenAddr"},
            {"mData": "billType"},
            {"mData": "status"},
            {"mData": "createTime"},
            {"mData": "lastUpdateTime"}
        ];
        var aoColumnDefs = [
            {
                "aTargets": [1],
                "mRender": function (a, b, c, d) {
                    if(a==null){
                        return "";
                    }else{
                        return a;
                    }
                }
            },
            {
                "aTargets": [2],
                "mRender": function (a, b, c, d) {
                    if(a==null){
                        return "";
                    }else{
                        return a;
                    }
                }
            },   {
                "aTargets": [9],
                "mRender": function (a, b, c, d) {
                    if(a==0){
                        return "提现";
                    }else {
                        return "支付"
                    }

                }
            },
            {
            "aTargets": [10],
            "mRender": function (a, b, c, d) {
                if(a==0){
                    return "初始";
                }else if(a==1){
                    return "处理中";
                }else if(a==2){
                    return "成功";
                }else if(a==3){
                    return "失败";
                }

            }
        },
            {
                "aTargets": [11],
                "mRender": function (a, b, c, d) {
                    if(a!=null||a!=""){
                        var date = new Date(a);
                        return date.getFullYear()+"-"+(date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-'+date.getDate() + ' '+date.getHours() + ':'+date.getMinutes() + ':'+date.getSeconds();
                    }

                }
            },
            {
                "aTargets": [12],
                "mRender": function (a, b, c, d) {
                    if(a!=null||a!=""){
                        var date = new Date(a);
                        return date.getFullYear()+"-"+(date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-'+date.getDate() + ' '+date.getHours() + ':'+date.getMinutes() + ':'+date.getSeconds();
                    }

                }
            }];
        var t = $("#dataTables-example");
        var csrf = $("#csrfId");
        initPageTable(t, "payBill/queryList?"+csrf.attr("name")+"="+csrf.attr("value"), aoColumns, aoColumnDefs, __queryHandler, __initHandler);
    };
    var __queryHandler =function (condition) {
        var merchantidSearch = $("#merchantidSearch").val();
        var fromAddrSearch = $("#fromAddrSearch").val();
        var toAddrSearch = $("#toAddrSearch").val();
        var billTypeSearch = $("#billType option:selected").val();
        if (assertNotNullStr(merchantidSearch)) condition.merchantidSearch = merchantidSearch;
        if (assertNotNullStr(fromAddrSearch)) condition.fromAddrSearch = fromAddrSearch;
        if (assertNotNullStr(toAddrSearch)) condition.channelSearch = toAddrSearch;
        if (assertNotNullStr(billTypeSearch)) condition.billTypeSearch = billTypeSearch;
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
                $.post("payBill/deletePay?" + csrfName + "=" + csrf.attr("value"), param).then(function (data) {
                    if (data.code == 200) {
                        layer.msg("删除成功");
                        var dataTable = $("#dataTables-example").dataTable();
                        dataTable.fnReloadAjax();
                    }
                });
            });
        });
    }

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

    return {
        init:function () {
            loadData();
            query();
        }
    };
}();