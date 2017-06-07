$(document).ready(function(){
	var data={page:1,limit:15};
	getInviteCodeData(data);
	getAllOperator();
	
	//导出
	$('.export_inviteCode_excel').on('click', function() {
		var invite_code = $('.invite_code');
		var invite_customer_name = $('.invite_customer_name');
		var invite_code_start = $('.invite_code_start');
		var invite_code_end = $('.invite_code_end');
		var remark = $('.remark');
		var start_date = $('.init_date_start');
		var end_date = $('.init_date_end');
		var create_user_name = $('.create_user_name');
		var business_hotline = $('.business_hotline');
		var start_date1 = '';
		var start_time ='';
		var reg = /\d+/g;
		if(start_date.val()!=''){
			start_date1 = start_date.val().match(reg)[0]+start_date.val().match(reg)[1]+start_date.val().match(reg)[2];
			start_time = start_date.val().match(reg)[3]+start_date.val().match(reg)[4]+start_date.val().match(reg)[5];
		}
		var end_date1 = '';
		var end_time = '';
		if(end_date.val()!=''){
			end_date1 = end_date.val().match(reg)[0]+end_date.val().match(reg)[1]+end_date.val().match(reg)[2];
			end_time = end_date.val().match(reg)[3]+end_date.val().match(reg)[4]+end_date.val().match(reg)[5];
		}
		window.location = '/invitation_import?invite_customer_name='+invite_customer_name.val().trim()
		+'&invite_code='+ invite_code.val().trim()
		+'&remark='+ remark.val().trim()
		+'&invite_code_start='+invite_code_start.val().trim()
		+'&invite_code_end='+ invite_code_end.val().trim()
		+'&create_user_name='+create_user_name.val().trim()
		+'&start_date='+start_date1+'&start_time='+start_time
		+'&end_date='+end_date1+'&end_time='+end_time
		+'&business_hotline='+business_hotline.val().trim();
	});
	
	//清除
	$('.clearInput').on('click', function() {
		var invite_code = $('.invite_code');
		var invite_customer_name = $('.invite_customer_name');
		var invite_code_start = $('.invite_code_start');
		var invite_code_end = $('.invite_code_end');
		var remark = $('.remark');
		var start_date = $('.init_date_start');
		var end_date = $('.init_date_end');
		var create_user_name = $('.create_user_name');
		invite_customer_name.val('');
		remark.val('');
		invite_code_start.val('');
		invite_code_end.val('');
		invite_code.val('');
		start_date.val('');
		end_date.val('');
		create_user_name.val('');
		showAllOperator();
	});
	
	
});
//查询
function searchOnclick(flag){
	var invite_code = $('.invite_code');
	var invite_customer_name = $('.invite_customer_name');
	var invite_code_start = $('.invite_code_start');
	var invite_code_end = $('.invite_code_end');
	var remark = $('.remark');
	var start_date = $('.init_date_start');
	var end_date = $('.init_date_end');
	var create_user_name = $('.create_user_name');
	var business_hotline = $('.business_hotline');
	var start_date1 = null;
	var start_time = null;
	var reg = /\d+/g;
	if(start_date.val()!=''){
		start_date1 = start_date.val().match(reg)[0]+start_date.val().match(reg)[1]+start_date.val().match(reg)[2];
		start_time = start_date.val().match(reg)[3]+start_date.val().match(reg)[4]+start_date.val().match(reg)[5];
	}
	var end_date1 = null;
	var end_time = null;
	if(end_date.val()!=''){
		end_date1 = end_date.val().match(reg)[0]+end_date.val().match(reg)[1]+end_date.val().match(reg)[2];
		end_time = end_date.val().match(reg)[3]+end_date.val().match(reg)[4]+end_date.val().match(reg)[5];
	}
	var data={
		invite_customer_name:invite_customer_name.val().trim(),
		remark:remark.val().trim(),
		invite_code_start:invite_code_start.val().trim(),
		invite_code_end:invite_code_end.val().trim(),
		invite_code:invite_code.val().trim(),
		access_token:localStorage.access_token,
		create_user_name:create_user_name.val(),
		start_date:start_date1,
		start_time:start_time,
		end_time:end_time,
		end_date:end_date1,
		page:1,
		business_hotline:business_hotline.val().trim(),
		limit:15
	}
	if(flag == 1){
		data.page=$('#page_now').val();
	}
	data.limit=$('#page_limit').val();
	getInviteCodeData(data);
}


//调接口 ，获取数据
function getInviteCodeData(data){
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url:"/invitation_query",
		dataType: 'json',
		data: JSON.stringify(data),
		success:function(responseText, textStatus, jqXHR){ 
			var dataHtml = '<tr><th>序号</th><th>邀请码</th><th>名称</th><th>业务咨询电话</th><th>备注</th><th>会员数</th><th>添加时间</th><th>添加人</th><th>最后修改时间</th><th>最后修改人</th><th>操作</th></tr>';
			var userHtml='';
			if(responseText.error_no == '0'){
				var resultList = responseText.result_list;
				$("#inviteCode_num").html(responseText.total_num);
				$(".page").html(pageHtml(data.page,data.limit,responseText.total_num));
				var count = (data.page-1)*data.limit;
				resultList.forEach(function(item) {
					count++;
					var button = "禁用";
					var status = '0';
					if(item.status == '未启用'){
						button = "启用";
						status = '1';
					}
					var init_date_format = dateFormat(item.init_date,item.init_time);
					var update_date_format = dateFormat(item.update_date,item.update_time);
					dataHtml += '<tr class="tableEditNone"><td>'+count+'</td><td>'+item.invite_code+'</td>'
					+'<td>'+item.invite_customer_name+'</td><td>'+item.business_hotline+'</td>'+'<td>'+item.remark+'</td>'+'<td>'+item.client_num+'</td>'
					+'<td>'+init_date_format+'</td>'+'<td>'+item.create_user_name+'</td>'+'<td>'+update_date_format+'</td>'
					+'<td>'+item.update_user_name+'</td>'
					+'<td><input type="button" name="" id=""  info='
					+escape(JSON.stringify(item))+' value="编 辑" class="tableBot3 categoryEdit " onclick="updateInfo(this)"/></td></tr>';
					
				});
			}
			$("#inviteCodeTable").html(dataHtml);
		},
		error: function(xhr, type) {
		}
	});
	
}
//点击新增按钮
function showAddInviteCodeWin(){
	$('.invite_code_add').val('');
	$('.invite_customer_name_add').val('');
	$('.business_hotline_add').val('');
	$('.remark_add').val('');
	$('.shade').show();
	$('.shadeCon').show();
}

//执行新增
function addInviteCode(){
	$("#addInviteCode").attr("onclick","");
	var invite_customer_name = $('.invite_customer_name_add').val().trim();
	var remark = $('.remark_add').val().trim();
	var invite_code = $('.invite_code_add').val().trim();
	var business_hotline = $('.business_hotline_add').val().trim();
	if(invite_customer_name.length>10 || invite_customer_name.length<1){
		alert("名称请输入1-10字符!");
		$("#addInviteCode").attr("onclick","addInviteCode()");
		return;
	}
	if(invite_code.length>10 || invite_code.length<1){
		alert("邀请码长度1-10数字!");
		$("#addInviteCode").attr("onclick","addInviteCode()");
		return;
	}
	if(business_hotline.length<1){
		alert("请填写业务咨询电话！");
		$("#addInviteCode").attr("onclick","addInviteCode()");
		return;
	}
	if(remark.length>10){
		alert("备注长度不得大于10个字符！");
		$("#addInviteCode").attr("onclick","addInviteCode()");
		return;
	}
	var data = {
		access_token : localStorage.access_token,
		invite_code:invite_code,
		invite_customer_name:invite_customer_name,
		business_hotline:business_hotline,
		remark:remark
	}
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url:"/invitation_add",
		dataType: 'json',
		data: JSON.stringify(data),
		success:function(responseText, textStatus, jqXHR){ 
			$("#addInviteCode").attr("onclick","addInviteCode()");
			if(responseText.error_no == '0'){
				alert("新增成功");
				$('.shade').hide();
				$('.shadeCon').hide();
				searchOnclick(1);
			}else{
				if(responseText.error_no == '88880103'){
					alert(responseText.error_info);
				}else{
					alert(responseText.error_no);
				}
			}
			
			
		},
		error: function(xhr, type) {
			$("#addInviteCode").attr("onclick","addInviteCode()");
			alert("网络异常");
		}
	});
}

//点击编辑按钮
function updateInfo(item) {
	var info=$(item).attr("info");
	var obj =  eval('(' + unescape(info) + ')');
	var infoHtml='<div class="shadeMain"><h4 class="borBot2 cor2">修改邀请码</h4><ul class="addInvite">'
		+'<li class="bor1"><label>邀请码</label><label class="invite_code_update">'+obj.invite_code+'</label></li>' 
		+'<li class="bor1"><label><span>*</span>名称</label><input type="text" name="" id="" value="'+obj.invite_customer_name+'" placeholder="输入名称" class="inviteName invite_customer_name_update" maxlength="10"/></li>'
		+'<li class="bor1"><label><span>*</span>业务咨询电话</label><input type="text" name="" id="" value="'+obj.business_hotline+'" placeholder="输入业务咨询电话" class="business_hotline_update" maxlength="20"/></li>'
		+'<li class="bor1"><label>备注</label><input type="text" name="" id="" value="'+obj.remark+'" placeholder="输入备注" class="remark_update" maxlength="10"/></li></ul>'
		+'<div class="shadeBotPos"><span class="shadeBot marR1 inviteKeep" onclick="updateShortName('+obj.invite_id+')">保存</span>'
		+'<span class="shadeBot inviteCancle" onclick="unShowUpdate()">取消</span></div></div>';
	
	$('.shadeCons').html(infoHtml);
	$('.shade').show();
	$('.shadeCons').show();
	
}

//新增框隐藏
function unShowAdd(){
	$('.shade').hide();
	$('.shadeCon').hide();
}

//编辑框隐藏
function unShowUpdate(){
	$('.shade').hide();
	$('.shadeCons').hide();
}

//执行修改
function updateShortName(id) {
	var invite_customer_name = $('.invite_customer_name_update').val().trim();
	var business_hotline = $('.business_hotline_update').val().trim();
	var remark = $('.remark_update').val().trim();
	var invite_code = $('.invite_code_update').text();
	if(invite_customer_name.length>10 || invite_customer_name.length<1){
		alert("名称请输入1-10字符!");
		return;
	}
	if(remark.length>10){
		alert("备注长度不得大于10个字符！");
		return;
	}
	if(business_hotline.length<1){
		alert("请填写业务咨询电话！");
		return;
	}
	var data = {
		access_token : localStorage.access_token,
		invite_id:id,
		business_hotline:business_hotline,
		invite_customer_name:invite_customer_name,
		remark:remark,
		invite_code:invite_code
	}
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url:"/invitation_edit",
		dataType: 'json',
		data: JSON.stringify(data),
		success:function(responseText, textStatus, jqXHR){ 
			if(responseText.error_no == '0'){
				alert("修改成功");
				$('.shade').hide();
				$('.shadeCons').hide();
				searchOnclick(1);
			}else{
				alert(responseText.error_info);
			}
			
			
		},
		error: function(xhr, type) {
			alert("网络异常");
		}
	});
	
	
}
