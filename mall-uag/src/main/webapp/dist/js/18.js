webpackJsonp([18],{

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

/***/ 92:
/***/ (function(module, exports, __webpack_require__) {

	var Component = __webpack_require__(13)(
	  /* script */
	  __webpack_require__(93),
	  /* template */
	  __webpack_require__(94),
	  /* scopeId */
	  null,
	  /* cssModules */
	  null
	)
	Component.options.__file = "D:\\eclipse-workspace3\\mall-full-jhd\\mall-uag\\src\\main\\webapp\\app\\components\\appVersion.vue"
	if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key !== "__esModule"})) {console.error("named exports are not supported in *.vue files.")}
	if (Component.options.functional) {console.error("[vue-loader] appVersion.vue: functional components are not supported with templates, they should use render functions.")}
	
	/* hot reload */
	if (false) {(function () {
	  var hotAPI = require("vue-hot-reload-api")
	  hotAPI.install(require("vue"), false)
	  if (!hotAPI.compatible) return
	  module.hot.accept()
	  if (!module.hot.data) {
	    hotAPI.createRecord("data-v-04547cee", Component.options)
	  } else {
	    hotAPI.reload("data-v-04547cee", Component.options)
	  }
	})()}
	
	module.exports = Component.exports


/***/ }),

/***/ 93:
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
	      ismodify: false,
	      dialogFormVisible: false,
	      formLabelWidth: "100px",
	      pickerOptions: {
	        shortcuts: [{
	          text: '最近一周',
	          onClick: function onClick(picker) {
	            var end = new Date();
	            var start = new Date();
	            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
	            picker.$emit('pick', [start, end]);
	          }
	        }, {
	          text: '最近一个月',
	          onClick: function onClick(picker) {
	            var end = new Date();
	            var start = new Date();
	            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
	            picker.$emit('pick', [start, end]);
	          }
	        }, {
	          text: '最近三个月',
	          onClick: function onClick(picker) {
	            var end = new Date();
	            var start = new Date();
	            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
	            picker.$emit('pick', [start, end]);
	          }
	        }]
	      },
	      showtooltip: true,
	      searchForm: this.getDefaultSearchForm(),
	      formScopeDialog: this.getDefaultDialogData(),
	      datepickerValue: "",
	      page: 1,
	      limit: 10,
	      loading: false,
	      appList: {}
	    };
	  },
	  created: function created() {
	    document.title = "后台管理系统-版本控制";
	    this.getListBySearchData();
	  },
	
	  methods: {
	    getDefaultSearchForm: function getDefaultSearchForm() {
	      return {
	        version_status: "",
	        app_type: "",
	        force_update: "",
	        version_title: "",
	        version_no: "",
	        creater_name: "",
	        date_start: "",
	        date_end: ""
	      };
	    },
	    getDefaultDialogData: function getDefaultDialogData() {
	      return {
	        app_type: "",
	        app_name: "",
	        version_title: "",
	        version_no: "",
	        version_info: "",
	        package_url: "",
	        force_update: ""
	      };
	    },
	    datepickerChange: function datepickerChange(val) {
	      if (!val) {
	        this.searchForm.date_start = "";
	        this.searchForm.date_end = "";
	      } else {
	        var arr = val.split(" - ");
	        this.searchForm.date_start = arr[0].match(/\d/g).join("");
	        this.searchForm.date_end = arr[1].match(/\d/g).join("");
	      }
	    },
	    addNewVersion: function addNewVersion() {
	      this.ismodify = false;
	      this.dialogFormVisible = true;
	      this.formScopeDialog = this.getDefaultDialogData();
	    },
	    modify: function modify(index, list) {
	      var item = list[index];
	      this.formScopeDialog = {
	        app_type: item.app_type == "Android" ? "0" : "1",
	        app_name: item.app_name,
	        version_title: item.version_title,
	        version_no: item.version_no,
	        version_info: item.version_info,
	        package_url: item.package_url,
	        version_id: item.version_id,
	        force_update: item.force_update == "强制更新" ? "1" : "0"
	      };
	      this.ismodify = true;
	      this.dialogFormVisible = true;
	    },
	    publish: function publish(formName) {
	      var _this = this;
	
	      var api = this.ismodify ? "/appversion/update" : "/appversion/add";
	      this.$refs[formName].validate(function (valid) {
	        if (valid) {
	          if (_this.ismodify) {
	            _this.formScopeDialog.access_token = localStorage.access_token;
	          }
	          _this.$http.post(api, _this.formScopeDialog).then(function (response) {
	            var jsondata = response.data;
	            _this.$message({
	              type: jsondata.error_no == '0' ? "success" : "error",
	              message: jsondata.error_no == '0' ? "操作成功" : jsondata.error_info
	            });
	            if (jsondata.error_no == '0') {
	              if (_this.ismodify) {
	                _this.getAppList();
	              } else {
	                _this.resetForm(_this.searchForm);
	              }
	
	              _this.dialogFormVisible = false;
	            }
	          });
	        }
	      });
	    },
	    submitForm: function submitForm() {
	      this.page = 1;
	      this.getListBySearchData();
	    },
	    resetForm: function resetForm(formName) {
	      this.searchForm = this.getDefaultSearchForm();
	      this.datepickerValue = "";
	      this.submitForm();
	    },
	    handleCurrentChange: function handleCurrentChange(val) {
	      this.page = val;
	      this.searchDataCache.page = val;
	      this.getAppList();
	    },
	    setStatus: function setStatus(index, list) {
	      var _this2 = this;
	
	      var item = list[index],
	          status = item.version_status == "生效中" ? "禁用" : "启用";
	      var param = {
	        access_token: localStorage.access_token,
	        version_id: item.version_id,
	        version_status: item.version_status == "生效中" ? 0 : 1
	      };
	      this.$confirm("确定要" + status + "该APP版本吗？", '', {
	        confirmButtonText: '确定',
	        cancelButtonText: '取消',
	        type: 'warning'
	      }).then(function () {
	        _this2.$http.post('/appversion/updatestatus', param).then(function (response) {
	          response = response.data;
	          _this2.$message({
	            type: response.error_no == '0' ? "success" : "error",
	            message: response.error_no == '0' ? "操作成功" : response.error_info
	          });
	          if (response.error_no == '0') {
	            _this2.getAppList();
	          }
	        });
	      });
	    },
	    deleteApp: function deleteApp(index, list) {
	      var _this3 = this;
	
	      var item = list[index];
	      var param = {
	        access_token: localStorage.access_token,
	        version_id: item.version_id,
	        status: 0
	      };
	      this.$confirm("确定要删除该APP版本吗？", '', {
	        confirmButtonText: '确定',
	        cancelButtonText: '取消',
	        type: 'warning'
	      }).then(function () {
	        _this3.$http.post('/appversion/updatestatus', param).then(function (response) {
	          response = response.data;
	          _this3.$message({
	            type: response.error_no == '0' ? "success" : "error",
	            message: response.error_no == '0' ? "操作成功" : response.error_info
	          });
	          if (response.error_no == '0') {
	            _this3.getAppList();
	          }
	        });
	      });
	    },
	    getListBySearchData: function getListBySearchData() {
	      var param = _common2.default.deepClone({
	        page: this.page,
	        limit: this.limit,
	        access_token: localStorage.access_token
	      }, this.searchForm);
	      this.searchDataCache = _common2.default.simpleClone(param);
	      this.getAppList();
	    },
	    getAppList: function getAppList() {
	      var _this4 = this;
	
	      if (this.loading) return;
	      this.loading = true;
	      this.$http.post("/appversion/query", _common2.default.filterParamByEmptyValue(this.searchDataCache)).then(function (response) {
	        var jsondata = response.data;
	        if (jsondata.error_no == 0) {
	          jsondata.result_list = jsondata.result_list.map(function (item) {
	            item.initDate = _common2.default.formatDateConcat(item.init_date, item.init_time);
	            return item;
	          });
	          _this4.appList = jsondata;
	        } else {
	          _this4.$message({
	            type: "warning",
	            message: jsonData.error_info
	          });
	        }
	        _this4.loading = false;
	      });
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
	//
	//
	//

/***/ }),

/***/ 94:
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
	  }, [_vm._v("用户中心")]), _vm._v(" "), _c('el-breadcrumb-item', [_vm._v("APP应用管理")]), _vm._v(" "), _c('el-breadcrumb-item', [_vm._v("版本控制")])], 1)], 1), _vm._v(" "), _c('div', {
	    staticClass: "block-white"
	  }, [_c('el-form', {
	    ref: _vm.searchForm,
	    staticClass: "demo-form-inline searchForm",
	    attrs: {
	      "inline": true,
	      "model": _vm.searchForm
	    }
	  }, [_c('el-form-item', [_c('el-select', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "请选择版本状态"
	    },
	    model: {
	      value: (_vm.searchForm.version_status),
	      callback: function($$v) {
	        _vm.searchForm.version_status = $$v
	      },
	      expression: "searchForm.version_status"
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
	      "placeholder": "选择APP类型"
	    },
	    model: {
	      value: (_vm.searchForm.app_type),
	      callback: function($$v) {
	        _vm.searchForm.app_type = $$v
	      },
	      expression: "searchForm.app_type"
	    }
	  }, [_c('el-option', {
	    attrs: {
	      "label": "IOS",
	      "value": "1"
	    }
	  }), _vm._v(" "), _c('el-option', {
	    attrs: {
	      "label": "Android",
	      "value": "0"
	    }
	  })], 1)], 1), _vm._v(" "), _c('el-form-item', [_c('el-select', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "选择更新类型"
	    },
	    model: {
	      value: (_vm.searchForm.force_update),
	      callback: function($$v) {
	        _vm.searchForm.force_update = $$v
	      },
	      expression: "searchForm.force_update"
	    }
	  }, [_c('el-option', {
	    attrs: {
	      "label": "强制更新",
	      "value": "1"
	    }
	  }), _vm._v(" "), _c('el-option', {
	    attrs: {
	      "label": "选择更新",
	      "value": "0"
	    }
	  })], 1)], 1), _vm._v(" "), _c('el-form-item', [_c('el-input', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "请输入版本名称"
	    },
	    model: {
	      value: (_vm.searchForm.version_title),
	      callback: function($$v) {
	        _vm.searchForm.version_title = $$v
	      },
	      expression: "searchForm.version_title"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', [_c('el-input', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "请输入版本号"
	    },
	    model: {
	      value: (_vm.searchForm.version_no),
	      callback: function($$v) {
	        _vm.searchForm.version_no = $$v
	      },
	      expression: "searchForm.version_no"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', [_c('el-date-picker', {
	    attrs: {
	      "type": "datetimerange",
	      "picker-options": _vm.pickerOptions,
	      "placeholder": "选择发布时间范围",
	      "align": "right"
	    },
	    on: {
	      "change": _vm.datepickerChange
	    },
	    model: {
	      value: (_vm.datepickerValue),
	      callback: function($$v) {
	        _vm.datepickerValue = $$v
	      },
	      expression: "datepickerValue"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', [_c('el-input', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "请输入发布人"
	    },
	    model: {
	      value: (_vm.searchForm.creater_name),
	      callback: function($$v) {
	        _vm.searchForm.creater_name = $$v
	      },
	      expression: "searchForm.creater_name"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', [_c('el-button', {
	    staticClass: "searchBtn",
	    attrs: {
	      "type": "primary"
	    },
	    on: {
	      "click": function($event) {
	        _vm.submitForm('searchForm')
	      }
	    }
	  }, [_vm._v("查询")]), _vm._v(" "), _c('el-button', {
	    on: {
	      "click": function($event) {
	        _vm.resetForm('searchForm')
	      }
	    }
	  }, [_vm._v("清除")])], 1)], 1)], 1), _vm._v(" "), _c('div', {
	    staticClass: "block-white"
	  }, [_c('el-button', {
	    attrs: {
	      "type": "info"
	    },
	    on: {
	      "click": _vm.addNewVersion
	    }
	  }, [_vm._v("发布")])], 1), _vm._v(" "), _c('div', {
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
	      "data": _vm.appList.result_list,
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
	      "prop": "app_type",
	      "label": "APP类型",
	      "width": "150"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "app_name",
	      "label": "APP名称",
	      "width": "150"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_vm._v(_vm._s(scope.row.app_name == 0 ? "集客" : (scope.row.app_name == 1 ? "全知道" : "机惠多")))]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "version_title",
	      "label": "版本名称",
	      "align": "center",
	      "width": "150"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "version_no",
	      "label": "版本号",
	      "align": "center",
	      "width": "150"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "version_status",
	      "label": "版本状态",
	      "width": "150"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "version_info",
	      "label": "版本描述",
	      "width": "350",
	      "show-overflow-tooltip": _vm.showtooltip
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "force_update",
	      "label": "更新类型",
	      "align": "center",
	      "width": "100"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "package_url",
	      "label": "APP发布地址",
	      "width": "300",
	      "show-overflow-tooltip": _vm.showtooltip
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "initDate",
	      "label": "发布时间",
	      "align": "center",
	      "width": "180"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "update_user",
	      "label": "发布人",
	      "align": "center",
	      "width": "120"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "fixed": "right",
	      "label": "操作",
	      "width": "250"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_c('el-button', {
	          attrs: {
	            "type": "warning"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.setStatus(scope.$index, _vm.appList.result_list)
	            }
	          }
	        }, [_vm._v(_vm._s(scope.row.version_status == "生效中" ? "禁用" : "启用"))]), _vm._v(" "), _c('el-button', {
	          attrs: {
	            "type": "info"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.modify(scope.$index, _vm.appList.result_list)
	            }
	          }
	        }, [_vm._v("编辑")]), _vm._v(" "), _c('el-button', {
	          attrs: {
	            "type": "danger"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.deleteApp(scope.$index, _vm.appList.result_list)
	            }
	          }
	        }, [_vm._v("删除")])]
	      }
	    }])
	  })], 1)], 1), _vm._v(" "), _c('div', {
	    staticClass: "block-white"
	  }, [_c('el-pagination', {
	    attrs: {
	      "current-page": _vm.page,
	      "page-size": _vm.limit,
	      "layout": "total, prev, pager, next, jumper",
	      "total": _vm.appList.total_num
	    },
	    on: {
	      "current-change": _vm.handleCurrentChange
	    }
	  })], 1), _vm._v(" "), _c('el-dialog', {
	    attrs: {
	      "title": _vm.ismodify ? '编辑版本' : '新增版本'
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
	      "height": "450px",
	      "overflow": "hidden",
	      "overflow-y": "auto"
	    }
	  }, [_c('el-form', {
	    ref: _vm.formScopeDialog,
	    attrs: {
	      "label-position": "right",
	      "label-width": _vm.formLabelWidth,
	      "model": _vm.formScopeDialog
	    }
	  }, [_c('el-form-item', {
	    attrs: {
	      "label": "APP类型",
	      "prop": "app_type",
	      "rules": {
	        required: true,
	        message: '请选择APP类型'
	      }
	    }
	  }, [_c('el-select', {
	    attrs: {
	      "placeholder": "请选择APP类型"
	    },
	    model: {
	      value: (_vm.formScopeDialog.app_type),
	      callback: function($$v) {
	        _vm.formScopeDialog.app_type = $$v
	      },
	      expression: "formScopeDialog.app_type"
	    }
	  }, [_c('el-option', {
	    attrs: {
	      "label": "IOS",
	      "value": "1"
	    }
	  }), _vm._v(" "), _c('el-option', {
	    attrs: {
	      "label": "Android",
	      "value": "0"
	    }
	  })], 1)], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "APP名称",
	      "prop": "app_name",
	      "rules": {
	        required: true,
	        message: '请选择APP名称'
	      }
	    }
	  }, [_c('el-select', {
	    attrs: {
	      "placeholder": "请选择APP名称"
	    },
	    model: {
	      value: (_vm.formScopeDialog.app_name),
	      callback: function($$v) {
	        _vm.formScopeDialog.app_name = $$v
	      },
	      expression: "formScopeDialog.app_name"
	    }
	  }, [_c('el-option', {
	    attrs: {
	      "label": "集客",
	      "value": "0"
	    }
	  }), _vm._v(" "), _c('el-option', {
	    attrs: {
	      "label": "全知道",
	      "value": "1"
	    }
	  }), _vm._v(" "), _c('el-option', {
	    attrs: {
	      "label": "机惠多",
	      "value": "2"
	    }
	  })], 1)], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "版本名称",
	      "prop": "version_title",
	      "rules": [{
	        required: true,
	        message: '请输入版本名称'
	      }, {
	        min: 1,
	        max: 10,
	        message: '请输入1-10个字符'
	      }]
	    }
	  }, [_c('el-input', {
	    attrs: {
	      "minlength": 1,
	      "maxlength": 10,
	      "placeholder": "请输入1-10个字符，可由字母、数字、符号组成"
	    },
	    model: {
	      value: (_vm.formScopeDialog.version_title),
	      callback: function($$v) {
	        _vm.formScopeDialog.version_title = $$v
	      },
	      expression: "formScopeDialog.version_title"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "版本号",
	      "prop": "version_no",
	      "rules": [{
	        required: true,
	        message: '请输入版本号'
	      }, {
	        min: 1,
	        max: 5,
	        message: '请输入1-5个字符'
	      }]
	    }
	  }, [_c('el-input', {
	    attrs: {
	      "minlength": 1,
	      "maxlength": 5,
	      "placeholder": "请输入1-5个字符，可由数字组成"
	    },
	    model: {
	      value: (_vm.formScopeDialog.version_no),
	      callback: function($$v) {
	        _vm.formScopeDialog.version_no = $$v
	      },
	      expression: "formScopeDialog.version_no"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "版本描述"
	    }
	  }, [_c('el-input', {
	    attrs: {
	      "type": "textarea",
	      "autosize": {
	        minRows: 2,
	        maxRows: 4
	      },
	      "minlength": 0,
	      "maxlength": 200,
	      "placeholder": "请输入0-200字符，可由汉字、字母、数字、符号组成"
	    },
	    model: {
	      value: (_vm.formScopeDialog.version_info),
	      callback: function($$v) {
	        _vm.formScopeDialog.version_info = $$v
	      },
	      expression: "formScopeDialog.version_info"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "发布地址",
	      "prop": "package_url",
	      "rules": [{
	        required: true,
	        message: '请输入发布地址'
	      }, {
	        min: 1,
	        max: 200,
	        message: '请输入1-200个字符'
	      }]
	    }
	  }, [_c('el-input', {
	    attrs: {
	      "minlength": 1,
	      "maxlength": 200,
	      "placeholder": "请输入1-200个字符，可由字母、数字、符号组成，并以http://或https://开头"
	    },
	    model: {
	      value: (_vm.formScopeDialog.package_url),
	      callback: function($$v) {
	        _vm.formScopeDialog.package_url = $$v
	      },
	      expression: "formScopeDialog.package_url"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "更新类型",
	      "prop": "force_update",
	      "rules": {
	        required: true,
	        message: '请选择更新类型'
	      }
	    }
	  }, [_c('el-select', {
	    attrs: {
	      "placeholder": "请选择更新类型"
	    },
	    model: {
	      value: (_vm.formScopeDialog.force_update),
	      callback: function($$v) {
	        _vm.formScopeDialog.force_update = $$v
	      },
	      expression: "formScopeDialog.force_update"
	    }
	  }, [_c('el-option', {
	    attrs: {
	      "label": "强制更新",
	      "value": "1"
	    }
	  }), _vm._v(" "), _c('el-option', {
	    attrs: {
	      "label": "选择更新",
	      "value": "0"
	    }
	  })], 1)], 1)], 1)], 1), _vm._v(" "), _c('div', {
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
	        _vm.publish(_vm.formScopeDialog)
	      }
	    }
	  }, [_vm._v("发布")]), _vm._v(" "), _c('el-button', {
	    on: {
	      "click": function($event) {
	        _vm.dialogFormVisible = false
	      }
	    }
	  }, [_vm._v("取消")])], 1)])], 1)
	},staticRenderFns: []}
	module.exports.render._withStripped = true
	if (false) {
	  module.hot.accept()
	  if (module.hot.data) {
	     require("vue-hot-reload-api").rerender("data-v-04547cee", module.exports)
	  }
	}

/***/ })

});
//# sourceMappingURL=18.js.map