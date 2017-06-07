$(document).ready(function() {
	var data = {
		page: 1,
		limit: 15
	};
	//编辑
	$('.mainLists').on('click', '.categoryEdit', function(data) {
		$('.shade').show();
		$('.shadeCons').show();
		$('.shadeMain h4').text('编辑机型');
		//从列表中获取数据
		var _that = $(this).parent();
		var _thatId = $(this).attr('id');
		$('li.bor1.params').remove();
		$('li.bor1.paramsShow').remove();
		var _app_show = _that.siblings('#appShow').attr('app_show');
		var _thatSrc = _that.siblings('.nowImgSrc').children('img').attr('src').trim();
		var _thatType = _that.siblings('.listsType').text().trim();
		var _thatCarModelId = _that.siblings('.carModelId').text();
		var _thatModelName = _that.siblings('.car_modelName').text().trim();
		var _thatBrandId = _that.siblings('.brandName').attr('brandid').trim();
		var _thatMetadataId = _that.siblings('.listsType').attr('metadata_id');
		var _thatcarParamList = _that.siblings('.carParamList').attr('info');
		//编辑页面传值
		if(_app_show == '1') {
			$('input#add_first_nav_sort').prop("checked", true);
		} else {
			$('input#add_first_nav_sort').prop("checked", false);
		}
		$(".carTypes").val(_thatMetadataId);
		$('.carbrand').attr('carmodelid', _thatCarModelId);
		$('.carbrand').attr('brandid', _thatBrandId);
		var obj = eval('(' + unescape(_thatcarParamList) + ')');
		$('#car_model_brand').val(_that.siblings('.brandName').text());
		$('.dataImgSrc img').attr('src', _thatSrc);
		$('#car_model_name').val(_thatModelName);
		var carModelInfo = '';
		if(obj.length > 0) {
			for(var i = 0; i < obj.length; i++) {
				carModelInfo += '<li class="bor1 params"><label><span>*</span>名称</label><input type="text" name="' + obj[i].car_params_id + '" id="" value="' + obj[i].car_params_name + '" placeholder="（2-50字符限制）" class="inviteNum car_paramName" maxlength="50" /><label style="text-align: right;padding-right: 10px;"><span>*</span>值</label><input type="text" name="" id="" value="' + obj[i].car_params_value + '" placeholder="（2-50字符限制）" class="inviteNum car_paramValue" maxlength="50" /><input type="button" name="" id="" value="删除" class="inputBot3 deletePara"/></li>';

			}
			$('.addP').before(carModelInfo);

		}

		$('.inviteCancle').on('click', function() {
			$('.shade').hide();
			$('.shadeCons').hide();
		})
		
		return false;
	});
	//保存并启用
	$('.saveCarModelEnable').on('click', function(data) {
		var aa = $('.params');

		var carParamList = [];
		if(aa.length > 0) {
			for(var i = 0; i < aa.length; i++) {
				var _thatName = $('.car_paramName')[i].value;
				var _thatId = $('.car_paramName')[i].name;
				var _thatValue = $('.car_paramValue')[i].value;
				var carParam = {
					car_params_id: _thatId,
					car_params_name: _thatName,
					car_params_value: _thatValue
				};
				if(_thatName.trim().length > 0 && _thatValue.trim().length > 0) {
					carParamList.push(carParam);
				} else {
					alert("请完善参数信息");
					return;
				}
			}
		}
		var app_show = '1';
		app_show = $('#add_first_nav_sort').is(':checked') ? 1 : 0;
		var data = {
			brand_name: $('.carbrand').val(),
			brand_id: $('.carbrand').attr('brandid'),
			car_models_id: $('.carbrand').attr('carmodelid'),
			metadata_name: $(".carTypes").find("option:selected").text(),
			metadata_id: $('.carTypes').val(),
			access_token: localStorage.access_token,
			status: 1,
			car_models_name: $('#car_model_name').val(),
			app_show: app_show
		}
		if(carParamList.length > 0) {
			data.carParamsList = carParamList;
		}
		if(data.metadata_name.trim().length = 0) {
			alert('请选择类型');
			return;
		}
		if(data.car_models_name.trim().length = 0) {
			alert('请填写型号');
			return;

		}
		if(data.car_models_name.trim().length > 10 || data.car_models_name.trim().length < 2) {
			alert('型号字数限制在2-10之间');
			return;

		} else {
			$.ajax({
				contentType: "application/json; charset=utf-8",
				type: 'POST',
				url: "/updateCarModel",
				dataType: 'json',
				data: JSON.stringify(data),
				success: function(responseText, textStatus, jqXHR) {
					alert(responseText.error_info);
					searchOnclick(1);
					$('.shadeCon').hide();
					$('.shade').hide();
				},
				error: function() {}
			})
		}
		$('.shade').hide();
		$('.shadeCons').hide();
	});
	//保存并禁用
	$('.saveCarModelDisable').on('click', function(data) {
		var aa = $('.params');
		var carParamList = [];
		if(aa.length > 0) {
			for(var i = 0; i < aa.length; i++) {
				var _thatName = $('.car_paramName')[i].value;
				var _thatId = $('.car_paramName')[i].name;
				var _thatValue = $('.car_paramValue')[i].value;
				var carParam = {
					car_params_id: _thatId,
					car_params_name: _thatName,
					car_params_value: _thatValue
				};
				if(_thatName.trim().length > 0 && _thatValue.trim().length > 0) {
					carParamList.push(carParam);
				} else {
					alert("请完善参数信息");
					return;
				}
			}
		}
		var app_show = '1';
		app_show = $('#add_first_nav_sort').is(':checked') ? 1 : 0;
		var data = {
			brand_name: $('.carbrand').val(),
			brand_id: $('.carbrand').attr('brandid'),
			car_models_id: $('.carbrand').attr('carmodelid'),
			metadata_name: $(".carTypes").find("option:selected").text(),
			metadata_id: $('.carTypes').val(),
			access_token: localStorage.access_token,
			status: 0,
			car_models_name: $('#car_model_name').val(),
			app_show: app_show
		}
		if(carParamList.length > 0) {
			data.carParamsList = carParamList;
		}
		if(data.metadata_name.trim().length = 0) {
			alert('请选择类型');
			return;
		}
		if(data.metadata_name.trim().length = 0) {
			alert('请填写型号');
			return;

		}
		if(data.metadata_name.trim().length > 10 || data.metadata_name.trim().length < 2) {
			alert('型号字数限制在2-10之间');
			return;

		} else {
			$.ajax({
				contentType: "application/json; charset=utf-8",
				type: 'POST',
				url: "/updateCarModel",
				dataType: 'json',
				data: JSON.stringify(data),
				success: function(responseText, textStatus, jqXHR) {
					alert(responseText.error_info);
					searchOnclick(1);
					$('.shadeCon').hide();
					$('.shade').hide();
				},
				error: function() {}
			})
		}
		$('.shade').hide();
		$('.shadeCons').hide();
	});
	//添加参数
	$('.addP').on('click', function() {
		var addP = $('<li class="bor1 params"><label><span>*</span>名称</label><input type="text" name="" id="" value="" placeholder="（2-50字符限制）" class="inviteNum car_paramName" maxlength="50" /><label style="text-align: right;padding-right: 10px;"><span>*</span>值</label><input type="text" name="" id="" value="" placeholder="（2-50字符限制）" class="inviteNum car_paramValue" maxlength="50"/><input type="button" name="" id="" value="删除" class="inputBot3 deletePara"/></li>');
		$(this).before(addP);
	});
	//全选事件
	$("#carModelTable").on('click', '#checkAll', function() {

		if($(this).is(':checked')) {
			$("#carModelTable .cklist").prop("checked", true);
			$('.tableEdit').addClass('trActive')
		} else {
			$("#carModelTable .cklist").prop("checked", false);
			$('.tableEdit').removeClass('trActive')
		}
	});
	dataUpdate(data);
	listsReady();
	queryblur();
});
//清除
$('.clearInput').on('click', function() {
	var carmodelId = $('.carmodelId');
	var carmodelBrand = $('.carmodelBrand');
	//var brand_name = $('.carmodelBrand');
	$("#pattern_name .pattern_item").css('display', 'block');
	var carmodelBrandId=carmodelBrand.attr('brand_id');
	var carmodelType = $('.carmodelType');
	var carmodelName = $('.carmodelName');
	var is_noparams = $('#is_noparams');
	var start_date = $('#dateFrom');
	var end_date = $('#dateTo');
	//brand_name.val('');
	carmodelId.val('');
	carmodelBrand.val('');
	carmodelBrandId='';
	carmodelType.val('');
	carmodelName.val('');
	is_noparams.val('2');
	start_date.val('');
	end_date.val('');
});


function searchOnclick(flag) {
	var carmodelId = $('.carmodelId');
	var carmodelBrand = $('.carmodelBrand').attr('brand_id');
	var brand_name = $('.carmodelBrand').val().trim();
	var carmodelType = $('.carmodelType');
	var carmodelName = $('.carmodelName');
	var options = $("#is_noparams option:selected"); //获取选中的项
	var is_noparams = options.val();
	var dataTimeFrom = $('#dateFrom');
	var dataTimeTo = $('#dateTo');
	var reg = /\d+/g;
	var a = null;
	var b = null;
	var c = null;
	var d = null;
	if(dataTimeFrom.val() != '') {
		a = dataTimeFrom.val().match(reg)[0] + dataTimeFrom.val().match(reg)[1] + dataTimeFrom.val().match(reg)[2] + dataTimeFrom.val().match(reg)[3] + dataTimeFrom.val().match(reg)[4] + dataTimeFrom.val().match(reg)[5]
	}
	if(dataTimeTo.val() != '') {
		c = dataTimeTo.val().match(reg)[0] + dataTimeTo.val().match(reg)[1] + dataTimeTo.val().match(reg)[2] + dataTimeTo.val().match(reg)[3] + dataTimeTo.val().match(reg)[4] + dataTimeTo.val().match(reg)[5]
	}
	var start_date = a;
	var end_date = c;
	//var car_model_alias=carmodelBrand.val()+carmodelType.val();
	var data = {
		page: 1,
		limit: 15,
		access_token: localStorage.access_token,
		car_models_name: carmodelName.val().trim(),
		car_models_id: carmodelId.val().trim(),
		metadata_name: carmodelType.val().trim(),
		//brand_id: carmodelBrand,
		end_date: end_date,
		start_date: start_date,
		is_noparams: is_noparams,
		brand_name:brand_name
	};
	if(flag == 1) {
		data.page = $('#page_now').val();
	}
	data.limit = $('#page_limit').val();
	dataUpdate(data);
}

function dataUpdate(data) {
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: "/queryCarModelList",
		dataType: 'json',
		data: JSON.stringify(data),
		success: function(result) {
			$('#total_num_carModel').text(result.total_num)
			var tableList = '<tr > <th><input id="checkAll" type="checkbox" name="checkbox1" value="checkbox"></th><th>序号</th><th>代码</th><th>品牌</th><th>品牌logo</th><th>类型</th><th>型号</th> <th>参数</th><th>创建日期</th><th>创建者</th> <th>最后操作日期</th><th>最后操作者</th><th>状态</th><th>APP显示</th><th>操作</th></tr>';
			var listsTdNum = result.result_list;

			var conNum = 0;
			$(".page").html(pageHtml(data.page, data.limit, result.total_num));
			var listCount = 0;
			listsTdNum.forEach(function(a) {
				conNum++;
				listCount++;
				var appShow = "APP显示";
				if(a.app_show == '0') {
					appShow = "APP不显示";
				}
				var status = "启用";
				var button = "禁用";
				if(a.status == '0') {
					status = "禁用";
					button = "启用";
				}
				var start_date = dateFormat(a.create_date, a.create_time);
				var update_date = dateFormat(a.update_date, a.update_time);
				tableList += '<tr class="tableEdit"><td><input id="" type="checkbox" name="checkbox1"  value="' + a.car_models_id + '" class="cklist"></td><td>' + listCount + '</td><td class="carModelId">' + a.car_models_id + '</td>' +
					'<td class="brandName" brandid="' + a.brand_id + '">' + a.brand_name + '</td>' + '<td class="nowImgSrc">' + '<img src="' + ossUrl + a.brand_logo + '"></td>' + '<td class="listsType" metadata_id="' + a.metadata_id + '">' + a.metadata_name + '</td>' +
					'<td class="car_modelName">' + a.car_models_name + '</td>';
				if(a.carParamsList.length > 0) {
					tableList += '<td class="carParamList" value="" info="' + escape(JSON.stringify(a.carParamsList)) + '">有</td>';
				} else {
					tableList += '<td class="carParamList" value="" info="' + escape(JSON.stringify(a.carParamsList)) + '">无</td>';
				}
				tableList += '<td>' + start_date + '</td>' + '<td>' + a.create_user + '</td>' +
					'<td>' + update_date + '</td>' + '<td>' + a.update_user_name + '</td>' + update_date + '<td>' + status + '</td>' + '<td id="appShow" app_show=' + a.app_show + '>' + appShow + '</td>' +
					'<td><input type="button" name="" id="" value="查 看" class="tableBot1 addBot" /> ' +
					'<input type="button" name="" id=""  value="编 辑" class="tableBot3 categoryEdit" /> ' +
					'<input type="button" name="' + a.status + '" id="'+ a.car_models_id +'" value="' + button + '" class="tableBot4 categoryStart"/>' +
					'</td></tr>';
			});
			$(".list table").html(tableList);

			$('.list').on('click', '.addBot', function() {
				$('.shadeMain h4').text('查看机型');
				//从列表中获取数据
				var _that = $(this).parent();
				var _thatId = $(this).attr('id');
				$('li.bor1.paramsShow').remove();
				$('li.bor1.params').remove();
				var _app_show = _that.siblings('#appShow').attr('app_show');
				var _thatSrc = _that.siblings('.nowImgSrc').children('img').attr('src');
				var _thatType = _that.siblings('.listsType').text();
				var _thatCarModelId = _that.siblings('.carModelId').text();
				var _thatModelName = _that.siblings('.car_modelName').text();
				var _thatBrandId = _that.siblings('.brandName').attr('brandid');
				var _thatMetadataId = _that.siblings('.listsType').attr('metadata_id');
				var _thatcarParamList = _that.siblings('.carParamList').attr('info');
				var obj = eval('(' + unescape(_thatcarParamList) + ')');
				if(_app_show == '1') {
					$('#add_first_nav_sort_show').prop("checked", true);
				} else {
					$('#add_first_nav_sort_show').prop("checked", false);
				}
				$('#add_first_nav_sort_show').attr("disabled", true)
				$('span.brandShow').text(_that.siblings('.brandName').text().trim());
				$('.dataImgSrcShow img').attr('src', _thatSrc);
				$('.carTypeShow').text(_thatType.trim());
				$('.carModelShow').text(_thatModelName.trim());
				var carModelInfo = '';
				if(obj.length > 0) {
					for(var i = 0; i < obj.length; i++) {
						carModelInfo += '<li class="bor1 paramsShow"><label><span>*</span>名称</label><span class="describe">' + obj[i].car_params_name + '</span><label style="text-align: right;padding-right: 10px;"><span>*</span>值</label><span class="describe">' + obj[i].car_params_value + '</span></li>';
					}

					$('.addInvite').append(carModelInfo);

				}
				$('.params').html(carModelInfo);
				$('.inviteCancle').on('click', function() {
					$('.shadeCon').hide();
					$('.shade').hide();
				})
				$('.shadeCon').show();
				$('.shade').show();
				return false;
			})

		},
		error: function() {}
	});
}

function listsReady() {
	var data = {
		page: 1,
		limit: 10000,
		status: 1,
		type: 1
	};
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: "/queryMetadata",
		dataType: 'json',
		data: JSON.stringify(data),
		success: function(responseText, textStatus, jqXHR) {
			if(responseText.error_no == '0') {
				var resultList = responseText.result_list;
				var carTypes = '';
				resultList.forEach(function(item) {
					carTypes += '<option value="' + item.metadata_id + '">' + item.metadata_name + '</option>';
				})
				$('.carTypes').append(carTypes);

			}
		},
		error: function(xhr, type) {}
	});
};
//禁用启用
$('.list').on('click', '.categoryStart', function() {
	var status=$(this).attr('name');
	var id=$(this).attr('id');
	if(status == '0') {
		status = '1';
	} else {
		status = '0'
	}
	var info = "确定要启用该机型吗？";
	info = status == 0 ? '确定要禁用该机型吗？' : '确定要启用该机型吗？';
	if(confirm(info)) {
		var data = {
			car_models_ids: id,
			status: status,
			access_token: localStorage.access_token
		}

	} else {
		return false;
	}

	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: "/updateCarStatusBatch",
		dataType: 'json',
		data: JSON.stringify(data),
		success: function(responseText, textStatus, jqXHR) {
			alert(responseText.error_info);
		},
		error: function() {}
	})
});

//批量修改状态
function updateStatusBatch(status) {
	var checkList = $("#carModelTable .cklist");
	var check_num = 0;
	var ids = '';
	for(var i = 0; i < checkList.length; i++) {
		if(checkList[i].checked) {
			check_num++;
			ids += (checkList[i].value) + ',';
		}
	}
	if(check_num < 1) { //未选择商品
		alert("请至少选择一种机型!");
		return;
	}
	var info = "确定要启用该机型吗？";
	info = status == 0 ? '确定要禁用该机型吗？' : '确定要启用该机型吗？';
	if(confirm(info)) {
		var data = {
			car_models_ids: ids,
			status: status,
			access_token: localStorage.access_token
		}

	} else {
		return;
	}
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: "/updateCarStatusBatch",
		dataType: 'json',
		data: JSON.stringify(data),
		success: function(responseText, textStatus, jqXHR) {
			alert(responseText.error_info);
			searchOnclick(1);
		},
		error: function() {}
	})

}