(function($){
		$.fn.selectbox = function(){
			var idm = $(this).attr("id") || $(this).attr("name");
			var selectboxObj=document.getElementById(idm);
			var onblur=getMethodBodyString(selectboxObj.getAttribute('onblur'),'onblur');
			var onfocus=getMethodBodyString(selectboxObj.getAttribute('onfocus'),'onfocus');
			var onkeypress=getMethodBodyString(selectboxObj.getAttribute('onkeypress'),'onkeypress');
			if($("#" + idm + "div").length <= 0){
				var divHtml = "<div style='display:none' id='" + idm + "div'><input type='text' class='cls-inputbox-normal'  id='" + idm + "inputextofcombox'";
				if(onblur!==null&&onblur!==''){ divHtml+=" onblur=\""+onblur+"\"";}
				if(onfocus!==null&&onfocus!==''){ divHtml+=" onfocus=\""+onfocus+"\"";}
				if(onkeypress!==null&&onkeypress!==''){ divHtml+=" onkeypress=\""+onkeypress+"\"";}
				divHtml+='/></div>';
				$(this).attr("tabindex",-1).after(divHtml);
				
			}
			$("#" + idm + "div").css({position:"absolute",top:$(this).position().top+1  ,left:$(this).position().left}).show();
				$("#" + idm + "inputextofcombox").val($(this).find("option:selected").text()).css({width:$(this).width()-16,height:$(this).height()-2 });
			$("#" + idm + "div").bgIframe();
			return $("#" + idm + "inputextofcombox").val();
		};
		
		$.fn.refreshSelectbox = function(){
			var idm = $(this).attr("id") || $(this).attr("name");
			var selectboxObj=document.getElementById(idm);
			if(selectboxObj.disabled===true)
			{//selectbox is disabled
				var divObj=document.getElementById(idm + "div");
				if(divObj!=null) divObj.style.display='none';
				var txtObj=document.getElementById(idm + "inputextofcombox");
				if(txtObj!=null) txtObj.setAttribute('id',idm + "inputextofcombox_disabled");
			}else
			{
				var txtObj=document.getElementById(idm + "inputextofcombox_disabled");
				if(txtObj!=null) txtObj.setAttribute('id',idm + "inputextofcombox");
				$("#" + idm + "div").css({position:"absolute",top:$(this).position().top+1  ,left:$(this).position().left}).show();
					$("#" + idm + "inputextofcombox").val($(this).find("option:selected").text()).css({width:$(this).width()-16,height:$(this).height()-2 });
				$("#" + idm + "div").bgIframe();
				if(selectboxObj.options!=null&&selectboxObj.options.length>0)
				{
					txtObj=document.getElementById(idm+'inputextofcombox');
					if(txtObj!=null) txtObj.value=selectboxObj.options[selectboxObj.options.selectedIndex].text;
				}
			}
		};
		
		function getMethodBodyString(methodObj,methodName)
		{
			if(methodObj===null||methodObj===''){ return null;}
			var methodStr=methodObj.toString();
			var tmp=methodStr.toLowerCase();
			if(tmp.indexOf('function ')===0&&tmp.indexOf(methodName)>0&&tmp.indexOf('{')>0&&tmp.indexOf('}')>0)
			{//get a function body,parse and get code inside(ie)
				methodStr=methodStr.substring(methodStr.indexOf('{')+1,methodStr.lastIndexOf('}'));
			}
			return methodStr;
		}
})(jQuery);