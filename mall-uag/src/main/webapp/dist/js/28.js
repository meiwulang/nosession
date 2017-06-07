webpackJsonp([28],{

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

/***/ 33:
/***/ (function(module, exports, __webpack_require__) {

	
	/* styles */
	__webpack_require__(34)
	
	var Component = __webpack_require__(13)(
	  /* script */
	  __webpack_require__(36),
	  /* template */
	  __webpack_require__(37),
	  /* scopeId */
	  null,
	  /* cssModules */
	  null
	)
	Component.options.__file = "/Users/xiangyangli/projects/hjh/jhd/mall-uag/src/main/webapp/app/components/carManagement.vue"
	if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key !== "__esModule"})) {console.error("named exports are not supported in *.vue files.")}
	if (Component.options.functional) {console.error("[vue-loader] carManagement.vue: functional components are not supported with templates, they should use render functions.")}
	
	/* hot reload */
	if (false) {(function () {
	  var hotAPI = require("vue-hot-reload-api")
	  hotAPI.install(require("vue"), false)
	  if (!hotAPI.compatible) return
	  module.hot.accept()
	  if (!module.hot.data) {
	    hotAPI.createRecord("data-v-1768a0e0", Component.options)
	  } else {
	    hotAPI.reload("data-v-1768a0e0", Component.options)
	  }
	})()}
	
	module.exports = Component.exports


/***/ }),

/***/ 34:
/***/ (function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(35);
	if(typeof content === 'string') content = [[module.id, content, '']];
	if(content.locals) module.exports = content.locals;
	// add the styles to the DOM
	var update = __webpack_require__(29)("79f535d8", content, false);
	// Hot Module Replacement
	if(false) {
	 // When the styles change, update the <style> tags
	 if(!content.locals) {
	   module.hot.accept("!!../../node_modules/css-loader/index.js?sourceMap!../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-1768a0e0!../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./carManagement.vue", function() {
	     var newContent = require("!!../../node_modules/css-loader/index.js?sourceMap!../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-1768a0e0!../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./carManagement.vue");
	     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
	     update(newContent);
	   });
	 }
	 // When the module is disposed, remove the <style> tags
	 module.hot.dispose(function() { update(); });
	}

/***/ }),

/***/ 35:
/***/ (function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(28)();
	// imports
	
	
	// module
	exports.push([module.id, "\n.el-dialog__body .el-form-item__content{\n\tline-height: 0;\n}\n.newCarTypeGroup{\n\theight: 60px;\n\tline-height: 60px;\n\tfont-family: \"\\5FAE\\8F6F\\96C5\\9ED1\";\n\tfont-size: 18px;\n\tcolor: #1D8CE0;\n}\n.addParameterBtn{\n\theight: 55px;\n    font-size: 14px;\n    margin: 20px 20px 20px 0;\n    text-align: center;\n    color: #58B7FF;\n    border: 1px dashed #58B7FF;\n    border-radius: 3px;\n    line-height: 55px;\n    cursor: pointer;\n}\n.addParameterBtn:hover{\n\tcolor: #20A0FF;\n    border: 1px dashed #20A0FF;\n}\n", "", {"version":3,"sources":["/./app/components/carManagement.vue?2e6766f9"],"names":[],"mappings":";AA+LA;CACA,eAAA;CACA;AACA;CACA,aAAA;CACA,kBAAA;CACA,oCAAA;CACA,gBAAA;CACA,eAAA;CACA;AACA;CACA,aAAA;IACA,gBAAA;IACA,yBAAA;IACA,mBAAA;IACA,eAAA;IACA,2BAAA;IACA,mBAAA;IACA,kBAAA;IACA,gBAAA;CACA;AACA;CACA,eAAA;IACA,2BAAA;CACA","file":"carManagement.vue","sourcesContent":["<template>\n\t<div>\n\t\t<!-- 面包屑 -->\n\t\t<div class=\"hjh-breadcrumb\">\n\t\t\t<el-breadcrumb separator=\"/\">\n\t\t\t\t<el-breadcrumb-item :to=\"{ path: '/' }\">用户中心</el-breadcrumb-item>\n\t\t\t\t<el-breadcrumb-item>机型管理</el-breadcrumb-item>\n\t\t\t\t<el-breadcrumb-item>机型管理</el-breadcrumb-item>\n\t\t\t</el-breadcrumb>\t\n\t\t</div>\n\t\t<!-- 面包屑 end -->\n\t\t<!-- 列表查询表单 -->\n\t\t<div class=\"block-white\">\n\t\t\t<el-form :inline=\"true\" :model=\"searchForm\" ref=\"searchForm\" class=\"demo-form-inline searchForm\">\n\t\t\t\t<el-form-item>\n\t\t\t\t\t<el-input v-model=\"searchForm.car_models_id\" placeholder=\"搜索代码\" style=\"width:150px;\"></el-input>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-select v-model=\"searchForm.brand_name\" filterable placeholder=\"搜索品牌\" style=\"width: 200px;\">\n\t\t\t\t\t<el-option v-for=\"item in brandlist\" :key=\"item.brand_id\" :label=\"item.brand_name\" :value=\"item.brand_name\">\n\t\t\t\t\t</el-option>\n\t\t\t\t</el-select>\n\t\t\t\t<el-form-item>\n\t\t\t\t\t<el-select v-model=\"searchForm.status\" placeholder=\"状态\" style=\"width:150px;\">\n\t\t\t\t\t\t<el-option label=\"启用\" value=\"1\"></el-option>\n\t\t\t\t\t\t<el-option label=\"禁用\" value=\"0\"></el-option>\n\t\t\t\t\t</el-select>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"类型\">\n\t\t\t\t\t<el-input v-model=\"searchForm.metadata_name\" placeholder=\"模糊匹配\" style=\"width:150px;\"></el-input>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item>\n\t\t\t\t\t<el-input v-model=\"searchForm.car_models_name\" placeholder=\"搜索型号\" style=\"width:150px;\"></el-input>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item>\n\t\t\t\t\t<el-select v-model=\"searchForm.is_noparams\" placeholder=\"参数\" style=\"width:150px;\">\n\t\t\t\t\t\t<el-option label=\"有参数\" value=\"1\"></el-option>\n\t\t\t\t\t\t<el-option label=\"无参数\" value=\"0\"></el-option>\n\t\t\t\t\t</el-select>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item>\n\t\t\t\t\t<el-select v-model=\"searchForm.app_show\" placeholder=\"APP显示\" style=\"width:150px;\">\n\t\t\t\t\t\t<el-option label=\"显示\" value=\"1\"></el-option>\n\t\t\t\t\t\t<el-option label=\"不显示\" value=\"0\"></el-option>\n\t\t\t\t\t</el-select>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item>\n\t\t\t\t\t<div style=\"width: 450px;\">\n\t\t\t\t\t<el-col :span=\"11\">\n\t\t\t\t\t\t<el-date-picker type=\"date\" placeholder=\"创建开始时间\" @change=\"initStartDateChange\" format=\"yyyy-MM-dd HH:mm:ss\"  v-model=\"init_start_date\" style=\"width:100%;\"></el-date-picker>\n\t\t\t\t\t</el-col>\n\t\t\t\t\t<el-col class=\"line\" :span=\"2\" style=\"text-align: center;\">到</el-col>\n\t\t\t\t\t<el-col :span=\"11\">\n\t\t\t\t\t\t<el-date-picker type=\"date\" placeholder=\"创建结束时间\" @change=\"initEndDateChange\" format=\"yyyy-MM-dd HH:mm:ss\" v-model=\"init_end_date\" style=\"width:100%;\"></el-date-picker>\n\t\t\t\t\t</el-col>\n\t\t\t\t\t</div>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item>\n\t\t\t\t\t<el-button class=\"searchBtn\" type=\"primary\" @click=\"submitForm('searchForm')\">搜索</el-button>\n\t\t\t\t\t<el-button @click=\"resetForm('searchForm')\">清除</el-button>\n\t\t\t\t</el-form-item>\n\t\t\t</el-form>\n\t\t</div>\n\t\t<!-- 列表查询表单 end -->\n\t\t<div class=\"block-white\">\n\t\t\t<el-button type=\"primary\" @click=\"updateCarStatusBatch(1)\" :disabled=\"!multipleSelection.length\">启用</el-button>\n\t\t\t<el-button type=\"danger\" @click=\"updateCarStatusBatch(0)\" :disabled=\"!multipleSelection.length\">禁用</el-button>\n\t\t</div>\n\t\t<div class=\"block-white\">\n\t\t\t<div class=\"selection-tip\">\n\t\t\t\t<i class=\"el-icon-warning\"></i><span>已选择 {{multipleSelection.length}} 项数据。</span>\n\t\t\t</div>\n\t\t</div>\n\t\t<!-- 会员管理列表 -->\n\t\t<div class=\"block-white\">\n\t\t\t<el-table v-loading.body=\"loading\" \n\t\t\t\t:data=\"ClientList.result_list\" \n\t\t\t\t@selection-change=\"handleSelectionChange\" \n\t\t\t\tref=\"multipleTable\"\n\t\t\t\tborder stripe style=\"width: 100%\" max-height=\"460\">\n\t\t\t\t<el-table-column fixed type=\"selection\" width=\"55\" class=\"hjhcheckbox\">\n\t\t\t\t\t\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column label=\"序号\" width=\"70\" align=\"center\"><template scope=\"scope\">{{scope.$index+1}}</template></el-table-column>\n\t\t\t\t<el-table-column prop=\"car_models_id\" label=\"代码\" width=\"200\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"brand_name\" label=\"品牌\" align=\"center\" width=\"130\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"brand_logo\" label=\"品牌logo\" width=\"110\">\n\t\t\t\t\t<template scope=\"scope\"><img :src=\"'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+scope.row.brand_logo\"></template>\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column prop=\"metadata_name\" label=\"类型\" align=\"center\" width=\"150\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"car_models_name\" label=\"型号\" align=\"center\" width=\"150\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"status\" label=\"参数\" align=\"center\" width=\"70\">\n\t\t\t\t\t<template scope=\"scope\">{{scope.row.carParamsList.length?\"有\":\"无\"}}</template>\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column prop=\"createTime\" label=\"创建日期\" align=\"center\"  width=\"180\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"create_user\" label=\"创建者\" align=\"center\"  width=\"100\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"updateTime\" label=\"最后操作日期\" align=\"center\" width=\"180\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"update_user_name\" label=\"最后操作者\" align=\"center\" width=\"120\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"status\" label=\"APP显示\" align=\"center\" width=\"120\">\n\t\t\t\t\t<template scope=\"scope\">APP{{scope.row.app_show==1?\"\":\"不\"}}显示</template>\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column prop=\"status\" label=\"状态\" align=\"center\" width=\"80\">\n\t\t\t\t\t<template scope=\"scope\">{{scope.row.status==1?\"启用\":\"禁用\"}}</template>\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column fixed=\"right\"  label=\"操作\" width=\"250\">\n\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t<el-button @click.native.prevent=\"checkView(scope.$index, ClientList.result_list)\" type=\"warning\">查看</el-button>\n\t\t\t\t\t\t<el-button @click.native.prevent=\"modify(scope.$index, ClientList.result_list)\" type=\"info\">编辑</el-button>\n\t\t\t\t\t\t<el-button @click.native.prevent=\"setStatus(scope.$index, ClientList.result_list)\" type=\"danger\" >{{scope.row.status==\"1\"?\"禁用\":\"启用\"}}</el-button>\n\t\t\t\t\t</template>\n\t\t\t\t</el-table-column>\n\t\t\t</el-table>\n\t\t</div>\n\t\t<!-- 会员管理列表 end -->\n\t\t<!-- 翻页组件 -->\n\t\t<div class=\"block-white\">\n\t\t\t<el-pagination\n\t\t      @current-change=\"handleCurrentChange\"\n\t\t      :current-page=\"page\"\n\t\t      :page-size=\"limit\"\n\t\t      layout=\"total, prev, pager, next, jumper\"\n\t\t      :total=\"ClientList.total_num\">\n\t\t    </el-pagination>\n\t\t</div>\n\t\t<!-- 翻页组件 end -->\n\t\t<!-- 查看与编辑弹窗 -->\n\t\t<el-dialog title=\"查看机型\" v-model=\"dialogFormVisible\">\n\t\t\t<div style=\"height: 320px; overflow: hidden; overflow-y: auto;\">\n\t\t\t\t<el-form :label-position=\"labelPosition\" :rules=\"rules\" :ref=\"formScopeDialog\" :label-width=\"formLabelWidth\" :model=\"formScopeDialog\">\n\t\t\t\t\t<el-form-item label=\"机型品牌\"  prop=\"brand_id\" >\n\t\t\t\t\t\t<el-select :disabled=\"!ismodify\" v-model=\"formScopeDialog.brand_id\" filterable placeholder=\"请选择机型品牌\" style=\"width: 200px;\">\n\t\t\t\t\t\t\t<el-option v-for=\"item in brandlist\" :key=\"item.brand_id\" :label=\"item.brand_name\" :value=\"item.brand_id\">\n\t\t\t\t\t\t\t</el-option>\n\t\t\t\t\t\t</el-select>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"品牌LOGO\"  prop=\"brand_logo\" >\n\t\t\t\t\t\t<el-input type=\"hidden\" v-model=\"formScopeDialog.brand_logo\"></el-input>\n\t\t\t\t\t\t<div style=\" overflow: hidden; line-height: 30px;\">\n\t\t\t\t\t\t\t<img width=\"80\" height=\"30\" style=\"display: block; float: left; margin-right: 10px;\" :src=\"'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+formScopeDialog.brand_logo\">\n\t\t\t\t\t\t\t<span>{{formScopeDialog.brand_logo}}</span>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"类型\"  prop=\"metadata_id\" >\n\t\t\t\t\t\t<el-select :disabled=\"!ismodify\" v-model=\"formScopeDialog.metadata_id\" placeholder=\"请选择类型\" style=\"width: 200px;\">\n\t\t\t\t\t\t\t<el-option v-for=\"item in metalist\" :key=\"item.metadata_id\" :label=\"item.metadata_name\" :value=\"item.metadata_id\">\n\t\t\t\t\t\t\t</el-option>\n\t\t\t\t\t\t</el-select>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"型号\"  prop=\"car_models_name\" >\n\t\t\t\t\t\t<el-input :disabled=\"!ismodify\" v-model=\"formScopeDialog.car_models_name\" :minlength=\"2\" :maxlength=\"10\" placeholder=\"请输入型号（2-10字数限制）\" style=\"width: 200px;\"></el-input>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"\" >\n\t\t\t\t\t\t<el-checkbox :disabled=\"!ismodify\" v-model=\"checked\">APP显示</el-checkbox>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t\n\t\t\t\t</el-form>\n\t\t\t\t<div class=\"newCarTypeGroup\">参数设置</div>\n\t\t\t\t<el-form label-width=\"100px\" :model=\"addParamForm\" :ref=\"addParamForm\" class=\"demo-form-inline\" style=\"width: 660px;\">\n\t\t\t\t\t<el-row v-for=\"(parameter,index) in addParamForm.carParameters\">\n\t\t\t\t\t\t<el-col :span=\"11\">\n\t\t\t\t\t\t\t<el-form-item label=\"型号\" :prop=\"'carParameters.'+index+'.car_params_name'\" :rules=\"[\n\t\t\t\t\t      { required: true, message: '请输入型号', trigger: 'blur' },\n\t\t\t\t\t      { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }\n\t\t\t\t\t    ]\">\n\t\t\t\t\t\t\t\t<el-input :disabled=\"!ismodify\" v-model=\"parameter.car_params_name\" :minlength=\"2\" :maxlength=\"50\" placeholder=\"输入（2-50字数限制）\" style=\"width: 200px;\"></el-input>\n\t\t\t\t\t\t\t</el-form-item>\n\t\t\t\t\t\t</el-col>\n\t\t\t\t\t\t<el-col :span=\"10\">\n\t\t\t\t\t\t\t<el-form-item label=\"值\"  label-width=\"40px\">\n\t\t\t\t\t\t\t\t<el-input :disabled=\"!ismodify\" v-model=\"parameter.car_params_value\" :minlength=\"2\" :maxlength=\"50\" placeholder=\"输入（2-50字数限制）\" style=\"width: 200px;\"></el-input>\n\t\t\t\t\t\t\t</el-form-item>\n\t\t\t\t\t\t</el-col>\n\t\t\t\t\t\t<el-col :span=\"2\" v-if=\"ismodify\">\n\t\t\t\t\t\t\t<el-button @click=\"deleteParam(parameter)\" :plain=\"true\" type=\"danger\">删除</el-button>\n\t\t\t\t\t\t</el-col>\n\t\t\t\t\t</el-row>\n\t\t\t\t\t<div class=\"addParameterBtn\" @click=\"addCarTypeParameter\" v-if=\"ismodify\"><i class=\"el-icon-plus\"></i> 添加参数</div>\n\t\t\t\t</el-form>\n\t\t\t</div>\n\t\t\t<div slot=\"footer\" class=\"dialog-footer\" style=\"text-align: center;\" v-if=\"!ismodify\">\n\t\t\t\t<el-button type=\"primary\" @click=\"dialogFormVisible = false\">确 定</el-button>\n\t\t\t</div>\n\t\t\t<div slot=\"footer\" class=\"dialog-footer\" style=\"text-align: center;\" v-if=\"ismodify\">\n\t\t\t\t<el-button type=\"primary\" @click=\"addCarModel(1)\">保存并启用</el-button>\n\t\t\t\t<el-button type=\"primary\" @click=\"addCarModel(0)\">保存并禁用</el-button>\n\t\t\t\t<el-button @click=\"dialogFormVisible = false\">关 闭</el-button>\n\t\t\t</div>\n\t\t</el-dialog>\n\t\t<!-- 查看与编辑弹窗 end -->\n\t</div>\n</template>\n<style>\n\t.el-dialog__body .el-form-item__content{\n\t\tline-height: 0;\n\t}\n\t.newCarTypeGroup{\n\t\theight: 60px;\n\t\tline-height: 60px;\n\t\tfont-family: \"微软雅黑\";\n\t\tfont-size: 18px;\n\t\tcolor: #1D8CE0;\n\t}\n\t.addParameterBtn{\n\t\theight: 55px;\n\t    font-size: 14px;\n\t    margin: 20px 20px 20px 0;\n\t    text-align: center;\n\t    color: #58B7FF;\n\t    border: 1px dashed #58B7FF;\n\t    border-radius: 3px;\n\t    line-height: 55px;\n\t    cursor: pointer;\n\t}\n\t.addParameterBtn:hover{\n\t\tcolor: #20A0FF;\n\t    border: 1px dashed #20A0FF;\n\t}\n</style>\n<script>\n\timport Common from './../js/common';\n\texport default {\n\t\tdata() {\n\t\t\treturn {\n\t\t\t\tsearchForm: this.getDefaultData(),\t//查询表单\n\t\t\t\tpage : 1,\t\t\t\t\t\t\t//列表初始当前页码\n\t\t\t\tlimit : 10,\t\t\t\t\t\t\t//每页列表数量\n\t\t\t\tinit_start_date : '',\n\t\t\t\tinit_end_date : '',\n\t\t\t\tbrandlist : [],\t\t\t\t\t\t//品牌列表\n\t\t\t\tmetalist : [],\n\t\t\t\tmultipleSelection: [],\n\t\t\t\tClientList : {},\t\t\t\t\t\t//会员列表数据缓存\n\t\t\t\tformScopeDialog:{},\t\t\t\t\t//查看或编辑时对应的数据缓存\n\t\t\t\tdialogFormVisible:false,\t\t\t\t//查看与编辑弹窗显示状态 false为隐藏 true为显示\n\t\t\t\tformLabelWidth: '110px',\t\t\t\t//弹窗中的表单label的宽度\n\t\t\t\tactiveName:\"first\",\t\t\t\t\t//弹窗中tabs选项卡初始选中值\n\t\t\t\tlabelPosition : \"right\",\t\t\t\t//弹窗中表单label文字的对齐方式\n\t\t\t\tismodify:false,\t\t\t\t\t\t//是否为编辑状态\n\t\t\t\tloading:false,\t\t\t\t\t\t//会员列表加载状态 true为加载中 false为加载完毕\n\t\t\t\ttimers:0,\n\t\t\t\t//验证规则\n\t\t\t\trules : {\n\t\t\t\t\t//机型品牌的验证规则\n\t\t\t\t\tbrand_id : [\n\t\t\t\t\t\t{required: true, message: '请选择机型品牌', trigger: 'blur,change'}\n\t\t\t\t\t],\n\t\t\t\t\t//类型验证规则\n\t\t\t\t\tmetadata_id : [\n\t\t\t\t\t\t{required: true, message: '请选择类型', trigger: 'blur,change'}\n\t\t\t\t\t],\n\t\t\t\t\t//型号的验证规则\n\t\t\t\t\tcar_models_name : [\n\t\t\t\t\t\t{required: true, message: '请输入型号', trigger: 'blur'},\n\t\t\t\t\t\t{ min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }\n\t\t\t\t\t],\n\t\t\t\t\tbrand_logo : [\n\t\t\t\t\t\t{required: true, message: '请选择机型品牌', trigger: 'blur,change'}\n\t\t\t\t\t]\n\t\t\t\t},\n\t\t\t\t//参数设置表单\n\t\t\t\taddParamForm: {\n\t\t        \t\tcarParameters : []\n\t\t        },\n\t\t        //是否在APP显示\n\t\t\t\tchecked : false\n\t\t\t}\n\t\t},\n\t\tcreated(){\n\t\t\tvar that = this;\n\t\t\t//获取品牌列表\n\t\t\tthat.$http.post(\"./../carbrand/queryforweb\",{limit:10000,page:1,status:1}).then(response =>{\n\t\t\t\tvar data = response.data;\n\t\t\t\tif(data.error_no == 0){\n\t\t\t\t\tthat.$data.brandlist = data.result_list;\n\t\t\t\t}\n\t\t\t});\n\t\t\t//获取类型列表\n\t\t\tthat.$http.post(\"./../queryMetadata\",{limit:10000,page:1,status:1,type:1}).then(response =>{\n\t\t\t\tvar data = response.data;\n\t\t\t\tif(data.error_no == 0){\n\t\t\t\t\tthat.$data.metalist = data.result_list;\n\t\t\t\t}\n\t\t\t});\n\t\t},\n\t\tmounted(){\n\t\t\tdocument.title = \"后台管理系统-机型管理\";\n\t\t\tthis.getListBySearchData();\n\t\t},\n\t\tmethods: {\n\t\t\t//获取查询表单初始数据\n\t\t\tgetDefaultData() {\n\t\t\t\treturn {\n\t\t\t\t\taccess_token: localStorage.access_token,\n\t\t\t\t\tstatus : \"\",\n\t\t\t\t\tapp_show : \"\",\n\t\t\t\t\tcar_models_id : '',\t\t//搜索代码\n\t\t\t\t\tbrand_name : '',\t\t\t//搜索品牌\n\t\t\t\t\tcar_models_name : '',\t//搜索型号\n\t\t\t\t\tis_noparams : '',\t\t//有无参数\n\t\t\t\t\tmetadata_name : ''\t\t//搜索类型\n\t\t\t\t}\n\t\t\t},\n\t\t\t//选择注册开始时间\n\t\t\tinitStartDateChange(val){\n\t\t\t\tif(val)\n\t\t\t\t\tthis.init_start_date = val.replace(\"00:00:00\",Common.getHMSforNow());\n\t\t\t},\n\t\t\t//选择注册结束时间\n\t\t\tinitEndDateChange(val){\n\t\t\t\tif(val)\n\t\t\t\t\tthis.init_end_date = val.replace(\"00:00:00\",Common.getHMSforNow());\n\t\t\t},\n\t\t\t//多选状态发生改变\n\t\t\thandleSelectionChange(val) {\n\t\t\t\tthis.multipleSelection = val;\n\t\t\t\t\n\t\t\t},\n\t\t\t//删除参数行\n\t\t\tdeleteParam(item){\n\t\t\t\tvar index = this.addParamForm.carParameters.indexOf(item);\n\t\t        if (index !== -1) {\n\t\t          this.addParamForm.carParameters.splice(index, 1)\n\t\t        }\n\t\t\t},\n\t\t\t//多选 启用或禁用 status = 1 启用 0禁用\n\t\t\tupdateCarStatusBatch(status){\n\t\t\t\tvar that = this;\n\t\t\t\tthis.$confirm('确定要'+ (status==1 ? '启用':'禁用') +'该机型吗？', '提示', {\n\t\t\t\t\tconfirmButtonText: '确定',\n\t\t\t\t\tcancelButtonText: '取消',\n\t\t\t\t\ttype: 'warning'\n\t\t\t\t}).then(() => {\n\t\t\t\t\tvar car_models_ids = that.multipleSelection.map(item=>{ return item.car_models_id; }).join(\",\")\n\t\t\t\t\tthat.ajaxUpdateCarStatusBatch(status,car_models_ids,function(){\n\t\t\t\t\t\tthat.multipleSelection = [];\n\t\t\t\t \t\tthat.$refs.multipleTable.clearSelection();\n\t\t\t\t\t});\n\t\t\t\t}).catch(() => {         \n\t\t\t\t});\n\t\t\t},\n\t\t\t//状态变更提交\n\t\t\tajaxUpdateCarStatusBatch(status,car_models_ids,handle){\n\t\t\t\tvar that = this;\n\t\t\t\tvar param = {\n\t\t\t\t\t\taccess_token : that.searchForm.access_token,\n\t\t\t\t\t\tstatus : status,\n\t\t\t\t\t\tcar_models_ids : car_models_ids\n\t\t\t\t\t};\n\t\t\t\tthat.$http.post(\"./../updateCarStatusBatch\",param).then(response=>{\n\t\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\t\tthat.$message({\n\t\t\t\t \t\t\ttype: jsondata.error_no == 0 ? \"success\" : \"error\",\n\t\t\t\t \t\t\tmessage: jsondata.error_no == 0 ? \"操作成功\" : jsondata.error_info\n\t\t\t\t \t\t});\n\t\t\t\t \t\tif(jsondata.error_no == 0){\n\t\t\t\t \t\t\tif(typeof handle == \"function\"){\n\t\t\t\t \t\t\t\thandle();\n\t\t\t\t \t\t\t}\n\t\t\t\t \t\t\tthat.getClientList();\n\t\t\t\t \t\t}\n\t\t\t\t\t})\n\t\t\t},\n\t\t\t//添加参数事件\n\t\t\taddCarTypeParameter(){\n\t\t\t\tthis.$data.addParamForm.carParameters.push({\n\t\t\t\t\tcar_params_name : '',\n\t\t\t\t\tcar_params_value : ''\n\t\t\t\t});\n\t\t\t},\n\t\t\t//操作－查询\n\t\t\tsubmitForm() {\n\t\t\t\tthis.$data.page = 1;\n\t\t\t\tthis.getListBySearchData();\n\t\t\t},\n\t\t\t//操作－重置\n\t\t\tresetForm(formName) {\n        \t\t\tthis.$refs[formName].resetFields();\n        \t\t\tthis.$data.searchForm = this.getDefaultData();\n        \t\t\tthis.init_start_date = \"\";\n        \t\t\tthis.init_end_date = \"\";\n        \t\t\tthis.submitForm();\n\t\t\t},\n\t\t\t//操作－翻页\n\t\t\thandleCurrentChange(val){\n\t\t\t\tthis.$data.page = val;\n\t\t\t\tthis.searchDataCache.page = val;\n\t\t\t\tthis.getClientList();\n\t\t\t},\n\t\t\t//操作－查看\n\t\t\tcheckView(index,list){\n\t\t\t\tthis.$data.dialogFormVisible = true;\n\t\t\t\tthis.$data.ismodify = false;\n\t\t\t\tthis.$data.formScopeDialog = list[index];\n\t\t\t\tthis.$data.checked = this.$data.formScopeDialog.app_show==1;\n\t\t\t},\n\t\t\t//操作－编辑\n\t\t\tmodify(index,list){\n\t\t\t\tthis.$data.dialogFormVisible = true;\n\t\t\t\tthis.$data.ismodify = true;\n\t\t\t\tthis.$data.formScopeDialog = Common.simpleClone(list[index]);\n\t\t\t\tthis.$data.checked = this.$data.formScopeDialog.app_show==1;\n\t\t\t\tvar that = this;\n\t\t\t\tsetTimeout(function(){\n\t\t\t\t\tthat.$refs[that.formScopeDialog].validate();\n\t\t\t\t},50);\n\t\t\t},\n\t\t\t//保存\n\t\t\taddCarModel(status){\n\t\t\t\tvar that = this,form = that.formScopeDialog;\n\t\t\t\tvar brand = this.$data.brandlist.filter(function(item){\n\t\t\t\t\treturn item.brand_id == form.brand_id;\n\t\t\t\t})[0];\n\t\t\t\tvar meta = this.$data.metalist.filter(function(item){\n\t\t\t\t\treturn item.metadata_id == form.metadata_id;\n\t\t\t\t})[0];\n\t\t\t\tvar param = {\n\t\t\t\t\t\taccess_token : that.searchForm.access_token,\n\t\t\t\t\t\tstatus : status,\n\t\t\t\t\t\tapp_show : that.checked ? 1 : 0,\n\t\t\t\t\t\tbrand_id : form.brand_id,\n\t\t\t\t\t\tbrand_name : brand.brand_name,\n\t\t\t\t\t\tcar_models_id : form.car_models_id,\n\t\t\t\t\t\tcar_models_name : form.car_models_name,\n\t\t\t\t\t\tmetadata_id : form.metadata_id,\n\t\t\t\t\t\tmetadata_name : meta.metadata_name,\n\t\t\t\t\t\tcarParamsList : that.addParamForm.carParameters\n\t\t\t\t};\n\t\t\t\tthat.$refs[that.formScopeDialog].validate((valid) => {\r\n\t\t\t\t\tif (valid) {\r\n\t\t\t\t\t\tthat.$refs[that.addParamForm].validate((valid1) => {\r\n\t\t\t\t\t\t\tif (valid1) {\n\t\t\t\t\t\t\t\tthis.$http.post('./../updateCarModel',param).then(response => {\n\t\t\t\t\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\t\t\t\t\t\tthat.$message({\n\t\t\t\t\t\t\t\t \t\t\ttype: jsondata.error_no == 0 ? \"success\" : \"error\",\n\t\t\t\t\t\t\t\t \t\t\tmessage: jsondata.error_no == 0 ? \"操作成功\" : jsondata.error_info\n\t\t\t\t\t\t\t\t \t\t});\n\t\t\t\t\t\t\t\t \t\tif(jsondata.error_no == 0){\n\t\t\t\t\t\t\t\t \t\t\tthat.getClientList();\n\t\t\t\t\t\t\t\t \t\t}\n\t\t\t\t\t\t\t\t \t\tthat.$data.dialogFormVisible = false;\n\t\t\t\t\t\t\t\t});\n\t\t\t\t\t\t\t}\r\n\t\t\t\t\t\t})\r\n\t\t\t\t\t}\r\n\t\t\t\t})\n\t\t\t\t\n\t\t\t},\n\t\t\t//操作－启用或禁用\n\t\t\tsetStatus(index,list){\n\t\t\t\tvar status = list[index].status==1?0:1,\n\t\t\t\t\tid = list[index].car_models_id,\n\t\t\t\t\tthat = this;\n\t\t\t\t this.$confirm(\"确定要\" + (status==1?\"启用\":\"禁用\") + \"该机型吗？\", '', {\n\t\t\t\t \tconfirmButtonText: '确定',\n\t\t\t\t \tcancelButtonText: '取消',\n\t\t\t\t \ttype: 'warning'\n\t\t\t\t }).then(() => {\n\t\t\t\t \tthat.ajaxUpdateCarStatusBatch(status,id);\n\t\t\t\t });\n\t\t\t},\n\t\t\tcheckLogout(error_no){\n\t\t\t\tif(error_no==88880020){\n\t\t\t\t\tlocation.href=\"/login\";\n\t\t\t\t}\n\t\t\t},\n\t\t\t//重置时间戳格式\n\t\t\treFormatDate(date){\n\t\t\t\tvar _date = new Date(date);\n\t\t\t\tif(!date || !_date.getTime()){return \"\";}\n\t\t\t\tvar y = _date.getFullYear(),\n\t\t\t\t\tM = _date.getMonth()+1,\n\t\t\t\t\td = _date.getDate(),\n\t\t\t\t\tH = _date.getHours(),\n\t\t\t\t\tm = _date.getMinutes(),\n\t\t\t\t\ts = _date.getSeconds();\n\t\t\t\tM = M > 9 ? M : (\"0\"+M);\n\t\t\t\td = d > 9 ? d : (\"0\"+d);\n\t\t\t\tH = H > 9 ? H : (\"0\"+H);\n\t\t\t\tm = m > 9 ? m : (\"0\"+m);\n\t\t\t\ts = s > 9 ? s : (\"0\"+s);\n\t\t\t\treturn y+M+d+H+m+s;\n\t\t\t},\n\t\t\tgetListBySearchData(){\n\t\t\t\tvar param = this.searchForm;\n\t\t \t\tparam.page = this.$data.page;\n\t\t \t\tparam.limit = this.$data.limit;\n\t\t \t\tparam.start_date = this.reFormatDate(this.$data.init_start_date);\n\t\t \t\tparam.end_date = this.reFormatDate(this.$data.init_end_date);\n\t\t\t\tthis.searchDataCache = Common.simpleClone(param);\n\t\t\t\tthis.getClientList();\n\t\t\t},\n\t\t \t//获取会员列表\n\t\t\tgetClientList() {\n\t\t\t\tif(this.loading) return;\n\t\t\t\tvar that = this;\n\t \t\t\tthat.$data.loading = true;\n\t \t\t\tthis.$http.post('./../queryCarModelList',this.searchDataCache).then(response => {\n\t\t\t\t\tvar jsonData=response.data;\n\t\t\t\t\tthat.checkLogout(jsonData.error_no);\n\t\t\t\t\tif(jsonData.error_no==0){\n\t\t\t\t\t\tjsonData.result_list = jsonData.result_list.map(function(item){\n\t\t\t\t\t\t\titem.createTime = Common.formatDateConcat(item.create_date,item.create_time);\n\t\t\t\t\t\t\titem.updateTime = Common.formatDateConcat(item.update_date,item.update_time);\n\t\t\t\t\t\t\treturn item;\n\t\t\t\t\t\t});\n\t\t\t\t\t\tthat.$data.ClientList = jsonData || {};\n\t\t\t\t\t}else{\n\t\t\t\t\t\tthat.$message({\n\t\t\t\t\t\t\ttype: \"warning\",\n\t\t\t\t\t\t\tmessage: jsonData.error_info\n\t\t\t\t\t\t});\n\t\t\t\t\t}\n\t\t\t\t\tthat.$data.loading = false;\n\t\t\t\t});\n\t\t\t}\n\t\t}\n\t}\n</script>\n"],"sourceRoot":"webpack://"}]);
	
	// exports


/***/ }),

/***/ 36:
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
				page: 1, //列表初始当前页码
				limit: 10, //每页列表数量
				init_start_date: '',
				init_end_date: '',
				brandlist: [], //品牌列表
				metalist: [],
				multipleSelection: [],
				ClientList: {}, //会员列表数据缓存
				formScopeDialog: {}, //查看或编辑时对应的数据缓存
				dialogFormVisible: false, //查看与编辑弹窗显示状态 false为隐藏 true为显示
				formLabelWidth: '110px', //弹窗中的表单label的宽度
				activeName: "first", //弹窗中tabs选项卡初始选中值
				labelPosition: "right", //弹窗中表单label文字的对齐方式
				ismodify: false, //是否为编辑状态
				loading: false, //会员列表加载状态 true为加载中 false为加载完毕
				timers: 0,
				//验证规则
				rules: {
					//机型品牌的验证规则
					brand_id: [{ required: true, message: '请选择机型品牌', trigger: 'blur,change' }],
					//类型验证规则
					metadata_id: [{ required: true, message: '请选择类型', trigger: 'blur,change' }],
					//型号的验证规则
					car_models_name: [{ required: true, message: '请输入型号', trigger: 'blur' }, { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }],
					brand_logo: [{ required: true, message: '请选择机型品牌', trigger: 'blur,change' }]
				},
				//参数设置表单
				addParamForm: {
					carParameters: []
				},
				//是否在APP显示
				checked: false
			};
		},
		created: function created() {
			var that = this;
			//获取品牌列表
			that.$http.post("./../carbrand/queryforweb", { limit: 10000, page: 1, status: 1 }).then(function (response) {
				var data = response.data;
				if (data.error_no == 0) {
					that.$data.brandlist = data.result_list;
				}
			});
			//获取类型列表
			that.$http.post("./../queryMetadata", { limit: 10000, page: 1, status: 1, type: 1 }).then(function (response) {
				var data = response.data;
				if (data.error_no == 0) {
					that.$data.metalist = data.result_list;
				}
			});
		},
		mounted: function mounted() {
			document.title = "后台管理系统-机型管理";
			this.getListBySearchData();
		},
	
		methods: {
			//获取查询表单初始数据
			getDefaultData: function getDefaultData() {
				return {
					access_token: localStorage.access_token,
					status: "",
					app_show: "",
					car_models_id: '', //搜索代码
					brand_name: '', //搜索品牌
					car_models_name: '', //搜索型号
					is_noparams: '', //有无参数
					metadata_name: '' //搜索类型
				};
			},
	
			//选择注册开始时间
			initStartDateChange: function initStartDateChange(val) {
				if (val) this.init_start_date = val.replace("00:00:00", _common2.default.getHMSforNow());
			},
	
			//选择注册结束时间
			initEndDateChange: function initEndDateChange(val) {
				if (val) this.init_end_date = val.replace("00:00:00", _common2.default.getHMSforNow());
			},
	
			//多选状态发生改变
			handleSelectionChange: function handleSelectionChange(val) {
				this.multipleSelection = val;
			},
	
			//删除参数行
			deleteParam: function deleteParam(item) {
				var index = this.addParamForm.carParameters.indexOf(item);
				if (index !== -1) {
					this.addParamForm.carParameters.splice(index, 1);
				}
			},
	
			//多选 启用或禁用 status = 1 启用 0禁用
			updateCarStatusBatch: function updateCarStatusBatch(status) {
				var that = this;
				this.$confirm('确定要' + (status == 1 ? '启用' : '禁用') + '该机型吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(function () {
					var car_models_ids = that.multipleSelection.map(function (item) {
						return item.car_models_id;
					}).join(",");
					that.ajaxUpdateCarStatusBatch(status, car_models_ids, function () {
						that.multipleSelection = [];
						that.$refs.multipleTable.clearSelection();
					});
				}).catch(function () {});
			},
	
			//状态变更提交
			ajaxUpdateCarStatusBatch: function ajaxUpdateCarStatusBatch(status, car_models_ids, handle) {
				var that = this;
				var param = {
					access_token: that.searchForm.access_token,
					status: status,
					car_models_ids: car_models_ids
				};
				that.$http.post("./../updateCarStatusBatch", param).then(function (response) {
					var jsondata = response.data;
					that.$message({
						type: jsondata.error_no == 0 ? "success" : "error",
						message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
					});
					if (jsondata.error_no == 0) {
						if (typeof handle == "function") {
							handle();
						}
						that.getClientList();
					}
				});
			},
	
			//添加参数事件
			addCarTypeParameter: function addCarTypeParameter() {
				this.$data.addParamForm.carParameters.push({
					car_params_name: '',
					car_params_value: ''
				});
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
				this.init_start_date = "";
				this.init_end_date = "";
				this.submitForm();
			},
	
			//操作－翻页
			handleCurrentChange: function handleCurrentChange(val) {
				this.$data.page = val;
				this.searchDataCache.page = val;
				this.getClientList();
			},
	
			//操作－查看
			checkView: function checkView(index, list) {
				this.$data.dialogFormVisible = true;
				this.$data.ismodify = false;
				this.$data.formScopeDialog = list[index];
				this.$data.checked = this.$data.formScopeDialog.app_show == 1;
			},
	
			//操作－编辑
			modify: function modify(index, list) {
				this.$data.dialogFormVisible = true;
				this.$data.ismodify = true;
				this.$data.formScopeDialog = _common2.default.simpleClone(list[index]);
				this.$data.checked = this.$data.formScopeDialog.app_show == 1;
				var that = this;
				setTimeout(function () {
					that.$refs[that.formScopeDialog].validate();
				}, 50);
			},
	
			//保存
			addCarModel: function addCarModel(status) {
				var _this = this;
	
				var that = this,
				    form = that.formScopeDialog;
				var brand = this.$data.brandlist.filter(function (item) {
					return item.brand_id == form.brand_id;
				})[0];
				var meta = this.$data.metalist.filter(function (item) {
					return item.metadata_id == form.metadata_id;
				})[0];
				var param = {
					access_token: that.searchForm.access_token,
					status: status,
					app_show: that.checked ? 1 : 0,
					brand_id: form.brand_id,
					brand_name: brand.brand_name,
					car_models_id: form.car_models_id,
					car_models_name: form.car_models_name,
					metadata_id: form.metadata_id,
					metadata_name: meta.metadata_name,
					carParamsList: that.addParamForm.carParameters
				};
				that.$refs[that.formScopeDialog].validate(function (valid) {
					if (valid) {
						that.$refs[that.addParamForm].validate(function (valid1) {
							if (valid1) {
								_this.$http.post('./../updateCarModel', param).then(function (response) {
									var jsondata = response.data;
									that.$message({
										type: jsondata.error_no == 0 ? "success" : "error",
										message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
									});
									if (jsondata.error_no == 0) {
										that.getClientList();
									}
									that.$data.dialogFormVisible = false;
								});
							}
						});
					}
				});
			},
	
			//操作－启用或禁用
			setStatus: function setStatus(index, list) {
				var status = list[index].status == 1 ? 0 : 1,
				    id = list[index].car_models_id,
				    that = this;
				this.$confirm("确定要" + (status == 1 ? "启用" : "禁用") + "该机型吗？", '', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(function () {
					that.ajaxUpdateCarStatusBatch(status, id);
				});
			},
			checkLogout: function checkLogout(error_no) {
				if (error_no == 88880020) {
					location.href = "/login";
				}
			},
	
			//重置时间戳格式
			reFormatDate: function reFormatDate(date) {
				var _date = new Date(date);
				if (!date || !_date.getTime()) {
					return "";
				}
				var y = _date.getFullYear(),
				    M = _date.getMonth() + 1,
				    d = _date.getDate(),
				    H = _date.getHours(),
				    m = _date.getMinutes(),
				    s = _date.getSeconds();
				M = M > 9 ? M : "0" + M;
				d = d > 9 ? d : "0" + d;
				H = H > 9 ? H : "0" + H;
				m = m > 9 ? m : "0" + m;
				s = s > 9 ? s : "0" + s;
				return y + M + d + H + m + s;
			},
			getListBySearchData: function getListBySearchData() {
				var param = this.searchForm;
				param.page = this.$data.page;
				param.limit = this.$data.limit;
				param.start_date = this.reFormatDate(this.$data.init_start_date);
				param.end_date = this.reFormatDate(this.$data.init_end_date);
				this.searchDataCache = _common2.default.simpleClone(param);
				this.getClientList();
			},
	
			//获取会员列表
			getClientList: function getClientList() {
				if (this.loading) return;
				var that = this;
				that.$data.loading = true;
				this.$http.post('./../queryCarModelList', this.searchDataCache).then(function (response) {
					var jsonData = response.data;
					that.checkLogout(jsonData.error_no);
					if (jsonData.error_no == 0) {
						jsonData.result_list = jsonData.result_list.map(function (item) {
							item.createTime = _common2.default.formatDateConcat(item.create_date, item.create_time);
							item.updateTime = _common2.default.formatDateConcat(item.update_date, item.update_time);
							return item;
						});
						that.$data.ClientList = jsonData || {};
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
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
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

/***/ 37:
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
	  }, [_vm._v("用户中心")]), _vm._v(" "), _c('el-breadcrumb-item', [_vm._v("机型管理")]), _vm._v(" "), _c('el-breadcrumb-item', [_vm._v("机型管理")])], 1)], 1), _vm._v(" "), _c('div', {
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
	      "placeholder": "搜索代码"
	    },
	    model: {
	      value: (_vm.searchForm.car_models_id),
	      callback: function($$v) {
	        _vm.searchForm.car_models_id = $$v
	      },
	      expression: "searchForm.car_models_id"
	    }
	  })], 1), _vm._v(" "), _c('el-select', {
	    staticStyle: {
	      "width": "200px"
	    },
	    attrs: {
	      "filterable": "",
	      "placeholder": "搜索品牌"
	    },
	    model: {
	      value: (_vm.searchForm.brand_name),
	      callback: function($$v) {
	        _vm.searchForm.brand_name = $$v
	      },
	      expression: "searchForm.brand_name"
	    }
	  }, _vm._l((_vm.brandlist), function(item) {
	    return _c('el-option', {
	      key: item.brand_id,
	      attrs: {
	        "label": item.brand_name,
	        "value": item.brand_name
	      }
	    })
	  })), _vm._v(" "), _c('el-form-item', [_c('el-select', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "状态"
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
	      "label": "类型"
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "模糊匹配"
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
	      "placeholder": "搜索型号"
	    },
	    model: {
	      value: (_vm.searchForm.car_models_name),
	      callback: function($$v) {
	        _vm.searchForm.car_models_name = $$v
	      },
	      expression: "searchForm.car_models_name"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', [_c('el-select', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "参数"
	    },
	    model: {
	      value: (_vm.searchForm.is_noparams),
	      callback: function($$v) {
	        _vm.searchForm.is_noparams = $$v
	      },
	      expression: "searchForm.is_noparams"
	    }
	  }, [_c('el-option', {
	    attrs: {
	      "label": "有参数",
	      "value": "1"
	    }
	  }), _vm._v(" "), _c('el-option', {
	    attrs: {
	      "label": "无参数",
	      "value": "0"
	    }
	  })], 1)], 1), _vm._v(" "), _c('el-form-item', [_c('el-select', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "APP显示"
	    },
	    model: {
	      value: (_vm.searchForm.app_show),
	      callback: function($$v) {
	        _vm.searchForm.app_show = $$v
	      },
	      expression: "searchForm.app_show"
	    }
	  }, [_c('el-option', {
	    attrs: {
	      "label": "显示",
	      "value": "1"
	    }
	  }), _vm._v(" "), _c('el-option', {
	    attrs: {
	      "label": "不显示",
	      "value": "0"
	    }
	  })], 1)], 1), _vm._v(" "), _c('el-form-item', [_c('div', {
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
	      "placeholder": "创建开始时间",
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
	      "placeholder": "创建结束时间",
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
	  }, [_vm._v("清除")])], 1)], 1)], 1), _vm._v(" "), _c('div', {
	    staticClass: "block-white"
	  }, [_c('el-button', {
	    attrs: {
	      "type": "primary",
	      "disabled": !_vm.multipleSelection.length
	    },
	    on: {
	      "click": function($event) {
	        _vm.updateCarStatusBatch(1)
	      }
	    }
	  }, [_vm._v("启用")]), _vm._v(" "), _c('el-button', {
	    attrs: {
	      "type": "danger",
	      "disabled": !_vm.multipleSelection.length
	    },
	    on: {
	      "click": function($event) {
	        _vm.updateCarStatusBatch(0)
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
	      "data": _vm.ClientList.result_list,
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
	      "prop": "car_models_id",
	      "label": "代码",
	      "width": "200"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "brand_name",
	      "label": "品牌",
	      "align": "center",
	      "width": "130"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "brand_logo",
	      "label": "品牌logo",
	      "width": "110"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_c('img', {
	          attrs: {
	            "src": 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/' + scope.row.brand_logo
	          }
	        })]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "metadata_name",
	      "label": "类型",
	      "align": "center",
	      "width": "150"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "car_models_name",
	      "label": "型号",
	      "align": "center",
	      "width": "150"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "status",
	      "label": "参数",
	      "align": "center",
	      "width": "70"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_vm._v(_vm._s(scope.row.carParamsList.length ? "有" : "无"))]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "createTime",
	      "label": "创建日期",
	      "align": "center",
	      "width": "180"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "create_user",
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
	      "prop": "update_user_name",
	      "label": "最后操作者",
	      "align": "center",
	      "width": "120"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "status",
	      "label": "APP显示",
	      "align": "center",
	      "width": "120"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_vm._v("APP" + _vm._s(scope.row.app_show == 1 ? "" : "不") + "显示")]
	      }
	    }])
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
	              _vm.checkView(scope.$index, _vm.ClientList.result_list)
	            }
	          }
	        }, [_vm._v("查看")]), _vm._v(" "), _c('el-button', {
	          attrs: {
	            "type": "info"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.modify(scope.$index, _vm.ClientList.result_list)
	            }
	          }
	        }, [_vm._v("编辑")]), _vm._v(" "), _c('el-button', {
	          attrs: {
	            "type": "danger"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.setStatus(scope.$index, _vm.ClientList.result_list)
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
	      "total": _vm.ClientList.total_num
	    },
	    on: {
	      "current-change": _vm.handleCurrentChange
	    }
	  })], 1), _vm._v(" "), _c('el-dialog', {
	    attrs: {
	      "title": "查看机型"
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
	      "rules": _vm.rules,
	      "label-width": _vm.formLabelWidth,
	      "model": _vm.formScopeDialog
	    }
	  }, [_c('el-form-item', {
	    attrs: {
	      "label": "机型品牌",
	      "prop": "brand_id"
	    }
	  }, [_c('el-select', {
	    staticStyle: {
	      "width": "200px"
	    },
	    attrs: {
	      "disabled": !_vm.ismodify,
	      "filterable": "",
	      "placeholder": "请选择机型品牌"
	    },
	    model: {
	      value: (_vm.formScopeDialog.brand_id),
	      callback: function($$v) {
	        _vm.formScopeDialog.brand_id = $$v
	      },
	      expression: "formScopeDialog.brand_id"
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
	      "label": "品牌LOGO",
	      "prop": "brand_logo"
	    }
	  }, [_c('el-input', {
	    attrs: {
	      "type": "hidden"
	    },
	    model: {
	      value: (_vm.formScopeDialog.brand_logo),
	      callback: function($$v) {
	        _vm.formScopeDialog.brand_logo = $$v
	      },
	      expression: "formScopeDialog.brand_logo"
	    }
	  }), _vm._v(" "), _c('div', {
	    staticStyle: {
	      "overflow": "hidden",
	      "line-height": "30px"
	    }
	  }, [_c('img', {
	    staticStyle: {
	      "display": "block",
	      "float": "left",
	      "margin-right": "10px"
	    },
	    attrs: {
	      "width": "80",
	      "height": "30",
	      "src": 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/' + _vm.formScopeDialog.brand_logo
	    }
	  }), _vm._v(" "), _c('span', [_vm._v(_vm._s(_vm.formScopeDialog.brand_logo))])])], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "类型",
	      "prop": "metadata_id"
	    }
	  }, [_c('el-select', {
	    staticStyle: {
	      "width": "200px"
	    },
	    attrs: {
	      "disabled": !_vm.ismodify,
	      "placeholder": "请选择类型"
	    },
	    model: {
	      value: (_vm.formScopeDialog.metadata_id),
	      callback: function($$v) {
	        _vm.formScopeDialog.metadata_id = $$v
	      },
	      expression: "formScopeDialog.metadata_id"
	    }
	  }, _vm._l((_vm.metalist), function(item) {
	    return _c('el-option', {
	      key: item.metadata_id,
	      attrs: {
	        "label": item.metadata_name,
	        "value": item.metadata_id
	      }
	    })
	  }))], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "型号",
	      "prop": "car_models_name"
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "200px"
	    },
	    attrs: {
	      "disabled": !_vm.ismodify,
	      "minlength": 2,
	      "maxlength": 10,
	      "placeholder": "请输入型号（2-10字数限制）"
	    },
	    model: {
	      value: (_vm.formScopeDialog.car_models_name),
	      callback: function($$v) {
	        _vm.formScopeDialog.car_models_name = $$v
	      },
	      expression: "formScopeDialog.car_models_name"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": ""
	    }
	  }, [_c('el-checkbox', {
	    attrs: {
	      "disabled": !_vm.ismodify
	    },
	    model: {
	      value: (_vm.checked),
	      callback: function($$v) {
	        _vm.checked = $$v
	      },
	      expression: "checked"
	    }
	  }, [_vm._v("APP显示")])], 1)], 1), _vm._v(" "), _c('div', {
	    staticClass: "newCarTypeGroup"
	  }, [_vm._v("参数设置")]), _vm._v(" "), _c('el-form', {
	    ref: _vm.addParamForm,
	    staticClass: "demo-form-inline",
	    staticStyle: {
	      "width": "660px"
	    },
	    attrs: {
	      "label-width": "100px",
	      "model": _vm.addParamForm
	    }
	  }, [_vm._l((_vm.addParamForm.carParameters), function(parameter, index) {
	    return _c('el-row', [_c('el-col', {
	      attrs: {
	        "span": 11
	      }
	    }, [_c('el-form-item', {
	      attrs: {
	        "label": "型号",
	        "prop": 'carParameters.' + index + '.car_params_name',
	        "rules": [{
	            required: true,
	            message: '请输入型号',
	            trigger: 'blur'
	          },
	          {
	            min: 2,
	            max: 50,
	            message: '长度在 2 到 50 个字符',
	            trigger: 'blur'
	          }
	        ]
	      }
	    }, [_c('el-input', {
	      staticStyle: {
	        "width": "200px"
	      },
	      attrs: {
	        "disabled": !_vm.ismodify,
	        "minlength": 2,
	        "maxlength": 50,
	        "placeholder": "输入（2-50字数限制）"
	      },
	      model: {
	        value: (parameter.car_params_name),
	        callback: function($$v) {
	          parameter.car_params_name = $$v
	        },
	        expression: "parameter.car_params_name"
	      }
	    })], 1)], 1), _vm._v(" "), _c('el-col', {
	      attrs: {
	        "span": 10
	      }
	    }, [_c('el-form-item', {
	      attrs: {
	        "label": "值",
	        "label-width": "40px"
	      }
	    }, [_c('el-input', {
	      staticStyle: {
	        "width": "200px"
	      },
	      attrs: {
	        "disabled": !_vm.ismodify,
	        "minlength": 2,
	        "maxlength": 50,
	        "placeholder": "输入（2-50字数限制）"
	      },
	      model: {
	        value: (parameter.car_params_value),
	        callback: function($$v) {
	          parameter.car_params_value = $$v
	        },
	        expression: "parameter.car_params_value"
	      }
	    })], 1)], 1), _vm._v(" "), (_vm.ismodify) ? _c('el-col', {
	      attrs: {
	        "span": 2
	      }
	    }, [_c('el-button', {
	      attrs: {
	        "plain": true,
	        "type": "danger"
	      },
	      on: {
	        "click": function($event) {
	          _vm.deleteParam(parameter)
	        }
	      }
	    }, [_vm._v("删除")])], 1) : _vm._e()], 1)
	  }), _vm._v(" "), (_vm.ismodify) ? _c('div', {
	    staticClass: "addParameterBtn",
	    on: {
	      "click": _vm.addCarTypeParameter
	    }
	  }, [_c('i', {
	    staticClass: "el-icon-plus"
	  }), _vm._v(" 添加参数")]) : _vm._e()], 2)], 1), _vm._v(" "), (!_vm.ismodify) ? _c('div', {
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
	        _vm.dialogFormVisible = false
	      }
	    }
	  }, [_vm._v("确 定")])], 1) : _vm._e(), _vm._v(" "), (_vm.ismodify) ? _c('div', {
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
	        _vm.addCarModel(1)
	      }
	    }
	  }, [_vm._v("保存并启用")]), _vm._v(" "), _c('el-button', {
	    attrs: {
	      "type": "primary"
	    },
	    on: {
	      "click": function($event) {
	        _vm.addCarModel(0)
	      }
	    }
	  }, [_vm._v("保存并禁用")]), _vm._v(" "), _c('el-button', {
	    on: {
	      "click": function($event) {
	        _vm.dialogFormVisible = false
	      }
	    }
	  }, [_vm._v("关 闭")])], 1) : _vm._e()])], 1)
	},staticRenderFns: []}
	module.exports.render._withStripped = true
	if (false) {
	  module.hot.accept()
	  if (module.hot.data) {
	     require("vue-hot-reload-api").rerender("data-v-1768a0e0", module.exports)
	  }
	}

/***/ })

});
//# sourceMappingURL=28.js.map