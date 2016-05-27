<%@page import="org.apache.tomcat.jni.ProcErrorCallback"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/common.css">
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
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
		if (document.getElementsByName("port")[0].value == "") {
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
		}
		/* document.form1.action = "performServlet?method=save"; */
		return true;

	}

	function removeChildren(pnode){    
		var childs=pnode.childNodes;    
		for(var i=childs.length-1;i>=0;i--){    
			pnode.removeChild(childs.item(i));    
		}    
	} 
	
	function testAPI() {
		if (checkEmpty()) {
			var logNode=document.getElementById("console");
			removeChildren(logNode);
			document.getElementById("type").value= "test";
		}
	}
	function savePlan() {
		if (checkEmpty()) {
			var logNode=document.getElementById("console");
			removeChildren(logNode);
			document.getElementById("type").value= "save";
		}
	}
	
	function submitPlan(){
		var url;
		if(document.getElementById("type").value== "save"){
			url = "performServlet?method=save";
		}else if(document.getElementById("type").value== "test"){
			url = "performServlet?method=test";
		}
		$.ajax({
			type:"post",
			url:url,
			data:$("#form").serialize(),
			success:function(msg){
				/* var text=$("<p></p>").text(msg); */ 
				$("#console").html(msg);
			},
			error:function(xhr){
			      alert("错误提示： " + xhr.status + " " + xhr.statusText);
			}
		
		});
        return false;
	}

	function ajaxRequest(url) {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function(result) {
			if (xmlhttp.readyState == 4) {
				if (xmlhttp.status == 200) {
					var preNode = document.createElement("p");
					var brNode = document.createElement("br");
					var textNode = document.createTextNode(xmlhttp.responseText);
					preNode.appendChild(textNode);
					preNode.appendChild(brNode);
					document.getElementById("console").appendChild(preNode);
				}/* else {
					var preNode = document.createElement("pre");
					var brNode = document.createElement("br");
					var textNode = document.createTextNode("请求失败");
					var textNode1 = document.createTextNode("http response code:" + xmlhttp.status);
					preNode.appendChild(textNode);
					preNode.appendChild(brNode);
					preNode.appendChild(textNode1);
					document.getElementById("console").appendChild(preNode);
				} */
			}
		}
		xmlhttp.open("GET", url, true);
		xmlhttp.send();
	}
</script>

</head>
<body>
	<div id=menu>
		<h2 style="text-align: center">自动化性能测试平台</h2>
		<form id="form" name="form1" action="" method="post" onsubmit="return submitPlan()">
			<h4>配置 HTTP 请求</h4>
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
						<td><input type="text" name="ip" size="30" value="172.28.29.82"></td>
						<td><p style="color: red; font-size: 10px">* 输入请求域名或ip地址</p></td>
					</tr>

					<tr>
						<td>Port Number:</td>
						<td><input type="text" name="port" size="30" value="8081"></td>
						<td><p style="color: red; font-size: 10px">*
								输入请求端口号，默认为8081</p></td>
					</tr>

				</table>

			</fieldset>
			<br>

			<fieldset>
				<legend>HTTP Request:</legend>
				<table>
					<tr>
						<td>Path:</td>
						<td><input type="text" name="path" size="30" value="/dataInterOper/operServlet/saveOperInfo"></td>
						<td><p style="color: red; font-size: 10px">* 输入接口请求路径</p></td>
					</tr>

					<tr>
						<td>Method:</td>
						<td><select name="requestmethod">
								<option value="get">GET</option>
								<option value="post" selected="selected">POST</option>
						</select></td>
					</tr>
				</table>
				Parameters:<br>
				<textarea name="parameters" value="user=root&passwd=1234">{"sign":"363b55c6fe4604377c0f64a112489411","t":"1456371556","data":{"sf": "2b9fc9ac4d8d12ce014d8dad55a30ad1","dn":"ac4d8d12ce014d8","ui":"22","ci":"34","ft":"2","fi":"004","bc":"1","bd":"1","dv":"3"},"appKey":"1c9fc9ac4d8dacce014d8dad55a30103"}</textarea>
				<p style="color: red; font-size: 10px">* 输入请求参数</p>
			</fieldset>
			<br>

			<h4>设置并发用户数</h4>
			<fieldset>
				<legend>Number of Threads(users)</legend>
				<input type="radio" name="vuser" value="10" onclick="Change('0')"
					checked="checked" />10 <input type="radio" name="vuser" value="50"
					onclick="Change('0')" />50 <input type="radio" name="vuser"
					value="100" onclick="Change('0')" />100 <input type="radio"
					name="vuser" value="200" onclick="Change('0')" />200 <input
					type="radio" name="vuser" value="500" onclick="Change('0')" />500
				<input type="radio" name="vuser" value="1000" onclick="Change('0')" />1000
				<input id="define" type="radio" name="vuser" onclick="Change('1')" />自定义<input
					id="defineValue" type="hidden" name="vuser" size="30"
					onchange="assignValue()">
			</fieldset>
			<br>

			<h4>设置响应断言</h4>
			<fieldset>
				<legend>Patterns to Test</legend>
				<textarea name="assertion"></textarea>
				<p style="color: red; font-size: 10px">*
					输入断言的参数结果，即：响应信息中只要包含所填写的参数信息，就表示成功。</p>
			</fieldset>
			<br /> <br />
			<div id="btn">
				<input  type="hidden" value="" name="type" id="type">  
				<input type="submit" value="保存" onclick="savePlan()" >
				<input type="submit" value="测试接口" onclick="testAPI()">
			</div>
			<br /> <br />

		</form>
		<fieldset>
			<legend>console</legend>
			<div id="console" rows="500"></div>
		</fieldset>
	</div>



</body>
</html>