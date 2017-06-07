$(document).ready(function() {
	var data = {
		page: 1,
		limit: 15
	};

	listsReady(data);
	// 获取所有的商品品牌
	getAllProductBrand();
})
// 导出
	$('.export_client_excel').on('click', function() {
		var orderNum = $('.orderNum').val();
		var orderTitle = $('.orderTitle').val();
		var orderBrand = $('.orderBrand').val();
		var orderCode = $('.orderCode').val();
		var orderTel = $('.orderTel').val();
		var start_date = $('.start_date').val();
		var end_date = $('.end_date').val();
		var order_invite_code_start = $('.orderCodeStart').val();
		var order_invite_code_end = $('.orderCodeEnd').val();
		var start_date1 = null;
		var reg = /\d+/g;
		if(start_date != '') {
			start_date1 = start_date.replace(/[^0-9]/ig, "");
		}
		var end_date1 = null;
		if(end_date != '') {
			end_date1 = end_date.replace(/[^0-9]/ig, "");
		}
		var requestparams = "?access_token="
				+ localStorage.access_token
				;
		if (start_date1
				&& start_date1.trim().length > 0) {
			requestparams += "&start_date="
				+ start_date1.trim();
		}
		if (end_date1
				&& end_date1.trim().length > 0) {
			requestparams += "&end_date="
				+ end_date1.trim();
		}
		if (orderNum
				&& orderNum.trim().length > 0) {
			requestparams += "&serialize_num="
				+ orderNum.trim();
		}
		if (orderTel
				&& orderTel.trim().length > 0) {
			requestparams += "&regist_tel="
					+ orderTel.trim();
		}
		if (orderTitle
				&& orderTitle.trim().length > 0) {
			requestparams += "&prdt_name="
					+ orderTitle.trim();
		}
		if (orderCode
				&& orderCode.trim().length > 0) {
			requestparams += "&invite_code="
					+ orderCode.trim();
		}
		if (orderBrand
				&& orderBrand.trim().length > 0) {
			requestparams += "&prdt_brand="
					+ orderBrand.trim();
		}
		if (order_invite_code_start
				&& order_invite_code_start.trim().length > 0) {
			requestparams += "&order_invite_code_start="
					+ order_invite_code_start.trim();
		}
		if (order_invite_code_end
				&& order_invite_code_end.trim().length > 0) {
			requestparams += "&order_invite_code_end="
					+ order_invite_code_end.trim();
		}
		window.open('/json/900102'+ requestparams, "_blank");
	});
// 查询
function searchOnclick(flag) {
	var orderNum = $('.orderNum');
	var orderTitle = $('.orderTitle');
	var orderBrand = $('.orderBrand');
	var orderCode = $('.orderCode');
	var orderTel = $('.orderTel');
	var start_date = $('.start_date').val();
	var end_date = $('.end_date').val();
	var order_invite_code_start = $('.orderCodeStart').val();
	var order_invite_code_end = $('.orderCodeEnd').val();
	var start_date1 = null;
	var reg = /\d+/g;
	if(start_date != '') {
		start_date1 = start_date.replace(/[^0-9]/ig, "");
	}
	var end_date1 = null;
	if(end_date != '') {
		end_date1 = end_date.replace(/[^0-9]/ig, "");
	}
	data = {
		serialize_num: orderNum.val(),
		prdt_name: orderTitle.val(),
		prdt_brand: orderBrand.val(),
		invite_code: orderCode.val(),
		regist_tel: orderTel.val(),
		start_date: start_date1,
		end_date: end_date1,
		page: 1,
		limit: 15
	}
	if(!end_date1){
		delete data.end_date;
	}
	if(!start_date1){
		delete data.start_date;
	}
	if(data.serialize_num.trim()==''){
	  delete data.serialize_num;
	}
	if(data.prdt_name.trim()==''){
	  delete data.prdt_name;
	}
	if(data.prdt_brand.trim()==''){
	  delete data.prdt_brand;
	}
	if(data.regist_tel.trim()==''){
	  delete data.regist_tel;
	}
	if(data.invite_code.trim()==''){
	  delete data.invite_code;
	}
	if (order_invite_code_start
	&& order_invite_code_start.trim().length > 0) {
		data.order_invite_code_start=order_invite_code_start;
	}
	if (order_invite_code_end
		&& order_invite_code_end.trim().length > 0) {
		data.order_invite_code_end=order_invite_code_end;
	}
	if(flag == 1) {
		data.page = $('#page_now').val();
	}
	data.limit = $('#page_limit').val();
	listsReady(data);
}

function listsReady(data) {
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: "/json/900101",
		dataType: 'json',
		data: JSON.stringify(data),
		success: function(responseText, textStatus, jqXHR) {
			var dataHtml = '<tr><th>序号</th><th>商品信息</th><th>订单信息</th><th>订单总额</th><th>下单时间</th><th>买家信息</th><th>收货信息</th><th>买家留言</th></tr>';
			if(responseText.error_no == '0') {
				var resultList = responseText.result_list;
				$("#total_num").html(responseText.total_num);
				$(".page").html(pageHtml(data.page, data.limit, responseText.total_num));
				var count = 0;
				resultList.forEach(function(item) {
					count++;

					dataHtml += '<tr class="tableEditNone">';
					// 商品信息
					dataHtml += '<td>' + count + '</td><td class="productTd"><dl class="productDESC"><dt class="productImg"><img src="' + ossUrl +
						item.prdt_img +
						'"></dt><dd class="productName"><span style="cursor: pointer;" info=' + escape(JSON.stringify(item)) + ' onclick="productInfo(this)" class="productName1 textU" id="' +
						item.prdt_id + '">' +
						item.prdt_name +
						'</span><br/><span class="productName2">' +
						item.prdt_id +
						'</span></dd><dd class="productClass"><span class="productName1">品牌：' +
						item.prdt_brand +
						'</span><br/><span class="productName1">类目：' +
						item.prdt_nav + '</span></dd></dl></td>';
					dataHtml += ' <td class="orderNumber"><span class="productName3">订单编号：' + item.serialize_num + '</span>';
					// 规格/价格
					var standardHtml = '';
					var consigneeHtml = '';
					var goodsHtml = '';
					var wordsHtml = '';
					item.standard_detail
						.forEach(function(standard_detail) {
							standardHtml += '<span class="productName3"><em class="standard_detail">' +
								standard_detail.standard_must + '|' +
								standard_detail.optional_first + '|' +
								standard_detail.optional_second + '</em><em class="prdt_price">' +
								standard_detail.prdt_price + '/' +
								standard_detail.metadata_name + '</em><em class="prdt_num">' +
								standard_detail.prdt_num + '</em></span>';
						});
					// 订单信息
					dataHtml += standardHtml;
					dataHtml += '</td>';
					// 总额
					dataHtml += '<td>';
					dataHtml += item.total_money;
					dataHtml += '</td>';

					// 下单时间
					dataHtml += '<td>';
					dataHtml += dateFormat(item.init_date, item.init_time);
					dataHtml += '</td>';

					// 买家信息
					dataHtml += '<td class="userInfo">';
					dataHtml += '<span class="productName3 textU" style="cursor: pointer;" onclick="buyerInfo('+item.client_id+')" id="' +
						item.client_id + '">' +
						item.nick_name + '</span><span class="productName3">' +
						item.regist_tel + '</span><span class="productName3">' +
						item.invite_code + '</span>';
					dataHtml += '</td>';

					// 收货信息
					dataHtml += '<td class="takeInfo">';
					dataHtml += '<span class="productName3 textU" style="cursor: pointer;" info=' + escape(JSON.stringify(item)) + ' onclick="takeInfo(this)">收货信息</span>';
					dataHtml += '</td>';

					// 买家留言
					if(item.leaved_word.trim() == '') {
						dataHtml += '<td class="wordsInfo">无</td>';
					} else {
						dataHtml += '<td class="wordsInfo"><span class="productName3 textU" style="cursor: pointer;" info=' + escape(JSON.stringify(item)) + ' onclick="wordsInfo(this)">买家留言</span></td>';
					}

					dataHtml += '</tr>';

				});
				// clear
				$('.clearInput').on('click', function() {
					$('.inputTex').val('');
					$('.dataType').find('option:first').prop('selected', true);
					$('.dataStatus').find('option:first').prop('selected', true);
					showAllGoodsbrand();
					var data = {
					    		page: 1,
					    		limit: 15
					    	};
					    	listsReady(data);
				})

			}
			$("#orderGrid").html(dataHtml);
		},
		error: function(xhr, type) {
			// alert("网络异常");
		}
	});
};
// 查看商品信息
function productInfo(item) {
	var dd = $(item).attr("info");
	var obj = eval('(' + unescape(dd) + ')');
	var prdt_id = obj.prdt_id;

	var dataS = {
		access_token: localStorage.access_token,
		goods_id: prdt_id,
	}
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: "/queryGoods",
		dataType: 'json',
		data: JSON.stringify(dataS),
		success: function(responseText, textStatus, jqXHR) {
			var takeHtml = '';
			if(responseText.error_no == '0') {
				var resultList = responseText.result_list;
				resultList.forEach(function(item) {
					// 单位信息
					var productHtml = '<div class="shadeMain"><h4 class="borBot2 cor2 unit">商品信息</h4><ul class="addInvite">';
					productHtml += '<li class="bor1"><label><span>*</span>商品名称</label><span>' +
						item.goods_name + '</span></li>';
					productHtml += '<li class="bor1"><label><span>*</span>类目</label><span>' +
						item.third_category_name + '</span></li>';
					productHtml += '<li class="bor1"><label><span>*</span>品牌</label><span>' +
						item.brand_name + '</span></li>';
					productHtml += '<li class="bor1"><label><span>*</span>计量单位</label><span>' +
						item.unit_name + '</span></li>';
					// 占位图
					if(item.ad_url == '') {
						productHtml += '<li class="bor1"><label><span>*</span>占位图</label><span>无</span></li>';
					} else {
						productHtml += '<li class="bor1"><label><span>*</span>占位图</label><span><img src="' + ossUrl +
							item.ad_url + '"></span></li>';
					}
					// 适用机型
					productHtml += '<li class="bor1" style="padding-left:60px"><label style="margin-left:0" class="jxlabel"><span>*</span>适用机型</label><div class="addDESC">';
					var jiHtml = '';
					item.carModelList
						.forEach(function(carModelList) {
							jiHtml += '<span class="jixing" id="' + carModelList.pic_id + '">' + carModelList.car_brand_name + '|' +
								carModelList.car_type + '|' +
								carModelList.car_models_name + '   </span>';
						});
					if(item.adapt_all_models == "1") {
						productHtml += '适用全部机型';
					} else {
						productHtml += jiHtml;
					}
					productHtml += '</div></li>';

					// banner图
					productHtml += '<li class="bor1 parameter">banner图<img src="/images/arrowDown.png"></li><li class="bor1"><div class="imgShowD">';
					var bannerHtml = '';
					item.bannerList
						.forEach(function(bannerList) {
							bannerHtml += '<div class="bannerS"><img src="' + ossUrl +
								bannerList.banner_url + '"></div>';
						});
					productHtml += bannerHtml;
					productHtml += '</div></li>';
					productHtml += '<li class="bor1 parameter">商品详图<img src="/images/arrowDown.png"></li><li class="bor1"><div class="imgShowD">';
					// 商品详图
					var shopHtml = '';
					item.detailList.forEach(function(detailList) {
						shopHtml += '<div class="bannerS"><img src="' + ossUrl +
							detailList.detail_url + '"><br/><span class="spantext">' +
							detailList.pic_desc + '</span></div>';
					});
					productHtml += shopHtml;
					productHtml += '</div></li>';
					productHtml += '<li class="bor1"><label><span>*</span>客服电话</label><span>' +
						item.tel + '</span></li>';
					productHtml += '<li class="bor1 parameter">商品参数<img src="/images/arrowDown.png"></li>';
					// 商品参数
					var detailHtml = '';
					item.infoList
						.forEach(function(infoList) {
							detailHtml += '<li class="bor1"><label style="padding-right: 20px;"><span>*</span>名称</label><span class="describe">' +
								infoList.info_title + '</span><label style="text-align: right;padding-right: 20px;"><span>*</span>值</label><span class="describe">' +
								infoList.info_content + '</span></li>';
						});
					productHtml += detailHtml;
					productHtml += '</li>';
					// 商品规格
					if(item.standardList.length == 0) {
						productHtml += '</ul><div class="shadeBotPos"><span class="shadeBot inviteCancle">关闭</span></div></div>'
					} else {
						productHtml += '<li class="bor1 parameter">商品规格<img src="/images/arrowDown.png"></li>';
						var standardHtml = '';
						item.standardList
							.forEach(function(standardList) {
								standardHtml += '<li class="bor1"><label class="describe1"><span>*</span>名称</label><span class="describe1">' +
									standardList.standard_must + '</span><span class="describe1">' +
									standardList.optional_first + '</span><span class="describe1">' +
									standardList.optional_second + '</span><label style="text-align: right;padding-right: 20px;"><span>*</span>价格</label><span class="describe1">' +
									standardList.price + '</span><label style="text-align: right;padding-right: 20px;"><span>*</span>库存</label><span class="describe1">' +
									standardList.store_num + '</span></li>';
							});
						productHtml += standardHtml;
						productHtml += '</li>';
						productHtml += '</ul><div class="shadeBotPos"><span class="shadeBot inviteCancle">关闭</span></div></div>';
					}

					$('.shadeCon').html(productHtml);
				});

				$('.shade').show();
				$('.shadeCon').show();

				$('.inviteCancle').on('click', function() {
					$('.shade').hide();
					$('.shadeCon').hide();
				})
			}
		},
		error: function() {

		}
	})

}
// 查看会员信息
function buyerInfo(client_id) {
	var dataS = {
		access_token: localStorage.access_token,
		client_id: client_id,
	}
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: "/getClientList",
		dataType: 'json',
		data: JSON.stringify(dataS),
		success: function(responseText, textStatus, jqXHR) {
			var takeHtml = '';
			if(responseText.error_no == '0') {
				var resultList = responseText.result_list;
				var count = 0;
				resultList.forEach(function(item) {
					count++;
					var buyerHtml ='<li class="bor1"><label><span>*</span>用户编号</label><span class="describe">' +
					item.client_code + '</span><label><span>*</span>邀请码</label><span class="describe">' +
					item.invite_code + '</span></li>';
					// 单位信息
					var unitHtml = '<li class="bor1"><label><span>*</span>单位名称</label><span>' +
						item.enterprise_name + '</span></li>';
					unitHtml += '<li class="bor1"><label><span>*</span>单位简称</label><span>' +
						item.enterprise_short_name + '</span></li>';
					unitHtml += '<li class="bor1"><label><span>*</span>单位地址</label><span>' +
						item.enterprise_address + '</span></li>';
					unitHtml += '<li class="bor1"><label><span>*</span>单位主营</label><span>' +
						item.major_business + '</span></li>';
					unitHtml += '<li class="bor1"><label><span>*</span>单位联系人</label><span>' +
						item.enterprise_linkman + '</span></li>';
					unitHtml += '<li class="bor1"><label><span>*</span>联系人电话</label><span>' +
						item.enterprise_tel + '</span></li>';

					// 用户信息
					var userHtml = '<li class="bor1"><label><span>*</span>昵称</label><span>' +
						item.nick_name + '</span></li>';
					userHtml += '<li class="bor1"><label><span>*</span>性别</label><span>' +
						item.sex + '</span></li>';
					userHtml += '<li class="bor1"><label><span>*</span>用户地区</label><span>' +
						item.address + '</span></li>';

					// 注册信息
					var regHtml = '<li class="bor1"><label><span>*</span>注册时间</label><span>' +
						item.init_date + '</span></li>';
					regHtml += '<li class="bor1"><label><span>*</span>账号状态</label><span>' +
						item.status + '</span></li>';
					regHtml += '<li class="bor1"><label><span>*</span>注册手机</label><span>' +
						item.mobile_tel + '</span></li>';

					var $buyer = $('<div class="shadeMain"><h4 class="borBot2 cor2 unit">会员信息</h4><ul class="addInvite">' +
						buyerHtml + '<h4 class="borBot2 cor2 unit">单位信息</h4>' +
						unitHtml + '<h4 class="borBot2 cor2 unit">用户信息</h4>' +
						userHtml + '<h4 class="borBot2 cor2 unit">注册信息</h4>' +
						regHtml + '</ul><div class="shadeBotPos"><span class="shadeBot inviteCancle">关闭</span></div></div>');
					$('.shadeCon').html($buyer);
				});

				$('.shade').show();
				$('.shadeCon').show();

				$('.inviteCancle').on('click', function() {
					$('.shade').hide();
					$('.shadeCon').hide();
				})
			}
		},
		error: function() {

		}
	})

}
// 查看收货信息
function takeInfo(item) {
	var dd = $(item).attr("info");
	var obj = eval('(' + unescape(dd) + ')');
	var $take = $('<div class="shadeMain"><h4 class="borBot2 cor2 unit">会员信息</h4><ul class="addInvite"><li class="bor1"><label><span>*</span>订单编号</label><span class="describe">' +
		obj.serialize_num + '</span><label><span>*</span>邀请码</label><span class="describe">' +
		obj.invite_code + '</span></li><h4 class="borBot2 cor2 unit"></h4><li class="bor1"><label><span>*</span>收货人</label><span>' +
		obj.consignee + '</span></li><li class="bor1"><label><span>*</span>收货地址</label><span>' +
		obj.address_info + '</span></li><li class="bor1"><label><span>*</span>联系电话</label><span>' +
		obj.consignee_tel + '</span></li></ul><div class="shadeBotPos"><span class="shadeBot inviteCancle">关闭</span></div></div>');
	$('.shadeCon').html($take);
	$('.shade').show();
	$('.shadeCon').show();

	$('.inviteCancle').on('click', function() {
		$('.shade').hide();
		$('.shadeCon').hide();
	})
}
// 查看买家留言
function wordsInfo(item) {
	var dd = $(item).attr("info");
	var obj = eval('(' + unescape(dd) + ')');
	var $words = $('<div class="shadeMain"><h4 class="borBot2 cor2 unit">买家留言</h4><ul class="addInvite"><li class="bor1"><label><span>*</span>订单编号</label><span class="describe">' +
		obj.serialize_num + '</span><label><span>*</span>邀请码</label><span class="describe">' +
		obj.invite_code + '</span></li><h4 class="borBot2 cor2 unit"></h4><li class="bor1 words">' +
		obj.leaved_word + '</li></ul><div class="shadeBotPos"><span class="shadeBot inviteCancle">关闭</span></div></div>');
	$('.shadeCon').html($words);
	$('.shade').show();
	$('.shadeCon').show();

	$('.inviteCancle').on('click', function() {
		$('.shade').hide();
		$('.shadeCon').hide();
	});

}

//图片查看事件
$('.shade').on('click', '.imgShowD .bannerS img', function() {
	$('.shadeImg').show();
	$('.shadeWord').show();
	var img = $(this).attr("src");
	var $img = $("<img src='" + img + "' />")
	$('.shadeWord .showImage').append($img);
	$('#closeImg').on('click', function() {
		$('.shadeImg').hide();
		$('.shadeWord').hide();
		$($img).remove();
		$(".shade").show();
		$(".shadeCon").show();
	});
});