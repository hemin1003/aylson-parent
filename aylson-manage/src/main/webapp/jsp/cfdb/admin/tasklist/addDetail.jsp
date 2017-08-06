<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
</head>
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.6/summernote.css" rel="stylesheet">

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

.radioSpan {
  position: relative;
  border: 1px solid #95B8E7;
  background-color: #fff;
  vertical-align: middle;
  display: inline-block;
  overflow: hidden;
  white-space: nowrap;
  margin: 0;
  padding: 0;
  -moz-border-radius: 5px 5px 5px 5px;
  -webkit-border-radius: 5px 5px 5px 5px;
  border-radius: 5px 5px 5px 5px;
  display:block;
}
</style>
<div align="center" >
	<div class="easyui-tabs" id="tabActivity" style="width:100%">
		 <div title="广告详情配置" style="padding:10px;text-align:center">
		 	<form id="taskDetailConfigForm" method="post">
				<table class="tableForm" style="width:99%;">
					<tr>
						<th>广告详情描述：</th>
						<td colspan="3" style="text-align:left">
							<div id="summernote" style="width:90%">
								${taskDetailVo.taskDesc}
							</div>
						</td>
					</tr>
					<tr>
						<th>
							<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-large-picture',size:'large',iconAlign:'top'" 
								onclick="uploadImgStepUrl('img','stepUrl','yfax-test')" id="uploadImg" style="margin-bottom:10px;">上传详情步骤图</a>
						</th>
						<td colspan="3" style="text-align:left">
							<input id="stepUrl" name="stepUrl" value="${taskDetailVo.stepUrl}" type="hidden"/>
							<div id="stepUrlDiv"></div>
						</td>
					</tr>
					<tr>
						<th>动态审核字段：</th>
						<td colspan="3" style="text-align:left">
							<div id="editor" style="width:90%;height:220px;">
								${taskDetailVo.fields}
							</div>
						</td>
					</tr>
					<tr>
						<th>是否上传图片：</th>
						<td colspan="3" style="text-align:left">
							<span class="radioSpan" style="text-align:left">
								<input type="radio" name="isUploadRd" value="0"><label>否</label></input>
								<input type="radio" name="isUploadRd" value="1"><label>是</label></input>
							</span>
						</td>
					</tr>
					<tr>
						<th>上传图片数量：</th>
						<td colspan="3" style="text-align:left">
							<input name="imagesNum" value="${taskDetailVo.imagesNum}" class="easyui-numberbox"
								style="text-align:left"/>
						</td>
					</tr>
				</table>
				<input name="taskDesc" id="taskDesc" type="hidden" value=""/>
				<input name="fields" id="fields" type="hidden" value=""/>
				<input name="taskId" type="hidden" value="${taskDetailVo.taskId}"/>
				<input id="isUpload" name="isUpload" type="hidden" value="${taskDetailVo.isUpload}"/>
			</form>
		</div>
	</div> 
</div>
<script>
	
var count = 0;	//全局图片标识数量，组合id
var editor = ace.edit("editor");
editor.setTheme("ace/theme/monokai");
editor.getSession().setMode("ace/mode/javascript");
//根据值，设置对应的radio选中
$(":radio[name='isUploadRd'][value='" + $("#isUpload").val() + "']").prop("checked", "checked");
   
//处理详情步骤图
var stepUrl = $('#stepUrl').val();
if(stepUrl){
	var arrays = stepUrl.split("#");
	for(var i=0; i<arrays.length; i++){  
		if(arrays[i]){
			addElementImg("stepUrlDiv", arrays[i], i);
			count++;
		}
	}
}
	
//初始化时先调用一次
finalStepUrl='';

//添加或者删除图片，重建stepUrl的值
function loopImgUrl(){
	finalStepUrl='';
	$("li img").each(function(){
        var src = $(this).attr("src");
        if(finalStepUrl.indexOf(src) == -1){	//如果不存在，则附加
        		finalStepUrl += src+"#";
        }
	});
}

function createEditor(){
	$('#summernote').summernote({
		height: 160,
		focus: true,
		lang: 'zh-CN'   	    		  
	});
}
$("#tabActivity").tabs({
	onSelect:function(title,index){
		if(index == 0){
			createEditor();
			loopImgUrl();
		}
	}
})

function preview(){  
    var simg = $('#stepUrl').val();
    wins = $("<div align='center' style='text-align:center; background:#90A4AE'><img id='simg'/></div>").dialog({
		title:'大图预览',
		width:'95%',
		height:'95%',
		maximizable:true,
		modal:true,
		onClose:function(){
	    		$(this).dialog("destroy");
	    },
	});
    $("#simg").attr("src",simg);  
}
    
//动态附加html元素
function addElementImg(obj, url, id) {
    	var ul = document.getElementById(obj);
    	var li = document.createElement("li");
    	li.id = "li" + id;
    	//添加 a
    	var a = document.createElement("a");
    	a.innerText = "查看大图";
    	a.setAttribute("href","javascript:preview(" + id + ")");
    	li.appendChild(a);
    	//添加 img
    	var img = document.createElement("img");
    	img.setAttribute("style", "width:40px;height:40px");
    	img.src = url;
    	li.appendChild(img);
    	//添加 hidden
    	var input = document.createElement("input");
    	input.type = 'hidden';
    	input.setAttribute("id", id);
    	input.setAttribute("value", url);
    	li.appendChild(input);
    	//添加 del a
    	var del = document.createElement("a");
    	del.innerText = "删除";
    	del.setAttribute("href","javascript:remove(" + id + ")");
    	li.appendChild(del);
    	ul.appendChild(li);
}

function preview(id){
    	var simg = $("#" + id + "").val();
        wins = $("<div align='center' style='text-align:center; background:#90A4AE'><img id='simg'/></div>").dialog({
    		title:'大图预览',
    		width:'95%',
    		height:'95%',
    		maximizable:true,
    		modal:true,
    		onClose:function(){
    	    		$(this).dialog("destroy");
    	    },
    	});
	$("#simg").attr("src",simg);  
}

function remove(id){
	$("#li" + id).remove();
	$("#li" + id).empty();
	loopImgUrl();
}

function uploadImgStepUrl(id,target,bucket){//id:获取上传图片地址的元素,target:目标id
	var win;
	win = $("<div></div>").dialog({
		title:'上传步骤图',
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
			    	$("#bucket").val(bucket);
			    	var filePath = $("#imgName").filebox('getValue');
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
				    			 		$("#"+id).attr({ src: data.data, title: data.data }).show();//系统使用缩略图
				    			 		$("#"+target).val(data.data);//目标字段保存原图
				    			 		//动态附加图
				    			 		addElementImg("stepUrlDiv", $("#"+target).val(), count);
				    					count++;
				    					loopImgUrl();
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
</html>