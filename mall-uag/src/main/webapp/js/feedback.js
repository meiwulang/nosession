function createFeedback() {
	overrideStoreProxy();
	debugger;
	var centerPanel = Ext.getCmp('centerPanel');
	Ext.define('myGrid', {
		extend : 'Ext.data.Model',
		fields : [ {
			name : 'feedback_id',
			type : 'string'
		}, {
			name : 'type',
			type : 'string'
		}, {
			name : 'content',
			type : 'string'
		}, {
			name : 'contact',
			type : 'string'
		}, {
			name : 'remark',
			type : 'string'
		}, 
		 {
			name : 'create_user',
			type : 'string'
		},		
		{
			name : 'reply_content',
			type : 'string'
		}, {
			name : 'reply_user',
			type : 'string'
		}, {
			name : 'reply_date',
			type : 'string'
		}, {
			name : 'status',
			type : 'string'
		}
		
		
		]
	});
	
	var feedbacklistStore = Ext.create('Ext.data.Store', {//意见反馈列表
		model : 'myGrid',
		proxy : {
			type : 'ajax',
			url : '/json/900602',
			headers : {
				"Content-Type" : "application/json;charset=utf-8"
			},
			actionMethods : {
				read : 'POST'
			},
			jsonData : true,
			extraParams : {
				access_token : localStorage.access_token,
				dictionary_type : '0003'
			},
			reader : {
				type : 'json',
				totalProperty : "totol_num",
				rootProperty : 'result_list'
			}
		},
		autoLoad : true
	});
	var myStoreStatus = Ext.create('Ext.data.Store', {//启用状态
	    fields: ['clumn', 'valueid'],
	    data : [
	        {"clumn":"用户反馈", "valueid":"1"},
	        {"clumn":"已回复", "valueid":"2"}
	    ],
		autoLoad : true
	});
	
	var myStoreTypes = Ext.create('Ext.data.Store', {//类别
	    fields: ['clumn', 'valueid'],
	    data : [
	        {"clumn":"产品建议", "valueid":"0"},
	        {"clumn":"程序问题", "valueid":"1"},
	        {"clumn":"其他", "valueid":"2"}
	    ],
		autoLoad : true
	});
	
	
	
	
	var gridPanel = Ext.create('Ext.grid.Panel', {
		viewConfig:{
		    enableTextSelection:true //可以复制单元格文字
		},
		forceFit: true, //列表宽度自适应
		enableColumnMove:false,// 禁止column移动,移动的话cellclick会出问题
		store : feedbacklistStore,
		title:'意见反馈',
		id : 'feedbackControl',
		columnLines: true,

		
		
		columns : [ {
			text : '序号',
			xtype : "rownumberer",
			align:'center',
			minWidth : 50
		},{
			text : 'ID',
			dataIndex : 'feedback_id',align:'center',
			minWidth : 130
		}, {
			text : '类别',
			dataIndex : 'type',
			renderer : function(value, cellmeta) {
				return showTypes(value);
			},
			align:'center',
			minWidth : 100,
		},
		{
			text : '反馈内容',
			dataIndex : 'content',align:'center',
			minWidth : 500,
			renderer: function(value, meta, record) {
				 meta.style = 'white-space:normal;word-break:break-all;';
		         return value;
		    }
		},
		{
			text : '状态',
			renderer : function(value, cellmeta) {
				return showStatus(value);
			},
			dataIndex : 'status',
			align:'center',
			minWidth : 100
		},
		{
			text : '反馈人',
			dataIndex : 'nick_name',
			align:'center',
			minWidth : 100
		},
//		{
//			text : '反馈人',
//			dataIndex : 'create_user',
//			align:'center',
//			minWidth : 100
//		},
		{
			text : '反馈人手机',
			dataIndex : 'contact',
			align:'center',
			minWidth : 100
		},
		{
			text : '反馈时间',
			dataIndex : 'create_date',align:'center',
			minWidth : 150
		},
		{
			xtype:'actioncolumn',
            text : '操作',
            width:'12%',
            align:'center',
            items: [{
                iconCls:'itemReplay',
                getClass: function(v, metaData, record) {
                	if(record.data.status == "1"){
                		return 'itemReplay';
                	}else{
                		return 'itemReplayed';
                	}
                	
                },
                tooltip: 'update',
                handler: function(grid, rowIndex, colIndex,a,b,c) {
                	grid.getSelectionModel().deselectAll();
                	grid.getSelectionModel().select(rowIndex);
                	var feedback_id = replyPanel.getForm().findField('feedback_id');// 反馈信息ID
                	var reply_content = replyPanel.getForm().findField('reply_content');// 反馈内容
                	
                	feedback_id.setValue(c.data.feedback_id);
                	reply_content.setValue(c.data.reply_content);
                	feedbackReplyWin.show();
                }
            },{
                iconCls:'itemIcon3',
                tooltip: 'Delete',
                handler: function(grid, rowIndex, colIndex,a,b,c) {
                	grid.getSelectionModel().deselectAll();
                	grid.getSelectionModel().select(rowIndex);
                	Ext.Msg.show({
                	    title:'提示',
                	    message: '确定要删除该成员信息吗？',
                	    buttons: Ext.Msg.YESNO,
                	    icon: Ext.Msg.QUESTION,
                	    fn: function(btn) {
                	        if (btn === 'yes') {
                	        	Ext.Ajax.request({
                    				url : '/json/900604',
                    				method : 'POST',
                    				jsonData : {
                    					access_token : localStorage.access_token,
                    					feedback_id : c.data.feedback_id
                    				},
                    				success : function(response, opts) {
                    					var o = Ext.util.JSON.decode(response.responseText);
                    					Ext.Msg.alert('提示', o.error_info);
                    					feedbacklistStore.load();
                    				},
                    				fail : function(response, options) {
                    					var o = Ext.util.JSON.decode(response.responseText); 
                    					Ext.Msg.alert('提示', o.error_info);
                    					feedbacklistStore.load();
                    				},
                    			})
                	        } else if (btn === 'no') {
                	           this.close();
                	        } 
                	    }
                	});
                	
                }
            }]
		}],
		height : '80%',
		width : '100%',
		tbar : [
				{
									fieldLabel : '',
									labelWidth : 50,
									xtype : 'combo',
									name : 'genus',
									mode : 'local',
									triggerAction : 'all',
									emptyText : '请选择',
									store : myStoreStatus,
									hiddenName : 'genus',
									valueField : 'valueid',
									displayField : 'clumn',
									editable : true,
									listeners : {
										specialkey : function(thisBtn, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												 reloadFeedback(thisBtn);
											}
										}
									}
								},
								{
									fieldLabel : '',
									labelWidth : 50,
									xtype : 'combo',
									name : 'province_id',
									mode : 'local',
									triggerAction : 'all',
									emptyText : '请选择',
									store : myStoreTypes,
									hiddenName : 'genus',
									valueField : 'valueid',
									displayField : 'clumn',
									editable : true,
									listeners : {
										specialkey : function(thisBtn, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												 reloadFeedback(thisBtn);
											}
										}
									}
								},
								{
									fieldLabel : 'id',
									labelWidth : 50,
									emptyText : '请输入反馈信息ID',
									xtype : "textfield",
									listeners : {
										specialkey : function(thisBtn, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												 reloadFeedback(thisBtn);
											}
										}
									}
								},
								{
									fieldLabel : '',
									labelWidth : 50,
									emptyText : '请输入反馈内容关键字',
									xtype : "textfield",
									listeners : {
										specialkey : function(thisBtn, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												 reloadFeedback(thisBtn);
											}
										}
									}
								},
								{
									text : '查询',
									handler : function(a, b, c, d, e) {
										
										reloadFeedback(a);
									
									}
								}], // 顶部工具栏
		bbar : [ {
			xtype : 'pagingtoolbar',
			store : feedbacklistStore, // same store GridPanel is using
			dock : 'bottom',
			inputItemWidth:50,
			displayInfo : true,
			displayMsg : '<font size=2>第{0}条到第{1}条，一共{2}条记录</font>'
		} ]
	});
	
	var replyPanel = Ext.create('Ext.form.Panel', {//修改频道panel
		frame : true, // frame属性
		// title: 'Form Fields',
		width : 700,
		bodyPadding : 5,
		// renderTo:"panel21",
		fieldDefaults : {
			labelAlign : 'left',
			labelWidth : 90,
			anchor : '100%'
		},
		items : [  
		{
			// 输入文本框
			xtype: "textarea", // 3
			name : 'reply_content',
			fieldLabel : '回复信息',
			// value: '输入文本框',		
			allowBlank : false,
			blankText : "回复内容",
            labelSepartor: "：",
            labelWidth: 70,
            width: 550,
            height: 330
		},
        {
			// 隐藏文本框channel_id
			xtype : 'textfield', // 3
			name : 'feedback_id',
			hidden: true,
		},
		{
		 xtype: 'button', //5
         text: '保存',
         handler: function() {
        	 this.disable();
				var btn = this;
		 Ext.Ajax.request({
			url : '/json/900603',
			method : 'POST',
			jsonData : {
				feedback_id:replyPanel.getForm()
				.findField('feedback_id').lastValue,
				reply_content : replyPanel.getForm()
						.findField('reply_content').lastValue,
			},
			success : function(response, opts) {
				btn.enable();
				var o = Ext.util.JSON
				.decode(response.responseText);
				alert(o.error_info);
				feedbackReplyWin.close();
				feedbacklistStore.load();
			},
			fail : function(response, options) {
				btn.enable();
				var o = Ext.util.JSON
				.decode(response.responseText); 
				alert(o.error_info);
				feedbacklistStore.load();
			},
			})
         },
         formBind:true
     },{
		 xtype: 'button', //5
         text: '取消',
         align:'center',
         handler: function() {
        	 feedbackReplyWin.close();
         }
     }
     ]
	});
	
	var feedbackReplyWin = Ext.create('Ext.window.Window', {
		title : "回复",
		width : 600,
		hight : 380,
		// height : 120,
		// plain : true,
		iconCls : "addicon",
		// 不可以随意改变大小
		resizable : false,
		// 是否可以拖动
		draggable:true,
		closeAction : 'close',
		closable : true,
		autoLoad : true,
		// 弹出模态窗体
		modal : 'true',
		buttonAlign : "center",
		bodyStyle : "padding:0 0 0 0",
		items : [replyPanel]
	});
	
	centerPanel.add(gridPanel);	
	
}


function showStatus(value){
	if(value.trim()=="1"){
		var returnStr = "用户反馈";
		return returnStr;
	}else if(value.trim()=="2"){
		var returnStr = "已回复";
		return returnStr;
	}else{
		return "无";
	}
}
function showTypes(value){
	if(value.trim()=="0"){
		var returnStr = "产品建议";
		return returnStr;
	}else if(value.trim()=="1"){
		var returnStr = "程序问题";
		return returnStr;
	}else if(value.trim()=="2"){
		var returnStr = "其他";
		return returnStr;
	}else{
		return "无";
	}	
}

// tbar刷新
function reloadFeedback(a){
	var status = a.ownerLayout.owner.items.items[0];
	var statusval=status.lastValue;
	
	var type = a.ownerLayout.owner.items.items[1];
	var typeval=type.lastValue;
	
	var feedback_id = a.ownerLayout.owner.items.items[2];
	var content = a.ownerLayout.owner.items.items[3];
	var feedbackControl = Ext.getCmp("feedbackControl");
	var feedbacklistStore = feedbackControl.getStore();
	var proxy = feedbacklistStore.getProxy();
	proxy.extraParams = {
		feedback_id : feedback_id.lastValue,
		content:content.lastValue,
		status : statusval,
		access_token : localStorage.access_token,
		type : typeval
		
	};
	feedbacklistStore.load();
}