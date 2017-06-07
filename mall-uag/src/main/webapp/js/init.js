var centerPanel;
Ext.onReady(function() {

	Ext.namespace("com.hjh.init");
	com.hjh.init = 0;
	init(); 
	centerPanel = Ext.getCmp('centerPanel');
	String.prototype.replaceAll = function(AFindText, ARepText) {
		raRegExp = new RegExp(AFindText, "g");// g代表全部
		return this.replace(raRegExp, ARepText);
	}
});

//date 20160808 time 151515 2016-08-08 15:15:15
function parse_date(date, time) {
	var result = date.substring(0, 4) + '-' + date.substring(4, 6) + '-'
			+ date.substring(6, 8) + ' ' + time.substring(0, 2) + ':'
			+ time.substring(2, 4) + ':' + time.substring(4, 6);
	return result;
}

function showImg(path, widht, height) {
	var realPath;
	if (path.startsWith('http')) {
		realPath = path;
	} else {
		realPath = com.hjh.oss.ossUrl + path;
	}

	var img = Ext.create('Ext.Img', {
		src : realPath,
		width : '100%',
		height : '100%'
	});

	var imageWin = Ext.create('Ext.window.Window', {
		title : "图片",
		layout : 'fit',
		width : widht == null ? '50%' : widht,
		height : height == null ? '50%' : height,
		// plain : true,
		iconCls : "addicon",
		// 不可以随意改变大小
		resizable : true,
		// 是否可以拖动
		draggable : true,
		closeAction : 'close',
		closable : true,
		// 弹出模态窗体
		modal : 'true',
		frame : false,
		constrain : true,// 限制移动范围在浏览器内
		buttonAlign : "center",
		bodyStyle : "padding:0 0 0 0"
	});
	imageWin.add(img);
	imageWin.show();
};

function overrideStoreProxy() {
	Ext.override(Ext.data.proxy.Ajax, {

		doRequest : function(operation, callback, scope) {
			var writer = this.getWriter(), request = this.buildRequest(
					operation, callback, scope);

			if (operation.allowWrite()) {
				request = writer.write(request);
			}

			Ext.apply(request, {
				headers : this.headers,
				timeout : this.timeout,
				scope : this,
				callback : this.createRequestCallback(request, operation,
						callback, scope),
				method : this.getMethod(request),
				url : this.url,
				disableCaching : false
			// explicitly set it to false, ServerProxy handles caching
			});

			// Added... jsonData is handled already
			if (this.jsonData) {
				request.jsonData = Ext.encode(request._params);
				delete request.params;
			}

			Ext.Ajax.request(request);

			return request;
		}
	});
}

function init() {

	var infoStore = Ext.create('Ext.data.TreeStore', {
		root : {
			expanded : true,
			children : [{
				text : '会员管理',
				expanded : true,
				children : [{
					text : '会员管理',
					valueId : 'userCenter',
					leaf : true
				},{
					text : '邀请码管理',
					valueId : 'invitation',
					leaf : true
				}]
			},
			{
				text : '商品管理',
				expanded : true,
				children : [{
					text : '首页轮播图管理',
					valueId : 'banner',
					leaf : true
				}, {
					text : '类目管理',
					valueId : 'category',
					leaf : true
				},{
					text : '商品管理',
					valueId : 'goods',
					leaf : true
				}]
			},
			{
				text : '订单中心',
				expanded : true,
				children : [{
					text : '订单管理',
					valueId : 'OrderList',
					leaf : true
				}]
			}, {
				text : 'APP应用管理',
				expanded : true,
				children : [{
					text : 'APP反馈信息管理',
					valueId : 'feedbackControl',
					leaf : true
				}]
			}
			]
		}
	});

	var west = Ext.create('Ext.tree.Panel', {
		region : "west",
		width : 194,
		height : '100%',
		title : '导航栏',
		layout : 'fit',
		store : infoStore,
		rootVisible : false,
		listeners : {
			'itemclick' : function(node, record, item, index, event, eOpts) {
				if (record.isLeaf()) {
					var currentId = record.raw.valueId;
					choseTree(currentId);
				}
			}
		}
	});
	var north = Ext.create('Ext.panel.Panel', {
		region : "north",
		height : 100,
		layout : 'absolute'
	// title : 'north'
	});

	var center = Ext.create('Ext.panel.Panel', {
		region : "center",
		id : 'centerPanel',
		split : true,
		border : true,
		layout : 'fit'
	// title : 'center'
	});

	var south = Ext.create('Ext.panel.Panel', {
		region : "south",
		border : false,
		layout : {
			align : 'middle',
			pack : 'center',
			type : 'hbox'
		}
	});
	var panel = Ext
			.create(
					'Ext.panel.Panel',
					{
						region : "south",
						cls : 'imgPointer',// css/pointer.css
						border : false,
						html : '<span style="text-align:center;line-height:18px;">powered by hjh Javaers</span>',
						listeners : {
							click : {
								element : 'el', // bind to the underlying el
								// property on the panel
								fn : function(a, b, c, d, e) {
									window.location = "http://hjh365.com/";
								}
							}
						}
					});

	south.add(panel);

	var view = new Ext.Viewport({
		title : "Viewport",
		layout : "border",
		defaults : {
			bodyStyle : "background-color: #FFFFFF;",
			frame : true
		}
	});

	var myMask = new Ext.LoadMask(view, {
		msg : "Please wait...",
		id : 'loadMask_init'
	});

	var logo = Ext.create('Ext.Img', {
		src : 'images/hjhlogo.jpg',
		cls : 'imgPointer',// css/pointer.css
		x : '1%',
		y : '1%',
		height : '90%',
		listeners : {
			click : {
				element : 'el', // bind to the underlying el
				// property on the panel
				fn : function(a, b, c, d, e) {
					window.location = "http://hjh365.com/";
				}
			}
		}
	});

	var exit = Ext.create('Ext.Img', {
		src : 'images/exit.png',
		width : 20,
		cls : 'imgPointer',// css/pointer.css
		x : '95%',
		y : '30%',
		height : 20,
		draggable : {
			// 拖动时不虚线显示原始位置
			insertProxy : false
		},
		listeners : {
			click : {
				element : 'el', // bind to the underlying el
				// property on the panel
				fn : function(a, b, c, d, e) {
					window.location = "logout";
				}
			}
		}
	});

	north.add(exit);
	north.add(logo);

	view.add(west);
	view.add(north);
	view.add(center);
	view.add(south);
	createGoodsGrid();
}

function choseTree(currentId) {
	var noOldPanel = changeCenter(currentId);
	switch (currentId) {
		case 'invitation' :
			if (noOldPanel) {
				createInvitation();
			}
			break;
		case 'memberGrid' :
			if (noOldPanel) {
				createMember();
			}
			break;
		case 'feedbackControl' :
			if (noOldPanel) {
				createFeedback();
			}
			break;
		case 'brandControl' :
			if (noOldPanel) {
				createbrandControl();
			}
			break;
		case 'userCenter' :
			if (noOldPanel) {
				createUserCenter();
			}
			break;
		case 'orderManage' :
			if (noOldPanel) {
				createOrderManage();
			}
			break;
		case 'category' :
			if (noOldPanel) {
				createCategory();
			}
			break;
		case 'activity' :
			if (noOldPanel) {
				createActivity();
			}
			break;
		case 'topic' :
			if (noOldPanel) {
				createTopic();
			}
			break;
		case 'operatorList' :
			if (noOldPanel) {
				createOperator();
			}
			break;
		case 'banner' :
			if (noOldPanel) {
				createBanner();
			}
			break;
		case 'OrderList' :
			if (noOldPanel) {
				createOrder();
			}
			break;
		case 'correction' :
			if (noOldPanel) {
				createCorrection();
			} else if (com.hjh.init == 1) {
				myStore.load();
				var proxy = myStore.getProxy();
				proxy.extraParams = {
					status : '3',
					customer_name : proxy.extraParams._customer_name,
					category_id : proxy.extraParams._category_id,
					client_enterprise_info : proxy.extraParams_client_enterprise_info,
					init_date : proxy.extraParams._init_date,
					init_time : proxy.extraParams._init_time,
					update_date : proxy.extraParams._update_date,
					update_time : proxy.extraParams._update_time,
					client_name : proxy.extraParams._client_name,
					enterprise_name : proxy.extraParams._enterprise_name,
					address : proxy.extraParams._address,
					update_user : proxy.extraParams._update_user
				};
				myStore.loadPage(1);
				com.hjh.init = 0;
			}
			break;
	}

}

function initCenter() {
	createNotCheckCard();
}

function changeCenter(changeId) {
	var noOldPanel = true;
	var centerPanel = Ext.getCmp('centerPanel');
	for (var i = 0; i < centerPanel.items.length; i++) {
		var temp = centerPanel.items.items[i];
		if (temp.id == changeId) {
			temp.show();
			noOldPanel = false;
		} else {
			temp.hide();
		}
	}
	return noOldPanel;
}

// date 20160808 time 151515 2016-08-08 15:15:15
function parseDate(date, time) {
	var result = date.substring(0, 4) + '-' + date.substring(4, 6) + '-'
			+ date.substring(6, 8);
	if (time != null) {
		result = ' ' + time.substring(0, 2) + ':' + time.substring(2, 4) + ':'
				+ time.substring(4, 6);
	}
	return result;
}
function insert_flg(date, time) {
	var result = date.substring(0, 4) + '-' + date.substring(4, 6) + '-'
			+ date.substring(6, 8) + ' ' + time.substring(0, 2) + ':'
			+ time.substring(2, 4) + ':' + time.substring(4, 6);
	return result;
}
document.write("<script language='javascript' src='/js/oss.js'></script>");