<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <!-- 引入 echarts.js -->
    <script src="<%=request.getContextPath()%>/resources/js/echarts/echarts.js"></script>
	<jsp:include page="/resources/inc/meta.jsp"></jsp:include>
	<jsp:include page="/resources/inc/easyui.jsp"></jsp:include>
</head>
<body class="easyui-layout" fit="true">
	<div region="north" style="white-space: nowrap;padding: 5px; height: 100px;">
		<div style="margin-bottom: 5px">
			<form id="yttSysReportInfoForm"  method="post">
				<table class="table_content" border="0" >
					<tr>
						<td class="tar" >今日用户金币余额：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="todayGold" id="todayGold"/>
						</td>
						<td class="tar" >昨日用户金币余额：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="yesterdayGold" id="yesterdayGold"/>
						</td>
						<td class="tar" >用户总金币余额：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="allIncome" id="allIncome"/>
						</td>
						<td class="tar" >用户总零钱余额：</td>
						<td class="tal" >
							<input class="easyui-textbox" name="allBalance" id="allBalance"/>
						</td>
					</tr>
					<tr>
						<td class="tar" colspan="3">
							<br /><br />
							<font size=3 bold>统计趋势</font>
						</td> 
					    <td class="tar" colspan="3">
					    		<br /><br />
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px" onclick="doSearch(7)">7天</a>
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px" onclick="doSearch(30)">30天</a>
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px" onclick="doSearch(60)">60天</a>
						</td> 
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div data-options="region:'center'" border="false" style="overflow: hidden;width:85%;">
		<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    		<div id="main" style="width: 100%; height: 320px;"></div>
    		<div style="width: 100%;height: 50%;">
    			<table id="datagrid"></table>
    		</div>
	</div>
</body>
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/aylson/ytt/sysReportInfo.js?date=2016112516"></script>
 <script type="text/javascript">
      // 基于准备好的dom，初始化echarts实例
      var myChart = echarts.init(document.getElementById('main'));

      option = {
      	    title: {
      	        text: ''
      	    },
      	    tooltip: {
      	        trigger: 'axis'
      	    },
      	    legend: {
      	    		data:['新增用户数','用户金币余额','用户零钱金额','用户提现金额','用户浏览新闻数']
      	    },
      	    grid: {
      	        left: '3%',
      	        right: '4%',
      	        bottom: '3%',
      	        containLabel: true
      	    },
      	    toolbox: {
      	        feature: {
      	            saveAsImage: {}
      	        }
      	    },
      	    xAxis: {
      	        type: 'category',
      	        boundaryGap: false,
      	        data: []
      	    },
      	    yAxis: {
      	        type: 'value'
      	    },
      	    series: [
      	        {
      	            name:'新增用户数',
      	            type:'line',
      	            stack: '总量',
      	            data:[]
      	        },
      	        {
      	            name:'用户金币余额',
      	            type:'line',
      	            stack: '总量',
      	            data:[]
      	        },
      	        {
      	            name:'用户零钱金额',
      	            type:'line',
      	            stack: '总量',
      	            data:[]
      	        },
      	        {
      	            name:'用户提现金额',
      	            type:'line',
      	            stack: '总量',
      	            data:[]
      	        },
      	        {
      	            name:'用户浏览新闻数',
      	            type:'line',
      	            stack: '总量',
      	            data:[]
      	        }
      	    ]
      	};
      
      //获取折图形数据
      function getMapData(num){
    	  	  myChart.showLoading();
       	  // 异步加载数据
          $.get(projectName+'/ytt/yttSysReportInfo/admin/listMap?num=' + num).done(function (data) {
          		myChart.hideLoading();
          		var dataObj = jQuery.parseJSON(data);
    	          // 填入数据
    	          myChart.setOption({
    	              xAxis: {
    	                  data: dataObj.categories
    	              },
    	              series: [{
    	   	            name : '新增用户数',
    	   	            data : dataObj.value1
    	   	        },
    	   	        {
    	   	            name : '用户金币余额',
    	   	            data : dataObj.value2
    	   	        },
    	   	        {
    	   	            name : '用户零钱金额',
    	   	            data : dataObj.value3
    	   	        },
    	   	        {
    	   	            name : '用户提现金额',
    	   	            data : dataObj.value4
    	   	        },
    	   	        {
    	   	            name : '用户浏览新闻数',
    	   	            data : dataObj.value5
    	   	        }]
    	          });
          });
      }
   	  
      // 异步加载数据
      $.get(projectName+'/ytt/yttSysReportInfo/admin/listTop').done(function (data) {
      		var dataObj = jQuery.parseJSON(data);
      		$("#todayGold").textbox('setValue', dataObj.todayGold);
      		$("#yesterdayGold").textbox('setValue', dataObj.yesterdayGold);
      		$("#allIncome").textbox('setValue', dataObj.allIncome);
      		$("#allBalance").textbox('setValue', dataObj.allBalance);
      });

      getMapData(7);
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
  </script>
</html>