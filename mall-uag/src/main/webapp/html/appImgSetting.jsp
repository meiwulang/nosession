<%@ page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta charset="utf-8" />
		<title>后台管理系统-app展示图片管理</title>
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
						<dd onclick="window.location.href='metadataSetting.html'">元数据管理</dd>
						<dd class="leftNaviOns">APP展示图片管理</dd>
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
					<a class="rightPosCld" href="javascript:;">基本管理</a>>
					<a class="rightPosCld" href="javascript:;">APP展示图片管理</a>
				</div>
				<div class="main">
					<div class="mainTitle boxSizing borBot1">
						<h2 class="boxSizing borBot2">APP展示图片管理</h2>
					</div>
					
					<div class="add">
						<input type="button" name="" id="addImgBtn" value="新 增" class="inputBot2 marL1 marT1 addAppBot" />
						<input type="hidden" name="page_now" id="page_now" value="1"/>
		                <input type="hidden" name="page_limit" id="page_limit" value="15"/>
		                <input type="button" name="" id="query_btn" value="查 询" class="inputBot1 marL1 searchLists" style="display:none"/>
						<input type="hidden" name="page_now" id="page_now" value="1"/>
		                <input type="hidden" name="page_limit" id="page_limit" value="15"/>
						<span class="resultNum">共搜索到<label id="appImg_num"></label>条数据</span>
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
							<div class="shadeMain">
								<h4 class="borBot2 cor2">新增展示图片</h4>
								<ul class="addInvite">
									<li class="bor1">
										<label><span>*</span>轮播位置</label>
										<select name="" class="baOrzhan">
											<option value="0">首页banner</option>
											<option value="1">首页占位图</option>
										</select>
									</li>
									<li class="bor1">
										<label><span>*</span>图片上传</label>
										<input type="file" name="" id="picture_path" value="" style="display: none;" class="addImg" maxlength="18" />
										<input type="text" name="" id="picture_path_display" value="" class="input1 inputFileShow" disabled="true"/>
										<input type="text" name="" id="picture_data" value="" style="display: none;" />
										<input type="button" name="" id="" value="上传" onclick="document.getElementById('picture_path').click()" class="inputBot1 marginLeft1" />
										<label>（建议尺寸750*300px）</label>
									</li>
									<li class="bor1">
										<label>排序</label>
										<input type="number" name="" id="" value="" step="1" min="1" max="3" class="sortStep" />
									</li>
									<li class="bor1">
										<label><span>*</span>是否跳转</label>
										<select name="" class="skipOrNot">
											<option value="0">不跳转</option>
											<option value="1">跳转</option>
										</select>
									</li>
									<li class="bor1">
										<label>链接地址</label>
										<input type="text" name="" id="" value="" placeholder="" class="inviteNum urlAddress" />
									</li>
									<li class="bor1">
										<label>备注</label>
										<input type="text" name="" id="" value="" placeholder="1-10字符限制" class="inviteNum beizhu" maxlength="10" />
									</li>
								</ul>
								<div class="shadeBotPos">
									<span class="shadeBot marR1 inviteKeep" onclick="submitButton()">保存</span>
									<span class="shadeBot inviteCancle">取消</span>
								</div>
							</div>
						</div>
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
				</div>
			</div>
		</div>
	</body>
	<script src="/js/jquery-1.12.1.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/main.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/htmlOss.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/common/common.js" type="text/javascript" charset="utf-8"></script>
	<script src="/js/hjyCmpress.js"  type="text/javascript" charset="utf-8"></script>
	<script src="/js/appImg/appImg.js" type="text/javascript" charset="utf-8"></script>

</html>