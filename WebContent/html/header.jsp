<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../js/jquery-2.0.0.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<link href="../css/bootstrap-combined.min.css" rel="stylesheet" media="screen">
<link href="../css/common.css" rel="stylesheet" media="screen">
<title>Insert title here</title>
</head>
<body>
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<div class="navbar">
				<div class="navbar-inner">
					<div class="container-fluid">
						<a href="#" class="brand"><img src="<%= request.getContextPath() %>/img/syswin.png"></a>
						<div class="nav-collapse collapse navbar-responsive-collapse">
							<ul class="nav pull-right">
								<li><a href="<%= request.getContextPath() %>/index.jsp">主页</a></li>
								<li class="divider-vertical"></li>
								<li class="dropdown"><a data-toggle="dropdown"
									class="dropdown-toggle" href="#">快速导航<strong class="caret"></strong></a>
									<ul class="dropdown-menu">
										<li><a href="html/addTestPlan.jsp">创建测试计划</a></li>
										<li><a href="html/runTest.jsp">执行测试</a></li>
										<li><a href="html/historyResult.jsp">查看历史结果</a></li>
									</ul></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>