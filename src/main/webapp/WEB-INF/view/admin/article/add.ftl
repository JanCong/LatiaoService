<div class="row">
    <div class="col-md-12">
        <form id="addArticleForm">
            <div>
                <div class="form-group">
                    <input type="hidden" class="form-control" name="userId" value="3">
                </div>
                <div class="form-group">
                    <label>内容</label>
                    <textarea rows="5" name="content" class="form-control" placeholder="输入内容..."></textarea>
                </div>
                <div class="form-group">
                    <label>图片</label>
                    <input type="file" id="fileImages" class="form-control" name="images" multiple>
                </div>
            </div>
            <div class="box-footer">
                <div class="pull-right">
                    <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"><i
                            class="fa fa-close"></i> 取消
                    </button>
                    <button type="submit" class="btn btn-primary btn-sm">
                        <i class="fa fa-save"></i> 添加
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript">
    $('#addArticleForm').submit(function () {
        var myFormData = {
            userId: 3,
            content: $('textarea[name="content"]').val(),
            images: $('input[name="images"]')[0].files[0]
        };

        var formData = new FormData();
        formData.append('userId', 3);
        formData.append('content', $('textarea[name="content"]').val());

        var files = $('input[name="images"]')[0].files;
        $(files).each(function () {
            formData.append('images', this);
        })

//        var files = $('input[name="images"]')[0].files;
//        for (var i = 0; i < files.length; i++) {
//            formData.append('images', files[i]);
//        }

        console.log(formData);
        $.ajax({
            url: '${ctx}/api/article',
            type: 'POST',
            data: formData,    // Do not send it as - data: { formData: formData }
            processData: false, // Tell jquery to don't process the data
            contentType: false, // If you do not set it as false, most probably you would get 400 or 415 error
            success: function (data, status) {
                alert('success');
            },
            failure: function (data) {

            }
        });

        return false;

    });
</script>