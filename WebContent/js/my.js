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
			url = "../performServlet?method=save";
		} else if (document.getElementById("type").value == "test") {
			url = "../performServlet?method=test";
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