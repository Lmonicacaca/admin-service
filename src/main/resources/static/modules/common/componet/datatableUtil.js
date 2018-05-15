//初始化datatables  
function initPageTable(/**Node*/dom, /**String*/url, /**Array*/aoColumns, /**Array*/aoColumnDefs, /**function*/conditionFun, /**function*/initHandlerFun) {
    var table = $(dom).DataTable({
        "bFilter" : false, // 搜索栏
        "processing" : true,
        "bSort" : false,
        "bLengthChange": true,
        "bAutoWidth": true,
        "bInfo": true,//页脚信息
        "serverSide" : true,// 打开后台分页
        "sServerMethod":"post",
        "pageLength" : 10,
        "ajax": {
            "url": url,
            "type": "post",
            "beforeSend": function(request) {
                //request.setRequestHeader("X-CSRF-TOKEN", $("csrfId").attr("value"));

            },
            "data": !conditionFun ? function (data) {

            } : conditionFun
        },
        "fnDrawCallback":function() {

            if (initHandlerFun) initHandlerFun();
            var api = this.api();
            var startIndex= api.context[0]._iDisplayStart;
           // if(isSeq!="1"){
                api.column(0).nodes().each(function(cell, i) {
                    cell.innerHTML = startIndex + i + 1;
                });
           // }

        },
        "oLanguage": {
            "sProcessing": "正在加载中......",
            "sLengthMenu": "每页显示 _MENU_ 条记录",
            "sZeroRecords": "没有数据！",
            "sEmptyTable": "表中无数据存在！",
            "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
            "sInfoEmpty": "显示0到0条记录",
            "sInfoFiltered": "数据表中共为 _MAX_ 条记录",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上一页",
                "sNext": "下一页",
                "sLast": "末页"
            }
        },
        "aoColumns": aoColumns,
        "aoColumnDefs":aoColumnDefs

    });
    return table;
}

function assertNotNullStr(/**Any Object*/obj) {
    if (( typeof obj == "undefined") || (obj == null) || (obj === "null") || (obj === "undefined") || (obj === ""))
        return false;
    return true;
};

function publishFormData(/**Node*/parentNode, /**Object*/data) {
    if (!parentNode || !data) return;
    console.log(data);
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
}
