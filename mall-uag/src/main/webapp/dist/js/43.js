webpackJsonp([43],{

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

/***/ 103:
/***/ (function(module, exports, __webpack_require__) {

	
	/* styles */
	__webpack_require__(104)
	
	var Component = __webpack_require__(13)(
	  /* script */
	  __webpack_require__(106),
	  /* template */
	  __webpack_require__(107),
	  /* scopeId */
	  null,
	  /* cssModules */
	  null
	)
	Component.options.__file = "/Users/xiangyangli/projects/hjh/jhd/mall-uag/src/main/webapp/app/components/newProduct.vue"
	if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key !== "__esModule"})) {console.error("named exports are not supported in *.vue files.")}
	if (Component.options.functional) {console.error("[vue-loader] newProduct.vue: functional components are not supported with templates, they should use render functions.")}
	
	/* hot reload */
	if (false) {(function () {
	  var hotAPI = require("vue-hot-reload-api")
	  hotAPI.install(require("vue"), false)
	  if (!hotAPI.compatible) return
	  module.hot.accept()
	  if (!module.hot.data) {
	    hotAPI.createRecord("data-v-1e5276b4", Component.options)
	  } else {
	    hotAPI.reload("data-v-1e5276b4", Component.options)
	  }
	})()}
	
	module.exports = Component.exports


/***/ }),

/***/ 104:
/***/ (function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(105);
	if(typeof content === 'string') content = [[module.id, content, '']];
	if(content.locals) module.exports = content.locals;
	// add the styles to the DOM
	var update = __webpack_require__(29)("54df3b9a", content, false);
	// Hot Module Replacement
	if(false) {
	 // When the styles change, update the <style> tags
	 if(!content.locals) {
	   module.hot.accept("!!../../node_modules/css-loader/index.js?sourceMap!../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-1e5276b4!../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./newProduct.vue", function() {
	     var newContent = require("!!../../node_modules/css-loader/index.js?sourceMap!../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-1e5276b4!../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./newProduct.vue");
	     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
	     update(newContent);
	   });
	 }
	 // When the module is disposed, remove the <style> tags
	 module.hot.dispose(function() { update(); });
	}

/***/ }),

/***/ 105:
/***/ (function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(28)();
	// imports
	
	
	// module
	exports.push([module.id, "\n.privewGoodsDetail{\n\twidth: 335px;\n\theight: 568px;\n\toverflow: hidden;\n\toverflow-y: auto;\n\tposition: fixed;\n\tborder: 1px solid #58B7FF; box-shadow: 0 0 10px #999;\n\tbackground-color: #ffffff;\n\ttop:114px;\n\tright: 30px;\n}\n.newCarTypeGroup{\n\t\theight: 60px;\n\t\tline-height: 60px;\n\t\tfont-family: \"\\5FAE\\8F6F\\96C5\\9ED1\";\n\t\tfont-size: 18px;\n\t\tcolor: #1D8CE0;\n}\n.addParameterBtn{\n\t\theight: 55px;\n\t    font-size: 14px;\n\t    margin: 20px 20px 20px 0;\n\t    text-align: center;\n\t    color: #58B7FF;\n\t    border: 1px dashed #58B7FF;\n\t    border-radius: 3px;\n\t    line-height: 55px;\n\t    cursor: pointer;\n}\n.addParameterBtn:hover{\n\t\tcolor: #20A0FF;\n\t    border: 1px dashed #20A0FF;\n}\n.sepecil .el-upload{\n\t\tfloat: left; margin-right: 10px;\n}\n.sepecil .el-upload__tip{\n\t\tfloat: left; margin: 0;\n}\n.sepecil .el-upload-list{\n\t\tclear: both;\n\t\twidth: 500px;\n}\n.el-tag{ margin-right: 10px;\n}\n.addParameterBtn{\n\t\theight: 55px;\n\t    font-size: 14px;\n\t    margin: 20px 20px 20px 0;\n\t    text-align: center;\n\t    color: #58B7FF;\n\t    border: 1px dashed #58B7FF;\n\t    border-radius: 3px;\n\t    line-height: 55px;\n\t    cursor: pointer;\n}\n.addParameterBtn:hover{\n\t\tcolor: #20A0FF;\n\t    border: 1px dashed #20A0FF;\n}\n.el-carousel__indicators{\n\tdisplay: none;\n}\n.addBtnHide .el-upload{\n\tdisplay: none;\n}\n.el-upload-list li:nth-child(6) + .is-ready{\n\tdisplay: none;\n}\n", "", {"version":3,"sources":["/./app/components/newProduct.vue?1789e4d0"],"names":[],"mappings":";AAqQA;CACA,aAAA;CACA,cAAA;CACA,iBAAA;CACA,iBAAA;CACA,gBAAA;CACA,0BAAA,CAAA,0BAAA;CACA,0BAAA;CACA,UAAA;CACA,YAAA;CACA;AACA;EACA,aAAA;EACA,kBAAA;EACA,oCAAA;EACA,gBAAA;EACA,eAAA;CACA;AACA;EACA,aAAA;KACA,gBAAA;KACA,yBAAA;KACA,mBAAA;KACA,eAAA;KACA,2BAAA;KACA,mBAAA;KACA,kBAAA;KACA,gBAAA;CACA;AACA;EACA,eAAA;KACA,2BAAA;CACA;AACA;EACA,YAAA,CAAA,mBAAA;CACA;AACA;EACA,YAAA,CAAA,UAAA;CACA;AACA;EACA,YAAA;EACA,aAAA;CACA;AACA,SAAA,mBAAA;CAAA;AACA;EACA,aAAA;KACA,gBAAA;KACA,yBAAA;KACA,mBAAA;KACA,eAAA;KACA,2BAAA;KACA,mBAAA;KACA,kBAAA;KACA,gBAAA;CACA;AACA;EACA,eAAA;KACA,2BAAA;CACA;AACA;CACA,cAAA;CACA;AACA;CACA,cAAA;CACA;AACA;CACA,cAAA;CACA","file":"newProduct.vue","sourcesContent":["<template>\n    <div>\n    \t\t<!-- 面包屑 -->\n\t\t<div class=\"hjh-breadcrumb\">\n\t\t\t<el-breadcrumb separator=\"/\">\n\t\t\t\t<el-breadcrumb-item :to=\"{ path: '/' }\">用户中心</el-breadcrumb-item>\n\t\t\t\t<el-breadcrumb-item>商品管理</el-breadcrumb-item>\n\t\t\t\t<el-breadcrumb-item>新增商品</el-breadcrumb-item>\n\t\t\t</el-breadcrumb>\t\n\t\t</div>\n\t\t<!-- 面包屑 end -->\n\t\t<div class=\"block-white\" style=\"padding-bottom:80px;\">\n\t\t\t<div class=\"newCarTypeGroup\">新增商品</div>\n\t\t\t<el-form :model=\"newProductForm\" :ref=\"newProductForm\" label-width=\"100px\" class=\"demo-form-inline\">\n\t\t\t\t<el-form-item label=\"商品名称：\"  prop=\"goods_name\" :rules=\"[{required: true, message: '请输入型号', trigger: 'blur'},\n\t\t\t\t\t\t{ min: 13, max: 38, message: '长度在 13 到 38 个字符', trigger: 'blur' }]\">\n\t\t\t\t\t<el-input v-model=\"newProductForm.goods_name\" :minlength=\"13\" :maxlength=\"38\" placeholder=\"请输入商品名称（13-38字数限制）\" style=\"width: 500px;\"></el-input>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"类目：\" prop=\"third_category_id\" :rules=\"[{required: true, message: '请选择类目'}]\">\n\t\t\t\t\t<input type=\"hidden\" v-model=\"newProductForm.third_category_id\" />\n\t\t\t\t\t<el-cascader \n\t\t\t\t\t:options=\"categoryOptions\" \n\t\t\t\t\t@change=\"handleCategoryChange\" \n\t\t\t\t\t@active-item-change=\"handleCategoryItemChange\"\n\t\t\t\t\t:props=\"categoryProps\"\n\t\t\t\t\t:showAllLevels=\"false\"\n\t\t\t\t\tfilterable\n\t\t\t\t\t>\n\t\t\t\t\t</el-cascader>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"品牌：\" prop=\"brand_id\" :rules=\"[{required: true, message: '请选择商品品牌'}]\">\n\t\t\t\t\t<el-select v-model=\"newProductForm.brand_id\" filterable placeholder=\"请选择商品品牌\" style=\"width: 150px;\" @change=\"brandChange\">\n\t\t\t\t\t\t<el-option v-for=\"item in brandList\" :key=\"item.brand_name\" :label=\"item.brand_name\" :value=\"item.brand_id\">\n\t\t\t\t\t\t</el-option>\n\t\t\t\t\t</el-select>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"计量单位：\" prop=\"unit_id\" :rules=\"[{required: true, message: '请选择计量单位'}]\">\n\t\t\t\t\t<el-select v-model=\"newProductForm.unit_id\" filterable placeholder=\"请选择计量单位\" style=\"width: 150px;\" @change=\"unitChange\">\n\t\t\t\t\t\t<el-option v-for=\"item in unitList\" :key=\"item.metadata_name\" :label=\"item.metadata_name\" :value=\"item.metadata_id\">\n\t\t\t\t\t\t</el-option>\n\t\t\t\t\t</el-select>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"占位图：\">\n\t\t\t\t\t<el-upload class=\"upload-demo sepecil\" \n\t\t\t\t\t\taction=\"\" \n\t\t\t\t\t\t:auto-upload=\"false\" \n\t\t\t\t\t\t:on-change=\"handleAdUrlChange\" \n\t\t\t\t\t\t:on-remove=\"handleAdUrlRemove\" \n\t\t\t\t\t\t:file-list=\"adFilesList\"\n\t\t\t\t\t>\n\t\t\t\t\t\t<el-button size=\"small\" type=\"primary\">点击上传</el-button>\n\t\t\t\t\t\t<div class=\"el-upload__tip\" slot=\"tip\">建议尺寸：750*68px</div>\n\t\t\t\t\t</el-upload>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"首页图：\" prop=\"show_url\" :rules=\"[{required: true, message: '请上传首页图'}]\">\n\t\t\t\t\t<el-upload class=\"upload-demo sepecil\" \n\t\t\t\t\t\taction=\"\" \n\t\t\t\t\t\t:auto-upload=\"false\" \n\t\t\t\t\t\t:on-change=\"handleShowUrlChange\" \n\t\t\t\t\t\t:on-remove=\"handleShowUrlRemove\" \n\t\t\t\t\t\t:file-list=\"showFilesList\"\n\t\t\t\t\t>\n\t\t\t\t\t\t<el-button size=\"small\" type=\"primary\">点击上传</el-button>\n\t\t\t\t\t\t<div class=\"el-upload__tip\" slot=\"tip\">建议尺寸：360*360px</div>\n\t\t\t\t\t</el-upload>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"适用机型：\">\n\t\t\t\t\t<div v-if=\"!adapt_all_models\" >\n\t\t\t\t\t\t<el-tag \n\t\t\t\t\t\t\tv-for=\"tag in carModelTagList\" \n\t\t\t\t\t\t\t:key=\"tag.value\" \n\t\t\t\t\t\t\t:closable=\"true\" \n\t\t\t\t\t\t\t:close-transition=\"false\" \n\t\t\t\t\t\t\t@close=\"handleTagClose(tag)\"\n\t\t\t\t\t\t\ttype=\"success\">\n\t\t\t\t\t\t\t{{tag.first.label}} / {{tag.second.label}} / {{tag.third.label}}\n\t\t\t\t\t\t</el-tag>\t\n\t\t\t\t\t</div>\n\t\t\t\t\t<el-cascader \n\t\t\t\t\t:disabled=\"adapt_all_models\"\n\t\t\t\t\t:options=\"carModelOptions\" \n\t\t\t\t\t@change=\"handleCarModelChange\"\n\t\t\t\t\t:value=\"CarModelSelected\"\n\t\t\t\t\t:props=\"carModelProps\"\n\t\t\t\t\t:clearable=\"true\"\n\t\t\t\t\t@active-item-change=\"handleCarModelItemChange\"\n\t\t\t\t\t:showAllLevels=\"true\"\n\t\t\t\t\tfilterable\n\t\t\t\t\tstyle=\"width:250px\"\n\t\t\t\t\t>\n\t\t\t\t\t</el-cascader>\n\t\t\t\t\t<el-checkbox v-model=\"adapt_all_models\" @change=\"adaptChange\">适配全部机型</el-checkbox>\n\t\t\t\t\t\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"banner图：\" prop=\"bannerList\" :rules=\"[{required: true, message: '请上传banner图 至少一张'}]\">\n\t\t\t\t\t<el-upload action=\"\"  class=\"upload-demo sepecil\">\n\t\t\t\t\t\t<div class=\"el-upload__tip\" slot=\"tip\">建议尺寸：750*600px <span style=\"color: #f00;\"> 至少一张</span></div>\n\t\t\t\t\t</el-upload>\n\t\t\t\t\t\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\" \" class=\"uploadBanner\">\n\t\t\t\t\t<el-upload class=\"upload-demo\" \n\t\t\t\t\t\t:disabled=\"bannerFilesList.length>=maxFileLength\"\n\t\t\t\t\t\taction=\"\" \n\t\t\t\t\t\tlist-type=\"picture-card\"\n\t\t\t\t\t\t:multiple = \"true\"\n\t\t\t\t\t\t:auto-upload=\"false\" \n\t\t\t\t\t\t:on-preview=\"handleBannerFilePreview\"\n\t\t\t\t\t\t:on-change=\"handleBannerFileChange\" \n\t\t\t\t\t\t:on-remove=\"handleBannerFileRemove\"\n\t\t\t\t\t\t:file-list=\"bannerFilesList\"\n\t\t\t\t\t>\n\t\t\t\t\t\t<i class=\"el-icon-plus\" :disabled=\"bannerFilesList.length>=maxFileLength\"></i>\n\t\t\t\t\t</el-upload>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"商品详图：\" prop=\"detailList\" :rules=\"[{required: true, message: '请上传商品详图 至少一张'}]\">\n\t\t\t\t\t<el-upload action=\"\"  class=\"upload-demo sepecil\">\n\t\t\t\t\t\t<div class=\"el-upload__tip\" slot=\"tip\"><span style=\"color: #f00;\">至少一张</span></div>\n\t\t\t\t\t</el-upload>\n\t\t\t\t\t\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\" \" class=\"uploadDetail\">\n\t\t\t\t\t<el-upload class=\"upload-demo\" \n\t\t\t\t\t\taction=\"\" \n\t\t\t\t\t\t:disabled=\"detailFilesList.length>=maxFileLength\"\n\t\t\t\t\t\tlist-type=\"picture-card\"\n\t\t\t\t\t\t:multiple = \"true\"\n\t\t\t\t\t\t:auto-upload=\"false\" \n\t\t\t\t\t\t:on-preview=\"handleDetailFilePreview\"\n\t\t\t\t\t\t:on-change=\"handleDetailFileChange\" \n\t\t\t\t\t\t:on-remove=\"handleDetailFileRemove\" \n\t\t\t\t\t\t:file-list=\"detailFilesList\"\n\t\t\t\t\t>\n\t\t\t\t\t\t<i class=\"el-icon-plus\" :disabled=\"detailFilesList.length>=maxFileLength\"></i>\n\t\t\t\t\t</el-upload>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\" \">\n\t\t\t\t\t<el-input v-for=\"item in newProductForm.detailList\" type=\"textarea\" :rows=\"2\" placeholder=\"输入描述 (2-200字数限制)\" v-model=\"item.pic_desc\" style=\"width:148px;margin-right:8px;\"></el-input>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-row>\n\t\t\t\t\t<el-col :span=\"8\">\n\t\t\t\t\t\t<el-form-item label=\"客服电话：\" prop=\"tel\" :rules=\"[{required: true, message: '请填写客服电话'},{max:20, message: '客服电话在20个字符以内'}]\">\n\t\t\t\t\t\t\t<el-input v-model=\"newProductForm.tel\" :minlength=\"1\" :maxlength=\"20\" placeholder=\"请填写客服电话（20字数限制）\" style=\"width: 100%;\"></el-input>\n\t\t\t\t\t\t</el-form-item>\n\t\t\t\t\t</el-col>\n\t\t\t\t\t<el-col :span=\"3\" style=\"text-align:right; line-height:36px;\">\n\t\t\t\t\t\t<el-checkbox v-model=\"isPayDeposit\" @change=\"paytypeChange\">预付定金</el-checkbox>\n\t\t\t\t\t</el-col>\n\t\t\t\t\t<el-col :span=\"12\" v-if=\"isPayDeposit\">\n\t\t\t\t\t\t\n\t\t\t\t\t\t<el-form-item label-width=\"20px\" prop=\"font_money_rate\" :rules=\"[{required: true, message: '请填写预付定金'}]\">\n\t\t\t\t\t\t\t<el-input v-model=\"newProductForm.font_money_rate\" type=\"number\" :min=\"1\" :max=\"100\" placeholder=\"定金支付比例\" style=\"width: 150px;\"></el-input>\n\t\t\t\t\t\t\t<span>%</span>\n\t\t\t\t\t\t</el-form-item>\n\t\t\t\t\t</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<div class=\"newCarTypeGroup\">商品介绍</div>\n\t\t\t\t<el-row v-for=\"(item,index) in newProductForm.infoList\">\n\t\t\t\t\t<el-col :span=\"8\">\n\t\t\t\t\t\t<el-form-item label=\"名称\" :prop=\"'infoList.'+index+'.info_title'\" :rules=\"[\n\t\t\t\t\t      { required: true, message: '请输入名称', trigger: 'blur' },\n\t\t\t\t\t      { min: 1, max: 5, message: '长度在 1 到 5 个字符', trigger: 'blur' }\n\t\t\t\t\t    ]\">\n\t\t\t\t\t\t\t<el-input v-model=\"item.info_title\" :minlength=\"1\" :maxlength=\"5\" placeholder=\"输入（1-5字数限制）\" style=\"width: 200px;\"></el-input>\n\t\t\t\t\t\t</el-form-item>\n\t\t\t\t\t</el-col>\n\t\t\t\t\t<el-col :span=\"14\">\n\t\t\t\t\t\t<el-form-item label=\"值\" label-width=\"40px\" :prop=\"'infoList.'+index+'.info_content'\" :rules=\"[\n\t\t\t\t\t      { required: true, message: '请输入介绍内容', trigger: 'blur' },\n\t\t\t\t\t      { min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: 'blur' }\n\t\t\t\t\t    ]\">\n\t\t\t\t\t\t\t<el-input v-model=\"item.info_content\" type=\"textarea\" :autosize=\"{ minRows: 1, maxRows: 4}\" :minlength=\"1\" :maxlength=\"200\" placeholder=\"输入（1-200字数限制）\" style=\"width: 100%;\"></el-input>\n\t\t\t\t\t\t</el-form-item>\n\t\t\t\t\t</el-col>\n\t\t\t\t\t<el-col :span=\"2\" align=\"center\">\n\t\t\t\t\t\t<el-button @click=\"deleteInfoList(item)\" :plain=\"true\" type=\"danger\">删除</el-button>\n\t\t\t\t\t</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<div class=\"addParameterBtn\" @click=\"addInfoList\"><i class=\"el-icon-plus\"></i> 添加商品介绍</div>\n\t\t\t\t<div class=\"newCarTypeGroup\">商品规格</div>\n\t\t\t\t<el-row v-for=\"(item,index) in newProductForm.standardList\">\n\t\t\t\t\t<el-col :span=\"10\">\n\t\t\t\t\t\t<el-form-item label=\"名称\" label-width=\"60px\" :prop=\"'standardList.'+index+'.standard_must'\" :rules=\"[\n\t\t\t\t\t      { required: true, message: '请输入名称', trigger: 'blur' },\n\t\t\t\t\t      { min: 1, max: 40, message: '长度在 1 到 40 个字符', trigger: 'blur' }\n\t\t\t\t\t    ]\">\n\t\t\t\t\t\t\t<el-input v-model=\"item.standard_must\" :minlength=\"1\" :maxlength=\"40\" placeholder=\"这个规格是必填的\" style=\"width: 30%;\"></el-input>\n\t\t\t\t\t\t\t<el-input v-model=\"item.optional_first\" :maxlength=\"40\" placeholder=\"选填规格1\" style=\"width: 30%;\"></el-input>\n\t\t\t\t\t\t\t<el-input v-model=\"item.optional_second\" :maxlength=\"40\" placeholder=\"选填规格2\" style=\"width: 30%;\"></el-input>\n\t\t\t\t\t\t</el-form-item>\n\t\t\t\t\t</el-col>\n\t\t\t\t\t<el-col :span=\"4\">\n\t\t\t\t\t\t<el-form-item label=\"价格\" label-width=\"60px\" :prop=\"'standardList.'+index+'.price'\" :rules=\"[\n\t\t\t\t\t      { required: true, message: '请输入价格', trigger: 'blur' },\n\t\t\t\t\t      { min: 1, max: 5, message: '长度在 1 到 10 个字符', trigger: 'blur' }\n\t\t\t\t\t    ]\">\n\t\t\t\t\t\t\t<el-input type=\"number\" v-model=\"item.price\" :minlength=\"1\" :maxlength=\"10\" placeholder=\"输入（1-10字数限制）\" style=\"width: 100%;\"></el-input>\n\t\t\t\t\t\t</el-form-item>\n\t\t\t\t\t</el-col>\n\t\t\t\t\t<el-col :span=\"5\">\n\t\t\t\t\t\t<el-form-item label=\"最大采购量(单笔)\" label-width=\"150px\" :prop=\"'standardList.'+index+'.max_sale_num'\" :rules=\"[\n\t\t\t\t\t      { required: true, message: '请输入采购量' }\n\t\t\t\t\t    ]\">\n\t\t\t\t\t\t\t<el-input type=\"number\" v-model=\"item.max_sale_num\" :minlength=\"1\" :maxlength=\"8\" placeholder=\"单笔最大采购量\" style=\"width: 100%;\"></el-input>\n\t\t\t\t\t\t</el-form-item>\n\t\t\t\t\t</el-col>\n\t\t\t\t\t<el-col :span=\"3\">\n\t\t\t\t\t\t<el-form-item label=\"指导价\" label-width=\"80px\" :prop=\"'standardList.'+index+'.reference_price'\" :rules=\"[\n\t\t\t\t\t      { required: true, message: '请输入指导价' }\n\t\t\t\t\t    ]\">\n\t\t\t\t\t\t\t<el-input type=\"number\" v-model=\"item.reference_price\" :minlength=\"1\" :maxlength=\"8\" placeholder=\"请输入指导价\" style=\"width: 100%;\"></el-input>\n\t\t\t\t\t\t</el-form-item>\n\t\t\t\t\t</el-col>\n\t\t\t\t\t<el-col :span=\"2\" align=\"center\">\n\t\t\t\t\t\t<el-button @click=\"deleteStandardList(item)\" :plain=\"true\" type=\"danger\">删除</el-button>\n\t\t\t\t\t</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<div class=\"addParameterBtn\" @click=\"addStandardList\"><i class=\"el-icon-plus\"></i> 添加商品规格</div>\n\n\t\t\t</el-form>\n\t\t\t\t\n\n\t\t</div>\n\n\t\t<div style=\"text-align:center; background:#fff; z-index:2; overflow:hidden; position:fixed; left:260px; right:0; bottom:0; padding:10px; border-top:1px solid #58B7FF;\">\n\t\t\t<el-button type=\"primary\" size=\"large\" @click=\"prviewGoods\">预览</el-button>\n\t\t\t<el-button type=\"warning\" size=\"large\" @click=\"adUnderdGoods\">待发布</el-button>\n\t\t\t<el-button type=\"success\" size=\"large\" @click=\"addGoods\">上架</el-button>\n\t\t</div>\n\t\t<el-dialog v-model=\"dialogPreviewVisible\">\n\t\t\t<img width=\"100%\" :src=\"dialogPreviewUrl\" alt=\"\">\n\t\t</el-dialog>\n\t\t<div class=\"privewGoodsDetail\" v-if=\"isPrviewGoods\">\n\t\t\t<el-carousel trigger=\"click\" height=\"225px\">\n\t\t\t\t<el-carousel-item v-for=\"item in newProductForm.bannerList\" :key=\"item\" indicator-position=\"none\" >\n\t\t\t\t\t<img v-if=\"item.pic_url\" :src=\"item.pic_url\" style=\"width:100%\">\n\t\t\t\t</el-carousel-item>\n\t\t\t</el-carousel>\n\t\t\t<div class=\"privewGoodsName\">{{newProductForm.goods_name}}</div>\n\t\t\t<div style=\"text-align:center;\">\n\t\t\t\t<img :src=\"newProductForm.ad_url\" style=\"width:93%; height:35px;\">\n\t\t\t</div>\n\t\t\t<div class=\"shopIntroduce\">\n\t\t\t\t<dl>\n\t\t\t\t\t<dt>商品介绍</dt>\n\t\t\t\t\t<dd class=\"viewBrand\"><label>品牌</label><span>{{newProductForm.brand_name}}</span></dd>\n\t\t\t\t\t<dd v-for=\"item in newProductForm.infoList\"><label>{{item.info_title}}</label><span>{{item.info_content}}</span></dd>\n\t\t\t\t</dl>\n\t\t\t</div>\n\t\t\t<div class=\"detailsNavi\">\n\t\t\t\t<div class=\"detailsNaviTop\">\n\t\t\t\t\t<div class=\"detailsNaviLeft\">图文详情</div>\n\t\t\t\t</div>\n\t\t\t\t<ul class=\"detailsCon\">\n\t\t\t\t\t<li v-for=\"item in newProductForm.detailList\"><img :src=\"item.pic_url\"><h5>{{item.pic_desc}}</h5></li>\n\t\t\t\t</ul>\n\t\t\t</div>\n\t\t</div>\n    </div>\n</template>\n<style>\n.privewGoodsDetail{\n\twidth: 335px;\n\theight: 568px;\n\toverflow: hidden;\n\toverflow-y: auto;\n\tposition: fixed;\n\tborder: 1px solid #58B7FF; box-shadow: 0 0 10px #999;\n\tbackground-color: #ffffff;\n\ttop:114px;\n\tright: 30px;\n}\n\t.newCarTypeGroup{\n\t\theight: 60px;\n\t\tline-height: 60px;\n\t\tfont-family: \"微软雅黑\";\n\t\tfont-size: 18px;\n\t\tcolor: #1D8CE0;\n\t}\n\t.addParameterBtn{\n\t\theight: 55px;\n\t    font-size: 14px;\n\t    margin: 20px 20px 20px 0;\n\t    text-align: center;\n\t    color: #58B7FF;\n\t    border: 1px dashed #58B7FF;\n\t    border-radius: 3px;\n\t    line-height: 55px;\n\t    cursor: pointer;\n\t}\n\t.addParameterBtn:hover{\n\t\tcolor: #20A0FF;\n\t    border: 1px dashed #20A0FF;\n\t}\n\t.sepecil .el-upload{\n\t\tfloat: left; margin-right: 10px;\n\t}\n\t.sepecil .el-upload__tip{\n\t\tfloat: left; margin: 0;\n\t}\n\t.sepecil .el-upload-list{\n\t\tclear: both;\n\t\twidth: 500px;\n\t}\n\t.el-tag{ margin-right: 10px; }\n\t.addParameterBtn{\n\t\theight: 55px;\n\t    font-size: 14px;\n\t    margin: 20px 20px 20px 0;\n\t    text-align: center;\n\t    color: #58B7FF;\n\t    border: 1px dashed #58B7FF;\n\t    border-radius: 3px;\n\t    line-height: 55px;\n\t    cursor: pointer;\n\t}\n\t.addParameterBtn:hover{\n\t\tcolor: #20A0FF;\n\t    border: 1px dashed #20A0FF;\n\t}\n.el-carousel__indicators{\n\tdisplay: none;\n}\n.addBtnHide .el-upload{\n\tdisplay: none;\n}\n.el-upload-list li:nth-child(6) + .is-ready{\n\tdisplay: none;\n}\n</style>\n<script>\n\timport Common from './../js/common';\n    export default{\n        data(){\n            return{\n                newProductForm : {\n                \tgoods_name : \"\",\t\t\t//商品名称\n                \tbrand_id : \"\",\t\t\t\t//品牌id\n                \tbrand_name : \"\",\t\t\t//品牌名称\n                \tunit_id : \"\",\t\t\t\t//计量单位id\n                \tunit_name : \"\",\t\t\t\t//计量单位name\n                \tad_url : \"\",\t\t\t\t//占位图 base64\n                \tshow_url : \"\",\t\t\t\t//首页图 base64\n                \tthird_category_id : \"\",\t\t//三级类目id\n                \tthird_category_name : \"\",\t//三级类目name\n                \tadapt_all_models : 0,\t\t//适配全部机型 0为否 1为选中\n                \tbannerList : [],\t\t\t//banner图列表\n                \tdetailList : [],\t\t\t//商品详图列表\n                \tinfoList : [],\t\t\t\t//商品介绍列表\n                \tstandardList : [],\t\t\t//商品规格列表\n                \tpay_type : 0,\n                \tcarModelList : []\n                },\n                categoryProps : {\t\t//类目 显示属性设置\n                \tvalue: 'value',\n                \tchildren: 'children'\n                },\n                categoryOptions : [],\t//类目 级联列表\n                brandList : [],\t\t\t//品牌列表\n                unitList : [],\t\t\t//计量单位列表\n                adFilesList : [],\t\t//占位图文件列表\n                showFilesList : [],\t\t//首页图文件列表\n                carModelTagList : [],\t//\n                carModelProps : {\t\t//机型 显示属性设置\n                \tvalue: 'value',\n                \tchildren: 'children'\n                },\n                carModelOptions : [],\t//机型 级联列表\n                bannerFilesList : [],\t//banner图文件列表\n                detailFilesList : [],\t//详情图文件列表\n\n                dialogPreviewVisible : false,\t//\n                dialogPreviewUrl : \"\",\t\t\t//\n                isPayDeposit : false,\t\t\t//\n                adapt_all_models : false,\t\t//\n                isPrviewGoods : false,\n                CarModelSelected : [],\n                maxFileLength : 6\n            }\n        },\n        created(){\n        \t\tdocument.title = \"后台管理系统-新增商品\";\n        \t\tthis.getBrandList();\n        \t\tthis.getUnitList();\n        \t\tthis.getCategoryList();\n        \t\tthis.getCarModelList();\n        },\n        mounted(){\n        \t\n        },\n        methods : {\n        \t\t/** 获取商品品牌列表 **/\n        \t\tgetBrandList(){\n        \t\t\tthis.$http.post(\"/goodsbrand/queryforweb\",{status:1,limit:10000000}).then(response=>{\n\t\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\t\tthis.brandList = jsondata.error_no == 0 ? jsondata.result_list : [];\n\t\t\t\t\t});\n        \t\t},\n        \t\t/** 获取计量单位列表 **/\n        \t\tgetUnitList(){\n        \t\t\tthis.$http.post(\"/queryMetadata\",{status:1,limit:10000000,type:0}).then(response=>{\n\t\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\t\tthis.unitList = jsondata.error_no == 0 ? jsondata.result_list : [];\n\t\t\t\t\t});\n        \t\t},\n        \t\t/** 获取类目列表 **/\n        \t\tgetCategoryList(){\n        \t\t\tthis.$http.post(\"/json/900507\",{status:1,limit:10000000}).then(response=>{\n\t\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\t\tvar options = jsondata.error_no == 0 ? jsondata.result_list : [];\n\t\t\t\t\t\tthis.categoryOptions = options.map(item=>{\n\t\t\t\t\t\t\treturn {\n\t\t\t\t\t\t\t\tvalue : item.category_id,\n\t\t\t\t\t\t\t\tlabel : item.category_name,\n\t\t\t\t\t\t\t\tchildren : []\n\t\t\t\t\t\t\t}\n\t\t\t\t\t\t});\n\t\t\t\t\t\treturn;\n\t\t\t\t\t\tvar that = this;\n\t\t\t\t\t\tthis.categoryOptions.map(function(item){\n\t\t\t\t\t\t\tthat.getCategoryChildren(item);\n\t\t\t\t\t\t});\n\t\t\t\t\t});\n        \t\t},\n        \t\thandleCategoryItemChange(val){ console.log(val);\n        \t\t\tvar len = val.length,\n        \t\t\t\tthat = this;\n        \t\t\tvar param = {\n\t\t\t        \t//fatherId : item.value,\n\t\t\t        \tlimit : 100000000,\n\t\t\t        \tstatus : 1,\n\t\t\t        \twebUse : true\n\t\t\t        };\n        \t\t\tthis.categoryOptions.map(first=>{\n        \t\t\t\tif(len==1){\n        \t\t\t\t\tif(first.value==val[0] && !first.children.length){\n        \t\t\t\t\t\tparam.fatherId = first.value;\n        \t\t\t\t\t\tthat.getCategoryChildren(param,first,true);\n        \t\t\t\t\t}\n        \t\t\t\t}else if(len==2){\n        \t\t\t\t\tif(first.value==val[0]){\n        \t\t\t\t\t\tfirst.children.map(second=>{\n        \t\t\t\t\t\t\tif(second.value==val[1] && !second.children.length){\n        \t\t\t\t\t\t\t\tparam.fatherId = second.value;\n        \t\t\t\t\t\t\t\tthat.getCategoryChildren(param,second);\n        \t\t\t\t\t\t\t}\n        \t\t\t\t\t\t})\n        \t\t\t\t\t}\n        \t\t\t\t}\n        \t\t\t});\n        \t\t},\n        \t\t/** 获取类目列表下子级列表 **/\n        \t\tgetCategoryChildren(param,target,bool){\n        \t\t\tthis.$http.post(\"/json/900515\",param).then(response=>{\n        \t\t\t\tvar jsondata = response.data;\n        \t\t\t\tvar list = jsondata.error_no == 0 ? jsondata.result_list : [];\n        \t\t\t\ttarget.children = list.map(item=>{\n\t\t\t\t\t        var obj = {\n\t\t\t\t\t\t\t\tvalue : item.category_id,\n\t\t\t\t\t\t\t\tlabel : item.category_name\n\t\t\t\t\t\t\t};\n\t\t\t\t\t\t\tif(bool){\n\t\t\t\t\t\t\t\tobj.children = [];\n\t\t\t\t\t\t\t}\n\t\t\t\t\t\t\treturn obj;\n\t\t\t\t\t    });\n        \t\t\t});\n        \t\t},\n        \t\t/** 类目级联选择器change钩子函数 **/\n        \t\thandleCategoryChange(val){ console.log(val);\n        \t\t\tvar first_category_id,first_category_name,second_category_id,second_category_name,\n        \t\t\t\tthird_category_id,third_category_name;\n        \t\t\tthis.categoryOptions.map(first=>{\n\n        \t\t\t\tif(first.value==val[0]){\n        \t\t\t\t\tfirst_category_id = val[0];\n        \t\t\t\t\tfirst_category_name = first.label;\n\n        \t\t\t\t\tif(first.children && val[1]){\n        \t\t\t\t\t\tfirst.children.map(second=>{\n\n        \t\t\t\t\t\t\tif(second.value==val[1]){\n        \t\t\t\t\t\t\t\tsecond_category_id = val[1];\n        \t\t\t\t\t\t\t\tsecond_category_name = second.label;\n        \t\t\t\t\t\t\t\tif(second.children && val[2]){\n        \t\t\t\t\t\t\t\t\tsecond.children.map(third=>{\n        \t\t\t\t\t\t\t\t\t\tif(third.value==val[2]){\n        \t\t\t\t\t\t\t\t\t\t\tthird_category_id = val[2];\n        \t\t\t\t\t\t\t\t\t\t\tthird_category_name = third.label;\n        \t\t\t\t\t\t\t\t\t\t}\n        \t\t\t\t\t\t\t\t\t})\n        \t\t\t\t\t\t\t\t}\n        \t\t\t\t\t\t\t}\n        \t\t\t\t\t\t});\n        \t\t\t\t\t}\n        \t\t\t\t\t\n        \t\t\t\t}\n        \t\t\t});\n        \t\t\tthis.newProductForm.first_category_id = first_category_id || \"\";\n        \t\t\tthis.newProductForm.first_category_name = first_category_name || \"\";\n        \t\t\tthis.newProductForm.second_category_id = second_category_id || \"\";\n        \t\t\tthis.newProductForm.second_category_name = second_category_name || \"\";\n        \t\t\tthis.newProductForm.third_category_id = third_category_id || \"\";\n        \t\t\tthis.newProductForm.third_category_name = third_category_name || \"\";\n        \t\t\tthis.newProductForm.category = third_category_id;\n        \t\t\tconsole.log(this.newProductForm);\n        \t\t},\n        \t\t/** 获取机型列表 **/\n        \t\tgetCarModelList(){\n        \t\t\tthis.$http.post(\"/carbrand/queryforweb\",{status:1,limit:10000000}).then(response=>{\n\t\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\t\tvar options = jsondata.error_no == 0 ? jsondata.result_list : [];\n\t\t\t\t\t\tthis.carModelOptions = options.map(item=>{\n\t\t\t\t\t\t\treturn {\n\t\t\t\t\t\t\t\tvalue : item.brand_id,\n\t\t\t\t\t\t\t\tlabel : item.brand_name,\n\t\t\t\t\t\t\t\tchildren : []\n\t\t\t\t\t\t\t}\n\t\t\t\t\t\t});\n\t\t\t\t\t});\n        \t\t},\n        \t\thandleCarModelItemChange(val){ console.log(val);\n        \t\t\tvar len = val.length;\n        \t\t\tvar url = len == 1 ? \"/json/901009\" : \"/queryCarModelListWeb\",\n        \t\t\t\tparam = {\n\t        \t\t\t\tbrand_id : val[0],\n\t        \t\t\t\tlimit : 100000000,\n\t        \t\t\t\tstatus : 1\n\t        \t\t\t};\n\t        \t\tvar that = this;\n\t        \t\tif(len==1){\n\t        \t\t\tthis.carModelOptions.map(first=>{\n\t        \t\t\t\tif(first.value == val[0] && !first.children.length){\n\t        \t\t\t\t\tthat.$http.post(url,param).then(response=>{\n\t        \t\t\t\t\t\tvar jsondata = response.data;\n        \t\t\t\t\t\t\tvar list = jsondata.error_no == 0 ? jsondata.result_list : [];\n        \t\t\t\t\t\t\tfirst.children = list.map(function(item){\n\t\t        \t\t\t\t\t\treturn {\n\t\t\t\t\t\t\t\t\t\t\tvalue : item.metadata_id,\n\t\t\t\t\t\t\t\t\t\t\tlabel : item.car_type,\n\t\t\t\t\t\t\t\t\t\t\tchildren : []\n\t\t\t\t\t\t\t\t\t\t}\n\t\t        \t\t\t\t\t});\n\t        \t\t\t\t\t});\n\t        \t\t\t\t}\n        \t\t\t\t})\n\t        \t\t}else if(len==2){\n\t        \t\t\tthis.carModelOptions.map(first=>{\n\t        \t\t\t\tif(first.value == val[0]){\n\t        \t\t\t\t\tfirst.children.map(second=>{\n\t        \t\t\t\t\t\tif(second.value == val[1] && !second.children.length){\n\t        \t\t\t\t\t\t\tparam.metadata_id = val[1];\n\t        \t\t\t\t\t\t\tthat.$http.post(url,param).then(response=>{\n\t\t\t        \t\t\t\t\t\tvar jsondata = response.data;\n\t\t        \t\t\t\t\t\t\tvar list = jsondata.error_no == 0 ? jsondata.result_list : [];\n\t\t        \t\t\t\t\t\t\tsecond.children = list.map(function(item){\n\t\t\t\t        \t\t\t\t\t\treturn {\n\t\t\t\t\t\t\t\t\t\t\t\t\tvalue : item.car_model_is,\n\t\t\t\t\t\t\t\t\t\t\t\t\tlabel : item.car_model_name\n\t\t\t\t\t\t\t\t\t\t\t\t}\n\t\t\t\t        \t\t\t\t\t});\n\t\t\t        \t\t\t\t\t});\n\t        \t\t\t\t\t\t}\n\t        \t\t\t\t\t});\n\t        \t\t\t\t}\n        \t\t\t\t})\n\t        \t\t}\n        \t\t},\n        \t\t\n        \t\t/** 适用机型 change钩子函数 **/\n        \t\thandleCarModelChange(val){\n        \t\t\tthis.CarModelSelected = [];\n        \t\t\t//console.log(val);\n        \t\t\tvar that = this;\n        \t\t\tthis.carModelOptions.map(first=>{\n        \t\t\t\tif(first.value == val[0]){\n        \t\t\t\t\tfirst.children.map(second=>{\n        \t\t\t\t\t\tif(second.value==val[1]){\n        \t\t\t\t\t\t\tsecond.children.map(third=>{\n        \t\t\t\t\t\t\t\tif(third.value==val[2]){\n        \t\t\t\t\t\t\t\t\tthat.carModelTagList.push({\n        \t\t\t\t\t\t\t\t\t\tfirst : {label:first.label,value:first.value},\n        \t\t\t\t\t\t\t\t\t\tsecond : {label:second.label,value:second.value},\n        \t\t\t\t\t\t\t\t\t\tthird : {label:third.label,value:third.value}\n        \t\t\t\t\t\t\t\t\t});\n        \t\t\t\t\t\t\t\t\tthird.disabled = true;\n        \t\t\t\t\t\t\t\t\tthat.newProductForm.carModelList.push(third.value);\n        \t\t\t\t\t\t\t\t}\n        \t\t\t\t\t\t\t\treturn third;\n        \t\t\t\t\t\t\t});\n        \t\t\t\t\t\t}\n        \t\t\t\t\t\treturn second;\n        \t\t\t\t\t});\n        \t\t\t\t}\n        \t\t\t\treturn first;\n        \t\t\t});\n        \t\t},\n        \t\thandleTagClose(tag){\n        \t\t\t//console.log(tag);\n        \t\t\t//console.log(this.carModelTagList);\n        \t\t\tvar that = this;\n        \t\t\tvar index = this.carModelTagList.indexOf(tag);\n        \t\t\tthis.carModelTagList = this.carModelTagList.filter(function(item,i){\n        \t\t\t\treturn index !== i;\n        \t\t\t});\n        \t\t\tthis.carModelOptions.map(first=>{\n        \t\t\t\tif(first.value == tag.first.value){\n        \t\t\t\t\tfirst.children.map(second=>{\n        \t\t\t\t\t\tif(second.value==tag.second.value){\n        \t\t\t\t\t\t\tsecond.children.map(third=>{\n        \t\t\t\t\t\t\t\tif(third.value==tag.third.value){\n        \t\t\t\t\t\t\t\t\tdelete third.disabled;\n        \t\t\t\t\t\t\t\t\tthat.newProductForm.carModelList = that.newProductForm.carModelList.filter(item=>{\n        \t\t\t\t\t\t\t\t\t\treturn item != third.value;\n        \t\t\t\t\t\t\t\t\t});\n        \t\t\t\t\t\t\t\t}\n        \t\t\t\t\t\t\t\treturn third;\n        \t\t\t\t\t\t\t});\n        \t\t\t\t\t\t}\n        \t\t\t\t\t\treturn second;\n        \t\t\t\t\t});\n        \t\t\t\t}\n        \t\t\t\treturn first;\n        \t\t\t});\n        \t\t},\n        \t\t/** 品牌选择 change钩子函数 **/\n        \t\tbrandChange(val){\n        \t\t\tvar result = this.brandList.filter(function(item){ return item.brand_id==val; })[0];\n        \t\t\tthis.newProductForm.brand_name = result ? result.brand_name : \"\";\n        \t\t},\n        \t\t/** 计量单位 change钩子函数 **/\n        \t\tunitChange(val){\n        \t\t\tvar result = this.unitList.filter(function(item){ return item.metadata_id==val; })[0];\n        \t\t\tthis.newProductForm.unit_name = result ? result.metadata_name : \"\";\n        \t\t},\n        \t\t/** 占位图 change钩子函数 **/\n        \t\thandleAdUrlChange(_file,_filelist){\n        \t\t\tthis.adFilesList = [_filelist[_filelist.length-1]];\n        \t\t\tvar that = this;\n        \t\t\tthis.compressImg(_file.raw,function(result){\n\t\t\t\t\t\tthat.newProductForm.ad_url = result;\n\t\t\t\t\t\t$(\".sepecil .el-upload-list > li\").last().prev().remove();\n        \t\t\t})\n        \t\t},\n        \t\t/** 移除占位图 钩子函数 **/\n        \t\thandleAdUrlRemove(_file,_filelist){\n        \t\t\tthis.adFilesList = _filelist;\n        \t\t\tif(!_filelist){\n        \t\t\t\tthis.newProductForm.ad_url = \"\";\n        \t\t\t}\n        \t\t},\n        \t\t/** 首页图 change钩子函数 **/\n        \t\thandleShowUrlChange(_file,_filelist){\n        \t\t\tthis.showFilesList = [_filelist[_filelist.length-1]];\n\t\t\t\t\tvar that = this;\n\t\t\t\t\tthis.compressImg(_file.raw,function(result){\n\t\t\t\t\t\tthat.newProductForm.show_url = result;\n\t\t\t\t\t\t$(\".sepecil .el-upload-list > li\").last().prev().remove();\n        \t\t\t})\n        \t\t},\n        \t\t/** 首页图移除 钩子函数 **/\n        \t\thandleShowUrlRemove(_file,_filelist){\n        \t\t\tthis.showFilesList = _filelist;\n        \t\t\tif(!_filelist){\n        \t\t\t\tthis.newProductForm.show_url = \"\";\n        \t\t\t}\n        \t\t},\n        \t\t/** 选择或者取消选择 适配全部机型 **/\n        \t\tadaptChange(){\n        \t\t\tthis.newProductForm.adapt_all_models = this.adapt_all_models ? 1 : 0;\n        \t\t},\n        \t\t/** 设置支付方式 0为全额支付 1为定金支付 **/\n        \t\tpaytypeChange(){\n        \t\t\tthis.newProductForm.pay_type = this.isPayDeposit ? 1 : 0;\n        \t\t},\n        \t\t/** banner图预览 **/\n        \t\thandleBannerFilePreview(file,filelist){\n        \t\t\t$(\".uploadBanner .el-upload__input\").trigger(\"click\");\n        \t\t\tthis.replaceBanner = true;\n        \t\t\tvar that = this;\n        \t\t\tthis.replaceBannerTarget = file;\n        \t\t},\n        \t\t/** 选择bannner图 **/\n        \t\thandleBannerFileChange(_file,_filelist){\n        \t\t\tif(this.bannerFilesList.length>=6 && !this.replaceBanner){return;}\n        \t\t\tvar that = this;\n        \t\t\tif(!this.replaceBanner){\n        \t\t\t\tthis.bannerFilesList.push(_file);\n\t        \t\t\tthis.newProductForm.bannerList = [];\n\t        \t\t\tthis.bannerFilesList.map((item,index)=>{\n\t        \t\t\t\tthat.compressImg(item.raw,function(result){\n\t\t\t\t\t\t\t\tthat.newProductForm.bannerList[index] = {pic_url:result,uid:item.uid};\n\t\t        \t\t\t})\n\t        \t\t\t})\n        \t\t\t}else{\n        \t\t\t\tthis.newProductForm.bannerList = [];\n        \t\t\t\tthis.bannerFilesList = this.bannerFilesList.map((item,index)=>{\n        \t\t\t\t\tif(item.uid == that.replaceBannerTarget.uid){\n        \t\t\t\t\t\titem = _file;\n        \t\t\t\t\t}\n        \t\t\t\t\tthat.compressImg(item.raw,function(result){\n        \t\t\t\t\t\tthat.newProductForm.bannerList[index] = {pic_url:result,uid:item.uid};\n        \t\t\t\t\t})\n        \t\t\t\t\treturn item;\n        \t\t\t\t})\n        \t\t\t\tthis.replaceBanner = false;\n        \t\t\t}\n\n        \t\t},\n        \t\t/** 移除banner图 **/\n        \t\thandleBannerFileRemove(_file,_filelist){\n        \t\t\tthis.bannerFilesList = _filelist;\n        \t\t\tthis.newProductForm.bannerList = this.newProductForm.bannerList.filter(item=>{\n        \t\t\t\treturn item.uid != _file.uid;\n        \t\t\t});\n        \t\t},\n        \t\t/** 商品详图预览 **/\n        \t\thandleDetailFilePreview(file,filelist){\n        \t\t\t$(\".uploadDetail .el-upload__input\").trigger(\"click\");\n        \t\t\tthis.replaceDetail = true;\n        \t\t\tvar that = this;\n        \t\t\tthis.replaceDetailTarget = file;\n        \t\t},\n        \t\t/** 添加商品详图 **/\n        \t\thandleDetailFileChange(file,filelist){\n        \t\t\tif(this.detailFilesList.length>=6 && !this.replaceDetail){return;}\n        \t\t\tvar that = this;\n        \t\t\tif(!this.replaceDetail){\n        \t\t\t\tthis.detailFilesList.push(file);\n        \t\t\t\tthis.compressImg(file.raw,function(result){\n        \t\t\t\t\tthat.newProductForm.detailList.push({pic_url:result,uid:file.uid,pic_desc:\"\"});\n        \t\t\t\t});\n\n        \t\t\t}else{\n        \t\t\t\t//this.newProductForm.detailList = [];\n        \t\t\t\tthis.detailFilesList = this.detailFilesList.map((item,index)=>{\n        \t\t\t\t\tif(item.uid == that.replaceDetailTarget.uid){\n        \t\t\t\t\t\titem = file;\n        \t\t\t\t\t\tvar _item = that.newProductForm.detailList[index];\n        \t\t\t\t\t\tthat.compressImg(item.raw,function(result){\n\t        \t\t\t\t\t\tthat.newProductForm.detailList[index] = {pic_url:result,uid:item.uid,pic_desc:_item.pic_desc};\n\t        \t\t\t\t\t})\n        \t\t\t\t\t}\n        \t\t\t\t\t\n        \t\t\t\t\treturn item;\n        \t\t\t\t})\n        \t\t\t\tthis.replaceDetail = false;\n        \t\t\t}\n        \t\t\t//console.log(this.newProductForm.detailList.length);\n\n        \t\t},\n        \t\t/** 商品详图删除 **/\n        \t\thandleDetailFileRemove(file,filelist){\n        \t\t\tthis.detailFilesList = filelist;\n        \t\t\tthis.newProductForm.detailList = this.newProductForm.detailList.filter(item=>{\n        \t\t\t\treturn item.uid != file.uid;\n        \t\t\t});\n\n        \t\t},\n        \t\t/** 添加商品介绍 **/\n        \t\taddInfoList(){\n        \t\t\tthis.newProductForm.infoList.push({info_title:\"\",info_content:\"\"});\n        \t\t},\n        \t\t/** 删除商品介绍 **/\n        \t\tdeleteInfoList(item){\n        \t\t\tvar index = this.newProductForm.infoList.indexOf(item);\n\t\t\t        if (index !== -1) {\n\t\t\t        \tthis.newProductForm.infoList.splice(index, 1)\n\t\t\t        }\n        \t\t},\n        \t\t/** 添加商品规格 **/\n        \t\taddStandardList(){\n\t\t\t\t\tthis.newProductForm.standardList.push({\n\t\t\t\t\t\tstandard_must:\"\",\n\t\t\t\t\t\toptional_first:\"\",\n\t\t\t\t\t\toptional_second:\"\",\n\t\t\t\t\t\tmax_sale_num:\"\",\n\t\t\t\t\t\tprice : \"\",\n\t\t\t\t\t\tstore_num : 999999\n\t\t\t\t\t});\n        \t\t},\n        \t\t/** 删除商品规格 **/\n        \t\tdeleteStandardList(item){\n        \t\t\tvar index = this.newProductForm.standardList.indexOf(item);\n\t\t\t        if (index !== -1) {\n\t\t\t        \tthis.newProductForm.standardList.splice(index, 1)\n\t\t\t        }\n        \t\t},\n        \t\t/** 图片压缩 **/\n        \t\tcompressImg(file,cb){\n        \t\t\tvar reader = new FileReader(); \n\t\t\t\t\treader.readAsDataURL(file);\n\t\t\t\t\treader.onload = function(e){\n\t\t\t\t\t\tvar img = new Image();\n\t\t\t\t\t\t\timg.src = this.result;\n\t\t\t\t\t\t\timg.onload = function(){\n\t\t\t\t\t\t\t\tcb(Common.compressImg(img,0.8,\"image/jpeg\"))\n\t\t\t\t\t\t\t}\n\t\t\t\t\t}\n        \t\t},\n        \t\t/** 上架操作 **/\n        \t\taddGoods(){\n        \t\t\tthis.$refs[this.newProductForm].validate((valid) => {\n        \t\t\t\tif(this.newProductForm.font_money_rate <0 || this.newProductForm.font_money_rate >100){\n        \t\t\t\t\tthis.$message({\n\t\t\t\t\t\t\t\ttype: \"error\",\n\t\t\t\t\t\t\t\tmessage: \"预付定金数字限制1-100\"\n\t\t\t\t\t\t\t});\n\t\t\t\t\t\t\treturn;\n        \t\t\t\t}\n        \t\t\t\tvar result = this.newProductForm.standardList.filter(item=>{\n        \t\t\t\t\treturn (item.standard_must.length + item.optional_first.length + item.optional_second.length) > 40;\n        \t\t\t\t});\n        \t\t\t\tif(result.length){\n        \t\t\t\t\tthis.$message({\n\t\t\t\t\t\t\t\ttype: \"error\",\n\t\t\t\t\t\t\t\tmessage: \"商品规格三个字段长度之和不能超过40字符\"\n\t\t\t\t\t\t\t});\n\t\t\t\t\t\t\treturn;\n        \t\t\t\t}\n        \t\t\t\tif(valid){\n        \t\t\t\t\t\n        \t\t\t\t\tthis.newProductForm.standardList = this.newProductForm.standardList.map(function(item){\n        \t\t\t\t\t\titem.max_sale_num = Number(item.max_sale_num);\n        \t\t\t\t\t\treturn item;\n        \t\t\t\t\t});\n        \t\t\t\t\tthis.$http.post(\"/addGoods\",this.newProductForm).then(response=>{\n        \t\t\t\t\t\tvar jsondata = response.data;\n        \t\t\t\t\t\tif(jsondata.error_no==0){\n        \t\t\t\t\t\t\tthis.$message({\n\t\t\t\t\t\t\t\t\t\ttype: \"success\",\n\t\t\t\t\t\t\t\t\t\tmessage: \"添加成功\"\n\t\t\t\t\t\t\t\t\t});\n\t\t\t\t\t\t\t\t\twindow.location.href = \"#/groundingProduct\";\n        \t\t\t\t\t\t}\n        \t\t\t\t\t})\n        \t\t\t\t}\n        \t\t\t})\n        \t\t},\n        \t\t/** 待发布操作 **/\n        \t\tadUnderdGoods(){\n        \t\t\tthis.$refs[this.newProductForm].validate((valid) => {\n        \t\t\t\tif(valid){\n        \t\t\t\t\tthis.newProductForm.standardList = this.newProductForm.standardList.map(function(item){\n        \t\t\t\t\t\titem.max_sale_num = Number(item.max_sale_num);\n        \t\t\t\t\t\treturn item;\n        \t\t\t\t\t});\n        \t\t\t\t\tthis.$http.post(\"/adUnderdGoods\",this.newProductForm).then(response=>{\n        \t\t\t\t\t\tvar jsondata = response.data;\n        \t\t\t\t\t\tif(jsondata.error_no==0){\n        \t\t\t\t\t\t\tthis.$message({\n\t\t\t\t\t\t\t\t\t\ttype: \"success\",\n\t\t\t\t\t\t\t\t\t\tmessage: \"添加成功\"\n\t\t\t\t\t\t\t\t\t});\n\t\t\t\t\t\t\t\t\twindow.location.href = \"#/groundingProduct\";\n        \t\t\t\t\t\t}\n        \t\t\t\t\t})\n        \t\t\t\t}\n        \t\t\t})\n        \t\t},\n        \t\t/** 预览商品 **/\n        \t\tprviewGoods(){\n        \t\t\tthis.isPrviewGoods = !this.isPrviewGoods;\n        \t\t\tconsole.log(this.CarModelSelected);\n        \t\t\tconsole.log(this.newProductForm);\n        \t\t}\n        }\n    }\n</script>\n"],"sourceRoot":"webpack://"}]);
	
	// exports


/***/ }),

/***/ 106:
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
	      newProductForm: {
	        goods_name: "", //商品名称
	        brand_id: "", //品牌id
	        brand_name: "", //品牌名称
	        unit_id: "", //计量单位id
	        unit_name: "", //计量单位name
	        ad_url: "", //占位图 base64
	        show_url: "", //首页图 base64
	        third_category_id: "", //三级类目id
	        third_category_name: "", //三级类目name
	        adapt_all_models: 0, //适配全部机型 0为否 1为选中
	        bannerList: [], //banner图列表
	        detailList: [], //商品详图列表
	        infoList: [], //商品介绍列表
	        standardList: [], //商品规格列表
	        pay_type: 0,
	        carModelList: []
	      },
	      categoryProps: { //类目 显示属性设置
	        value: 'value',
	        children: 'children'
	      },
	      categoryOptions: [], //类目 级联列表
	      brandList: [], //品牌列表
	      unitList: [], //计量单位列表
	      adFilesList: [], //占位图文件列表
	      showFilesList: [], //首页图文件列表
	      carModelTagList: [], //
	      carModelProps: { //机型 显示属性设置
	        value: 'value',
	        children: 'children'
	      },
	      carModelOptions: [], //机型 级联列表
	      bannerFilesList: [], //banner图文件列表
	      detailFilesList: [], //详情图文件列表
	
	      dialogPreviewVisible: false, //
	      dialogPreviewUrl: "", //
	      isPayDeposit: false, //
	      adapt_all_models: false, //
	      isPrviewGoods: false,
	      CarModelSelected: [],
	      maxFileLength: 6
	    };
	  },
	  created: function created() {
	    document.title = "后台管理系统-新增商品";
	    this.getBrandList();
	    this.getUnitList();
	    this.getCategoryList();
	    this.getCarModelList();
	  },
	  mounted: function mounted() {},
	
	  methods: {
	    /** 获取商品品牌列表 **/
	    getBrandList: function getBrandList() {
	      var _this = this;
	
	      this.$http.post("/goodsbrand/queryforweb", { status: 1, limit: 10000000 }).then(function (response) {
	        var jsondata = response.data;
	        _this.brandList = jsondata.error_no == 0 ? jsondata.result_list : [];
	      });
	    },
	
	    /** 获取计量单位列表 **/
	    getUnitList: function getUnitList() {
	      var _this2 = this;
	
	      this.$http.post("/queryMetadata", { status: 1, limit: 10000000, type: 0 }).then(function (response) {
	        var jsondata = response.data;
	        _this2.unitList = jsondata.error_no == 0 ? jsondata.result_list : [];
	      });
	    },
	
	    /** 获取类目列表 **/
	    getCategoryList: function getCategoryList() {
	      var _this3 = this;
	
	      this.$http.post("/json/900507", { status: 1, limit: 10000000 }).then(function (response) {
	        var jsondata = response.data;
	        var options = jsondata.error_no == 0 ? jsondata.result_list : [];
	        _this3.categoryOptions = options.map(function (item) {
	          return {
	            value: item.category_id,
	            label: item.category_name,
	            children: []
	          };
	        });
	        return;
	        var that = _this3;
	        _this3.categoryOptions.map(function (item) {
	          that.getCategoryChildren(item);
	        });
	      });
	    },
	    handleCategoryItemChange: function handleCategoryItemChange(val) {
	      console.log(val);
	      var len = val.length,
	          that = this;
	      var param = {
	        //fatherId : item.value,
	        limit: 100000000,
	        status: 1,
	        webUse: true
	      };
	      this.categoryOptions.map(function (first) {
	        if (len == 1) {
	          if (first.value == val[0] && !first.children.length) {
	            param.fatherId = first.value;
	            that.getCategoryChildren(param, first, true);
	          }
	        } else if (len == 2) {
	          if (first.value == val[0]) {
	            first.children.map(function (second) {
	              if (second.value == val[1] && !second.children.length) {
	                param.fatherId = second.value;
	                that.getCategoryChildren(param, second);
	              }
	            });
	          }
	        }
	      });
	    },
	
	    /** 获取类目列表下子级列表 **/
	    getCategoryChildren: function getCategoryChildren(param, target, bool) {
	      this.$http.post("/json/900515", param).then(function (response) {
	        var jsondata = response.data;
	        var list = jsondata.error_no == 0 ? jsondata.result_list : [];
	        target.children = list.map(function (item) {
	          var obj = {
	            value: item.category_id,
	            label: item.category_name
	          };
	          if (bool) {
	            obj.children = [];
	          }
	          return obj;
	        });
	      });
	    },
	
	    /** 类目级联选择器change钩子函数 **/
	    handleCategoryChange: function handleCategoryChange(val) {
	      console.log(val);
	      var first_category_id, first_category_name, second_category_id, second_category_name, third_category_id, third_category_name;
	      this.categoryOptions.map(function (first) {
	
	        if (first.value == val[0]) {
	          first_category_id = val[0];
	          first_category_name = first.label;
	
	          if (first.children && val[1]) {
	            first.children.map(function (second) {
	
	              if (second.value == val[1]) {
	                second_category_id = val[1];
	                second_category_name = second.label;
	                if (second.children && val[2]) {
	                  second.children.map(function (third) {
	                    if (third.value == val[2]) {
	                      third_category_id = val[2];
	                      third_category_name = third.label;
	                    }
	                  });
	                }
	              }
	            });
	          }
	        }
	      });
	      this.newProductForm.first_category_id = first_category_id || "";
	      this.newProductForm.first_category_name = first_category_name || "";
	      this.newProductForm.second_category_id = second_category_id || "";
	      this.newProductForm.second_category_name = second_category_name || "";
	      this.newProductForm.third_category_id = third_category_id || "";
	      this.newProductForm.third_category_name = third_category_name || "";
	      this.newProductForm.category = third_category_id;
	      console.log(this.newProductForm);
	    },
	
	    /** 获取机型列表 **/
	    getCarModelList: function getCarModelList() {
	      var _this4 = this;
	
	      this.$http.post("/carbrand/queryforweb", { status: 1, limit: 10000000 }).then(function (response) {
	        var jsondata = response.data;
	        var options = jsondata.error_no == 0 ? jsondata.result_list : [];
	        _this4.carModelOptions = options.map(function (item) {
	          return {
	            value: item.brand_id,
	            label: item.brand_name,
	            children: []
	          };
	        });
	      });
	    },
	    handleCarModelItemChange: function handleCarModelItemChange(val) {
	      console.log(val);
	      var len = val.length;
	      var url = len == 1 ? "/json/901009" : "/queryCarModelListWeb",
	          param = {
	        brand_id: val[0],
	        limit: 100000000,
	        status: 1
	      };
	      var that = this;
	      if (len == 1) {
	        this.carModelOptions.map(function (first) {
	          if (first.value == val[0] && !first.children.length) {
	            that.$http.post(url, param).then(function (response) {
	              var jsondata = response.data;
	              var list = jsondata.error_no == 0 ? jsondata.result_list : [];
	              first.children = list.map(function (item) {
	                return {
	                  value: item.metadata_id,
	                  label: item.car_type,
	                  children: []
	                };
	              });
	            });
	          }
	        });
	      } else if (len == 2) {
	        this.carModelOptions.map(function (first) {
	          if (first.value == val[0]) {
	            first.children.map(function (second) {
	              if (second.value == val[1] && !second.children.length) {
	                param.metadata_id = val[1];
	                that.$http.post(url, param).then(function (response) {
	                  var jsondata = response.data;
	                  var list = jsondata.error_no == 0 ? jsondata.result_list : [];
	                  second.children = list.map(function (item) {
	                    return {
	                      value: item.car_model_is,
	                      label: item.car_model_name
	                    };
	                  });
	                });
	              }
	            });
	          }
	        });
	      }
	    },
	
	
	    /** 适用机型 change钩子函数 **/
	    handleCarModelChange: function handleCarModelChange(val) {
	      this.CarModelSelected = [];
	      //console.log(val);
	      var that = this;
	      this.carModelOptions.map(function (first) {
	        if (first.value == val[0]) {
	          first.children.map(function (second) {
	            if (second.value == val[1]) {
	              second.children.map(function (third) {
	                if (third.value == val[2]) {
	                  that.carModelTagList.push({
	                    first: { label: first.label, value: first.value },
	                    second: { label: second.label, value: second.value },
	                    third: { label: third.label, value: third.value }
	                  });
	                  third.disabled = true;
	                  that.newProductForm.carModelList.push(third.value);
	                }
	                return third;
	              });
	            }
	            return second;
	          });
	        }
	        return first;
	      });
	    },
	    handleTagClose: function handleTagClose(tag) {
	      //console.log(tag);
	      //console.log(this.carModelTagList);
	      var that = this;
	      var index = this.carModelTagList.indexOf(tag);
	      this.carModelTagList = this.carModelTagList.filter(function (item, i) {
	        return index !== i;
	      });
	      this.carModelOptions.map(function (first) {
	        if (first.value == tag.first.value) {
	          first.children.map(function (second) {
	            if (second.value == tag.second.value) {
	              second.children.map(function (third) {
	                if (third.value == tag.third.value) {
	                  delete third.disabled;
	                  that.newProductForm.carModelList = that.newProductForm.carModelList.filter(function (item) {
	                    return item != third.value;
	                  });
	                }
	                return third;
	              });
	            }
	            return second;
	          });
	        }
	        return first;
	      });
	    },
	
	    /** 品牌选择 change钩子函数 **/
	    brandChange: function brandChange(val) {
	      var result = this.brandList.filter(function (item) {
	        return item.brand_id == val;
	      })[0];
	      this.newProductForm.brand_name = result ? result.brand_name : "";
	    },
	
	    /** 计量单位 change钩子函数 **/
	    unitChange: function unitChange(val) {
	      var result = this.unitList.filter(function (item) {
	        return item.metadata_id == val;
	      })[0];
	      this.newProductForm.unit_name = result ? result.metadata_name : "";
	    },
	
	    /** 占位图 change钩子函数 **/
	    handleAdUrlChange: function handleAdUrlChange(_file, _filelist) {
	      this.adFilesList = [_filelist[_filelist.length - 1]];
	      var that = this;
	      this.compressImg(_file.raw, function (result) {
	        that.newProductForm.ad_url = result;
	        $(".sepecil .el-upload-list > li").last().prev().remove();
	      });
	    },
	
	    /** 移除占位图 钩子函数 **/
	    handleAdUrlRemove: function handleAdUrlRemove(_file, _filelist) {
	      this.adFilesList = _filelist;
	      if (!_filelist) {
	        this.newProductForm.ad_url = "";
	      }
	    },
	
	    /** 首页图 change钩子函数 **/
	    handleShowUrlChange: function handleShowUrlChange(_file, _filelist) {
	      this.showFilesList = [_filelist[_filelist.length - 1]];
	      var that = this;
	      this.compressImg(_file.raw, function (result) {
	        that.newProductForm.show_url = result;
	        $(".sepecil .el-upload-list > li").last().prev().remove();
	      });
	    },
	
	    /** 首页图移除 钩子函数 **/
	    handleShowUrlRemove: function handleShowUrlRemove(_file, _filelist) {
	      this.showFilesList = _filelist;
	      if (!_filelist) {
	        this.newProductForm.show_url = "";
	      }
	    },
	
	    /** 选择或者取消选择 适配全部机型 **/
	    adaptChange: function adaptChange() {
	      this.newProductForm.adapt_all_models = this.adapt_all_models ? 1 : 0;
	    },
	
	    /** 设置支付方式 0为全额支付 1为定金支付 **/
	    paytypeChange: function paytypeChange() {
	      this.newProductForm.pay_type = this.isPayDeposit ? 1 : 0;
	    },
	
	    /** banner图预览 **/
	    handleBannerFilePreview: function handleBannerFilePreview(file, filelist) {
	      $(".uploadBanner .el-upload__input").trigger("click");
	      this.replaceBanner = true;
	      var that = this;
	      this.replaceBannerTarget = file;
	    },
	
	    /** 选择bannner图 **/
	    handleBannerFileChange: function handleBannerFileChange(_file, _filelist) {
	      if (this.bannerFilesList.length >= 6 && !this.replaceBanner) {
	        return;
	      }
	      var that = this;
	      if (!this.replaceBanner) {
	        this.bannerFilesList.push(_file);
	        this.newProductForm.bannerList = [];
	        this.bannerFilesList.map(function (item, index) {
	          that.compressImg(item.raw, function (result) {
	            that.newProductForm.bannerList[index] = { pic_url: result, uid: item.uid };
	          });
	        });
	      } else {
	        this.newProductForm.bannerList = [];
	        this.bannerFilesList = this.bannerFilesList.map(function (item, index) {
	          if (item.uid == that.replaceBannerTarget.uid) {
	            item = _file;
	          }
	          that.compressImg(item.raw, function (result) {
	            that.newProductForm.bannerList[index] = { pic_url: result, uid: item.uid };
	          });
	          return item;
	        });
	        this.replaceBanner = false;
	      }
	    },
	
	    /** 移除banner图 **/
	    handleBannerFileRemove: function handleBannerFileRemove(_file, _filelist) {
	      this.bannerFilesList = _filelist;
	      this.newProductForm.bannerList = this.newProductForm.bannerList.filter(function (item) {
	        return item.uid != _file.uid;
	      });
	    },
	
	    /** 商品详图预览 **/
	    handleDetailFilePreview: function handleDetailFilePreview(file, filelist) {
	      $(".uploadDetail .el-upload__input").trigger("click");
	      this.replaceDetail = true;
	      var that = this;
	      this.replaceDetailTarget = file;
	    },
	
	    /** 添加商品详图 **/
	    handleDetailFileChange: function handleDetailFileChange(file, filelist) {
	      if (this.detailFilesList.length >= 6 && !this.replaceDetail) {
	        return;
	      }
	      var that = this;
	      if (!this.replaceDetail) {
	        this.detailFilesList.push(file);
	        this.compressImg(file.raw, function (result) {
	          that.newProductForm.detailList.push({ pic_url: result, uid: file.uid, pic_desc: "" });
	        });
	      } else {
	        //this.newProductForm.detailList = [];
	        this.detailFilesList = this.detailFilesList.map(function (item, index) {
	          if (item.uid == that.replaceDetailTarget.uid) {
	            item = file;
	            var _item = that.newProductForm.detailList[index];
	            that.compressImg(item.raw, function (result) {
	              that.newProductForm.detailList[index] = { pic_url: result, uid: item.uid, pic_desc: _item.pic_desc };
	            });
	          }
	
	          return item;
	        });
	        this.replaceDetail = false;
	      }
	      //console.log(this.newProductForm.detailList.length);
	    },
	
	    /** 商品详图删除 **/
	    handleDetailFileRemove: function handleDetailFileRemove(file, filelist) {
	      this.detailFilesList = filelist;
	      this.newProductForm.detailList = this.newProductForm.detailList.filter(function (item) {
	        return item.uid != file.uid;
	      });
	    },
	
	    /** 添加商品介绍 **/
	    addInfoList: function addInfoList() {
	      this.newProductForm.infoList.push({ info_title: "", info_content: "" });
	    },
	
	    /** 删除商品介绍 **/
	    deleteInfoList: function deleteInfoList(item) {
	      var index = this.newProductForm.infoList.indexOf(item);
	      if (index !== -1) {
	        this.newProductForm.infoList.splice(index, 1);
	      }
	    },
	
	    /** 添加商品规格 **/
	    addStandardList: function addStandardList() {
	      this.newProductForm.standardList.push({
	        standard_must: "",
	        optional_first: "",
	        optional_second: "",
	        max_sale_num: "",
	        price: "",
	        store_num: 999999
	      });
	    },
	
	    /** 删除商品规格 **/
	    deleteStandardList: function deleteStandardList(item) {
	      var index = this.newProductForm.standardList.indexOf(item);
	      if (index !== -1) {
	        this.newProductForm.standardList.splice(index, 1);
	      }
	    },
	
	    /** 图片压缩 **/
	    compressImg: function compressImg(file, cb) {
	      var reader = new FileReader();
	      reader.readAsDataURL(file);
	      reader.onload = function (e) {
	        var img = new Image();
	        img.src = this.result;
	        img.onload = function () {
	          cb(_common2.default.compressImg(img, 0.8, "image/jpeg"));
	        };
	      };
	    },
	
	    /** 上架操作 **/
	    addGoods: function addGoods() {
	      var _this5 = this;
	
	      this.$refs[this.newProductForm].validate(function (valid) {
	        if (_this5.newProductForm.font_money_rate < 0 || _this5.newProductForm.font_money_rate > 100) {
	          _this5.$message({
	            type: "error",
	            message: "预付定金数字限制1-100"
	          });
	          return;
	        }
	        var result = _this5.newProductForm.standardList.filter(function (item) {
	          return item.standard_must.length + item.optional_first.length + item.optional_second.length > 40;
	        });
	        if (result.length) {
	          _this5.$message({
	            type: "error",
	            message: "商品规格三个字段长度之和不能超过40字符"
	          });
	          return;
	        }
	        if (valid) {
	
	          _this5.newProductForm.standardList = _this5.newProductForm.standardList.map(function (item) {
	            item.max_sale_num = Number(item.max_sale_num);
	            return item;
	          });
	          _this5.$http.post("/addGoods", _this5.newProductForm).then(function (response) {
	            var jsondata = response.data;
	            if (jsondata.error_no == 0) {
	              _this5.$message({
	                type: "success",
	                message: "添加成功"
	              });
	              window.location.href = "#/groundingProduct";
	            }
	          });
	        }
	      });
	    },
	
	    /** 待发布操作 **/
	    adUnderdGoods: function adUnderdGoods() {
	      var _this6 = this;
	
	      this.$refs[this.newProductForm].validate(function (valid) {
	        if (valid) {
	          _this6.newProductForm.standardList = _this6.newProductForm.standardList.map(function (item) {
	            item.max_sale_num = Number(item.max_sale_num);
	            return item;
	          });
	          _this6.$http.post("/adUnderdGoods", _this6.newProductForm).then(function (response) {
	            var jsondata = response.data;
	            if (jsondata.error_no == 0) {
	              _this6.$message({
	                type: "success",
	                message: "添加成功"
	              });
	              window.location.href = "#/groundingProduct";
	            }
	          });
	        }
	      });
	    },
	
	    /** 预览商品 **/
	    prviewGoods: function prviewGoods() {
	      this.isPrviewGoods = !this.isPrviewGoods;
	      console.log(this.CarModelSelected);
	      console.log(this.newProductForm);
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
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
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

/***/ 107:
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
	  }, [_vm._v("用户中心")]), _vm._v(" "), _c('el-breadcrumb-item', [_vm._v("商品管理")]), _vm._v(" "), _c('el-breadcrumb-item', [_vm._v("新增商品")])], 1)], 1), _vm._v(" "), _c('div', {
	    staticClass: "block-white",
	    staticStyle: {
	      "padding-bottom": "80px"
	    }
	  }, [_c('div', {
	    staticClass: "newCarTypeGroup"
	  }, [_vm._v("新增商品")]), _vm._v(" "), _c('el-form', {
	    ref: _vm.newProductForm,
	    staticClass: "demo-form-inline",
	    attrs: {
	      "model": _vm.newProductForm,
	      "label-width": "100px"
	    }
	  }, [_c('el-form-item', {
	    attrs: {
	      "label": "商品名称：",
	      "prop": "goods_name",
	      "rules": [{
	          required: true,
	          message: '请输入型号',
	          trigger: 'blur'
	        },
	        {
	          min: 13,
	          max: 38,
	          message: '长度在 13 到 38 个字符',
	          trigger: 'blur'
	        }
	      ]
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "500px"
	    },
	    attrs: {
	      "minlength": 13,
	      "maxlength": 38,
	      "placeholder": "请输入商品名称（13-38字数限制）"
	    },
	    model: {
	      value: (_vm.newProductForm.goods_name),
	      callback: function($$v) {
	        _vm.newProductForm.goods_name = $$v
	      },
	      expression: "newProductForm.goods_name"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "类目：",
	      "prop": "third_category_id",
	      "rules": [{
	        required: true,
	        message: '请选择类目'
	      }]
	    }
	  }, [_c('input', {
	    directives: [{
	      name: "model",
	      rawName: "v-model",
	      value: (_vm.newProductForm.third_category_id),
	      expression: "newProductForm.third_category_id"
	    }],
	    attrs: {
	      "type": "hidden"
	    },
	    domProps: {
	      "value": (_vm.newProductForm.third_category_id)
	    },
	    on: {
	      "input": function($event) {
	        if ($event.target.composing) { return; }
	        _vm.newProductForm.third_category_id = $event.target.value
	      }
	    }
	  }), _vm._v(" "), _c('el-cascader', {
	    attrs: {
	      "options": _vm.categoryOptions,
	      "props": _vm.categoryProps,
	      "showAllLevels": false,
	      "filterable": ""
	    },
	    on: {
	      "change": _vm.handleCategoryChange,
	      "active-item-change": _vm.handleCategoryItemChange
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "品牌：",
	      "prop": "brand_id",
	      "rules": [{
	        required: true,
	        message: '请选择商品品牌'
	      }]
	    }
	  }, [_c('el-select', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "filterable": "",
	      "placeholder": "请选择商品品牌"
	    },
	    on: {
	      "change": _vm.brandChange
	    },
	    model: {
	      value: (_vm.newProductForm.brand_id),
	      callback: function($$v) {
	        _vm.newProductForm.brand_id = $$v
	      },
	      expression: "newProductForm.brand_id"
	    }
	  }, _vm._l((_vm.brandList), function(item) {
	    return _c('el-option', {
	      key: item.brand_name,
	      attrs: {
	        "label": item.brand_name,
	        "value": item.brand_id
	      }
	    })
	  }))], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "计量单位：",
	      "prop": "unit_id",
	      "rules": [{
	        required: true,
	        message: '请选择计量单位'
	      }]
	    }
	  }, [_c('el-select', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "filterable": "",
	      "placeholder": "请选择计量单位"
	    },
	    on: {
	      "change": _vm.unitChange
	    },
	    model: {
	      value: (_vm.newProductForm.unit_id),
	      callback: function($$v) {
	        _vm.newProductForm.unit_id = $$v
	      },
	      expression: "newProductForm.unit_id"
	    }
	  }, _vm._l((_vm.unitList), function(item) {
	    return _c('el-option', {
	      key: item.metadata_name,
	      attrs: {
	        "label": item.metadata_name,
	        "value": item.metadata_id
	      }
	    })
	  }))], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "占位图："
	    }
	  }, [_c('el-upload', {
	    staticClass: "upload-demo sepecil",
	    attrs: {
	      "action": "",
	      "auto-upload": false,
	      "on-change": _vm.handleAdUrlChange,
	      "on-remove": _vm.handleAdUrlRemove,
	      "file-list": _vm.adFilesList
	    }
	  }, [_c('el-button', {
	    attrs: {
	      "size": "small",
	      "type": "primary"
	    }
	  }, [_vm._v("点击上传")]), _vm._v(" "), _c('div', {
	    staticClass: "el-upload__tip",
	    slot: "tip"
	  }, [_vm._v("建议尺寸：750*68px")])], 1)], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "首页图：",
	      "prop": "show_url",
	      "rules": [{
	        required: true,
	        message: '请上传首页图'
	      }]
	    }
	  }, [_c('el-upload', {
	    staticClass: "upload-demo sepecil",
	    attrs: {
	      "action": "",
	      "auto-upload": false,
	      "on-change": _vm.handleShowUrlChange,
	      "on-remove": _vm.handleShowUrlRemove,
	      "file-list": _vm.showFilesList
	    }
	  }, [_c('el-button', {
	    attrs: {
	      "size": "small",
	      "type": "primary"
	    }
	  }, [_vm._v("点击上传")]), _vm._v(" "), _c('div', {
	    staticClass: "el-upload__tip",
	    slot: "tip"
	  }, [_vm._v("建议尺寸：360*360px")])], 1)], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "适用机型："
	    }
	  }, [(!_vm.adapt_all_models) ? _c('div', _vm._l((_vm.carModelTagList), function(tag) {
	    return _c('el-tag', {
	      key: tag.value,
	      attrs: {
	        "closable": true,
	        "close-transition": false,
	        "type": "success"
	      },
	      on: {
	        "close": function($event) {
	          _vm.handleTagClose(tag)
	        }
	      }
	    }, [_vm._v("\n\t\t\t\t\t\t\t" + _vm._s(tag.first.label) + " / " + _vm._s(tag.second.label) + " / " + _vm._s(tag.third.label) + "\n\t\t\t\t\t\t")])
	  })) : _vm._e(), _vm._v(" "), _c('el-cascader', {
	    staticStyle: {
	      "width": "250px"
	    },
	    attrs: {
	      "disabled": _vm.adapt_all_models,
	      "options": _vm.carModelOptions,
	      "value": _vm.CarModelSelected,
	      "props": _vm.carModelProps,
	      "clearable": true,
	      "showAllLevels": true,
	      "filterable": ""
	    },
	    on: {
	      "change": _vm.handleCarModelChange,
	      "active-item-change": _vm.handleCarModelItemChange
	    }
	  }), _vm._v(" "), _c('el-checkbox', {
	    on: {
	      "change": _vm.adaptChange
	    },
	    model: {
	      value: (_vm.adapt_all_models),
	      callback: function($$v) {
	        _vm.adapt_all_models = $$v
	      },
	      expression: "adapt_all_models"
	    }
	  }, [_vm._v("适配全部机型")])], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "banner图：",
	      "prop": "bannerList",
	      "rules": [{
	        required: true,
	        message: '请上传banner图 至少一张'
	      }]
	    }
	  }, [_c('el-upload', {
	    staticClass: "upload-demo sepecil",
	    attrs: {
	      "action": ""
	    }
	  }, [_c('div', {
	    staticClass: "el-upload__tip",
	    slot: "tip"
	  }, [_vm._v("建议尺寸：750*600px "), _c('span', {
	    staticStyle: {
	      "color": "#f00"
	    }
	  }, [_vm._v(" 至少一张")])])])], 1), _vm._v(" "), _c('el-form-item', {
	    staticClass: "uploadBanner",
	    attrs: {
	      "label": " "
	    }
	  }, [_c('el-upload', {
	    staticClass: "upload-demo",
	    attrs: {
	      "disabled": _vm.bannerFilesList.length >= _vm.maxFileLength,
	      "action": "",
	      "list-type": "picture-card",
	      "multiple": true,
	      "auto-upload": false,
	      "on-preview": _vm.handleBannerFilePreview,
	      "on-change": _vm.handleBannerFileChange,
	      "on-remove": _vm.handleBannerFileRemove,
	      "file-list": _vm.bannerFilesList
	    }
	  }, [_c('i', {
	    staticClass: "el-icon-plus",
	    attrs: {
	      "disabled": _vm.bannerFilesList.length >= _vm.maxFileLength
	    }
	  })])], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "商品详图：",
	      "prop": "detailList",
	      "rules": [{
	        required: true,
	        message: '请上传商品详图 至少一张'
	      }]
	    }
	  }, [_c('el-upload', {
	    staticClass: "upload-demo sepecil",
	    attrs: {
	      "action": ""
	    }
	  }, [_c('div', {
	    staticClass: "el-upload__tip",
	    slot: "tip"
	  }, [_c('span', {
	    staticStyle: {
	      "color": "#f00"
	    }
	  }, [_vm._v("至少一张")])])])], 1), _vm._v(" "), _c('el-form-item', {
	    staticClass: "uploadDetail",
	    attrs: {
	      "label": " "
	    }
	  }, [_c('el-upload', {
	    staticClass: "upload-demo",
	    attrs: {
	      "action": "",
	      "disabled": _vm.detailFilesList.length >= _vm.maxFileLength,
	      "list-type": "picture-card",
	      "multiple": true,
	      "auto-upload": false,
	      "on-preview": _vm.handleDetailFilePreview,
	      "on-change": _vm.handleDetailFileChange,
	      "on-remove": _vm.handleDetailFileRemove,
	      "file-list": _vm.detailFilesList
	    }
	  }, [_c('i', {
	    staticClass: "el-icon-plus",
	    attrs: {
	      "disabled": _vm.detailFilesList.length >= _vm.maxFileLength
	    }
	  })])], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": " "
	    }
	  }, _vm._l((_vm.newProductForm.detailList), function(item) {
	    return _c('el-input', {
	      staticStyle: {
	        "width": "148px",
	        "margin-right": "8px"
	      },
	      attrs: {
	        "type": "textarea",
	        "rows": 2,
	        "placeholder": "输入描述 (2-200字数限制)"
	      },
	      model: {
	        value: (item.pic_desc),
	        callback: function($$v) {
	          item.pic_desc = $$v
	        },
	        expression: "item.pic_desc"
	      }
	    })
	  })), _vm._v(" "), _c('el-row', [_c('el-col', {
	    attrs: {
	      "span": 8
	    }
	  }, [_c('el-form-item', {
	    attrs: {
	      "label": "客服电话：",
	      "prop": "tel",
	      "rules": [{
	        required: true,
	        message: '请填写客服电话'
	      }, {
	        max: 20,
	        message: '客服电话在20个字符以内'
	      }]
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "100%"
	    },
	    attrs: {
	      "minlength": 1,
	      "maxlength": 20,
	      "placeholder": "请填写客服电话（20字数限制）"
	    },
	    model: {
	      value: (_vm.newProductForm.tel),
	      callback: function($$v) {
	        _vm.newProductForm.tel = $$v
	      },
	      expression: "newProductForm.tel"
	    }
	  })], 1)], 1), _vm._v(" "), _c('el-col', {
	    staticStyle: {
	      "text-align": "right",
	      "line-height": "36px"
	    },
	    attrs: {
	      "span": 3
	    }
	  }, [_c('el-checkbox', {
	    on: {
	      "change": _vm.paytypeChange
	    },
	    model: {
	      value: (_vm.isPayDeposit),
	      callback: function($$v) {
	        _vm.isPayDeposit = $$v
	      },
	      expression: "isPayDeposit"
	    }
	  }, [_vm._v("预付定金")])], 1), _vm._v(" "), (_vm.isPayDeposit) ? _c('el-col', {
	    attrs: {
	      "span": 12
	    }
	  }, [_c('el-form-item', {
	    attrs: {
	      "label-width": "20px",
	      "prop": "font_money_rate",
	      "rules": [{
	        required: true,
	        message: '请填写预付定金'
	      }]
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "type": "number",
	      "min": 1,
	      "max": 100,
	      "placeholder": "定金支付比例"
	    },
	    model: {
	      value: (_vm.newProductForm.font_money_rate),
	      callback: function($$v) {
	        _vm.newProductForm.font_money_rate = $$v
	      },
	      expression: "newProductForm.font_money_rate"
	    }
	  }), _vm._v(" "), _c('span', [_vm._v("%")])], 1)], 1) : _vm._e()], 1), _vm._v(" "), _c('div', {
	    staticClass: "newCarTypeGroup"
	  }, [_vm._v("商品介绍")]), _vm._v(" "), _vm._l((_vm.newProductForm.infoList), function(item, index) {
	    return _c('el-row', [_c('el-col', {
	      attrs: {
	        "span": 8
	      }
	    }, [_c('el-form-item', {
	      attrs: {
	        "label": "名称",
	        "prop": 'infoList.' + index + '.info_title',
	        "rules": [{
	            required: true,
	            message: '请输入名称',
	            trigger: 'blur'
	          },
	          {
	            min: 1,
	            max: 5,
	            message: '长度在 1 到 5 个字符',
	            trigger: 'blur'
	          }
	        ]
	      }
	    }, [_c('el-input', {
	      staticStyle: {
	        "width": "200px"
	      },
	      attrs: {
	        "minlength": 1,
	        "maxlength": 5,
	        "placeholder": "输入（1-5字数限制）"
	      },
	      model: {
	        value: (item.info_title),
	        callback: function($$v) {
	          item.info_title = $$v
	        },
	        expression: "item.info_title"
	      }
	    })], 1)], 1), _vm._v(" "), _c('el-col', {
	      attrs: {
	        "span": 14
	      }
	    }, [_c('el-form-item', {
	      attrs: {
	        "label": "值",
	        "label-width": "40px",
	        "prop": 'infoList.' + index + '.info_content',
	        "rules": [{
	            required: true,
	            message: '请输入介绍内容',
	            trigger: 'blur'
	          },
	          {
	            min: 1,
	            max: 200,
	            message: '长度在 1 到 200 个字符',
	            trigger: 'blur'
	          }
	        ]
	      }
	    }, [_c('el-input', {
	      staticStyle: {
	        "width": "100%"
	      },
	      attrs: {
	        "type": "textarea",
	        "autosize": {
	          minRows: 1,
	          maxRows: 4
	        },
	        "minlength": 1,
	        "maxlength": 200,
	        "placeholder": "输入（1-200字数限制）"
	      },
	      model: {
	        value: (item.info_content),
	        callback: function($$v) {
	          item.info_content = $$v
	        },
	        expression: "item.info_content"
	      }
	    })], 1)], 1), _vm._v(" "), _c('el-col', {
	      attrs: {
	        "span": 2,
	        "align": "center"
	      }
	    }, [_c('el-button', {
	      attrs: {
	        "plain": true,
	        "type": "danger"
	      },
	      on: {
	        "click": function($event) {
	          _vm.deleteInfoList(item)
	        }
	      }
	    }, [_vm._v("删除")])], 1)], 1)
	  }), _vm._v(" "), _c('div', {
	    staticClass: "addParameterBtn",
	    on: {
	      "click": _vm.addInfoList
	    }
	  }, [_c('i', {
	    staticClass: "el-icon-plus"
	  }), _vm._v(" 添加商品介绍")]), _vm._v(" "), _c('div', {
	    staticClass: "newCarTypeGroup"
	  }, [_vm._v("商品规格")]), _vm._v(" "), _vm._l((_vm.newProductForm.standardList), function(item, index) {
	    return _c('el-row', [_c('el-col', {
	      attrs: {
	        "span": 10
	      }
	    }, [_c('el-form-item', {
	      attrs: {
	        "label": "名称",
	        "label-width": "60px",
	        "prop": 'standardList.' + index + '.standard_must',
	        "rules": [{
	            required: true,
	            message: '请输入名称',
	            trigger: 'blur'
	          },
	          {
	            min: 1,
	            max: 40,
	            message: '长度在 1 到 40 个字符',
	            trigger: 'blur'
	          }
	        ]
	      }
	    }, [_c('el-input', {
	      staticStyle: {
	        "width": "30%"
	      },
	      attrs: {
	        "minlength": 1,
	        "maxlength": 40,
	        "placeholder": "这个规格是必填的"
	      },
	      model: {
	        value: (item.standard_must),
	        callback: function($$v) {
	          item.standard_must = $$v
	        },
	        expression: "item.standard_must"
	      }
	    }), _vm._v(" "), _c('el-input', {
	      staticStyle: {
	        "width": "30%"
	      },
	      attrs: {
	        "maxlength": 40,
	        "placeholder": "选填规格1"
	      },
	      model: {
	        value: (item.optional_first),
	        callback: function($$v) {
	          item.optional_first = $$v
	        },
	        expression: "item.optional_first"
	      }
	    }), _vm._v(" "), _c('el-input', {
	      staticStyle: {
	        "width": "30%"
	      },
	      attrs: {
	        "maxlength": 40,
	        "placeholder": "选填规格2"
	      },
	      model: {
	        value: (item.optional_second),
	        callback: function($$v) {
	          item.optional_second = $$v
	        },
	        expression: "item.optional_second"
	      }
	    })], 1)], 1), _vm._v(" "), _c('el-col', {
	      attrs: {
	        "span": 4
	      }
	    }, [_c('el-form-item', {
	      attrs: {
	        "label": "价格",
	        "label-width": "60px",
	        "prop": 'standardList.' + index + '.price',
	        "rules": [{
	            required: true,
	            message: '请输入价格',
	            trigger: 'blur'
	          },
	          {
	            min: 1,
	            max: 5,
	            message: '长度在 1 到 10 个字符',
	            trigger: 'blur'
	          }
	        ]
	      }
	    }, [_c('el-input', {
	      staticStyle: {
	        "width": "100%"
	      },
	      attrs: {
	        "type": "number",
	        "minlength": 1,
	        "maxlength": 10,
	        "placeholder": "输入（1-10字数限制）"
	      },
	      model: {
	        value: (item.price),
	        callback: function($$v) {
	          item.price = $$v
	        },
	        expression: "item.price"
	      }
	    })], 1)], 1), _vm._v(" "), _c('el-col', {
	      attrs: {
	        "span": 5
	      }
	    }, [_c('el-form-item', {
	      attrs: {
	        "label": "最大采购量(单笔)",
	        "label-width": "150px",
	        "prop": 'standardList.' + index + '.max_sale_num',
	        "rules": [{
	          required: true,
	          message: '请输入采购量'
	        }]
	      }
	    }, [_c('el-input', {
	      staticStyle: {
	        "width": "100%"
	      },
	      attrs: {
	        "type": "number",
	        "minlength": 1,
	        "maxlength": 8,
	        "placeholder": "单笔最大采购量"
	      },
	      model: {
	        value: (item.max_sale_num),
	        callback: function($$v) {
	          item.max_sale_num = $$v
	        },
	        expression: "item.max_sale_num"
	      }
	    })], 1)], 1), _vm._v(" "), _c('el-col', {
	      attrs: {
	        "span": 3
	      }
	    }, [_c('el-form-item', {
	      attrs: {
	        "label": "指导价",
	        "label-width": "80px",
	        "prop": 'standardList.' + index + '.reference_price',
	        "rules": [{
	          required: true,
	          message: '请输入指导价'
	        }]
	      }
	    }, [_c('el-input', {
	      staticStyle: {
	        "width": "100%"
	      },
	      attrs: {
	        "type": "number",
	        "minlength": 1,
	        "maxlength": 8,
	        "placeholder": "请输入指导价"
	      },
	      model: {
	        value: (item.reference_price),
	        callback: function($$v) {
	          item.reference_price = $$v
	        },
	        expression: "item.reference_price"
	      }
	    })], 1)], 1), _vm._v(" "), _c('el-col', {
	      attrs: {
	        "span": 2,
	        "align": "center"
	      }
	    }, [_c('el-button', {
	      attrs: {
	        "plain": true,
	        "type": "danger"
	      },
	      on: {
	        "click": function($event) {
	          _vm.deleteStandardList(item)
	        }
	      }
	    }, [_vm._v("删除")])], 1)], 1)
	  }), _vm._v(" "), _c('div', {
	    staticClass: "addParameterBtn",
	    on: {
	      "click": _vm.addStandardList
	    }
	  }, [_c('i', {
	    staticClass: "el-icon-plus"
	  }), _vm._v(" 添加商品规格")])], 2)], 1), _vm._v(" "), _c('div', {
	    staticStyle: {
	      "text-align": "center",
	      "background": "#fff",
	      "z-index": "2",
	      "overflow": "hidden",
	      "position": "fixed",
	      "left": "260px",
	      "right": "0",
	      "bottom": "0",
	      "padding": "10px",
	      "border-top": "1px solid #58B7FF"
	    }
	  }, [_c('el-button', {
	    attrs: {
	      "type": "primary",
	      "size": "large"
	    },
	    on: {
	      "click": _vm.prviewGoods
	    }
	  }, [_vm._v("预览")]), _vm._v(" "), _c('el-button', {
	    attrs: {
	      "type": "warning",
	      "size": "large"
	    },
	    on: {
	      "click": _vm.adUnderdGoods
	    }
	  }, [_vm._v("待发布")]), _vm._v(" "), _c('el-button', {
	    attrs: {
	      "type": "success",
	      "size": "large"
	    },
	    on: {
	      "click": _vm.addGoods
	    }
	  }, [_vm._v("上架")])], 1), _vm._v(" "), _c('el-dialog', {
	    model: {
	      value: (_vm.dialogPreviewVisible),
	      callback: function($$v) {
	        _vm.dialogPreviewVisible = $$v
	      },
	      expression: "dialogPreviewVisible"
	    }
	  }, [_c('img', {
	    attrs: {
	      "width": "100%",
	      "src": _vm.dialogPreviewUrl,
	      "alt": ""
	    }
	  })]), _vm._v(" "), (_vm.isPrviewGoods) ? _c('div', {
	    staticClass: "privewGoodsDetail"
	  }, [_c('el-carousel', {
	    attrs: {
	      "trigger": "click",
	      "height": "225px"
	    }
	  }, _vm._l((_vm.newProductForm.bannerList), function(item) {
	    return _c('el-carousel-item', {
	      key: item,
	      attrs: {
	        "indicator-position": "none"
	      }
	    }, [(item.pic_url) ? _c('img', {
	      staticStyle: {
	        "width": "100%"
	      },
	      attrs: {
	        "src": item.pic_url
	      }
	    }) : _vm._e()])
	  })), _vm._v(" "), _c('div', {
	    staticClass: "privewGoodsName"
	  }, [_vm._v(_vm._s(_vm.newProductForm.goods_name))]), _vm._v(" "), _c('div', {
	    staticStyle: {
	      "text-align": "center"
	    }
	  }, [_c('img', {
	    staticStyle: {
	      "width": "93%",
	      "height": "35px"
	    },
	    attrs: {
	      "src": _vm.newProductForm.ad_url
	    }
	  })]), _vm._v(" "), _c('div', {
	    staticClass: "shopIntroduce"
	  }, [_c('dl', [_c('dt', [_vm._v("商品介绍")]), _vm._v(" "), _c('dd', {
	    staticClass: "viewBrand"
	  }, [_c('label', [_vm._v("品牌")]), _c('span', [_vm._v(_vm._s(_vm.newProductForm.brand_name))])]), _vm._v(" "), _vm._l((_vm.newProductForm.infoList), function(item) {
	    return _c('dd', [_c('label', [_vm._v(_vm._s(item.info_title))]), _c('span', [_vm._v(_vm._s(item.info_content))])])
	  })], 2)]), _vm._v(" "), _c('div', {
	    staticClass: "detailsNavi"
	  }, [_vm._m(0), _vm._v(" "), _c('ul', {
	    staticClass: "detailsCon"
	  }, _vm._l((_vm.newProductForm.detailList), function(item) {
	    return _c('li', [_c('img', {
	      attrs: {
	        "src": item.pic_url
	      }
	    }), _c('h5', [_vm._v(_vm._s(item.pic_desc))])])
	  }))])], 1) : _vm._e()], 1)
	},staticRenderFns: [function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
	  return _c('div', {
	    staticClass: "detailsNaviTop"
	  }, [_c('div', {
	    staticClass: "detailsNaviLeft"
	  }, [_vm._v("图文详情")])])
	}]}
	module.exports.render._withStripped = true
	if (false) {
	  module.hot.accept()
	  if (module.hot.data) {
	     require("vue-hot-reload-api").rerender("data-v-1e5276b4", module.exports)
	  }
	}

/***/ })

});
//# sourceMappingURL=43.js.map