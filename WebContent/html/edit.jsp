<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="cn.systoon.qc.domain.ServiceList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript"
	src="<%=application.getContextPath()%>/js/jquery-2.0.0.min.js"></script>
<script type="text/javascript"
	src="<%=application.getContextPath()%>/js/jquery-ui.js"></script>
<script type="text/javascript"
	src="<%=application.getContextPath()%>/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="<%=application.getContextPath()%>/js/edit.js"></script>
<link rel="stylesheet"
	href="<%=application.getContextPath()%>/css/bootstrap-combined.min.css"
	media="screen" />
<link rel="stylesheet"
	href="<%=application.getContextPath()%>/css/common.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>edit</title>
<script type="text/javascript">
	
</script>

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
				<h3 class="text-center">创建编辑测试计划</h3>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span8">
				<form id="form" name="form1" action="" method="get"
					onsubmit="return submitPlan()">
					<div class="tabbable" id="tabs-789280">
						<ul class="nav nav-tabs">
							<li class="active"><a data-toggle="tab" href="#panel-978253">配置接口请求</a></li>
							<li><a data-toggle="tab" href="#panel-653429">设置性能参数</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="panel-978253">
								<!-- <h4>配置接口请求</h4> -->
								<fieldset>
									<legend>Web Server:</legend>
									<table>
										<tr>
											<td>Environment:</td>
											<td><select id="environment" name="environment">
													<option value="1">开发环境</option>
													<option value="2">测试环境</option>
													<option value="3">预生产环境</option>
											</select></td>
										</tr>

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
												<%-- 
												使用标签库需导入 jstl.jar和standerd.jar
												<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
												
												<c:forEach items="${serviceList }" var="location">
    												<option value="${serviceList.id}">${serviceList.projectCN }</option>
    											</c:forEach> --%>

											</select></td>
										</tr>

										<tr>
											<td>Server Name or IP:</td>
											<td><input id="ip" type="text" name="ip" size="30"
												value="" /></td>
											<td id="domainInfo" style="color: red; display: none">域名访问时，请在hosts中绑定不同环境的IP地址</td>
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
											<td><select id="path" name="path" style="width: 250px">
													<option value="" ">请选择接口地址</option>
											</select></td>
										</tr>

										<tr>
											<td>Method:</td>
											<td><select id="requestmethod" name="requestMethod" style="width: 100px">
													<option value="get">GET</option>
													<option value="post" selected="selected">POST</option>
											</select></td>
										</tr>
										
			<!-- 							<tr>
											<td>Http Header:</td>
										</tr>
										<tr>
											<table id="headerTable"
												class="table table-bordered table-condensed">
												<thead>
													<tr>
														<th width="45%">name</th>
														<th width="45%">value</th>
														<th width="10%">
															<div class="span12">
																<button id="addHeaderBtn" type="button"
																	style="float: none; opacity: 0.6; font-weight: normal;">add</button>
															</div>
														</th>
													</tr>
												</thead>
												<tbody id="headertbody">
															<tr class="success/error/warning/info">

												</tbody>
											</table>
										</tr>
						 -->			</table>
								</fieldset>
								<br />
								<fieldset>
									<legend>Parameter:</legend>
									<!-- 参数块start -->
									<div class="container-fluid">
										<div class="row-fluid">
											<div class="span12">
												<div class="tabbable" id="tabs-52171">
													<ul class="nav nav-tabs">
														<li class="active"><a href="#panel-141708"
															data-toggle="tab" onclick="setParamType(1)">Parameters</a>
														</li>
														<li><a href="#panel-974412" data-toggle="tab"
															onclick="setParamType(2)">BodyData</a>
														</li>
													</ul>
													<div class="tab-content">
														<div class="tab-pane active" id="panel-141708">

															<!-- <table class="table table-bordered table-hover table-condensed"> -->
															<table id="paramTable"
																class="table table-bordered table-condensed">
																<thead>
																	<tr>
																		<th width="45%">name</th>
																		<th width="45%">value</th>
																		<th width="10%">
																			<div class="span12">
																				<button id="addParamBtn" type="button"
																					style="float: none; opacity: 0.6; font-weight: normal;">add</button>
																			</div>
																		</th>
																	</tr>
																</thead>
																<tbody id="paramtbody">
																	<!-- 		<tr class="success/error/warning/info"> -->

																</tbody>
															</table>
															<span class="help-block">* 添加请求参数</span>

														</div>
														<div class="tab-pane" id="panel-974412">
															<div class="span12">
																<textarea class="textdefine" name="parameters" id="bodyData"
																	style="height: 100px"></textarea>
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
										onclick="Change('0')" checked="checked" /> 10 <input
										type="radio" name="vuser" value="50" onclick="Change('0')" /> 50
									<input type="radio" name="vuser" value="100"
										onclick="Change('0')" /> 100 <input type="radio" name="vuser"
										value="200" onclick="Change('0')" /> 200 <input type="radio"
										name="vuser" value="500" onclick="Change('0')" /> 500 <input
										type="radio" name="vuser" value="1000" onclick="Change('0')" /> 1000
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
									<h4>要测试的响应字段</h4>
									<input type="radio" name="testFiled" value="Assertion.response_data" checked="checked"/> 响应文本 
									<input type="radio" name="testFiled" value="Assertion.response_data_as_document"/> Document(text) 
									<input type="radio" name="testFiled" value="Assertion.sample_label"/> URL样本 
									<input type="radio" name="testFiled" value="Assertion.response_code"/> 响应代码 
									<input type="radio" name="testFiled" value="Assertion.response_message"/> 响应信息
									<input type="radio" name="testFiled" value="Assertion.response_headers"/> Response Headers <br>
									<!-- <input type="checkbox" name="assume_success"/ value="1"> Ignore Status  -->
									
									<h4>模式匹配规则</h4>
									<input type="radio" name="testType" value="2" checked="checked"/> 包括 
									<input type="radio" name="testType" value="1"/> 匹配
									<input type="radio" name="testType" value="8"/> Equals
									<input type="radio" name="testType" value="16"/> SubString 
									
									<!-- <input type="checkbox" name="testType"/ value="20"> 否  -->
									<h4>断言内容</h4>
									<textarea class="textdefine" name="assertion"></textarea>
								
								</fieldset>
								<!-- <h4>保存文档</h4> -->
								<fieldset>
									<legend>保存测试计划</legend>
									<table>
										<tr>
											<td>测试计划名称：<input type="text" name="testPlanName"
												size="30" value="" /></td>
										</tr>

										<tr>
											<td>测试计划描述：<input type="text" name="testPlanDesc"
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
									<input type="hidden" value="" name="pathText" id="pathText" /> 
									<input type="hidden" value="1" name="paramType" id="paramType" /> 
									<input type="hidden" value="0" name="paramCount" id="paramCount" />
									<input type="hidden" value="0" name="headerCount" id="headerCount" />
									<input type="hidden" value="1" name="jmxPlanType" id="jmxPlanType" />
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="span4">
				<div class="tabbable" id="tabs-174529">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#panel-913764">操作日志</a></li>
					</ul>
				</div>
				<div class="tab-content">
					<div class="tab-pane active" id="panel-913764">
						<fieldset>
							<legend>console</legend>
							<div id="console_edit"></div>
						</fieldset>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>