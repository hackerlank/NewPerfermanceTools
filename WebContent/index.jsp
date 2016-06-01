<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery-2.0.0.min.js"></script>
<script type="text/javascript" src="js/jquery-ui"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<link href="css/bootstrap-combined.min.css" rel="stylesheet" media="screen">
<link href="css/common.css" rel="stylesheet" media="screen">
<title>Insert title here</title>
<style>
	.container{
		max-width: 80%;
	}
</style>
<script type="text/javascript">
	$(function(){
		$("#editbtn").hover(function(){
			$("#run").css("display","none");
			$("#report").css("display","none");
			$("#edit").css("display","block");
		 });
		
		$("#runbtn").hover(function(){
			$("#edit").css("display","none");
			$("#report").css("display","none");
			$("#run").css("display","block");
		 });
		
		$("#reportbtn").hover(function(){
			$("#run").css("display","none");
			$("#edit").css("display","none");
			$("#report").css("display","block");
		 });
	});
</script>
</head>
<body>
<div id="container" class="container-fluid" style="padding: 0px 20px; margin: 0 auto">
<jsp:include page="html/header.jsp"></jsp:include>
	<div class="row-fluid">
		<div class="span12">
			<div class="container-fluid">
				<div class="row">
					<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
						<h1 class="page-header">
							性能自动化测试工具
						</h1>
						<div class="row placeholders">
							<div id="editbtn" class="col-xs-6 col-sm-3 placeholder">
								<a href="html/addTestPlan.jsp"><img alt="200x200" class="img-responsive" src="svg/edit.svg" /></a>
								<h4>
									编辑测试计划
								</h4> <span class="text-muted">创建、修改测试计划</span>
							</div>
							<div id="runbtn"class="col-xs-6 col-sm-3 placeholder">
								<a href="html/runTest.jsp"><img alt="200x200" class="img-responsive" src="svg/run.svg" /></a>
								<h4>
									执行测试
								</h4> <span class="text-muted">执行已有测试计划</span>
							</div>
							<div id="reportbtn"class="col-xs-6 col-sm-3 placeholder">
								<a href="html/historyResult.jsp"><img alt="200x200" class="img-responsive" src="svg/report.svg" /></a>
								<h4>
									查看测试结果
								</h4><span class="text-muted">查看性能测试历史结果</span>
							</div>
							<div class="col-xs-6 col-sm-3 placeholder">
							</div>
						</div>
					</div>
				</div>
			</div>

			<div id="edit" class="hero-unit" style="display: block">
				<h1>
					创建计划
				</h1><br>
				<p>
					通过该模块，创建待测接口的性能测试计划。根据页面提示，填写指定内容，后台将自动生成用于jmeter执行的测试计划。
				</p>
				<p>
					测试计划可在该平台直接执行测试，也可下载后，通过本地jmeter工具执行。
				</p>
			</div>
			
			<div id="run" class="hero-unit" style="display: none">
				<h1>
					执行测试
				</h1><br>
				<p>
					选择项目，指定该项目中已有的测试计划，执行性能测试，得到测试结果。
				</p>
				<p>
					适用场景，对于已生成的测试计划，修改代码后，多次相同的测试计划，比较测试结果，衡量代码修改的效率。
				</p>
			</div>
			
			<div id="report" class="hero-unit" style="display: none">
				<h1>
					查看历史测试结果
				</h1><br>
				<p>
					在该模块中可查看在该平台执行过的所有测试计划生成的测试结果。
				</p>
			</div>

		</div>
	</div>


</div>
</body>
</html>