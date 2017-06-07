<%@ page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>后台管理系统-待发布商品</title>
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
		<input type=file id='hiddenFileOneInput' valueId='' style='display: none' onchange="uploadPicOneOnchange()" />
		<input type=file id='hiddenFileInput' valueId='' style='display:none' multiple="multiple" onchange="uploadPicOnchange()" />
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
						<dd>新增商品</dd>
						<dd>已上架商品</dd>
						<dd class="leftNaviOns">待发布商品</dd>
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
								<div class="viewCotent"></div>
								<div class="viewFooter">
									<div class="viewFooterLeft"></div>
									<div class="viewFooterRight">
										<label style="line-height: 40px;">选择规格</label>
										<div class="viewFooterNav">
											<ul class="viewFooterPend">
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
					<a class="rightPosCld" href="javascript:;">待发布商品</a>
				</div>
				<div class="main">
					<div class="mainTitle boxSizing borBot1">
						<h2 class="boxSizing borBot2">待发布商品</h2>
					</div>
					<div class="mainOperation">
						<div class="mainOperationInput">
							<span><input type="text" name="" id="" value="" placeholder="请输入商品编号" class="inputText1 inputTex marR1 goods_id"/></span>
							<span><input type="text" name="" id="" value="" placeholder="请输入商品标题" class="inputText1 inputTex marR1 goods_name"/></span>
							<span>
								<input type="text" name="" id="" value="" placeholder="请输入品牌" class="inputText1 inputTex marR1 brand_name pattern_productbrand"/>
								<div id="pattern_productbrand" class="pattern_name"></div>
							</span>
							<span>
								<input type="text" name="" id="" value="" placeholder="请输入类目" class="inputText1 inputTex marR1 third_category_name"/>
								<div id="third_category_pattern_name" class="pattern_name"></div>
							</span>
							<span>
								<input type="text" name="" id="" value="" placeholder="请输入创建人" class="inputText1 inputTex marR1 create_user_name"/>
								<div id="pattern_name" class="pattern_name"></div>
							</span>
							<span>
								<input class="datainp inputTex inputText6" id="dateFrom" type="text" placeholder="创建开始时间" value=""  readonly>
								到
								<input class="datainp inputTex inputText6 marR1" id="dateTo" type="text" placeholder="创建结束时间" value=""  readonly>
							</span>
							<span><input type="button" name="" id="" value="搜 索" class="inputBot1 marL1 searchLists"  onclick="searchOnclick(0)"/></span>
							<span>
								<input type="button" name="" id="" value="清 除" class="inputBot1 marL1 clearInput"/>
								<input type="hidden" name="page_now" id="page_now" value="1"/>
                    			<input type="hidden" name="page_limit" id="page_limit" value="15"/>
							</span>
						</div>
					</div>
					<div class="add">
						<input type="button" name="" id="" value="批量上架" class="inputBot2" onclick="bathGroundingGoods()" />
						<span class="resultNum" id="total_num">共搜索到<label>0</label>条数据</span>
					</div>
					<div class="mainLists">
						<div class="list">
							<table border="1" cellspacing="" cellpadding="" id="dataGrid">

							</table>
						</div>
						<div class="page">

						</div>
					</div>
					<div class="shadePro">
						<div class="shadeConst">
							<div class="shadeMain edit_goods">
								<h4 class="borBot2 cor2">编辑商品</h4>
								<ul class="addInvite">
									<li class="bor1">
										<label><span>*</span>名称</label>
										<input type="text" name="" id="" value="" placeholder="字数限制13-38 " class="inviteNum goods_name_form" maxlength="38" minlength="13" />
									</li>
									<li class="bor1">
										<label><span>*</span>类目</label>
										<input type="button" name="" id="" value="滤芯" class="inputBot1 marginLeft1 categoryEdit category_name_form" />
									</li>
									<li class="bor1">
										<label><span>*</span>品牌</label>
										<input type="button" name="" id="" value="三一" class="inputBot1 marginLeft1 brand_name_form" />
									</li>
									<li class="bor1">
										<label><span>*</span>计量单位</label>
										<input type="button" name="" id="" value="个" class="inputBot1 marginLeft1 addBot unit_name_form" />
									</li>
									<li class="bor1">
										<label><span>*</span>占位图</label>
										<!-- 										<img class="zhanwei ad_url_form" src="/images/zhanwei.png"> -->
										<input type="file" name="" id="ad_url_file" value="" placeholder="输入名称" style="display: none;" class="input_file_ad" maxlength="8" onchange="uploadAdUrl()" /> <input type="text" name="" id="" value="" class="input1 ad_url_show" disabled="true" /> <input type="button" name="" id="" value="上传" onclick="document.getElementById('ad_url_file').click()" class="inputBot1 mar1" /> <input type="button" name="" id="del_ad_url" value="删除占位图" class="inputBot1 mar1" /> <input type="text" style='display: none' name="" id="ad_url" value="" />
									</li>
									<li class="bor1">
										<label><span>*</span>首页图</label>
										<!-- 										<img class="zhanwei show_url_form" src="/images/zhanwei.png"> -->
										<input type="file" name="" id="show_url_file" value="" placeholder="输入名称" style="display: none;" class="input_file_show" maxlength="8" onchange="uploadShowUrl()" /> <input type="text" name="" id="" value="" class="input1 show_url_show" disabled="true" /> <input type="button" name="" id="" value="上传" onclick="document.getElementById('show_url_file').click()" class="inputBot1 mar1" /> <input type="text" style='display: none' name="" id="show_url" value="" />
									</li>
									<li class="bor1">
										<label>适用机型</label>
										<input type="button" name="" id="" value="滤芯" class="inputBot1 marginLeft1 categoryEdit car_models_form" />
									</li>
									<li class="bor1 parameter">banner图<img src="/images/arrowDown.png"></li>
									<li class="bor1">
										<div class="imgShowD banner_form">
											<div class="bannerShow"><img src="/images/bannerShow.png"><img src="/images/delete.png" class="delete"></div>
											<div class="addIco"><img src="/images/addIco.png"></div>
										</div>
									</li>
									<li class="bor1 parameter">商品详图<img src="/images/arrowDown.png"></li>
									<li class="bor1">
										<div class="imgShowD detail_form">
											<div class="bannerShow"><img src="/images/bannerShow.png"><img src="/images/delete.png" class="delete"></div>
											<div class="addIco"><img src="/images/addIco.png"></div>
										</div>
									</li>
									<li class="bor1">
										<label><span>*</span>客服电话</label>
										<input type="text" name="" id="" value="" placeholder="进口沃尔沃空气滤芯 " class="inviteNum tel_form" maxlength="20" />
									</li>
									<li class="bor1 parameter">商品参数<img src="/images/arrowDown.png"></li>
									<li class="bor1 info_list_form parameter">
										<label><span>*</span>名称</label>
										<input type="text" name="" id="" value="名称" placeholder="（2-10字符限制）" class="inviteNum" maxlength="5" />
										<label style="text-align: right;padding-right: 10px;"><span>*</span>值</label>
										<input type="text" name="" id="" value="1000W" placeholder="（2-10字符限制）" class="inviteNum" />
										<input type="button" name="" id="" value="删除" class="inputBot3 deletePa delete_info" />
									</li>
									<li class="addP add_info">+ 添加</li>
									<li class="bor1 parameter">商品规格<img src="/images/arrowDown.png"></li>
									<li class="bor1 standard_list_form">
										<label class="paraLabel"><span>*</span>名称</label>
										<input type="text" name="" id="" value="功率" placeholder="（2-10字符限制）" class="inviteNum2" maxlength="8" />
										<input type="text" name="" id="" value="功率" placeholder="（2-10字符限制）" class="inviteNum2" maxlength="8" />
										<input type="text" name="" id="" value="功率" placeholder="（2-10字符限制）" class="inviteNum2" maxlength="8" />
										<label class="paraLabel " style="text-align: right;padding-right: 10px; "><span>*</span>价格</label>
										<input type="text " name=" " id=" " value="1000W " placeholder="（2-10字符限制） " class="inviteNum2 " />
										<label class="paraLabel " style="text-align: right;padding-right: 10px; "><span>*</span>库存</label>
										<input type="text " name=" " id=" " value="1000W " placeholder="（1-8字符限制） " class="inviteNum2 " />
										<input type="button" name="" id="" value="删除" class="inputBot3 deletePara delete_standard" />
									</li>
									<li class="addPP add_standard">+ 添加参数</li>
								</ul>
								<div class="shadeBotPos ">
									<span class="shadeBot marR1 inviteKeep update_goods">保存</span>
									<span class="shadeBot inviteCancle ">取消</span>
								</div>
							</div>
						</div>
					</div>

					<div class="shade show1 modeladd">
						<div class="shadeMain show1">
							<h4 class="borBot2 cor2">
							请选择机型
						</h4>
							<div class="shadeThree">
								<div class="shadeThreeLeft show1Left categorylistInput" style="position:relative">
									<div style="width:100%;height:1000%;background:rgba(0,0,0,.6);z-index:999;position:absolute;left:0;top:0;text-align:center;padding-top:20px;color:#fff;display:none" class="cancleAll">请取消适用全部机型</div>
									<ul class="car_parent_list">
										<li><span class="first">100</span>
										</li>
										<li><span class="first">200</span>
										</li>
										<li><span class="first">300</span>
										</li>
									</ul>
								</div>
								<div class="shadeThreeCenter show1Center categorylistInput"></div>
								<div class="shadeThreeRight show1Right categorylistInput"></div>
								<div class="shadeThreeChose show1Chose categorylistInput"></div>
							</div>
							<div class="addCarType" style="margin-top: 20px; clear: both;">
								<label style="float: left;margin-left: 70px;"><input type="checkbox" class="addCheck" id="all_models_chk"/><span>适用全部机型</span></label>
								<input type="button" name="" id="" value="确 定" class="inputBot2 addCarConfirm" /> <input type="button" name="" id="" value="取 消" class="inputBot2 marL1 addCarCancle" />
							</div>
						</div>
					</div>

					<div class="shade show2 categoryadd">
						<div class="shadeMain show2">
							<h4 class="borBot2 cor2">
							请选择类目
						</h4>
							<div class="shadeThree">
								<div class="shadeThreeLeft show2Left categorylistInput">
									<ul class="category_parent_list">
										<li><span class="first">100</span>
										</li>
										<li><span class="first">200</span>
										</li>
										<li><span class="first">300</span>
										</li>
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
										<li class="s-pu_active"><span class="cho-name graycol">易损件</span>
											<div style="float: right">
												<input type="checkbox" name="checkbox_1" class="chk_2" id="check0" value="011001"> <label for="check0" class="label001 checkMT_o"></label>
											</div>
										</li>
										<li><span class="cho-name graycol">发动机</span>
											<div style="float: right">
												<input type="checkbox" name="checkbox_1" class="chk_2" id="check5" value="011002"> <label for="check5" class="label001 checkMT_o"></label>
											</div>
										</li>
										<li><span class="cho-name graycol">液压件</span>
											<div style="float: right">
												<input type="checkbox" name="checkbox_1" class="chk_2" id="check6" value="011003"> <label for="check6" class="label001 checkMT_o"></label>
											</div>
										</li>
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
					<div class="shadeShow show3">
						<div class="shadeM abcdefg">
						</div>
					</div>
					<div class="shade " id="normalShade"></div>
					<div class="shadeImg">
						<div class="shadeWord">
							<div class="shadeMain">
								<h4 class="borBot2 cor2">查看图片</h4>
								<div class="showImage"></div>
								<div class="shadeBotPos">
									<span class="shadeBot inviteCancle" id="closeImg">关闭</span>
								</div>
							</div>
						</div>
					</div>
	</body>
	<script src="/plugin/jedate/jedate.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		jeDate({
			dateCell: "#dateFrom",
			format: "YYYY年MM月DD日 hh:mm:ss",
			isinitVal: false,
			isTime: true,
		});
		jeDate({
			dateCell: "#dateTo",
			format: "YYYY年MM月DD日 hh:mm:ss",
			isinitVal: false,
			isTime: true,
		});

		function getNowFormatDate() {
			var date = new Date();
			var seperator1 = "-";
			var seperator2 = ":";
			var month = date.getMonth() + 1;
			var strDate = date.getDate();
			if(month >= 1 && month <= 9) {
				month = "0" + month;
			}
			if(strDate >= 0 && strDate <= 9) {
				strDate = "0" + strDate;
			}
			var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate +
				" " + date.getHours() + seperator2 + date.getMinutes() +
				seperator2 + date.getSeconds();
			return currentdate;
		};
	</script>
	<script src="/js/jquery-1.12.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/main.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/common/goodsBrandData.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/common/common.js" type="text/javascript " charset="utf-8"></script>
	<script src="/js/goods/prepareProduct.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/hjyCmpress.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/swiper-3.3.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/goods/goodsCommon.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		var swiper = new Swiper('.swiper-container', {
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
	<script>
		$(".leftNavi dl dd").on('click', function() {
			if($(".shadePro").is(":visible")) {
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
					} else if($(this).text() == "新增商品") {
						location.href = 'newProduct.html';
					} else if($(this).text() == "待发布商品") {
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
				} else if($(this).text() == "已上架商品") {
					location.href = 'groundingProduct.html';
				} else if($(this).text() == "新增商品") {
					location.href = 'newProduct.html';
				} else if($(this).text() == "待发布商品") {
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

		})
	</script>

</html>