<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="cn.systoon.qc.domain.ServiceList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-2.0.0.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery-ui.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/my.js"></script>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap-combined.min.css"
	media="screen" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/common.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>edit</title>
<script type="text/javascript">
	$(function(){
		$("#warname").change(function() {
			var serviceId = $(this).val();
			$("#ip").val("");
			$("#port").val("");
			if (serviceId != null) {
				
				var url = "<%= request.getContextPath()%>/performServlet?method=getServiceIp";
				var args = {
					"id" : serviceId
				};

				$.getJSON(url,args,function(data){
					if(data.prdomain != null){
						$("#ip").val(data.prdomain);
					}else{
						$("#ip").val(data.pressIp);
					}
					$("#port").val("8081");
				});
			}

		});
	})
</script>

</head>
<body>
	<%@include file="/html/header.jspf"%>
	<div id="container" class="container-fluid"
		style="padding: 0px 20px; margin: 0 auto">
		<div class="row-fluid">
			<div class="span12">
				<h3 class="text-center">创建编辑测试计划</h3>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span8">
				<form id="form" name="form1" action="" method="post"
					onsubmit="return submitPlan()">
					<div class="tabbable" id="tabs-789280">
						<ul class="nav nav-tabs">
							<li class="active"><a  data-toggle="tab" href="#panel-978253">配置接口请求</a></li>
							<li><a data-toggle="tab" href="#panel-653429">设置性能参数</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="panel-978253">
								<!-- <h4>配置接口请求</h4> -->
								<fieldset>
									<legend>Web Server:</legend>
									<table>
										<tr>
											<td>Project Name:</td>
											<td><select id="warname" name="warname">
													<option value="">请选择项目名称</option>
												    <% 			
															List<ServiceList> serviceLists = null;
															serviceLists = (List<ServiceList>) request.getAttribute("serviceLists");
															if (serviceLists != null && serviceLists.size() > 0) {
																for (ServiceList serviceList : serviceLists) {
																	if (StringUtils.isNotBlank(serviceList.getProjectCN())){
													%>
													<option value="<%=serviceList.getId()%>"><%=serviceList.getProjectCN()%></option>
													
													<%
																}
															}
														}

													%>
													
											</select></td>
										</tr>
										
										<tr>
											<td>Server Name or IP:</td>
											<td><input id="ip" type="text" name="ip" size="30"
												value="" /></td>
										</tr>

										<tr>
											<td>Port Number:</td>
											<td><input id="port" type="text" name="port" size="30"
												value="" /></td>
										</tr>

									</table>

								</fieldset>
								<br />
								<fieldset>
									<legend>HTTP Request:</legend>
									<table>
										<tr>
											<td>Path:</td>
											<td><input type="text" name="path" size="30" value="" /></td>
											<td><span class="help-block">输入接口请求路径</span></td>
										</tr>

										<tr>
											<td>Method:</td>
											<td><select name="requestmethod">
													<option value="get" selected="selected">GET</option>
													<option value="post">POST</option>
											</select></td>
										</tr>
									</table>
								</fieldset>
								<br/>
								<fieldset>
									<legend>Parameter:</legend>
									<!-- 参数块start -->
									<div class="container-fluid">
										<div class="row-fluid">
											<div class="span12">
												<div class="tabbable" id="tabs-52171">
													<ul class="nav nav-tabs">
														<li class="active">
															<a href="#panel-141708" data-toggle="tab">Parameters</a>
														</li>
														<li>
															<a href="#panel-974412" data-toggle="tab">BodyData</a>
														</li>
													</ul>
													<div class="tab-content">
														<div class="tab-pane active" id="panel-141708">
				
												   <!-- <table class="table table-bordered table-hover table-condensed"> -->
														<table class="table table-bordered">
															<thead>
																<tr>
																	<th>
																		name
																	</th>
																	<th>
																		value
																	</th>
																	<th>
																	
																	</th>
																</tr>
															</thead>
															<tbody>
															<!-- 		<tr class="success/error/warning/info"> -->
																<tr>
																	<td>
																		<input type="text" name="paramName" style="border: none;box-shadow:none" >
																	</td>
																	<td>
																		<input type="text" name="paramValue" style="border: none;box-shadow:none" >
																	</td>
																	<td>
																		<div class="span12">
																			 <button type="button" class="close" style="float:left;margin-left: 2px" >×</button>
																		</div>
																	</td>
																</tr>
																
															</tbody>
														</table>
														<div class="container-fluid">
															<span class="help-block">* 添加请求参数</span>
															<div class="row-fluid">
																<div class="span2">
																	 <button class="btn btn-block" type="button">添加</button>
																</div>
																
															</div>
														</div>
												
														</div>
														<div class="tab-pane" id="panel-974412">
															<div class="span12">
																<textarea class="textdefine" name="parameters"></textarea>
															</div>
															<span class="help-block">输入接口请求参数</span>
														</div>
														
														
													</div>
												</div>
											</div>
										</div>
									</div>
									
									<!-- 参数块end -->
								</fieldset>
								<br />
								<button type="submit" class="btn" onclick="testAPI()">测试接口</button>
								<br />
							</div>
							<div class="tab-pane" id="panel-653429">
								<fieldset>
									<legend>并发用户数</legend>
									<input type="radio" name="vuser" value="10"
										onclick="Change('0')" checked="checked" />10 <input
										type="radio" name="vuser" value="50" onclick="Change('0')" />50
									<input type="radio" name="vuser" value="100"
										onclick="Change('0')" />100 <input type="radio" name="vuser"
										value="200" onclick="Change('0')" />200 <input type="radio"
										name="vuser" value="500" onclick="Change('0')" />500 <input
										type="radio" name="vuser" value="1000" onclick="Change('0')" />1000
									<input id="define" type="radio" name="vuser"
										onclick="Change('1')" />自定义<input id="defineValue"
										type="hidden" name="vuser" size="30" onchange="assignValue()" />
								</fieldset>
								<br />
								<fieldset>
									<legend>持续执行时间（秒）</legend>
									<table>
										<tr>
											<td><input type="text" name="duration" size="30"
												value="300" /></td>
											<td><span class="help-block">默认执行5分钟</span></td>
										</tr>
									</table>
								</fieldset>

								<fieldset>
									<legend>响应断言</legend>
									<textarea class="textdefine" name="assertion"></textarea>
									<div class="alert">
										<button type="button" class="close" >×</button>
										<strong>提示</strong> 输入断言的参数结果，即：响应信息中只要包含所填写的参数文本信息，就表示成功。
									</div>
								</fieldset>
								<!-- <h4>保存文档</h4> -->
								<fieldset>
									<legend>保存测试计划</legend>
									<table>
										<tr>
											<td>测试计划名称：<input type="text" name="testplanname"
												size="30" value="" /></td>
										</tr>

										<tr>
											<td>测试计划描述：<input type="text" name="testplandesc"
												size="30" value="" /></td>
										</tr>

										<tr>
											<td><button type="submit" class="btn"
													onclick="savePlan()">保存</button></td>
										</tr>

									</table>
								</fieldset>
								<br />
								<div>
									<input type="hidden" value="" name="type" id="type" />
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="span4">
				<div class="tabbable" id="tabs-174529">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#panel-913764">操作日志</a>
						</li>
					</ul>
				</div>
				<div class="tab-content">
					<div class="tab-pane active" id="panel-913764">
						<fieldset>
							<legend>console</legend>
							<div id="console"></div>
						</fieldset>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>