var url;

function searchCustomer() {
	$("#dg").datagrid('load', {
		"name" : $("#s_name").val()
	});
}

function resetValue() {
	$("#name").val("");
	$("#contact").val("");
	$("#number").val("");
	$("#address").val("");
	$("#remarks").val("");
}

function closeCustomerDialog() {
	$("#dlg").dialog("close");
	resetValue();
}

function saveCustomer() {
	$("#fm").form("submit", {
		url : url,
		onSubmit : function() {
			return $(this).form("validate");
		},
		success : function(result) {
			var result = eval('(' + result + ')');
			if (result.success) {
				$.messager.alert("系统提示", "保存成功！");
				resetValue();
				$("#dlg").dialog("close");
				$("#dg").datagrid("reload");
			} else {
				$.messager.alert("系统提示", result.errorInfo);
			}
		}
	});
}

function openCustomerAddDialog() {
	$("#dlg").dialog("open").dialog("setTitle", "添加客户信息");
	url = "/admin/customer/save";
}

function openCustomerModifyDialog() {
	var selectedRows = $("#dg").datagrid("getSelections");
	if (selectedRows.length != 1) {
		$.messager.alert("系统提示", "请选择一条要修改的数据！");
		return;
	}
	var row = selectedRows[0];
	$("#dlg").dialog("open").dialog("setTitle", "修改客户信息");
	$("#fm").form("load", row);
	url = "/admin/customer/save?id=" + row.id;
}

function deleteCustomer() {
	var selectedRows = $("#dg").datagrid("getSelections");
	if (selectedRows.length == 0) {
		$.messager.alert("系统提示", "请选择要删除的数据！");
		return;
	}
	var strIds = [];
	for (var i = 0; i < selectedRows.length; i++) {
		strIds.push(selectedRows[i].id);
	}
	var ids = strIds.join(",");
	$.messager.confirm("系统提示", "您确定要删除这条数据吗?", function(r) {
		if (r) {
			$.post("/admin/customer/delete", {
				ids : ids
			}, function(result) {
				if (result.success) {
					$.messager.alert("系统提示", "数据已成功删除！");
					$("#dg").datagrid("reload");
				} else {
					$.messager.alert("系统提示", result.errorInfo);
				}
			}, "json");
		}
	});
}

$(document).ready(function() {

	$("#dg").datagrid({
		onDblClickRow : function(index, row) {
			$("#dlg").dialog("open").dialog("setTitle", "修改客户信息");
			$("#fm").form("load", row);
			url = "/admin/customer/save?id=" + row.id;
		}
	});

});
//批量导入
function uploadAnalyzeFile(){
	//打开批量导入窗口
	$("#uploadXmlOrXmls").dialog("open");
}
//上传批量导入的文件进行分析
function saveUploadFile(){
	$("#fm6").form("submit", {
		url : "/admin/customer/upload",
		onSubmit : function() {
			return $(this).form("validate");
		},
		success : function(result) {
			var result = eval('(' + result + ')');
			if (result.success) {
				$.messager.alert("系统提示", "保存成功！");
				closeUploadFileDialog();
				$("#dg").datagrid("reload");
			} else {
				$.messager.alert("系统提示", result.errorInfo);
			}
		}
	});
}
//关闭批量导入
function closeUploadFileDialog(){
	$("#uploadXmlOrXmls").dialog("close");
}