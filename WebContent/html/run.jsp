<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="cn.systoon.qc.domain.ServiceList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="../js/jquery-2.0.0.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js"></script>
<script type="text/javascript" src="../js/run.js"></script>
<link rel="stylesheet" href="../css/bootstrap-combined.min.css"
	media="screen" />
<link rel="stylesheet" href="../css/common.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>run</title>

</head>
<body>

	<%
		List<ServiceList> serviceLists = null;
		serviceLists = (List<ServiceList>) application.getAttribute("serviceLists");
	%>
	<%@include file="/html/header.jspf"%>
	<div id="container" class="container-fluid"
		style="padding: 0px 20px; margin: 0 auto">
		<div class="row-fluid">
			<div class="span12">
				<h3 class="text-center">执行压力测试</h3>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<form id="form" name="form1" action="" method="get"
					onsubmit="retur runTest()">
					<div class="tab-content">
						<div class="tab-pane active" id="panel-978253">
							<fieldset>
								<legend>选择测试计划:</legend>
								<table >
									<tr>
										<td>Project Name:</td>
										<td><select id="project" name="project">
												<option value="0" selected="selected">请选择项目名称</option>
												<%
													if (serviceLists != null && serviceLists.size() > 0) {
														for (ServiceList serviceList : serviceLists) {
															if (StringUtils.isNotBlank(serviceList.getProjectCN())) {
												%>
												<option value="<%=serviceList.getId()%>"><%=serviceList.getProjectCN()%></option>

												<%
													}
														}
													}
												%>

										</select></td>
						
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Path:</td>
										<td><select id="path" name="path" style="width: 250px">
												<option value="">请选择接口地址</option>
										</select></td>
							
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Test Plan:</td>
										<td><select id="plan" name="plan" style="width: 250px">
												<option value="">请选择测试计划</option>
										</select></td>
									</tr>
								</table>
							</fieldset>
							<input type="submit" class="btn" value="执行测试"></input> <br />
						</div>
					</div>
				</form>
				<fieldset>
					<legend>console</legend>
					<div id="console_run"></div>
				</fieldset>
			</div>
		</div>
	</div>
</body>
</html>