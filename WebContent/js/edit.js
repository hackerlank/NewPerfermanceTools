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
	/*
	 * if (document.getElementsByName("port")[0].value == "") {
	 * alert("请输入端口号。"); document.form1.port.focus(); return false; } if
	 * (document.getElementsByName("path")[0].value == "") {
	 * alert("请输入请求接口的路径。"); document.form1.path.focus(); return false; } if
	 * (document.getElementsByName("parameters")[0].value == "") {
	 * alert("请输入请求参数parameters。"); document.form1.parameters.focus(); return
	 * false; }
	 */
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
	var logNode = document.getElementById("console_edit");
	removeChildren(logNode);
	document.getElementById("type").value = "test";
}
function savePlan() {
	var logNode = document.getElementById("console_edit");
	removeChildren(logNode);
	document.getElementById("type").value = "save";
}

function cc() {
	var e = event.srcElement;
	var r = e.createTextRange();
	r.moveStart('character', e.value.length);
	r.collapse(true);
	r.select();
}

function submitPlan() {
	var url;
	if (document.getElementById("type").value == "save") {
		url = "../performServlet?method=save";
	} else if (document.getElementById("type").value == "test") {
		url = "../performServlet?method=test";
		// url = "performServlet?method=getServiceIp";
	}
	if (checkEmpty()) {
		$.ajax({
			type : "post",
			url : url,
			data : $("#form").serialize(),
			success : function(msg) {
				/*
				 * var text=$("<p></p>").text(msg);
				 * $("#console").append(text);
				 */
				$("#console_edit").html(msg);
			},
			error : function(xhr) {
				alert("错误提示： " + xhr.status + " " + xhr.statusText);
			}

		});
	}
	return false;
}



function setParamType(type) {
	// type = 1, k-v type=2,BodyData
	$("#paramType").val(type);

}

function showParamName() {
	$("input:text", $("#paramTable")).each(function() {
		alert($(this).attr("name"));
		// alert($(this).parent().parent().index());

	});
}

/**
 * 判断元素是否为空
 * 
 * @param obj
 * @returns {Boolean}
 */
function isEmptyObject(obj) {
	for ( var key in obj) {
		return false;
	}
	return true;
}

/**
 * 切换工程名称时，自动填入IP地址和端口号
 */
function changeToAddIP() {
	var serviceId = $("#project option:selected").val();

	$("#ip").val("");
	$("#port").val("");
	if (serviceId != null) {
		var url = "../performServlet?method=getServiceIp";
		var args = {"id" : serviceId};
		$.getJSON(url, args, function (data) {

			if (data.length == 0) {
				alert("请输入IP地址，端口号");
			} else {
				if (!isEmptyObject(data.prdomain)) {
					$("#ip").val(data.prdomain);
					$("#domainInfo").show();
				} else if ($("#environment").val() == 1) {
					$("#ip").val(data.devIp);
					$("#domainInfo").hide();
				} else if ($("#environment").val() == 2) {
					$("#ip").val(data.testIp);
					$("#domainInfo").hide();
				} else if ($("#environment").val() == 3) {
					if (!isEmptyObject(data.preproPubIp)) {
						var preproIP = data.preproPubIp;
						var preIP = preproIP.substr(0, preproIP.length - 5);
					}
					$("#ip").val(preIP);
					$("#domainInfo").hide();
				}
				$("#port").val("8081");
			}
		});
	}
}
/**
 * 切换工程名称时，自动填入Path(接口路径)
 */
function changeToAddPath() {
//	var serviceId = $(this).val();
	
	var serviceId = $("#project option:selected").val();
	
	//清空path下除第一行以外的数据
	$("#path option:not(:first)").remove();
	
	//清空parameters
	$("#bodyData").empty();
	$("#paramtbody").empty();
	$("#paramCount").val(0);

	//获取Path路径
	if (serviceId != null) {
		var url = "../performServlet?method=getListApiPath";
		var args = {"id" : serviceId};
		$.getJSON(url, args, function (data) {
			
			if (data.length == 0) {
				alert("请配置接口路径");
			} else {
				for(var i=0;i<data.length;i++){
					var apiId = data[i].id;
					var apiName = data[i].path;
					$("#path").append("<option value='"+apiId+"'>"+apiName+"</option>");
				}
				
			}
		});
	}
}

/**
 * 点击add按钮 添加 header和parameter
 */
function addNewLine(type){
//	alert(type);
	//type =1  添加Header  Type=2 添加param
	if(type == 1){
		var count = parseInt($("#headerCount").val()); /*String 转 Int */
		var typeName = "header";
		var tr = makeTr(type,typeName,count,"","");
		if (count == 0) {
			$("#headertbody").append(tr);
			$("#headerCount").val(count + 1);
			// alert($("#paramCount").val());
		} else {
			/* 判断input是否为空 */
			var flag = true;
			$("input:text", $("#headerTable")).each(function() {
				if ($(this).val().trim() == "") {
					$(this).val("");
					alert("有空参数");
					flag = false;
					return false;
				}
			});
			
			if (flag) {
				$("#headertbody").append(tr);
				$("#headerCount").val(count + 1);
				// alert($("#paramCount").val());
			}
		}
		return false;

	}else if(type == 2){
		var count = parseInt($("#paramCount").val()); /*String 转 Int */
		var typeName = "param";
		var tr = makeTr(type,typeName,count,"123","123");
		if (count == 0) {
			$("#paramtbody").append(tr);
			$("#paramCount").val(count + 1);
			// alert($("#paramCount").val());
		} else {
			/* 判断input是否为空 */
			var flag = true;
			$("input:text", $("#paramTable")).each(function() {
				if ($(this).val().trim() == "") {
					$(this).val("");
					alert("有空参数");
					flag = false;
					return false;
				}
			});
			
			if (flag) {
				$("#paramtbody").append(tr);
				$("#paramCount").val(count + 1);
				// alert($("#paramCount").val());
			}
		}
		return false;
	}
}
/**
 * 通过ApiID 获取method 和 paramType
 */
function changeToAddMethod(){
	//清空parameters
	$("#bodyData").empty();
	$("#paramtbody").empty();
	$("#paramCount").val(0);
	$("#console_edit").empty();
	
	
//	var pathId = $("this").val();
	var pathId = $("#path option:selected").val();
	$("#pathText").val($("#path option:selected").text());
	if(pathId != ""){
		var url = "../performServlet?method=getApiInfoById";
		var args = {"id":pathId};
		$.getJSON(url, args, function (data) {
			
			if (data.length == 0) {
				alert("请配置参数数据");
			} else {
				//获取method，并选中对应方法  method = 1 代表get方法  method=2 代表Post方法
				if(data.method == 1){
					$("#requestmethod").val("get");
				}else if(data.method == 2){
					$("#requestmethod").val("post");
				}
				
				//获取paramType =1 表示 kv模式  paramType=2 表示 body模式
				if(data.paramType == 2){
					$("#tabs-52171 ul li:last").attr("class","active");
					$("#tabs-52171 ul li:last a").attr("aria-expanded","true");
					$("#panel-141708").attr("class","tab-pane");
					$("#tabs-52171 ul li:first").attr("class","");
					$("#tabs-52171 ul li:first a").attr("aria-expanded","false");
					$("#panel-974412").attr("class","tab-pane active");
					$("#bodyData").text(data.parameters);
					$("#paramType").val(data.paramType);
					
				}else if(data.paramType == 1){
					$("#tabs-52171 ul li:last").attr("class","");
					$("#tabs-52171 ul li:last a").attr("aria-expanded","false");
					$("#panel-141708").attr("class","tab-pane active");
					$("#tabs-52171 ul li:first").attr("class","tab-pane active");
					$("#tabs-52171 ul li:first a").attr("aria-expanded","true");
					$("#panel-974412").attr("class","tab-pane");
					$("#paramType").val(data.paramType);
					getParameters(pathId);
				}
				
			}
		});
	}
}

/**
 * 如果参数类型ParamType = 1，调用方法获取paramers
 */
function getParameters(apiId){
	var args = {"id":apiId};
	var url = "../performServlet?method=getParametersByApiId";
	$.getJSON(url, args, function (data) {
		
		if (data.length == 0) {
			alert("请配置请求参数");
		} else {
			$("#paramCount").val(data.length);
			for(var i=0;i<data.length;i++){
				var tr = makeTr(2,"param",i,data[i].paramName,data[i].paramValue);
				$("#paramtbody").append(tr);
			}
			
			
		}
	});
}

/**
 * 拼接table的 tr 源码
 */
function makeTr(type,typeName,count,paramName,paramValue){
	//type = 1 添加Header   type=2  添加param
	var tr = "<tr><td><input type=\"text\" "
		tr += "name=\"" + typeName + "Name" + count + "\"";
		tr += "style=\"border: none;box-shadow:none\" value=\""+ paramName +"\"></td>"
		tr += "<td><input type=\"text\" "
		tr += "name=\"" + typeName + "Value" + count + "\"";
		tr += "style=\"border: none;box-shadow:none\" value=\""+ paramValue+"\"></td>"
		tr += "<td><button class=\"delbtn close\" type=\"button\" class=\"close\" style=\"float:left;margin-left: 2px\" onclick=\"deleteTr(this," + type + ")\" >×</button></td></tr>";
	return tr;
}

/**
 * 点击 X 删除 当前行
 */
function deleteTr(nowTr,type) {
	if(type == 1){
		var count = parseInt($("#headerCount").val()); /* String 转 Int */
		count = count - 1;
		$("#headerCount").val(count);
		$(nowTr).parent().parent().remove();
		// alert($("#paramCount").val());
		
		$("input:text", $("#headerTable")).each(function() {
			if ($(this).parent().index() == 0) {
				var strName = "headerName" + $(this).parent().parent().index();
			} else if ($(this).parent().index() == 1) {
				var strName = "headerValue" + $(this).parent().parent().index();
			}
			$(this).attr("name", strName);
		});		
	}else if(type == 2){
		var count = parseInt($("#paramCount").val()); /* String 转 Int */
		count = count - 1;
		$("#paramCount").val(count);
		$(nowTr).parent().parent().remove();
		// alert($("#paramCount").val());
		
		$("input:text", $("#paramTable")).each(function() {
			if ($(this).parent().index() == 0) {
				var strName = "paramName" + $(this).parent().parent().index();
			} else if ($(this).parent().index() == 1) {
				var strName = "paramValue" + $(this).parent().parent().index();
			}
			$(this).attr("name", strName);
		});
	}
}


/**
 * 加载全局变量
 */
$(function() {
	$("#project").change(function(){
		changeToAddIP();
		changeToAddPath();
	});
	
	$("#environment").change(function (){
		if($("#project option:selected").val() != 0){
			changeToAddIP();
		}
	});
		
	$("#path").change(function(){
		changeToAddMethod();
		changeToAddParam();
	});

	// 增加单独接口配置页面
	// 后期优化：增加和删除时，添加删除和修改标记，以便后期修改parameter表
	
	$("#addHeaderBtn").click(function(){
		addNewLine(1);
	});
	$("#addParamBtn").click(function(){
		addNewLine(2);
	});
	
})

/**
 * 执行测试计划
 */
function runTest(){
	
}

function testActive(type){
//	$("#tabs-52171 ul li:last").attr("class","active");
//	$("#tabs-52171 ul li:last a").attr("aria-expanded","true");
//	$("#panel-141708").attr("class","tab-pane");
//	$("#tabs-52171 ul li:first").attr("class","");
//	$("#tabs-52171 ul li:first a").attr("aria-expanded","false");
//	$("#panel-974412").attr("class","tab-pane active");
//	if(type%2 == 0){
//		$("#requestmethod").find("option[value='post']").attr("selected",false);
//		$("#requestmethod").find("option[value='get']").attr("selected",true);
//	}
//	else{
//		$("#requestmethod").find("option[value='post']").attr("selected",true);
//		$("#requestmethod").find("option[value='get']").attr("selected",false);
//	}
	alter((type % 2));
	//testActive(type+1);
	
}
