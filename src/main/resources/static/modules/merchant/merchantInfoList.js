var merchantInfo = function () {
    var index =0;
    var loadData = function () {
        var aoColumns = [
            {"mData": "id"},
            {"mData": "channel"},
            {"mData": "name"},
            {"mData": "logoBill"},
            {"mData": "website"},
            {"mData": "rsaPublic"},
            {"mData": "rsaPrivate"},
            {"mData": "audit"},
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
                    }else{
                        return a;
                    }
                }
            },
            {
                "aTargets": [3],
                "mRender": function (a, b, c, d) {
                    if(a==null){
                        return "";
                    }else{
                        return "<img src='"+a+"' style='max-height: 50px'/>";
                    }
                }
            },
            {
                "aTargets": [4],
                "mRender": function (a, b, c, d) {
                    if(a==null){
                        return "";
                    }else{
                        return "<a href='"+a+"' target='_blank'>网站</a>";
                    }
                }
            },
            {
                "aTargets": [5],
                "mRender": function (a, b, c, d) {
                    if(a!=null&&a!=""){
                        return "<a class=\"edit\" name =\"rsaPublic\" href=\"javascript:;\">公钥</a>";
                    }else{
                        return "";
                    }

                }
            },
            {
                "aTargets": [6],
                "mRender": function (a, b, c, d) {
                    if(a!=null&&a!=""){
                        return "<a class=\"edit\" name =\"rsaPrivate\" href=\"javascript:;\">私钥</a>";
                    }else{
                        return ""
                    }

                }
            },
            {
                "aTargets": [7],
                "mRender": function (a, b, c, d) {
                    if(a==0){
                        return "未审核"
                    }else if(a==1){
                        return "审核通过"
                    }else{
                        return "审核不通过"
                    }
                }
            },
            {
                "aTargets": [8],
                "mRender": function (a, b, c, d) {
                    if(a==0){
                        return "可用"
                    }else if(a==1){
                        return "不可用"
                    }
                }
            },
            {
                "aTargets": [9],
                "mRender": function (a, b, c, d) {
                    var date = new Date(a);
                    return date.getFullYear()+"-"+(date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-'+date.getDate() + ' '+date.getHours() + ':'+date.getMinutes() + ':'+date.getSeconds();
                }
            },
            {
            "aTargets": [10],
            "mRender": function (a, b, c, d) {
                return "<a class=\"red\" name=\"delete\" href=\"javascript:;\"> 删除 </a>";
            }
        }];
        var t = $("#dataTables-example");
        var csrf = $("#csrfId");
        initPageTable(t, "merchantInfo/queryList?"+csrf.attr("name")+"="+csrf.attr("value"), aoColumns, aoColumnDefs, __queryHandler, __initHandler);
    };
    var __queryHandler =function (condition) {
        var nameSearch = $("#nameSearch").val();
        if (assertNotNullStr(nameSearch)) condition.nameSearch = nameSearch;
    };
    var __initHandler =function () {

        $("#dataTables-example tbody").on("click", "a[name='rsaPublic']", function () {
            $("#showRsa").val("");
            var table = $('#dataTables-example').DataTable();
            var d = table.row($(this).parents('tr')).data();
            var rsa = "";
            $.ajax({
                url:getRootPath()+"/merchantInfo/queryRsaPublic",
                type:"get",
                async:false,
                data:"id="+d.id,
                success:function(data){
                    rsa = data;
                }
            })

            $("#rsa").val(rsa);
            layer.open({
                area: '800px',
                shade: [0.8, '#393D49'],
                title: "公钥",
                type: 1,
                content: $("#showRsa")
            })
        })
        $("#dataTables-example tbody").on("click", "a[name='rsaPrivate']", function () {
            $("#showRsa").val("");
            var table = $('#dataTables-example').DataTable();
            var d = table.row($(this).parents('tr')).data();
            var rsa = "";
            $.ajax({
                url:getRootPath()+"/merchantInfo/queryRsaPrivate",
                type:"get",
                async:false,
                data:"id="+d.id,
                success:function(data){
                    rsa = data;
                }
            })

            $("#rsa").val(rsa);
            layer.open({
                area: '800px',
                shade: [0.8, '#393D49'],
                title: "私钥",
                type: 1,
                content: $("#showRsa")
            })
        })



        //删除
        $("#dataTables-example tbody").on("click", "a[name='delete']", function () {
            var table = $('#dataTables-example').DataTable();
            var d = table.row($(this).parents('tr')).data();
            var param = {"id": d.id};
            var csrf = $("#csrfId");
            var csrfName = csrf.attr('name');
            layer.confirm("你确定要删除该数据吗？", function (index) {
                $.post("merchantInfo/deleteMerchant?" + csrfName + "=" + csrf.attr("value"), param).then(function (data) {
                    if (data.code == 200) {
                        layer.msg("删除成功");
                        var dataTable = $("#dataTables-example").dataTable();
                        dataTable.fnReloadAjax();
                    }
                });
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