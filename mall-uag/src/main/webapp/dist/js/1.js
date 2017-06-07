webpackJsonp([1],{

/***/ 12:
/***/ (function(module, exports, __webpack_require__) {

	var Component = __webpack_require__(13)(
	  /* script */
	  __webpack_require__(14),
	  /* template */
	  __webpack_require__(15),
	  /* scopeId */
	  null,
	  /* cssModules */
	  null
	)
	Component.options.__file = "D:\\eclipse-workspace3\\mall-full-jhd\\mall-uag\\src\\main\\webapp\\app\\components\\App.vue"
	if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key !== "__esModule"})) {console.error("named exports are not supported in *.vue files.")}
	if (Component.options.functional) {console.error("[vue-loader] App.vue: functional components are not supported with templates, they should use render functions.")}
	
	/* hot reload */
	if (false) {(function () {
	  var hotAPI = require("vue-hot-reload-api")
	  hotAPI.install(require("vue"), false)
	  if (!hotAPI.compatible) return
	  module.hot.accept()
	  if (!module.hot.data) {
	    hotAPI.createRecord("data-v-53f98e9a", Component.options)
	  } else {
	    hotAPI.reload("data-v-53f98e9a", Component.options)
	  }
	})()}
	
	module.exports = Component.exports


/***/ }),

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

/***/ 14:
/***/ (function(module, exports) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
	    value: true
	});
	//
	//
	//
	//
	//
	//
	
	exports.default = {
	    data: function data() {
	        return {};
	    },
	
	    methods: {
	        turnpage: function turnpage() {
	            window.open(location.hash.replace("#", "/html") + ".jsp", "_blank");
	        }
	    }
	};

/***/ }),

/***/ 15:
/***/ (function(module, exports, __webpack_require__) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticStyle: {
	      "line-height": "60px",
	      "font-size": "16px",
	      "color": "#333333",
	      "margin-left": "20px"
	    }
	  }, [_vm._v("\n    这个页面尚未改造 "), _c('a', {
	    attrs: {
	      "target": "_blank",
	      "href": "javascript:void(0);"
	    },
	    on: {
	      "click": _vm.turnpage
	    }
	  }, [_vm._v("点击这里查看旧版页面")])])
	},staticRenderFns: []}
	module.exports.render._withStripped = true
	if (false) {
	  module.hot.accept()
	  if (module.hot.data) {
	     require("vue-hot-reload-api").rerender("data-v-53f98e9a", module.exports)
	  }
	}

/***/ })

});
//# sourceMappingURL=1.js.map