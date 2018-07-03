var ethTransaction = function () {
    var index =0;
    var loadData = function () {
        var aoColumns = [
            {"mData": "id"},
            {"mData": "orderId"},
            {"mData": "coinName"},
            {"mData": "txStatus"},
            {"mData": "createTime"},
            {"mData": "from"},
            {"mData": "to"},
            {"mData": "value"},
            {"mData": "isErc20"},

        ];
        var aoColumnDefs = [{
            "aTargets": [3],
            "mRender": function (a, b, c, d) {
                if(a==-1){
                    return "交易发送";
                }else if(a==0){
                    return "交易pending";
                }else if(a==1){
                    return "交易失败";
                }else if(a==2){
                    return "交易确认";
                }else if(a==3){
                    return "交易成功";
                }

            }
        },{
            "aTargets": [8],
            "mRender": function (a, b, c, d) {
                if(a==false){
                    return "否";
                }else if(a==true){
                    return "是";
                }

            }
        }];
        var t = $("#dataTables-example");
        var csrf = $("#csrfId");
        initPageTable(t,"ethTransaction/queryList?"+csrf.attr("name")+"="+csrf.attr("value"), aoColumns, aoColumnDefs, __queryHandler, __initHandler);
    };
    var __queryHandler =function (condition) {
        var orderIdSearch = $("#orderIdSearch").val();
       /* var fromSearch = $("#fromSearch").val();
        var toSearch = $("#toSearch").val();*/
        if (assertNotNullStr(orderIdSearch)) condition.orderIdSearch = orderIdSearch;
       /* if (assertNotNullStr(fromSearch)) condition.fromSearch = fromSearch;
        if (assertNotNullStr(toSearch)) condition.fromSearch = toSearch;*/
    };
    var __initHandler =function () {

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

    return {
        init:function () {
            loadData();
            query();
        }
    };
}();