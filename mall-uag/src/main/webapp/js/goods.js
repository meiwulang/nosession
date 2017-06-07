//创建grid 的主方法
function createGoodsGrid() {
	overrideStoreProxy();
	// 创建商品类目的store
	createGoodsCategoryStore();
	// 创建category 的store
	var goodsStore = createGoodsStore();
	// 创建添加活动的window
	var win = createAddGoodsWin();
	// 创建columns
	var columns = createGoodsColumns();
	//
	var categoryGrid = Ext.create('Ext.grid.Panel', {
		viewConfig : {
			enableTextSelection : true
		// 可以复制单元格文字
		},
		enableColumnMove : false,// 禁止column移动,移动的话cellclick会出问题
		height : '100%',
		width : '100%',
		id : "goods",
		store : goodsStore,
		title : '商品管理',
		columnLines : true,
		tbar : createGoodsTbar(win),
		columns : columns,
		bbar : [ {
			xtype : 'pagingtoolbar',
			store : goodsStore, // same sstore GridPanel is using
			dock : 'bottom',
			displayInfo : true,
			inputItemWidth : 50,
			beforePageText : '第',
			afterPageText : '页',
			displayMsg : '第{0}条到第{1}条，一共{2}条记录',
			emptyMsg : '没有记录'
		} ]
	});

	var centerPanel = Ext.getCmp('centerPanel');

	centerPanel.add(categoryGrid);

}

// grid 的单击事件中的显示图片
function showImgByActititySwtich(fieldName, record) {
	switch (fieldName) {
	case 'icon':
		var path = record.data.icon;
		if (path.trim() != "") {
			showImg(path);
		}
		break;
	case 'img_url_source':
		var path = record.data.img_url_source;
		if (path.trim() != "") {
			showImg(path);
		}
		break;
	case 'img_url':
		var path = record.data.img_url;
		if (path.trim() != "") {
			showImg(path, 147, 110);
		}
		break;
	}
}

// 创建columns
function createGoodsColumns() {
	var list = [
			{
				text : '序号',
				xtype : "rownumberer",
				width : '3%',
				align : 'center',
			},
			{
				text : '商品编号',
				dataIndex : 'goods_id',
				width : 160,
				align : 'center',
				renderer : function(value, metaData, b, c, d) {
					metaData.tdStyle = "vertical-align:middle";
					var extraParams = getGoodsTbarValues(this.dockedItems.items[2].items);
					if (extraParams.goods_id != null && value.length > 0) {
						var value = value.replace(extraParams.goods_id, "<font color = 'red' size = '3'>"
								+ extraParams.goods_id + "</font>");
					}
					return value;
				},
			},
			{
				text : '类目',
				dataIndex : 'category_name',
				align : 'center',
				renderer : function(value, metaData, b, c, d) {
					metaData.tdStyle = "vertical-align:middle";
					return value;
				},
			},
			{
				text : '品牌',
				dataIndex : 'brand_name',
				align : 'center',
				renderer : function(value, metaData, b, c, d) {
					metaData.tdStyle = "vertical-align:middle";
					return value;
				},
			},
			{
				text : '排序字段',
				dataIndex : 'sort',
				align : 'center',
				renderer : function(value, metaData, b, c, d) {
					metaData.tdStyle = "vertical-align:middle";
					return value;
				},
			},
			{
				text : '商品名称',
				dataIndex : 'goods_name',
				width : 300,
				align : 'center',
				renderer : function(value, metaData) {
					metaData.tdStyle = "vertical-align:middle";
					var extraParams = getGoodsTbarValues(this.dockedItems.items[2].items);
					if (extraParams.goods_name != null && value.length > 0) {
						var value = value.replace(extraParams.goods_name, "<font color = 'red' size = '3'>"
								+ extraParams.goods_name + "</font>");
					}
					return value;
				},
			}, {
				text : '商品规格',
				dataIndex : 'standardList',
				width : 400,
				align : 'center',
				renderer : function(value, metaData) {
					metaData.tdCls = "orderTd";
					var result = "";
					for (var t = 0; t < value.length; t++) {
						result += "<span style='color:orange'>" + value[t].standard_must + "</span>";
						if (value[t].optional_first && value[t].optional_first.trim().length > 0) {
							result += "|" + "<span style='color:orange'>" + value[t].optional_first + "</span>";
						}
						if (value[t].optional_second && value[t].optional_second.trim().length > 0) {
							result += "|" + "<span style='color:orange'>" + value[t].optional_second + "</span>";
						}
						if (t != value.length - 1) {
							result += "<hr style='color:#e9e9e9;border-top: 2px;'>";
						}
					}
					return result;
				},
			}, {
				text : '商品价格',
				dataIndex : 'standardList',
				align : 'center',
				renderer : function(value, metaData) {
					metaData.tdCls = "orderTd";
					var result = "";
					for (var t = 0; t < value.length; t++) {
						result += value[t].price;
						if (t != value.length - 1) {
							result += "<hr style='color:#e9e9e9;border-top: 2px;'>";
						}
					}
					result += "";
					return result;
				},
			}, {
				text : '最后一次操作时间',
				dataIndex : 'update_date',
				width : 180,
				align : 'center',
				renderer : function(value, metaData) {
					metaData.tdStyle = "vertical-align:middle";
					var date = metaData.record.data.update_date;
					var time = metaData.record.data.update_time;
					return parse_date(date, time);
				},
			}, {
				text : '操作人',
				dataIndex : 'operator_name',
				align : 'center',
				renderer : function(value, metaData, b, c, d) {
					metaData.tdStyle = "vertical-align:middle";
					return value;
				},
			}, {
				xtype : 'actioncolumn',
				text : '操作',
				width : '12%',
				align : 'center',
				items : [ {
					tooltip : 'update',
					getClass : function(v, metaData, record) {
						metaData.tdStyle = "vertical-align:middle";
						if (record.data.goods_status == "1") {
							return 'xiajia';
						} else {
							return 'shangjia';
						}

					},
					handler : function(grid, rowIndex, colIndex, a, b, c) {
						grid.getSelectionModel().deselectAll();
						grid.getSelectionModel().select(rowIndex);
						var goods_id = c.data.goods_id;
						var status = '';
						var info = '';
						var url = "";
						if (c.data.goods_status == "1") {
							status = '0';
							info = '禁用';
							url = "/undercarriageGoods";
						} else {
							status = '1';
							info = '启用';
							url = "groundingGoods";
						}

						Ext.Msg.show({
							title : '提示',
							message : '确定要' + info + '该商品吗？',
							buttons : Ext.Msg.YESNO,
							icon : Ext.Msg.QUESTION,
							fn : function(btn) {
								if (btn === 'yes') {
									Ext.Ajax.request({
										url : url,
										method : 'POST',
										jsonData : {
											access_token : localStorage.access_token,
											goods_id : goods_id,
										},
										success : function(response, opts) {
											var o = Ext.util.JSON.decode(response.responseText);
											if (o.error_no == '0') {
												Ext.Msg.alert('提示', info + '成功！');
												var goodsStore = Ext.getStore('goodsStore');

												var proxy = goodsStore.getProxy();

												goodsStore.loadPage(1);
											} else {
												Ext.Msg.alert('提示', o.error_info);
											}
										},
										fail : function(response, options) {
											var o = Ext.util.JSON.decode(response.responseText);
											Ext.Msg.alert('提示', o.error_info);
											var goodsStore = Ext.getStore('goodsStore');

											var proxy = goodsStore.getProxy();

											goodsStore.loadPage(1);
										},
									})
								} else if (btn === 'no') {
									return;
								}
							}
						});

					}
				}, {
					iconCls : 'itemIcon4',
					tooltip : 'update',
					handler : function(grid, rowIndex, colIndex, a, b, row) {
						grid.getSelectionModel().deselectAll();
						grid.getSelectionModel().select(rowIndex);
						createModifyGoodsWin(row.data);
					}
				}, {
					iconCls : 'itemIcon3',
					tooltip : 'update',
					handler : function(grid, rowIndex, colIndex, a, b, row) {

						grid.getSelectionModel().deselectAll();
						grid.getSelectionModel().select(rowIndex);
						var goods_id = row.data.goods_id;
						var status = '';
						var info = '删除';
						var url = "/delGoods";

						Ext.Msg.show({
							title : '提示',
							message : '确定要' + info + '该商品吗？',
							buttons : Ext.Msg.YESNO,
							icon : Ext.Msg.QUESTION,
							fn : function(btn) {
								if (btn === 'yes') {
									Ext.Ajax.request({
										url : url,
										method : 'POST',
										jsonData : {
											access_token : localStorage.access_token,
											goods_id : goods_id,
										},
										success : function(response, opts) {
											var o = Ext.util.JSON.decode(response.responseText);
											if (o.error_no == '0') {
												Ext.Msg.alert('提示', info + '成功！');
												var goodsStore = Ext.getStore('goodsStore');

												var proxy = goodsStore.getProxy();

												goodsStore.loadPage(1);
											} else {
												Ext.Msg.alert('提示', o.error_info);
											}
										},
										fail : function(response, options) {
											var o = Ext.util.JSON.decode(response.responseText);
											Ext.Msg.alert('提示', o.error_info);
											var goodsStore = Ext.getStore('goodsStore');

											var proxy = goodsStore.getProxy();

											goodsStore.loadPage(1);
										},
									})
								} else if (btn === 'no') {
									return;
								}
							}
						});

					}
				} ],
				align : 'center'
			} ];

	return list;
}

// 修改表单显示
function createModifyGoodsWin(data) {
	var win = Ext.getCmp('addOrUpdateWin_goods');
	var form = win.items.items[0].getForm();

	var goods_name = form.findField("goods_name");
	goods_name.setValue(data.goods_name);

	var category_id = form.findField("category_id");
	category_id.setValue(data.category_id);

	var brand_name = form.findField("brand_name");
	brand_name.setValue(data.brand_name);

	var sort = form.findField("sort");
	sort.setValue(data.sort);

	var tel = form.findField("tel");
	tel.setValue(data.tel);

	// 隐藏id
	var goods_id = form.findField("goods_id");
	goods_id.setValue(data.goods_id);

	win.setTitle("修改商品");
	win.show();

	var infoList = data.infoList;
	var info_goods = Ext.getCmp("info_goods");
	for (var i = 0; i < infoList.length; i++) {
		var info = infoList[i];
		info_goods.add(createInfoTextField());
		var form = info_goods.items.items[i].getForm();
		form.findField('info_title').setValue(info.info_title);
		form.findField('info_content').setValue(info.info_content);
		form.findField('info_id').setValue(info.info_id);
	}
	info_goods.items.items[info_goods.items.items.length - 1].destroy();

	var standardList = data.standardList;
	var standard_goods = Ext.getCmp("standard_goods");
	for (var i = 0; i < standardList.length; i++) {
		var standard = standardList[i];
		standard_goods.add(createStandardForm());
		var standardForm = standard_goods.items.items[i].getForm();
		standardForm.findField('standard_must').setValue(standard.standard_must);
		standardForm.findField('optional_first').setValue(standard.optional_first);
		standardForm.findField('optional_second').setValue(standard.optional_second);
		standardForm.findField('price').setValue(standard.price);
		standardForm.findField('standard_id').setValue(standard.standard_id);
	}

	if (standard_goods.items.items.length == 1) {
		var form = standard_goods.items.items[0].getForm();
		form.reset();
		form.findField("standard_must").setDisabled(true);
		form.findField("optional_first").setDisabled(true);
		form.findField("optional_second").setDisabled(true);
		form.findField("price").setDisabled(true);
	} else {
		standard_goods.items.items[standard_goods.items.items.length - 1].destroy();
	}

	// 占位图一
	var ad_goods = Ext.getCmp("ad_goods");

	var img = Ext.create('Ext.Img', {
		src : com.hjh.oss.ossUrl + data.ad_url,
		width : 100,
		height : 100,
		margin : '10 10'
	});

	var delBtn = createDelBtn(ad_goods, "ad_goods");
	var singlePic = Ext.create('Ext.panel.Panel', {
		border : false,
	});
	ad_goods.removeAll()
	singlePic.add(img, delBtn);
	if (data.ad_url.trim().length > 1) {
		ad_goods.add(singlePic);
	} else {
		ad_goods.add(createAddPicBtn("ad_goods"));
	}

	// banner图
	var banner_goods = Ext.getCmp("banner_goods");
	var bannerList = data.bannerList;
	banner_goods.removeAll();
	for (var i = 0; i < bannerList.length; i++) {
		var img = Ext.create('Ext.Img', {
			src : com.hjh.oss.ossUrl + bannerList[i].banner_url,
			width : 100,
			height : 100,
			margin : '10 10'
		});

		var delBtn = createDelBtn(banner_goods, "banner_goods");
		var singlePic = Ext.create('Ext.panel.Panel', {
			border : false,
		});
		var hiddenField = createHiddenField(bannerList[i].pic_id);
		singlePic.add(img, delBtn, hiddenField);
		if (bannerList[i].banner_url.trim().length > 1) {
			banner_goods.add(singlePic);
		}
	}
	if (banner_goods.items.items.length < 6) {
		var addPicBtn = createAddPicBtn("banner_goods");
		banner_goods.add(addPicBtn);
	}

	// 详情图文图
	var detail_goods = Ext.getCmp("detail_goods");
	var detailList = data.detailList;
	detail_goods.removeAll();
	for (var i = 0; i < detailList.length; i++) {
		var img = Ext.create('Ext.Img', {
			src : com.hjh.oss.ossUrl + detailList[i].detail_url,
			width : 100,
			height : 100,
			margin : '10 10'
		});
		var text = createMsgTextField(detailList[i].pic_desc);

		var delBtn = createDelBtn(detail_goods, "detail_goods");
		var singlePic = Ext.create('Ext.panel.Panel', {
			border : false,
		});
		var hiddenField = createHiddenField(detailList[i].pic_id);
		singlePic.add(img, delBtn, text, hiddenField);
		if (detailList[i].detail_url.trim().length > 1) {
			detail_goods.add(singlePic);
		}
	}
	if (detail_goods.items.items.length < 6) {
		var addPicBtn = createAddPicBtn("detail_goods");
		detail_goods.add(addPicBtn);
	}

}

// 活动的确认更新框
function createActivitComfirUpdateStatus(data, info) {
	Ext.Msg.show({
		title : '提示',
		message : '确定要' + info + '该活动吗？',
		buttons : Ext.Msg.YESNO,
		icon : Ext.Msg.QUESTION,
		fn : function(btn) {
			if (btn === 'yes') {
				Ext.Ajax.request({
					url : '/json/800103',
					method : 'POST',
					jsonData : data,
					success : function(response, opts) {
						var result = Ext.decode(response.responseText);
						if (result.error_no == '0') {
							var goodsStore = Ext.getStore('goodsStore');
							goodsStore.load();
							Ext.Msg.alert('成功', '修改成功.');
						} else {
							Ext.Msg.alert('错误', result.error_info);
						}
					},
					fail : function(response, options) {
						var o = Ext.util.JSON.decode(response.responseText);
						Ext.Msg.alert('提示', o.error_info);
						clientDataStore.load();
					},
				})
			} else if (btn === 'no') {
				return;
			}
		}
	});
}

// 创建category 的store
function createGoodsStore() {
	var goodsStore = Ext.create('Ext.data.Store', {
		pageSize : 15,
		storeId : 'goodsStore',
		proxy : {
			type : 'ajax',
			url : '/queryGoods',
			headers : {
				"Content-Type" : "application/json;charset=utf-8"
			},
			actionMethods : {
				read : 'POST'
			},
			jsonData : true,
			extraParams : {
				access_token : localStorage.access_token
			},
			reader : {
				type : 'json',
				rootProperty : 'result_list',
				totalProperty : 'total_num'
			}
		},
		autoLoad : true
	});

	return goodsStore;
}

// 创建商品类型的store
function createGoodsCategoryStore() {
	var categoryStore = Ext.create('Ext.data.Store', {
		pageSize : 15,
		storeId : 'categoryStore_goods',
		proxy : {
			type : 'ajax',
			url : '/queryCategory',
			headers : {
				"Content-Type" : "application/json;charset=utf-8"
			},
			actionMethods : {
				read : 'POST'
			},
			jsonData : true,
			extraParams : {
				access_token : localStorage.access_token,
			},
			reader : {
				type : 'json',
				rootProperty : 'result_list',
				totalProperty : 'total_num'
			}
		},
		autoLoad : true
	});
}

// 创建tbar
function createGoodsTbar(win) {
	overrideStoreProxy();

	var tbar = [ {
		xtype : 'textfield',
		emptyText : '输入商品名称',
		width : 170,
		maxLength : 36,
		listeners : {
			specialkey : function(thisBtn, e) {
				if (e.getKey() == Ext.EventObject.ENTER) {
					var extraParams = getGoodsTbarValues(thisBtn.ownerLayout.owner.items);
					loadGoods(extraParams);
				}
			}
		}
	}, {
		xtype : 'textfield',
		emptyText : '输入商品编号',
		width : 170,
		maxLength : 36,
		listeners : {
			specialkey : function(thisBtn, e) {
				if (e.getKey() == Ext.EventObject.ENTER) {
					var extraParams = getGoodsTbarValues(thisBtn.ownerLayout.owner.items);
					loadGoods(extraParams);
				}
			}
		}
	}, {
		xtype : 'combo',
		emptyText : '类目',
		width : 120,
		store : Ext.getStore('categoryStore_goods'),
		displayField : 'category_name',
		valueField : 'category_id',
		mode : 'local',
		listeners : {
			specialkey : function(thisBtn, e) {
				if (e.getKey() == Ext.EventObject.ENTER) {
					var extraParams = getGoodsTbarValues(thisBtn.ownerLayout.owner.items);
					loadGoods(extraParams);
				}
			}
		}
	}, {
		xtype : 'combo',
		emptyText : '商品状态',
		width : 120,
		store : new Ext.data.SimpleStore({
			fields : [ 'value', 'text' ],
			data : [ [ '1', '上架' ], [ '0', '下架' ] ]
		}),
		displayField : 'text',
		valueField : 'value',
		mode : 'local',
		listeners : {
			specialkey : function(thisBtn, e) {
				if (e.getKey() == Ext.EventObject.ENTER) {
					var extraParams = getGoodsTbarValues(thisBtn.ownerLayout.owner.items);
					loadGoods(extraParams);
				}
			}
		}
	}, {
		xtype : "datefield",
		emptyText : '开始时间',
		format : "Y年m月d日",// 日期的格式
		altFormats : "Y/m/d|Ymd",
		editable : true,
		listeners : {
			specialkey : function(thisBtn, e) {
				if (e.getKey() == Ext.EventObject.ENTER) {
					var extraParams = getGoodsTbarValues(thisBtn.ownerLayout.owner.items);
					loadGoods(extraParams);
				}
			}
		}
	}, {
		xtype : "datefield",
		emptyText : '结束时间',
		labelSeparator : '',
		format : "Y年m月d日",// 日期的格式
		altFormats : "Y/m/d|Ymd",
		maxValue : new Date(),
		editable : true,
		listeners : {
			specialkey : function(thisBtn, e) {
				if (e.getKey() == Ext.EventObject.ENTER) {
					var extraParams = getGoodsTbarValues(thisBtn.ownerLayout.owner.items);
					loadGoods(extraParams);
				}
			}
		}
	}, {
		xtype : 'button',
		text : '查询',
		handler : function(thisBtn) {
			var extraParams = getGoodsTbarValues(thisBtn.ownerLayout.owner.items);
			loadGoods(extraParams);
		}
	}, '->', {
		xtype : 'button',
		text : '添加',
		handler : function(thisBtn) {
			var win = Ext.getCmp('addOrUpdateWin_goods');
			win.setTitle("添加商品");
			var form = win.items.items[0].getForm();

			win.show();

		}
	}, ];
	return tbar;
}

function createGoodsForm() {
	var form = Ext
			.create(
					'Ext.form.Panel',
					{
						frame : true, // frame属性
						autoScroll : true,
						defaults : {
							labelWidth : 90,
							margin : '10 30 0 30',
							columnWidth : .5,
						},
						items : [ {
							name : 'goods_name',
							xtype : "textfield",
							maxLength : 38,
							minLength : 13,
							fieldLabel : "<font color = 'red'>*</font>商品名称",
							width : 420,
							msgTarget : 'under',
							allowBlank : false,
						}, {
							fieldLabel : '排序字段',
							name : 'sort',
							xtype : "numberfield",
							width : 300,
							emptyText : '从小到大排序,默认为100',
							msgTarget : 'under',
							value : 100,
							allowDecimals : false, // 只允许整数
							maxValue : 100,
							minValue : 1,
							maxLength : 3,
							allowBlank : false,
						}, {
							fieldLabel : "<font color = 'red'>*</font>类目",
							xtype : 'combo',
							name : 'category_id',
							emptyText : '类目',
							store : Ext.getStore('categoryStore_goods'),
							displayField : 'category_name',
							valueField : 'category_id',
							mode : 'local',
							msgTarget : 'under',
							allowBlank : false,
							listeners : {
								specialkey : function(thisBtn, e) {
									if (e.getKey() == Ext.EventObject.ENTER) {
										var extraParams = getGoodsTbarValues(thisBtn.ownerLayout.owner.items);
										loadGoods(extraParams);
									}
								}
							}
						}, {
							border : false,
							id : "standard_goods",
							items : [ createStandardForm() ]
						}, {
							xtype : "label",
							html : "占位图一:",
						}, {
							xtype : "panel",
							id : "ad_goods",
							layout : 'hbox',
							items : [ createAddPicBtn("ad_goods") ],
						}, {
							name : 'brand_name',
							xtype : "textfield",
							maxLength : 50,
							fieldLabel : "<font color = 'red'>*</font>品牌",
							emptyText : '必填',
							msgTarget : 'under',
							allowBlank : false,
						}, {
							xtype : "label",
							html : "<font color = 'red'>*</font>商品banner图:",
						}, {
							name : 'banner_goods',
							xtype : "panel",
							id : "banner_goods",
							layout : 'hbox',
							items : [ createAddPicBtn("banner_goods") ],
						}, {
							xtype : "label",
							html : "<font color = 'red'>*</font>商品详情图:",
						}, {
							name : 'detail_ids',
							xtype : "panel",
							id : "detail_goods",
							layout : 'hbox',
							items : [ createAddPicBtn("detail_goods") ],
						}, {
							border : false,
							id : "info_goods",
							items : [ createInfoTextField() ],
						}, {
							name : 'tel',
							xtype : "textfield",
							border : false,
							fieldLabel : "<font color = 'red'>*</font>客服电话",
							msgTarget : 'under',
							allowBlank : false,
						}, {
							xtype : "textfield",
							name : 'goods_id',
							hidden : true,
						}, ],
						buttons : [
								{
									text : '取消',
									handler : function(thisBtn) {
										thisBtn.ownerLayout.owner.ownerLayout.owner.ownerLayout.owner.close();
									}
								},
								{
									text : '保存',
									handler : function(thisBtn, b, c, d) {
										var btn = thisBtn;
										btn.disable();
										var form = thisBtn.ownerLayout.owner.ownerLayout.owner.getForm();
										if (!form.isValid()) {
											btn.enable();
											Ext.Msg.alert('提示', '存在格式不正确');
											return;
										}
										var standard_goodsForm = Ext.getCmp('standard_goods');
										var standardList = [];
										for (var i = 0; i < standard_goodsForm.items.items.length; i++) {
											var obj = {
												standard_must : standard_goodsForm.items.items[i].getForm().findField(
														"standard_must").value,
												optional_first : standard_goodsForm.items.items[i].getForm().findField(
														"optional_first").value,
												optional_second : standard_goodsForm.items.items[i].getForm()
														.findField("optional_second").value,
												price : standard_goodsForm.items.items[i].getForm().findField("price").value,
												standard_id : standard_goodsForm.items.items[i].getForm().findField(
														"standard_id").value,
											};
											if (obj.standard_must.length + obj.optional_first.length
													+ obj.optional_second.length > 20) {
												btn.enable();
												Ext.Msg.alert('提示', '每一条规格字数不能超过20个');
												return;
											}
											standardList.push(obj);
										}

										var banner_goods = Ext.getCmp('banner_goods');
										if (banner_goods.items.items.length < 2) {
											btn.enable();
											Ext.Msg.alert('提示', '必须要有一个banner图');
											return;
										}
										var bannerList = [];
										for (var i = 0; i < banner_goods.items.items.length; i++) {
											var panel = banner_goods.items.items[i];
											if (panel.xtype == "panel") {
												var obj = {
													pic_url : panel.items.items[0].src.replace(com.hjh.oss.ossUrl, ""),
													pic_desc : " ",
												};
												if (panel.items.items.length == 3) {
													obj.pic_id = panel.items.items[2].value
												}
												bannerList.push(obj);
											}
										}

										var detail_goods = Ext.getCmp('detail_goods');
										if (detail_goods.items.items.length < 2) {
											Ext.Msg.alert('提示', '必须要有一个详情图');
											btn.enable();
											return;
										}

										var detailList = [];
										for (var i = 0; i < detail_goods.items.items.length; i++) {
											var panel = detail_goods.items.items[i];
											if (panel.xtype == "panel") {
												var obj = {
													pic_url : panel.items.items[0].src.replace(com.hjh.oss.ossUrl, ""),
													pic_desc : panel.items.items[2].value,
												};
												if (panel.items.items.length == 4) {
													obj.pic_id = panel.items.items[3].value
												}
												detailList.push(obj);
											}
										}
										var info_goods = Ext.getCmp('info_goods');
										var infoList = [];
										for (var i = 0; i < info_goods.items.items.length; i++) {
											var infoForm = info_goods.items.items[i].getForm();
											var obj = {
												info_title : infoForm.findField("info_title").value,
												info_content : infoForm.findField("info_content").value,
												info_id : infoForm.findField("info_id").value,
											};
											infoList.push(obj);
										}

										var ad_goods = Ext.getCmp('ad_goods');

										var data = {
											goods_name : form.findField('goods_name').value,
											sort : form.findField('sort').value,
											category_id : form.findField('category_id').value,
											brand_name : form.findField('brand_name').value,
											tel : form.findField('tel').value,
											standardList : standardList,
											bannerList : bannerList,
											detailList : detailList,
											infoList : infoList,
											goods_id : form.findField('goods_id').value
										};
										if (ad_goods.items.items[0].items) {
											data.ad_url = ad_goods.items.items[0].items.items[0].src.replace(
													com.hjh.oss.ossUrl, "");
										}
										if (data.standardList[0].standard_must == null
												|| data.standardList[0].standard_must.trim().length < 1) {
											delete data.standardList;
										}

										var url = '';
										var updateUrl = "/modifyGoods";
										var addUrl = "/addGoods";
										url += (data.goods_id == null || data.goods_id.trim().length == 0) ? addUrl
												: updateUrl;
										Ext.Ajax.request({
											url : url,
											method : 'POST',
											headers : {
												'Content-Type' : 'application/json;charset=UTF-8'
											},
											jsonData : data,
											success : function(response, opts) {
												console.log("success");
												btn.enable();
												var result = Ext.decode(response.responseText);
												if (result.error_no == '0') {
													var goodsStore = Ext.getStore('goodsStore');
													goodsStore.load();
													Ext.Msg.alert('成功', '保存成功');
													var win = Ext.getCmp('addOrUpdateWin_goods');
													win.close();
												} else {
													Ext.Msg.alert('错误', result.error_info);
												}

											},
											failure : function(response, opts) {
												console.log("failure");
												btn.enable();
											}
										});

									}
								} ]
					});
	return form;
}

// 创建规格表单
function createStandardForm() {
	var q = {
		xtype : 'form',
		layout : 'column',
		border : false,
		defaults : {
			margin : '10 0 0 10',
			columnWidth : .16,
		},
		items : [ {
			name : 'standard_must',
			xtype : "textfield",
			labelWidth : 90,
			margin : '10 0 0 0',
			emptyText : '必填',
			fieldLabel : "<font color = 'red'>*</font>商品规格",
			msgTarget : 'under',
			allowBlank : false,
		}, {
			name : 'optional_first',
			xtype : "textfield",
			emptyText : '非必填',
			msgTarget : 'under',
			maxLength : 20,
		}, {
			name : 'optional_second',
			xtype : "textfield",
			emptyText : '非必填',
			msgTarget : 'under',
			maxLength : 20,
		}, {
			fieldLabel : '价格',
			labelWidth : 40,
			name : 'price',
			xtype : "numberfield",
			emptyText : '必填数字',
			msgTarget : 'under',
			maxLength : 10,
			allowBlank : false,
		}, {
			xtype : "textfield",
			name : 'standard_id',
			hidden : true,
		}, {
			xtype : 'button',
			text : '添加',
			columnWidth : .08,
			handler : function(thisBtn) {

				var parentPanel = thisBtn.ownerLayout.owner.ownerLayout.owner.items.items;
				if (parentPanel.length > 1) {
					parentPanel = thisBtn.ownerLayout.owner.ownerLayout.owner;
					parentPanel.add(createStandardForm());
				} else if (parentPanel.length == 1) {
					var currentForm = thisBtn.ownerLayout.owner;
					var form = currentForm.getForm();
					if (form.findField("standard_must").disabled) {
						form.reset();
						form.findField("standard_must").setDisabled(false);
						form.findField("optional_first").setDisabled(false);
						form.findField("optional_second").setDisabled(false);
						form.findField("price").setDisabled(false);
					} else {
						var parentPanel = thisBtn.ownerLayout.owner.ownerLayout.owner.items.items;
						parentPanel = thisBtn.ownerLayout.owner.ownerLayout.owner;
						parentPanel.add(createStandardForm());
					}
				}
			}
		}, {
			xtype : 'button',
			text : '删除',
			columnWidth : .08,
			handler : function(thisBtn) {
				var parentPanel = thisBtn.ownerLayout.owner.ownerLayout.owner.items.items;
				if (parentPanel.length > 1) {
					var currentForm = thisBtn.ownerLayout.owner;
					currentForm.destroy();
				} else if (parentPanel.length == 1) {
					var currentForm = thisBtn.ownerLayout.owner;
					var form = currentForm.getForm();
					form.reset();
					form.findField("standard_must").setDisabled(true);
					form.findField("optional_first").setDisabled(true);
					form.findField("optional_second").setDisabled(true);
					form.findField("price").setDisabled(true);
				}
			}
		}, ]
	}

	return q;
}

// 创建添加活动的window
function createAddGoodsWin() {
	var win = Ext.create('Ext.window.Window', {
		id : 'addOrUpdateWin_goods',
		height : '90%',
		width : '60%',
		layout : 'fit',
		// plain : true,
		// 不可以随意改变大小
		resizable : true,
		// 是否可以拖动
		draggable : true,
		closeAction : 'close',
		closable : true,
		autoLoad : true,
		// 弹出模态窗体
		modal : 'true',
		buttonAlign : "center",
		maximizable : true,// 最大化
		bodyStyle : "padding:0 0 0 0",
		items : [ createGoodsForm() ]
	});

	win.on("beforeClose", function() {
		win.items.items[0].getForm().reset();
		var ad_goods = Ext.getCmp("ad_goods");
		ad_goods.removeAll()
		ad_goods.add(createAddPicBtn("ad_goods"));

		var banner_goods = Ext.getCmp("banner_goods");
		banner_goods.removeAll()
		banner_goods.add(createAddPicBtn("banner_goods"));

		var detail_goods = Ext.getCmp("detail_goods");
		detail_goods.removeAll()
		detail_goods.add(createAddPicBtn("detail_goods"));

		var standard_goods = Ext.getCmp("standard_goods");
		standard_goods.removeAll();
		standard_goods.add(createStandardForm());

		var info_goods = Ext.getCmp("info_goods");
		info_goods.removeAll();
		info_goods.add(createInfoTextField());
	});

	return win;
}

// 得到tbar上的参数
function getGoodsTbarValues(items) {
	var extraParams = {
		goods_name : items.items[0].value,
		goods_id : items.items[1].value,
		category_id : items.items[2].value,
		goods_status : items.items[3].value,
		start_date : formate(items.items[4].rawValue),
		end_date : formate(items.items[5].rawValue),
	};
	if (extraParams.goods_name == null || extraParams.goods_name.trim().length < 1) {
		delete extraParams.goods_name
	}
	if (extraParams.goods_id == null || extraParams.goods_id.trim().length < 1) {
		delete extraParams.goods_id
	}
	if (extraParams.category_id == null || extraParams.category_id.trim().length < 1) {
		delete extraParams.category_id
	}
	if (extraParams.goods_status == null || extraParams.goods_status.trim().length < 1) {
		delete extraParams.goods_status
	}
	if (extraParams.start_date == null || extraParams.start_date.trim().length < 1) {
		delete extraParams.start_date
	}
	if (extraParams.end_date == null || extraParams.end_date.trim().length < 1) {
		delete extraParams.end_date
	}
	return extraParams;
}

// 用tabr上的参数加载grid
function loadGoods(extraParams) {
	// if (extraParams.end_date != null && extraParams.start_date != null) {
	// if (extraParams.end_date < extraParams.start_date) {
	// Ext.Msg.alert('提示', '结束时间必须大于开始时间');
	// return;
	// }
	// } else if (extraParams.end_date == null && extraParams.start_date ==
	// null) {
	// // do nthing
	// } else {
	// Ext.Msg.alert('提示', '开始时间与结束时间必须同时填写');
	// return;
	// }

	var goodsStore = Ext.getStore('goodsStore');

	var proxy = goodsStore.getProxy();
	proxy.extraParams = extraParams;

	goodsStore.loadPage(1);
}

// 本地选择上传图片
function uploadPicOnchange() {
	var fileInput = document.getElementById("hiddenFileInput");
	for (var i = 0; i < fileInput.files.length; i++) {
		var type = fileInput.files[i].type;
		if (type.indexOf("image") != '0') {
			Ext.Msg.alert('警告', "您选中的" + fileInput.files[i].name + "不是图片类型，请检查！");
			return;
		}
	}
	var panelId = document.getElementById("hiddenFileInput").valueId;
	var picParentPanel = '';
	var picParentPanel = Ext.getCmp(panelId);
	console.log(panelId);
	var exist_num = picParentPanel.items.items.length;

	if (panelId == 'banner_goods' || panelId == 'detail_goods' || panelId == 'banner_goods') {
		if (fileInput.files.length + exist_num > 7) {
			Ext.Msg.alert('警告', "轮播图最多只能6张");
			fileInput.value = '';
			return;
		}
	} else {
		if (fileInput.files.length + exist_num > 2) {
			Ext.Msg.alert('警告', "该类型照片最多只能1张");
			fileInput.value = '';
			return;
		}
	}

	bathChangeBase64(0, fileInput);
}

// 批量修改，因为base64转换的原因 不能使用for循环
function bathChangeBase64(index, fileInput) {
	var file = fileInput.files[index];
	var fileSize = fileInput.files.length;
	var panelId = document.getElementById("hiddenFileInput").valueId;
	var picParentPanel = '';
	var picParentPanel = Ext.getCmp(panelId);
	var url = window.URL.createObjectURL(file);
	var img = Ext.create('Ext.Img', {
		src : url,
		width : 100,
		height : 100,
		margin : '10 10'
	});

	if (file.size / 1024 < 300) {
		var reader = new FileReader();
		reader.readAsDataURL(file);
		reader.onload = function(e) {
			img.src = this.result;
			index++;
			if (fileSize > index) {
				file = fileInput.files[index];
				bathChangeBase64(index, fileInput);
			} else {
				fileInput.value = ''
			}
		}
	} else {
		var reader = new FileReader();
		reader.readAsDataURL(file);
		reader.onload = function(e) {
			img.src = compress(this.result, 0.8, "image/jpeg");
			index++;
			if (fileSize > index) {
				file = fileInput.files[index];
				bathChangeBase64(index, fileInput);
			} else {
				fileInput.value = ''
			}
		}
	}
	var addPicBtn = createAddPicBtn(panelId);
	var delBtn = createDelBtn(picParentPanel, panelId);

	picParentPanel.items.items[picParentPanel.items.items.length - 1].destroy();
	var singlePic = Ext.create('Ext.panel.Panel', {
		border : false,
	});
	if (panelId == 'banner_goods') {
		singlePic.add(img, delBtn);
		picParentPanel.add(singlePic);
	} else if (panelId == "detail_goods") {
		singlePic.add(img, delBtn, createMsgTextField());
		picParentPanel.add(singlePic);
	}
	if (panelId == "ad_goods") {
		singlePic.add(img, delBtn);
		picParentPanel.add(singlePic);
	} else {
		if (picParentPanel.items.items.length < 6) {
			picParentPanel.add(addPicBtn);
		}
	}
}

// 创建简介的textfield
function createMsgTextField(text) {
	var textField = {
		xtype : "textfield",
		margin : '0 0 10 10',
		width : 100,
		value : text,
		maxLength : 200,
		emptyText : '请输入介绍',
	};
	return textField;
}
// 创建隐藏的textfield
function createHiddenField(text) {
	var textField = {
		xtype : "textfield",
		margin : '0 0 10 10',
		width : 100,
		value : text,
		hidden : true,
	};
	return textField;
}
// 创建删除图片的按钮
function createDelBtn(picParentPanel, valueId) {
	var delBtn = new Ext.Button({
		buttonOnly : true,
		hideLabel : true,
		margin : '0 0 0 0',
		style : "background-color: white;",
		padding : '0 0 0 0',
		icon : " images/deletePic.png",
		border : false,
		width : 20,
		height : 20,
		handler : function(thisBtn, b, c, d) {
			var lastElement = picParentPanel.items.items[picParentPanel.items.items.length - 1];
			var addPicBtn = createAddPicBtn(valueId);
			if (lastElement.xtype == "image") {// 添加按钮
				lastElement.destroy();
				thisBtn.ownerLayout.owner.destroy()
				picParentPanel.add(addPicBtn);
			} else {
				thisBtn.ownerLayout.owner.destroy()
				picParentPanel.add(addPicBtn);
			}

		}
	});
	return delBtn;
}

// 创建添加图片的按钮
function createAddPicBtn(valueId) {
	var btn = {
		xtype : 'image',
		margin : '10 10 10 10',
		// style : 'border: 1px solid ;',
		width : 100,
		valueId : 'nihao',
		height : 100,
		listeners : {
			scope : this,
			el : {
				click : function() {
					document.getElementById("hiddenFileInput").valueId = valueId;
					console.log(valueId);
					document.getElementById("hiddenFileInput").click();
				}
			}
		},
		src : "../images/addPic.png",
		cls : 'imgPointer'// css/pointer.css
	};
	return btn;
}
// 创建简介的文本域
function createInfoTextField() {
	var q = {
		xtype : 'form',
		layout : 'column',
		border : false,
		width : 750,
		defaults : {
			margin : '10 0 0 10',
			columnWidth : .25,
		},
		items : [ {
			name : 'info_title',
			xtype : "textfield",
			emptyText : '商品介绍标题',
			labelWidth : 90,
			minLength : 1,
			maxLength : 5,
			columnWidth : .30,
			margin : '10 0 0 0',
			fieldLabel : "<font color = 'red'>*</font>商品介绍",
			msgTarget : 'under',
			allowBlank : false,
		}, {
			name : 'info_content',
			xtype : "textarea",
			emptyText : '商品介绍内容',
			columnWidth : .50,
			maxLength : 60,
			labelWidth : 70,
			msgTarget : 'under',
			allowBlank : false,
		}, {
			hidden : true,
			xtype : "textfield",
			name : 'info_id',
		}, {
			xtype : 'button',
			text : '添加',
			columnWidth : .1,
			handler : function(thisBtn) {
				var parentPanel = thisBtn.ownerLayout.owner.ownerLayout.owner;
				parentPanel.add(createInfoTextField());
			}
		}, {
			xtype : 'button',
			text : '删除',
			columnWidth : .1,
			handler : function(thisBtn) {
				var parentPanel = thisBtn.ownerLayout.owner.ownerLayout.owner.items.items;
				if (parentPanel.length > 1) {
					var currentForm = thisBtn.ownerLayout.owner;
					currentForm.destroy();
				}
			}
		}, ]
	}

	return q;
}
