<div class="row">
    <div class="col-xs-12">
        <div class="box">
            <div class="box-header">
                <h3 class="box-title">无聊图管理</h3>
                <div class="box-tools pull-right">
                    <a onclick="emailAccountInfoToListAjax();" class="btn btn-sm btn-primary" target="modal" modal="lg"
                       href="${ctx}/admin/article/addPicture">添加</a>
                </div>
            </div>
            <div class="box-body">
                <div class="clearfix">
                    <div class="col-md-4">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="fa fa-search"></i></span>
                            <input type="text" class="form-control" id="accountInfoPremise" placeholder="搜索">
                        </div>
                    </div>
                    <div class="col-md-4">
                        <button type="button" onclick="emailAccountInfoReload();" class="btn btn-primary">搜索</button>
                    </div>
                </div>
                <table id="pictureList_tab" class="table table-bordered table-striped">
                    <thead>
                    <tr>
                        <th style="width: 100px">序号</th>
                        <th>ID</th>
                        <th style="width: 100px">作者ID</th>
                        <th style="width: 100px">作者</th>
                        <th>内容</th>
                        <th style="width: 100px">评论</th>
                        <th>赞</th>
                        <th>踩</th>
                        <th style="width: 50px">状态</th>
                        <th style="width: 150px">创建时间</th>
                        <th style="width: 150px">操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var pictureList_tab;
    $(function () {
        //初始化表格

        var No = 0;
        pictureList_tab = $('#pictureList_tab').DataTable({
            "dom": "itflp",
            "processing": true,
            "searching": false,
            "serverSide": true, //启用服务器端分页
            "bInfo": false,
            "language": {"url": "plugins/datatables/language.json"},
            "aLengthMenu": [5, 10, 25, 50, 100],
            //"bAutoWidth": false,
            "ajax": function (data, callback, settings) {
                //封装请求参数
                var param = {};
                param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                param.start = data.start;//开始的记录序号
                param.page = (data.start / data.length) + 1;//当前页码
                //ajax请求数据
                $.get('${api}/article/picture/' + param.page + '/' + param.limit, function (result) {
                    console.log(result);
                    //封装返回数据
                    var returnData = {};
                    returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                    returnData.recordsTotal = result.total;//返回数据全部记录
                    returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                    returnData.data = result.list;//返回的数据列表
                    //console.log(returnData);
                    //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                    //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                    callback(returnData);
                });
            },
            "columns": [
                {data: null},
                {data: "id"},
                {data: "authorId"},
                {data: "authorName"},
                {data: "images"},
                {data: "commentCount"},
                {data: "likeCount"},
                {data: "hateCount"},
                {data: "status"},
                {data: "createTime"},
                {data: null}
            ],
            "columnDefs": [
                {
                    targets: 0,
                    data: null,
                    render: function (data) {
                        No = No + 1;
                        return No;
                    }
                },
                {
                    targets: 4,
                    data: null,
                    render: function (data, type, full) {
                        var html = '<p>' + full.content + '</p>';
                        if (data) {
                            data.forEach(function (img) {
                                console.log(img);
                                html += '<img src="' + img.thumbnailUrl + '" /><br />';
                            });
                        }
                        console.log(html);
                        return html;
                    }
                },
                {
                    targets: -1,
                    data: null,
                    render: function (data) {
                        var btn = '<a class="btn btn-xs btn-primary" target="modal" modal="lg" href="${ctx}/admin/emailAccountInfo/view?id=' + data.id + '">查看</a> &nbsp;'
                                + '<a class="btn btn-xs btn-info" onclick="emailAccountInfoToListAjax();" target="modal" modal="lg" href="${ctx}/admin/emailAccountInfo/edit?id=' + data.id + '">修改</a> &nbsp;'
                                + '<a class="btn btn-xs btn-default" callback="emailAccountInfoReload();" data-body="确认要删除吗？" target="ajaxTodo" href="${ctx}/admin/emailAccountInfo/delete?id=' + data.id + '">删除</a>';
                        return btn;
                    }
                }]
        }).on('preXhr.dt', function (e, settings, data) {
            No = 0;
        });
    });

    function emailAccountInfoToListAjax() {
        list_ajax = pictureList_tab;
    }
    function emailAccountInfoReload() {
        reloadTable(pictureList_tab, "#accountInfoTime", "#accountInfoPremise");
    }
</script>