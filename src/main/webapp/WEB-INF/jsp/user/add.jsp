<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/doc.min.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor: pointer;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;"
                    href="${pageContext.request.contextPath}/user/index.do">众筹平台 - 用户维护</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li style="padding-top:8px;">
                    <%@include file="/WEB-INF/jsp/common/userinfo.jsp" %>
                </li>
                <li style="margin-left:10px;padding-top:8px;">
                    <button type="button" class="btn btn-default btn-danger">
                        <span class="glyphicon glyphicon-question-sign"></span> 帮助
                    </button>
                </li>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <div class="tree">
                <%@include file="/WEB-INF/jsp/common/menu.jsp" %>
            </div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="${pageContext.request.contextPath}/main.do">首页</a></li>
                <li><a href="${pageContext.request.contextPath}/user/index.do">数据列表</a></li>
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">新增用户表单
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <form id="addForm">
                        <div class="form-group">
                            <label for="floginacct">登陆账号</label>
                            <input type="text" class="form-control" id="floginacct" name="loginacct" placeholder="请输入登陆账号">
                        </div>
                        <div class="form-group">
                            <label for="fuserpswd">密码</label>
                            <input type="text" class="form-control" id="fuserpswd" name="userpswd" placeholder="请输入登录密码">
                        </div>
                        <div class="form-group">
                            <label for="fusername">用户名称</label>
                            <input type="text" class="form-control" id="fusername" name="username" placeholder="请输入用户名称">
                        </div>
                        <div class="form-group">
                            <label for="femail">邮箱地址</label>
                            <input type="email" class="form-control" id="femail" name="email" placeholder="请输入邮箱地址">
                        </div>
                        <button id="addBtn" type="button" class="btn btn-success"><i
                                class="glyphicon glyphicon-plus"></i> 新增
                        </button>
                        <button id="resetBtn" type="button" class="btn btn-danger"><i
                                class="glyphicon glyphicon-refresh"></i> 重置
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">帮助</h4>
            </div>
            <div class="modal-body">
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题1</h4>
                    <p>测试内容1，测试内容1，测试内容1，测试内容1，测试内容1，测试内容1</p>
                </div>
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题2</h4>
                    <p>测试内容2，测试内容2，测试内容2，测试内容2，测试内容2，测试内容2</p>
                </div>
            </div>
            <!--
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary">Save changes</button>
            </div>
            -->
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/script/docs.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/layer/layer.js"></script>

<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function () {
            if ($(this).find("ul")) {
                $(this).toggleClass("tree-closed");
                if ($(this).hasClass("tree-closed")) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
    });


    $("#addBtn").click(function () {

        var floginacct = $("#floginacct").val();
        var fuserpswd = $("#fuserpswd").val();
        var fusername = $("#fusername").val();
        var femail = $("#femail").val();
        if ($.trim(floginacct) == "") {
            layer.msg("用户账号不能为空,请重新输入!", {time: 1000, icon: 5, shift: 6}, function () {
                floginacct.val("");
                floginacct.focus();
            });
            return false;
        }
        if ($.trim(fuserpswd) == "") {
            layer.msg("密码不能为空,请重新输入!", {time: 1000, icon: 5, shift: 6}, function () {
                fuserpswd.val("");
                fuserpswd.focus();
            });
            return false;
        }
        if ($.trim(fusername) == "") {
            layer.msg("用户名不能为空,请重新输入!", {time: 1000, icon: 5, shift: 6}, function () {
                fusername.val("");
                fusername.focus();
            });
            return false;
        }
        if ($.trim(femail) == "") {
            layer.msg("邮箱不能为空,请重新输入!", {time: 1000, icon: 5, shift: 6}, function () {
                femail.val("");
                femail.focus();
            });
            return false;
        }


        $.ajax({
            type: "POST",
            data: {loginacct:floginacct,
                userpswd:fuserpswd,
                username:fusername,
                email:femail
            },
            url: "${pageContext.request.contextPath}/user/doAdd.do",
            beforeSend: function () {
                return true;
            },
            success: function (result) {
                if (result.flag) {
                    window.location.href = "${pageContext.request.contextPath}/user/index.do";
                } else {
                    layer.msg("保存用户失败", {time: 1000, icon: 5, shift: 6});
                }
            },
            error: function () {
                layer.msg("保存失败", {time: 1000, icon: 5, shift: 6});
            }
        });


    });


    $("#resetBtn").click(function () {
        $("#addForm")[0].reset();
    });


</script>
</body>
</html>
    