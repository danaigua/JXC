function formatUser(val, row) {
	return val.trueName + "&nbsp;(" + val.userName + ")&nbsp;";
}

function searchCustomerReturn() {
	$("#dg2").datagrid("loadData", {
		total : 0,
		rows : []
	});
	var customerId;
	if (isNaN($("#s_customer").combobox("getValue"))) {
		customerId = "";
	} else {
		customerId = $("#s_customer").combobox("getValue");
	}
	$("#dg").datagrid(
			{
				url : '/admin/customerReturnList/list',
				queryParams : {
					customerReturnNumber : $("#s_customerReturnNumber").val(),
					"customer.id" : customerId,
					state : $("#s_state").combobox("getValue"),
					bCustomerReturnDate : $("#s_bCustomerReturnDate").datebox(
							"getValue"),
					eCustomerReturnDate : $("#s_eCustomerReturnDate").datebox(
							"getValue")
				}
			});
}

function formatSupplier(val, row) {
	return val.name;
}

function formatAmountPayable(val, row) {
	return "¥" + val;
}

function formatUser(val, row) {
	return val.trueName;
}

function deleteCustomerReturn() {
	var selectedRows = $("#dg").datagrid("getSelections");
	if (selectedRows.length != 1) {
		$.messager.alert("系统提示", "请选择一条要删除的客户退货单！");
		return;
	}
	var id = selectedRows[0].id;
	$.messager.confirm("系统提示", "<font color=red>删除客户退货单后将无法恢复，是否删除?</font>",
			function(r) {
				if (r) {
					$.post("/admin/customerReturnList/delete", {
						id : id
					}, function(result) {
						if (result.success) {
							$("#dg").datagrid("reload");
							$("#dg2").datagrid("reload");
						} else {
							$.messager.alert("系统提示", result.errorInfo);
						}
					}, "json");
				}
			});
}

$(document).ready(function() {

	$("#s_bCustomerReturnDate").datebox("setValue", genLastMonthDayStr()); // 设置上个月日期
	$("#s_eCustomerReturnDate").datebox("setValue", genTodayStr()); // 设置当前日期

	$("#dg").datagrid({
		onClickRow : function(index, row) {
			$("#dg2").datagrid({
				url : '/admin/customerReturnList/listGoods',
				queryParams : {
					customerReturnListId : row.id
				}
			});
		}
	});

});

function formatPrice(val, row) {
	return "¥" + val;
}

function formatTotal(val, row) {
	return "¥" + val;
}