$(document).ready(function(){
	var data={page:1,limit:15};
	getMemberData(data);
	
	//导出
	$('.export_client_excel').on('click', function() {
		var nick_name = $('.nick_name');
		var mobile_tel = $('.mobile_tel');
		var invite_code_start = $('.invite_code_start');
		var invite_code_end = $('.invite_code_end');
		var invite_code = $('.invite_code');
		var start_date = $('.start_date');
		var end_date = $('.end_date');
		var memberStatus = $('.memberStatus');
		var start_date1 = null;
		var start_date2='';
		var reg = /\d+/g;
		if(start_date.val()!=''){
			start_date1 = start_date.val().match(reg)[0]+start_date.val().match(reg)[1]+start_date.val().match(reg)[2];
			start_date2 = start_date1;
		}
		var end_date1 = null;
		var end_date2 = '';
		if(end_date.val()!=''){
			end_date1 = end_date.val().match(reg)[0]+end_date.val().match(reg)[1]+end_date.val().match(reg)[2];
			end_date2 = end_date1;
		}
		 window.location ='/export_client_excel?access_token='+localStorage.access_token+'&invite_code='+invite_code.val().trim()
			+'&nick_name='+nick_name.val().trim()+'&status='+memberStatus.val()+'&mobile_tel='+mobile_tel.val().trim()
			+'&invite_code_start='+invite_code_start.val().trim()
			+'&invite_code_end='+invite_code_end.val().trim()
			+'&start_date='+start_date2+'&end_date='+end_date2
			+'&limit=10000&page=1';
	});
	
	//清除
	$('.clearInput').on('click', function() {
		var nick_name = $('.nick_name');
		var mobile_tel = $('.mobile_tel');
		var invite_code_start = $('.invite_code_start');
		var invite_code_end = $('.invite_code_end');
		var invite_code = $('.invite_code');
		var start_date = $('.start_date');
		var end_date = $('.end_date');
		var memberStatus = $('.memberStatus');
		nick_name.val('');
		mobile_tel.val('');
		invite_code_start.val('');
		invite_code_end.val('');
		invite_code.val('');
		start_date.val('');
		end_date.val('');
		memberStatus.val('');
	});
	
	
});

//查询
function searchOnclick(flag) {
	var nick_name = $('.nick_name');
	var mobile_tel = $('.mobile_tel');
	var invite_code_start = $('.invite_code_start');
	var invite_code_end = $('.invite_code_end');
	var invite_code = $('.invite_code');
	var start_date = $('.start_date');
	var end_date = $('.end_date');
	var memberStatus = $('.memberStatus');
	var start_date1 = null;
	var reg = /\d+/g;
	if(start_date.val()!=''){
		start_date1 = start_date.val().match(reg)[0]+start_date.val().match(reg)[1]+start_date.val().match(reg)[2]
						+start_date.val().match(reg)[3]+start_date.val().match(reg)[4]+start_date.val().match(reg)[5];
	}
	var end_date1 = null;
	if(end_date.val()!=''){
		end_date1 = end_date.val().match(reg)[0]+end_date.val().match(reg)[1]+end_date.val().match(reg)[2]
					+end_date.val().match(reg)[3]+end_date.val().match(reg)[4]+end_date.val().match(reg)[5];
	}
	var data={
		nick_name:nick_name.val().trim(),
		mobile_tel:mobile_tel.val().trim(),
		invite_code_start:invite_code_start.val().trim(),
		invite_code_end:invite_code_end.val().trim(),
		invite_code:invite_code.val().trim(),
		access_token:localStorage.access_token,
		page:1,
		limit:15,
		status:memberStatus.val(),
		start_date:start_date1,
		end_date:end_date1
	}
	if(flag == 1){
		data.page=$('#page_now').val();
	}
	data.limit=$('#page_limit').val();
	
	getMemberData(data);
}

//调接口 ，获取数据
function getMemberData(data){
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url:"http://192.168.0.74:8887/getClientList",
		dataType: 'json',
		data: JSON.stringify(data),
		success:function(responseText, textStatus, jqXHR){ 
			var dataHtml = '<tr><th>序号</th><th>用户编码</th><th>昵称</th><th>注册手机号</th><th>单位名称(简称)</th><th>单位地址</th><th>单位联系人</th><th>联系人电话</th><th>邀请码</th><th>注册时间</th><th>状态</th><th>操作</th></tr>';
			if(responseText.error_no == '0'){
				var resultList = responseText.result_list;
				$("#member_num").html(responseText.total_num);
				$(".page").html(pageHtml(data.page,data.limit,responseText.total_num));
				var pageNum = responseText.total_num%data.limit==0?responseText.total_num/data.limit:responseText.total_num/data.limit+1;
				var count = (data.page-1)*data.limit;
				resultList.forEach(function(item) {
					count++;
					var button = "禁用";
					var status = '0';
					if(item.status == '未启用'){
						button = "启用";
						status = '1';
					}
//					var init_date_format = dateFormat(item.init_date,item.init_time);
					dataHtml += '<tr class="tableEditNone"><td>'+count+'</td><td>'+item.client_code+'</td>'
					+'<td>'+item.nick_name+'</td>'+'<td>'+item.mobile_tel+'</td>'+'<td>'+item.enterprise_short_name+'</td>'
					+'<td>'+item.enterprise_address+'</td>'+'<td>'+item.enterprise_linkman+'</td>'+'<td>'+item.enterprise_tel+'</td>'
					+'<td>'+item.invite_code+'</td>'+'<td>'+item.init_date+'</td>'+'<td>'+item.status+'</td>'
					+'<td><input type="button" info='+escape(JSON.stringify(item))+' name="" id="" value="查看" class="tableBot1 marR1 addBot" onclick="showInfo(this)"/>'
					+'<input type="button" info='+escape(JSON.stringify(item))+' name="" id="" value="编 辑" class="tableBot3 marR1 categoryEdit" onclick="updateInfo(this)"/>'
					+'<input type="button" name="" id="" value="'+button+'" onclick="updateStatus('+item.client_id+','+item.mobile_tel+','+status+')" class="tableBot4 categoryStart" /></td></tr>';
				});
			}
			$("#memberTable").html(dataHtml);
			//$(province_class).append(cityHtml);
			
		},
		error: function(xhr, type) {
			//alert("网络异常");
		}
	});
	
}

//启用或者禁用
function updateStatus(id,tel,status) {
	var info = '启用';
	if(status == '0'){
		info = '禁用';
	}
	if(confirm("确定要"+info+"该用户吗？")){
		var data ={
				status:status,
				client_id:id,
				mobile_tel:tel,
				access_token:localStorage.access_token
			}
			
		$.ajax({
			contentType: "application/json; charset=utf-8",
			type: 'POST',
			url:"/updateClientStatus",
			dataType: 'json',
			data: JSON.stringify(data),
			success:function(responseText, textStatus, jqXHR){ 
				if(responseText.error_no == '0'){
					alert("操作成功!");
					searchOnclick(1);
				}else{
					alert(responseText.error_info);
				}
			},
			error: function(xhr, type) {
				
			}
		});
	}
	
}

//查看
function showInfo(item) {
	var dd=$(item).attr("info");
	var obj =  eval('(' + unescape(dd) + ')');
	var infoHtml = '<div class="shadeMain"><h4 class="borBot2 cor2">会员信息</h4>'
		+'<ul class="addInvite"><li class="bor1"><label>用户编码</label><span class="describe">'+obj.client_code+'</span>'
		+'<label style="text-align: right;padding-right: 10px;"><span></span>邀请码</label><span class="describe">'+obj.invite_code+'</span></li>'
		+'<h4 class="borBot2 cor2">单位信息</h4><li class="bor1"><label>单位名称</label><span>'+obj.enterprise_name+'</span></li>'
		+'<li class="bor1"><label>单位简称</label><span>'+obj.enterprise_short_name+'</span></li>'
		+'<li class="bor1"><label>单位地址</label><span>'+obj.enterprise_address+'</span></li>'
		+'<li class="bor1"><label>单位主营</label><span>'+obj.major_business+'</span></li>'
		+'<li class="bor1"><label>单位联系人</label><span>'+obj.enterprise_linkman+'</span></li>'
		+'<li class="bor1"><label>联系人电话</label><span>'+obj.enterprise_tel+'</span></li>'
		+'<h4 class="borBot2 cor2">个人资料</h4><li class="bor1"><label>昵称</label><span>'+obj.nick_name+'</span></li>'
		+'<li class="bor1"><label>性别</label><span>'+obj.sex+'</span></li>'
		+'<li class="bor1"><label>用户地区</label><span>'+obj.address+'</span></li>'
		+'<h4 class="borBot2 cor2">注册信息</h4><li class="bor1"><label>注册时间</label><span>'+obj.init_date+'</span></li>'
		+'<li class="bor1"><label>账号状态</label><span>'+obj.status+'</span></li>'
		+'<li class="bor1"><label>注册手机</label><span>'+obj.mobile_tel+'</span></li></ul>'
		+'<div class="shadeBotPos"><span class="shadeBot inviteCancle">关闭</span></div></div>';
	$('.shadeCon').html(infoHtml);
	$('.shade').show();
	$('.shadeCon').show();
	
	$('.inviteCancle').on('click', function() {
		$('.shade').hide();
		$('.shadeCon').hide();
	})
}

//编辑
function updateInfo(item) {
	var dd=$(item).attr("info");
	var obj =  eval('(' + unescape(dd) + ')');
	var infoHtml = '<div class="shadeMain"><h4 class="borBot2 cor2">会员信息</h4>'
		+'<ul class="addInvite"><li class="bor1"><label>用户编码</label><span class="describe">'+obj.client_code+'</span>'
		+'<label style="text-align: right;padding-right: 10px;"><span></span>邀请码</label><span class="describe">'+obj.invite_code+'</span></li>'
		+'<h4 class="borBot2 cor2">单位信息</h4><li class="bor1"><label>单位名称</label><span>'+obj.enterprise_name+'</span></li>'
		+'<li class="bor1"><label>单位简称</label><input type="text" name="" id="" value="'+obj.enterprise_short_name+'" class="inviteName" maxlength="8" /></li>'
		+'<li class="bor1"><label>单位地址</label><span>'+obj.enterprise_address+'</span></li>'
		+'<li class="bor1"><label>单位主营</label><span>'+obj.major_business+'</span></li>'
		+'<li class="bor1"><label>单位联系人</label><span>'+obj.enterprise_linkman+'</span></li>'
		+'<li class="bor1"><label>联系人电话</label><span>'+obj.enterprise_tel+'</span></li>'
		+'<h4 class="borBot2 cor2">个人资料</h4><li class="bor1"><label>昵称</label><span>'+obj.nick_name+'</span></li>'
		+'<li class="bor1"><label>性别</label><span>'+obj.sex+'</span></li>'
		+'<li class="bor1"><label>用户地区</label><span>'+obj.address+'</span></li>'
		+'<h4 class="borBot2 cor2">注册信息</h4><li class="bor1"><label>注册时间</label><span>'+obj.init_date+'</span></li>'
		+'<li class="bor1"><label>账号状态</label><span>'+obj.status+'</span></li>'
		+'<li class="bor1"><label>注册手机</label><span>'+obj.mobile_tel+'</span></li></ul>'
		+'<div class="shadeBotPos"><span class="shadeBot inviteKeep1" onclick="updateShortName('+obj.client_enterprise_id+')">保存</span>&nbsp;<span class="shadeBot inviteCancle" onclick="closeUpdateWin()">关闭</span></div></div>';
	$('.shadeCons').html(infoHtml);
	$('.shade').show();
	$('.shadeCons').show();
}

function closeUpdateWin() {
	$('.shade').hide();
	$('.shadeCons').hide();
}

//修改企业简称
function updateShortName(id) {
	var short_name = $('.inviteName').val().trim();
	if(short_name.length>6){
		alert("公司简称长度不得大于6个字符！");
		return;
	}
	var data = {
		access_token : localStorage.access_token,
		client_enterprise_id:id,
		enterprise_short_name:short_name
	}
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url:"/updateEnterpriseName",
		dataType: 'json',
		data: JSON.stringify(data),
		success:function(responseText, textStatus, jqXHR){ 
			if(responseText.error_no == '0'){
				alert("修改成功");
				$('.shade').hide();
				$('.shadeCons').hide();
//				location.reload();
				searchOnclick(1);
			}
			
		},
		error: function(xhr, type) {
			alert("网络异常");
		}
	});
	
	
}

