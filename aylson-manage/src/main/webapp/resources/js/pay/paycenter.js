/**
 * 支付中心
 */
function isPositiveInteger(str)
{
	var re = /^[1-9]+[0-9]*]*$/;
	if(re.test(str)){
		return true;
	}
	return false;
}

function isExistAccount(wvid){
	$('#userid').val(wvid);
	$("#validateaccountfrom").form("submit",{
		success: function(data){
			var d = $.parseJSON(data);
			if(d.success==true){
				$("#validateinfo").html("（账号有效）");
				$("#validateinfo").css("color","green");
				$("#validateinfo").show();
			}else{
				$("#validateinfo").html("（账号不存在）");
				$("#validateinfo").css("color","red");
				$("#validateinfo").show();
			}
		}
	});
}

function radiocheck(index){
	$('input:radio[name=50]')[index].checked = true;
}

function bankcheck(index){
	$('input:radio[name=yinhang]')[index].checked = true;
}

function isCunstomPay(){
	var val=$('input:radio[name=50]:checked').val();
	if(val==null){
		alert("请选择充值金额");
		return false;
	}else{
		if(val==0){
			return true;
		}else{
			return false;
		}
	}
}

$(function() {	
	//点击下一步按钮
	$('#next_btn').click(function(){
		var wvid = $('#wvid').val();
		
		if (wvid==""){
			alert('请输入您的世界观ID');
			$('#wvid').focus().select();
			return;
		}
		if (!isPositiveInteger(wvid)) {
    		alert('世界观ID不正确');
    		$('#wvid').focus();
    		return;
    	}
		
		//获取金额
		var price = $('input:radio:checked').val();
		if(price==0){
			price = $('#accountmoney').val();
		}
		if (typeof(price) == "undefined")
		{
		    alert('请选择充值金额');
		    return;
		}
		if (!isPositiveInteger(price)) {
    		alert('金额不正确,必须为整数');
    		return;
    	}
		
		if (isCunstomPay()) {
    		if(price.length > 0 && parseInt(price) <= 500){
    			alert("自定义充值金额需大于500元");
        		$("#accountmoney").focus();
        		return;
    		}
    	}
		
		if (parseInt(price) > 100000) {
    		alert("自定义充值金额不能大于100000元");
    		$("#accountmoney").focus();
    		return;
    	}
		
		$('#userid').val(wvid);
		$("#validateaccountfrom").form("submit",{
			success: function(data){
				var d = $.parseJSON(data);
				if (d.success==true){
					$('#memberId').val(wvid);
					$('#price').val(price);
					var form = $('#submitfrom');
			    	form.submit();
				}else{
					alert("世界观ID不存在");
					$('#wvid').focus();
		    		return;
				}
			}
		});
	});
	
	
	
	//提交支付
	$('#submitpay').click(function(){
		//获取银行代号
		var paymenttype = $('input:radio:checked').val();
		if (typeof(paymenttype) == "undefined")
		{
		    alert('请选择支付银行','提示');
		    return;
		}

		$('#PaymentType').val(paymenttype);
		var form = $('#payform');
    	form.submit();
	});
	
	$("#accountmoney").click(function(){
		$('input:radio[name=50]')[5].checked = true;
	});
	
	
	$("#wvid").keyup(function(event){
		var keycode = event.which;  
	    if (keycode == 13) {  
	    	isExistAccount($('#wvid').val());
	    } 
	});
	
	$("#accountmoney").keyup(function(){
		var pay_money = $("#accountmoney").val();
		if(isNaN(pay_money)){
			$("#tips").css("color","red");
			$("#tips").html("（自定义金额必须为数字）");
			$("#tips").show();
		}else{
			if(parseInt(pay_money) > 500){
				$("#tips").html("（" + Math.floor(parseInt(pay_money)*100)+ " <img src='../resources/images/pay/14pxyezi.png'/>）");
			}else{
				$("#tips").html("（自定义金额需大于500元,且是整数）");
			}
			if(parseInt(pay_money) > 100000){
				$("#tips").html("（自定义金额不能大于100000元）");
			}
		}
		if(pay_money==""){
			$("#tips").html("（自定义金额需大于500元,且是整数）");
		}
	});
	
});

