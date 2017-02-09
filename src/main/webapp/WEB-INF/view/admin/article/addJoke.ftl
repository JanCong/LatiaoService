<div class="row">

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