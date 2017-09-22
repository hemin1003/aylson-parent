<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/resources/inc/meta.jsp"></jsp:include>
<jsp:include page="/resources/inc/easyui.jsp"></jsp:include>
</head>
<body class="easyui-layout" fit="true">
	<div region="north" style="white-space: nowrap;padding: 5px; height: 50px;">
		<div style="margin-bottom: 5px">
			<form id="advDetailForm"  method="post">
				<table class="table_content"   border="0" >
					<tr>
						<td class="tar" >新闻Tab标识：</td>
						<td class="tal" >
							<select name="tagNameLike" class="easyui-combobox"  style="width: 120px;" editable=false>
								<option value="">全部</option>
								<option value="头条">头条</option>
								<option value="社会">社会</option>
								<option value="国内">国内</option>
								<option value="国际">国际</option>
								<option value="笑话">笑话</option>
								<option value="娱乐">娱乐</option>
								<option value="健康">健康</option>
								<option value="体育">体育</option>
								<option value="军事">军事</option>
								<option value="科技">科技</option>
								<option value="财经">财经</option>
								<option value="游戏">游戏</option>
								<option value="汽车">汽车</option>
								<option value="时尚">时尚</option>
							</select>
						</td>
						<td class="tar" >广告来源：</td>
						<td class="tal" >
							<select name="advFkid" class="easyui-combobox"  style="width: 120px;" editable=false>
								<option value="">全部</option>
								<option value="15DF93E1E3623FAACD23">百度</option>
							</select>
						</td>
					    <td style="padding-left:20px">
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px" onclick="doSearch()">搜索</a>
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" style="width:80px" onclick="reset()">重置</a>
						</td> 
					</tr>
				</table>
			</form>
		</div>
	</div> 
	<div data-options="region:'center'" border="false" style="overflow: hidden;width:85%">
    	<table id="datagrid"></table>
    </div>
</body>
<script type="text/javascript" charset="UTF-8" src="<%=request.getContextPath()%>/resources/js/aylson/ytt/advDetail.js?date=2016112516"></script>
</html>