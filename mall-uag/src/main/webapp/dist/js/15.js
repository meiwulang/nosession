webpackJsonp([15],{

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

/***/ 28:
/***/ (function(module, exports) {

	/*
		MIT License http://www.opensource.org/licenses/mit-license.php
		Author Tobias Koppers @sokra
	*/
	// css base code, injected by the css-loader
	module.exports = function() {
		var list = [];
	
		// return the list of modules as css string
		list.toString = function toString() {
			var result = [];
			for(var i = 0; i < this.length; i++) {
				var item = this[i];
				if(item[2]) {
					result.push("@media " + item[2] + "{" + item[1] + "}");
				} else {
					result.push(item[1]);
				}
			}
			return result.join("");
		};
	
		// import a list of modules into the list
		list.i = function(modules, mediaQuery) {
			if(typeof modules === "string")
				modules = [[null, modules, ""]];
			var alreadyImportedModules = {};
			for(var i = 0; i < this.length; i++) {
				var id = this[i][0];
				if(typeof id === "number")
					alreadyImportedModules[id] = true;
			}
			for(i = 0; i < modules.length; i++) {
				var item = modules[i];
				// skip already imported module
				// this implementation is not 100% perfect for weird media query combinations
				//  when a module is imported multiple times with different media queries.
				//  I hope this will never occur (Hey this way we have smaller bundles)
				if(typeof item[0] !== "number" || !alreadyImportedModules[item[0]]) {
					if(mediaQuery && !item[2]) {
						item[2] = mediaQuery;
					} else if(mediaQuery) {
						item[2] = "(" + item[2] + ") and (" + mediaQuery + ")";
					}
					list.push(item);
				}
			}
		};
		return list;
	};


/***/ }),

/***/ 29:
/***/ (function(module, exports, __webpack_require__) {

	/*
	  MIT License http://www.opensource.org/licenses/mit-license.php
	  Author Tobias Koppers @sokra
	  Modified by Evan You @yyx990803
	*/
	
	var hasDocument = typeof document !== 'undefined'
	
	if (false) {
	  if (!hasDocument) {
	    throw new Error(
	    'vue-style-loader cannot be used in a non-browser environment. ' +
	    "Use { target: 'node' } in your Webpack config to indicate a server-rendering environment."
	  ) }
	}
	
	var listToStyles = __webpack_require__(30)
	
	/*
	type StyleObject = {
	  id: number;
	  parts: Array<StyleObjectPart>
	}
	
	type StyleObjectPart = {
	  css: string;
	  media: string;
	  sourceMap: ?string
	}
	*/
	
	var stylesInDom = {/*
	  [id: number]: {
	    id: number,
	    refs: number,
	    parts: Array<(obj?: StyleObjectPart) => void>
	  }
	*/}
	
	var head = hasDocument && (document.head || document.getElementsByTagName('head')[0])
	var singletonElement = null
	var singletonCounter = 0
	var isProduction = false
	var noop = function () {}
	
	// Force single-tag solution on IE6-9, which has a hard limit on the # of <style>
	// tags it will allow on a page
	var isOldIE = typeof navigator !== 'undefined' && /msie [6-9]\b/.test(navigator.userAgent.toLowerCase())
	
	module.exports = function (parentId, list, _isProduction) {
	  isProduction = _isProduction
	
	  var styles = listToStyles(parentId, list)
	  addStylesToDom(styles)
	
	  return function update (newList) {
	    var mayRemove = []
	    for (var i = 0; i < styles.length; i++) {
	      var item = styles[i]
	      var domStyle = stylesInDom[item.id]
	      domStyle.refs--
	      mayRemove.push(domStyle)
	    }
	    if (newList) {
	      styles = listToStyles(parentId, newList)
	      addStylesToDom(styles)
	    } else {
	      styles = []
	    }
	    for (var i = 0; i < mayRemove.length; i++) {
	      var domStyle = mayRemove[i]
	      if (domStyle.refs === 0) {
	        for (var j = 0; j < domStyle.parts.length; j++) {
	          domStyle.parts[j]()
	        }
	        delete stylesInDom[domStyle.id]
	      }
	    }
	  }
	}
	
	function addStylesToDom (styles /* Array<StyleObject> */) {
	  for (var i = 0; i < styles.length; i++) {
	    var item = styles[i]
	    var domStyle = stylesInDom[item.id]
	    if (domStyle) {
	      domStyle.refs++
	      for (var j = 0; j < domStyle.parts.length; j++) {
	        domStyle.parts[j](item.parts[j])
	      }
	      for (; j < item.parts.length; j++) {
	        domStyle.parts.push(addStyle(item.parts[j]))
	      }
	      if (domStyle.parts.length > item.parts.length) {
	        domStyle.parts.length = item.parts.length
	      }
	    } else {
	      var parts = []
	      for (var j = 0; j < item.parts.length; j++) {
	        parts.push(addStyle(item.parts[j]))
	      }
	      stylesInDom[item.id] = { id: item.id, refs: 1, parts: parts }
	    }
	  }
	}
	
	function createStyleElement () {
	  var styleElement = document.createElement('style')
	  styleElement.type = 'text/css'
	  head.appendChild(styleElement)
	  return styleElement
	}
	
	function addStyle (obj /* StyleObjectPart */) {
	  var update, remove
	  var styleElement = document.querySelector('style[data-vue-ssr-id~="' + obj.id + '"]')
	
	  if (styleElement) {
	    if (isProduction) {
	      // has SSR styles and in production mode.
	      // simply do nothing.
	      return noop
	    } else {
	      // has SSR styles but in dev mode.
	      // for some reason Chrome can't handle source map in server-rendered
	      // style tags - source maps in <style> only works if the style tag is
	      // created and inserted dynamically. So we remove the server rendered
	      // styles and inject new ones.
	      styleElement.parentNode.removeChild(styleElement)
	    }
	  }
	
	  if (isOldIE) {
	    // use singleton mode for IE9.
	    var styleIndex = singletonCounter++
	    styleElement = singletonElement || (singletonElement = createStyleElement())
	    update = applyToSingletonTag.bind(null, styleElement, styleIndex, false)
	    remove = applyToSingletonTag.bind(null, styleElement, styleIndex, true)
	  } else {
	    // use multi-style-tag mode in all other cases
	    styleElement = createStyleElement()
	    update = applyToTag.bind(null, styleElement)
	    remove = function () {
	      styleElement.parentNode.removeChild(styleElement)
	    }
	  }
	
	  update(obj)
	
	  return function updateStyle (newObj /* StyleObjectPart */) {
	    if (newObj) {
	      if (newObj.css === obj.css &&
	          newObj.media === obj.media &&
	          newObj.sourceMap === obj.sourceMap) {
	        return
	      }
	      update(obj = newObj)
	    } else {
	      remove()
	    }
	  }
	}
	
	var replaceText = (function () {
	  var textStore = []
	
	  return function (index, replacement) {
	    textStore[index] = replacement
	    return textStore.filter(Boolean).join('\n')
	  }
	})()
	
	function applyToSingletonTag (styleElement, index, remove, obj) {
	  var css = remove ? '' : obj.css
	
	  if (styleElement.styleSheet) {
	    styleElement.styleSheet.cssText = replaceText(index, css)
	  } else {
	    var cssNode = document.createTextNode(css)
	    var childNodes = styleElement.childNodes
	    if (childNodes[index]) styleElement.removeChild(childNodes[index])
	    if (childNodes.length) {
	      styleElement.insertBefore(cssNode, childNodes[index])
	    } else {
	      styleElement.appendChild(cssNode)
	    }
	  }
	}
	
	function applyToTag (styleElement, obj) {
	  var css = obj.css
	  var media = obj.media
	  var sourceMap = obj.sourceMap
	
	  if (media) {
	    styleElement.setAttribute('media', media)
	  }
	
	  if (sourceMap) {
	    // https://developer.chrome.com/devtools/docs/javascript-debugging
	    // this makes source maps inside style tags work properly in Chrome
	    css += '\n/*# sourceURL=' + sourceMap.sources[0] + ' */'
	    // http://stackoverflow.com/a/26603875
	    css += '\n/*# sourceMappingURL=data:application/json;base64,' + btoa(unescape(encodeURIComponent(JSON.stringify(sourceMap)))) + ' */'
	  }
	
	  if (styleElement.styleSheet) {
	    styleElement.styleSheet.cssText = css
	  } else {
	    while (styleElement.firstChild) {
	      styleElement.removeChild(styleElement.firstChild)
	    }
	    styleElement.appendChild(document.createTextNode(css))
	  }
	}


/***/ }),

/***/ 30:
/***/ (function(module, exports) {

	/**
	 * Translates the list format produced by css-loader into something
	 * easier to manipulate.
	 */
	module.exports = function listToStyles (parentId, list) {
	  var styles = []
	  var newStyles = {}
	  for (var i = 0; i < list.length; i++) {
	    var item = list[i]
	    var id = item[0]
	    var css = item[1]
	    var media = item[2]
	    var sourceMap = item[3]
	    var part = {
	      id: parentId + ':' + i,
	      css: css,
	      media: media,
	      sourceMap: sourceMap
	    }
	    if (!newStyles[id]) {
	      styles.push(newStyles[id] = { id: id, parts: [part] })
	    } else {
	      newStyles[id].parts.push(part)
	    }
	  }
	  return styles
	}


/***/ }),

/***/ 77:
/***/ (function(module, exports, __webpack_require__) {

	
	/* styles */
	__webpack_require__(78)
	
	var Component = __webpack_require__(13)(
	  /* script */
	  __webpack_require__(80),
	  /* template */
	  __webpack_require__(81),
	  /* scopeId */
	  null,
	  /* cssModules */
	  null
	)
	Component.options.__file = "D:\\eclipse-workspace3\\mall-full-jhd\\mall-uag\\src\\main\\webapp\\app\\components\\brandIntroduction.vue"
	if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key !== "__esModule"})) {console.error("named exports are not supported in *.vue files.")}
	if (Component.options.functional) {console.error("[vue-loader] brandIntroduction.vue: functional components are not supported with templates, they should use render functions.")}
	
	/* hot reload */
	if (false) {(function () {
	  var hotAPI = require("vue-hot-reload-api")
	  hotAPI.install(require("vue"), false)
	  if (!hotAPI.compatible) return
	  module.hot.accept()
	  if (!module.hot.data) {
	    hotAPI.createRecord("data-v-00d847cc", Component.options)
	  } else {
	    hotAPI.reload("data-v-00d847cc", Component.options)
	  }
	})()}
	
	module.exports = Component.exports


/***/ }),

/***/ 78:
/***/ (function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(79);
	if(typeof content === 'string') content = [[module.id, content, '']];
	if(content.locals) module.exports = content.locals;
	// add the styles to the DOM
	var update = __webpack_require__(29)("46afb935", content, false);
	// Hot Module Replacement
	if(false) {
	 // When the styles change, update the <style> tags
	 if(!content.locals) {
	   module.hot.accept("!!../../node_modules/css-loader/index.js?sourceMap!../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-00d847cc!../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./brandIntroduction.vue", function() {
	     var newContent = require("!!../../node_modules/css-loader/index.js?sourceMap!../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-00d847cc!../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./brandIntroduction.vue");
	     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
	     update(newContent);
	   });
	 }
	 // When the module is disposed, remove the <style> tags
	 module.hot.dispose(function() { update(); });
	}

/***/ }),

/***/ 79:
/***/ (function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(28)();
	// imports
	
	
	// module
	exports.push([module.id, "\n.simditor .simditor-body{\n\theight: 150px;\n\tmin-height: 150px;\n\toverflow: hidden;\n\toverflow-y: auto;\n\tpadding: 10px;\n}\n.el-dialog__body .el-form-item__content{\n\tline-height: 0;\n}\n.el-upload__tip{\n\tmargin-top: 10px;\n}\n", "", {"version":3,"sources":["/./app/components/brandIntroduction.vue?e747f806"],"names":[],"mappings":";AA6JA;CACA,cAAA;CACA,kBAAA;CACA,iBAAA;CACA,iBAAA;CACA,cAAA;CACA;AACA;CACA,eAAA;CACA;AACA;CACA,iBAAA;CACA","file":"brandIntroduction.vue","sourcesContent":["<template>\n    <div>\n    \t\t<!-- 面包屑 -->\n\t\t<div class=\"hjh-breadcrumb\">\n\t\t\t<el-breadcrumb separator=\"/\">\n\t\t\t\t<el-breadcrumb-item :to=\"{ path: '/' }\">用户中心</el-breadcrumb-item>\n\t\t\t\t<el-breadcrumb-item>内容管理</el-breadcrumb-item>\n\t\t\t\t<el-breadcrumb-item>品牌介绍</el-breadcrumb-item>\n\t\t\t</el-breadcrumb>\t\n\t\t</div>\n\t\t<!-- 面包屑 end -->\n\t\t<div class=\"block-white\">\n\t\t\t<el-button type=\"primary\" @click=\"addNew\">新增</el-button>\n\t\t</div>\n\t\t<!-- 订单列表 -->\n\t\t<div class=\"block-white\">\n\t\t\t<el-table v-loading.body=\"loading\" :data=\"activityList.result_list\" border stripe style=\"width: 100%\" max-height=\"460\">\n\t\t\t\t<el-table-column fixed label=\"序号\" width=\"70\" align=\"center\"><template scope=\"scope\">{{scope.$index+1}}</template></el-table-column>\n\t\t\t\t<el-table-column prop=\"brand_name\" label=\"品牌\" width=\"180\">\n\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t<a style=\"cursor:pointer\" @click=\"checkBrandDetail(scope.$index,activityList.result_list)\">{{scope.row.brand_name}}</a>\n\t\t\t\t\t</template>\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column prop=\"brand_info_img\" label=\"图片\" width=\"120\">\n\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t<el-button @click.native.prevent=\"checkImage(scope.$index, activityList.result_list)\" type=\"text\">点击查看</el-button>\n\t\t\t\t\t</template>\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column prop=\"sort\" label=\"排序\" width=\"80\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"app_display\" label=\"状态\" width=\"100\">\n\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t{{scope.row.app_display == 1?\"已上架\":\"已下架\"}}\n\t\t\t\t\t</template>\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column prop=\"remark\" label=\"备注\" width=\"300\" :show-overflow-tooltip=\"showtooltip\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"reinitTime\" label=\"添加时间\" width=\"180\" align=\"center\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"create_user\" label=\"添加人\" width=\"180\" align=\"center\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"reupdateTime\" label=\"最后修改时间\" width=\"180\" align=\"center\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"update_user\" label=\"最后修改人\" width=\"180\" align=\"center\"></el-table-column>\n\t\t\t\t<el-table-column fixed=\"right\" label=\"操作\" width=\"140\" align=\"center\">\n\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t<el-button @click.native.prevent=\"offTheShelf(scope.$index, activityList.result_list)\" type=\"text\" v-if=\"scope.row.state\">下架</el-button>\n\t\t\t\t\t\t\n\t\t\t\t\t\t<el-button @click.native.prevent=\"modifyActivity(scope.$index, activityList.result_list)\" type=\"text\" v-if=\"!scope.row.state\">编辑</el-button>\n\t\t\t\t\t\t<el-button @click.native.prevent=\"onTheShelves(scope.$index, activityList.result_list)\" type=\"text\"  v-if=\"!scope.row.state\">上架</el-button>\n\t\t\t\t\t</template>\n\t\t\t\t</el-table-column>\n\t\t\t</el-table>\n\t\t</div>\n\t\t<!-- 订单列表 end -->\n\t\t<!-- 翻页组件 -->\n\t\t<div class=\"block-white\">\n\t\t\t<el-pagination\n\t\t      @current-change=\"handleCurrentChange\"\n\t\t      :current-page=\"page\"\n\t\t      :page-size=\"limit\"\n\t\t      layout=\"total, prev, pager, next, jumper\"\n\t\t      :total=\"activityList.total_num\">\n\t\t    </el-pagination>\n\t\t</div>\n\t\t<!-- 翻页组件 end -->\n\t\t<el-dialog title=\"图片展示\" v-model=\"dialogImageVisible\">\n\t\t\t<div style=\"text-align: center;\">\n\t\t\t\t<img style=\"display: inline-block; margin: 0 auto; max-width: 100%;\" :src=\"BigImageUrl\">\n\t\t\t</div>\n\t\t\t<div slot=\"footer\" class=\"dialog-footer\" style=\"text-align: center;\">\n\t\t\t\t<el-button @click=\"dialogImageVisible = false\">关 闭</el-button>\n\t\t\t</div>\n\t\t</el-dialog>\n\t\t<el-dialog title=\"新增品牌介绍\" v-model=\"dialogFormVisibleAdd\">\n\t\t\t<div style=\"height: 570px; overflow: hidden; overflow-y: auto; padding-right: 20px;\">\n\t\t\t\t<el-form label-position=\"right\" :label-width=\"formLabelWidth\" :ref=\"formScopeDialogAdd\" :model=\"formScopeDialogAdd\">\n\t\t\t\t\t<el-form-item label=\"图片上传\" prop=\"brand_info_img\" :rules=\"{ required: true, message: '请上传图片' }\">\n\t\t\t\t\t\t<el-input v-model=\"formScopeDialogAdd.brand_info_img\" type=\"hidden\"></el-input>\n\t\t\t\t\t\t<el-upload\n\t\t\t\t\t\t  class=\"upload-demo\"\n\t\t\t\t\t\t  action=\"https://jsonplaceholder.typicode.com/posts/\"\n\t\t\t\t\t\t  :auto-upload=\"false\"\n\t\t\t\t\t\t\t  :on-change=\"handleFileChange\"\n\t\t\t\t\t\t  :file-list=\"filesList\">\n\t\t\t\t\t\t  <el-button size=\"small\" type=\"primary\">点击上传</el-button>\n\t\t\t\t\t\t  <div class=\"el-upload__tip\" slot=\"tip\">建议尺寸：750*300px 且不超过500kb</div>\n\t\t\t\t\t\t</el-upload>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"排序\" prop=\"sort\" :rules=\"{ required: true, message: '请设置排序值' }\">\n\t\t\t\t\t\t<el-input type=\"number\" v-model=\"formScopeDialogAdd.sort\" :minlength=\"1\" :maxlength=\"3\" placeholder=\"排序\" style=\"width:150px;\"></el-input>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"品牌\" prop=\"brand_id\" :rules=\"{ required: true, message: '请输入品牌' }\">\n\t\t\t\t\t\t<el-select v-model=\"formScopeDialogAdd.brand_id\" filterable placeholder=\"搜索品牌\" style=\"width: 200px;\">\n\t\t\t\t\t\t\t<el-option v-for=\"item in brandlist\" :key=\"item.brand_id\" :label=\"item.brand_name\" :value=\"item.brand_id\">\n\t\t\t\t\t\t\t</el-option>\n\t\t\t\t\t\t</el-select>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"\" prop=\"app_display\" :rules=\"{ required: true, message: '请设置品牌介绍的状态' }\">\n\t\t\t\t\t\t<el-radio class=\"radio\" v-model=\"formScopeDialogAdd.app_display\" label=\"1\">上架</el-radio>\n  \t\t\t\t\t\t<el-radio class=\"radio\" v-model=\"formScopeDialogAdd.app_display\" label=\"0\">下架</el-radio>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"展示内容\">\n\t\t\t\t\t\t<textarea id=\"dialogEditor\"></textarea>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"备注\" prop=\"remark\">\n\t\t\t\t\t\t<el-input v-model=\"formScopeDialogAdd.remark\" :minlength=\"1\" :maxlength=\"10\" placeholder=\"备注\" style=\"width:300px;\"></el-input>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t</el-form>\n\t\t\t</div>\n\t\t\t<div slot=\"footer\" class=\"dialog-footer\" style=\"text-align: center;\">\n\t\t\t\t<el-button type=\"primary\" @click=\"saveModel(formScopeDialogAdd)\">保存</el-button>\n\t\t\t\t<el-button @click=\"dialogFormVisibleAdd = false\">关 闭</el-button>\n\t\t\t</div>\n\t\t</el-dialog>\n\t\t<el-dialog title=\"编辑品牌介绍\" v-model=\"dialogFormVisibleUpdate\">\n\t\t\t<div style=\"height: 570px; overflow: hidden; overflow-y: auto; padding-right: 20px;\">\n\t\t\t\t<el-form label-position=\"right\" :label-width=\"formLabelWidth\" :ref=\"formScopeDialogUpdate\" :model=\"formScopeDialogUpdate\">\n\t\t\t\t\t<el-form-item label=\"图片上传\" prop=\"brand_info_img\" :rules=\"{ required: true, message: '请上传图片' }\">\n\t\t\t\t\t\t<el-input v-model=\"formScopeDialogUpdate.brand_info_img\" type=\"hidden\"></el-input>\n\t\t\t\t\t\t<el-upload\n\t\t\t\t\t\t  class=\"upload-demo\"\n\t\t\t\t\t\t  action=\"https://jsonplaceholder.typicode.com/posts/\"\n\t\t\t\t\t\t  :auto-upload=\"false\"\n\t\t\t\t\t\t  :on-change=\"handleFileChange\"\n\t\t\t\t\t\t  :file-list=\"filesList\">\n\t\t\t\t\t\t  <el-button size=\"small\" type=\"primary\">点击上传</el-button>\n\t\t\t\t\t\t  <div class=\"el-upload__tip\" slot=\"tip\">建议尺寸：750*300px 且不超过500kb</div>\n\t\t\t\t\t\t</el-upload>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"排序\" prop=\"sort\" :rules=\"{ required: true, message: '请设置排序值' }\">\n\t\t\t\t\t\t<el-input type=\"number\" v-model=\"formScopeDialogUpdate.sort\" :minlength=\"1\" :maxlength=\"3\" placeholder=\"排序\" style=\"width:150px;\"></el-input>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"品牌\" prop=\"brand_id\" :rules=\"{ required: true, message: '请输入品牌' }\">\n\t\t\t\t\t\t<el-select v-model=\"formScopeDialogUpdate.brand_id\" filterable placeholder=\"搜索品牌\" style=\"width: 200px;\">\n\t\t\t\t\t\t\t<el-option v-for=\"item in brandlist\" :key=\"item.brand_id\" :label=\"item.brand_name\" :value=\"item.brand_id\">\n\t\t\t\t\t\t\t</el-option>\n\t\t\t\t\t\t</el-select>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"\" prop=\"app_display\" :rules=\"{ required: true, message: '请设置品牌介绍的状态' }\">\n\t\t\t\t\t\t<el-radio class=\"radio\" v-model=\"formScopeDialogUpdate.app_display\" label=\"1\">上架</el-radio>\n  \t\t\t\t\t\t<el-radio class=\"radio\" v-model=\"formScopeDialogUpdate.app_display\" label=\"0\">下架</el-radio>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"展示内容\">\n\t\t\t\t\t\t<textarea id=\"dialogEditor\"></textarea>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"备注\" prop=\"remark\">\n\t\t\t\t\t\t<el-input v-model=\"formScopeDialogUpdate.remark\" :minlength=\"1\" :maxlength=\"10\" placeholder=\"备注\" style=\"width:300px;\"></el-input>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t</el-form>\n\t\t\t</div>\n\t\t\t<div slot=\"footer\" class=\"dialog-footer\" style=\"text-align: center;\">\n\t\t\t\t<el-button type=\"primary\" @click=\"saveModel(formScopeDialogUpdate)\">保存</el-button>\n\t\t\t\t<el-button @click=\"dialogFormVisibleUpdate = false\">关 闭</el-button>\n\t\t\t</div>\n\t\t</el-dialog>\n\t\t<el-dialog title=\"查看品牌介绍\" v-model=\"dialogFormVisibleCheck\">\n\t\t\t<iframe :src=\"ossurl\" style=\"display:block; border:2px solid #d1d1d1; width:375px; height:667px; overflow:hidden; overflow-y:auto; margin:0 auto;\"></iframe>\n\t\t</el-dialog>\n    </div>\n</template>\n<style>\n\t.simditor .simditor-body{\n\t\theight: 150px;\n\t\tmin-height: 150px;\n\t\toverflow: hidden;\n\t\toverflow-y: auto;\n\t\tpadding: 10px;\n\t}\n\t.el-dialog__body .el-form-item__content{\n\t\tline-height: 0;\n\t}\n\t.el-upload__tip{\n\t\tmargin-top: 10px;\n\t}\n</style>\n<script>\n\timport Common from './../js/common';\n    export default{\n        data(){\n            return{\n                activityList : {},\t\t\t\t\t\t\t\t//活动列表数据缓存\n                page : 1,\t\t\t\t\t\t\t\t\t\t//当前列表页码\n                limit : 10,\t\t\t\t\t\t\t\t\t\t//列表－每页条数\n                loading : false,\t\t\t\t\t\t\t\t\t//列表加载状态\n                activityListApi : \"/brandinfo/query\"\t,\t\t\t\t//品牌列表接口地址\n                updateappdisplayApi : \"/brandinfo/updateappdisplay\",//上架和下架接口\n                uploadImageApi : \"/json/901210\",\t\t\t\t\t//上传图片接口地址\n                uploadHtmlApi : \"/json/901211\",\t\t\t\t\t//上传富文本接口地址\n                addActivityApi : \"/brandinfo/add\",\t\t\t\t\t//添加活动接口地址\n                updateActivityApi : \"/brandinfo/update\",\t\t\t\t//更新活动接口地址\n                getTextDetailApi : \"/json/901213\",\t\t\t\t\t//获取富文本详情接口地址\n                showtooltip : true,\t\t\t\t\t\t\t\t//是否当内容过长被隐藏时显示tooltip true显示 flase不显示\n                resourceDomain : 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/',\n                dialogImageVisible : false,\n                dialogFormVisibleAdd : false,\n                dialogFormVisibleUpdate : false,\n                dialogFormVisibleCheck : false,\n                BigImageUrl : \"\",\n                formLabelWidth : \"85px\",\n                ismodify : false,\n                filesList : [],\n                formScopeDialogAdd : this.getDefaultDialogForm(),\n                formScopeDialogUpdate : {},\n                brandlist : [],\n                htmlTemplate : \"\",\n                ossurl : \"\"\n            }\n        },\n        created(){\n\t\t\tvar that = this;\n\t\t\t//获取品牌列表\n\t\t\tthat.$http.post(\"./../goodsbrand/queryforweb\",{limit:10000,page:1,status:1}).then(response =>{\n\t\t\t\tvar data = response.data;\n\t\t\t\tif(data.error_no == 0){\n\t\t\t\t\tthat.$data.brandlist = data.result_list;\n\t\t\t\t}\n\t\t\t});\n\t\t\tthis.$http.get(\"/dist/data/template.txt\").then(res=>{\n\t\t\t\tthis.htmlTemplate = res.data;\n\t\t\t});\n\t\t},\n\t\tmounted(){\n\t\t\tdocument.title = \"后台管理系统-活动管理\";\n\t\t\tthis.getactivityList();\n\t\t},\n\t\tmethods : {\n\t\t\t//获取弹窗表单默认数据\n\t\t\tgetDefaultDialogForm(){\n\t\t\t\treturn {\n                \t\tbrand_info_img : \"\",\n                \t\tbrand_info_content : \"\",\n                \t\tsort : \"\",\n                \t\tremark : \"\",\n                \t\tbrand_id : \"\",\n                \t\tapp_display : \"\"\n                }\n\t\t\t},\n\t\t\t//点击新增\n\t\t\taddNew(){\n\t\t\t\tvar that = this;\n\t\t\t\tthis.formScopeDialogAdd = this.getDefaultDialogForm();\n\t\t\t\tthis.filesList = [];\n\t\t\t\tthis.dialogFormVisibleAdd = true;\n\t\t\t\tthis.ismodify = false;\n\t\t\t\tsetTimeout(function(){\n\t\t\t\t\tthat.editor = new Simditor({\n\t\t\t\t\t\ttextarea: $('#dialogEditor'),\n\t\t\t\t\t\tupload:{}\n\t\t\t\t\t});\n\t\t\t\t},100);\n\t\t\t},\n\t\t\t//保存\n\t\t\tsaveModel(refname){\n\t\t\t\tvar that = this;\n\t\t\t\tthis.$refs[refname].validate((valid) => {\n\t\t\t\t\tif (valid) {\n\t\t\t\t\t\tthat.beforesaveActivity();\n\t\t\t\t\t}\n\t\t\t\t});\n\t\t\t},\n\t\t\t//提交前检查\n\t\t\tbeforesaveActivity(){\n\t\t\t\tvar text = this.editor.getValue(),that = this;\n\t\t\t\tvar formScopeDialog = that.ismodify ? this.formScopeDialogUpdate : this.formScopeDialogAdd;\n\t\t\t\tif(!text){\n\t\t\t\t\tthat.$message({\n\t\t\t\t\t\ttype: \"error\",\n\t\t\t\t\t\tmessage: \"请编写展示内容\"\n\t\t\t\t\t});\n\t\t\t\t\treturn;\n\t\t\t\t}\n\t\t\t\tif(formScopeDialog.remark && formScopeDialog.remark.length>10){\n\t\t\t\t\tthat.$message({\n\t\t\t\t\t\ttype: \"error\",\n\t\t\t\t\t\tmessage: \"备注过长 请输入1 至 10个字符\"\n\t\t\t\t\t});\n\t\t\t\t\treturn;\n\t\t\t\t}\n\t\t\t\tthat.replaceImage(text,function(newtext){\n\t\t\t\t\tnewtext = newtext.replace(/width=\\\"\\d+\\\"/g,\"\").replace(/height=\\\"\\d+\\\"/g,\"\");\n\t\t\t\t\tformScopeDialog.brand_info_content = that.htmlTemplate.replace(\"{{}}\",newtext);\n\t\t\t\t\tif(that.ismodify){\n\t\t\t\t\t\tthat.updateActivity();\n\t\t\t\t\t}else{\n\t\t\t\t\t\tthat.AddActivity();\n\t\t\t\t\t}\n\t\t\t\t});\n\t\t\t\t\n\t\t\t},\n\t\t\treplaceImage(text,callback){\n\t\t\t\tvar reg = /data:\\S+\\\"/g;\n\t\t\t\tvar arr = text.match(reg);\n\t\t\t\tvar count = 0,that = this;\n\t\t\t\tif(!arr || !arr.length){\n\t\t\t\t\tcallback(text);\n\t\t\t\t\treturn;\n\t\t\t\t}\n\t\t\t\tarr.map(function(item){\n\t\t\t\t\tvar src = item.substr(0,item.length-1);\n\t\t\t\t\tvar img = new Image();\n\t\t\t\t\t\timg.src = src;\n\t\t\t\t\t\timg.onload = function(){\n\t\t\t\t\t\t\tvar result = Common.compressImg(img,0.8,\"image/jpeg\");\n\t\t\t\t\t\t\tthat.uploadCompressImg(result,function(imgurl){\n\t\t\t\t\t\t\t\tcount++;\n\t\t\t\t\t\t\t\ttext = text.replace(src,imgurl);\n\t\t\t\t\t\t\t\tif(count == arr.length){\n\t\t\t\t\t\t\t\t\tcallback(text);\n\t\t\t\t\t\t\t\t}\n\t\t\t\t\t\t\t});\n\t\t\t\t\t\t}\n\t\t\t\t});\n\t\t\t},\n\t\t\tuploadCompressImg(result,cb){\n\t\t\t\tvar that = this,\n\t\t\t\t\tparam = {\n\t\t\t\t\t\taccess_token : localStorage.access_token,\n\t\t\t\t\t\timgType : 19,\n\t\t\t\t\t\tbase64Str : result\n\t\t\t\t\t}\n\t\t\t\tthat.$http.post(that.uploadImageApi,param).then(response => {\n\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\tcb(that.resourceDomain+jsondata.img_uri);\n\t\t\t\t});\n\t\t\t},\n\t\t\t//新增品牌介绍\n\t\t\tAddActivity(){\n\t\t\t\tvar that = this;\n\t\t\t\tthis.formScopeDialogAdd.access_token = localStorage.access_token;\n\t\t\t\tthat.$http.post(that.addActivityApi,this.formScopeDialogAdd).then(response=>{\n\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\tthat.$message({\n\t\t\t\t\t\ttype: jsondata.error_no==0 ? \"success\":\"error\",\n\t\t\t\t\t\tmessage: jsondata.error_no==0 ? \"活动添加成功\" : jsondata.error_info\n\t\t\t\t\t});\n\t\t\t\t\tif(jsondata.error_no==0){\n\t\t\t\t\t\tthat.dialogFormVisibleAdd= false;\n\t\t\t\t\t\tthat.getactivityList();\n\t\t\t\t\t\tthat.formScopeDialogAdd = that.getDefaultDialogForm();\n\t\t\t\t\t\tthat.filesList = [];\n\t\t\t\t\t}\n\t\t\t\t});\n\t\t\t},\n\t\t\t//更新品牌介绍\n\t\t\tupdateActivity(){\n\t\t\t\tvar that = this,index = this.formScopeDialogUpdate.brand_info_img.indexOf(\"data:\");\n\t\t\t\tconsole.log(index);\n\t\t\t\tif(index<0){\n\t\t\t\t\tthis.cache_brand_info_img = this.formScopeDialogUpdate.brand_info_img;\n\t\t\t\t\tdelete this.formScopeDialogUpdate.brand_info_img;\n\t\t\t\t}\n\t\t\t\tthis.formScopeDialogUpdate.access_token = localStorage.access_token;\n\t\t\t\tthis.formScopeDialogUpdate.status = this.formScopeDialogUpdate.app_display;\n\t\t\t\tthat.$http.post(that.updateActivityApi,this.formScopeDialogUpdate).then(response=>{\n\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\tthat.$message({\n\t\t\t\t\t\ttype: jsondata.error_no==0 ? \"success\":\"error\",\n\t\t\t\t\t\tmessage: jsondata.error_no==0 ? \"操作成功\" : jsondata.error_info\n\t\t\t\t\t});\n\t\t\t\t\tif(jsondata.error_no==0){\n\t\t\t\t\t\tthat.dialogFormVisibleUpdate = false;\n\t\t\t\t\t\tthat.getactivityList();\n\t\t\t\t\t\tthat.formScopeDialogUpdate = that.getDefaultDialogForm();\n\t\t\t\t\t\tthat.filesList = [];\n\t\t\t\t\t}else{\n\t\t\t\t\t\tif(this.cache_brand_info_img){\n\t\t\t\t\t\t\tthis.formScopeDialogUpdate.brand_info_img = this.cache_brand_info_img;\n\t\t\t\t\t\t\tdelete this.cache_brand_info_img;\n\t\t\t\t\t\t}\n\t\t\t\t\t}\n\t\t\t\t});\n\t\t\t},\n\t\t\t//点击下架\n\t\t\toffTheShelf(index,list){\n\t\t\t\tvar that = this,item = list[index];\n\t\t\t\tthis.updateappdisplay(0,item);\n\t\t\t},\n\t\t\t//点击上架\n\t\t\tonTheShelves(index,list){\n\t\t\t\tvar that = this,item = list[index];\n\t\t\t\tthis.updateappdisplay(1,item);\n\t\t\t},\n\t\t\t//上架或下架\n\t\t\tupdateappdisplay(app_display,item){\n\t\t\t\tvar that = this;\n\t\t\t\tthis.$confirm('确定要'+ (app_display==1?'上架':'下架') +'该品牌介绍吗？', '提示', {\n\t\t\t\t\tconfirmButtonText: '确定',\n\t\t\t\t\tcancelButtonText: '取消',\n\t\t\t\t\ttype: 'warning'\n\t\t\t\t}).then(() => {\n\t\t\t\t\tvar param = {\n\t\t\t\t\t\tagent_id : item.brand_id,\n\t\t\t\t\t\taccess_token : localStorage.access_token,\n\t\t\t\t\t\tapp_display : app_display,\n\t\t\t\t\t\tupdate_user : item.update_user,\n\t\t\t\t\t\tbrand_info_id : item.brand_info_id\n\t\t\t\t\t}\n\t\t\t\t\tthat.$http.post(that.updateappdisplayApi,param).then(response=>{\n\t\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\t\tthat.$message({\n\t\t\t\t\t\t\ttype: jsondata.error_no==0 ? \"success\":\"error\",\n\t\t\t\t\t\t\tmessage: jsondata.error_no==0 ? \"操作成功\" : jsondata.error_info\n\t\t\t\t\t\t});\n\t\t\t\t\t\tif(jsondata.error_no==0){\n\t\t\t\t\t\t\tthat.getactivityList();\n\t\t\t\t\t\t}\n\t\t\t\t\t});\n\t\t\t\t}).catch(() => {\n\t\t\t\t\t\n\t\t\t\t});\n\t\t\t},\n\t\t\t//点击编辑\n\t\t\tmodifyActivity(index,list){\n\t\t\t\tvar item = list[index];\n\t\t\t\tvar that = this;\n\t\t\t\tthis.formScopeDialogUpdate = {\n\t\t\t\t\tbrand_id : item.brand_id,\n\t\t\t\t\tsort : item.sort,\n\t\t\t\t\tbrand_info_img : item.brand_info_img,\n\t\t\t\t\tbrand_info_content : item.brand_info_content,\n\t\t\t\t\tremark : item.remark,\n\t\t\t\t\tapp_display : item.app_display,\n\t\t\t\t\tbrand_info_id : item.brand_info_id\n\t\t\t\t};\n\t\t\t\tthis.filesList = [{\n\t\t          name: item.brand_info_img,\n\t\t          url: 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+item.brand_info_img,\n\t\t          status: 'finished'\n\t\t        }];\n\t\t\t\tthis.dialogFormVisibleUpdate = true;\n\t\t\t\tthis.ismodify = true;\n\t\t\t\tsetTimeout(function(){\n\t\t\t\t\tthat.editor = new Simditor({\n\t\t\t\t\t\ttextarea: $('#dialogEditor'),\n\t\t\t\t\t\tupload:{}\n\t\t\t\t\t});\n\t\t\t\t\tvar content = that.formScopeDialogUpdate.brand_info_content,\n\t\t\t\t\t\tbool = content.indexOf(\"<body>\") > 0 && content.indexOf(\"</body>\") > 0;\n\t\t\t\t\tvar _content = !bool ? content : content.split(\"<body>\")[1].split(\"</body>\")[0];\n\t\t\t\t\t//console.log(_content);\n\t\t\t\t\tthat.editor.setValue(_content);\n\t\t\t\t},200);\n\t\t\t},\n\t\t\t//选择文件时\n\t\t\thandleFileChange(_file,_filelist){\n\t\t\t\tthis.filesList = [_filelist[_filelist.length-1]];\n\t\t\t\tvar that = this;\n\t\t\t\tvar reader = new FileReader(); \n\t\t\t\t\treader.readAsDataURL(_file.raw);\n\t\t\t\t\treader.onload = function(e){\n\t\t\t\t\t\tvar img = new Image();\n\t\t\t\t\t\t\timg.src = this.result;\n\t\t\t\t\t\t\timg.onload = function(){\n\t\t\t\t\t\t\t\tif(that.ismodify){\n\t\t\t\t\t\t\t\t\tthat.formScopeDialogUpdate.brand_info_img = Common.compressImg(img,0.8,\"image/jpeg\");\n\t\t\t\t\t\t\t\t}else{\n\t\t\t\t\t\t\t\t\tthat.formScopeDialogAdd.brand_info_img = Common.compressImg(img,0.8,\"image/jpeg\");\n\t\t\t\t\t\t\t\t}\n\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t$(\".el-upload-list > li\").last().prev().remove();\n\t\t\t\t\t\t\t}\n\t\t\t\t\t}\n\t\t\t},\n\t\t\t//图片压缩\n\t\t\tcompressImg(img, rate, fileType){\n\t\t\t\tvar cvs = document.createElement('canvas');\n\t\t\t\tvar width=img.width;\n\t\t\t\tvar height = img.height;\n\t\t\t\tvar dic=height/width;\n\t\t\t\tvar width_cvs=width>600?600:width;\n\t\t\t\tcvs.width = width_cvs;  //图片压缩的标准，这里是按照定款600px计算\n\t\t\t\tcvs.height = width_cvs * dic;\n\t\t\t\tvar cxt=cvs.getContext(\"2d\");\n\t\t\t\tcxt.fillStyle=\"#FFF\";\n\t\t\t    cxt.fillRect(0,0,width_cvs,width_cvs*dic);   \n\t\t\t\tcxt.drawImage(img,0,0,width_cvs,width_cvs*dic);\n\t\t\t\treturn cvs.toDataURL(fileType, rate);\n\t\t\t},\n\t\t\t//翻页回调\n\t\t\thandleCurrentChange(val){\n\t\t\t\tthis.$data.page = val;\n\t\t\t\tthis.getactivityList();\n\t\t\t},\n\t\t\t//点击查看\n\t\t\tcheckImage(index,list){\n\t\t\t\tthis.dialogImageVisible = true;\n\t\t\t\tthis.BigImageUrl = this.resourceDomain + list[index].brand_info_img;\n\t\t\t},\n\t\t\t//查看品牌介绍页面\n\t\t\tcheckBrandDetail(index,list){\n\t\t\t\tthis.ossurl = \"\";\n\t\t\t\tthis.ossurl = 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/' + list[index].oss_url;\n\t\t\t\tthis.dialogFormVisibleCheck = true;\n\t\t\t},\n\t\t\t//获取活动列表\n\t\t\tgetactivityList(){\n\t\t\t\tvar that = this;\n\t\t\t\tthat.loading = true;\n\t\t\t\tvar param = {\n\t\t\t\t\tpage : this.page,\n\t\t\t\t\tlimit : this.limit,\n\t\t\t\t\taccess_token : localStorage.access_token\n\t\t\t\t}\n\t\t\t\tthis.$http.post(this.activityListApi,param).then(response=>{\n\t\t\t\t\tvar jsonData = response.data;\n\t\t\t\t\tif(jsonData.error_no==0){\n\t\t\t\t\t\tif(jsonData.result_list){\n\t\t\t\t\t\t\tjsonData.result_list = jsonData.result_list.map(function(item){\n\t\t\t\t\t\t\t\titem.state = item.app_display==1;\n\t\t\t\t\t\t\t\titem.reinitTime = Common.formatDateConcat(item.init_date,item.init_time);\n\t\t\t\t\t\t\t\titem.reupdateTime = Common.formatDateConcat(item.update_date,item.update_time);\n\t\t\t\t\t\t\t\treturn item;\n\t\t\t\t\t\t\t});\n\t\t\t\t\t\t}\n\t\t\t\t\t\tthat.$data.activityList = jsonData || {};\n\t\t\t\t\t}else{\n\t\t\t\t\t\tthat.$message({\n\t\t\t\t\t\t\ttype: \"warning\",\n\t\t\t\t\t\t\tmessage: jsonData.error_info\n\t\t\t\t\t\t});\n\t\t\t\t\t}\n\t\t\t\t\tthat.$data.loading = false;\n\t\t\t\t});\n\t\t\t}\n\t\t}\n    }\n</script>\n"],"sourceRoot":"webpack://"}]);
	
	// exports


/***/ }),

/***/ 80:
/***/ (function(module, exports, __webpack_require__) {

	"use strict";
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	var _common = __webpack_require__(8);
	
	var _common2 = _interopRequireDefault(_common);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	exports.default = {
		data: function data() {
			return {
				activityList: {}, //活动列表数据缓存
				page: 1, //当前列表页码
				limit: 10, //列表－每页条数
				loading: false, //列表加载状态
				activityListApi: "/brandinfo/query", //品牌列表接口地址
				updateappdisplayApi: "/brandinfo/updateappdisplay", //上架和下架接口
				uploadImageApi: "/json/901210", //上传图片接口地址
				uploadHtmlApi: "/json/901211", //上传富文本接口地址
				addActivityApi: "/brandinfo/add", //添加活动接口地址
				updateActivityApi: "/brandinfo/update", //更新活动接口地址
				getTextDetailApi: "/json/901213", //获取富文本详情接口地址
				showtooltip: true, //是否当内容过长被隐藏时显示tooltip true显示 flase不显示
				resourceDomain: 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/',
				dialogImageVisible: false,
				dialogFormVisibleAdd: false,
				dialogFormVisibleUpdate: false,
				dialogFormVisibleCheck: false,
				BigImageUrl: "",
				formLabelWidth: "85px",
				ismodify: false,
				filesList: [],
				formScopeDialogAdd: this.getDefaultDialogForm(),
				formScopeDialogUpdate: {},
				brandlist: [],
				htmlTemplate: "",
				ossurl: ""
			};
		},
		created: function created() {
			var _this = this;
	
			var that = this;
			//获取品牌列表
			that.$http.post("./../goodsbrand/queryforweb", { limit: 10000, page: 1, status: 1 }).then(function (response) {
				var data = response.data;
				if (data.error_no == 0) {
					that.$data.brandlist = data.result_list;
				}
			});
			this.$http.get("/dist/data/template.txt").then(function (res) {
				_this.htmlTemplate = res.data;
			});
		},
		mounted: function mounted() {
			document.title = "后台管理系统-活动管理";
			this.getactivityList();
		},
	
		methods: {
			//获取弹窗表单默认数据
			getDefaultDialogForm: function getDefaultDialogForm() {
				return {
					brand_info_img: "",
					brand_info_content: "",
					sort: "",
					remark: "",
					brand_id: "",
					app_display: ""
				};
			},
	
			//点击新增
			addNew: function addNew() {
				var that = this;
				this.formScopeDialogAdd = this.getDefaultDialogForm();
				this.filesList = [];
				this.dialogFormVisibleAdd = true;
				this.ismodify = false;
				setTimeout(function () {
					that.editor = new Simditor({
						textarea: $('#dialogEditor'),
						upload: {}
					});
				}, 100);
			},
	
			//保存
			saveModel: function saveModel(refname) {
				var that = this;
				this.$refs[refname].validate(function (valid) {
					if (valid) {
						that.beforesaveActivity();
					}
				});
			},
	
			//提交前检查
			beforesaveActivity: function beforesaveActivity() {
				var text = this.editor.getValue(),
				    that = this;
				var formScopeDialog = that.ismodify ? this.formScopeDialogUpdate : this.formScopeDialogAdd;
				if (!text) {
					that.$message({
						type: "error",
						message: "请编写展示内容"
					});
					return;
				}
				if (formScopeDialog.remark && formScopeDialog.remark.length > 10) {
					that.$message({
						type: "error",
						message: "备注过长 请输入1 至 10个字符"
					});
					return;
				}
				that.replaceImage(text, function (newtext) {
					newtext = newtext.replace(/width=\"\d+\"/g, "").replace(/height=\"\d+\"/g, "");
					formScopeDialog.brand_info_content = that.htmlTemplate.replace("{{}}", newtext);
					if (that.ismodify) {
						that.updateActivity();
					} else {
						that.AddActivity();
					}
				});
			},
			replaceImage: function replaceImage(text, callback) {
				var reg = /data:\S+\"/g;
				var arr = text.match(reg);
				var count = 0,
				    that = this;
				if (!arr || !arr.length) {
					callback(text);
					return;
				}
				arr.map(function (item) {
					var src = item.substr(0, item.length - 1);
					var img = new Image();
					img.src = src;
					img.onload = function () {
						var result = _common2.default.compressImg(img, 0.8, "image/jpeg");
						that.uploadCompressImg(result, function (imgurl) {
							count++;
							text = text.replace(src, imgurl);
							if (count == arr.length) {
								callback(text);
							}
						});
					};
				});
			},
			uploadCompressImg: function uploadCompressImg(result, cb) {
				var that = this,
				    param = {
					access_token: localStorage.access_token,
					imgType: 19,
					base64Str: result
				};
				that.$http.post(that.uploadImageApi, param).then(function (response) {
					var jsondata = response.data;
					cb(that.resourceDomain + jsondata.img_uri);
				});
			},
	
			//新增品牌介绍
			AddActivity: function AddActivity() {
				var that = this;
				this.formScopeDialogAdd.access_token = localStorage.access_token;
				that.$http.post(that.addActivityApi, this.formScopeDialogAdd).then(function (response) {
					var jsondata = response.data;
					that.$message({
						type: jsondata.error_no == 0 ? "success" : "error",
						message: jsondata.error_no == 0 ? "活动添加成功" : jsondata.error_info
					});
					if (jsondata.error_no == 0) {
						that.dialogFormVisibleAdd = false;
						that.getactivityList();
						that.formScopeDialogAdd = that.getDefaultDialogForm();
						that.filesList = [];
					}
				});
			},
	
			//更新品牌介绍
			updateActivity: function updateActivity() {
				var _this2 = this;
	
				var that = this,
				    index = this.formScopeDialogUpdate.brand_info_img.indexOf("data:");
				console.log(index);
				if (index < 0) {
					this.cache_brand_info_img = this.formScopeDialogUpdate.brand_info_img;
					delete this.formScopeDialogUpdate.brand_info_img;
				}
				this.formScopeDialogUpdate.access_token = localStorage.access_token;
				this.formScopeDialogUpdate.status = this.formScopeDialogUpdate.app_display;
				that.$http.post(that.updateActivityApi, this.formScopeDialogUpdate).then(function (response) {
					var jsondata = response.data;
					that.$message({
						type: jsondata.error_no == 0 ? "success" : "error",
						message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
					});
					if (jsondata.error_no == 0) {
						that.dialogFormVisibleUpdate = false;
						that.getactivityList();
						that.formScopeDialogUpdate = that.getDefaultDialogForm();
						that.filesList = [];
					} else {
						if (_this2.cache_brand_info_img) {
							_this2.formScopeDialogUpdate.brand_info_img = _this2.cache_brand_info_img;
							delete _this2.cache_brand_info_img;
						}
					}
				});
			},
	
			//点击下架
			offTheShelf: function offTheShelf(index, list) {
				var that = this,
				    item = list[index];
				this.updateappdisplay(0, item);
			},
	
			//点击上架
			onTheShelves: function onTheShelves(index, list) {
				var that = this,
				    item = list[index];
				this.updateappdisplay(1, item);
			},
	
			//上架或下架
			updateappdisplay: function updateappdisplay(app_display, item) {
				var that = this;
				this.$confirm('确定要' + (app_display == 1 ? '上架' : '下架') + '该品牌介绍吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(function () {
					var param = {
						agent_id: item.brand_id,
						access_token: localStorage.access_token,
						app_display: app_display,
						update_user: item.update_user,
						brand_info_id: item.brand_info_id
					};
					that.$http.post(that.updateappdisplayApi, param).then(function (response) {
						var jsondata = response.data;
						that.$message({
							type: jsondata.error_no == 0 ? "success" : "error",
							message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
						});
						if (jsondata.error_no == 0) {
							that.getactivityList();
						}
					});
				}).catch(function () {});
			},
	
			//点击编辑
			modifyActivity: function modifyActivity(index, list) {
				var item = list[index];
				var that = this;
				this.formScopeDialogUpdate = {
					brand_id: item.brand_id,
					sort: item.sort,
					brand_info_img: item.brand_info_img,
					brand_info_content: item.brand_info_content,
					remark: item.remark,
					app_display: item.app_display,
					brand_info_id: item.brand_info_id
				};
				this.filesList = [{
					name: item.brand_info_img,
					url: 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/' + item.brand_info_img,
					status: 'finished'
				}];
				this.dialogFormVisibleUpdate = true;
				this.ismodify = true;
				setTimeout(function () {
					that.editor = new Simditor({
						textarea: $('#dialogEditor'),
						upload: {}
					});
					var content = that.formScopeDialogUpdate.brand_info_content,
					    bool = content.indexOf("<body>") > 0 && content.indexOf("</body>") > 0;
					var _content = !bool ? content : content.split("<body>")[1].split("</body>")[0];
					//console.log(_content);
					that.editor.setValue(_content);
				}, 200);
			},
	
			//选择文件时
			handleFileChange: function handleFileChange(_file, _filelist) {
				this.filesList = [_filelist[_filelist.length - 1]];
				var that = this;
				var reader = new FileReader();
				reader.readAsDataURL(_file.raw);
				reader.onload = function (e) {
					var img = new Image();
					img.src = this.result;
					img.onload = function () {
						if (that.ismodify) {
							that.formScopeDialogUpdate.brand_info_img = _common2.default.compressImg(img, 0.8, "image/jpeg");
						} else {
							that.formScopeDialogAdd.brand_info_img = _common2.default.compressImg(img, 0.8, "image/jpeg");
						}
	
						$(".el-upload-list > li").last().prev().remove();
					};
				};
			},
	
			//图片压缩
			compressImg: function compressImg(img, rate, fileType) {
				var cvs = document.createElement('canvas');
				var width = img.width;
				var height = img.height;
				var dic = height / width;
				var width_cvs = width > 600 ? 600 : width;
				cvs.width = width_cvs; //图片压缩的标准，这里是按照定款600px计算
				cvs.height = width_cvs * dic;
				var cxt = cvs.getContext("2d");
				cxt.fillStyle = "#FFF";
				cxt.fillRect(0, 0, width_cvs, width_cvs * dic);
				cxt.drawImage(img, 0, 0, width_cvs, width_cvs * dic);
				return cvs.toDataURL(fileType, rate);
			},
	
			//翻页回调
			handleCurrentChange: function handleCurrentChange(val) {
				this.$data.page = val;
				this.getactivityList();
			},
	
			//点击查看
			checkImage: function checkImage(index, list) {
				this.dialogImageVisible = true;
				this.BigImageUrl = this.resourceDomain + list[index].brand_info_img;
			},
	
			//查看品牌介绍页面
			checkBrandDetail: function checkBrandDetail(index, list) {
				this.ossurl = "";
				this.ossurl = 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/' + list[index].oss_url;
				this.dialogFormVisibleCheck = true;
			},
	
			//获取活动列表
			getactivityList: function getactivityList() {
				var that = this;
				that.loading = true;
				var param = {
					page: this.page,
					limit: this.limit,
					access_token: localStorage.access_token
				};
				this.$http.post(this.activityListApi, param).then(function (response) {
					var jsonData = response.data;
					if (jsonData.error_no == 0) {
						if (jsonData.result_list) {
							jsonData.result_list = jsonData.result_list.map(function (item) {
								item.state = item.app_display == 1;
								item.reinitTime = _common2.default.formatDateConcat(item.init_date, item.init_time);
								item.reupdateTime = _common2.default.formatDateConcat(item.update_date, item.update_time);
								return item;
							});
						}
						that.$data.activityList = jsonData || {};
					} else {
						that.$message({
							type: "warning",
							message: jsonData.error_info
						});
					}
					that.$data.loading = false;
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
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
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

/***/ 81:
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
	  }, [_vm._v("用户中心")]), _vm._v(" "), _c('el-breadcrumb-item', [_vm._v("内容管理")]), _vm._v(" "), _c('el-breadcrumb-item', [_vm._v("品牌介绍")])], 1)], 1), _vm._v(" "), _c('div', {
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
	      "data": _vm.activityList.result_list,
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
	      "prop": "brand_name",
	      "label": "品牌",
	      "width": "180"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_c('a', {
	          staticStyle: {
	            "cursor": "pointer"
	          },
	          on: {
	            "click": function($event) {
	              _vm.checkBrandDetail(scope.$index, _vm.activityList.result_list)
	            }
	          }
	        }, [_vm._v(_vm._s(scope.row.brand_name))])]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "brand_info_img",
	      "label": "图片",
	      "width": "120"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_c('el-button', {
	          attrs: {
	            "type": "text"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.checkImage(scope.$index, _vm.activityList.result_list)
	            }
	          }
	        }, [_vm._v("点击查看")])]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "sort",
	      "label": "排序",
	      "width": "80"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "app_display",
	      "label": "状态",
	      "width": "100"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_vm._v("\n\t\t\t\t\t\t" + _vm._s(scope.row.app_display == 1 ? "已上架" : "已下架") + "\n\t\t\t\t\t")]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "remark",
	      "label": "备注",
	      "width": "300",
	      "show-overflow-tooltip": _vm.showtooltip
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "reinitTime",
	      "label": "添加时间",
	      "width": "180",
	      "align": "center"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "create_user",
	      "label": "添加人",
	      "width": "180",
	      "align": "center"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "reupdateTime",
	      "label": "最后修改时间",
	      "width": "180",
	      "align": "center"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "update_user",
	      "label": "最后修改人",
	      "width": "180",
	      "align": "center"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "fixed": "right",
	      "label": "操作",
	      "width": "140",
	      "align": "center"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [(scope.row.state) ? _c('el-button', {
	          attrs: {
	            "type": "text"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.offTheShelf(scope.$index, _vm.activityList.result_list)
	            }
	          }
	        }, [_vm._v("下架")]) : _vm._e(), _vm._v(" "), (!scope.row.state) ? _c('el-button', {
	          attrs: {
	            "type": "text"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.modifyActivity(scope.$index, _vm.activityList.result_list)
	            }
	          }
	        }, [_vm._v("编辑")]) : _vm._e(), _vm._v(" "), (!scope.row.state) ? _c('el-button', {
	          attrs: {
	            "type": "text"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.onTheShelves(scope.$index, _vm.activityList.result_list)
	            }
	          }
	        }, [_vm._v("上架")]) : _vm._e()]
	      }
	    }])
	  })], 1)], 1), _vm._v(" "), _c('div', {
	    staticClass: "block-white"
	  }, [_c('el-pagination', {
	    attrs: {
	      "current-page": _vm.page,
	      "page-size": _vm.limit,
	      "layout": "total, prev, pager, next, jumper",
	      "total": _vm.activityList.total_num
	    },
	    on: {
	      "current-change": _vm.handleCurrentChange
	    }
	  })], 1), _vm._v(" "), _c('el-dialog', {
	    attrs: {
	      "title": "图片展示"
	    },
	    model: {
	      value: (_vm.dialogImageVisible),
	      callback: function($$v) {
	        _vm.dialogImageVisible = $$v
	      },
	      expression: "dialogImageVisible"
	    }
	  }, [_c('div', {
	    staticStyle: {
	      "text-align": "center"
	    }
	  }, [_c('img', {
	    staticStyle: {
	      "display": "inline-block",
	      "margin": "0 auto",
	      "max-width": "100%"
	    },
	    attrs: {
	      "src": _vm.BigImageUrl
	    }
	  })]), _vm._v(" "), _c('div', {
	    staticClass: "dialog-footer",
	    staticStyle: {
	      "text-align": "center"
	    },
	    slot: "footer"
	  }, [_c('el-button', {
	    on: {
	      "click": function($event) {
	        _vm.dialogImageVisible = false
	      }
	    }
	  }, [_vm._v("关 闭")])], 1)]), _vm._v(" "), _c('el-dialog', {
	    attrs: {
	      "title": "新增品牌介绍"
	    },
	    model: {
	      value: (_vm.dialogFormVisibleAdd),
	      callback: function($$v) {
	        _vm.dialogFormVisibleAdd = $$v
	      },
	      expression: "dialogFormVisibleAdd"
	    }
	  }, [_c('div', {
	    staticStyle: {
	      "height": "570px",
	      "overflow": "hidden",
	      "overflow-y": "auto",
	      "padding-right": "20px"
	    }
	  }, [_c('el-form', {
	    ref: _vm.formScopeDialogAdd,
	    attrs: {
	      "label-position": "right",
	      "label-width": _vm.formLabelWidth,
	      "model": _vm.formScopeDialogAdd
	    }
	  }, [_c('el-form-item', {
	    attrs: {
	      "label": "图片上传",
	      "prop": "brand_info_img",
	      "rules": {
	        required: true,
	        message: '请上传图片'
	      }
	    }
	  }, [_c('el-input', {
	    attrs: {
	      "type": "hidden"
	    },
	    model: {
	      value: (_vm.formScopeDialogAdd.brand_info_img),
	      callback: function($$v) {
	        _vm.formScopeDialogAdd.brand_info_img = $$v
	      },
	      expression: "formScopeDialogAdd.brand_info_img"
	    }
	  }), _vm._v(" "), _c('el-upload', {
	    staticClass: "upload-demo",
	    attrs: {
	      "action": "https://jsonplaceholder.typicode.com/posts/",
	      "auto-upload": false,
	      "on-change": _vm.handleFileChange,
	      "file-list": _vm.filesList
	    }
	  }, [_c('el-button', {
	    attrs: {
	      "size": "small",
	      "type": "primary"
	    }
	  }, [_vm._v("点击上传")]), _vm._v(" "), _c('div', {
	    staticClass: "el-upload__tip",
	    slot: "tip"
	  }, [_vm._v("建议尺寸：750*300px 且不超过500kb")])], 1)], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "排序",
	      "prop": "sort",
	      "rules": {
	        required: true,
	        message: '请设置排序值'
	      }
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "type": "number",
	      "minlength": 1,
	      "maxlength": 3,
	      "placeholder": "排序"
	    },
	    model: {
	      value: (_vm.formScopeDialogAdd.sort),
	      callback: function($$v) {
	        _vm.formScopeDialogAdd.sort = $$v
	      },
	      expression: "formScopeDialogAdd.sort"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "品牌",
	      "prop": "brand_id",
	      "rules": {
	        required: true,
	        message: '请输入品牌'
	      }
	    }
	  }, [_c('el-select', {
	    staticStyle: {
	      "width": "200px"
	    },
	    attrs: {
	      "filterable": "",
	      "placeholder": "搜索品牌"
	    },
	    model: {
	      value: (_vm.formScopeDialogAdd.brand_id),
	      callback: function($$v) {
	        _vm.formScopeDialogAdd.brand_id = $$v
	      },
	      expression: "formScopeDialogAdd.brand_id"
	    }
	  }, _vm._l((_vm.brandlist), function(item) {
	    return _c('el-option', {
	      key: item.brand_id,
	      attrs: {
	        "label": item.brand_name,
	        "value": item.brand_id
	      }
	    })
	  }))], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "",
	      "prop": "app_display",
	      "rules": {
	        required: true,
	        message: '请设置品牌介绍的状态'
	      }
	    }
	  }, [_c('el-radio', {
	    staticClass: "radio",
	    attrs: {
	      "label": "1"
	    },
	    model: {
	      value: (_vm.formScopeDialogAdd.app_display),
	      callback: function($$v) {
	        _vm.formScopeDialogAdd.app_display = $$v
	      },
	      expression: "formScopeDialogAdd.app_display"
	    }
	  }, [_vm._v("上架")]), _vm._v(" "), _c('el-radio', {
	    staticClass: "radio",
	    attrs: {
	      "label": "0"
	    },
	    model: {
	      value: (_vm.formScopeDialogAdd.app_display),
	      callback: function($$v) {
	        _vm.formScopeDialogAdd.app_display = $$v
	      },
	      expression: "formScopeDialogAdd.app_display"
	    }
	  }, [_vm._v("下架")])], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "展示内容"
	    }
	  }, [_c('textarea', {
	    attrs: {
	      "id": "dialogEditor"
	    }
	  })]), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "备注",
	      "prop": "remark"
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "300px"
	    },
	    attrs: {
	      "minlength": 1,
	      "maxlength": 10,
	      "placeholder": "备注"
	    },
	    model: {
	      value: (_vm.formScopeDialogAdd.remark),
	      callback: function($$v) {
	        _vm.formScopeDialogAdd.remark = $$v
	      },
	      expression: "formScopeDialogAdd.remark"
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
	        _vm.saveModel(_vm.formScopeDialogAdd)
	      }
	    }
	  }, [_vm._v("保存")]), _vm._v(" "), _c('el-button', {
	    on: {
	      "click": function($event) {
	        _vm.dialogFormVisibleAdd = false
	      }
	    }
	  }, [_vm._v("关 闭")])], 1)]), _vm._v(" "), _c('el-dialog', {
	    attrs: {
	      "title": "编辑品牌介绍"
	    },
	    model: {
	      value: (_vm.dialogFormVisibleUpdate),
	      callback: function($$v) {
	        _vm.dialogFormVisibleUpdate = $$v
	      },
	      expression: "dialogFormVisibleUpdate"
	    }
	  }, [_c('div', {
	    staticStyle: {
	      "height": "570px",
	      "overflow": "hidden",
	      "overflow-y": "auto",
	      "padding-right": "20px"
	    }
	  }, [_c('el-form', {
	    ref: _vm.formScopeDialogUpdate,
	    attrs: {
	      "label-position": "right",
	      "label-width": _vm.formLabelWidth,
	      "model": _vm.formScopeDialogUpdate
	    }
	  }, [_c('el-form-item', {
	    attrs: {
	      "label": "图片上传",
	      "prop": "brand_info_img",
	      "rules": {
	        required: true,
	        message: '请上传图片'
	      }
	    }
	  }, [_c('el-input', {
	    attrs: {
	      "type": "hidden"
	    },
	    model: {
	      value: (_vm.formScopeDialogUpdate.brand_info_img),
	      callback: function($$v) {
	        _vm.formScopeDialogUpdate.brand_info_img = $$v
	      },
	      expression: "formScopeDialogUpdate.brand_info_img"
	    }
	  }), _vm._v(" "), _c('el-upload', {
	    staticClass: "upload-demo",
	    attrs: {
	      "action": "https://jsonplaceholder.typicode.com/posts/",
	      "auto-upload": false,
	      "on-change": _vm.handleFileChange,
	      "file-list": _vm.filesList
	    }
	  }, [_c('el-button', {
	    attrs: {
	      "size": "small",
	      "type": "primary"
	    }
	  }, [_vm._v("点击上传")]), _vm._v(" "), _c('div', {
	    staticClass: "el-upload__tip",
	    slot: "tip"
	  }, [_vm._v("建议尺寸：750*300px 且不超过500kb")])], 1)], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "排序",
	      "prop": "sort",
	      "rules": {
	        required: true,
	        message: '请设置排序值'
	      }
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "type": "number",
	      "minlength": 1,
	      "maxlength": 3,
	      "placeholder": "排序"
	    },
	    model: {
	      value: (_vm.formScopeDialogUpdate.sort),
	      callback: function($$v) {
	        _vm.formScopeDialogUpdate.sort = $$v
	      },
	      expression: "formScopeDialogUpdate.sort"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "品牌",
	      "prop": "brand_id",
	      "rules": {
	        required: true,
	        message: '请输入品牌'
	      }
	    }
	  }, [_c('el-select', {
	    staticStyle: {
	      "width": "200px"
	    },
	    attrs: {
	      "filterable": "",
	      "placeholder": "搜索品牌"
	    },
	    model: {
	      value: (_vm.formScopeDialogUpdate.brand_id),
	      callback: function($$v) {
	        _vm.formScopeDialogUpdate.brand_id = $$v
	      },
	      expression: "formScopeDialogUpdate.brand_id"
	    }
	  }, _vm._l((_vm.brandlist), function(item) {
	    return _c('el-option', {
	      key: item.brand_id,
	      attrs: {
	        "label": item.brand_name,
	        "value": item.brand_id
	      }
	    })
	  }))], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "",
	      "prop": "app_display",
	      "rules": {
	        required: true,
	        message: '请设置品牌介绍的状态'
	      }
	    }
	  }, [_c('el-radio', {
	    staticClass: "radio",
	    attrs: {
	      "label": "1"
	    },
	    model: {
	      value: (_vm.formScopeDialogUpdate.app_display),
	      callback: function($$v) {
	        _vm.formScopeDialogUpdate.app_display = $$v
	      },
	      expression: "formScopeDialogUpdate.app_display"
	    }
	  }, [_vm._v("上架")]), _vm._v(" "), _c('el-radio', {
	    staticClass: "radio",
	    attrs: {
	      "label": "0"
	    },
	    model: {
	      value: (_vm.formScopeDialogUpdate.app_display),
	      callback: function($$v) {
	        _vm.formScopeDialogUpdate.app_display = $$v
	      },
	      expression: "formScopeDialogUpdate.app_display"
	    }
	  }, [_vm._v("下架")])], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "展示内容"
	    }
	  }, [_c('textarea', {
	    attrs: {
	      "id": "dialogEditor"
	    }
	  })]), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "备注",
	      "prop": "remark"
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "300px"
	    },
	    attrs: {
	      "minlength": 1,
	      "maxlength": 10,
	      "placeholder": "备注"
	    },
	    model: {
	      value: (_vm.formScopeDialogUpdate.remark),
	      callback: function($$v) {
	        _vm.formScopeDialogUpdate.remark = $$v
	      },
	      expression: "formScopeDialogUpdate.remark"
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
	        _vm.saveModel(_vm.formScopeDialogUpdate)
	      }
	    }
	  }, [_vm._v("保存")]), _vm._v(" "), _c('el-button', {
	    on: {
	      "click": function($event) {
	        _vm.dialogFormVisibleUpdate = false
	      }
	    }
	  }, [_vm._v("关 闭")])], 1)]), _vm._v(" "), _c('el-dialog', {
	    attrs: {
	      "title": "查看品牌介绍"
	    },
	    model: {
	      value: (_vm.dialogFormVisibleCheck),
	      callback: function($$v) {
	        _vm.dialogFormVisibleCheck = $$v
	      },
	      expression: "dialogFormVisibleCheck"
	    }
	  }, [_c('iframe', {
	    staticStyle: {
	      "display": "block",
	      "border": "2px solid #d1d1d1",
	      "width": "375px",
	      "height": "667px",
	      "overflow": "hidden",
	      "overflow-y": "auto",
	      "margin": "0 auto"
	    },
	    attrs: {
	      "src": _vm.ossurl
	    }
	  })], 1)], 1)
	},staticRenderFns: []}
	module.exports.render._withStripped = true
	if (false) {
	  module.hot.accept()
	  if (module.hot.data) {
	     require("vue-hot-reload-api").rerender("data-v-00d847cc", module.exports)
	  }
	}

/***/ })

});
//# sourceMappingURL=15.js.map