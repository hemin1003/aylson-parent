<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/plugs/kindeditor/kindeditor-4.1.10/themes/default/default.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/plugs/kindeditor/kindeditor-4.1.10/plugins/code/prettify.css" />
<script charset="utf-8" src="<%=request.getContextPath()%>/resources/plugs/kindeditor/kindeditor-4.1.10/kindeditor.js"></script>
<script charset="utf-8" src="<%=request.getContextPath()%>/resources/plugs/kindeditor/kindeditor-4.1.10/lang/zh_CN.js"></script>
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
	width:150px;
	height:100px;
	padding:2px;
	margin:2px;
}
.imgItem_td_1 img{
	width:150px;
}
.basic_td{
text-align:left!important;
}
</style>
<script type="text/javascript">

/* $(document).ready(function(){
    //do something
	createEditor();
}) */
/* $(function(){
   window.editor = KindEditor.create('#editor_id',{
             resizeType:1,      
             urlType:projectName, // 带有域名的绝对路径
             allowFileManager : false,
             allowImageUpload : true,
             uploadJson : projectName+'/sys/fileHandle/kindeditorUpload?bucket=dc-gift',
             //uploadJson:projectName+'/resources/plugs/kindeditor/kindeditor-4.1.10/jsp/upload_json.jsp',
			 items : [
					'source', '|','fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
					'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
					'insertunorderedlist', '|', 'emoticons', 'image', 'link','fullscreen'], 
					
	});
}); */
function createEditor(){
	 window.editor = KindEditor.create('#editor_id',{
         resizeType:1,      
         urlType:projectName, // 带有域名的绝对路径
         allowFileManager : false,
         allowImageUpload : true,
         uploadJson : projectName+'/sys/fileHandle/kindeditorUpload?bucket=dc-gift',
		 items : [
				'source', '|','fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
				'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
				'insertunorderedlist', '|', 'emoticons', 'image', 'link','fullscreen'], 
				
	});
}
$("#tabGift").tabs({
	onSelect:function(title,index){
		if(index == 3){
			createEditor();
		}
	}
})
</script>
<div align="center" >
<form id="giftConfigForm" method="post">
	<div class="easyui-tabs" id="tabGift" style="width:100%">
		 <div title="基本信息" style="padding:10px;text-align:center">
			<table class="tableForm" style="width:75%;">
				<tr>
					<th colspan="2" style="text-align:center;">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-large-picture',size:'large',iconAlign:'top'"onclick="uploadImg('img','imgUrl','dc-gift')" id="uploadImg" style="margin-bottom:10px;">添加标题图片</a>
					</th>
					<td colspan="2" rowspan="6">
						<div style="border-style:solid;border-width:1px;width:300px;height:100%">
						<c:if test="${not empty giftConfigVo.imgUrl}"><img id="img" src="${giftConfigVo.imgUrl}" style="width:300px;height:100%"/></c:if>
						<c:if test="${empty giftConfigVo.imgUrl }"><img id="img" src="" style="width:300px;height:100%"/></c:if>
						<input id="imgUrl" name="imgUrl" value="${giftConfigVo.imgUrl}" type="hidden"/>
						</div>
					</td>
				</tr>
				<tr>
					<th>礼品名称：</th>
					<td class="basic_td">
						<input name="giftName" value="${giftConfigVo.giftName}"
						class="easyui-textbox" data-options="required:true"
						style="width: 200px;" />
					</td>
				</tr>
				<tr>
					<th>礼品编码：</th>
					<td class="basic_td"><input name="giftCode" value="${giftConfigVo.giftCode}"
						class="easyui-textbox" data-options="required:true"
						style="width: 200px;" />
					</td>
				</tr>
				<tr>
					<th>所需积分：</th>
					<td class="basic_td"><input name="integral" value="${giftConfigVo.integral}"
						class="easyui-numberbox" data-options="min:0,required:true"
						style="width: 200px;" />
					</td>
				</tr>
				<tr>
					<th>适用兑换对象：</th>
					<td class="basic_td">
						<select id="matchObj" name="matchObj" class="easyui-combobox"  style="width: 200px;" editable=false
						data-options="onSelect:function(record){
								if(record.value == 10){
									$('#matchActivityId').combobox({readonly:false,required:true});
								}else{
									$('#matchActivityId').combobox({readonly:true,required:false});
									$('#matchActivityId').combobox('setValue','');
									$('#matchActivityCode').val('');
									$('#matchActivity').val('');
								}
							}"
						>
								<option value="0">全部</option>
							<c:if test="${!empty matchObjType }">
								<c:forEach items="${matchObjType}" var="item">
									<option value="${item.key}" <c:if test = "${item.key == giftConfigVo.matchObj }">selected = "selected" </c:if>>${item.value }</option>
								</c:forEach>
							</c:if>
						</select>
					</td>
				</tr>
				<tr>
					<th>适用活动类型：</th>
					<td class="basic_td">
						<input  id="matchActivityId" name="matchActivityId" value="${giftConfigVo.matchActivityId}" 
							data-options="readonly:true,editable:false, url:'<%=request.getContextPath()%>/sys/dictionary/getList?dicType=MatchActivity_item', valueField:'id', textField:'dicName', method:'GET',
							onSelect:function(record){
								$('#matchActivityCode').val(record.dicValue);
								$('#matchActivity').val(record.dicName);
							}
							" class="easyui-combobox" style="width: 200px;" prompt="-全部-"/>
							<input type="hidden" id="matchActivityCode" name="matchActivityCode" value="${giftConfigVo.matchActivityCode}"/>
							<input type="hidden" id="matchActivity" name="matchActivity" value="${giftConfigVo.matchActivity}"/>
					</td>
				</tr>
				<tr>
					<td colspan="4">
					<font color=red>
					说明：
					</font>
					</td>
				</tr>
			</table>
		</div>
		<div title="规格参数" style="padding:10px">
			<table class="tableForm" id="params">
				<tr>
				<th colspan="10" style="text-align:left;padding:5px;">
				<a href="#" onClick="addParams()" class="easyui-linkbutton c1" style="width:100px">添加参数</a>
				</th>
				</tr>
				<tr><th class="param_th">参数名</th><th class="param_th">参数值</th><th class="param_th">操作</th></tr>
				<c:if test="${!empty giftConfigVo.parameterMap }">
						<c:forEach items="${giftConfigVo.parameterMap}" var="item">
							<tr>
							<th><input  name='paramTitle' value='${item.key}'class='easyui-textbox' data-options='required:true'	style='width: 200px;' />：</th>
							<td><input  name='paramValue' value='${item.value }'class='easyui-textbox' data-options='required:true'	style='width: 200px;' /></td>
							<td><a href='#' onclick='delParam(this)' class='easyui-linkbutton c8' style='width:100px'>删除</a></td>
							</tr>
						</c:forEach>
				</c:if>
			</table>
		</div>
		<div title="图片介绍" style="padding:10px">
			<table class="tableForm">
			<tr><th colspan="10" style="text-align:left;padding:5px;">
				<a href="#" class="easyui-linkbutton c1" onclick="addImg()" style="width:100px">添加</a>
				</th></tr>
				<tr style="text-align:center">
					<c:if test="${!empty giftConfigVo.imgNavigationAddress }">
						<c:forEach items="${giftConfigVo.imgNavigationAddress}" var="item">
							<td class="imgItem_td_1">
								<img  src="${item}"  />
								<input name="imgNavigationItem" value="${item}" type="hidden"/>
								<a href="#" onclick="delImg(this)" class="easyui-linkbutton c8" style="width:120px;">删除</a>
							</td>
						</c:forEach>
					</c:if>
					<td class="imgItem_td_2"></td>
				</tr>
			</table>
		</div>
		<div title="详细描述" style="padding:10px">
			<table class="tableForm">
				<tr>
					<td  >
						<textarea id="editor_id" name="content" style="width:700px;height:300px;">
							${giftConfigVo.description}
						</textarea>
					</td>
				</tr>
			</table>
		</div>
	</div> 
	<input name="id" type="hidden" value="${giftConfigVo.id}"/>
	<input name="description" id="description" type="hidden" value=""/>
</form>
</div>
<script>
function delParam(obj){
	$(obj).parent().parent().remove();
}
function addParams(){
	var param = "";
	param += "<tr>";
	param += "	<th>";
	param += "		<input class='textboxCss' name='paramTitle' value=''class='easyui-textbox' data-options='required:true'	style='width: 200px;' />：";
	param += "	</th>";
	param += "	<td>";
	param += "		<input class='textboxCss' name='paramValue' value=''class='easyui-textbox' data-options='required:true'	style='width: 200px;' />";
	param += "	</td>";
	param += "	<td class='param_td_oper'>";
	param += "		<a href='#'class='buttonCss' onclick='delParam(this)' class='easyui-linkbutton c8' style='width:100px'>删除</a>";
	param += "	</td>";
	param += "</tr>";
	$("#params").append(param);
	$(".textboxCss").textbox({required:true})
	$(".buttonCss").linkbutton();
	$(".buttonCss").addClass("c8");
}

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
		    	var filePath = $("#imgName").filebox('getValue');
		    	$("#bucket").val("dc-gift");
		    	if(imgTypeCheck(filePath)){
			    	$("#imgUploadForm").form('submit',{
			    		 type:'POST',
			    		 url:projectName+'/sys/fileHandle/imgUpload',
			    		 success:function(responseData){
			    			 if(responseData){
			    				var data = $.parseJSON(responseData);
			    			 	$.messager.show({"title":"系统提示","msg":data.message,"timeout":1000});
			    			 	if(data.success){
			    			 		//$("#"+id).attr({ src: data.data, title: data.data }).show();//系统使用缩略图
			    			 		//$("#"+target).val(data.data);//目标字段保存原图
			    			 		var param = "";
			    			 		param += "	<td class='imgItem_td_1'>";
			    			 		param += "		<img  src='"+data.data+"' />";
			    			 		param += "		<input name='imgNavigationItem' value='"+data.data+"' type='hidden'/>";
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