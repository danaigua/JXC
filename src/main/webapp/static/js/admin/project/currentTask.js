var rowId;
var contentValue;
var saveReplyUrl;
var currentId;
function formatEdit(val, row) {
	contentValue = row.valuesname;
	return "<a href=\"javascript:openEditWindow("+row.id+")\">编写文档</a>";
}
function examineEdit(val, row){
	var h = '';
	if(row != null){
		if(row.examine1!=null){
			if(row.reply1 == null){
				row.reply1 = '';
			}
			h = "<a href=\"javascript:openExaminWindow('"+row.examine1+"','"+row.reply1+"','"+1+"')\">审批一</a>" + h;
		}
		if(row.examine2!=null){
			if(row.reply2 == null){
				row.reply2 = '';
			}
			h = "<a href=\"javascript:openExaminWindow('"+row.examine2+"','"+row.reply2+"','"+1+"')\">审批二</a>\t" + h;
		}
		if(row.examine3!=null){
			if(row.reply3 == null){
				row.reply3 = '';
			}
			h = "<a href=\"javascript:openExaminWindow('"+row.examine3+"','"+row.reply3+"','"+1+"')\">审批三</a>\t" + h;
		}
	}
	return h;
}

$(document).ready(function() {
	$('#edit').window('close');
	$('#examine').window('close');
});
function openEditWindow(planCourseId){
	rowId = planCourseId;
	if(contentValue == null){
		contentValue = '';
	}
	UE.getEditor('editor').setContent(contentValue, true);
	$('#edit').window('open');
}
//保存文档
function saveDocument(){
	var content=UE.getEditor('editor').getContent();
	$.post("/admin/planCourse/saveDocument", {id:rowId, content:content}, function(result){
		if(result.success){
			$.messager.alert("系统提示", "数据已成功保存！");
			$("#dg").datagrid("reload", {finish:0});
		}else{
			$.messager.alert("系统提示", result.errorInfo);
		}
	}, "json");
	
}
//展示没有完成的任务
function showNotFinishProject(){
	$("#dg").datagrid("reload", {finish:0});
}
//展示已经完成的任务
function showFinishProject(){
	$("#dg").datagrid("reload", {finish:1});
}
//设置为已经完成状态
function setAsFinish(){
	//获取选中的
	var selectedRows=$("#dg").datagrid("getSelections");
	if(selectedRows.length!=1){
		$.messager.alert("系统提示","请选择一条要修改的数据！");
		return;
	}
	var row=selectedRows[0];
	$.post("/admin/planCourse/setFinish", {id:row.id, finish:1}, function(result){
		if(result.success){
			$.messager.alert("系统提示", "设置成功！目前显示的是已经完成的");
			$("#dg").datagrid("reload", {finish:1});
		}else{
			$.messager.alert("系统提示", result.errorInfo);
		}
	}, "json");
}
//设置为未完成
function setAsNotFinish(){
	//获取选中的
	var selectedRows=$("#dg").datagrid("getSelections");
	if(selectedRows.length!=1){
		$.messager.alert("系统提示","请选择一条要修改的数据！");
		return;
	}
	var row=selectedRows[0];
	$.post("/admin/planCourse/setFinish", {id:row.id, finish:0}, function(result){
		if(result.success){
			$.messager.alert("系统提示", "设置成功！目前显示的是没有完成的");
			$("#dg").datagrid("reload", {finish:0});
		}else{
			$.messager.alert("系统提示", result.errorInfo);
		}
	}, "json");
}
//打开一个回复窗口
function openExaminWindow(examine, reply, reply){
	if(reply != null && reply == 1){
		saveReplyUrl = "/admin/planCourse/saveReply?reply=1";
	}else if(reply != null && reply == 2){
		saveReplyUrl = "/admin/planCourse/saveReply?reply=2";
	}else if(reply != null && reply == 3){
		saveReplyUrl = "/admin/planCourse/saveReply?reply=3";
	}
	$("#examine").window("open").window("setTitle", "审批一");
	$("#examineContent").html(examine);
	UE.getEditor('examine').setContent(reply, true);
}
//保存回复
function saveReply(){
	//获取选中的
	var selectedRows=$("#dg").datagrid("getSelections");
	var row=selectedRows[0];
	var content=UE.getEditor('examine').getContent();
	$.post(saveReplyUrl, {id:row.id, replyContent:content}, function(result){
		if(result.success){
			$.messager.alert("系统提示", "数据已成功保存！");
			$("#dg").datagrid("reload", {finish:0});
			$("#examine").window("close");
		}else{
			$("#examine").window("close");
			$.messager.alert("系统提示", result.errorInfo);
		}
	}, "json");
}