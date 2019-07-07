<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

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
            <div><a class="navbar-brand" href="index.do" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>

<div class="container">

    <form id="loginForm" action="" method="POST" class="form-signin" role="form">
        ${exception.message }
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 忘记密码</h2>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="loginacct" name="loginacct" placeholder="请输入登录账号" autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="email" name="email" placeholder="请输入邮箱" autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="text" class="form-control" id="checkCode" name="checkCode" placeholder="请输入验证码"
                   style="width: 50%;float: left" autofocus>
            <img title="点击切换验证码" src="${pageContext.request.contextPath}/getCheckCodeImage.do" id="imgCode"
                 height="43px" width="145px" style="cursor: pointer" onclick="changeCode()">
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="sendEmail()"> 发送邮件</a>
    </form>
</div>
<script src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/layer/layer.js"></script>
<script>
    function sendEmail() {

        var loginacct = $("#loginacct").val();
        var email = $("#email").val();

        //对于表单数据而言不能用null进行判断,如果文本框什么都不输入,获取的值是""
        if ($.trim(loginacct) == "") {
            layer.msg("用户账号不能为空,请重新输入!", {time: 1000, icon: 5, shift: 6}, function () {
                $("#loginacct").focus();
                $("#loginacct").val("");
                //return false ;  //不能结束if,只是结束msg函数.
            });
            return false;
        }
        if ($.trim(email) == "") {
            layer.msg("用户密码不能为空,请重新输入!", {time: 1000, icon: 5, shift: 6}, function () {
                $("#userpswd").focus();
                $("#userpswd").val("");
                //return false ;  //不能结束if,只是结束msg函数.
            });
            return false;
        }

        var loadingIndex = -1;
        $.ajax({
            type: "POST",
            data: {
                loginacct: loginacct,
                email: email
            },
            url: "${pageContext.request.contextPath}/resetPasswordEmail.do",
            beforeSend: function () {
                loadingIndex = layer.msg('处理中', {icon: 16});
                //一般做表单数据校验.
                return true;
            },
            success: function (result) { //{"success":true}  或    {"success":false,"message":"登录失败!"}
                layer.close(loadingIndex);
                if (result.flag) {
                    layer.alert(result.message, function (index) {
                        // 回调方法
                        layer.close(index);
                        window.location.href = "${pageContext.request.contextPath}/login.do";
                    });

                } else {
                    layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
                }
            },
            error: function () {
                layer.msg("系统繁忙!", {time: 1000, icon: 5, shift: 6});
            }
        });
    }

    function changeCode() {
        //得到图片对象
        var image = document.getElementById("imgCode");
        image.src = "${pageContext.request.contextPath}/getCheckCodeImage.do?" + Math.random();
    }
</script>
</body>
</html>