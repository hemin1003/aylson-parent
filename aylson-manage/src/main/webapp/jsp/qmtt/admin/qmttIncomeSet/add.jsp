<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<style>
.param_th{
text-align:center!important;
font-size:14px;
padding:5px;
}
.param_td_oper{
	text-align:center!important;
}
.imgItem_td_1{
	width:60px;
	height:100px;
	padding:2px;
	margin:2px;
}
.imgItem_td_1 img{
	width:60px;
}
.basic_td{
text-align:left!important;
}
</style>
<div align="center" >
	<div class="easyui-tabs" id="tabActivity" style="width:100%">
		 <div title="提现金额配置" style="padding:10px;text-align:center">
		 	<form id="qmttIncomeSetConfigForm" method="post">
				<table class="tableForm" style="width:99%;">
					<tr>
						<th>提现金额：</th>
						<td colspan="3" style="text-align:left">
							<input name="income" value="${qmttIncomeSetVo.income}" data-options="required:true"
								class="easyui-numberbox"
								style="width:95%; text-align:left"/>
						</td>
					</tr>
					<tr>
						<th>提现状态：</th>
						<td colspan="3" style="text-align:left">
							<select id="saleFlagType" name="saleFlagType" class="easyui-combobox" 
								data-options=" 
								onSelect:function(data){
									$('#saleFlag').val(data.value);
								}" 
								style="width:50%; " editable=false>
								<option value="1">下架</option>
								<option value="2">上架</option>
								<option value="3">缺货</option>
							</select>
						</td>
					</tr>
				</table>
				<input id="saleFlag" name="saleFlag" type="hidden" value="${qmttIncomeSetVo.saleFlag}"/>
				<input name="id" type="hidden" value="${qmttIncomeSetVo.id}"/>
			</form>
		</div>
	</div> 
</div>
<script type="text/javascript">
$(function(){
	var select = document.getElementById("saleFlagType");  
	var value = $('#saleFlag').val();  
	for(var i=0; i<select.options.length; i++){  
	    if(select.options[i].value == value){
	        select.options[i].selected = true;  
	        break;  
	    }  
	}  
});
</script>