var rootPath = getRootPath();
function saveHost() {

	$.ajax({
		type : "POST",
		url : rootPath + '/host!insertHost.action',
		cache : false,
		data : $("form").serialize(),
		dataType : "json",
		success : function(retObj) {

			if (retObj.operationResult > 0) {
				alert("Success Insert");
				$('#dlg').dialog('close');
				$('#dg').datagrid('reload');
			} else {

			}

		},
		error : function(XMLHttpRequest) {

			$.messager.show({ // show error message
				title : 'Error',
				msg : 'Failed of insert'
			});
		}

	});

}


function updateHost() {
  
	$.ajax({
		type : "POST",
		url : rootPath + '/host!updateHost.action',
		cache : false,
		data : $("form").serialize(),
		dataType : "json",
		success : function(retObj) {

			if (retObj.operationResult > 0) {
				alert("Success update");
				$('#dlg').dialog('close');
				$('#dg').datagrid('reload');
			} else {

			}

		},
		error : function(XMLHttpRequest) {

			$.messager.show({ // show error message
				title : 'Error',
				msg : 'Failed of update'
			});
		}

	});

}

function createTable() {

	var columnsStr = [ [ {
		field : 'hostIp',
		title : 'Host Ip',
		width : 200
	}, {
		field : 'hostName',
		title : 'Host Name',
		width : 200
	}, {
		field : 'hostDesc',
		title : 'Host Desc',
		width : 200,
		align : 'right'
	} ] ];

	var url = rootPath + "/host!getList.action";
	$('#dg').datagrid({
		url : url,
		columns : columnsStr,
		loadMsg : '数据正在加载中,请稍后。。。'

	});

}

function newHost() {
	$('#dlg').dialog('open').dialog('setTitle', 'New Host');
	
	$('#fm').form('clear');
	$('#save-host').show();
	$('#update-host').hide();

}
function removeHost() {
	var row = $('#dg').datagrid('getSelected');
	var hostIp = row.hostIp;
	if (row == null) {
		$.messager.show({ // show error message
			title : 'Error',
			msg : 'Please choose the host to delete! '
		});
	}
	$.ajax({
		type : "POST",
		url : rootPath + '/host!deleteHost.action',
		cache : false,
		data : "hostIp=" + hostIp,
		dataType : "json",
		success : function(retObj) {

			if (retObj.operationResult > 0) {
				alert("Success delete");
				$('#dlg').dialog('close');
				$('#dg').datagrid('reload');
			} else {

			}

		},
		error : function(XMLHttpRequest) {

			$.messager.show({ // show error message
				title : 'Error',
				msg : 'Failed of delete'
			});
		}

	});

}

function editHost() {
	
	var row = $('#dg').datagrid('getSelected');
	if (row !== null) {
		$('#dlg').dialog('open').dialog('setTitle', 'Edit Host');
		$('#save-host').hide();
		$('#update-host').show();
		
		$("[name='hostIp']").val(row.hostIp);
		$("[name='hostName']").val(row.hostName);
		$("[name='hostDesc']").val(row.hostDesc);
		
		
	}else{
		$.messager.show({ // show error message
			title : 'Error',
			msg : 'Please choose the host to edit! '
		});
	}

}

/**
 * 获取工程路径
 * 
 * @returns
 */
function getRootPath() {
	var path = $("#basePath").val();
	return path;

}
