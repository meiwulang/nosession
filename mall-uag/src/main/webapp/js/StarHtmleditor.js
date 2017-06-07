Ext.define('Ext.ux.form.MyEditor', {
 
    alias: 'widget.myeditor',
 
    extend: 'Ext.form.field.HtmlEditor',
 
    requires: ['Ext.form.field.HtmlEditor'],
 
    createToolbar: function(){
        var me = this;
        me.callParent(arguments);
        me.toolbar.insert(17, {
            xtype: 'button',
            icon: '../images/uploadPic.png',
            handler: this.showImgUploader,
            scope: this
        });
        return me.toolbar;
    },
 
    showImgUploader: function(){
        var editor = this;
        var imgform = Ext.create('Ext.tab.Panel', {
            region: 'left',
            border: false,
            activeTab: 0,
            items: [{
                title: '本地图片',
//                icon: 'extjs/resources/images/computer.png',
                layout: 'fit',
                items: [{
                    xtype: 'form',
                    border: false,
                    bodyPadding:10,
                    items: [{
                        xtype: 'filefield',
                        labelWidth: 70,
                        fieldLabel: '图片',
                        buttonText: '浏览',
        				listeners:{
        		            change:function(btn, value){
//        		                var file = btn.fileInputEl.dom.files[0];
//        		                var reader = new FileReader();
//        		                reader.readAsDataURL(file);
//        		                reader.onload = function(e){
//        		                	com.hjh.news.url=this.result;
//        		                }
        		                
        		            	var file = btn.fileInputEl.dom.files[0];
								if (file.size / 1024 / 1024 > 5) {
									var form = this.ownerLayout.owner;
									var filefield = form.items.items[0];
									filefield.setRawValue('');
									Ext.Msg.alert('警告',
											"亲，图片太大了，请处理一下,别大于5M。");
								}
								if (file.size / 1024 < 300) {
									var reader = new FileReader();
									reader.readAsDataURL(file);
									com.hjh.news.type = file.type;
									reader.onload = function(e) {
										var img = new Image();
										img.src = this.result;
										com.hjh.news.imageWidth = img.width;
										com.hjh.news.imageHeight = img.height;
										
										com.hjh.news.url = this.result;
									}
								} else {
									var reader = new FileReader();
									reader.readAsDataURL(file);
									com.hjh.news.type = file.type;
									reader.onload = function(e) {
										var img = new Image();
										img.src = this.result;
										com.hjh.news.imageWidth = img.width;
										com.hjh.news.imageHeight = img.height;
										
										com.hjh.news.url = compress(
												this.result,
												1 / 10,
												"image/jpeg");
									}
								}
        		                
        		                
        		            }
        		        }	,
                        name: 'pic',
                        allowBlank: false,
                        blankText: '文件不能为空',
                        anchor: '100%'
                    }],
                    dockedItems: [{
                        xtype: 'toolbar',
                        dock: 'bottom',
                        layout: { pack: 'end' },
                        items: [{
                            text: '上传',
                            formBind: true,
                            handler: function(obj) {
                            	var selection = editor.win.getSelection();
                            	if(selection.rangeCount==0){
                            		alert('亲，你还没有填写内容，请先点击一下正文空白处');
                            		win.hide();
                            	}else {
                            		
                            	
                                var f = obj.up('form');
                                if (!f.getForm().isValid()) {
                                    return;
                                }
                                var data = {
                                		imagecontext:com.hjh.news.url,
                                		upload_type:'04'
                				}
                                var vals = f.getForm().getValues();
                                this.disable();
                                var btn = this;
                                Ext.Ajax.request({
                					url : '/json/102003',
                					method : 'POST',
                					jsonData : data,
                					success : function(response, opts) {
                						btn.enable();
                						var result = Ext.decode(response.responseText);
                						if(result.error_no=='0'){
                							 var element = document.createElement('img');
                                           element.src = com.hjh.oss.ossUrl+result.avatar_path;
                                           element.width = com.hjh.news.imageWidth>400?400:com.hjh.news.imageWidth;
                                           element.height = com.hjh.news.imageHeight>200?200:com.hjh.news.imageHeight;
                                           if (Ext.isIE) {
                                               editor.insertAtCursor(element.outerHTML);
                                           } else {
                                               var selection = editor.win.getSelection();
                                               if (!selection.isCollapsed) {
                                                   selection.deleteFromDocument();
                                               }
                                               editor.setValue(editor.value+element.outerHTML);
//                                               selection.getRangeAt(0).insertNode(element);
                                           }
                                           win.hide();
                						    Ext.Msg.alert('成功', '添加成功.');
                						}else{
                							 Ext.Msg.alert('错误', '添加失败.');
                						}
                					}
                				});
                            	}
                            }
                        }, {
                            text: '取消',
                            handler: function() {
                                win.hide();
                            }
                        }]
                    }]
                }]
            }
//            {
//                title: '远程图片',
//                icon: 'extjs/resources/images/link.png',
//                layout: 'fit',
//                items: [{
//                    xtype: 'form',
//                    border: false,
//                    bodyPadding:10,
//                    items: [{
//                        xtype: 'textfield',
//                        vtype: 'url',
//                        labelWidth: 70,
//                        fieldLabel: '图片URL',
//                        anchor: '100%',
//                        name: 'pic',
//                        allowBlank: false,
//                        blankText: '图片URL不能为空'
//                    }, {
//                        layout: 'column',
//                        border: false,
//                        items: [{
//                            layout: 'form',
//                            columnWidth:.36,
//                            xtype: 'numberfield',
//                            labelWidth: 70,
//                            fieldLabel: '尺寸(宽x高)',
//                            name: 'width'
//                        },{
//                            columnWidth:.05,
//                            xtype: 'label',
//                            html: ' px'
//                        },{
//                            layout: 'form',
//                            xtype: 'numberfield',
//                            columnWidth:.15,
//                            name: 'height'
//                        },{
//                            columnWidth:.05,
//                            xtype: 'label',
//                            html: ' px'
//                        }]
//                    }],
//                    dockedItems: [{
//                        xtype: 'toolbar',
//                        dock: 'bottom',
//                        layout: { pack: 'end' },
//                        border: false,
//                        items: [{
//                            text: '添加',
//                            formBind: true,
//                            handler: function(obj) {
//                                var f = obj.up('form');
//                                if (!f.getForm().isValid()) {
//                                    return;
//                                }
//                                var vals = f.getForm().getValues();
//                                var pic = vals.pic;
//                                var fileext = pic.substring(pic.lastIndexOf('.'), pic.length).toLowerCase();
//                                if (fileext != '.jpg' && fileext != '.gif' && fileext != '.jpeg' && fileext != '.png' && fileext != '.bmp') {
//                                    f.items.items[0].setValue('');
//                                    Ext.Msg.show({
//                                        title: '提示',
//                                        icon: 'ext-mb-error',
//                                        width: 300,
//                                        msg: '对不起，系统仅支持标准格式的照片，请调整格式后重新上传，谢谢 ！',
//                                        buttons: Ext.MessageBox.OK
//                                    });
//                                    return;
//                                }
//                                var element = document.createElement('img');
//                                element.src = pic;
//                                if(vals.width>0 && vals.height>0){
//                                    element.width = vals.width;
//                                    element.height = vals.height;
//                                }
//                                if(Ext.isIE) {
//                                    editor.insertAtCursor(element.outerHTML);
//                                }else{
//                                    var selection = editor.win.getSelection();
//                                    if(!selection.isCollapsed) {
//                                        selection.deleteFromDocument();
//                                    }
//                                    selection.getRangeAt(0).insertNode(element);
//                                }
//                                win.hide();
//                            }
//                        }, {
//                            text: '取消',
//                            handler: function() {
//                                win.hide();
//                            }
//                        }]
//                    }]
//                }],
//            }
            
            ]
        });
        var win = Ext.create('Ext.Window', {
            title: '插入图片',
//            icon: '/admin/extjs/resources/images/picture.png',
            width: 400,
            height: 175,
            plain: true,
            modal: true,
            closeAction: 'hide',
            resizable: false,
            border: false,
            layout: 'fit',
            items: imgform
        });
        win.show(this);
    }
 
});


//Ext.define("Ext.ux.form.field.CKEditor", {
//    extend: 'Ext.form.field.TextArea',
//    alias: 'widget.ckeditor',
// 
//    constructor : function() {
//        this.callParent(arguments);//必须先构造父类对象
//        this.addEvents("instanceReady");//注册一个instanceReady事件
//    },
//     
//    initComponent: function () {
//        this.callParent(arguments);
//        this.on("afterrender", function(){
//            Ext.apply(this.CKConfig, {
//               height : this.getHeight(),
//               width : this.getWidth()
//            });
//            this.editor = CKEDITOR.replace(this.inputEl.id, this.CKConfig);
//            this.editor.name = this.name;//把配置中的name属性赋值给CKEditor的name属性
//            this.editor.on("instanceReady", function(){
//                this.fireEvent("instanceReady", this, this.editor);//触发instanceReady事件
//            }, this);
//        }, this);
//    },
//    onRender: function (ct, position) {
//        if (!this.el) {
//            this.defaultAutoCreate = {
//                tag: 'textarea',
//                autocomplete: 'off'
//            };
//        }
//        this.callParent(arguments)
//    },
//    setValue: function (value) {
//        this.callParent(arguments);
//        if (this.editor) {
//            this.editor.setData(value);
//        }
//    },
//    getRawValue: function () {//要覆盖getRawValue方法，否则会取不到值
//        if (this.editor) {
//            return this.editor.getData();
//        } else {
//            return ''
//        }
//    },
//    getValue: function () {
//        return this.getRawValue();
//    }
//});