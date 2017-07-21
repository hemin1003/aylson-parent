<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="/resources/inc/meta.jsp"></jsp:include>
<jsp:include page="/resources/inc/easyui.jsp"></jsp:include>
</head>
<style>
	td{font-size:12px;padding:3px;height:25px;text-align:left;}
	.td_1{background-color:#eeeeff;}
	.td_2{padding:0px;}
	.td_3{text-align:center;font-weight:bolder;}
</style>
<body >
	<div align="left" >
		<!-- <form id="integralConfigForm" method="post"> -->
			<div id="tab-tools">
				<a href="#" class="easyui-linkbutton" title="保存	当前列表配置" data-options="plain:true,iconCls:'icon-ok'"  onclick="save()">保存</a>
				<a href="#" class="easyui-linkbutton" title="重置	当前列表内容" data-options="plain:true,iconCls:'icon-clear'"  onclick="reset()">重置</a>
			</div>
			<!-- tabIntegralConfig -->
			<div class="easyui-tabs" id="tabIntegralConfig" data-options="tools:'#tab-tools'" style="width:100%">
				 <div title="产业工人积分赠送配置" style="padding:10px;text-align:center">
				 	<form id="integralConfigForm_1" method="post">
				 	<table class="tableForm" cellpadding="1" cellspacing="0" border="1" style="width:100%;border-collapse: collapse;border-style:solid;border-color:#95B8E7">
				 	<tr>
						<td class="td_1" colspan="4" style="font-weight:bolder;font-size:16px;">产业工人积分赠送配置：</td>
					</tr>
					<tr>
						<td class="td_1">注册成功赠送[我的推荐人]积分：</td>
						<td>
						<input name="integral"	value="${integralConfigVo_7.integral }" class="easyui-numberbox" data-options=""
							style="width: 200px;" />
						<input name="type" value="7" type="hidden"/>
						<input name="rangeBegin" value="0" type="hidden"/>
						<input name="rangeEnd" value="0" type="hidden"/>
						<input name="rate" value="0" type="hidden"/>
						</td>
						<td class="td_1">注册成功赠送[我的推荐人的推荐人]积分：</td>
						<td>
						<input name="integral"	value="${integralConfigVo_8.integral }"class="easyui-numberbox" data-options=""
							style="width: 200px;" />
						<input name="type" value="8" type="hidden"/>
						<input name="rangeBegin" value="0" type="hidden"/>
						<input name="rangeEnd" value="0" type="hidden"/>
						<input name="rate" value="0" type="hidden"/>
						</td>
					</tr>
					<tr>
						<td class="td_1" >提交客户资料赠送[提交人]积分：</td>
						<td>
						<input name="integral"	value="${integralConfigVo_9.integral }" class="easyui-numberbox" data-options=""
							style="width: 200px;" />
						<input name="type" value="9" type="hidden"/>
						<input name="rangeBegin" value="0" type="hidden"/>
						<input name="rangeEnd" value="0" type="hidden"/>
						<input name="rate" value="0" type="hidden"/>
						</td>
						<td class="td_1">提交客户资料赠送[推荐人]积分：</td>
						<td>
						<input name="integral"	value="${integralConfigVo_10.integral }" class="easyui-numberbox" data-options=""
							style="width: 200px;" />
						<input name="type" value="10" type="hidden"/>
						<input name="rangeBegin" value="0" type="hidden"/>
						<input name="rangeEnd" value="0" type="hidden"/>
						<input name="rate" value="0" type="hidden"/>
						</td>
					</tr>
				 	</table>
				 	</form>
				 </div>
				 <div title="设计师积分赠送配置" style="padding:10px;text-align:center">
				 	<form id="integralConfigForm_2" method="post">
				 	<table class="tableForm" cellpadding="1" cellspacing="0" border="1" style="width:100%;border-collapse: collapse;border-style:solid;border-color:#95B8E7">
				 	<tr>
						<td class="td_1" colspan="4" style="font-weight:bolder;font-size:16px;">设计师积分赠送配置：</td>
					</tr>
					<tr>
						<td class="td_1">注册成功赠送[我的推荐人]积分：</td>
						<td>
						<input name="integral"	value="${integralConfigVo_1.integral }" class="easyui-numberbox" data-options=""
							style="width: 200px;" />
						<input name="type" value="1" type="hidden"/>
						<input name="rangeBegin" value="0" type="hidden"/>
						<input name="rangeEnd" value="0" type="hidden"/>
						<input name="rate" value="0" type="hidden"/>
						</td>
						<td class="td_1">注册成功赠送[我的推荐人的推荐人]积分：</td>
						<td>
						<input name="integral"	value="${integralConfigVo_2.integral }"class="easyui-numberbox" data-options=""
							style="width: 200px;" />
						<input name="type" value="2" type="hidden"/>
						<input name="rangeBegin" value="0" type="hidden"/>
						<input name="rangeEnd" value="0" type="hidden"/>
						<input name="rate" value="0" type="hidden"/>
						</td>
					</tr>
					<tr>
						<td class="td_1" >提交客户资料赠送[提交人]积分：</td>
						<td>
						<input name="integral"	value="${integralConfigVo_3.integral }" class="easyui-numberbox" data-options=""
							style="width: 200px;" />
						<input name="type" value="3" type="hidden"/>
						<input name="rangeBegin" value="0" type="hidden"/>
						<input name="rangeEnd" value="0" type="hidden"/>
						<input name="rate" value="0" type="hidden"/>
						</td>
						<td class="td_1">提交客户资料赠送[推荐人]积分：</td>
						<td>
						<input name="integral"	value="${integralConfigVo_4.integral }" class="easyui-numberbox" data-options=""
							style="width: 200px;" />
						<input name="type" value="4" type="hidden"/>
						<input name="rangeBegin" value="0" type="hidden"/>
						<input name="rangeEnd" value="0" type="hidden"/>
						<input name="rate" value="0" type="hidden"/>
						</td>
					</tr>
					<tr>
						<td class="td_1">结算方案赠送[提交人]积分：</td>
						<td colspan="3" class="td_2">
							<table id="table_1" cellpadding="1" cellspacing="0" border="1" style="width:100%;border-collapse: collapse;border-style:solid;border-color:#95B8E7">
								<tr id="tr_1">
								<td width="30%">项目总金额（万元）</td>
								<td width="30%">积分公式</td>
								<td width="40%"><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" style="width:80px" onclick="addConfig('tr_1',5)">添加</a>
								</td>
								</tr>
								<c:forEach items="${list_5}" var="integralConfigVo_5">
									<tr>
										<td >
											<input type="hidden" name="integral" value="0"/>
											<input type="hidden" name="type" value="5"/>
											<input class="easyui-numberbox" name="rangeBegin" value="${integralConfigVo_5.rangeBegin }" style="width: 100px;" />—
											<input class="easyui-numberbox" name="rangeEnd"  value="${integralConfigVo_5.rangeEnd }"style="width: 100px;" />
										</td>
										<td >
											总金额&nbsp;x&nbsp;<input class="easyui-numberbox" value="${integralConfigVo_5.rate }" name="rate" precision="2" style="width: 100px;" />&nbsp;%
										</td>
										<td ><a href="#" onclick="delConfig(this)">删除</a></td>
									</tr>
								</c:forEach>
								<tr>
								<td colspan="3" style="color:red;font-size:9px;">注：金额小于最小范围的，按照最小范围公式计算，金额大于最大范围的，按照最大范围公式计算</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="td_1">结算方案赠送[推荐人]积分：</td>
						<td colspan="3" class="td_2">
							<table cellpadding="1" cellspacing="0" border="1" style="width:100%;border-collapse: collapse;border-style:solid;border-color:#95B8E7">
								<tr id="tr_2">
								<td width="30%">项目总金额（万元）</td>
								<td width="30%">积分公式</td>
								<td width="40%"><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" style="width:80px" onclick="addConfig('tr_2',6)">添加</a></td>
								</tr>
								<c:forEach items="${list_6}" var="integralConfigVo_6">
									<tr>
										<td >
											<input type="hidden" name="integral" value="0"/>
											<input type="hidden" name="type" value="6"/>
											<input class="easyui-numberbox" value="${integralConfigVo_6.rangeBegin }" name="rangeBegin" style="width: 100px;" />—
											<input class="easyui-numberbox" value="${integralConfigVo_6.rangeEnd }" name="rangeEnd" style="width: 100px;" />
										</td>
										<td >
											总金额&nbsp;x&nbsp;<input class="easyui-numberbox" value="${integralConfigVo_6.rate }" name="rate" precision="2" style="width: 100px;" />&nbsp;%
										</td>
										<td ><a href="#" onclick="delConfig(this)">删除</a></td>
									</tr>
								</c:forEach>
								<tr>
								<td colspan="3" style="color:red;font-size:9px;">注：金额小于最小范围的，按照最小范围公式计算，金额大于最大范围的，按照最大范围公式计算</td>
								</tr>
							</table>
						</td>
					</tr>
				 	</table>
				 	</form>
				 </div>
				 <div title="开拓者积分赠送配置" style="padding:10px;text-align:center">
				 	<form id="integralConfigForm_3" method="post">
				 	<table class="tableForm" cellpadding="1" cellspacing="0" border="1" style="width:100%;border-collapse: collapse;border-style:solid;border-color:#95B8E7">
		 				<tr>
							<td class="td_1" colspan="4" style="font-weight:bolder;font-size:16px;">开拓者积分赠送配置：</td>
						</tr>
						<tr>
							<td class="td_1">注册成功赠送[我的推荐人]积分：</td>
							<td>
							<input name="integral"	value="${integralConfigVo_13.integral }" class="easyui-numberbox" data-options=""
								style="width: 200px;" />
							<input name="type" value="13" type="hidden"/>
							<input name="rangeBegin" value="0" type="hidden"/>
							<input name="rangeEnd" value="0" type="hidden"/>
							<input name="rate" value="0" type="hidden"/>
							</td>
							<td class="td_1">注册成功赠送[我的推荐人的推荐人]积分：</td>
							<td>
							<input name="integral"	value="${integralConfigVo_14.integral }"class="easyui-numberbox" data-options=""
								style="width: 200px;" />
							<input name="type" value="14" type="hidden"/>
							<input name="rangeBegin" value="0" type="hidden"/>
							<input name="rangeEnd" value="0" type="hidden"/>
							<input name="rate" value="0" type="hidden"/>
							</td>
						</tr>
						<tr>
							<td class="td_1" >提交客户资料赠送[提交人]积分：</td>
							<td>
							<input name="integral"	value="${integralConfigVo_15.integral }" class="easyui-numberbox" data-options=""
								style="width: 200px;" />
							<input name="type" value="15" type="hidden"/>
							<input name="rangeBegin" value="0" type="hidden"/>
							<input name="rangeEnd" value="0" type="hidden"/>
							<input name="rate" value="0" type="hidden"/>
							</td>
							<td class="td_1">提交客户资料赠送[推荐人]积分：</td>
							<td>
							<input name="integral"	value="${integralConfigVo_16.integral }" class="easyui-numberbox" data-options=""
								style="width: 200px;" />
							<input name="type" value="16" type="hidden"/>
							<input name="rangeBegin" value="0" type="hidden"/>
							<input name="rangeEnd" value="0" type="hidden"/>
							<input name="rate" value="0" type="hidden"/>
							</td>
						</tr>
						<tr>
							<td class="td_1" >邀请代理商赠送[邀请人]积分：</td>
							<td>
							<input name="integral"	value="${integralConfigVo_17.integral }" class="easyui-numberbox" data-options=""
								style="width: 200px;" />
							<input name="type" value="17" type="hidden"/>
							<input name="rangeBegin" value="0" type="hidden"/>
							<input name="rangeEnd" value="0" type="hidden"/>
							<input name="rate" value="0" type="hidden"/>
							</td>
							<td class="td_1">邀请代理商赠送[推荐人]积分：</td>
							<td>
							<input name="integral"	value="${integralConfigVo_18.integral }" class="easyui-numberbox" data-options=""
								style="width: 200px;" />
							<input name="type" value="18" type="hidden"/>
							<input name="rangeBegin" value="0" type="hidden"/>
							<input name="rangeEnd" value="0" type="hidden"/>
							<input name="rate" value="0" type="hidden"/>
							</td>
						</tr>
						<tr>
							<td class="td_1">代理商签约赠送[邀请人]积分-<font color=red>独立介绍方式</font>：</td>
							<td class="td_2">
								<input name="integral"	value="${integralConfigVo_19.integral }" class="easyui-numberbox" data-options=""
									style="width: 200px;" />
								<input name="type" value="19" type="hidden"/>
								<input name="rangeBegin" value="0" type="hidden"/>
								<input name="rangeEnd" value="0" type="hidden"/>
							</td>
							<td class="td_1">代理商签约赠送[邀请人]现金：</td>
							<td class="td_2">
								<input class="easyui-numberbox" value="${integralConfigVo_19.rate }" name="rate" precision="2" style="width: 200px;" />
							</td>
						</tr>
						<tr>
							<td class="td_1">代理商签约赠送[邀请人]积分-<font color=red>非独立介绍方式</font>：</td>
							<td class="td_2">
								<input name="integral"	value="${integralConfigVo_22.integral }" class="easyui-numberbox" data-options=""
									style="width: 200px;" />
								<input name="type" value="22" type="hidden"/>
								<input name="rangeBegin" value="0" type="hidden"/>
								<input name="rangeEnd" value="0" type="hidden"/>
							</td>
							<td class="td_1">代理商签约赠送[邀请人]现金：</td>
							<td class="td_2">
								<input class="easyui-numberbox" value="${integralConfigVo_22.rate }" name="rate" precision="2" style="width: 200px;" />
							</td>
						</tr>
						<tr>
							<td class="td_1">代理商签约赠送[推荐人]积分：</td>
							<td class="td_2" colspan="3">
								<input name="integral"	value="${integralConfigVo_23.integral }" class="easyui-numberbox" data-options=""
									style="width: 200px;" />
								<input name="type" value="23" type="hidden"/>
								<input name="rangeBegin" value="0" type="hidden"/>
								<input name="rangeEnd" value="0" type="hidden"/>
								<input name="rate" value="0" type="hidden"/>
							</td>
						</tr>
						<tr>
							<td class="td_1">代理商开业赠送[邀请人]积分&现金：</td>
							<td colspan="3" class="td_2">
								<table id="table_1" cellpadding="1" cellspacing="0" border="1" style="width:100%;border-collapse: collapse;border-style:solid;border-color:#95B8E7">
									<tr><th></th><td colspan="2" class="td_1 td_3" >独立介绍</td><td colspan="2" class="td_1 td_3" >非独立介绍</td><th></th></tr>
									<tr id="tr_20">
									<td width="25%">商店面积（m2）</td>
									<td width="12%">赠送积分</td><!-- 独立介绍 -->
									<td width="12%">赠送现金</td><!-- 独立介绍 -->
									<td width="12%">赠送积分</td><!-- 非独立介绍 -->
									<td width="12%">赠送现金</td><!-- 非独立介绍 -->
									<td width="25%"><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" style="width:80px" onclick="addConfig2('tr_20',20,25)">添加</a>	</td>
									</tr>
									<c:forEach items="${list_20}" var="integralConfigVo_20">
										<tr>
											<td >
												<input type="hidden" name="type" value="20"/>
												<input class="easyui-numberbox" name="rangeBegin" value="${integralConfigVo_20.rangeBegin }" style="width: 100px;" data-options="
												onChange:function(newValue,oldValue){
														$(this).closest('tr').find('[class=\'rangeBegin25\']').val(newValue);
													}
												"/>—
												<input class="easyui-numberbox" name="rangeEnd"  value="${integralConfigVo_20.rangeEnd }"style="width: 100px;" data-options="
												onChange:function(newValue,oldValue){
														$(this).closest('tr').find('[class=\'rangeEnd25\']').val(newValue);
													}
												"/>
											</td>
											<td >
												<input class="easyui-numberbox" value="${integralConfigVo_20.integral }" name="integral"  style="width: 100px;" data-options=""/>
											</td>
											<td >
												<input class="easyui-numberbox" value="${integralConfigVo_20.rate }" name="rate"  style="width: 100px;" />
											</td>
											<c:forEach items="${list_25}" var="integralConfigVo_25">
												<c:if test="${integralConfigVo_20.rangeBegin==integralConfigVo_25.rangeBegin && integralConfigVo_20.rangeEnd==integralConfigVo_25.rangeEnd }">
												<td >
													<input type="hidden" name="type" value="25"/>
													<input type="hidden" class="rangeBegin25" name="rangeBegin" value="${integralConfigVo_25.rangeBegin }" style="width: 100px;" />
													<input type="hidden" class="rangeEnd25" name="rangeEnd"  value="${integralConfigVo_25.rangeEnd }" style="width: 100px;" />
													<input class="easyui-numberbox" value="${integralConfigVo_25.integral }" name="integral"  style="width: 100px;" />
												</td>
												<td >
													<input class="easyui-numberbox" value="${integralConfigVo_25.rate }" name="rate"  style="width: 100px;" />
												</td>
												</c:if>
											</c:forEach>
											<td ><a href="#" onclick="delConfig(this)">删除</a></td>
										</tr>
									</c:forEach>
									<tr>
									<td colspan="6" style="color:red;font-size:9px;">注：面积小于最小范围的，按照最小范围公式计算，面积大于最大范围的，按照最大范围公式计算</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="td_1">代理商开业赠送[推荐人]积分：</td>
							<td class="td_2" colspan="3">
								<input name="integral"	value="${integralConfigVo_24.integral }" class="easyui-numberbox" data-options=""
									style="width: 200px;" />
								<input name="type" value="24" type="hidden"/>
								<input name="rangeBegin" value="0" type="hidden"/>
								<input name="rangeEnd" value="0" type="hidden"/>
								<input name="rate" value="0" type="hidden"/>
							</td>
						</tr>
						<tr>
							<td class="td_1">已加盟代理商年销售额给[邀请人]回扣：</td>
							<td colspan="3" class="td_2">
								<input name="integral" value="0" type="hidden"/>
								<input name="type" value="21" type="hidden"/>
								<input name="rangeBegin" value="0" type="hidden"/>
								<input name="rangeEnd" value="0" type="hidden"/>
								总金额&nbsp;x&nbsp;<input class="easyui-numberbox" value="${integralConfigVo_21.rate }" name="rate" precision="2" style="width: 100px;" />&nbsp;%
							</td>
						</tr>
				 	</table>
				 	</form>
				 </div>
			</div>
		<!-- </form> -->
	</div>
</body>
<script>

$(function(){
	$(".easyui-numberbox").numberbox({required:true})
})

function delConfig(obj){
	$(obj).parent().parent().remove();
}

function addConfig(id,type){
	var config = "<tr><td ><input type=\"hidden\" name=\"integral\" value=\"0\"/>"+
	"<input type=\"hidden\" name=\"type\" value=\""+type+"\"/>"+
	"<input class=\"numberboxCss\" name=\"rangeBegin\" style=\"width: 100px;\" />—"+
	"<input class=\"numberboxCss\" name=\"rangeEnd\" style=\"width: 100px;\" /></td>"+
	"<td >总金额&nbsp;x&nbsp;<input name=\"rate\" precision=\"2\" class=\"numberboxCss\" style=\"width: 100px;\" />&nbsp;%</td>"+
	"<td ><a href=\"#\" onclick=\"delConfig(this)\">删除</a></td></tr>";
	$("#"+id).after(config);
	$(".numberboxCss").numberbox({required:true})
}

function addConfig2(id,type,type2){
	var config = "<tr><td >"+
	"<input type=\"hidden\" name=\"type\" value=\""+type+"\"/>"+
	"<input class=\"numberboxCss1\" name=\"rangeBegin\" style=\"width: 100px;\" />—"+
	"<input class=\"numberboxCss2\" name=\"rangeEnd\" style=\"width: 100px;\" /></td>"+
	"<td ><input name=\"integral\"  class=\"numberboxCss\" style=\"width: 100px;\" /></td>"+
	"<td ><input name=\"rate\"  class=\"numberboxCss\" style=\"width: 100px;\" /></td>"+
	"<td>"+
	"<input type=\"hidden\" name=\"type\" value=\""+type2+"\"/>"+
	"<input type=\"hidden\"  class=\"rangeBegin25\" name=\"rangeBegin\" />—"+
	"<input type=\"hidden\"  class=\"rangeEnd25\"   name=\"rangeEnd\"  />"+
	"<input name=\"integral\"  class=\"numberboxCss\" style=\"width: 100px;\" />"+
	"</td>"+
	"<td ><input name=\"rate\"  class=\"numberboxCss\" style=\"width: 100px;\" /></td>"+
	"<td ><a href=\"#\" onclick=\"delConfig(this)\">删除</a></td></tr>";
	$("#"+id).after(config);
	$(".numberboxCss").numberbox({required:true})
	$(".numberboxCss1").numberbox({required:true,
		onChange:function(newValue,oldValue){
			$(this).closest('tr').find("[class='rangeBegin25']").val(newValue);
		}	
	})
	$(".numberboxCss2").numberbox({required:true,
		onChange:function(newValue,oldValue){
			$(this).closest('tr').find("[class='rangeEnd25']").val(newValue);
		}	
	})
}

function save(){
	var curTabIndex = getTabIndex();
	var saveForm = '';
	if(curTabIndex == 0){
		saveForm = 'integralConfigForm_1';
	}else if(curTabIndex == 1){
		saveForm = 'integralConfigForm_2';
	}else if(curTabIndex == 2){
		saveForm = 'integralConfigForm_3';
	}
	if(saveForm == ''){
		$.messager.show({"title":"系统提示","msg":"获取保存表单失败","timeout":1000});
		return;
	}
	 $("#"+saveForm).form('submit',{
		 type:'POST',
		 url:projectName+'/sys/integralConfig/admin/update?bigType=' + curTabIndex,
		 success:function(responseData){
			 if(responseData){
				var data = $.parseJSON(responseData);
			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
			 	if(data.success){
			 		//location.reload();
				}
			 } 
		 }
	 });
}

function reset(){
	var curTabIndex = getTabIndex();
	if(curTabIndex == 0){
		$('#integralConfigForm_1').form('clear');
	}else if(curTabIndex == 1){
		$('#integralConfigForm_2').form('clear');
	}else if(curTabIndex == 2){
		$('#integralConfigForm_3').form('clear');
	}
	
}

function getTabIndex(){
	var tab = $('#tabIntegralConfig').tabs('getSelected');
	var index = $('#tabIntegralConfig').tabs('getTabIndex',tab);
	return index;
}
</script>
</html>