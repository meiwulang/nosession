var category_temp = {};
var category_persist = {};
var model_temp = new Array();
var model_persist = new Array();
var model_obj_temp = new Array();
var model_obj_persist = new Array();

$(document).ready(function() {
	$('.addGoods').on('click', function() {
		$('.addGoods').attr('disabled', true);
		$('.adUnderdGoods').attr('disabled', true);
		var data = getAddParam();
		if(data) {
			addGoods(data, "/addGoods");
		} else {
			$('.addGoods').attr('disabled', false)
			$('.adUnderdGoods').attr('disabled', false);
		}
	});
	$('.viewFooterRight').on('click', function() {
		if($('.viewFooterNav').is(':hidden')) {
			$('.viewFooterNav').show();
		} else {
			$('.viewFooterNav').hide();
		}
	})
	//预览
	$('.addView').on('click', function() {
		$('.viewBg').css('visibility', 'visible');
		var fistSrc = $('.banner_list').find('.forupload').eq(0).attr('src');
		var secondSrc = $('.banner_list').find('.forupload').eq(1).attr('src');
		var thirdSrc = $('.banner_list').find('.forupload').eq(2).attr('src');
		var fourthSrc = $('.banner_list').find('.forupload').eq(3).attr('src');
		var fifthSrc = $('.banner_list').find('.forupload').eq(4).attr('src');
		var sixSrc = $('.banner_list').find('.forupload').eq(5).attr('src');
		$('.firstBanner').attr('src', fistSrc);
		$('.secondBanner').attr('src', secondSrc);
		$('.thirdBanner').attr('src', thirdSrc);
		$('.fourthBanner').attr('src', fourthSrc);
		$('.fifthBanner').attr('src', fifthSrc);
		$('.sixBanner').attr('src', sixSrc);
		$('.viewAdver img').attr('src', $('#ad_url').val());
		$('.viewCotent h1').text($('.carParameter').find('input').val());
		$('.viewBrand span').text($('.addBrand').val());
		$('.viewListsDd').html();
		var infoListHtml = $('.info_list');
		var addS = '';
		for(var i = 0; i < infoListHtml.length; i++) {
			var info_title = $('.info_list .leftParameter input[type="text"]').get(i);
			var info_content = $('.info_list .rightParameter textarea').get(i);
			var obj = {
				info_title: info_title.value,
				info_content: info_content.value.replace(/\n|\r\n/g, "<br>").replace(/\s/g, "&nbsp;")
			};
			addS += '<dd><label>' + obj.info_title + '</label><span>' + obj.info_content + '</span></dd>'
		}
		var chooseView = $('.cateViewNext dd');
		var chooseViewValue = '';
		for(var i = 0; i < chooseView.length; i++) {
			var thisValue = chooseView.eq(i).text();
			var thisbrandName = chooseView.eq(i).attr("brand_name");
			var thismetadataName = chooseView.eq(i).attr("metadata_name");
			chooseViewValue += '<li>' + thisbrandName + ' ' + thismetadataName + ' ' + thisValue + '</li>'
		}
		$('.detailsFit').html(chooseViewValue);
		$('.viewListsDd').html(addS);
		var detailHtml = $(".detail_list div");
		var detailList = new Array();
		var viewDetails = '';
		for(var i = 0; i < detailHtml.length; i++) {
			var obj = {};
			if($(".detail_list div:eq(" + i + ") img").parent().prop("className") != "addIco") {
				obj = {
					pic_url: $(".detail_list div:eq(" + i + ")").prev().attr('base64'),
					pic_desc: $('.imgdetails').get(i).value.replace(/\n|\r\n/g, "<br>").replace(/\s/g, "&nbsp;")
				};
				viewDetails += '<li><img src="' + obj.pic_url + '" /><h5>' + obj.pic_desc + '</h5></li>'
			}
		}
		$('.detailsCon').html(viewDetails);
		$('.viewFooterPend h4').html('请选择你需要的商品数量(单位：' + $('.addUnit').val() + ')<span>关闭</span>');
		var standHtml = $('.stand_list');
		var standardList = new Array();
		var viewStand = '';
		for(var i = 0; i < standHtml.length; i++) {
			var index = i * 5;
			var standard_must = $('.stand_list input[type="text"]').get(index);
			var optional_first = $('.stand_list input[type="text"]').get(index + 1);
			var optional_second = $('.stand_list input[type="text"]').get(index + 2);
			var price = $('.stand_list input[type="text"]').get(index + 3);
			var store_num = $('.stand_list input[type="text"]').get(index + 4);
			var obj = {
				standard_must: standard_must.value,
				optional_first: optional_first.value,
				optional_second: optional_second.value,
				price: price.value,
				store_num: store_num.value
			};
			viewStand += '<li><dl><dt>' + obj.standard_must + '<span>' + obj.optional_first + '</span><span>' + obj.optional_second + '</span></dt><dd><span>单价&nbsp;&nbsp;&nbsp;￥' + obj.price + '</span><span style="margin-left:100px">数量：' + obj.store_num + '</span></dd><dl><div class="viewBgColor"></div></li>'
		}
		$('.viewStand').html(viewStand)
		$('.closeView').on('click', function() {
			$('.viewBg').css('visibility', 'hidden');
		})
	})
	$('.adUnderdGoods').on('click', function() {
		$('.addGoods').attr('disabled', true);
		$('.adUnderdGoods').attr('disabled', true);
		var data = getAddParam();
		if(data) {
			addGoods(data, "/adUnderdGoods");
		} else {
			$('.addGoods').attr('disabled', false);
			$('.adUnderdGoods').attr('disabled', false);
		}
	});

	// 删除占位图的按钮事件
	$('#del_ad_url').on('click', function() {
		$('#ad_url').val(''); // base64清空,隐藏域
		$('#ad_url_file').val(''); // base64清空,实际上传的表单
		$('.ad_url_show').val(''); // 展示假地址的文本框
	});

	$('.addStandard').on('click', function() {
		var carParameter2 = $('<div class="carParameter stand_list">' +
			'<label class="label1"><em>*</em> 名称：</label>' +
			'<input type="text" name="" id="" value="" placeholder="这个规格是必填的" class="inputText1 floatL marT1 marR1" />' +
			'<input type="text" name="" id="" value="" placeholder="三个规格40字数限制" class="inputText1 floatL marT1 marR1" />' +
			'<input type="text" name="" id="" value="" placeholder="三个规格40字数限制" class="inputText1 floatL marT1 marR1" />' +
			'<label class="label1"><em>*</em>价格：</label> ' +
			'<input type="text" name="" id="" value="" title="输入（百万及以下的价格，小数点后保留两位）" placeholder="输入（1-10个字符）" class="amount inputText1 floatL marT1 marR1" />' +
			'<label class="label1"><em>*</em> 库存：</label> ' +
			'<input type="text" maxLength="8" minLength="1"  name="" id="" value=""  placeholder="输入（1-8字数限制）" class="inputText1 floatL marT1 marR1 storeNum" />' +
			'<input type="button" name="" id="" value="删除" class="inputBot3 carParameterD " /></div>');
		$(this).before(carParameter2);
	});

	$('.mainLists').on('change', '.amount', function(event) {
		var $amountInput = $(this);
		var value=parseInt($(this).val());
		//响应鼠标事件，允许左右方向键移动 
		event = window.event || event;
		if(event.keyCode == 37 | event.keyCode == 39) {
			return;
		}
		//先把非数字的都替换掉，除了数字和. 
		$amountInput.val($amountInput.val().replace(/[^\d.]/g, "").
			//只允许一个小数点              
			replace(/^\./g, "").replace(/\.{2,}/g, ".").
			//只能输入小数点后两位
			replace(".", "$#$").replace(/\./g, "").replace("$#$", ".").replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'));
		var reg=/\d{8,}\.*/g;
		if(reg.test(value)){
			alert("价格：1-10位字符限制！");
			$(this).val("");
		}
	});
	$('.mainLists').on('change', '.storeNum', function(event) {
		var $amountInput = $(this);
		//响应鼠标事件，允许左右方向键移动 
		event = window.event || event;
		if(event.keyCode == 37 | event.keyCode == 39) {
			return;
		}
		//先把非数字的都替换掉，除了数字和. 
		$amountInput.val($amountInput.val().replace(/[^\d.]/g, ""));
	});
	$("#amount").on('blur', function() {
		var $amountInput = $(this);
		//最后一位是小数点的话，移除
		$amountInput.val(($amountInput.val().replace(/\.$/g, "")));
	});

	// 删除商品介绍
	$('.list').on('click', '.goodsParameter .rightParameter .inputBot3', function() {
		if($('.info_list').length > 1) {
			$(this).parent().parent().remove();
		}
	});

	// 删除商品规格
	$('.list').on('click', '.carParameterD', function() {
		$(this).parent().remove();
	});

	// 添加介绍
	$('.addParameter').on('click', function() {
		var carParameter1 = $('<div class="goodsParameter info_list"> <div class="leftParameter"> <label><em>*</em>  名称：</label> ' +
			'<input type="text" maxLength="5" minLength="1" name="" id="" value="" placeholder="输入（1-5字数限制）" class="inputText2 mar2"/> ' +
			'</div> <div class="rightParameter"> <label class="rightLabel">  值：</label> ' +
			'<textarea maxLength="200"	name="" id="" value="" placeholder="输入（1-200字数限制）" rows="4" ></textarea>' +
			'<input type="button" name="" id="" value="删除" class="inputBot3 mar2"/></div></div>');
		$(this).before(carParameter1);
	});

	// 单张图片的，展示假的地址
	$('.input_file_ad').change(function() {
		var thisV = $(this).val();
		if(thisV != '') { // 点击取消的时候，thisV的值为''
			$('.ad_url_show').val(thisV);
		}
	});

	$('.input_file_show').change(function() {
		var thisV = $(this).val();
		if(thisV != '') { // 点击取消的时候，thisV的值为''
			$('.show_url_show').val(thisV);
		}
	});
	// 单张图片的，展示假的地址

	$('.addBrand').on('click', function() {
		$('#select_brand_or_unit').empty();
		$('.add_brand_show3').show();
		$('.add_brand_show3').show();
		var data = {
			status: 1,
			limit: 1000000
		};
		$('#name_search').val(null);
		$("#gg_type1").html(null);
		listBrand(data);
		$('#name_search').attr("placeholder", "请输入品牌关键词,输入匹配");
		$('.brandOrUnit_text span').text('品牌')
	});

	$('.addUnit').on('click', function() {
		$('#select_brand_or_unit').empty();
		$('.add_brand_show3').show();
		$('.add_brand_show3').show();
		var data = {
			type: 0,
			status: 1,
			limit: 10000
		};
		$("#gg_type1").html(null);
		$('#name_search').val(null);
		listUnit(data);
		$('#name_search').attr("placeholder", "请输入计量单位关键词,输入匹配");
		$('.brandOrUnit_text span').text('计量单位');
	});

	// 保存 品牌或者计量单位
	$('.shadeCon .inviteKeep').on('click', function() {
		var numVal = $('.shadeCon .s-1-select li input[name="checkbox_1"]:checked').val();
		var numIndex = $('.shadeCon .s-1-select li input[name="checkbox_1"]:checked').parent().index();
		var numName = $('.shadeCon .s-1-select li input[name="checkbox_1"]:checked').parent().siblings().text();
		if(numVal == '' || numName == '') {
			alert('请填写信息');
		} else {
			var operatorClass = '';
			if($('.brandOrUnit_text span').text() == '品牌') {
				$('.addBrand').val(numName);
				$('.addBrand').attr("hiddelValue", numVal);
			} else if($('.brandOrUnit_text span').text() == '计量单位') {
				$('.addUnit').val(numName);
				$('.addUnit').attr("hiddelValue", numVal);
			}

			// 右边已选择添加一个
			var liItem = $('<li>' + numName + ' <img src="/images/cuohao.png"></li>');
			$(".selected1 li").remove();
			$(".selected1").append(liItem);
			$('.shade').hide();
			$('.shadeCon').hide();
		}
	});

	$('.inviteCancle').on('click', function() {
		$('.shade').hide();
		$('.shadeCon').hide();
	})

	$(".s-1-select").on('click', "li", function() {
		if($(this).hasClass("s-pu_active")) {
			$(this).removeClass("s-pu_active");
			$(this).find('input[name="checkbox_1"]').removeAttr("checked");
			$(".selected1 li").remove();
		} else {
			$(this).addClass('s-pu_active').siblings().removeClass('s-pu_active');
			$(this).find('input[name="checkbox_1"]').prop('checked', true);
			$(this).siblings().find('input[name="checkbox_1"]').removeAttr("checked");
			var thisV = $(this).find("span").text();
			var liItem = $('<li>' + thisV + '<img src="/images/cuohao.png"></li>');
			$(".selected1 li").remove();
			$(".selected1").append(liItem);
		}
	});
	$(".selected1").on('click', 'li', function() {
		$(this).remove();
		$(".s-pu_active").find('input[name="checkbox_1"]').removeAttr("checked");
		$(".s-pu_active").removeClass("s-pu_active");
	});

	$('#reloadBrand').on('click', function() {
		if($('.brandOrUnit_text span').text() == '品牌') {
			$(".selected1 li").remove();
			var data = {
				brand_name: $('#name_search').val(),
				status: 1,
				limit: 1000000
			};
			if(data.brand_name == null || data.brand_name.trim().length == 0) {
				data = {
					status: 1,
					limit: 1000000
				};
			}
			listBrand(data);
		} else {
			$(".selected1 li").remove();
			var data = {
				metadata_name: $('#name_search').val(),
				status: 1,
				limit: 1000000
			};
			if(data.metadata_name == null || data.metadata_name.trim().length == 0) {
				data = {
					status: 1,
					limit: 1000000
				};
			}
			listUnit(data);
		}
	});
});

function listBrand(data) {
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: "/goodsbrand/queryforweb",
		dataType: 'json',
		data: JSON.stringify(data),
		success: function(responseText, textStatus, jqXHR) {
			var dataHtml = '';
			var first = true;
			if(responseText.error_no == '0') {
				var resultList = responseText.result_list;
				resultList.forEach(function(item) {
					dataHtml += '<li><span class="cho-name graycol">';
					dataHtml += item.brand_name + '</span><div style="float: right"><input type="checkbox" name="checkbox_1" class="chk_2"   value="' +
						item.brand_id + '"><label class="label001 checkMT_o"></label></div></li>';
				});
			}
			$("#gg_type1").html(dataHtml);
			var brand_id = $('.addBrand').attr("hiddelValue");
			if(brand_id != null && brand_id.trim().length != 0) {
				$('.shadeCon .s-1-select li input[value="' + brand_id + '"]').attr("checked", true);
				$('.shadeCon .s-1-select li input[value="' + brand_id + '"]').parent().parent().addClass("s-pu_active");
				var liItem = $('<li>' + $('.addBrand').val() + ' <img src="/images/cuohao.png"></li>');
				$(".selected1 li").remove();
				$(".selected1").append(liItem);
			}
		},
		error: function(xhr, type) {
			alert("网络异常");
		}
	});
};

// 计量单位
function listUnit(data) {
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: "/queryMetadata",
		dataType: 'json',
		data: JSON.stringify(data),
		success: function(responseText, textStatus, jqXHR) {
			$(".selected1 li").remove();
			var dataHtml = '';
			var first = true;
			if(responseText.error_no == '0') {
				var resultList = responseText.result_list;
				resultList.forEach(function(item) {
					dataHtml += '<li><span class="cho-name graycol">';
					dataHtml += item.metadata_name + '</span><div style="float: right"><input type="checkbox" name="checkbox_1" class="chk_2"   value="' +
						item.metadata_id + '"><label class="label001 checkMT_o"></label></div></li>';
				});
			}
			$("#gg_type1").html(dataHtml);
			var brand_id = $('.addUnit').attr("hiddelValue");
			if(brand_id != null && brand_id.trim().length != 0) {
				$('.shadeCon .s-1-select li input[value="' + brand_id + '"]').attr("checked", true);
				$('.shadeCon .s-1-select li input[value="' + brand_id + '"]').parent().parent().addClass("s-pu_active");
				var liItem = $('<li>' + $('.addUnit').val() + ' <img src="/images/cuohao.png"></li>');
				$(".selected1 li").remove();
				$(".selected1").append(liItem);
			}
		},
		error: function(xhr, type) {
			alert("网络异常");
		}
	});
};

// 占位图
function uploadAdUrl() {
	var file = $('#ad_url_file').get(0).files[0];
	var reader = new FileReader();
	reader.readAsDataURL(file);
	reader.onload = function(e) {
		var img = new Image();
		img.src = this.result;
		img.onload = function() {
			var base64 = compressImg(img, 0.8, "image/jpeg");
			$('#ad_url').val(base64);
		}

	}
}

// 首页图
function uploadShowUrl() {
	var file = $('#show_url_file').get(0).files[0];
	var reader = new FileReader();
	reader.readAsDataURL(file);
	reader.onload = function(e) {
		var img = new Image();
		img.src = this.result;
		img.onload = function() {
			var base64 = compressImg(img, 0.8, "image/jpeg");
			$('#show_url').val(base64);
		}
	}
}
//本地选择上传图片,处理单张图片修改
function uploadPicOneOnchange() {
	var fileInput = document.getElementById("hiddenFileOneInput");
	for(var i = 0; i < fileInput.files.length; i++) {
		var type = fileInput.files[i].type;
		if(type.indexOf("image") != '0') {
			Ext.Msg.alert('警告', "您选中的" + fileInput.files[i].name + "不是图片类型，请检查！");
			return;
		}
	}
	var panelId = document.getElementById("hiddenFileOneInput").valueId;
	var childrenList = $('.' + panelId);
	var thisdiv = $('.' + panelId).find('.current');

	var reader = new FileReader();
	reader.readAsDataURL(fileInput.files[0]);
	fileInput.value = "";
	reader.onload = function(e) {
		var img = new Image();
		img.src = this.result;
		img.onload = function() {
			var base64 = compressImg(img, 0.8, "image/jpeg");
			thisdiv.prev().attr("base64", base64);
			thisdiv.prev().val(base64);
			thisdiv.find('.forupload').attr('src', base64);
		}

	}

}
// 本地选择上传图片
function uploadPicOnchange() {
	var fileInput = document.getElementById("hiddenFileInput");
	for(var i = 0; i < fileInput.files.length; i++) {
		var type = fileInput.files[i].type;
		if(type.indexOf("image") != '0') {
			alert("您选中的" + fileInput.files[i].name + "不是图片类型，请检查！");
			return;
		}
	}
	var panelId = document.getElementById("hiddenFileInput").valueId;
	var childrenList = $('.' + panelId);
	if($('.' + panelId).find('.bannerShow').length + fileInput.files.length > 6) {
		alert('只能选择6张图片');
		return;
	}

	bathChangeBase64(0, fileInput);
}

// 批量修改，因为base64转换的原因 不能使用for循环
function bathChangeBase64(index, fileInput) {
	var panelId = document.getElementById("hiddenFileInput").valueId;
	var addBtn = '<div class="addIco" onclick="document.getElementById(\'hiddenFileInput\').valueId = \'' + panelId +
		'\';document.getElementById(\'hiddenFileInput\').click()"><img src="/images/addIco.png"></div>';
	var file = fileInput.files[index];
	var fileSize = fileInput.files.length;
	var picParentPanel = '';
	var url = window.URL.createObjectURL(file);
	var showId = panelId + "_show";
	var _base64 = panelId + "_base64";

	if(panelId == "detail_list") {
		var img = '<input type="text" style="display:none"  class="' +
			_base64 + '"   name="" id="" value="111" /><div class="bannerShow ' +
			showId + '"><img class="forupload" src="' +
			url + '"><textarea name="" id="tel" value="" class="imgdetails" placeholder="输入（2-200字数限制）" rows="3" cols="10" maxlength="200"></textarea><img src="/images/img-edit.png" class="edit" valueId="detail_list"/><img src="/images/delete.png" class="delete" valueId="detail_list"></div>';
	} else {
		var img = '<input type="text" style="display:none"  class="' + _base64 +
			'"   name="" id="" value="111" /><div class="bannerShow ' + showId + '"><img class="forupload" src="' +
			url + '"/><img src="/images/img-edit.png" class="edit" valueId="banner_list"/><img src="/images/delete.png" class="delete" valueId="banner_list"/></div>';
	}

	var panelId = document.getElementById("hiddenFileInput").valueId;
	// 删除最后一个加号
	var banner_list = $('.' + panelId);
	banner_list.children().get(banner_list.children().length - 1).remove();
	// 添加选择的图片
	$('.' + panelId).append(img);
	if(file.size / 1024 < 300) {
		var reader = new FileReader();
		reader.readAsDataURL(file);
		reader.onload = function(e) {
			var hiddenIndex = $('.' + document.getElementById("hiddenFileInput").valueId + ' .' +
				document.getElementById("hiddenFileInput").valueId + '_show').length;
			hiddenIndex = hiddenIndex <= 0 ? 0 : hiddenIndex - 1;
			banner_list.find('.' + _base64 + ':eq(' + hiddenIndex + ')').attr("base64", this.result);
			index++;
			if(fileSize > index) {
				file = fileInput.files[index];
				bathChangeBase64(index, fileInput);
			} else {
				fileInput.value = ''
			}
		}
	} else {
		var reader = new FileReader();
		reader.readAsDataURL(file);
		reader.onload = function(e) {
			var img = new Image();
			img.src = this.result;
			img.onload = function() {
				var base64 = compressImg(img, 0.8, "image/jpeg");
				var hiddenIndex = $('.' + document.getElementById("hiddenFileInput").valueId + ' .' +
					document.getElementById("hiddenFileInput").valueId + '_show').length;
				hiddenIndex = hiddenIndex <= 0 ? 0 : hiddenIndex - 1;
				banner_list.find('.' + _base64 + ':eq(' + hiddenIndex + ')').attr("base64",
					base64);
				index++;
				if(fileSize > index) {
					file = fileInput.files[index];
					bathChangeBase64(index, fileInput);
				} else {
					fileInput.value = ''
				}
			}

		}
	}

	var childrenList = $('.' + panelId);
	if($('.' + panelId).find('.bannerShow').length >= 6) {
		return;
	} else {
		banner_list.append(addBtn);
	}
}
//图片删除事件
$('.imgShowD').on('click', '.delete', function() {
	var panelId = $(this).attr("valueId");
	var showId = $(this).attr("valueId") + "_show";
	if($('.' + $(this).attr("valueId")).find('.' + showId).length >= 6) {
		var addBtn = '<div class="addIco" onclick="document.getElementById(\'hiddenFileInput\').valueId = \'' +
			panelId + '\';document.getElementById(\'hiddenFileInput\').click()"><img src="/images/addIco.png"></div>';
		var banner_list = $('.' + panelId);
		banner_list.append(addBtn);
	}
	$(this).parent().prev().remove();
	$(this).parent().remove();
});
//图片修改事件
$('.imgShowD').on('click', '.edit', function() {
	var panelId = $(this).attr("valueId");
	var showId = $(this).attr("valueId") + "_show";
	document.getElementById("hiddenFileOneInput").valueId = panelId;
	$("#hiddenFileOneInput").click();
	$(".bannerShow").removeClass("current");
	$(this).parent().addClass("current");
});

//图片查看事件
$('.imgShowD').on('click', '.forupload', function() {
	$('.shadePro').show();
	$('.shadeConst').show();
	var img = $(this).attr("src");
	var $img = $("<img src='" + img + "' />")
	$('.shadeConst .showImage').append($img);
	$('.inviteCancle').on('click', function() {
		$('.shadePro').hide();
		$('.shadeConst').hide();
		$($img).remove();
	});
});
// 收集保存到数据库的数据
function getAddParam() {
	var detailHtml = $(".detail_list div");
	var detailList = new Array();
	for(var i = 0; i < detailHtml.length; i++) {
		var obj = {};
		if($(".detail_list div:eq(" + i + ") img").parent().prop("className") != "addIco") {
			obj = {
				pic_url: $(".detail_list div:eq(" + i + ")").prev().attr('base64'),
				pic_desc: $('.imgdetails').get(i).value
			};
			if(obj.pic_desc.trim().length > 200) {
				alert("详情描述的大小不能超过200");
				return;
			}
			detailList.push(obj);
		}
	}

	var bannerHtml = $('.banner_list div');
	var bannerList = new Array();
	for(var i = 0; i < bannerHtml.length; i++) {
		var obj = {};
		var pic_id = $(".banner_list div:eq(" + i + ") img").eq(0).attr("banner_id");
		if($(".banner_list div:eq(" + i + ") img").parent().prop("className") != "addIco") {

			obj = {
				pic_url: $(".banner_list div:eq(" + i + ")").prev().attr('base64'),
			};
			bannerList.push(obj);
		}
	}

	// 商品介绍
	var infoListHtml = $('.info_list');
	var infoList = new Array();
	for(var i = 0; i < infoListHtml.length; i++) {
		var info_title = $('.info_list .leftParameter input[type="text"]').get(i);
		var info_content = $('.info_list .rightParameter textarea').get(i);
		var obj = {
			info_title: info_title.value,
			info_content: info_content.value
		};
		if(obj.info_title.trim().length < 1) {
			alert("商品介绍名称不能为空");
			return;
		}
		if(obj.info_content.trim().length < 1) {
			alert("商品介绍的内容不能为空");
			return;
		}
		infoList.push(obj);
	}

	// 商品规格
	var standHtml = $('.stand_list');
	var standardList = new Array();
	for(var i = 0; i < standHtml.length; i++) {
		var index = i * 5;
		var standard_must = $('.stand_list input[type="text"]').get(index);
		var optional_first = $('.stand_list input[type="text"]').get(index + 1);
		var optional_second = $('.stand_list input[type="text"]').get(index + 2);
		var price = $('.stand_list input[type="text"]').get(index + 3);
		var store_num = $('.stand_list input[type="text"]').get(index + 4);
		var obj = {
			standard_must: standard_must.value,
			optional_first: optional_first.value,
			optional_second: optional_second.value,
			price: price.value,
			store_num: store_num.value
		};
		if(obj.standard_must.trim().length < 1) {
			alert('第一个为必填规格');
			return;
		}
		if(obj.price.trim().length < 1 || obj.price.trim().length > 100000) {
			alert('价格为必填');
			return;
		}
		if(obj.store_num.trim().length < 1 || obj.store_num.trim().length > 100000) {
			alert('库存为必填');
			return;
		}
		if(obj.store_num <= 0) {
			alert('库存量必须大于0');
			return;
		}
		if((obj.standard_must.trim().length + obj.optional_first.trim().length + obj.optional_second.trim().length) > 40) {
			alert("商品规格总长度不能超过40");
			return;
		}
		standardList.push(obj);
	}

	var adapt_all_models = $('#all_models_chk').is(':checked') ? 1 : 0;

	var data = {
		goods_name: $('.list input[name="goods_name"]').val(),
		brand_id: $('.addBrand').attr("hiddelValue"),
		brand_name: $('.addBrand').val(),
		unit_name: $('.addUnit').val(),
		unit_id: $('.addUnit').attr("hiddelValue"),
		ad_url: $('#ad_url').val(),
		show_url: $('#show_url').val(),
		tel: $('.tel').val(),
		infoList: infoList,
		standardList: standardList,
		bannerList: bannerList,
		detailList: detailList,
		carModelList: model_persist,
		adapt_all_models: adapt_all_models
	};

	console.log(data);

	if(data.goods_name.length > 38 || data.goods_name.length < 13) {
		alert("商品名称长度必须在13到38之间");
		return;
	}
	if(category_persist == null || category_persist['third_category_id'] == null ||
		category_persist['third_category_id'] == "") {
		alert("类目格为必选字段");
		return;
	} else {
		data.first_category_id = category_persist.first_category_id;
		data.first_category_name = category_persist.first_category_name;
		data.second_category_id = category_persist.second_category_id;
		data.second_category_name = category_persist.second_category_name;
		data.third_category_id = category_persist.third_category_id;
		data.third_category_name = category_persist.third_category_name;
	}

	if(data.brand_id == null || data.brand_id.trim().length < 1) {
		alert("品牌为必选字段");
		return;
	}

	if(data.unit_id == null || data.unit_id.trim().length < 1) {
		alert("计量单位为必选字段");
		return;
	}

	if(data.show_url == null || data.show_url.trim().length < 1) {
		alert("首页为必选字段");
		return;
	}
	if(data.bannerList == null || data.bannerList.length < 1) {
		alert("banner图为必选字段");
		return;
	}

	if(data.detailList == null || data.detailList.length < 1) {
		alert("详情图为必选字段");
		return;
	}
	if(data.tel.length < 1 || data.tel.length > 20) {
		alert("电话长度不能超过20");
		return;
	}
	if(data.infoList == null || data.infoList.length < 1) {
		alert("商品介绍为必选字段");
		return;
	}
	if($('#all_models_chk').is(':checked')) {
		data.adapt_all_models = 1;
	} else {
		data.adapt_all_models = 0;
	}

	return data;
}

// 添加商品到数据库
function addGoods(data, url) {
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: url,
		dataType: 'json',
		data: JSON.stringify(data),
		success: function(responseText, textStatus, jqXHR) {
			$('.addGoods').attr('disabled', false);
			$('.adUnderdGoods').attr('disabled', false);
			var dataHtml = '';
			var first = true;
			if(responseText.error_no == '0') {
				alert("添加成功");
				window.location.reload();
			} else {
				alert(responseText.error_info);
			}
		},
		error: function(xhr, type) {
			$('.addGoods').attr('disabled', false);
			$('.adUnderdGoods').attr('disabled', false);
			alert("网络异常");
		}
	});
}