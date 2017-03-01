<div class="row">
    <div class="col-md-12">
        <form id="accountInfoAddForm" action="/api/article/upload" method="post" enctype="multipart/form-data">
            <div>
                <div class="form-group">
                    <input type="hidden" class="form-control" name="userId" value="3">
                </div>
                <div class="form-group">
                    <label id="fromUserLabel">内容</label>
                    <input type="text" class="form-control" name="content" id="fromUser" placeholder="输入邮箱...">
                </div>
                <div class="form-group">
                    <label id="passwdLabel">图片</label>
                    <input type="file" class="form-control" name="images" multiple>
                </div>
            </div>
            <div class="box-footer">
                <div class="pull-right">
                    <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i
                            class="fa fa-close"></i>取消
                    </button>
                    <button type="button" class="btn btn-primary btn-sm"><i
                            class="fa fa-save"></i>添加
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript">
    function emilAccountInfoSave() {
        var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
        var status = 1;

        $("span").remove(".errClass");
        $("br").remove(".errClass");
//        if ($("#fromUser").val() == "") {
//            $("#fromUserLabel").prepend('<span class="errClass" style="color:red">*邮箱不能为空</span><br class="errClass"/>');
//            status = 0;
//        }
//        if (!reg.test($("#fromUser").val()) && $("#fromUser").val() != "") {
//            $("#fromUserLabel").prepend('<span class="errClass" style="color:red">*邮箱格式不正确</span><br class="errClass"/>');
//            status = 0;
//        }
//        if ($("#passwd").val() == "") {
//            $("#passwdLabel").prepend('<span class="errClass" style="color:red">*授权码不能为空</span><br class="errClass"/>');
//            status = 0;
//        }
        if (status == 0) {
            return false;
        } else {
            $.ajax({
                url: '${ctx}/api/article/upload',
                type: 'post',
                dataType: 'multipart/form-data',
                data: $("#accountInfoAddForm").serialize(),
                success: function (data) {
                    $("#lgModal").modal('hide');
                    alertMsg("添加成功", "success");
                    reloadTable(list_ajax, "#accountInfoTime", "#accountInfoPremise");
                }
            });
        }
    }
</script>