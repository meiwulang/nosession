function createInvitation() {
	overrideStoreProxy();
	var centerPanel = Ext.getCmp('centerPanel');
	Ext.define('myGrid', {
		extend : 'Ext.data.Model',
		fields : [ {
			name : 'intivate_id',
			type : 'string'
		}, {
			name : 'intivate_customer_name',
			type : 'string'
		},
		{
			name : 'intivate_code',
			type : 'string'
		},
		{
			name : 'init_date',
			type : 'string'
		},
		{
			name : 'status',
			type : 'string'
		}, 
		{
			name : 'init_time',
			type : 'string'
		}, {
			name : 'create_user',
			type : 'string'
		}]
	});
	
	var intivateStore = Ext.create('Ext.data.Store', {
		model : 'myGrid',
		pageSize : 25,
		proxy : {
			type : 'ajax',
			url : '/invitation_query',
			headers : {
				"Content-Type" : "application/json;charset=utf-8"
			},
			actionMethods : {
				read : 'POST'
			},
			jsonData : true,
			extraParams : {
				//access_token : localStorage.access_token,
				//news_content_id : 1234567890123456
			},
			reader : {
				type : 'json',
				rootProperty : 'result_list',
				totalProperty : 'total_num',
			}
		},
		autoLoad : true
	});

	
	var myStoreStatus = Ext.create('Ext.data.Store', {//启用状态
	    fields: ['clumn', 'valueid'],
	    data : [
	        {"clumn":"生效", "valueid":"1"},
	        {"clumn":"失效", "valueid":"2"}
	    ],
		autoLoad : true
	});
	

	
	
	var gridPanel = Ext.create('Ext.grid.Panel', {
		viewConfig:{
		    enableTextSelection:true //可以复制单元格文字
		},
		enableColumnMove:false,// 禁止column移动,移动的话cellclick会出问题
		forceFit: true, //列表宽度自适应 
		store : intivateStore,
		title:'邀请码管理',
		id : 'invitation',
		columnLines: true,
		columns : [ {
			text : '序号',
			xtype : "rownumberer",
			align:'center',
			minWidth : 50
		}, {
			text : '名称',
			dataIndex : 'invite_customer_name',
			algin:'left',
			minWidth : 100,
			align : 'center'
		},
		{
			text : '备注',
			dataIndex : 'remark',align:'center',
			minWidth : 130,
			align : 'center'
		},
		{
			text : '邀请码',
			dataIndex : 'invite_code',align:'center',
			minWidth : 100,
			align : 'center'
		},
		{
			text : '会员数',
			dataIndex : 'client_num',align:'center',
			minWidth : 150,
			align : 'center'
		},
	
		{	
			
			/*text : "操作",
			dataIndex : 'handle',
			align : "center",
			minWidth : 150,
			renderer : function(value, cellmeta) {
			var returnStr = "<INPUT type='button' value='查看'>   <INPUT type='button' value='编辑'>   <INPUT type='button' value='删除'>";
			return returnStr;*/
            xtype:'actioncolumn',
            text : '操作',
            width:'12%',
            align:'center',
            items: [{
                iconCls:'itemIcon4',
                tooltip: 'update',
                handler: function(grid, rowIndex, colIndex,a,b,c) {
                	var invite_id = editPanel.getForm().findField('invite_id');// 邀请码ID
                	var invite_customer_name = editPanel.getForm().findField('invite_customer_name');// 邀请码名称
                	var remark = editPanel.getForm().findField('remark');//备注
                	var invite_code = editPanel.getForm().findField('invite_code');//邀请码
                
                	invite_code.setValue(c.data.invite_code);
                	invite_id.setValue(c.data.invite_id);
                	invite_customer_name.setValue(c.data.invite_customer_name);
                	remark.setValue(c.data.remark);
                	inviteEditWin.show();
                }
            },{
                iconCls:'itemIcon3',
                tooltip: 'Delete',
                handler: function(grid, rowIndex, colIndex,a,b,c) {
                	Ext.Msg.show({
                	    title:'提示',
                	    message: '确定要删除该成员信息吗？',
                	    buttons: Ext.Msg.YESNO,
                	    icon: Ext.Msg.QUESTION,
                	    fn: function(btn) {
                	        if (btn === 'yes') {
                	        	Ext.Ajax.request({
                    				url : '/invitation_del',
                    				method : 'POST',
                    				jsonData : {
                    					invite_id : c.data.invite_id
                    				},
                    				success : function(response, opts) {
                    					var o = Ext.util.JSON.decode(response.responseText);
                    					if(o.error_no=='0'){
                    						Ext.Msg.alert('提示', o.error_info);
                    					}else{
                    						Ext.Msg.alert('提示', o.error_no);
                    					}
                    					
                    					intivateStore.load();
                    				},
                    				fail : function(response, options) {
                    					var o = Ext.util.JSON.decode(response.responseText);
                    					Ext.Msg.alert('提示', o.error_info);
                    					intivateStore.load();
                    				},
                    			})
                	        } else if (btn === 'no') {
                	           this.close();
                	        } 
                	    }
                	});
                	
                }
            }]

		} ],
		height : '80%',
		width : '100%',
		tbar : [
								{
									fieldLabel : '名称',
									labelWidth : 50,
									name : 'name',
									xtype : "textfield",
									listeners : {
										specialkey : function(thisBtn, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												reload(thisBtn);
											}
										}
									}
								},
								{
									fieldLabel : '邀请码',
									labelWidth : 50,
									name : 'invite_code',
									xtype : "textfield",
									listeners : {
										specialkey : function(thisBtn, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												reload(thisBtn);
											}
										}
									}
								},
								{
									fieldLabel : '备注',
									labelWidth : 50,
									name : 'remark',
									xtype : "textfield",
									listeners : {
										specialkey : function(thisBtn, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												reload(thisBtn);
											}
										}
									}
								},
								{
									fieldLabel : '区间输入',
									labelWidth : 80,
									name : 'invite_code_begin',
									xtype : "textfield",
									listeners : {
										specialkey : function(thisBtn, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												reload(thisBtn);
											}
										}
									}
								},'--'
								,
								{
									labelWidth : 50,
									name : 'invite_code_end',
									xtype : "textfield",
									listeners : {
										specialkey : function(thisBtn, e) {
											if (e.getKey() == Ext.EventObject.ENTER) {
												reload(thisBtn);
											}
										}
									}
								},
								{
									text : '查询',
									handler : function(a, b, c, d, e) {
										reload(a);
									}
								},
								{
									text : '导出',
									handler : function(a, b, c, d, e) {
										
										var invite_customer_name = a.ownerLayout.owner.items.items[0];
										var invite_code = a.ownerLayout.owner.items.items[1];
										var remark = a.ownerLayout.owner.items.items[2];
										var invite_code_start = a.ownerLayout.owner.items.items[3];
										var invite_code_end = a.ownerLayout.owner.items.items[5];
										//var proxy = intivateStore.getProxy();
										debugger;
										window.location = '/invitation_import?invite_customer_name='+invite_customer_name.value
										+'&invite_code='+ invite_code.value
										+'&remark='+ remark.value
										+'&invite_code_start='+invite_code_start.value
										+'&invite_code_end='+ invite_code_end.value;
									}
								}
								, '->', {
									text : '添加',
									handler : function() {
										inviteAddWin.show();
									}
								}
								
								
								], // 顶部工具栏
		bbar : [ {
			xtype : 'pagingtoolbar',
			store : intivateStore, // same store GridPanel is using
			dock : 'bottom',
			displayInfo : true,
			inputItemWidth:50,
			displayMsg : '<font size=2>第{0}条到第{1}条，一共{2}条记录</font>'
		} ]
	});
	function reload(a){
		var invite_customer_name = a.ownerLayout.owner.items.items[0];
		var invite_code = a.ownerLayout.owner.items.items[1];
		var remark = a.ownerLayout.owner.items.items[2];
		var invite_code_start = a.ownerLayout.owner.items.items[3];
		var invite_code_end = a.ownerLayout.owner.items.items[5];
		var proxy = intivateStore.getProxy();
		proxy.extraParams = {
			invite_customer_name : invite_customer_name.value,
			invite_code : invite_code.value,
			remark : remark.value,
			invite_code_start:invite_code_start.value,
			invite_code_end : invite_code_end.value	
		};
		intivateStore.loadPage(1);
		intivateStore.load();
	}	
	var addPanel = Ext.create('Ext.form.Panel', {//添加邀请码panel
		frame : true, // frame属性
		// title: 'Form Fields',
		width : '100%',
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
			xtype : 'textfield', // 3
			name : 'invite_customer_name',
			fieldLabel : '名称',
			// value: '输入文本框',		
			allowBlank : false,
			blankText : "名称"
		},
		{  
            xtype: 'textarea',  
            name: 'remark',  
            fieldLabel: '备注',  
            flex: 20  
        } ,
        {  
            xtype: 'textfield',  
            name: 'invite_code',  
            fieldLabel: '邀请码',  
            flex: 20  
        } ,
		{
		 xtype: 'button', //5
         text: '保存',
         handler: function() {
        	 debugger;
		 Ext.Ajax.request({
			url : '/invitation_add',
			method : 'POST',
			jsonData : {
				invite_customer_name : addPanel.getForm()
						.findField('invite_customer_name').lastValue,
				invite_code :  addPanel.getForm().findField(
				'invite_code').lastValue,
				remark :  addPanel.getForm().findField(
				'remark').lastValue,
			},
			success : function(response, opts) {
				var o = Ext.util.JSON
				.decode(response.responseText);
				if(o.error_no!=0){
					Ext.MessageBox.alert("提示框",o.error_no);
				}else{
					Ext.MessageBox.alert("提示框",o.error_info);
					addPanel.reset();
					intivateStore.load();	
					inviteAddWin.close();
				}
				
			},
			fail : function(response, options) {
				var o = Ext.util.JSON
				.decode(response.responseText); 
				alert(o.error_info);
			},
			})
         },
         formBind:true
     },{
		 xtype: 'button', //5
         text: '取消',
         align:'center',
         handler: function() {
        	 inviteAddWin.close();
         }
     }
     ]
	});
	
	var editPanel = Ext.create('Ext.form.Panel', {//修改邀请码panel
		frame : true, // frame属性
		// title: 'Form Fields',
		width : '100%',
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
			xtype : 'textfield', // 3
			name : 'invite_customer_name',
			fieldLabel : '名称',
			// value: '输入文本框',		
			allowBlank : false,
			blankText : "名称"
		},
		{  
            xtype: 'textfield',  
            name: 'remark',  
            fieldLabel: '备注',  
            flex: 20  
        } ,
        {  
            xtype: 'textfield',  
            name: 'invite_code',  
            fieldLabel: '邀请码',
            disabled: true,
            flex: 20  
        } ,
        {
			// 隐藏文本框invite_id
			xtype : 'textarea', // 3
			name : 'invite_id',
			hidden: true,
		},
		{
		 xtype: 'button', //5
         text: '保存',
         handler: function() {

		 Ext.Ajax.request({
			url : '/invitation_edit',
			method : 'POST',
			jsonData : {
				invite_id:editPanel.getForm()
				.findField('invite_id').lastValue,
				invite_customer_name : editPanel.getForm()
						.findField('invite_customer_name').lastValue,
				invite_code :  editPanel.getForm().findField(
				'invite_code').lastValue,
				remark :  editPanel.getForm().findField(
				'remark').lastValue,
			},
			success : function(response, opts) {
				var o = Ext.util.JSON
				.decode(response.responseText);
				alert(o.error_info);
				editPanel.reset();
				intivateStore.load();

				inviteEditWin.close();	
			},
			fail : function(response, options) {
				var o = Ext.util.JSON
				.decode(response.responseText); 
				alert(o.error_info);
			},
			})
         },
         formBind:true
     },{
		 xtype: 'button', //5
         text: '取消',
         align:'center',
         handler: function() {
        	 inviteEditWin.close();
         }
     }
     ]
	});
	
	var inviteAddWin = Ext.create('Ext.window.Window', {
		layout:'vbox',
		title : "添加邀请码",
		width : 500,
		hight : 800,
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
		items : [addPanel]
	});
	inviteAddWin.on("beforeClose", function() {
		addPanel.reset();
	});
	
	var inviteEditWin = Ext.create('Ext.window.Window', {
		layout:'vbox',
		title : "修改邀请码",
		width : 500,
		hight : 800,
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
		items : [editPanel]
	});
	
	centerPanel.add(gridPanel);	
};
function dicID(value){
	if(value.trim()!=""){
		var returnStr = "<INPUT type='button' value='查看附件'>";
		return returnStr;
	}else{
		return "无";
	}
}