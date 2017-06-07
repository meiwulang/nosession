webpackJsonp([36],{

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

/***/ 72:
/***/ (function(module, exports, __webpack_require__) {

	
	/* styles */
	__webpack_require__(73)
	
	var Component = __webpack_require__(13)(
	  /* script */
	  __webpack_require__(75),
	  /* template */
	  __webpack_require__(76),
	  /* scopeId */
	  null,
	  /* cssModules */
	  null
	)
	Component.options.__file = "/Users/xiangyangli/projects/hjh/jhd/mall-uag/src/main/webapp/app/components/categoryThird.vue"
	if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key !== "__esModule"})) {console.error("named exports are not supported in *.vue files.")}
	if (Component.options.functional) {console.error("[vue-loader] categoryThird.vue: functional components are not supported with templates, they should use render functions.")}
	
	/* hot reload */
	if (false) {(function () {
	  var hotAPI = require("vue-hot-reload-api")
	  hotAPI.install(require("vue"), false)
	  if (!hotAPI.compatible) return
	  module.hot.accept()
	  if (!module.hot.data) {
	    hotAPI.createRecord("data-v-7d84a262", Component.options)
	  } else {
	    hotAPI.reload("data-v-7d84a262", Component.options)
	  }
	})()}
	
	module.exports = Component.exports


/***/ }),

/***/ 73:
/***/ (function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(74);
	if(typeof content === 'string') content = [[module.id, content, '']];
	if(content.locals) module.exports = content.locals;
	// add the styles to the DOM
	var update = __webpack_require__(29)("1240954e", content, false);
	// Hot Module Replacement
	if(false) {
	 // When the styles change, update the <style> tags
	 if(!content.locals) {
	   module.hot.accept("!!../../node_modules/css-loader/index.js?sourceMap!../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-7d84a262!../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./categoryThird.vue", function() {
	     var newContent = require("!!../../node_modules/css-loader/index.js?sourceMap!../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-7d84a262!../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./categoryThird.vue");
	     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
	     update(newContent);
	   });
	 }
	 // When the module is disposed, remove the <style> tags
	 module.hot.dispose(function() { update(); });
	}

/***/ }),

/***/ 74:
/***/ (function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(28)();
	// imports
	
	
	// module
	exports.push([module.id, "\n.el-dialog__body .el-form-item__content{\n\tline-height: 0;\n}\n.el-form-item__content{\n\t/*position: static;*/\n}\n", "", {"version":3,"sources":["/./app/components/categoryThird.vue?03f27cf6"],"names":[],"mappings":";AA2NA;CACA,eAAA;CACA;AACA;CACA,qBAAA;CACA","file":"categoryThird.vue","sourcesContent":["<template>\n\t<div>\n\t\t<!-- 面包屑 -->\n\t\t<div class=\"hjh-breadcrumb\">\n\t\t\t<el-breadcrumb separator=\"/\">\n\t\t\t\t<el-breadcrumb-item :to=\"{ path: '/' }\">用户中心</el-breadcrumb-item>\n\t\t\t\t<el-breadcrumb-item>类目管理</el-breadcrumb-item>\n\t\t\t\t<el-breadcrumb-item>三级类目管理</el-breadcrumb-item>\n\t\t\t</el-breadcrumb>\n\t\t</div>\n\t\t<!-- 面包屑 end -->\n\t\t<!-- 列表查询表单 -->\n\t\t<div class=\"block-white\">\n\t\t\t<el-form :inline=\"true\" :model=\"searchForm\" :ref=\"searchForm\" class=\"demo-form-inline searchForm\">\n\t\t\t\t<el-form-item label=\"一级\">\n\t\t\t\t\t<el-select v-model=\"searchForm.grandName\" filterable placeholder=\"输入匹配\" style=\"width: 150px;\" @change=\"grandChange\">\n\t\t\t\t\t\t<el-option v-for=\"item in grandList\" :key=\"item.category_id\" :label=\"item.category_name\" :value=\"item.category_name\">\n\t\t\t\t\t\t</el-option>\n\t\t\t\t\t</el-select>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"二级\">\n\t\t\t\t\t<el-select v-model=\"searchForm.fatherName\" filterable placeholder=\"输入匹配\" style=\"width: 150px;\">\n\t\t\t\t\t\t<el-option v-for=\"item in fatherList\" :key=\"item.bid\" :label=\"item.bname\" :value=\"item.bname\">\n\t\t\t\t\t\t</el-option>\n\t\t\t\t\t</el-select>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"三级\">\n\t\t\t\t\t<el-input v-model=\"searchForm.categoryName\" placeholder=\"模糊查询\" style=\"width:150px;\"></el-input>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"创建者\">\n\t\t\t\t\t<el-select v-model=\"searchForm.creater\" filterable placeholder=\"输入匹配\" style=\"width: 150px;\">\n\t\t\t\t\t\t<el-option v-for=\"item in operatorList\" :key=\"item.operator_name\" :label=\"item.operator_name\" :value=\"item.operator_name\">\n\t\t\t\t\t\t</el-option>\n\t\t\t\t\t</el-select>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item>\n\t\t\t\t\t<el-select v-model=\"searchForm.status\" placeholder=\"请选择状态\" style=\"width:150px;\">\n\t\t\t\t\t\t<el-option label=\"启用\" value=\"1\"></el-option>\n\t\t\t\t\t\t<el-option label=\"禁用\" value=\"0\"></el-option>\n\t\t\t\t\t</el-select>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"APP显示\">\n\t\t\t\t\t<el-select v-model=\"searchForm.appDisplay\" placeholder=\"请选择APP状态\" style=\"width:150px;\">\n\t\t\t\t\t\t<el-option label=\"显示\" value=\"true\"></el-option>\n\t\t\t\t\t\t<el-option label=\"不显示\" value=\"false\"></el-option>\n\t\t\t\t\t</el-select>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"创建日期\">\n\t\t\t\t\t<el-date-picker v-model=\"datepickerValue\" type=\"datetimerange\" :picker-options=\"pickerOptions\" @change=\"datepickerChange\" placeholder=\"选择时间范围\" align=\"right\">\n\t\t\t\t\t</el-date-picker>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item>\n\t\t\t\t\t<el-button class=\"searchBtn\" type=\"primary\" @click=\"submitForm(searchForm)\">搜索</el-button>\n\t\t\t\t\t<el-button @click=\"resetForm(searchForm)\">清除</el-button>\n\t\t\t\t</el-form-item>\n\t\t\t</el-form>\n\t\t</div>\n\t\t<!-- 列表查询表单 end -->\n\t\t<div class=\"block-white\">\n\t\t\t<el-button @click=\"addNewCategory\" type=\"info\">新增</el-button>\n\t\t\t<el-button @click=\"setStatusBySelection(1)\" :disabled=\"!multipleSelection.length\" type=\"info\">启用</el-button>\n\t\t\t<el-button @click=\"setStatusBySelection(0)\" :disabled=\"!multipleSelection.length\" type=\"danger\">禁用</el-button>\n\t\t</div>\n\t\t<div class=\"block-white\">\n\t\t\t<div class=\"selection-tip\">\n\t\t\t\t<i class=\"el-icon-warning\"></i><span>已选择 {{multipleSelection.length}} 项数据。</span>\n\t\t\t</div>\n\t\t</div>\n\t\t<!-- 二级类目管理列表 -->\n\t\t<div class=\"block-white\">\n\t\t\t<el-table v-loading.body=\"loading\" \n\t\t\t\t:data=\"categoryList.result_list\" \n\t\t\t\t@selection-change=\"handleSelectionChange\" \n\t\t\t\tref=\"multipleTable\"\n\t\t\t\tborder stripe style=\"width: 100%\" max-height=\"460\">\n\t\t\t\t<el-table-column fixed type=\"selection\" width=\"55\" class=\"hjhcheckbox\">\n\t\t\t\t\t\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column label=\"序号\" width=\"70\" align=\"center\"><template scope=\"scope\">{{scope.$index+1}}</template></el-table-column>\n\t\t\t\t<el-table-column prop=\"cid\" label=\"代码\" width=\"200\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"aname\" label=\"一级\" align=\"center\" width=\"130\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"bname\" label=\"二级\" align=\"center\" width=\"130\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"cname\" label=\"三级\" align=\"center\" width=\"130\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"nick_name\" label=\"三级别名\" align=\"center\" width=\"130\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"icon\" label=\"图片\" width=\"110\" align=\"center\">\n\t\t\t\t\t<template scope=\"scope\"><img :src=\"'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+scope.row.icon\"></template>\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column prop=\"sort\" label=\"APP排序\" align=\"center\" width=\"110\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"hot_sort\" label=\"热门排序\" width=\"110\" align=\"center\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"createTime\" label=\"创建日期\" align=\"center\"  width=\"180\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"creater\" label=\"创建者\" align=\"center\"  width=\"100\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"updateTime\" label=\"最后操作日期\" align=\"center\" width=\"180\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"updater\" label=\"最后操作者\" align=\"center\" width=\"120\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"status\" label=\"状态\" align=\"center\" width=\"80\">\n\t\t\t\t\t<template scope=\"scope\">{{scope.row.status==1?\"启用\":\"禁用\"}}</template>\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column fixed=\"right\"  label=\"操作\" width=\"160\">\n\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t<el-button @click.native.prevent=\"modify(scope.$index, categoryList.result_list)\" type=\"info\">编辑</el-button>\n\t\t\t\t\t\t<el-button @click.native.prevent=\"setStatus(scope.$index, categoryList.result_list)\" type=\"danger\" >{{scope.row.status==\"1\"?\"禁用\":\"启用\"}}</el-button>\n\t\t\t\t\t</template>\n\t\t\t\t</el-table-column>\n\t\t\t</el-table>\n\t\t</div>\n\t\t<!-- 二级类目管理列表 end -->\n\t\t<!-- 翻页组件 -->\n\t\t<div class=\"block-white\">\n\t\t\t<el-pagination\n\t\t      @current-change=\"handleCurrentChange\"\n\t\t      :current-page=\"page\"\n\t\t      :page-size=\"limit\"\n\t\t      layout=\"total, prev, pager, next, jumper\"\n\t\t      :total=\"categoryList.total_num\">\n\t\t    </el-pagination>\n\t\t</div>\n\t\t<!-- 翻页组件 end -->\n\t\t<!-- 查看与编辑弹窗 -->\n\t\t<el-dialog :title=\"ismodify?'编辑三级类目':'新增三级类目'\" v-model=\"dialogFormVisible\">\n\t\t\t<div style=\"height: 320px; overflow: hidden; overflow-y: auto;\">\n\t\t\t\t<el-form label-position=\"right\" :label-width=\"formLabelWidth\" :ref=\"formScopeDialog\" :model=\"formScopeDialog\">\n\t\t\t\t\t<el-row>\n\t\t\t\t\t\t<el-col :span=\"12\">\n\t\t\t\t\t\t\t<el-form-item label=\"一级类目\" prop=\"dialogGrandId\" :rules=\"[{ required: true, message: '请选择一级类目', trigger: 'blur' }]\">\n\t\t\t\t\t\t\t\t<el-select v-model=\"formScopeDialog.dialogGrandId\" @change=\"grandIdChange\" filterable placeholder=\"输入匹配\" style=\"width: 200px;\">\n\t\t\t\t\t\t\t\t\t<el-option v-for=\"item in grandList\" :key=\"item.category_id\" :label=\"item.category_name\" :value=\"item.category_id\">\n\t\t\t\t\t\t\t\t\t</el-option>\n\t\t\t\t\t\t\t\t</el-select>\n\t\t\t\t\t\t\t</el-form-item>\n\t\t\t\t\t\t</el-col>\n\t\t\t\t\t\t<el-col class=\"line\" :span=\"2\" style=\"text-align: right; line-height: 36px; padding-right: 10px;\">别名 </el-col>\n\t\t\t\t\t\t<el-col :span=\"8\">\n\t\t\t\t\t\t\t<el-input v-model=\"grandNickName\" :readonly=\"true\" :minlength=\"2\" :maxlength=\"7\" placeholder=\"输入别名\"></el-input>\n\t\t\t\t\t\t</el-col>\n\t\t\t\t\t</el-row>\n\t\t\t\t\t<el-row>\n\t\t\t\t\t\t<el-col :span=\"12\">\n\t\t\t\t\t\t\t<el-form-item label=\"二级类目\" prop=\"fatherId\" :rules=\"[{ required: true, message: '请选择二级类目', trigger: 'blur' }]\">\n\t\t\t\t\t\t\t\t<el-select v-model=\"formScopeDialog.fatherId\" @change=\"fatherIdChange\" filterable placeholder=\"输入匹配\" style=\"width: 200px;\">\n\t\t\t\t\t\t\t\t\t<el-option v-for=\"item in dialogFatherList\" :key=\"item.category_id\" :label=\"item.category_name\" :value=\"item.category_id\">\n\t\t\t\t\t\t\t\t\t</el-option>\n\t\t\t\t\t\t\t\t</el-select>\n\t\t\t\t\t\t\t</el-form-item>\n\t\t\t\t\t\t</el-col>\n\t\t\t\t\t\t<el-col class=\"line\" :span=\"2\" style=\"text-align: right; line-height: 36px; padding-right: 10px;\">别名 </el-col>\n\t\t\t\t\t\t<el-col :span=\"8\">\n\t\t\t\t\t\t\t<el-input v-model=\"fatherNickName\" :readonly=\"true\" :minlength=\"2\" :maxlength=\"7\" placeholder=\"输入别名\"></el-input>\n\t\t\t\t\t\t</el-col>\n\t\t\t\t\t</el-row>\n\t\t\t\t\t<el-row>\n\t\t\t\t\t\t<el-col :span=\"12\">\n\t\t\t\t\t\t\t<el-form-item label=\"三级类目\" :label-width=\"formLabelWidth\" prop=\"categoryName\" :rules=\"[{ required: true, message: '请输入三级类目', trigger: 'blur' },{ min:2,max:7, message: '二级类目长度为2 至 7个字符', trigger: 'blur' }]\">\n\t\t\t\t\t\t\t\t<el-input v-model=\"formScopeDialog.categoryName\" :minlength=\"2\" :maxlength=\"7\" placeholder=\"输入三级类目\" style=\"width: 200px;\"></el-input>\n\t\t\t\t\t\t\t</el-form-item>\n\t\t\t\t\t\t</el-col>\n\t\t\t\t\t\t<el-col class=\"line\" :span=\"2\" style=\"text-align: right; line-height: 36px; padding-right: 10px;\">别名</el-col>\n\t\t\t\t\t\t<el-col :span=\"8\">\n\t\t\t\t\t\t\t<el-input v-model=\"formScopeDialog.nickName\" :minlength=\"2\" :maxlength=\"7\" placeholder=\"输入别名\"></el-input>\n\t\t\t\t\t\t</el-col>\n\t\t\t\t\t</el-row>\n\t\t\t\t\t\n\t\t\t\t\t<el-form-item :label-width=\"formLabelWidth\" label=\"图标\" prop=\"icon\" :rules=\"{ required: true, message: '请设置图标', trigger: 'blur' }\">\n\t\t\t\t\t\t\t<el-input v-model=\"formScopeDialog.icon\" type=\"hidden\"></el-input>\n\t\t\t\t\t\t\t<el-upload\n\t\t\t\t\t\t\t  class=\"upload-demo\"\n\t\t\t\t\t\t\t  action=\"https://jsonplaceholder.typicode.com/posts/\"\n\t\t\t\t\t\t\t  :auto-upload=\"false\"\n\t\t\t\t\t\t\t  :on-change=\"handleFileChange\"\n\t\t\t\t\t\t\t  :on-remove=\"handleRemoveFile\"\n\t\t\t\t\t\t\t  :file-list=\"filesList\">\n\t\t\t\t\t\t\t  <el-button size=\"small\" type=\"primary\">点击上传</el-button>\n\t\t\t\t\t\t\t  <div slot=\"tip\" class=\"el-upload__tip\">建议尺寸：750*270px</div>\n\t\t\t\t\t\t\t</el-upload>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item :label-width=\"formLabelWidth\">\n\t\t\t\t\t\t<el-col :span=\"4\">\n\t\t\t\t\t\t\t<el-checkbox v-model=\"checked\">APP显示</el-checkbox>\n\t\t\t\t\t\t</el-col>\n\t\t\t\t\t\t<el-col class=\"line\" v-if=\"checked\" :span=\"4\" style=\"text-align: right; line-height: 20px; padding-right: 10px;\">排序 </el-col>\n\t\t\t\t\t\t<el-col :span=\"8\" v-if=\"checked\" style=\"margin-top: -10px;\">\n\t\t\t\t\t\t\t<el-input type=\"number\" v-model=\"formScopeDialog.sort\" placeholder=\"排序\" style=\"width:150px;\"></el-input>\n\t\t\t\t\t\t</el-col>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t\n\t\t\t\t\t<el-form-item v-if=\"checked\" :label-width=\"formLabelWidth\">\n\t\t\t\t\t\t<el-col :span=\"4\">\n\t\t\t\t\t\t\t<el-checkbox v-model=\"formScopeDialog.hotable\">热门排序</el-checkbox>\n\t\t\t\t\t\t</el-col>\n\t\t\t\t\t\t<el-col class=\"line\" v-if=\"formScopeDialog.hotable\" :span=\"4\" style=\"text-align: right; line-height: 20px; padding-right: 10px;\">热门排序 </el-col>\n\t\t\t\t\t\t<el-col :span=\"8\" v-if=\"formScopeDialog.hotable\" style=\"margin-top: -10px;\">\n\t\t\t\t\t\t\t<el-input type=\"number\" v-model=\"formScopeDialog.hotSort\" placeholder=\"排序\" style=\"width:150px;\"></el-input>\n\t\t\t\t\t\t</el-col>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t\n\t\t\t\t\t<el-form-item :label-width=\"formLabelWidth\" label=\"热门图标\" prop=\"hotIcon\" :rules=\"{ required: true, message: '请设置图标', trigger: 'blur' }\" v-if=\"checked && formScopeDialog.hotable\">\n\t\t\t\t\t\t\t<el-input v-model=\"formScopeDialog.hotIcon\" type=\"hidden\"></el-input>\n\t\t\t\t\t\t\t<el-upload\n\t\t\t\t\t\t\t  class=\"upload-demo\"\n\t\t\t\t\t\t\t  action=\"https://jsonplaceholder.typicode.com/posts/\"\n\t\t\t\t\t\t\t  :auto-upload=\"false\"\n\t\t\t\t\t\t\t  :on-change=\"handleFileChangehot\"\n\t\t\t\t\t\t\t  :on-remove=\"handleRemoveFileHot\"\n\t\t\t\t\t\t\t  :file-list=\"filesListhot\">\n\t\t\t\t\t\t\t  <el-button size=\"small\" type=\"primary\">点击上传</el-button>\n\t\t\t\t\t\t\t  <div slot=\"tip\" class=\"el-upload__tip\">建议尺寸：750*270px</div>\n\t\t\t\t\t\t\t</el-upload>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t\n\t\t\t\t</el-form>\n\t\t\t</div>\n\t\t\t<div slot=\"footer\" class=\"dialog-footer\" style=\"text-align: center;\">\n\t\t\t\t<el-button type=\"primary\" v-if=\"!ismodify\" @click=\"saveNewCategory(formScopeDialog)\">保存</el-button>\n\t\t\t\t<el-button type=\"primary\" v-if=\"ismodify\" @click=\"modifyCategory(formScopeDialog)\">保存</el-button>\n\t\t\t\t<el-button @click=\"dialogFormVisible = false\">关 闭</el-button>\n\t\t\t</div>\n\t\t</el-dialog>\n\t\t<!-- 查看与编辑弹窗 end -->\n\t</div>\n</template>\n<style>\n\t.el-dialog__body .el-form-item__content{\n\t\tline-height: 0;\n\t}\n\t.el-form-item__content{\n\t\t/*position: static;*/\n\t}\n</style>\n<script>\n\timport Common from './../js/common';\n\texport default {\n\t\tdata() {\n\t\t\treturn {\n\t\t\t\tpickerOptions: {\n\t\t\t\t\tshortcuts: [{\n\t\t\t\t\t\ttext: '最近一周',\n\t\t\t\t\t\tonClick(picker) {\n\t\t\t\t\t\t\tconst end = new Date();\n\t\t\t\t\t\t\tconst start = new Date();\n\t\t\t\t\t\t\tstart.setTime(start.getTime() - 3600 * 1000 * 24 * 7);\n\t\t\t\t\t\t\tpicker.$emit('pick', [start, end]);\n\t\t\t\t\t\t}\n\t\t\t\t\t}, {\n\t\t\t\t\t\ttext: '最近一个月',\n\t\t\t\t\t\tonClick(picker) {\n\t\t\t\t\t\t\tconst end = new Date();\n\t\t\t\t\t\t\tconst start = new Date();\n\t\t\t\t\t\t\tstart.setTime(start.getTime() - 3600 * 1000 * 24 * 30);\n\t\t\t\t\t\t\tpicker.$emit('pick', [start, end]);\n\t\t\t\t\t\t}\n\t\t\t\t\t}, {\n\t\t\t\t\t\ttext: '最近三个月',\n\t\t\t\t\t\tonClick(picker) {\n\t\t\t\t\t\t\tconst end = new Date();\n\t\t\t\t\t\t\tconst start = new Date();\n\t\t\t\t\t\t\tstart.setTime(start.getTime() - 3600 * 1000 * 24 * 90);\n\t\t\t\t\t\t\tpicker.$emit('pick', [start, end]);\n\t\t\t\t\t\t}\n\t\t\t\t\t}]\n\t\t\t\t},\n\t\t\t\tdatepickerValue : [],\t\t\t\t\t\t//日期区间\n\t\t\t\tsearchForm : this.getDefaultSearchForm(),\t//搜索表单\n\t\t\t\tgrandList : [],\t\t\t\t\t\t\t\t//一级类目列表\n\t\t\t\tfatherList : [],\t\t\t\t\t\t\t\t//二级类目列表\n\t\t\t\tdialogFatherList:[],\t\t\t\t\t\t\t//弹窗中二级类目列表\n\t\t\t\toperatorList : [],\t\t\t\t\t\t\t//创建者列表\n\t\t\t\tpage : 1,\t\t\t\t\t\t\t\t\t//页码\n\t\t\t\tlimit : 10,\t\t\t\t\t\t\t\t\t//每页条数\n\t\t\t\tloading : false,\t\t\t\t\t\t\t\t//加载\n\t\t\t\tismodify : false,\t\t\t\t\t\t\t//编辑状态\n\t\t\t\tchecked : false,\t\t\t\t\t\t\t\t//app显示\n\t\t\t\tmultipleSelection: [],\t\t\t\t\t\t//多选集合\n\t\t\t\tcategoryList : {},\t\t\t\t\t\t\t//二级类目集合\n\t\t\t\tdialogFormVisible : false,\t\t\t\t\t//弹窗显示状态\n\t\t\t\tformLabelWidth : \"120px\",\t\t\t\t\t//label宽度\n\t\t\t\tfilesList : [],\t\t\t\t\t\t\t\t//上传图片列表\n\t\t\t\tfilesListhot : [],\t\t\t\t\t\t\t\t//上传图片列表\n\t\t\t\tgrandNickName : \"\",\t\t\t\t\t\t\t//一级类目别名\n\t\t\t\tfatherNickName : \"\",\t\t\t\t\t\t\t//二级类目别名\n\t\t\t\tformScopeDialog : this.getEmptyDialogForm()\t//弹窗表单\n\t\t\t}\n\t\t},\n\t\tmounted(){\n\t\t\tdocument.title = \"后台管理系统-三级类目管理\";\n\t\t\tthis.getOperatorList();\n\t\t\tthis.getGrandList();\n\t\t\tthis.getFatherList();\n\t\t\tthis.getListBySearchData();\n\t\t},\n\t\tmethods : {\n\t\t\t//搜索表单初始化\n\t\t\tgetDefaultSearchForm(){\n\t\t\t\treturn {\n\t\t\t\t\tgrandName : \"\",\n\t\t\t\t\tfatherName : \"\",\n\t\t\t\t\tcategoryName : \"\",\n\t\t\t\t\tstatus : \"\",\n\t\t\t\t\tappDisplay : \"\",\n\t\t\t\t\tcreater : \"\",\n\t\t\t\t\tstartTime : \"\",\n\t\t\t\t\tendTime : \"\"\n\t\t\t\t}\n\t\t\t},\n\t\t\t//新建时获取空表单\n\t\t\tgetEmptyDialogForm(){\n\t\t\t\treturn {\n\t\t\t\t\tdialogGrandId : \"\",\n\t\t\t\t\taccess_token : localStorage.access_token,\n\t\t\t\t\tcategoryName : \"\",\n\t\t\t\t\tfatherId : \"\",\n\t\t\t\t\thotIcon : \"\",\n\t\t\t\t\thotSort : -1,\n\t\t\t\t\thotable : false,\n\t\t\t\t\ticon : \"\",\n\t\t\t\t\tlevel : \"3\",\n\t\t\t\t\tnickName : \"\",\n\t\t\t\t\tsort : -1\n\t\t\t\t}\n\t\t\t},\n\t\t\t//获取创建者列表\n\t\t\tgetOperatorList(){\n\t\t\t\tthis.$http.post(\"/getAllOperators\",{}).then(response =>{\n\t\t\t\t\tthis.operatorList = response.data.result_list;\n\t\t\t\t});\n\t\t\t},\n\t\t\t//获取一级类目列表\n\t\t\tgetGrandList(){\n\t\t\t\tthis.$http.post(\"/json/900507\",{page:0,status:1,limit:10000}).then(response =>{\n\t\t\t\t\tthis.grandList = response.data.result_list;\n\t\t\t\t});\n\t\t\t},\n\t\t\t//获取二级类目列表\n\t\t\tgetFatherList(){\n\t\t\t\tthis.$http.post(\"/json/900508\",{page:0,limit:10000}).then(response =>{\n\t\t\t\t\tthis.fatherList = response.data.result_list;\n\t\t\t\t});\n\t\t\t},\n\t\t\t//获取二级类目列表\n\t\t\tgetFatherListById(id){\n\t\t\t\tthis.$http.post(\"/json/900515\",{fatherId : id, status : 1, webUse : true}).then(response =>{\n\t\t\t\t\tthis.fatherList = response.data.result_list.map(function(item){\n\t\t\t\t\t\treturn {\n\t\t\t\t\t\t\tbid : item.category_id,\n\t\t\t\t\t\t\tbname :item.category_name\n\t\t\t\t\t\t}\n\t\t\t\t\t});\n\t\t\t\t});\n\t\t\t},\n\t\t\t//选择一级类目\n\t\t\tgrandChange(val){\n\t\t\t\tif(!val){\n\t\t\t\t\tthis.getFatherList();\n\t\t\t\t\treturn;\n\t\t\t\t}\n\t\t\t\tvar id = this.grandList.filter(function(item){\n\t\t\t\t\treturn item.category_name == val;\n\t\t\t\t})[0].category_id;\n\t\t\t\tthis.getFatherListById(id);\n\t\t\t},\n\t\t\t//重置\n\t\t\tresetForm(formName){\n\t\t\t\tthis.$refs[formName].resetFields();\n\t\t\t\tthis.searchForm = this.getDefaultSearchForm();\n\t\t\t\tthis.datepickerValue = [];\n\t\t\t\tthis.submitForm();\n\t\t\t},\n\t\t\t//搜索\n\t\t\tsubmitForm(formName){\n\t\t\t\tthis.$data.page = 1;\t\n\t\t\t\tthis.getListBySearchData();\n\t\t\t},\n\t\t\t//日期范围变化\n\t\t\tdatepickerChange(val){\n\t\t\t\tif(val){\n\t\t\t\t\tvar arr = val.split(\" - \");\n\t\t\t\t\tthis.searchForm.startTime = arr[0].match(/\\d/g).join(\"\");\n\t\t\t\t\tthis.searchForm.endTime = arr[1].match(/\\d/g).join(\"\");\n\t\t\t\t}else{\n\t\t\t\t\tthis.searchForm.startTime = \"\";\n\t\t\t\t\tthis.searchForm.endTime = \"\";\n\t\t\t\t}\n\t\t\t},\n\t\t\t//多选状态改变时\n\t\t\thandleSelectionChange(val){\n\t\t\t\tthis.multipleSelection = val;\n\t\t\t},\n\t\t\t//翻页\n\t\t\thandleCurrentChange(val){\n\t\t\t\tthis.$data.page = val;\n\t\t\t\tthis.searchDataCache.page = (val-1)*this.limit;\n\t\t\t\tthis.getcategoryList();\n\t\t\t},\n\t\t\t//选择文件时\n\t\t\thandleFileChange(_file,_filelist){\n\t\t\t\tthis.filesList = [_filelist[_filelist.length-1]];\r\n\t\t\t\tvar that = this;\r\n\t\t\t\tvar reader = new FileReader(); \r\n\t\t\t\t\treader.readAsDataURL(_file.raw);\r\n\t\t\t\t\treader.onload = function(e){\r\n\t\t\t\t\t\tvar img = new Image();\r\n\t\t\t\t\t\t\timg.src = this.result;\r\n\t\t\t\t\t\t\timg.onload = function(){\r\n\t\t\t\t\t\t\t\tthat.formScopeDialog.icon = Common.compressImg(img,0.8,\"image/jpeg\");\n\t\t\t\t\t\t\t\t$(\".el-upload-list > li\").last().prev().remove();\r\n\t\t\t\t\t\t\t}\r\n\t\t\t\t\t}\n\t\t\t},\n\t\t\t//选择文件时\n\t\t\thandleFileChangehot(_file,_filelist){\n\t\t\t\tthis.filesListhot = [_filelist[_filelist.length-1]];\n\t\t\t\tvar that = this;\n\t\t\t\tvar reader = new FileReader(); \n\t\t\t\t\treader.readAsDataURL(_file.raw);\n\t\t\t\t\treader.onload = function(e){\n\t\t\t\t\t\tvar img = new Image();\n\t\t\t\t\t\t\timg.src = this.result;\n\t\t\t\t\t\t\timg.onload = function(){\n\t\t\t\t\t\t\t\tthat.formScopeDialog.hotIcon = Common.compressImg(img,0.8,\"image/jpeg\");\n\t\t\t\t\t\t\t\t$(\".el-upload-list > li\").last().prev().remove();\n\t\t\t\t\t\t\t}\n\t\t\t\t\t}\n\t\t\t},\n\t\t\thandleRemoveFile(_file,_filelist){\n\t\t\t\tif(!_filelist.length){\n\t\t\t\t\tthis.formScopeDialog.icon = \"\";\n\t\t\t\t}\n\t\t\t},\n\t\t\thandleRemoveFileHot(_file,_filelist){\n\t\t\t\tif(!_filelist.length){\n\t\t\t\t\tthis.formScopeDialog.hotIcon = \"\";\n\t\t\t\t}\n\t\t\t},\n\t\t\t//选择一级类目\n\t\t\tgrandIdChange(id){\n\t\t\t\tthis.grandNickName = !id ? \"\" : (this.grandList.filter(function(item){ return item.category_id == id; })[0].nick_name);\n\t\t\t\tif(!id){return;}\n\t\t\t\tthis.$http.post(\"/json/900515\",{fatherId:id,status:1,webUse:true}).then(response=>{\n\t\t\t\t\tif(response.data.error_no ==0){\n\t\t\t\t\t\tthis.dialogFatherList = response.data.result_list;\n\t\t\t\t\t}\n\t\t\t\t});\n\t\t\t},\n\t\t\t//选择二类类目\n\t\t\tfatherIdChange(val){\n\t\t\t\tif(!val){\n\t\t\t\t\tthis.fatherNickName = \"\";\n\t\t\t\t\treturn;\n\t\t\t\t}\n\t\t\t\tvar obj = this.dialogFatherList.filter((item)=>{\n\t\t\t\t\treturn item.category_id == val;\n\t\t\t\t})[0];\n\t\t\t\tif(obj){\n\t\t\t\t\tthis.fatherNickName = this.fatherNickName || obj.nick_name;\n\t\t\t\t}\n\t\t\t},\n\t\t\t//保存－新建类目\n\t\t\tsaveNewCategory(formName){\n\t\t\t\tthis.formScopeDialog.access_token = localStorage.access_token;\n\t\t\t\tthis.$refs[formName].validate((valid) => {\n\t\t\t\t\tif (valid) {\n\t\t\t\t\t\tthis.$http.post(\"/json/900505\",Common.filterParamByEmptyValue(this.formScopeDialog)).then(response=>{\n\t\t\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\t\t\tthis.$message({\n\t\t\t\t\t \t\t\ttype: jsondata.error_no == 0 ? \"success\" : \"error\",\n\t\t\t\t\t \t\t\tmessage: jsondata.error_no == 0 ? \"添加成功\" : jsondata.error_info\n\t\t\t\t\t \t\t});\n\t\t\t\t\t \t\tif(jsondata.error_no == 0){\n\t\t\t\t\t \t\t\tthis.resetForm(this.searchForm);\n\t\t\t\t\t \t\t\tthis.dialogFormVisible = false;\n\t\t\t\t\t \t\t\tlocalStorage.access_token = jsondata.access_token || localStorage.access_token;\n\t\t\t\t\t \t\t}\n\t\t\t\t\t\t});\n\t\t\t\t\t}\n\t\t\t\t});\n\t\t\t},\n\t\t\t//点击编辑\n\t\t\tmodify(index,list){\n\t\t\t\tvar item = list[index];\n\t\t\t\tthis.formScopeDialog = {\n\t\t\t\t\taccess_token : localStorage.access_token,\n\t\t\t\t\tdialogGrandId : item.aid,\n\t\t\t\t\tcategoryId : item.cid,\n\t\t\t\t\tcategoryName : item.cname,\n\t\t\t\t\t//fatherId : item.bid,\n\t\t\t\t\ticon : item.icon,\n\t\t\t\t\thotIcon : item.hot_icon,\n\t\t\t\t\tlevel : \"3\",\n\t\t\t\t\tnickName : item.nick_name,\n\t\t\t\t\tsort : item.sort,\n\t\t\t\t\thotSort : item.hot_sort,\n\t\t\t\t\thotable : item.hot_sort > -1\n\t\t\t\t};\n\t\t\t\tthis.grandIdChange(item.aid);\n\t\t\t\tthis.formScopeDialog.fatherId = item.bid;\n\t\t\t\tthis.grandNickName = item.anick_name;\n\t\t\t\tthis.fatherNickName = item.bnick_name;\n\t\t\t\tthis.filesList = [{\n\t\t          name: item.icon,\n\t\t          url: 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+item.icon,\n\t\t          status: 'finished'\n\t\t        }];\n\t\t        this.filesListhot = [{\n\t\t          name: item.hot_icon,\n\t\t          url: 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+item.hot_icon,\n\t\t          status: 'finished'\n\t\t        }];\n\t\t\t\tthis.checked = item.sort > -1;\n\t\t\t\tthis.dialogFormVisible = true;\n\t\t\t\tthis.ismodify = true;\n\t\t\t\tvar that = this;\n\t\t\t\tsetTimeout(function(){\n\t\t\t\t\tthat.$refs[that.formScopeDialog].validate();\n\t\t\t\t},50);\n\t\t\t},\n\t\t\t//编辑状态保存\n\t\t\tmodifyCategory(formName){\n\t\t\t\tthis.$refs[formName].validate((valid) => {\n\t\t\t\t\tif (valid) {\n\t\t\t\t\t\tthis.$http.post(\"/json/900506\",this.formScopeDialog).then(response=>{\n\t\t\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\t\t\tthis.$message({\n\t\t\t\t\t \t\t\ttype: jsondata.error_no == 0 ? \"success\" : \"error\",\n\t\t\t\t\t \t\t\tmessage: jsondata.error_no == 0 ? \"保存成功\" : jsondata.error_info\n\t\t\t\t\t \t\t});\n\t\t\t\t\t \t\tif(jsondata.error_no == 0){\n\t\t\t\t\t \t\t\tthis.getcategoryList();\n\t\t\t\t\t \t\t\tthis.dialogFormVisible = false;\n\t\t\t\t\t \t\t}\n\t\t\t\t\t\t});\n\t\t\t\t\t}\n\t\t\t\t});\n\t\t\t},\n\t\t\t//启用禁用\n\t\t\tsetStatus(index,list){\n\t\t\t\tvar item = list[index], status = item.status==1?0:1,\n\t\t\t\t\tcategoryId = item.cid;\n\t\t\t\t this.$confirm(\"确定要\" + (status==1?\"启用\":\"禁用\") + \"该类目吗？\", '', {\n\t\t\t\t \tconfirmButtonText: '确定',\n\t\t\t\t \tcancelButtonText: '取消',\n\t\t\t\t \ttype: 'warning'\n\t\t\t\t }).then(() => {\n\t\t\t\t \tvar param = {\n\t\t\t\t \t\taccess_token : localStorage.access_token,\n\t\t\t\t \t\tcategoryId : categoryId,\n\t\t\t\t \t\tstatus : status\n\t\t\t\t \t};\n\t\t\t\t \tthis.$http.post('./../json/900517',param).then(response => {\n\t\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\t\t\tthis.$message({\n\t\t\t\t\t \t\t\ttype: jsondata.error_no == 0 ? \"success\" : \"error\",\n\t\t\t\t\t \t\t\tmessage: jsondata.error_no == 0 ? \"操作成功\" : jsondata.error_info\n\t\t\t\t\t \t\t});\n\t\t\t\t\t \t\tif(jsondata.error_no == 0){\n\t\t\t\t\t \t\t\tthis.getcategoryList();\n\t\t\t\t\t \t\t}\n\t\t\t\t\t});\n\t\t\t\t });\n\t\t\t},\n\t\t\t//多选启用禁用\n\t\t\tsetStatusBySelection(status){\n\t\t\t\tvar categoryIds = this.multipleSelection.map(function(item){\n\t\t\t\t\treturn item.cid;\n\t\t\t\t});\n\t\t\t\t this.$confirm(\"确定要批量\" + (status==1?\"启用\":\"禁用\"), '', {\n\t\t\t\t \tconfirmButtonText: '确定',\n\t\t\t\t \tcancelButtonText: '取消',\n\t\t\t\t \ttype: 'warning'\n\t\t\t\t }).then(() => {\n\t\t\t\t \tvar param = {\n\t\t\t\t \t\taccess_token : localStorage.access_token,\n\t\t\t\t \t\tcategoryIds : categoryIds,\n\t\t\t\t \t\tstatus : status\n\t\t\t\t \t};\n\t\t\t\t \tthis.$http.post('./../json/900513',param).then(response => {\n\t\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\t\t\tthis.$message({\n\t\t\t\t\t \t\t\ttype: jsondata.error_no == 0 ? \"success\" : \"error\",\n\t\t\t\t\t \t\t\tmessage: jsondata.error_no == 0 ? \"操作成功\" : jsondata.error_info\n\t\t\t\t\t \t\t});\n\t\t\t\t\t \t\tif(jsondata.error_no == 0){\n\t\t\t\t\t \t\t\tthis.getcategoryList();\n\t\t\t\t\t \t\t}\n\t\t\t\t\t});\n\t\t\t\t });\n\t\t\t},\n\t\t\t//新建\n\t\t\taddNewCategory(){\n\t\t\t\tthis.dialogFormVisible = true;\n\t\t\t\tthis.ismodify = false;\n\t\t\t\tthis.checked = false;\n\t\t\t\t//this.dialogFatherList = this.fatherList;\n\t\t\t\tthis.filesList = [];\n\t\t\t\tthis.filesListhot = [];\n\t\t\t\tthis.formScopeDialog = this.getEmptyDialogForm();\n\t\t\t},\n\t\t\tgetListBySearchData(){\n\t\t\t\tvar param = Common.deepClone(this.searchForm,{\n\t\t\t\t\tpage : (this.page-1)*this.limit,\n\t\t\t\t\tlimit : this.limit\n\t\t\t\t});\n\t\t\t\tthis.searchDataCache = Common.simpleClone(param);\n\t\t\t\tthis.getcategoryList();\n\t\t\t},\n\t\t\t//获取类目列表\n\t\t\tgetcategoryList(){\n\t\t\t\tif(this.loading) return;\n\t\t\t\tthis.loading = true;\n\t\t\t\tthis.$http.post(\"/json/900511\",Common.filterParamByEmptyValue(this.searchDataCache)).then(response => {\n\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\tif(jsondata.error_no !=0){\n\t\t\t\t\t\tthis.$message({\n\t\t\t\t\t\t\ttype: \"warning\",\n\t\t\t\t\t\t\tmessage: jsondata.error_info\n\t\t\t\t\t\t});\n\t\t\t\t\t}else{\t\n\t\t\t\t\t\tjsondata.result_list = jsondata.result_list.map((item)=>{\n\t\t\t\t\t\t\titem.createTime = Common.formatDateConcat(item.init_date,item.init_time);\n\t\t\t\t\t\t\titem.updateTime = Common.formatDateConcat(item.update_date,item.update_time);\n\t\t\t\t\t\t\treturn item;\n\t\t\t\t\t\t});\n\t\t\t\t\t\tthis.categoryList = jsondata;\n\t\t\t\t\t}\n\t\t\t\t\t\n\t\t\t\t\tthis.loading = false;\n\t\t\t\t\t\n\t\t\t\t});\n\t\t\t}\n\t\t}\n\t}\n</script>"],"sourceRoot":"webpack://"}]);
	
	// exports


/***/ }),

/***/ 75:
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
				datepickerValue: [], //日期区间
				searchForm: this.getDefaultSearchForm(), //搜索表单
				grandList: [], //一级类目列表
				fatherList: [], //二级类目列表
				dialogFatherList: [], //弹窗中二级类目列表
				operatorList: [], //创建者列表
				page: 1, //页码
				limit: 10, //每页条数
				loading: false, //加载
				ismodify: false, //编辑状态
				checked: false, //app显示
				multipleSelection: [], //多选集合
				categoryList: {}, //二级类目集合
				dialogFormVisible: false, //弹窗显示状态
				formLabelWidth: "120px", //label宽度
				filesList: [], //上传图片列表
				filesListhot: [], //上传图片列表
				grandNickName: "", //一级类目别名
				fatherNickName: "", //二级类目别名
				formScopeDialog: this.getEmptyDialogForm() //弹窗表单
			};
		},
		mounted: function mounted() {
			document.title = "后台管理系统-三级类目管理";
			this.getOperatorList();
			this.getGrandList();
			this.getFatherList();
			this.getListBySearchData();
		},
	
		methods: {
			//搜索表单初始化
			getDefaultSearchForm: function getDefaultSearchForm() {
				return {
					grandName: "",
					fatherName: "",
					categoryName: "",
					status: "",
					appDisplay: "",
					creater: "",
					startTime: "",
					endTime: ""
				};
			},
	
			//新建时获取空表单
			getEmptyDialogForm: function getEmptyDialogForm() {
				return {
					dialogGrandId: "",
					access_token: localStorage.access_token,
					categoryName: "",
					fatherId: "",
					hotIcon: "",
					hotSort: -1,
					hotable: false,
					icon: "",
					level: "3",
					nickName: "",
					sort: -1
				};
			},
	
			//获取创建者列表
			getOperatorList: function getOperatorList() {
				var _this = this;
	
				this.$http.post("/getAllOperators", {}).then(function (response) {
					_this.operatorList = response.data.result_list;
				});
			},
	
			//获取一级类目列表
			getGrandList: function getGrandList() {
				var _this2 = this;
	
				this.$http.post("/json/900507", { page: 0, status: 1, limit: 10000 }).then(function (response) {
					_this2.grandList = response.data.result_list;
				});
			},
	
			//获取二级类目列表
			getFatherList: function getFatherList() {
				var _this3 = this;
	
				this.$http.post("/json/900508", { page: 0, limit: 10000 }).then(function (response) {
					_this3.fatherList = response.data.result_list;
				});
			},
	
			//获取二级类目列表
			getFatherListById: function getFatherListById(id) {
				var _this4 = this;
	
				this.$http.post("/json/900515", { fatherId: id, status: 1, webUse: true }).then(function (response) {
					_this4.fatherList = response.data.result_list.map(function (item) {
						return {
							bid: item.category_id,
							bname: item.category_name
						};
					});
				});
			},
	
			//选择一级类目
			grandChange: function grandChange(val) {
				if (!val) {
					this.getFatherList();
					return;
				}
				var id = this.grandList.filter(function (item) {
					return item.category_name == val;
				})[0].category_id;
				this.getFatherListById(id);
			},
	
			//重置
			resetForm: function resetForm(formName) {
				this.$refs[formName].resetFields();
				this.searchForm = this.getDefaultSearchForm();
				this.datepickerValue = [];
				this.submitForm();
			},
	
			//搜索
			submitForm: function submitForm(formName) {
				this.$data.page = 1;
				this.getListBySearchData();
			},
	
			//日期范围变化
			datepickerChange: function datepickerChange(val) {
				if (val) {
					var arr = val.split(" - ");
					this.searchForm.startTime = arr[0].match(/\d/g).join("");
					this.searchForm.endTime = arr[1].match(/\d/g).join("");
				} else {
					this.searchForm.startTime = "";
					this.searchForm.endTime = "";
				}
			},
	
			//多选状态改变时
			handleSelectionChange: function handleSelectionChange(val) {
				this.multipleSelection = val;
			},
	
			//翻页
			handleCurrentChange: function handleCurrentChange(val) {
				this.$data.page = val;
				this.searchDataCache.page = (val - 1) * this.limit;
				this.getcategoryList();
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
						that.formScopeDialog.icon = _common2.default.compressImg(img, 0.8, "image/jpeg");
						$(".el-upload-list > li").last().prev().remove();
					};
				};
			},
	
			//选择文件时
			handleFileChangehot: function handleFileChangehot(_file, _filelist) {
				this.filesListhot = [_filelist[_filelist.length - 1]];
				var that = this;
				var reader = new FileReader();
				reader.readAsDataURL(_file.raw);
				reader.onload = function (e) {
					var img = new Image();
					img.src = this.result;
					img.onload = function () {
						that.formScopeDialog.hotIcon = _common2.default.compressImg(img, 0.8, "image/jpeg");
						$(".el-upload-list > li").last().prev().remove();
					};
				};
			},
			handleRemoveFile: function handleRemoveFile(_file, _filelist) {
				if (!_filelist.length) {
					this.formScopeDialog.icon = "";
				}
			},
			handleRemoveFileHot: function handleRemoveFileHot(_file, _filelist) {
				if (!_filelist.length) {
					this.formScopeDialog.hotIcon = "";
				}
			},
	
			//选择一级类目
			grandIdChange: function grandIdChange(id) {
				var _this5 = this;
	
				this.grandNickName = !id ? "" : this.grandList.filter(function (item) {
					return item.category_id == id;
				})[0].nick_name;
				if (!id) {
					return;
				}
				this.$http.post("/json/900515", { fatherId: id, status: 1, webUse: true }).then(function (response) {
					if (response.data.error_no == 0) {
						_this5.dialogFatherList = response.data.result_list;
					}
				});
			},
	
			//选择二类类目
			fatherIdChange: function fatherIdChange(val) {
				if (!val) {
					this.fatherNickName = "";
					return;
				}
				var obj = this.dialogFatherList.filter(function (item) {
					return item.category_id == val;
				})[0];
				if (obj) {
					this.fatherNickName = this.fatherNickName || obj.nick_name;
				}
			},
	
			//保存－新建类目
			saveNewCategory: function saveNewCategory(formName) {
				var _this6 = this;
	
				this.formScopeDialog.access_token = localStorage.access_token;
				this.$refs[formName].validate(function (valid) {
					if (valid) {
						_this6.$http.post("/json/900505", _common2.default.filterParamByEmptyValue(_this6.formScopeDialog)).then(function (response) {
							var jsondata = response.data;
							_this6.$message({
								type: jsondata.error_no == 0 ? "success" : "error",
								message: jsondata.error_no == 0 ? "添加成功" : jsondata.error_info
							});
							if (jsondata.error_no == 0) {
								_this6.resetForm(_this6.searchForm);
								_this6.dialogFormVisible = false;
								localStorage.access_token = jsondata.access_token || localStorage.access_token;
							}
						});
					}
				});
			},
	
			//点击编辑
			modify: function modify(index, list) {
				var item = list[index];
				this.formScopeDialog = {
					access_token: localStorage.access_token,
					dialogGrandId: item.aid,
					categoryId: item.cid,
					categoryName: item.cname,
					//fatherId : item.bid,
					icon: item.icon,
					hotIcon: item.hot_icon,
					level: "3",
					nickName: item.nick_name,
					sort: item.sort,
					hotSort: item.hot_sort,
					hotable: item.hot_sort > -1
				};
				this.grandIdChange(item.aid);
				this.formScopeDialog.fatherId = item.bid;
				this.grandNickName = item.anick_name;
				this.fatherNickName = item.bnick_name;
				this.filesList = [{
					name: item.icon,
					url: 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/' + item.icon,
					status: 'finished'
				}];
				this.filesListhot = [{
					name: item.hot_icon,
					url: 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/' + item.hot_icon,
					status: 'finished'
				}];
				this.checked = item.sort > -1;
				this.dialogFormVisible = true;
				this.ismodify = true;
				var that = this;
				setTimeout(function () {
					that.$refs[that.formScopeDialog].validate();
				}, 50);
			},
	
			//编辑状态保存
			modifyCategory: function modifyCategory(formName) {
				var _this7 = this;
	
				this.$refs[formName].validate(function (valid) {
					if (valid) {
						_this7.$http.post("/json/900506", _this7.formScopeDialog).then(function (response) {
							var jsondata = response.data;
							_this7.$message({
								type: jsondata.error_no == 0 ? "success" : "error",
								message: jsondata.error_no == 0 ? "保存成功" : jsondata.error_info
							});
							if (jsondata.error_no == 0) {
								_this7.getcategoryList();
								_this7.dialogFormVisible = false;
							}
						});
					}
				});
			},
	
			//启用禁用
			setStatus: function setStatus(index, list) {
				var _this8 = this;
	
				var item = list[index],
				    status = item.status == 1 ? 0 : 1,
				    categoryId = item.cid;
				this.$confirm("确定要" + (status == 1 ? "启用" : "禁用") + "该类目吗？", '', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(function () {
					var param = {
						access_token: localStorage.access_token,
						categoryId: categoryId,
						status: status
					};
					_this8.$http.post('./../json/900517', param).then(function (response) {
						var jsondata = response.data;
						_this8.$message({
							type: jsondata.error_no == 0 ? "success" : "error",
							message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
						});
						if (jsondata.error_no == 0) {
							_this8.getcategoryList();
						}
					});
				});
			},
	
			//多选启用禁用
			setStatusBySelection: function setStatusBySelection(status) {
				var _this9 = this;
	
				var categoryIds = this.multipleSelection.map(function (item) {
					return item.cid;
				});
				this.$confirm("确定要批量" + (status == 1 ? "启用" : "禁用"), '', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(function () {
					var param = {
						access_token: localStorage.access_token,
						categoryIds: categoryIds,
						status: status
					};
					_this9.$http.post('./../json/900513', param).then(function (response) {
						var jsondata = response.data;
						_this9.$message({
							type: jsondata.error_no == 0 ? "success" : "error",
							message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
						});
						if (jsondata.error_no == 0) {
							_this9.getcategoryList();
						}
					});
				});
			},
	
			//新建
			addNewCategory: function addNewCategory() {
				this.dialogFormVisible = true;
				this.ismodify = false;
				this.checked = false;
				//this.dialogFatherList = this.fatherList;
				this.filesList = [];
				this.filesListhot = [];
				this.formScopeDialog = this.getEmptyDialogForm();
			},
			getListBySearchData: function getListBySearchData() {
				var param = _common2.default.deepClone(this.searchForm, {
					page: (this.page - 1) * this.limit,
					limit: this.limit
				});
				this.searchDataCache = _common2.default.simpleClone(param);
				this.getcategoryList();
			},
	
			//获取类目列表
			getcategoryList: function getcategoryList() {
				var _this10 = this;
	
				if (this.loading) return;
				this.loading = true;
				this.$http.post("/json/900511", _common2.default.filterParamByEmptyValue(this.searchDataCache)).then(function (response) {
					var jsondata = response.data;
					if (jsondata.error_no != 0) {
						_this10.$message({
							type: "warning",
							message: jsondata.error_info
						});
					} else {
						jsondata.result_list = jsondata.result_list.map(function (item) {
							item.createTime = _common2.default.formatDateConcat(item.init_date, item.init_time);
							item.updateTime = _common2.default.formatDateConcat(item.update_date, item.update_time);
							return item;
						});
						_this10.categoryList = jsondata;
					}
	
					_this10.loading = false;
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
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
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

/***/ 76:
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
	  }, [_vm._v("用户中心")]), _vm._v(" "), _c('el-breadcrumb-item', [_vm._v("类目管理")]), _vm._v(" "), _c('el-breadcrumb-item', [_vm._v("三级类目管理")])], 1)], 1), _vm._v(" "), _c('div', {
	    staticClass: "block-white"
	  }, [_c('el-form', {
	    ref: _vm.searchForm,
	    staticClass: "demo-form-inline searchForm",
	    attrs: {
	      "inline": true,
	      "model": _vm.searchForm
	    }
	  }, [_c('el-form-item', {
	    attrs: {
	      "label": "一级"
	    }
	  }, [_c('el-select', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "filterable": "",
	      "placeholder": "输入匹配"
	    },
	    on: {
	      "change": _vm.grandChange
	    },
	    model: {
	      value: (_vm.searchForm.grandName),
	      callback: function($$v) {
	        _vm.searchForm.grandName = $$v
	      },
	      expression: "searchForm.grandName"
	    }
	  }, _vm._l((_vm.grandList), function(item) {
	    return _c('el-option', {
	      key: item.category_id,
	      attrs: {
	        "label": item.category_name,
	        "value": item.category_name
	      }
	    })
	  }))], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "二级"
	    }
	  }, [_c('el-select', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "filterable": "",
	      "placeholder": "输入匹配"
	    },
	    model: {
	      value: (_vm.searchForm.fatherName),
	      callback: function($$v) {
	        _vm.searchForm.fatherName = $$v
	      },
	      expression: "searchForm.fatherName"
	    }
	  }, _vm._l((_vm.fatherList), function(item) {
	    return _c('el-option', {
	      key: item.bid,
	      attrs: {
	        "label": item.bname,
	        "value": item.bname
	      }
	    })
	  }))], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "三级"
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "模糊查询"
	    },
	    model: {
	      value: (_vm.searchForm.categoryName),
	      callback: function($$v) {
	        _vm.searchForm.categoryName = $$v
	      },
	      expression: "searchForm.categoryName"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "创建者"
	    }
	  }, [_c('el-select', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "filterable": "",
	      "placeholder": "输入匹配"
	    },
	    model: {
	      value: (_vm.searchForm.creater),
	      callback: function($$v) {
	        _vm.searchForm.creater = $$v
	      },
	      expression: "searchForm.creater"
	    }
	  }, _vm._l((_vm.operatorList), function(item) {
	    return _c('el-option', {
	      key: item.operator_name,
	      attrs: {
	        "label": item.operator_name,
	        "value": item.operator_name
	      }
	    })
	  }))], 1), _vm._v(" "), _c('el-form-item', [_c('el-select', {
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
	  })], 1)], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "APP显示"
	    }
	  }, [_c('el-select', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "请选择APP状态"
	    },
	    model: {
	      value: (_vm.searchForm.appDisplay),
	      callback: function($$v) {
	        _vm.searchForm.appDisplay = $$v
	      },
	      expression: "searchForm.appDisplay"
	    }
	  }, [_c('el-option', {
	    attrs: {
	      "label": "显示",
	      "value": "true"
	    }
	  }), _vm._v(" "), _c('el-option', {
	    attrs: {
	      "label": "不显示",
	      "value": "false"
	    }
	  })], 1)], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "创建日期"
	    }
	  }, [_c('el-date-picker', {
	    attrs: {
	      "type": "datetimerange",
	      "picker-options": _vm.pickerOptions,
	      "placeholder": "选择时间范围",
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
	  })], 1), _vm._v(" "), _c('el-form-item', [_c('el-button', {
	    staticClass: "searchBtn",
	    attrs: {
	      "type": "primary"
	    },
	    on: {
	      "click": function($event) {
	        _vm.submitForm(_vm.searchForm)
	      }
	    }
	  }, [_vm._v("搜索")]), _vm._v(" "), _c('el-button', {
	    on: {
	      "click": function($event) {
	        _vm.resetForm(_vm.searchForm)
	      }
	    }
	  }, [_vm._v("清除")])], 1)], 1)], 1), _vm._v(" "), _c('div', {
	    staticClass: "block-white"
	  }, [_c('el-button', {
	    attrs: {
	      "type": "info"
	    },
	    on: {
	      "click": _vm.addNewCategory
	    }
	  }, [_vm._v("新增")]), _vm._v(" "), _c('el-button', {
	    attrs: {
	      "disabled": !_vm.multipleSelection.length,
	      "type": "info"
	    },
	    on: {
	      "click": function($event) {
	        _vm.setStatusBySelection(1)
	      }
	    }
	  }, [_vm._v("启用")]), _vm._v(" "), _c('el-button', {
	    attrs: {
	      "disabled": !_vm.multipleSelection.length,
	      "type": "danger"
	    },
	    on: {
	      "click": function($event) {
	        _vm.setStatusBySelection(0)
	      }
	    }
	  }, [_vm._v("禁用")])], 1), _vm._v(" "), _c('div', {
	    staticClass: "block-white"
	  }, [_c('div', {
	    staticClass: "selection-tip"
	  }, [_c('i', {
	    staticClass: "el-icon-warning"
	  }), _c('span', [_vm._v("已选择 " + _vm._s(_vm.multipleSelection.length) + " 项数据。")])])]), _vm._v(" "), _c('div', {
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
	    ref: "multipleTable",
	    staticStyle: {
	      "width": "100%"
	    },
	    attrs: {
	      "data": _vm.categoryList.result_list,
	      "border": "",
	      "stripe": "",
	      "max-height": "460"
	    },
	    on: {
	      "selection-change": _vm.handleSelectionChange
	    }
	  }, [_c('el-table-column', {
	    staticClass: "hjhcheckbox",
	    attrs: {
	      "fixed": "",
	      "type": "selection",
	      "width": "55"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
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
	      "prop": "cid",
	      "label": "代码",
	      "width": "200"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "aname",
	      "label": "一级",
	      "align": "center",
	      "width": "130"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "bname",
	      "label": "二级",
	      "align": "center",
	      "width": "130"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "cname",
	      "label": "三级",
	      "align": "center",
	      "width": "130"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "nick_name",
	      "label": "三级别名",
	      "align": "center",
	      "width": "130"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "icon",
	      "label": "图片",
	      "width": "110",
	      "align": "center"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_c('img', {
	          attrs: {
	            "src": 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/' + scope.row.icon
	          }
	        })]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "sort",
	      "label": "APP排序",
	      "align": "center",
	      "width": "110"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "hot_sort",
	      "label": "热门排序",
	      "width": "110",
	      "align": "center"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "createTime",
	      "label": "创建日期",
	      "align": "center",
	      "width": "180"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "creater",
	      "label": "创建者",
	      "align": "center",
	      "width": "100"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "updateTime",
	      "label": "最后操作日期",
	      "align": "center",
	      "width": "180"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "updater",
	      "label": "最后操作者",
	      "align": "center",
	      "width": "120"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "status",
	      "label": "状态",
	      "align": "center",
	      "width": "80"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_vm._v(_vm._s(scope.row.status == 1 ? "启用" : "禁用"))]
	      }
	    }])
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
	              _vm.modify(scope.$index, _vm.categoryList.result_list)
	            }
	          }
	        }, [_vm._v("编辑")]), _vm._v(" "), _c('el-button', {
	          attrs: {
	            "type": "danger"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.setStatus(scope.$index, _vm.categoryList.result_list)
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
	      "total": _vm.categoryList.total_num
	    },
	    on: {
	      "current-change": _vm.handleCurrentChange
	    }
	  })], 1), _vm._v(" "), _c('el-dialog', {
	    attrs: {
	      "title": _vm.ismodify ? '编辑三级类目' : '新增三级类目'
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
	      "label-position": "right",
	      "label-width": _vm.formLabelWidth,
	      "model": _vm.formScopeDialog
	    }
	  }, [_c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 12
	    }
	  }, [_c('el-form-item', {
	    attrs: {
	      "label": "一级类目",
	      "prop": "dialogGrandId",
	      "rules": [{
	        required: true,
	        message: '请选择一级类目',
	        trigger: 'blur'
	      }]
	    }
	  }, [_c('el-select', {
	    staticStyle: {
	      "width": "200px"
	    },
	    attrs: {
	      "filterable": "",
	      "placeholder": "输入匹配"
	    },
	    on: {
	      "change": _vm.grandIdChange
	    },
	    model: {
	      value: (_vm.formScopeDialog.dialogGrandId),
	      callback: function($$v) {
	        _vm.formScopeDialog.dialogGrandId = $$v
	      },
	      expression: "formScopeDialog.dialogGrandId"
	    }
	  }, _vm._l((_vm.grandList), function(item) {
	    return _c('el-option', {
	      key: item.category_id,
	      attrs: {
	        "label": item.category_name,
	        "value": item.category_id
	      }
	    })
	  }))], 1)], 1), _vm._v(" "), _c('el-col', {
	    staticClass: "line",
	    staticStyle: {
	      "text-align": "right",
	      "line-height": "36px",
	      "padding-right": "10px"
	    },
	    attrs: {
	      "span": 2
	    }
	  }, [_vm._v("别名 ")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 8
	    }
	  }, [_c('el-input', {
	    attrs: {
	      "readonly": true,
	      "minlength": 2,
	      "maxlength": 7,
	      "placeholder": "输入别名"
	    },
	    model: {
	      value: (_vm.grandNickName),
	      callback: function($$v) {
	        _vm.grandNickName = $$v
	      },
	      expression: "grandNickName"
	    }
	  })], 1)], 1), _vm._v(" "), _c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 12
	    }
	  }, [_c('el-form-item', {
	    attrs: {
	      "label": "二级类目",
	      "prop": "fatherId",
	      "rules": [{
	        required: true,
	        message: '请选择二级类目',
	        trigger: 'blur'
	      }]
	    }
	  }, [_c('el-select', {
	    staticStyle: {
	      "width": "200px"
	    },
	    attrs: {
	      "filterable": "",
	      "placeholder": "输入匹配"
	    },
	    on: {
	      "change": _vm.fatherIdChange
	    },
	    model: {
	      value: (_vm.formScopeDialog.fatherId),
	      callback: function($$v) {
	        _vm.formScopeDialog.fatherId = $$v
	      },
	      expression: "formScopeDialog.fatherId"
	    }
	  }, _vm._l((_vm.dialogFatherList), function(item) {
	    return _c('el-option', {
	      key: item.category_id,
	      attrs: {
	        "label": item.category_name,
	        "value": item.category_id
	      }
	    })
	  }))], 1)], 1), _vm._v(" "), _c('el-col', {
	    staticClass: "line",
	    staticStyle: {
	      "text-align": "right",
	      "line-height": "36px",
	      "padding-right": "10px"
	    },
	    attrs: {
	      "span": 2
	    }
	  }, [_vm._v("别名 ")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 8
	    }
	  }, [_c('el-input', {
	    attrs: {
	      "readonly": true,
	      "minlength": 2,
	      "maxlength": 7,
	      "placeholder": "输入别名"
	    },
	    model: {
	      value: (_vm.fatherNickName),
	      callback: function($$v) {
	        _vm.fatherNickName = $$v
	      },
	      expression: "fatherNickName"
	    }
	  })], 1)], 1), _vm._v(" "), _c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 12
	    }
	  }, [_c('el-form-item', {
	    attrs: {
	      "label": "三级类目",
	      "label-width": _vm.formLabelWidth,
	      "prop": "categoryName",
	      "rules": [{
	        required: true,
	        message: '请输入三级类目',
	        trigger: 'blur'
	      }, {
	        min: 2,
	        max: 7,
	        message: '二级类目长度为2 至 7个字符',
	        trigger: 'blur'
	      }]
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "200px"
	    },
	    attrs: {
	      "minlength": 2,
	      "maxlength": 7,
	      "placeholder": "输入三级类目"
	    },
	    model: {
	      value: (_vm.formScopeDialog.categoryName),
	      callback: function($$v) {
	        _vm.formScopeDialog.categoryName = $$v
	      },
	      expression: "formScopeDialog.categoryName"
	    }
	  })], 1)], 1), _vm._v(" "), _c('el-col', {
	    staticClass: "line",
	    staticStyle: {
	      "text-align": "right",
	      "line-height": "36px",
	      "padding-right": "10px"
	    },
	    attrs: {
	      "span": 2
	    }
	  }, [_vm._v("别名")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 8
	    }
	  }, [_c('el-input', {
	    attrs: {
	      "minlength": 2,
	      "maxlength": 7,
	      "placeholder": "输入别名"
	    },
	    model: {
	      value: (_vm.formScopeDialog.nickName),
	      callback: function($$v) {
	        _vm.formScopeDialog.nickName = $$v
	      },
	      expression: "formScopeDialog.nickName"
	    }
	  })], 1)], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label-width": _vm.formLabelWidth,
	      "label": "图标",
	      "prop": "icon",
	      "rules": {
	        required: true,
	        message: '请设置图标',
	        trigger: 'blur'
	      }
	    }
	  }, [_c('el-input', {
	    attrs: {
	      "type": "hidden"
	    },
	    model: {
	      value: (_vm.formScopeDialog.icon),
	      callback: function($$v) {
	        _vm.formScopeDialog.icon = $$v
	      },
	      expression: "formScopeDialog.icon"
	    }
	  }), _vm._v(" "), _c('el-upload', {
	    staticClass: "upload-demo",
	    attrs: {
	      "action": "https://jsonplaceholder.typicode.com/posts/",
	      "auto-upload": false,
	      "on-change": _vm.handleFileChange,
	      "on-remove": _vm.handleRemoveFile,
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
	  }, [_vm._v("建议尺寸：750*270px")])], 1)], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label-width": _vm.formLabelWidth
	    }
	  }, [_c('el-col', {
	    attrs: {
	      "span": 4
	    }
	  }, [_c('el-checkbox', {
	    model: {
	      value: (_vm.checked),
	      callback: function($$v) {
	        _vm.checked = $$v
	      },
	      expression: "checked"
	    }
	  }, [_vm._v("APP显示")])], 1), _vm._v(" "), (_vm.checked) ? _c('el-col', {
	    staticClass: "line",
	    staticStyle: {
	      "text-align": "right",
	      "line-height": "20px",
	      "padding-right": "10px"
	    },
	    attrs: {
	      "span": 4
	    }
	  }, [_vm._v("排序 ")]) : _vm._e(), _vm._v(" "), (_vm.checked) ? _c('el-col', {
	    staticStyle: {
	      "margin-top": "-10px"
	    },
	    attrs: {
	      "span": 8
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "type": "number",
	      "placeholder": "排序"
	    },
	    model: {
	      value: (_vm.formScopeDialog.sort),
	      callback: function($$v) {
	        _vm.formScopeDialog.sort = $$v
	      },
	      expression: "formScopeDialog.sort"
	    }
	  })], 1) : _vm._e()], 1), _vm._v(" "), (_vm.checked) ? _c('el-form-item', {
	    attrs: {
	      "label-width": _vm.formLabelWidth
	    }
	  }, [_c('el-col', {
	    attrs: {
	      "span": 4
	    }
	  }, [_c('el-checkbox', {
	    model: {
	      value: (_vm.formScopeDialog.hotable),
	      callback: function($$v) {
	        _vm.formScopeDialog.hotable = $$v
	      },
	      expression: "formScopeDialog.hotable"
	    }
	  }, [_vm._v("热门排序")])], 1), _vm._v(" "), (_vm.formScopeDialog.hotable) ? _c('el-col', {
	    staticClass: "line",
	    staticStyle: {
	      "text-align": "right",
	      "line-height": "20px",
	      "padding-right": "10px"
	    },
	    attrs: {
	      "span": 4
	    }
	  }, [_vm._v("热门排序 ")]) : _vm._e(), _vm._v(" "), (_vm.formScopeDialog.hotable) ? _c('el-col', {
	    staticStyle: {
	      "margin-top": "-10px"
	    },
	    attrs: {
	      "span": 8
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "type": "number",
	      "placeholder": "排序"
	    },
	    model: {
	      value: (_vm.formScopeDialog.hotSort),
	      callback: function($$v) {
	        _vm.formScopeDialog.hotSort = $$v
	      },
	      expression: "formScopeDialog.hotSort"
	    }
	  })], 1) : _vm._e()], 1) : _vm._e(), _vm._v(" "), (_vm.checked && _vm.formScopeDialog.hotable) ? _c('el-form-item', {
	    attrs: {
	      "label-width": _vm.formLabelWidth,
	      "label": "热门图标",
	      "prop": "hotIcon",
	      "rules": {
	        required: true,
	        message: '请设置图标',
	        trigger: 'blur'
	      }
	    }
	  }, [_c('el-input', {
	    attrs: {
	      "type": "hidden"
	    },
	    model: {
	      value: (_vm.formScopeDialog.hotIcon),
	      callback: function($$v) {
	        _vm.formScopeDialog.hotIcon = $$v
	      },
	      expression: "formScopeDialog.hotIcon"
	    }
	  }), _vm._v(" "), _c('el-upload', {
	    staticClass: "upload-demo",
	    attrs: {
	      "action": "https://jsonplaceholder.typicode.com/posts/",
	      "auto-upload": false,
	      "on-change": _vm.handleFileChangehot,
	      "on-remove": _vm.handleRemoveFileHot,
	      "file-list": _vm.filesListhot
	    }
	  }, [_c('el-button', {
	    attrs: {
	      "size": "small",
	      "type": "primary"
	    }
	  }, [_vm._v("点击上传")]), _vm._v(" "), _c('div', {
	    staticClass: "el-upload__tip",
	    slot: "tip"
	  }, [_vm._v("建议尺寸：750*270px")])], 1)], 1) : _vm._e()], 1)], 1), _vm._v(" "), _c('div', {
	    staticClass: "dialog-footer",
	    staticStyle: {
	      "text-align": "center"
	    },
	    slot: "footer"
	  }, [(!_vm.ismodify) ? _c('el-button', {
	    attrs: {
	      "type": "primary"
	    },
	    on: {
	      "click": function($event) {
	        _vm.saveNewCategory(_vm.formScopeDialog)
	      }
	    }
	  }, [_vm._v("保存")]) : _vm._e(), _vm._v(" "), (_vm.ismodify) ? _c('el-button', {
	    attrs: {
	      "type": "primary"
	    },
	    on: {
	      "click": function($event) {
	        _vm.modifyCategory(_vm.formScopeDialog)
	      }
	    }
	  }, [_vm._v("保存")]) : _vm._e(), _vm._v(" "), _c('el-button', {
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
	     require("vue-hot-reload-api").rerender("data-v-7d84a262", module.exports)
	  }
	}

/***/ })

});
//# sourceMappingURL=36.js.map