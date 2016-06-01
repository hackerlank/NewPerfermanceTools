<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- <html xmlns="http://www.w3.org/1999/xhtml" lang="zh-cn"> -->
<html>
<head>
<script type="text/javascript" src="js/jquery-2.0.0.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/my.js"></script>
<link rel="stylesheet" href="css/bootstrap-combined.min.css"
	media="screen" />
<link rel="stylesheet" href="css/common.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>PerformanceTestPlatform</title>

</head>
<body>
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
											<td>Project Name:</td>
											<td><select name="warname">
													<option value="">请选择项目名称</option>
													<option value="businessAPi">businessAPi</option>
													<option value="Author">Author</option>

											</select></td>
										</tr>

										<tr>
											<td>Server Name or IP:</td>
											<td><input type="text" name="ip" size="30" value="" /></td>
										</tr>

										<tr>
											<td>Port Number:</td>
											<td><input type="text" name="port" size="30" value="" /></td>
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
									Parameters:<br />
									<textarea class="textdefine" name="parameters"></textarea>
									<span class="help-block">输入接口请求参数</span>
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
								<br/>
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
										<button type="button" class="close" data-dismiss="alert">×</button>
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
						<li class="active"><a href="#panel-913764" data-toggle="tab">操作日志</a>
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