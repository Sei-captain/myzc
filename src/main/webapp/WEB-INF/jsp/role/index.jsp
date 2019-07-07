<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
			cursor:pointer;
		}
		table tbody tr:nth-child(odd){background:#F4F4F4;}
		table tbody td:nth-child(even){color:#C00;}
	</style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<div><a class="navbar-brand" style="font-size:32px;" href="${pageContext.request.contextPath}/role/index.do">众筹平台 - 角色维护</a></div>
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
								<div class="input-group-addon">名称查询</div>
								<input id="queryText" value="${queryText}" class="form-control has-success" type="text" placeholder="请输入查询条件">
							</div>
						</div>
						<button type="button" id="queryBtn" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
					</form>
					<button type="button" onclick="deleteRole()" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
					<button type="button" onclick="location.href='${pageContext.request.contextPath}/role/toAdd.do'" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增</button>
					<br>
					<hr style="clear:both;">
					<div class="table-responsive">
						<form id="roleForm" method="post">
						<table class="table  table-bordered">
							<thead>
							<tr >
								<th width="30">#</th>
								<th width="30"><input id="allCheckbox" type="checkbox"></th>
								<th>名称</th>
								<th width="100">操作</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach items="${pageInfo.list}" var="role" varStatus="n">
							<tr>
								<td>${n.count}</td>
								<td><input name="ids" value="${role.id}" type="checkbox"></td>
								<td>${role.name}</td>
								<td>
									<button type="button" onclick="location.href='${pageContext.request.contextPath}/role/toAssignPermission.do?id=${role.id}'"class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>
									<button type="button" onclick="location.href='${pageContext.request.contextPath}/role/toUpdateRole.do?id=${role.id}'" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>
									<button type="button" onclick="deleteRoleById(${role.id},'${role.name}')" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>
								</td>
							</tr>
							</c:forEach>
							</tbody>
							<tfoot>
							<tr >
								<td colspan="6" align="center">
									<ul class="pagination">
										<li><a href="${pageContext.request.contextPath}/role/index.do?pageNum=${pageInfo.pageNum-1}&pageSize=${pageInfo.pageSize}&queryText=${queryText}">上一页</a></li>

										<c:forEach begin="1" end="${pageInfo.pages}" var="i">
											<c:choose>
												<c:when test="${pageInfo.pageNum==i}">
													<li class="active"><a
															href="${pageContext.request.contextPath}/role/index.do?pageNum=${i}&pageSize=${pageInfo.pageSize}&queryText=${queryText}">${i} </a>
													</li>
												</c:when>
												<c:otherwise>
													<li>
														<a href="${pageContext.request.contextPath}/role/index.do?pageNum=${i}&pageSize=${pageInfo.pageSize}&queryText=${queryText}">${i} </a>
													</li>
												</c:otherwise>
											</c:choose>

										</c:forEach>
										<li><a href="${pageContext.request.contextPath}/role/index.do?pageNum=${pageInfo.pageNum+1}&pageSize=${pageInfo.pageSize}&queryText=${queryText}">下一页</a></li>
									</ul>
									总共${pageInfo.pages}页，共${pageInfo.total}条数据。每页
									<select id="pageSize" onchange="changePageSize()">
										<c:forEach begin="1" end="2" var="i">
											<c:if test="${pageInfo.pageSize==(5*i)}">
												<option selected>${5*i}</option>
											</c:if>
											<c:if test="${pageInfo.pageSize!=(5*i)}">
												<option>${5*i}</option>
											</c:if>
										</c:forEach>
									</select>条
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
		$(".list-group-item").click(function(){
			if ( $(this).find("ul") ) {
				$(this).toggleClass("tree-closed");
				if ( $(this).hasClass("tree-closed") ) {
					$("ul", this).hide("fast");
				} else {
					$("ul", this).show("fast");
				}
			}
		});
	});

	$("#allCheckbox").click(function () {
		var ids = document.getElementsByName("ids");
		for (var i=0;i<ids.length;i++){
			ids[i].checked=this.checked;
		}
	});

	function deleteRole() {
		var selectCheckbox = $("tbody tr td input:checked");
		if (selectCheckbox.length == 0) {
			layer.msg("至少选择一个角色进行删除!", {time: 1000, icon: 5, shift: 6});
			return false;
		}
		layer.confirm("确认要删除这些角色吗?", {icon: 3, title: '提示'}, function (cindex) {
			$.ajax({
				type:"post",
				url:"${pageContext.request.contextPath}/role/deleteRole.do",
				data:$("#roleForm").serialize(),
				success:function (result) {
					if (result.flag) {
						location.href="${pageContext.request.contextPath}/role/index.do"
					}else {
						layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
					}
				},
				error:function () {
					layer.msg("删除异常!", {time: 1000, icon: 5, shift: 6});
				}
			})

		});
	}
	function deleteRoleById(id,n) {
		layer.confirm("确认要删除["+n+"]角色吗?", {icon: 3, title: '提示'}, function (cindex) {
			$.ajax({
				type:"post",
				url:"${pageContext.request.contextPath}/role/deleteRole.do",
				data:{ids:id},
				success:function (result) {
					if (result.flag) {
						location.href="${pageContext.request.contextPath}/role/index.do"
					}else {
						layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
					}
				},
				error:function () {
					layer.msg("删除异常!", {time: 1000, icon: 5, shift: 6});
				}
			})

		});
	}

	$("#queryBtn").click(function () {
		var queryText = $("#queryText").val();
		location.href="${pageContext.request.contextPath}/role/index.do?queryText="+queryText;
	});

	function changePageSize() {
		var pageSize = $("#pageSize").val();
		location.href = "${pageContext.request.contextPath}/role/index.do?pageNum=1&pageSize=" + pageSize+"&queryText=${queryText}";
	}
</script>
</body>
</html>
