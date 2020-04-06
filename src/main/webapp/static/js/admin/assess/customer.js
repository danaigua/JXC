var url;

$(document).ready(function() {

	$("#customerId").combobox({
		mode : 'remote',
		url : '/admin/customer/comboList',
		valueField : 'id',
		textField : 'name',
		delay : 100
	});
	$("#searchCustomerReturnGoodsWindow").window('close');
	$("#customerReturnDate").datebox("setValue", genTodayStr());

	// $("#xt").load("/admin/ckCustomerReturnList/genCode");
	$("#xt").load("/admin/ckCustomerReturnList/genCode");

	$("#s_bCustomerReturnDate").datebox("setValue",genLastMonthDayStr()); // 设置上个月日期
	$("#s_eCustomerReturnDate").datebox("setValue",genTodayStr()); // 设置当前日期
	
	$("#dg").datagrid({
		onClickRow:function(index,row){
			$("#dg2").datagrid({
				url:'/admin/customerReturnList/listGoods',
				queryParams:{
					customerReturnListId:row.id
				}
			});
		}
	});
});

function saveCustomerReturnGoods() {
	$("#customerReturnNumber").val($("#xt").text());
	$("#goodsJson").val(JSON.stringify($("#dg").datagrid("getData").rows));
	$("#fm").form("submit", {
		url : "/admin/ckCustomerReturnList/save",
		onSubmit : function() {
			if ($("#dg").datagrid("getRows").length == 0) {
				$.messager.alert("系统提示", "请添加客户退货物料！");
				return false;
			}
			if (!$(this).form("validate")) {
				return false;
			}
			if (isNaN($("#customerId").combobox("getValue"))) {
				$.messager.alert("系统提示", "请选择客户！");
				return false;
			}
			return true;
		},
		success : function(result) {
			var result = eval('(' + result + ')');
			if (result.success) {
				alert("保存成功！");
				window.location.reload();
			} else {
				$.messager.alert("系统提示", result.errorInfo);
			}
		}
	});
}

function openCustomerReturnListGoodsModifyDialog() {
	$("#saveAddAddNextButton").hide();
	var selectedRows = $("#dg").datagrid("getSelections");
	if (selectedRows.length != 1) {
		$.messager.alert("系统提示", "请选择一个物料！");
		return;
	}
	var row = selectedRows[0];
	$("#dlg2").dialog("open").dialog("setTitle", "修改客户退货单物料");
	$("#fm2").form("load", row);
	$("#sellingPrice").val("¥" + row.sellingPrice);
	$("#price").numberbox("setValue", row.price);
	$("#num").numberbox("setValue", row.num);
	$("#action").val("modify");
	$("#num").focus();
	$("#rowIndex").val($("#dg").datagrid("getRowIndex", row));
}

function deleteCustomerReturnListGoods() {
	var selectedRows = $("#dg").datagrid("getSelections");
	if (selectedRows.length != 1) {
		$.messager.alert("系统提示", "请选择一条要删除的物料！");
		return;
	}
	$.messager.confirm("系统提示", "您确定要删除这物料吗?", function(r) {
		if (r) {
			$("#dg").datagrid("deleteRow",
					$("#dg").datagrid("getRowIndex", selectedRows[0]));
			setCustomerReturnListAmount();
		}
	});
}

// 客户退货模块

function setCustomerReturnListAmount() {
	var rows = $("#dg").datagrid("getRows");
	var amount = 0;
	for (var i = 0; i < rows.length; i++) {
		var row = rows[i];
		amount += row.total;
	}
	$("#amountPayable").val(amount.toFixed(2));
	$("#amountPaid").val(amount.toFixed(2));
}

function openCustomerReturnListGoodsAddDialog() {
	$("#dlg").dialog("open").dialog("setTitle", "客户退货物料选择");

	$("#tree").tree({
		url : '/admin/ckGoodsType/loadTreeInfo',
		onLoadSuccess : function() {
			var rootNode = $("#tree").tree("getRoot");
			$("#tree").tree("expand", rootNode.target);
		},
		onClick : function(node) {
			if (node.attributes.state == 0) { // 假如是叶子节点，删除按钮恢复可用
				$("#del").linkbutton("enable");
			} else {
				$("#del").linkbutton("disable");
			}
			$("#dg2").datagrid('load', {
				"type.id" : node.id
			});
		}
	});
}

// 物料选择模块

function searchGoods() {
	$("#dg2").datagrid('load', {
		"codeOrName" : $("#s_codeOrName").val()
	});
}

function formatLastSellingPrice(val, row) {
	return "¥" + val;
}

function formatSellingPrice(val, row) {
	return "¥" + val;
}

function openGoodsChooseDialog() {
	$("#saveAddAddNextButton").show();
	var selectedRows = $("#dg2").datagrid("getSelections");
	if (selectedRows.length != 1) {
		$.messager.alert("系统提示", "请选择一个物料！");
		return;
	}
	var row = selectedRows[0];
	$("#dlg2").dialog("open").dialog("setTitle", "选择物料");
	$("#fm2").form("load", row);
	$("#sellingPrice").val("¥" + row.sellingPrice);
	$("#price").numberbox("setValue", row.sellingPrice);
	$("#action").val("add");
	$("#num").focus();
}

function resetValue() {
	$("#price").numberbox("setValue", "");
	$("#num").numberbox("setValue", "");
}
//保存商品信息
function saveGoods(type) {
	var action = $("#action").val();
	if (!$("#fm4").form("validate")) {
		return;
	}
	if (action == "add") {
		var selectedRows = $("#dg2").datagrid("getSelections");
		var row = selectedRows[0];
		var price = $("#price").numberbox("getValue");
		var num = $("#num").numberbox("getValue");
		var total = price * num;
		$("#dg").datagrid("appendRow", {
			code : row.code,
			name : row.name,
			model : row.model,
			unit : row.unit,
			price : price,
			num : num,
			total : total,
			typeId : row.type.id,
			goodsId : row.id,
			inventoryQuantity : row.inventoryQuantity,
			sellingPrice : row.sellingPrice
		});
	} else if (action == "modify") {
		var rowIndex = $("#rowIndex").val();
		var selectedRows = $("#dg").datagrid("getSelections");
		var row = selectedRows[0];
		var price = $("#price").numberbox("getValue");
		var num = $("#num").numberbox("getValue");
		var total = price * num;
		$("#dg").datagrid("updateRow", {
			index : rowIndex,
			row : {
				code : row.code,
				name : row.name,
				model : row.model,
				unit : row.unit,
				price : price,
				num : num,
				total : total,
				typeId : row.typeId,
				goodsId : row.id,
				inventoryQuantity : row.inventoryQuantity,
				sellingPrice : row.sellingPrice
			}
		});
	}
	setCustomerReturnListAmount();
	if (type == 1) {
		closeGoodsDialog();
	}
	closeGoodsChooseDialog();
}

function closeGoodsDialog() {
	$('#s_codeOrName').val('');
	$("#dlg").dialog("close");
}

function closeGoodsChooseDialog() {
	resetValue();
	$("#dlg2").dialog("close");
}

// 物料类别模块

function openGoodsTypeAddDialog() {
	$("#dlg3").dialog("open").dialog("setTitle", "新增物料类别");
}

function deleteGoodsType() {
	var node = $("#tree").tree("getSelected"); // 获取选中节点
	var id = node.id;
	$.post("/admin/ckGoodsType/delete", {
		id : id
	}, function(result) {
		if (result.success) {
			$("#tree").tree("reload");
			$("#del").linkbutton("disable");
		} else {
			$.messager.alert("系统提示", result.errorInfo);
		}
	}, "json");
}

function saveGoodsType() {
	if (!$("#fm3").form("validate")) {
		return;
	}
	var goodsTypeName = $('#goodsTypeName').val();
	var node = $("#tree").tree("getSelected"); // 获取选中节点
	var parentId;
	if (node == null) {
		parentId = 1;
	} else {
		parentId = node.id;
	}
	$.post("/admin/ckGoodsType/save", {
		name : goodsTypeName,
		parentId : parentId
	}, function(result) {
		if (result.success) {
			$("#tree").tree("reload");
			closeGoodsTypeDialog();
		} else {
			$.messager.alert("系统提示", "提交失败，请联系管理员！");
		}
	}, "json");
}

function closeGoodsTypeDialog() {
	$("#dlg3").dialog("close");
	$('#goodsTypeName').val('');
}

$(document).ready(function() {

	$("#dg").datagrid({
		onDblClickRow : function(index, row) {
			$("#saveAddAddNextButton").hide();
			$("#dlg2").dialog("open").dialog("setTitle", "修改客户退货单物料");
			$("#fm2").form("load", row);
			$("#sellingPrice").val("¥" + row.sellingPrice);
			$("#price").numberbox("setValue", row.price);
			$("#num").numberbox("setValue", row.num);
			$("#action").val("modify");
			$("#num").focus();
			$("#rowIndex").val($("#dg").datagrid("getRowIndex", row));
		}
	});

	$("#dg2").datagrid({
		onDblClickRow : function(index, row) {
			$("#saveAddAddNextButton").show();
			$("#dlg2").dialog("open").dialog("setTitle", "选择物料");
			$("#fm2").form("load", row);
			$("#sellingPrice").val("¥" + row.sellingPrice);
			$("#price").numberbox("setValue", row.sellingPrice);
			$("#action").val("add");
			$("#num").focus();
		}
	});

});
