/* 
 * Copyright (C) 2010-2014 星星(wuweihua)<349446658@qq.com>
 * 
 * This file is part of Wabacus 
 * 
 * Wabacus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * 显示My97日期选择框窗口
 */
function showMy97DatepickerBox(datepickerObj)
{
	var boxMetadataObj=getInputboxMetadataObj(datepickerObj.getAttribute('id'));
	if(boxMetadataObj==null) return;
	var datepickerparams=boxMetadataObj.getAttribute('inputboxparams');
	var paramsObj=(datepickerparams==null||datepickerparams=='')?null:getObjectByJsonString(datepickerparams);
	WdatePicker(paramsObj);
}

/**
 * 显示datepicker2日期选择框窗口
 */
function showDatepickerBox2(datepickerObj)
{
	var boxMetadataObj=getInputboxMetadataObj(datepickerObj.getAttribute('id'));
	if(boxMetadataObj==null) return;
	var dateformat=boxMetadataObj.getAttribute('dateformat');
	if(dateformat==null||dateformat=='') dateformat='y-mm-dd';
	showCalendar(datepickerObj.getAttribute('id'),dateformat);
}

/**
 * 初始化富文本编辑器tinyMCE事件
 */
function initTinymce(ed)
{
	ed.getBody().onfocus=function(ed){
		return function(){tinymceBoxOnfocus(ed);};
	}(ed);
	ed.getBody().onblur=function(ed){
		return function(){tinymceBoxOnblur(ed);};
	}(ed);
	ed=null;
}

function tinymceBoxOnfocus(ed)
{
	//var idx=ed.id.indexOf('_wxcol_');//编辑列上的输入框
	//if(idx<=0) idx=ed.id.indexOf('_wxcondition_');//查询条件上的输入框
	//if(idx<=0) return;
	//WX_MAP_INPUTBOXOLDVALUES[getReportGuidByInputboxId(ed.id)+'_'+ed.id]=ed.getContent();//将旧数据存放起来，以便onblur判断是否有数据变更
}

function tinymceBoxOnblur(ed)
{
	var inputboxid=ed.id;
	var idx=inputboxid.indexOf('_wxcol_');//编辑列上的输入框
	if(idx<=0) idx=inputboxid.indexOf('_wxcondition_');//查询条件上的输入框
	if(idx<=0) return;
	var reportguid=getReportGuidByInputboxId(inputboxid);
	var inputboxObj=document.getElementById(inputboxid);
	if(inputboxObj==null) return;
	var parentEleObj=getInputboxParentElementObj(inputboxObj);
	if(parentEleObj==null) return;
	var oldvalue=parentEleObj.getAttribute('oldvalue');
	var newvalue=ed.getContent();
	if(oldvalue==null) oldvalue='';
	if(newvalue==null) newvalue='';
	//alert(newvalue+'       '+oldvalue);
	if(newvalue!=oldvalue) addDataForSaving(reportguid,ed.getBody());//TODO只能用这个接口方法添加，不能用addElementForSaving()
}

var mAllSelectBoxOptionLabelValues=new Object();//存放输入框中所有名值对

/**
 * 在加载页面时显示组合框对象
 */
function showComboxAddOptionsOnload(paramsObj)
{
	if(paramsObj==null||paramsObj.ids==null) return;
	var ids=paramsObj.ids;
	var idsArr=ids.split(';');
	for(var i=0;i<idsArr.length;i++)
	{
		if(idsArr[i]==null||idsArr[i]=='') continue;
		showComboxAddOptionsById(idsArr[i]);
	}
}

function showComboxOnload(paramsObj)
{
	if(paramsObj==null) return;
	showCombox(document.getElementById(paramsObj.id),paramsObj.optiosArr,paramsObj.selectedvalue,paramsObj.autocompleteParamsObj);
}

function showComboxAddOptionsById(selectboxId)
{
	if(selectboxId==null||selectboxId=='') return;
	showComboxAddOptionsByObj(document.getElementById(selectboxId));
}

function showComboxAddOptionsByObj(selectboxObj)
{
	var boxId=selectboxObj.getAttribute('id');
	if(boxId==null||boxId=='') return;
	var boxMetadataObj=getInputboxMetadataObj(boxId);
	if(boxMetadataObj==null) return;
	var optionObjTmp;
	var parentEleObj=getInputboxParentElementObj(selectboxObj);
	var selectedlabel=null,selectedvalue=null;//存放选中选项的label和value
	var reportguid=getReportGuidByInputboxId(boxId);
	if(parentEleObj!=null)
	{//取到本输入框所在列（或条件）的选项选项value和label
		var updateColDestObj=getUpdateColDestObj(parentEleObj,reportguid,parentEleObj);
		selectedvalue=updateColDestObj.getAttribute('value');//本列或条件的值（这里不能调用getColConditionValue()，因为对于直接显示输入框的情况，调此方法会从输入框中取，而此输入框还没显示好）
		if(selectedvalue!=null)
		{
			var updateColSrcObj=getUpdateColSrcObj(parentEleObj,reportguid,null);
			if(updateColSrcObj!==null)
			{//取更新它的列的值做为选项label
				selectedlabel=updateColSrcObj.getAttribute('value');
				if(selectedlabel==null) selectedlabel='';
			}else
			{
				selectedlabel=mAllSelectBoxOptionLabelValues[boxId+'_'+selectedvalue];
				if(selectedlabel==null)
				{//还没在这里存放
					selectedlabel=getNonExistLabelByValue(selectboxObj,selectedvalue);
					mAllSelectBoxOptionLabelValues[boxId+'_'+selectedvalue]=selectedlabel;
				}
			}
		}
	}
	var optionsArr=getSelectBoxOptionsFromMetadata(boxMetadataObj);
	if(optionsArr==null) optionsArr=new Array();
	var isExistSelectedOption=false;//在选项列表中是否存在本列（条件）的值
	for(var i=0;i<optionsArr.length;i++)
	{
		if(selectedvalue!=null&&selectedvalue==optionsArr[i].value) isExistSelectedOption=true;
	}
	if(!isExistSelectedOption&&selectedvalue!=null)
	{//如果所有选项中没有包含本列（条件）的值对应的选项，则将此列的值做为选项新添加进去
		optionObjTmp=new Object();
		optionObjTmp.label=selectedlabel;
		optionObjTmp.value=selectedvalue;
		optionsArr[optionsArr.length]=optionObjTmp;
	}
	showCombox(selectboxObj,optionsArr,selectedvalue,getAutoCompleteParamsObj(boxMetadataObj));
	//var parentIds=boxMetadataObj.getAttribute('parentids');
	//if(parentIds!=null&&parentIds!='') wx_reloadChildSelectBoxOptions(boxId);//如果当前是依赖其它输入框的子组合框，则根据父输入框的值刷新它的选项列表
}

function getAutoCompleteParamsObj(boxMetadataObj)
{
	var autocomplete=boxMetadataObj.getAttribute('autocomplete');
	var autocompleteParamsObj=null;
	if(autocomplete==='true')
	{//如果需要输入联想功能
		var autocompleteParams=boxMetadataObj.getAttribute('autocompleteParams');
		if(autocompleteParams==null||autocompleteParams=='')
		{
			autocompleteParamsObj=new Object();
		}else
		{
			autocompleteParamsObj=getObjectByJsonString(autocompleteParams);
		}
	}
	return autocompleteParamsObj;
}

/**
 *
 * @param optiosArr 选项对象数组，格式为：[{label:"label1",value:"value1"},{label:"label2",value:"value2"},{label:"label3",value:"value3"},...]
 *			如果optiosArr为null或为空，则只显示出组合框，而不向其中添加任何选项
 * @param selectedvalue 选择框选中的选项值，如果为null，则选中第一个选项
 * @param autocompleteParamsObj 如果需要输入联想功能，这里存放输入联想的参数，如果传入null，则不需要输入联想功能
 */
function showCombox(selectboxObj,optiosArr,selectedvalue,autocompleteParamsObj)
{
	if(selectboxObj==null) return;
	var selectedlabel=null;
	if(optiosArr!=null&&optiosArr.length>0)
	{//如果需要向组合框中添加选项
		for(var i=0;i<optiosArr.length;i++)
		{
			selectboxObj.options.add(new Option(optiosArr[i].label,optiosArr[i].value));
			if(selectedvalue!=null&&selectedvalue==optiosArr[i].value) selectedlabel=optiosArr[i].label;
		}
	}
	if(selectboxObj.disabled!==true)
	{//如果组合框没有被禁用
		var realInputboxId=selectboxObj.getAttribute('id');
		$('#'+realInputboxId).selectbox();
		if(autocompleteParamsObj!=null) 
		{
			if(autocompleteParamsObj.minChars==null) autocompleteParamsObj.minChars=1;
			if(autocompleteParamsObj.width==null) autocompleteParamsObj.width=selectboxObj.clientWidth;//一定要调用了$('#'+realInputboxId).selectbox();后才能通过clientWidth取到组合框的真正宽度，否则得到的是不带选项的组合框宽度
			if(autocompleteParamsObj.matchContains==null) autocompleteParamsObj.matchContains=true;
			if(autocompleteParamsObj.autoFill==null) autocompleteParamsObj.autoFill=true;
			if(autocompleteParamsObj.max==null) autocompleteParamsObj.max=15;
			if(autocompleteParamsObj.formatItem==null) autocompleteParamsObj.formatItem=function(row, i, max) {
					return row.label;
				};
			if(autocompleteParamsObj.formatMatch==null) autocompleteParamsObj.formatMatch=function(row, i, max) {
					return row.label;
				};
			if(autocompleteParamsObj.formatResult==null) autocompleteParamsObj.formatResult=function(row) {
					return row.label;
				};
			$('#'+realInputboxId+'inputextofcombox').autocomplete(optiosArr,autocompleteParamsObj);//如果需要输入联想功能
		}
	}
	if(selectedvalue!=null)
	{//指定了选中选项值
		if(selectedlabel==null) selectedlabel=selectedvalue;
		setComboxValue(selectboxObj,selectedvalue,selectedlabel);
	}
}

function getComboxLabelValueById(boxId)
{
	if(boxId==null||boxId=='') return {label:null,value:null};
	return getComboxLabelValue(document.getElementById(boxId));
}

function getComboxLabelValue(boxObj)
{
	var selectboxObj=getSelectBoxObjOfCombox(boxObj);
	if(selectboxObj==null) return {label:null,value:null};
	var txtObj=getTextBoxObjOfCombox(boxObj);
	var txtValue=txtObj.value;
	if(selectboxObj.options!=null&&selectboxObj.options.length>0)
	{
		for(var i=0;i<selectboxObj.options.length;i++)
		{
			if(txtValue==selectboxObj.options[i].text)
			{//找到了文本框对应label的选项
				return {label:txtValue,value:selectboxObj.options[i].value};
			}
		}
	}
	return {label:txtValue,value:getNonExistValueByLabel(selectboxObj,txtValue)};
}

/**
 * 获取在选项列表中不存在的label对应的value
 */
function getNonExistValueByLabel(selectboxObj,label)
{
	var boxId=selectboxObj.getAttribute('id');//最好不要用boxObj.getAttribute('id')，因为文本框的id可能格式有点不合规范
	if(boxId==null||boxId=='') return label;
	var boxMetadataObj=getInputboxMetadataObj(boxId);
	if(boxMetadataObj==null) return label;
	var onGetNonExistValueByLabelMethod=boxMetadataObj.getAttribute('onGetNonExistValueByLabelMethod');
	if(onGetNonExistValueByLabelMethod==null||onGetNonExistValueByLabelMethod=='') return label;
	var onGetNonExistValueByLabelMethodObj=getObjectByJsonString(onGetNonExistValueByLabelMethod);
	var resultStr=onGetNonExistValueByLabelMethodObj.method(selectboxObj,label);
	return resultStr==null?label:resultStr;
}

/**
 * 获取在选项列表中不存在的value对应的label
 */
function getNonExistLabelByValue(selectboxObj,value)
{
	var boxId=selectboxObj.getAttribute('id');//最好不要用boxObj.getAttribute('id')，因为文本框的id可能格式有点不合规范
	if(boxId==null||boxId=='') return value;
	var boxMetadataObj=getInputboxMetadataObj(boxId);
	if(boxMetadataObj==null) return value;
	var onGetNonExistLabelByValueMethod=boxMetadataObj.getAttribute('onGetNonExistLabelByValueMethod');
	if(onGetNonExistLabelByValueMethod==null||onGetNonExistLabelByValueMethod=='') return value;
	var onGetNonExistLabelByValueMethodObj=getObjectByJsonString(onGetNonExistLabelByValueMethod);
	var resultStr=onGetNonExistLabelByValueMethodObj.method(selectboxObj,value);
	return resultStr==null?value:resultStr;
}

/**
 * 设置值的label和value时候如果在选项中不存在，则会在下拉框中增加这个选项
 * @param value 要设置的选项值
 * @param label 要设置的选项的显示字符串，只有当所有选项列表中都不存在value对应的选项，才会用这里的label，否则就会用value对应的选项的label做为显示
 */
function setComboxValueById(boxId,value,label)
{
	if(boxId==null||boxId=='') return;
	setComboxValue(document.getElementById(boxId),label,value);
}

function setComboxValue(boxObj,value,label)
{
	var selectboxObj=getSelectBoxObjOfCombox(boxObj);
	if(selectboxObj==null) return;
	var isExistSelectedOption=false;//是否存在设置value对应的选项
	if(selectboxObj.options!=null&&selectboxObj.options.length>0)
	{
		for(var i=0;i<selectboxObj.options.length;i++)
		{
			if(value==selectboxObj.options[i].value)
			{//找到了文本框对应label的选项
				selectboxObj.options[i].selected=true;
				label=selectboxObj.options[i].text;
				isExistSelectedOption=true;
				break;
			}
		}
	}
	if(label==null) label=value;
	var txtObj=getTextBoxObjOfCombox(boxObj);
	txtObj.value=label;
	if(!isExistSelectedOption)
	{//不存在这个选项，则把它做为新选项加进去
		var optionObjTmp=new Option(label,value);
		selectboxObj.options.add(optionObjTmp);
		optionObjTmp.selected=true;
		mAllSelectBoxOptionLabelValues[selectboxObj.getAttribute('id')+'_'+selectedvalue]=label;
	}
}

function setComboxLabelById(boxId,label)
{
	if(boxId==null||boxId=='') return;
	setComboxLabel(document.getElementById(boxId),label);
}

function setComboxLabel(boxObj,label)
{
	var selectboxObj=getSelectBoxObjOfCombox(boxObj);
	if(selectboxObj==null) return;
	if(label==null) label='';
	var txtObj=getTextBoxObjOfCombox(boxObj);
	if(txtObj!=null) txtObj.value=label;
}

/**
 * 下拉框在onchange时填充对应文本框的数据
 */
function setSelectBoxLabelToTextBoxOnChange(selectboxObj)
{
	if(selectboxObj.options==null||selectboxObj.options.length==0) return;
	var txtObj=document.getElementById(selectboxObj.getAttribute('id')+'inputextofcombox');
	if(txtObj!=null) txtObj.value=selectboxObj.options[selectboxObj.options.selectedIndex].text;
}

/**
 * 获取组合框对应的下拉框对象
 * @param boxObj 可能是下拉框也可能是文本框对象
 */
function getSelectBoxObjOfCombox(boxObj)
{
	if(boxObj==null) return null;
	var id=boxObj.getAttribute('id');
	if(id==null||id=='') return null;
	if(id.lastIndexOf('inputextofcombox')==id.length-'inputextofcombox'.length)
	{
		return document.getElementById(id.substring(0,id.lastIndexOf('inputextofcombox')));
	}
	return boxObj;
}

/**
 * 获取组合框对应的文本框对象
 * @param boxObj 可能是下拉框也可能是文本框对象
 */
function getTextBoxObjOfCombox(boxObj)
{
	if(boxObj==null) return null;
	var id=boxObj.getAttribute('id');
	if(id==null||id=='') return null;
	if(id.lastIndexOf('inputextofcombox')==id.length-'inputextofcombox'.length) return boxObj;
	return document.getElementById(id+'inputextofcombox');
}
