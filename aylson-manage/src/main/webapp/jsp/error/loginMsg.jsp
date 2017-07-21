<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="<%=request.getContextPath()%>/resources/js/jquery-easyui-1.4.2/jquery.min.js" charset="UTF-8" type="text/javascript"></script>
        <title>提示</title>
        <script type="text/javascript">
            var t = 3;    //设置一个跳转秒数

			$(function(){
			    setInterval("count()",1000);  //定义一个定时器
			    $("#timeCount").html(t);    //显示当前的秒数
			});
			
			function count(){
			    t --;  //秒数自减
			    if(t >= 0){
			        $("#timeCount").html(t);  //刷新当前的秒数，重新显示秒数
			        
			    }else{
			        //clearInterval();//这个可以不用，因为页面都要跳转了，要了也没多大差别
			        window.location.href="<%=request.getContextPath()%>/login";  // 设置跳转的链接
			    }
			}
        </script>
    </head>
    <body>
        <div>  
        	${msg}<script>try{parent.$.messager.progress('close');}catch(e){}</script>
            <span id="timeCount" style="color:red">
            </span> 
            <span style="color:green;font-weight:bold;">
           		秒后   <a href="<%=request.getContextPath()%>/login">返回登陆页面</a>。。。
             </span>
        </div>
    </body>
</html>
