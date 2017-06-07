<%@ page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>后台管理系统-新增机型</title>
		<meta name="Description" content="" />
		<meta name="Keywords" content="" />
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<meta name="renderer" content="webkit|ie-comp|ie-stand" />
		<link rel="icon" href="/images/headIco.png" type="image/x-ico" />
		<link rel="stylesheet" type="text/css" href="/css/reset.css" />
		<link rel="stylesheet" type="text/css" href="/css/style.css" />
		<link rel="stylesheet" type="text/css" href="/css/common.css" />
	</head>

	<body>
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
						<dt class="leftNaviOn">机型管理</dt>
						<dd class="leftNaviOns">新增机型</dd>
						<dd>机型管理</dd>
					</dl>
					<dl>
						<dt>商品管理</dt>
						<dd>新增商品</dd>
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
				<div class="exit">
					<div class="exitBot">
						<img src="/images/exit.png" />
					</div>
				</div>
				<div class="rightPos">
					<a class="rightPosTit" href="javascript:;">用户中心</a> >
					<a class="rightPosCld" href="javascript:;">机型管理</a>>
					<a class="rightPosCld" href="javascript:;">新增机型</a>
				</div>
				<div class="main">
					<div class="mainTitle boxSizing borBot1">
						<h2 class="boxSizing borBot2">新增机型</h2>
					</div>
					<div class="mainLists">
						<div class="list">
							<div class="carParameter " >
								<label><em>*</em>  机型品牌：</label>
<!-- 								<select name="" class="inputText2 carBrands carmodelBrand"> -->
									<input class="inputText2 carBrands carmodelBrand"/>
									<div id="pattern_name" class="pattern_name"> </div>
								</select>
							</div>
							<div class="carParameter">
								<label><em>*</em>  类型：</label>
								<!--                         <input type="text" name="" id="" value="" placeholder="输入匹配" class="inputText2"/> -->
								<select name="" class="inputText2 carMetadata">
									<option></option>
								</select>
							</div>
							<div class="carParameter">
								<label><em>*</em>  型号：</label>
								<input type="text" name="" id="" value="" placeholder="输入（2-10字数限制）" class="inputText2 carModelNams" />
							</div>
							<div class="carParameter" style="line-height:55px;text-indent:10px;">
                    	<input type="checkbox" name="sort" id="add_first_nav_sort" value="" class="addCheck sortckbox" style="margin-right:10px">APP显示
                   			 </div>
							<P>参数设置</P>
							<div class="addParameter">+ 添加参数</div>
						</div>
						<div class="addCarType">
							<input type="button" name="" id="" value="保存并启用" class="inputBot1 marL1 saveEnable" />
							<input type="button" name="" id="" value="保存并禁用" class="inputBot1 saveDisable" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script src="/js/jquery-1.12.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/main.js" type="text/javascript" charset="utf-8"></script>
		<script src="/js/carmodel/carBrand.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/carmodel/addCarModel.js" type="text/javascript" charset="utf-8"></script>
	<script>
		$(".leftNavi dl dd").on('click', function() {
			var brand_Id = $(".carBrands").val().trim();
			var metadata_Id = $(".carMetadata").val().trim();
			var carModel_Name = $('.carModelNams').val().trim();
			var carParams_List = [];
			if($('.addCarParams').length > 0) {
				for(var i = 0; i < $('.addCarParams').length; i++) {
					var car_params_value = $('.addCarValues')[i].value.trim();
					var car_params_name = $('input.inputText2.addCarParams')[i].value.trim();
					var carParams = {
						car_params_value: car_params_value,
						car_params_name: car_params_name
					};
					if(car_params_value.length > 0 && car_params_name.length > 0) {
						carParams_List.push(carParams);
					}
				}
			}
			if(carModel_Name.length > 0 || brand_Id.length > 0 || metadata_Id.length > 0 || carParams_List.length > 0) {
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
					} else if($(this).text() == "新增商品") {
						location.href = 'newProduct.html';
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
				} else if($(this).text() == "新增商品") {
					location.href = 'newProduct.html';
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