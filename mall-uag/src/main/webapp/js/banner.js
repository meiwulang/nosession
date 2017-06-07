function createBanner() {
	overrideStoreProxy();
	Ext.namespace("com.hjh.banner");
	com.hjh.banner.banner = null;
	com.hjh.banner.type = '';
	
	
	var centerPanel = Ext.getCmp('centerPanel');
	
	//轮播图类型store
	 var bannerTypeStore = Ext.create('Ext.data.Store', {
		 	fields: ["type", "name"],
		    data: [
                { type: "", name: "全部" },
		        { type: "0", name: "轮播图" },
		        { type: "1", name: "占位图" }
		    ]
	 });
	
	// 创建所有后台人员的store
 	var operatorStore = Ext.create('Ext.data.Store', {
 		pageSize : 1000,
 		proxy : {
 			type : 'ajax',
 			url : '/getAllOperators',
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
 				rootProperty : 'result_list'
 			}
 		},
 		autoLoad : true
 	});
	
	//图片状态Store
	 var banner_statusStore = Ext.create('Ext.data.Store', {
		 	fields: ["banner_status", "name"],
		    data: [
		        { banner_status: "0", name: "禁用" },
		        { banner_status: "1", name: "启用" }
		    ]
	 });
	 
	//轮播图列表store
	var bannerDataStore = Ext.create('Ext.data.Store', {
	     pageSize:15,
	     proxy: {
	         type: 'ajax',
	         url: '/getBannerList',
	         headers:{"Content-Type":"application/json;charset=utf-8"},
	         actionMethods: {
	             read: 'POST'
	         },
	         jsonData: true, 
	         extraParams :{
	        	 access_token : localStorage.access_token
	        	 }, 
	         reader: {
	             type: 'json',
	             rootProperty: 'result_list',
	             totalProperty: "total_num",
	         }
	     },
	     autoLoad: true
	 });
	
	
	 var gridPanel = Ext.create('Ext.grid.Panel', {
		 viewConfig:{
			    enableTextSelection:true //可以复制单元格文字
			},
			store : bannerDataStore,
			enableColumnMove:false,// 禁止column移动,移动的话cellclick会出问题
			//forceFit: true, //列表宽度自适应 
			id : 'banner',
			title:'轮播图管理',
			columnLines: true,
			columns : [ 
			   { text : '序号',xtype: "rownumberer" ,width:'3%',align:'center'},        
			   {
				text : '轮播图顺序',
				dataIndex : 'sort',width:'5%',align:'center'
			}, { 
				xtype:'actioncolumn',
	            text : '轮播图',
	            width:'8%',
	            align:'center',
	            items: [{
	                iconCls:'lookFile',
	                tooltip: 'lookFile',
	                handler: function(grid, rowIndex, colIndex,a,b,c) {
	                	var banner_path = c.data.banner_path;// 营业执照
						if (banner_path.trim() != "") {
							showImg(banner_path);
						}
	                }
	            }]
			},{
				text : '是否跳转',
				dataIndex : 'is_skip',width:'8%',align:'center',
				renderer : function(value, cellmeta) {
					if(value == '1'){
						return "跳转";
					}else{
						return "不跳转";
					}
				}
			} , {
				text : 'app跳转地址',
				dataIndex : 'app_url',width:'12%',align:'center'
			} , {
				text : '图片类型',
				dataIndex : 'type',width:'6%',align:'center',
				renderer : function(value, cellmeta) {
					if(value == '0'){
						return "轮播图";
					}else if(value == '1'){
						return "占位图";
					}
				}
			}, {
				text : '图片状态',
				dataIndex : 'status',width:'6%',align:'center',
				renderer : function(value, cellmeta) {
					if(value == '1'){
						return "启用";
					}else{
						return "禁用";
					}
				}
			}, {
				text : '备注',
				dataIndex : 'remark',width:'10%',align:'center'
			}, {
				text : '修改时间',
				dataIndex : 'update_date',width:'10%',align:'center',
				renderer : function(value, cellmeta,a,b,c) {
					var update_time = a.data.update_time;
					if(update_time != null && update_time.trim().length > 0){
						update_time = update_time.substring(0,2)+":"+update_time.substring(2,4)+":"+update_time.substring(4);
					}
					var update_date = value;
					if(value != null && value.trim().length > 0){
						update_date = value.substring(0,4)+"-"+value.substring(4,6)+"-"+value.substring(6);
					}
					return update_date+" "+update_time;
				}
			}, {
				text : '修改人',
				dataIndex : 'update_user',width:'5%',align:'center',
				renderer : function(value, cellmeta) {
					for(var i=0;i<operatorStore.getCount();i++){
						if(value == operatorStore.getAt(i).data.operator_id){
							return operatorStore.getAt(i).data.operator_name;
						}
					}
				}
			},{

	            xtype:'actioncolumn',
	            text : '操作',
	            width:'10%',
	            align:'center',
	            items: [{
	                iconCls:'itemIcon4',
	                tooltip: 'update',
	                handler: function(grid, rowIndex, colIndex,a,b,c) {
	                	var type = update_win.items.items[0].getForm().findField('type');// 轮播图类型
	                	var banner_path = update_win.items.items[0].getForm().findField('banner_path');// 轮播图
	                	var sort = update_win.items.items[0].getForm().findField('sort');// 顺序
	                	var is_skip = update_win.items.items[0].getForm().findField('is_skip');
	                	var banner_id = update_win.items.items[0].getForm().findField('banner_id');
	                	var remark = update_win.items.items[0].getForm().findField('remark');
	                	var app_url = update_win.items.items[0].getForm().findField('app_url');
	                	var status = update_win.items.items[0].getForm().findField('status');
	                	
	                	type.setValue(c.data.category_id);
	                	banner_path.setValue(c.data.banner_path);
	                	sort.setValue(c.data.sort);
	                	banner_id.setValue(c.data.banner_id);
	                	remark.setValue(c.data.remark);
	                	app_url.setValue(c.data.app_url);
	                	status.setValue(c.data.status);
	                	var skipType = (c.data.is_skip == null || c.data.is_skip.trim().length == 0) ? '0' : c.data.is_skip;
	                	var bannerType = (c.data.type == null || c.data.type.trim().length == 0) ? '0' : c.data.type;
	                	is_skip.items.items[skipType].setValue(true);
	                	type.items.items[bannerType].setValue(true);
	                	
	                	var showMsg = null;

	                	if (c.data.banner_path == null || c.data.banner_path.trim().length == 0) {
	                		showMsg = "未上传";
	                	} else {
	                		showMsg = "已上传";
	                	}
	                	banner_path.setRawValue(showMsg);
	                	
	                	update_win.show();
	                }
	            },{
	                tooltip: 'update',
	                getClass: function(v, metaData, record) {
	                	if(record.data.status == "1"){
	                		return 'disabled';
	                	}else{
	                		return 'enable';
	                	}
	                	
	                },
	                handler: function(grid, rowIndex, colIndex,a,b,c) {
	                	grid.getSelectionModel().deselectAll();
		            	grid.getSelectionModel().select(rowIndex);
	                	var banner_id = c.data.banner_id;
	                	var type = c.data.type;
	                	var status = '';
	                	var info = '';
	                	if(c.data.status == "1"){
	                		status = '0';
	                		info = '禁用';
	                	}else{
	                		status = '1';
	                		info = '启用';
	                	}
	                	Ext.Msg.show({
	                	    title:'提示',
	                	    message: '确定要'+info+'该轮播图吗？',
	                	    buttons: Ext.Msg.YESNO,
	                	    icon: Ext.Msg.QUESTION,
	                	    fn: function(btn) {
	                	        if (btn === 'yes') {
	                	        	Ext.Ajax.request({
	                    				url : '/updateBannerStatus',
	                    				method : 'POST',
	                    				jsonData : {
	                    					access_token : localStorage.access_token,
	                    					banner_id : banner_id,
	                    					type:type,
	                    					status:status
	                    				},
	                    				success : function(response, opts) {
	                    					var o = Ext.util.JSON.decode(response.responseText);
	                    					if(o.error_no == '0'){
	                    						Ext.Msg.alert('提示', info+'成功！');
	                    						bannerDataStore.load();
	                    					}else{
	                    						Ext.Msg.alert('提示', o.error_info);
	                    					}
	                    				},
	                    				fail : function(response, options) {
	                    					var o = Ext.util.JSON.decode(response.responseText); 
	                    					Ext.Msg.alert('提示', o.error_info);
	                    					bannerDataStore.load();
	                    				},
	                    			})
	                	        } else if (btn === 'no') {
	                	        	return;
	                	        } 
	                	    }
	                	});
	                	
	                }
	            }]
	        
			} ],
			height : '80%',
			width : '100%',
			tbar:[
			  {
				fieldLabel:'图片类型',
				labelWidth:80,
			     xtype : 'combo',  
			     mode : 'local',
			     id:"type",
			     triggerAction : 'all',
			     emptyText : '请选择图片类型',
			     store :bannerTypeStore ,
			     valueField : 'type',
			     displayField : 'name',
			     listeners : {
						specialkey : function(thisBtn, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
								 reloadBanner();
							}
						}
					}
			},{
				fieldLabel:'轮播图状态',
				labelWidth:80,
			     xtype : 'combo',  
			     mode : 'local',
			     id:"banner_status",
			     triggerAction : 'all',
			     emptyText : '轮播图状态',
			     store :banner_statusStore ,
			     valueField : 'banner_status',
			     displayField : 'name',
			     listeners : {
						specialkey : function(thisBtn, e) {
							if (e.getKey() == Ext.EventObject.ENTER) {
								reloadBanner();
							}
						}
					}
			},{  
	        	text:'查询',  
	            handler:function (){  
	            	reloadBanner();
	            }  
	        },{  
	        	text:'新增', 
	        	width:50,
	            handler:function (){  
	            	newWin.show();
	            }  
	        }], 
			bbar : [ {
				xtype : 'pagingtoolbar',
				store : bannerDataStore, // same store GridPanel is using
				dock : 'bottom',
				displayInfo : true,
				beforePageText:'第',
				afterPageText:'页',
				inputItemWidth:50,
				displayMsg: '第{0}条到第{1}条，一共{2}条记录',
				emptyMsg : '没有记录'
			} ]
	 });
	 
	 centerPanel.add(gridPanel);
	 
	//新增轮播图
	var newWin =  createWin("add_banner_win","新增轮播图",bannerTypeStore,com.hjh.banner.banner,bannerDataStore,"0");
	
	//修改轮播图
	var update_win =  createWin("update_banner_win","修改轮播图",bannerTypeStore,com.hjh.banner.banner,bannerDataStore,"1");
		
}

//创建window
function createWin(winID,winTitle,bannerTypeStore,banner_path,bannerDataStore,flag){
	var newWin = Ext.create('Ext.window.Window', {
		title : winTitle,
		id : winID,
		width : 350,
		// height : 120,
		// plain : true,
		iconCls : "addicon",
		// 不可以随意改变大小
		resizable : false,
		// 是否可以拖动
		// draggable:false,
		closeAction : 'close',
		closable : true,
		// 弹出模态窗体
		modal : 'true',
		buttonAlign : "center",
		bodyStyle : "padding:0 0 0 0",
		items : [createForm(winID,banner_path,bannerTypeStore,bannerDataStore,flag)]
	});
	return newWin;
}

//创建form Panel
function createForm(winID,banner_path,bannerTypeStore,bannerDataStore,flag){
	var new_winForm = Ext.create('Ext.form.Panel', {
		frame : true, // frame属性
		width : 340,
		bodyPadding : 5,
		// renderTo:"panel21",
		fieldDefaults : {
			labelAlign : 'left',
			labelWidth : 90,
			anchor : '100%'
		},
		items : [{
			xtype : 'textfield', // 3
			name : 'banner_id',
			fieldLabel : 'banner_id',
			readOnly : true,
			hidden : true
		},{
			xtype : 'textfield', // 3
			name : 'status',
			fieldLabel : 'status',
			readOnly : true,
			hidden : true
		},{
			xtype : 'radiogroup',
			fieldLabel : '是否为占位图',
			layout : 'hbox',
			name : 'type',
			items : [{
				boxLabel : '否',
				width:80,
				checked : true,
				inputValue : 0
			}, {
				boxLabel : '是',
				inputValue : 1
			}]
		},
		{
			xtype : 'filefield',
			fieldLabel : '轮播图'+'<span style="color: rgb(255, 0, 0); padding-left: 2px;">*</span>',
			name : 'banner_path',
			allowBlank : false,
			buttonText : '请选择图片',
			blankText : '请上传文件',
			emptyText : '请选择图片',
			anchor : '90%',
			listeners : {
				change : function(btn, value) {

					var file = btn.fileInputEl.dom.files[0];
					if (file.size / 1024 / 1024 > 1) {
						var form = this.ownerLayout.owner;
						var filefield = form.items.items[2];
						filefield.setRawValue('');
						Ext.Msg.alert('警告', "亲，图片太大了，请处理一下,别大于1M。");
					}
					var reader = new FileReader();
					reader.readAsDataURL(file);
					reader.onload = function(e) {
						banner_path = this.result;
					}
				}
			}
		},
		
		{
			// 输入文本框
			xtype : 'numberfield', // 3
			name : 'sort',
			fieldLabel : '轮播图顺序',
			value: '1',
			allowBlank : true,
			maxLength: 50 ,
			allowDecimals:false ,
			emptyText : '轮播图顺序(只能输入整数)'
		}, {
			xtype : 'radiogroup',
			fieldLabel : '是否跳转',
			layout : 'hbox',
			name : 'is_skip',
			items : [{
				boxLabel : '否',
				width:80,
				inputValue : 0
			}, {
				boxLabel : '是',
				checked : true,
				inputValue : 1
			}]
		}, {  
            xtype: 'textfield',  
            name : 'app_url',  
            fieldLabel: 'APP跳转地址',  
        },{
			// 多行文本输入框
			xtype : 'textareafield', // 5
			name : 'remark',
			maxLength: 200 ,
			maxLengthText:'备注不能超过200',
			fieldLabel : '备注',
			value : ''
		},{
            //显示文本框，相当于label
            xtype: 'displayfield', //2
            name: 'displayfield1',
            value: '带'+'<span style="color: rgb(255, 0, 0);">*</span>'+'的为必填项'
           
        }],
		buttons:[{
			xtype: 'button', //5
		    text: '保存',
		    handler: function(thisBtn, b, c, d) {
		    	this.disable();
				var btn = this;
		    	var url = '/addBanner';
		    	if(flag == '1'){//0:新增；1:修改
		    		url = '/updateBanner';
		    	}
				Ext.Ajax.request({
					url : url,
					method : 'POST',
					jsonData : {
						access_token : localStorage.access_token,
						banner_id: new_winForm.getForm().findField('banner_id').lastValue,
						type : new_winForm.getForm().findField('type').getValue().type,
					    banner_path : banner_path,
					    sort : new_winForm.getForm().findField('sort').lastValue,
					    is_skip : new_winForm.getForm().findField('is_skip').getValue().is_skip,
						remark : new_winForm.getForm().findField('remark').lastValue,
						app_url:new_winForm.getForm().findField('app_url').lastValue,
						status:new_winForm.getForm().findField('status').lastValue
						
					},
					success : function(response, opts) {
						btn.enable();
						var o = Ext.util.JSON.decode(response.responseText);
						/*alert(o.error_info);*/
						if(o.error_no == '0'){
							if(flag == '0'){
								Ext.Msg.alert('提示', '轮播图新增成功！');
							}else{
								Ext.Msg.alert('提示', '轮播图修改成功！');
							}
							
							new_winForm.reset();
							var win = Ext.getCmp(winID);
							banner_path = null;
							win.close();
							bannerDataStore.load();
						}else{
							Ext.Msg.alert('提示', o.error_info);
						}
						
					},
					fail : function(response, options) {
						btn.enable();
						var o = Ext.util.JSON.decode(response.responseText); 
						Ext.Msg.alert('提示', o.error_info);
		
						bannerDataStore.load();
					},
					})
		    },
		    formBind:true
		},{
			 xtype: 'button', //5
		    text: '关闭',
		    align:'center',
		    handler: function() {
			     new_winForm.reset();
			     var win = Ext.getCmp(winID);
			     win.close();
		    }
		}]
	});

	return new_winForm;
}

//加载数据
function reloadBanner() {
	var bannerGrid = Ext.getCmp("banner");
	var bannerDataStore = bannerGrid.getStore();
	bannerDataStore.loadPage(1);
	var type =  Ext.getCmp('type');
	var status =  Ext.getCmp('banner_status');
	var proxy = bannerDataStore.getProxy();
	proxy.extraParams = 
	{ 
	  		 access_token : localStorage.access_token,
	  		 type:type.lastValue,
	  		 status:status.lastValue
	   };
	bannerDataStore.load();
	
}