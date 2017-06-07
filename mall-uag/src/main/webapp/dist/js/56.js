webpackJsonp([56],{

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

/***/ 56:
/***/ (function(module, exports, __webpack_require__) {

	
	/* styles */
	__webpack_require__(57)
	
	var Component = __webpack_require__(13)(
	  /* script */
	  __webpack_require__(59),
	  /* template */
	  __webpack_require__(61),
	  /* scopeId */
	  null,
	  /* cssModules */
	  null
	)
	Component.options.__file = "/Users/xiangyangli/projects/hjh/jhd/mall-uag/src/main/webapp/app/components/orderManagement.vue"
	if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key !== "__esModule"})) {console.error("named exports are not supported in *.vue files.")}
	if (Component.options.functional) {console.error("[vue-loader] orderManagement.vue: functional components are not supported with templates, they should use render functions.")}
	
	/* hot reload */
	if (false) {(function () {
	  var hotAPI = require("vue-hot-reload-api")
	  hotAPI.install(require("vue"), false)
	  if (!hotAPI.compatible) return
	  module.hot.accept()
	  if (!module.hot.data) {
	    hotAPI.createRecord("data-v-6c53cd4a", Component.options)
	  } else {
	    hotAPI.reload("data-v-6c53cd4a", Component.options)
	  }
	})()}
	
	module.exports = Component.exports


/***/ }),

/***/ 57:
/***/ (function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(58);
	if(typeof content === 'string') content = [[module.id, content, '']];
	if(content.locals) module.exports = content.locals;
	// add the styles to the DOM
	var update = __webpack_require__(29)("1481c33a", content, false);
	// Hot Module Replacement
	if(false) {
	 // When the styles change, update the <style> tags
	 if(!content.locals) {
	   module.hot.accept("!!../../node_modules/css-loader/index.js?sourceMap!../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-6c53cd4a!../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./orderManagement.vue", function() {
	     var newContent = require("!!../../node_modules/css-loader/index.js?sourceMap!../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-6c53cd4a!../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./orderManagement.vue");
	     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
	     update(newContent);
	   });
	 }
	 // When the module is disposed, remove the <style> tags
	 module.hot.dispose(function() { update(); });
	}

/***/ }),

/***/ 58:
/***/ (function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(28)();
	// imports
	
	
	// module
	exports.push([module.id, "\n.orderManagement .el-table__body .el-button{\n\tdisplay:block; margin: 10px auto 0;\n}\n.orderManagement .el-table__body .el-button:last-child{\n\t margin: 10px auto 10px;\n}\n.orderManagement .el-dialog__body .el-form-item__content\n{\n\tline-height: 36px;\n}\n.orderManagement .dialogDelivery .el-dialog__body .el-form-item__content,\n.orderManagement .dialogProofsModify .el-dialog__body .el-form-item__content{\n\tline-height: 0;\n}\n\n", "", {"version":3,"sources":["/./app/components/orderManagement.vue?37f415c5"],"names":[],"mappings":";AAirBA;CACA,cAAA,CAAA,oBAAA;CACA;AACA;EACA,uBAAA;CACA;AACA;;CAEA,kBAAA;CACA;AACA;;CAEA,eAAA;CACA","file":"orderManagement.vue","sourcesContent":["<template>\n    <div class=\"orderManagement\">\n    \t\t<!-- 面包屑 -->\n\t\t<div class=\"hjh-breadcrumb\">\n\t\t\t<el-breadcrumb separator=\"/\">\n\t\t\t\t<el-breadcrumb-item :to=\"{ path: '/' }\">用户中心</el-breadcrumb-item>\n\t\t\t\t<el-breadcrumb-item>订单管理</el-breadcrumb-item>\n\t\t\t\t<el-breadcrumb-item>订单管理</el-breadcrumb-item>\n\t\t\t</el-breadcrumb>\t\n\t\t</div>\n\t\t<!-- 面包屑 end -->\n\t\t<!-- 列表查询表单 -->\n\t\t<div class=\"block-white\">\n\t\t\t<el-form :inline=\"true\" :model=\"searchForm\" ref=\"searchForm\" class=\"demo-form-inline searchForm\">\n\t\t\t\t<el-form-item>\n\t\t\t\t\t<el-input v-model=\"searchForm.productNoLike\" placeholder=\"请输入商品编号\" style=\"width:150px;\"></el-input>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item>\n\t\t\t\t\t<el-input v-model=\"searchForm.productTitleLike\" placeholder=\"请输入商品标题\" style=\"width:150px;\"></el-input>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item>\n\t\t\t\t\t<el-select v-model=\"searchForm.brandNameLike\" filterable placeholder=\"请选择商品品牌\" style=\"width: 150px;\">\n\t\t\t\t\t\t<el-option v-for=\"item in brandList\" :key=\"item.brand_name\" :label=\"item.brand_name\" :value=\"item.brand_name\">\n\t\t\t\t\t\t</el-option>\n\t\t\t\t\t</el-select>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item>\n\t\t\t\t\t<el-input v-model=\"searchForm.orderNoLike\" placeholder=\"请输入订单编号\" style=\"width:150px;\"></el-input>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item>\n\t\t\t\t\t<el-input v-model=\"searchForm.userMobileLike\" placeholder=\"买家联系方式\" style=\"width:150px;\"></el-input>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item>\n\t\t\t\t\t<el-col :span=\"11\">\n\t\t\t\t\t\t<el-input v-model=\"searchForm.inviteCodeBegin\" placeholder=\"邀请码区间查询开始\"></el-input>\n\t\t\t\t\t</el-col>\n\t\t\t\t\t<el-col class=\"line\" :span=\"2\" style=\"text-align: center;\">－</el-col>\n\t\t\t\t\t<el-col :span=\"11\">\n\t\t\t\t\t\t<el-input v-model=\"searchForm.inviteCodeEnd\" placeholder=\"邀请码区间查询结束\"></el-input>\n\t\t\t\t\t</el-col>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item>\n\t\t\t\t\t<el-select v-model=\"searchForm.payType\" placeholder=\"请选择支付方式\" style=\"width:150px;\">\n\t\t\t\t\t\t<el-option label=\"银行汇款\" value=\"2\"></el-option>\n\t\t\t\t\t\t<el-option label=\"支付宝\" value=\"1\"></el-option>\n\t\t\t\t\t\t<el-option label=\"微信\" value=\"0\"></el-option>\n\t\t\t\t\t</el-select>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item>\n\t\t\t\t\t<el-input v-model=\"searchForm.estimateDeliveryDescLike\" placeholder=\"预计发货日期\" style=\"width:150px;\"></el-input>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item>\n\t\t\t\t\t<el-date-picker v-model=\"deliveryDate\" type=\"datetimerange\" :picker-options=\"pickerOptions\" @change=\"deliveryDateChange\" placeholder=\"发货日期\" align=\"right\">\n\t\t\t\t\t</el-date-picker>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item>\n\t\t\t\t\t<el-date-picker v-model=\"createDate\" type=\"datetimerange\" :picker-options=\"pickerOptions\" @change=\"createDateChange\" placeholder=\"下单日期\" align=\"right\">\n\t\t\t\t\t</el-date-picker>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item>\n\t\t\t\t\t<el-button class=\"searchBtn\" type=\"primary\" @click=\"submitForm('searchForm')\">搜索</el-button>\n\t\t\t\t\t<el-button @click=\"resetForm('searchForm')\">清除</el-button>\n\t\t\t\t\t<el-button @click=\"exportTable\">导出</el-button>\n\t\t\t\t</el-form-item>\n\t\t\t</el-form>\n\t\t</div>\n\t\t<!-- 列表查询表单 end -->\n\t\t<div class=\"block-white\">\n\t\t\t<el-tabs v-model=\"activeName\" @tab-click=\"handleTabsClick\">\n\t\t\t\t<el-tab-pane v-for=\"item in tabs\" :label=\"item.title\" :name=\"item.name\"></el-tab-pane>\n\t\t\t</el-tabs>\n\t\t</div>\n\t\t<!-- 订单列表 -->\n\t\t<div class=\"block-white\">\n\t\t\t<el-table v-loading.body=\"loading\" :data=\"OrderList.items\" \n\t\t\t\tref=\"multipleTable\"\n\t\t\t\tborder stripe style=\"width: 100%\" max-height=\"550\">\n\t\t\t\t<el-table-column label=\"序号\" width=\"70\" align=\"center\"><template scope=\"scope\">{{scope.$index+1}}</template></el-table-column>\n\t\t\t\t<el-table-column label=\"商品信息\" align=\"center\" width=\"450\">\n\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t<div class=\"order-scope\">\n\t\t\t\t\t\t\t订单编号：<a @click=\"checkOrder(scope.row)\">{{scope.row.orderNo}}</a>\n\t\t\t\t\t\t\t订单时间：{{scope.row.createTime}}\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<ul class=\"productsList\">\n\t\t\t\t\t\t\t<li v-for=\"item in scope.row.productList\">\n\t\t\t\t\t\t\t\t<div class=\"product-pic\">\n\t\t\t\t\t\t\t\t\t<img :src=\"'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+item.pictureCode\">\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t<div class=\"product-info\" @click=\"checkProductDetail(item)\">\n\t\t\t\t\t\t\t\t\t<div class=\"product-name\">{{item.productName}}</div>\n\t\t\t\t\t\t\t\t\t<div class=\"product-id\">{{item.productId}}</div>\n\t\t\t\t\t\t\t\t\t<div class=\"product-brand\">\n\t\t\t\t\t\t\t\t\t\t<div class=\"fl\">品牌：{{item.brandName}}</div>\n\t\t\t\t\t\t\t\t\t\t<div class=\"fr\">类目：{{item.categoryName || item.orderItemList[0].categoryName}}</div>\n\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t</li>\n\t\t\t\t\t\t</ul>\n\t\t\t\t\t</template>\n\t\t\t\t</el-table-column>\n\t\t\t\t\n\t\t\t\t<el-table-column label=\"订单详情\" align=\"center\">\n\t\t\t\t\t<el-table-column prop=\"\" label=\"规格\" width=\"250\">\n\t\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t\t<div class=\"order-scope\">\n\t\t\t\t\t\t\t\t<div v-if=\"scope.row.orderStatus==1 && scope.row.payDeposit && !scope.row.payBalance\">\n\t\t\t\t\t\t\t\t\t<span v-if=\"scope.row.balanceDateCount>0\">余款延期：{{scope.row.balanceDateCount}}天</span>\n\t\t\t\t\t\t\t\t\t<span v-if=\"scope.row.balanceDateCount<=0\">定金未确认收款，请联系客服</span>\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t<div v-if=\"scope.row.orderStatus==2 && scope.row.payDeposit && !scope.row.payBalance\">\n\t\t\t\t\t\t\t\t\t<span v-if=\"scope.row.balanceDateCount>0\">{{scope.row.balanceDateCount}}天后可付款</span>\n\t\t\t\t\t\t\t\t\t<span v-if=\"scope.row.balanceDateCount<=0\" style=\"color:#f00;\">等待支付余款</span>\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<ul class=\"Especificaciones\" v-for=\"item in scope.row.productList\">\n\t\t\t\t\t\t\t\t<li v-for=\"_item in item.orderItemList\">\n\t\t\t\t\t\t\t\t\t<span>{{_item.productStandardMust}} | {{_item.productOptionalFirst}} | {{_item.productOptionalSecond}}</span>\n\t\t\t\t\t\t\t\t</li>\n\t\t\t\t\t\t\t</ul>\n\t\t\t\t\t\t</template>\n\t\t\t\t\t</el-table-column>\n\t\t\t\t\t<el-table-column prop=\"\" label=\"商品价格\" width=\"120\" align=\"center\" :show-overflow-tooltip=\"showtooltip\">\n\t\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t\t<div class=\"productPrice\" v-for=\"item in scope.row.productList\">\n\t\t\t\t\t\t\t\t<div v-for=\"_item in item.orderItemList\">\n\t\t\t\t\t\t\t\t\t{{_item.price.toFixed(2)}}/{{_item.unit}}\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</template>\n\t\t\t\t\t</el-table-column>\n\t\t\t\t\t<el-table-column prop=\"\" label=\"采购数量\" width=\"120\" align=\"center\" :show-overflow-tooltip=\"showtooltip\">\n\t\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t\t<div class=\"productPrice\" v-for=\"item in scope.row.productList\">\n\t\t\t\t\t\t\t\t<div v-for=\"_item in item.orderItemList\">\n\t\t\t\t\t\t\t\t\t<span style=\"color:#f00;\">{{_item.quantity}}</span>\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</template>\n\t\t\t\t\t</el-table-column>\n\t\t\t\t\t<el-table-column prop=\"\" label=\"金额\" align=\"center\" width=\"120\" :show-overflow-tooltip=\"showtooltip\">\n\t\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t\t<div class=\"productPrice\" v-for=\"item in scope.row.productList\">\n\t\t\t\t\t\t\t\t<div v-for=\"_item in item.orderItemList\">\n\t\t\t\t\t\t\t\t\t{{_item.amount.toFixed(2)}}\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</template>\n\t\t\t\t\t</el-table-column>\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column prop=\"transactionPayAmount\" label=\"订单总额\" align=\"center\" width=\"120\" :show-overflow-tooltip=\"showtooltip\">\n\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t<div style=\"color:#f00;\">{{scope.row.transactionPayAmount}}</div>\n\t\t\t\t\t</template>\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column prop=\"\" label=\"支付信息\" align=\"center\" width=\"200\" :show-overflow-tooltip=\"showtooltip\">\n\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t<div v-if=\"activeName!=='status1' && activeName!=='status2'\">\n\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t<div v-if=\"scope.row.transactionType==1\">\n\t\t\t\t\t\t\t\t<div>定金：{{(scope.row.orderStatus==1&&scope.row.payDeposit)?\"待确认\":(\"&yen;\"+scope.row.transactionDeposit)}}</div>\n\t\t\t\t\t\t\t\t<div v-if=\"!scope.row.payBalance\" style=\"color:#f00;\">\n\t\t\t\t\t\t\t\t\t待付余款：&yen;{{scope.row.transactionBalance||(scope.row.transactionPayAmount-scope.row.transactionDeposit).toFixed(2)}}\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t<div v-if=\"scope.row.transactionActualPayBalance && scope.row.payBalance\">\n\t\t\t\t\t\t\t\t\t余款：{{scope.row.transactionActualPayBalance}}\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t<div v-if=\"(scope.row.orderStatus==1||scope.row.orderStatus==2) && scope.row.payBalance\">\n\t\t\t\t\t\t\t\t\t余款：待确认\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<div v-if=\"scope.row.transactionType==0\">\n\t\t\t\t\t\t\t\t收款金额：{{\n\t\t\t\t\t\t\t\t!scope.row.payBalance ? \"待确认\" : (\"&yen;\"+(scope.row.transactionActualPayAmount||scope.row.transactionPayAmount))\n\t\t\t\t\t\t\t\t}}\n\t\t\t\t\t\t\t</div>\n\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</template>\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column prop=\"\" label=\"买家信息\" align=\"center\" width=\"300\" :show-overflow-tooltip=\"showtooltip\">\n\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t<div><a class=\"consigneeLink\" @click=\"checkBuyer(scope.row)\">{{scope.row.userName}}</a></div>\n\t\t\t\t\t\t<div>{{scope.row.consigneeMobile}}</div>\n\t\t\t\t\t\t<div>{{scope.row.inviteCode}}</div>\n\t\t\t\t\t</template>\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column prop=\"\" label=\"发货信息\" width=\"150\" align=\"center\" :show-overflow-tooltip=\"showtooltip\">\n\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t<div v-if=\"scope.row.estimateDeliveryDesc\">{{scope.row.estimateDeliveryDesc}}</div>\n\t\t\t\t\t\t<div v-if=\"scope.row.postageCalculation\">运费：&yen;{{scope.row.postage}}</div>\n\t\t\t\t\t\t<div><a class=\"consigneeLink\" @click=\"checkConsignee(scope.row)\">收货地址</a></div>\n\t\t\t\t\t\t<div v-if=\"scope.row.buyerComments\">\n\t\t\t\t\t\t\t<a class=\"consigneeLink\" @click=\"checkComment(scope.row)\">买家留言</a>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</template>\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column fixed=\"right\" label=\"操作\" align=\"center\" width=\"120\">\n\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t<div data-title=\"待核算\" v-if=\"scope.row.orderStatus==1 && !scope.row.postage && (activeName=='status1'||activeName=='status0')\">\n\t\t\t\t\t\t\t<el-button @click.native.prevent=\"accountingPostage(scope.row)\" type=\"info\">运费核算</el-button>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<div data-title=\"待付款\" v-if=\"scope.row.orderStatus==1 && scope.row.postage && !scope.row.payDeposit && !scope.row.payBalance && (activeName=='status2'||activeName=='status0')\">\n\t\t\t\t\t\t\t<el-button @click.native.prevent=\"accountingPostage(scope.row)\" type=\"warning\">修改运费</el-button>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<div data-title=\"已付定金－未确认收款\" v-if=\"scope.row.orderStatus==1 && scope.row.payDeposit && !scope.row.payBalance && (activeName=='status3'||activeName=='status0')\">\n\t\t\t\t\t\t\t<el-button @click.native.prevent=\"eventCheckProofs(scope.row)\" type=\"info\">支付凭证</el-button>\n\t\t\t\t\t\t\t<el-button @click.native.prevent=\"eventModifyProofs(scope.row)\" type=\"warning\">编辑凭证</el-button>\n\t\t\t\t\t\t\t<el-button @click.native.prevent=\"eventDeposit(scope.row)\" type=\"info\">确认收款</el-button>\n\t\t\t\t\t\t\t<el-button @click.native.prevent=\"eventCancelOrder(scope.row)\" type=\"danger\">取消订单</el-button>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<div data-title=\"已付定金－已确认收款\" v-if=\"scope.row.orderStatus==2 &&  scope.row.payDeposit && !scope.row.payBalance && (activeName=='status3'||activeName=='status0')\">\n\t\t\t\t\t\t\t<el-button @click.native.prevent=\"eventCheckProofs(scope.row)\" type=\"info\">支付凭证</el-button>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<div data-title=\"已付款\" v-if=\"(scope.row.orderStatus==1||scope.row.orderStatus==2) && scope.row.payBalance && (activeName=='status4'||activeName=='status0')\">\n\t\t\t\t\t\t\t<el-button @click.native.prevent=\"eventCheckProofs(scope.row)\" type=\"info\">支付凭证</el-button>\n\t\t\t\t\t\t\t<el-button @click.native.prevent=\"eventModifyProofs(scope.row)\" type=\"warning\">编辑凭证</el-button>\n\t\t\t\t\t\t\t<el-button @click.native.prevent=\"eventBalance(scope.row)\" type=\"info\">确认收款</el-button>\n\t\t\t\t\t\t\t<el-button @click.native.prevent=\"eventCancelOrder(scope.row)\" type=\"danger\" v-if=\"scope.row.transactionType==0\">取消订单</el-button>\n\t\t\t\t\t\t</div>\n\n\t\t\t\t\t\t<div data-title=\"退款中\" v-if=\"scope.row.orderStatus==9\">\n\t\t\t\t\t\t\t<el-button @click.native.prevent=\"eventCheckProofs(scope.row)\" type=\"info\">支付凭证</el-button>\n\t\t\t\t\t\t\t<el-button @click.native.prevent=\"eventRefundOrderComplete(scope.row)\" type=\"info\">退款确认</el-button>\n\t\t\t\t\t\t</div>\n\n\t\t\t\t\t\t<div data-title=\"等待卖家发货\" v-if=\"scope.row.orderStatus==3\">\n\t\t\t\t\t\t\t<el-button @click.native.prevent=\"eventDelivery(scope.row)\" type=\"info\">发货</el-button>\n\t\t\t\t\t\t</div>\n\n\t\t\t\t\t\t<div data-title=\"卖家已发货\" v-if=\"scope.row.orderStatus==4\">\n\t\t\t\t\t\t\t<el-button @click.native.prevent=\"eventGetLogistics(scope.row)\" type=\"info\">发货信息</el-button>\n\t\t\t\t\t\t\t<el-button @click.native.prevent=\"eventReceived(scope.row)\" type=\"info\">确认到货</el-button>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<div data-title=\"成功的订单\" v-if=\"scope.row.orderStatus==5 || scope.row.orderStatus==6\">\n\t\t\t\t\t\t\t<el-button @click.native.prevent=\"eventCheckProofs(scope.row)\" type=\"info\">支付凭证</el-button>\n\t\t\t\t\t\t\t<el-button @click.native.prevent=\"eventGetLogistics(scope.row)\" type=\"info\">发货信息</el-button>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<div data-title=\"关闭的订单\" v-if=\"scope.row.orderStatus==11 || scope.row.orderStatus==12\">\n\t\t\t\t\t\t\t<el-button @click.native.prevent=\"eventCheckRefund(scope.row)\" type=\"info\">退款信息</el-button>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</template>\n\t\t\t\t</el-table-column>\n\t\t\t</el-table>\n\t\t</div>\n\t\t<!-- 订单列表 end -->\n\t\t<!-- 翻页组件 -->\n\t\t<div class=\"block-white\">\n\t\t\t<el-pagination\n\t\t      @current-change=\"handleCurrentChange\"\n\t\t      :current-page=\"page\"\n\t\t      :page-size=\"pageSize\"\n\t\t      layout=\"total, prev, pager, next, jumper\"\n\t\t      :total=\"OrderList.total\">\n\t\t    </el-pagination>\n\t\t</div>\n\t\t<!-- 翻页组件 end -->\n\n\t\t<!-- 查看订单详情 -->\n\t\t<el-dialog title=\"订单详情\" v-model=\"dialogOrderDetailVisible\">\n\t\t\t<div style=\"height: 570px; overflow: hidden; overflow-y: auto; padding-right: 20px; line-height:30px;\">\n\t\t\t\t<el-row>\n\t\t\t\t\t<el-col :span=\"12\">订单编号：{{dialogOrderDetailData.orderNo}}</el-col>\n\t\t\t\t\t<el-col :span=\"12\" style=\"text-align:right\">订单状态：{{dialogOrderDetailData.orderStatusDescription}}</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<el-row>\n\t\t\t\t\t<el-col :span=\"8\">买家：{{dialogOrderDetailData.consigneeName}}</el-col>\n\t\t\t\t\t<el-col :span=\"8\" style=\"text-align:center;\">联系电话：{{dialogOrderDetailData.consigneeMobile}}</el-col>\n\t\t\t\t\t<el-col :span=\"8\" style=\"text-align:right;\">邀请码：{{dialogOrderDetailData.inviteCode}}</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<el-collapse v-model=\"collapseActiveNames\" style=\"margin-top:20px;\">\n\t\t\t\t\t<el-collapse-item title=\"订单明细\" name=\"1\">\n\t\t\t\t\t\t<div v-for=\"item in dialogOrderDetailData.productList\" style=\"padding-bottom:20px; overflow:hidden;\">\n\t\t\t\t\t\t\t<div>{{item.productName}}</div>\n\t\t\t\t\t\t\t<div style=\"color:#999;\">{{item.productId}}</div>\n\t\t\t\t\t\t\t<div>\n\t\t\t\t\t\t\t\t<span>品牌：{{item.brandName}}</span>\n\t\t\t\t\t\t\t\t<span style=\"margin-left:30px;\">类目：{{item.categoryName}}</span>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<el-table :data=\"item.orderItemList\" border stripe show-summary sum-text=\"商品总价\" style=\"width: 100%\">\n\t\t\t\t\t\t\t\t<el-table-column prop=\"productStandardMust\" label=\"规格\">\n\t\t\t\t\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t\t\t\t\t<span>{{scope.row.productStandardMust}} | {{scope.row.productOptionalFirst}} | {{scope.row.productOptionalSecond}}</span>\n\t\t\t\t\t\t\t\t\t</template>\n\t\t\t\t\t\t\t\t</el-table-column>\n\t\t\t\t\t\t\t\t<el-table-column prop=\"price\" label=\"商品价格(元)\">\n\t\t\t\t\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t\t\t\t\t{{scope.row.price.toFixed(2)}}/{{scope.row.unit}}\n\t\t\t\t\t\t\t\t\t</template>\n\t\t\t\t\t\t\t\t</el-table-column>\n\t\t\t\t\t\t\t\t<el-table-column prop=\"quantity\" label=\"采购数量\">\n\t\t\t\t\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t\t\t\t\t{{scope.row.quantity}}\n\t\t\t\t\t\t\t\t\t</template>\n\t\t\t\t\t\t\t\t</el-table-column>\n\t\t\t\t\t\t\t\t<el-table-column prop=\"amount\" label=\"金额(元)\">\n\t\t\t\t\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t\t\t\t\t{{scope.row.amount.toFixed(2)}}\n\t\t\t\t\t\t\t\t\t</template>\n\t\t\t\t\t\t\t\t</el-table-column>\n\t\t\t\t\t\t\t</el-table>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<el-row>\n\t\t\t\t\t\t\t<el-col :span=\"4\">\n\t\t\t\t\t\t\t运费：\n\t\t\t\t\t\t\t<span v-if=\"!dialogOrderDetailData.postage\" style='color:#f00;'>待核算</span>\n\t\t\t\t\t\t\t<span v-if=\"dialogOrderDetailData.postage\">{{dialogOrderDetailData.postage}}</span>\n\t\t\t\t\t\t\t</el-col>\n\t\t\t\t\t\t\t<el-col :span=\"20\" style=\"text-align:center;\">订单总额：{{dialogOrderDetailData.transactionAmount}}</el-col>\n\t\t\t\t\t\t</el-row>\n\t\t\t\t\t</el-collapse-item>\n\t\t\t\t\t<el-collapse-item title=\"待付款明细\" name=\"2\">\n\n\t\t\t\t\t</el-collapse-item>\n\t\t\t\t\t<el-collapse-item title=\"运费核算\" name=\"3\">\n\n\t\t\t\t\t</el-collapse-item>\n\t\t\t\t</el-collapse>\n\t\t\t</div>\n\t\t\t<div slot=\"footer\" class=\"dialog-footer\" style=\"text-align: center;\">\n\t\t\t\t<el-button @click=\"dialogOrderDetailVisible = false\">关 闭</el-button>\n\t\t\t</div>\n\t\t</el-dialog>\n\n\t\t<!-- 查看买家信息 -->\n\t\t<el-dialog title=\"会员信息\" v-model=\"dialogBuyerVisible\">\n\t\t\t<el-row style=\"height:40px;\">\n\t\t\t\t<el-col :span=\"12\">用户编号：{{dialogBuyerData.client_code}}</el-col>\n\t\t\t\t<el-col :span=\"12\">邀请码：{{dialogBuyerData.invite_code}}</el-col>\n\t\t\t</el-row>\n\t\t\t<el-collapse v-model=\"buyActiveNames\">\n\t\t\t\t<el-collapse-item title=\"单位信息\" name=\"1\">\n\t\t\t\t\t<div>单位名称：{{dialogBuyerData.enterprise_name}}</div>\n\t\t\t\t\t<div>单位简称：{{dialogBuyerData.enterprise_short_name}}</div>\n\t\t\t\t\t<div>地址：{{dialogBuyerData.enterprise_address}}</div>\n\t\t\t\t\t<div>主营：{{dialogBuyerData.major_business}}</div>\n\t\t\t\t\t<div>联系人：{{dialogBuyerData.enterprise_linkman}}</div>\n\t\t\t\t\t<div>联系人电话：{{dialogBuyerData.enterprise_tel}}</div>\n\t\t\t\t</el-collapse-item>\n\t\t\t\t<el-collapse-item title=\"用户信息\" name=\"2\">\n\t\t\t\t\t<div>昵称：{{dialogBuyerData.nick_name}}</div>\n\t\t\t\t\t<div>性别：{{dialogBuyerData.sex}}</div>\n\t\t\t\t\t<div>用户地区：{{dialogBuyerData.address}}</div>\n\t\t\t\t</el-collapse-item>\n\t\t\t\t<el-collapse-item title=\"注册信息\" name=\"3\">\n\t\t\t\t\t<div>注册时间：{{dialogBuyerData.init_date}}</div>\n\t\t\t\t\t<div style=\"color:#f00;\">账号状态：{{dialogBuyerData.status}}</div>\n\t\t\t\t\t<div>注册手机：{{dialogBuyerData.mobile_tel}}</div>\n\t\t\t\t</el-collapse-item>\n\t\t\t</el-collapse>\n\t\t</el-dialog>\n\n\t\t<!-- 查看收货地址 -->\n\t\t<el-dialog title=\"收货地址\" v-model=\"dialogConsigneeVisible\">\n\t\t\t<el-form :model=\"dialogConsigneeData\" label-width=\"100px\" class=\"demo-form-inline\">\n\t\t\t\t<el-form-item label=\"收货人：\">{{dialogConsigneeData.consigneeName}}</el-form-item>\n\t\t\t\t<el-form-item label=\"收货地址：\">{{dialogConsigneeData.consigneeAddress}}</el-form-item>\n\t\t\t\t<el-form-item label=\"联系电话：\">{{dialogConsigneeData.consigneeMobile}}</el-form-item>\n\t\t\t</el-form>\n\t\t</el-dialog>\n\n\t\t<!-- 查看买家留言 -->\n\t\t<el-dialog title=\"买家留言\" v-model=\"dialogCommentVisible\">\n\t\t\t<el-form :model=\"dialogCommentData\" label-width=\"100px\" class=\"demo-form-inline\">\n\t\t\t\t<el-row>\n\t\t\t\t\t<el-col :span=\"12\">\n\t\t\t\t\t\t<el-form-item label=\"订单编号：\">{{dialogCommentData.orderNo}}</el-form-item>\n\t\t\t\t\t</el-col>\n\t\t\t\t\t<el-col :span=\"12\">\n\t\t\t\t\t\t<el-form-item label=\"邀请码：\">{{dialogCommentData.inviteCode}}</el-form-item>\n\t\t\t\t\t</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<div style=\"padding:20px\">\n\t\t\t\t\t{{dialogCommentData.buyerComments}}\n\t\t\t\t</div>\n\t\t\t</el-form>\n\t\t</el-dialog>\n\n\t\t<!-- 查看商品详情 -->\n\t\t<el-dialog title=\"商品信息\" v-model=\"dialogGoodsDetailVisible\">\n\t\t\t<div class=\"goodsDetail\" style=\"height: 450px; overflow: hidden; overflow-y: auto;\">\n\t\t\t\t<el-row>\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\n\t\t\t\t\t<el-col :span=\"3\">商品名称</el-col>\n\t\t\t\t\t<el-col :span=\"20\">{{dialogGoodsDetailData.goods_name}}</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<el-row>\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\n\t\t\t\t\t<el-col :span=\"3\">类目</el-col>\n\t\t\t\t\t<el-col :span=\"20\">{{dialogGoodsDetailData.third_category_name}}</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<el-row>\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\n\t\t\t\t\t<el-col :span=\"3\">品牌</el-col>\n\t\t\t\t\t<el-col :span=\"20\">{{dialogGoodsDetailData.brand_name}}</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<el-row>\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\n\t\t\t\t\t<el-col :span=\"3\">计量单位</el-col>\n\t\t\t\t\t<el-col :span=\"20\">{{dialogGoodsDetailData.unit_name}}</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<el-row>\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\n\t\t\t\t\t<el-col :span=\"3\">占位图</el-col>\n\t\t\t\t\t<el-col :span=\"20\">\n\t\t\t\t\t\t<img v-if=\"dialogGoodsDetailData.ad_url\" style=\"display: inline-block; width: 80px; height: 30px;\" :src=\"ossUrl+dialogGoodsDetailData.ad_url\">\n\t\t\t\t\t\t<span v-if=\"!dialogGoodsDetailData.ad_url\">无</span>\n\t\t\t\t\t</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<el-row>\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\n\t\t\t\t\t<el-col :span=\"3\">适用机型</el-col>\n\t\t\t\t\t<el-col :span=\"20\">\n\t\t\t\t\t\t<span class=\"carModelList\" v-for=\"item in dialogGoodsDetailData.carModelList\">\n\t\t\t\t\t\t\t{{item.car_brand_name}} | {{item.car_type}} | {{item.car_models_name}}\n\t\t\t\t\t\t</span>\n\t\t\t\t\t\t<div v-if=\"dialogGoodsDetailData.adapt_all_models==1\">适用全部机型</div>\n\t\t\t\t\t</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<el-row>\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\n\t\t\t\t\t<el-col :span=\"3\">banner图</el-col>\n\t\t\t\t\t<el-col :span=\"20\">\n\t\t\t\t\t\t<ul class=\"banner-list\">\n\t\t\t\t\t\t\t<li v-for=\"item in dialogGoodsDetailData.bannerList\">\n\t\t\t\t\t\t\t\t<img :src=\"ossUrl+item.banner_url\">\n\t\t\t\t\t\t\t</li>\n\t\t\t\t\t\t</ul>\n\t\t\t\t\t</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<el-row>\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\n\t\t\t\t\t<el-col :span=\"3\">商品详图</el-col>\n\t\t\t\t\t<el-col :span=\"20\">\n\t\t\t\t\t\t<ul class=\"detail-list\">\n\t\t\t\t\t\t\t<li v-for=\"item in dialogGoodsDetailData.detailList\">\n\t\t\t\t\t\t\t\t<img :src=\"ossUrl+item.detail_url\">\n\t\t\t\t\t\t\t\t<span>{{item.pic_desc}}</span>\n\t\t\t\t\t\t\t</li>\n\t\t\t\t\t\t</ul>\n\t\t\t\t\t</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<el-row>\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\n\t\t\t\t\t<el-col :span=\"3\">客服电话</el-col>\n\t\t\t\t\t<el-col :span=\"10\">{{dialogGoodsDetailData.tel}}</el-col>\n\t\t\t\t\t<el-col :span=\"10\">预付定金：{{dialogGoodsDetailData.font_money_rate}}%</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<el-row>\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\n\t\t\t\t\t<el-col :span=\"3\">商品介绍</el-col>\n\t\t\t\t\t<el-col :span=\"20\">\n\t\t\t\t\t\t<ul class=\"param-list\">\n\t\t\t\t\t\t\t<li v-for=\"item in dialogGoodsDetailData.infoList\">\n\t\t\t\t\t\t\t\t<span>名称<span>{{item.info_title}}</span></span>\n\t\t\t\t\t\t\t\t<span>值<span>{{item.info_content}}</span></span>\n\t\t\t\t\t\t\t</li>\n\t\t\t\t\t\t</ul>\n\t\t\t\t\t</el-col>\n\t\t\t\t</el-row>\n\t\t\t</div>\n\t\t\t<div slot=\"footer\" class=\"dialog-footer\" style=\"text-align: center;\">\n\t\t\t\t<el-button @click=\"dialogGoodsDetailVisible = false\">关 闭</el-button>\n\t\t\t</div>\n\t\t</el-dialog>\n\n\t\t<!-- 支付凭证 -->\n\t\t<el-dialog :title=\"isProofsModify?'编辑凭证':'支付凭证'\" v-model=\"dialogProofsVisible\">\n\t\t\t<div v-for=\"proofData in dialogProofsData\">\n\t\t\t\t<el-form :model=\"proofData\" :ref=\"proofData\" label-width=\"120px\" class=\"demo-form-inline\">\n\t\t\t\t\t<el-form-item label=\"付  款  人：\">\n\t\t\t\t\t\t<el-input type=\"text\" v-model=\"proofData.paymentUserName\" :readonly=\"!isProofsModify\" placeholder=\"付款人\" style=\"width:100%;\"></el-input>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"付款银行：\">\n\t\t\t\t\t\t<el-input type=\"text\" v-model=\"proofData.bankAccount\" :readonly=\"!isProofsModify\" placeholder=\"付款银行\" style=\"width:100%;\"></el-input>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"付款账号：\">\n\t\t\t\t\t\t<el-input type=\"text\" v-model=\"proofData.paymentAccount\" :readonly=\"!isProofsModify\" placeholder=\"付款账号\" style=\"width:100%;\"></el-input>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"付款金额：\">\n\t\t\t\t\t\t<el-input type=\"number\" v-model=\"proofData.amount\" :readonly=\"!isProofsModify\" placeholder=\"付款金额\" style=\"width:100%;\"></el-input>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item>\n\t\t\t\t\t\t<div v-if=\"proofData.paymentProofSnapshot\">\n\t\t\t\t\t\t\t<img style=\"max-width:100%;\" :src=\"'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+proofData.paymentProofSnapshot\">\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<div v-if=\"!proofData.paymentProofSnapshot\">未上传凭证</div>\n\t\t\t\t\t\t\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"上传时间：\">\n\t\t\t\t\t\t{{proofData.createDate}}\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item>\n\t\t\t\t\t\t<el-button type=\"info\" v-if=\"isProofsModify\">修改</el-button>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t</el-form>\t\n\t\t\t</div>\n\t\t</el-dialog>\n\n\t\t<!-- 编辑支付凭证 -->\n\t\t<el-dialog :title=\"isProofsModify?'编辑凭证':'支付凭证'\" v-model=\"dialogProofsModifyVisible\" class=\"dialogProofsModify\">\n\t\t\t\n\t\t\t\t<el-form v-if=\"dialogProofsData[0]\" :model=\"dialogProofsData[0]\" :ref=\"dialogProofsData[0]\" label-width=\"120px\" class=\"demo-form-inline\">\n\t\t\t\t\t<el-form-item label=\"付  款  人：\">\n\t\t\t\t\t\t<el-input type=\"text\" v-model=\"dialogProofsData[0].paymentUserName\" :minlength=\"1\" :maxlength=\"10\" :readonly=\"!isProofsModify\" placeholder=\"付款人\" style=\"width:100%;\"></el-input>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"付款银行：\">\n\t\t\t\t\t\t<el-input type=\"text\" v-model=\"dialogProofsData[0].bankAccount\" :maxlength=\"30\" :readonly=\"!isProofsModify\" placeholder=\"付款银行\" style=\"width:100%;\"></el-input>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"付款账号：\">\n\t\t\t\t\t\t<el-input type=\"text\" v-model=\"dialogProofsData[0].paymentAccount\" :minlength=\"1\" :maxlength=\"20\" :readonly=\"!isProofsModify\" placeholder=\"付款账号\" style=\"width:100%;\"></el-input>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"付款金额：\">\n\t\t\t\t\t\t<el-input type=\"number\" v-model=\"dialogProofsData[0].amount\" :maxlength=\"10\" :readonly=\"!isProofsModify\" placeholder=\"付款金额\" style=\"width:100%;\"></el-input>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"凭证图片：\">\n\t\t\t\t\t\t<el-row>\n\t\t\t\t\t\t\t<el-col :span=\"11\">\n\t\t\t\t\t\t\t\t<el-input v-model=\"dialogProofsData[0].paymentProofSnapshot\" type=\"hidden\"></el-input>\n\t\t\t\t\t\t\t\t<el-upload\n\t\t\t\t\t\t\t\t  class=\"upload-demo\"\n\t\t\t\t\t\t\t\t  action=\"https://jsonplaceholder.typicode.com/posts/\"\n\t\t\t\t\t\t\t\t  :auto-upload=\"false\"\n\t\t\t\t\t\t\t\t  :on-change=\"handleFileChange\"\n\t\t\t\t\t\t\t\t  :on-remove=\"handleFileRemove\"\n\t\t\t\t\t\t\t\t  :file-list=\"proofs0List\">\n\t\t\t\t\t\t\t\t  <el-button size=\"small\" type=\"primary\">{{!proofs0List.length?\"上传凭证\":\"修改\"}}</el-button>\n\t\t\t\t\t\t\t\t</el-upload>\n\t\t\t\t\t\t\t</el-col>\n\t\t\t\t\t\t\t<el-col :span=\"1\"></el-col>\n\t\t\t\t\t\t\t<el-col :span=\"12\">\n\t\t\t\t\t\t\t\t<img style=\"max-width:100%;\" :src=\"dialogProofsData[0].priviewSnapshot\">\n\t\t\t\t\t\t\t</el-col>\n\t\t\t\t\t\t</el-row>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"上传时间：\">\n\t\t\t\t\t\t<div style=\"line-height:36px;\">{{dialogProofsData[0].createDate}}</div>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t</el-form>\n\t\t\t\t<el-form v-if=\"dialogProofsData[1]\" :model=\"dialogProofsData[1]\" :ref=\"dialogProofsData[1]\" label-width=\"120px\" class=\"demo-form-inline\">\n\t\t\t\t\t<el-form-item label=\"付  款  人：\">\n\t\t\t\t\t\t<el-input type=\"text\" v-model=\"dialogProofsData[1].paymentUserName\" :readonly=\"!isProofsModify\" placeholder=\"付款人\" style=\"width:100%;\"></el-input>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"付款银行：\">\n\t\t\t\t\t\t<el-input type=\"text\" v-model=\"dialogProofsData[1].bankAccount\" :readonly=\"!isProofsModify\" placeholder=\"付款银行\" style=\"width:100%;\"></el-input>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"付款账号：\">\n\t\t\t\t\t\t<el-input type=\"text\" v-model=\"dialogProofsData[1].paymentAccount\" :readonly=\"!isProofsModify\" placeholder=\"付款账号\" style=\"width:100%;\"></el-input>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"付款金额：\">\n\t\t\t\t\t\t<el-input type=\"number\" v-model=\"dialogProofsData[1].amount\" :readonly=\"!isProofsModify\" placeholder=\"付款金额\" style=\"width:100%;\"></el-input>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"凭证图片：\">\n\t\t\t\t\t\t<el-row>\n\t\t\t\t\t\t\t<el-col :span=\"11\" style=\"line-height:0;\">\n\t\t\t\t\t\t\t\t<el-input v-model=\"dialogProofsData[1].paymentProofSnapshot\" type=\"hidden\"></el-input>\n\t\t\t\t\t\t\t\t<el-upload\n\t\t\t\t\t\t\t\t  class=\"upload-demo\"\n\t\t\t\t\t\t\t\t  action=\"https://jsonplaceholder.typicode.com/posts/\"\n\t\t\t\t\t\t\t\t  :auto-upload=\"false\"\n\t\t\t\t\t\t\t\t  :on-change=\"handleFileChange1\"\n\t\t\t\t\t\t\t\t  :on-remove=\"handleFileRemove1\"\n\t\t\t\t\t\t\t\t  :file-list=\"proofs1List\">\n\t\t\t\t\t\t\t\t  <el-button size=\"small\" type=\"primary\">{{!proofs1List.length?\"上传凭证\":\"修改\"}}</el-button>\n\t\t\t\t\t\t\t\t</el-upload>\n\t\t\t\t\t\t\t</el-col>\n\t\t\t\t\t\t\t<el-col :span=\"1\"></el-col>\n\t\t\t\t\t\t\t<el-col :span=\"12\">\n\t\t\t\t\t\t\t\t<img style=\"max-width:100%;\" :src=\"dialogProofsData[1].priviewSnapshot\">\n\t\t\t\t\t\t\t</el-col>\n\t\t\t\t\t\t</el-row>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"上传时间：\" class=\"proofsUploadDate\">\n\t\t\t\t\t\t<div style=\"line-height:36px;\">{{dialogProofsData[1].createDate}}</div>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t</el-form>\t\n\t\t\t\t<div style=\"text-align:center;\">\n\t\t\t\t\t<el-button type=\"primary\" @click=\"handleModifyProofs(dialogProofsData)\">保存</el-button>\n\t\t\t\t</div>\n\t\t</el-dialog>\n\n\t\t<!-- 运费核算 -->\n\t\t<el-dialog :title=\"this.activeName=='status0'?'运费核算':'修改运费'\" v-model=\"postageVisible\">\n\t\t\t<el-form :model=\"dialogpostageData\" :ref=\"dialogpostageData\" label-width=\"120px\" class=\"demo-form-inline\">\n\t\t\t\t<el-form-item label=\"订单编号\">\n\t\t\t\t\t{{dialogpostageData.orderNo}}\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"运费(元)\" prop=\"postage\" :rules=\"{ required: true, message: '请设置运费' }\">\n\t\t\t\t\t<el-input type=\"number\" v-model=\"dialogpostageData.postage\" placeholder=\"运费\" style=\"width:150px;\"></el-input>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"余款延期(天)\" prop=\"balanceDateCount\" v-if=\"dialogpostageData.transactionType==1\" :rules=\"{ required: true, message: '请设填写余款延期天数' }\">\n\t\t\t\t\t<el-input type=\"number\" v-model=\"dialogpostageData.balanceDateCount\" placeholder=\"余款延期天数\" style=\"width:150px;\"></el-input>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"预计发货日期\" prop=\"estimateDeliveryDesc\" :rules=\"[{ required: true, message: '请填写预计发货日期' },{ min: 1,max:20, message: '发货日期1-20字符' }]\">\n\t\t\t\t\t\n\t\t\t\t\t<el-input v-model=\"dialogpostageData.estimateDeliveryDesc\" :maxlength=\"20\" placeholder=\"填写预计发货日期\" style=\"width:300px;\"></el-input>\n\t\t\t\t</el-form-item>\n\t\t\t</el-form>\n\t\t\t<div slot=\"footer\" class=\"dialog-footer\" style=\"text-align: center;\">\n\t\t\t\t<el-button type=\"primary\" @click=\"postageAndDeliveryDate(dialogpostageData)\">保存</el-button>\n\t\t\t\t<el-button @click=\"postageVisible = false\">关 闭</el-button>\n\t\t\t</div>\n\t\t</el-dialog>\n\n\t\t<!-- 退款确认 -->\n\t\t<el-dialog title=\"你确定退款吗？\" v-model=\"dialogRefundOrderVisible\">\n\t\t\t<el-form :model=\"dialogRefundOrderData\" :ref=\"dialogRefundOrderData\" label-width=\"120px\" class=\"demo-form-inline\">\n\t\t\t\t<div style=\"color:#f00; text-align:center; padding-bottom:20px;\">请与买家做好确认，并输入实际退款金额</div>\n\t\t\t\t<el-form-item label=\"退款金额\">\n\t\t\t\t\t<el-input type=\"number\" v-model=\"dialogRefundOrderData.refundingAmount\" placeholder=\"输入退款金额\" style=\"width:150px;\"></el-input>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"退款说明\">\n\t\t\t\t\t<el-input type=\"text\" v-model=\"dialogRefundOrderData.refundingExplain\" :minlength=\"1\" :maxlength=\"20\" placeholder=\"输入退款说明\" style=\"width:100%;\"></el-input>\n\t\t\t\t</el-form-item>\n\t\t\t</el-form>\n\t\t\t<div slot=\"footer\" class=\"dialog-footer\" style=\"text-align: center;\">\n\t\t\t\t<el-button type=\"primary\" @click=\"handleRefundOrderComplete(dialogpostageData)\" :disabled=\"dialogRefundOrderData.refundingAmount&&dialogRefundOrderData.refundingExplain\">确认退款</el-button>\n\t\t\t\t<el-button @click=\"dialogRefundOrderVisible = false\">关 闭</el-button>\n\t\t\t</div>\n\t\t</el-dialog>\n\n\t\t<!-- 发货 -->\n\t\t<el-dialog title=\"发货信息\" v-model=\"deliveryVisible\" class=\"dialogDelivery\">\n\t\t\t<el-form :model=\"dialogDeliveryData\" :ref=\"dialogDeliveryData\" label-width=\"120px\" class=\"demo-form-inline\">\n\t\t\t\t<el-form-item label=\"订单编号\">\n\t\t\t\t\t<el-input type=\"text\" v-model=\"dialogDeliveryData.orderNo\" disabled placeholder=\"\" style=\"width:200px;\"></el-input>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"联系人\">\n\t\t\t\t\t<el-input type=\"text\" v-model=\"dialogDeliveryData.senderName\" placeholder=\"\" style=\"width:200px;\"></el-input>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"联系电话\">\n\t\t\t\t\t<el-input type=\"text\" v-model=\"dialogDeliveryData.senderMobile\" placeholder=\"\" style=\"width:200px;\"></el-input>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"物流名称\" prop=\"logisticsCompany\" :rules=\"[{ required: true, message: '请填写物流名称' },{min:2,max:10, message: '请填写物流名称' }]\">\n\t\t\t\t\t<el-input type=\"text\" v-model=\"dialogDeliveryData.logisticsCompany\" :minlength=\"2\" :maxlength=\"10\" placeholder=\"\" style=\"width:200px;\"></el-input>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"运单号\" >\n\t\t\t\t\t<el-input type=\"text\" v-model=\"dialogDeliveryData.logisticsNo\" placeholder=\"\" style=\"width:200px;\"></el-input>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"发货时间\" prop=\"deliveryDate\" :rules=\"{ required: true, message: '请填写发货时间' }\">\n\t\t\t\t\t<el-date-picker id=\"deliveryDate\" v-model=\"dialogDeliveryData.deliveryDate\" format=\"yyyy-MM-dd HH:mm:ss\" type=\"datetime\" placeholder=\"请填写发货时间\"></el-date-picker>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"运单照片\">\n\t\t\t\t\t<el-input v-model=\"dialogDeliveryData.logisticsNoteSnapshot\" type=\"hidden\"></el-input>\n\t\t\t\t\t\t<el-upload\n\t\t\t\t\t\t  class=\"upload-demo\"\n\t\t\t\t\t\t  action=\"https://jsonplaceholder.typicode.com/posts/\"\n\t\t\t\t\t\t  :auto-upload=\"false\"\n\t\t\t\t\t\t  :on-change=\"handleLogisticsNoteSnapshotChange\"\n\t\t\t\t\t\t  :on-remove=\"handleLogisticsNoteSnapshotRemove\"\n\t\t\t\t\t\t  :file-list=\"LogisticsNoteSnapshotList\">\n\t\t\t\t\t\t  <el-button size=\"small\" type=\"primary\">点击上传</el-button>\n\t\t\t\t\t\t</el-upload>\n\t\t\t\t</el-form-item>\n\t\t\t\t\n\t\t\t</el-form>\n\t\t\t<div slot=\"footer\" class=\"dialog-footer\" style=\"text-align: center;\">\n\t\t\t\t<el-button type=\"primary\" @click=\"handleDelivery(dialogDeliveryData)\">保存</el-button>\n\t\t\t\t<el-button @click=\"deliveryVisible = false\">关 闭</el-button>\n\t\t\t</div>\n\t\t</el-dialog>\n\n\t\t<!-- 查看发货信息 -->\n\t\t<el-dialog title=\"发货信息\" v-model=\"dialogLogisticsVisible\">\n\t\t\t<el-form :model=\"dialogLogisticsData\" label-width=\"100px\" class=\"demo-form-inline\">\n\t\t\t\t<el-form-item label=\"操作员：\">{{dialogLogisticsData.createUserName}}</el-form-item>\n\t\t\t\t<el-form-item label=\"操作时间：\">{{dialogLogisticsData.createDate}}</el-form-item>\n\t\t\t\t<el-form-item label=\"联系人：\">{{dialogLogisticsData.senderName}}</el-form-item>\n\t\t\t\t<el-form-item label=\"联系电话：\">{{dialogLogisticsData.senderMobile}}</el-form-item>\n\t\t\t\t<el-form-item label=\"物流名称：\">{{dialogLogisticsData.logisticsCompany}}</el-form-item>\n\t\t\t\t<el-form-item label=\"运单号：\">{{dialogLogisticsData.logisticsNo}}</el-form-item>\n\t\t\t\t<el-form-item label=\"发货时间：\">{{dialogLogisticsData.deliveryDate}}</el-form-item>\n\t\t\t\t<el-form-item label=\"运单照片：\">\n\t\t\t\t\t<img style=\"max-width:100%;\" :src=\"'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+dialogLogisticsData.logisticsNoteSnapshot\">\n\t\t\t\t</el-form-item>\n\t\t\t</el-form>\n\t\t</el-dialog>\n\n\t\t<!-- 查看退款信息 -->\n\t\t<el-dialog title=\"退款信息\" v-model=\"dialogRefundInfoVisible\">\n\t\t\t<el-form :model=\"dialogRefundInfoData\" label-width=\"100px\" class=\"demo-form-inline\">\n\t\t\t\t<el-form-item label=\"退款金额：\">{{Number(dialogRefundInfoData.refundingAmount).toFixed(2)}}元</el-form-item>\n\t\t\t\t<el-form-item label=\"退款说明：\">{{dialogRefundInfoData.refundingExplain}}</el-form-item>\n\t\t\t</el-form>\n\t\t</el-dialog>\n    </div>\n</template>\n<style type=\"text/css\">\n.orderManagement .el-table__body .el-button{\n\tdisplay:block; margin: 10px auto 0;\n}\n.orderManagement .el-table__body .el-button:last-child{\n\t margin: 10px auto 10px;\n}\n.orderManagement .el-dialog__body .el-form-item__content\n{\n\tline-height: 36px;\n}\n.orderManagement .dialogDelivery .el-dialog__body .el-form-item__content,\n.orderManagement .dialogProofsModify .el-dialog__body .el-form-item__content{\n\tline-height: 0;\n}\n\n</style>\n<script>\n\timport './../css/order.scss';\n\timport Common from './../js/common';\n    export default{\n        data(){\n            return{\n            \ttabs : [\n            \t\t\t{title:\"全部\",name:\"status0\"},{title:\"待核算\",name:\"status1\"},{title:\"待付款\",name:\"status2\"},{title:\"已付定金\",name:\"status3\"},\n            \t\t\t{title:\"已付款\",name:\"status4\"},{title:\"退款中\",name:\"status5\"},{title:\"待发货\",name:\"status6\"},{title:\"已发货\",name:\"status7\"},\n            \t\t\t{title:\"成功的订单\",name:\"status8\"},{title:\"关闭的订单\",name:\"status9\"}\n            \t\t],\n                searchForm : this.getDefaultDataForSearch(),\t\t//搜索项表单信息\n                OrderList : {},\t\t\t\t\t\t\t\t\t//订单列表数据缓存\n                brandList : [],\t\t\t\t\t\t\t\t\t//品牌列表\n                exceptDeliveryDare : '',\t\t\t\t\t\t\t//搜索项－创建开始时间\n                deliveryDate : '',\t\t\t\t\t\t\t\t//搜索项－创建结束时间\n                createDate : '',\n                activeName : \"status0\",\n                ossUrl : \"http://test-hjh.oss-cn-shanghai.aliyuncs.com/\",\n                page : 1,\t\t\t\t\t\t\t\t\t\t//当前列表页码\n                pageSize : 10,\t\t\t\t\t\t\t\t\t//列表－每页条数\n                loading : false,\t\t\t\t\t\t\t\t\t//列表加载状态\n                brandListApi : \"/goodsbrand/queryforweb\"\t,\t\t//品牌列表接口地址\n                orderListApi : \"/order-manager-service/orders\",\t//订单列表接口地址\n                queryGoodsApi: \"/queryGoods\",\t\t\t\t//商品信息接口地址\n                clientListApi: \"./../getClientList\",\t\t\t\t//买家信息接口地址\n                showtooltip : true,\t\t\t\t\t\t\t\t//是否当内容过长被隐藏时显示tooltip true显示 flase不显示\n                dialogBuyerVisible : false,\t\t\t\t//买家资料－弹窗显示状态\n                dialogBuyerData : {},\t\t\t\t\t//买家资料－数据\n                dialogConsigneeVisible : false,\t\t\t//收获地址－弹窗显示状态\n                dialogConsigneeData : {},\t\t\t\t//收货地址－数据\n                dialogCommentVisible : false,\t\t\t//买家留言－弹窗显示状态\n                dialogCommentData : {},\t\t\t\t\t//买家留言－数据\n                dialogOrderDetailVisible : false,\t\t//订单详情－弹窗显示状态\n                dialogOrderDetailData : {},\t\t\t\t//订单详情－数据\n                dialogGoodsDetailVisible : false,\t\t//商品详情－弹窗显示状态\n                dialogGoodsDetailData : {},\t\t\t\t//商品详情－数据\n                postageVisible : false,\t\t\t\t\t//运费设置－弹窗显示状态\n                dialogpostageData : {\t\t\t\t\t//运费设置－数据\n                \tbalanceDateCount : 0\n                },\n                deliveryVisible : false,\t\t\t\t//发货－弹窗显示状态\n                dialogDeliveryData : {},\t\t\t\t//发货－数据\n                dialogRefundOrderVisible : false,\t\t//退款确认－弹窗显示状态\n                dialogRefundOrderData : {},\t\t\t\t//退款确认－数据\n                dialogProofsVisible : false,\t\t\t//支付凭证－弹窗显示状态\n                dialogProofsData : [],\t\t\t\t\t//支付凭证－数据\n                dialogProofsModifyVisible : false,\n                proofs0List : [],\n                proofs1List : [],\n                isProofsModify : false,\n                dialogLogisticsVisible : false,\t\t\t//发货信息－弹窗显示状态\n                dialogLogisticsData : {},\t\t\t\t//发货信息－数据\n                dialogRefundInfoVisible : false,\n                dialogRefundInfoData : {},\n                collapseActiveNames : [\"1\",\"2\",\"3\"],\n                buyActiveNames : [\"1\",\"2\",\"3\"],\n                LogisticsNoteSnapshotList : [],\n                pickerOptions: {\n\t\t\t\t\tshortcuts: [{\n\t\t\t\t\t\ttext: '最近一周',\n\t\t\t\t\t\tonClick(picker) {\n\t\t\t\t\t\t\tconst end = new Date();\n\t\t\t\t\t\t\tconst start = new Date();\n\t\t\t\t\t\t\tstart.setTime(start.getTime() - 3600 * 1000 * 24 * 7);\n\t\t\t\t\t\t\tpicker.$emit('pick', [start, end]);\n\t\t\t\t\t\t}\n\t\t\t\t\t}, {\n\t\t\t\t\t\ttext: '最近一个月',\n\t\t\t\t\t\tonClick(picker) {\n\t\t\t\t\t\t\tconst end = new Date();\n\t\t\t\t\t\t\tconst start = new Date();\n\t\t\t\t\t\t\tstart.setTime(start.getTime() - 3600 * 1000 * 24 * 30);\n\t\t\t\t\t\t\tpicker.$emit('pick', [start, end]);\n\t\t\t\t\t\t}\n\t\t\t\t\t}, {\n\t\t\t\t\t\ttext: '最近三个月',\n\t\t\t\t\t\tonClick(picker) {\n\t\t\t\t\t\t\tconst end = new Date();\n\t\t\t\t\t\t\tconst start = new Date();\n\t\t\t\t\t\t\tstart.setTime(start.getTime() - 3600 * 1000 * 24 * 90);\n\t\t\t\t\t\t\tpicker.$emit('pick', [start, end]);\n\t\t\t\t\t\t}\n\t\t\t\t\t}]\n\t\t\t\t},\n            }\n       \t},\n\t\tcreated(){\n\t\t\tvar status = this.$route.params.status || 0;\n\t\t\tthis.activeName = \"status\" + status;\n\t\t\tdocument.title = \"后台管理系统-订单管理\";\n\t\t\tthis.getParamByTabs();\n\t\t\tthis.getOrderListByCache();\n\t\t\tthis.getBrandList();\n\t\t\tvar that = this;\n\t\t\twindow.onhashchange = function(){\n\t\t\t\tvar status = location.hash.match(/\\d+/g);\n\t\t\t\tstatus = status ? status.join(\"\") : 0;\n\t\t\t\tthat.activeName = \"status\" + status;\n\t\t\t\t\n\t\t\t\t$(\".hjh-menu a\").each(function(){\n\t\t    \t\t\tif($(this).attr(\"href\")==location.hash){\n\t\t    \t\t\t\t$(this).parent().addClass(\"is-active\").siblings().removeClass(\"is-active\")\n\t\t    \t\t}\n\t\t    \t});\n\t\t\t\tif(that.ClickTargetIsTab){\n\t\t\t\t\tthat.ClickTargetIsTab = false;\n\t\t\t\t\treturn;\n\t\t\t\t}else{\n\t\t\t\t\tthat.getOrderListByTabs();\n\t\t\t\t}\n\t\t\t}\n\t\t},\n\t\tmethods : {\n\t\t\t/* 切换订单状态tab后根据tab值 设置查询入参 */\n\t\t\tgetParamByTabs(){\n\t\t\t\tvar i = Number(this.activeName.replace(\"status\",\"\"));\n\t\t\t\tvar param = {};\n\t\t\t\tswitch(i){\n\t\t\t\t\t//待核算\n\t\t\t\t\tcase 1 :\n\t\t\t\t\t\tparam = {\n\t\t\t\t\t\t\torderStatus : [\"NON_PAYMENT\"],\n\t\t\t\t\t\t\tisPostageCalculation : false\n\t\t\t\t\t\t}\n\t\t\t\t\t\tbreak;\n\t\t\t\t\t//待付款\n\t\t\t\t\tcase 2 : \n\t\t\t\t\t\tparam = {\n\t\t\t\t\t\t\torderStatus : [\"NON_PAYMENT\"],\n\t\t\t\t\t\t\tisPostageCalculation : true,\n\t\t\t\t\t\t\thaveDepositPayProof : false,\n\t\t\t\t\t\t\thaveFullPayProof : false\n\t\t\t\t\t\t}\n\t\t\t\t\t\tbreak;\n\t\t\t\t\t//已付定金\n\t\t\t\t\tcase 3 : \n\t\t\t\t\t\tparam = {\n\t\t\t\t\t\t\torderStatus : [\"NON_PAYMENT\",\"PART_PAID\"],\n\t\t\t\t\t\t\thaveDepositPayProof : true,\n\t\t\t\t\t\t\thaveFullPayProof : false\n\t\t\t\t\t\t}\n\t\t\t\t\t\tbreak;\n\t\t\t\t\t//已付款\n\t\t\t\t\tcase 4 : \n\t\t\t\t\t\tparam = {\n\t\t\t\t\t\t\torderStatus : [\"NON_PAYMENT\",\"PART_PAID\"],\n\t\t\t\t\t\t\thaveFullPayProof : true\n\t\t\t\t\t\t}\n\t\t\t\t\t\tbreak;\n\t\t\t\t\t//退款中\n\t\t\t\t\tcase 5 :\n\t\t\t\t\t\tparam = {\n\t\t\t\t\t\t\torderStatus : [\"REFUNDING\"]\n\t\t\t\t\t\t}\n\t\t\t\t\t\tbreak;\n\t\t\t\t\t//待发货\n\t\t\t\t\tcase 6 :\n\t\t\t\t\t\tparam = {\n\t\t\t\t\t\t\torderStatus : [\"UN_DELIVERY\"]\n\t\t\t\t\t\t}\n\t\t\t\t\t\tbreak;\n\t\t\t\t\t//已发货\n\t\t\t\t\tcase 7 :\n\t\t\t\t\t\tparam = {\n\t\t\t\t\t\t\torderStatus : [\"DELIVERED\"]\n\t\t\t\t\t\t}\n\t\t\t\t\t\tbreak;\n\t\t\t\t\t//成功的订单\n\t\t\t\t\tcase 8 :\n\t\t\t\t\t\tparam = {\n\t\t\t\t\t\t\torderStatus : [\"RECEIVED\",\"SUCCESS\"]\n\t\t\t\t\t\t}\n\t\t\t\t\t\tbreak;\n\t\t\t\t\t//关闭的订单\n\t\t\t\t\tcase 9 :\n\t\t\t\t\t\tparam = {\n\t\t\t\t\t\t\torderStatus : [\"CLOSED_BUYER_CANCEL\",\"CLOSED_SELLER_CANCEL\",\"CLOSED_REFUNDED\",\"CLOSED_RETURN_GOODS\",\"CLOSED_EXCEPTION\"]\n\t\t\t\t\t\t}\n\t\t\t\t\t\tbreak;\n\t\t\t\t};\n\t\t\t\tthis.paramsOrderStatus = param;\n\t\t\t},\n\t\t\t/** 发货日期发生改变 **/\n\t\t\tdeliveryDateChange(val){\n\t\t\t\tif(!val){\n\t\t\t\t\tthis.searchForm.deliveryDateBegin = \"\";\n\t\t\t\t\tthis.searchForm.deliveryDateEnd = \"\";\n\t\t\t\t}else{\n\t\t\t\t\tvar arr = val.split(\" - \");\n\t\t\t\t\tthis.searchForm.deliveryDateBegin = arr[0].replace(/\\s+/g,\"T\")+\".442Z\";\n\t\t\t\t\tthis.searchForm.deliveryDateEnd = arr[1].replace(/\\s+/g,\"T\")+\".442Z\";\t\n\t\t\t\t}\n\t\t\t},\n\t\t\t/** 下单日期发生改变 **/\n\t\t\tcreateDateChange(val){\n\t\t\t\tif(!val){\n\t\t\t\t\tthis.searchForm.createDateBegin = \"\";\n\t\t\t\t\tthis.searchForm.createDateEnd = \"\";\n\t\t\t\t}else{\n\t\t\t\t\tvar arr = val.split(\" - \");\n\t\t\t\t\tthis.searchForm.createDateBegin = arr[0].replace(/\\s+/g,\"T\")+\".442Z\";\n\t\t\t\t\tthis.searchForm.createDateEnd = arr[1].replace(/\\s+/g,\"T\")+\".442Z\";\t\n\t\t\t\t}\n\t\t\t},\n\t\t\t/** tab状态选项卡切换回调 **/\n\t\t\thandleTabsClick(tab){\n\t\t\t\tthis.ClickTargetIsTab = true;\n\t\t\t\tthis.setMenuActive();\n\t\t\t\tthis.getOrderListByTabs();\n\t\t\t},\n\t\t\t/** 静态切换菜单选中状态 **/\n\t\t\tsetMenuActive(){\n\t\t\t\tvar i = Number(this.activeName.replace(\"status\",\"\"));\n\t\t\t\tlocation.href = \"#/orderManagement\"+ (i ? (\"/\"+i) : \"\");\n\t\t\t\t$(\".hjh-menu a\").each(function(){\n\t\t    \t\tif($(this).attr(\"href\")==location.hash){\n\t\t    \t\t\t$(this).parent().addClass(\"is-active\").siblings().removeClass(\"is-active\")\n\t\t    \t\t}\n\t\t    \t});\n\t\t\t},\n\t\t\t/** 根据tab选中状态加在对应数据列表 **/\n\t\t\tgetOrderListByTabs(){\n\t\t\t\tthis.getParamByTabs();\n\t\t\t\tthis.submitForm();\n\t\t\t},\n\t\t\t//搜索项表单初始化\n\t\t\tgetDefaultDataForSearch(){\n\t\t\t\treturn {\n\t\t\t\t\tproductNoLike : '',\n\t\t\t\t\tproductTitleLike : '',\n\t\t\t\t\tbrandNameLike : '',\n\t\t\t\t\torderNoLike : '',\n\t\t\t\t\tuserMobileLike : '',\n\t\t\t\t\tinviteCodeBegin : '',\n\t\t\t\t\tinviteCodeEnd : '',\n\t\t\t\t\tpayType : ''\n\t\t\t\t}\n\t\t\t},\n\t\t\t//翻页回调\n\t\t\thandleCurrentChange(val){\n\t\t\t\tthis.$data.page = val;\n\t\t\t\tthis.searchDataCache.pageNum = val;\n\t\t\t\tthis.getOrderList();\n\t\t\t},\n\t\t\t//订单列表 点击订单编号查看订单详情\n\t\t\tcheckOrder(data){\n\t\t\t\tthis.$http.post(\"/order-manager-service/order/progress\",{\n\t\t\t\t\taccess_token : localStorage.access_token,\n\t\t\t\t\torderId : data.orderId\n\t\t\t\t}).then(res=>{});\n\t\t\t\t//return;\n\t\t\t\tthis.dialogOrderDetailData = data;\n\t\t\t\tthis.dialogOrderDetailVisible = true;\n\t\t\t\treturn;\n\t\t\t\tvar param = {\n\t\t\t\t\taccess_token : localStorage.access_token,\n\t\t\t\t\torderId : data.orderId\n\t\t\t\t};\n\t\t\t\tthis.$http.post(\"/dev/order-manager-service/order/orderId\",param).then(res=>{\n\t\t\t\t\tif(res.data.error_no==0){\n\t\t\t\t\t\tthis.dialogOrderDetailData = res.data.data;\n\t\t\t\t\t}else{\n\t\t\t\t\t\tthis.$message({\n\t\t\t\t\t\t\ttype: \"warning\",\n\t\t\t\t\t\t\tmessage: res.data.error_info || \"系统繁忙,稍后再试\"\n\t\t\t\t\t\t});\n\t\t\t\t\t}\n\t\t\t\t});\n\t\t\t},\n\t\t\t//查看商品信息\n\t\t\tcheckProductDetail(item){\n\t\t\t\t//return;\n\t\t\t\tvar param = {\n\t\t\t\t\tgoods_id : item.productId,\n\t\t\t\t\tgoods_name : item.productName\n\t\t\t\t};\n\t\t\t\tthis.$http.post(this.queryGoodsApi,param).then(response=>{\n\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\tif(jsondata.error_no==0 && jsondata.result_list.length){\n\t\t\t\t\t\tthis.dialogGoodsDetailData = jsondata.result_list[0];\n\t\t\t\t\t\tthis.dialogGoodsDetailVisible = true;\n\t\t\t\t\t}else{\n\t\t\t\t\t\tthis.$message({\n\t\t\t\t\t\t\ttype: \"warning\",\n\t\t\t\t\t\t\tmessage: \"商品详情数据查询异常\"\n\t\t\t\t\t\t});\n\t\t\t\t\t}\n\t\t\t\t});\n\t\t\t},\n\t\t\t//查看买家信息\n\t\t\tcheckBuyer(item){\n\t\t\t\tvar param = Common.filterParamByEmptyValue({\n\t\t\t\t\t//nick_name : item.userName\n\t\t\t\t\tclient_id : item.userId\n\t\t\t\t});\n\t\t\t\tthis.$http.post(\"/getClientList\",param).then(response=>{\n                \tvar jsondata = response.data;\n\t\t\t\t\tif(jsondata.error_no==0 && jsondata.result_list.length){\n\t\t\t\t\t\tthis.dialogBuyerData = jsondata.result_list[0];\n\t\t\t\t\t\tthis.dialogBuyerVisible = true;\n\t\t\t\t\t}else{\n\t\t\t\t\t\tthis.$message({\n\t\t\t\t\t\t\ttype: \"warning\",\n\t\t\t\t\t\t\tmessage: \"买家信息数据查询异常\"\n\t\t\t\t\t\t});\n\t\t\t\t\t}\n\t\t\t\t});\n\t\t\t},\n\t\t\t//查看收货地址\n\t\t\tcheckConsignee(data){\n\t\t\t\tthis.dialogConsigneeVisible = true;\n\t\t\t\tthis.dialogConsigneeData = data;\n\t\t\t},\n\t\t\t/** 查看买家留言 **/\n\t\t\tcheckComment(data){\n\t\t\t\tthis.dialogCommentVisible = true;\n\t\t\t\tthis.dialogCommentData = data;\n\t\t\t},\n\t\t\t/** 查看支付凭证 **/\n\t\t\teventCheckProofs(item){\n\t\t\t\tvar api = \"/order-manager-service/order/orderId/pay/getproofs\",\n\t\t\t\t\tparam = {\n\t\t\t\t\t\taccess_token : localStorage.access_token,\n\t\t\t\t\t\torderId : item.orderId\n\t\t\t\t\t};\n\t\t\t\tthis.$http.post(\"/order-manager-service/order/orderId/pay/getproofs\",param).then(response=>{\n\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\tif(jsondata.error_no==0){\n\t\t\t\t\t\tthis.dialogProofsData = jsondata.data;\n\t\t\t\t\t\tthis.dialogProofsData = this.dialogProofsData.map(function(item){\n\t\t\t\t\t\t\titem.createDate = Common.time2DateFormat(item.createDate);\n\t\t\t\t\t\t\titem.amount = Number(item.amount).toFixed(2);\n\t\t\t\t\t\t\treturn item;\n\t\t\t\t\t\t});\n\t\t\t\t\t\tthis.isProofsModify = false;\n\t\t\t\t\t\tthis.dialogProofsVisible = true;\n\t\t\t\t\t}else{\n\t\t\t\t\t\tthis.$message({\n\t\t\t\t\t\t\ttype: \"warning\",\n\t\t\t\t\t\t\tmessage: jsondata.error_info\n\t\t\t\t\t\t});\n\t\t\t\t\t}\n\t\t\t\t});\n\t\t\t},\n\t\t\t/** 点击按钮－编辑支付凭证 **/\n\t\t\teventModifyProofs(item){\n\t\t\t\tvar api = \"/order-manager-service/order/orderId/pay/getproofs\",\n\t\t\t\t\tparam = {\n\t\t\t\t\t\taccess_token : localStorage.access_token,\n\t\t\t\t\t\torderId : item.orderId\n\t\t\t\t\t};\n\t\t\t\tthis.$http.post(\"/order-manager-service/order/orderId/pay/getproofs\",param).then(response=>{\n\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\tif(jsondata.error_no==0){\n\t\t\t\t\t\tthis.dialogProofsData = jsondata.data;\n\t\t\t\t\t\tvar that = this;\n\t\t\t\t\t\tthis.dialogProofsData = this.dialogProofsData.map((item,index)=>{\n\t\t\t\t\t\t\titem.priviewSnapshot = \"http://test-hjh.oss-cn-shanghai.aliyuncs.com/\" + item.paymentProofSnapshot;\n\t\t\t\t\t\t\titem.createDate = Common.time2DateFormat(item.createDate);\n\t\t\t\t\t\t\titem.amount = Number(item.amount).toFixed(2);\n\t\t\t\t\t\t\tif(index==0){\n\t\t\t\t\t\t\t\tthat.proofs0List = [{\n\t\t\t\t\t\t          name: item.paymentProofSnapshot,\n\t\t\t\t\t\t          url: 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+item.paymentProofSnapshot,\n\t\t\t\t\t\t          status: 'finished'\n\t\t\t\t\t\t        }]\n\t\t\t\t\t\t\t}\n\t\t\t\t\t\t\tif(index==1){\n\t\t\t\t\t\t\t\tthat.proofs1List = [{\n\t\t\t\t\t\t          name: item.paymentProofSnapshot,\n\t\t\t\t\t\t          url: 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+item.paymentProofSnapshot,\n\t\t\t\t\t\t          status: 'finished'\n\t\t\t\t\t\t        }]\n\t\t\t\t\t\t\t}\n\t\t\t\t\t\t\treturn item;\n\t\t\t\t\t\t});\n\n\t\t\t\t\t\tthis.isProofsModify = true;\n\t\t\t\t\t\tthis.dialogProofsModifyVisible = true;\n\t\t\t\t\t}else{\n\t\t\t\t\t\tthis.$message({\n\t\t\t\t\t\t\ttype: \"warning\",\n\t\t\t\t\t\t\tmessage: jsondata.error_info\n\t\t\t\t\t\t});\n\t\t\t\t\t}\n\t\t\t\t});\n\t\t\t},\n\t\t\t/** 修改支付凭证 **/\n\t\t\thandleModifyProofs(item){\n\t\t\t\tvar access_token = localStorage.access_token;\n\t\t\t\tvar that = this,reg = /data:\\S+\\\"/g;\n\t\t\t\tthis.proofUpdatedCount = 0;\n\t\t\t\titem.map(child=>{\n\t\t\t\t\t//var arr = child.paymentProofSnapshot.match(reg);\n\t\t\t\t\tvar bool = child.paymentProofSnapshot.indexOf(\"data:\")==0;\n\t\t\t\t\tvar param = {\n\t\t\t\t\t\t\taccess_token : access_token,\n\t\t\t\t\t\t\tamount : child.amount,\n\t\t\t\t\t\t\tbankAccount : child.bankAccount,\n\t\t\t\t\t\t\tpayProofId : child.id,\n\t\t\t\t\t\t\tpaymentAccount : child.paymentAccount,\n\t\t\t\t\t\t\tpaymentProofSnapshot : child.paymentProofSnapshot,\n\t\t\t\t\t\t\tpaymentUserName : child.paymentUserName,\n\t\t\t\t\t\t\torderId : child.orderId\n\t\t\t\t\t\t};\n\t\t\t\t\tif(bool){\n\t\t\t\t\t\tthat.uploadCompressImg(child.paymentProofSnapshot,function(img_uri){\n\t\t\t\t\t\t\tparam.paymentProofSnapshot = img_uri;\n\t\t\t\t\t\t\tthat.updateProof(param);\n\t\t\t\t\t\t});\n\t\t\t\t\t}else{\n\t\t\t\t\t\tthat.updateProof(param);\n\t\t\t\t\t}\n\t\t\t\t});\n\t\t\t},\n\t\t\t/** 上传图片 **/\n\t\t\tuploadCompressImg(result,cb){\n\t\t\t\tvar that = this,\n\t\t\t\t\tparam = {\n\t\t\t\t\t\taccess_token : localStorage.access_token,\n\t\t\t\t\t\tbase64Str : result,\n\t\t\t\t\t\timgType : 19\n\t\t\t\t\t};\n\t\t\t\tthat.$http.post(\"/json/901210\",param).then(response => {\n\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\tif(jsondata.error_no==0){\n\t\t\t\t\t\tcb(jsondata.img_uri);\n\t\t\t\t\t}else{\n\t\t\t\t\t\tthat.$message({\n\t\t\t\t\t\t\ttype: \"warning\",\n\t\t\t\t\t\t\tmessage: (jsondata.error_on +\":\"+ jsondata.error_info)\n\t\t\t\t\t\t});\n\t\t\t\t\t}\n\t\t\t\t\t\n\t\t\t\t});\n\t\t\t},\n\t\t\t/** 更新凭证 **/\n\t\t\tupdateProof(param){\n\t\t\t\tvar api = \"/order-manager-service/order/orderId/pay/proof\";\n\t\t\t\tthis.$http.post(api,param).then(response=>{\n\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\tthis.$message({\n\t\t\t\t\t\ttype: jsondata.error_no==0 ? \"success\" : \"warning\",\n\t\t\t\t\t\tmessage: jsondata.error_no==0 ? \"修改成功\" : (jsondata.error_no +\":\"+ jsondata.error_info)\n\t\t\t\t\t});\n\t\t\t\t\tif(jsondata.error_no==0){\n\t\t\t\t\t\tthis.proofUpdatedCount++;\n\t\t\t\t\t\tif(this.proofUpdatedCount == this.dialogProofsData.length){\n\t\t\t\t\t\t\tthis.isProofsModify = true;\n\t\t\t\t\t\t\tthis.dialogProofsModifyVisible = false;\n\t\t\t\t\t\t}\n\t\t\t\t\t}\n\t\t\t\t});\n\t\t\t},\n\t\t\t/** 支付凭证1的上传文件变化 **/\n\t\t\thandleFileChange(_file,_filelist){\n\t\t\t\tthis.proofs0List = [_filelist[_filelist.length-1]];\n\t\t\t\tvar that = this;\n\t\t\t\tvar reader = new FileReader(); \n\t\t\t\t\treader.readAsDataURL(_file.raw);\n\t\t\t\t\treader.onload = function(e){\n\t\t\t\t\t\tvar img = new Image();\n\t\t\t\t\t\t\timg.src = this.result;\n\t\t\t\t\t\t\timg.onload = function(){\n\t\t\t\t\t\t\t\tthat.dialogProofsData[0].paymentProofSnapshot = Common.compressImg(img,0.8,\"image/jpeg\");\n\t\t\t\t\t\t\t\tthat.dialogProofsData[0].priviewSnapshot = Common.compressImg(img,0.8,\"image/jpeg\");\n\t\t\t\t\t\t\t\t$(\".el-upload-list\").eq(0).children(\"li\").last().prev().remove();\n\t\t\t\t\t\t\t}\n\t\t\t\t\t}\n\t\t\t},\n\t\t\t/** 支付凭证1的上传文件删除 **/\n\t\t\thandleFileRemove(_file,_filelist){\n\t\t\t\tif(!_filelist.length){\n\t\t\t\t\tthis.dialogProofsData[0].paymentProofSnapshot = \"\";\n\t\t\t\t\tthis.dialogProofsData[0].priviewSnapshot = \"\";\n\t\t\t\t\tthis.proofs0List = [];\n\t\t\t\t}\n\t\t\t},\n\t\t\t/** 支付凭证2的上传文件变化 **/\n\t\t\thandleFileChange1(_file,_filelist){\n\t\t\t\tthis.proofs1List = [_filelist[_filelist.length-1]];\n\t\t\t\tvar that = this;\n\t\t\t\tvar reader = new FileReader(); \n\t\t\t\t\treader.readAsDataURL(_file.raw);\n\t\t\t\t\treader.onload = function(e){\n\t\t\t\t\t\tvar img = new Image();\n\t\t\t\t\t\t\timg.src = this.result;\n\t\t\t\t\t\t\timg.onload = function(){\n\t\t\t\t\t\t\t\tthat.dialogProofsData[1].paymentProofSnapshot = Common.compressImg(img,0.8,\"image/jpeg\");\n\t\t\t\t\t\t\t\tthat.dialogProofsData[1].priviewSnapshot = Common.compressImg(img,0.8,\"image/jpeg\");\n\t\t\t\t\t\t\t\t$(\".el-upload-list\").eq(1).children(\"li\").last().prev().remove();\n\t\t\t\t\t\t\t}\n\t\t\t\t\t}\n\t\t\t},\n\t\t\t/** 支付凭证2的上传文件删除 **/\n\t\t\thandleFileRemove1(_file,_filelist){ \n\t\t\t\tif(!_filelist.length){\n\t\t\t\t\tthis.dialogProofsData[1].paymentProofSnapshot = \"\";\n\t\t\t\t\tthis.dialogProofsData[1].priviewSnapshot = \"\";\n\t\t\t\t\tthis.proofs1List = [];\n\t\t\t\t}\n\t\t\t},\n\t\t\t/**  点击按钮－运费核算 **/\n\t\t\taccountingPostage(item){\n\t\t\t\t//var time = new Date(item.estimateDeliveryDesc).getTime();\n\t\t\t\tthis.dialogpostageData = {\n\t\t\t\t\torderNo : item.orderNo,\n\t\t\t\t\tbalanceDateCount : item.balanceDateCount,\n\t\t\t\t\t//estimateDeliveryDesc : time ? new Date(item.estimateDeliveryDesc) : \"\",\n\t\t\t\t\testimateDeliveryDesc : item.estimateDeliveryDesc,\n\t\t\t\t\torderId : item.orderId,\n\t\t\t\t\tpostage : item.postage,\n\t\t\t\t\ttransactionType : item.transactionType\n\t\t\t\t};\n\t\t\t\t\n\t\t\t\tthis.postageVisible = true;\n\t\t\t},\n\t\t\t/** 提交数据－运费核算 **/\n\t\t\tpostageAndDeliveryDate(formdata){\n\t\t\t\tthis.$refs[this.dialogpostageData].validate((valid) => {\n\t\t\t\t\tif (valid) {\n\t\t\t\t\t\tvar param = {\n\t\t\t\t\t\t\taccess_token : localStorage.access_token,\n\t\t\t\t\t\t\tbalanceDateCount : formdata.balanceDateCount,\n\t\t\t\t\t\t\testimateDeliveryDesc : formdata.estimateDeliveryDesc || Common.time2DateFormat(formdata.estimateDeliveryDesc).split(\" \")[0],\n\t\t\t\t\t\t\torderId : formdata.orderId,\n\t\t\t\t\t\t\tpostage : formdata.postage\n\t\t\t\t\t\t},api = \"/order-manager-service/order/orderId/transaction/postageAndDeliveryDate\";\n\t\t\t\t\t\t//if(formdata.transactionType==0){\n\t\t\t\t\t\t//\tdelete param.balanceDateCount;\n\t\t\t\t\t\t//}\n\t\t\t\t\t\tthis.$http.post(api,param).then(response=>{\n\t\t\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\t\t\tthis.$message({\n\t\t\t\t\t\t\t\ttype: jsondata.error_no==0 ? \"success\" : \"warning\",\n\t\t\t\t\t\t\t\tmessage: jsondata.error_no==0 ? \"操作成功\" : jsondata.error_info\n\t\t\t\t\t\t\t});\n\t\t\t\t\t\t\tif(jsondata.error_no==0){\n\t\t\t\t\t\t\t\tthis.postageVisible = false;\n\t\t\t\t\t\t\t\tthis.getOrderListByCache();\n\t\t\t\t\t\t\t}\n\t\t\t\t\t\t});\n\t\t\t\t\t}\n\t\t\t\t});\n\t\t\t},\n\t\t\t/** 已付定金－确认收款 **/\n\t\t\teventDeposit(item){\n\t\t\t\tthis.$confirm('确认收款，即视为实际收款金额等于应支付定金！', '确认收款', {\n\t\t\t\t\tconfirmButtonText: '确定',\n\t\t\t\t\tcancelButtonText: '取消',\n\t\t\t\t\ttype: 'warning'\n\t\t\t\t}).then(() => {\n\t\t\t\t\tvar api = \"/order-manager-service/order/orderId/transaction/deposit\",\n\t\t\t\t\t\tparam = {\n\t\t\t\t\t\t\taccess_token : localStorage.access_token,\n\t\t\t\t\t\t\torderId : item.orderId,\n\t\t\t\t\t\t\tdeposit : item.transactionDeposit\n\t\t\t\t\t\t};\n\t\t\t\t\tthis.$http.post(api,param).then(response=>{\n\t\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\t\t\tthis.$message({\n\t\t\t\t\t\t\t\ttype: jsondata.error_no==0 ? \"success\" : \"warning\",\n\t\t\t\t\t\t\t\tmessage: jsondata.error_no==0 ? \"操作成功\" : jsondata.error_info\n\t\t\t\t\t\t\t});\n\t\t\t\t\t\t\tif(jsondata.error_no==0){\n\t\t\t\t\t\t\t\tthis.getOrderListByCache();\n\t\t\t\t\t\t\t}\n\t\t\t\t\t});\n\t\t\t\t});\n\t\t\t},\n\t\t\t/** 已付款－确认收款 **/\n\t\t\teventBalance(item){\n\t\t\t\tthis.$confirm('请确认实际收款总额等于订单总额。', '确认收款', {\n\t\t\t\t\tconfirmButtonText: '确定',\n\t\t\t\t\tcancelButtonText: '取消',\n\t\t\t\t\ttype: 'warning'\n\t\t\t\t}).then(() => {\n\t\t\t\t\tvar api = item.transactionType==1 ? \"/order-manager-service/order/orderId/transaction/balance\" : \"/order-manager-service/order/transaction/actualAmount\",\n\t\t\t\t\t\tparam = {\n\t\t\t\t\t\t\taccess_token : localStorage.access_token,\n\t\t\t\t\t\t\torderId : item.orderId,\n\t\t\t\t\t\t\tbalance : item.transactionBalance\n\t\t\t\t\t\t};\n\t\t\t\t\t\tif(item.transactionType==0){\n\t\t\t\t\t\t\tdelete param.balance;\n\t\t\t\t\t\t\tparam.amount = item.transactionPayAmount;\n\t\t\t\t\t\t}\n\t\t\t\t\tconsole.log(api,param);\n\t\t\t\t\tthis.$http.post(api,param).then(response=>{\n\t\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\t\t\tthis.$message({\n\t\t\t\t\t\t\t\ttype: jsondata.error_no==0 ? \"success\" : \"warning\",\n\t\t\t\t\t\t\t\tmessage: jsondata.error_no==0 ? \"操作成功\" : jsondata.error_info\n\t\t\t\t\t\t\t});\n\t\t\t\t\t\t\tif(jsondata.error_no==0){\n\t\t\t\t\t\t\t\tthis.getOrderListByCache();\n\t\t\t\t\t\t\t}\n\t\t\t\t\t});\n\t\t\t\t});\n\t\t\t},\n\t\t\t/** 点击按钮－取消订单 **/\n\t\t\teventCancelOrder(item){\n\t\t\t\tthis.$confirm('实际已付款的订单请勿取消订单！取消订单后订单关闭！', '取消订单', {\n\t\t\t\t\tconfirmButtonText: '确定',\n\t\t\t\t\tcancelButtonText: '取消',\n\t\t\t\t\ttype: 'warning'\n\t\t\t\t}).then(() => {\n\t\t\t\t\tvar api = \"/order-manager-service/order/cancelOrder\",\n\t\t\t\t\t\tparam = {\n\t\t\t\t\t\t\taccess_token : localStorage.access_token,\n\t\t\t\t\t\t\torderId : item.orderId\n\t\t\t\t\t\t};\n\t\t\t\t\tthis.$http.post(api,param).then(response=>{\n\t\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\t\t\tthis.$message({\n\t\t\t\t\t\t\t\ttype: jsondata.error_no==0 ? \"success\" : \"warning\",\n\t\t\t\t\t\t\t\tmessage: jsondata.error_no==0 ? \"操作成功\" : jsondata.error_info\n\t\t\t\t\t\t\t});\n\t\t\t\t\t\t\tif(jsondata.error_no==0){\n\t\t\t\t\t\t\t\tthis.getOrderListByCache();\n\t\t\t\t\t\t\t}\n\t\t\t\t\t});\n\t\t\t\t});\n\t\t\t},\n\t\t\t/** 点击按钮－退款确认 **/\n\t\t\teventRefundOrderComplete(item){\n\t\t\t\tthis.dialogRefundOrderData.max = item.transactionPayAmount;\n\t\t\t\tthis.dialogRefundOrderData.orderId = item.orderId;\n\t\t\t\tthis.dialogRefundOrderVisible = true;\n\t\t\t},\n\t\t\t/** 提交操作－退款确认 **/\n\t\t\thandleRefundOrderComplete(){\n\n\t\t\t\tif(!this.dialogRefundOrderData.refundingAmount){\n\t\t\t\t\tthis.$message({\n\t\t\t\t\t\ttype: \"warning\",\n\t\t\t\t\t\tmessage: \"请填写退款金额\"\n\t\t\t\t\t});\n\t\t\t\t\treturn;\n\t\t\t\t}\n\t\t\t\tif(this.dialogRefundOrderData.refundingAmount > this.dialogRefundOrderData.max){\n\t\t\t\t\tthis.$message({\n\t\t\t\t\t\ttype: \"warning\",\n\t\t\t\t\t\tmessage: \"退款金额超额\"\n\t\t\t\t\t});\n\t\t\t\t\treturn;\n\t\t\t\t}\n\t\t\t\tif(!this.dialogRefundOrderData.refundingExplain){\n\t\t\t\t\tthis.$message({\n\t\t\t\t\t\ttype: \"warning\",\n\t\t\t\t\t\tmessage: \"请填写退款说明\"\n\t\t\t\t\t});\n\t\t\t\t\treturn;\n\t\t\t\t}\n\n\t\t\t\tthis.dialogRefundOrderData.access_token = localStorage.access_token;\n\t\t\t\tvar api = \"/order-manager-service/order/refundOrderComplete\";\n\t\t\t\tthis.$http.post(api,this.dialogRefundOrderData).then(response=>{\n\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\tthis.$message({\n\t\t\t\t\t\ttype: jsondata.error_no==0 ? \"success\" : \"warning\",\n\t\t\t\t\t\tmessage: jsondata.error_no==0 ? \"操作成功\" : jsondata.error_info\n\t\t\t\t\t});\n\t\t\t\t\tif(jsondata.error_no==0){\n\t\t\t\t\t\tthis.dialogRefundOrderVisible = false;\n\t\t\t\t\t\tthis.getOrderListByCache();\n\t\t\t\t\t}\n\t\t\t\t});\n\t\t\t},\n\t\t\t/** 点击按钮－发货 **/\n\t\t\teventDelivery(item){\n\t\t\t\tthis.dialogDeliveryData = {\n\t\t\t\t\torderId : item.orderId,\n\t\t\t\t\torderNo : item.orderNo,\n\t\t\t\t\tlogisticsCompany : \"\",\n\t\t\t\t\tlogisticsNo : \"\",\n\t\t\t\t\tlogisticsNoteSnapshot : \"\",\n\t\t\t\t\tdeliveryDate : \"\"\n\t\t\t\t};\n\t\t\t\tthis.LogisticsNoteSnapshotList=[];\n\t\t\t\tthis.deliveryVisible = true;\n\t\t\t\t$(\"#deliveryDate input\").val(\"\");\n\t\t\t},\n\t\t\t/** 提交操作－发货 **/\n\t\t\thandleDelivery(formdata){\n\t\t\t\tthis.$refs[this.dialogDeliveryData].validate((valid) => {\n\t\t\t\t\tif(!valid){return;}\n\t\t\t\t\tthis.dialogDeliveryData.access_token = localStorage.access_token;\n\t\t\t\t\tthis.dialogDeliveryData.deliveryDate = Common.time2DateFormat(this.dialogDeliveryData.deliveryDate).replace(/\\s+/g,\"T\")+\".442Z\";\n\t\t\t\t\tvar api = \"/order-manager-service/order/orderId/logistics/delivery\";\n\t\t\t\t\tthis.$http.post(api,this.dialogDeliveryData).then(response=>{\n\t\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\t\t\tthis.$message({\n\t\t\t\t\t\t\t\ttype: jsondata.error_no==0 ? \"success\" : \"warning\",\n\t\t\t\t\t\t\t\tmessage: jsondata.error_no==0 ? \"操作成功\" : (jsondata.error_no + jsondata.error_info)\n\t\t\t\t\t\t\t});\n\t\t\t\t\t\t\tif(jsondata.error_no==0){\n\t\t\t\t\t\t\t\tthis.deliveryVisible = false;\n\t\t\t\t\t\t\t\tthis.getOrderListByCache();\n\t\t\t\t\t\t\t}\n\t\t\t\t\t});\n\n\t\t\t\t});\n\t\t\t},\n\t\t\t/** 发货－选择运单照片 **/\n\t\t\thandleLogisticsNoteSnapshotChange(_file,_filelist){\n\t\t\t\tthis.LogisticsNoteSnapshotList = [_filelist[_filelist.length-1]];\n\t\t\t\tvar that = this;\n\t\t\t\tvar reader = new FileReader(); \n\t\t\t\t\treader.readAsDataURL(_file.raw);\n\t\t\t\t\treader.onload = function(e){\n\t\t\t\t\t\tvar img = new Image();\n\t\t\t\t\t\t\timg.src = this.result;\n\t\t\t\t\t\t\timg.onload = function(){\n\t\t\t\t\t\t\t\tthat.dialogDeliveryData.logisticsNoteSnapshot = Common.compressImg(img,0.8,\"image/jpeg\");\n\t\t\t\t\t\t\t\t$(\".el-upload-list > li\").last().prev().remove();\n\t\t\t\t\t\t\t}\n\t\t\t\t\t}\n\t\t\t},\n\t\t\t/** 发货－删除运单照片 **/\n\t\t\thandleLogisticsNoteSnapshotRemove(_file,_filelist){\n\t\t\t\tif(!_filelist.length){\n\t\t\t\t\tthis.dialogDeliveryData.logisticsNoteSnapshot = \"\";\n\t\t\t\t}\n\t\t\t},\n\t\t\t/** 点击按钮－查看发货信息 **/\n\t\t\teventGetLogistics(item){\n\t\t\t\tvar api = \"/order-manager-service/order/orderId/logistics/getLogistics\",\n\t\t\t\t\tparam = {\n\t\t\t\t\t\taccess_token : localStorage.access_token,\n\t\t\t\t\t\torderId : item.orderId\n\t\t\t\t\t};\n\t\t\t\tthis.$http.post(api,param).then(response=>{\n\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\tif(jsondata.error_no==0){\n\t\t\t\t\t\tthis.dialogLogisticsData = jsondata.data;\n\t\t\t\t\t\tthis.dialogLogisticsData.createDate = Common.time2DateFormat(this.dialogLogisticsData.createDate);\n\t\t\t\t\t\tthis.dialogLogisticsData.deliveryDate = Common.time2DateFormat(this.dialogLogisticsData.deliveryDate);\n\t\t\t\t\t\tthis.dialogLogisticsVisible = true;\n\t\t\t\t\t}else{\n\t\t\t\t\t\tthis.$message({\n\t\t\t\t\t\t\ttype: \"warning\",\n\t\t\t\t\t\t\tmessage: jsondata.error_info\n\t\t\t\t\t\t});\n\t\t\t\t\t}\n\t\t\t\t});\n\t\t\t},\n\t\t\t/** 点击按钮确认到货 **/\n\t\t\teventReceived(item){\n\t\t\t\tthis.$confirm('确认到货后，该订单流程结束', '你确认已到货吗？', {\n\t\t\t\t\tconfirmButtonText: '确定',\n\t\t\t\t\tcancelButtonText: '取消',\n\t\t\t\t\ttype: 'warning'\n\t\t\t\t}).then(() => {\n\t\t\t\t\tvar api = \"/order-manager-service/order/orderId/logistics/received\",\n\t\t\t\t\t\tparam = {\n\t\t\t\t\t\t\taccess_token : localStorage.access_token,\n\t\t\t\t\t\t\torderId : item.orderId\n\t\t\t\t\t\t};\n\t\t\t\t\tthis.$http.post(api,param).then(response=>{\n\t\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\t\t\tthis.$message({\n\t\t\t\t\t\t\t\ttype: jsondata.error_no==0 ? \"success\" : \"warning\",\n\t\t\t\t\t\t\t\tmessage: jsondata.error_no==0 ? \"操作成功\" : jsondata.error_info\n\t\t\t\t\t\t\t});\n\t\t\t\t\t\t\tif(jsondata.error_no==0){\n\t\t\t\t\t\t\t\tthis.getOrderListByCache();\n\t\t\t\t\t\t\t}\n\t\t\t\t\t});\n\t\t\t\t});\n\t\t\t},\n\t\t\t/** 点击按钮－退款信息 **/\n\t\t\teventCheckRefund(item){\n\t\t\t\tthis.dialogRefundInfoVisible = true;\n\t\t\t\tthis.dialogRefundInfoData = item;\n\t\t\t},\n\t\t\t/** 根据入参缓存进行查询 **/\n\t\t\tgetOrderListByCache(params){\n\t\t\t\tparams = Common.deepClone({pageNum:this.page,pageSize:this.pageSize},params||{});\n\t\t\t\tparams = Common.deepClone(params,this.paramsOrderStatus);\n\t\t\t\tparams = Common.filterParamByEmptyValue(params);\n\t\t\t\tthis.searchDataCache = params;\n\t\t\t\tthis.getOrderList();\n\t\t\t},\n\t\t\t//获取订单列表\n\t\t\tgetOrderList(){\n\t\t\t\tif(this.loading) return;\n\t\t\t\tthis.loading = true;\n\t\t\t\tvar that = this;\n\t\t\t\tthis.$http.post(this.orderListApi,this.searchDataCache).then(response=>{\n\t\t\t\t\tvar jsonData = response.data;\n\t\t\t\t\tif(jsonData.error_no==0){\n\t\t\t\t\t\tif(jsonData.data){\n\t\t\t\t\t\t\tjsonData.data.items = jsonData.data.items.map(function(item){\n\t\t\t\t\t\t\t\titem.createTime = Common.time2DateFormat(item.createdDate);\n\t\t\t\t\t\t\t\treturn item;\n\t\t\t\t\t\t\t});\n\t\t\t\t\t\t}\n\t\t\t\t\t\tthat.$data.OrderList = jsonData.data || {};\n\t\t\t\t\t\t$(\".el-table__body-wrapper\").scrollTop(0);\n\t\t\t\t\t}else{\n\t\t\t\t\t\tthat.$message({\n\t\t\t\t\t\t\ttype: \"warning\",\n\t\t\t\t\t\t\tmessage: jsonData.error_info || \"服务器异常\"\n\t\t\t\t\t\t});\n\t\t\t\t\t}\n\t\t\t\t\tthat.$data.loading = false;\n\t\t\t\t});\n\t\t\t},\n\t\t\t//获取品牌列表\n\t\t\tgetBrandList(){\n\t\t\t\tvar that = this;\n\t\t\t\tthis.$http.post(this.brandListApi,{status:1,limit:10000000}).then(response=>{\n\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\tthat.brandList = jsondata.error_no == 0 ? jsondata.result_list : [];\n\t\t\t\t});\n\t\t\t},\n\t\t\t//搜索\n\t\t\tsubmitForm(form){\n\t\t\t\t//console.log(this.searchForm.createDateBegin);\n\t\t\t\tthis.$data.page = 1;\n\t\t\t\tthis.getOrderListByCache(this.searchForm);\n\t\t\t},\n\t\t\t//清除\n\t\t\tresetForm(form){\n\t\t\t\tthis.searchForm = this.getDefaultDataForSearch();\n\t\t\t\tthis.exceptDeliveryDare = \"\";\n\t\t\t\tthis.deliveryDate = \"\";\n\t\t\t\tthis.createDate = \"\";\n\t\t\t\tthis.searchForm.createDateBegin = \"\";\n\t\t\t\tthis.searchForm.createDateEnd = \"\";\n\t\t\t\tthis.searchForm.deliveryDateBegin = \"\";\n\t\t\t\tthis.searchForm.deliveryDateEnd = \"\";\n\t\t\t\tthis.searchForm.exceptDeliveryBegin = \"\";\n\t\t\t\tthis.searchForm.exceptDeliveryEnd = \"\";\n\t\t\t\tthis.submitForm();\n\t\t\t},\n\t\t\t//导出\n\t\t\texportTable(){\n\t\t\t\t\n\t\t\t}\n\t\t}\n    }\n</script>\n"],"sourceRoot":"webpack://"}]);
	
	// exports


/***/ }),

/***/ 59:
/***/ (function(module, exports, __webpack_require__) {

	'use strict';
	
	Object.defineProperty(exports, "__esModule", {
		value: true
	});
	
	__webpack_require__(60);
	
	var _common = __webpack_require__(8);
	
	var _common2 = _interopRequireDefault(_common);
	
	function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }
	
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	
	exports.default = {
		data: function data() {
			return {
				tabs: [{ title: "全部", name: "status0" }, { title: "待核算", name: "status1" }, { title: "待付款", name: "status2" }, { title: "已付定金", name: "status3" }, { title: "已付款", name: "status4" }, { title: "退款中", name: "status5" }, { title: "待发货", name: "status6" }, { title: "已发货", name: "status7" }, { title: "成功的订单", name: "status8" }, { title: "关闭的订单", name: "status9" }],
				searchForm: this.getDefaultDataForSearch(), //搜索项表单信息
				OrderList: {}, //订单列表数据缓存
				brandList: [], //品牌列表
				exceptDeliveryDare: '', //搜索项－创建开始时间
				deliveryDate: '', //搜索项－创建结束时间
				createDate: '',
				activeName: "status0",
				ossUrl: "http://test-hjh.oss-cn-shanghai.aliyuncs.com/",
				page: 1, //当前列表页码
				pageSize: 10, //列表－每页条数
				loading: false, //列表加载状态
				brandListApi: "/goodsbrand/queryforweb", //品牌列表接口地址
				orderListApi: "/order-manager-service/orders", //订单列表接口地址
				queryGoodsApi: "/queryGoods", //商品信息接口地址
				clientListApi: "./../getClientList", //买家信息接口地址
				showtooltip: true, //是否当内容过长被隐藏时显示tooltip true显示 flase不显示
				dialogBuyerVisible: false, //买家资料－弹窗显示状态
				dialogBuyerData: {}, //买家资料－数据
				dialogConsigneeVisible: false, //收获地址－弹窗显示状态
				dialogConsigneeData: {}, //收货地址－数据
				dialogCommentVisible: false, //买家留言－弹窗显示状态
				dialogCommentData: {}, //买家留言－数据
				dialogOrderDetailVisible: false, //订单详情－弹窗显示状态
				dialogOrderDetailData: {}, //订单详情－数据
				dialogGoodsDetailVisible: false, //商品详情－弹窗显示状态
				dialogGoodsDetailData: {}, //商品详情－数据
				postageVisible: false, //运费设置－弹窗显示状态
				dialogpostageData: { //运费设置－数据
					balanceDateCount: 0
				},
				deliveryVisible: false, //发货－弹窗显示状态
				dialogDeliveryData: {}, //发货－数据
				dialogRefundOrderVisible: false, //退款确认－弹窗显示状态
				dialogRefundOrderData: {}, //退款确认－数据
				dialogProofsVisible: false, //支付凭证－弹窗显示状态
				dialogProofsData: [], //支付凭证－数据
				dialogProofsModifyVisible: false,
				proofs0List: [],
				proofs1List: [],
				isProofsModify: false,
				dialogLogisticsVisible: false, //发货信息－弹窗显示状态
				dialogLogisticsData: {}, //发货信息－数据
				dialogRefundInfoVisible: false,
				dialogRefundInfoData: {},
				collapseActiveNames: ["1", "2", "3"],
				buyActiveNames: ["1", "2", "3"],
				LogisticsNoteSnapshotList: [],
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
				}
			};
		},
		created: function created() {
			var status = this.$route.params.status || 0;
			this.activeName = "status" + status;
			document.title = "后台管理系统-订单管理";
			this.getParamByTabs();
			this.getOrderListByCache();
			this.getBrandList();
			var that = this;
			window.onhashchange = function () {
				var status = location.hash.match(/\d+/g);
				status = status ? status.join("") : 0;
				that.activeName = "status" + status;
	
				$(".hjh-menu a").each(function () {
					if ($(this).attr("href") == location.hash) {
						$(this).parent().addClass("is-active").siblings().removeClass("is-active");
					}
				});
				if (that.ClickTargetIsTab) {
					that.ClickTargetIsTab = false;
					return;
				} else {
					that.getOrderListByTabs();
				}
			};
		},
	
		methods: {
			/* 切换订单状态tab后根据tab值 设置查询入参 */
			getParamByTabs: function getParamByTabs() {
				var i = Number(this.activeName.replace("status", ""));
				var param = {};
				switch (i) {
					//待核算
					case 1:
						param = {
							orderStatus: ["NON_PAYMENT"],
							isPostageCalculation: false
						};
						break;
					//待付款
					case 2:
						param = {
							orderStatus: ["NON_PAYMENT"],
							isPostageCalculation: true,
							haveDepositPayProof: false,
							haveFullPayProof: false
						};
						break;
					//已付定金
					case 3:
						param = {
							orderStatus: ["NON_PAYMENT", "PART_PAID"],
							haveDepositPayProof: true,
							haveFullPayProof: false
						};
						break;
					//已付款
					case 4:
						param = {
							orderStatus: ["NON_PAYMENT", "PART_PAID"],
							haveFullPayProof: true
						};
						break;
					//退款中
					case 5:
						param = {
							orderStatus: ["REFUNDING"]
						};
						break;
					//待发货
					case 6:
						param = {
							orderStatus: ["UN_DELIVERY"]
						};
						break;
					//已发货
					case 7:
						param = {
							orderStatus: ["DELIVERED"]
						};
						break;
					//成功的订单
					case 8:
						param = {
							orderStatus: ["RECEIVED", "SUCCESS"]
						};
						break;
					//关闭的订单
					case 9:
						param = {
							orderStatus: ["CLOSED_BUYER_CANCEL", "CLOSED_SELLER_CANCEL", "CLOSED_REFUNDED", "CLOSED_RETURN_GOODS", "CLOSED_EXCEPTION"]
						};
						break;
				};
				this.paramsOrderStatus = param;
			},
	
			/** 发货日期发生改变 **/
			deliveryDateChange: function deliveryDateChange(val) {
				if (!val) {
					this.searchForm.deliveryDateBegin = "";
					this.searchForm.deliveryDateEnd = "";
				} else {
					var arr = val.split(" - ");
					this.searchForm.deliveryDateBegin = arr[0].replace(/\s+/g, "T") + ".442Z";
					this.searchForm.deliveryDateEnd = arr[1].replace(/\s+/g, "T") + ".442Z";
				}
			},
	
			/** 下单日期发生改变 **/
			createDateChange: function createDateChange(val) {
				if (!val) {
					this.searchForm.createDateBegin = "";
					this.searchForm.createDateEnd = "";
				} else {
					var arr = val.split(" - ");
					this.searchForm.createDateBegin = arr[0].replace(/\s+/g, "T") + ".442Z";
					this.searchForm.createDateEnd = arr[1].replace(/\s+/g, "T") + ".442Z";
				}
			},
	
			/** tab状态选项卡切换回调 **/
			handleTabsClick: function handleTabsClick(tab) {
				this.ClickTargetIsTab = true;
				this.setMenuActive();
				this.getOrderListByTabs();
			},
	
			/** 静态切换菜单选中状态 **/
			setMenuActive: function setMenuActive() {
				var i = Number(this.activeName.replace("status", ""));
				location.href = "#/orderManagement" + (i ? "/" + i : "");
				$(".hjh-menu a").each(function () {
					if ($(this).attr("href") == location.hash) {
						$(this).parent().addClass("is-active").siblings().removeClass("is-active");
					}
				});
			},
	
			/** 根据tab选中状态加在对应数据列表 **/
			getOrderListByTabs: function getOrderListByTabs() {
				this.getParamByTabs();
				this.submitForm();
			},
	
			//搜索项表单初始化
			getDefaultDataForSearch: function getDefaultDataForSearch() {
				return {
					productNoLike: '',
					productTitleLike: '',
					brandNameLike: '',
					orderNoLike: '',
					userMobileLike: '',
					inviteCodeBegin: '',
					inviteCodeEnd: '',
					payType: ''
				};
			},
	
			//翻页回调
			handleCurrentChange: function handleCurrentChange(val) {
				this.$data.page = val;
				this.searchDataCache.pageNum = val;
				this.getOrderList();
			},
	
			//订单列表 点击订单编号查看订单详情
			checkOrder: function checkOrder(data) {
				var _this = this;
	
				this.$http.post("/order-manager-service/order/progress", {
					access_token: localStorage.access_token,
					orderId: data.orderId
				}).then(function (res) {});
				//return;
				this.dialogOrderDetailData = data;
				this.dialogOrderDetailVisible = true;
				return;
				var param = {
					access_token: localStorage.access_token,
					orderId: data.orderId
				};
				this.$http.post("/dev/order-manager-service/order/orderId", param).then(function (res) {
					if (res.data.error_no == 0) {
						_this.dialogOrderDetailData = res.data.data;
					} else {
						_this.$message({
							type: "warning",
							message: res.data.error_info || "系统繁忙,稍后再试"
						});
					}
				});
			},
	
			//查看商品信息
			checkProductDetail: function checkProductDetail(item) {
				var _this2 = this;
	
				//return;
				var param = {
					goods_id: item.productId,
					goods_name: item.productName
				};
				this.$http.post(this.queryGoodsApi, param).then(function (response) {
					var jsondata = response.data;
					if (jsondata.error_no == 0 && jsondata.result_list.length) {
						_this2.dialogGoodsDetailData = jsondata.result_list[0];
						_this2.dialogGoodsDetailVisible = true;
					} else {
						_this2.$message({
							type: "warning",
							message: "商品详情数据查询异常"
						});
					}
				});
			},
	
			//查看买家信息
			checkBuyer: function checkBuyer(item) {
				var _this3 = this;
	
				var param = _common2.default.filterParamByEmptyValue({
					//nick_name : item.userName
					client_id: item.userId
				});
				this.$http.post("/getClientList", param).then(function (response) {
					var jsondata = response.data;
					if (jsondata.error_no == 0 && jsondata.result_list.length) {
						_this3.dialogBuyerData = jsondata.result_list[0];
						_this3.dialogBuyerVisible = true;
					} else {
						_this3.$message({
							type: "warning",
							message: "买家信息数据查询异常"
						});
					}
				});
			},
	
			//查看收货地址
			checkConsignee: function checkConsignee(data) {
				this.dialogConsigneeVisible = true;
				this.dialogConsigneeData = data;
			},
	
			/** 查看买家留言 **/
			checkComment: function checkComment(data) {
				this.dialogCommentVisible = true;
				this.dialogCommentData = data;
			},
	
			/** 查看支付凭证 **/
			eventCheckProofs: function eventCheckProofs(item) {
				var _this4 = this;
	
				var api = "/order-manager-service/order/orderId/pay/getproofs",
				    param = {
					access_token: localStorage.access_token,
					orderId: item.orderId
				};
				this.$http.post("/order-manager-service/order/orderId/pay/getproofs", param).then(function (response) {
					var jsondata = response.data;
					if (jsondata.error_no == 0) {
						_this4.dialogProofsData = jsondata.data;
						_this4.dialogProofsData = _this4.dialogProofsData.map(function (item) {
							item.createDate = _common2.default.time2DateFormat(item.createDate);
							item.amount = Number(item.amount).toFixed(2);
							return item;
						});
						_this4.isProofsModify = false;
						_this4.dialogProofsVisible = true;
					} else {
						_this4.$message({
							type: "warning",
							message: jsondata.error_info
						});
					}
				});
			},
	
			/** 点击按钮－编辑支付凭证 **/
			eventModifyProofs: function eventModifyProofs(item) {
				var _this5 = this;
	
				var api = "/order-manager-service/order/orderId/pay/getproofs",
				    param = {
					access_token: localStorage.access_token,
					orderId: item.orderId
				};
				this.$http.post("/order-manager-service/order/orderId/pay/getproofs", param).then(function (response) {
					var jsondata = response.data;
					if (jsondata.error_no == 0) {
						_this5.dialogProofsData = jsondata.data;
						var that = _this5;
						_this5.dialogProofsData = _this5.dialogProofsData.map(function (item, index) {
							item.priviewSnapshot = "http://test-hjh.oss-cn-shanghai.aliyuncs.com/" + item.paymentProofSnapshot;
							item.createDate = _common2.default.time2DateFormat(item.createDate);
							item.amount = Number(item.amount).toFixed(2);
							if (index == 0) {
								that.proofs0List = [{
									name: item.paymentProofSnapshot,
									url: 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/' + item.paymentProofSnapshot,
									status: 'finished'
								}];
							}
							if (index == 1) {
								that.proofs1List = [{
									name: item.paymentProofSnapshot,
									url: 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/' + item.paymentProofSnapshot,
									status: 'finished'
								}];
							}
							return item;
						});
	
						_this5.isProofsModify = true;
						_this5.dialogProofsModifyVisible = true;
					} else {
						_this5.$message({
							type: "warning",
							message: jsondata.error_info
						});
					}
				});
			},
	
			/** 修改支付凭证 **/
			handleModifyProofs: function handleModifyProofs(item) {
				var access_token = localStorage.access_token;
				var that = this,
				    reg = /data:\S+\"/g;
				this.proofUpdatedCount = 0;
				item.map(function (child) {
					//var arr = child.paymentProofSnapshot.match(reg);
					var bool = child.paymentProofSnapshot.indexOf("data:") == 0;
					var param = {
						access_token: access_token,
						amount: child.amount,
						bankAccount: child.bankAccount,
						payProofId: child.id,
						paymentAccount: child.paymentAccount,
						paymentProofSnapshot: child.paymentProofSnapshot,
						paymentUserName: child.paymentUserName,
						orderId: child.orderId
					};
					if (bool) {
						that.uploadCompressImg(child.paymentProofSnapshot, function (img_uri) {
							param.paymentProofSnapshot = img_uri;
							that.updateProof(param);
						});
					} else {
						that.updateProof(param);
					}
				});
			},
	
			/** 上传图片 **/
			uploadCompressImg: function uploadCompressImg(result, cb) {
				var that = this,
				    param = {
					access_token: localStorage.access_token,
					base64Str: result,
					imgType: 19
				};
				that.$http.post("/json/901210", param).then(function (response) {
					var jsondata = response.data;
					if (jsondata.error_no == 0) {
						cb(jsondata.img_uri);
					} else {
						that.$message({
							type: "warning",
							message: jsondata.error_on + ":" + jsondata.error_info
						});
					}
				});
			},
	
			/** 更新凭证 **/
			updateProof: function updateProof(param) {
				var _this6 = this;
	
				var api = "/order-manager-service/order/orderId/pay/proof";
				this.$http.post(api, param).then(function (response) {
					var jsondata = response.data;
					_this6.$message({
						type: jsondata.error_no == 0 ? "success" : "warning",
						message: jsondata.error_no == 0 ? "修改成功" : jsondata.error_no + ":" + jsondata.error_info
					});
					if (jsondata.error_no == 0) {
						_this6.proofUpdatedCount++;
						if (_this6.proofUpdatedCount == _this6.dialogProofsData.length) {
							_this6.isProofsModify = true;
							_this6.dialogProofsModifyVisible = false;
						}
					}
				});
			},
	
			/** 支付凭证1的上传文件变化 **/
			handleFileChange: function handleFileChange(_file, _filelist) {
				this.proofs0List = [_filelist[_filelist.length - 1]];
				var that = this;
				var reader = new FileReader();
				reader.readAsDataURL(_file.raw);
				reader.onload = function (e) {
					var img = new Image();
					img.src = this.result;
					img.onload = function () {
						that.dialogProofsData[0].paymentProofSnapshot = _common2.default.compressImg(img, 0.8, "image/jpeg");
						that.dialogProofsData[0].priviewSnapshot = _common2.default.compressImg(img, 0.8, "image/jpeg");
						$(".el-upload-list").eq(0).children("li").last().prev().remove();
					};
				};
			},
	
			/** 支付凭证1的上传文件删除 **/
			handleFileRemove: function handleFileRemove(_file, _filelist) {
				if (!_filelist.length) {
					this.dialogProofsData[0].paymentProofSnapshot = "";
					this.dialogProofsData[0].priviewSnapshot = "";
					this.proofs0List = [];
				}
			},
	
			/** 支付凭证2的上传文件变化 **/
			handleFileChange1: function handleFileChange1(_file, _filelist) {
				this.proofs1List = [_filelist[_filelist.length - 1]];
				var that = this;
				var reader = new FileReader();
				reader.readAsDataURL(_file.raw);
				reader.onload = function (e) {
					var img = new Image();
					img.src = this.result;
					img.onload = function () {
						that.dialogProofsData[1].paymentProofSnapshot = _common2.default.compressImg(img, 0.8, "image/jpeg");
						that.dialogProofsData[1].priviewSnapshot = _common2.default.compressImg(img, 0.8, "image/jpeg");
						$(".el-upload-list").eq(1).children("li").last().prev().remove();
					};
				};
			},
	
			/** 支付凭证2的上传文件删除 **/
			handleFileRemove1: function handleFileRemove1(_file, _filelist) {
				if (!_filelist.length) {
					this.dialogProofsData[1].paymentProofSnapshot = "";
					this.dialogProofsData[1].priviewSnapshot = "";
					this.proofs1List = [];
				}
			},
	
			/**  点击按钮－运费核算 **/
			accountingPostage: function accountingPostage(item) {
				//var time = new Date(item.estimateDeliveryDesc).getTime();
				this.dialogpostageData = {
					orderNo: item.orderNo,
					balanceDateCount: item.balanceDateCount,
					//estimateDeliveryDesc : time ? new Date(item.estimateDeliveryDesc) : "",
					estimateDeliveryDesc: item.estimateDeliveryDesc,
					orderId: item.orderId,
					postage: item.postage,
					transactionType: item.transactionType
				};
	
				this.postageVisible = true;
			},
	
			/** 提交数据－运费核算 **/
			postageAndDeliveryDate: function postageAndDeliveryDate(formdata) {
				var _this7 = this;
	
				this.$refs[this.dialogpostageData].validate(function (valid) {
					if (valid) {
						var param = {
							access_token: localStorage.access_token,
							balanceDateCount: formdata.balanceDateCount,
							estimateDeliveryDesc: formdata.estimateDeliveryDesc || _common2.default.time2DateFormat(formdata.estimateDeliveryDesc).split(" ")[0],
							orderId: formdata.orderId,
							postage: formdata.postage
						},
						    api = "/order-manager-service/order/orderId/transaction/postageAndDeliveryDate";
						//if(formdata.transactionType==0){
						//	delete param.balanceDateCount;
						//}
						_this7.$http.post(api, param).then(function (response) {
							var jsondata = response.data;
							_this7.$message({
								type: jsondata.error_no == 0 ? "success" : "warning",
								message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
							});
							if (jsondata.error_no == 0) {
								_this7.postageVisible = false;
								_this7.getOrderListByCache();
							}
						});
					}
				});
			},
	
			/** 已付定金－确认收款 **/
			eventDeposit: function eventDeposit(item) {
				var _this8 = this;
	
				this.$confirm('确认收款，即视为实际收款金额等于应支付定金！', '确认收款', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(function () {
					var api = "/order-manager-service/order/orderId/transaction/deposit",
					    param = {
						access_token: localStorage.access_token,
						orderId: item.orderId,
						deposit: item.transactionDeposit
					};
					_this8.$http.post(api, param).then(function (response) {
						var jsondata = response.data;
						_this8.$message({
							type: jsondata.error_no == 0 ? "success" : "warning",
							message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
						});
						if (jsondata.error_no == 0) {
							_this8.getOrderListByCache();
						}
					});
				});
			},
	
			/** 已付款－确认收款 **/
			eventBalance: function eventBalance(item) {
				var _this9 = this;
	
				this.$confirm('请确认实际收款总额等于订单总额。', '确认收款', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(function () {
					var api = item.transactionType == 1 ? "/order-manager-service/order/orderId/transaction/balance" : "/order-manager-service/order/transaction/actualAmount",
					    param = {
						access_token: localStorage.access_token,
						orderId: item.orderId,
						balance: item.transactionBalance
					};
					if (item.transactionType == 0) {
						delete param.balance;
						param.amount = item.transactionPayAmount;
					}
					console.log(api, param);
					_this9.$http.post(api, param).then(function (response) {
						var jsondata = response.data;
						_this9.$message({
							type: jsondata.error_no == 0 ? "success" : "warning",
							message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
						});
						if (jsondata.error_no == 0) {
							_this9.getOrderListByCache();
						}
					});
				});
			},
	
			/** 点击按钮－取消订单 **/
			eventCancelOrder: function eventCancelOrder(item) {
				var _this10 = this;
	
				this.$confirm('实际已付款的订单请勿取消订单！取消订单后订单关闭！', '取消订单', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(function () {
					var api = "/order-manager-service/order/cancelOrder",
					    param = {
						access_token: localStorage.access_token,
						orderId: item.orderId
					};
					_this10.$http.post(api, param).then(function (response) {
						var jsondata = response.data;
						_this10.$message({
							type: jsondata.error_no == 0 ? "success" : "warning",
							message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
						});
						if (jsondata.error_no == 0) {
							_this10.getOrderListByCache();
						}
					});
				});
			},
	
			/** 点击按钮－退款确认 **/
			eventRefundOrderComplete: function eventRefundOrderComplete(item) {
				this.dialogRefundOrderData.max = item.transactionPayAmount;
				this.dialogRefundOrderData.orderId = item.orderId;
				this.dialogRefundOrderVisible = true;
			},
	
			/** 提交操作－退款确认 **/
			handleRefundOrderComplete: function handleRefundOrderComplete() {
				var _this11 = this;
	
				if (!this.dialogRefundOrderData.refundingAmount) {
					this.$message({
						type: "warning",
						message: "请填写退款金额"
					});
					return;
				}
				if (this.dialogRefundOrderData.refundingAmount > this.dialogRefundOrderData.max) {
					this.$message({
						type: "warning",
						message: "退款金额超额"
					});
					return;
				}
				if (!this.dialogRefundOrderData.refundingExplain) {
					this.$message({
						type: "warning",
						message: "请填写退款说明"
					});
					return;
				}
	
				this.dialogRefundOrderData.access_token = localStorage.access_token;
				var api = "/order-manager-service/order/refundOrderComplete";
				this.$http.post(api, this.dialogRefundOrderData).then(function (response) {
					var jsondata = response.data;
					_this11.$message({
						type: jsondata.error_no == 0 ? "success" : "warning",
						message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
					});
					if (jsondata.error_no == 0) {
						_this11.dialogRefundOrderVisible = false;
						_this11.getOrderListByCache();
					}
				});
			},
	
			/** 点击按钮－发货 **/
			eventDelivery: function eventDelivery(item) {
				this.dialogDeliveryData = {
					orderId: item.orderId,
					orderNo: item.orderNo,
					logisticsCompany: "",
					logisticsNo: "",
					logisticsNoteSnapshot: "",
					deliveryDate: ""
				};
				this.LogisticsNoteSnapshotList = [];
				this.deliveryVisible = true;
				$("#deliveryDate input").val("");
			},
	
			/** 提交操作－发货 **/
			handleDelivery: function handleDelivery(formdata) {
				var _this12 = this;
	
				this.$refs[this.dialogDeliveryData].validate(function (valid) {
					if (!valid) {
						return;
					}
					_this12.dialogDeliveryData.access_token = localStorage.access_token;
					_this12.dialogDeliveryData.deliveryDate = _common2.default.time2DateFormat(_this12.dialogDeliveryData.deliveryDate).replace(/\s+/g, "T") + ".442Z";
					var api = "/order-manager-service/order/orderId/logistics/delivery";
					_this12.$http.post(api, _this12.dialogDeliveryData).then(function (response) {
						var jsondata = response.data;
						_this12.$message({
							type: jsondata.error_no == 0 ? "success" : "warning",
							message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_no + jsondata.error_info
						});
						if (jsondata.error_no == 0) {
							_this12.deliveryVisible = false;
							_this12.getOrderListByCache();
						}
					});
				});
			},
	
			/** 发货－选择运单照片 **/
			handleLogisticsNoteSnapshotChange: function handleLogisticsNoteSnapshotChange(_file, _filelist) {
				this.LogisticsNoteSnapshotList = [_filelist[_filelist.length - 1]];
				var that = this;
				var reader = new FileReader();
				reader.readAsDataURL(_file.raw);
				reader.onload = function (e) {
					var img = new Image();
					img.src = this.result;
					img.onload = function () {
						that.dialogDeliveryData.logisticsNoteSnapshot = _common2.default.compressImg(img, 0.8, "image/jpeg");
						$(".el-upload-list > li").last().prev().remove();
					};
				};
			},
	
			/** 发货－删除运单照片 **/
			handleLogisticsNoteSnapshotRemove: function handleLogisticsNoteSnapshotRemove(_file, _filelist) {
				if (!_filelist.length) {
					this.dialogDeliveryData.logisticsNoteSnapshot = "";
				}
			},
	
			/** 点击按钮－查看发货信息 **/
			eventGetLogistics: function eventGetLogistics(item) {
				var _this13 = this;
	
				var api = "/order-manager-service/order/orderId/logistics/getLogistics",
				    param = {
					access_token: localStorage.access_token,
					orderId: item.orderId
				};
				this.$http.post(api, param).then(function (response) {
					var jsondata = response.data;
					if (jsondata.error_no == 0) {
						_this13.dialogLogisticsData = jsondata.data;
						_this13.dialogLogisticsData.createDate = _common2.default.time2DateFormat(_this13.dialogLogisticsData.createDate);
						_this13.dialogLogisticsData.deliveryDate = _common2.default.time2DateFormat(_this13.dialogLogisticsData.deliveryDate);
						_this13.dialogLogisticsVisible = true;
					} else {
						_this13.$message({
							type: "warning",
							message: jsondata.error_info
						});
					}
				});
			},
	
			/** 点击按钮确认到货 **/
			eventReceived: function eventReceived(item) {
				var _this14 = this;
	
				this.$confirm('确认到货后，该订单流程结束', '你确认已到货吗？', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(function () {
					var api = "/order-manager-service/order/orderId/logistics/received",
					    param = {
						access_token: localStorage.access_token,
						orderId: item.orderId
					};
					_this14.$http.post(api, param).then(function (response) {
						var jsondata = response.data;
						_this14.$message({
							type: jsondata.error_no == 0 ? "success" : "warning",
							message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
						});
						if (jsondata.error_no == 0) {
							_this14.getOrderListByCache();
						}
					});
				});
			},
	
			/** 点击按钮－退款信息 **/
			eventCheckRefund: function eventCheckRefund(item) {
				this.dialogRefundInfoVisible = true;
				this.dialogRefundInfoData = item;
			},
	
			/** 根据入参缓存进行查询 **/
			getOrderListByCache: function getOrderListByCache(params) {
				params = _common2.default.deepClone({ pageNum: this.page, pageSize: this.pageSize }, params || {});
				params = _common2.default.deepClone(params, this.paramsOrderStatus);
				params = _common2.default.filterParamByEmptyValue(params);
				this.searchDataCache = params;
				this.getOrderList();
			},
	
			//获取订单列表
			getOrderList: function getOrderList() {
				if (this.loading) return;
				this.loading = true;
				var that = this;
				this.$http.post(this.orderListApi, this.searchDataCache).then(function (response) {
					var jsonData = response.data;
					if (jsonData.error_no == 0) {
						if (jsonData.data) {
							jsonData.data.items = jsonData.data.items.map(function (item) {
								item.createTime = _common2.default.time2DateFormat(item.createdDate);
								return item;
							});
						}
						that.$data.OrderList = jsonData.data || {};
						$(".el-table__body-wrapper").scrollTop(0);
					} else {
						that.$message({
							type: "warning",
							message: jsonData.error_info || "服务器异常"
						});
					}
					that.$data.loading = false;
				});
			},
	
			//获取品牌列表
			getBrandList: function getBrandList() {
				var that = this;
				this.$http.post(this.brandListApi, { status: 1, limit: 10000000 }).then(function (response) {
					var jsondata = response.data;
					that.brandList = jsondata.error_no == 0 ? jsondata.result_list : [];
				});
			},
	
			//搜索
			submitForm: function submitForm(form) {
				//console.log(this.searchForm.createDateBegin);
				this.$data.page = 1;
				this.getOrderListByCache(this.searchForm);
			},
	
			//清除
			resetForm: function resetForm(form) {
				this.searchForm = this.getDefaultDataForSearch();
				this.exceptDeliveryDare = "";
				this.deliveryDate = "";
				this.createDate = "";
				this.searchForm.createDateBegin = "";
				this.searchForm.createDateEnd = "";
				this.searchForm.deliveryDateBegin = "";
				this.searchForm.deliveryDateEnd = "";
				this.searchForm.exceptDeliveryBegin = "";
				this.searchForm.exceptDeliveryEnd = "";
				this.submitForm();
			},
	
			//导出
			exportTable: function exportTable() {}
		}
	};

/***/ }),

/***/ 60:
/***/ (function(module, exports) {

	// removed by extract-text-webpack-plugin

/***/ }),

/***/ 61:
/***/ (function(module, exports, __webpack_require__) {

	module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: "orderManagement"
	  }, [_c('div', {
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
	  }, [_vm._v("用户中心")]), _vm._v(" "), _c('el-breadcrumb-item', [_vm._v("订单管理")]), _vm._v(" "), _c('el-breadcrumb-item', [_vm._v("订单管理")])], 1)], 1), _vm._v(" "), _c('div', {
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
	      "placeholder": "请输入商品编号"
	    },
	    model: {
	      value: (_vm.searchForm.productNoLike),
	      callback: function($$v) {
	        _vm.searchForm.productNoLike = $$v
	      },
	      expression: "searchForm.productNoLike"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', [_c('el-input', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "请输入商品标题"
	    },
	    model: {
	      value: (_vm.searchForm.productTitleLike),
	      callback: function($$v) {
	        _vm.searchForm.productTitleLike = $$v
	      },
	      expression: "searchForm.productTitleLike"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', [_c('el-select', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "filterable": "",
	      "placeholder": "请选择商品品牌"
	    },
	    model: {
	      value: (_vm.searchForm.brandNameLike),
	      callback: function($$v) {
	        _vm.searchForm.brandNameLike = $$v
	      },
	      expression: "searchForm.brandNameLike"
	    }
	  }, _vm._l((_vm.brandList), function(item) {
	    return _c('el-option', {
	      key: item.brand_name,
	      attrs: {
	        "label": item.brand_name,
	        "value": item.brand_name
	      }
	    })
	  }))], 1), _vm._v(" "), _c('el-form-item', [_c('el-input', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "请输入订单编号"
	    },
	    model: {
	      value: (_vm.searchForm.orderNoLike),
	      callback: function($$v) {
	        _vm.searchForm.orderNoLike = $$v
	      },
	      expression: "searchForm.orderNoLike"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', [_c('el-input', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "买家联系方式"
	    },
	    model: {
	      value: (_vm.searchForm.userMobileLike),
	      callback: function($$v) {
	        _vm.searchForm.userMobileLike = $$v
	      },
	      expression: "searchForm.userMobileLike"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', [_c('el-col', {
	    attrs: {
	      "span": 11
	    }
	  }, [_c('el-input', {
	    attrs: {
	      "placeholder": "邀请码区间查询开始"
	    },
	    model: {
	      value: (_vm.searchForm.inviteCodeBegin),
	      callback: function($$v) {
	        _vm.searchForm.inviteCodeBegin = $$v
	      },
	      expression: "searchForm.inviteCodeBegin"
	    }
	  })], 1), _vm._v(" "), _c('el-col', {
	    staticClass: "line",
	    staticStyle: {
	      "text-align": "center"
	    },
	    attrs: {
	      "span": 2
	    }
	  }, [_vm._v("－")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 11
	    }
	  }, [_c('el-input', {
	    attrs: {
	      "placeholder": "邀请码区间查询结束"
	    },
	    model: {
	      value: (_vm.searchForm.inviteCodeEnd),
	      callback: function($$v) {
	        _vm.searchForm.inviteCodeEnd = $$v
	      },
	      expression: "searchForm.inviteCodeEnd"
	    }
	  })], 1)], 1), _vm._v(" "), _c('el-form-item', [_c('el-select', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "请选择支付方式"
	    },
	    model: {
	      value: (_vm.searchForm.payType),
	      callback: function($$v) {
	        _vm.searchForm.payType = $$v
	      },
	      expression: "searchForm.payType"
	    }
	  }, [_c('el-option', {
	    attrs: {
	      "label": "银行汇款",
	      "value": "2"
	    }
	  }), _vm._v(" "), _c('el-option', {
	    attrs: {
	      "label": "支付宝",
	      "value": "1"
	    }
	  }), _vm._v(" "), _c('el-option', {
	    attrs: {
	      "label": "微信",
	      "value": "0"
	    }
	  })], 1)], 1), _vm._v(" "), _c('el-form-item', [_c('el-input', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "预计发货日期"
	    },
	    model: {
	      value: (_vm.searchForm.estimateDeliveryDescLike),
	      callback: function($$v) {
	        _vm.searchForm.estimateDeliveryDescLike = $$v
	      },
	      expression: "searchForm.estimateDeliveryDescLike"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', [_c('el-date-picker', {
	    attrs: {
	      "type": "datetimerange",
	      "picker-options": _vm.pickerOptions,
	      "placeholder": "发货日期",
	      "align": "right"
	    },
	    on: {
	      "change": _vm.deliveryDateChange
	    },
	    model: {
	      value: (_vm.deliveryDate),
	      callback: function($$v) {
	        _vm.deliveryDate = $$v
	      },
	      expression: "deliveryDate"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', [_c('el-date-picker', {
	    attrs: {
	      "type": "datetimerange",
	      "picker-options": _vm.pickerOptions,
	      "placeholder": "下单日期",
	      "align": "right"
	    },
	    on: {
	      "change": _vm.createDateChange
	    },
	    model: {
	      value: (_vm.createDate),
	      callback: function($$v) {
	        _vm.createDate = $$v
	      },
	      expression: "createDate"
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
	  }, [_vm._v("清除")]), _vm._v(" "), _c('el-button', {
	    on: {
	      "click": _vm.exportTable
	    }
	  }, [_vm._v("导出")])], 1)], 1)], 1), _vm._v(" "), _c('div', {
	    staticClass: "block-white"
	  }, [_c('el-tabs', {
	    on: {
	      "tab-click": _vm.handleTabsClick
	    },
	    model: {
	      value: (_vm.activeName),
	      callback: function($$v) {
	        _vm.activeName = $$v
	      },
	      expression: "activeName"
	    }
	  }, _vm._l((_vm.tabs), function(item) {
	    return _c('el-tab-pane', {
	      attrs: {
	        "label": item.title,
	        "name": item.name
	      }
	    })
	  }))], 1), _vm._v(" "), _c('div', {
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
	      "data": _vm.OrderList.items,
	      "border": "",
	      "stripe": "",
	      "max-height": "550"
	    }
	  }, [_c('el-table-column', {
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
	      "label": "商品信息",
	      "align": "center",
	      "width": "450"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_c('div', {
	          staticClass: "order-scope"
	        }, [_vm._v("\n\t\t\t\t\t\t\t订单编号："), _c('a', {
	          on: {
	            "click": function($event) {
	              _vm.checkOrder(scope.row)
	            }
	          }
	        }, [_vm._v(_vm._s(scope.row.orderNo))]), _vm._v("\n\t\t\t\t\t\t\t订单时间：" + _vm._s(scope.row.createTime) + "\n\t\t\t\t\t\t")]), _vm._v(" "), _c('ul', {
	          staticClass: "productsList"
	        }, _vm._l((scope.row.productList), function(item) {
	          return _c('li', [_c('div', {
	            staticClass: "product-pic"
	          }, [_c('img', {
	            attrs: {
	              "src": 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/' + item.pictureCode
	            }
	          })]), _vm._v(" "), _c('div', {
	            staticClass: "product-info",
	            on: {
	              "click": function($event) {
	                _vm.checkProductDetail(item)
	              }
	            }
	          }, [_c('div', {
	            staticClass: "product-name"
	          }, [_vm._v(_vm._s(item.productName))]), _vm._v(" "), _c('div', {
	            staticClass: "product-id"
	          }, [_vm._v(_vm._s(item.productId))]), _vm._v(" "), _c('div', {
	            staticClass: "product-brand"
	          }, [_c('div', {
	            staticClass: "fl"
	          }, [_vm._v("品牌：" + _vm._s(item.brandName))]), _vm._v(" "), _c('div', {
	            staticClass: "fr"
	          }, [_vm._v("类目：" + _vm._s(item.categoryName || item.orderItemList[0].categoryName))])])])])
	        }))]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "label": "订单详情",
	      "align": "center"
	    }
	  }, [_c('el-table-column', {
	    attrs: {
	      "prop": "",
	      "label": "规格",
	      "width": "250"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_c('div', {
	          staticClass: "order-scope"
	        }, [(scope.row.orderStatus == 1 && scope.row.payDeposit && !scope.row.payBalance) ? _c('div', [(scope.row.balanceDateCount > 0) ? _c('span', [_vm._v("余款延期：" + _vm._s(scope.row.balanceDateCount) + "天")]) : _vm._e(), _vm._v(" "), (scope.row.balanceDateCount <= 0) ? _c('span', [_vm._v("定金未确认收款，请联系客服")]) : _vm._e()]) : _vm._e(), _vm._v(" "), (scope.row.orderStatus == 2 && scope.row.payDeposit && !scope.row.payBalance) ? _c('div', [(scope.row.balanceDateCount > 0) ? _c('span', [_vm._v(_vm._s(scope.row.balanceDateCount) + "天后可付款")]) : _vm._e(), _vm._v(" "), (scope.row.balanceDateCount <= 0) ? _c('span', {
	          staticStyle: {
	            "color": "#f00"
	          }
	        }, [_vm._v("等待支付余款")]) : _vm._e()]) : _vm._e()]), _vm._v(" "), _vm._l((scope.row.productList), function(item) {
	          return _c('ul', {
	            staticClass: "Especificaciones"
	          }, _vm._l((item.orderItemList), function(_item) {
	            return _c('li', [_c('span', [_vm._v(_vm._s(_item.productStandardMust) + " | " + _vm._s(_item.productOptionalFirst) + " | " + _vm._s(_item.productOptionalSecond))])])
	          }))
	        })]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "",
	      "label": "商品价格",
	      "width": "120",
	      "align": "center",
	      "show-overflow-tooltip": _vm.showtooltip
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return _vm._l((scope.row.productList), function(item) {
	          return _c('div', {
	            staticClass: "productPrice"
	          }, _vm._l((item.orderItemList), function(_item) {
	            return _c('div', [_vm._v("\n\t\t\t\t\t\t\t\t\t" + _vm._s(_item.price.toFixed(2)) + "/" + _vm._s(_item.unit) + "\n\t\t\t\t\t\t\t\t")])
	          }))
	        })
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "",
	      "label": "采购数量",
	      "width": "120",
	      "align": "center",
	      "show-overflow-tooltip": _vm.showtooltip
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return _vm._l((scope.row.productList), function(item) {
	          return _c('div', {
	            staticClass: "productPrice"
	          }, _vm._l((item.orderItemList), function(_item) {
	            return _c('div', [_c('span', {
	              staticStyle: {
	                "color": "#f00"
	              }
	            }, [_vm._v(_vm._s(_item.quantity))])])
	          }))
	        })
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "",
	      "label": "金额",
	      "align": "center",
	      "width": "120",
	      "show-overflow-tooltip": _vm.showtooltip
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return _vm._l((scope.row.productList), function(item) {
	          return _c('div', {
	            staticClass: "productPrice"
	          }, _vm._l((item.orderItemList), function(_item) {
	            return _c('div', [_vm._v("\n\t\t\t\t\t\t\t\t\t" + _vm._s(_item.amount.toFixed(2)) + "\n\t\t\t\t\t\t\t\t")])
	          }))
	        })
	      }
	    }])
	  })], 1), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "transactionPayAmount",
	      "label": "订单总额",
	      "align": "center",
	      "width": "120",
	      "show-overflow-tooltip": _vm.showtooltip
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_c('div', {
	          staticStyle: {
	            "color": "#f00"
	          }
	        }, [_vm._v(_vm._s(scope.row.transactionPayAmount))])]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "",
	      "label": "支付信息",
	      "align": "center",
	      "width": "200",
	      "show-overflow-tooltip": _vm.showtooltip
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [(_vm.activeName !== 'status1' && _vm.activeName !== 'status2') ? _c('div', [(scope.row.transactionType == 1) ? _c('div', [_c('div', [_vm._v("定金：" + _vm._s((scope.row.orderStatus == 1 && scope.row.payDeposit) ? "待确认" : ("¥" + scope.row.transactionDeposit)))]), _vm._v(" "), (!scope.row.payBalance) ? _c('div', {
	          staticStyle: {
	            "color": "#f00"
	          }
	        }, [_vm._v("\n\t\t\t\t\t\t\t\t\t待付余款：¥" + _vm._s(scope.row.transactionBalance || (scope.row.transactionPayAmount - scope.row.transactionDeposit).toFixed(2)) + "\n\t\t\t\t\t\t\t\t")]) : _vm._e(), _vm._v(" "), (scope.row.transactionActualPayBalance && scope.row.payBalance) ? _c('div', [_vm._v("\n\t\t\t\t\t\t\t\t\t余款：" + _vm._s(scope.row.transactionActualPayBalance) + "\n\t\t\t\t\t\t\t\t")]) : _vm._e(), _vm._v(" "), ((scope.row.orderStatus == 1 || scope.row.orderStatus == 2) && scope.row.payBalance) ? _c('div', [_vm._v("\n\t\t\t\t\t\t\t\t\t余款：待确认\n\t\t\t\t\t\t\t\t")]) : _vm._e()]) : _vm._e(), _vm._v(" "), (scope.row.transactionType == 0) ? _c('div', [_vm._v("\n\t\t\t\t\t\t\t\t收款金额：" + _vm._s(!scope.row.payBalance ? "待确认" : ("¥" + (scope.row.transactionActualPayAmount || scope.row.transactionPayAmount))) + "\n\t\t\t\t\t\t\t")]) : _vm._e()]) : _vm._e()]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "",
	      "label": "买家信息",
	      "align": "center",
	      "width": "300",
	      "show-overflow-tooltip": _vm.showtooltip
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_c('div', [_c('a', {
	          staticClass: "consigneeLink",
	          on: {
	            "click": function($event) {
	              _vm.checkBuyer(scope.row)
	            }
	          }
	        }, [_vm._v(_vm._s(scope.row.userName))])]), _vm._v(" "), _c('div', [_vm._v(_vm._s(scope.row.consigneeMobile))]), _vm._v(" "), _c('div', [_vm._v(_vm._s(scope.row.inviteCode))])]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "",
	      "label": "发货信息",
	      "width": "150",
	      "align": "center",
	      "show-overflow-tooltip": _vm.showtooltip
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [(scope.row.estimateDeliveryDesc) ? _c('div', [_vm._v(_vm._s(scope.row.estimateDeliveryDesc))]) : _vm._e(), _vm._v(" "), (scope.row.postageCalculation) ? _c('div', [_vm._v("运费：¥" + _vm._s(scope.row.postage))]) : _vm._e(), _vm._v(" "), _c('div', [_c('a', {
	          staticClass: "consigneeLink",
	          on: {
	            "click": function($event) {
	              _vm.checkConsignee(scope.row)
	            }
	          }
	        }, [_vm._v("收货地址")])]), _vm._v(" "), (scope.row.buyerComments) ? _c('div', [_c('a', {
	          staticClass: "consigneeLink",
	          on: {
	            "click": function($event) {
	              _vm.checkComment(scope.row)
	            }
	          }
	        }, [_vm._v("买家留言")])]) : _vm._e()]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "fixed": "right",
	      "label": "操作",
	      "align": "center",
	      "width": "120"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [(scope.row.orderStatus == 1 && !scope.row.postage && (_vm.activeName == 'status1' || _vm.activeName == 'status0')) ? _c('div', {
	          attrs: {
	            "data-title": "待核算"
	          }
	        }, [_c('el-button', {
	          attrs: {
	            "type": "info"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.accountingPostage(scope.row)
	            }
	          }
	        }, [_vm._v("运费核算")])], 1) : _vm._e(), _vm._v(" "), (scope.row.orderStatus == 1 && scope.row.postage && !scope.row.payDeposit && !scope.row.payBalance && (_vm.activeName == 'status2' || _vm.activeName == 'status0')) ? _c('div', {
	          attrs: {
	            "data-title": "待付款"
	          }
	        }, [_c('el-button', {
	          attrs: {
	            "type": "warning"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.accountingPostage(scope.row)
	            }
	          }
	        }, [_vm._v("修改运费")])], 1) : _vm._e(), _vm._v(" "), (scope.row.orderStatus == 1 && scope.row.payDeposit && !scope.row.payBalance && (_vm.activeName == 'status3' || _vm.activeName == 'status0')) ? _c('div', {
	          attrs: {
	            "data-title": "已付定金－未确认收款"
	          }
	        }, [_c('el-button', {
	          attrs: {
	            "type": "info"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.eventCheckProofs(scope.row)
	            }
	          }
	        }, [_vm._v("支付凭证")]), _vm._v(" "), _c('el-button', {
	          attrs: {
	            "type": "warning"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.eventModifyProofs(scope.row)
	            }
	          }
	        }, [_vm._v("编辑凭证")]), _vm._v(" "), _c('el-button', {
	          attrs: {
	            "type": "info"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.eventDeposit(scope.row)
	            }
	          }
	        }, [_vm._v("确认收款")]), _vm._v(" "), _c('el-button', {
	          attrs: {
	            "type": "danger"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.eventCancelOrder(scope.row)
	            }
	          }
	        }, [_vm._v("取消订单")])], 1) : _vm._e(), _vm._v(" "), (scope.row.orderStatus == 2 && scope.row.payDeposit && !scope.row.payBalance && (_vm.activeName == 'status3' || _vm.activeName == 'status0')) ? _c('div', {
	          attrs: {
	            "data-title": "已付定金－已确认收款"
	          }
	        }, [_c('el-button', {
	          attrs: {
	            "type": "info"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.eventCheckProofs(scope.row)
	            }
	          }
	        }, [_vm._v("支付凭证")])], 1) : _vm._e(), _vm._v(" "), ((scope.row.orderStatus == 1 || scope.row.orderStatus == 2) && scope.row.payBalance && (_vm.activeName == 'status4' || _vm.activeName == 'status0')) ? _c('div', {
	          attrs: {
	            "data-title": "已付款"
	          }
	        }, [_c('el-button', {
	          attrs: {
	            "type": "info"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.eventCheckProofs(scope.row)
	            }
	          }
	        }, [_vm._v("支付凭证")]), _vm._v(" "), _c('el-button', {
	          attrs: {
	            "type": "warning"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.eventModifyProofs(scope.row)
	            }
	          }
	        }, [_vm._v("编辑凭证")]), _vm._v(" "), _c('el-button', {
	          attrs: {
	            "type": "info"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.eventBalance(scope.row)
	            }
	          }
	        }, [_vm._v("确认收款")]), _vm._v(" "), (scope.row.transactionType == 0) ? _c('el-button', {
	          attrs: {
	            "type": "danger"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.eventCancelOrder(scope.row)
	            }
	          }
	        }, [_vm._v("取消订单")]) : _vm._e()], 1) : _vm._e(), _vm._v(" "), (scope.row.orderStatus == 9) ? _c('div', {
	          attrs: {
	            "data-title": "退款中"
	          }
	        }, [_c('el-button', {
	          attrs: {
	            "type": "info"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.eventCheckProofs(scope.row)
	            }
	          }
	        }, [_vm._v("支付凭证")]), _vm._v(" "), _c('el-button', {
	          attrs: {
	            "type": "info"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.eventRefundOrderComplete(scope.row)
	            }
	          }
	        }, [_vm._v("退款确认")])], 1) : _vm._e(), _vm._v(" "), (scope.row.orderStatus == 3) ? _c('div', {
	          attrs: {
	            "data-title": "等待卖家发货"
	          }
	        }, [_c('el-button', {
	          attrs: {
	            "type": "info"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.eventDelivery(scope.row)
	            }
	          }
	        }, [_vm._v("发货")])], 1) : _vm._e(), _vm._v(" "), (scope.row.orderStatus == 4) ? _c('div', {
	          attrs: {
	            "data-title": "卖家已发货"
	          }
	        }, [_c('el-button', {
	          attrs: {
	            "type": "info"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.eventGetLogistics(scope.row)
	            }
	          }
	        }, [_vm._v("发货信息")]), _vm._v(" "), _c('el-button', {
	          attrs: {
	            "type": "info"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.eventReceived(scope.row)
	            }
	          }
	        }, [_vm._v("确认到货")])], 1) : _vm._e(), _vm._v(" "), (scope.row.orderStatus == 5 || scope.row.orderStatus == 6) ? _c('div', {
	          attrs: {
	            "data-title": "成功的订单"
	          }
	        }, [_c('el-button', {
	          attrs: {
	            "type": "info"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.eventCheckProofs(scope.row)
	            }
	          }
	        }, [_vm._v("支付凭证")]), _vm._v(" "), _c('el-button', {
	          attrs: {
	            "type": "info"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.eventGetLogistics(scope.row)
	            }
	          }
	        }, [_vm._v("发货信息")])], 1) : _vm._e(), _vm._v(" "), (scope.row.orderStatus == 11 || scope.row.orderStatus == 12) ? _c('div', {
	          attrs: {
	            "data-title": "关闭的订单"
	          }
	        }, [_c('el-button', {
	          attrs: {
	            "type": "info"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.eventCheckRefund(scope.row)
	            }
	          }
	        }, [_vm._v("退款信息")])], 1) : _vm._e()]
	      }
	    }])
	  })], 1)], 1), _vm._v(" "), _c('div', {
	    staticClass: "block-white"
	  }, [_c('el-pagination', {
	    attrs: {
	      "current-page": _vm.page,
	      "page-size": _vm.pageSize,
	      "layout": "total, prev, pager, next, jumper",
	      "total": _vm.OrderList.total
	    },
	    on: {
	      "current-change": _vm.handleCurrentChange
	    }
	  })], 1), _vm._v(" "), _c('el-dialog', {
	    attrs: {
	      "title": "订单详情"
	    },
	    model: {
	      value: (_vm.dialogOrderDetailVisible),
	      callback: function($$v) {
	        _vm.dialogOrderDetailVisible = $$v
	      },
	      expression: "dialogOrderDetailVisible"
	    }
	  }, [_c('div', {
	    staticStyle: {
	      "height": "570px",
	      "overflow": "hidden",
	      "overflow-y": "auto",
	      "padding-right": "20px",
	      "line-height": "30px"
	    }
	  }, [_c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 12
	    }
	  }, [_vm._v("订单编号：" + _vm._s(_vm.dialogOrderDetailData.orderNo))]), _vm._v(" "), _c('el-col', {
	    staticStyle: {
	      "text-align": "right"
	    },
	    attrs: {
	      "span": 12
	    }
	  }, [_vm._v("订单状态：" + _vm._s(_vm.dialogOrderDetailData.orderStatusDescription))])], 1), _vm._v(" "), _c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 8
	    }
	  }, [_vm._v("买家：" + _vm._s(_vm.dialogOrderDetailData.consigneeName))]), _vm._v(" "), _c('el-col', {
	    staticStyle: {
	      "text-align": "center"
	    },
	    attrs: {
	      "span": 8
	    }
	  }, [_vm._v("联系电话：" + _vm._s(_vm.dialogOrderDetailData.consigneeMobile))]), _vm._v(" "), _c('el-col', {
	    staticStyle: {
	      "text-align": "right"
	    },
	    attrs: {
	      "span": 8
	    }
	  }, [_vm._v("邀请码：" + _vm._s(_vm.dialogOrderDetailData.inviteCode))])], 1), _vm._v(" "), _c('el-collapse', {
	    staticStyle: {
	      "margin-top": "20px"
	    },
	    model: {
	      value: (_vm.collapseActiveNames),
	      callback: function($$v) {
	        _vm.collapseActiveNames = $$v
	      },
	      expression: "collapseActiveNames"
	    }
	  }, [_c('el-collapse-item', {
	    attrs: {
	      "title": "订单明细",
	      "name": "1"
	    }
	  }, [_vm._l((_vm.dialogOrderDetailData.productList), function(item) {
	    return _c('div', {
	      staticStyle: {
	        "padding-bottom": "20px",
	        "overflow": "hidden"
	      }
	    }, [_c('div', [_vm._v(_vm._s(item.productName))]), _vm._v(" "), _c('div', {
	      staticStyle: {
	        "color": "#999"
	      }
	    }, [_vm._v(_vm._s(item.productId))]), _vm._v(" "), _c('div', [_c('span', [_vm._v("品牌：" + _vm._s(item.brandName))]), _vm._v(" "), _c('span', {
	      staticStyle: {
	        "margin-left": "30px"
	      }
	    }, [_vm._v("类目：" + _vm._s(item.categoryName))])]), _vm._v(" "), _c('el-table', {
	      staticStyle: {
	        "width": "100%"
	      },
	      attrs: {
	        "data": item.orderItemList,
	        "border": "",
	        "stripe": "",
	        "show-summary": "",
	        "sum-text": "商品总价"
	      }
	    }, [_c('el-table-column', {
	      attrs: {
	        "prop": "productStandardMust",
	        "label": "规格"
	      },
	      scopedSlots: _vm._u([{
	        key: "default",
	        fn: function(scope) {
	          return [_c('span', [_vm._v(_vm._s(scope.row.productStandardMust) + " | " + _vm._s(scope.row.productOptionalFirst) + " | " + _vm._s(scope.row.productOptionalSecond))])]
	        }
	      }])
	    }), _vm._v(" "), _c('el-table-column', {
	      attrs: {
	        "prop": "price",
	        "label": "商品价格(元)"
	      },
	      scopedSlots: _vm._u([{
	        key: "default",
	        fn: function(scope) {
	          return [_vm._v("\n\t\t\t\t\t\t\t\t\t\t" + _vm._s(scope.row.price.toFixed(2)) + "/" + _vm._s(scope.row.unit) + "\n\t\t\t\t\t\t\t\t\t")]
	        }
	      }])
	    }), _vm._v(" "), _c('el-table-column', {
	      attrs: {
	        "prop": "quantity",
	        "label": "采购数量"
	      },
	      scopedSlots: _vm._u([{
	        key: "default",
	        fn: function(scope) {
	          return [_vm._v("\n\t\t\t\t\t\t\t\t\t\t" + _vm._s(scope.row.quantity) + "\n\t\t\t\t\t\t\t\t\t")]
	        }
	      }])
	    }), _vm._v(" "), _c('el-table-column', {
	      attrs: {
	        "prop": "amount",
	        "label": "金额(元)"
	      },
	      scopedSlots: _vm._u([{
	        key: "default",
	        fn: function(scope) {
	          return [_vm._v("\n\t\t\t\t\t\t\t\t\t\t" + _vm._s(scope.row.amount.toFixed(2)) + "\n\t\t\t\t\t\t\t\t\t")]
	        }
	      }])
	    })], 1)], 1)
	  }), _vm._v(" "), _c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 4
	    }
	  }, [_vm._v("\n\t\t\t\t\t\t\t运费：\n\t\t\t\t\t\t\t"), (!_vm.dialogOrderDetailData.postage) ? _c('span', {
	    staticStyle: {
	      "color": "#f00"
	    }
	  }, [_vm._v("待核算")]) : _vm._e(), _vm._v(" "), (_vm.dialogOrderDetailData.postage) ? _c('span', [_vm._v(_vm._s(_vm.dialogOrderDetailData.postage))]) : _vm._e()]), _vm._v(" "), _c('el-col', {
	    staticStyle: {
	      "text-align": "center"
	    },
	    attrs: {
	      "span": 20
	    }
	  }, [_vm._v("订单总额：" + _vm._s(_vm.dialogOrderDetailData.transactionAmount))])], 1)], 2), _vm._v(" "), _c('el-collapse-item', {
	    attrs: {
	      "title": "待付款明细",
	      "name": "2"
	    }
	  }), _vm._v(" "), _c('el-collapse-item', {
	    attrs: {
	      "title": "运费核算",
	      "name": "3"
	    }
	  })], 1)], 1), _vm._v(" "), _c('div', {
	    staticClass: "dialog-footer",
	    staticStyle: {
	      "text-align": "center"
	    },
	    slot: "footer"
	  }, [_c('el-button', {
	    on: {
	      "click": function($event) {
	        _vm.dialogOrderDetailVisible = false
	      }
	    }
	  }, [_vm._v("关 闭")])], 1)]), _vm._v(" "), _c('el-dialog', {
	    attrs: {
	      "title": "会员信息"
	    },
	    model: {
	      value: (_vm.dialogBuyerVisible),
	      callback: function($$v) {
	        _vm.dialogBuyerVisible = $$v
	      },
	      expression: "dialogBuyerVisible"
	    }
	  }, [_c('el-row', {
	    staticStyle: {
	      "height": "40px"
	    }
	  }, [_c('el-col', {
	    attrs: {
	      "span": 12
	    }
	  }, [_vm._v("用户编号：" + _vm._s(_vm.dialogBuyerData.client_code))]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 12
	    }
	  }, [_vm._v("邀请码：" + _vm._s(_vm.dialogBuyerData.invite_code))])], 1), _vm._v(" "), _c('el-collapse', {
	    model: {
	      value: (_vm.buyActiveNames),
	      callback: function($$v) {
	        _vm.buyActiveNames = $$v
	      },
	      expression: "buyActiveNames"
	    }
	  }, [_c('el-collapse-item', {
	    attrs: {
	      "title": "单位信息",
	      "name": "1"
	    }
	  }, [_c('div', [_vm._v("单位名称：" + _vm._s(_vm.dialogBuyerData.enterprise_name))]), _vm._v(" "), _c('div', [_vm._v("单位简称：" + _vm._s(_vm.dialogBuyerData.enterprise_short_name))]), _vm._v(" "), _c('div', [_vm._v("地址：" + _vm._s(_vm.dialogBuyerData.enterprise_address))]), _vm._v(" "), _c('div', [_vm._v("主营：" + _vm._s(_vm.dialogBuyerData.major_business))]), _vm._v(" "), _c('div', [_vm._v("联系人：" + _vm._s(_vm.dialogBuyerData.enterprise_linkman))]), _vm._v(" "), _c('div', [_vm._v("联系人电话：" + _vm._s(_vm.dialogBuyerData.enterprise_tel))])]), _vm._v(" "), _c('el-collapse-item', {
	    attrs: {
	      "title": "用户信息",
	      "name": "2"
	    }
	  }, [_c('div', [_vm._v("昵称：" + _vm._s(_vm.dialogBuyerData.nick_name))]), _vm._v(" "), _c('div', [_vm._v("性别：" + _vm._s(_vm.dialogBuyerData.sex))]), _vm._v(" "), _c('div', [_vm._v("用户地区：" + _vm._s(_vm.dialogBuyerData.address))])]), _vm._v(" "), _c('el-collapse-item', {
	    attrs: {
	      "title": "注册信息",
	      "name": "3"
	    }
	  }, [_c('div', [_vm._v("注册时间：" + _vm._s(_vm.dialogBuyerData.init_date))]), _vm._v(" "), _c('div', {
	    staticStyle: {
	      "color": "#f00"
	    }
	  }, [_vm._v("账号状态：" + _vm._s(_vm.dialogBuyerData.status))]), _vm._v(" "), _c('div', [_vm._v("注册手机：" + _vm._s(_vm.dialogBuyerData.mobile_tel))])])], 1)], 1), _vm._v(" "), _c('el-dialog', {
	    attrs: {
	      "title": "收货地址"
	    },
	    model: {
	      value: (_vm.dialogConsigneeVisible),
	      callback: function($$v) {
	        _vm.dialogConsigneeVisible = $$v
	      },
	      expression: "dialogConsigneeVisible"
	    }
	  }, [_c('el-form', {
	    staticClass: "demo-form-inline",
	    attrs: {
	      "model": _vm.dialogConsigneeData,
	      "label-width": "100px"
	    }
	  }, [_c('el-form-item', {
	    attrs: {
	      "label": "收货人："
	    }
	  }, [_vm._v(_vm._s(_vm.dialogConsigneeData.consigneeName))]), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "收货地址："
	    }
	  }, [_vm._v(_vm._s(_vm.dialogConsigneeData.consigneeAddress))]), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "联系电话："
	    }
	  }, [_vm._v(_vm._s(_vm.dialogConsigneeData.consigneeMobile))])], 1)], 1), _vm._v(" "), _c('el-dialog', {
	    attrs: {
	      "title": "买家留言"
	    },
	    model: {
	      value: (_vm.dialogCommentVisible),
	      callback: function($$v) {
	        _vm.dialogCommentVisible = $$v
	      },
	      expression: "dialogCommentVisible"
	    }
	  }, [_c('el-form', {
	    staticClass: "demo-form-inline",
	    attrs: {
	      "model": _vm.dialogCommentData,
	      "label-width": "100px"
	    }
	  }, [_c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 12
	    }
	  }, [_c('el-form-item', {
	    attrs: {
	      "label": "订单编号："
	    }
	  }, [_vm._v(_vm._s(_vm.dialogCommentData.orderNo))])], 1), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 12
	    }
	  }, [_c('el-form-item', {
	    attrs: {
	      "label": "邀请码："
	    }
	  }, [_vm._v(_vm._s(_vm.dialogCommentData.inviteCode))])], 1)], 1), _vm._v(" "), _c('div', {
	    staticStyle: {
	      "padding": "20px"
	    }
	  }, [_vm._v("\n\t\t\t\t\t" + _vm._s(_vm.dialogCommentData.buyerComments) + "\n\t\t\t\t")])], 1)], 1), _vm._v(" "), _c('el-dialog', {
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
	  }, [_vm._v("商品名称")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 20
	    }
	  }, [_vm._v(_vm._s(_vm.dialogGoodsDetailData.goods_name))])], 1), _vm._v(" "), _c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 1
	    }
	  }, [_vm._v(" ")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 3
	    }
	  }, [_vm._v("类目")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 20
	    }
	  }, [_vm._v(_vm._s(_vm.dialogGoodsDetailData.third_category_name))])], 1), _vm._v(" "), _c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 1
	    }
	  }, [_vm._v(" ")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 3
	    }
	  }, [_vm._v("品牌")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 20
	    }
	  }, [_vm._v(_vm._s(_vm.dialogGoodsDetailData.brand_name))])], 1), _vm._v(" "), _c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 1
	    }
	  }, [_vm._v(" ")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 3
	    }
	  }, [_vm._v("计量单位")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 20
	    }
	  }, [_vm._v(_vm._s(_vm.dialogGoodsDetailData.unit_name))])], 1), _vm._v(" "), _c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 1
	    }
	  }, [_vm._v(" ")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 3
	    }
	  }, [_vm._v("占位图")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 20
	    }
	  }, [(_vm.dialogGoodsDetailData.ad_url) ? _c('img', {
	    staticStyle: {
	      "display": "inline-block",
	      "width": "80px",
	      "height": "30px"
	    },
	    attrs: {
	      "src": _vm.ossUrl + _vm.dialogGoodsDetailData.ad_url
	    }
	  }) : _vm._e(), _vm._v(" "), (!_vm.dialogGoodsDetailData.ad_url) ? _c('span', [_vm._v("无")]) : _vm._e()])], 1), _vm._v(" "), _c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 1
	    }
	  }, [_vm._v(" ")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 3
	    }
	  }, [_vm._v("适用机型")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 20
	    }
	  }, [_vm._l((_vm.dialogGoodsDetailData.carModelList), function(item) {
	    return _c('span', {
	      staticClass: "carModelList"
	    }, [_vm._v("\n\t\t\t\t\t\t\t" + _vm._s(item.car_brand_name) + " | " + _vm._s(item.car_type) + " | " + _vm._s(item.car_models_name) + "\n\t\t\t\t\t\t")])
	  }), _vm._v(" "), (_vm.dialogGoodsDetailData.adapt_all_models == 1) ? _c('div', [_vm._v("适用全部机型")]) : _vm._e()], 2)], 1), _vm._v(" "), _c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 1
	    }
	  }, [_vm._v(" ")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 3
	    }
	  }, [_vm._v("banner图")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 20
	    }
	  }, [_c('ul', {
	    staticClass: "banner-list"
	  }, _vm._l((_vm.dialogGoodsDetailData.bannerList), function(item) {
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
	  }, [_vm._v("商品详图")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 20
	    }
	  }, [_c('ul', {
	    staticClass: "detail-list"
	  }, _vm._l((_vm.dialogGoodsDetailData.detailList), function(item) {
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
	  }, [_vm._v("客服电话")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 10
	    }
	  }, [_vm._v(_vm._s(_vm.dialogGoodsDetailData.tel))]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 10
	    }
	  }, [_vm._v("预付定金：" + _vm._s(_vm.dialogGoodsDetailData.font_money_rate) + "%")])], 1), _vm._v(" "), _c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 1
	    }
	  }, [_vm._v(" ")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 3
	    }
	  }, [_vm._v("商品介绍")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 20
	    }
	  }, [_c('ul', {
	    staticClass: "param-list"
	  }, _vm._l((_vm.dialogGoodsDetailData.infoList), function(item) {
	    return _c('li', [_c('span', [_vm._v("名称"), _c('span', [_vm._v(_vm._s(item.info_title))])]), _vm._v(" "), _c('span', [_vm._v("值"), _c('span', [_vm._v(_vm._s(item.info_content))])])])
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
	  }, [_vm._v("关 闭")])], 1)]), _vm._v(" "), _c('el-dialog', {
	    attrs: {
	      "title": _vm.isProofsModify ? '编辑凭证' : '支付凭证'
	    },
	    model: {
	      value: (_vm.dialogProofsVisible),
	      callback: function($$v) {
	        _vm.dialogProofsVisible = $$v
	      },
	      expression: "dialogProofsVisible"
	    }
	  }, _vm._l((_vm.dialogProofsData), function(proofData) {
	    return _c('div', [_c('el-form', {
	      ref: proofData,
	      refInFor: true,
	      staticClass: "demo-form-inline",
	      attrs: {
	        "model": proofData,
	        "label-width": "120px"
	      }
	    }, [_c('el-form-item', {
	      attrs: {
	        "label": "付  款  人："
	      }
	    }, [_c('el-input', {
	      staticStyle: {
	        "width": "100%"
	      },
	      attrs: {
	        "type": "text",
	        "readonly": !_vm.isProofsModify,
	        "placeholder": "付款人"
	      },
	      model: {
	        value: (proofData.paymentUserName),
	        callback: function($$v) {
	          proofData.paymentUserName = $$v
	        },
	        expression: "proofData.paymentUserName"
	      }
	    })], 1), _vm._v(" "), _c('el-form-item', {
	      attrs: {
	        "label": "付款银行："
	      }
	    }, [_c('el-input', {
	      staticStyle: {
	        "width": "100%"
	      },
	      attrs: {
	        "type": "text",
	        "readonly": !_vm.isProofsModify,
	        "placeholder": "付款银行"
	      },
	      model: {
	        value: (proofData.bankAccount),
	        callback: function($$v) {
	          proofData.bankAccount = $$v
	        },
	        expression: "proofData.bankAccount"
	      }
	    })], 1), _vm._v(" "), _c('el-form-item', {
	      attrs: {
	        "label": "付款账号："
	      }
	    }, [_c('el-input', {
	      staticStyle: {
	        "width": "100%"
	      },
	      attrs: {
	        "type": "text",
	        "readonly": !_vm.isProofsModify,
	        "placeholder": "付款账号"
	      },
	      model: {
	        value: (proofData.paymentAccount),
	        callback: function($$v) {
	          proofData.paymentAccount = $$v
	        },
	        expression: "proofData.paymentAccount"
	      }
	    })], 1), _vm._v(" "), _c('el-form-item', {
	      attrs: {
	        "label": "付款金额："
	      }
	    }, [_c('el-input', {
	      staticStyle: {
	        "width": "100%"
	      },
	      attrs: {
	        "type": "number",
	        "readonly": !_vm.isProofsModify,
	        "placeholder": "付款金额"
	      },
	      model: {
	        value: (proofData.amount),
	        callback: function($$v) {
	          proofData.amount = $$v
	        },
	        expression: "proofData.amount"
	      }
	    })], 1), _vm._v(" "), _c('el-form-item', [(proofData.paymentProofSnapshot) ? _c('div', [_c('img', {
	      staticStyle: {
	        "max-width": "100%"
	      },
	      attrs: {
	        "src": 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/' + proofData.paymentProofSnapshot
	      }
	    })]) : _vm._e(), _vm._v(" "), (!proofData.paymentProofSnapshot) ? _c('div', [_vm._v("未上传凭证")]) : _vm._e()]), _vm._v(" "), _c('el-form-item', {
	      attrs: {
	        "label": "上传时间："
	      }
	    }, [_vm._v("\n\t\t\t\t\t\t" + _vm._s(proofData.createDate) + "\n\t\t\t\t\t")]), _vm._v(" "), _c('el-form-item', [(_vm.isProofsModify) ? _c('el-button', {
	      attrs: {
	        "type": "info"
	      }
	    }, [_vm._v("修改")]) : _vm._e()], 1)], 1)], 1)
	  })), _vm._v(" "), _c('el-dialog', {
	    staticClass: "dialogProofsModify",
	    attrs: {
	      "title": _vm.isProofsModify ? '编辑凭证' : '支付凭证'
	    },
	    model: {
	      value: (_vm.dialogProofsModifyVisible),
	      callback: function($$v) {
	        _vm.dialogProofsModifyVisible = $$v
	      },
	      expression: "dialogProofsModifyVisible"
	    }
	  }, [(_vm.dialogProofsData[0]) ? _c('el-form', {
	    ref: _vm.dialogProofsData[0],
	    staticClass: "demo-form-inline",
	    attrs: {
	      "model": _vm.dialogProofsData[0],
	      "label-width": "120px"
	    }
	  }, [_c('el-form-item', {
	    attrs: {
	      "label": "付  款  人："
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "100%"
	    },
	    attrs: {
	      "type": "text",
	      "minlength": 1,
	      "maxlength": 10,
	      "readonly": !_vm.isProofsModify,
	      "placeholder": "付款人"
	    },
	    model: {
	      value: (_vm.dialogProofsData[0].paymentUserName),
	      callback: function($$v) {
	        _vm.dialogProofsData[0].paymentUserName = $$v
	      },
	      expression: "dialogProofsData[0].paymentUserName"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "付款银行："
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "100%"
	    },
	    attrs: {
	      "type": "text",
	      "maxlength": 30,
	      "readonly": !_vm.isProofsModify,
	      "placeholder": "付款银行"
	    },
	    model: {
	      value: (_vm.dialogProofsData[0].bankAccount),
	      callback: function($$v) {
	        _vm.dialogProofsData[0].bankAccount = $$v
	      },
	      expression: "dialogProofsData[0].bankAccount"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "付款账号："
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "100%"
	    },
	    attrs: {
	      "type": "text",
	      "minlength": 1,
	      "maxlength": 20,
	      "readonly": !_vm.isProofsModify,
	      "placeholder": "付款账号"
	    },
	    model: {
	      value: (_vm.dialogProofsData[0].paymentAccount),
	      callback: function($$v) {
	        _vm.dialogProofsData[0].paymentAccount = $$v
	      },
	      expression: "dialogProofsData[0].paymentAccount"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "付款金额："
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "100%"
	    },
	    attrs: {
	      "type": "number",
	      "maxlength": 10,
	      "readonly": !_vm.isProofsModify,
	      "placeholder": "付款金额"
	    },
	    model: {
	      value: (_vm.dialogProofsData[0].amount),
	      callback: function($$v) {
	        _vm.dialogProofsData[0].amount = $$v
	      },
	      expression: "dialogProofsData[0].amount"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "凭证图片："
	    }
	  }, [_c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 11
	    }
	  }, [_c('el-input', {
	    attrs: {
	      "type": "hidden"
	    },
	    model: {
	      value: (_vm.dialogProofsData[0].paymentProofSnapshot),
	      callback: function($$v) {
	        _vm.dialogProofsData[0].paymentProofSnapshot = $$v
	      },
	      expression: "dialogProofsData[0].paymentProofSnapshot"
	    }
	  }), _vm._v(" "), _c('el-upload', {
	    staticClass: "upload-demo",
	    attrs: {
	      "action": "https://jsonplaceholder.typicode.com/posts/",
	      "auto-upload": false,
	      "on-change": _vm.handleFileChange,
	      "on-remove": _vm.handleFileRemove,
	      "file-list": _vm.proofs0List
	    }
	  }, [_c('el-button', {
	    attrs: {
	      "size": "small",
	      "type": "primary"
	    }
	  }, [_vm._v(_vm._s(!_vm.proofs0List.length ? "上传凭证" : "修改"))])], 1)], 1), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 1
	    }
	  }), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 12
	    }
	  }, [_c('img', {
	    staticStyle: {
	      "max-width": "100%"
	    },
	    attrs: {
	      "src": _vm.dialogProofsData[0].priviewSnapshot
	    }
	  })])], 1)], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "上传时间："
	    }
	  }, [_c('div', {
	    staticStyle: {
	      "line-height": "36px"
	    }
	  }, [_vm._v(_vm._s(_vm.dialogProofsData[0].createDate))])])], 1) : _vm._e(), _vm._v(" "), (_vm.dialogProofsData[1]) ? _c('el-form', {
	    ref: _vm.dialogProofsData[1],
	    staticClass: "demo-form-inline",
	    attrs: {
	      "model": _vm.dialogProofsData[1],
	      "label-width": "120px"
	    }
	  }, [_c('el-form-item', {
	    attrs: {
	      "label": "付  款  人："
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "100%"
	    },
	    attrs: {
	      "type": "text",
	      "readonly": !_vm.isProofsModify,
	      "placeholder": "付款人"
	    },
	    model: {
	      value: (_vm.dialogProofsData[1].paymentUserName),
	      callback: function($$v) {
	        _vm.dialogProofsData[1].paymentUserName = $$v
	      },
	      expression: "dialogProofsData[1].paymentUserName"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "付款银行："
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "100%"
	    },
	    attrs: {
	      "type": "text",
	      "readonly": !_vm.isProofsModify,
	      "placeholder": "付款银行"
	    },
	    model: {
	      value: (_vm.dialogProofsData[1].bankAccount),
	      callback: function($$v) {
	        _vm.dialogProofsData[1].bankAccount = $$v
	      },
	      expression: "dialogProofsData[1].bankAccount"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "付款账号："
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "100%"
	    },
	    attrs: {
	      "type": "text",
	      "readonly": !_vm.isProofsModify,
	      "placeholder": "付款账号"
	    },
	    model: {
	      value: (_vm.dialogProofsData[1].paymentAccount),
	      callback: function($$v) {
	        _vm.dialogProofsData[1].paymentAccount = $$v
	      },
	      expression: "dialogProofsData[1].paymentAccount"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "付款金额："
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "100%"
	    },
	    attrs: {
	      "type": "number",
	      "readonly": !_vm.isProofsModify,
	      "placeholder": "付款金额"
	    },
	    model: {
	      value: (_vm.dialogProofsData[1].amount),
	      callback: function($$v) {
	        _vm.dialogProofsData[1].amount = $$v
	      },
	      expression: "dialogProofsData[1].amount"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "凭证图片："
	    }
	  }, [_c('el-row', [_c('el-col', {
	    staticStyle: {
	      "line-height": "0"
	    },
	    attrs: {
	      "span": 11
	    }
	  }, [_c('el-input', {
	    attrs: {
	      "type": "hidden"
	    },
	    model: {
	      value: (_vm.dialogProofsData[1].paymentProofSnapshot),
	      callback: function($$v) {
	        _vm.dialogProofsData[1].paymentProofSnapshot = $$v
	      },
	      expression: "dialogProofsData[1].paymentProofSnapshot"
	    }
	  }), _vm._v(" "), _c('el-upload', {
	    staticClass: "upload-demo",
	    attrs: {
	      "action": "https://jsonplaceholder.typicode.com/posts/",
	      "auto-upload": false,
	      "on-change": _vm.handleFileChange1,
	      "on-remove": _vm.handleFileRemove1,
	      "file-list": _vm.proofs1List
	    }
	  }, [_c('el-button', {
	    attrs: {
	      "size": "small",
	      "type": "primary"
	    }
	  }, [_vm._v(_vm._s(!_vm.proofs1List.length ? "上传凭证" : "修改"))])], 1)], 1), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 1
	    }
	  }), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 12
	    }
	  }, [_c('img', {
	    staticStyle: {
	      "max-width": "100%"
	    },
	    attrs: {
	      "src": _vm.dialogProofsData[1].priviewSnapshot
	    }
	  })])], 1)], 1), _vm._v(" "), _c('el-form-item', {
	    staticClass: "proofsUploadDate",
	    attrs: {
	      "label": "上传时间："
	    }
	  }, [_c('div', {
	    staticStyle: {
	      "line-height": "36px"
	    }
	  }, [_vm._v(_vm._s(_vm.dialogProofsData[1].createDate))])])], 1) : _vm._e(), _vm._v(" "), _c('div', {
	    staticStyle: {
	      "text-align": "center"
	    }
	  }, [_c('el-button', {
	    attrs: {
	      "type": "primary"
	    },
	    on: {
	      "click": function($event) {
	        _vm.handleModifyProofs(_vm.dialogProofsData)
	      }
	    }
	  }, [_vm._v("保存")])], 1)], 1), _vm._v(" "), _c('el-dialog', {
	    attrs: {
	      "title": this.activeName == 'status0' ? '运费核算' : '修改运费'
	    },
	    model: {
	      value: (_vm.postageVisible),
	      callback: function($$v) {
	        _vm.postageVisible = $$v
	      },
	      expression: "postageVisible"
	    }
	  }, [_c('el-form', {
	    ref: _vm.dialogpostageData,
	    staticClass: "demo-form-inline",
	    attrs: {
	      "model": _vm.dialogpostageData,
	      "label-width": "120px"
	    }
	  }, [_c('el-form-item', {
	    attrs: {
	      "label": "订单编号"
	    }
	  }, [_vm._v("\n\t\t\t\t\t" + _vm._s(_vm.dialogpostageData.orderNo) + "\n\t\t\t\t")]), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "运费(元)",
	      "prop": "postage",
	      "rules": {
	        required: true,
	        message: '请设置运费'
	      }
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "type": "number",
	      "placeholder": "运费"
	    },
	    model: {
	      value: (_vm.dialogpostageData.postage),
	      callback: function($$v) {
	        _vm.dialogpostageData.postage = $$v
	      },
	      expression: "dialogpostageData.postage"
	    }
	  })], 1), _vm._v(" "), (_vm.dialogpostageData.transactionType == 1) ? _c('el-form-item', {
	    attrs: {
	      "label": "余款延期(天)",
	      "prop": "balanceDateCount",
	      "rules": {
	        required: true,
	        message: '请设填写余款延期天数'
	      }
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "type": "number",
	      "placeholder": "余款延期天数"
	    },
	    model: {
	      value: (_vm.dialogpostageData.balanceDateCount),
	      callback: function($$v) {
	        _vm.dialogpostageData.balanceDateCount = $$v
	      },
	      expression: "dialogpostageData.balanceDateCount"
	    }
	  })], 1) : _vm._e(), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "预计发货日期",
	      "prop": "estimateDeliveryDesc",
	      "rules": [{
	        required: true,
	        message: '请填写预计发货日期'
	      }, {
	        min: 1,
	        max: 20,
	        message: '发货日期1-20字符'
	      }]
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "300px"
	    },
	    attrs: {
	      "maxlength": 20,
	      "placeholder": "填写预计发货日期"
	    },
	    model: {
	      value: (_vm.dialogpostageData.estimateDeliveryDesc),
	      callback: function($$v) {
	        _vm.dialogpostageData.estimateDeliveryDesc = $$v
	      },
	      expression: "dialogpostageData.estimateDeliveryDesc"
	    }
	  })], 1)], 1), _vm._v(" "), _c('div', {
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
	        _vm.postageAndDeliveryDate(_vm.dialogpostageData)
	      }
	    }
	  }, [_vm._v("保存")]), _vm._v(" "), _c('el-button', {
	    on: {
	      "click": function($event) {
	        _vm.postageVisible = false
	      }
	    }
	  }, [_vm._v("关 闭")])], 1)], 1), _vm._v(" "), _c('el-dialog', {
	    attrs: {
	      "title": "你确定退款吗？"
	    },
	    model: {
	      value: (_vm.dialogRefundOrderVisible),
	      callback: function($$v) {
	        _vm.dialogRefundOrderVisible = $$v
	      },
	      expression: "dialogRefundOrderVisible"
	    }
	  }, [_c('el-form', {
	    ref: _vm.dialogRefundOrderData,
	    staticClass: "demo-form-inline",
	    attrs: {
	      "model": _vm.dialogRefundOrderData,
	      "label-width": "120px"
	    }
	  }, [_c('div', {
	    staticStyle: {
	      "color": "#f00",
	      "text-align": "center",
	      "padding-bottom": "20px"
	    }
	  }, [_vm._v("请与买家做好确认，并输入实际退款金额")]), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "退款金额"
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "type": "number",
	      "placeholder": "输入退款金额"
	    },
	    model: {
	      value: (_vm.dialogRefundOrderData.refundingAmount),
	      callback: function($$v) {
	        _vm.dialogRefundOrderData.refundingAmount = $$v
	      },
	      expression: "dialogRefundOrderData.refundingAmount"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "退款说明"
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "100%"
	    },
	    attrs: {
	      "type": "text",
	      "minlength": 1,
	      "maxlength": 20,
	      "placeholder": "输入退款说明"
	    },
	    model: {
	      value: (_vm.dialogRefundOrderData.refundingExplain),
	      callback: function($$v) {
	        _vm.dialogRefundOrderData.refundingExplain = $$v
	      },
	      expression: "dialogRefundOrderData.refundingExplain"
	    }
	  })], 1)], 1), _vm._v(" "), _c('div', {
	    staticClass: "dialog-footer",
	    staticStyle: {
	      "text-align": "center"
	    },
	    slot: "footer"
	  }, [_c('el-button', {
	    attrs: {
	      "type": "primary",
	      "disabled": _vm.dialogRefundOrderData.refundingAmount && _vm.dialogRefundOrderData.refundingExplain
	    },
	    on: {
	      "click": function($event) {
	        _vm.handleRefundOrderComplete(_vm.dialogpostageData)
	      }
	    }
	  }, [_vm._v("确认退款")]), _vm._v(" "), _c('el-button', {
	    on: {
	      "click": function($event) {
	        _vm.dialogRefundOrderVisible = false
	      }
	    }
	  }, [_vm._v("关 闭")])], 1)], 1), _vm._v(" "), _c('el-dialog', {
	    staticClass: "dialogDelivery",
	    attrs: {
	      "title": "发货信息"
	    },
	    model: {
	      value: (_vm.deliveryVisible),
	      callback: function($$v) {
	        _vm.deliveryVisible = $$v
	      },
	      expression: "deliveryVisible"
	    }
	  }, [_c('el-form', {
	    ref: _vm.dialogDeliveryData,
	    staticClass: "demo-form-inline",
	    attrs: {
	      "model": _vm.dialogDeliveryData,
	      "label-width": "120px"
	    }
	  }, [_c('el-form-item', {
	    attrs: {
	      "label": "订单编号"
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "200px"
	    },
	    attrs: {
	      "type": "text",
	      "disabled": "",
	      "placeholder": ""
	    },
	    model: {
	      value: (_vm.dialogDeliveryData.orderNo),
	      callback: function($$v) {
	        _vm.dialogDeliveryData.orderNo = $$v
	      },
	      expression: "dialogDeliveryData.orderNo"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "联系人"
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "200px"
	    },
	    attrs: {
	      "type": "text",
	      "placeholder": ""
	    },
	    model: {
	      value: (_vm.dialogDeliveryData.senderName),
	      callback: function($$v) {
	        _vm.dialogDeliveryData.senderName = $$v
	      },
	      expression: "dialogDeliveryData.senderName"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "联系电话"
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "200px"
	    },
	    attrs: {
	      "type": "text",
	      "placeholder": ""
	    },
	    model: {
	      value: (_vm.dialogDeliveryData.senderMobile),
	      callback: function($$v) {
	        _vm.dialogDeliveryData.senderMobile = $$v
	      },
	      expression: "dialogDeliveryData.senderMobile"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "物流名称",
	      "prop": "logisticsCompany",
	      "rules": [{
	        required: true,
	        message: '请填写物流名称'
	      }, {
	        min: 2,
	        max: 10,
	        message: '请填写物流名称'
	      }]
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "200px"
	    },
	    attrs: {
	      "type": "text",
	      "minlength": 2,
	      "maxlength": 10,
	      "placeholder": ""
	    },
	    model: {
	      value: (_vm.dialogDeliveryData.logisticsCompany),
	      callback: function($$v) {
	        _vm.dialogDeliveryData.logisticsCompany = $$v
	      },
	      expression: "dialogDeliveryData.logisticsCompany"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "运单号"
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "200px"
	    },
	    attrs: {
	      "type": "text",
	      "placeholder": ""
	    },
	    model: {
	      value: (_vm.dialogDeliveryData.logisticsNo),
	      callback: function($$v) {
	        _vm.dialogDeliveryData.logisticsNo = $$v
	      },
	      expression: "dialogDeliveryData.logisticsNo"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "发货时间",
	      "prop": "deliveryDate",
	      "rules": {
	        required: true,
	        message: '请填写发货时间'
	      }
	    }
	  }, [_c('el-date-picker', {
	    attrs: {
	      "id": "deliveryDate",
	      "format": "yyyy-MM-dd HH:mm:ss",
	      "type": "datetime",
	      "placeholder": "请填写发货时间"
	    },
	    model: {
	      value: (_vm.dialogDeliveryData.deliveryDate),
	      callback: function($$v) {
	        _vm.dialogDeliveryData.deliveryDate = $$v
	      },
	      expression: "dialogDeliveryData.deliveryDate"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "运单照片"
	    }
	  }, [_c('el-input', {
	    attrs: {
	      "type": "hidden"
	    },
	    model: {
	      value: (_vm.dialogDeliveryData.logisticsNoteSnapshot),
	      callback: function($$v) {
	        _vm.dialogDeliveryData.logisticsNoteSnapshot = $$v
	      },
	      expression: "dialogDeliveryData.logisticsNoteSnapshot"
	    }
	  }), _vm._v(" "), _c('el-upload', {
	    staticClass: "upload-demo",
	    attrs: {
	      "action": "https://jsonplaceholder.typicode.com/posts/",
	      "auto-upload": false,
	      "on-change": _vm.handleLogisticsNoteSnapshotChange,
	      "on-remove": _vm.handleLogisticsNoteSnapshotRemove,
	      "file-list": _vm.LogisticsNoteSnapshotList
	    }
	  }, [_c('el-button', {
	    attrs: {
	      "size": "small",
	      "type": "primary"
	    }
	  }, [_vm._v("点击上传")])], 1)], 1)], 1), _vm._v(" "), _c('div', {
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
	        _vm.handleDelivery(_vm.dialogDeliveryData)
	      }
	    }
	  }, [_vm._v("保存")]), _vm._v(" "), _c('el-button', {
	    on: {
	      "click": function($event) {
	        _vm.deliveryVisible = false
	      }
	    }
	  }, [_vm._v("关 闭")])], 1)], 1), _vm._v(" "), _c('el-dialog', {
	    attrs: {
	      "title": "发货信息"
	    },
	    model: {
	      value: (_vm.dialogLogisticsVisible),
	      callback: function($$v) {
	        _vm.dialogLogisticsVisible = $$v
	      },
	      expression: "dialogLogisticsVisible"
	    }
	  }, [_c('el-form', {
	    staticClass: "demo-form-inline",
	    attrs: {
	      "model": _vm.dialogLogisticsData,
	      "label-width": "100px"
	    }
	  }, [_c('el-form-item', {
	    attrs: {
	      "label": "操作员："
	    }
	  }, [_vm._v(_vm._s(_vm.dialogLogisticsData.createUserName))]), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "操作时间："
	    }
	  }, [_vm._v(_vm._s(_vm.dialogLogisticsData.createDate))]), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "联系人："
	    }
	  }, [_vm._v(_vm._s(_vm.dialogLogisticsData.senderName))]), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "联系电话："
	    }
	  }, [_vm._v(_vm._s(_vm.dialogLogisticsData.senderMobile))]), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "物流名称："
	    }
	  }, [_vm._v(_vm._s(_vm.dialogLogisticsData.logisticsCompany))]), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "运单号："
	    }
	  }, [_vm._v(_vm._s(_vm.dialogLogisticsData.logisticsNo))]), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "发货时间："
	    }
	  }, [_vm._v(_vm._s(_vm.dialogLogisticsData.deliveryDate))]), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "运单照片："
	    }
	  }, [_c('img', {
	    staticStyle: {
	      "max-width": "100%"
	    },
	    attrs: {
	      "src": 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/' + _vm.dialogLogisticsData.logisticsNoteSnapshot
	    }
	  })])], 1)], 1), _vm._v(" "), _c('el-dialog', {
	    attrs: {
	      "title": "退款信息"
	    },
	    model: {
	      value: (_vm.dialogRefundInfoVisible),
	      callback: function($$v) {
	        _vm.dialogRefundInfoVisible = $$v
	      },
	      expression: "dialogRefundInfoVisible"
	    }
	  }, [_c('el-form', {
	    staticClass: "demo-form-inline",
	    attrs: {
	      "model": _vm.dialogRefundInfoData,
	      "label-width": "100px"
	    }
	  }, [_c('el-form-item', {
	    attrs: {
	      "label": "退款金额："
	    }
	  }, [_vm._v(_vm._s(Number(_vm.dialogRefundInfoData.refundingAmount).toFixed(2)) + "元")]), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "退款说明："
	    }
	  }, [_vm._v(_vm._s(_vm.dialogRefundInfoData.refundingExplain))])], 1)], 1)], 1)
	},staticRenderFns: []}
	module.exports.render._withStripped = true
	if (false) {
	  module.hot.accept()
	  if (module.hot.data) {
	     require("vue-hot-reload-api").rerender("data-v-6c53cd4a", module.exports)
	  }
	}

/***/ })

});
//# sourceMappingURL=56.js.map