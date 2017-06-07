Ext.namespace("com.hjh.category");
function createCategory() {

	overrideStoreProxy();

	var categoryStore = createCategoryStore();

	var addOrUpdateWin = createAddLabelWin();

	var labelGrid = Ext.create('Ext.grid.Panel', {
		viewConfig : {
			enableTextSelection : true
		// 可以复制单元格文字
		},
		forceFit : true, // 列表宽度自适应
		enableColumnMove : false,// 禁止column移动,移动的话cellclick会出问题
		height : '100%',
		width : '100%',
		id : "category",
		store : categoryStore,
		title : '类目管理',
		columnLines : true,
		tbar : createCategoryTbar(),
		listeners : {
			cellClick : function(thisTab, td, cellIndex, record, tr, rowIndex, event, eventObj) {
				var fieldName = this.columns[cellIndex].dataIndex;
				switch (fieldName) {
				case 'icon':
					var path = record.data.icon;
					if (path.trim() != "") {
						showImg(path);
					}
					break;
				}
			}
		},
		columns : createCategoryColumn(),
		bbar : [ {
			xtype : 'pagingtoolbar',
			store : categoryStore, // same store GridPanel is using
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

	centerPanel.add(labelGrid);

}

// 添加标签的column
function createCategoryColumn() {
	var list = [ {
		text : '序号',
		xtype : "rownumberer",
		width : '3%',
		align : 'center',
	}, {
		text : '名称',
		dataIndex : 'category_name',
		align : 'center'
	}, {
		text : '图标',
		dataIndex : 'icon',
		align : 'center',
		renderer : function(value, cellmeta) {
			return dicID(value);
		}
	}, {
		text : '备注',
		dataIndex : 'remark',
		align : 'center'
	}, {
		text : '操作时间',
		dataIndex : 'update_date',
		renderer : function(value, cellmeta) {
			var date = cellmeta.record.data.update_date;
			var time = cellmeta.record.data.update_time;
			var formateDate = "";
			formateDate = (date == null || date == "") ? "" : insert_flg(date, time);
			return formateDate;
		},
		align : 'center'
	},
	 {
		text : '操作人',
		dataIndex : 'updater',
		align : 'center'
	},{
		xtype : 'actioncolumn',
		text : '操作',
		width : '12%',
		align : 'center',
		items : [ {
			iconCls : 'itemIcon4',
			tooltip : 'update',
			handler : function(grid, rowIndex, colIndex, a, b, row) {
				grid.getSelectionModel().deselectAll();
				grid.getSelectionModel().select(rowIndex);
				var win = Ext.getCmp('addOrUpdateWin_basicCategory');
				showModifyLabelWin(win, row.data);
			}
		} ,
		{
			iconCls : 'itemIcon3',
			tooltip : 'delete',
			handler : function(grid, rowIndex, colIndex, a, b, row) {

				grid.getSelectionModel().deselectAll();
				grid.getSelectionModel().select(rowIndex);
				var category_id = row.data.category_id;
				var info = '删除';
				var url = "/deleteCategory";

				Ext.Msg.show({
					title : '提示',
					message : '确定要' + info + '该类目吗？',
					buttons : Ext.Msg.YESNO,
					icon : Ext.Msg.QUESTION,
					fn : function(btn) {
						if (btn === 'yes') {
							Ext.Ajax.request({
								url : url,
								method : 'POST',
								jsonData : {
									access_token : localStorage.access_token,
									category_id : category_id,
								},
								success : function(response, opts) {
									var o = Ext.util.JSON.decode(response.responseText);
									if (o.error_no == '0') {
										Ext.Msg.alert('提示', info + '成功！');
										var categoryStore = Ext.getStore('categoryStore');

										var proxy = categoryStore.getProxy();

										categoryStore.loadPage(1);
									} else {
										Ext.Msg.alert('提示', o.error_info);
									}
								},
								fail : function(response, options) {
									var o = Ext.util.JSON.decode(response.responseText);
									Ext.Msg.alert('提示', o.error_info);
									var categoryStore = Ext.getStore('categoryStore');

									var proxy = categoryStore.getProxy();

									categoryStore.loadPage(1);
								},
							})
						} else if (btn === 'no') {
							return;
						}
					}
				});

			}
		}],
		align : 'center'
	} ];
	return list;
}

// 刷新 label的缓存
function refreshLabelCache() {
	var mask = Ext.getCmp('loadMask_init');
	mask.show();

	var data = {
		access_token : localStorage.access_token
	};

	Ext.Ajax.request({
		url : '/json/700306',
		method : 'POST',
		headers : {
			'Content-Type' : 'application/json;charset=UTF-8'
		},
		jsonData : data,
		success : function(response, opts) {
			var mask = Ext.getCmp('loadMask_init');
			mask.hide();
		},
		failure : function(response, opts) {
			var mask = Ext.getCmp('loadMask_init');
			mask.hide();
		}
	});
}

// 打开编辑页面
function showModifyLabelWin(updateLabelWin, data) {
	var form = updateLabelWin.items.items[0].getForm();
	form.reset();

	var categoryNameField = updateLabelWin.items.items[0].getForm().findField('categoryName');
	categoryNameField.setValue(data.category_name);

//	var sortField = updateLabelWin.items.items[0].getForm().findField('sort');
//	sortField.setValue(data.sort_num);

	var categoryIdField = updateLabelWin.items.items[0].getForm().findField('categoryId');
	categoryIdField.setValue(data.category_id);

	var remarkField = form.findField("remark");
	remarkField.setEmptyText('请填写修改原因');
	remarkField.setValue(data.remark);

	var iconField = form.findField('icon');
	var showMsg = null;

	if (data.icon == null || data.icon.trim().length == 0) {
		showMsg = "未上传";
	} else {
		showMsg = "已上传";
	}
	iconField.setRawValue(showMsg);

	updateLabelWin.show();
	updateLabelWin.setTitle('编辑');
}

// 显示添加label 的window
function showAddLabelWin(addLabelWin) {
	var fatherNameField = addLabelWin.items.items[0].getForm().findField('fatherName');
	fatherNameField.setValue("");

	var form = addLabelWin.items.items[0].getForm();
	var remark = form.findField("remark");
	remark.setEmptyText('备注（可以不填）');

	addLabelWin.show();
	addLabelWin.setTitle('添加类目');
}

// 添加的form
function createAddCategoryForm() {
	var form = Ext.create('Ext.form.Panel', {
		padding : '10 0 0 0',
		frame : true, // frame属性
		autoScroll : true,
		fieldDefaults : {
			labelAlign : 'left',
			labelWidth : 90
		},
		items : [ {
			xtype : "textfield",
			name : 'iconHiddenValue',
			fieldLabel : "标题图片隐藏域",
			hidden : true
		}, {
			xtype : 'textfield', // 3
			name : 'categoryId',
			width : 400,
			fieldLabel : 'categoryId',
			readOnly : true,
			hidden : true
		}, {
			xtype : 'textfield', // 3
			emptyText : '请填写类目名称(必填)',
			name : 'categoryName',
			allowBlank : false,
			maxLength : 20,
			width : 400,
			fieldLabel : '类目名称'
		}, {
			xtype : 'filefield', // 3
			emptyText : '请上传类目图标(必传)',
			name : 'icon',
			width : 400,
			fieldLabel : '图标',
			listeners : {
				change : function(btn, value) {

					var file = btn.fileInputEl.dom.files[0];
					var form = btn.ownerLayout.owner.getForm();
					var iconHiddenValue = form.findField("iconHiddenValue");
					if (file != null || file == "") {
						if (file.size / 1024 / 1024 > 1) {
							form.findField("icon").setRawValue('');
							iconHiddenValue.setValue(null);
							Ext.Msg.alert('警告', "亲，图片太大了，请处理一下,别大于1M。");
						} else {
							var reader = new FileReader();
							reader.readAsDataURL(file);
							reader.onload = function(e) {
								iconHiddenValue.setValue(this.result);
							}
						}
					} else {
						form.findField("icon").setRawValue('');
						iconHiddenValue.setValue(null);
					}
				}
			}
		}, {
			xtype : 'textarea', // 3
			emptyText : '备注',
			emptyValue : '',
			name : 'remark',
			width : 400,
			maxLength : 200,
			fieldLabel : '备注'
		} ],
		buttons : [ {
			text : '取消',
			handler : function(thisBtn) {
				thisBtn.ownerLayout.owner.ownerLayout.owner.ownerLayout.owner.close();
			}
		}, {
			text : '保存',
			formBind : true,
			handler : function(thisBtn, b, c, d) {
				this.disable();
				var btn = this;

				var form = thisBtn.ownerLayout.owner.ownerLayout.owner.getForm();
				var data = {
					category_name : form.findField('categoryName').value.trim(),
					//sort_num : form.findField('sort').value.trim(),
					icon : form.findField('iconHiddenValue').value,
					remark : form.findField('remark').value.trim(),
					category_id : form.findField('categoryId').value,
				};

				var url = '/json/';
				var updateUrl = "900502";// 更新的url
				var addUrl = "900501";// 添加的url
				url += data.category_id.length == 16 ? updateUrl : addUrl;

				if (data.icon == null || data.icon.trim().length < 1) {
					delete data.icon;
				}

//				if (data.remark == null || data.remark.trim().length < 1 && data.category_id.length == 16) {
//					if (data.remark == null || data.remark.trim().length < 1) {
//						btn.enable();
//						Ext.Msg.alert('错误', '备注不能为空');
//						return;
//					}
//				}

				Ext.Ajax.request({
					url : url,
					method : 'POST',
					headers : {
						'Content-Type' : 'application/json;charset=UTF-8'
					},
					jsonData : data,
					success : function(response, opts) {
						btn.enable();
						var result = Ext.decode(response.responseText);
						if (result.error_no == '0') {
							Ext.Msg.alert('成功', '保存成功.');
							var categoryStore = Ext.getStore('categoryStore');
							var win = Ext.getCmp('addOrUpdateWin_basicCategory');
							win.hide();
							win.items.items[0].reset();
							categoryStore.load();
						} else {
							var errorOn = result.error_on == null ? "" : result.error_on;
							Ext.Msg.alert('错误', errorOn + result.error_info);
						}
					},
					failure : function(response, opts) {
						btn.enable();
					}
				});

			}
		} ]
	});
	return form;
}

// 添加的window
function createAddLabelWin() {
	var win = Ext.create('Ext.window.Window', {
		title : "添加类目",
		id : 'addOrUpdateWin_basicCategory',
		height : 410,
		width : 500,
		// plain : true,
		layout : {
			type : 'hbox',
			align : 'middle ',
			pack : 'center'
		},
		layout : 'fit',
		iconCls : "addicon",
		// 不可以随意改变大小
		resizable : false,
		// 是否可以拖动
		draggable : true,
		autoScroll : true,
		closeAction : 'close',
		closable : true,
		autoLoad : true,
		// 弹出模态窗体
		modal : 'true',
		buttonAlign : "center",
		bodyStyle : "padding:0 0 0 0",
		items : [ createAddCategoryForm() ]
	});

	win.on("beforeClose", function() {
		com.hjh.category.icon = null;
		win.items.items[0].getForm().reset();
	});

	return win;
}

// 创建label 的store
function createCategoryStore() {
	var categoryStore = Ext.create('Ext.data.Store', {
		pageSize : 15,
		storeId : 'categoryStore',
		proxy : {
			type : 'ajax',
			url : '/json/900503',
			headers : {
				"Content-Type" : "application/json;charset=utf-8"
			},
			actionMethods : {
				read : 'POST'
			},
			jsonData : true,
			extraParams : {},
			reader : {
				type : 'json',
				rootProperty : 'result_list',
				totalProperty : 'total_num'
			}
		},
		autoLoad : true
	});
	return categoryStore;
}

// 创建tbar
function createCategoryTbar() {
	overrideStoreProxy();

	var tbar = [ {
		xtype : 'textfield',
		emptyText : '请输入类目名称',
		width : 170,
		maxLength : 36,
		listeners : {
			specialkey : function(thisBtn, e) {
				if (e.getKey() == Ext.EventObject.ENTER) {
					var extraParams = getLabelTbarValues(thisBtn.ownerLayout.owner.items);
					loadLabel(extraParams);
				}
			}
		}
	}, {
		xtype : 'textfield',
		emptyText : '请输入备注信息',
		width : 170,
		maxLength : 36,
		listeners : {
			specialkey : function(thisBtn, e) {
				if (e.getKey() == Ext.EventObject.ENTER) {
					var extraParams = getLabelTbarValues(thisBtn.ownerLayout.owner.items);
					loadLabel(extraParams);
				}
			}
		}
	}, {
		xtype : 'textfield',
		emptyText : '请输入操作人',
		width : 170,
		maxLength : 36,
		listeners : {
			specialkey : function(thisBtn, e) {
				if (e.getKey() == Ext.EventObject.ENTER) {
					var extraParams = getLabelTbarValues(thisBtn.ownerLayout.owner.items);
					loadLabel(extraParams);
				}
			},
		}
	}, {
		xtype : "datefield",
		emptyText : '请输入操作时间(开始)',
		format : "Y年m月d日",// 日期的格式
		altFormats : "Y/m/d|Ymd",
		editable : true,
		maxValue : new Date(),
		width : 200,
		listeners : {
			specialkey : function(thisBtn, e) {
				if (e.getKey() == Ext.EventObject.ENTER) {
					var extraParams = getLabelTbarValues(thisBtn.ownerLayout.owner.items);
					loadLabel(extraParams);
				}
			},
			change : function(thisBtn, e) {
				var startDate = thisBtn.getValue();
				var endDateField = thisBtn.ownerLayout.owner.items.items[4];
				if (endDateField.getValue() == null || endDateField.getValue() == "") {
					endDateField.setValue(startDate);
				}
			},
		}
	}, {
		xtype : "datefield",
		emptyText : '请输入操作时间(结束)',
		format : "Y年m月d日",// 日期格式
		altFormats : "Y/m/d|Ymd",
		maxValue : new Date(),
		editable : true,
		width : 200,
		listeners : {
			specialkey : function(thisBtn, e) {
				if (e.getKey() == Ext.EventObject.ENTER) {
					var extraParams = getLabelTbarValues(thisBtn.ownerLayout.owner.items);
					loadLabel(extraParams);
				}
			},
			change : function(thisBtn, e) {
				var endDate = thisBtn.getValue();
				var startDateField = thisBtn.ownerLayout.owner.items.items[3];
				if (startDateField.getValue() == null || startDateField.getValue() == "") {
					startDateField.setValue(endDate);
				}
			},
		}
	}, {
		xtype : 'button',
		text : '查询',
		handler : function(thisBtn) {
			var extraParams = getLabelTbarValues(thisBtn.ownerLayout.owner.items);
			loadLabel(extraParams);
		}
	}, '->', {
		xtype : 'button',
		text : '新建',
		handler : function(thisBtn) {
			var win = Ext.getCmp('addOrUpdateWin_basicCategory');
			win.setTitle("新增");

			var form = win.items.items[0].getForm();
			form.reset();

			var remarkField = form.findField("remark");
			remarkField.setEmptyText('备注（可以不填）');

			win.show();
		}
	}, ];
	return tbar;
}

// 得到tbar上的参数
function getLabelTbarValues(items) {
	var extraParams = {
		category_name : items.items[0].value,
		remark : items.items[1].value,
		updater : items.items[2].value,
		start_date : formate(items.items[3].rawValue),
		end_date : formate(items.items[4].rawValue),
	};
	if (extraParams.category_name == null || extraParams.category_name.trim().length < 1) {
		delete extraParams.category_name;
	}
	if (extraParams.remark == null || extraParams.remark.trim().length < 1) {
		delete extraParams.remark;
	}
	if (extraParams.start_date == null || extraParams.start_date.trim().length < 1) {
		delete extraParams.start_date;
	}
	if (extraParams.end_date == null || extraParams.end_date.trim().length < 1) {
		delete extraParams.end_date;
	}
	if (extraParams.updater == null || extraParams.updater.trim().length < 1) {
		delete extraParams.updater;
	}
	return extraParams;
}

// 用tabr上的参数加载grid
function loadLabel(extraParams) {

	var categoryStore = Ext.getStore('categoryStore');

	var proxy = categoryStore.getProxy();
	proxy.extraParams = extraParams;

	categoryStore.loadPage(1);
}
function formate(date) {
	date = date.replace("年", "");
	date = date.replace("月", "");
	date = date.replace("日", "");
	return date;
};