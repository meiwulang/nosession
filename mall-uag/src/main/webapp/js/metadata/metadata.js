$(document).ready(function(){
	var data={
		page:1,
		limit:15
	};
	//edit
	$('.mainLists').on('click','.dataEditBot',function(data){
		$('.shadeMain h4').text('编辑元数据');
		var _that = $(this).parent();
		var _thatId = $(this).attr('id');
//		$('.shadeBotPos').find('.keepConfirm').removeClass('keepConfirm').addClass('editConfirm');
//		$('.shadeBotPos').find('.keepCancle').removeClass('keepCancle').addClass('editCancle');
		$('.shade').show();
		$('.shadeCon').show();
		var _thatName = _that.siblings('.lists_name').text();
		var _thatAlias = _that.siblings('.lists_alias').text();
		var _thatType = _that.siblings('.lists_sort').text();
		var _thatStatusAllow = _that.siblings('.lists_type').attr("value");
		$('.addMetaName').val(_thatName);
		$('.addMetaAlias').val(_thatAlias);
		$('.addMetaSort').val(_thatType);
		$('.addMetaType').val(_thatStatusAllow);
		$('.keepCancle').attr("metadata_id",_thatId);
		$('.keepConfirm').attr("metadata_id",_thatId);
		$('.inviteCancle').on('click', function() {
			$('.shade').hide();
			$('.shadeCon').hide();
		})
	})
	//add
	$('.addLists').on('click',function(){
		$('.shadeMain h4').text('新增元数据');
		$('.addMetaName').val('');
		$('.addMetaAlias').val('');
		$('.addMetaSort').val('0');
		$('.addMetaType').val('');
		$('.shade').show();
		$('.shadeCon').show();
		$('.shadeBotPos').find('.editConfirm').removeClass('editConfirm').addClass('keepConfirm');
		$('.shadeBotPos').find('.editCancle').removeClass('editCancle').addClass('keepCancle');
		$('.keepCancle').attr("metadata_id","0");
		$('.keepConfirm').attr("metadata_id","0");
		$('.inviteCancle').on('click', function() {
			$('.shade').hide();
			$('.shadeCon').hide();
		})
	})
	
	//启用或者禁用
	$('.mainLists').on('click','.allowUn',function(){
		var r = confirm('确认操作？');
		var that = $(this);
		if(r == true){
			if(that.val() == '禁用'){
				status = '0';
			}else{
				status = '1';
			}
			var dataS = {
				access_token:localStorage.access_token,
				status:status,
				metadata_id:that.attr('id')
			}
			$.ajax({
				contentType: "application/json; charset=utf-8",
				type: 'POST',
				url:"/updateMetadataStatus",
				dataType: 'json',
				data: JSON.stringify(dataS),
				success:function(responseText, textStatus, jqXHR){
					if(responseText.error_no == '0'){
						if(status == 0){
							that.val('启用');
							that.parent().siblings('.listStatusAllow').text('禁用')
						}else{
							that.val('禁用');
							that.parent().siblings('.listStatusAllow').text('启用')
						}
					}else{
						alert(responseText.error_info)
					}
				},error:function(){
					
				}
			})
		}
	})
	
	listsReady(data);
	getAllOperator();
})

//查询
function searchOnclick(flag){
	var dataName = $('.dataName');
	var dataAlias = $('.dataAlias');
	var dataType = $('.dataType');
	var dataStatus = $('.dataStatus');
	var dataPerson = $('.dataPerson');
	var dataTimeFrom = $('#dateFrom');
	var dataTimeTo = $('#dateTo');
	var reg = /\d+/g;
	var a = null;
	var b = null;
	var c = null;
	var d = null;
	if(dataTimeFrom.val()!=''){
		a = dataTimeFrom.val().match(reg)[0]+dataTimeFrom.val().match(reg)[1]+dataTimeFrom.val().match(reg)[2]
	}
	if(dataTimeTo.val() != ''){
		b = dataTimeFrom.val().match(reg)[3]+dataTimeFrom.val().match(reg)[4]+dataTimeFrom.val().match(reg)[5]
	}
	if(dataTimeTo.val() != ''){
		c = dataTimeTo.val().match(reg)[0]+dataTimeTo.val().match(reg)[1]+dataTimeTo.val().match(reg)[2]
	}
	if(dataTimeTo.val() != ''){
		d = dataTimeTo.val().match(reg)[3]+dataTimeTo.val().match(reg)[4]+dataTimeTo.val().match(reg)[5]
	}
	data={
		page:1,
		limit:15,
		metadata_name:dataName.val().trim(),
		alias:dataAlias.val().trim(),
		update_user:dataPerson.val().trim(),
		update_date_start: a,
		update_date_end: c,
		update_time_start:b,
		update_time_end:d,
		status:dataStatus.val(),
		type:dataType.val()
	}
	if(flag == 1){
		data.page=$('#page_now').val();
	}
	data.limit=$('#page_limit').val();
	listsReady(data);
}

function listsReady(data){
		$.ajax({
			contentType: "application/json; charset=utf-8",
			type: 'POST',
			url:"/queryMetadata",
			dataType: 'json',
			data: JSON.stringify(data),
			success:function(responseText, textStatus, jqXHR){ 
				var dataHtml = '<tr><th>序号</th><th>名称</th><th>别名</th><th>类别</th><th>排序</th><th>状态</th><th>创建日期</th><th>创建者</th><th>最后操作日期</th><th>最后操作者</th><th>操作</th></tr>';
				if(responseText.error_no == '0'){
					var resultList = responseText.result_list;
					$("#total_num").html(responseText.total_num);
					$(".page").html(pageHtml(data.page,data.limit,responseText.total_num));
					var count = 0;
					resultList.forEach(function(item) {
						count++;
						var status = "启用";
						var button = "禁用";
						if(item.status == '0'){
							status = "禁用";
							button = "启用";
						}
						var type = "计量单位";
						if(item.type == '1'){
							type = "机械类型";
						}
						var init_date_format = dateFormat(item.init_date,item.init_time);
						var update_date_format = dateFormat(item.update_date,item.update_time);
						
						dataHtml += '<tr class="tableEditNone"><td>'+count+'</td><td class="lists_name">'+item.metadata_name+'</td>'
						+'<td class="lists_alias">'+item.alias+'</td>'+'<td class="lists_type" value="'+item.type+'">'+type+'</td>'+'<td class="lists_sort">'+item.sort+'</td>'
						+'<td class="listStatusAllow">'+status+'</td>'+'<td>'+init_date_format+'</td>'+'<td>'+item.create_user+'</td>'
						+'<td>'+update_date_format+'</td>'+'<td>'+item.update_user+'</td>'
						+'<td><input type="button" name="" id="'+ item.metadata_id +'" value="编 辑" class="tableBot3 dataEditBot" />&nbsp;'
						+'<input type="button" name="" id="'+item.metadata_id+'" value="'+button+'" class="tableBot4 allowUn" /></td></tr>';
					});
					//clear
					$('.clearInput').on('click',function(){
						$('.inputTex').val('');
						$('.dataType').find('option:first').prop('selected', true);
						$('.dataStatus').find('option:first').prop('selected', true);
						showAllOperator();
					})
//					$('.dataType').on('change',function(){
//						alert($(this).find('option:selected').text())
//					})
					
				}
				$("#dataGrid").html(dataHtml);
				//$(province_class).append(cityHtml);
			},
			error: function(xhr, type) {
				//alert("网络异常");
			}
		});
	};
	
//刷新缓存
function reloadCache(){
	var data={
		access_token:localStorage.access_token	
	}
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url:"/reloadAllMetadata",
		dataType: 'json',
		data: JSON.stringify(data),
		success:function(responseText, textStatus, jqXHR){
			if(responseText.error_no == '0'){
				alert("刷新缓存成功！");
			}else{
				alert(responseText.error_info);
			}
		},error:function(){
		}
	})
}

//新增或者修改
function submitMetaData(status){
	$('.keepCancle').attr("onclick","");
	$('.keepConfirm').attr("onclick","");
	var metadata_id = $('.keepCancle').attr("metadata_id");
	if(status == '1'){//按的是保存并启用按钮
		metadata_id = $('.keepConfirm').attr("metadata_id");
	}
	var data = {
		metadata_name:$('.addMetaName').val().trim(),
		alias:$('.addMetaAlias').val().trim(),
		type:$('.addMetaType').val(),
		sort:$('.addMetaSort').val().trim(),
		access_token:localStorage.access_token,
		metadata_id:metadata_id,
		status:status
	}
	if($(".addMetaType").val() == '' || data.metadata_name == '' ){
		alert('请填写信息');
		$('.keepCancle').attr("onclick","submitMetaData("+status+")");
		$('.keepConfirm').attr("onclick","submitMetaData("+status+")");
		return;
	}
	var url = "/updateMetadata";
	if(metadata_id == '0'){
		url = "/addMetadata";
	}
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url:url,
		dataType: 'json',
		data: JSON.stringify(data),
		success:function(responseText, textStatus, jqXHR){
			$('.keepCancle').attr("onclick","submitMetaData("+status+")");
			$('.keepConfirm').attr("onclick","submitMetaData("+status+")");
			if(responseText.error_no == '0'){
				alert('操作成功！');
				location.reload();
			}else{
				alert(responseText.error_info);
			}
			
		},error:function(){
			$('.keepCancle').attr("onclick","submitMetaData("+status+")");
			$('.keepConfirm').attr("onclick","submitMetaData("+status+")");
		}
	})
	
}
