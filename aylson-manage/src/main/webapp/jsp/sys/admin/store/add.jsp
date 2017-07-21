<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
.imgItem_td_1{
	width:150px;
	height:100px;
	padding:2px;
	margin:2px;
}
.imgItem_td_1 img{
	width:150px;
}
</style>
<div align="center">
	<form id="storeForm" method="post">
		<div class="easyui-tabs" id="tabStore" style="width:99%">
		 <div title="门店信息" style="padding:10px;text-align:left">
			<table class="tableForm" style="width:95%;">
				<tr>
					<th colspan="2" style="text-align:center;">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-large-picture',size:'large',iconAlign:'top'"onclick="uploadImg('img','thumbnail','dc-test')" id="uploadImg" style="margin-bottom:10px;">添加缩略图</a>
					</th>
					<td colspan="2" rowspan="5">
						<div style="border-style:solid;border-width:1px;width:100%;height:200px">
						<c:if test="${not empty storeVo.thumbnail}"><img id="img" src="${storeVo.thumbnail}" style="width:100%;height:100%"/></c:if>
						<c:if test="${empty storeVo.thumbnail }"><img id="img" src="" style="width:100%;height:100%"/></c:if>
						<input id="thumbnail" name="thumbnail" value="${storeVo.thumbnail}" type="hidden"/>
						</div> 
					</td>
				</tr>
				<tr>
					<th>序号：</th>
					<td><input name="seq" value="${storeVo.seq}"
					class="easyui-numberbox" 
					style="width: 200px;" /></td>
				</tr>
				<tr>
					<th>门店名称：</th>
					<td><input name="storeName" value="${storeVo.storeName}"
					class="easyui-textbox" data-options="required:true"
					style="width: 200px;" /></td>
				</tr>
				<tr>
					<th>营业时间：</th>
					<td><input name="openTime" value="${storeVo.openTime}"
					class="easyui-textbox" data-options="required:true"
					style="width: 200px;" /></td>
				</tr>
				<tr>
					<th>工作电话：</th>
					<td><input name="workPhone" value="${storeVo.workPhone}"
						class="easyui-textbox" style="width: 200px;" /></td>
				</tr>
				<tr>
					<th>经度：</th>
					<td><input name="longitude" id="longitude" value="${storeVo.longitude}"
						class="easyui-numberbox" precision="4" style="width: 200px;" /></td>
					<th>纬度：</th>
					<td><input name="latitude" id="latitude" value="${storeVo.latitude}"
						class="easyui-numberbox" precision="4" style="width: 200px;" /></td>
				</tr>
				<tr>
					<th>门店地址：</th>
					<td colspan="3"><input name="address" id="address" value="${storeVo.address}"
						class="easyui-textbox" 	style="width: 400px;" />
					<a href="#" title="说明：通过地址获取经纬度或多或少会有所偏差，可以通过网页查询手动精确定位" class="easyui-linkbutton c8" onclick="getGps()">获取经纬度</a>	
					<a href="http://map.yanue.net/toLatLng/" target="_blank" class="easyui-linkbutton c8" onclick="">网页查询</a>	
					</td>
				</tr>
				<tr>
					<th>所属门店用户：</th>
					<td colspan="3">
					<input  name="agentInfoId" value="${storeVo.agentInfoId}" 
					data-options=" url:'<%=request.getContextPath()%>/sys/agentUser/getList', valueField:'id', textField:'agentDesc', method:'GET'" class="easyui-combobox" style="width: 600px;" prompt="-请选择门店所属系统用户，支持输入关键字进行过滤，如：广州-"/>
					</td>
				</tr>
				<tr>
					<th>门店介绍：</th>
					<td colspan="3"><input name="introduce" value="${storeVo.introduce}"
						class="easyui-textbox" multiline="true"	style="width: 400px;height:100px"/></td>
				</tr>
			</table> 
		</div>
		<div title="门店图片" style="padding:10px">
			<table class="tableForm">
			<tr><th colspan="10" style="text-align:left;padding:5px;">
				<a href="#" class="easyui-linkbutton c1" onclick="addImg()" style="width:100px">添加</a>
				</th></tr>
				<tr style="text-align:center">
					<c:if test="${!empty storeVo.storeImgList }">
						<c:forEach items="${storeVo.storeImgList}" var="item">
							<td class="imgItem_td_1">
								<img  src="${item}"  />
								<input name="storeImgItem" value="${item}" type="hidden"/>
								<a href="#" onclick="delImg(this)" class="easyui-linkbutton c8" style="width:120px;">删除</a>
							</td>
						</c:forEach>
					</c:if>
					<td class="imgItem_td_2"></td>
				</tr>
			</table>
		</div>
	</div> 
		<input name="id" type="hidden" value="${storeVo.id}"/>
	</form>
</div>
<script>
function delImg(obj){
	$(obj).parent().remove();
}

function addImg(){//id:获取上传图片地址的元素,target:目标id
	var win;
	win = $("<div></div>").dialog({
		title:'上传图片',
		width:500,
		height:200,
		modal:true,
		href:projectName+'/sys/fileHandle/toImgUpload',
		onClose:function(){
			$(this).dialog("destroy");
		},
		buttons:[{
			text:'确定',
		    iconCls:'icon-ok',
		    handler:function(){
		    	var winTip = $.messager.progress({
					title:'请稍候...',
					msg:'正在上传...'
				});
		    	var filePath = $("#imgName").filebox('getValue');
		    	$("#bucket").val("dc-test");
		    	if(imgTypeCheck(filePath)){
			    	$("#imgUploadForm").form('submit',{
			    		 type:'POST',
			    		 url:projectName+'/sys/fileHandle/imgUpload',
			    		 success:function(responseData){
			    			 $.messager.progress('close');
			    			 if(responseData){
			    				var data = $.parseJSON(responseData);
			    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
			    			 	if(data.success){
			    			 		//$("#"+id).attr({ src: data.data, title: data.data }).show();//系统使用缩略图
			    			 		//$("#"+target).val(data.data);//目标字段保存原图
			    			 		var param = "";
			    			 		param += "	<td class='imgItem_td_1'>";
			    			 		param += "		<img  src='"+data.data+"' />";
			    			 		param += "		<input name='storeImgItem' value='"+data.data+"' type='hidden'/>";
			    			 		param += "		<a href='#'class='buttonCss' onclick='delImg(this)' class='easyui-linkbutton c8' style='width:120px'>删除</a>";
			    			 		param += "	</td>";
			    			 		$(".imgItem_td_2").before(param);
			    			 		$(".textboxCss").textbox({required:true})
			    			 		$(".buttonCss").linkbutton();
			    			 		$(".buttonCss").addClass("c8");
			    			 		win.dialog('destroy');
			    				}
			    			 } 
			    		 }
			    	});
		    	}
		     }   
		   },{
			 text:'取消',
		     iconCls:'icon-cancel',  
		 	 handler:function(){
		 		 win.dialog('destroy');
		 	 }   
		  }]
	});
}


</script>