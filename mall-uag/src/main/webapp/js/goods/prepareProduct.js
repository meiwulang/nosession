document.write("<script language='javascript' src='/js/htmlOss.js'></script>");
Array.prototype.indexOfObj = function(val) {
	for(var i = 0; i < this.length; i++) {
		if(val instanceof Object) {
			if(val.car_models_id == this[i].car_models_id) {
				return i;
			}
		} else {

		}
		if(this[i] == val)
			return i;
	}
	return -1;
};
Array.prototype.remove = function(val) {
	var index = this.indexOfObj(val);
	if(index > -1) {
		this.splice(index, 1);
	}
};
var carList = {};
var bannerList = {};
var detailList = {};
var infoList = {};
var standardList = {};

var category_temp = {};
var category_persist = {};
var model_temp = new Array();
var model_persist = new Array();
var model_obj_temp = new Array();
var model_obj_persist = new Array();
$(document)
	.ready(
		function() {
			getAllOperator(); // common.js

			getAllThridCategory(); // goodsCommon.js

			getAllProductBrand(); // common.js

			// 添加商品简介按钮
			$('.addP')
				.on(
					'click',
					function() {
						var addP = $('<li class="bor1 info_list_form"><label><span>*</span>名称</label><input type="text" name="" id="" value="" placeholder="（2-10字符限制）" class="inviteNum" maxlength="8" /><label style="text-align: right;padding-right: 10px;"><span>*</span>值</label><textarea maxLength="200"	name="" id="" value="" placeholder="输入（1-200字数限制）" rows="4" ></textarea><input type="button" name="" id="" value="删除" class="inputBot3 deletePa delete_info"/></li>');
						$(this).before(addP);
					});

			// 添加商品规格按钮
			$('.addPP').on('click', function() {
				var addP = $('<li class="bor1 standard_list_form"><label class="paraLabel"><span>*</span>名称</label>' +
					'<input type="text" name="" id="" value="" placeholder="这个规格是必填的" class="inviteNum2 marR2" />' +
					'<input type="text" name="" id="" value="" placeholder="三个规格40字限制" class="inviteNum2 marR2" />' +
					'<input type="text" name="" id="" value="" placeholder="三个规格40字限制" class="inviteNum2 marR2" />' +
					'<label class="paraLabel" style="text-align: right;padding-right: 10px;"><span>*</span>价格</label>' +
					'<input type="text" name="" id="" value="" placeholder="(1-10字符限制)" title="输入(百万及以下的价格，小数点后保留两位)" class="inviteNum2 marR2 amount" />' +
					'<label class="paraLabel" style="text-align: right;padding-right: 10px;"><span>*</span>库存</label>' +
					'<input type="text" maxLength="8" minLength="1"  name="" id="" value="" placeholder="(1-8字数限制)" class="inviteNum2 storeNum" />' +
					'<input type="button" name="" id="" value="删除" class="inputBot3 deletePara delete_standard"/></li>');
				$(this).before(addP);
			});
			$('.shadePro').on('change', '.amount', function(event) {
				var $amountInput = $(this);
				var value = parseInt($(this).val());
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
				var reg = /\d{8,}\.*/g;
				if(reg.test(value)) {
					alert("输入百万及以下的价格，小数点后保留两位!");
					$(this).val("");
				}
			});
			$('.shadePro').on('change', '.storeNum', function(event) {
				var $amountInput = $(this);
				//响应鼠标事件，允许左右方向键移动 
				event = window.event || event;
				if(event.keyCode == 37 | event.keyCode == 39) {
					return;
				}
				//先把非数字的都替换掉，除了数字和. 
				$amountInput.val($amountInput.val().replace(/[^\d.]/g, ""));
			});
			$('.categoryadd .search_btn').on('click', function() {
				_search_text = $(".categoryadd .inputText1").val();
				var firstcategorylist = $('.shadeThreeLeft .category_parent_list li');
				for(var i = 0; i < firstcategorylist.length; i++) {
					var li = $(firstcategorylist[i]);

					if(li.find('span').text().indexOf(_search_text) < 0) {
						li.find('span').removeClass('addColor');
						li.hide();
					} else {
						li.show();
					}
				}
			});
			// 机型搜查事件
			$('.modeladd .search_btn').on('click', function() {
				_search_text = $('.modeladd .inputText1').val();
				var firstcategorylist = $('.shadeThreeLeft .car_parent_list li');
				for(var i = 0; i < firstcategorylist.length; i++) {
					var li = $(firstcategorylist[i]);
					if(li.find('span').text().indexOf(_search_text) < 0) {
						li.find('span').removeClass('addColor');
						li.hide();
					} else {
						li.show();
					}
				}
			});
			$('.update_goods').on('click', function() {
				$('.update_goods').attr('disabled', true);
				var data = getAddParam();
				if(data) {
					updateGoods(data, "/modifyGoods");
				} else {
					$('.update_goods').attr('disabled', false);
				}
			});

			// 类目
			$('.category_name_form')
				.on(
					'click',
					function() {
						$('.show2').show();

						$('.category_parent_list').empty();
						$('.shadeThreeCenter').empty();
						$('.shadeThreeRight').empty();
						$('.show2Chose').empty();

						if(category_temp != null && category_temp['third_category_id'] != null) {
							$('.show2Chose').append(
								'<dd car_model_id="' + category_temp['third_category_id'] +
								'" class="addColor">' +
								category_temp['third_category_name'] + '</dd>');
						}

						var data = {
							limit: 100000000,
							status: 1
						};
						listFirstCategory(data);

						$('.show2Left').on(
							'click',
							'li span',
							function() {
								var data = {
									fatherId: $(this).attr("first_category_id"),
									limit: 100000000,
									status: 1
								};
								$('.show2Right').empty();
								$('.show2Center').empty();
								listSecondCategory(data);
								category_temp["first_category_id"] = $(this).attr(
									"first_category_id");
								category_temp["first_category_name"] = $(this).text();

								$(this).addClass('addColor').parent().siblings().children('span')
									.removeClass('addColor');
							});

						$('.show2Center').on('click', 'dt', function() {
							var data = {
								fatherId: $(this).attr("second_category_id"),
								limit: 100000000,
								status: 1
							};

							listThirdCategory(data);
							category_temp["second_category_id"] = $(this).attr("second_category_id");
							category_temp["second_category_name"] = $(this).text();
							$(this).addClass('addColor').siblings().removeClass('addColor');
						});

						$('.show2Right').on(
							'click',
							'dd',
							function() {
								var id = $(this).attr("third_category_id");
								var tex = $(this).text();
								$(this).parent().children('dd').removeClass('addColor');
								$(this).addClass('addColor');
								$('.show2Chose').empty();
								$('.show2Chose').append(
									'<dd car_model_id="' + id + '" class="addColor">' + tex +
									'</dd>');

								category_temp["third_category_id"] = $(this).attr(
									"third_category_id");
								category_temp["third_category_name"] = $(this).text();
							});

						// 确定
						$('.addCarConfirm')
							.on(
								'click',
								function() {
									$('.show2').hide();
									if(category_temp != null &&
										category_temp['third_category_id'] != null) {
										$('.category_name_form').val(
											category_temp["third_category_name"]);
										category_persist["first_category_id"] = category_temp["first_category_id"];
										category_persist["first_category_name"] = category_temp["first_category_name"];
										category_persist["second_category_id"] = category_temp["second_category_id"];
										category_persist["second_category_name"] = category_temp["second_category_name"];
										category_persist["third_category_id"] = category_temp["third_category_id"];
										category_persist["third_category_name"] = category_temp["third_category_name"];
									} else {
										category_persist = {};
										$('.category_name_form').val('点击选择');
									}

								});
						$('.addCarCancle')
							.on(
								'click',
								function() {
									$('.show2').hide();
									category_temp["first_category_id"] = category_persist["first_category_id"];
									category_temp["first_category_name"] = category_persist["first_category_name"];
									category_temp["second_category_id"] = category_persist["second_category_id"];
									category_temp["second_category_name"] = category_persist["second_category_name"];
									category_temp["third_category_id"] = category_persist["third_category_id"];
									category_temp["third_category_name"] = category_persist["third_category_name"];
								})
					});
			// 类目

			// 机型
			$('.car_models_form').on(
				'click',
				function() {
					var _adapt_all_models = $('.car_models_form').attr("adapt_all_models");
					if(_adapt_all_models == '1' || _adapt_all_models == 1) {
						$('#all_models_chk').prop('checked', true);
						adaptionAll();
						cancleAll();
					} else {
						$('#all_models_chk').prop('checked', false);
						cancleAllNo();
					}
					if($('#all_models_chk').is(':checked')) {
						$('.show1Center').empty();
						$('.show1Right').empty();
						$('.show1Chose').empty();

						model_temp = [];
						model_obj_temp = [];
						$('.car_models_form').val('适用全部机型');
					}
					$('.show1').show();

					$('.car_parent_list').empty();
					$('.show1Center').empty();
					$('.show1Right').empty();

					$('.show1Chose').html('<input type="text" name="" id="" style="width: 100%;height: 30px;border: 1px solid #fdae60;box-sizing: border-box;" placeholder="输入搜索已选机型"/>');
					if(model_obj_temp != null && model_obj_temp.length > 0) {

						model_obj_temp.forEach(function(item) {
							$('.show1Chose').append(
								'<dd id="' + item.car_models_id + '" car_model_id="' +
								item.car_models_id + '" class="addColor">' +
								item.car_models_name + '</dd>');

							$('.show1Chose').on('click', '#' + item.car_models_id, function() {
								$(this).remove();
								$('.show1Right').find('#' + item.car_models_id).removeClass('addColor');

								model_temp.remove(item.car_models_id);
								var obj = {
									'car_models_id': item.car_models_id,
									'car_models_name': item.car_models_name
								}
								model_obj_temp.remove(obj);

							});

						});
					}

					listCarBrand();

					// 确定
					$('.addCarConfirm').on('click', function() {

						$('.show1').hide();

						if(model_obj_temp != null && model_obj_temp.length > 0) {
							model_persist = model_temp.slice(0);
							model_obj_persist = model_obj_temp.slice(0);
							var show_text = model_obj_persist[0]['car_models_name'];
							if(model_obj_persist.length > 1) {
								show_text += "等";
							}
							$('.car_models_form').val(show_text);
						} else {
							model_persist = [];
							model_obj_persist = [];
							$('.car_models_form').val('点击选择');
						}

						$('.car_models_form').attr('adapt_all_models', $('#all_models_chk').is(':checked') ? 1 : 0);

						if($('#all_models_chk').is(':checked')) {
							adaptionAll();
						}

					});
					$('.addCarCancle').on('click', function() {
						$('.show1').hide();
						model_temp = model_persist.slice(0);
						model_obj_temp = model_obj_persist.slice(0);

						var _adapt_all_models = $('.car_models_form').attr("adapt_all_models");
						if(model_obj_temp != null && model_obj_temp.length > 0) {
							var show_text = model_obj_persist[0]['car_models_name'];
							if(model_obj_persist.length > 1) {
								show_text += "等";
							}
							$('.car_models_form').val(show_text);
						} else if(_adapt_all_models == "1" || _adapt_all_models == 1) {
							$('.car_models_form').val('适用全部机型');
						} else {
							$('.car_models_form').val('点击选择');
						}

						if(_adapt_all_models == '1' || _adapt_all_models == 1) {
							$('#all_models_chk').prop('checked', true);
						} else {
							$('#all_models_chk').prop('checked', false);
						}

					})
					$('.show1Left').on(
						'click',
						'li span',
						function() {
							// var index =
							// $(this).parent().index();
							// console.log(index)
							var data = {
								brand_id: $(this).attr("brand_id"),
								limit: 100000000,
								status: 1
							};
							listCarType(data);

							model_temp["brand_id"] = $(this).attr("brand_id");
							model_temp["brand_name"] = $(this).text();
							$(this).addClass('addColor').parent().siblings().children('span')
								.removeClass('addColor');
						});

					$('.show1Center').on('click', 'dt', function() {
						var data = {
							brand_id: $(this).attr("brand_id"),
							metadata_id: $(this).attr("metadata_id"),
							limit: 100000000,
							status: 1
						};

						listCarModel(data);

						$(this).addClass('addColor').siblings().removeClass('addColor');
					})

				});
			// 机型
			$('.show1Right').on(
				'click',
				'dd',
				function() {
					var car_models_id = $(this).attr("id");
					$(this).attr('id', car_models_id);
					var tex = $(this).text();
					$(this).toggleClass('addColor');
					hasClas = $(this).hasClass('addColor');
					if(hasClas == false) {
						// 不存在
						var thisMeta = $(this).parent().siblings('.show1Chose').find('dd');
						for(var i = 0; i < thisMeta.length; i++) {
							var obj = $(thisMeta[i]);
							if(obj.attr("car_model_id") == car_models_id) {
								console.log(obj.attr("metaDate"));
								thisMeta[i].remove();
							}
						}
						model_temp.remove(car_models_id);
						var obj = {
							'car_models_id': car_models_id,
							'car_models_name': tex
						}
						model_obj_temp.remove(obj);

					} else {
						$('.show1Chose').append(
							'<dd id="' + car_models_id + '" car_model_id="' + car_models_id +
							'" class="addColor">' + tex + '</dd>');
						model_temp.push(car_models_id);
						var obj = {
							'car_models_id': car_models_id,
							'car_models_name': tex
						}
						model_obj_temp.push(obj);
					}
					$('.show1Chose').on('click', '#' + car_models_id, function() {
						$(this).remove();
						$('.show1Right').find('#' + car_models_id).removeClass('addColor');

						model_temp.remove(car_models_id);
						var obj = {
							'car_models_id': car_models_id,
							'car_models_name': tex
						}
						model_obj_temp.remove(obj);

					});

				});

			$('.truncate_category').on('click', function() {
				$('.show2Chose').empty();
				$('.show2Center').empty();
				$('.show2Right').empty();
				$('.categoryadd .inputText1').val('');
				$('.categoryadd .search_btn').click();
				category_temp = {};
			});
			$('.truncate_brand').on('click', function() {
				$('.show1Chose').empty();
				$('.show1Center').empty();
				$('.show1Right').empty();
				$('.modeladd .inputText1').val('');
				$('.categoryadd .inputText1').val('');
				$('.modeladd .search_btn').click();
				$('.categoryadd .search_btn').click();
				model_temp = [];
				model_obj_temp = [];
			});

			// 删除占位图的按钮事件
			$('#del_ad_url').on('click', function() {
				$('#ad_url').val(' '); // base64清空,隐藏域
				$('#ad_url_file').val(''); // base64清空,实际上传的表单
				$('.ad_url_show').val(''); // 展示假地址的文本框
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

			var data = {
				goods_status: 0,
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
			});
			$(".dataType").change(function() {
				var checkText = $(".dataType").find("option:selected").text();
				if(checkText == '计量单位') {
					dataTypeNum = 0
				} else if(checkText == '机械类型') {
					dataTypeNum = 1
				}
			});

			$('.brand_name_form').on('click', function() {
				$('#select_brand_or_unit').empty();
				$('.add_brand_show3').show();
				var data = {
					limit: 100000000,
					status: 1
				};
				$('#name_search').val(null);
				listBrand(data);
				$('#name_search').attr("placeholder", "请输入品牌关键词,输入匹配");
				$('.brandOrUnit_text span').text('品牌')
			});

			$('.unit_name_form').on('click', function() {
				$('.add_brand_show3').show();
				var data = {
					type: 0,
					status: 1,
					limit: 100000000,
				};
				listUnit(data);
				$('#name_search').attr("placeholder", "请输入计量单位关键词,输入匹配");
				$('.brandOrUnit_text span').text('计量单位');
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

			$('.inviteCancle').on('click', function() {
				$('.shade').hide();
				$('.shadeCon').hide();
			})
			// 保存 品牌或者计量单位
			$('.shadeCon .inviteKeep').on(
				'click',
				function() {
					// var numVal =
					// $('input[type="checkbox"]').Text();
					// var numName = $('.chk_2').attr("id");
					var numVal = $('.shadeCon .s-1-select li input[name="checkbox_1"]:checked').val();
					var numIndex = $('.shadeCon .s-1-select li input[name="checkbox_1"]:checked').parent()
						.index();
					// $('.shadeCon .s-1-select li
					// input[name="'+name+'"]').attr("checked",true);
					var numName = $('.shadeCon .s-1-select li input[name="checkbox_1"]:checked').parent()
						.siblings().text();
					if(numVal == '' || numName == '') {
						alert('请填写信息');
					} else {
						var operatorClass = '';
						if($('.brandOrUnit_text span').text() == '品牌') {
							$('.brand_name_form').val(numName);
							$('.brand_name_form').attr("hiddelValue", numVal);
						} else if($('.brandOrUnit_text span').text() == '计量单位') {
							$('.unit_name_form').val(numName);
							$('.unit_name_form').attr("hiddelValue", numVal);
						}

						alert('保存成功！');
						$('.shade').hide();
						$('.shadeCon').hide();
					}
				});

			// 删除
			$('.list').on('click', '.del_goods', function() {
				if(confirm("确定要删除选中商品吗？")) {
					var _parent = $(this).parent().parent();
					var _goods_id = _parent.find(".goods_id").text();
					var data = {
						goods_id: _goods_id
					};
					if(data) {
						delGoods(data, "/delGoods");
					}
				}
				return false;
			});
			// 上架
			$('.list').on('click', '.ground_goods', function() {

				if(confirm("确定要上架选中商品吗？")) {
					var _parent = $(this).parent().parent();
					var _goods_id = _parent.find(".goods_id").text();
					var data = {
						goods_id: _goods_id
					};
					var list = checkProductStandard(data);
					var errMsg = "";
					if(list.length > 0) {
						for(var i = 0; i < list.length; i++) {
							errMsg += list[i] + "\n";
						}
						alert(errMsg);
						return;
					}

					if(data) {
						groundGoods(data, "/groundingGoods");
					}
				}else{
					return false;
				}
			});

			$('#all_models_chk').on(
				'click',
				function() {
					if($(this).is(':checked')) {
						adaptionAll();
						cancleAll();
					} else {
						cancleAllNo();
						model_temp = model_persist.slice(0);
						model_obj_temp = model_obj_persist.slice(0);
						if(model_obj_temp != null && model_obj_temp.length > 0) {

							model_obj_temp.forEach(function(item) {
								$('.show1Chose').append(
									'<dd id="' + item.car_models_id + '" car_model_id="' +
									item.car_models_id + '" class="addColor">' +
									item.car_models_name + '</dd>');
								$('.show1Chose').on(
									'click',
									'#' + item.car_models_id,
									function() {
										$(this).remove();
										$('.show1Right').find('#' + item.car_models_id).removeClass(
											'addColor');

										model_temp.remove(item.car_models_id);
										var obj = {
											'car_models_id': item.car_models_id,
											'car_models_name': item.car_models_name
										}
										model_obj_temp.remove(obj);

									});

							});
						}
					}
				})

			// 编辑商品
			$('.list').on('click', '.productEdit', function() {
				$('#show_url_file').val(''); // 清空
				$('#ad_url_file').val(''); // 清空
				$('.shadePro').show();
				$('.shadeConst').show();

				var _parent = $(this).parent().parent();
				var _goods_id = _parent.find(".goods_id").text();

				var _dbObj = eval('(' +
					unescape(_parent.find('.hidden_grid_value').attr("json")) + ')');
				category_persist["first_category_id"] = _dbObj.first_category_id;
				category_persist["first_category_name"] = _dbObj.first_category_name;
				category_persist["second_category_id"] = _dbObj.second_category_id;
				category_persist["second_category_name"] = _dbObj.second_category_name;
				category_persist["third_category_id"] = _dbObj.third_category_id;
				category_persist["third_category_name"] = _dbObj.third_category_name;

				category_temp["third_category_id"] = category_persist["third_category_id"];
				category_temp["third_category_name"] = category_persist["third_category_name"];

				var _temp_car_model_list = _dbObj.carModelList;
				model_obj_persist = new Array();
				model_obj_temp = new Array();
				model_temp = new Array();
				model_persist = new Array();

				_temp_car_model_list.forEach(function(item) {
					if(item.car_models_id != null && item.car_models_id.trim() != '') {
						model_temp.push(item.car_models_id);
						model_persist.push(item.car_models_id);
						var obj = {
							'car_models_id': item.car_models_id,
							'car_models_name': item.car_models_name
						}
						model_obj_persist.push(obj);
						model_obj_temp.push(obj);
					}
				});

				// 修改表单中添加数据
				$('.goods_name_form').val(_parent.find(".goods_name").text());
				$('.goods_name_form').attr('goods_id', _goods_id);
				$('.brand_name_form')
					.val(_parent.find(".brand_name").text().replace("品牌：", ""));
				$('.brand_name_form').attr("brand_id",
					_parent.find(".brand_name").attr("brand_id"));
				$('.category_name_form').attr("first_category_id",
					_parent.find(".category_name").attr("first_category_id"));
				$('.category_name_form').attr("first_category_name",
					_parent.find(".category_name").attr("first_category_name"));
				$('.category_name_form').attr("second_category_id",
					_parent.find(".category_name").attr("second_category_id"));
				$('.category_name_form').attr("second_category_name",
					_parent.find(".category_name").attr("second_category_name"));
				$('.category_name_form').attr("third_category_id",
					_parent.find(".category_name").attr("third_category_id"));
				$('.category_name_form').attr("third_category_name",
					_parent.find(".category_name").attr("third_category_name"));
				$('.category_name_form').val(
					_parent.find(".category_name").attr("third_category_name"));
				$('.unit_name_form').attr("unit_id",
					_parent.find(".hidden_grid_value").attr("unit_id"));
				$('.unit_name_form').val(_parent.find(".hidden_grid_value").attr("unit_name"));

				$('.ad_url_show').val(_parent.find(".hidden_grid_value").attr("ad_url"));
				$('#ad_url').val(_parent.find(".hidden_grid_value").attr("ad_url"));
				$('.show_url_show').val(_parent.find(".hidden_grid_value").attr("show_url"));
				$('#show_url').val(_parent.find(".hidden_grid_value").attr("show_url"));

				var modle_name = '';

				if(_dbObj.adapt_all_models == 1) {
					$('#all_models_chk').prop('checked', true);
					adaptionAll();
					cancleAll();
					modle_name = '适配全部机型';
				} else {
					cancleAllNo();
					$('#all_models_chk').prop('checked', false);
					if(model_obj_persist == null || model_obj_persist.length == 0) {
						modle_name = '点击选择';
					} else if(model_obj_persist.length > 1) {
						var show_text = model_obj_persist[0].car_models_name;
						if(model_obj_persist.length > 1) {
							show_text += "等";
						}
						modle_name = model_obj_persist[0].car_models_name + "等";
					} else if(model_obj_persist.length == 1) {
						modle_name = model_obj_persist[0].car_models_name;
					}
				}

				$('.car_models_form').val(modle_name);
				$('.car_models_form').attr("adapt_all_models", _dbObj.adapt_all_models);

				var _banner_show = $('.banner_form');
				_banner_show.empty();
				bannerList[_goods_id]
					.forEach(function(item) {

						var _img = '<input type="text" style="display:none"  class="banner_form_base64"   name="" id="" value="111" /><div class="bannerS banner_form_show"><img class=forupload src="' +
							ossUrl +
							item.banner_url +
							'" banner_id=' +
							item.pic_id +
							'><img src="/images/img-edit.png" class="edit" valueId="banner_form"/><img src="/images/delete.png" class="delete" valueId="banner_form"></div>';

						_banner_show.append(_img);
					});
				if(_banner_show.find('.banner_form_show').length < 6) {
					var _addBtn = '<div class="addIco" onclick="document.getElementById(\'hiddenFileInput\').valueId=\'banner_form\';document.getElementById(\'hiddenFileInput\').click()"><img src="/images/addIco.png"></div>';
					_banner_show.append(_addBtn);
				}

				var reg = new RegExp("<br>", "g"); //创建正则RegExp对象    
				var _detail_show = $('.detail_form');
				_detail_show.empty();
				detailList[_goods_id]
					.forEach(function(item) {
						var _img = '<input type="text" style="display:none"  class="detail_form_base64"   name="" id="" value="111" /><div class="bannerShow detail_form_show' +
							'"><img class="forupload" pic_id="' +
							item.pic_id +
							'" src="' +
							ossUrl +
							item.detail_url +
							'"><textarea name="" id="tel" value="" class="imgdetails" placeholder="输入（2-200字数限制）" rows="3" cols="10" maxlength="200">' +
							item.pic_desc.replace(reg, "\n") +
							'</textarea><img src="/images/img-edit.png" class="edit" valueId="detail_form"/><img src="/images/delete.png" class="delete" valueId="detail_form"></div>';

						_detail_show.append(_img);
					});
				if(_detail_show.find('.detail_form_show').length < 6) {
					var _addBtn = '<div class="addIco" onclick="document.getElementById(\'hiddenFileInput\').valueId=\'detail_form\';document.getElementById(\'hiddenFileInput\').click()"><img src="/images/addIco.png"></div>';
					_detail_show.append(_addBtn);
				}

				$('.tel_form').val(_dbObj.tel);

				var _info_list_form = $('.info_list_form');
				_info_list_form.remove();
				infoList[_goods_id]
					.forEach(function(item) {
						var _li_child = '<li class="bor1 info_list_form" info_id="' +
							item.info_id +
							'"><label><span>*</span>名称</label>	<input type="text" name="" id="" value="' +
							item.info_title +
							'" placeholder="（1-5字符限制）" class="inviteNum" maxlength="5" /><label style="text-align: right;padding-right: 10px;"><span>*</span>值</label><textarea maxLength="200"	name="" id="" value="" placeholder="输入（1-200字数限制）" rows="4" >' +
							item.info_content.replace(reg, "\n") + '</textarea><input type="button" name="" id="" value="删除" class="inputBot3 deletePa delete_info" /></li>';
						var _add_info = $('.add_info');
						_add_info.before(_li_child);
					});

				// 删除商品简介按钮
				$('.shadePro').on('click', '.delete_info', function() {
					if($('.info_list_form').length > 1) {
						$(this).parent().remove();
					}
				});

				var _standard_list_form = $('.standard_list_form');
				_standard_list_form.remove();
				standardList[_goods_id]
					.forEach(function(item) {
						var _li_child = '<li class="bor1 standard_list_form"><label class="paraLabel"><span>*</span>名称</label><input standard_id="' +
							item.standard_id +
							'" type="text" name="" id="" value="' +
							item.standard_must +
							'" placeholder="这个规格是必填的" class="inviteNum2 marR2" /><input type="text" name="" id="" value="' +
							item.optional_first +
							'" placeholder="三个规格40字限制" class="inviteNum2 marR2" /><input type="text" name="" id="" value="' +
							item.optional_second +
							'" placeholder="三个规格40字限制" class="inviteNum2 marR2" /><label class="paraLabel " style="text-align: right;padding-right: 10px; "><span>*</span>价格</label><input type="text" name=" " id=" " value="' +
							item.price +
							'" placeholder="(1-10字符限制) " title="输入(百万及以下的价格，小数点后保留两位)" class="inviteNum2 amount" />'+
							'<label class="paraLabel " style="text-align: right;padding-right: 10px; "><span>*</span>库存</label><input sale_num="' +
							item.sale_num +
							'"  type="text" maxLength="8" minLength="1"   name=" " id=" " value="' +
							item.store_num +
							'" placeholder="(1-8字符限制) " class="inviteNum2 storeNum" /><input  type="button" name="" id="" value="删除" class="inputBot3 deletePara delete_standard" /></li>';
						var _add_standard = $('.add_standard');
						_add_standard.before(_li_child);
					});

				// 删除商品规格按钮
				$('.shadePro').on('click', '.delete_standard', function() {
					$(this).parent().remove();
				});

				$('.shadePro .inviteCancle').on('click', function() {
					$('.shadePro').hide();
					$('.shadeConst').hide();
				})
				return false;
			})
			listsReady(data);
		})
// 图片的删除按钮
$('.shadePro .imgShowD').on('click', '.delete', function() {
	var panelId = $(this).attr("valueId");
	var showId = $(this).attr("valueId") + "_show";
	if($('.' + $(this).attr("valueId")).find('.' + showId).length >= 6) {
		var addBtn = '<div class="addIco" onclick="document.getElementById(\'hiddenFileInput\').valueId = \'' +
			panelId +
			'\';document.getElementById(\'hiddenFileInput\').click()"><img  src="/images/addIco.png"></div>';
		var banner_list = $('.' + panelId);
		banner_list.append(addBtn);
	}
	$(this).parent().prev().remove();
	$(this).parent().remove();
});

//图片修改事件
$('.shadePro .imgShowD').on('click', '.edit', function() {
	var panelId = $(this).attr("valueId");
	//var showId = $(this).attr("valueId") + "_show";
	document.getElementById("hiddenFileOneInput").valueId = panelId;
	$("#hiddenFileOneInput").click();
	$(".bannerShow").removeClass("current");
	$(".banner_form_show").removeClass("current");
	$(this).parent().addClass("current");
	//$(this).parent().prev().remove();
	//$(this).parent().remove();
});
//图片查看事件
$('.shadePro .imgShowD').on('click', '.forupload', function() {
	$('.shadeImg').show();
	$('.shadeWord').show();
	var img = $(this).attr("src");
	var $img = $("<img src='" + img + "' />")
	$('.shadeWord .showImage').append($img);
});

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
		goods_status: 0,
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
}

function listsReady(data) {
	$
		.ajax({
			contentType: "application/json; charset=utf-8",
			type: 'POST',
			url: "/queryGoods",
			dataType: 'json',
			data: JSON.stringify(data),
			success: function(responseText, textStatus, jqXHR) {
				var dataHtml = '<tr><th><input id="checkAll" type="checkbox" name="checkbox1" value="checkbox"></th><th>商品信息</th><th>规格/价格</th><th>已售</th><th>库存</th><th>创建时间</th><th>创建人</th><th>最后操作时间</th><th>最后操作者</th><th>操作</th></tr>';
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
								item.goods_id +
								'"></td><td class="productTd"><dl class="productDESC"><dt class="productImg"><img src="' +
								ossUrl +
								item.show_url +
								'"></dt><dd class="productName"><span class="productName1 goods_name textU" style="display:block" onclick="searchProduct(' +
								item.goods_id +
								')">' +
								item.goods_name +
								'</span><span class="productName2 goods_id">' +
								item.goods_id +
								'</span></dd><dd class="productClass"><span class="productName1 brand_name" brand_id="' +
								item.brand_id +
								'">品牌：' +
								item.brand_name +
								'</span><br/><span class="productName1 category category_name" first_category_id=' +
								item.first_category_id + ' first_category_name=' +
								item.first_category_name + ' second_category_id=' +
								item.second_category_id + ' second_category_name =' +
								item.second_category_name + ' third_category_id =' +
								item.third_category_id + ' third_category_name=' +
								item.third_category_name + '>三级类目：' + item.third_category_name +
								'</span></dd></dl></td>';
							dataHtml += ' <td>';
							// 规格/价格 已售 库存
							var standardHtml = '';
							var saleHtml = '';
							var storeHtml = '';
							item.standardList
								.forEach(function(standardList) {
									standardHtml += '<span class="productName3"><em class="standard_detail">' +
										standardList.standard_must +
										'|' +
										standardList.optional_first +
										'|' +
										standardList.optional_second +
										'</em><em class="prdt_price"><i>' +
										standardList.price +
										'</i>/' +
										standardList.unit_name +
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
							dataHtml += '<td><input type="button" name="" id="" value="预览" class="tableBot4 marR1 productView" /><input type="button" name="" id="" value="编辑" class="tableBot1 marR1 productEdit" /><input type="button" name="" id="" value="删除" class="tableBot4 marR1 del_goods" /><input type="button" name="" id="" value="上架" class="tableBot3 ground_goods" /></td>';

							// 计量单位 隐藏
							var ad_url = (item.ad_url == null || item.ad_url.length < 1) ? " " : item.ad_url;
							dataHtml += '<td style="display:none">';
							dataHtml += '<span class="hidden_grid_value" unit_id=' + item.unit_id +
								' unit_name=' + item.unit_name + ' show_url=' + item.show_url + ' test=' +
								item.carModelList + ' tel=' + item.tel + ' json=' +
								escape(JSON.stringify(item)) + ' ad_url=' + ad_url + '/>';

							carList[item.goods_id] = item.carModelList;
							bannerList[item.goods_id] = item.bannerList;
							detailList[item.goods_id] = item.detailList;
							infoList[item.goods_id] = item.infoList;
							standardList[item.goods_id] = item.standardList;

							dataHtml += '</td>';

							dataHtml += '</tr>';

						});
					// clear
					$('.clearInput').on('click', function() {
						showAllOperator();
						$('.inputTex').val('');
						showAllGoodsbrand();
						showAllOperator();
						showAllThirdCategory();
						//							dataType.find('option:first').prop('selected', true);
						//							dataStatus.find('option:first').prop('selected', true);
					})
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
				// 全选事件
				$("#dataGrid").on('click', '#checkAll', function() {
					if($(this).is(':checked')) {
						$("#dataGrid .cklist").prop("checked", true);
						$('.tableEdit').addClass('trActive')
					} else {
						$("#dataGrid .cklist").prop("checked", false);
						$('.tableEdit').removeClass('trActive')
					}
				});
				// $(province_class).append(cityHtml);

			}
		});
};

// 查询要上架上品的规格库存
function checkProductStandard(dataS) {
	var result = -1;
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: "/queryGoods",
		dataType: 'json',
		async: false,
		data: JSON.stringify(dataS),
		success: function(responseText, textStatus, jqXHR) {
			var takeHtml = '';
			if(responseText.error_no == '0') {
				var resultList = responseText.result_list;
				result = checkStand(resultList);
			}
		},
		error: function() {

		}
	})
	return result;
}

$('.viewFooterRight').on('click', function() {
	if($('.viewFooterNav').is(':hidden')) {
		$('.viewFooterNav').show();
	} else {
		$('.viewFooterNav').hide();
	}
})
//点击预览功能
$('.mainLists').on('click', '.productView', function() {
	var thisId = $(this).parent().siblings('.productTd').find('.goods_id').text();
	var dataS = {
		access_token: localStorage.access_token,
		goods_id: thisId
	}
	var viewDom = '';
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: "/queryGoods",
		dataType: 'json',
		data: JSON.stringify(dataS),
		success: function(a) {
			$('.viewFooterPend h4').on('click', 'span', function() {
				$('.viewFooterNav').hide();
			})
			$('.viewBg').css('visibility', 'visible');
			var resultList = a.result_list;
			resultList.forEach(function(b) {
				var choose = '<h4 style="width:100%;text-indent:5px;text-align:left;border-bottom:1px solid #EC283F;">请选择你需要的商品数量(单位:' + b.unit_name + ')<span>关闭</span></h4>';
				b.standardList.forEach(function(choosed) {
					choose += '<li><dl><dt>' + choosed.standard_must + '</span><span>' + choosed.optional_first + '</span><span>' + choosed.optional_second + '</span></dt><dd><span>单价&nbsp;&nbsp;&nbsp;￥' + choosed.price + '</span><span style="margin-left:100px">数量：' + choosed.store_num + '</span></dd></dl><div class="viewBgColor"></div></li>';
				})
				$('.viewFooterPend').html(choose);
				var bannerList = b.bannerList;
				if(bannerList.length == 1) {
					$('.firstBanner').attr('src', ossUrl + bannerList[0].banner_url);
				} else if(bannerList.length == 2) {
					$('.firstBanner').attr('src', ossUrl + bannerList[0].banner_url);
					$('.secondBanner').attr('src', ossUrl + bannerList[1].banner_url);
				} else if(bannerList.length == 3) {
					$('.firstBanner').attr('src', ossUrl + bannerList[0].banner_url);
					$('.secondBanner').attr('src', ossUrl + bannerList[1].banner_url);
					$('.thirdBanner').attr('src', ossUrl + bannerList[2].banner_url);
				} else if(bannerList.length == 4) {
					$('.firstBanner').attr('src', ossUrl + bannerList[0].banner_url);
					$('.secondBanner').attr('src', ossUrl + bannerList[1].banner_url);
					$('.thirdBanner').attr('src', ossUrl + bannerList[2].banner_url);
					$('.fourthBanner').attr('src', ossUrl + bannerList[3].banner_url);
				} else if(bannerList.length == 5) {
					$('.firstBanner').attr('src', ossUrl + bannerList[0].banner_url);
					$('.secondBanner').attr('src', ossUrl + bannerList[1].banner_url);
					$('.thirdBanner').attr('src', ossUrl + bannerList[2].banner_url);
					$('.fourthBanner').attr('src', ossUrl + bannerList[3].banner_url);
					$('.fifthBanner').attr('src', ossUrl + bannerList[4].banner_url);
				} else if(bannerList.length == 6) {
					$('.firstBanner').attr('src', ossUrl + bannerList[0].banner_url);
					$('.secondBanner').attr('src', ossUrl + bannerList[1].banner_url);
					$('.thirdBanner').attr('src', ossUrl + bannerList[2].banner_url);
					$('.fourthBanner').attr('src', ossUrl + bannerList[3].banner_url);
					$('.fifthBanner').attr('src', ossUrl + bannerList[4].banner_url);
					$('.sixBanner').attr('src', ossUrl + bannerList[5].banner_url);
				}
				//				for(var i=0;i<bannerList.length;i++)
				//				b.bannerList.forEach(function(banners) {
				//					bannerHtml += '<div class="swiper-slide"><img src="' + ossUrl +
				//						banners.banner_url + '"></div>';
				//				});

				var productStyle = '';
				b.infoList.forEach(function(style) {
					productStyle += '<dd><label>' + style.info_title + '</label><span>' + style.info_content.replace(/\n|\r\n/g, "<br>").replace(/\s/g, "&nbsp;") + '</dd>'
				});
				//商品详图
				var shopImg = '';
				b.detailList.forEach(function(detials) {
					shopImg += '<li><img src="' + ossUrl + detials.detail_url + '"><h5>' + detials.pic_desc.replace(/\n|\r\n/g, "<br>").replace(/\s/g, "&nbsp;") + '</h5></li>'
				})
				//机型
				var carStyle = '';
				b.carModelList.forEach(function(styles) {
					carStyle += '<li style="width:96.5%;margin-left:3.5%;border-bottom:1px solid #ebebeb;clear:both;">' + styles.car_brand_name + ' ' + styles.car_type + ' ' + styles.car_models_name + '</li>'
				})

				var viewList = '';
				viewList += '<h1>' +
					b.goods_name +
					'</h1><h2 class="viewAdver"><img src="' + ossUrl + b.ad_url + '"></h2><div class="viewBgColor"></div><div class="shopIntroduce"><dl><dt>商品介绍</dt><dd><label>品牌</label><span>' +
					b.brand_name + '</span></dd>' +
					productStyle +
					'<div class="viewBgColor"></div><div class="detailsNavi"><div class="detailsNaviTop"><div class="detailsNaviLeft">图文详情</div></div>' +
					shopImg +
					'</div><div class="detailsNaviTop"><div class="detailsNaviLeft">适用机型</div></div>' + carStyle + '</dl></div>';
				$('.viewCotent').html(viewList);
				if(b.ad_url.trim() == '') {
					$('.viewCotent').find('.viewAdver').hide();
				}
			})
		},
		error: function() {
			alert(2)
		}
	});
	$('.closeView').on('click', function() {
		$('.viewBg').css('visibility', 'hidden');
		$('.firstBanner').attr('src', '');
		$('.secondBanner').attr('src', '');
		$('.thirdBanner').attr('src', '');
		$('.fourthBanner').attr('src', '');
		$('.fifthBanner').attr('src', '');
		$('.sixBanner').attr('src', '');
	})
	
	return false
})
// 检查要上级商品是否有库存为0的情况
function checkStand(resultList) {
	var result = [];
	// 商品规格
	resultList.forEach(function(item) {
		var productName = item.goods_name;
		item.standardList.forEach(function(standardList) {
			var store_num = Number(standardList.store_num);
			if(store_num <= 0) {
				var itemMsg = '商品名称：' + productName + '库存为' + store_num;
				result.push(itemMsg);
			}
		});
	});
	return result;
}

// 查看商品信息
function searchProduct(id) {
	var goods_id = id;

	var dataS = {
		access_token: localStorage.access_token,
		goods_id: goods_id,
	}
	$
		.ajax({
			contentType: "application/json; charset=utf-8",
			type: 'POST',
			url: "/queryGoods",
			dataType: 'json',
			data: JSON.stringify(dataS),
			success: function(responseText, textStatus, jqXHR) {
				var takeHtml = '';
				if(responseText.error_no == '0') {
					var resultList = responseText.result_list;
					resultList
						.forEach(function(item) {
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
							if(item.ad_url.trim() == '') {
								productHtml += '<li class="bor1"><label><span>*</span>占位图</label><span>无</span></li>';
							} else {
								productHtml += '<li class="bor1"><label><span>*</span>占位图</label><span><img src="' +
									ossUrl + item.ad_url + '"></span></li>';
							}
							// 适用机型
							productHtml += '<li class="bor1" style="padding-left:60px"><label style="margin-left:0" class="jxlabel"><span>*</span>适用机型</label><div class="addDESC">';
							var jiHtml = '';
							item.carModelList.forEach(function(carModelList) {
								jiHtml += '<span class="jixing" id="' + carModelList.pic_id + '">' +
									carModelList.car_brand_name + '|' + carModelList.car_type + '|' +
									carModelList.car_models_name + '   </span>';
							});
							if(item.adapt_all_models == "1") {
								productHtml += '适用全部机型';
							} else {
								productHtml += jiHtml;
							}
							productHtml += '<div></li>';

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
								shopHtml += '<div class="bannerS"><img src="' + ossUrl + detailList.detail_url +
									'"><br/><span class="spantext">' + detailList.pic_desc +
									'</span></div>';
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
										infoList.info_title +
										'</span><label style="text-align: right;padding-right: 20px;"><span>*</span>值</label><span class="describe">' +
										infoList.info_content + '</span></li>';
								});
							productHtml += detailHtml;
							productHtml += '</li>';
							// 商品规格
							if(item.standardList.length == 0) {
								productHtml += '</ul><div class="shadeBotPos"><span class="shadeBot inviteCancle showClose">关闭</span></div></div>'
							} else {
								productHtml += '<li class="bor1 parameter">商品规格<img src="/images/arrowDown.png"></li>';
								var standardHtml = '';
								item.standardList
									.forEach(function(standardList) {
										standardHtml += '<li class="bor1"><label class="describe1"><span>*</span>名称</label><span class="describe1">' +
											standardList.standard_must +
											'</span><span class="describe1">' +
											standardList.optional_first +
											'</span><span class="describe1">' +
											standardList.optional_second +
											'</span><label class="describe1"><span>*</span>价格</label><span class="describe1">' +
											standardList.price +
											'</span><label class="describe1"><span>*</span>库存</label><span class="describe1">' +
											standardList.store_num + '</span></li>';
									});
								productHtml += standardHtml;
								productHtml += '</li>';
								productHtml += '</ul><div class="shadeBotPos"><span class="shadeBot inviteCancle showClose">关闭</span></div></div>';
							}
							$('.abcdefg').html(productHtml);
						});

					$('.show3').show();
					$('.abcdefg').show();

					$('.showClose').on('click', function() {
						$('.show3').hide();
						$('.abcdefg').hide();
					})
				}
			},
			error: function() {

			}
		})
}

//图片查看事件
$('.shadeShow .abcdefg').on('click', '.imgShowD .bannerS img', function() {
	$('.shadeImg').show();
	$('.shadeWord').show();
	var img = $(this).attr("src");
	var $img = $("<img src='" + img + "' />")
	$('.shadeWord .showImage').append($img);
	return false;
});
$('.main').on('click','.shadeImg .shadeWord #closeImg', function() {
	$('.shadeImg').hide();
	$('.shadeWord').hide();
	$('.shadeImg .shadeWord img').remove();
});
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
			Ext.Msg.alert('警告', "您选中的" + fileInput.files[i].name + "不是图片类型，请检查！");
			return;
		}
	}
	var panelId = document.getElementById("hiddenFileInput").valueId;
	var childrenList = $('.' + panelId);
	if($('.' + panelId).find('.' + panelId + '_show').length + fileInput.files.length > 6) {
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

	if(panelId == "detail_form") {
		var img = '<input type="text" style="display:none"  class="' +
			_base64 +
			'"   name="" id="" value="111" /><div class="bannerShow ' +
			showId +
			'"><img class="forupload" src="' +
			url +
			'"><textarea name="" id="tel" value="" class="imgdetails" placeholder="输入（2-10字数限制）" rows="3" cols="10" maxlength="200"></textarea><img src="/images/img-edit.png" class="edit" valueId="detail_form"/><img src="/images/delete.png" class="delete" valueId="detail_form"/></div>';
	} else {
		var img = '<input type="text" style="display:none"  class="' + _base64 +
			'"   name="" id="" value="111" /><div class="bannerS ' + showId + '"><img class="forupload" src="' +
			url + '"><img src="/images/img-edit.png" class="edit" valueId="' + panelId + '"/><img src="/images/delete.png" class="delete" valueId="' + panelId + '"></div>';
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
	if($('.' + panelId).find('.' + panelId + '_show').length >= 6) {
		return;
	} else {
		banner_list.append(addBtn);
	}
}
// 占位图
function uploadAdUrl() {
	var file = $('#ad_url_file').get(0).files[0];
	var reader = new FileReader();
	if(file != null) {
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
}

// 首页图
function uploadShowUrl() {
	var file = $('#show_url_file').get(0).files[0];
	var reader = new FileReader();
	if(file != null) {
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
}

function dateFormat(date, time) {
	var date_format = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8) + " ";
	var time_format = time.substring(0, 2) + ":" + time.substring(2, 4) + ":" + time.substring(4, 6);
	return date_format + time_format;
}

function listCarBrand() {
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: "/carbrand/queryforweb",
		dataType: 'json',
		data: JSON.stringify({
			limit: 100000000,
			status: 1
		}),
		success: function(responseText, textStatus, jqXHR) {
			var resultList = responseText.result_list;
			var dataHtml = '';
			var first = true;
			if(responseText.error_no == '0') {
				$('.car_parent_list').empty();
				resultList.forEach(function(item) {
					dataHtml += '<li><span class="first" brand_id=' + item.brand_id + '>' + item.brand_name +
						'</span></li>';
				});
			}
			$('.car_parent_list').append('<input type="text" name="" id="" style="width: 100%;height: 30px;border: 1px solid #fdae60;box-sizing: border-box;" placeholder="输入搜索机型品牌"/>')
			$(".car_parent_list").append(dataHtml);
		},
		error: function(xhr, type) {
			alert("网络异常");
		}
	});
}

function listCarType(data) {
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: "/json/901009",
		dataType: 'json',
		data: JSON.stringify(data),
		success: function(responseText, textStatus, jqXHR) {
			$('.show1Center').empty();
			var resultList = responseText.result_list;
			var dataHtml = '';
			var first = true;
			if(responseText.error_no == '0') {
				$('.show1Center').empty();
				resultList.forEach(function(item) {
					dataHtml += '<dt brand_id=' + data.brand_id + ' metadata_id=' + item.metadata_id + '>' +
						item.car_type + '</dt>';
				});
			}
			$('.show1Center').append('<input type="text" name="" id="" style="width: 100%;height: 30px;border: 1px solid #fdae60;box-sizing: border-box;" placeholder="输入搜索机械类型"/>')
			$('.show1Center').append(dataHtml);

		},
		error: function(xhr, type) {
			alert("网络异常");
		}
	});
}

function listCarModel(data) {
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: "/queryCarModelListWeb",
		dataType: 'json',
		data: JSON.stringify(data),
		success: function(responseText, textStatus, jqXHR) {
			$('.show1Right').empty();
			var resultList = responseText.result_list;
			var dataHtml = '';
			var first = true;
			if(responseText.error_no == '0') {
				resultList.forEach(function(item) {
					if($.inArray(item.car_model_is, model_temp) > -1) {
						dataHtml += '<dd class="addColor" id=' + item.car_model_is + '>' + item.car_model_name +
							'</dd>';
					} else {
						dataHtml += '<dd id=' + item.car_model_is + '>' + item.car_model_name + '</dd>';
					}
				});
			}
			$('.show1Right').append('<input type="text" name="" id="" style="width: 100%;height: 30px;border: 1px solid #fdae60;box-sizing: border-box;" placeholder="输入搜索型号"/>')
			$('.show1Right').append(dataHtml);

		},
		error: function(xhr, type) {
			alert("网络异常");
		}
	});
}

function listFirstCategory(data) {
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: "/json/900507",
		dataType: 'json',
		data: JSON.stringify(data),
		success: function(responseText, textStatus, jqXHR) {
			var resultList = responseText.result_list;
			var dataHtml = '';
			var first = true;
			if(responseText.error_no == '0') {
				$('.category_parent_list').html('<input type="text" name="" id="" style="width: 100%;height: 30px;border: 1px solid #fdae60;box-sizing: border-box;" placeholder="输入搜索一级类目"/>');
				resultList.forEach(function(item) {
					dataHtml += '<li><span class="first" first_category_id=' + item.category_id + '>' +
						item.category_name + '</span></li>';
				});
			}
			$('.category_parent_list').append(dataHtml);

		},
		error: function(xhr, type) {
			alert("网络异常");
		}
	});
}

function listSecondCategory(data) {
	data.webUse = true;
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: "/json/900515",
		dataType: 'json',
		data: JSON.stringify(data),
		success: function(responseText, textStatus, jqXHR) {
			$('.show2Right').empty();
			var resultList = responseText.result_list;
			var dataHtml = '';
			var first = true;
			if(responseText.error_no == '0') {
				resultList.forEach(function(item) {
					$('.show2Center').empty();
					if(category_temp['second_category_id'] == item.category_id) {
						dataHtml += '<dt class="addColor" second_category_id=' + item.category_id + '>' +
							item.category_name + '</dt>';
					} else {
						dataHtml += '<dt second_category_id=' + item.category_id + '>' + item.category_name + '</dt>';
					}
				});
			}
			$('.show2Center').append('<input type="text" name="" id="" style="width: 100%;height: 30px;border: 1px solid #fdae60;box-sizing: border-box;" placeholder="输入搜索二级类目"/>')
			$('.show2Center').append(dataHtml);

		},
		error: function(xhr, type) {
			alert("网络异常");
		}
	});
}

function listThirdCategory(data) {
	data.webUse = true;
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: "/json/900515",
		dataType: 'json',
		data: JSON.stringify(data),
		success: function(responseText, textStatus, jqXHR) {
			$('.show2Right').empty();
			var resultList = responseText.result_list;
			var dataHtml = '';
			var first = true;
			if(responseText.error_no == '0') {
				resultList.forEach(function(item) {
					$('.show2Right').empty();
					if(category_temp['third_category_id'] == item.category_id) {
						dataHtml += '<dd class="addColor" third_category_id=' + item.category_id + '>' +
							item.category_name + '</dd>';
					} else {
						dataHtml += '<dd third_category_id=' + item.category_id + '>' + item.category_name + '</dd>';
					}
				});
			}
			$('.show2Right').append('<input type="text" name="" id="" style="width: 100%;height: 30px;border: 1px solid #fdae60;box-sizing: border-box;" placeholder="输入搜索三级类目"/>')
			$('.show2Right').append(dataHtml);

		},
		error: function(xhr, type) {
			alert("网络异常");
		}
	});
}

function listBrand(data) {
	$
		.ajax({
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
					resultList
						.forEach(function(item) {
							dataHtml += '<li><span class="cho-name graycol">';
							dataHtml += item.brand_name +
								'</span><div style="float: right"><input type="checkbox" name="checkbox_1" class="chk_2"   value="' +
								item.brand_id + '"><label class="label001 checkMT_o"></label></div></li>';
						});
				}
				$("#gg_type1").html(dataHtml);
				var brand_id = $('.brand_name_form').attr("brand_id");
				if(brand_id != null && brand_id.trim().length != 0) {
					$('.shadeCon .s-1-select li input[value="' + brand_id + '"]').attr("checked", true);
					$('.shadeCon .s-1-select li input[value="' + brand_id + '"]').parent().parent().addClass(
						"s-pu_active");
					var liItem = $('<li>' + $('.brand_name_form').val() + ' <img src="/images/cuohao.png"></li>');
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
	$
		.ajax({
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
					resultList
						.forEach(function(item) {
							dataHtml += '<li><span class="cho-name graycol">';
							dataHtml += item.metadata_name +
								'</span><div style="float: right"><input type="checkbox" name="checkbox_1" class="chk_2"   value="' +
								item.metadata_id +
								'"><label class="label001 checkMT_o"></label></div></li>';
						});
				}
				$("#gg_type1").html(dataHtml);
				var unit_id = $('.unit_name_form').attr("unit_id");
				if(unit_id != null && unit_id.trim().length != 0) {
					$('.shadeCon .s-1-select li input[value="' + unit_id + '"]').attr("checked", true);
					var liItem = $('<li>' + $('.unit_name_form').val() + ' <img src="/images/cuohao.png"></li>');
					$('.shadeCon .s-1-select li input[value="' + unit_id + '"]').parent().parent().addClass(
						"s-pu_active");
					$(".selected1 li").remove();
					$(".selected1").append(liItem);
				}
			},
			error: function(xhr, type) {
				alert("网络异常");
			}
		});
};

// 收集保存到数据库的数据
function getAddParam() {
	// 详情图
	// var detailHtml = $(".detail_form");
	var detailHtml = $(".detail_form div");
	var detailList = new Array();
	for(var i = 0; i < detailHtml.length; i++) {
		var obj = {};
		var pic_id = $(".detail_form div:eq(" + i + ") img").eq(0).attr("pic_id");
		if($(".detail_form div:eq(" + i + ") img").parent().prop("className") != "addIco") {

			if(pic_id != null && pic_id != "") {
				obj = {
					pic_url: $(".detail_form").find(".forupload").eq(i).attr('src').replace(ossUrl, ""),
					pic_id: pic_id,
					pic_desc: $('.imgdetails').get(i).value
				};
			} else {
				obj = {
					pic_url: $(".detail_form div:eq(" + i + ")").prev().attr('base64'),
					pic_desc: $('.imgdetails').get(i).value
				};
			}
			if(obj.pic_desc.trim().length > 200) {
				alert("详情描述的大小不能超过200");
				return;
			}
			detailList.push(obj);
		}
	}

	var bannerHtml = $('.banner_form div');
	var bannerList = new Array();
	for(var i = 0; i < bannerHtml.length; i++) {
		var obj = {};
		var pic_id = $(".banner_form div:eq(" + i + ") img").eq(0).attr("banner_id");
		if($(".banner_form div:eq(" + i + ") img").parent().prop("className") != "addIco") {

			if(pic_id != null && pic_id != "") {
				obj = {
					pic_url: $(".banner_form").find(".forupload").eq(i).attr('src').replace(ossUrl, ""),
					pic_id: pic_id
				};
			} else {
				obj = {
					pic_url: $(".banner_form div:eq(" + i + ")").prev().attr('base64'),
				};
			}
			bannerList.push(obj);
		}
	}

	// 商品介绍
	var infoListHtml = $('.info_list_form');
	var infoList = new Array();
	for(var i = 0; i < infoListHtml.length; i++) {
		var info_title = $('.info_list_form:eq(' + i + ')').children().eq(1).val();
		var info_content = $('.info_list_form:eq(' + i + ')').children().eq(3).val();
		var obj = {
			info_title: info_title,
			info_content: info_content
		};
		if($('.info_list_form:eq(' + i + ')').attr('info_id') != null &&
			$('.info_list_form:eq(' + i + ')').attr('info_id') != '') {
			obj.info_id = $('.info_list_form:eq(' + i + ')').attr('info_id');
		}
		if(obj.info_title.trim().length > 5 || obj.info_title.trim().length < 1) {
			alert("商品介绍的标题长度不能超过5");
			return;
		}
		if(obj.info_content.trim().length > 200 || obj.info_content.trim().length < 1) {
			alert("商品介绍的内容必须在在1到200之间");
			return;
		}
		infoList.push(obj);
	}

	// 商品规格
	var standHtml = $('.standard_list_form');
	var standardList = new Array();
	for(var i = 0; i < standHtml.length; i++) {
		var standard_must = $('.standard_list_form:eq(' + i + ')').children().eq(1).val();
		var standard_id = $('.standard_list_form:eq(' + i + ')').children().eq(1).attr('standard_id');
		var optional_first = $('.standard_list_form:eq(' + i + ')').children().eq(2).val();
		var optional_second = $('.standard_list_form:eq(' + i + ')').children().eq(3).val();
		var price = $('.standard_list_form:eq(' + i + ')').children().eq(5).val();
		var store_num = $('.standard_list_form:eq(' + i + ')').children().eq(7).val();
		var sale_num = $('.standard_list_form:eq(' + i + ')').children().eq(7).attr('sale_num');
		var obj = {
			standard_id: standard_id,
			standard_must: standard_must,
			optional_first: optional_first,
			optional_second: optional_second,
			price: price,
			sale_num: sale_num,
			store_num: store_num
		};
		if(obj.standard_id == null || obj.standard_id.length == 0) {
			delete obj.standard_id;
		}
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
		if((obj.standard_must.trim().length + obj.optional_first.trim().length + obj.optional_second.trim().length) > 40) {
			alert("商品规格总长度不能超过40");
			return;
		}
		if(obj.store_num <= 0) {
			alert('库存量必须大于0');
			return;
		}
		standardList.push(obj);
	}
	var adapt_all_models = $('#all_models_chk').is(':checked') ? 1 : 0;
	var data = {
		goods_name: $('.goods_name_form').val(),
		goods_id: $('.goods_name_form').attr('goods_id'),
		brand_id: $('.brand_name_form').attr("brand_id"),
		brand_name: $('.brand_name_form').val(),
		unit_name: $('.unit_name_form').val(),
		unit_id: $('.unit_name_form').attr("unit_id"),
		ad_url: $('#ad_url').val(),
		show_url: $('#show_url').val(),
		tel: $('.tel_form').val(),
		infoList: infoList,
		standardList: standardList,
		bannerList: bannerList,
		detailList: detailList,
		carModelList: model_persist,
		adapt_all_models: adapt_all_models
	};

	// var ad_url = '';
	// if ($('#ad_url').val() != null && $('#ad_url').val().trim() != "") {
	// ad_url = $('#ad_url').val();
	// } else {
	// delete data.ad_url;
	// }

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

	// if (data.standardList == null || data.standardList.length < 1) {
	// alert("商品规格为必选字段");
	// return;
	// }

	return data;
}
// 添加商品到数据库
function updateGoods(data, url) {
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: url,
		dataType: 'json',
		data: JSON.stringify(data),
		success: function(responseText, textStatus, jqXHR) {
			$('.update_goods').attr('disabled', false);
			var dataHtml = '';
			var first = true;
			if(responseText.error_no == '0') {
				alert("操作成功");
				$('.shadePro').hide();
				$('.shadeConst').hide();
				searchOnclick(1);
			} else {
				alert(responseText.error_info);
			}
		},
		error: function(xhr, type) {
			$('.update_goods').attr('disabled', false);
			alert("网络异常");
		}
	});
}

function delGoods(data, url) {
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: url,
		dataType: 'json',
		data: JSON.stringify(data),
		success: function(responseText, textStatus, jqXHR) {
			var dataHtml = '';
			var first = true;
			if(responseText.error_no == '0') {
				alert("删除成功");
				searchOnclick(1);
			} else {
				alert(responseText.error_info);
			}
		},
		error: function(xhr, type) {
			alert("网络异常");
		}
	});
}

function groundGoods(data, url) {
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url: url,
		dataType: 'json',
		data: JSON.stringify(data),
		success: function(responseText, textStatus, jqXHR) {
			var dataHtml = '';
			var first = true;
			if(responseText.error_no == '0') {
				alert("上架成功");
				searchOnclick(1);
			} else {
				alert(responseText.error_info);
			}
		},
		error: function(xhr, type) {
			alert("网络异常");
		}
	});
}

// 批量上架
function bathGroundingGoods() {
	var checkList = $("#dataGrid .cklist");
	var check_num = 0;
	var ids = [];
	for(var i = 0; i < checkList.length; i++) {
		if(checkList[i].checked) {
			check_num++;
			ids.push(checkList[i].value);
		}
	}
	if(check_num < 1) { // 未选择商品
		alert("请至少选择一种商品!");
		return;
	}
	if(confirm("确定要上架选中商品吗？")) {
		var data = {
			ids: ids,
			access_token: localStorage.access_token
		}
		// 上架前，判断是否存款库存 为空的商品
		var list = checkProductStandard(data);
		var errMsg = "";
		if(list == -1) {
			alert('超时了');
			return;
		}
		if(list.length > 0) {
			for(var i = 0; i < list.length; i++) {
				errMsg += list[i] + "\n";
			}
			alert(errMsg);
			return;
		}

		$.ajax({
			contentType: "application/json; charset=utf-8",
			type: 'POST',
			url: "/bathGroundingGoods",
			dataType: 'json',
			data: JSON.stringify(data),
			success: function(responseText, textStatus, jqXHR) {
				if(responseText.error_no == '0') {
					alert("上架成功!");
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

// 适配全部机型
function adaptionAll() {
	$('.show1Center').empty();
	$('.show1Right').empty();
	$('.show1Chose').empty();
	$('.show1Chose').html('<input type="text" name="" id="" style="width: 100%;height: 30px;border: 1px solid #fdae60;box-sizing: border-box;" placeholder="输入搜索已选机型"/>');

	model_temp = [];
	model_obj_temp = [];
	$('.car_models_form').val('适用全部机型');
}

function cancleAll() {
	$('.cancleAll').show();
}

function cancleAllNo() {
	$('.cancleAll').hide();
}