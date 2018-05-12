<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="/static/layui/css/layui.css">
    <script src="/static/jquery-1.8.0.js"></script>
    <script src="/static/layui/layui.js"></script>
    <script src="/static/layui/moment.js"></script>
</head>
<style>
    .layui-table-body {overflow-x: hidden;}
</style>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">layui 后台布局</div>
        <!-- 头部区域（可配合layui已有的水平导航） -->
        <ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item"><a href="">控制台</a></li>
            <li class="layui-nav-item"><a href="">商品管理</a></li>
            <li class="layui-nav-item"><a href="">用户</a></li>
            <li class="layui-nav-item">
                <a href="javascript:;">其它系统</a>
                <dl class="layui-nav-child">
                    <dd><a href="">邮件管理</a></dd>
                    <dd><a href="">消息管理</a></dd>
                    <dd><a href="">授权管理</a></dd>
                </dl>
            </li>
        </ul>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <img src="http://t.cn/RCzsdCq" class="layui-nav-img">
                    贤心
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="">基本资料</a></dd>
                    <dd><a href="">安全设置</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="">退了</a></li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree"  lay-filter="test">
                <li class="layui-nav-item layui-nav-itemed">
                    <a class="" href="javascript:;">所有商品</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;">列表一</a></dd>
                        <dd><a href="javascript:;">列表二</a></dd>
                        <dd><a href="javascript:;">列表三</a></dd>
                        <dd><a href="">超链接</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item">
                    <a href="javascript:;">解决方案</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;">列表一</a></dd>
                        <dd><a href="javascript:;">列表二</a></dd>
                        <dd><a href="">超链接</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item"><a href="">云市场</a></li>
                <li class="layui-nav-item"><a href="">发布商品</a></li>
            </ul>
        </div>
    </div>

    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div style="padding: 15px;">



            <h1 style="text-align: center;padding: 20px;">hello world</h1>
            <div id="message">
                <div class="layui-form-item">
                    <label class="layui-form-label">名称搜索：</label>
                    <div class="layui-input-block">
                        <input type="text" name="user" required placeholder="请输入名称" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">开始时间：</label>
                    <div class="layui-input-block">
                        <input type="text" class="layui-input" id="test1" name="datetime">
                    </div>
                </div>
            </div>
            <div style="padding:10px;">
                <button id="reload" class="layui-btn"><i class="layui-icon">&#xe615;</i>搜索</button>
                <button id="add" class="layui-btn layui-btn-normal"><i class="layui-icon">&#xe654;</i>增加</button>
                <button id="console" class="layui-btn layui-btn-warm"><i class="layui-icon">&#xe640;</i>批量删除</button>
            </div>
            <table id="demo" lay-filter="test"></table>




        </div>
    </div>

    <div class="layui-footer">
        <!-- 底部固定区域 -->
        © layui.com - 底部固定区域
    </div>
</div>
</body>
<script>
    reloads = "";
    tables = "";
    layui.use('table', function(){
        //表格！！！！
        var table = layui.table;
        //表格第一个实例
        tables = table.render({
            elem: '#demo'
            ,url: '/tableData' //数据接口
            ,page:true
            ,limit:5
            ,id: 'testReload'
            ,limits:[5,8,11]
            ,cols: [[ //表头
                {field: 'check', title: 'check',type:'checkbox'},
                {field: 'id', title: 'ID'},
                {field: 'name', title: '名称'},
                {field: 'datetime', title: '创建时间',templet: function (row) {
                        return moment(row.datetime).format("YYYY-MM-DD")
                    }},
                {field: 'operation', title: '操作',toolbar:"#barDemo"}
            ]]
        });
        //表格重新加载方法
        reloads = function reload(page){
            //执行重载
            tables = table.reload('testReload', {
                page: {
                    curr: page //重新从第 1 页开始
                },
                where: { //设定异步数据接口的额外参数，任意设
                    name:$("#message [name=user]").val(),
                    datetimeqvjian:$("#message [name=datetime]").val()
                }
            });
        }
        //点击刷新事件
        $('#reload').click(function () {
            table.reload('testReload', {
                url: '/search',
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: { //设定异步数据接口的额外参数，任意设
                    name:$("#message [name=user]").val(),
                    datetimeqvjian:$("#message [name=datetime]").val()
                }
            });
        })
        //修改和删除
        table.on('tool(test)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
                layer.confirm('真的删除行么', function(index){
                    $.post("/delete",{id:data.id},function (response) {
                        layer.msg("删除成功！");
                        tables.reload();
                    });
                    layer.close(index);
                });
            } else{
                $.post("/edit",{id:data.id},function (response) {
                    layer.open({
                        title:"修改",
                        type:1,
                        content:response,
                        offset: '10px',
                        resize:false,
                        move: false,
                        area: ['360px', '320px']
                    })
                });
            }
        });
        //批量删除事件
        $("#console").click(function () {
            var checkStatus = table.checkStatus('testReload');
            var arr = [];
            $.each(checkStatus.data,function (i,k) {
                arr.push(k.id);
            });
            if(arr.length==0){
                layer.msg('你删除个鸡毛！！！');
            }else {
                layer.confirm('真的删除行么', function(index){
                    $.ajax({
                        url:"/deleteAll",
                        type:"post",
                        data:{"array":arr},
                        traditional:true,
                        dataType:"text",
                        success:function(response){
                            layer.msg("删除成功！");
                            tables.reload();
                        },
                        error:function(){
                            alert("失败！");
                        }
                    });
                })
            }

        })
    });
    //日期选择的！！
    layui.use('laydate', function(){
        var laydate = layui.laydate;
        //执行一个laydate实例
        laydate.render({
            elem: '#test1' //指定元素
            ,range:true
        });
    });
    //基本的
    layui.use(['layer', 'form'], function(){
        var layer = layui.layer,form = layui.form;
        $("#add").click(function () {
            $.get('/add',function(str){
                layer.open({
                    title:"新增",
                    type:1,
                    content:str,
                    offset: '10px',
                    resize:false,
                    move: false,
                    area: ['360px', '250px']
                })
            });
        })
    });
    //JavaScript代码区域
    layui.use('element', function(){
        var element = layui.element;
    });
</script>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
</html>
