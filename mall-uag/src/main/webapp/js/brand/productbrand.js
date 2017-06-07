$(document).ready(function() {
	getAllOperator();
	//getAllProductBrand()
	var data = {
		page: 1,
		limit: 15
	};
	var dataStatusNum, dataTypeNum;
	$(".dataStatus").change(function() {
		var checkText = $(".dataStatus").find("option:selected").text();
		if(checkText == '禁用') {
			dataStatusNum = 0
		} else if(checkText == '启用') {
			dataStatusNum = 1
		}
	})
	$(".dataType").change(function() {
		var checkText = $(".dataType").find("option:selected").text();
		if(checkText == '计量单位') {
			dataTypeNum = 0
		} else if(checkText == '机械类型') {
			dataTypeNum = 1
		}
	})
	//编辑商品品牌
	$('.mainLists').on('click', '.dataEditBot', function(data) {
		initform(".editform");//清空上次弹出框记录
		$('.shadeMain h4').text('编辑商品品牌');
		var _that = $(this).parent();
		var _brand_id = _that.attr('brand_id');
		var _brand_name = _that.attr('brand_name');
		var _brand_logo = _that.attr('brand_logo');
		var _is_top = _that.attr('is_top');
		var _sort = _that.attr('sort');
		$('.shadeBotPos').find('.keepConfirm').removeClass('keepConfirm').addClass('editConfirm');
		$('.shadeBotPos').find('.keepCancle').removeClass('keepCancle').addClass('editCancle');
		$('.shade').show();
		$('.shadeCons').show();

		var thisEditWin = $('.editform');
		thisEditWin.find('.brand_name').val(_brand_name);
		thisEditWin.find('.brand_id').val(_brand_id);
		thisEditWin.find('.sort').val(_sort);
		thisEditWin.find('.brand_logo').prev().val(_brand_logo);
		if(_is_top == '1') {
			$(".sortckbox").prop("checked", true);
			$(".sortckbox").next().show();
			$(".sortckbox").next().next().show();
		} else {
			$(".sortckbox").prop("checked", false);
			$(".sortckbox").next().hide();
			$(".sortckbox").next().next().hide();
		}

		$(".sortckbox").on('click', function() {
			if($(this).is(':checked')) {
				$(this).next().show();
				$(this).next().next().show();
			} else {
				$(this).next().next().hide();
				$(this).next().hide();
			}
		})

		$('.inviteCancle').on('click', function() {
			initform(".editform");//清空上次弹出框记录
			$('.shade').hide();
			$('.shadeCon').hide();
		})
		return false;
	})
	//add
	$('.addLists').on('click', function() {
		initform(".addform");//清空上次弹出框记录
		$('.shadeMain h4').text('新增品牌');
		$('.shade').show();
		$('.shadeCon').show();
		$('.shadeBotPos').find('.editConfirm').removeClass('editConfirm').addClass('keepConfirm');
		$('.shadeBotPos').find('.editCancle').removeClass('editCancle').addClass('keepCancle');
		$(".sortckbox").on('click', function() {
			if($(this).is(':checked')) {
				$(this).next().show();
				$(this).next().next().show();
			} else {
				$(this).next().next().hide();
				$(this).next().hide();
			}
		})
		//取消
		$('.inviteCancle').on('click', function() {
			initform(".addform");//清空上次弹出框记录
			$('.shade').hide();
			$('.shadeCon').hide();
		})
	})
	//search
	$('.searchLists').on('click', function() {
		loadList();
	})
	loadList();

})

//导出数据
$("#export").on('click', function() {
	var _brand_name = $('.mainOperationInput #brand_name').val().trim();
	var _brand_id = $('.mainOperationInput #brand_id').val().trim();
	var _status = $('.mainOperationInput #status').val();
	var _create_user = $('.mainOperationInput #create_user').val();
	var _is_top = $('.mainOperationInput #is_top').val();
	var dataTimeFrom = $('#dateFrom');
	var dataTimeTo = $('#dateTo');
	var _page = $('#page_now').val();
	var _limit = $('#page_limit').val();
	var reg = /\d+/g;
	var a = "";
	var b = "";
	var c = "";
	var d = "";
	if(dataTimeFrom.val() != '') {
		a = dataTimeFrom.val().match(reg)[0] + '-' + dataTimeFrom.val().match(reg)[1] + '-' + dataTimeFrom.val().match(reg)[2]
	}
	if(dataTimeFrom.val() != '') {
		b = dataTimeFrom.val().match(reg)[3] + ':' + dataTimeFrom.val().match(reg)[4] + ':' + dataTimeFrom.val().match(reg)[5]
	}
	if(dataTimeTo.val() != '') {
		c = dataTimeTo.val().match(reg)[0] + '-' + dataTimeTo.val().match(reg)[1] + '-' + dataTimeTo.val().match(reg)[2]
	}
	if(dataTimeTo.val() != '') {
		d = dataTimeTo.val().match(reg)[3] + ':' + dataTimeTo.val().match(reg)[4] + ':' + dataTimeTo.val().match(reg)[5]
	}
	data = {
		page: _page,
		limit: _limit,
		brand_name: _brand_name,
		brand_id: _brand_id,
		status: _status,
		is_top: _is_top,
		create_user_name: _create_user,
		date_start: a + b,
		date_end: c + d,
		access_token: localStorage.access_token
	}

	if(false) {
		alert('请填写信息');
	} else {
		var form = $("<form style='display:none' target='' method='post' action='/goodsbrand/export'><input type='hidden' name='page' value='"+
		data.page+"'><input type='hidden' name='limit' value='"+
		data.limit+"'><input type='hidden' name='brand_name' value='"+
		data.brand_name+"'><input type='hidden' name='brand_id' value='"+
		data.brand_id+"'><input type='hidden' name='status' value='"+
		data.status+"'><input type='hidden' name='is_top' value='"+
		data.is_top+"'><input type='hidden' name='create_user_name' value='"+
		data.create_user_name+"'><input type='hidden' name='date_start' value='"+
		data.date_start+"'><input type='hidden' name='date_end' value='"+
		data.date_end+"'>");

		$("body").append(form);
		form.submit();
		form.remove();

	}

})

//全选事件绑定
$("#dataGrid").on('click', '#checkAll', function() {
	if($(this).is(':checked')) {
		$("#dataGrid .cklist").prop("checked", true);
		$('.tableEdit').addClass('trActive')
	} else {
		$("#dataGrid .cklist").prop("checked", false);
		$('.tableEdit').removeClass('trActive')
	}
});

//批量启用
$(".add").on('click', '.btnact', function() {
	var _ids = "";
	var idchecklist = $("#dataGrid .cklist:checked");
	if(idchecklist.length == 0) {
		alert('你没有选择任何数据');
	} else {
		for(var i = 0; i < idchecklist.length; i++) {
			var obj = $(idchecklist[i]);
			if(i < idchecklist.length - 1) {
				_ids = _ids + obj.val() + ",";
			} else {
				_ids = _ids + obj.val();
			}
		}

		var data = {
			brand_id: _ids,
			access_token: localStorage.access_token,
			status: 1
		}
		if(!confirm('是否批量启用？')) {
			return;
		} else {
			$.ajax({
				contentType: "application/json; charset=utf-8",
				type: 'POST',
				url: "/goodsbrand/batchstatus",
				dataType: 'json',
				data: JSON.stringify(data),
				success: function(responseText, textStatus, jqXHR) {
					alert('批量启用成功！');
					loadpage();
				},
				error: function() {}
			})
		}
	}

})
//批量禁用
$(".add").on('click', '.btnfor', function() {
	var _ids = "";
	var idchecklist = $("#dataGrid .cklist:checked");
	if(idchecklist.length == 0) {
		alert('你没有选择任何数据');
	} else {
		for(var i = 0; i < idchecklist.length; i++) {
			var obj = $(idchecklist[i]);
			if(i < idchecklist.length - 1) {
				_ids = _ids + obj.val() + ",";
			} else {
				_ids = _ids + obj.val();
			}
		}

		var data = {
			brand_id: _ids,
			access_token: localStorage.access_token,
			status: 0
		}
		if(!confirm('是否批量禁用？')) {
			return;
		} else {
			$.ajax({
				contentType: "application/json; charset=utf-8",
				type: 'POST',
				url: "/goodsbrand/batchstatus",
				dataType: 'json',
				data: JSON.stringify(data),
				success: function(responseText, textStatus, jqXHR) {
					alert('批量禁用成功！');
					loadpage();
				},
				error: function() {}
			})
		}
	}

})
//获取添加时参数
function getParam(formclass) {
	var _brand_name = $(formclass).find(".brand_name").val();
	var _brand_logo = $(formclass).find(".brand_logo").val();
	var _brand_id = $(formclass).find(".brand_id").val();
	var ckbox = $(formclass).find(".sortckbox");
	var _is_top = 0,
		_sort = 0;
	if(ckbox.is(':checked')) {
		_is_top = 1;
		_sort = $(formclass).find(".sort").val();
	}
	var data = {
		brand_name: _brand_name,
		brand_logo: _brand_logo,
		sort: _sort,
		brand_id: _brand_id,
		is_top: _is_top,
		access_token: localStorage.access_token,
		status: 1
	}
	return data;
}
//检查添加
function checkInputAdd(data) {
	var result = {
		have_errors: false,
		error_info: ""
	}
	if(data.brand_name == '') {
		result.error_info = '请填写品牌名称';
		result.have_errors = true;
	} else if($('.addform').find('.inputFileShow').val()==''){
		result.error_info = '品牌图片不能为空！';
		result.have_errors = true;
	} else if(getSortNum(data) > 0) {
		result.error_info = '排序值重复！';
		result.have_errors = true;
	}
	return result;
}
//检查修改
function checkInputEdit(data) {
	var result = {
		have_errors: false,
		error_info: ""
	}
	if(data.brand_name == '') {
		result.error_info = '请填写品牌名称';
		result.have_errors = true;
	}else if($('.editform').find('.inputFileShow').val()==''){
		result.error_info = '品牌图片不能为空！';
		result.have_errors = true;
	} else if(getSortNum(data) > 0) {
		result.error_info = '排序值重复！';
		result.have_errors = true;
	}
	return result;
}

//检查修改sort 是否重复,0不重复，1重复
function getSortNum(data) {
	var totalnum;
	if(data.is_top != 1) {
		return false;
	}
	var result = {
		have_errors: false,
		error_info: ""
	}

	var dataquery = {
		sort: 0,
		is_top: 1,
		brand_id: ""
	};
	dataquery.sort = data.sort;
	dataquery.brand_id = data.brand_id;

	$.ajax({
		async: false,
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: "/goodsbrand/queryexist",
		dataType: 'json',
		data: JSON.stringify(dataquery),
		success: function(responseText, textStatus, jqXHR) {
			if(responseText.error_no == '0') {
				totalnum = responseText.exist;
			} else {
				alert(responseText.error_info);
			}
		},
		error: function() {}
	})
	return totalnum;
}

//增加保存并启用
$('.addform').on('click', '.saveAct', function(data) {
	var _this = $(this);
	var data = getParam(".addform");
	data.status = '1';
	var check_result = checkInputAdd(data);
	if(check_result.have_errors) {
		alert(check_result.error_info);
	} else {
		if(_this.attr("status") != 1) {
			return;
		}
		_this.attr("status", 0);
		$.ajax({
			contentType: "application/json; charset=utf-8",
			type: 'POST',
			url: "/goodsbrand/save",
			dataType: 'json',
			data: JSON.stringify(data),
			success: function(responseText, textStatus, jqXHR) {
				_this.attr("status", 1);
				if(responseText.error_no == '0') {
					alert('操作成功！');
					loadpage();
				} else {
					alert(responseText.error_info);
				}
			},
			error: function() {
				_this.attr("status", 1);
			}
		})
	}
});
//增加保存并禁用
$('.addform').on('click', '.saveFor', function(data) {
	var _this = $(this);
	var data = getParam(".addform");
	data.status = '0';
	var check_result = checkInputAdd(data);
	if(check_result.have_errors) {
		alert(check_result.error_info);
	} else {
		if(_this.attr("status") != 1) {
			return;
		}
		_this.attr("status", 0);
		$.ajax({
			contentType: "application/json; charset=utf-8",
			type: 'POST',
			url: "/goodsbrand/save",
			dataType: 'json',
			data: JSON.stringify(data),
			success: function(responseText, textStatus, jqXHR) {
				_this.attr("status", 1);
				if(responseText.error_no == '0') {
					alert('操作成功！');
					loadpage();
				} else {
					alert(responseText.error_info);
				}
			},
			error: function() {
				_this.attr("status", 1);
			}
		})
	}
});

//编辑保存并启用
$('.editform').on('click', '.saveAct', function(data) {
	var data = getParam(".editform");
	data.status = '1';
	var check_result = checkInputEdit(data);
	if(check_result.have_errors) {
		alert(check_result.error_info);
	} else {
		$.ajax({
			contentType: "application/json; charset=utf-8",
			type: 'POST',
			url: "/goodsbrand/brand_update",
			dataType: 'json',
			data: JSON.stringify(data),
			success: function(responseText, textStatus, jqXHR) {
				if(responseText.error_no == '0') {
					alert('操作成功！');
					loadpage();
				} else {
					alert(responseText.error_info);
				}
			},
			error: function() {}
		})
	}
});
//编辑保存并禁用
$('.editform .saveFor').on('click', function(data) {
	var data = getParam(".editform");
	data.status = '0';
	var check_result = checkInputEdit(data);
	if(check_result.have_errors) {
		alert(check_result.error_info);
	} else {
		$.ajax({
			contentType: "application/json; charset=utf-8",
			type: 'POST',
			url: "/goodsbrand/brand_update",
			dataType: 'json',
			data: JSON.stringify(data),
			success: function(responseText, textStatus, jqXHR) {
				if(responseText.error_no == '0') {
					alert('操作成功！');
					loadpage();
				} else {
					alert(responseText.error_info);
				}
			},
			error: function() {}
		})
	}
});
//DOM渲染数据	
function loadList(){
	var _brand_name = $('.mainOperationInput #brand_name').val().trim();
	var _brand_id = $('.mainOperationInput #brand_id').val().trim();
	var _status = $('.mainOperationInput #status').val();
	var _create_user = $('.mainOperationInput #create_user').val();
	var _is_top = $('.mainOperationInput #is_top').val();
	var dataTimeFrom = $('#dateFrom');
	var dataTimeTo = $('#dateTo');
	var _page = $('#page_now').val();
	var _limit = $('#page_limit').val();
	var reg = /\d+/g;
	var a = "";
	var b = "";
	var c = "";
	var d = "";
	if(dataTimeFrom.val() != '') {
		a = dataTimeFrom.val().match(reg)[0] + dataTimeFrom.val().match(reg)[1] + dataTimeFrom.val().match(reg)[2]
	}
	if(dataTimeFrom.val() != '') {
		b = dataTimeFrom.val().match(reg)[3] + dataTimeFrom.val().match(reg)[4] + dataTimeFrom.val().match(reg)[5]
	}
	if(dataTimeTo.val() != '') {
		c = dataTimeTo.val().match(reg)[0] + dataTimeTo.val().match(reg)[1] + dataTimeTo.val().match(reg)[2]
	}
	if(dataTimeTo.val() != '') {
		d = dataTimeTo.val().match(reg)[3] + dataTimeTo.val().match(reg)[4] + dataTimeTo.val().match(reg)[5]
	}
	data = {
		page: _page,
		limit: _limit,
		brand_name: _brand_name,
		brand_id: _brand_id,
		status: _status,
		is_top: _is_top,
		create_user_name: _create_user,
		date_start: a + b,
		date_end: c + d
	}
	listsReady(data);
}
function listsReady(data) {
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: "/goodsbrand/queryblur",
		dataType: 'json',
		data: JSON.stringify(data),
		success: function(responseText, textStatus, jqXHR) {
			var dataHtml = '<tr><th><input id="checkAll" type="checkbox" name="checkbox1" value="checkbox"></th><th>代码</th><th>品牌</th><th>logo</th><th>商品数</th><th>排序</th><th>创建日期</th><th>创建者</th><th>最后操作日期</th><th>最后操作者</th><th>状态</th><th>操作</th></tr>';
			if(responseText.error_no == '0') {
				var resultList = responseText.result_list;
				$("#total_num").html(responseText.total_num);
				$(".resultNum > label").html(responseText.total_num);
				$(".page").html(pageHtml(data.page, data.limit, responseText.total_num));
				var count = 0;
				resultList.forEach(function(item) {
					count++;
					var status = "启用";
					var button = "禁用";
					if(item.status == '0') {
						status = "禁用";
						button = "启用";
					}
					var init_date_format = dateFormat(item.init_date, item.init_time);
					var update_date_format = dateFormat(item.update_date, item.update_time);

					dataHtml += '<tr class="tableEdit"><td><input id="" class="cklist" type="checkbox" name="checkbox1" value="' + item.brand_id + '" brand_logo="' + item.brand_logo + '"></td>' +
						'<td class="lists_name">' + item.brand_id + '</td>' +
						'<td class="lists_alias">' + item.brand_name + '</td>' +
						'<td class="lists_type"><img src=' + ossUrl + item.brand_logo + ' width="" height=""/></td>' +
						'<td >' + item.model_num + '</td>' +
						'<td class="lists_sort">' + item.sort + '</td>' +
						'<td>' + init_date_format + '</td>' +
						'<td>' + item.create_user_name + '</td>' +
						'<td>' + update_date_format + '</td>' +
						'<td>' + item.update_user_name + '</td>' +
						'<td class="listStatusAllow">' + status + '</td>' +
						'<td brand_id="' + item.brand_id + '" brand_name="' + item.brand_name + '" is_top="' + item.is_top + '" sort="' + item.sort + '" brand_logo="' + item.brand_logo + '"><input type="button" name=""  value="编 辑" class="tableBot3 dataEditBot" />&nbsp;' +
						'<input type="button" name="" value="' + button + '" class="tableBot4 allowUn" /></td></tr>';
				});
				//clear
				$('.clearInput').on('click', function() {
					showAllGoodsbrand();
					showAllOperator();
					$('.inputTex').val('');
					$('.dataType').find('option:first').prop('selected', true);
					$('.dataStatus').find('option:first').prop('selected', true);
					searchOnclick(0);
				})
			}
			$("#dataGrid").html(dataHtml);
		},
		error: function(xhr, type) {}
	});
};

//状态修改
$('.mainLists').on('click', '.allowUn', function() {
	var r = confirm('确认操作？');
	var that = $(this);
	if(r == true) {
		if(that.val() == '禁用') {
			status = '0';
		} else {
			status = '1';
		}
		var dataS = {
			access_token: localStorage.access_token,
			status: status,
			brand_id: that.parent().attr('brand_id')
		}
		$.ajax({
			contentType: "application/json; charset=utf-8",
			type: 'POST',
			url: "/goodsbrand/brand_update_status",
			dataType: 'json',
			data: JSON.stringify(dataS),
			success: function(responseText, textStatus, jqXHR) {
				if(responseText.error_no == '0') {
					if(status == 0) {
						that.val('启用');
						that.parent().siblings('.listStatusAllow').text('禁用')
					} else {
						that.val('禁用');
						that.parent().siblings('.listStatusAllow').text('启用')
					}
					loadList();
				} else {
					alert(responseText.error_info)
				}
			},
			error: function() {

			}
		})
	} else {

	}
	return false;
})

function imgChangeAdd(e) {
    var reader = new FileReader();
    reader.onload = (function (file) {
    	return function (e) {
        	var img = new Image();
			img.src=this.result;
			img.onload=function(){
				var base64 = compressImg(img, 0.8, "image/jpeg");
				$('.addform').find(".brand_logo").val(base64);
			}
    	}
    })(e.target.files[0]);
    if(e.target.files[0]!=null){
    	reader.readAsDataURL(e.target.files[0]);
    }
}

function imgChangeEdit(e) {
    var reader = new FileReader();
    reader.onload = (function (file) {
    	return function (e) {
    	var img = new Image();
		img.src=this.result;
		img.onload=function(){
			var base64 = compressImg(img, 0.8, "image/jpeg");
			 $('.editform').find(".brand_logo").val(base64);
		}
    	}
    })(e.target.files[0]);
    if(e.target.files[0]!=null){
    	reader.readAsDataURL(e.target.files[0]);
    }
    
}


//刷新局部数据
function loadpage(){
	$('.shade').hide();
	$('.shadeCons').hide();
	$('.shadeCon').hide();
	loadList();	
}
//初始化表单
function initform(formclass){
	$(formclass).find(".brand_name").val("");
	$(formclass).find(".brand_logo").val("");
	$(formclass).find(".inputFileShow").val("");
	$(formclass).find(".inputFile1").val("");
	$(formclass).find(".brand_id").val("");
	var ckbox = $(formclass).find(".sortckbox");
	ckbox.prop("checked", false);
	$(formclass).find(".sort").val("");
}
function searchOnclick(flag) {
	var data = { page: 1, limit: 15 };
	if(flag == 1) {
		data.page = $('#page_now').val();
	}
	data.limit = $('#page_limit').val();
	//listsReady(data)
	loadList();
}