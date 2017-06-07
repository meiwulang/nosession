document.write("<script language='javascript' src='/js/htmlOss.js'></script>");
$(document).ready(function() {
	getAllOperator(); // common.js
	
	getAllThridCategory();// goodsCommon.js

	getAllProductBrand();// common.js
	
	var data = {
		goods_status: 1,
		page: 1,
		limit: 15
	};
	listsReady(data);
	//全选事件
	$("#dataGrid").on('click', '#checkAll', function() {
		if($(this).is(':checked')) {
			$("#dataGrid .cklist").prop("checked", true);
			$('.tableEdit').addClass('trActive')
		} else {
			$("#dataGrid .cklist").prop("checked", false);
			$('.tableEdit').removeClass('trActive')
		}
	});
})

//查询（flag:点击查询、选择每页条数时，flag传0,选择页面、跳页时，flag传1）
function searchOnclick(flag) {
	var goods_id = $('.goods_id');
	var goods_name = $('.goods_name');
	var brand_name = $('.brand_name');
	var create_user_name = $('.create_user_name');
	var third_category_name = $('.third_category_name');
	var dataType = $('.dataType');
	var dataTimeFrom = $('#dateFrom');
	var dataTimeTo = $('#dateTo');
	var reg = /\d+/g;
	var a = null;
	var b = null;
	var c = null;
	var d = null;
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
		page: 1,
		limit: 15,
		goods_status: 1,
		goods_id: goods_id.val(),
		goods_name: goods_name.val(),
		brand_name: brand_name.val(),
		create_user_name: create_user_name.val(),
		third_category_name: third_category_name.val(),
		start_date: a + b,
		end_date: c + d,
	};
	if(data.goods_id == null || data.goods_id.length == 0) {
		delete data.goods_id;
	}
	if(data.goods_name == null || data.goods_name.length == 0) {
		delete data.goods_name;
	}
	if(data.brand_name == null || data.brand_name.length == 0) {
		delete data.brand_name;
	}
	if(data.third_category_name == null || data.third_category_name.length == 0) {
		delete data.third_category_name;
	}
	if(data.create_user_name == null || data.create_user_name.length == 0) {
		delete data.create_user_name;
	}
	if(data.start_date == null || data.start_date == 0) {
		delete data.start_date;
	}
	if(data.end_date == null || data.end_date == 0) {
		delete data.end_date;
	}
	if(flag == 1) {
		data.page = $('#page_now').val();
	}
	data.limit = $('#page_limit').val();
	listsReady(data);
//	
//	window.location ='/exportCar?access_token='+localStorage.access_token+'&car_models_name='+carmodelName.val().trim()
//	+'&car_models_id='+carmodelId.val().trim()+'&status='+is_noparams+'&metadata_name='+carmodelType.val().trim()+'&brand_name='+carmodelBrand.val().trim()
//	+'&start_date='+start_date2+'&end_date='+end_date2
//	+'&limit=10000&page=1';
}

function listsReady(data) {
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: "/queryGoods",
		dataType: 'json',
		data: JSON.stringify(data),
		success: function(responseText, textStatus, jqXHR) {
			var dataHtml = ' <tr><th><input id="checkAll" type="checkbox" name="checkbox1" value="checkbox"></th><th>商品信息</th><th>规格/价格</th><th>已售</th><th>库存</th><th>创建日期</th><th>创建人</th><th>上架日期</th><th>最后操作者</th><th>操作</th></tr>';
			if(responseText.error_no == '0') {
				var resultList = responseText.result_list;
				$("#total_num").html('共搜索到<label>' + responseText.total_num + '</label>条数据');
				$(".page").html(pageHtml(data.page, data.limit, responseText.total_num));
				var count = 0;
				resultList
					.forEach(function(item) {
						count++;
						var status = "启用";
						var button = "禁用";
						if(item.status == '0') {
							status = "禁用";
							button = "启用";
						}
						var init_date_format = dateFormat(item.init_date, item.init_time);
						var update_date_format = dateFormat(item.update_date, item.update_time);

						dataHtml += '<tr class="tableEdit">';
						// 商品信息
						dataHtml += '<td><input type="checkbox" class="cklist" name="subBox" value="' + 
						    item.goods_id + '"></td><td class="productTd"><dl class="productDESC"><dt class="productImg"><img src="'+ossUrl +
							item.show_url + '"></dt><dd class="productName"><span class="productName1 textU" style="display:block;" info=' +
							escape(JSON.stringify(item)) + ' onclick="searchPro(this)">' +
							item.goods_name + '</span><span class="productName2">' +
							item.goods_id + '</span></dd><dd class="productClass"><span class="productName1">品牌：' +
							item.brand_name +
							'</span><br/><span class="productName1">三级类目：' +
							item.third_category_name + '</span></dd></dl></td>';
						dataHtml += ' <td>';
						// 规格/价格 已售 库存
						var standardHtml = '';
						var saleHtml = '';
						var storeHtml = '';
						item.standardList
							.forEach(function(standardList) {
								standardHtml += '<span class="productName3"><em class="standard_detail">' +
									standardList.standard_must + '|' +
									standardList.optional_first + '|' +
									standardList.optional_second + '</em><em class="prdt_price"><i>' +
									standardList.price + '</i>/' + standardList.unit_name +
									'</em></span>';
								saleHtml += '<span class="productName3">' + standardList.sale_num +
									'</span>';
								storeHtml += '<span class="productName3">' + standardList.store_num +
									'</span>';
							});
						dataHtml += standardHtml;
						dataHtml += '</td>';

						// 已售
						dataHtml += '<td>';
						dataHtml += saleHtml;
						dataHtml += '</td>';

						// 库存
						dataHtml += '<td>';
						dataHtml += storeHtml;
						dataHtml += '</td>';

						// 创建日期
						dataHtml += '<td>';
						dataHtml += dateFormat(item.init_date, item.init_time);
						dataHtml += '</td>';

						// 创建人
						dataHtml += '<td>';
						dataHtml += item.create_user_name;
						dataHtml += '</td>';
						
						// 上架日期
						dataHtml += '<td>';
						dataHtml += dateFormat(item.update_date, item.update_time);
						dataHtml += '</td>';

						// 最后操作者
						dataHtml += '<td>';
						dataHtml += item.update_user_name;
						dataHtml += '</td>';

						// 操作
						dataHtml += '<td>';
						dataHtml += '<input type="button" name="" id="'+item.goods_id+'" value="下  架" class="tableBot3 ground_goods" />';
						dataHtml += '</td>';

						dataHtml += '</tr>';
					});
				// clear
				$('.clearInput').on('click', function() {
					$('.inputTex').val('');
					
					showAllGoodsbrand();
					showAllOperator();
					showAllThirdCategory();
					
					
					
				})
				// $('.dataType').on('change',function(){
				// alert($(this).find('option:selected').text())
				// })
				// sure||unsure
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
							metadata_id: that.attr('id')
						}
						$.ajax({
							contentType: "application/json; charset=utf-8",
							type: 'POST',
							url: "/updateMetadataStatus",
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
								} else {
									alert(responseText.error_info)
								}
							},
							error: function() {

							}
						})
					} else {

					}
				})
			}
			$("#dataGrid").html(dataHtml);
			// $(province_class).append(cityHtml);

		}

	});
};

//查看商品信息
function searchPro(item) {
	var dd = $(item).attr("info");
	var obj = eval('(' + unescape(dd) + ')');
	var goods_id = obj.goods_id;

	var dataS = {
		access_token: localStorage.access_token,
		goods_id: goods_id,
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
					//单位信息
					var productHtml = '<div class="shadeMain"><h4 class="borBot2 cor2 unit">商品信息</h4><ul class="addInvite">';
					productHtml += '<li class="bor1"><label><span>*</span>商品名称</label><span>' +
						item.goods_name + '</span></li>';
					productHtml += '<li class="bor1"><label><span>*</span>类目</label><span>' +
						item.third_category_name + '</span></li>';
					productHtml += '<li class="bor1"><label><span>*</span>品牌</label><span>' +
						item.brand_name + '</span></li>';
					productHtml += '<li class="bor1"><label><span>*</span>计量单位</label><span>' +
						item.unit_name + '</span></li>';
					//占位图
					if(item.ad_url.trim() == '') {
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

					productHtml += '<li class="bor1 parameter">banner图<img src="/images/arrowDown.png"></li><li class="bor1"><div class="imgShowD">';
					// banner图
					var bannerHtml = '';
					item.bannerList.forEach(function(bannerList) {
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
					item.infoList.forEach(function(infoList) {
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
									standardList.optional_second + '</span><label class="describe1"><span>*</span>价格</label><span class="describe1">' +
									standardList.price + '</span><label class="describe1"><span>*</span>库存</label><span class="describe1">' +
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

//下架商品（单个）
$('.list').on('click', '.ground_goods', function(){
	var goods_id = $(this).attr('id');
	var data = {
		goods_id: goods_id,
		access_token: localStorage.access_token
	}
	if(confirm("确定要下架该商品吗？")) {
		$.ajax({
			contentType: "application/json; charset=utf-8",
			type: 'POST',
			url: "/undercarriageGoods",
			dataType: 'json',
			data: JSON.stringify(data),
			success: function(responseText, textStatus, jqXHR) {
				if(responseText.error_no == '0') {
					alert("下架成功!");
					searchOnclick(1);
				} else {
					alert(responseText.error_info);
				}
			},
			error: function(xhr, type) {

			}
		});
	}else{
		return false;
	}
});
//批量下架
function bathUndercarriageGoods() {
	var checkList = $("#dataGrid .cklist");
	var check_num = 0;
	var ids = [];
	for(var i = 0; i < checkList.length; i++) {
		if(checkList[i].checked) {
			check_num++;
			ids.push(checkList[i].value);
		}
	}
	if(check_num < 1) { //未选择商品
		alert("请至少选择一种商品!");
		return;
	}
	if(confirm("确定要下架选中商品吗？")) {
		var data = {
			ids: ids,
			access_token: localStorage.access_token
		}
		$.ajax({
			contentType: "application/json; charset=utf-8",
			type: 'POST',
			url: "/bathUndercarriageGoods",
			dataType: 'json',
			data: JSON.stringify(data),
			success: function(responseText, textStatus, jqXHR) {
				if(responseText.error_no == '0') {
					alert("下架成功!");
					searchOnclick(1);
				} else {
					alert(responseText.error_info);
				}
			},
			error: function(xhr, type) {

			}
		});
	}

}

function dateFormat(date, time) {
	var date_format = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8) + " ";
	var time_format = time.substring(0, 2) + ":" + time.substring(2, 4) + ":" + time.substring(4, 6);
	return date_format + time_format;
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

// 批量更新已上架的搜索引擎
function updateGroundGoods(goods_id) {
	var data = {
		access_token: localStorage.access_token
	}
	if(confirm("如无特殊需求，请不要点击该按钮,确定要刷新搜索引擎吗？")) {
		$.ajax({
			contentType: "application/json; charset=utf-8",
			type: 'POST',
			url: "/updateGroundGoods",
			dataType: 'json',
			data: JSON.stringify(data),
			success: function(responseText, textStatus, jqXHR) {
				if(responseText.error_no == '0') {
					alert("正在刷新搜索引擎中，请稍后去app查看变化!");
				} else {
					alert(responseText.error_info);
				}
			},
			error: function(xhr, type) {

			}
		});
	}
}