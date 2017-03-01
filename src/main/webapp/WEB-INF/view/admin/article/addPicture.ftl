<div class="row">
    <div class="col-md-3 col-md-offset-3">
        <form role="form" id="addMemberintegral">
            <div class="box-body">

                <div class="form-group row">
                    <label class="col-sm-4 control-label">用户名</label>

                    <div class="col-sm-8">
                        <input type="text" class="form-control" name="userName">
                    </div>
                </div>

                <div class="form-group row">
                    <label class="col-sm-4 control-label">充值积分</label>

                    <div class="col-sm-8">
                        <input type="number" required min="0.01" step="0.01" class="form-control" name="integral">
                    </div>
                </div>

                <div class="form-group row" id="noUser" style="display:none;">
                    <p>
                        用户不存在，请先
                        <a href="@System.Configuration.ConfigurationManager.AppSettings[" MainUrl"]register"
                        target="_blank" />
                        注册
                        @System.Configuration.ConfigurationManager.AppSettings["MainUrl"]register</a>
                    </p>
                </div>

            </div>
            <!-- /.box-body -->

            <div class="box-footer">
                <button type="submit" data-loading-text="请稍后..." class="btn btn-primary col-sm-offset-5">提交</button>
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
        if ($("#fromUser").val() == "") {
            $("#fromUserLabel").prepend('<span class="errClass" style="color:red">*邮箱不能为空</span><br class="errClass"/>');
            status = 0;
        }
        if (!reg.test($("#fromUser").val()) && $("#fromUser").val() != "") {
            $("#fromUserLabel").prepend('<span class="errClass" style="color:red">*邮箱格式不正确</span><br class="errClass"/>');
            status = 0;
        }
        if ($("#passwd").val() == "") {
            $("#passwdLabel").prepend('<span class="errClass" style="color:red">*授权码不能为空</span><br class="errClass"/>');
            status = 0;
        }
        if (status == 0) {
            return false;
        } else {
            $.ajax({
                url: '${ctx}/admin/emailAccountInfo/save',
                type: 'post',
                dataType: 'text',
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