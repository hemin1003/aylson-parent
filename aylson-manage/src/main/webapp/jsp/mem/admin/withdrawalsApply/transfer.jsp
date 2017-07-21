<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
.ul_1 li{
	list-style-type:none;
	float:left;
	width:50px;
	margin:0 5px;
}
.ul_1 li a{
	text-decoration:none;
}
</style>
<div align="center">
	<form id="withdrawalsApplyForm" method="post">
		<table class="tableForm">
			<tr>
				<th>申请人：</th>
				<td>
					<input  name="applier" value="${withdrawalsApplyVo.applier}" style="width:200px"
					class="easyui-textbox" readonly="true"/>
				</td>
				<th>申请人电话：</th>
				<td>
					<input name="applierPhone" value="${withdrawalsApplyVo.applierPhone}" style="width:200px"
					class="easyui-textbox" readonly="true"/>
				</td>
			</tr>
			<tr>
				<th>提现金额：￥</th>
				<td>
					<input name="amount" value="${withdrawalsApplyVo.amount}" style="width:200px"
					class="easyui-numberbox" readonly="true"/>
				</td>
				<th>银行卡号：</th>
				<td>
					<input name="bankNum" value="${withdrawalsApplyVo.bankNum}" style="width:200px"
					class="easyui-textbox" readonly="true"/>
				</td>
			</tr>
			<tr>
				<th>
				<a href="javascript:void(0)" class="easyui-linkbutton c8" onclick="uploadTransferProof('transferItem','transferProof','dc-transferproof')" id="uploadFile" style="padding:2px;margin:5px">上传转账凭证</a>
				</th>
				<td colspan="3">
					<input id="transferProof" type="hidden" name="transferProof" value="${withdrawalsApplyVo.transferProof}"/> 
					<ul class="ul_1" id="transferItem">
						
					</ul>
					
				</td>
			</tr>
			<tr>
				<th>备注：</th>
				<td colspan="3"><input id="remark" name="remark" value="${withdrawalsApplyVo.remark}"
					class="easyui-textbox" 	multiline="true" style="width: 400px;height:100px;" 
					prompt="转账失败必须备注原因"/>
				</td>
			</tr>
		</table>
		<input name="id" type="hidden" value="${withdrawalsApplyVo.id}"/>
		<input name="applierId" type="hidden" value="${withdrawalsApplyVo.applierId}"/>
		<input name="wxOpenId" type="hidden" value="${withdrawalsApplyVo.wxOpenId}"/>
	</form>
</div>
<div id="divImage" style="display:none; left: 220px;top:-200px; position: absolute">
        <img id="imgBig" src="" alt="预览" />
</div>
<script>
	$(function(){
		viewImg();
		resetTransferProof();
	})
	
</script>