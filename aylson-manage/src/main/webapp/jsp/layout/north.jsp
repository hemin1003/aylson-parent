<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<style>
<!--
.memu_1{
	text-decoration:none;
	color:#ffffff;
}
.memu_1:hover{
	color:#000000;
}
-->
</style>
<div style="position: absolute; right: 0px; bottom: 5px;right:15px; font-weight:bolder;font-family:微软雅黑;font-size:14px; ">
	<font color="white"><c:if test="${sessionInfo != null}"><strong>${sessionInfo.user.userName}</strong>，欢迎您！</c:if>|</font>
	<c:if test="${sessionInfo.user.type == 2 }"><!-- 代理商 -->
	<font color="white"><a href="javascript:void(0);" class="easyui-menubutton memu_1" menu="#layout_north_kzmbMenu" >我的二维码</a>&nbsp;|</font>
	</c:if>
	<font color="white"><a href="javascript:void(0);" class="memu_1" onclick="modifyPwd();">修改密码</a>&nbsp;|</font>
	<font color="white"><a href="javascript:void(0);" class="memu_1" onclick="editUserInfo();">个人资料</a>&nbsp;|</font>
	<font color="white"><a href="javascript:void(0);" class="memu_1" onclick="logout();">注销系统</a></font>
<!-- 	<a href="javascript:void(0);" class="easyui-menubutton" menu="#layout_north_kzmbMenu" iconCls="icon-help"><font color="white">控制面板</font></a>
 	<a href="javascript:void(0);" class="easyui-menubutton"  menu="#layout_north_zxMenu" iconCls="icon-back"><font color="white">注销</font></a>
-->
</div>
<c:if test="${sessionInfo.user.type == 2 }">
<div id="layout_north_kzmbMenu">
	<div onclick="getMyQrCodeUrl(${sessionInfo.user.id});">设计师推广二维码</div>
	<div onclick="getMyOwnQrCodeUrl(${sessionInfo.user.id});">安居艾臣业主推广二维码</div>
</div>
</c:if>

<div id="layout_north_zxMenu" style="width: 100px; display: none;">
	<div class="menu-sep"></div>
	<div onclick="logout();">注销系统</div>
</div>
<div id="qrCodeDlg" class="easyui-dialog" title="我的二维码" data-options="closed:true,resizable:true" style="width:450px;height:510px;">
		<img id="qrCode" src=''/>
</div>
<input type="hidden" id="isLiveMode" value="${isLiveMode }"/>
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/aylson/sys/user.js?date=2016050803"></script>
