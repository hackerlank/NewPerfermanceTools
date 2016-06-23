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
			url = "performServlet?method=save";
		} else if (document.getElementById("type").value == "test") {
			url = "performServlet?method=test";
	//		url = "performServlet?method=getServiceIp";
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
	
function deleteTr(nowTr){
	var paramCount = parseInt($("#paramCount").val()); /* String 转 Int */
	paramCount = paramCount - 1;
	$("#paramCount").val(paramCount);
	$(nowTr).parent().parent().remove(); 
//	alert($("#paramCount").val());

	$("input:text",$("#paramTable")).each(function(){
		if($(this).parent().index() == 0){
			var strName = "paramName" + $(this).parent().parent().index();
		}else if($(this).parent().index() == 1){
			var strName = "paramValue" + $(this).parent().parent().index();
		}
		$(this).attr("name",strName);
	}); 
}

function setParamType(type){
	//	type = 1, parameter  type=2,BodyData
	$("#paramType").val(type);
	
}

function showParamName(){
	$("input:text",$("#paramTable")).each(function(){
		alert($(this).attr("name"));
	//	alert($(this).parent().parent().index());
		
	}); 
}

$(function(){
	$("#project").change(function() {
		var serviceId = $(this).val();
		$("#ip").val("");
		$("#port").val("");
		if (serviceId != null) {
			
			var url = "performServlet?method=getServiceIp";
			var args = {
				"id" : serviceId
			};

			$.getJSON(url,args,function(data){
				if (data.length == 0) {
					alert("请输入IP地址，端口号");
				} else {
					if(data.prdomain != null){
						$("#ip").val(data.prdomain);
					}else{
						$("#ip").val(data.pressIp);
					}
					$("#port").val("8081");
				}
			});
		}

	});
	
	// 增加单独接口配置页面
	//后期优化：增加和删除时，添加删除和修改标记，以便后期修改parameter表	
	$("#addbtn").click(function(){
		var paramCount = parseInt($("#paramCount").val());  /* String 转 Int */
		var tr = "<tr><td><input type=\"text\" "
		tr += "name=\"paramName" +  paramCount + "\"";
		tr += "style=\"border: none;box-shadow:none\" >"
		tr += "<td><input type=\"text\" "
		tr += "name=\"paramValue" +  paramCount + "\"";
		tr += "style=\"border: none;box-shadow:none\" ></td>"
		tr += "<td><button class=\"delbtn close\" type=\"button\" class=\"close\" style=\"float:left;margin-left: 2px\" onclick=\"deleteTr(this)\" >×</button></td></tr>";
		
		if(paramCount == 0){
			$("#paramtbody").append(tr);
			$("#paramCount").val(paramCount+1);
//			alert($("#paramCount").val());
		}else{
			/*判断input是否为空  */
			var flag = true;
		$("input:text",$("#paramTable")).each(function(){
 				if($(this).val().trim() == ""){
 					$(this).val("");
					alert("有空参数");
					flag = false;
					return false;
				}
			}); 
			
			if(flag){
				$("#paramtbody").append(tr);
				$("#paramCount").val(paramCount+1);
//				alert($("#paramCount").val());
			}
		}
		return false;

	});
})
	