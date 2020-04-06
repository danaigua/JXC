var url;
function closeDormitoryDialog() {
	$("#dlg").dialog("close");
	resetValue();
}
function openDormitoryDialog(){
	$("#dlg").dialog("open").dialog("setTitle", "添加宿舍");
	url = "/admin/dormitory/save";
}
function saveDormitory(){
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
function openModifyDormitoryDialog(){
	var selectedRows = $("#dg").datagrid("getSelections");
	if (selectedRows.length != 1) {
		$.messager.alert("系统提示", "请选择一条要修改的数据！");
		return;
	}
	var id = selectedRows[0].id;
	$("#dlg").dialog("open").dialog("setTitle", "修改宿舍信息");
	$("#fm").form("load", selectedRows[0]);
	url = "/admin/dormitory/save?id=" + id;
}
function deleteDormitory() {
	var selectedRows = $("#dg").datagrid("getSelections");
	if (selectedRows.length != 1) {
		$.messager.alert("系统提示", "请选择一条要删除的数据！");
		return;
	}
	var id = selectedRows[0].id;
	$.messager.confirm("系统提示", "您确定要删除这条数据吗?", function(r) {
		if (r) {
			$.post("/admin/dormitory/delete", {
				id : id
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
// 添加双击事件
$(document).ready(function() {
	$("#dg").datagrid({
		onDblClickRow : function(index, row) {
			$("#dlg").dialog("open").dialog("setTitle", "修改宿舍信息");
			$("#fm").form("load", row);
			url = "/admin/dormitory/save?id=" + row.id;
		}
	});
});
function resetValue(){
	$("#name").val('');
	$("#employee1").val('');
	$("#employee2").val('');
	$("#employee3").val('');
	$("#employee4").val('');
}