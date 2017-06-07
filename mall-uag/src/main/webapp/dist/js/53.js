webpackJsonp([53],{

/***/ 13:
/***/ (function(module, exports) {

	module.exports = function normalizeComponent (
	  rawScriptExports,
	  compiledTemplate,
	  scopeId,
	  cssModules
	) {
	  var esModule
	  var scriptExports = rawScriptExports = rawScriptExports || {}
	
	  // ES6 modules interop
	  var type = typeof rawScriptExports.default
	  if (type === 'object' || type === 'function') {
	    esModule = rawScriptExports
	    scriptExports = rawScriptExports.default
	  }
	
	  // Vue.extend constructor export interop
	  var options = typeof scriptExports === 'function'
	    ? scriptExports.options
	    : scriptExports
	
	  // render functions
	  if (compiledTemplate) {
	    options.render = compiledTemplate.render
	    options.staticRenderFns = compiledTemplate.staticRenderFns
	  }
	
	  // scopedId
	  if (scopeId) {
	    options._scopeId = scopeId
	  }
	
	  // inject cssModules
	  if (cssModules) {
	    var computed = options.computed || (options.computed = {})
	    Object.keys(cssModules).forEach(function (key) {
	      var module = cssModules[key]
	      computed[key] = function () { return module }
	    })
	  }
	
	  return {
	    esModule: esModule,
	    exports: scriptExports,
	    options: options
	  }
	}


/***/ }),

/***/ 43:
/***/ (function(module, exports, __webpack_require__) {

	var Component = __webpack_require__(13)(
	  /* script */
	  __webpack_require__(44),
	  /* template */
	  __webpack_require__(45),
	  /* scopeId */
	  null,
	  /* cssModules */
	  null
	)
	Component.options.__file = "/Users/xiangyangli/projects/hjh/jhd/mall-uag/src/main/webapp/app/components/metadataSetting.vue"
	if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key !== "__esModule"})) {console.error("named exports are not supported in *.vue files.")}
	if (Component.options.functional) {console.error("[vue-loader] metadataSetting.vue: functional components are not supported with templates, they should use render functions.")}
	
	/* hot reload */
	if (false) {(function () {
	  var hotAPI = require("vue-hot-reload-api")
	  hotAPI.install(require("vue"), false)
	  if (!hotAPI.compatible) return
	  module.hot.accept()
	  if (!module.hot.data) {
	    hotAPI.createRecord("data-v-9c26698c", Component.options)
	  } else {
	    hotAPI.reload("data-v-9c26698c", Component.options)
	  }
	})()}
	
	module.exports = Component.exports


/***/ }),

/***/ 44:
/***/ (function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	var _common = __webpack_require__(8);
	
	var _common2 = _interopRequireDefault(_common);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.default = {
		data: function data() {
			return {
				searchForm: this.getDefaultData(), //查询表单
				init_start_date: '', //表单项｀创建开始时间
				init_end_date: '', //表单项｀创建结束时间
				page: 1, //列表初始当前页码
				limit: 10, //每页列表数量
				operatorList: [], //创建者列表
				MetadataList: {}, //元数据列表数据缓存
				formScopeDialog: {}, //查看或编辑时对应的数据缓存
				dialogFormVisible: false, //查看与编辑弹窗显示状态 false为隐藏 true为显示
				formLabelWidth: '150px', //弹窗中的表单label的宽度
				labelPosition: "right", //弹窗中表单label文字的对齐方式
				ismodify: false, //是否为编辑状态
				lenOftype2: false,
				loading: false //列表加载状态 true为加载中 false为加载完毕
			};
		},
		created: function created() {
			var that = this;
			//获取创建者列表
			that.$http.post("./../getAllOperators", { limit: 10000, page: 1, status: 1 }).then(function (response) {
				var data = response.data;
				if (data.error_no == 0) {
					that.$data.operatorList = data.result_list;
				}
			});
		},
		mounted: function mounted() {
			document.title = "后台管理系统-元数据管理";
			this.getListBySearchData();
		},
	
		methods: {
			checklenOftype2: function checklenOftype2() {
				var _this = this;
	
				this.$http.post('/queryMetadata', { page: 1, limit: 10, type: 2 }).then(function (response) {
					var data = response.data;
					if (data.error_no == 0) {
						_this.lenOftype2 = data.result_list.length > 0;
					}
				});
			},
	
			//选择注册开始时间
			initStartDateChange: function initStartDateChange(val) {
				if (val) this.init_start_date = val.replace("00:00:00", _common2.default.getHMSforNow());
			},
	
			//选择注册结束时间
			initEndDateChange: function initEndDateChange(val) {
				if (val) this.init_end_date = val.replace("00:00:00", _common2.default.getHMSforNow());
			},
	
			//操作－查询
			submitForm: function submitForm() {
				this.$data.page = 1;
				this.getListBySearchData();
			},
	
			//操作－重置
			resetForm: function resetForm(formName) {
				this.$refs[formName].resetFields();
				this.$data.searchForm = this.getDefaultData();
				this.$data.init_start_date = '';
				this.$data.init_end_date = '';
				this.submitForm();
			},
	
			//操作－刷新缓存
			clearCache: function clearCache() {
				var that = this;
				that.$http.post("./../reloadAllMetadata", { access_token: localStorage.access_token }).then(function (response) {
					response = response.data;
					that.$message({
						type: response.error_no == 0 ? "success" : "error",
						message: response.error_no == 0 ? "缓存刷新成功" : response.error_info
					});
				});
			},
	
			//操作－翻页
			handleCurrentChange: function handleCurrentChange(val) {
				this.$data.page = val;
				this.searchDataCache.page = val;
				this.getMetadataList();
			},
	
			//新增
			addNew: function addNew() {
				console.log(this.lenOftype2);
				this.$data.dialogFormVisible = true;
				this.$data.ismodify = false;
				//this.$data.formScopeDialog = this.getFormDialog();
				this.$data.formScopeDialog = {
					metadata_name: "",
					alias: "",
					type: "",
					sort: ""
				};
			},
	
			//操作－编辑
			modify: function modify(index, list) {
				this.$data.dialogFormVisible = true;
				this.$data.ismodify = true;
				//this.$data.formScopeDialog = this.getFormDialog(list[index]);
				this.$data.formScopeDialog = _common2.default.simpleClone(list[index]);
				this.$data.formScopeDialog.type = list[index].type + "";
				this.typecache = this.$data.formScopeDialog.type;
				var that = this;
				setTimeout(function () {
					that.$refs[that.formScopeDialog].validate();
				}, 50);
			},
	
			//启用or禁用
			setStatus: function setStatus(index, list) {
				var status = list[index].status == 1 ? 0 : 1,
				    metadata_id = list[index].metadata_id,
				    that = this;
				this.$confirm("确定要" + (status == 1 ? "启用" : "禁用") + "该条数据吗？", '', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(function () {
					var param = { access_token: that.searchForm.access_token, metadata_id: metadata_id, status: status };
					that.$http.post("./../updateMetadataStatus", param).then(function (response) {
						response = response.data;
						that.$message({
							type: response.error_no == 0 ? "success" : "error",
							message: response.error_no == 0 ? "操作成功" : response.error_info
						});
						if (response.error_no == 0) {
							that.getMetadataList();
						}
					});
				});
			},
	
			//获取弹窗中表单数据 -- 初始化设置
			getFormDialog: function getFormDialog(data) {
				if (!data) return {
					access_token: this.$data.searchForm.access_token,
					sort: 0,
					metadata_id: "0"
				};
				return {
					access_token: this.$data.searchForm.access_token,
					invite_code: data.invite_code,
					invite_id: data.invite_id,
					invite_customer_name: data.invite_customer_name,
					business_hotline: data.business_hotline,
					remark: data.remark
				};
			},
	
			//操作－确认(保存)
			updateEnterpriseName: function updateEnterpriseName(status) {
				var _this2 = this;
	
				var api = this.$data.ismodify ? "./../updateMetadata" : "./../addMetadata";
				var that = this,
				    dialog = {}; // this.$data.formScopeDialog;
				dialog = {
					access_token: localStorage.access_token,
					status: status
	
				};
				this.$data.formScopeDialog.status = status;
				this.$data.formScopeDialog.access_token = localStorage.access_token;
				that.$refs[that.formScopeDialog].validate(function (valid) {
					if (valid) {
						that.$http.post(api, that.$data.formScopeDialog).then(function (response) {
							response = response.data;
							that.$message({
								type: response.error_no == 0 ? "success" : "error",
								message: response.error_no == 0 ? "操作成功" : response.error_info
							});
							if (response.error_no == 0) {
								if (_this2.ismodify) {
									that.getMetadataList();
								} else {
									that.resetForm(_this2.searchForm);
								}
	
								that.$data.dialogFormVisible = false;
							}
						});
					}
				});
			},
			checkLogout: function checkLogout(error_no) {
				if (error_no == 88880020) {
					location.href = "/login";
				}
			},
			getListBySearchData: function getListBySearchData() {
				var param = this.searchForm;
				param.page = this.$data.page;
				param.limit = this.$data.limit;
				param.update_date_start = _common2.default.getYMDforNowNull(this.init_start_date);
				param.update_time_start = _common2.default.getYMDforNowNull(this.init_start_date, 1);
				param.update_date_end = _common2.default.getYMDforNowNull(this.init_end_date);
				param.update_time_end = _common2.default.getYMDforNowNull(this.init_end_date, 1);
	
				this.searchDataCache = _common2.default.simpleClone(param);
				this.getMetadataList();
			},
	
			//获取会员列表
			getMetadataList: function getMetadataList() {
				if (this.loading) return;
				var that = this;
				that.$data.loading = true;
				this.$http.post('./../queryMetadata', this.searchDataCache).then(function (response) {
					var jsonData = response.data;
					that.checkLogout(jsonData.error_no);
					if (jsonData.error_no == 0) {
						if (jsonData.result_list) {
							jsonData.result_list = jsonData.result_list.map(function (item) {
								item.initTime = _common2.default.formatDateConcat(item.init_date, item.init_time);
								item.updateTime = _common2.default.formatDateConcat(item.update_date, item.update_time);
								return item;
							});
						}
						that.$data.MetadataList = jsonData || {};
						that.checklenOftype2();
					} else {
						that.$message({
							type: "warning",
							message: jsonData.error_info
						});
					}
					that.$data.loading = false;
				});
			},
	
			//获取查询表单初始数据
			getDefaultData: function getDefaultData() {
				return {
					//access_token : localStorage.access_token,
					metadata_name: '', //名称
					alias: '', //别名
					type: '', //类别
					status: '', //状态
					update_user: '', //操作人
					update_date_start: null, //创建开始时间 年月日
					update_time_start: null, //创建开始时间 时分秒
					update_date_end: null, //创建结束时间 年月日
					update_time_end: null //创建结束时间 时分秒
				};
			}
		}
	}; //
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//

/***/ }),

/***/ 45:
/***/ (function(module, exports, __webpack_require__) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', [_c('div', {
	    staticClass: "hjh-breadcrumb"
	  }, [_c('el-breadcrumb', {
	    attrs: {
	      "separator": "/"
	    }
	  }, [_c('el-breadcrumb-item', {
	    attrs: {
	      "to": {
	        path: '/'
	      }
	    }
	  }, [_vm._v("用户中心")]), _vm._v(" "), _c('el-breadcrumb-item', [_vm._v("基本管理")]), _vm._v(" "), _c('el-breadcrumb-item', [_vm._v("元数据管理")])], 1)], 1), _vm._v(" "), _c('div', {
	    staticClass: "block-white"
	  }, [_c('el-form', {
	    ref: "searchForm",
	    staticClass: "demo-form-inline searchForm",
	    attrs: {
	      "inline": true,
	      "model": _vm.searchForm
	    }
	  }, [_c('el-form-item', [_c('el-input', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "请输入名称"
	    },
	    model: {
	      value: (_vm.searchForm.metadata_name),
	      callback: function($$v) {
	        _vm.searchForm.metadata_name = $$v
	      },
	      expression: "searchForm.metadata_name"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', [_c('el-input', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "请输入别名"
	    },
	    model: {
	      value: (_vm.searchForm.alias),
	      callback: function($$v) {
	        _vm.searchForm.alias = $$v
	      },
	      expression: "searchForm.alias"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', [_c('el-select', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "请选择类别"
	    },
	    model: {
	      value: (_vm.searchForm.type),
	      callback: function($$v) {
	        _vm.searchForm.type = $$v
	      },
	      expression: "searchForm.type"
	    }
	  }, [_c('el-option', {
	    attrs: {
	      "label": "计量单位",
	      "value": "0"
	    }
	  }), _vm._v(" "), _c('el-option', {
	    attrs: {
	      "label": "机械类型",
	      "value": "1"
	    }
	  }), _vm._v(" "), _c('el-option', {
	    attrs: {
	      "label": "售后电话",
	      "value": "2"
	    }
	  })], 1)], 1), _vm._v(" "), _c('el-form-item', [_c('el-select', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "请选择状态"
	    },
	    model: {
	      value: (_vm.searchForm.status),
	      callback: function($$v) {
	        _vm.searchForm.status = $$v
	      },
	      expression: "searchForm.status"
	    }
	  }, [_c('el-option', {
	    attrs: {
	      "label": "启用",
	      "value": "1"
	    }
	  }), _vm._v(" "), _c('el-option', {
	    attrs: {
	      "label": "禁用",
	      "value": "0"
	    }
	  })], 1)], 1), _vm._v(" "), _c('el-form-item', [_c('el-select', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "filterable": "",
	      "placeholder": "请输入操作人"
	    },
	    model: {
	      value: (_vm.searchForm.update_user),
	      callback: function($$v) {
	        _vm.searchForm.update_user = $$v
	      },
	      expression: "searchForm.update_user"
	    }
	  }, _vm._l((_vm.operatorList), function(item) {
	    return _c('el-option', {
	      key: item.operator_name,
	      attrs: {
	        "label": item.operator_name,
	        "value": item.operator_name
	      }
	    })
	  }))], 1), _vm._v(" "), _c('el-form-item', [_c('div', {
	    staticStyle: {
	      "width": "450px"
	    }
	  }, [_c('el-col', {
	    attrs: {
	      "span": 11
	    }
	  }, [_c('el-date-picker', {
	    staticStyle: {
	      "width": "100%"
	    },
	    attrs: {
	      "type": "date",
	      "placeholder": "最后操作开始时间",
	      "format": "yyyy-MM-dd HH:mm:ss"
	    },
	    on: {
	      "change": _vm.initStartDateChange
	    },
	    model: {
	      value: (_vm.init_start_date),
	      callback: function($$v) {
	        _vm.init_start_date = $$v
	      },
	      expression: "init_start_date"
	    }
	  })], 1), _vm._v(" "), _c('el-col', {
	    staticClass: "line",
	    staticStyle: {
	      "text-align": "center"
	    },
	    attrs: {
	      "span": 2
	    }
	  }, [_vm._v("到")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 11
	    }
	  }, [_c('el-date-picker', {
	    staticStyle: {
	      "width": "100%"
	    },
	    attrs: {
	      "type": "date",
	      "placeholder": "最后操作结束时间",
	      "format": "yyyy-MM-dd HH:mm:ss"
	    },
	    on: {
	      "change": _vm.initEndDateChange
	    },
	    model: {
	      value: (_vm.init_end_date),
	      callback: function($$v) {
	        _vm.init_end_date = $$v
	      },
	      expression: "init_end_date"
	    }
	  })], 1)], 1)]), _vm._v(" "), _c('el-form-item', [_c('el-button', {
	    staticClass: "searchBtn",
	    attrs: {
	      "type": "primary"
	    },
	    on: {
	      "click": function($event) {
	        _vm.submitForm('searchForm')
	      }
	    }
	  }, [_vm._v("搜索")]), _vm._v(" "), _c('el-button', {
	    on: {
	      "click": function($event) {
	        _vm.resetForm('searchForm')
	      }
	    }
	  }, [_vm._v("清除")]), _vm._v(" "), _c('el-button', {
	    on: {
	      "click": _vm.clearCache
	    }
	  }, [_vm._v("刷新缓存")])], 1)], 1)], 1), _vm._v(" "), _c('div', {
	    staticClass: "block-white"
	  }, [_c('el-button', {
	    attrs: {
	      "type": "primary"
	    },
	    on: {
	      "click": _vm.addNew
	    }
	  }, [_vm._v("新增")])], 1), _vm._v(" "), _c('div', {
	    staticClass: "block-white"
	  }, [_c('el-table', {
	    directives: [{
	      name: "loading",
	      rawName: "v-loading.body",
	      value: (_vm.loading),
	      expression: "loading",
	      modifiers: {
	        "body": true
	      }
	    }],
	    staticStyle: {
	      "width": "100%"
	    },
	    attrs: {
	      "data": _vm.MetadataList.result_list,
	      "border": "",
	      "stripe": "",
	      "max-height": "460"
	    }
	  }, [_c('el-table-column', {
	    attrs: {
	      "fixed": "",
	      "label": "序号",
	      "width": "70",
	      "align": "center"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_vm._v(_vm._s(scope.$index + 1))]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "metadata_name",
	      "label": "名称",
	      "width": "200"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "alias",
	      "label": "别名",
	      "width": "200"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "type",
	      "label": "类别",
	      "align": "center",
	      "width": "100"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_vm._v(_vm._s(scope.row.type == 0 ? "计量单位" : (scope.row.type == 1 ? "机械类型" : "售后电话")))]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "sort",
	      "label": "排序",
	      "align": "center",
	      "width": "100"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "status",
	      "label": "状态",
	      "align": "center",
	      "width": "100"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_vm._v(_vm._s(scope.row.status == 1 ? "启用" : "禁用"))]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "initTime",
	      "label": "创建日期",
	      "align": "center",
	      "sortable": "",
	      "width": "180"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "create_user",
	      "label": "创建者",
	      "align": "center",
	      "width": "140"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "updateTime",
	      "label": "最后操作日期",
	      "align": "center",
	      "sortable": "",
	      "width": "180"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "update_user",
	      "label": "最后操作者",
	      "align": "center",
	      "width": "140"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "fixed": "right",
	      "label": "操作",
	      "width": "180"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_c('el-button', {
	          attrs: {
	            "type": "info"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.modify(scope.$index, _vm.MetadataList.result_list)
	            }
	          }
	        }, [_vm._v("编辑")]), _vm._v(" "), _c('el-button', {
	          attrs: {
	            "type": "danger"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.setStatus(scope.$index, _vm.MetadataList.result_list)
	            }
	          }
	        }, [_vm._v(_vm._s(scope.row.status == "1" ? "禁用" : "启用"))])]
	      }
	    }])
	  })], 1)], 1), _vm._v(" "), _c('div', {
	    staticClass: "block-white"
	  }, [_c('el-pagination', {
	    attrs: {
	      "current-page": _vm.page,
	      "page-size": _vm.limit,
	      "layout": "total, prev, pager, next, jumper",
	      "total": _vm.MetadataList.total_num
	    },
	    on: {
	      "current-change": _vm.handleCurrentChange
	    }
	  })], 1), _vm._v(" "), _c('el-dialog', {
	    attrs: {
	      "title": _vm.ismodify ? '编辑元数据' : '新增元数据'
	    },
	    model: {
	      value: (_vm.dialogFormVisible),
	      callback: function($$v) {
	        _vm.dialogFormVisible = $$v
	      },
	      expression: "dialogFormVisible"
	    }
	  }, [_c('div', {
	    staticStyle: {
	      "height": "320px",
	      "overflow": "hidden",
	      "overflow-y": "auto"
	    }
	  }, [_c('el-form', {
	    ref: _vm.formScopeDialog,
	    attrs: {
	      "label-position": _vm.labelPosition,
	      "label-width": _vm.formLabelWidth,
	      "model": _vm.formScopeDialog
	    }
	  }, [_c('el-form-item', {
	    attrs: {
	      "label": "名称",
	      "prop": "metadata_name",
	      "rules": [{
	          required: true,
	          message: '请输入名称',
	          trigger: 'blur'
	        },
	        {
	          required: true,
	          message: '请输入1-20个字符',
	          trigger: 'blur'
	        }
	      ]
	    }
	  }, [_c('el-input', {
	    attrs: {
	      "minlength": 1,
	      "maxlength": 20,
	      "placeholder": "输入名称"
	    },
	    model: {
	      value: (_vm.formScopeDialog.metadata_name),
	      callback: function($$v) {
	        _vm.formScopeDialog.metadata_name = $$v
	      },
	      expression: "formScopeDialog.metadata_name"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "显示名称（别名）",
	      "prop": "alias"
	    }
	  }, [_c('el-input', {
	    attrs: {
	      "placeholder": "输入别名",
	      "minlength": 1,
	      "maxlength": 20
	    },
	    model: {
	      value: (_vm.formScopeDialog.alias),
	      callback: function($$v) {
	        _vm.formScopeDialog.alias = $$v
	      },
	      expression: "formScopeDialog.alias"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "类别",
	      "prop": "type",
	      "rules": [{
	        required: true,
	        message: '请选择类别',
	        trigger: 'blur'
	      }]
	    }
	  }, [_c('el-select', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "请选择类别"
	    },
	    model: {
	      value: (_vm.formScopeDialog.type),
	      callback: function($$v) {
	        _vm.formScopeDialog.type = $$v
	      },
	      expression: "formScopeDialog.type"
	    }
	  }, [_c('el-option', {
	    attrs: {
	      "label": "计量单位",
	      "value": "0"
	    }
	  }), _vm._v(" "), _c('el-option', {
	    attrs: {
	      "label": "机械类型",
	      "value": "1"
	    }
	  }), _vm._v(" "), _c('el-option', {
	    attrs: {
	      "label": "售后电话",
	      "value": "2",
	      "disabled": (!_vm.ismodify && _vm.lenOftype2) || (_vm.lenOftype2 && _vm.ismodify && _vm.typecache != 2)
	    }
	  })], 1)], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "排序"
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "type": "number"
	    },
	    model: {
	      value: (_vm.formScopeDialog.sort),
	      callback: function($$v) {
	        _vm.formScopeDialog.sort = $$v
	      },
	      expression: "formScopeDialog.sort"
	    }
	  })], 1)], 1)], 1), _vm._v(" "), _c('div', {
	    staticClass: "dialog-footer",
	    staticStyle: {
	      "text-align": "center"
	    },
	    slot: "footer"
	  }, [_c('el-button', {
	    attrs: {
	      "type": "primary"
	    },
	    on: {
	      "click": function($event) {
	        _vm.updateEnterpriseName(1)
	      }
	    }
	  }, [_vm._v("保存并启用")]), _vm._v(" "), _c('el-button', {
	    attrs: {
	      "type": "primary"
	    },
	    on: {
	      "click": function($event) {
	        _vm.updateEnterpriseName(0)
	      }
	    }
	  }, [_vm._v("保存并禁用")]), _vm._v(" "), _c('el-button', {
	    on: {
	      "click": function($event) {
	        _vm.dialogFormVisible = false
	      }
	    }
	  }, [_vm._v("关 闭")])], 1)])], 1)
	},staticRenderFns: []}
	module.exports.render._withStripped = true
	if (false) {
	  module.hot.accept()
	  if (module.hot.data) {
	     require("vue-hot-reload-api").rerender("data-v-9c26698c", module.exports)
	  }
	}

/***/ })

});
//# sourceMappingURL=53.js.map