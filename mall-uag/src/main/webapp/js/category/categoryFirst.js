document.write("<script language='javascript' src='/js/htmlOss.js'></script>");
document.write("<script language='javascript' src='/js/cookies.js'></script>");
document.write("<script language='javascript' src='/js/hjyCmpress.js'></script>");
// 方法定义
var firstNav = {}
// 查询
firstNav.getData = function(paramsJson) {
	var json = {};
	$.ajax({
		contentType: 'application/json; charset=utf-8',
		type: 'POST',
		url: '/json/900507',
		dataType: 'json',
		data: JSON.stringify(paramsJson),
		async: false,
		success: function(responseText, textStatus, jqXHR) {
			if(responseText.error_no == '0') {
				json = responseText;
			} else {
				if(responseText.error_on) {
					alert(responseText.error_on + responseText.error_info);
				} else {
					if(responseText.error_on) {
						alert(responseText.error_on + responseText.error_info);
					} else {
						alert(responseText.error_info);
					}
				}
			}
		},
		error: function() {
			console.log('error')
		}
	})
	return json;
}
// 绘制元素
firstNav.drawData = function(jsonData) {
	$('.drawData').remove();
	var str = '';
	json = jsonData.result_list;
	for(var i = 0; json.length != null && i < json.length; i++) {
		str += "<tr class='drawData tableEdit'><td> <input type='checkbox' class='cklist' id='firstNavigation" +
			i +
			"'name='subBox' value='" +
			json[i].category_id +
			"'> </td> <td> " +
			(i + 1) +
			" </td> <td> " +
			json[i].category_id +
			" </td> <td class='categoryFirst'> " +
			json[i].category_name +
			" </td> <td > " +
			json[i].nick_name +
			" </td> <td class='categorySrcFirst'><img class='logoImg' src='" +
			ossUrl +
			json[i].icon +
			"'></td> <td class='categorySort'> " +
			json[i].sort +
			" </td> <td> " +
			firstNav.formatDate(json[i].init_date, json[i].init_time) +
			" </td> <td> " +
			json[i].creater +
			" </td> <td>" +
			firstNav.formatDate(json[i].update_date, json[i].update_time) +
			"</td> <td>" +
			json[i].updater +
			" </td> <td class='categoryState'> " +
			firstNav.formatStatus(json[i].status) +
			" </td> <td> <input type='button' name='' id='firstNavigationEdit" +
			(i + 1) +
			"' value='编 辑' class='tableBot3 categoryEdit' info='" + escape(JSON.stringify(json[i])) + "'/> <input type='button' name='' id='" +
			json[i].category_id +"' value='" +
			firstNav.editFormatStatus(json[i].status) +"' class='tableBot4 categoryStart'/> </td> </tr>"
	}
	$('#firstNavigation').append(str);
	$('label#fisrst_nav_total').text(jsonData.total_num);
	this.pageHtml(jsonData.total_num)
}
// 时间格式化
firstNav.formatDate = function(date, time) {
	if(date.length != 8) {
		return ""
	}
	if(time.length != 6) {
		return ""
	}
	return result = date.substring(0, 4) + '-' + date.substring(4, 6) + '-' +
		date.substring(6, 8) + ' ' + time.substring(0, 2) + ':' +
		time.substring(2, 4) + ':' + time.substring(4, 6);
}
// 时间去格式化
firstNav.delDateFormat = function(date) {
	return date.replace(/[^0-9]/ig, "")
}
// 状态格式化
firstNav.editFormatStatus = function(str) {
	if(str == null || str.trim() == "") {
		return ""
	}
	return str == "1" ? "禁用" : str == "0" ? "启用" : "";
}
// 编辑状态格式化
firstNav.formatStatus = function(str) {
	if(str == null || str.trim() == "") {
		return ""
	}
	return str == "0" ? "禁用" : str == "1" ? "启用" : "";
}
// 行元素编辑
$(".mainLists").on('click','.categoryEdit',function(){
	var dd = $(this).attr("info");
	var obj = eval('(' + unescape(dd) + ')');
	$('.shade').show();
	$('.shadeCons').show();
	$("#edit_first_name").val(obj.category_name);
	$("#edit_first_alias").val(obj.nick_name);
	$("#first_nav_edit_display").val(obj.icon);
	$('#edit_first_icon_data').val(obj.icon);
	$('#edit_first_icon').val("");
	$('#first_nav_edit_display').attr('metaSrc', obj.icon);
	$('#edit_submit').attr('edit_id', obj.category_id);
	if(obj.sort > 0) {
		$("#edit_first_nav_sort").click();
		$("#edit_first_nav_sort_data").val(obj.sort);
	}
	return false;
})
//firstNav.edit = function(item) {
//	
//}
$('#edit_submit').on('click', function() {
	var thisMin = $('#edit_first_alias').val().trim().length;
	if(thisMin == 1) {
		alert('别名：2-8个字符限制！');
		return
	}
	var id = $('#edit_submit').attr('edit_id');
	var display = $('#first_nav_edit_display').val();
	var icon = $("#edit_first_icon_data").val();
	if(display.trim() == '') {
		alert("请选择图片文件");
		return false;
	}
	var categoryName = $('#edit_first_name').val();
	var nickName = $('#edit_first_alias').val();
	var sort = $("#edit_first_nav_sort_data").val();
	var json = {
		access_token: localStorage.access_token,
		level: "1",
		categoryName: categoryName,
		nickName: nickName,
		icon: icon,
		sort: sort,
		categoryId: id,
		level: '1'
	};
	var edit_first_nav_sort = $("#edit_first_nav_sort").is(':checked');
	if(edit_first_nav_sort && (sort == null || sort.trim().length < 1)) {
		alert('请填写APP排序数字');
		return;
	}
	if(null == categoryName || categoryName.trim().length < 1) {
		alert('请填写类目信息');
		return;
	} else {
		json.categoryName = categoryName.trim();
	}
	if(null == nickName || nickName.trim().length < 1) {
		delete json.nickName;
	}
	if(!edit_first_nav_sort) {
		json.sort = -1;
	}
	if(edit_first_nav_sort && sort < 1) {
		alert('app排序请输入正整数');
		return;
	}
	var result = $
		.ajax({
			contentType: 'application/json; charset=utf-8',
			type: 'POST',
			url: '/json/900506',
			dataType: 'json',
			data: JSON.stringify(json),
			async: false,
			success: function(responseText,
				textStatus, jqXHR) {
				if(responseText.error_no == '0') {
					alert('修改成功');
					$('.inviteCancle').click();
					firstNav.search(1);
				} else {
					if(responseText.error_on) {
						alert(responseText.error_on +
							responseText.error_info);
					} else {
						alert(responseText.error_info);
					}
				}
			},
			error: function() {
				console.log('error')
			}
		})
});
$('.inviteCancle').on('click', function() {
	$(".sortckbox").removeAttr('checked');
	$('.shade').hide();
	$('.shadeCons').hide();
	var categoryName = $("#edit_first_name").val("");
	var nickName = $("#edit_first_alias").val("");
	var icon = $("#edit_first_icon_data").val("");
	var sort = $("#edit_first_nav_sort_data").val("");
	$('#first_nav_edit_display').val("");
})
// 行元素修改状态
$('.mainLists').on('click', '.categoryStart', function(){
	var r = confirm('确认操作？');
	var category_id=$(this).attr('id');
	if(r == true) {
		var that = $(this);
		var val = $(this).val();
		var json = {
			access_token: localStorage.access_token,
			categoryId: category_id
		};
		json.status = val == "禁用" ? "0" : "1";
		var result = $.ajax({
			contentType: 'application/json; charset=utf-8',
			type: 'POST',
			url: '/json/900517',
			dataType: 'json',
			data: JSON.stringify(json),
			async: false,
			success: function(responseText, textStatus, jqXHR) {
				if(responseText.error_no == '0') {

				} else {
					alert(responseText.error_info);
				}
			},
			error: function() {
				console.log('error')
			}
		})
		firstNav.search(1);
	} else {
		return false;
	}
});
// 添加类目
firstNav.createNavition = function() {
	var icon = $("#add_first_icon_data").val();
	if(!icon || icon.trim().length < 1) {
		alert("请选择图片文件");
		return false;
	}
	var add_first_nav_sort = $("#add_first_nav_sort").is(':checked');
	var sort = $("#add_first_nav_sort_data").val();
	if(add_first_nav_sort && (sort == null || sort.trim().length < 1)) {
		alert('请填写排序信息');
		return;
	}
	var json = {
		access_token: localStorage.access_token,
		level: "1",
		categoryName: $("#add_first_name").val(),
		icon: icon,
		sort: -1
	};
	if(add_first_nav_sort && sort < 1) {
		alert('app排序请输入正整数');
		return;
	}
	if(!add_first_nav_sort) {
		json.sort = -1;
	} else {
		json.sort = sort
	}
	var nickName = $("#add_first_alias").val();
	if(nickName != null && nickName.trim().length > 0) {
		json.nickName = nickName;
	}
	var result = $.ajax({
		contentType: 'application/json; charset=utf-8',
		type: 'POST',
		url: '/json/900505',
		dataType: 'json',
		data: JSON.stringify(json),
		async: false,
		success: function(responseText, textStatus, jqXHR) {
			if(responseText.error_no == '0') {
				localStorage.access_token = responseText.access_token;
				delCookie('__access_token')
				setCookie('__access_token', responseText.access_token)
				$('#first_nav_add_cancel').click();
				alert('添加成功');
			} else {
				if(responseText.error_on) {
					alert(responseText.error_on + responseText.error_info);
				} else {
					alert(responseText.error_info);
				}
			}
		},
		error: function() {
			console.log('网络错误');
		}
	})
	var str = JSON.parse(result.responseText);
	if(str.error_no == 0) {
		this.search(0);
		return;
	}
}
// 转换图片内容
firstNav.getFileData = function(target) {
	var file = target.files[0];
	if(!/image\/\w+/.test(file.type)) {
		alert("请选择图片文件");
		return false;
	}
	var reader = new FileReader();
	reader.readAsDataURL(file);
	var image = new Image(),
		canvas = document.createElement("canvas"),
		ctx = canvas.getContext('2d');
	reader.onload = function(e) {
		//$('input#' + target.id + "_data").val(this.result);
		var url = reader.result; //读取到的文件内容.这个属性只在读取操作完成之后才有效,并且数据的格式取决于读取操作是由哪个方法发起的.所以必须使用reader.onload，     
		image.src = url; //reader读取的文件内容是base64,利用这个url就能实现上传前预览图片
		reader.onload = null;
	}
	image.onload = function() {
		var w = image.naturalWidth,
			h = image.naturalHeight;
		var dic = h / w;
		canvas.width = w > 600 ? 600 : w;
		canvas.height = dic * canvas.width;
		ctx.drawImage(image, 0, 0, canvas.width, canvas.height);
		data = canvas.toDataURL("image/gif", 0.8);
		$('input#' + target.id + "_data").val(data);
		image.onload = null;
	};
}
// 搜索
firstNav.search = function(flag) {
	var categoryName = $("#fisrst_nav_name").val();
	var status = $("#fisrst_nav_status").val();
	var creater = $("#first_nav_creater").val();
	var appDisplay = $("#first_nav_app_display").val();
	var startTime = this.delDateFormat($("#dateFrom").val());
	var endTime = this.delDateFormat($("#dateTo").val());
	var limit = $("#page_limit").val();

	if(flag == 1) {
		var page = $("#page_now").val();
		page = page - 1;
		page = page * limit;

		var jsonParams = {
			page: page,
			limit: limit
		}
	} else if(flag == 0) {
		var page = 0;
		$("#page_now").val(1);

		var jsonParams = {
			page: page,
			limit: limit
		};
	}
	if(categoryName && categoryName.trim().length > 0) {
		jsonParams.categoryName = categoryName;
	}
	if(status && status.trim().length > 0) {
		jsonParams.status = status;
	}
	if(creater && creater.trim().length > 0) {
		jsonParams.creater = creater;
	}
	if(appDisplay && appDisplay.trim().length > 0) {
		jsonParams.appDisplay = appDisplay;
	}
	if(startTime && startTime.trim().length > 0) {
		jsonParams.startTime = startTime;
	}
	if(endTime && endTime.trim().length > 0) {
		jsonParams.endTime = endTime;
	}
	this.drawData(this.getData(jsonParams));
}
// 顶部搜索
firstNav.topSearch = function() {
	var categoryName = $("#fisrst_nav_name").val();
	var status = $("#fisrst_nav_status").val();
	var creater = $("#first_nav_creater").val();
	var appDisplay = $("#first_nav_app_display").val();
	var startTime = this.delDateFormat($("#dateFrom").val());
	var endTime = this.delDateFormat($("#dateTo").val());
	var page = 0;
	$("#page_now").val(1);
	var limit = $("#page_limit").val();

	var jsonParams = {
		page: page,
		limit: limit
	};
	if(categoryName && categoryName.trim().length > 0) {
		jsonParams.categoryName = categoryName;
	}
	if(status && status.trim().length > 0) {
		jsonParams.status = status;
	}
	if(creater && creater.trim().length > 0) {
		jsonParams.creater = creater;
	}
	if(appDisplay && appDisplay.trim().length > 0) {
		jsonParams.appDisplay = appDisplay;
	}
	if(startTime && startTime.trim().length > 0) {
		jsonParams.startTime = startTime;
	}
	if(endTime && endTime.trim().length > 0) {
		jsonParams.endTime = endTime;
	}
	this.drawData(this.getData(jsonParams));

}
// 清空
firstNav.cancel = function() {
	$("#fisrst_nav_name").val("");
	$("#fisrst_nav_status").val("");
	$("#first_nav_creater").val("");
	$("#first_nav_app_display").val("");
	$("#dateFrom").val("");
	$("#dateTo").val("");
	firstNav.search(0);
}

function pageJump(topage) {
	$('#page_now').val(topage);
	$('.mainLists').animate({ scrollTop: 0 }, 300);
	firstNav.search(1);
}
// 跳转到topage 页
function reSetLimit(limit) {
	$('#page_now').val(1);
	$('#page_limit').val(limit);
	$('.mainLists').animate({ scrollTop: 0 }, 300);
	firstNav.search(0);
}
// 处理 跳转页面 输入狂 回车事件
function keyDown(e, _this) {
	var _this = $(_this);
	var val = parseInt($(_this).val());
	var ev = window.event || e;
	// 13是键盘上面固定的回车键
	if(ev.keyCode == 13) {
		// 你要执行的方法
		pageJump(val);
	}
}
// 通过 当前页（page_now），每页条数（page_size），总条数（data_size），返回分页代码
firstNav.pageHtml = function(data_size) {
	var page_now = this.delDateFormat($("#page_now").val());
	var page_size = this.delDateFormat($("#page_limit").val());
	var page_total = data_size / page_size == Math.floor(data_size / page_size) ? Math
		.floor(data_size / page_size) :
		Math.floor(data_size / page_size) + 1;
	var str = '<div class="pageList">';
	if(page_now != 1) {
		str += '<span onclick=javascript:pageJump(' + (Number(page_now) - 1) +
			')>&lt;</span>';
	} else {
		str += '<span>&lt;</span>';
	}

	var start = page_now - 4 < 1 ? 1 : page_now - 4;
	var end = page_now + 4 > page_total ? page_total : page_now + 4;
	for(var i = start; i <= end; i++) {
		if(page_now == i) {
			str += '<span class="pageListOn">' + i + '</span>';
		} else {
			str += '<span onclick="pageJump(' + i + ')">' + i +
				'</span>';
		}

	}
	if(page_now != page_total) {
		str += '<span onclick=javascript:pageJump(' + (Number(page_now) + 1) +
			')>></span>';
	} else {
		str += '<span>></span>';
	}
	var page_limit = Number($('#page_limit').val());

	str += '<select name="" class="pageSelect" onchange="javascript:reSetLimit(this.options[this.options.selectedIndex].value)">';
	if(page_limit == 15) {
		str += '<option value="15" selected>15条/页</option>';
	} else {
		str += '<option value="15">15条/页</option>';
	}
	if(page_limit == 20) {
		str += '<option value="20" selected>20条/页</option>';
	} else {
		str += '<option value="20">20条/页</option>';
	}
	if(page_limit == 30) {
		str += '<option value="30" selected>30条/页</option>';
	} else {
		str += '<option value="30">30条/页</option>';
	}

	str += '</select>';
	str += '<label class="pageGo">';
	str += '跳至<input type="text" value="" name="keyDown" onkeydown="keyDown(event,this)" />页';
	str += '</label>';
	str += '</div>';
	$(".page").html(str);
}
//获取所有的后台操作人
function getAllOperator() {
	var data = {};
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: "/getAllOperators",
		dataType: 'json',
		data: JSON.stringify(data),
		success: function(responseText, textStatus, jqXHR) {
			if(responseText.error_no == '0') {
				var resultList = responseText.result_list;
				var operatorHtml = '';
				resultList.forEach(function(item) {
					operatorHtml += '<div class="pattern_item" id="' + item.operator_id + '">' + item.operator_name + '</div>';
				});
				$("#pattern_name").html(operatorHtml);
			} else {
				alert(responseText.error_no);
			}
		},
		error: function(xhr, type) {

		}
	});
}
// 页面加载
$(document).ready(function() {
	firstNav.search(0);
	getAllOperator();
});
// --------事件处理------------------------
// 清空事件
$("#fisrst_nav_cancel").bind("click", function() {
	firstNav.cancel();
});
// 搜索事件
$("#fisrst_nav_search").bind("click", function() {
	firstNav.search(0);
	//	firstNav.topSearch();
});

$('#fisrst_nav_add').on('click', function() {
	$('.shade').show();
	$('.shadeCon').show();
})
$('#first_nav_add_cancel').on('click', function() {
	$('.shade').hide();
	$('.shadeCon').hide();
	var categoryName = $("#add_first_name").val("");
	var nickName = $("#add_first_alias").val("");
	var icon = $("#add_first_icon").val("");
	var icon = $("#add_first_icon_data").val("");
	var sort = $("#add_first_nav_sort_data").val("");
	$("input#first_nav_display").val("");
})
$('#first_nav_save').on('click', function() {
	var thisMin = $('#add_first_alias').val().trim().length;
	if(thisMin == 1) {
		alert('别名：2-8个字符限制！');
		return
	}
	firstNav.createNavition();
});
// 批量启用事件
$("#fisrst_nav_batch_enable").on("click", function() {
	var _ids = [];
	var idchecklist = $("#firstNavigation .cklist:checked");
	if(idchecklist.length == 0) {
		alert('你没有选择任何数据');
	} else {
		for(var i = 0; i < idchecklist.length; i++) {
			var obj = $(idchecklist[i]);
			_ids[i] = obj.val();
		}
		var data = {
			categoryIds: _ids,
			access_token: localStorage.access_token,
			status: 1
		}
		if(false) {
			alert('请填写信息');
		} else {
			if(idchecklist.length > 1) {
				rightTrue = confirm('是否批量启用？');
				testV = '批量启用成功！'
			} else {
				rightTrue = confirm('是否启用？');
				testV = '启用成功！'
			}
			if(rightTrue == true) {
				$.ajax({
					contentType: "application/json; charset=utf-8",
					type: 'POST',
					url: '/json/900513',
					dataType: 'json',
					data: JSON.stringify(data),
					success: function(responseText, textStatus, jqXHR) {
						alert(testV);
						firstNav.search(1);
						$("#checkAll").attr("checked", false);
					},
					error: function() {}
				})
			} else {}
		}
	}
});
// 批量禁用事件
$("#fisrst_nav_batch_disable").on("click", function() {
	var _idsd = [];
	var idchecklistd = $("#firstNavigation .cklist:checked");
	if(idchecklistd.length == 0) {
		alert('你没有选择任何数据');
	} else {
		for(var i = 0; i < idchecklistd.length; i++) {
			var objd = $(idchecklistd[i]);
			_idsd[i] = objd.val();
		}
		var data = {
			categoryIds: _idsd,
			access_token: localStorage.access_token,
			status: 0
		}
		if(false) {
			alert('请填写信息');
		} else {
			if(idchecklistd.length > 1) {
				rightTrues = confirm('是否批量禁用？');
				testV = '批量禁用成功！'
			} else {
				rightTrues = confirm('是否禁用？');
				testV = '禁用成功！'
			}
			if(rightTrues == true) {
				$.ajax({
					contentType: "application/json; charset=utf-8",
					type: 'POST',
					url: '/json/900513',
					dataType: 'json',
					data: JSON.stringify(data),
					success: function(responseText, textStatus, jqXHR) {
						if(responseText.error_no == '0') {
							alert('批量禁用成功！');
							firstNav.search(0);
						} else {
							if(responseText.error_on) {
								alert(responseText.error_on +
									responseText.error_info);
							} else {
								alert(responseText.error_info);
							}
						}
						$("#checkAll").attr("checked", false);
					},
					error: function() {}
				})
			} else {}
		}
	}
});
//全选事件绑定
$("#firstNavigation").on('click', '#checkAll', function() {
	if($(this).is(':checked')) {
		$("#firstNavigation .cklist").prop("checked", true);
		$('.tableEdit').addClass('trActive')
	} else {
		$("#firstNavigation .cklist").prop("checked", false);
		$('.tableEdit').removeClass('trActive')
	}
});
// 添加图片
$("#add_first_icon").on("change", function() {
	var thisV = $(this).val();
	if(thisV != '') {
		$('#first_nav_display').val(thisV)
		firstNav.getFileData(this);
	}
});
// 编辑图片
$("#edit_first_icon").on("change", function() {
	var thisV = $(this).val();
	if(thisV != '') {
		$('#first_nav_edit_display').val(thisV)
		firstNav.getFileData(this);
	}
});

$(".sortckbox").on('click', function() {
	if($(this).is(':checked')) {
		$(this).next().show();
		$(this).next().next().show();
	} else {
		$(this).next().next().hide();
		$(this).next().hide();
	}
})
$('#add_upload_img').on('click', function() {
	document.getElementById('add_first_icon').click();
})
$('#edit_upload_img').on('click', function() {
	document.getElementById('edit_first_icon').click();
});
//复选框选中
$('.mainLists').on('click', '.tableEdit', function() {
	var isCheck = $(this).find('input[type="checkbox"]').is(':checked');
	if(isCheck) {
		$(this).removeClass('trActive');
		$(this).find('input[type="checkbox"]').prop('checked', false);
	} else {
		$(this).addClass('trActive');
		$(this).find('input[type="checkbox"]').prop('checked', true);
	}
})
//没有复选框选中状态
$('.mainLists').on('click', '.tableEditNone', function() {
	$(this).addClass('trActive').siblings().removeClass('trActive');
})
//获取所有的后台操作人
function getAllOperator() {
	var data = {};
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: "/getAllOperators",
		dataType: 'json',
		data: JSON.stringify(data),
		success: function(responseText, textStatus, jqXHR) {
			if(responseText.error_no == '0') {
				var resultList = responseText.result_list;
				var operatorHtml = '';
				resultList.forEach(function(item) {
					operatorHtml += '<div class="pattern_item" id="' + item.operator_id + '">' + item.operator_name + '</div>';
				});
				$("#pattern_name").html(operatorHtml);
			} else {
				alert(responseText.error_no);
			}
		},
		error: function(xhr, type) {

		}
	});
}
//项点击  
$("#pattern_name").on('click', '.pattern_item', function() {
	$(".create_user_name").val($(this).text());
});
//点击清除时调用
function showAllOperator() {
	$("#pattern_name .pattern_item").css('display', 'block');
}

//弹出列表框  
$(".create_user_name").click(function() {
	var input = $(this);
	var offset = input.offset();
	$("#pattern_name").css('display', 'block');
	$('#pattern_name').css('left', offset.left + 'px').css('top', offset.top + input.height() + 'px').fadeIn().css('position', "fixed");
	return false;
});
//隐藏列表框  
$("body").click(function() {
	$("#pattern_name").css('display', 'none');
});
//移入移出效果  
$(".pattern_item").hover(function() {
	$(this).css('background-color', '#1C86EE').css('color', 'white');
}, function() {
	$(this).css('background-color', 'white').css('color', 'black');
});

//文本框输入  
$(".create_user_name").keyup(function() {
	$("#pattern_name").css('display', 'block'); //只要输入就显示列表框  

	if($(".create_user_name").val().trim().length <= 0) {
		$("#pattern_name .pattern_item").css('display', 'block'); //如果什么都没填，跳出，保持全部显示状态  
		return;
	}

	$("#pattern_name .pattern_item").css('display', 'none'); //如果填了，先将所有的选项隐藏  

	for(var i = 0; i < $("#pattern_name .pattern_item").length; i++) {
		//模糊匹配，将所有匹配项显示  
		if($("#pattern_name .pattern_item").eq(i).text().indexOf($(".create_user_name").val().trim()) > -1) {
			$("#pattern_name .pattern_item").eq(i).css('display', 'block');
		}
	}
});

$('.mainLists').on('click', '.cklist', function() {
	var that = $(this);
	var isC = that.is(':checked');
	if(isC) {
		that.prop('checked', false)
	} else {
		that.prop('checked', true)
	}
})