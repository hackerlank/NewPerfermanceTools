/**
 * 切换工程名称时，自动填入Path(接口路径)
 */
function changeToAddPath() {
	
	var serviceId = $("#project option:selected").val();
	
	//清空path下除第一行以外的数据
	$("#path option:not(:first)").remove();
	
	//清空plan
	$("#plan option:not(:first)").remove();

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
 * 切换工程名称时，自动填入Plan(测试计划)
 */
function changeToAddPlan() {
//	var serviceId = $(this).val();
	
	var pathId = $("#path option:selected").val();
	
	//清空plan
	$("#plan option:not(:first)").remove();

	//获取Path路径
	if (pathId != null) {
		var url = "../performServlet?method=getListApiPLA";
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
 * 加载全局变量
 */
$(function() {
	$("#project").change(function(){
		changeToAddPath();
	});
		
	$("#path").change(function(){
		changeToAddPlan();
	});
	
})

/**
 * 执行测试计划
 */
function runTest(){
	
}


