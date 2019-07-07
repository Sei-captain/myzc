<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <style>
        .tree li {
            list-style-type: none;
            cursor: pointer;
        }

        table tbody tr:nth-child(odd) {
            background: #F4F4F4;
        }

        table tbody td:nth-child(even) {
            color: #C00;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 用户维护</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li style="padding-top:8px;">
                    <jsp:include page="/WEB-INF/jsp/common/userinfo.jsp"></jsp:include>
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
                <jsp:include page="/WEB-INF/jsp/common/menu.jsp"></jsp:include>
            </div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">账号查询</div>
                                <input type="text" id="queryText" value="${queryText}" class="form-control has-success"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" id="queryBtn" class="btn btn-warning"><i
                                class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"
                            onclick="deleteUser()"><i class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button type="button" class="btn btn-primary" style="float:right;"
                            onclick="location.href='${pageContext.request.contextPath}/user/toAdd.do'"><i
                            class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <form id="userForm" method="post">
                            <table class="table  table-bordered">
                                <thead>
                                <tr>
                                    <th width="30">#</th>
                                    <th width="30"><input id="allCheckbox" type="checkbox"></th>
                                    <th>账号</th>
                                    <th>名称</th>
                                    <th>邮箱地址</th>
                                    <th>创建时间</th>
                                    <th width="100">操作</th>
                                </tr>
                                </thead>
                                <tbody id="tbody">


                                </tbody>
                                <tfoot>
                                <tr>
                                    <td colspan="7" align="center">
                                        <ul class="pagination">

                                        </ul>
                                        <span id="span"></span>
                                    </td>
                                </tr>

                                </tfoot>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/jquery/layer/layer.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/script/docs.min.js"></script>
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
        queryPage(1);
    });

    $("#allCheckbox").click(function () {
        var flag = this.checked;
        $("#tbody :checkbox").each(function () {
            this.checked=flag;
        });
       /* var ids = document.getElementsByName("ids");
        for (var i = 0; i < ids.length; i++) {
            ids[i].checked = this.checked;
        }*/
    });

    var queryFlag = false;
    $("#queryBtn").click(function () {
        var queryText = $("#queryText").val();
        var pageSize = $("#pageSize").val();
        if ($.trim(queryText)==""){
            queryFlag=false;
        }else {
            queryFlag=true;
        }
        queryPage(1,pageSize);
    });

    var loadingIndex = null;

    function queryPage(pageNum,pageSize) {
        jsonData={ pageNum: pageNum, pageSize: pageSize};
        if (queryFlag) {
            jsonData.queryText=$("#queryText").val();
        }

        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/user/queryPage.do",
            data:jsonData,
            beforeSend: function () {
                loadingIndex = layer.msg('处理中', {icon: 16});
            },
            success: function (result) {
                layer.close(loadingIndex);
                if (result.flag) {
                    var contentTbody = "";
                    var contentPagination = "";
                    var span="";
                    var pageInfo = result.pageInfo;
                    var users = pageInfo.list;

                    $.each(users, function (i, user) {
                        contentTbody += '<tr>';
                        contentTbody += '    <td>' + (i + 1) + '</td>';
                        contentTbody += '    <td><input type="checkbox" name="ids" ></td>';
                        contentTbody += '        <td>' + user.loginacct + '</td>';
                        contentTbody += '        <td>' + user.username + '</td>';
                        contentTbody += '        <td>' + user.email + '</td>';
                        contentTbody += '        <td>' + user.createtime + '</td>';
                        contentTbody += '        <td>';
                        contentTbody += '    <button type="button" onclick="location.href=\'${pageContext.request.contextPath}/user/assignRole.do?id=' + user.id + '\'" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';
                        contentTbody += '    <button type="button" onclick="location.href=\'${pageContext.request.contextPath}/user/toUpdateUser.do?id=' + user.id + '\'" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';
                        contentTbody += '    <button type="button" onclick="deleteUserById(' + user.id + ',\'' + user.username + '\')" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>';
                        contentTbody += '    </td>';
                        contentTbody += '</tr>';
                    });


                    contentPagination += '<li><a href="#" onclick="queryPage(' + (pageInfo.pageNum - 1) + ','+pageInfo.pageSize+')">上一页</a></li>';
                    for (var i = 1; i <= pageInfo.pages; i++) {
                        if (pageInfo.pageNum == i) {
                            contentPagination += '<li class="active"><a href="#" onclick="queryPage(' + i + ','+pageInfo.pageSize+')">' + i + ' </a></li>';
                        }
                        if (pageInfo.pageNum != i) {

                            contentPagination += '<li><a href="#" onclick="queryPage(' + i + ','+pageInfo.pageSize+')">' + i + ' </a></li>';
                        }
                    }
                    contentPagination += '<li><a href="#" onclick="queryPage(' + (pageInfo.pageNum + 1) + ','+pageInfo.pageSize+')">下一页</a></li>';

                    span += '总共'+pageInfo.pages+'页，共'+pageInfo.total+'条数据。每页';
                    span += '<select id="pageSize" onchange="changePageSize()">';
                    for (var i=1;i<=3;i++){
                        if (pageInfo.pageSize==5*i){
                            span += '<option selected>'+5*i+'</option>';
                        } else {
                            span +='<option>'+5*i+'</option>';
                        }
                    }
                    span += '</select>条';
                    
                    $("#tbody").html(contentTbody);
                    $(".pagination").html(contentPagination);
                    $("#span").html(span);
                } else {
                    layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
                }

            }
        });
    }

    function deleteUser() {
        var selectCheckbox = $("tbody tr td input:checked");
        if (selectCheckbox.length == 0) {
            layer.msg("至少选择一个用户进行删除!请选择用户!", {time: 1000, icon: 5, shift: 6});
            return false;
        }
        layer.confirm("确认要删除这些用户吗?", {icon: 3, title: '提示'}, function (cindex) {
            $.ajax({
                type: "post",
                url: "${pageContext.request.contextPath}/user/deleteUser.do",
                data: $("#userForm").serialize(),
                success: function (result) {
                    if (result.flag) {
                        location.href = "${pageContext.request.contextPath}/user/index.do"
                    } else {
                        layer.msg("删除失败!", {time: 1000, icon: 5, shift: 6});
                    }
                },
                error: function () {
                    layer.msg("删除异常!", {time: 1000, icon: 5, shift: 6});
                }
            })

        });
    }

    function deleteUserById(id, username) {
        layer.confirm("确认要删除[" + username + "]用户吗?", {icon: 3, title: '提示'}, function (cindex) {
            $.ajax({
                type: "post",
                url: "${pageContext.request.contextPath}/user/deleteUser.do",
                data: {ids: id},
                success: function (result) {
                    if (result.flag) {
                        location.href = "${pageContext.request.contextPath}/user/index.do"
                    } else {
                        layer.msg("删除失败!", {time: 1000, icon: 5, shift: 6});
                    }
                },
                error: function () {
                    layer.msg("删除异常!", {time: 1000, icon: 5, shift: 6});
                }
            })

        });
    }

    function changePageSize() {
        var pageSize = $("#pageSize").val();
        queryPage(1,pageSize);
    }

</script>
</body>
</html>

