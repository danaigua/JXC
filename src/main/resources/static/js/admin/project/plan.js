var url;
var standardUrl;
var nowTreeId;
var currentProject;
var examineUrl;
var examineId;
function searchTime() {
	$("#dg").datagrid('load', {
		"name" : $("#s_name").val(),
	});
}

function searchTime() {
	$("#dg").datagrid('load', {
		"userName" : $("#s_userName").val()
	});
}
/**
 * 重置填写的值
 * @returns
 */
function resetValue(){
	$("#planName").val('');
	$("#planContent").val('');
	$("#planRemarks").val('');
}
function closePlanDialog() {
	$("#dlg").dialog("close");
	resetValue();
}

function savePlan() {
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

function openPlanAddDialog() {
	$("#dlg").dialog("open").dialog("setTitle", "新增项目");
	url = "/admin/plan/save";
}

function openPlanModifyDialog() {
	var selectedRows = $("#dg").datagrid("getSelections");
	if (selectedRows.length != 1) {
		$.messager.alert("系统提示", "请选择一条要修改的数据！");
		return;
	}
	var row = selectedRows[0];
	$("#dlgModyfy").dialog("open");
	$("#fmModify").form("load", row);
	url = "/admin/plan/save?id=" + row.id;
}

function deletePlan() {
	var selectedRows = $("#dg").datagrid("getSelections");
	if (selectedRows.length != 1) {
		$.messager.alert("系统提示", "请选择一条要删除的数据！");
		return;
	}
	var id = selectedRows[0].id;
	$.messager.confirm("系统提示", "您确定要删除这条数据吗?", function(r) {
		if (r) {
			$.post("/admin/plan/delete", {
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
function closeWindows() {
	$('#planCourseDialog').window('close');
}
// 添加双击事件
$(document).ready(function() {
	$('#planCourseDialog').window('close');
	$('#showExamineWindow').window('close');
	$("#dg").datagrid({
		onDblClickRow : function(index, row) {
			currentProject = row.id;
			$('#planCourseDialog').window('open');
			//重新设置数据
			$("#dgTask").datagrid('load', {"finish" : "0", planId : currentProject});
		}
	});
	//单击的时候被触发
	$("#dgTask").datagrid({
		onClickRow : function(index, row) {
			console.log(row.planCourseStandard);
			$("#document1").html(row.planCourseStandard.standard);
			$("#document2").html(row.planCourseStandard.explains);
			$("#document3").html(row.notFinishQuestion);
			$("#document4").html(row.content);
			$("#document5").html(row.remarks);
			$("#document6").html(row.valuesname);
		}
	});
	$("#dgTask").datagrid({
		onDblClickRow : function(index, row) {
			openPlanPlanCourseModifyDialog();
		}
	});
	$("#standardDg").datagrid({
		onDblClickRow : function(index, row) {
			$("#standardSelect").combobox("setValue", row.id);
			$("#standardSelect").combobox("setText", row.name);
			$("#standardSelect1").combobox("setValue", row.id);
			$("#standardSelect1").combobox("setText", row.name);
			$("#standard").dialog("close");
		}
	});
});
// 打开项目过程任务窗口
function openPlanPlanCourseAddDialog() {
	url = "/admin/planCourse/save?planId=" + currentProject;
	$("#openTask").dialog("open");
	newAddTaskReset();
}
//新添加任务表单刷新
function newAddTaskReset(){
	//清空combobox
	$("#standardSelect1").combobox("setValue", '');
	$("#task-name").val('');
	$("#new-add-task").combobox("setValue", '');
	$("#task-finishTime").datebox('setValue', '');
	$("#task-desc").val('');
	$("#task-remarks").val('');
}

function savePlanCourse() {
	$("#taskForm").form("submit", {
		url : url,
		onSubmit : function() {
			return $(this).form("validate");
		},
		success : function(result) {
			var result = eval('(' + result + ')');
			if (result.success) {
				$.messager.alert("系统提示", "保存成功！");
				$("#openTask").dialog("close");
//				$("#dgTask").datagrid("reload");
				$("#dgTask").datagrid('load', {"finish" : "0", planId : currentProject});
			} else {
				$.messager.alert("系统提示", result.errorInfo);
			}
		}
	});
}
function closePlanCourseDialog() {
	$("#openTask").dialog("close");
}
function resetTaskValue() {
	$("#taskModifyName").val("");
	$("#task-modify-principal").val("");
	$("#task-modify-id").val("");
	$("#task-user-id").val("");
	$("#task-user-times").val("");
	$("#standardSelect").combobox("setValue", '');
	$("#task_finishTime").datebox('setValue', '');
	$("#taskModifyDesc").val("");
	$("#taskModifyRemarks").val("");
}
function savePlanModify(){
	$("#fmModify").form("submit", {
		url : url,
		onSubmit : function() {
			return $(this).form("validate");
		},
		success : function(result) {
			var result = eval('(' + result + ')');
			if (result.success) {
				$.messager.alert("系统提示", "保存成功！");
				$("#dlgModyfy").dialog("close");
				$("#dg").datagrid("reload");
				resetTaskValue();
			} else {
				$.messager.alert("系统提示", result.errorInfo);
			}
		}
	});
}
function closePlanModifyDialog(){
	$("#dlgModyfy").dialog("close");
}
function resetModifyValue(){
	$("#name").val("");
	$("#id").val("");
	$("#time").val("");
	$("#schedules").val("");
	$("#finish").val("");
	$("#content").val("");
	$("#remarks").val("");
}
//打开修改任务修改窗口
function openPlanPlanCourseModifyDialog(){
	var selectedRows = $("#dgTask").datagrid("getSelections");
	if (selectedRows.length != 1) {
		$.messager.alert("系统提示", "请选择一条要修改的数据！");
		return;
	}
	var row = selectedRows[0];
	$("#openModifyTask").dialog("open");
	if(row.planCourseStandard != null){
		$("#standardSelect").combobox("setValue", row.planCourseStandard.id);
		$("#standardSelect").combobox("setText", row.planCourseStandard.name);
		
		if(row.finish == 0){
			$("#finishOrNot").combobox("setValue", 0);
			$("#finishOrNot").combobox("setText", "未完成");
		}else{
			$("#finishOrNot").combobox("setValue", 1);
			$("#finishOrNot").combobox("setText", "已完成");
		}
		
		
	}
	$("#taskModifyForm").form("load", row);
	url = "/admin/planCourse/save?id=" + row.id;
}
//保存修改
function saveModifyPlanCourse() {
	$("#taskModifyForm").form("submit", {
		url : url,
		onSubmit : function() {
			return $(this).form("validate");
		},
		success : function(result) {
			var result = eval('(' + result + ')');
			if (result.success) {
				$.messager.alert("系统提示", "保存成功！");
				$("#openModifyTask").dialog("close");
//				$("#dgTask").datagrid("reload");
				$("#dgTask").datagrid('load', {"finish" : "0", planId : currentProject});
				resetTaskValue();
			} else {
				$.messager.alert("系统提示", result.errorInfo);
			}
		}
	});
}
//关闭任务窗口
function closeModifyPlanCourse(){
	$("#openModifyTask").dialog("close");
}
//控制完成进度
$(function(){
	$('#cc').combo({
		required:true,
		editable:false
	});
	$('#sp').appendTo($('#cc').combo('panel'));
	$('#sp input').click(function(){
		var v = $(this).val();
		var s = $(this).next('span').text();
		$('#cc').combo('setValue', v).combo('setText', s).combo('hidePanel');
	});
});
//打开一个任务标准窗口
function openStandardDialog(){
	//加载树
	$("#standardTree").tree({
		animate:true,
		url : '/admin/planCourseStandardType/loadTreeInfo',
		onLoadSuccess : function() {
			$("#standardTree").tree("expandAll");
		},
		onClick : function(node) {
			if (node.attributes.state == 0) { // 假如是叶子节点，删除按钮恢复可用
				$("#standardTypeDel").linkbutton("enable");
			} else {
				$("#standardTypeDel").linkbutton("disable");
			}
			nowTreeId = node.id;
			$("#standardDg").datagrid('load', {
				"name" : $("#standardName").val(),
				"planCourseStandardType.id" : node.id
			});
		}
	});
	$("#standard").dialog("open");
}
//保存修改任务
function saveModifyPlanCourse(){
	$("#taskModifyForm").form("submit", {
		url : url,
		onSubmit : function() {
			return $(this).form("validate");
		},
		success : function(result) {
			var result = eval('(' + result + ')');
			if (result.success) {
				$.messager.alert("系统提示", "保存成功！");
				$("#openModifyTask").dialog("close");
//				$("#dgTask").datagrid("reload");
				$("#dgTask").datagrid('load', {"finish" : "0", planId : currentProject});
			} else {
				$.messager.alert("系统提示", result.errorInfo);
			}
		}
	});
}
//关闭任务修改窗口
function closeModifyPlanCourseDialog(){
	$("#openModifyTask").dialog("close");
}

//新增一个执行标准类别
function openStandrdTypeAddDialog(){
	$("#newStandardType").dialog("open");
}
//删除一个执行标准类别
function deleteStandardType(){
	var select = $("#standardTree").tree("getSelected"); // 获取选中节点
	$.post("/admin/planCourseStandardType/delete", {id:select.id}, function(result){
		if (result.success) {
			$("#standardTree").tree("reload");
		} else {
			$.messager.alert("系统提示", result.errorInfo);
		}
	}, "json");
}

//关闭一个执行标准类型框
function closeStandardTypeNewOrChange(){
	$("#newStandardType").dialog("close");
}

//保存一个执行标准类型框
function saveStandardTypeNewOrChange(){
	var select = $("#standardTree").tree("getSelected"); // 获取选中节点
	if(select==null || select.id==null){
		select.id = 1;
	}
	$.post("/admin/planCourseStandardType/save", {name:$("#standard-type-name").val(), parentId:select.id}, function(result){
		if (result.success) {
			$("#standardTree").tree("reload");
			$("#newStandardType").dialog("close");
		} else {
			$.messager.alert("系统提示", result.errorInfo);
		}
	}, "json");
}

//重置执行标准添加
function resetStandardValue(){
	$("standard-name").val('');
	$("standard-standard").val('');
	$("standard-desc").val('');
	$("standard-remarks").val('');
}

//打开一个添加执行标准的框
function openStandardAddDialog(){
	//判断是否选中树形类别
	var select = $("#standardTree").tree("getSelected"); // 获取选中节点
	if(select == null || select.id == 1){
		$.messager.alert("系统提示", "请选择标准类别");
		return;
	}
	//打开一个窗口
	$("#newStandard").dialog("open").dialog("setTitle", "添加执行标准");
	standardUrl = "/admin/planCourseStandard/save";
}
//重置standardForm表单中的值
function resetStandardFormValues(){
	$("#standard-name").val('');
	$("#standard-standard").val('');
	$("#standard-desc").val('');
	$("#standard-remarks").val('');
}
//保存添加或者修改执行标准值
function saveStandardNewOrChange(){
	$("#typeId").val(nowTreeId);
	$("#standardForm").form("submit", {
		url : standardUrl,
		onSubmit : function() {
			return $(this).form("validate");
		},
		success : function(result) {
			var result = eval('(' + result + ')');
			if (result.success) {
				$.messager.alert("系统提示", "保存成功！");
				resetStandardFormValues();
				$("#newStandard").dialog("close");
				$("#standardDg").datagrid("reload");
			} else {
				$.messager.alert("系统提示", result.errorInfo);
			}
		}
	});
}
function closeStandardNewOrChange(){
	$("#newStandard").dialog("close");
}
//打开一个修改执行标注的框
function openStandardModifyDialog(){
	//获取选中的一行
	var selectedRows=$("#standardDg").datagrid("getSelections");
	if(selectedRows.length!=1){
		$.messager.alert("系统提示","请选择一条要修改的数据！");
		return;
	}
	var row=selectedRows[0];
	//修改
	$("#newStandard").dialog("open").dialog("setTitle", "修改执行标准");
	$("#standardForm").form("load",row);
	standardUrl = "/admin/planCourseStandard/save?id=" + row.id;
}
//删除一个执行标准
function deleteStandard(){
	//获取选中的
	var selectedRows=$("#standardDg").datagrid("getSelections");
	if(selectedRows.length!=1){
		$.messager.alert("系统提示","请选择一条要修改的数据！");
		return;
	}
	var row=selectedRows[0];
	$.messager.confirm("系统提示", "您确定要删除这条数据吗?", function(r) {
		if (r) {
			$.post("/admin/planCourseStandard/delete", {
				id : row.id
			}, function(result) {
				if (result.success) {
					$.messager.alert("系统提示", "数据已成功删除！");
					$("#standardDg").datagrid("reload");
				} else {
					$.messager.alert("系统提示", result.errorInfo);
				}
			}, "json");
		}
	});
}
//搜索执行标准
function searchStandard(){
	
	$("#standardDg").datagrid('load', {
		"name" : $("#standardName").val(),
		"planCourseStandardType.id" : nowTreeId
	});
}
//保存执行标准的选择
function saveStandardSelect(){
	var selectedRows = $("#standardDg").datagrid("getSelections");
	if (selectedRows.length != 1) {
		$.messager.alert("系统提示", "请选择一条要修改的数据！");
		return;
	}
	var row = selectedRows[0];
	$("#standardSelect").combobox("setValue", row.id);
	$("#standardSelect").combobox("setText", row.name);
	$("#standardSelect1").combobox("setValue", row.id);
	$("#standardSelect1").combobox("setText", row.name);
	$("#standard").dialog("close");
}
//关闭执行标准选择
function closeStandardDialog(){
	$("#standard").dialog("close");
}
//删除一个plancourse
function deletePlanPlanCourse(){
	var selectedRows=$("#dgTask").datagrid("getSelections");
	if (selectedRows.length != 1) {
		$.messager.alert("系统提示", "请选择一条要修改的数据！");
		return;
	}
	var row = selectedRows[0];
	$.post("/admin/planCourse/delete", {
		id : row.id
	}, function(result) {
		if (result.success) {
			$.messager.alert("系统提示", "数据已成功删除！");
//			$("#dgTask").datagrid("reload");
			$("#dgTask").datagrid('load', {"finish" : "0", planId : currentProject});
		} else {
			$.messager.alert("系统提示", result.errorInfo);
		}
	}, "json");
}
//显示已经完成的任务
function showFinishTask(){
	$("#dgTask").datagrid('load', {"finish" : "1", planId : currentProject});
}
//显示未完成
function showNotFinishTask(){
	$("#dgTask").datagrid('load', {"finish" : "0", planId : currentProject});
}
//打开任务窗口
function openTaskWindows(){
	var selectedRows = $("#dg").datagrid("getSelections");
	if (selectedRows.length != 1) {
		$.messager.alert("系统提示", "请选择一个要打开任务窗口的项目！");
		return;
	}
	var row = selectedRows[0];
	currentProject = row.id;
	$('#planCourseDialog').window('open');
	$("#dgTask").datagrid('load', {"finish" : "0", planId : currentProject});
}
//显示未完成的项目
function showNotFinishProject(){
	$("#dg").datagrid('load', {
		"finish" : "0"
	});
}
//显示已经完成的项目
function showFinishProject(){
	$("#dg").datagrid('load', {
		"finish" : "1"
	});
}
//添加审批1
function examineTask1(){
	var selectedRows = $("#dgTask").datagrid("getSelections");
	if (selectedRows.length != 1) {
		$.messager.alert("系统提示", "请选择一个要审批的任务！");
		return;
	}
	var row = selectedRows[0];
	if(row.affirm == 1){
		$.messager.alert("系统提示", "该任务已经确认！无法审批！");
		return;
	}
	if(row.examine1 == null){
		examineId = row.id;
		//打开一个添加审批的框框
		examineUrl = "/admin/planCourse/examine?examine=1";
		$("#examineTask").dialog("open");
	}else{
		//查看审批
		$("#showExamineWindow").window("open").window("setTitle", "审批一");
		$("#examineContent").html(row.examine1);
		$("#replyContent").html(row.reply1);
	}
	
}
//添加审批2
function examineTask2(){
	var selectedRows = $("#dgTask").datagrid("getSelections");
	if (selectedRows.length != 1) {
		$.messager.alert("系统提示", "请选择一个要审批的任务！");
		return;
	}
	var row = selectedRows[0];
	if(row.affirm == 1){
		$.messager.alert("系统提示", "该任务已经确认！无法审批！");
		return;
	}
	if(row.examine2 == null){
		examineId = row.id;
		//打开一个添加审批的框框
		examineUrl = "/admin/planCourse/examine?examine=2";
		$("#examineTask").dialog("open");
	}else{
		//查看审批
		$("#showExamineWindow").window("open").window("setTitle", "审批二");
		$("#examineContent").html(row.examine2);
		$("#replyContent").html(row.reply2);
	}
}
//添加审批3
function examineTask3(){
	var selectedRows = $("#dgTask").datagrid("getSelections");
	if (selectedRows.length != 1) {
		$.messager.alert("系统提示", "请选择一个要审批的任务！");
		return;
	}
	var row = selectedRows[0];
	if(row.affirm == 1){
		$.messager.alert("系统提示", "该任务已经确认！无法审批！");
		return;
	}
	
	if(row.examine3 == null){
		examineId = row.id;
		//打开一个添加审批的框框
		examineUrl = "/admin/planCourse/examine?examine=3";
		$("#examineTask").dialog("open");
	}else{
		//查看审批
		$("#showExamineWindow").window("open").window("setTitle", "审批三");
		$("#examineContent").html(row.examine3);
		$("#replyContent").html(row.reply3);
	}
}
//转换成确认状态
function changeToAffirm(){
	var selectedRows = $("#dgTask").datagrid("getSelections");
	if (selectedRows.length != 1) {
		$.messager.alert("系统提示", "请选择一个要确认的任务！");
		return;
	}
	var row = selectedRows[0];
	if(row.finish == null || row.finish==0){
		$.messager.alert("系统提示", "该任务还没有完成！因此需要完成之后才可以修改成确认状态！");
		return;
	}
	$.post("/admin/planCourse/changeToAffirm", {
		id : row.id
	}, function(result) {
		if (result.success) {
			$.messager.alert("系统提示", "设置完成！");
			$("#dgTask").datagrid('load', {"finish" : "0", planId : currentProject});
		} else {
			$.messager.alert("系统提示", result.errorInfo);
		}
	}, "json");
}
//保存审批
function savePlanCourseExamine(){
	$.post(examineUrl, {id:examineId, examineContent:$("#task-examine").val()}, function(result){
		if (result.success) {
			$.messager.alert("系统提示", "审批完成！");
			$("#dgTask").datagrid('load', {"finish" : "0", planId : currentProject});
			closePlanCourseExamineDialog();
		} else {
			$.messager.alert("系统提示", result.errorInfo);
		}
	},"json");
}
//关闭审批
function closePlanCourseExamineDialog(){
	$("#examineTask").dialog("close");
	$("#task-examine").val('');
}