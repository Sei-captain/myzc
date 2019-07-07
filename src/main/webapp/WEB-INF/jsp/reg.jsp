<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="GB18030">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <style>

    </style>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="index.htm" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>

<div class="container">

    <form class="form-signin" role="form">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户注册</h2>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="loginacct" placeholder="请输入登录账号" autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="userpswd" placeholder="请输入登录密码" style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="username" placeholder="请输入用户名" style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="email" placeholder="请输入邮箱地址" style="margin-top:10px;">
            <span class="glyphicon glyphicon glyphicon-envelope form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <select class="form-control" >
                <option>企业</option>
                <option>个人</option>
            </select>
        </div>
        <div class="checkbox">
            <label>
                忘记密码
            </label>
            <label style="float:right">
                <a href="login.htm">我有账号</a>
            </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" id="regBtn" > 注册</a>
        <a class="btn btn-lg  btn-block" onclick="location.reload()" > 重置</a>
    </form>
</div>
<script src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/jquery/layer/layer.js"></script>
</body>
<script>
    $(function () {
       $("#loginacct").blur(function () {
           $.ajax({
               url:"${pageContext.request.contextPath}/checkAccount.do",
               type:"post",
               data:{
                   loginacct:$("#loginacct").val()
               },
               success:function (data) {
                   if (data.success){
                       layer.msg(data.message, {time:1000, icon:5, shift:6});
                   }else {
                       layer.msg(data.message, {time:1000, icon:6, shift:5});
                   }
               }
           })
       })
    });

    $("#regBtn").click(function () {
        $.ajax({
            url:"${pageContext.request.contextPath}/doReg.do",
            type:"post",
            data:{
                loginacct:$("#loginacct").val(),
                userpswd:$("#username").val(),
                username:$("#userpswd").val(),
                email:$("#email").val()
            },
            success:function (data) {
                if (data.success) {
                    layer.msg(data.message, {time:1500, icon:6, shift:5});
                    location.href="${pageContext.request.contextPath}/index.htm"
                }else {
                    layer.msg(data.message, {time:1000, icon:5, shift:6});
                }
            }
        });
    });
</script>
</html>