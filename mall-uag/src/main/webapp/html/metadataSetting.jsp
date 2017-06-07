<%@ page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>后台管理系统-元数据管理</title>
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
						<dt>商品管理</dt>
						<dd onclick="window.location.href='newProduct.html'">新增商品</dd>
						<dd onclick="window.location.href='groundingProduct.html'">已上架商品</dd>
						<dd onclick="window.location.href='prepareProduct.html'">待发布商品</dd>
					</dl>
					<dl>
						<dt>订单管理</dt>
						<dd onclick="window.location.href='orderManagement.html'">订单管理</dd>
					</dl>
					<dl>
						<dt class="leftNaviOn">基础设置</dt>
						<dd onclick="window.location.href='carBrandSetting.html'">机型品牌管理</dd>
						<dd onclick="window.location.href='productBrandSetting.html'">商品品牌管理</dd>
						<dd class="leftNaviOns">元数据管理</dd>
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
					<a class="rightPosTit" href="/javascript:;">用户中心</a> >
					<a class="rightPosCld" href="/javascript:;">基本管理</a>>
					<a class="rightPosCld" href="/javascript:;">元数据管理</a>
				</div>
				<div class="main">
					<div class="mainTitle boxSizing borBot1">
						<h2 class="boxSizing borBot2">元数据管理</h2>
					</div>
					<div class="mainOperation">
						<div class="mainOperationInput">
							<span>
		                    	<input type="text" name="" id="" value="" placeholder="请输入名称" class="inputText1 inputTex marR1 dataName"/>
		                    </span>
		                    <span>
		                    	<input type="text" name="" id="" value="" placeholder="请输入别名" class="inputText1 inputTex marR1 dataAlias"/>
		                    </span>
		                    <select name="" class="dataType">
		                        <option value="">请选择类别</option>
		                        <option value="0">计量单位</option>
		                        <option value="1">机械类型</option>
		                    </select>
		                    <select name="" class="dataStatus">
		                        <option value="">请选择状态</option>
		                        <option value="1">启用</option>
		                        <option value="0">禁用</option>
		                    </select>
		                    <span>
		                    	<input type="text" name="" id="" value="" placeholder="请输入操作人" class="inputText1 inputTex marR1 dataPerson create_user_name"/>
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
		                    <span><input type="button" name="" id="" value="刷新缓存 " class="inputBot1 marL1" onclick="reloadCache()"/></span>
						</div>
					</div>
					<div class="add">
						<input type="button" name="" id="" value="新 增" class="inputBot2 addBot addLists" />
						<span class="resultNum">共搜索到<label id = "total_num"></label>条数据</span>
					</div>
					<div class="mainLists">
						<div class="list">
							<table border="1" cellspacing="" cellpadding="" id="dataGrid">
							</table>
						</div>
						<div class="page"><!--page begin-->
						   
						</div><!--page end-->
					</div>
					<div class="shade">
						<div class="shadeCon">
							<div class="shadeMain">
								<h4 class="borBot2 cor2">新增元数据</h4>
								<ul class="addInvite">
									<li class="bor1">
										<label><span>*</span>名称</label>
										<input type="text" name="" id="" value="" placeholder="输入名称" class="inviteNum addMetaName" maxlength="20" />
									</li>
									<li class="bor1">
										<label>显示名称（别名）</label>
										<input type="text" name="" id="" value="" placeholder="输入别名" class="inviteNum addMetaAlias" maxlength="20" />
									</li>
									<li class="bor1">
										<label><span>*</span>类别</label>
										<select name="" class="addMetaType">
											<option value="">请选择类别</option>
											<option value="0">计量单位</option>
											<option value="1">机械类型</option>
										</select>
									</li>
									<li class="bor1">
										<label>排序</label>
										<input type="number" name="" id="" class="addMetaSort" value="0" step="1" min="0"/>
									</li>
								</ul>
								<div class="shadeBotPos">
									<span class="shadeBot marR1 inviteKeep keepConfirm" onclick="submitMetaData(1)">保存并启用</span>
									<span class="shadeBot marR1 inviteKeep keepCancle" onclick="submitMetaData(0)">保存并禁用</span>
									<span class="shadeBot inviteCancle">取消</span>
								</div>
							</div>
						</div>
						<div class="shadeCons">
							<div class="shadeMain">
								<h4 class="borBot2 cor2">编辑元数据</h4>
								<ul class="addInvite">
									<li class="bor1">
										<label><span>*</span>名称</label>
										<input type="text" name="" id="" value="" placeholder="输入名称" class="inviteNum" maxlength="8" />
									</li>
									<li class="bor1">
										<label><span>*</span>显示名称（别名）</label>
										<input type="text" name="" id="" value="" placeholder="输入别名" class="inviteNum" maxlength="8" />
									</li>
									<li class="bor1">
										<label><span>*</span>类别</label>
										<select name="">
											<option value="">APP显示</option>
											<option value="">有</option>
											<option value="">无</option>
										</select>
									</li>
									<li class="bor1">
										<label><span>*</span>排序</label>
										<input type="number" name="" id="" value="" step="1" min="0" />
									</li>
								</ul>
								<div class="shadeBotPos">
									<span class="shadeBot marR1 inviteKeep">保存并启用</span>
									<span class="shadeBot marR1 inviteKeep">保存并禁用</span>
									<span class="shadeBot inviteCancle">取消</span>
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
	<script src="/js/common/common.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/metadata/metadata.js" type="text/javascript" charset="utf-8"></script>

</html>