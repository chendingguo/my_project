<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Basic DataGrid - jQuery EasyUI Demo</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/style.css">
<script type="text/javascript" src="<%=basePath%>js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.easyui.min.js"></script>

<script type="text/javascript" src="<%=basePath%>js/host.js"></script>
</head>
<body onload="createTable()">
	<input type="hidden" id="basePath" value=<%=basePath%> />
	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newHost()">New
			Host</a> <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editHost()">Edit
			Host</a> <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
			onclick="removeHost()">Remove Host</a>
	</div>
	<table id="dg" class="datagrid" style="width:600; height: 250px">
		<div id="toolbar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newHost()">New
				Host</a> <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editHost()">Edit
				Host</a> <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
				onclick="removeHost()">Remove Host</a>
		</div>

	</table>




	<!-- modify host -->
	<div id="dlg" class="easyui-dialog" style="width: 400px; height: 280px; padding: 10px 20px"
		closed="true" buttons="#dlg-buttons">
		<div class="ftitle">Host Information</div>
		<form id="fm" method="post">
			<div class="fitem">
				<label>Host Ip:</label> <input name="hostIp" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>Host Name:</label> <input name="hostName" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>Host Desc:</label> <input name="hostDesc">
			</div>

		</form>
	</div>
	
	<div id="dlg-buttons">
		 <a href="#" id="save-host" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveHost()">Save</a>
		<a href="#" id="update-host" class="easyui-linkbutton" iconCls="icon-ok" onclick="updateHost()">Update</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
	</div>
</body>
</html>