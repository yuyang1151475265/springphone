<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
${datas}
<div id="updateform" style="padding-top: 20px">
<form class="layui-form updateform">
    <div class="layui-form-item">
        <label class="layui-form-label">id</label>
        <div class="layui-input-inline">
            <input readonly type="text" name="id" required  lay-verify="required" placeholder="你的名字" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">名称</label>
        <div class="layui-input-inline">
            <input type="text" name="name" required  lay-verify="required" placeholder="你的名字" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">你的生日</label>
        <div class="layui-input-inline">
            <input id="test2" type="text" name="datetime" required  lay-verify="required" placeholder="生日" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit>立即提交</button>
            <button type="button" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
</div>
<script>
    //日期选择的！！
    layui.use('laydate', function(){
        var laydate = layui.laydate;
        laydate.render({
            elem: '#test2' //指定元素
        });
    });
    //监听
    layui.use('form', function(){
        var form = layui.form;
        var layer = layui.layer;
        //监听提交
        form.on('submit()', function(data){
            $.ajax({
                url:"/updateUser",
                type:"post",
                data:$(".updateform").serialize(),
                dataType:"text",
                success:function(response){
                    reloads(tables.config.page.curr);
                    layer.closeAll();
                },
                error:function(){
                    alert("失败！");
                }
            });
            return false;
        });
        //回显
        var data = JSON.parse('${data}');
        $(".updateform [name=id]").val(data.id);
        $(".updateform [name=name]").val(data.name);
        $(".updateform [name=datetime]").val(moment(data.datetime).format("YYYY-MM-DD"));
        $(".layui-btn-primary").click(function () {
            $(".updateform [name=id]").val(data.id);
            $(".updateform [name=name]").val(data.name);
            $(".updateform [name=datetime]").val(moment(data.datetime).format("YYYY-MM-DD"));
        });
    });
</script>
</body>
</html>