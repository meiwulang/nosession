<%@ page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>后台管理系统-机型品牌管理</title>
    <meta name="Description" content=""/>
    <meta name="Keywords" content=""/>
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <meta name="renderer" content="webkit|ie-comp|ie-stand" />
    <link rel="icon" href="/images/headIco.png" type="image/x-ico" />
    <link rel="stylesheet" type="text/css" href="/css/reset.css"/>
    <link rel="stylesheet" type="text/css" href="/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/css/common.css"/>
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
                <dd class="leftNaviOns">机型品牌管理</dd>
                <dd onclick="window.location.href='productBrandSetting.html'">商品品牌管理</dd>
                <dd onclick="window.location.href='metadataSetting.html'">元数据管理</dd>
                <dd onclick="window.location.href='appImgSetting.html'">APP展示图片管理</dd>
            </dl>
        </div>
    </div>
    <div id="right">
        <div class="exit">
            <div class="exitBot">
                <img src="/images/exit.png"/>
            </div>
        </div>
        <div class="rightPos">
            <a class="rightPosTit" href="javascript:;">用户中心</a> > <a class="rightPosCld" href="javascript:;">基本管理</a>> <a class="rightPosCld" href="javascript:;">机型品牌管理</a>
        </div>
        <div class="main">
            <div class="mainTitle boxSizing borBot1">
                <h2 class="boxSizing borBot2">机型品牌管理</h2>
            </div>
            <div class="mainOperation">
                <div class="mainOperationInput">
                    <span><input type="text" name=""  id="brand_id" value="" placeholder="搜索代码" class="inputText1 inputTex marR1"/></span>
                            <span><input type="text" name=""  id="brand_name" value="" placeholder="搜索品牌" class="inputText1 inputTex marR1"/>
                            <!-- <div id="pattern_carbrand" class="pattern_name"></div> -->
                            </span>
                            <select name="status" id="status" class="dataStatus">
                                <option value="">状态</option>
                                <option value="1">启用</option>
                                <option value="0">禁用</option>
                            </select>
                            <span><input type="text" name="create_user" id="create_user" value="" placeholder="搜索创建者" class="inputText1 inputTex marR1 create_user_name"/>
                            <div id="pattern_name" class="pattern_name"></div>
                            </span>
                            <select name="is_top" id="is_top" class="dataType">
                                <option value="">APP显示</option>
                                <option value="1">显示</option>
                                <option value="0">不显示</option>
                            </select>
                            <span>
                                <input class="datainp inputTex inputText6" id="dateFrom" type="text" placeholder="创建开始时间" value=""  readonly>
								到
								<input class="datainp inputTex inputText6 marR1" id="dateTo" type="text" placeholder="创建结束时间" value=""  readonly>
                           </span>
                            <span><input type="button" name="" id="query_btn" value="搜 索" class="inputBot1 marL1 searchLists"/></span>
                            <span><input type="button" name="" id="" value="清 除" class="inputBot1 marL1 clearInput"/></span>
                            <input type="hidden" name="page_now" id="page_now" value="1"/>
                            <input type="hidden" name="page_limit" id="page_limit" value="15"/>
                </div>
            </div>
            <div class="add">
                        <input type="button" name="" id="" value="新 增" class="inputBot2 addBot addLists" />
                        <input type="button" name="" id="" value="启 用" class="inputBot2 btnact" />
                        <input type="button" name="" id="" value="禁 用" class="inputBot2 btnfor" />
                        <span class="resultNum">共搜索到<label>0</label>条数据</span>
                    </div>
                    <div class="mainLists">
                        <div class="list">
                            <table id="dataGrid" border="1" cellspacing="" cellpadding="">
                    </table>
                </div>
                <div class="page">
                </div>
            </div>
            <div class="shade">
                        <div class="shadeCon addform">
                            <div class="shadeMain">
                                <h4 class="borBot2 cor2">新增机型品牌</h4>
                                <ul class="addInvite">
                                    <li class="bor1">
                                        <label><span>*</span>名称</label>
                                        <input type="text" name="brand_name" class="brand_name" value="" placeholder="输入机型品牌名称" class="inviteNum" maxlength="8" />
                                    </li>
                                    <li class="bor1">
                                        <label><span>*</span>图标</label>
                                        <input type="file" name="pathadd" id="pathadd" value="" placeholder="上传后显示（建议图标尺寸：mm*mm）" onchange="javascript:imgChangeAdd(event)" style="display: none;" class="inputFile1" maxlength="8" />
                                        <input type="text" name="" id="" value="" class="input1 inputFileShow" disabled="true" />
                                        <input type="hidden" name="brand_logo" class="brand_logo" value=""/>
                                        <input type="button" name="" id="" value="上传" onclick="document.getElementById('pathadd').click()" class="inputBot1 marginLeft1" />
                                    	<span style="max-width:200px;margin-left:10px;">建议尺寸：60*80px</span>
                                    </li>
                                    <li class="bor1">
                                        <input type="checkbox" name="sort" id="" value="" class="addCheck sortckbox" />APP显示   
                                        <span class="indexDD" style="display: none;">排序</span> 
                                        <input type="number" name="sort" class="sort" id="" value="" placeholder="" class="inviteNum indexDDD" maxlength="10" style="display: none;"/>
                                    </li>
                                </ul>
                                <div class="shadeBotPos">
                                    <span class="shadeBot marR1 inviteKeep saveAct" status="1">保存并启用</span>
                                    <span class="shadeBot marR1 inviteKeep saveFor" status="1">保存并禁用</span>
                                    <span class="shadeBot inviteCancle">取消</span>
                                </div>
                            </div>
                        </div>
                        <div class="shadeCons editform">
                        <div class="shadeMain">
                            <h4 class="borBot2 cor2">编辑机型品牌</h4>
                            <ul class="addInvite">
                                    <li class="bor1">
                                        <label><span>*</span>名称</label>
                                        <input type="text" name="brand_name" id="" value="" placeholder="输入品牌名称" class="inviteNum brand_name" maxlength="8" />
                                    </li>
                                    <li class="bor1">
                                        <label><span>*</span>图标</label>
                                        <input type="file" name="" id="pathedit" value="" placeholder="上传后显示（建议图标尺寸：mm*mm）" onchange="javascript:imgChangeEdit(event)" style="display: none;" class="inputFile1" maxlength="8"/>
                                        <input type="text" name="" id="" value="" class="input1 inputFileShow" disabled="true" />
                                        <input type="hidden" name="brand_logo" class="brand_logo" value=""/>
                                        <input type="hidden" name="brand_id" class="brand_id" value=""/>
                                        <input type="button" name="" id="" value="上传" onclick="document.getElementById('pathedit').click()" class="inputBot1 marginLeft1" />
                                   		<span style="max-width:200px;margin-left:10px;">建议尺寸：60*80px</span>
                                    </li>
                                    <li class="bor1">
                                        <input type="checkbox" name="sort" id="" value="" class="addCheck sortckbox" />APP显示   
                                        <span class="indexDD">排序</span> 
                                        <input type="number" name="sort" class="sort" id="" value="" placeholder="" class="inviteNum indexDDD" maxlength="10" />
                                    </li>
                            </ul>
                            <div class="shadeBotPos">
                                <span class="shadeBot marR1 keepConfirm saveAct">保存并启用</span>
                                <span class="shadeBot marR1 keepCancle saveFor">保存并禁用</span>
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
<script src="/js/hjyCmpress.js"  type="text/javascript" charset="utf-8"></script>
<script src="/js/jquery-1.12.1.min.js" type="text/javascript" charset="utf-8"></script>
<script src="/js/main.js" type="text/javascript" charset="utf-8"></script>
<script src="/js/common/common.js" type="text/javascript" charset="utf-8"></script>
<script src="/js/common/carBrandData.js" type="text/javascript" charset="utf-8"></script>
<script src="/js/htmlOss.js" type="text/javascript" charset="utf-8"></script>
<script src="/js/brand/carbrand.js" type="text/javascript" charset="utf-8"></script>
</html>
