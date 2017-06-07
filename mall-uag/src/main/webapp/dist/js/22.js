webpackJsonp([22],{

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

/***/ 108:
/***/ (function(module, exports, __webpack_require__) {

	
	/* styles */
	__webpack_require__(109)
	
	var Component = __webpack_require__(13)(
	  /* script */
	  __webpack_require__(111),
	  /* template */
	  __webpack_require__(112),
	  /* scopeId */
	  null,
	  /* cssModules */
	  null
	)
	Component.options.__file = "/Users/xiangyangli/projects/hjh/jhd/mall-uag/src/main/webapp/app/components/groundingProduct.vue"
	if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key !== "__esModule"})) {console.error("named exports are not supported in *.vue files.")}
	if (Component.options.functional) {console.error("[vue-loader] groundingProduct.vue: functional components are not supported with templates, they should use render functions.")}
	
	/* hot reload */
	if (false) {(function () {
	  var hotAPI = require("vue-hot-reload-api")
	  hotAPI.install(require("vue"), false)
	  if (!hotAPI.compatible) return
	  module.hot.accept()
	  if (!module.hot.data) {
	    hotAPI.createRecord("data-v-485a572b", Component.options)
	  } else {
	    hotAPI.reload("data-v-485a572b", Component.options)
	  }
	})()}
	
	module.exports = Component.exports


/***/ }),

/***/ 109:
/***/ (function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(110);
	if(typeof content === 'string') content = [[module.id, content, '']];
	if(content.locals) module.exports = content.locals;
	// add the styles to the DOM
	var update = __webpack_require__(29)("c5a0a516", content, false);
	// Hot Module Replacement
	if(false) {
	 // When the styles change, update the <style> tags
	 if(!content.locals) {
	   module.hot.accept("!!../../node_modules/css-loader/index.js?sourceMap!../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-485a572b!../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./groundingProduct.vue", function() {
	     var newContent = require("!!../../node_modules/css-loader/index.js?sourceMap!../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-485a572b!../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./groundingProduct.vue");
	     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
	     update(newContent);
	   });
	 }
	 // When the module is disposed, remove the <style> tags
	 module.hot.dispose(function() { update(); });
	}

/***/ }),

/***/ 110:
/***/ (function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(28)();
	// imports
	
	
	// module
	exports.push([module.id, "\n.el-dialog__body .el-form-item__content{\n\tline-height: 0;\n}\n.newCarTypeGroup{\n\theight: 60px;\n\tline-height: 60px;\n\tfont-family: \"\\5FAE\\8F6F\\96C5\\9ED1\";\n\tfont-size: 18px;\n\tcolor: #1D8CE0;\n}\n.addParameterBtn{\n\theight: 55px;\n    font-size: 14px;\n    margin: 20px 20px 20px 0;\n    text-align: center;\n    color: #58B7FF;\n    border: 1px dashed #58B7FF;\n    border-radius: 3px;\n    line-height: 55px;\n    cursor: pointer;\n}\n.addParameterBtn:hover{\n\tcolor: #20A0FF;\n    border: 1px dashed #20A0FF;\n}\n", "", {"version":3,"sources":["/./app/components/groundingProduct.vue?663cb7dd"],"names":[],"mappings":";AAmQA;CACA,eAAA;CACA;AACA;CACA,aAAA;CACA,kBAAA;CACA,oCAAA;CACA,gBAAA;CACA,eAAA;CACA;AACA;CACA,aAAA;IACA,gBAAA;IACA,yBAAA;IACA,mBAAA;IACA,eAAA;IACA,2BAAA;IACA,mBAAA;IACA,kBAAA;IACA,gBAAA;CACA;AACA;CACA,eAAA;IACA,2BAAA;CACA","file":"groundingProduct.vue","sourcesContent":["<template>\r\n\t<div>\r\n\t\t<!-- 面包屑 -->\r\n\t\t<div class=\"hjh-breadcrumb\">\r\n\t\t\t<el-breadcrumb separator=\"/\">\r\n\t\t\t\t<el-breadcrumb-item :to=\"{ path: '/' }\">用户中心</el-breadcrumb-item>\r\n\t\t\t\t<el-breadcrumb-item>商品管理</el-breadcrumb-item>\r\n\t\t\t\t<el-breadcrumb-item>已上架商品</el-breadcrumb-item>\r\n\t\t\t</el-breadcrumb>\t\r\n\t\t</div>\r\n\t\t<!-- 面包屑 end -->\r\n\t\t<!-- 列表查询表单 -->\r\n\t\t<div class=\"block-white\">\r\n\t\t\t<el-form :inline=\"true\" :model=\"searchForm\" ref=\"searchForm\" class=\"demo-form-inline searchForm\">\r\n\t\t\t\t<el-form-item>\r\n\t\t\t\t\t<el-input v-model=\"searchForm.goods_id\" placeholder=\"商品编号\" style=\"width:150px;\"></el-input>\r\n\t\t\t\t</el-form-item>\r\n\t\t\t\t<el-form-item >\r\n\t\t\t\t\t<el-input v-model=\"searchForm.goods_name\" placeholder=\"商品标题\" style=\"width:150px;\"></el-input>\r\n\t\t\t\t</el-form-item>\r\n\t\t\t\t<el-form-item >\r\n\t\t\t\t\t<el-select v-model=\"searchForm.brand_name\" filterable placeholder=\"品牌\" style=\"width: 150px;\">\r\n\t\t\t\t\t\t<el-option v-for=\"item in goodsBrandList\" :key=\"item.brand_name\" :label=\"item.brand_name\" :value=\"item.brand_name\">\r\n\t\t\t\t\t\t</el-option>\r\n\t\t\t\t\t</el-select>\r\n\t\t\t\t</el-form-item>\r\n\t\t\t\t<el-form-item >\r\n\t\t\t\t\t<el-select v-model=\"searchForm.third_category_name\" filterable placeholder=\"类目\" style=\"width: 150px;\">\r\n\t\t\t\t\t\t<el-option v-for=\"item in categoryList\" :key=\"item.cname\" :label=\"item.cname\" :value=\"item.brand_name\">\r\n\t\t\t\t\t\t</el-option>\r\n\t\t\t\t\t</el-select>\r\n\t\t\t\t</el-form-item>\r\n\t\t\t\t<el-form-item>\r\n\t\t\t\t\t<el-select v-model=\"searchForm.create_user_name\" filterable placeholder=\"请输入操作人\" style=\"width: 150px;\">\r\n\t\t\t\t\t\t<el-option v-for=\"item in operatorList\" :key=\"item.operator_name\" :label=\"item.operator_name\" :value=\"item.operator_name\">\r\n\t\t\t\t\t\t</el-option>\r\n\t\t\t\t\t</el-select>\r\n\t\t\t\t</el-form-item>\r\n\t\t\t\t<el-form-item>\r\n\t\t\t\t\t<div style=\"width: 450px;\">\r\n\t\t\t\t\t<el-col :span=\"11\">\r\n\t\t\t\t\t\t<el-date-picker type=\"date\" placeholder=\"创建开始时间\" @change=\"initStartDateChange\" format=\"yyyy-MM-dd HH:mm:ss\"  v-model=\"init_start_date\" style=\"width:100%;\"></el-date-picker>\r\n\t\t\t\t\t</el-col>\r\n\t\t\t\t\t<el-col class=\"line\" :span=\"2\" style=\"text-align: center;\">到</el-col>\r\n\t\t\t\t\t<el-col :span=\"11\">\r\n\t\t\t\t\t\t<el-date-picker type=\"date\" placeholder=\"创建结束时间\" @change=\"initEndDateChange\" format=\"yyyy-MM-dd HH:mm:ss\" v-model=\"init_end_date\" style=\"width:100%;\"></el-date-picker>\r\n\t\t\t\t\t</el-col>\r\n\t\t\t\t\t</div>\r\n\t\t\t\t</el-form-item>\r\n\t\t\t\t<el-form-item >\r\n\t\t\t\t\t<el-input v-model=\"searchForm.metadata_name\" placeholder=\"模糊查询规格的3个字段\" style=\"width:170px;\"></el-input>\r\n\t\t\t\t</el-form-item>\r\n\t\t\t\t<el-form-item>\r\n\t\t\t\t\t<el-button class=\"searchBtn\" type=\"primary\" @click=\"submitForm('searchForm')\">搜索</el-button>\r\n\t\t\t\t\t<el-button @click=\"resetForm('searchForm')\">清除</el-button>\r\n\t\t\t\t</el-form-item>\r\n\t\t\t</el-form>\r\n\t\t</div>\r\n\t\t<!-- 列表查询表单 end -->\r\n\t\t<div class=\"block-white\">\r\n\t\t\t<el-button type=\"primary\" @click=\"bathUndercarriageGoods\" :disabled=\"!multipleSelection.length\">批量下架</el-button>\r\n\t\t\t<el-button type=\"danger\" @click=\"updateGroundGoods\">更新搜索引擎</el-button>\r\n\t\t</div>\r\n\t\t<div class=\"block-white\">\r\n\t\t\t<div class=\"selection-tip\">\r\n\t\t\t\t<i class=\"el-icon-warning\"></i><span>已选择 {{multipleSelection.length}} 项数据。</span>\r\n\t\t\t</div>\r\n\t\t</div>\r\n\t\t<!-- 商品列表 -->\r\n\t\t<div class=\"block-white\">\r\n\t\t\t<el-table v-loading.body=\"loading\" \r\n\t\t\t\t:data=\"ClientList.result_list\" \r\n\t\t\t\t@selection-change=\"handleSelectionChange\" \r\n\t\t\t\tref=\"multipleTable\"\r\n\t\t\t\tborder stripe style=\"width: 100%\" max-height=\"460\">\r\n\t\t\t\t<el-table-column fixed type=\"selection\" width=\"55\" class=\"hjhcheckbox\"></el-table-column>\r\n\t\t\t\t<el-table-column label=\"商品信息\" width=\"450\">\r\n\t\t\t\t\t<template scope=\"scope\">\r\n\t\t\t\t\t\t<img :src=\"'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+scope.row.show_url\">\r\n\t\t\t\t\t\t<a @click=\"checkProduct(scope.$index, ClientList.result_list)\"><div class=\"\">{{scope.row.goods_name}}</div></a>\r\n\t\t\t\t\t\t<div class=\"\">{{scope.row.goods_id}}</div>\r\n\t\t\t\t\t\t<div class=\"\">品牌：{{scope.row.brand_name}}</div>\r\n\t\t\t\t\t\t<div class=\"\">三级类目：{{scope.row.third_category_name}}</div>\r\n\t\t\t\t\t</template>\r\n\t\t\t\t</el-table-column>\r\n\t\t\t\t<el-table-column prop=\"brand_name\" label=\"规格/价格\" align=\"center\" width=\"300\">\r\n\t\t\t\t\t<template scope=\"scope\">\r\n\t\t\t\t\t\t<ul>\r\n\t\t\t\t\t\t\t<li v-for=\"item in scope.row.standardList\">\r\n\t\t\t\t\t\t\t    <div class=\"\" style=\"float:left\">{{item.standard_must}}|{{item.optional_first}}|{{item.optional_second}}</div>\r\n\t\t\t\t\t\t\t\t<div class=\"\" > &nbsp;&nbsp;&nbsp; {{item.price}}/{{item.unit_name}}</div>\r\n\t\t\t\t\t\t\t</li>\r\n\t\t\t\t\t\t</ul>\r\n\t\t\t\t\t</template>\r\n\t\t\t\t</el-table-column>\r\n\t\t\t\t<el-table-column  label=\"已售\" width=\"100\" align=\"center\">\r\n\t\t\t\t\t<template scope=\"scope\">\r\n\t\t\t\t\t\t<ul>\r\n\t\t\t\t\t\t\t<li v-for=\"item in scope.row.standardList\">\r\n\t\t\t\t\t\t\t    <div class=\"\">{{item.sale_num}}</div>\r\n\t\t\t\t\t\t\t</li>\r\n\t\t\t\t\t\t</ul>\r\n\t\t\t\t\t</template>\r\n\t\t\t\t</el-table-column>\r\n\t\t\t\t<el-table-column  label=\"库存\" align=\"center\" width=\"100\">\r\n\t\t\t\t\t<template scope=\"scope\">\r\n\t\t\t\t\t\t<ul>\r\n\t\t\t\t\t\t\t<li v-for=\"item in scope.row.standardList\">\r\n\t\t\t\t\t\t\t    <div class=\"\">{{item.store_num}}</div>\r\n\t\t\t\t\t\t\t</li>\r\n\t\t\t\t\t\t</ul>\r\n\t\t\t\t\t</template>\r\n\t\t\t\t</el-table-column>\r\n\t\t\t\t<el-table-column prop=\"createTime\" label=\"创建日期\" align=\"center\" width=\"180\"></el-table-column>\r\n\t\t\t\t<el-table-column prop=\"create_user_name\" label=\"创建人\" align=\"center\" width=\"150\"></el-table-column>\r\n\t\t\t\t<el-table-column prop=\"updateTime\" label=\"上架日期\" align=\"center\" width=\"180\"></el-table-column>\r\n\t\t\t\t<el-table-column prop=\"update_user_name\" label=\"最后操作者\" align=\"center\" width=\"120\"></el-table-column>\r\n\t\t\t\t<el-table-column fixed=\"right\"  label=\"操作\" width=\"100\">\r\n\t\t\t\t\t<template scope=\"scope\">\r\n\t\t\t\t\t\t<el-button @click.native.prevent=\"modifyGoodsStatus(scope.row.goods_id)\" type=\"warning\">下架</el-button>\r\n\t\t\t\t\t</template>\r\n\t\t\t\t</el-table-column>\r\n\t\t\t</el-table>\r\n\t\t</div>\r\n\t\t<!-- 会员管理列表 end -->\r\n\t\t<!-- 翻页组件 -->\r\n\t\t<div class=\"block-white\">\r\n\t\t\t<el-pagination\r\n\t\t      @current-change=\"handleCurrentChange\"\r\n\t\t      :current-page=\"page\"\r\n\t\t      :page-size=\"limit\"\r\n\t\t      layout=\"total, prev, pager, next, jumper\"\r\n\t\t      :total=\"ClientList.total_num\">\r\n\t\t    </el-pagination>\r\n\t\t</div>\r\n\t\t<!-- 翻页组件 end -->\r\n\t\t\t\t<el-dialog title=\"商品信息\" v-model=\"dialogGoodsDetailVisible\">\r\n\t\t\t<div class=\"goodsDetail\" style=\"height: 450px; overflow: hidden; overflow-y: auto;\">\r\n\t\t\t\t<el-row>\r\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\r\n\t\t\t\t\t<el-col :span=\"3\">\r\n\t\t\t\t\t\t<em style=\"color: #FF0000;\"></em>商品名称\r\n\t\t\t\t\t</el-col>\r\n\t\t\t\t\t<el-col :span=\"20\">{{goodsDetail.goods_name}}</el-col>\r\n\t\t\t\t</el-row>\r\n\t\t\t\t<el-row>\r\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\r\n\t\t\t\t\t<el-col :span=\"3\">\r\n\t\t\t\t\t\t<em style=\"color: #FF0000;\"></em>类目\r\n\t\t\t\t\t</el-col>\r\n\t\t\t\t\t<el-col :span=\"20\">{{goodsDetail.third_category_name}}</el-col>\r\n\t\t\t\t</el-row>\r\n\t\t\t\t<el-row>\r\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\r\n\t\t\t\t\t<el-col :span=\"3\">\r\n\t\t\t\t\t\t<em style=\"color: #FF0000;\"></em>品牌\r\n\t\t\t\t\t</el-col>\r\n\t\t\t\t\t<el-col :span=\"20\">{{goodsDetail.brand_name}}</el-col>\r\n\t\t\t\t</el-row>\r\n\t\t\t\t<el-row>\r\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\r\n\t\t\t\t\t<el-col :span=\"3\">\r\n\t\t\t\t\t\t<em style=\"color: #FF0000;\"></em>计量单位\r\n\t\t\t\t\t</el-col>\r\n\t\t\t\t\t<el-col :span=\"20\">{{goodsDetail.unit_name}}</el-col>\r\n\t\t\t\t</el-row>\r\n\t\t\t\t<el-row>\r\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\r\n\t\t\t\t\t<el-col :span=\"3\">\r\n\t\t\t\t\t\t<em style=\"color: #FF0000;\"></em>占位图\r\n\t\t\t\t\t</el-col>\r\n\t\t\t\t\t<el-col :span=\"20\">\r\n\t\t\t\t\t\t<img v-if=\"goodsDetail.ad_url\" style=\"display: inline-block; width: 80px; height: 30px;\" :src=\"ossUrl+goodsDetail.ad_url\">\r\n\t\t\t\t\t\t<span v-if=\"!goodsDetail.ad_url\">无</span>\r\n\t\t\t\t\t</el-col>\r\n\t\t\t\t</el-row>\r\n\t\t\t\t<el-row>\r\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\r\n\t\t\t\t\t<el-col :span=\"3\">\r\n\t\t\t\t\t\t<em style=\"color: #FF0000;\"></em>适用机型\r\n\t\t\t\t\t</el-col>\r\n\t\t\t\t\t<el-col :span=\"20\">\r\n\t\t\t\t\t\t<span class=\"carModelList\" v-for=\"item in goodsDetail.carModelList\">\r\n\t\t\t\t\t\t\t{{item.car_brand_name}} | {{item.car_type}} | {{item.car_models_name}}\r\n\t\t\t\t\t\t</span>\r\n\t\t\t\t\t\t<div v-if=\"goodsDetail.adapt_all_models==1\">适用全部机型</div>\r\n\t\t\t\t\t</el-col>\r\n\t\t\t\t</el-row>\r\n\t\t\t\t<el-row>\r\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\r\n\t\t\t\t\t<el-col :span=\"3\">\r\n\t\t\t\t\t\t<em style=\"color: #FF0000;\"></em>banner图\r\n\t\t\t\t\t</el-col>\r\n\t\t\t\t\t<el-col :span=\"20\">\r\n\t\t\t\t\t\t<ul class=\"banner-list\">\r\n\t\t\t\t\t\t\t<li v-for=\"item in goodsDetail.bannerList\">\r\n\t\t\t\t\t\t\t\t<img :src=\"ossUrl+item.banner_url\">\r\n\t\t\t\t\t\t\t</li>\r\n\t\t\t\t\t\t</ul>\r\n\t\t\t\t\t</el-col>\r\n\t\t\t\t</el-row>\r\n\t\t\t\t<el-row>\r\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\r\n\t\t\t\t\t<el-col :span=\"3\">\r\n\t\t\t\t\t\t<em style=\"color: #FF0000;\"></em>商品详图\r\n\t\t\t\t\t</el-col>\r\n\t\t\t\t\t<el-col :span=\"20\">\r\n\t\t\t\t\t\t<ul class=\"detail-list\">\r\n\t\t\t\t\t\t\t<li v-for=\"item in goodsDetail.detailList\">\r\n\t\t\t\t\t\t\t\t<img :src=\"ossUrl+item.detail_url\">\r\n\t\t\t\t\t\t\t\t<span>{{item.pic_desc}}</span>\r\n\t\t\t\t\t\t\t</li>\r\n\t\t\t\t\t\t</ul>\r\n\t\t\t\t\t</el-col>\r\n\t\t\t\t</el-row>\r\n\t\t\t\t<el-row>\r\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\r\n\t\t\t\t\t<el-col :span=\"3\">\r\n\t\t\t\t\t\t<em style=\"color: #FF0000;\"></em>客服电话\r\n\t\t\t\t\t</el-col>\r\n\t\t\t\t\t<el-col :span=\"20\">{{goodsDetail.tel}}</el-col>\r\n\t\t\t\t</el-row>\r\n\t\t\t\t<el-row>\r\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\r\n\t\t\t\t\t<el-col :span=\"3\">\r\n\t\t\t\t\t\t<em style=\"color: #FF0000;\"></em>商品参数\r\n\t\t\t\t\t</el-col>\r\n\t\t\t\t\t<el-col :span=\"20\">\r\n\t\t\t\t\t\t<ul class=\"param-list\">\r\n\t\t\t\t\t\t\t<li v-for=\"item in goodsDetail.infoList\">\r\n\t\t\t\t\t\t\t\t<span><em></em>名称<span>{{item.info_title}}</span></span>\r\n\t\t\t\t\t\t\t\t<span><em></em>值<span>{{item.info_content}}</span></span>\r\n\t\t\t\t\t\t\t</li>\r\n\t\t\t\t\t\t</ul>\r\n\t\t\t\t\t</el-col>\r\n\t\t\t\t</el-row>\r\n\t\t\t\t<el-row>\r\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\r\n\t\t\t\t\t<el-col :span=\"3\">\r\n\t\t\t\t\t\t<em style=\"color: #FF0000;\"></em>商品规格\r\n\t\t\t\t\t</el-col>\r\n\t\t\t\t\t<el-col :span=\"20\">\r\n\t\t\t\t\t\t<ul class=\"standard-list\">\r\n\t\t\t\t\t\t\t<li v-for=\"item in goodsDetail.standardList\">\r\n\t\t\t\t\t\t\t\t<span><em></em>名称<span>{{item.standard_must}}</span><span>{{item.optional_first}}</span><span>{{item.optional_second}}</span></span>\r\n\t\t\t\t\t\t\t\t<span><em></em>价格<span>{{item.price}}</span></span>\r\n\t\t\t\t\t\t\t\t<span><em></em>库存<span>{{item.store_num}}</span></span>\r\n\t\t\t\t\t\t\t</li>\r\n\t\t\t\t\t\t</ul>\r\n\t\t\t\t\t</el-col>\r\n\t\t\t\t</el-row>\r\n\t\t\t</div>\r\n\t\t\t<div slot=\"footer\" class=\"dialog-footer\" style=\"text-align: center;\">\r\n\t\t\t\t<el-button @click=\"dialogGoodsDetailVisible = false\">关 闭</el-button>\r\n\t\t\t</div>\r\n\t\t</el-dialog>\r\n\t</div>\r\n</template>\r\n<style>\r\n\t.el-dialog__body .el-form-item__content{\r\n\t\tline-height: 0;\r\n\t}\r\n\t.newCarTypeGroup{\r\n\t\theight: 60px;\r\n\t\tline-height: 60px;\r\n\t\tfont-family: \"微软雅黑\";\r\n\t\tfont-size: 18px;\r\n\t\tcolor: #1D8CE0;\r\n\t}\r\n\t.addParameterBtn{\r\n\t\theight: 55px;\r\n\t    font-size: 14px;\r\n\t    margin: 20px 20px 20px 0;\r\n\t    text-align: center;\r\n\t    color: #58B7FF;\r\n\t    border: 1px dashed #58B7FF;\r\n\t    border-radius: 3px;\r\n\t    line-height: 55px;\r\n\t    cursor: pointer;\r\n\t}\r\n\t.addParameterBtn:hover{\r\n\t\tcolor: #20A0FF;\r\n\t    border: 1px dashed #20A0FF;\r\n\t}\r\n</style>\r\n<script>\r\n\timport Common from './../js/common';\r\n\texport default {\r\n\t\tdata() {\r\n\t\t\treturn {\r\n\t\t\t\tsearchForm: this.getDefaultData(),\t//查询表单\r\n\t\t\t\tpage : 1,\t\t\t\t\t\t\t//列表初始当前页码\r\n\t\t\t\tlimit : 10,\t\t\t\t\t\t\t//每页列表数量\r\n\t\t\t\tinit_start_date : '',\r\n\t\t\t\tinit_end_date : '',\r\n\t\t\t\tgoodsBrandList : [],\t\t\t\t\t\t//品牌列表\r\n\t\t\t\tmetalist : [],\r\n\t\t\t\toperatorList : [],\r\n\t\t\t\tcategoryList:[],\r\n\t\t\t\tmultipleSelection: [],\r\n\t\t\t\tossUrl : \"http://test-hjh.oss-cn-shanghai.aliyuncs.com/\",\r\n\t\t\t\tdialogGoodsDetailVisible : false,\r\n\t\t\t\tgoodsDetail : {},\r\n\t\t\t\tClientList : {},\t\t\t\t\t\t//会员列表数据缓存\r\n\t\t\t\tformScopeDialog:{},\t\t\t\t\t//查看或编辑时对应的数据缓存\r\n\t\t\t\tdialogFormVisible:false,\t\t\t\t//查看与编辑弹窗显示状态 false为隐藏 true为显示\r\n\t\t\t\tformLabelWidth: '110px',\t\t\t\t//弹窗中的表单label的宽度\r\n\t\t\t\tactiveName:\"first\",\t\t\t\t\t//弹窗中tabs选项卡初始选中值\r\n\t\t\t\tlabelPosition : \"right\",\t\t\t\t//弹窗中表单label文字的对齐方式\r\n\t\t\t\tismodify:false,\t\t\t\t\t\t//是否为编辑状态\r\n\t\t\t\tloading:false,\t\t\t\t\t\t//会员列表加载状态 true为加载中 false为加载完毕\r\n\t\t\t\ttimers:0,\r\n\t\t\t\t//验证规则\r\n\t\t\t\trules : {\r\n\t\t\t\t\t//机型品牌的验证规则\r\n\t\t\t\t\tbrand_id : [\r\n\t\t\t\t\t\t{required: true, message: '请选择机型品牌', trigger: 'blur,change'}\r\n\t\t\t\t\t],\r\n\t\t\t\t\t//类型验证规则\r\n\t\t\t\t\tmetadata_id : [\r\n\t\t\t\t\t\t{required: true, message: '请选择类型', trigger: 'blur,change'}\r\n\t\t\t\t\t],\r\n\t\t\t\t\t//型号的验证规则\r\n\t\t\t\t\tcar_models_name : [\r\n\t\t\t\t\t\t{required: true, message: '请输入型号', trigger: 'blur'},\r\n\t\t\t\t\t\t{ min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }\r\n\t\t\t\t\t],\r\n\t\t\t\t\tbrand_logo : [\r\n\t\t\t\t\t\t{required: true, message: '请选择机型品牌', trigger: 'blur,change'}\r\n\t\t\t\t\t]\r\n\t\t\t\t},\r\n\t\t\t\t//参数设置表单\r\n\t\t\t\taddParamForm: {\r\n\t\t        \t\tcarParameters : []\r\n\t\t        },\r\n\t\t        //是否在APP显示\r\n\t\t\t\tchecked : false\r\n\t\t\t}\r\n\t\t},\r\n\t\tcreated(){\r\n\t\t\tvar that = this;\r\n\t\t\t//获取类型列表\r\n\t\t\tthat.$http.post(\"./../queryMetadata\",{limit:10000,page:1,status:1,type:1}).then(response =>{\r\n\t\t\t\tvar data = response.data;\r\n\t\t\t\tif(data.error_no == 0){\r\n\t\t\t\t\tthat.$data.metalist = data.result_list;\r\n\t\t\t\t}\r\n\t\t\t});\r\n\t\t\t//获取操作人列表\r\n\t\t\tthis.$http.post(\"/getAllOperators\",{}).then(res=>{\r\n\t\t\t\tvar data = res.data;\r\n\t\t\t\tif(data.error_no == 0){\r\n\t\t\t\t\tthis.$data.operatorList = data.result_list;\r\n\t\t\t\t}\r\n\t\t\t});\r\n\t\t\t//获取品牌列表\r\n\t\t\tthis.$http.post(\"/goodsbrand/queryforweb\",{limit:1000000,status:1}).then(res=>{\r\n\t\t\t\tvar data = res.data;\r\n\t\t\t\tif(data.error_no == 0){\r\n\t\t\t\t\tthis.$data.goodsBrandList = data.result_list;\r\n\t\t\t\t}\r\n\t\t\t});\r\n\t\t\t//获取三级类目列表\r\n\t\t\tthis.$http.post(\"/json/900511\",{limit:1000000,status:1}).then(res=>{\r\n\t\t\t\tvar data = res.data;\r\n\t\t\t\tif(data.error_no == 0){\r\n\t\t\t\t\tthis.$data.categoryList = data.result_list;\r\n\t\t\t\t}\r\n\t\t\t});\r\n\t\t},\r\n\t\tmounted(){\r\n\t\t\tdocument.title = \"后台管理系统-已上架商品\";\r\n\t\t\tthis.getListBySearchData();\r\n\t\t},\r\n\t\tmethods: {\r\n\t\t\t//弹框展示详情\r\n\t\t\tcheckProduct(index,list){\r\n        \t\tvar item = list[index];\r\n\t\t\t\tthis.goodsDetail = item;\r\n\t\t\t\tthis.dialogGoodsDetailVisible = true;\r\n        \t\t},\r\n\t\t\t//选择添加开始时间\r\n\t\t\tinitStartDateChange(val){\r\n\t\t\t\tif(val)\r\n\t\t\t\t\tthis.init_start_date = val.replace(\"00:00:00\",Common.getHMSforNow());\r\n\t\t\t},\r\n\t\t\t//选择添加结束时间\r\n\t\t\tinitEndDateChange(val){\r\n\t\t\t\tif(val)\r\n\t\t\t\t\tthis.init_end_date = val.replace(\"00:00:00\",Common.getHMSforNow());\r\n\t\t\t},\r\n\t\t\t//多选状态发生改变\r\n\t\t\thandleSelectionChange(val) {\r\n\t\t\t\tthis.multipleSelection = val;\r\n\t\t\t\t\r\n\t\t\t},\r\n\t\t\t//获取查询表单初始数据\r\n\t\t\tgetDefaultData() {\r\n\t\t\t\treturn {\r\n\t\t\t\t\taccess_token: localStorage.access_token,\r\n\t\t\t\t\tbrand_name : \"\",\r\n\t\t\t\t\tcreate_user_name : \"\",\r\n\t\t\t\t\tend_date : '',\t\t\r\n\t\t\t\t\tgoods_id : '',\t\t\t\r\n\t\t\t\t\tgoods_name : '',\t\r\n\t\t\t\t\tgoods_status : '1',\t\t\r\n\t\t\t\t\tlimit : '',\t\t\r\n\t\t\t\t\tpage : '',\t\t\r\n\t\t\t\t\tstart_date : '',\t\t\r\n\t\t\t\t\tthird_category_name : ''\r\n\t\t\t\t}\r\n\t\t\t},\r\n\t\t\t//下架单个商品\r\n\t\t\tmodifyGoodsStatus(val){\r\n\t\t\t\tvar that = this;\r\n\t\t\t\tthis.$confirm('确定要下架该商品吗？', '提示', {\r\n\t\t\t\t\tconfirmButtonText: '确定',\r\n\t\t\t\t\tcancelButtonText: '取消',\r\n\t\t\t\t\ttype: 'warning'\r\n\t\t\t\t}).then(() => {\r\n\t\t\t\t\tthis.$http.post('/undercarriageGoods',{goods_id:val,access_token:localStorage.access_token}).then(response => {\r\n\t\t\t\t\t\tvar jsondata = response.data;\r\n\t\t\t\t\t\t\tthat.$message({\r\n\t\t\t\t\t \t\t\ttype: jsondata.error_no == 0 ? \"success\" : \"error\",\r\n\t\t\t\t\t \t\t\tmessage: jsondata.error_no == 0 ? \"操作成功\" : jsondata.error_info\r\n\t\t\t\t\t \t\t});\r\n\t\t\t\t\t \t\tif(jsondata.error_no == 0){\r\n\t\t\t\t\t \t\t\tthat.getGoodsList();\r\n\t\t\t\t\t \t\t}\r\n\t\t\t\t\t})\r\n\t\t\t\t}).catch(() => {         \r\n\t\t\t\t});\r\n\t\t\t},\r\n\t\t\t//批量下架商品\r\n\t\t\tbathUndercarriageGoods(val){\r\n\t\t\t\tvar that = this;\r\n\t\t\t\tthis.$confirm('确定要下架选中商品吗？', '提示', {\r\n\t\t\t\t\tconfirmButtonText: '确定',\r\n\t\t\t\t\tcancelButtonText: '取消',\r\n\t\t\t\t\ttype: 'warning'\r\n\t\t\t\t}).then(() => {\r\n\t\t\t\t\tvar goods_ids = that.multipleSelection.map(item=>{ return item.goods_id; });\r\n\t\t\t\t\tthis.$http.post('/bathUndercarriageGoods',{ids:goods_ids,access_token:localStorage.access_token}).then(response => {\r\n\t\t\t\t\t\tvar jsondata = response.data;\r\n\t\t\t\t\t\t\tthat.$message({\r\n\t\t\t\t\t \t\t\ttype: jsondata.error_no == 0 ? \"success\" : \"error\",\r\n\t\t\t\t\t \t\t\tmessage: jsondata.error_no == 0 ? \"操作成功\" : jsondata.error_info\r\n\t\t\t\t\t \t\t});\r\n\t\t\t\t\t \t\tif(jsondata.error_no == 0){\r\n\t\t\t\t\t \t\t\tthat.getGoodsList();\r\n\t\t\t\t\t \t\t}\r\n\t\t\t\t\t})\r\n\t\t\t\t}).catch(() => {         \r\n\t\t\t\t});\r\n\t\t\t},\r\n\t\t\t//批量更新已上架的搜索引擎\r\n\t\t\tupdateGroundGoods(val) {\r\n\t\t\t\tvar that = this;\r\n\t\t\t\tthis.$confirm('如无特殊需求，请不要点击该按钮,确定要刷新搜索引擎吗？', '提示', {\r\n\t\t\t\t\tconfirmButtonText: '确定',\r\n\t\t\t\t\tcancelButtonText: '取消',\r\n\t\t\t\t\ttype: 'warning'\r\n\t\t\t\t}).then(() => {\r\n\t\t\t\t\tvar goods_ids = that.multipleSelection.map(item=>{ return item.goods_id; });\r\n\t\t\t\t\tthis.$http.post('/updateGroundGoods',{access_token:localStorage.access_token}).then(response => {\r\n\t\t\t\t\t\tvar jsondata = response.data;\r\n\t\t\t\t\t\t\tthat.$message({\r\n\t\t\t\t\t \t\t\ttype: jsondata.error_no == 0 ? \"success\" : \"error\",\r\n\t\t\t\t\t \t\t\tmessage: jsondata.error_no == 0 ? \"操作成功\" : jsondata.error_on+jsondata.error_info\r\n\t\t\t\t\t \t\t});\r\n\t\t\t\t\t \t\tif(jsondata.error_no == 0){\r\n\t\t\t\t\t \t\t\tthat.getGoodsList();\r\n\t\t\t\t\t \t\t}\r\n\t\t\t\t\t})\r\n\t\t\t\t}).catch(() => {         \r\n\t\t\t\t});\r\n\t\t\t\t\r\n\t\t\t},\r\n\t\t\t//删除参数行\r\n\t\t\tdeleteParam(item){\r\n\t\t\t\tvar index = this.addParamForm.carParameters.indexOf(item);\r\n\t\t        if (index !== -1) {\r\n\t\t          this.addParamForm.carParameters.splice(index, 1)\r\n\t\t        }\r\n\t\t\t},\r\n\t\t\t//多选 启用或禁用 status = 1 启用 0禁用\r\n\t\t\tupdateCarStatusBatch(status){\r\n\t\t\t\tvar that = this;\r\n\t\t\t\tthis.$confirm('确定要'+ (status==1 ? '启用':'禁用') +'该机型吗？', '提示', {\r\n\t\t\t\t\tconfirmButtonText: '确定',\r\n\t\t\t\t\tcancelButtonText: '取消',\r\n\t\t\t\t\ttype: 'warning'\r\n\t\t\t\t}).then(() => {\r\n\t\t\t\t\tvar car_models_ids = that.multipleSelection.map(item=>{ return item.car_models_id; }).join(\",\")\r\n\t\t\t\t\tthat.ajaxUpdateCarStatusBatch(status,car_models_ids,function(){\r\n\t\t\t\t\t\tthat.multipleSelection = [];\r\n\t\t\t\t \t\tthat.$refs.multipleTable.clearSelection();\r\n\t\t\t\t\t});\r\n\t\t\t\t}).catch(() => {         \r\n\t\t\t\t});\r\n\t\t\t},\r\n\t\t\t//状态变更提交\r\n\t\t\tajaxUpdateCarStatusBatch(status,car_models_ids,handle){\r\n\t\t\t\tvar that = this;\r\n\t\t\t\tvar param = {\r\n\t\t\t\t\t\taccess_token : that.searchForm.access_token,\r\n\t\t\t\t\t\tstatus : status,\r\n\t\t\t\t\t\tcar_models_ids : car_models_ids\r\n\t\t\t\t\t};\r\n\t\t\t\tthat.$http.post(\"./../updateCarStatusBatch\",param).then(response=>{\r\n\t\t\t\t\t\tvar jsondata = response.data;\r\n\t\t\t\t\t\tthat.$message({\r\n\t\t\t\t \t\t\ttype: jsondata.error_no == 0 ? \"success\" : \"error\",\r\n\t\t\t\t \t\t\tmessage: jsondata.error_no == 0 ? \"操作成功\" : jsondata.error_info\r\n\t\t\t\t \t\t});\r\n\t\t\t\t \t\tif(jsondata.error_no == 0){\r\n\t\t\t\t \t\t\tif(typeof handle == \"function\"){\r\n\t\t\t\t \t\t\t\thandle();\r\n\t\t\t\t \t\t\t}\r\n\t\t\t\t \t\t\tthat.getGoodsList();\r\n\t\t\t\t \t\t}\r\n\t\t\t\t\t})\r\n\t\t\t},\r\n\t\t\t//添加参数事件\r\n\t\t\taddCarTypeParameter(){\r\n\t\t\t\tthis.$data.addParamForm.carParameters.push({\r\n\t\t\t\t\tcar_params_name : '',\r\n\t\t\t\t\tcar_params_value : ''\r\n\t\t\t\t});\r\n\t\t\t},\r\n\t\t\t//操作－查询\r\n\t\t\tsubmitForm() {\r\n\t\t\t\tthis.$data.page = 1;\r\n\t\t\t\tthis.getListBySearchData();\r\n\t\t\t},\r\n\t\t\t//操作－重置\r\n\t\t\tresetForm(formName) {\r\n        \t\t\tthis.$refs[formName].resetFields();\r\n        \t\t\tthis.$data.searchForm = this.getDefaultData();\r\n        \t\t\tthis.init_start_date = \"\";\r\n        \t\t\tthis.init_end_date = \"\";\r\n        \t\t\tthis.submitForm();\r\n\t\t\t},\r\n\t\t\t//操作－翻页\r\n\t\t\thandleCurrentChange(val){\r\n\t\t\t\tthis.$data.page = val;\r\n\t\t\t\tthis.searchDataCache.page = val;\r\n\t\t\t\tthis.getGoodsList();\r\n\t\t\t},\r\n\t\t\t//操作－查看\r\n\t\t\tcheckView(index,list){\r\n\t\t\t\tthis.$data.dialogFormVisible = true;\r\n\t\t\t\tthis.$data.ismodify = false;\r\n\t\t\t\tthis.$data.formScopeDialog = list[index];\r\n\t\t\t\tthis.$data.checked = this.$data.formScopeDialog.app_show==1;\r\n\t\t\t},\r\n\t\t\t//操作－编辑\r\n\t\t\tmodify(index,list){\r\n\t\t\t\tthis.$data.dialogFormVisible = true;\r\n\t\t\t\tthis.$data.ismodify = true;\r\n\t\t\t\tthis.$data.formScopeDialog = Common.simpleClone(list[index]);\r\n\t\t\t\tthis.$data.checked = this.$data.formScopeDialog.app_show==1;\r\n\t\t\t\tvar that = this;\r\n\t\t\t\tsetTimeout(function(){\r\n\t\t\t\t\tthat.$refs[that.formScopeDialog].validate();\r\n\t\t\t\t},50);\r\n\t\t\t},\r\n\t\t\t\r\n\t\t\t//操作－启用或禁用\r\n\t\t\tsetStatus(index,list){\r\n\t\t\t\tvar status = list[index].status==1?0:1,\r\n\t\t\t\t\tid = list[index].car_models_id,\r\n\t\t\t\t\tthat = this;\r\n\t\t\t\t this.$confirm(\"确定要\" + (status==1?\"启用\":\"禁用\") + \"该机型吗？\", '', {\r\n\t\t\t\t \tconfirmButtonText: '确定',\r\n\t\t\t\t \tcancelButtonText: '取消',\r\n\t\t\t\t \ttype: 'warning'\r\n\t\t\t\t }).then(() => {\r\n\t\t\t\t \tthat.ajaxUpdateCarStatusBatch(status,id);\r\n\t\t\t\t });\r\n\t\t\t},\r\n\t\t\tcheckLogout(error_no){\r\n\t\t\t\tif(error_no==88880020){\r\n\t\t\t\t\tlocation.href=\"/login\";\r\n\t\t\t\t}\r\n\t\t\t},\r\n\t\t\t//重置时间戳格式\r\n\t\t\treFormatDate(date){\r\n\t\t\t\tvar _date = new Date(date);\r\n\t\t\t\tif(!date || !_date.getTime()){return \"\";}\r\n\t\t\t\tvar y = _date.getFullYear(),\r\n\t\t\t\t\tM = _date.getMonth()+1,\r\n\t\t\t\t\td = _date.getDate(),\r\n\t\t\t\t\tH = _date.getHours(),\r\n\t\t\t\t\tm = _date.getMinutes(),\r\n\t\t\t\t\ts = _date.getSeconds();\r\n\t\t\t\tM = M > 9 ? M : (\"0\"+M);\r\n\t\t\t\td = d > 9 ? d : (\"0\"+d);\r\n\t\t\t\tH = H > 9 ? H : (\"0\"+H);\r\n\t\t\t\tm = m > 9 ? m : (\"0\"+m);\r\n\t\t\t\ts = s > 9 ? s : (\"0\"+s);\r\n\t\t\t\treturn y+M+d+H+m+s;\r\n\t\t\t},\r\n\t\t\tgetListBySearchData(){\r\n\t\t\t\tvar param = this.searchForm;\r\n\t\t \t\tparam.page = this.$data.page;\r\n\t\t \t\tparam.limit = this.$data.limit;\r\n\t\t \t\tparam.start_date = this.reFormatDate(this.$data.init_start_date);\r\n\t\t \t\tparam.end_date = this.reFormatDate(this.$data.init_end_date);\r\n\t\t\t\tthis.searchDataCache = Common.simpleClone(param);\r\n\t\t\t\tthis.getGoodsList();\r\n\t\t\t},\r\n\t\t \t//获取商品列表\r\n\t\t\tgetGoodsList() {\r\n\t\t\t\tif(this.loading) return;\r\n\t\t\t\tvar that = this;\r\n\t \t\t\tthat.$data.loading = true;\r\n\t \t\t\tvar param = Common.filterParamByEmptyValue(this.searchDataCache);\r\n\t \t\t\tthis.$http.post('./../queryGoods',param).then(response => {\r\n\t\t\t\t\tvar jsonData=response.data;\r\n\t\t\t\t\tthat.checkLogout(jsonData.error_no);\r\n\t\t\t\t\tif(jsonData.error_no==0){\r\n\t\t\t\t\t\tjsonData.result_list = jsonData.result_list.map(function(item){\r\n\t\t\t\t\t\t\titem.createTime = Common.formatDateConcat(item.init_date,item.init_time);\r\n\t\t\t\t\t\t\titem.updateTime = Common.formatDateConcat(item.update_date,item.update_time);\r\n\t\t\t\t\t\t\treturn item;\r\n\t\t\t\t\t\t});\r\n\t\t\t\t\t\tthat.$data.ClientList = jsonData || {};\r\n\t\t\t\t\t}else{\r\n\t\t\t\t\t\tthat.$message({\r\n\t\t\t\t\t\t\ttype: \"warning\",\r\n\t\t\t\t\t\t\tmessage: jsonData.error_info\r\n\t\t\t\t\t\t});\r\n\t\t\t\t\t}\r\n\t\t\t\t\tthat.$data.loading = false;\r\n\t\t\t\t});\r\n\t\t\t}\r\n\t\t}\r\n\t}\r\n</script>\r\n"],"sourceRoot":"webpack://"}]);
	
	// exports


/***/ }),

/***/ 111:
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
				goodsBrandList: [], //品牌列表
				metalist: [],
				operatorList: [],
				categoryList: [],
				multipleSelection: [],
				ossUrl: "http://test-hjh.oss-cn-shanghai.aliyuncs.com/",
				dialogGoodsDetailVisible: false,
				goodsDetail: {},
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
			var _this = this;
	
			var that = this;
			//获取类型列表
			that.$http.post("./../queryMetadata", { limit: 10000, page: 1, status: 1, type: 1 }).then(function (response) {
				var data = response.data;
				if (data.error_no == 0) {
					that.$data.metalist = data.result_list;
				}
			});
			//获取操作人列表
			this.$http.post("/getAllOperators", {}).then(function (res) {
				var data = res.data;
				if (data.error_no == 0) {
					_this.$data.operatorList = data.result_list;
				}
			});
			//获取品牌列表
			this.$http.post("/goodsbrand/queryforweb", { limit: 1000000, status: 1 }).then(function (res) {
				var data = res.data;
				if (data.error_no == 0) {
					_this.$data.goodsBrandList = data.result_list;
				}
			});
			//获取三级类目列表
			this.$http.post("/json/900511", { limit: 1000000, status: 1 }).then(function (res) {
				var data = res.data;
				if (data.error_no == 0) {
					_this.$data.categoryList = data.result_list;
				}
			});
		},
		mounted: function mounted() {
			document.title = "后台管理系统-已上架商品";
			this.getListBySearchData();
		},
	
		methods: {
			//弹框展示详情
			checkProduct: function checkProduct(index, list) {
				var item = list[index];
				this.goodsDetail = item;
				this.dialogGoodsDetailVisible = true;
			},
	
			//选择添加开始时间
			initStartDateChange: function initStartDateChange(val) {
				if (val) this.init_start_date = val.replace("00:00:00", _common2.default.getHMSforNow());
			},
	
			//选择添加结束时间
			initEndDateChange: function initEndDateChange(val) {
				if (val) this.init_end_date = val.replace("00:00:00", _common2.default.getHMSforNow());
			},
	
			//多选状态发生改变
			handleSelectionChange: function handleSelectionChange(val) {
				this.multipleSelection = val;
			},
	
			//获取查询表单初始数据
			getDefaultData: function getDefaultData() {
				return {
					access_token: localStorage.access_token,
					brand_name: "",
					create_user_name: "",
					end_date: '',
					goods_id: '',
					goods_name: '',
					goods_status: '1',
					limit: '',
					page: '',
					start_date: '',
					third_category_name: ''
				};
			},
	
			//下架单个商品
			modifyGoodsStatus: function modifyGoodsStatus(val) {
				var _this2 = this;
	
				var that = this;
				this.$confirm('确定要下架该商品吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(function () {
					_this2.$http.post('/undercarriageGoods', { goods_id: val, access_token: localStorage.access_token }).then(function (response) {
						var jsondata = response.data;
						that.$message({
							type: jsondata.error_no == 0 ? "success" : "error",
							message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
						});
						if (jsondata.error_no == 0) {
							that.getGoodsList();
						}
					});
				}).catch(function () {});
			},
	
			//批量下架商品
			bathUndercarriageGoods: function bathUndercarriageGoods(val) {
				var _this3 = this;
	
				var that = this;
				this.$confirm('确定要下架选中商品吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(function () {
					var goods_ids = that.multipleSelection.map(function (item) {
						return item.goods_id;
					});
					_this3.$http.post('/bathUndercarriageGoods', { ids: goods_ids, access_token: localStorage.access_token }).then(function (response) {
						var jsondata = response.data;
						that.$message({
							type: jsondata.error_no == 0 ? "success" : "error",
							message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
						});
						if (jsondata.error_no == 0) {
							that.getGoodsList();
						}
					});
				}).catch(function () {});
			},
	
			//批量更新已上架的搜索引擎
			updateGroundGoods: function updateGroundGoods(val) {
				var _this4 = this;
	
				var that = this;
				this.$confirm('如无特殊需求，请不要点击该按钮,确定要刷新搜索引擎吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(function () {
					var goods_ids = that.multipleSelection.map(function (item) {
						return item.goods_id;
					});
					_this4.$http.post('/updateGroundGoods', { access_token: localStorage.access_token }).then(function (response) {
						var jsondata = response.data;
						that.$message({
							type: jsondata.error_no == 0 ? "success" : "error",
							message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_on + jsondata.error_info
						});
						if (jsondata.error_no == 0) {
							that.getGoodsList();
						}
					});
				}).catch(function () {});
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
						that.getGoodsList();
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
				this.getGoodsList();
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
				this.getGoodsList();
			},
	
			//获取商品列表
			getGoodsList: function getGoodsList() {
				if (this.loading) return;
				var that = this;
				that.$data.loading = true;
				var param = _common2.default.filterParamByEmptyValue(this.searchDataCache);
				this.$http.post('./../queryGoods', param).then(function (response) {
					var jsonData = response.data;
					that.checkLogout(jsonData.error_no);
					if (jsonData.error_no == 0) {
						jsonData.result_list = jsonData.result_list.map(function (item) {
							item.createTime = _common2.default.formatDateConcat(item.init_date, item.init_time);
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
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
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

/***/ 112:
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
	  }, [_vm._v("用户中心")]), _vm._v(" "), _c('el-breadcrumb-item', [_vm._v("商品管理")]), _vm._v(" "), _c('el-breadcrumb-item', [_vm._v("已上架商品")])], 1)], 1), _vm._v(" "), _c('div', {
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
	      "placeholder": "商品编号"
	    },
	    model: {
	      value: (_vm.searchForm.goods_id),
	      callback: function($$v) {
	        _vm.searchForm.goods_id = $$v
	      },
	      expression: "searchForm.goods_id"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', [_c('el-input', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "商品标题"
	    },
	    model: {
	      value: (_vm.searchForm.goods_name),
	      callback: function($$v) {
	        _vm.searchForm.goods_name = $$v
	      },
	      expression: "searchForm.goods_name"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', [_c('el-select', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "filterable": "",
	      "placeholder": "品牌"
	    },
	    model: {
	      value: (_vm.searchForm.brand_name),
	      callback: function($$v) {
	        _vm.searchForm.brand_name = $$v
	      },
	      expression: "searchForm.brand_name"
	    }
	  }, _vm._l((_vm.goodsBrandList), function(item) {
	    return _c('el-option', {
	      key: item.brand_name,
	      attrs: {
	        "label": item.brand_name,
	        "value": item.brand_name
	      }
	    })
	  }))], 1), _vm._v(" "), _c('el-form-item', [_c('el-select', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "filterable": "",
	      "placeholder": "类目"
	    },
	    model: {
	      value: (_vm.searchForm.third_category_name),
	      callback: function($$v) {
	        _vm.searchForm.third_category_name = $$v
	      },
	      expression: "searchForm.third_category_name"
	    }
	  }, _vm._l((_vm.categoryList), function(item) {
	    return _c('el-option', {
	      key: item.cname,
	      attrs: {
	        "label": item.cname,
	        "value": item.brand_name
	      }
	    })
	  }))], 1), _vm._v(" "), _c('el-form-item', [_c('el-select', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "filterable": "",
	      "placeholder": "请输入操作人"
	    },
	    model: {
	      value: (_vm.searchForm.create_user_name),
	      callback: function($$v) {
	        _vm.searchForm.create_user_name = $$v
	      },
	      expression: "searchForm.create_user_name"
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
	  })], 1)], 1)]), _vm._v(" "), _c('el-form-item', [_c('el-input', {
	    staticStyle: {
	      "width": "170px"
	    },
	    attrs: {
	      "placeholder": "模糊查询规格的3个字段"
	    },
	    model: {
	      value: (_vm.searchForm.metadata_name),
	      callback: function($$v) {
	        _vm.searchForm.metadata_name = $$v
	      },
	      expression: "searchForm.metadata_name"
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
	      "click": _vm.bathUndercarriageGoods
	    }
	  }, [_vm._v("批量下架")]), _vm._v(" "), _c('el-button', {
	    attrs: {
	      "type": "danger"
	    },
	    on: {
	      "click": _vm.updateGroundGoods
	    }
	  }, [_vm._v("更新搜索引擎")])], 1), _vm._v(" "), _c('div', {
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
	      "label": "商品信息",
	      "width": "450"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_c('img', {
	          attrs: {
	            "src": 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/' + scope.row.show_url
	          }
	        }), _vm._v(" "), _c('a', {
	          on: {
	            "click": function($event) {
	              _vm.checkProduct(scope.$index, _vm.ClientList.result_list)
	            }
	          }
	        }, [_c('div', {}, [_vm._v(_vm._s(scope.row.goods_name))])]), _vm._v(" "), _c('div', {}, [_vm._v(_vm._s(scope.row.goods_id))]), _vm._v(" "), _c('div', {}, [_vm._v("品牌：" + _vm._s(scope.row.brand_name))]), _vm._v(" "), _c('div', {}, [_vm._v("三级类目：" + _vm._s(scope.row.third_category_name))])]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "brand_name",
	      "label": "规格/价格",
	      "align": "center",
	      "width": "300"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_c('ul', _vm._l((scope.row.standardList), function(item) {
	          return _c('li', [_c('div', {
	            staticStyle: {
	              "float": "left"
	            }
	          }, [_vm._v(_vm._s(item.standard_must) + "|" + _vm._s(item.optional_first) + "|" + _vm._s(item.optional_second))]), _vm._v(" "), _c('div', {}, [_vm._v("     " + _vm._s(item.price) + "/" + _vm._s(item.unit_name))])])
	        }))]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "label": "已售",
	      "width": "100",
	      "align": "center"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_c('ul', _vm._l((scope.row.standardList), function(item) {
	          return _c('li', [_c('div', {}, [_vm._v(_vm._s(item.sale_num))])])
	        }))]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "label": "库存",
	      "align": "center",
	      "width": "100"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_c('ul', _vm._l((scope.row.standardList), function(item) {
	          return _c('li', [_c('div', {}, [_vm._v(_vm._s(item.store_num))])])
	        }))]
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
	      "prop": "create_user_name",
	      "label": "创建人",
	      "align": "center",
	      "width": "150"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "updateTime",
	      "label": "上架日期",
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
	      "fixed": "right",
	      "label": "操作",
	      "width": "100"
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
	              _vm.modifyGoodsStatus(scope.row.goods_id)
	            }
	          }
	        }, [_vm._v("下架")])]
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
	      "title": "商品信息"
	    },
	    model: {
	      value: (_vm.dialogGoodsDetailVisible),
	      callback: function($$v) {
	        _vm.dialogGoodsDetailVisible = $$v
	      },
	      expression: "dialogGoodsDetailVisible"
	    }
	  }, [_c('div', {
	    staticClass: "goodsDetail",
	    staticStyle: {
	      "height": "450px",
	      "overflow": "hidden",
	      "overflow-y": "auto"
	    }
	  }, [_c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 1
	    }
	  }, [_vm._v(" ")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 3
	    }
	  }, [_c('em', {
	    staticStyle: {
	      "color": "#FF0000"
	    }
	  }), _vm._v("商品名称\n\t\t\t\t")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 20
	    }
	  }, [_vm._v(_vm._s(_vm.goodsDetail.goods_name))])], 1), _vm._v(" "), _c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 1
	    }
	  }, [_vm._v(" ")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 3
	    }
	  }, [_c('em', {
	    staticStyle: {
	      "color": "#FF0000"
	    }
	  }), _vm._v("类目\n\t\t\t\t")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 20
	    }
	  }, [_vm._v(_vm._s(_vm.goodsDetail.third_category_name))])], 1), _vm._v(" "), _c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 1
	    }
	  }, [_vm._v(" ")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 3
	    }
	  }, [_c('em', {
	    staticStyle: {
	      "color": "#FF0000"
	    }
	  }), _vm._v("品牌\n\t\t\t\t")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 20
	    }
	  }, [_vm._v(_vm._s(_vm.goodsDetail.brand_name))])], 1), _vm._v(" "), _c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 1
	    }
	  }, [_vm._v(" ")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 3
	    }
	  }, [_c('em', {
	    staticStyle: {
	      "color": "#FF0000"
	    }
	  }), _vm._v("计量单位\n\t\t\t\t")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 20
	    }
	  }, [_vm._v(_vm._s(_vm.goodsDetail.unit_name))])], 1), _vm._v(" "), _c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 1
	    }
	  }, [_vm._v(" ")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 3
	    }
	  }, [_c('em', {
	    staticStyle: {
	      "color": "#FF0000"
	    }
	  }), _vm._v("占位图\n\t\t\t\t")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 20
	    }
	  }, [(_vm.goodsDetail.ad_url) ? _c('img', {
	    staticStyle: {
	      "display": "inline-block",
	      "width": "80px",
	      "height": "30px"
	    },
	    attrs: {
	      "src": _vm.ossUrl + _vm.goodsDetail.ad_url
	    }
	  }) : _vm._e(), _vm._v(" "), (!_vm.goodsDetail.ad_url) ? _c('span', [_vm._v("无")]) : _vm._e()])], 1), _vm._v(" "), _c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 1
	    }
	  }, [_vm._v(" ")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 3
	    }
	  }, [_c('em', {
	    staticStyle: {
	      "color": "#FF0000"
	    }
	  }), _vm._v("适用机型\n\t\t\t\t")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 20
	    }
	  }, [_vm._l((_vm.goodsDetail.carModelList), function(item) {
	    return _c('span', {
	      staticClass: "carModelList"
	    }, [_vm._v("\n\t\t\t\t\t\t" + _vm._s(item.car_brand_name) + " | " + _vm._s(item.car_type) + " | " + _vm._s(item.car_models_name) + "\n\t\t\t\t\t")])
	  }), _vm._v(" "), (_vm.goodsDetail.adapt_all_models == 1) ? _c('div', [_vm._v("适用全部机型")]) : _vm._e()], 2)], 1), _vm._v(" "), _c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 1
	    }
	  }, [_vm._v(" ")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 3
	    }
	  }, [_c('em', {
	    staticStyle: {
	      "color": "#FF0000"
	    }
	  }), _vm._v("banner图\n\t\t\t\t")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 20
	    }
	  }, [_c('ul', {
	    staticClass: "banner-list"
	  }, _vm._l((_vm.goodsDetail.bannerList), function(item) {
	    return _c('li', [_c('img', {
	      attrs: {
	        "src": _vm.ossUrl + item.banner_url
	      }
	    })])
	  }))])], 1), _vm._v(" "), _c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 1
	    }
	  }, [_vm._v(" ")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 3
	    }
	  }, [_c('em', {
	    staticStyle: {
	      "color": "#FF0000"
	    }
	  }), _vm._v("商品详图\n\t\t\t\t")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 20
	    }
	  }, [_c('ul', {
	    staticClass: "detail-list"
	  }, _vm._l((_vm.goodsDetail.detailList), function(item) {
	    return _c('li', [_c('img', {
	      attrs: {
	        "src": _vm.ossUrl + item.detail_url
	      }
	    }), _vm._v(" "), _c('span', [_vm._v(_vm._s(item.pic_desc))])])
	  }))])], 1), _vm._v(" "), _c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 1
	    }
	  }, [_vm._v(" ")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 3
	    }
	  }, [_c('em', {
	    staticStyle: {
	      "color": "#FF0000"
	    }
	  }), _vm._v("客服电话\n\t\t\t\t")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 20
	    }
	  }, [_vm._v(_vm._s(_vm.goodsDetail.tel))])], 1), _vm._v(" "), _c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 1
	    }
	  }, [_vm._v(" ")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 3
	    }
	  }, [_c('em', {
	    staticStyle: {
	      "color": "#FF0000"
	    }
	  }), _vm._v("商品参数\n\t\t\t\t")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 20
	    }
	  }, [_c('ul', {
	    staticClass: "param-list"
	  }, _vm._l((_vm.goodsDetail.infoList), function(item) {
	    return _c('li', [_c('span', [_c('em'), _vm._v("名称"), _c('span', [_vm._v(_vm._s(item.info_title))])]), _vm._v(" "), _c('span', [_c('em'), _vm._v("值"), _c('span', [_vm._v(_vm._s(item.info_content))])])])
	  }))])], 1), _vm._v(" "), _c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 1
	    }
	  }, [_vm._v(" ")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 3
	    }
	  }, [_c('em', {
	    staticStyle: {
	      "color": "#FF0000"
	    }
	  }), _vm._v("商品规格\n\t\t\t\t")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 20
	    }
	  }, [_c('ul', {
	    staticClass: "standard-list"
	  }, _vm._l((_vm.goodsDetail.standardList), function(item) {
	    return _c('li', [_c('span', [_c('em'), _vm._v("名称"), _c('span', [_vm._v(_vm._s(item.standard_must))]), _c('span', [_vm._v(_vm._s(item.optional_first))]), _c('span', [_vm._v(_vm._s(item.optional_second))])]), _vm._v(" "), _c('span', [_c('em'), _vm._v("价格"), _c('span', [_vm._v(_vm._s(item.price))])]), _vm._v(" "), _c('span', [_c('em'), _vm._v("库存"), _c('span', [_vm._v(_vm._s(item.store_num))])])])
	  }))])], 1)], 1), _vm._v(" "), _c('div', {
	    staticClass: "dialog-footer",
	    staticStyle: {
	      "text-align": "center"
	    },
	    slot: "footer"
	  }, [_c('el-button', {
	    on: {
	      "click": function($event) {
	        _vm.dialogGoodsDetailVisible = false
	      }
	    }
	  }, [_vm._v("关 闭")])], 1)])], 1)
	},staticRenderFns: []}
	module.exports.render._withStripped = true
	if (false) {
	  module.hot.accept()
	  if (module.hot.data) {
	     require("vue-hot-reload-api").rerender("data-v-485a572b", module.exports)
	  }
	}

/***/ })

});
//# sourceMappingURL=22.js.map