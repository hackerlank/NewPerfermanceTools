<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="../js/jquery-2.0.0.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/my.js"></script>
<link rel="stylesheet" href="../css/bootstrap-combined.min.css"
	media="screen" />
<link rel="stylesheet" href="../css/common.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>run</title>

</head>
<body>
<!-- 导航开始 -->
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
										<li><a href="<%= request.getContextPath() %>/html/edit.jsp">创建测试计划</a></li>
										<li><a href="<%= request.getContextPath() %>/html/run.jsp">执行测试</a></li>
										<li><a href="<%= request.getContextPath() %>/html/report.jsp">查看历史结果</a></li>
									</ul></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div> 
<!-- 导航end -->
</body>
</html>