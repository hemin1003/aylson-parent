<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>ACE in Action</title>
<style type="text/css" media="screen">
    #editor { 
        position: absolute;
        top: 200px;
        right: 200px;
        bottom: 200px;
        left: 200px;
    }
</style>
</head>
<body>

<div id="editor">
[{
  "name": "name",
  "label": "姓名",
  "type": "string",
  "placeholder": "请输入姓名"
},{
  "name": "mobile",
  "type": "string",
  "label": "手机号",
  "placeholder": "请输入手机号"
}]
</div>

<div><button onclick="test()">点我点我点我</button></div>

<script src="<%=request.getContextPath()%>/resources/plugs/ace/src-noconflict/ace.js" type="text/javascript" charset="utf-8"></script>
<script>
    var editor = ace.edit("editor");
    editor.setTheme("ace/theme/monokai");
    editor.getSession().setMode("ace/mode/json");
    editor.resize();
    function test(){
		alert(editor.getValue());
	}
</script>
</body>
</html>