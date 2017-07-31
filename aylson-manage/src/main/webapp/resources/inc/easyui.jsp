<%@ page language="java" pageEncoding="UTF-8"%>
<link href="<%=request.getContextPath()%>/resources/js/jquery-easyui-1.4.2/themes/default/easyui.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/resources/js/jquery-easyui-1.4.2/themes/color.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/resources/js/jquery-easyui-1.4.2/themes/icon.css" rel="stylesheet" type="text/css" media="screen">
<script src="<%=request.getContextPath()%>/resources/js/jquery-easyui-1.4.2/jquery.min.js" charset="UTF-8" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery-easyui-1.4.2/jquery.easyui.min.js" charset="UTF-8" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/jquery-easyui-1.4.2/locale/easyui-lang-zh_CN.js" charset="UTF-8" type="text/javascript"></script>

<script src="<%=request.getContextPath()%>/resources/js/syUtils.js?v_date=201506093" charset="UTF-8" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/sysConfig.js?v_date=201506092" charset="UTF-8" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resources/js/aylson/sys/fileUpload.js?v_date=2015083101" charset="UTF-8" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/resources/css/sys.css?v_date=2015082400" rel="stylesheet" type="text/css" media="screen">

<!-- 两个插件集成，后续优化 -->
<script src="<%=request.getContextPath()%>/resources/plugs/ace/src-min/ace.js" type="text/javascript" charset="utf-8"></script>
<!-- include libraries(jQuery, bootstrap) -->
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script> 
<!-- include summernote css/js-->
<script src="<%=request.getContextPath()%>/resources/plugs/summernote/dist/summernote.js"></script>
<script charset="utf-8" src="<%=request.getContextPath()%>/resources/plugs/summernote/lang/summernote-zh-CN.js"></script>

<script>
//获取项目名称
var pathName=window.document.location.pathname;
//var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);// 如/manage
var projectName ="<%=request.getContextPath()%>";
</script>
