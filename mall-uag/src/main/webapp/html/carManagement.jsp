<%@ page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>后台管理系统-机型管理</title>
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
						<dd>新增机型</dd>
						<dd class="leftNaviOns">机型管理</dd>
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
					<a class="rightPosCld" href="javascript:;">机型管理</a>
				</div>
				<div class="main">
					<div class="mainTitle boxSizing borBot1">
						<h2 class="boxSizing borBot2">机型管理</h2>
					</div>
					<div class="mainOperation">
						<div class="mainOperationInput">
							<span><input type="text" name="" id="" value="" placeholder="搜索代码" class="inputText1 inputTex marR1 carmodelId"/></span>
							<span><input type="text" name="" id="" value="" placeholder="搜索品牌" class="inputText1 inputTex marR1 carmodelBrand " />
							<div id="pattern_name" class="pattern_name"> </div>
							</span>
							<span><input type="text" name="" id="" value="" placeholder="搜索类型" class="inputText1 inputTex marR1 carmodelType"/></span>
							<span><input type="text" name="" id="" value="" placeholder="搜索型号" class="inputText1 inputTex marR1 carmodelName"/></span>
							<select name="" class="marR1" id="is_noparams">
								<option value="2">参数</option>
								<option value="1">有</option>
								<option value="0">无</option>
							</select>
							<span>
								<input class="datainp inputTex inputText6" id="dateFrom" type="text" placeholder="创建开始时间" value=""  readonly>
								到
								<input class="datainp inputTex inputText6 marR1" id="dateTo" type="text" placeholder="创建结束时间" value=""  readonly>
							</span>
							<span><input type="button" name="" id="" value="搜 索" class="inputBot1 marL1 searchLists searchCarModels" onclick="searchOnclick(0)"/></span>
							<span><input type="button" name="" id="" value="清 除" class="inputBot1 marL1 clearInput"/>
								  <input type="hidden" name="car_page_now" id="page_now" value="1"/>
		                    	  <input type="hidden" name="car_page_limit" id="page_limit" value="15"/>
							</span>

						</div>
					</div>
					<div class="add">
						<input type="button" name="" id="" value="启 用" class="inputBot2" onclick="updateStatusBatch(1)" />
						<input type="button" name="" id="" value="禁 用" class="inputBot2" onclick="updateStatusBatch(0)" />
						<span class="resultNum">共搜索到<label id="total_num_carModel"></label>条数据</span>
					</div>
					<div class="mainLists">
						<div class="list">
							<table border="1" cellspacing="" cellpadding="" id="carModelTable">
							</table>
						</div>
						<div class="page">
							<!--page begin-->

						</div>
						<!--page end-->
					</div>
					<div class="shade">
						<div class="shadeCons">
							<div class="shadeMain" id="updateCar">
								<h4 class="borBot2 cor2">编辑机型</h4>
								<ul class="addInvite">
									<li class="bor1">
										<label><span>*</span>品牌</label>
										<input type="text" name="" id="car_model_brand" value="" placeholder="" class="inviteNum carbrand" readonly/>
									</li>
									<li class="bor1 dataImgSrc">
										<label><span>*</span>品牌LOGO</label>
										<img src="">
									</li>
									<li class="bor1 carType">
										<label><span>*</span>类型</label>
										<select name="" class="marR1 carTypes">
										</select>
										<!-- 										<input type="text" name="" id="car_model_type" value="" placeholder="三一" class="inviteNum" maxlength="8" /> -->
									</li>
									<li class="bor1">
										<label><span>*</span>型号</label>
										<input type="text" name="" id="car_model_name" value="" placeholder="（2-10字符限制）" class="inviteNum" maxlength="10" />
									</li>
									<li class="bor1">
										<input type="checkbox" name="sort" id="add_first_nav_sort" value="" class="addCheck sortckbox">APP显示
									</li>
									<li class="bor1 parameter">参数设置<img src="/images/arrowDown.png"></li>
									<li class="addP">+ 添加参数</li>
								</ul>
								<div class="shadeBotPos">
									<span class="shadeBot marR1 inviteKeep saveCarModelEnable">保存并启用</span>
									<span class="shadeBot marR1 inviteKeep  saveCarModelDisable">保存并禁用</span>
									<span class="shadeBot inviteCancle">取消</span>
								</div>
							</div>
						</div>
						<div class="shadeCon">
							<div class="shadeMain ">
								<h4 class="borBot2 cor2">查看机型</h4>
								<ul class="addInvite ">
									<li class="bor1 ">
										<label><span>*</span>品牌</label>
										<span class="brandShow">三一</span>
									</li>
									<li class="bor1 dataImgSrcShow">
										<label><span>*</span>品牌LOGO</label>
										<img src="/images/sany.jpg">
									</li>
									<li class="bor1 ">
										<label><span>*</span>类型</label>
										<span class="carModelShow">推土机</span>
									</li>
									<li class="bor1 ">
										<label><span>*</span>型号</label>
										<span class="carTypeShow">1000</span>
									</li>
									<li class="bor1">
										<input type="checkbox" name="sort" id="add_first_nav_sort_show" value="" class="addCheck sortckbox">APP显示
									</li>
									<li class="bor1 parameter">参数设置<img src="/images/arrowDown.png"></li>
								</ul>
								<div class="shadeBotPos">
									<span class="shadeBot inviteCancle">关闭</span>
								</div>
							</div>
						</div>
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
	<script src="/js/htmlOss.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/carmodel/carBrand.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/common/common.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/carmodel/carModel.js" type="text/javascript" charset="utf-8"></script>
	<script>
		$(".leftNavi dl dd").on('click', function() {
			if($(".shade").is(":visible")) {
				if(confirm('您要离开本页面吗？')) {
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
					} else if($(this).text() == "待发布商品") {
						location.href = 'prepareProduct.html';
					} else if($(this).text() == "新增机型") {
						location.href = 'newCarType.html';
					} else if($(this).text() == "已上架商品") {
						location.href = 'groundingProduct.html';
					} else if($(this).text() == "新增商品") {
						location.href = 'newProduct.html';
					} else if($(this).text() == "机型管理") {
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
				} else {
					return false
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
				} else if($(this).text() == "待发布商品") {
					location.href = 'prepareProduct.html';
				} else if($(this).text() == "新增机型") {
					location.href = 'newCarType.html';
				} else if($(this).text() == "已上架商品") {
					location.href = 'groundingProduct.html';
				} else if($(this).text() == "新增商品") {
					location.href = 'newProduct.html';
				} else if($(this).text() == "机型管理") {
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