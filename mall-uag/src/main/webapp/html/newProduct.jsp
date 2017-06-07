<%@ page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>后台管理系统-新增商品</title>
		<meta name="Description" content="" />
		<meta name="Keywords" content="" />
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="renderer" content="webkit|ie-comp|ie-stand" />
		<link rel="icon" href="/images/headIco.png" type="image/x-ico" />
		<link rel="stylesheet" type="text/css" href="/css/reset.css" />
		<link rel="stylesheet" type="text/css" href="/css/style.css" />
		<link rel="stylesheet" type="text/css" href="/css/common.css" />
		<link rel="stylesheet" type="text/css" href="/css/swiper-3.3.1.min.css" />
	</head>

	<body>
		<input type=file id='hiddenFileInput' valueId='' style='display: none' multiple="multiple" onchange="uploadPicOnchange()" />
		<input type=file id='hiddenFileOneInput' valueId='' style='display: none' onchange="uploadPicOneOnchange()" />
		<div id="wrap">
			<div id="left">
				<h1></h1>
				<div class="leftNavi">
					<dl>
						<dt>会员管理</dt>
						<dd>会员管理</dd>
						<dd>邀请码管理</dd>
					</dl>
					<dl>
						<dt>类目管理</dt>
						<dd>一级类目管理</dd>
						<dd>二级类目管理</dd>
						<dd>三级类目管理</dd>
					</dl>
					<dl>
						<dt>机型管理</dt>
						<dd>新增机型</dd>
						<dd>机型管理</dd>
					</dl>
					<dl>
						<dt class="leftNaviOn">商品管理</dt>
						<dd class="leftNaviOns">新增商品</dd>
						<dd>已上架商品</dd>
						<dd>待发布商品</dd>
					</dl>
					<dl>
						<dt>订单管理</dt>
						<dd>订单管理</dd>
					</dl>
					<dl>
						<dt>基础设置</dt>
						<dd>机型品牌管理</dd>
						<dd>商品品牌管理</dd>
						<dd>元数据管理</dd>
						<dd>APP展示图片管理</dd>
					</dl>
				</div>
			</div>
			<div id="right">
				<div class="viewBg">
					<div class="viewBody">
						<div class="closeView">
							<img src="/images/close.png" />
						</div>
						<div class="viewIphone">
							<div class="viewIphoneMain">
								<div class="viewBanner">
									<div class="swiper-container banner-wrap">
										<div class="swiper-wrapper">
											<div class="swiper-slide"><img src="" class="firstBanner"></div>
											<div class="swiper-slide"><img src="" class="secondBanner"></div>
											<div class="swiper-slide"><img src="" class="thirdBanner"></div>
											<div class="swiper-slide"><img src="" class="fourthBanner"></div>
											<div class="swiper-slide"><img src="" class="fifthBanner"></div>
											<div class="swiper-slide"><img src="" class="sixBanner"></div>
										</div>
										<div class="swiper-pagination businessSwiperCount" style="width:40px;height: 40px;color: #fff;line-height: 40px;border-radius:50%;text-align: center;background: rgba(0,0,0,0.7);"></div>
									</div>
								</div>
								<div class="viewCotent">
									<h1></h1>
									<h2 class="viewAdver"><img src=""/></h2>
									<div class="viewBgColor"></div>
									<div class="shopIntroduce">
										<dl>
											<dt>商品介绍</dt>
											<dd class="viewBrand"><label>品牌</label><span></span></dd>
											<div class="viewListsDd"></div>
										</dl>
									</div>
									<div class="viewBgColor"></div>
									<div class="detailsNavi">
										<div class="detailsNaviTop">
											<div class="detailsNaviLeft">图文详情</div>
										</div>
										<ul class="detailsCon"></ul>
									</div>
									<div class="detailsNavi">
										<div class="detailsNaviTop">
											<div class="detailsNaviLeft">适用机型</div>
										</div>
										<ul class="detailsFit"></ul>
									</div>
								</div>
								<div class="viewFooter">
									<div class="viewFooterLeft"></div>
									<div class="viewFooterRight">
										<label style="line-height: 40px;">选择规格</label>
										<div class="viewFooterNav">
											<ul class="viewFooterPend">
												<h4 style="width:100%;text-indent:5px;text-align:left;border-bottom:1px solid #EC283F;"></h4>
												<div class="viewStand"></div>
											</ul>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="exit">
					<div class="exitBot">
						<img src="/images/exit.png" />
					</div>
				</div>
				<div class="rightPos">
					<a class="rightPosTit" href="javascript:;">用户中心</a> >
					<a class="rightPosCld" href="javascript:;">商品管理</a>>
					<a class="rightPosCld" href="javascript:;">新增商品</a>
				</div>
				<div class="main">
					<div class="mainTitle boxSizing borBot1">
						<h2 class="boxSizing borBot2">新增商品</h2>
					</div>
					<div class="mainLists">
						<div class="list">
							<div class="carParameter">
								<label><em>*</em> 商品名称：</label> <input type="text" maxLength="38" minLength="13" name="goods_name" id="" value="" placeholder="输入（13-38字数限制）" class="inputText2" />
							</div>
							<div class="carParameter">
								<label><em>*</em> 类目：</label> <input type="button" name="" value="点击选择" class="inputBot1 mar1 classEdit clickCategory" />
							</div>
							<div class="carParameter">
								<label><em>*</em> 品牌：</label> <input type="button" name="" value="点击选择" class="inputBot1 mar1 addBrand" hiddenValue="" />
							</div>
							<div class="carParameter">
								<label><em>*</em> 计量单位：</label> <input type="button" name="" value="点击选择" class="inputBot1 mar1 addUnit" hiddenValue="" />
							</div>
							<div class="carParameter">
								<div class="leftParameter" style="min-width: 800px;" id="ad_url_parent">
									<label>占位图：</label> 
									<input type="file" name="" id="ad_url_file" value="" placeholder="输入名称" style="display: none;" class="input_file_ad" maxlength="8" onchange="uploadAdUrl()" /> 
									<input type="text" name="" id="" value="" class="input1 ad_url_show" disabled="true" /> 
									<input type="button" name="" id="" value="上传" onclick="document.getElementById('ad_url_file').click()" class="inputBot1 mar1" /> 
									<input type="button" name="" id="del_ad_url" value="删除占位图" class="inputBot1 mar1" /> 
									<input type="text" style='display: none' name="" id="ad_url" value="" />
									<span style="max-width:200px;margin-left:10px; float: none;">建议尺寸：750*68px</span>
								</div>
								<div class="rightParameter">
									<label><em>*</em>  首页图：</label> 
									<input type="file" name="" id="show_url_file" value="" placeholder="输入名称" style="display: none;" class="input_file_show" maxlength="8" onchange="uploadShowUrl()" /> 
									<input type="text" name="" id="" value="" class="input1 show_url_show" disabled="true" /> 
									<input type="button" name="" id="" value="上传" onclick="document.getElementById('show_url_file').click()" class="inputBot1 mar1" /> 
									<input type="text" style='display: none' name="" id="show_url" value="" />
									<span style="max-width:200px;margin-left:10px; float: none;">建议尺寸：360*360px</span>
								</div>
							</div>
							<div class="carParameter">
								<label>适用机型：</label> <input type="button" name="" value="点击选择" class="inputBot1 mar1 clickChose" />
							</div>

							<div class="imgShow">
								<div class="carParameter">
									<label class="leftParameter"><em>*</em> banner图：(建议尺寸：750*600px)<em>&nbsp;&nbsp;&nbsp;&nbsp;至少一张</em></label>
								</div>
								<div class="imgShowD banner_list">
									<!-- 									<div class="bannerShow"><img src="/images/bannerShow.png"><img src="/images/delete.png" class="delete"></div> -->
									<div class="addIco" onclick="document.getElementById('hiddenFileInput').valueId = 'banner_list';document.getElementById('hiddenFileInput').click()">
										<img src="/images/addIco.png">
									</div>
								</div>
							</div>
							<div class="imgShow">
								<div class="carParameter">
									<label class="leftParameter"><em>*</em> 商品详图：<em>&nbsp;&nbsp;&nbsp;&nbsp;至少一张</em></label>
								</div>
								<div class="imgShowD detail_list">
									<input type="file" name="" id="path" value="" style="display: none;" class="inputFile addImg" maxlength="18" />
									<!-- 									<div class="bannerShow"><img src="/images/bannerShow.png"><img src="/images/delete.png" class="delete"></div> -->
									<div class="addIco" onclick="document.getElementById('hiddenFileInput').valueId = 'detail_list';document.getElementById('hiddenFileInput').click()">
										<img src="/images/addIco.png">
									</div>
								</div>
							</div>
							<div class="carParameter">
								<label><em>*</em> 客服电话：</label> <input type="text" maxLength="20" id="" value="" placeholder="输入（20字数限制）" class="inputText2 tel" />
							</div>
							<p>
								商品介绍<img src="/images/down.png">
							</p>
							<div class="goodsParameter info_list">
								<div class="leftParameter">
									<label><em>*</em> 名称：</label>
									<input type="text" maxLength="5" minLength="1" name="" id="" value="" placeholder="输入（1-5字数限制）" class="inputText2 mar2" maxLength="5" />
								</div>
								<div class="rightParameter">
									<label class="rightLabel"> 值：</label>
									<textarea maxLength="200" name="" id="" value="" placeholder="输入（1-200字数限制）" rows="4"></textarea>
									<input type="button" name="" id="" value="删除" class="inputBot3 mar2" />
								</div>
							</div>
							<div class="addParameter">+ 添加</div>
							<p>
								商品规格<img src="/images/down.png">
							</p>
							<div class="carParameter stand_list">
								<label class="label1"><em>*</em> 名称：</label> 
								<input type="text" name="" id="" value="" placeholder="这个规格是必填的" class="inputText1 floatL marT1 marR1" /> 
								<input type="text" name="" id="" value="" placeholder="三个规格40字数限制" class="inputText1 floatL marT1 marR1" /> 
								<input type="text" name="" id="" value="" placeholder="三个规格40字数限制" class="inputText1 floatL marT1 marR1" /> 
								<label class="label1"><em>*</em>价格：</label> 
								<input type="text" name="" id="" title="输入（百万及以下的价格，小数点后保留两位）" placeholder="输入（1-10个字符）" value=""  class="inputText1 floatL marT1 marR1 amount" />
								<label class="label1"><em>*</em> 库存：</label> 
								<input type="text" maxLength="8" minLength="1"  name="" id="" value="" placeholder="输入（1-8字数限制）" class="inputText1 floatL marT1 marR1 storeNum" />
								<input type="button" name="" id="" value="删除" class="inputBot3 carParameterD " />
							</div>
							<div class="addStandard">+ 添加</div>
						</div>
						<div class="addCarType">
							<input type="button" name="" id="" value="预 览" class="inputBot4 addView" /> <input type="button" name="" id="" value="待发布" class="inputBot1 adUnderdGoods marL1" /> <input type="button" name="" id="" value="上  架" class="inputBot2 marL1 addGoods" />
						</div>
					</div>
					<div class="shade show1 modeladd">
						<div class="shadeMain show1">
							<h4 class="borBot2 cor2">
							请选择机型
						</h4>
							<div class="shadeThree">
								<div class="shadeThreeLeft show1Left categorylistInput" style="position:relative;">
									<div style="width:100%;height:1000%;background:rgba(0,0,0,.6);z-index:999;position:absolute;left:0;top:0;text-align:center;padding-top:20px;color:#fff;display:none" class="cancleAll">请取消适用全部机型</div>
									<ul class="car_parent_list">
										<li><span class="first">100</span>
											<dl>
											</dl>
											<dl>
											</dl>
										</li>
										<li><span class="first">200</span>
											<dl>
											</dl>
										</li>
										<li><span class="first">300</span>
											<dl>
											</dl>
										</li>
									</ul>
								</div>
								<div class="shadeThreeCenter show1Center categorylistInput"></div>
								<div class="shadeThreeRight show1Right categorylistInput"></div>
								<div class="shadeThreeChose show1Chose categorylistInput cateViewNext"></div>
							</div>
							<div class="addCarType" style="margin-top: 20px; clear: both;">
								<label style="float: left;margin-left: 70px;"><input type="checkbox" class="addCheck" id="all_models_chk"/><span>适用全部机型</span></label>
								<input type="button" name="" id="" value="确 定" class="inputBot2 addCarConfirm" /> <input type="button" name="" id="" value="取 消" class="inputBot2 marL1 addCarCancle" />
							</div>
						</div>
					</div>

					<div class="shade show2  categoryadd">
						<div class="shadeMain show2">
							<h4 class="borBot2 cor2">
							请选择类目
						</h4>
							<div class="shadeThree">
								<div class="shadeThreeLeft show2Left categorylistInput">
								<input type="text" name="" id="" style="width: 100%;height: 30px;border: 1px solid #fdae60;box-sizing: border-box;" placeholder="输入搜索一级类目"/>
									<ul class="category_parent_list">
									</ul>
								</div>
								<div class="shadeThreeCenter show2Center categorylistInput"></div>
								<div class="shadeThreeRight show2Right categorylistInput"></div>
								<div class="shadeThreeChose show2Chose categorylistInput"></div>
							</div>
							<div class="addCarType" style="margin-top: 20px; clear: both;">
								<input type="button" name="" id="" value="确 定" class="inputBot2 addCarConfirm" /> <input type="button" name="" id="" value="取 消" class="inputBot2 marL1 addCarCancle" />
							</div>
						</div>
					</div>

					<div class="shade add_brand_show3">
						<div class="shadeCon add_brand_show3">
							<div class="shadeMain1">
								<h4 class="borBot2 cor2 marB0 brandOrUnit_text">
								请选择<span>品牌</span> <input type="botton" value="搜 索"
									class="shadeBot marR1" id="reloadBrand"
									style="float: right; border: none; margin: 10px 10px 0 0" /> <input
									type="text" name="" id="name_search" value=""
									placeholder="请输入三级类目关键词,输入匹配" class="inputText4 marR1" />
							</h4>
								<div class="clearfloat" id="">
									<ul class="s-pu s-1-select" id="gg_type1">
									</ul>
									<ul class="selected1" id="select_brand_or_unit">
									</ul>
								</div>
								<div class="shadeBotPos">
									<span class="shadeBot marR1 inviteKeep">保存</span> <span class="shadeBot inviteCancle">取消</span>
								</div>

							</div>
						</div>
					</div>

					<div class="shade" id="normalShade"></div>
					<div class="shadePro">
						<div class="shadeConst">
							<div class="shadeMain">
								<h4 class="borBot2 cor2">查看图片</h4>
								<div class="showImage"></div>
								<div class="shadeBotPos">
									<span class="shadeBot inviteCancle">关闭</span>
								</div>
							</div>
						</div>
					</div>
	</body>
	<script src="/js/jquery-1.12.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/main.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/goods/newProduct.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/hjyCmpress.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/swiper-3.3.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/goods/goodsCommon.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		swiper = new Swiper('.swiper-container', {
				pagination: '.swiper-pagination',
				paginationClickable: true,
				autoplayDisableOnInteraction: false,
				grabCursor: true,
				observer: false,
				observerParents: false,
				paginationType: 'custom',
				paginationCustomRender: function(swiper, current, total) {
					return current + '/' + total;
				},
			});
	</script>
	<script type="text/javascript">
		function showClass(obj) {
			$(obj).show();
		}

		function closeClass(obj) {
			$(obj).hide();
		}
	</script>
	<script type="text/javascript">
		var category_temp = {};
		var category_persist = {};
		var model_temp = new Array();
		var model_persist = new Array();
		var model_obj_temp = new Array();
		var model_obj_persist = new Array();

		//类目搜查事件
		$('.categoryadd .search_btn').on('click', function() {
			var _search_text = $(".categoryadd .inputText1").val();
			var _category = $(this).next().find("option:selected").val();
			if(_category == 'shadeThreeLeft') {
				var firstcategorylist = $('.' + _category + ' li');
				for(var i = 0; i < firstcategorylist.length; i++) {
					var li = $(firstcategorylist[i]);

					if(li.find('span').text().indexOf(_search_text) < 0) {
						li.find('span').removeClass('addColor');
						li.hide();
					} else {
						li.show();
					}
				}
			} else if(_category == 'shadeThreeCenter') {
				var firstcategorylist = $('.' + _category + ' dt');
				for(var i = 0; i < firstcategorylist.length; i++) {
					var li = $(firstcategorylist[i]);

					if(li.text().indexOf(_search_text) < 0) {
						li.removeClass('addColor');
						li.hide();
					} else {
						li.show();
					}
				}
			} else if(_category == 'shadeThreeRight') {
				var firstcategorylist = $('.' + _category + ' dd');
				for(var i = 0; i < firstcategorylist.length; i++) {
					var li = $(firstcategorylist[i]);

					if(li.text().indexOf(_search_text) < 0) {
						li.removeClass('addColor');
						li.hide();
					} else {
						li.show();
					}
				}
			}
		});
		//机型搜查事件
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

		$(document).ready(

			function() {

				// 类目
				$('.clickCategory').on(
					'click',
					function() {

						$('.show2').show();

						$('.category_parent_list').empty();
						$('.shadeThreeCenter').empty();
						$('.shadeThreeRight').empty();
						$('.show2Chose').empty();

						if(category_temp != null && category_temp['third_category_id'] != null) {
							$('.show2Chose').append(
								'<dd car_model_id="' + category_temp['third_category_id'] + '" class="addColor">' +
								category_temp['third_category_name'] + '</dd>');
						}

						var data = {
							status: "1",
							limit: 100000,
						};
						listFirstCategory(data);

						$('.show2Left').on(
							'click',
							'li span',
							function() {
								$('.shadeThreeRight').empty();
								var data = {
									fatherId: $(this).attr("first_category_id"),
									limit: 100000000,
									status: 1
								};
								$('.show2Right').empty();
								$('.show2Center').empty();
								listSecondCategory(data);
								category_temp["first_category_id"] = $(this).attr("first_category_id");
								category_temp["first_category_name"] = $(this).text();

								$(this).addClass('addColor').parent().siblings().children('span').removeClass(
									'addColor');
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

						$('.show2Right').on('click', 'dd', function() {
							var id = $(this).attr("third_category_id");
							var tex = $(this).text();
							$(this).parent().children('dd').removeClass('addColor');
							$(this).addClass('addColor');
							$('.show2Chose').empty();
							$('.show2Chose').append('<dd car_model_id="' + id + '" class="addColor">' + tex + '</dd>');

							category_temp["third_category_id"] = $(this).attr("third_category_id");
							category_temp["third_category_name"] = $(this).text();
						});

						//确定
						$('.addCarConfirm').on('click', function() {
							$('.show2').hide();
							if(category_temp != null && category_temp['third_category_id'] != null) {
								$('.clickCategory').val(category_temp["third_category_name"]);
								category_persist["first_category_id"] = category_temp["first_category_id"];
								category_persist["first_category_name"] = category_temp["first_category_name"];
								category_persist["second_category_id"] = category_temp["second_category_id"];
								category_persist["second_category_name"] = category_temp["second_category_name"];
								category_persist["third_category_id"] = category_temp["third_category_id"];
								category_persist["third_category_name"] = category_temp["third_category_name"];
							} else {
								category_persist = {};
								$('.clickCategory').val('点击选择');
							}

						});
						$('.addCarCancle').on('click', function() {
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

				if($('.clickChose').val() == '适用全部机型') {
					$('#all_models_chk').attr('checked', true);
					adaptionAll();
				}
				$('#all_models_chk').on('click', function() {
					if($(this).is(':checked')) {
						adaptionAll();
						cancleAll();
					} else {
						cancleAllNo();
					}
				})
				$('.clickChose').on('click',function() {
						var _adapt_all_models = $('.clickChose').attr("adapt_all_models");
						if(_adapt_all_models == '1' || _adapt_all_models == 1) {
							$('#all_models_chk').prop('checked', true);
							adaptionAll();
							cancleAll();
						} else{
							$('#all_models_chk').prop('checked', false);
							cancleAllNo();
						}

						if($('#all_models_chk').is(':checked')) {
							$('.show1Center').empty();
							$('.show1Right').empty();
							$('.show1Chose').empty();

							model_temp = [];
							model_obj_temp = [];
							$('.clickChose').val('适用全部机型');
						}

						$('.show1').show();

						$('.car_parent_list').empty();
						$('.show1Center').empty();
						$('.show1Right').empty();

						$('.show1Chose').empty();
						$('.show1Chose').append('<input type="text" name="" id="" style="width: 100%;height: 30px;border: 1px solid #fdae60;box-sizing: border-box;" placeholder="输入搜索已选机型"/>')
						if(model_obj_temp != null && model_obj_temp.length > 0) {

							model_obj_temp.forEach(function(item) {
								$('.show1Chose').append(
									'<dd brand_name="'+ item.brand_name +'" metadata_name="'+ item.metadata_name+'" id="' + item.car_models_id + '" car_model_id="' + item.car_models_id + '" class="addColor">' +
									item.car_models_name + '</dd>');
							});
						}
						listCarBrand();

						//确定
						$('.addCarConfirm').on('click', function() {
							$('.show1').hide();

							if(model_obj_temp != null && model_obj_temp.length > 0) {
								model_persist = model_temp.slice(0);
								model_obj_persist = model_obj_temp.slice(0);
								var show_text = model_obj_persist[0]['car_models_name'];
								if(model_obj_persist.length > 1) {
									show_text += "等";
								}
								$('.clickChose').val(show_text);
							} else {
								model_persist = [];
								model_obj_persist = [];
								$('.clickChose').val('点击选择');
							}
							$('.clickChose').attr('adapt_all_models', $('#all_models_chk').is(':checked')?1:0);
							if($('#all_models_chk').is(':checked')) {
								adaptionAll();
							}

						});
						$('.addCarCancle').on('click', function() {
							$('.show1').hide();
							model_temp = model_persist.slice(0);
							model_obj_temp = model_obj_persist.slice(0);
							var _adapt_all_models = $('.clickChose').attr("adapt_all_models");
							if(model_obj_temp != null && model_obj_temp.length > 0) {
								var show_text = model_obj_persist[0]['car_models_name'];
								if(model_obj_persist.length > 1) {
									show_text += "等";
								}
								$('.clickChose').val(show_text);
							} else if(_adapt_all_models== "1" || _adapt_all_models == 1 ) {
								$('.clickChose').val('适用全部机型');
							}else{
								$('.clickChose').val('点击选择');
							}
							
							if(_adapt_all_models == '1' || _adapt_all_models == 1) {
								$('#all_models_chk').prop('checked', true);
							}else{
								$('#all_models_chk').prop('checked', false);
							}
							
						})
						$('.show1Left').on('click','li span',function() {
								var data = {
									brand_id: $(this).attr("brand_id"),
									brand_name:$(this).text(),
									limit: 100000000,
									status: 1
								};
								listCarType(data);

								model_temp["brand_id"] = $(this).attr("brand_id");
								model_temp["brand_name"] = $(this).text();
								$(this).addClass('addColor').parent().siblings().children('span').removeClass('addColor');
								$('.show1Right').empty();
							});

						$('.show1Center').on('click', 'dt', function() {
							var data = {
								brand_id: $(this).attr("brand_id"),
								brand_name: $(this).attr("brand_name"),
								metadata_id: $(this).attr("metadata_id"),
								metadata_name: $(this).text(),
								status: 1,
								limit: 100000
							};

							listCarModel(data);

							$(this).addClass('addColor').siblings().removeClass('addColor');
						})

					});
				// 机型 
				$('.show1Right').on('click','dd',function() {
						var car_models_id = $(this).attr("id");
						$(this).attr('id', car_models_id);
						var tex = $(this).text();
						var brand_name=$(this).attr("brand_name");
						var metadata_name=$(this).attr('metadata_name');
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
							model_temp.pop(car_models_id);
							var obj = {
								'car_models_id': car_models_id,
								'car_models_name': tex,
								'brand_name':brand_name,
								'metadata_name':metadata_name
							}
							model_obj_temp.pop(obj);

						} else {
							$('.show1Chose').append(
								'<dd brand_name="'+brand_name+'" metadata_name="'+metadata_name+'" id="' + car_models_id + '" car_model_id="' + car_models_id + '" class="addColor">' +
								tex + '</dd>');
							model_temp.push(car_models_id);
							var obj = {
								'car_models_id': car_models_id,
								'car_models_name': tex,
								'brand_name':brand_name,
								'metadata_name':metadata_name,
							}
							model_obj_temp.push(obj);
						}
						$('.show1Chose').on('click', '#' + car_models_id, function() {
							$(this).remove();
							$('.show1Right').find('#' + car_models_id).removeClass('addColor');

							model_temp.pop(car_models_id);
							var obj = {
								'car_models_id': car_models_id,
								'car_models_name': tex,
								'brand_name':brand_name,
								'metadata_name':metadata_name
							}
							model_obj_temp.pop(obj);

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
			})

		function listCarBrand() {
			$.ajax({
				contentType: "application/json; charset=utf-8",
				type: 'POST',
				url: "/carbrand/queryforweb",
				dataType: 'json',
				data: JSON.stringify({
					status: 1,
					limit: 100000000
				}),
				success: function(responseText, textStatus, jqXHR) {
					var resultList = responseText.result_list;
					var dataHtml = '';
					var first = true;
					if(responseText.error_no == '0') {
						$('.car_parent_list').html('<input type="text" name="" id="" style="width: 100%;height: 30px;border: 1px solid #fdae60;box-sizing: border-box;" placeholder="输入搜索机型品牌"/>');
						resultList.forEach(function(item) {
							dataHtml += '<li><span class="first" brand_id=' + item.brand_id + '>' + item.brand_name +
								'</span></li>';
						});
					}
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
							dataHtml += '<dt brand_name='+data.brand_name+' brand_id=' + data.brand_id + ' metadata_id=' + item.metadata_id + '>' + item.car_type +
								'</dt>';
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
								dataHtml += '<dd class="addColor" brand_name='+data.brand_name+' metadata_name='+data.metadata_name+'  id=' + item.car_model_is + '>' + item.car_model_name +
									'</dd>';
							} else {
								dataHtml += '<dd brand_name='+data.brand_name+' metadata_name='+data.metadata_name+' id=' + item.car_model_is + '>' + item.car_model_name + '</dd>';
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
						resultList.forEach(function(item) {
							$('.category_parent_list').empty();
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
					$('.show2Center').empty();
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
					$('.show2Center').append('<input type="text" name="" id="" style="width: 100%;height: 30px;border: 1px solid #fdae60;box-sizing: border-box;" placeholder="输入搜索二级类目"/>');
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
					$('.show2Right').append('<input type="text" name="" id="" style="width: 100%;height: 30px;border: 1px solid #fdae60;box-sizing: border-box;" placeholder="输入搜索三级类目"/>');
					$('.show2Right').append(dataHtml);

				},
				error: function(xhr, type) {
					alert("网络异常");
				}
			});
		}

		// 适配全部机型 
		function adaptionAll() {
			$('.show1Center').empty();
			$('.show1Right').empty();
			$('.show1Chose').empty();
			$('.show1Chose').append('<input type="text" name="" id="" style="width: 100%;height: 30px;border: 1px solid #fdae60;box-sizing: border-box;" placeholder="输入搜索已选机型"/>')
			

			model_temp = [];
			model_obj_temp = [];
			$('.clickChose').val('适用全部机型');
			
		}

		function cancleAll() {
			$('.cancleAll').show();
		}

		function cancleAllNo() {
			$('.cancleAll').hide();
		}
	</script>
	<script>
		$(".leftNavi dl dd").on('click', function() {
			var goods_name = $('.list input[name="goods_name"]').val().trim();
			var clickCategory = $(".clickCategory").val().trim();
			var addBrand = $('.addBrand').val().trim();
			var addUnit = $('.addUnit').val().trim();
			var ad_url = $('#ad_url').val();
			var show_url = $('#show_url').val();
			var clickChose = $('.clickChose').val().trim();
			var tel = $('.tel').val().trim();
			var detailHtml = $(".detail_list div");
			var detailList = new Array();
			for(var i = 0; i < detailHtml.length; i++) {
				var obj = {};
				if($(".detail_list div:eq(" + i + ") img").parent().prop("className") != "addIco") {

					obj = {
						pic_url: $(".detail_list div:eq(" + i + ")").prev().attr('base64'),
						pic_desc: $('.imgdetails').get(i).value
					};
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
				if(info_title.value != '' || info_content.value != '') {
					var obj = {
						info_title: info_title.value,
						info_content: info_content.value
					};
					infoList.push(obj);
				}

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
				if(standard_must.value != "" || optional_first.value != "" || optional_second.value != "" || price.value != "" || store_num.value != "") {
					var obj = {
						standard_must: standard_must.value,
						optional_first: optional_first.value,
						optional_second: optional_second.value,
						price: price.value,
						store_num: store_num.value
					};
					standardList.push(obj);
				}
			}

			if(goods_name.length > 0 || clickCategory != "点击选择" || addBrand != "点击选择" || clickCategory != "点击选择" || ad_url.length > 0 || show_url.length > 0 || clickChose != "点击选择" || tel.length > 0 || detailList.length > 0 || bannerList.length > 0 || infoList.length > 0 || standardList.length > 0) {
				if(!confirm('您要离开本页面吗？')) {
					return
				} else {
					if($(this).text() == "会员管理") {
						location.href = 'memberManagement.html';
					} else if($(this).text() == "邀请码管理") {
						location.href = 'inviteCode.html';
					} else if($(this).text() == "一级类目管理") {
						location.href = 'categoryFirst.html';
					} else if($(this).text() == "二级类目管理") {
						location.href = 'categorySecond.html';
					} else if($(this).text() == "三级类目管理") {
						location.href = 'categoryThird.html';
					} else if($(this).text() == "机型管理") {
						location.href = 'carManagement.html';
					} else if($(this).text() == "新增机型") {
						location.href = 'newCarType.html';
					} else if($(this).text() == "已上架商品") {
						location.href = 'groundingProduct.html';
					} else if($(this).text() == "待发布商品") {
						location.href = 'prepareProduct.html';
					} else if($(this).text() == "新增商品") {
						return
					} else if($(this).text() == "订单管理") {
						location.href = 'orderManagement.html';
					} else if($(this).text() == "机型品牌管理") {
						location.href = 'carBrandSetting.html';
					} else if($(this).text() == "商品品牌管理") {
						location.href = 'productBrandSetting.html';
					} else if($(this).text() == "元数据管理") {
						location.href = 'metadataSetting.html';
					} else if($(this).text() == "APP展示图片管理") {
						location.href = 'appImgSetting.html';
					}
				}
			} else {
				if($(this).text() == "会员管理") {
					location.href = 'memberManagement.html';
				} else if($(this).text() == "邀请码管理") {
					location.href = 'inviteCode.html';
				} else if($(this).text() == "一级类目管理") {
					location.href = 'categoryFirst.html';
				} else if($(this).text() == "二级类目管理") {
					location.href = 'categorySecond.html';
				} else if($(this).text() == "三级类目管理") {
					location.href = 'categoryThird.html';
				} else if($(this).text() == "机型管理") {
					location.href = 'carManagement.html';
				} else if($(this).text() == "新增机型") {
					location.href = 'newCarType.html';
				} else if($(this).text() == "新增商品") {
					return
				} else if($(this).text() == "已上架商品") {
					location.href = 'groundingProduct.html';
				} else if($(this).text() == "待发布商品") {
					location.href = 'prepareProduct.html';
				} else if($(this).text() == "订单管理") {
					location.href = 'orderManagement.html';
				} else if($(this).text() == "机型品牌管理") {
					location.href = 'carBrandSetting.html';
				} else if($(this).text() == "商品品牌管理") {
					location.href = 'productBrandSetting.html';
				} else if($(this).text() == "元数据管理") {
					location.href = 'metadataSetting.html';
				} else if($(this).text() == "APP展示图片管理") {
					location.href = 'appImgSetting.html';
				}
			}
		})
	</script>

</html>