<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="Shortcut Icon" href="resources/images/favicon.ico">
	<jsp:include page="resources/inc/meta.jsp"></jsp:include>
	<jsp:include page="resources/inc/easyui.jsp"></jsp:include>
	<link href="<%=request.getContextPath()%>/resources/css/bootstrap/2.2.2/bootstrap.min-2.2.2.css" rel="stylesheet" type="text/css">
	<link href="<%=request.getContextPath()%>/resources/css/bootstrap/2.2.2/style.css" rel="stylesheet" type="text/css">
	<title>系统登录</title>
    <style type="text/css">
      .container {
        padding-top: 40px;
        padding-bottom: 40px;
        //background-color: #f5f5f5;
      }
      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading {
         margin-bottom: 30px;
         margin-left: 80px;
      }
      .form-signin .checkbox {
         margin-top: 10px;
         margin-bottom: 20px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
         font-size: 16px;
         height: auto;
         margin-bottom: 15px;
         padding: 8px 9px;
      }
    </style>
    
    <script type="text/javascript">
		$(function(){   
			 $('#loginErrorID').hide();
		  	 $('#kaptchaImage').click(function () {//生成验证码  
		 	 	$(this).hide().attr('src', '<%=request.getContextPath()%>/captchaImage?' + Math.floor(Math.random()*100)).fadeIn(); 
		  	 })     
		 	 $(window).keydown(function(event){
				  if(event.keyCode == 13) {//enter快捷键
				  		login();
				  }
			 });
		 	$("#userName").focus();
		});  
		
		function login(){
			var userName = $("#userName").textbox("getValue");
			if(userName =="" || $("#password").val()=="" || $("#validateCode").val()==""){
				 $("#lblMsg").text("用户名，密码，验证码不能为空");
				 $('#loginErrorID').show();
				 return;
			}
			$("#login_form").find(":submit").prop("disabled", true); 
			$("#login_form").form('submit',{
	    		 type:'post',
	    		 url:'<%=request.getContextPath()%>/sys/user/admin/login',
	    		 success:function(responseData){
	    			 if(responseData){
	    				var data = $.parseJSON(responseData);
	    				if(!data.success){
	    					$("#login_form").find(":submit").prop("disabled", false); 
	    					$('#loginErrorID').show();
	    					$("#lblMsg").text(data.message);
	    					return;
	    				}
	    				if($("#rememberme").attr("checked")){
	    					setCookie('qbsusername',$("#userName").val(),365);
	    				}
	    				window.location.href ='<%=request.getContextPath()%>'+data.message;
	    			 }
	    		 }
	    	 });
		};
		
		function getCookie(c_name)
		{
			if (document.cookie.length>0)
			{
				  c_start=document.cookie.indexOf(c_name + "=")
				  if (c_start!=-1)
				    { 
				    c_start=c_start + c_name.length+1 
				    c_end=document.cookie.indexOf(";",c_start)
				    if (c_end==-1) c_end=document.cookie.length
				    return unescape(document.cookie.substring(c_start,c_end))
				    } 
			}
			return ""
		}
		
		function setCookie(c_name,value,expiredays)
		{
			var exdate=new Date()
			exdate.setDate(exdate.getDate()+expiredays)
			document.cookie=c_name+ "=" +escape(value)+
			((expiredays==null) ? "" : ";expires="+exdate.toGMTString())
		}
	</script>
  </head>
  <body>
    <div class="header" style="overflow:hidden;background: url('<%=request.getContextPath()%>/resources/images/bg/login_top.png')">
         <div class="container">
             <div class="row">
                 <div class="logo span4">
                     <h1><a href=""><span class="red"></span></a></h1>
                 </div>
             </div>
         </div>
    </div>
    
    <div class="container">
    	<%-- <div style="text-align:center;" ><img src="<%=request.getContextPath()%>/resources/images/bg/topline.jpg"/>
		</div> --%>
		<form class="form-signin" id="login_form" method="post" autocomplete="off">
		   <h2 class="form-signin-heading">系统登录</h2>
		   <div class="user_loginInfo" id="loginErrorID"><img src="./resources/images/error.png" width="16px" style="margin:0 0 0 0">
		   	<span id="lblMsg" class="red"></span>
           </div>
           <input  id="userName" name="userName" value="test001"  prompt="用户名" placeholder="输入可搜索" pattern="输入可搜索"  class="easyui-textbox"  style="width: 300px;height:40px;padding:8px 9px;font-size:16px;"/><br/>
		   <input type="password" id="password" name="pwd" prompt="密码" class="input-block-level" value = ""  placeholder="密码" autocomplete="off" style="margin-top:15px;">
		   <input type="text" id="validateCode" name="validateCode"  class="input-block-level" value="开发" placeholder="验证码" maxlength="4" autocomplete="off" />
		   <img src="<%=request.getContextPath()%>/captchaImage"  height="45" id="kaptchaImage"/>   
		   <label class="checkbox">
		     <input type="checkbox" id="rememberme" value="remember-me" checked>记住我
		   </label>
		   <button class="btn btn-large btn-primary" type="button" onclick="login()">进入</button>
		</form>
		<div align="center">版权所有 @ 2017.北京御风翱翔科技有限公司.</div>
    </div>
    <div style="left: 0px; top: 0px;bottom:0px; overflow: hidden; margin: 0px; padding: 0px; width: 1920px; z-index: -999999; position: fixed;"><img src="<%=request.getContextPath()%>/resources/images/bg/login_bg.jpg" style="width:100%;height:100%;">
   </body>
</html>