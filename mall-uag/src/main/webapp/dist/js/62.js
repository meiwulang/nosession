webpackJsonp([62],{

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

/***/ 87:
/***/ (function(module, exports, __webpack_require__) {

	
	/* styles */
	__webpack_require__(88)
	
	var Component = __webpack_require__(13)(
	  /* script */
	  __webpack_require__(90),
	  /* template */
	  __webpack_require__(91),
	  /* scopeId */
	  null,
	  /* cssModules */
	  null
	)
	Component.options.__file = "/Users/xiangyangli/projects/hjh/jhd/mall-uag/src/main/webapp/app/components/activityGoods.vue"
	if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key !== "__esModule"})) {console.error("named exports are not supported in *.vue files.")}
	if (Component.options.functional) {console.error("[vue-loader] activityGoods.vue: functional components are not supported with templates, they should use render functions.")}
	
	/* hot reload */
	if (false) {(function () {
	  var hotAPI = require("vue-hot-reload-api")
	  hotAPI.install(require("vue"), false)
	  if (!hotAPI.compatible) return
	  module.hot.accept()
	  if (!module.hot.data) {
	    hotAPI.createRecord("data-v-dbc79c40", Component.options)
	  } else {
	    hotAPI.reload("data-v-dbc79c40", Component.options)
	  }
	})()}
	
	module.exports = Component.exports


/***/ }),

/***/ 88:
/***/ (function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(89);
	if(typeof content === 'string') content = [[module.id, content, '']];
	if(content.locals) module.exports = content.locals;
	// add the styles to the DOM
	var update = __webpack_require__(29)("834226e4", content, false);
	// Hot Module Replacement
	if(false) {
	 // When the styles change, update the <style> tags
	 if(!content.locals) {
	   module.hot.accept("!!../../node_modules/css-loader/index.js?sourceMap!../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-dbc79c40!../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./activityGoods.vue", function() {
	     var newContent = require("!!../../node_modules/css-loader/index.js?sourceMap!../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-dbc79c40!../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./activityGoods.vue");
	     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
	     update(newContent);
	   });
	 }
	 // When the module is disposed, remove the <style> tags
	 module.hot.dispose(function() { update(); });
	}

/***/ }),

/***/ 89:
/***/ (function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(28)();
	// imports
	
	
	// module
	exports.push([module.id, "\n.cell a{\n\tcursor: pointer;\n}\n.goodsDetail .el-row{\n\tpadding: 5px 0;\n\toverflow: hidden;\n\tborder: 1px solid #58B7FF;\n\tmargin: 10px 0;\n\tline-height: 24px;\n}\n.goodsDetail .el-row em{\n\tcolor: #FF0000;\n}\n.carModelList{\n\twidth: 33.33%; float: left; line-height: 24px;\n}\nol,ul,li{\n\tlist-style: none; padding: 0; margin: 0;\n}\n.banner-list li{\n\tfloat: left; margin-right: 10px; width: 80px; height: 80px; border-radius: 4px; overflow: hidden;\n}\n.banner-list li img{\n\twidth: 100%; height: 100%; display: block;\n}\n.detail-list li{\n\tfloat: left; margin-right: 10px; width: 80px;overflow: hidden;\n}\n.detail-list li img{\n\twidth: 80px; height: 80px; display: block; border-radius: 4px;\n}\n.detail-list li span{\n\tdisplay: block; line-height: 20px; font-size: 12px;\n}\n.param-list li >span{\n\tdisplay: inline-block; width: 48%;\n}\n.param-list li >span span{\n\tmargin-left: 10px;\n}\n.standard-list li >span{\n\tdisplay: inline-block; width: 28%;\n}\n.standard-list li >span:first-child{\n\twidth: 42%;\n}\n.standard-list li >span span{\n\tmargin-left: 10px;\n}\n", "", {"version":3,"sources":["/./app/components/activityGoods.vue?abb0d048"],"names":[],"mappings":";AAgQA;CACA,gBAAA;CACA;AACA;CACA,eAAA;CACA,iBAAA;CACA,0BAAA;CACA,eAAA;CACA,kBAAA;CACA;AACA;CACA,eAAA;CACA;AACA;CACA,cAAA,CAAA,YAAA,CAAA,kBAAA;CACA;AACA;CACA,iBAAA,CAAA,WAAA,CAAA,UAAA;CACA;AACA;CACA,YAAA,CAAA,mBAAA,CAAA,YAAA,CAAA,aAAA,CAAA,mBAAA,CAAA,iBAAA;CACA;AAEA;CACA,YAAA,CAAA,aAAA,CAAA,eAAA;CACA;AACA;CACA,YAAA,CAAA,mBAAA,CAAA,YAAA,iBAAA;CACA;AACA;CACA,YAAA,CAAA,aAAA,CAAA,eAAA,CAAA,mBAAA;CACA;AACA;CACA,eAAA,CAAA,kBAAA,CAAA,gBAAA;CACA;AACA;CACA,sBAAA,CAAA,WAAA;CACA;AACA;CACA,kBAAA;CACA;AACA;CACA,sBAAA,CAAA,WAAA;CACA;AACA;CACA,WAAA;CACA;AACA;CACA,kBAAA;CACA","file":"activityGoods.vue","sourcesContent":["<template>\n    <div>\n    \t\t<!-- 面包屑 -->\n\t\t<div class=\"hjh-breadcrumb\">\n\t\t\t<el-breadcrumb separator=\"/\">\n\t\t\t\t<el-breadcrumb-item :to=\"{ path: '/' }\">用户中心</el-breadcrumb-item>\n\t\t\t\t<el-breadcrumb-item>内容管理</el-breadcrumb-item>\n\t\t\t\t<el-breadcrumb-item :to=\"{ path: '/activityManagement' }\">专题管理</el-breadcrumb-item>\n\t\t\t\t<el-breadcrumb-item>商品管理</el-breadcrumb-item>\n\t\t\t</el-breadcrumb>\t\n\t\t</div>\n\t\t<!-- 面包屑 end -->\n\t\t<div class=\"block-white\">\n\t\t\t<el-tabs v-model=\"activeName\" @tab-click=\"handleTabClick\">\n\t\t\t\t<el-tab-pane label=\"已选商品\" name=\"first\"></el-tab-pane>\n\t\t\t\t<el-tab-pane label=\"新增商品\" name=\"second\" v-if=\"!action\"></el-tab-pane>\n\t\t\t</el-tabs>\n\t\t</div>\n\t\t<!-- 查询表单 -->\n\t\t<div class=\"block-white\">\n\t\t\t<el-form :inline=\"true\" :model=\"searchForm\" ref=\"searchForm\" class=\"demo-form-inline searchForm\">\n\t\t\t\t<el-form-item label=\"商品编号:\">\n\t\t\t\t\t<el-input v-model=\"searchForm.goods_id\" placeholder=\"商品编号\" style=\"width:150px;\"></el-input>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"商品品牌:\">\n\t\t\t\t\t<el-select v-model=\"searchForm.brand_name\" filterable placeholder=\"商品品牌\" style=\"width: 200px;\">\n\t\t\t\t\t\t<el-option v-for=\"item in brandlist\" :key=\"item.brand_id\" :label=\"item.brand_name\" :value=\"item.brand_name\">\n\t\t\t\t\t\t</el-option>\n\t\t\t\t\t</el-select>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"商品类目:\">\n\t\t\t\t\t<el-select v-model=\"searchForm.third_category_id\" filterable placeholder=\"商品类目\" style=\"width: 200px;\">\n\t\t\t\t\t\t<el-option v-for=\"item in categorylist\" :key=\"item.cid\" :label=\"item.cname\" :value=\"item.cid\">\n\t\t\t\t\t\t</el-option>\n\t\t\t\t\t</el-select>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"商品标题:\">\n\t\t\t\t\t<el-input v-model=\"searchForm.goods_name\" placeholder=\"商品标题\" style=\"width:150px;\"></el-input>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"上架时间:\">\n\t\t\t\t\t<el-date-picker v-model=\"datepickerValue\" type=\"datetimerange\" :picker-options=\"pickerOptions\" @change=\"datepickerchange\" placeholder=\"选择时间范围\" align=\"right\">\n\t\t\t\t\t</el-date-picker>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"规格:\">\n\t\t\t\t\t<el-input placeholder=\"模糊匹配\" style=\"width:150px;\"></el-input>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item>\n\t\t\t\t\t<el-button class=\"searchBtn\" type=\"primary\" @click=\"submitForm('searchForm')\">搜索</el-button>\n\t\t\t\t\t<el-button @click=\"resetForm('searchForm')\">清除</el-button>\n\t\t\t\t</el-form-item>\n\t\t\t</el-form>\n\t\t</div>\n\t\t<!-- 查询表单 end -->\n\t\t<div class=\"block-white\">\n\t\t\t<el-button type=\"danger\" @click=\"deleteGoods()\" :disabled=\"!selectcount\" v-if=\"isnotnew && !action\">删除商品</el-button>\n\t\t\t<el-button type=\"primary\" @click=\"addGoods()\" :disabled=\"!selectcount\" v-if=\"!isnotnew\">新增商品</el-button>\n\t\t</div>\n\t\t<!-- 订单列表 -->\n\t\t<div class=\"block-white\">\n\t\t\t<el-table v-loading.body=\"loading\" :data=\"GoodsList.result_list\" \n\t\t\t\t@selection-change=\"handleSelectionChange\" \n\t\t\t\tref=\"multipleTable\"\n\t\t\t\tborder stripe style=\"width: 100%\" max-height=\"460\">\n\t\t\t\t<el-table-column fixed type=\"selection\" width=\"55\" class=\"hjhcheckbox\" v-if=\"!action\">\n\t\t\t\t\t\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column label=\"序号\" width=\"70\" align=\"center\"><template scope=\"scope\">{{scope.$index+1}}</template></el-table-column>\n\t\t\t\t<el-table-column label=\"商品信息\" align=\"center\">\n\t\t\t\t\t<el-table-column prop=\"show_url\" label=\"商品图片\" width=\"150\">\n\t\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t\t<img :src=\"'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+scope.row.show_url\" />\n\t\t\t\t\t\t</template>\n\t\t\t\t\t</el-table-column>\n\t\t\t\t\t<el-table-column prop=\"goods_name\" label=\"商品名称\" width=\"250\" :show-overflow-tooltip=\"showtooltip\">\n\t\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t\t<a @click=\"checkProduct(scope.$index, GoodsList.result_list)\">{{scope.row.goods_name}}</a>\n\t\t\t\t\t\t</template>\n\t\t\t\t\t</el-table-column>\n\t\t\t\t\t<el-table-column prop=\"goods_id\" label=\"商品编号\" width=\"200\" :show-overflow-tooltip=\"showtooltip\"></el-table-column>\n\t\t\t\t\t<el-table-column prop=\"brand_name\" label=\"品牌\" width=\"150\" :show-overflow-tooltip=\"showtooltip\"></el-table-column>\n\t\t\t\t\t<el-table-column prop=\"third_category_name\" label=\"类目\" width=\"150\" :show-overflow-tooltip=\"showtooltip\"></el-table-column>\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column prop=\"standardList\" label=\"规格/价格\" width=\"300\" :show-overflow-tooltip=\"showtooltip\">\n\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t<div v-for=\"item in scope.row.standardList\">\n\t\t\t\t\t\t\t<div style=\"overflow: hidden;\">\n\t\t\t\t\t\t\t\t<span style=\"float: left;\">{{item.standard_must}}|{{item.optional_first}}|{{item.optional_second}}</span>\n\t\t\t\t\t\t\t\t<span style=\"float: right;\"><span style=\"color: #FD8F00;\">{{item.price}}</span>/{{item.unit_name}}</span>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</template>\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column prop=\"standardList\" label=\"已售\" width=\"100\" align=\"center\" :show-overflow-tooltip=\"showtooltip\">\n\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t<div v-for=\"item in scope.row.standardList\">{{item.sale_num || 0}}</div>\n\t\t\t\t\t</template>\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column prop=\"standardList\" label=\"库存\" width=\"100\" align=\"center\" :show-overflow-tooltip=\"showtooltip\">\n\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t<div v-for=\"item in scope.row.standardList\">{{item.store_num || 0}}</div>\n\t\t\t\t\t</template>\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column prop=\"initTime\" label=\"创建时间\" width=\"180\" align=\"center\" :show-overflow-tooltip=\"showtooltip\">\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column prop=\"create_user_name\" label=\"创建人\" width=\"120\" align=\"center\" :show-overflow-tooltip=\"showtooltip\">\n\t\t\t\t\t\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column prop=\"updateTime\" label=\"上架时间\" width=\"180\" align=\"center\" :show-overflow-tooltip=\"showtooltip\">\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column prop=\"update_user_name\" label=\"最后操作人\" width=\"120\" :show-overflow-tooltip=\"showtooltip\"></el-table-column>\n\t\t\t</el-table>\n\t\t</div>\n\t\t<!-- 订单列表 end -->\n\t\t<!-- 翻页组件 -->\n\t\t<div class=\"block-white\">\n\t\t\t<el-pagination\n\t\t      @current-change=\"handleCurrentChange\"\n\t\t      :current-page=\"page\"\n\t\t      :page-size=\"limit\"\n\t\t      layout=\"total, prev, pager, next, jumper\"\n\t\t      :total=\"GoodsList.total_num\">\n\t\t    </el-pagination>\n\t\t</div>\n\t\t<!-- 翻页组件 end -->\n\t\t<el-dialog title=\"图片展示\" v-model=\"dialogImageVisible\">\n\t\t\t<div style=\"text-align: center;\">\n\t\t\t\t<img style=\"display: inline-block; margin: 0 auto; max-width: 100%;\" :src=\"BigImageUrl\">\n\t\t\t</div>\n\t\t\t<div slot=\"footer\" class=\"dialog-footer\" style=\"text-align: center;\">\n\t\t\t\t<el-button @click=\"dialogImageVisible = false\">关 闭</el-button>\n\t\t\t</div>\n\t\t</el-dialog>\n\t\t<el-dialog title=\"商品信息\" v-model=\"dialogGoodsDetailVisible\">\n\t\t\t<div class=\"goodsDetail\" style=\"height: 450px; overflow: hidden; overflow-y: auto;\">\n\t\t\t\t<el-row>\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\n\t\t\t\t\t<el-col :span=\"3\">\n\t\t\t\t\t\t<em style=\"color: #FF0000;\">*</em>商品名称\n\t\t\t\t\t</el-col>\n\t\t\t\t\t<el-col :span=\"20\">{{goodsDetail.goods_name}}</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<el-row>\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\n\t\t\t\t\t<el-col :span=\"3\">\n\t\t\t\t\t\t<em style=\"color: #FF0000;\">*</em>类目\n\t\t\t\t\t</el-col>\n\t\t\t\t\t<el-col :span=\"20\">{{goodsDetail.third_category_name}}</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<el-row>\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\n\t\t\t\t\t<el-col :span=\"3\">\n\t\t\t\t\t\t<em style=\"color: #FF0000;\">*</em>品牌\n\t\t\t\t\t</el-col>\n\t\t\t\t\t<el-col :span=\"20\">{{goodsDetail.brand_name}}</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<el-row>\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\n\t\t\t\t\t<el-col :span=\"3\">\n\t\t\t\t\t\t<em style=\"color: #FF0000;\">*</em>计量单位\n\t\t\t\t\t</el-col>\n\t\t\t\t\t<el-col :span=\"20\">{{goodsDetail.unit_name}}</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<el-row>\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\n\t\t\t\t\t<el-col :span=\"3\">\n\t\t\t\t\t\t<em style=\"color: #FF0000;\">*</em>占位图\n\t\t\t\t\t</el-col>\n\t\t\t\t\t<el-col :span=\"20\">\n\t\t\t\t\t\t<img v-if=\"goodsDetail.ad_url\" style=\"display: inline-block; width: 80px; height: 30px;\" :src=\"ossUrl+goodsDetail.ad_url\">\n\t\t\t\t\t\t<span v-if=\"!goodsDetail.ad_url\">无</span>\n\t\t\t\t\t</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<el-row>\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\n\t\t\t\t\t<el-col :span=\"3\">\n\t\t\t\t\t\t<em style=\"color: #FF0000;\">*</em>适用机型\n\t\t\t\t\t</el-col>\n\t\t\t\t\t<el-col :span=\"20\">\n\t\t\t\t\t\t<span class=\"carModelList\" v-for=\"item in goodsDetail.carModelList\">\n\t\t\t\t\t\t\t{{item.car_brand_name}} | {{item.car_type}} | {{item.car_models_name}}\n\t\t\t\t\t\t</span>\n\t\t\t\t\t\t<div v-if=\"goodsDetail.adapt_all_models==1\">适用全部机型</div>\n\t\t\t\t\t</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<el-row>\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\n\t\t\t\t\t<el-col :span=\"3\">\n\t\t\t\t\t\t<em style=\"color: #FF0000;\">*</em>banner图\n\t\t\t\t\t</el-col>\n\t\t\t\t\t<el-col :span=\"20\">\n\t\t\t\t\t\t<ul class=\"banner-list\">\n\t\t\t\t\t\t\t<li v-for=\"item in goodsDetail.bannerList\">\n\t\t\t\t\t\t\t\t<img :src=\"ossUrl+item.banner_url\">\n\t\t\t\t\t\t\t</li>\n\t\t\t\t\t\t</ul>\n\t\t\t\t\t</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<el-row>\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\n\t\t\t\t\t<el-col :span=\"3\">\n\t\t\t\t\t\t<em style=\"color: #FF0000;\">*</em>商品详图\n\t\t\t\t\t</el-col>\n\t\t\t\t\t<el-col :span=\"20\">\n\t\t\t\t\t\t<ul class=\"detail-list\">\n\t\t\t\t\t\t\t<li v-for=\"item in goodsDetail.detailList\">\n\t\t\t\t\t\t\t\t<img :src=\"ossUrl+item.detail_url\">\n\t\t\t\t\t\t\t\t<span>{{item.pic_desc}}</span>\n\t\t\t\t\t\t\t</li>\n\t\t\t\t\t\t</ul>\n\t\t\t\t\t</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<el-row>\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\n\t\t\t\t\t<el-col :span=\"3\">\n\t\t\t\t\t\t<em style=\"color: #FF0000;\">*</em>客服电话\n\t\t\t\t\t</el-col>\n\t\t\t\t\t<el-col :span=\"20\">{{goodsDetail.tel}}</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<el-row>\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\n\t\t\t\t\t<el-col :span=\"3\">\n\t\t\t\t\t\t<em style=\"color: #FF0000;\">*</em>商品参数\n\t\t\t\t\t</el-col>\n\t\t\t\t\t<el-col :span=\"20\">\n\t\t\t\t\t\t<ul class=\"param-list\">\n\t\t\t\t\t\t\t<li v-for=\"item in goodsDetail.infoList\">\n\t\t\t\t\t\t\t\t<span><em>*</em>名称<span>{{item.info_title}}</span></span>\n\t\t\t\t\t\t\t\t<span><em>*</em>值<span>{{item.info_content}}</span></span>\n\t\t\t\t\t\t\t</li>\n\t\t\t\t\t\t</ul>\n\t\t\t\t\t</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<el-row>\n\t\t\t\t\t<el-col :span=\"1\">&nbsp;</el-col>\n\t\t\t\t\t<el-col :span=\"3\">\n\t\t\t\t\t\t<em style=\"color: #FF0000;\">*</em>商品规格\n\t\t\t\t\t</el-col>\n\t\t\t\t\t<el-col :span=\"20\">\n\t\t\t\t\t\t<ul class=\"standard-list\">\n\t\t\t\t\t\t\t<li v-for=\"item in goodsDetail.standardList\">\n\t\t\t\t\t\t\t\t<span><em>*</em>名称<span>{{item.standard_must}}</span><span>{{item.optional_first}}</span><span>{{item.optional_second}}</span></span>\n\t\t\t\t\t\t\t\t<span><em>*</em>价格<span>{{item.price}}</span></span>\n\t\t\t\t\t\t\t\t<span><em>*</em>库存<span>{{item.store_num}}</span></span>\n\t\t\t\t\t\t\t</li>\n\t\t\t\t\t\t</ul>\n\t\t\t\t\t</el-col>\n\t\t\t\t</el-row>\n\t\t\t</div>\n\t\t\t<div slot=\"footer\" class=\"dialog-footer\" style=\"text-align: center;\">\n\t\t\t\t<el-button @click=\"dialogGoodsDetailVisible = false\">关 闭</el-button>\n\t\t\t</div>\n\t\t</el-dialog>\n\t\t\n    </div>\n</template>\n<style>\n\t.cell a{\n\t\tcursor: pointer;\n\t}\n\t.goodsDetail .el-row{\n\t\tpadding: 5px 0;\n\t\toverflow: hidden;\n\t\tborder: 1px solid #58B7FF;\n\t\tmargin: 10px 0;\n\t\tline-height: 24px;\n\t}\n\t.goodsDetail .el-row em{\n\t\tcolor: #FF0000;\n\t}\n\t.carModelList{\n\t\twidth: 33.33%; float: left; line-height: 24px;\n\t}\n\tol,ul,li{\n\t\tlist-style: none; padding: 0; margin: 0;\n\t}\n\t.banner-list li{\n\t\tfloat: left; margin-right: 10px; width: 80px; height: 80px; border-radius: 4px; overflow: hidden;\n\t}\n\t\n\t.banner-list li img{\n\t\twidth: 100%; height: 100%; display: block;\n\t}\n\t.detail-list li{\n\t\tfloat: left; margin-right: 10px; width: 80px;overflow: hidden;\n\t}\n\t.detail-list li img{\n\t\twidth: 80px; height: 80px; display: block; border-radius: 4px;\n\t}\n\t.detail-list li span{\n\t\tdisplay: block; line-height: 20px; font-size: 12px;\n\t}\n\t.param-list li >span{\n\t\tdisplay: inline-block; width: 48%;\n\t}\n\t.param-list li >span span{\n\t\tmargin-left: 10px;\n\t}\n\t.standard-list li >span{\n\t\tdisplay: inline-block; width: 28%;\n\t}\n\t.standard-list li >span:first-child{\n\t\twidth: 42%;\n\t}\n\t.standard-list li >span span{\n\t\tmargin-left: 10px;\n\t}\n</style>\n<script>\n\timport Common from './../js/common';\n    export default{\n        data(){\n            return{\n                activeName : \"first\",\t\t\t\t\t\t//tab标签页定位标识\n\t\t\t\tsearchForm: this.getDeaultSearchForm(),\t\t//搜索选项表单信息\n\t\t\t\tactivityId: this.$route.params.id,\t\t\t//活动id\n\t\t\t\tgetGoodsListApi : \"/json/901208\",\t\t\t//获取已选商品列表api\n\t\t\t\tBigImageUrl : \"\",\t\t\t\t\t\t\t//查看大图图片链接\n\t\t\t\taction : \"\",\n\t\t\t\tdatepickerValue: [],\t\t\t\t\t\t\t\t//上架之间 [开始时间，结束时间]\n\t\t\t\tbrandlist: [],\t\t\t\t\t\t\t\t//品牌列表\n\t\t\t\tGoodsList : {},\t\t\t\t\t\t\t\t//商品列表\n\t\t\t\tisnotnew :true,\t\t\t\t\t\t\t\t//true为已选 false为新增\n\t\t\t\tloading : false,\t\t\t\t\t\t\t\t//列表加载状态\n\t\t\t\tshowtooltip :true,\t\t\t\t\t\t\t//表格项文字隐藏显示提示\n\t\t\t\tdialogImageVisible : false,\t\t\t\t\t//查看大图弹窗显示状态\n\t\t\t\tdialogGoodsDetailVisible : false,\n\t\t\t\tgoodsDetail : {},\n\t\t\t\tossUrl : \"http://test-hjh.oss-cn-shanghai.aliyuncs.com/\",\n\t\t\t\tcategorylist: [],\t\t\t\t\t\t\t//类目列表\n\t\t\t\tmultipleSelection: {},\t\t\t\t\t\t//已选列集合\n\t\t\t\tselectcount : 0,\n\t\t\t\tpage : 1,\t\t\t\t\t\t\t\t\t//列表当前页码\n\t\t\t\tlimit : 10,\t\t\t\t\t\t\t\t\t//列表每页条数\n\t\t\t\tpickerOptions: {\n\t\t\t\t\tshortcuts: [{\n\t\t\t\t\t\ttext: '最近一周',\n\t\t\t\t\t\tonClick(picker) {\n\t\t\t\t\t\t\tconst end = new Date();\n\t\t\t\t\t\t\tconst start = new Date();\n\t\t\t\t\t\t\tstart.setTime(start.getTime() - 3600 * 1000 * 24 * 7);\n\t\t\t\t\t\t\tpicker.$emit('pick', [start, end]);\n\t\t\t\t\t\t}\n\t\t\t\t\t}, {\n\t\t\t\t\t\ttext: '最近一个月',\n\t\t\t\t\t\tonClick(picker) {\n\t\t\t\t\t\t\tconst end = new Date();\n\t\t\t\t\t\t\tconst start = new Date();\n\t\t\t\t\t\t\tstart.setTime(start.getTime() - 3600 * 1000 * 24 * 30);\n\t\t\t\t\t\t\tpicker.$emit('pick', [start, end]);\n\t\t\t\t\t\t}\n\t\t\t\t\t}, {\n\t\t\t\t\t\ttext: '最近三个月',\n\t\t\t\t\t\tonClick(picker) {\n\t\t\t\t\t\t\tconst end = new Date();\n\t\t\t\t\t\t\tconst start = new Date();\n\t\t\t\t\t\t\tstart.setTime(start.getTime() - 3600 * 1000 * 24 * 90);\n\t\t\t\t\t\t\tpicker.$emit('pick', [start, end]);\n\t\t\t\t\t\t}\n\t\t\t\t\t}]\n\t\t\t\t}\n\t\t\t}\n\t\t},\n\t\tmounted(){\n\t\t\tdocument.title = \"后台管理系统-活动商品管理\";\n\t\t\t//console.log(this.activityId);\n\t\t\tthis.getListBySearchData();\n\t\t},\n\t\tcreated(){\n\t\t\tthis.action = location.hash.indexOf(\"action=checkGoods\") > -1;\n\t\t\tvar that = this;\n\t\t\t//获取品牌列表\n\t\t\tthat.$http.post(\"/goodsbrand/queryforweb\",{limit:10000,page:1}).then(response =>{\n\t\t\t\tvar data = response.data;\n\t\t\t\tif(data.error_no == 0){\n\t\t\t\t\tthat.$data.brandlist = data.result_list;\n\t\t\t\t}\n\t\t\t});\n\t\t\t//获取类型列表\n\t\t\tthat.$http.post(\"/json/900511\",{limit:10000,page:0}).then(response =>{\n\t\t\t\tvar data = response.data;\n\t\t\t\tif(data.error_no == 0){\n\t\t\t\t\tthat.$data.categorylist = data.result_list;\n\t\t\t\t}\n\t\t\t});\n\t\t},\n\t\tmethods: {\n\t\t\tgetDeaultSearchForm() {\n\t\t\t\treturn {\n\t\t\t\t\tgoods_id:\"\",\n        \t\t\t\tbrand_name : \"\",\n        \t\t\t\tthird_category_id : \"\",\n        \t\t\t\tgoods_name : \"\",\n        \t\t\t\tstart_date :\"\",\n        \t\t\t\tend_date : \"\"\n        \t\t\t}\n        \t\t},\n        \t\thandleTabClick(tab,event){\n        \t\t\tthis.getGoodsListApi = this.activeName==\"first\" ? \"/json/901208\" : \"/json/901214\";\n        \t\t\tthis.isnotnew = this.activeName==\"first\";\n        \t\t\tthis.resetForm();\n        \t\t\tthis.multipleSelection = {};\n        \t\t\tthis.selectcount = 0;\n        \t\t},\n        \t\tdatepickerchange(val){\n        \t\t\tif(!val){\n        \t\t\t\tthis.searchForm.start_date = \"\";\n        \t\t\t\tthis.searchForm.end_date = \"\";\n        \t\t\t}else{\n        \t\t\t\tvar arr = val.split(\" - \");\n        \t\t\t\tthis.searchForm.start_date = arr[0].match(/\\d/g).join(\"\");\n        \t\t\t\tthis.searchForm.end_date =  arr[1].match(/\\d/g).join(\"\");\n        \t\t\t}\n        \t\t},\n        \t\tsubmitForm(){\n        \t\t\tthis.$data.page = 1;\n\t\t\t\tthis.getListBySearchData();\n        \t\t},\n        \t\tresetForm(){\n        \t\t\tthis.searchForm = this.getDeaultSearchForm();\n        \t\t\tthis.datepickerValue = [];\n        \t\t\tthis.submitForm();\n        \t\t},\n        \t\thandleCurrentChange(val){\n        \t\t\tthis.$data.page = val;\n\t\t\t\t\tthis.searchDataCache.page = val;\n\t\t\t\t\tthis.getGoodsList();\n        \t\t},\n        \t\tcheckProduct(index,list){\n        \t\t\tvar item = list[index];\n        \t\t\t//this.BigImageUrl = 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+item.show_url;\n\t\t\t\t//this.dialogImageVisible = true;\n\t\t\t\tthis.goodsDetail = item;\n\t\t\t\tthis.dialogGoodsDetailVisible = true;\n        \t\t},\n        \t\tdeleteGoods(){\n        \t\t\tvar that = this;\n        \t\t\tthis.$confirm('确定要删除该商品吗？', '提示', {\n\t\t\t\t\tconfirmButtonText: '确定',\n\t\t\t\t\tcancelButtonText: '取消',\n\t\t\t\t\ttype: 'warning'\n\t\t\t\t}).then(() => {\n\t\t\t\t\t//var actGoodIds = that.multipleSelection.map(item=>{ return item.goods_id; });\n\t\t\t\t\tvar actGoodIds = [];\n\t\t\t\t\tfor(var i in that.multipleSelection){\n\t\t\t\t\t\tactGoodIds = actGoodIds.concat(that.multipleSelection[i].map(item=>{ return item.goods_id; }));\n\t\t\t\t\t}\n\t\t\t\t\tthis.$http.post(\"/json/901206\",{\n\t\t\t\t\t\taccess_token:localStorage.access_token,\n\t\t\t\t\t\tactGoodIds:actGoodIds,\n\t\t\t\t\t\tactivityId : this.activityId\n\t\t\t\t\t}).then(response=>{\n\t\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\t\tthat.$message({\n\t\t\t\t \t\t\ttype: jsondata.error_no == 0 ? \"success\" : \"error\",\n\t\t\t\t \t\t\tmessage: jsondata.error_no == 0 ? \"操作成功\" : jsondata.error_info\n\t\t\t\t \t\t});\n\t\t\t\t \t\tif(jsondata.error_no == 0){\n\t\t\t\t \t\t\tthat.getGoodsList();\n\t\t\t\t \t\t\tthat.multipleSelection = {};\n\t\t\t\t \t\t\tthat.selectcount = 0;\n\t\t\t\t \t\t}\n\t\t\t\t\t});\n\t\t\t\t}).catch(() => {         \n\t\t\t\t});\n        \t\t},\n        \t\taddGoods(){\n        \t\t\tvar that = this;\n        \t\t\tthis.$confirm('确定要添加该商品吗？', '提示', {\n\t\t\t\t\tconfirmButtonText: '确定',\n\t\t\t\t\tcancelButtonText: '取消',\n\t\t\t\t\ttype: 'warning'\n\t\t\t\t}).then(() => {\n\t\t\t\t\t//var actGoodIds = that.multipleSelection.map(item=>{ return item.goods_id; });\n\t\t\t\t\tvar actGoodIds = [];\n\t\t\t\t\tfor(var i in that.multipleSelection){\n\t\t\t\t\t\tactGoodIds = actGoodIds.concat(that.multipleSelection[i].map(item=>{ return item.goods_id; }));\n\t\t\t\t\t}\n\t\t\t\t\tthis.$http.post(\"/json/901205\",{access_token:localStorage.access_token,goodIds:actGoodIds,activityId:this.activityId}).then(response=>{\n\t\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\t\tthat.$message({\n\t\t\t\t \t\t\ttype: jsondata.error_no == 0 ? \"success\" : \"error\",\n\t\t\t\t \t\t\tmessage: jsondata.error_no == 0 ? \"操作成功\" : jsondata.error_info\n\t\t\t\t \t\t});\n\t\t\t\t \t\tif(jsondata.error_no == 0){\n\t\t\t\t \t\t\tthat.getGoodsList();\n\t\t\t\t \t\t\tthat.multipleSelection = {};\n\t\t\t\t \t\t\tthis.selectcount = 0;\n\t\t\t\t \t\t}\n\t\t\t\t\t});\n\t\t\t\t}).catch(() => {         \n\t\t\t\t});\n        \t\t},\n        \t\t\n        \t\thandleSelectionChange(val) {\n        \t\t\tthis.multipleSelection[this.page] = val;\n        \t\t\tvar count = 0;\n        \t\t\tfor(var i in this.multipleSelection){\n        \t\t\t\tcount += this.multipleSelection[i].length;\n        \t\t\t}\n        \t\t\tthis.selectcount = count;\n\t\t\t\t//this.multipleSelection = val;\n\t\t\t},\n\t\t\tgetListBySearchData(){\n        \t\t\tvar param = {\n        \t\t\t\tactivityId : this.activityId,\n        \t\t\t\tpage : this.page,\n\t\t\t\t\tlimit : this.limit,\n\t\t\t\t\taccess_token : localStorage.access_token,\n\t\t\t\t\tgoods_id:this.searchForm.goods_id,\n        \t\t\t\tbrand_name : this.searchForm.brand_name,\n        \t\t\t\tthird_category_id : this.searchForm.third_category_id,\n        \t\t\t\tgoods_name : this.searchForm.goods_name,\n        \t\t\t\tupdate_start_date :this.searchForm.start_date,\n        \t\t\t\tupdate_end_date : this.searchForm.end_date\n        \t\t\t};\n        \t\t\tthis.searchDataCache = Common.simpleClone(param);\n\t\t\t\tthis.getGoodsList();\n\t\t\t},\n\t\t\ttoggleSelection() {\n\t\t\t\tvar rows = this.multipleSelection[this.page];\n\t\t\t\t//this.$refs.multipleTable.toggleRowSelection(this.GoodsList.result_list[1],true);\n\t\t\t\t//this.$refs.multipleTable.toggleRowSelection(rows[1],true);\n\t\t\t\t//return;\n\t\t        if (rows) {\n\t\t          rows.forEach(row => {\n\t\t            this.$refs.multipleTable.toggleRowSelection(row,true);\n\t\t          });\n\t\t        } else {\n\t\t          this.$refs.multipleTable.clearSelection();\n\t\t        }\n\t\t      },\n        \t\tgetGoodsList(){\n        \t\t\tif(this.loading) return;\n        \t\t\tvar that = this,param = this.searchDataCache;\n        \t\t\tthis.loading = true;\n        \t\t\t\n        \t\t\tif(!this.isnotnew){\n        \t\t\t\tparam.goods_status = 1;\n        \t\t\t}\n        \t\t\tparam = Common.filterParamByEmptyValue(param);\n        \t\t\tthat.$http.post(this.getGoodsListApi,param).then(response => {\n        \t\t\t\tthat.GoodsList = response.data;\n        \t\t\t\tif(that.GoodsList.error_no==0){\n\t        \t\t\t\tthat.GoodsList.result_list = that.GoodsList.result_list.map((item,index)=>{\n\t        \t\t\t\t\titem.initTime = Common.formatDateConcat(item.init_date,item.init_time);\n\t        \t\t\t\t\titem.updateTime = Common.formatDateConcat(item.update_date,item.update_time);\n\t        \t\t\t\t\treturn item;\n\t        \t\t\t\t});\n\t        \t\t\t\tthis.toggleSelection();\n        \t\t\t\t}else{\n        \t\t\t\t\tthat.$message({\n        \t\t\t\t\t\ttype: \"error\",\n\t\t\t\t \t\t\t\tmessage: that.GoodsList.error_info\n        \t\t\t\t\t})\n        \t\t\t\t}\n        \t\t\t\tthat.loading = false;\n        \t\t\t});\n        \t\t}\n        }\n    }\n</script>\n"],"sourceRoot":"webpack://"}]);
	
	// exports


/***/ }),

/***/ 90:
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
				activeName: "first", //tab标签页定位标识
				searchForm: this.getDeaultSearchForm(), //搜索选项表单信息
				activityId: this.$route.params.id, //活动id
				getGoodsListApi: "/json/901208", //获取已选商品列表api
				BigImageUrl: "", //查看大图图片链接
				action: "",
				datepickerValue: [], //上架之间 [开始时间，结束时间]
				brandlist: [], //品牌列表
				GoodsList: {}, //商品列表
				isnotnew: true, //true为已选 false为新增
				loading: false, //列表加载状态
				showtooltip: true, //表格项文字隐藏显示提示
				dialogImageVisible: false, //查看大图弹窗显示状态
				dialogGoodsDetailVisible: false,
				goodsDetail: {},
				ossUrl: "http://test-hjh.oss-cn-shanghai.aliyuncs.com/",
				categorylist: [], //类目列表
				multipleSelection: {}, //已选列集合
				selectcount: 0,
				page: 1, //列表当前页码
				limit: 10, //列表每页条数
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
		mounted: function mounted() {
			document.title = "后台管理系统-活动商品管理";
			//console.log(this.activityId);
			this.getListBySearchData();
		},
		created: function created() {
			this.action = location.hash.indexOf("action=checkGoods") > -1;
			var that = this;
			//获取品牌列表
			that.$http.post("/goodsbrand/queryforweb", { limit: 10000, page: 1 }).then(function (response) {
				var data = response.data;
				if (data.error_no == 0) {
					that.$data.brandlist = data.result_list;
				}
			});
			//获取类型列表
			that.$http.post("/json/900511", { limit: 10000, page: 0 }).then(function (response) {
				var data = response.data;
				if (data.error_no == 0) {
					that.$data.categorylist = data.result_list;
				}
			});
		},
	
		methods: {
			getDeaultSearchForm: function getDeaultSearchForm() {
				return {
					goods_id: "",
					brand_name: "",
					third_category_id: "",
					goods_name: "",
					start_date: "",
					end_date: ""
				};
			},
			handleTabClick: function handleTabClick(tab, event) {
				this.getGoodsListApi = this.activeName == "first" ? "/json/901208" : "/json/901214";
				this.isnotnew = this.activeName == "first";
				this.resetForm();
				this.multipleSelection = {};
				this.selectcount = 0;
			},
			datepickerchange: function datepickerchange(val) {
				if (!val) {
					this.searchForm.start_date = "";
					this.searchForm.end_date = "";
				} else {
					var arr = val.split(" - ");
					this.searchForm.start_date = arr[0].match(/\d/g).join("");
					this.searchForm.end_date = arr[1].match(/\d/g).join("");
				}
			},
			submitForm: function submitForm() {
				this.$data.page = 1;
				this.getListBySearchData();
			},
			resetForm: function resetForm() {
				this.searchForm = this.getDeaultSearchForm();
				this.datepickerValue = [];
				this.submitForm();
			},
			handleCurrentChange: function handleCurrentChange(val) {
				this.$data.page = val;
				this.searchDataCache.page = val;
				this.getGoodsList();
			},
			checkProduct: function checkProduct(index, list) {
				var item = list[index];
				//this.BigImageUrl = 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+item.show_url;
				//this.dialogImageVisible = true;
				this.goodsDetail = item;
				this.dialogGoodsDetailVisible = true;
			},
			deleteGoods: function deleteGoods() {
				var _this = this;
	
				var that = this;
				this.$confirm('确定要删除该商品吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(function () {
					//var actGoodIds = that.multipleSelection.map(item=>{ return item.goods_id; });
					var actGoodIds = [];
					for (var i in that.multipleSelection) {
						actGoodIds = actGoodIds.concat(that.multipleSelection[i].map(function (item) {
							return item.goods_id;
						}));
					}
					_this.$http.post("/json/901206", {
						access_token: localStorage.access_token,
						actGoodIds: actGoodIds,
						activityId: _this.activityId
					}).then(function (response) {
						var jsondata = response.data;
						that.$message({
							type: jsondata.error_no == 0 ? "success" : "error",
							message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
						});
						if (jsondata.error_no == 0) {
							that.getGoodsList();
							that.multipleSelection = {};
							that.selectcount = 0;
						}
					});
				}).catch(function () {});
			},
			addGoods: function addGoods() {
				var _this2 = this;
	
				var that = this;
				this.$confirm('确定要添加该商品吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(function () {
					//var actGoodIds = that.multipleSelection.map(item=>{ return item.goods_id; });
					var actGoodIds = [];
					for (var i in that.multipleSelection) {
						actGoodIds = actGoodIds.concat(that.multipleSelection[i].map(function (item) {
							return item.goods_id;
						}));
					}
					_this2.$http.post("/json/901205", { access_token: localStorage.access_token, goodIds: actGoodIds, activityId: _this2.activityId }).then(function (response) {
						var jsondata = response.data;
						that.$message({
							type: jsondata.error_no == 0 ? "success" : "error",
							message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
						});
						if (jsondata.error_no == 0) {
							that.getGoodsList();
							that.multipleSelection = {};
							_this2.selectcount = 0;
						}
					});
				}).catch(function () {});
			},
			handleSelectionChange: function handleSelectionChange(val) {
				this.multipleSelection[this.page] = val;
				var count = 0;
				for (var i in this.multipleSelection) {
					count += this.multipleSelection[i].length;
				}
				this.selectcount = count;
				//this.multipleSelection = val;
			},
			getListBySearchData: function getListBySearchData() {
				var param = {
					activityId: this.activityId,
					page: this.page,
					limit: this.limit,
					access_token: localStorage.access_token,
					goods_id: this.searchForm.goods_id,
					brand_name: this.searchForm.brand_name,
					third_category_id: this.searchForm.third_category_id,
					goods_name: this.searchForm.goods_name,
					update_start_date: this.searchForm.start_date,
					update_end_date: this.searchForm.end_date
				};
				this.searchDataCache = _common2.default.simpleClone(param);
				this.getGoodsList();
			},
			toggleSelection: function toggleSelection() {
				var _this3 = this;
	
				var rows = this.multipleSelection[this.page];
				//this.$refs.multipleTable.toggleRowSelection(this.GoodsList.result_list[1],true);
				//this.$refs.multipleTable.toggleRowSelection(rows[1],true);
				//return;
				if (rows) {
					rows.forEach(function (row) {
						_this3.$refs.multipleTable.toggleRowSelection(row, true);
					});
				} else {
					this.$refs.multipleTable.clearSelection();
				}
			},
			getGoodsList: function getGoodsList() {
				var _this4 = this;
	
				if (this.loading) return;
				var that = this,
				    param = this.searchDataCache;
				this.loading = true;
	
				if (!this.isnotnew) {
					param.goods_status = 1;
				}
				param = _common2.default.filterParamByEmptyValue(param);
				that.$http.post(this.getGoodsListApi, param).then(function (response) {
					that.GoodsList = response.data;
					if (that.GoodsList.error_no == 0) {
						that.GoodsList.result_list = that.GoodsList.result_list.map(function (item, index) {
							item.initTime = _common2.default.formatDateConcat(item.init_date, item.init_time);
							item.updateTime = _common2.default.formatDateConcat(item.update_date, item.update_time);
							return item;
						});
						_this4.toggleSelection();
					} else {
						that.$message({
							type: "error",
							message: that.GoodsList.error_info
						});
					}
					that.loading = false;
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
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
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

/***/ 91:
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
	  }, [_vm._v("用户中心")]), _vm._v(" "), _c('el-breadcrumb-item', [_vm._v("内容管理")]), _vm._v(" "), _c('el-breadcrumb-item', {
	    attrs: {
	      "to": {
	        path: '/activityManagement'
	      }
	    }
	  }, [_vm._v("专题管理")]), _vm._v(" "), _c('el-breadcrumb-item', [_vm._v("商品管理")])], 1)], 1), _vm._v(" "), _c('div', {
	    staticClass: "block-white"
	  }, [_c('el-tabs', {
	    on: {
	      "tab-click": _vm.handleTabClick
	    },
	    model: {
	      value: (_vm.activeName),
	      callback: function($$v) {
	        _vm.activeName = $$v
	      },
	      expression: "activeName"
	    }
	  }, [_c('el-tab-pane', {
	    attrs: {
	      "label": "已选商品",
	      "name": "first"
	    }
	  }), _vm._v(" "), (!_vm.action) ? _c('el-tab-pane', {
	    attrs: {
	      "label": "新增商品",
	      "name": "second"
	    }
	  }) : _vm._e()], 1)], 1), _vm._v(" "), _c('div', {
	    staticClass: "block-white"
	  }, [_c('el-form', {
	    ref: "searchForm",
	    staticClass: "demo-form-inline searchForm",
	    attrs: {
	      "inline": true,
	      "model": _vm.searchForm
	    }
	  }, [_c('el-form-item', {
	    attrs: {
	      "label": "商品编号:"
	    }
	  }, [_c('el-input', {
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
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "商品品牌:"
	    }
	  }, [_c('el-select', {
	    staticStyle: {
	      "width": "200px"
	    },
	    attrs: {
	      "filterable": "",
	      "placeholder": "商品品牌"
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
	  }))], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "商品类目:"
	    }
	  }, [_c('el-select', {
	    staticStyle: {
	      "width": "200px"
	    },
	    attrs: {
	      "filterable": "",
	      "placeholder": "商品类目"
	    },
	    model: {
	      value: (_vm.searchForm.third_category_id),
	      callback: function($$v) {
	        _vm.searchForm.third_category_id = $$v
	      },
	      expression: "searchForm.third_category_id"
	    }
	  }, _vm._l((_vm.categorylist), function(item) {
	    return _c('el-option', {
	      key: item.cid,
	      attrs: {
	        "label": item.cname,
	        "value": item.cid
	      }
	    })
	  }))], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "商品标题:"
	    }
	  }, [_c('el-input', {
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
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "上架时间:"
	    }
	  }, [_c('el-date-picker', {
	    attrs: {
	      "type": "datetimerange",
	      "picker-options": _vm.pickerOptions,
	      "placeholder": "选择时间范围",
	      "align": "right"
	    },
	    on: {
	      "change": _vm.datepickerchange
	    },
	    model: {
	      value: (_vm.datepickerValue),
	      callback: function($$v) {
	        _vm.datepickerValue = $$v
	      },
	      expression: "datepickerValue"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "规格:"
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "模糊匹配"
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
	  }, [(_vm.isnotnew && !_vm.action) ? _c('el-button', {
	    attrs: {
	      "type": "danger",
	      "disabled": !_vm.selectcount
	    },
	    on: {
	      "click": function($event) {
	        _vm.deleteGoods()
	      }
	    }
	  }, [_vm._v("删除商品")]) : _vm._e(), _vm._v(" "), (!_vm.isnotnew) ? _c('el-button', {
	    attrs: {
	      "type": "primary",
	      "disabled": !_vm.selectcount
	    },
	    on: {
	      "click": function($event) {
	        _vm.addGoods()
	      }
	    }
	  }, [_vm._v("新增商品")]) : _vm._e()], 1), _vm._v(" "), _c('div', {
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
	      "data": _vm.GoodsList.result_list,
	      "border": "",
	      "stripe": "",
	      "max-height": "460"
	    },
	    on: {
	      "selection-change": _vm.handleSelectionChange
	    }
	  }, [(!_vm.action) ? _c('el-table-column', {
	    staticClass: "hjhcheckbox",
	    attrs: {
	      "fixed": "",
	      "type": "selection",
	      "width": "55"
	    }
	  }) : _vm._e(), _vm._v(" "), _c('el-table-column', {
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
	      "align": "center"
	    }
	  }, [_c('el-table-column', {
	    attrs: {
	      "prop": "show_url",
	      "label": "商品图片",
	      "width": "150"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_c('img', {
	          attrs: {
	            "src": 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/' + scope.row.show_url
	          }
	        })]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "goods_name",
	      "label": "商品名称",
	      "width": "250",
	      "show-overflow-tooltip": _vm.showtooltip
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_c('a', {
	          on: {
	            "click": function($event) {
	              _vm.checkProduct(scope.$index, _vm.GoodsList.result_list)
	            }
	          }
	        }, [_vm._v(_vm._s(scope.row.goods_name))])]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "goods_id",
	      "label": "商品编号",
	      "width": "200",
	      "show-overflow-tooltip": _vm.showtooltip
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "brand_name",
	      "label": "品牌",
	      "width": "150",
	      "show-overflow-tooltip": _vm.showtooltip
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "third_category_name",
	      "label": "类目",
	      "width": "150",
	      "show-overflow-tooltip": _vm.showtooltip
	    }
	  })], 1), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "standardList",
	      "label": "规格/价格",
	      "width": "300",
	      "show-overflow-tooltip": _vm.showtooltip
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return _vm._l((scope.row.standardList), function(item) {
	          return _c('div', [_c('div', {
	            staticStyle: {
	              "overflow": "hidden"
	            }
	          }, [_c('span', {
	            staticStyle: {
	              "float": "left"
	            }
	          }, [_vm._v(_vm._s(item.standard_must) + "|" + _vm._s(item.optional_first) + "|" + _vm._s(item.optional_second))]), _vm._v(" "), _c('span', {
	            staticStyle: {
	              "float": "right"
	            }
	          }, [_c('span', {
	            staticStyle: {
	              "color": "#FD8F00"
	            }
	          }, [_vm._v(_vm._s(item.price))]), _vm._v("/" + _vm._s(item.unit_name))])])])
	        })
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "standardList",
	      "label": "已售",
	      "width": "100",
	      "align": "center",
	      "show-overflow-tooltip": _vm.showtooltip
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return _vm._l((scope.row.standardList), function(item) {
	          return _c('div', [_vm._v(_vm._s(item.sale_num || 0))])
	        })
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "standardList",
	      "label": "库存",
	      "width": "100",
	      "align": "center",
	      "show-overflow-tooltip": _vm.showtooltip
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return _vm._l((scope.row.standardList), function(item) {
	          return _c('div', [_vm._v(_vm._s(item.store_num || 0))])
	        })
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "initTime",
	      "label": "创建时间",
	      "width": "180",
	      "align": "center",
	      "show-overflow-tooltip": _vm.showtooltip
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "create_user_name",
	      "label": "创建人",
	      "width": "120",
	      "align": "center",
	      "show-overflow-tooltip": _vm.showtooltip
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "updateTime",
	      "label": "上架时间",
	      "width": "180",
	      "align": "center",
	      "show-overflow-tooltip": _vm.showtooltip
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "update_user_name",
	      "label": "最后操作人",
	      "width": "120",
	      "show-overflow-tooltip": _vm.showtooltip
	    }
	  })], 1)], 1), _vm._v(" "), _c('div', {
	    staticClass: "block-white"
	  }, [_c('el-pagination', {
	    attrs: {
	      "current-page": _vm.page,
	      "page-size": _vm.limit,
	      "layout": "total, prev, pager, next, jumper",
	      "total": _vm.GoodsList.total_num
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
	  }, [_vm._v("*")]), _vm._v("商品名称\n\t\t\t\t\t")]), _vm._v(" "), _c('el-col', {
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
	  }, [_vm._v("*")]), _vm._v("类目\n\t\t\t\t\t")]), _vm._v(" "), _c('el-col', {
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
	  }, [_vm._v("*")]), _vm._v("品牌\n\t\t\t\t\t")]), _vm._v(" "), _c('el-col', {
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
	  }, [_vm._v("*")]), _vm._v("计量单位\n\t\t\t\t\t")]), _vm._v(" "), _c('el-col', {
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
	  }, [_vm._v("*")]), _vm._v("占位图\n\t\t\t\t\t")]), _vm._v(" "), _c('el-col', {
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
	  }, [_vm._v("*")]), _vm._v("适用机型\n\t\t\t\t\t")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 20
	    }
	  }, [_vm._l((_vm.goodsDetail.carModelList), function(item) {
	    return _c('span', {
	      staticClass: "carModelList"
	    }, [_vm._v("\n\t\t\t\t\t\t\t" + _vm._s(item.car_brand_name) + " | " + _vm._s(item.car_type) + " | " + _vm._s(item.car_models_name) + "\n\t\t\t\t\t\t")])
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
	  }, [_vm._v("*")]), _vm._v("banner图\n\t\t\t\t\t")]), _vm._v(" "), _c('el-col', {
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
	  }, [_vm._v("*")]), _vm._v("商品详图\n\t\t\t\t\t")]), _vm._v(" "), _c('el-col', {
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
	  }, [_vm._v("*")]), _vm._v("客服电话\n\t\t\t\t\t")]), _vm._v(" "), _c('el-col', {
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
	  }, [_vm._v("*")]), _vm._v("商品参数\n\t\t\t\t\t")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 20
	    }
	  }, [_c('ul', {
	    staticClass: "param-list"
	  }, _vm._l((_vm.goodsDetail.infoList), function(item) {
	    return _c('li', [_c('span', [_c('em', [_vm._v("*")]), _vm._v("名称"), _c('span', [_vm._v(_vm._s(item.info_title))])]), _vm._v(" "), _c('span', [_c('em', [_vm._v("*")]), _vm._v("值"), _c('span', [_vm._v(_vm._s(item.info_content))])])])
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
	  }, [_vm._v("*")]), _vm._v("商品规格\n\t\t\t\t\t")]), _vm._v(" "), _c('el-col', {
	    attrs: {
	      "span": 20
	    }
	  }, [_c('ul', {
	    staticClass: "standard-list"
	  }, _vm._l((_vm.goodsDetail.standardList), function(item) {
	    return _c('li', [_c('span', [_c('em', [_vm._v("*")]), _vm._v("名称"), _c('span', [_vm._v(_vm._s(item.standard_must))]), _c('span', [_vm._v(_vm._s(item.optional_first))]), _c('span', [_vm._v(_vm._s(item.optional_second))])]), _vm._v(" "), _c('span', [_c('em', [_vm._v("*")]), _vm._v("价格"), _c('span', [_vm._v(_vm._s(item.price))])]), _vm._v(" "), _c('span', [_c('em', [_vm._v("*")]), _vm._v("库存"), _c('span', [_vm._v(_vm._s(item.store_num))])])])
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
	     require("vue-hot-reload-api").rerender("data-v-dbc79c40", module.exports)
	  }
	}

/***/ })

});
//# sourceMappingURL=62.js.map