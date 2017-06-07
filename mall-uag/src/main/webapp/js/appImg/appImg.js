$(document).ready(function() {
	//编辑
	$('.mainLists').on('click','.dataEditBot',function(data){
		$('.shadeMain h4').text('编辑图片展示');
		var _that = $(this).parent();
		var _thatId = $(this).attr('id');
		$('.shade').show();
		$('.shadeCon').show();
		$('#picture_path').val('');
		var _thatSort = _that.siblings('.app_sort').text();
		var _thatType = _that.siblings('.app_type').attr('value');
		var _thatPath = _that.siblings('.showImg').find('img').attr("name");
		var _thatUrl = _that.siblings('.app_url').text();
		var _thatSkip = _that.siblings('.app_skip').attr('value');
		var _thatRemark = _that.siblings('.app_remark').text();
		$('.sortStep').val(_thatSort);
		$('.urlAddress').val(_thatUrl);
		$('.beizhu').val(_thatRemark);
		$('.inputFileShow').val(_thatPath);
		$('#picture_data').val(_thatPath);
		$('.baOrzhan').val(_thatType);
		$('.skipOrNot').val(_thatSkip);
		$('.inviteKeep').attr("banner_id",_thatId);
		
		//选择图片
		$("#picture_path").bind("change", function() {
	//		var thisV = $(this).val();
	//		$('#first_nav_display').val(thisV)
			var thisV = $(this).val();
			if(thisV != ''){
				$('#picture_path_display').val(thisV);
				getFileData(this);
			}
		});
		$('.inviteCancle').on('click', function() {
			$('.shade').hide();
			$('.shadeCon').hide();
		})
	});
	//add
	$('.addAppBot').on('click',function(){
		$('.shadeMain h4').text('新增图片展示');
		$('.shade').show();
		$('.shadeCon').show();
		$('.sortStep').val('0');
		$('.urlAddress').val('');
		$('.beizhu').val('');
		$('.inputFileShow').val('');
		$('#picture_data').val('');
		$('.baOrzhan').val('0');
		$('.skipOrNot').val('0');
		$('.inviteKeep').attr("banner_id","0");
		//选择图片
		$("#picture_path").bind("change", function() {
	//		var thisV = $(this).val();
	//		$('#first_nav_display').val(thisV)
			var thisV = $(this).val();
			if(thisV != ''){
				$('#picture_path_display').val(thisV);
				getFileData(this);
			}
		});
		$('.inviteCancle').on('click', function() {
			$('.shade').hide();
			$('.shadeCon').hide();
		})
	});
	//查看大图
	$('#dataGrid').on('click', '.showImg', function() {
		var $index = $(this).parent().index();
		$('.shade').show();
		$('.shadeConst').show();
		var imgId = "#img" + $index;
		var img = $(imgId).attr("src");
		var $img = $("<img id='show" + imgId + "' src='" + img + "' />")
		$('.shadeConst .showImage').append($img);
		$('.inviteCancle').on('click', function() {
			$('.shade').hide();
			$('.shadeConst').hide();
			$($img).remove();
		});
	});
	
	//sure||unsure
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
				banner_id:that.attr('id')
			}
			$.ajax({
				contentType: "application/json; charset=utf-8",
				type: 'POST',
				url:"/updateBannerStatus",
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
		}else{
			
		}
	});
	
	searchOnclick(0);
});

//新增或者修改保存
function submitButton() {
	$('.inviteKeep').attr("onclick","");//防止重复提交
	var banner_id = $('.inviteKeep').attr("banner_id");
	if($('.urlAddress').val().trim() != ''){
		if(!checkUrl($('.urlAddress').val().trim())){
			alert("链接地址不是以http://或https://开头，或者不是网址！请重新输入");
			$('.inviteKeep').attr("onclick","submitButton()");
			return;
		}
	}
	if($('.sortStep').val().length > 3){
		alert("排序只能1-3位");
		$('.inviteKeep').attr("onclick","submitButton()");
		return;
	}
	
	var data = {
		banner_path:$('#picture_data').val(),
		app_url:$('.urlAddress').val().trim(),
		type:$('.baOrzhan').val(),
		sort:$('.sortStep').val(),
		access_token:localStorage.access_token,
		is_skip:$('.skipOrNot').val(),
		remark:$('.beizhu').val(),
		banner_id,banner_id
	}
	if($(".baOrzhan").find("option:selected").text() == '请选择类别' || $(".baOrzhan").val()== ''|| data.banner_path == ''){
		alert('请填写信息');
		$('.inviteKeep').attr("onclick","submitButton()");
		return;
	}
	var url_path = "/addBanner";
	if(banner_id != '0'){
		url_path = "/updateBanner"
	}
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url:url_path,
		dataType: 'json',
		data: JSON.stringify(data),
		success:function(responseText, textStatus, jqXHR){
			$('.inviteKeep').attr("onclick","submitButton()");
			if(responseText.error_no == '0'){
				if(banner_id == '0'){
					alert('新增成功！');
				}else{
					alert('修改成功！');
				}
				$('.shade').hide();
				$('.shadeCon').hide();
				searchOnclick(1);
			}else{
				alert(responseText.error_info);
			}
			
		},error:function(){
			$('.inviteKeep').attr("onclick","submitButton()");
		}
	})
	
}


function searchOnclick(flag) {
	var data = { page: 1, limit: 15};
	if(flag == 1){
		data.page=$('#page_now').val();
	}
	data.limit=$('#page_limit').val();
	listsReady(data)
}

function getFileData(target){ 
	var file = target.files[0]; 
	var reader = new FileReader(); 
	reader.readAsDataURL(file);
	if(file.size/1024>100){//首页图片需要清晰，如果不大于50kb ，不触发压缩
		reader.onload = function(e){ 
			var img = new Image();
			img.src=this.result;
			img.onload=function(){
				var base64 = compressAppIndex(img, 0.9, "image/jpeg");
				$('#picture_data').val(base64);
			}
		} 
	}else{
		reader.onload = function(e){ 
			$('#picture_data').val(this.result);
		}
	}
} 

function listsReady(data){
		$.ajax({
			contentType: "application/json; charset=utf-8",
			type: 'POST',
			url:"/getBannerList",
			dataType: 'json',
			data: JSON.stringify(data),
			success:function(responseText, textStatus, jqXHR){ 
				var dataHtml = '<tr><th>序号</th><th>图片类型</th><th>顺序</th><th>图片</th><th>是否跳转</th><th>跳转地址</th><th>添加人</th><th>添加时间</th><th>最后修改人</th><th>最后修改时间</th><th>备注</th><th>状态</th><th>操作</th></tr>';
				if(responseText.error_no == '0'){
					var resultList = responseText.result_list;
					$("#appImg_num").html(responseText.total_num);
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
						
						var type = "首页banner";
						if(item.type == '1'){
							type = "首页占位图";
						}
						var is_skip = '不跳转';
						if(item.is_skip == '1'){
							is_skip = '跳转';
						}
						

						var init_date_format = dateFormat(item.init_date,item.init_time);
						var update_date_format = dateFormat(item.update_date,item.update_time);
						
						dataHtml += '<tr class="tableEditNone"><td>' + count + '</td><td class="app_type" value="'+item.type+'">' + type + '</td>' +
						'<td class="app_sort">' + item.sort + '</td>'+
						'<td class="showImg"><img id="img' + count + '" name="'+item.banner_path+'" src="' +ossUrl+ item.banner_path + '">  点击查看大图</td>'+
						'<td class="app_skip" value="'+item.is_skip+'">' + is_skip + '</td><td class="app_url"><a href="'+item.app_url+'" target="_Blank">' + item.app_url + '</a></td>'+
						'<td>' + item.create_user_name + '</td><td>' + init_date_format + '</td>' +
						'<td>' + item.update_user_name + '</td><td>' + update_date_format + '</td><td class="app_remark">' + item.remark + '</td><td  class="listStatusAllow">' + status + '</td>' +
						'<td><input type="button" name="" id="'+ item.banner_id +'" value="编 辑" class="tableBot3 dataEditBot" />  ' +
						'<input type="button" name="" id="'+item.banner_id+'" value="'+button+'" class="tableBot4 allowUn" /></td></tr>';
					});
					
				}
				$("#dataGrid").html(dataHtml);
				//$(province_class).append(cityHtml);
			},
			error: function(xhr, type) {
				//alert("网络异常");
			}
		});
	};
