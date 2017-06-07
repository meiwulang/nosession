<%@ page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8" />
        <title>后台管理系统-一级类目管理</title>
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
                        <dt class="leftNaviOn">类目管理</dt>
                        <dd class="leftNaviOns">一级类目管理</dd>
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
                    <a class="rightPosTit" href="javascript:;">用户中心</a> > <a class="rightPosCld" href="javascript:;">类目管理</a>> <a class="rightPosCld" href="javascript:;">一级类目管理</a>
                </div>
                <div class="main">
                    <div class="mainTitle boxSizing borBot1">
                        <h2 class="boxSizing borBot2">一级类目管理</h2>
                    </div>
                    <div class="mainOperation">
                        <div class="mainOperationInput">
                            <span>一级:
                                <input type="text" name="" id="fisrst_nav_name" value="" placeholder="输入匹配" class="inputText1 inputTex marR1"/>
                            </span>
                            <span> 状态:
                                <select name="fisrst_nav_status" id="fisrst_nav_status"class="marR1">
                                    <option value="">全部</option>
                                    <option value="1">启用</option>
                                    <option value="0">禁用</option>
                                </select> </span>
                            <span>
                                <input type="text" name="first_nav_creater" id="first_nav_creater" value="" placeholder="输入创建者" class="inputText1 inputTex marR1 create_user_name"/>
                                <div id="pattern_name" class="pattern_name"></div>                           
                            </span>
                            <span> APP显示:
                                <select name="first_nav_app_display" id="first_nav_app_display" class="marR1">
                                    <option value="">全部</option>
                                    <option value="true">显示</option>
                                    <option value="false">不显示</option>
                                </select> </span>
                            <span>
                                <input class="datainp inputTex inputText6" id="dateFrom" type="text" placeholder="创建开始时间" value=""  readonly>
                                到
                                <input class="datainp inputTex inputText6 marR1" id="dateTo" type="text" placeholder="创建结束时间" value=""  readonly>
                            </span>
                            <span>
                                <input type="button" name="" id="fisrst_nav_search" value="搜 索" class="inputBot1 marL1 searchLists" />
                            </span>
                            <span>
                                <input type="button" name="" id="fisrst_nav_cancel" value="清 除" class="inputBot1 marL1 clearInput"/>
                                <input type="hidden" name="page_now" id="page_now" value="1"/>
                                <input type="hidden" name="page_limit" id="page_limit" value="15"/>
                            </span>
                        </div>
                    </div>
                    <div class="add">
                        <input type="button" name="" id="fisrst_nav_add" value="新 增" class="inputBot2 addBot" />
                        <input type="button" name="" id="fisrst_nav_batch_enable" value="启 用" class="inputBot2" />
                        <input type="button" name="" id="fisrst_nav_batch_disable" value="禁 用" class="inputBot2" />
                        <span class="resultNum">共搜索到<label id="fisrst_nav_total">0</label>条数据</span>
                    </div>
                    <div class="mainLists">
                        <div class="list">
                            <table id = "firstNavigation" border="1" cellspacing="" cellpadding="">
                                <tr>
                                    <th>
                                    <input id="checkAll" type="checkbox" name="checkbox1" value="checkbox">
                                    </th>
                                    <th>序号</th>
                                    <th>代码</th>
                                    <th>一级</th>
                                    <th>一级别名</th>
                                    <th>图片</th>
                                    <th>App排序</th>
                                    <th>创建日期</th>
                                    <th>创建者</th>
                                    <th>最后操作日期</th>
                                    <th>最后操作者</th>
                                    <th>状态</th>
                                    <th>操作</th>
                                </tr>
                            </table>
                        </div>
                        <div class="page">
                        </div>
                    </div>
                    <div class="shade">
                        <div class="shadeCon">
                            <div class="shadeMain">
                                <h4 class="borBot2 cor2">新增一级分类</h4>
                                <ul class="addInvite">
                                    <li class="bor1">
                                        <label><span>*</span>一级类目</label>
                                        <input type="text" name="" id="add_first_name" value="" placeholder="输入一级分类" class="inviteNum" maxlength="7" />
                                        <label style="text-align: right;padding-right: 10px;"><span></span>别名</label>       
                                        <input type="text" name="" id="add_first_alias" value="" placeholder="输入别名" class="inviteNum" maxlength="7"/>
                                    </li>
                                    <li class="bor1">
                                        <label><span>*</span>图标</label>
                                        <input type="file"  name="" id="add_first_icon" value="" placeholder="输入名称" style="display: none;" class="" maxlength="7" />
                                        <input type="text" name="" id="first_nav_display" value="" class="input1 " readonly="readonly" />
                                        <input type="text" name="" id="add_first_icon_data" value="" style="display: none;" />
                                        <input type="button" name="add_upload_img" id="add_upload_img" value="上传"  class="inputBot1 marginLeft1" />
                                    </li>                                                       
                                    <li class="bor1">
                                        <input type="checkbox" name="sort" id="add_first_nav_sort" value="" class="addCheck sortckbox" />APP显示   
                                        <span class="indexDD" style='display:none'>排序</span> 
                                        <input type="number" style='display:none' name="sort" class="sort" id="add_first_nav_sort_data"  placeholder="" class="indexDDD" maxlength="10" />
                                    </li>
                                </ul>
                                <div class="shadeBotPos">
                                    <span id="first_nav_save"class="shadeBot marR1 inviteKeep">保存</span>
                                    <span id="first_nav_add_cancel"class="shadeBot inviteCancle">取消</span>
                                </div>
                            </div>
                        </div>
                        <div class="shadeCons">
                            <div class="shadeMain">
                                <h4 class="borBot2 cor2">编辑一级分类</h4>
                                <ul class="addInvite">
                                    <li class="bor1">
                                        <label><span>*</span>一级类目</label>
                                        <input type="text" name="" id="edit_first_name" value="" placeholder="输入一级分类" class="inviteNum" maxlength="7" />
                                        <label style="text-align: right;padding-right: 10px;"><span></span>别名</label>
                                        <input type="text" name="" id="edit_first_alias" value="" placeholder="输入别名" class="inviteNum" maxlength="7" />
                                    </li>
                                    <li class="bor1">
                                        <label><span>*</span>图标</label>
                                        <input type="file"  name="" id="edit_first_icon" value="" placeholder="输入名称" style="display: none;" class="" maxlength="7" />
                                        <input type="text" name="" id="first_nav_edit_display" value="" class="input1 " readonly="readonly" />
                                        <input type="text" name="" id="edit_first_icon_data" value="" style="display: none;" />
                                        <input type="button" name="" id="edit_upload_img" value="上传" class="inputBot1 marginLeft1 sssss" />
                                    </li>
                                    <li class="bor1">
                                        <input type="checkbox" name="sort" id="edit_first_nav_sort" value="" class="addCheck sortckbox" />APP显示   
                                        <span class="indexDD" style='display:none'>排序</span> 
                                        <input type="number" name="sort" style='display:none' class="sort" id="edit_first_nav_sort_data" value="" placeholder="" class="indexDDD" maxlength="10" />
                                    </li>
                                </ul>
                                <div class="shadeBotPos">
                                    <span id='edit_submit'class="shadeBot marR1 inviteKeep">保存</span>
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
    <script src="/js/category/categoryFirst.js" type="text/javascript" charset="utf-8"></script>
</html>