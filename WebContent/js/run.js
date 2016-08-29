/**
 * 切换工程名称时，自动填入Path(接口路径)
 */
function changeToAddPath() {

	var serviceId = $("#project option:selected").val();

	// 清空path下除第一行以外的数据
	$("#path option:not(:first)").remove();

	// 清空plan
	$("#plan option:not(:first)").remove();

	// 获取Path路径
	if (serviceId != null) {
		var url = "../performServlet?method=getListApiPath";
		var args = {
			"id" : serviceId
		};
		$.getJSON(url, args, function(data) {

			if (data.length == 0) {
				alert("请配置接口路径");
			} else {
				for (var i = 0; i < data.length; i++) {
					var apiId = data[i].id;
					var apiName = data[i].path;
					$("#path").append(
							"<option value='" + apiId + "'>" + apiName
									+ "</option>");
				}

			}
		});
	}
}

/**
 * 切换工程名称时，自动填入Plan(测试计划)
 */
function changeToAddPlan() {
	// var serviceId = $(this).val();

	var pathId = $("#path option:selected").val();

	// 清空plan
	$("#plan option:not(:first)").remove();
	// 清空desc
	$("#desc").empty(); // 只清空内容

	// 获取Path路径
	if (pathId != null) {
		var url = "../performServlet?method=getListApiPlan";
		var args = {
			"id" : pathId
		};
		$.getJSON(url, args, function(data) {

			if (data.length == 0) {
				alert("请配置接口路径");
			} else {
				for (var i = 0; i < data.length; i++) {
					var planId = data[i].id;
					var planName = data[i].testPlanName;
					var planDesc = data[i].descript;
					$("#plan").append(
							"<option value='" + planId + "'>" + planName
									+ "</option>");
				}

			}
		});
	}
}

/**
 * 切换testPlan(测试计划)名称时，自动填入描述信息
 */
function changeToAddDesc() {
	// var serviceId = $(this).val();

	var planId = $("#plan option:selected").val();

	// 清空desc
	$("#desc").empty(); // 只清空内容
	// $("#desc").remove(); //移除节点
	// 获取Path路径
	if (planId != null) {
		var url = "../performServlet?method=getDescWithPlanId";
		var args = {
			"id" : planId
		};
		$.getJSON(url, args, function(data) {

			if (data.length == 0) {
			} else {
				var planDesc = data.descript;
				$("#desc").append(
						"<option>" + "&nbsp;&nbsp;&nbsp;&nbsp;" + planDesc
								+ "</option>");
				// $("#desc").html("&nbsp;&nbsp;"+planDesc); //可以
				// $("#desc").val("&nbsp;&nbsp;"+planDesc); //不可以
			}

		});
	}
}

/**
 * 加载全局变量
 */
$(function() {
	$("#project").change(function() {
		changeToAddPath();
	});

	$("#path").change(function() {
		changeToAddPlan();
	});

	$("#plan").change(function() {
		changeToAddDesc();
	});

})

/**
 * 执行测试计划
 */
function runTest() {
	var url = "../performServlet?method=run";
	$("#console_run").html(""); // 清空日志区域
	$.ajax({
		type : "post",
		url : url,
		data : $("#form").serialize(),
		success : function(msg) {
			// $("#console_run").html(msg);
		},
		error : function(xhr) {
			alert("错误提示： " + xhr.status + " " + xhr.statusText);
		}

	});
//	getRuningLog(0); // 获取执行日志，读取内存中的logMapTemp参数
	return false;
}

function getRuningLog(line) {
	alert(line);
	if (line == -1) {
		return;
	}
	var url = "../performServlet?method=getMapLog";
	var args = {
		"line" : line
	};
	$.getJSON(url, args, function(data) {
//		if (data.length != 0) {
			for (var i = 0; i < data.length; i++) {
				if(data[i] != null){
					$("#console_run").append(data[i] + "<br>");
				}
				// alert(data[i]);
			}
			alert(data[i-1]);
//			if(data[i-1].indexOf("end") != -1){
//				line = line + data.length;
//			}else{
//				line = -1;
//			}
			line = line + data.length;
//		}
		getRuningLog(line);
	});

}
