<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/3/23
  Time: 17:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/static/layui/css/layui.css">
    <script src="/static/jquery-1.8.0.js"></script>
    <script src="/static/layui/layui.js"></script>
</head>
<style>
    .yy-diy{width: 60%; display: inline-block}
    #myform{padding-right: 50px; padding-top:20px;}
    #box{display: none}
    .sjyzm{width: 106px;transition: all 1s;}
    .suoding{background-color:#946a6a; width:53px;}
</style>
<body>

<div id="box">
<form id="myform" class="layui-form">
    <div class="layui-form-item">
        <label class="layui-form-label">账户:</label>
        <div class="layui-input-block">
            <input type="text" name="username" lay-verify="required" placeholder="账户" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">密码:</label>
        <div class="layui-input-block">
            <input type="password" name="password" lay-verify="required|passwordtong" placeholder="密码" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">确认密码:</label>
        <div class="layui-input-block">
            <input type="password" name="passwordtwo" lay-verify="required|passwordtong" placeholder="确认密码" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">手机:</label>
        <div class="layui-input-block">
            <input type="text" name="phone" lay-verify="required|phone" placeholder="手机" autocomplete="off" class="layui-input yy-diy">
            <button type="button" class="layui-btn layui-btn-radius sjyzm">获取验证码</button>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">手机验证码:</label>
        <div class="layui-input-block">
            <input type="text" name="phoneyzm" lay-verify="required" placeholder="手机验证码" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>
</div>

</body>
<script>
    var int ="";
    layui.use(['layer', 'form'], function(){
        var layer = layui.layer,form = layui.form;
        layer.open({
            type:1
            ,title:'注册'
            ,skin:'layui-layer-lan'
            ,content:$("#myform")
            ,anim:2
            ,closeBtn:0
            ,area: ['500px', '400px']
            ,success:function () {
                $("#box").show();
            }
        });

        form.on('submit(formDemo)', function(data){
                $.ajax({
                url:"/insert",
                type:"post",
                data:$("#myform").serialize(),
                dataType:"text",
                success:function(response){
                    if(response=="yzmygq"){
                        layer.msg("验证码无效！请重新获取",{icon:5,anim:6});
                    }else if(response=="cuowu"){
                        layer.msg("验证码错误！",{icon:4,anim:6});
                    }else {
                        layer.msg("验证码成功！",{icon:6,anim:0});
                    }
                },
                error:function(){
                    alert("失败！");
                }
                });
                return false;
        });
        function shoujiyz() {
                if(/^0?(13|14|15|17|18|19)[0-9]{9}$/.test($("[name=phone]").val())) {
                    $(".sjyzm").addClass("suoding").html(60).off();
                    int=setInterval(xunhuan,1000);
                    $.post("/phone",{phone:$("[name=phone]").val()},function (response) {
                        if(response!="gc"){
                            $(".sjyzm").html(response);
                            layer.msg("请等待"+response+"秒",{icon:4,anim:6})
                        }
                    });
                }else {
                    $("[name=phone]").addClass("layui-form-danger").focus();
                    layer.msg("请填入正确的手机格式", {
                        anim: 6
                        , time: 1500
                        , icon: 5
                    })
                }
            function xunhuan() {
                $(".sjyzm").html(function (index,value) {
                    if(value==1) {
                        window.clearInterval(int)
                        $(".sjyzm").removeClass("suoding").on("click",shoujiyz);
                        return "获取验证码";
                    }
                    return  parseInt(value)-1;
                });
            }
        }
        $(".sjyzm").on("click",shoujiyz)
        form.verify({
            passwordtong: function(value, item){
                if($("[name=password]").val()!=$("[name=passwordtwo]").val()){
                    return '密码不相同！！';
                }
            }
        });

        $("[type=reset]").click(function () {
            window.clearInterval(int);
            $(".sjyzm").removeClass("suoding").off().on("click",shoujiyz).html("获取验证码");
        });

    });
</script>
</html>
