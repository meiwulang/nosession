function createUserCenter() {
	overrideStoreProxy();
	
	var centerPanel = Ext.getCmp('centerPanel');
//	 Ext.define('myGrid', {
//	     extend: 'Ext.data.Model',
//	     fields: [
//	         {name: 'client_id',  type: 'string'},
//	         {name: 'nick_name',  type: 'string'},
//	         {name: 'mobile_tel',       type: 'string'},
//	         {name: 'sex',  type: 'string'},
//	         {name: 'address', type: 'string'},
//	         {name: 'status',  type: 'string'}
//	     ]
//	 });
	 
	 var province_store = new Ext.data.JsonStore({  
		    autoLoad: true,  
		    proxy : { 
		        type : "ajax", 
		        url :  '../js/AreaData.json', 
		        reader : { 
		            type : "json", 
		            root : "data"
		        } 
		    } ,
		    fields: ["id", "name"]
	 }); 
	 var city_store = new Ext.data.JsonStore({  
		 autoLoad: true,  
		 fields: ["id", "name"]
	 }); 
	 
	 
	//账号状态Store
	 var client_statusStore = Ext.create('Ext.data.Store', {
		 	fields: ["client_status", "name"],
		    data: [
                { client_status: "", name: "全部" },
		        { client_status: "0", name: "禁用" },
		        { client_status: "1", name: "启用" }
		    ]
	 });
	 
	//性别store
	 var sexStore = Ext.create('Ext.data.Store', {
		 	fields: ["sex_type", "name"],
		    data: [
	            { sex_type: "", name: "全部" },
		        { sex_type: "1", name: "男" },
		        { sex_type: "0", name: "女" }
		    ]
	 });
	 
	 //成员列表store
	 var clientDataStore = Ext.create('Ext.data.Store', {
	     //model: 'myGrid',
	     pageSize:15,
	     proxy: {
	         type: 'ajax',
	         url: '/getClientList',
	         headers:{"Content-Type":"application/json;charset=utf-8"},
	         actionMethods: {
	             read: 'POST'
	         },
	         jsonData: true, 
	         extraParams :{access_token : localStorage.access_token
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
		enableColumnMove:false,// 禁止column移动,移动的话cellclick会出问题
		store : clientDataStore,
		id : 'userCenter',
//		forceFit : true, // 列表宽度自适应
		viewConfig : {
		    forceFit : true, //让grid的列自动填满grid的整个宽度，不用一列一列的设定宽度。
		    enableTextSelection:true //可以复制单元格文字
		},
		title:'会员管理',
		columnLines: true,
		columns : [ 
		   { text : '序号',xtype: "rownumberer" ,width:50,align:'center'}, 
		   { text : '用户编号',dataIndex : 'client_code',align:'center',width:120},   
		  {
			text : '昵称',
			dataIndex : 'nick_name',align:'center',
			width:150
		}, {
			text : '注册手机号',
			dataIndex : 'mobile_tel',align:'center',
			width:150
		}, {
			text : '性别',
			dataIndex : 'sex',align:'center'
		}
		, {
			text : '用户所属地区',
			dataIndex : 'address',align:'center',
			width:200
		} , {
			text : '单位名称',
			dataIndex : 'enterprise_name',align:'center',
			width:250
		}, {
			text : '单位地址',
			dataIndex : 'enterprise_address',align:'center',
			width:350,
//			renderer: function(value, meta, record) {
//                meta.style = 'overflow:auto;padding: 3px 6px;text-overflow: ellipsis;white-space: nowrap;white-space:normal;line-height:20px;';   
//                return value;   
//            }
			
		}, {
			text : '单位联系人',
			dataIndex : 'enterprise_linkman',align:'center',
			width:150
		}, {
			text : '单位联系电话',
			dataIndex : 'enterprise_tel',align:'center',
			width:150
		}, {
			text : '单位主营',
			dataIndex : 'major_business',align:'center',
			width:150	
		}, {
			text : '注册时间',
			dataIndex : 'init_date',align:'center',
			width:200
		}, {
			text : '邀请码',
			dataIndex : 'invite_code',align:'center',
			width:150
		}, {
			text : '账号状态',
			dataIndex : 'status',align:'center'
		},{

            xtype:'actioncolumn',
            text : '操作',
            align:'center',
            items: [/*{
                iconCls:'itemIcon4',
                tooltip: 'update',
                handler: function(grid, rowIndex, colIndex,a,b,c) {
                	var client_id = update_winForm.getForm().findField('client_id');// 成员ID
                	var mobile_tel = update_winForm.getForm().findField('mobile_tel');// 成员ID
                	mobile_tel.setValue(c.data.mobile_tel);
                	var client_name = update_winForm.getForm().findField('nick_name');// 成员昵称
                	var province = update_winForm.getForm().findField('province');
                	var sex = update_winForm.getForm().findField('sex');
                	var sexValue = '';
                	if(c.data.sex == '男'){
                		sexValue = '1';
                	}
                	if(c.data.sex == '女'){
                		sexValue = '0';
                	}
                	sex.setValue(sexValue);
                	var city = update_winForm.getForm().findField('city');
                	if(c.data.area_code!='' && c.data.area_code != null && c.data.area_code!= undefined){
                		province.setValue(c.data.area_code.split(',')[0]);
                		city.setValue(c.data.area_code.split(',')[1]);
                	}else{
                		province.setValue('');
                		city.setValue('');
                	}
                	
                	client_id.setValue(c.data.client_id);
                	client_name.setValue(c.data.nick_name);
                	
                	syswin.show();
                }
            },*/{
                tooltip: 'update',
                getClass: function(v, metaData, record) {
                	if(record.data.status == "启用"){
                		return 'disabled';
                	}else{
                		return 'enable';
                	}
                	
                },
                handler: function(grid, rowIndex, colIndex,a,b,c) {
                	grid.getSelectionModel().deselectAll();
                	grid.getSelectionModel().select(rowIndex);
                	var client_id = c.data.client_id;
                	var status = '';
                	var info = '';
                	if(c.data.status == "启用"){
                		status = '0';
                		info = '禁用';
                	}else{
                		status = '1';
                		info = '启用';
                	}
                	Ext.Msg.show({
                	    title:'提示',
                	    message: '确定要'+info+'该用户吗？',
                	    buttons: Ext.Msg.YESNO,
                	    icon: Ext.Msg.QUESTION,
                	    fn: function(btn) {
                	        if (btn === 'yes') {
                	        	Ext.Ajax.request({
                    				url : '/updateClientStatus',
                    				method : 'POST',
                    				jsonData : {
                    					access_token : localStorage.access_token,
                    					client_id : client_id,
                    					mobile_tel:c.data.mobile_tel,
                    					status:status
                    				},
                    				success : function(response, opts) {
                    					var o = Ext.util.JSON.decode(response.responseText);
                    					if(o.error_no == '0'){
                    						Ext.Msg.alert('提示', info+'成功！');
                    						clientDataStore.load();
                    					}else{
                    						Ext.Msg.alert('提示', o.error_info);
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
            }/*,{
                iconCls:'resetPwd',
                tooltip: 'update',
                handler: function(grid, rowIndex, colIndex,a,b,c) {
                	Ext.Msg.show({
                	    title:'提示',
                	    message: '确定要重置该用户密码吗？',
                	    buttons: Ext.Msg.YESNO,
                	    icon: Ext.Msg.QUESTION,
                	    fn: function(btn) {
                	        if (btn === 'yes') {
                	        	Ext.Ajax.request({
                    				url : '/json/600115',
                    				method : 'POST',
                    				jsonData : {
                    					access_token : localStorage.access_token,
                    					client_id : c.data.client_id,
                    					mobile_tel:c.data.mobile_tel
                    				},
                    				success : function(response, opts) {
                    					var o = Ext.util.JSON.decode(response.responseText);
                    					if(o.error_no == '0'){
                    						Ext.Msg.alert('提示', '密码重置成功！');
                        					clientDataStore.load();
                    					}else{
                    						Ext.Msg.alert('提示', o.error_info);
                    					}
                    				},
                    				fail : function(response, options) {
                    					var o = Ext.util.JSON.decode(response.responseText); 
                    					Ext.Msg.alert('提示', o.error_info);
                    					clientDataStore.load();
                    				},
                    			})
                	        } else if (btn === 'no') {
                	           this.close();
                	        } 
                	    }
                	});
                	
                }
            }*/]
        
		} ],
		height : '80%',
		width : '100%',
		tbar:[
		  {
			fieldLabel:'账号状态',
			labelWidth:60,
		     xtype : 'combo',  
		     id : 'client_status',
		     mode : 'local',
		     triggerAction : 'all',
		     emptyText : '账号状态',
		     width:180,
		     store :client_statusStore ,
		     valueField : 'client_status',
		     displayField : 'name',editable:false,
		     listeners : {
					specialkey : function(thisBtn, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {
							 reloadUserCenter();
						}
					}
				}
		},{  
			fieldLabel:'邀请码',id:'invite_code',labelWidth:50,width:160,xtype:"textfield",
			listeners : {
				specialkey : function(thisBtn, e) {
					if (e.getKey() == Ext.EventObject.ENTER) {
						 reloadUserCenter();
					}
				}
			}
        },{  
			fieldLabel:'昵称',id:'userCenter_name',labelWidth:40,width:160,xtype:"textfield",
			listeners : {
				specialkey : function(thisBtn, e) {
					if (e.getKey() == Ext.EventObject.ENTER) {
						 reloadUserCenter();
					}
				}
			}
        },{  
			fieldLabel:'电话',id:'userCenter_tel',labelWidth:40,width:170,xtype:"textfield",
			listeners : {
				specialkey : function(thisBtn, e) {
					if (e.getKey() == Ext.EventObject.ENTER) {
						 reloadUserCenter();
					}
				}
			}
        }
        ,{  
			fieldLabel:'注册时间',id:'user_start_date',forceFit: true,xtype:"datefield",labelWidth:60,width:190,
			emptyText : "开始时间",
			format : "Y-m-d",// 日期的格式
			altFormats : "Y/m/d|Ymd",
			maxValue : new Date(),
			//editable : false
			listeners : {
				specialkey : function(thisBtn, e) {
					if (e.getKey() == Ext.EventObject.ENTER) {
						reloadUserCenter();
					}
				}
			}
		}
	  ,{  
		  	labelSeparator: '',fieldLabel:'&nbsp;&nbsp;-',id:'user_end_date',forceFit: true,xtype:"datefield",labelWidth:30,width:160,
			emptyText : "结束时间",
			format : "Y-m-d",// 日期的格式
			altFormats : "Y/m/d|Ymd",
			maxValue : new Date(),
			//editable : false
			listeners : {
				specialkey : function(thisBtn, e) {
					if (e.getKey() == Ext.EventObject.ENTER) {
						reloadUserCenter();
					}
				}
			}
		},{
			fieldLabel:'区间查询',id:'invite_code_start',labelWidth:60,width:180,xtype:"textfield",
			emptyText : "请输入邀请码",
			listeners : {
				specialkey : function(thisBtn, e) {
					if (e.getKey() == Ext.EventObject.ENTER) {
						 reloadUserCenter();
					}
				}
			}
		},
			{
				labelSeparator: '',fieldLabel:'&nbsp;&nbsp;-',id:'invite_code_end',labelWidth:30,width:150,xtype:"textfield",
				emptyText : "请输入邀请码",
				listeners : {
					specialkey : function(thisBtn, e) {
						if (e.getKey() == Ext.EventObject.ENTER) {
							 reloadUserCenter();
						}
					}
				}
			}
		
        ,{  
        	text:'查询',  
            handler:function (){  
            	
            	reloadUserCenter();
            	
            }  
        },{  
        	text:'导出', 
        	width:50,
            handler:function (){  
            	
            	 var status =  Ext.getCmp('client_status');
            	 var name =  Ext.getCmp('userCenter_name');
            	 var tel =  Ext.getCmp('userCenter_tel');
            	 var invite_code = Ext.getCmp('invite_code');
            	 var invite_code_start = Ext.getCmp('invite_code_start');
            	 var invite_code_end = Ext.getCmp('invite_code_end');
            	 
            	 var start_dateObj = Ext.getCmp('user_start_date').rawValue;
            	 var end_dateObj = Ext.getCmp('user_end_date').rawValue;
            	 var start_date = '';
            	 var end_date = '';
            	 if(start_dateObj){
            		 start_date=start_dateObj.replaceAll('-','');
            	 }
            	 if(end_dateObj){
            		 end_date=end_dateObj.replaceAll('-','');
            	 }
            	 
            	 
            	 window.location ='export_client_excel?access_token='+localStorage.access_token+'&invite_code='+invite_code.lastValue
            	 					+'&nick_name='+name.lastValue+'&status='+status.lastValue+'&mobile_tel='+tel.lastValue
            	 					+'&invite_code_start='+invite_code_start.lastValue
            	 					+'&invite_code_end='+invite_code_end.lastValue
            	 					+'&start_date='+start_date+'&end_date='+end_date;
            }  
        }], //顶部工具栏
		bbar : [ {
			xtype : 'pagingtoolbar',
			store : clientDataStore, // same store GridPanel is using
			dock : 'bottom',
			displayInfo : true,
			beforePageText:'第',
			inputItemWidth:50,
			afterPageText:'页',
			displayMsg: '第{0}条到第{1}条，一共{2}条记录',
			emptyMsg : '没有记录'
		} ]
	});
	centerPanel.add(gridPanel);
	var update_winForm = Ext.create('Ext.form.Panel', {
		frame : true, // frame属性
		// title: 'Form Fields',
		width : 340,
		bodyPadding : 5,
		// renderTo:"panel21",
		fieldDefaults : {
			labelAlign : 'left',
			labelWidth : 90,
			anchor : '100%'
		},
		items : [ {
			// 输入文本框
			xtype : 'textfield', // 3
			name : 'client_id',
			fieldLabel : '成员ID',
			// value: '输入文本框',
			allowBlank : false,
			hidden:true,
			emptyText : '',
			blankText : "提示"
		},  {
			// 输入文本框
			xtype : 'textfield', // 3
			name : 'mobile_tel',
			fieldLabel : '联系电话',
			// value: '输入文本框',
			allowBlank : false,
			hidden:true,
			emptyText : '',
			blankText : "提示"
		},{  
            xtype: 'textfield',  
            name : 'nick_name', 
            fieldLabel: '昵称',  
            allowBlank : false,
            blankText : "用户昵称不能为空",
            maxLength: 50 ,
			maxLengthText:'用户昵称不能超过50位'
         },{
			 fieldLabel:'性别',
		     xtype : 'combo',  
		     name : 'sex',
		     mode : 'local',
		     triggerAction : 'all',
		     emptyText : '请选择性别',
		     editable:false ,
		     store :sexStore ,
		     valueField : 'sex_type',
		     displayField : 'name'
		}
         ,
		{
			 fieldLabel:'省(直辖市)',
		     xtype : 'combo',  
		     name : 'province',
		     mode : 'local',
		     triggerAction : 'all',
		     emptyText : '请选择省份(直辖市)',
		     store :province_store ,
		     editable:false ,
		     valueField : 'id',
		     displayField : 'name',
	    	 listeners : {
	    		 change: function(btn, value,a,b,c,d) {
	    		     var city = update_winForm.getForm().findField('city');
	    		     if(btn.lastValue != null){
	    		    	 if(btn.lastSelection[0].data.child == null || btn.lastSelection[0].data.child == undefined){
		    		    	 city.setHidden(true);
		    		     }else{
		    		    	 city.setHidden(false);
		    		    	 city.clearValue();
		    		    	 //city.clearRawValue();
							 var cityStore=city.getStore();
							 cityStore.removeAll();
							 cityStore.setData(btn.lastSelection[0].data.child);
							 //cityStore.load();
		    		     }
	    		     }
                 }
             } 
		},{
			fieldLabel:'市(城区)',
		     xtype : 'combo',  
		     name : 'city',
		     mode : 'local',
		     editable:false ,
		     triggerAction : 'all',
		     emptyText : '请选择城市(城区)',
		     store :city_store ,
		     valueField : 'id',
		     displayField : 'name'
		}
     ],
     buttons:[{
		 xtype: 'button', //5
         text: '保存',
         handler: function() {
        	 	this.disable();
				var btn = this;
				var province = update_winForm.getForm().findField('province');
				var city = update_winForm.getForm().findField('city');
				var province_name = province.rawValue;//省份名称
				var province_code = province.lastValue;//省份编号
				var city_name = city.rawValue;//城市名称
				var city_code = city.lastValue;//城市编号
				var address = null;
				var areaCode = null;
				if(province_code != null){
					if(province.lastSelection[0].data.child == undefined || province.lastSelection[0].data.child == null){
						city_name = '';
						city_code = '0';
					}
					address = province_name+' '+city_name;
					areaCode = province_code+','+city_code;
				}
				
			Ext.Ajax.request({
				url : '/json/600107',
				method : 'POST',
				jsonData : {
					client_id : update_winForm.getForm().findField('client_id').lastValue,
					nick_name : update_winForm.getForm().findField('nick_name').lastValue.trim(),
					sex : update_winForm.getForm().findField('sex').lastValue,
					address:address,
					mobile_tel:update_winForm.getForm().findField('mobile_tel').lastValue,
					area_code:areaCode
				},
				success : function(response, opts) {
					btn.enable();
					var o = Ext.util.JSON.decode(response.responseText);
					if(o.error_no == '0'){
						Ext.Msg.alert('提示', '修改成功！');
						syswin.close();
						clientDataStore.load();
					}else{
						Ext.Msg.alert('提示', o.error_info);
					}
					
				},
				fail : function(response, options) {
					btn.enable();
					var o = Ext.util.JSON
					.decode(response.responseText); 
					Ext.Msg.alert('提示', o.error_info);
	
					clientDataStore.load();
				},
				})
	         },
	         formBind:true
     },{
		 xtype: 'button', //5
         text: '关闭',
         handler: function() {
        	 syswin.close();
         }
     }]
	});
	var syswin = Ext.create('Ext.window.Window', {
		title : "修改用户信息",
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
		items : [ update_winForm ]
	});
	
}
		
//tbar刷新
function reloadUserCenter(){
	var userCenter = Ext.getCmp("userCenter");
	var clientDataStore = userCenter.getStore();
	//var userCenter_id =  Ext.getCmp('userCenter_id');
	var status =  Ext.getCmp('client_status');
	 var name =  Ext.getCmp('userCenter_name');
	 var tel =  Ext.getCmp('userCenter_tel');
	 var invite_code = Ext.getCmp('invite_code');
	 var invite_code_start = Ext.getCmp('invite_code_start');
	 var invite_code_end = Ext.getCmp('invite_code_end');
	 var jsonData={ 
		 access_token : localStorage.access_token,
		 nick_name : name.lastValue.trim(),
		 mobile_tel:tel.lastValue.trim(),
		 //client_id:userCenter_id.lastValue.trim(),
		 status:status.lastValue,
		 invite_code:invite_code.lastValue,
		 invite_code_start:invite_code_start.lastValue,
		 invite_code_end:invite_code_end.lastValue
	 };
	 var start_dateObj = Ext.getCmp('user_start_date').rawValue;
	 var end_dateObj = Ext.getCmp('user_end_date').rawValue;
	 if(start_dateObj){
		 jsonData.start_date=start_dateObj.replaceAll('-','');
	 }
	 if(end_dateObj){
		 jsonData.end_date=end_dateObj.replaceAll('-','');
	 }
 	 if(jsonData.start_date&&jsonData.end_date&&jsonData.start_date > jsonData.end_date){
 		Ext.Msg.alert('提示', '开始时间必须小于结束时间！');
 		return;
 	 }
     var proxy = clientDataStore.getProxy();
     proxy.extraParams = jsonData;
     clientDataStore.loadPage(1);
}