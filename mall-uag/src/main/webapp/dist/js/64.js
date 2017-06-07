webpackJsonp([64],{

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

/***/ 95:
/***/ (function(module, exports, __webpack_require__) {

	var Component = __webpack_require__(13)(
	  /* script */
	  __webpack_require__(96),
	  /* template */
	  __webpack_require__(97),
	  /* scopeId */
	  null,
	  /* cssModules */
	  null
	)
	Component.options.__file = "/Users/xiangyangli/projects/hjh/jhd/mall-uag/src/main/webapp/app/components/Feedback.vue"
	if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key !== "__esModule"})) {console.error("named exports are not supported in *.vue files.")}
	if (Component.options.functional) {console.error("[vue-loader] Feedback.vue: functional components are not supported with templates, they should use render functions.")}
	
	/* hot reload */
	if (false) {(function () {
	  var hotAPI = require("vue-hot-reload-api")
	  hotAPI.install(require("vue"), false)
	  if (!hotAPI.compatible) return
	  module.hot.accept()
	  if (!module.hot.data) {
	    hotAPI.createRecord("data-v-3cfb6448", Component.options)
	  } else {
	    hotAPI.reload("data-v-3cfb6448", Component.options)
	  }
	})()}
	
	module.exports = Component.exports


/***/ }),

/***/ 96:
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
	      feedbackList: {}
	    };
	  },
	  created: function created() {
	    document.title = "后台管理系统-反馈信息";
	    this.getListBySearchData();
	  },
	
	  methods: {
	    getDefaultSearchForm: function getDefaultSearchForm() {
	      return {
	        status: "",
	        type: "",
	        feedback_id: "",
	        content: "",
	        create_user_name: "",
	        date_start: "",
	        date_end: ""
	      };
	    },
	    getDefaultDialogData: function getDefaultDialogData() {
	      return {
	        reply_content: ""
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
	    reply: function reply(index, list) {
	      var item = list[index];
	      this.formScopeDialog = {
	        feedback_id: item.feedback_id,
	        reply_content: ""
	      };
	      this.dialogFormVisible = true;
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
	    publish: function publish() {
	      var _this = this;
	
	      this.$http.post("/feedback/reply", this.formScopeDialog).then(function (response) {
	        var response = response.data;
	        _this.$message({
	          type: response.error_no == '0' ? "success" : "error",
	          message: response.error_no == '0' ? "回复成功" : response.error_info
	        });
	        if (response.error_no == '0') {
	          _this.getAppList();
	          _this.dialogFormVisible = false;
	        }
	      });
	    },
	    deleteR: function deleteR(index, list) {
	      var _this2 = this;
	
	      var item = list[index];
	      var param = {
	        //access_token : localStorage.access_token,
	        feedback_id: item.feedback_id
	      };
	      this.$confirm("确定要删除该反馈信息吗？", '', {
	        confirmButtonText: '确定',
	        cancelButtonText: '取消',
	        type: 'warning'
	      }).then(function () {
	        _this2.$http.post('/feedback/delete', param).then(function (response) {
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
	      var _this3 = this;
	
	      if (this.loading) return;
	      this.loading = true;
	      this.$http.post("/feedback/query", _common2.default.filterParamByEmptyValue(this.searchDataCache)).then(function (response) {
	        var jsondata = response.data;
	        if (jsondata.error_no == 0) {
	          jsondata.result_list = jsondata.result_list.map(function (item) {
	            item.initDate = _common2.default.formatDateConcat(item.init_date, item.init_time);
	            return item;
	          });
	          _this3.feedbackList = jsondata;
	        } else {
	          _this3.$message({
	            type: "warning",
	            message: jsonData.error_info
	          });
	        }
	        _this3.loading = false;
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

/***/ }),

/***/ 97:
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
	  }, [_vm._v("用户中心")]), _vm._v(" "), _c('el-breadcrumb-item', [_vm._v("APP应用管理")]), _vm._v(" "), _c('el-breadcrumb-item', [_vm._v("反馈信息")])], 1)], 1), _vm._v(" "), _c('div', {
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
	      "placeholder": "选择状态"
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
	      "label": "用户反馈",
	      "value": "1"
	    }
	  }), _vm._v(" "), _c('el-option', {
	    attrs: {
	      "label": "已回复",
	      "value": "2"
	    }
	  }), _vm._v(" "), _c('el-option', {
	    attrs: {
	      "label": "删除",
	      "value": "0"
	    }
	  })], 1)], 1), _vm._v(" "), _c('el-form-item', [_c('el-select', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "选择类别"
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
	      "label": "产品建议",
	      "value": "0"
	    }
	  }), _vm._v(" "), _c('el-option', {
	    attrs: {
	      "label": "程序问题",
	      "value": "1"
	    }
	  }), _vm._v(" "), _c('el-option', {
	    attrs: {
	      "label": "其它",
	      "value": "2"
	    }
	  })], 1)], 1), _vm._v(" "), _c('el-form-item', [_c('el-input', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "输入反馈信息ID"
	    },
	    model: {
	      value: (_vm.searchForm.feedback_id),
	      callback: function($$v) {
	        _vm.searchForm.feedback_id = $$v
	      },
	      expression: "searchForm.feedback_id"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', [_c('el-input', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "输入反馈信息关键字"
	    },
	    model: {
	      value: (_vm.searchForm.content),
	      callback: function($$v) {
	        _vm.searchForm.content = $$v
	      },
	      expression: "searchForm.content"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', [_c('el-date-picker', {
	    attrs: {
	      "type": "datetimerange",
	      "picker-options": _vm.pickerOptions,
	      "placeholder": "选择反馈时间查询区间",
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
	      "placeholder": "输入反馈人姓名"
	    },
	    model: {
	      value: (_vm.searchForm.create_user_name),
	      callback: function($$v) {
	        _vm.searchForm.create_user_name = $$v
	      },
	      expression: "searchForm.create_user_name"
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
	      "data": _vm.feedbackList.result_list,
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
	      "prop": "feedback_id",
	      "label": "ID",
	      "width": "200"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "type",
	      "label": "类别",
	      "width": "150",
	      "align": "center"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_vm._v(_vm._s(scope.row.type == 0 ? "产品建议" : (scope.row.type == 1 ? "程序问题" : "其它")))]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "mobile_tel",
	      "label": "注册手机号",
	      "align": "center",
	      "width": "150"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "content",
	      "label": "反馈内容",
	      "width": "500",
	      "show-overflow-tooltip": _vm.showtooltip
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
	        return [_vm._v(_vm._s(scope.row.status == 0 ? "删除" : (scope.row.status == 1 ? "用户反馈" : "已回复")))]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "initDate",
	      "label": "反馈时间",
	      "align": "center",
	      "width": "180"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "create_user_name",
	      "label": "反馈人",
	      "align": "center",
	      "width": "120"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "fixed": "right",
	      "label": "操作",
	      "width": "160"
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
	              _vm.reply(scope.$index, _vm.feedbackList.result_list)
	            }
	          }
	        }, [_vm._v("回复")]), _vm._v(" "), _c('el-button', {
	          attrs: {
	            "type": "danger"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.deleteR(scope.$index, _vm.feedbackList.result_list)
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
	      "total": _vm.feedbackList.totol_num
	    },
	    on: {
	      "current-change": _vm.handleCurrentChange
	    }
	  })], 1), _vm._v(" "), _c('el-dialog', {
	    attrs: {
	      "title": "回复反馈内容"
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
	      "height": "230px",
	      "overflow": "hidden",
	      "overflow-y": "auto"
	    }
	  }, [_c('el-input', {
	    attrs: {
	      "type": "textarea",
	      "rows": 10,
	      "placeholder": "请输入反馈内容"
	    },
	    model: {
	      value: (_vm.formScopeDialog.reply_content),
	      callback: function($$v) {
	        _vm.formScopeDialog.reply_content = $$v
	      },
	      expression: "formScopeDialog.reply_content"
	    }
	  })], 1), _vm._v(" "), _c('div', {
	    staticClass: "dialog-footer",
	    staticStyle: {
	      "text-align": "center"
	    },
	    slot: "footer"
	  }, [_c('el-button', {
	    attrs: {
	      "type": "primary",
	      "disabled": !_vm.formScopeDialog.reply_content
	    },
	    on: {
	      "click": function($event) {
	        _vm.publish(_vm.formScopeDialog)
	      }
	    }
	  }, [_vm._v("确定")]), _vm._v(" "), _c('el-button', {
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
	     require("vue-hot-reload-api").rerender("data-v-3cfb6448", module.exports)
	  }
	}

/***/ })

});
//# sourceMappingURL=64.js.map