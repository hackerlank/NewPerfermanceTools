<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<title>Insert title here</title>
<style>
  div { margin:3px; width:40px; height:40px;
        position:absolute; left:0px; top:30px; 
        background:green; display:none; 
        border: 1px}
  div.newcolor { background:blue; }
</style>
<script type="text/javascript">
 $(function(){
	 
	$(document.body).click(function () {
	    $("div").show("slow");
 	    $("div").animate({left:'+=200'},2000);
	    $("div").queue(function () {
	        $(this).addClass("newcolor");
	        $(this).dequeue();
	    });
/*	    $("div").animate({left:'-=200'},500);
	    $("div").queue(function () {
	        $(this).removeClass("newcolor");
	        $(this).dequeue();
	    });*/
	    $("div").slideUp(); 
	});
	 
 });
</script>
</head>
<body>
  <button>Click here...</button>
  <div>haha</div>
  
  <a href="testResponseServlet">test response</a>
</body>
</html>