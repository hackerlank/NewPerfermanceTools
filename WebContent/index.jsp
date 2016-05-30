<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<html>
<head>
<script type="text/javascript"
	src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/jquery-2.0.0.min.js"></script>
<script type="text/javascript"
	src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/jquery-ui"></script>
<link
	href="http://www.francescomalagrino.com/BootstrapPageGenerator/3/css/bootstrap-combined.min.css"
	rel="stylesheet" media="screen">
<link rel="stylesheet" href="css/common.css">
<script type="text/javascript"
	src="http://www.francescomalagrino.com/BootstrapPageGenerator/3/js/bootstrap.min.js"></script>
</head>
<title>PerformanceTestPlatform</title>
<script type="text/javascript">
	function Change(obj) {
		if (obj == 1) {
			document.getElementById("defineValue").value = "";
			document.getElementById("defineValue").type = "text";
		} else {
			document.getElementById("defineValue").type = "hidden";
		}
	}

	function assignValue() {
		var value = document.getElementById("defineValue").value;
		document.getElementById("define").value = value;

	}

	function checkEmpty() {

		if (document.getElementsByName("ip")[0].value == "") {
			alert("请输入服务域名或ip地址。");
			document.form1.ip.focus();
			return false;
		}
		/*		if (document.getElementsByName("port")[0].value == "") {
		 alert("请输入端口号。");
		 document.form1.port.focus();
		 return false;
		 }
		 if (document.getElementsByName("path")[0].value == "") {
		 alert("请输入请求接口的路径。");
		 document.form1.path.focus();
		 return false;
		 }
		 if (document.getElementsByName("parameters")[0].value == "") {
		 alert("请输入请求参数parameters。");
		 document.form1.parameters.focus();
		 return false;
		 } */
		/* document.form1.action = "performServlet?method=save"; */
		return true;

	}

	function removeChildren(pnode) {
		var childs = pnode.childNodes;
		for (var i = childs.length - 1; i >= 0; i--) {
			pnode.removeChild(childs.item(i));
		}
	}

	function testAPI() {
		var logNode = document.getElementById("console");
		removeChildren(logNode);
		document.getElementById("type").value = "test";
	}
	function savePlan() {
		var logNode = document.getElementById("console");
		removeChildren(logNode);
		document.getElementById("type").value = "save";
	}

	function submitPlan() {
		var url;
		if (document.getElementById("type").value == "save") {
			url = "performServlet?method=save";
		} else if (document.getElementById("type").value == "test") {
			url = "performServlet?method=test";
		}
		if (checkEmpty()) {
			$.ajax({
				type : "post",
				url : url,
				data : $("#form").serialize(),
				success : function(msg) {
					/* 		var text=$("<p></p>").text(msg);
								$("#console").append(text);  */
					$("#console").html(msg);
				},
				error : function(xhr) {
					alert("错误提示： " + xhr.status + " " + xhr.statusText);
				}

			});
		}
		return false;
	}
</script>
<body>
	<div class="container-fluid" style="padding: 0px 20px;margin-left: 40px;margin-right: 40px;">
		<div class="row-fluid">
			<div class="span12">
				<h3 class="text-center">性能自动化测试工具</h3>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span8">
				<div class="tabbable" id="tabs-789280">
					<ul class="nav nav-tabs">
						<li><a data-toggle="tab" href="#panel-978253">配置接口请求</a></li>
						<li class="active"><a data-toggle="tab" href="#panel-653429">设置性能参数</a>
						</li>
					</ul>
					<div class="tab-content">
						<div class="tab-pane" id="panel-978253">
							<form id="form" name="form1" action="" method="post"
								onsubmit="return submitPlan()">
								<!-- <h4>配置接口请求</h4> -->
								<fieldset>
									<legend>Web Server:</legend>
									<table>
										<tr>
											<td>Project Name:</td>
											<td><select name="warname">
													<option value="">请选择项目名称</option>
													<option value="businessAPi">businessAPi</option>
													<option value="Author">Author</option>

											</select></td>
										</tr>

										<tr>
											<td>Server Name or IP:</td>
											<td><input type="text" name="ip" size="30" value=""></td>
											<td><span class="help-block">输入请求域名和ip地址.</span></td>
										</tr>

										<tr>
											<td>Port Number:</td>
											<td><input type="text" name="port" size="30" value=""></td>
											<td><span class="help-block">输入请求端口号，默认为8081</span></td>
										</tr>

									</table>

								</fieldset>
								<br>

									<fieldset>
										<legend>HTTP Request:</legend>
										<table>
											<tr>
												<td>Path:</td>
												<td><input type="text" name="path" size="30" value=""></td>
												<td><span class="help-block">输入接口请求路径</span></td>
											</tr>

											<tr>
												<td>Method:</td>
												<td><select name="requestmethod">
														<option value="get" selected="selected">GET</option>
														<option value="post">POST</option>
												</select></td>
											</tr>
											<br>
										</table>
										Parameters:<br> <textarea name="parameters" value=""></textarea>
											<span class="help-block">输入接口请求参数</span>
									</fieldset> <br>
										<button type="submit" class="btn" onclick="testAPI()">测试接口</button>

										<br>
						</div>
						<div class="tab-pane active" id="panel-653429">
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
									type="hidden" name="vuser" size="30" onchange="assignValue()">
							</fieldset>
							<br>

								<fieldset>
									<legend>持续执行时间（秒）</legend>
									<table>
										<tr>
											<td><input type="text" name="duration" size="30"
												value=""></td>
											<td><span class="help-block">输入持续执行时间(秒)</span></td>
										</tr>

									</table>

								</fieldset> <br>

									<fieldset>
										<legend>响应断言</legend>
										<textarea name="assertion"></textarea>
										<span class="help-block">
											输入断言的参数结果，即：响应信息中只要包含所填写的参数文本信息，就表示成功。</span>
									</fieldset> <br /> <br /> <!-- <h4>保存文档</h4> -->
									<fieldset>
										<legend>保存测试计划</legend>
										<table>
											<tr>
												<td>测试计划名称<input type="text" name="testplanname"
													size="30" value=""></td>
											</tr>

											<tr>
												<td>测试计划描述<input type="text" name="testplandesc"
													size="30" value=""></td>
											</tr>

											<tr>
												<td><button type="submit" class="btn"
														onclick="savePlan()">保存</button></td>
											</tr>

										</table>
									</fieldset> <br>
										<div>
											<input type="hidden" value="" name="type" id="type">
										</div> <br /> <br />
										</form>
						</div>
					</div>
				</div>
				
			</div>
			<div class="span4">
				<div class="row-fluid">
					<div class="span12">
						<h4>操作日志</h4>
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