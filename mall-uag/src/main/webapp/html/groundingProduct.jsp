<%@ page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>后台管理系统-已上架商品</title>
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
						<dd onclick="window.location.href='memberManagement.html'">会员管理</dd>
						<dd onclick="window.location.href='inviteCode.html'">邀请码管理</dd>
					</dl>
					<dl>
						<dt>类目管理</dt>
						<dd onclick="window.location.href='categoryFirst.html'">一级类目管理</dd>
						<dd onclick="window.location.href='categorySecond.html'">二级类目管理</dd>
						<dd onclick="window.location.href='categoryThird.html'">三级类目管理</dd>
					</dl>
					<dl>
						<dt>机型管理</dt>
						<dd onclick="window.location.href='newCarType.html'">新增机型</dd>
						<dd onclick="window.location.href='carManagement.html'">机型管理</dd>
					</dl>
					<dl>
						<dt class="leftNaviOn">商品管理</dt>
						<dd onclick="window.location.href='newProduct.html'">新增商品</dd>
						<dd class="leftNaviOns">已上架商品</dd>
						<dd onclick="window.location.href='prepareProduct.html'">待发布商品</dd>
					</dl>
					<dl>
						<dt>订单管理</dt>
						<dd onclick="window.location.href='orderManagement.html'">订单管理</dd>
					</dl>
					<dl>
						<dt>基础设置</dt>
						<dd onclick="window.location.href='carBrandSetting.html'">机型品牌管理</dd>
						<dd onclick="window.location.href='productBrandSetting.html'">商品品牌管理</dd>
						<dd onclick="window.location.href='metadataSetting.html'">元数据管理</dd>
						<dd onclick="window.location.href='appImgSetting.html'">APP展示图片管理</dd>
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
					<a class="rightPosCld" href="javascript:;">商品管理</a>>
					<a class="rightPosCld" href="javascript:;">已上架商品</a>
				</div>
				<div class="main">
					<div class="mainTitle boxSizing borBot1">
						<h2 class="boxSizing borBot2">已上架商品</h2>
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
							<span><input type="button" name="" id="" value="搜 索" class="inputBot1 marL1 searchLists" onclick="searchOnclick(0)"/></span>
							<span>
	                    <input type="button" name="" id="" value="清 除" class="inputBot1 marL1 clearInput"/>
	                    <input type="hidden" name="page_now" id="page_now" value="1"/>
                    	<input type="hidden" name="page_limit" id="page_limit" value="15"/>
                    </span>
						</div>
					</div>
					<div class="add">
						<input type="button" name="" id="" value="批量下架" class="inputBot2" onclick="bathUndercarriageGoods()" />
						<input type="button" name="" id="" value="更新搜索引擎" class="inputBot2" onclick="updateGroundGoods()" />
						<span class="resultNum" id="total_num">共搜索到<label></label>条数据</span>
					</div>
					<div class="mainLists">
						<div class="list">
							<table border="1" cellspacing="" cellpadding="" id="dataGrid">

							</table>
						</div>
						<div class="page">

						</div>
					</div>
					<div class="shade">
						<div class="shadeCon">

						</div>
					</div>
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
	<script src="/js/common/common.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/goods/groundingProduct.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/goods/goodsCommon.js" type="text/javascript" charset="utf-8"></script>

</html>