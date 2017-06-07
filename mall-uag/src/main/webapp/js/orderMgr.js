//创建grid 的主方法
function createOrder() {
	overrideStoreProxy();
	Ext.namespace("com.hjh.mall.order");
	com.hjh.mall.order = 0;
	// 获取认领数据并生成store
	var Orders = getOrderData();
	// 绘制面板，并加载数据
	var OrderGrid = createOrderGrid(Orders);
	// 将面板加载到中央展示区域
	var centerPanel = Ext.getCmp('centerPanel');

	centerPanel.add(OrderGrid);

}
// 获取数据
function getOrderData(jsonData) {
	var postData = {}
	if (jsonData) {
		postData = jsonData;
	}
	postData.access_token = localStorage.access_token;
	return Ext.create('Ext.data.Store', {
		pageSize : 15,
		proxy : {
			type : 'ajax',
			url : '/json/900101',
			headers : {
				"Content-Type" : "application/json;charset=utf-8"
			},
			actionMethods : {
				read : 'POST'
			},
			jsonData : true,
			extraParams : postData,
			reader : {
				type : 'json',
				rootProperty : 'result_list',
				totalProperty : "total_num",
			}
		},
		autoLoad : true
	});
}
function createOrderGrid(Orders) {
	return Ext
			.create(
					'Ext.grid.Panel',
					{
						enableColumnMove : false,// 禁止column移动,移动的话cellclick会出问题
						// 允许选中列表内容
						viewConfig : {
							enableTextSelection : true,
							forceFit : false, // 列表宽度自适应
						},
						store : Orders,
						id : 'OrderList',
						title : '订单管理',
						columnLines : true,
						height : '80%',
						width : '100%',
						listeners : {
							cellClick : function(thisTab, td, cellIndex,
									record, tr, rowIndex, event, eventObj) {
							}
						},
						// ----数据表格start-----
						columns : [{
							text : '订单编号',width : 120,
							dataIndex : 'serialize_num',
							align : 'center',
							renderer : function(value, cellmeta) {
								cellmeta.tdCls="orderPadding";
								return value;
							},
							
						}, {
							text : '商品编号',
							dataIndex : 'prdt_id',
							align : 'center',width : 120,
							renderer : function(value, cellmeta) {
								cellmeta.tdCls="orderPadding";
								return value;
							}
						}, {
							text : '商品名称',width : 300,
							dataIndex : 'prdt_name',
							align : 'center',
							renderer : function(value, cellmeta) {
								cellmeta.tdCls="orderPadding";
								return value;
							},
						}, {
							text : '商品规格',width : 500,
							dataIndex : 'standard_detail',
							align : 'center',
							renderer : function(value, cellmeta) {
								cellmeta.tdCls="orderTd";
								var result="";
								for(var t=0 ;t<value.length;t++){
									result+="<span style='color:orange'>"+value[t].standard_must+"</span>";
									if(value[t].optional_first&&value[t].optional_first.trim().length>0){
										result+="|"+"<span style='color:orange'>"+value[t].optional_first+"</span>";
									}
									if(value[t].optional_second&&value[t].optional_second.trim().length>0){
										result+="|"+"<span style='color:orange'>"+value[t].optional_second+"</span>";
									}
									if(t!=value.length-1){
										result+="<hr style='color:#e9e9e9;border-top: 2px;'>";
									}
								}
								return result;
							}
						}, {
							text : '购买数量',
							dataIndex : 'standard_detail',
							align : 'center',width : 100,
							renderer : function(value, cellmeta) {
								cellmeta.tdCls="orderTd";
								var result="";
								for(var t=0 ;t<value.length;t++){
									result+=value[t].prdt_num;
									if(t!=value.length-1){
										result+="<hr style='color:#e9e9e9;border-top: 2px;'>";
									}
								}
								return result;
							}
						}, {
							text : '商品价格',
							dataIndex : 'standard_detail',
							align : 'center',width : 100,
							renderer : function(value, cellmeta) {
								cellmeta.tdCls="orderTd";
								var result="";
								for(var t=0 ;t<value.length;t++){
									result+=value[t].prdt_price;
									if(t!=value.length-1){
										result+="<hr style='color:#e9e9e9;border-top: 2px;'>";
									}
								}
								return result;
							}
						}, {
							text : '订单金额',width : 120,
							dataIndex : 'total_money',
							align : 'center',renderer : function(value, cellmeta) {
								cellmeta.tdCls="orderPadding";
								return value;
							},

						}, {
							text : '订单时间',
							dataIndex : 'init_date',
							align : 'center',
							width : 200,
							renderer : function(value, cellmeta) {
								cellmeta.tdCls="orderPadding";
								var date = cellmeta.record.data.init_date;
								var time = cellmeta.record.data.init_time;
								if (date == "") {
									return '无';
								}
								return insert_flg(date, time);
							}
						},{
							text : '买家留言',width : 300,
							dataIndex : 'leaved_word',
							align : 'center',align : 'center',renderer : function(value, cellmeta) {
								cellmeta.tdCls="orderPadding";
								return value;
							},
						},
						{
							text : '买家注册手机号',width : 120,
							dataIndex : 'regist_tel',
							align : 'center',align : 'center',renderer : function(value, cellmeta) {
								cellmeta.tdCls="orderPadding";
								return value;
							},
						},
						{
							text : '买家用户名',width : 120,
							dataIndex : 'consignee',
							align : 'center',align : 'center',renderer : function(value, cellmeta) {
								cellmeta.tdCls="orderPadding";
								return value;
							},
						}, {
							text : '邀请码',width : 120,
							dataIndex : 'invite_code',
							align : 'center',align : 'center',renderer : function(value, cellmeta) {
								cellmeta.tdCls="orderPadding";
								return value;
							},
						}, {
							text : '收货联系电话',width : 120,
							dataIndex : 'consignee_tel',
							align : 'center',align : 'center',renderer : function(value, cellmeta) {
								cellmeta.tdCls="orderPadding";
								return value;
							},
						}, {
							text : '收货地址',width : 300,
							dataIndex : 'address_info',
							align : 'center',align : 'center',renderer : function(value, cellmeta) {
								cellmeta.tdCls="orderPadding";
								return value;
							},
						}
						],
						tbar : [
								{
									width : 150,
									forceFit : true,
									xtype : "datefield",
									labelWidth : 60,
									emptyText : "开始时间",
									id : "order_start_date",
									format : "Y-m-d",// 日期的格式
									altFormats : "Y/m/d|Ymd",
									maxValue : new Date(),
									editable : true,
									listeners : {
										specialkey : function(thisBtn, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												reloadOrder(Orders);
											}
										}
									}
								},
								{
									width : 150,
									forceFit : true,
									xtype : "datefield",
									labelWidth : 60,
									id : "order_end_date",
									emptyText : "结束时间",
									format : "Y-m-d",// 日期的格式
									altFormats : "Y/m/d|Ymd",
									maxValue : new Date(),
									editable : true,
									listeners : {
										specialkey : function(thisBtn, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												reloadOrder(Orders);
											}
										}
									}
								},
								{
									width : 150,
									id : 'serialize_num',
									xtype : "textfield",
									emptyText : "订单编号",
									forceFit : true,
									listeners : {
										specialkey : function(thisBtn, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												reloadOrder(Orders);
											}
										}
									}
								},
								{
									width : 150,
									id : 'orderPrdId',
									xtype : "textfield",
									emptyText : "商品编号",
									forceFit : true,
									listeners : {
										specialkey : function(thisBtn, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												reloadOrder(Orders);
											}
										}
									}
								},
								{
									width : 150,
									emptyText : "商品名称",
									id : 'orderPrdName',
									xtype : "textfield",
									forceFit : true,
									listeners : {
										specialkey : function(thisBtn, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												reloadOrder(Orders);
											}
										}
									}
								},
								{
									width : 150,
									emptyText:"买家注册手机号",
									id : 'regist_tel',
									xtype : "textfield",
									forceFit : true,
									listeners : {
										specialkey : function(thisBtn, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												reloadOrder(Orders);
											}
										}
									}
								},
								{	labelWidth:60,
									fieldLabel : '区间查询',
									width : 210,
									emptyText:"输入邀请码",
									id : 'order_invite_code_start',
									xtype : "textfield",
									forceFit : true,
									listeners : {
										specialkey : function(thisBtn, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												reloadOrder(Orders);
											}
										}
									}
								},{
									width : 150,
									emptyText:"输入邀请码",
									id : 'order_invite_code_end',
									xtype : "textfield",
									forceFit : true,
									listeners : {
										specialkey : function(thisBtn, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												reloadOrder(Orders);
											}
										}
									}
								},
								{
									text : '清空',
									handler : function() {
										clearTbarInfo();
										reloadOrder(Orders);

									}
								},
								{
									text : '查询',
									handler : function() {

										reloadOrder(Orders);

									}
								},
								{
									text : '导出',
									width : 50,
									handler : function() {
										var orderPrdId = Ext
												.getCmp("orderPrdId").lastValue;
										var orderPrdName = Ext
												.getCmp("orderPrdName").lastValue;
										var regist_tel = Ext
												.getCmp("regist_tel").lastValue;
										var order_start_date = Ext
												.getCmp("order_start_date").rawValue;
										var order_end_date = Ext
												.getCmp("order_end_date").rawValue;
										var serialize_num = Ext
										.getCmp("serialize_num").rawValue;
										var limit = Orders.pageSize;
										var page = Orders.currentPage;
										var order_invite_code_start = Ext.getCmp("order_invite_code_start").rawValue;
										var order_invite_code_end = Ext.getCmp("order_invite_code_end").rawValue;
										var requestparams = "?access_token="
												+ localStorage.access_token
												+ "&limit=" + limit + "&page="
												+ page;
										if (orderPrdId
												&& orderPrdId.trim().length > 0) {
											requestparams += "&prdt_id="
													+ orderPrdId.trim();
										}
										if (order_invite_code_start
												&& order_invite_code_start.trim().length > 0) {
											requestparams += "&order_invite_code_start="
												+ order_invite_code_start.trim();
										}
										if (order_invite_code_end
												&& order_invite_code_end.trim().length > 0) {
											requestparams += "&order_invite_code_end="
												+ order_invite_code_end.trim();
										}
										if (serialize_num
												&& serialize_num.trim().length > 0) {
											requestparams += "&serialize_num="
												+ serialize_num.trim();
										}
										if (regist_tel
												&& regist_tel.trim().length > 0) {
											requestparams += "&regist_tel="
													+ regist_tel.trim();
										}
										if (orderPrdName
												&& orderPrdName.trim().length > 0) {
											requestparams += "&prdt_name="
													+ orderPrdName.trim();
										}
										var order_start_date1 = "";
										if (order_start_date
												&& order_start_date.trim().length > 0) {
											order_start_date1 = order_start_date
													.trim().replaceAll('-', '');
											requestparams += "&start_date="
													+ order_start_date1;
										}
										var order_end_date1 = "";
										if (order_end_date
												&& order_end_date.trim().length > 0) {
											order_end_date1 = order_end_date
													.trim().replaceAll('-', '');
											requestparams += "&end_date="
													+ order_end_date1;
										}
										if (order_start_date1
												&& order_end_date1
												&& order_start_date1 > order_end_date1) {
											Ext.Msg
													.alert('提示',
															'开始时间必须小于结束时间！');
											return;
										}
										window.open('/json/900102'
												+ requestparams, "_blank");
//										window.location = '/json/900102'
//												+ requestparams;
									}
								}],
						bbar : [{
							xtype : 'pagingtoolbar',
							store : Orders, //
							dock : 'bottom',
							displayInfo : true,
							beforePageText : '第',
							afterPageText : '页',
							inputItemWidth : 50,// 当前页页码显示的宽度
							displayMsg : '第{0}条到第{1}条，一共{2}条记录',
							emptyMsg : '没有记录'
						}]
					});
}
// 清空输入框内容
function clearTbarInfo() {
	Ext.getCmp("orderPrdId").setValue(null);
	Ext.getCmp("orderPrdName").setValue(null);
	Ext.getCmp("order_start_date").setValue(null);
	Ext.getCmp("order_end_date").setValue(null);
	Ext.getCmp("regist_tel").setValue(null);
	Ext.getCmp("order_invite_code_start").setValue(null);
	Ext.getCmp("order_invite_code_end").setValue(null);
	Ext.getCmp("serialize_num").setValue(null);
}
// tbar刷新
function reloadOrder(Orders) {
	var orderPrdId = Ext.getCmp("orderPrdId").lastValue;
	var orderPrdName = Ext.getCmp("orderPrdName").lastValue;
	var order_start_date = Ext.getCmp("order_start_date").rawValue;
	var order_end_date = Ext.getCmp("order_end_date").rawValue;
	var order_invite_code_start = Ext.getCmp("order_invite_code_start").rawValue;
	var order_invite_code_end = Ext.getCmp("order_invite_code_end").rawValue;
	var regist_tel = Ext.getCmp("regist_tel").lastValue;
	var serialize_num = Ext.getCmp("serialize_num").lastValue;
	var jsonData = {
		access_token : localStorage.access_token
	};
	if (orderPrdId && orderPrdId.trim().length > 0) {
		jsonData.prdt_id = orderPrdId.trim();
	}
	if (orderPrdName && orderPrdName.trim().length > 0) {
		jsonData.prdt_name = orderPrdName.trim();
	}
	if (order_start_date && order_start_date.trim().length > 0) {
		jsonData.start_date = order_start_date.trim().replaceAll('-', '');
	}
	if (order_end_date && order_end_date.trim().length > 0) {
		jsonData.end_date = order_end_date.trim().replaceAll('-', '');
	}
	if (regist_tel && regist_tel.trim().length > 0) {
		jsonData.regist_tel = regist_tel.trim();
	}
	if (order_invite_code_start && order_invite_code_start.trim().length > 0) {
		jsonData.order_invite_code_start = order_invite_code_start.trim();
	}
	if (order_invite_code_end && order_invite_code_end.trim().length > 0) {
		jsonData.order_invite_code_end = order_invite_code_end.trim();
	}
	if (serialize_num && serialize_num.trim().length > 0) {
		jsonData.serialize_num = serialize_num.trim();
	}
	if (jsonData.start_date && jsonData.end_date
			&& jsonData.start_date > jsonData.end_date) {
		Ext.Msg.alert('提示', '开始时间必须小于结束时间！');
		return;
	}
	var proxy = Orders.getProxy();
	proxy.extraParams = jsonData;
	Orders.loadPage(1);
}
String.prototype.replaceAll = function(AFindText, ARepText) {
	raRegExp = new RegExp(AFindText, "g");// g代表全部
	return this.replace(raRegExp, ARepText);
}
