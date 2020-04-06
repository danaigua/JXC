var url;

function closeCanteenDialog() {
	$("#dlg").dialog("close");
	resetValue();
}

function saveCanteen(){
	console.log($("#year").val());
	console.log($("#month").val());
	$("#times").val($("#year").val() + $("#month").val());
	$("#fm-canteen").form("submit", {
		url : "/admin/canteen/saveCanteen",
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

function openCanteenAddDialog() {
	resetValue();
	$("#dlg").dialog("open").dialog("setTitle", "添加就餐信息");
	url = "/admin/canteen/save";
}

function openEmployeeModifyDialog() {
	var selectedRows = $("#dg").datagrid("getSelections");
	if (selectedRows.length != 1) {
		$.messager.alert("系统提示", "请选择一条要修改的数据！");
		return;
	}
	var row = selectedRows[0];
	$("#dlg").dialog("open").dialog("setTitle", "修改员工信息");
	$("#fm").form("load", row);
	url = "/admin/employee/save?id=" + row.id;
}

function deleteCanteen() {
	var selectedRows = $("#dg").datagrid("getSelections");
	if (selectedRows.length != 1) {
		$.messager.alert("系统提示", "请选择一条要删除的数据！");
		return;
	}
	var id = selectedRows[0].id;
	$.messager.confirm("系统提示", "您确定要删除这条数据吗?", function(r) {
		if (r) {
			$.post("/admin/canteen/delete", {
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
// 取消选择，直接关掉部门信息框
function cancelChooseDepartment() {
	$("#departmentDlg").dialog("close");
}
// 添加双击事件
$(document).ready(function() {
	$("#dg").datagrid({
		onDblClickRow : function(index, row) {
			$("#dlg").dialog("open").dialog("setTitle", "修改就餐信息");
			$("#fm-canteen").form("load", row);
			$("#year").combobox("setValue", row.year);
		}
	});
	$("#dg").datagrid({
		onClickRow:function(index,row){
			$("#dg-employee").datagrid({
				url:'/admin/canteen/list',
				queryParams:{
					canteenMonthId:row.id
				}
			});
		}
	});

});
//打开临时就餐人员名单
function openTempAddDialog(){
	var selectedRows = $("#dg").datagrid("getSelections");
	if (selectedRows.length != 1) {
		$.messager.alert("系统提示", "请选择一条要添加的数据！");
		return;
	}
	$("#temp-dlg").dialog("open").dialog("setTitle", "临时就餐人员数据录入");
}
//关闭临时就餐人员名单
function closeTempAddDialog(){
	$("#temp-dlg").dialog("close");
	$('#employeeName').val('');$('times').val('');$('timesMoney').val('');
}
//保存临时就餐人员
function saveTempCanteen(){
	var selectedRows = $("#dg").datagrid("getSelections");
	if (selectedRows.length != 1) {
		$.messager.alert("系统提示", "请选择一条要添加的数据！");
		return;
	}
	var id = selectedRows[0].id;
	$("#tempId").val(id);
	$("#fm-canteen-temp").form("submit", {
		url : '/admin/canteen/saveTempCanteen',
		onSubmit : function() {
			return $(this).form("validate");
		},
		success : function(result) {
			var result = eval('(' + result + ')');
			if (result.success) {
				$.messager.alert("系统提示", "保存成功！");
				closeTempAddDialog();
				$("#dg").datagrid("reload");
			} else {
				$.messager.alert("系统提示", result.errorInfo);
			}
		}
	});
}
function resetValue(){
	$("#year").val('');
	$("#id").val('');
	$("#month").val('');
	$("#afternoon").val('');
	$("#afternoonMoney").val('');
	$("#night").val('');
	$("#nightnMoney").val('');
	$("#afternoonAndNight").val('');
	$("#afternoonAndNightMoney").val('');
	$("#sandun").val('');
	$("#sandunMoney").val('');
}