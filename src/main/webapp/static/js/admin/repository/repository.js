function formatEdit(val, row) {
	return "<a href=\"javascript:openDownloadDialog(" + row.id + ") \">下载</a>";
}
var url;
var downloadRowId;
function openDownloadDialog(rowId) {
	downloadRowId = rowId;
	$("#dlg3").dialog("open");
}
function closeDownloadDialog3() {
	$("#dlg3").dialog("close");
}
function openDownloadDialog5(rowId) {
	downloadRowId = rowId;
	$("#dlg5").dialog("open");
}
function closeDownloadDialog5() {
	$("#dlg5").dialog("close");
}
function download(rowId) {
	$.post("/admin/isoFileUpload/download", {
		downloadFileId : downloadRowId,
		path : $('#path').val()
	}, function(result) {
//		console.log(result);
		if (result.success) {
			$.messager.alert("系统提示", "下载成功！");
			$("#dlg3").dialog("close");
		} else {
			$.messager.alert("系统提示", result.errorInfo);
			$("#dlg3").dialog("close");
		}
	});
}
function downloadFolder1(rowId) {
	$.post("/admin/isoFileUpload/downloadFolder", {
		downloadFolderId : downloadRowId,
		path : $('#path2').val()
	}, function(result) {
//		console.log(result);
		if (result.success) {
			$("#dlg5").dialog("close");
			$.messager.alert("系统提示", "下载成功！");
		} else {
			$("#dlg5").dialog("close");
			$.messager.alert("系统提示", result.errorInfo);
		}
	});
}
function searchDataUpload() {
	$("#dg").datagrid('load', {
		"name" : $("#name").val(),
		"content" : $("#content1").val()
	});
}
function resetValue() {
	$("#file").val("");
}
function closeDataUploadDialog2() {
	$("#dlg2").dialog("close");
	resetValue();
}
function closeDataUploadDialog() {
	$("#dlg").dialog("close");
	resetValue();
}
function saveDataUpload() {
	var select = $("#tree").tree("getSelected"); // 获取选中节点
	$("#pid").val(select.id);
	$("#fm").form("submit", {
		url : url,
		onSubmit : function() {
			return $(this).form("validate");
		},
		success : function(result) {
			var result = eval('(' + result + ')');
			if (result.success) {
//				$.messager.alert("系统提示", "保存成功！");
				resetValue();
				$("#dlg").dialog("close");
				$("#dg").datagrid("reload");
//				$("#tree").tree("reload");
				var dataJson = eval('(' + result.newTree + ')'); 
				var chidren = $('#tree').tree('getChildren', select.target);
				var i;
				for(i = 0; i < chidren.length; i++){
					$("#tree").tree("remove", chidren[i].target);
				}
				console.log(dataJson);
				$("#tree").tree("append", {parent: select.target,data:dataJson});
			} else {
				$.messager.alert("系统提示", result.errorInfo);
			}
		}
	});
}
function saveDataUpload2() {
	$("#fm2").form("submit", {
		url : url,
		onSubmit : function() {
			return $(this).form("validate");
		},
		success : function(result) {
			var result = eval('(' + result + ')');
			if (result.success) {
				$.messager.alert("系统提示", "保存成功！");
				resetValue();
				$("#dlg2").dialog("close");
				$("#dg").datagrid("reload");
			} else {
				$.messager.alert("系统提示", result.errorInfo);
			}
		}
	});
}
function openDataUploadAddDialog() {
	$("#dlg").dialog("open").dialog("setTitle", "添加文件");
	url = "/admin/isoFileUpload/isoUpload";
}

function openDataUploadModifyDialog() {
	var selectedRows = $("#dg").datagrid("getSelections");
	if (selectedRows.length != 1) {
		$.messager.alert("系统提示", "请选择一条要修改的数据！");
		return;
	}
	var row = selectedRows[0];
	$("#dlg2").dialog("open").dialog("setTitle", "修改文件信息");
	$("#fm2").form("load", row);
	url = "/admin/isoFileUpload/save?id=" + row.id;
}

function deleteDataUpload() {
	var selectedRows = $("#dg").datagrid("getSelections");
	if (selectedRows.length != 1) {
		$.messager.alert("系统提示", "请选择一条要删除的数据！");
		return;
	}
	var id = selectedRows[0].id;
	$.messager.confirm("系统提示", "您确定要删除这条数据吗?", function(r) {
		if (r) {
			$.post("/admin/isoFileUpload/delete", {
				id : id
			}, function(result) {
				if (result.success) {
					$.messager.alert("系统提示", "数据已成功删除！");
					$("#dg").datagrid("reload");
				} else {
					$.messager.alert("系统提示", "系统发生了未知错误，请稍后重试！");
					$("#dg").datagrid("reload");
				}
			}, "json");
		}
	});
}
// 添加双击事件
$(document).ready(function() {
	$("#dg").datagrid({
		onDblClickRow : function(index, row) {
			$("#dlg2").dialog("open").dialog("setTitle", "修改文件信息");
			$("#fm2").form("load", row);
			url = "/admin/isoFileUpload/save?id=" + row.id;
		}
	});
	
	
	$('#tree').tree({
		onContextMenu: function(e, node){
			e.preventDefault();
			// select the node
			$('#tree').tree('select', node.target);
			// display context menu
			$('#mm').menu('show', {
				left: e.pageX,
				top: e.pageY
			});
		}
	});

});
$(document).ready(function() {

	$("#tree").tree({
		// lines : true,
		animate : true,
		url : '/admin/folderType/loadTreeInfo',
		// loadFilter:myLoadFilter,
		onLoadSuccess : function() {
			// $("#tree").tree("expandAll");
		},
		onClick : function(node) {
			if (node.attributes.state == 0) { // 假如是叶子节点，删除按钮恢复可用
				$("#del").linkbutton("enable");
			} else {
				$("#del").linkbutton("disable");
			}
			$("#dg").datagrid('load', {
				"name" : $("#s_name").val(),
				"folderType.id" : node.id
			});
		}
	});
});

function openFolderTypeAddDialog() {
	$("#dlg4").dialog("open").dialog("setTitle", "新文件或者文件夹");
}

function deleteFolderType() {
	var node = $("#tree").tree("getSelected"); // 获取选中节点
	var parentNode = $("#tree").tree('getParent', node.target);
	var id = node.id;
	$.post("/admin/folderType/delete", {
		id : id
	}, function(result) {
		if (result.success) {
			//$('#tree').tree('reload');
			$('#tree').tree('remove', node.target);
			$("#del").linkbutton("disable");
		} else {
			$.messager.alert("系统提示", result.errorInfo);
		}
	}, "json");
}

function saveFolderType() {
	if (!$("#fm4").form("validate")) {
		return;
	}
	var folderTypeName = $('#folderTypeName').val();
	var node = $("#tree").tree("getSelected"); // 获取选中节点
	var parentId;
	if (node == null) {
		parentId = 1;
	} else {
		parentId = node.id;
	}
	$.post("/admin/folderType/save", {
		name : folderTypeName,
		parentId : parentId
	}, function(result) {
		if (result.success) {
			var dataJson = eval('(' + result.newTree + ')'); 
			var chidren = $('#tree').tree('getChildren', node.target);
			var i;
			for(i = 0; i < chidren.length; i++){
				$("#tree").tree("remove", chidren[i].target);
			}
			$("#tree").tree("append", {parent: node.target,data:dataJson});
			closeFolderTypeDialog();
		} else {
			$.messager.alert("系统提示", "提交失败！错误信息" + result.errorInfo);
		}
	}, "json");
}
function closeFolderTypeDialog() {
	$("#dlg4").dialog("close");
	$('#folderTypeName').val('');
}

function downloadFolder() {
	var node = $("#tree").tree("getSelected"); // 获取选中节点
	if (node == null) {
		$.messager.alert("系统提示", "您没有选中节点，请选中节点之后在下载！");
		return;
	}
	if (node.id == 1) {
		$.messager.alert("系统提示", "您不能下载根目录！");
		return;
	}
	downloadRowId = node.id;
	$("#dlg5").dialog("open");
}
function openDataUploadAddFolder() {
	$("#dlg6").dialog("open").dialog("setTitle", "上传文件夹");
	url = "/admin/isoFileUpload/uploadDir";
}
function closeDataUploadAddFolder() {
	$("#dlg6").dialog("close");
}
function uploadDir() {
	var node = $("#tree").tree("getSelected"); // 获取选中节点
	if (node == null) {
		$.messager.alert("系统提示", "您没有选中节点，请选中节点之后在上传！");
		return;
	}
	if (node.id == 1) {
		$.messager.alert("系统提示", "您不能上传到根目录！");
		return;
	}
	$("#uploadFolderId").val(node.id);
	$("#fm6").form("submit", {
		url : url,
		onSubmit : function() {
			return $(this).form("validate");
		},
		success : function(result) {
			var result = eval('(' + result + ')');
			if (result.success) {
//				console.log(result.newTree);
				
				var dataJson = eval('(' + result.newTree + ')'); 
				var newNode = node;
				var chidren = $('#tree').tree('getChildren', node.target);
				var i;
				for(i = 0; i < chidren.length; i++){
					$("#tree").tree("remove", chidren[i].target);
				}
				$("#tree").tree("append", {parent: node.target,data:dataJson});
//				$("#tree").tree("append", {parent: node.target,data:dataJson});
//				$("#tree").tree("expand", node.target);
//				$.messager.alert("系统提示", "上传成功！");
				resetValue();
				$("#dlg6").dialog("close");
				$("#dg").datagrid("reload");
//				$("#tree").tree("reload");
			} else {
				$.messager.alert("系统提示", result.errorInfo);
			}
		}
	});

}