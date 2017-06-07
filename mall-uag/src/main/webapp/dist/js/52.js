webpackJsonp([52],{

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

/***/ 38:
/***/ (function(module, exports, __webpack_require__) {

	
	/* styles */
	__webpack_require__(39)
	
	var Component = __webpack_require__(13)(
	  /* script */
	  __webpack_require__(41),
	  /* template */
	  __webpack_require__(42),
	  /* scopeId */
	  null,
	  /* cssModules */
	  null
	)
	Component.options.__file = "/Users/xiangyangli/projects/hjh/jhd/mall-uag/src/main/webapp/app/components/appImgSetting.vue"
	if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key !== "__esModule"})) {console.error("named exports are not supported in *.vue files.")}
	if (Component.options.functional) {console.error("[vue-loader] appImgSetting.vue: functional components are not supported with templates, they should use render functions.")}
	
	/* hot reload */
	if (false) {(function () {
	  var hotAPI = require("vue-hot-reload-api")
	  hotAPI.install(require("vue"), false)
	  if (!hotAPI.compatible) return
	  module.hot.accept()
	  if (!module.hot.data) {
	    hotAPI.createRecord("data-v-d6d2e232", Component.options)
	  } else {
	    hotAPI.reload("data-v-d6d2e232", Component.options)
	  }
	})()}
	
	module.exports = Component.exports


/***/ }),

/***/ 39:
/***/ (function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(40);
	if(typeof content === 'string') content = [[module.id, content, '']];
	if(content.locals) module.exports = content.locals;
	// add the styles to the DOM
	var update = __webpack_require__(29)("75f5d9b3", content, false);
	// Hot Module Replacement
	if(false) {
	 // When the styles change, update the <style> tags
	 if(!content.locals) {
	   module.hot.accept("!!../../node_modules/css-loader/index.js?sourceMap!../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-d6d2e232!../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./appImgSetting.vue", function() {
	     var newContent = require("!!../../node_modules/css-loader/index.js?sourceMap!../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-d6d2e232!../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./appImgSetting.vue");
	     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
	     update(newContent);
	   });
	 }
	 // When the module is disposed, remove the <style> tags
	 module.hot.dispose(function() { update(); });
	}

/***/ }),

/***/ 40:
/***/ (function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(28)();
	// imports
	
	
	// module
	exports.push([module.id, "\n.el-dialog__body .el-form-item__content{\n\tline-height: 0;\n}\n", "", {"version":3,"sources":["/./app/components/appImgSetting.vue?6b29b238"],"names":[],"mappings":";AAyHA;CACA,eAAA;CACA","file":"appImgSetting.vue","sourcesContent":["<template>\n\t<div>\n\t\t<!-- 面包屑 -->\n\t\t<div class=\"hjh-breadcrumb\">\n\t\t\t<el-breadcrumb separator=\"/\">\n\t\t\t\t<el-breadcrumb-item :to=\"{ path: '/' }\">用户中心</el-breadcrumb-item>\n\t\t\t\t<el-breadcrumb-item>基本管理</el-breadcrumb-item>\n\t\t\t\t<el-breadcrumb-item>APP展示图片管理</el-breadcrumb-item>\n\t\t\t</el-breadcrumb>\t\n\t\t</div>\n\t\t<!-- 面包屑 end -->\n\t\t<!-- 列表查询表单 -->\n\t\t<div class=\"block-white\">\n\t\t\t<el-button type=\"primary\" @click=\"addNew\">新增</el-button>\n\t\t</div>\n\t\t<!-- 列表查询表单 end -->\n\t\t<!-- 会员管理列表 -->\n\t\t<div class=\"block-white\">\n\t\t\t<el-table v-loading.body=\"loading\" :data=\"appimgList.result_list\" border stripe style=\"width: 100%\" max-height=\"460\">\n\t\t\t\t<el-table-column fixed label=\"序号\" width=\"70\" align=\"center\"><template scope=\"scope\">{{scope.$index+1}}</template></el-table-column>\n\t\t\t\t<el-table-column prop=\"type\" label=\"图片类型\" width=\"120\">\n\t\t\t\t\t<template scope=\"scope\">首页{{scope.row.type==1?\"占位图\":\"banner\"}}</template>\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column prop=\"sort\" label=\"顺序\" align=\"center\" width=\"80\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"banner_path\" label=\"图片\" width=\"210\">\n\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t<div @click=\"showBigImage(scope.row.banner_path)\">\n\t\t\t\t\t\t\t<img :src=\"'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+scope.row.banner_path\">点击查看大图\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</template>\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column prop=\"is_skip\" label=\"是否跳转\" align=\"center\" width=\"100\">\n\t\t\t\t\t<template scope=\"scope\">{{scope.row.is_skip==1?\"跳转\":\"不跳转\"}}</template>\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column prop=\"app_url\" label=\"跳转地址\" align=\"center\" width=\"400\" :show-overflow-tooltip=\"showtooltip\">\n\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t<a target=\"_blank\" :href=\"scope.row.app_url\">{{scope.row.app_url}}</a>\n\t\t\t\t\t</template>\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column prop=\"create_user_name\" label=\"添加人\" align=\"center\" width=\"140\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"initTime\" label=\"添加时间\" align=\"center\" width=\"180\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"update_user_name\" label=\"最后修改人\" align=\"center\" width=\"140\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"updateTime\" label=\"最后修改时间\" align=\"center\" width=\"180\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"remark\" label=\"备注\" align=\"center\" width=\"180\"></el-table-column>\n\t\t\t\t<el-table-column prop=\"status\" label=\"状态\" align=\"center\" width=\"80\">\n\t\t\t\t\t<template scope=\"scope\">{{scope.row.status==0?\"禁用\":\"启用\"}}</template>\n\t\t\t\t</el-table-column>\n\t\t\t\t<el-table-column fixed=\"right\" label=\"操作\" width=\"160\">\n\t\t\t\t\t<template scope=\"scope\">\n\t\t\t\t\t\t<el-button @click.native.prevent=\"modify(scope.$index, appimgList.result_list)\" type=\"info\">编辑</el-button>\n\t\t\t\t\t\t<el-button @click.native.prevent=\"setStatus(scope.$index, appimgList.result_list)\" type=\"danger\" >{{scope.row.status==0?\"启用\":\"禁用\"}}</el-button>\n\t\t\t\t\t</template>\n\t\t\t\t</el-table-column>\n\t\t\t</el-table>\n\t\t</div>\n\t\t<!-- 会员管理列表 end -->\n\t\t<!-- 翻页组件 -->\n\t\t<div class=\"block-white\">\n\t\t\t<el-pagination\n\t\t      @current-change=\"handleCurrentChange\"\n\t\t      :current-page=\"page\"\n\t\t      :page-size=\"limit\"\n\t\t      layout=\"total, prev, pager, next, jumper\"\n\t\t      :total=\"appimgList.total_num\">\n\t\t    </el-pagination>\n\t\t</div>\n\t\t<!-- 翻页组件 end -->\n\t\t<!-- 查看与编辑弹窗 -->\n\t\t<el-dialog :title=\"ismodify?'编辑图片展示':'新增图片展示'\" v-model=\"dialogFormVisible\" size=\"tiny\">\n\t\t\t<div style=\"height: 390px; overflow: hidden;\">\n\t\t\t\t<el-form label-position=\"right\" :label-width=\"formLabelWidth\" :model=\"formScopeDialog\">\n\t\t\t\t\t<el-form-item label=\"轮播位置\" prop=\"type\" :rules=\"{ required: true, message: '请选择轮播位置', trigger: 'blur' }\">\n\t\t\t\t\t\t<el-select v-model=\"formScopeDialog.type\" placeholder=\"请选择轮播位置\" @change=\"typeChange\" style=\"width:150px;\">\n\t\t\t\t\t\t\t<el-option label=\"首页banner\" value=\"0\"></el-option>\n\t\t\t\t\t\t\t<el-option label=\"首页占位图\" value=\"1\"></el-option>\n\t\t\t\t\t\t</el-select>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"图片上传\" prop=\"banner_path\" :rules=\"{ required: true, message: '请选择图片', trigger: 'blur' }\">\n\t\t\t\t\t\t<el-input v-model=\"formScopeDialog.banner_path\" type=\"hidden\"></el-input>\n\t\t\t\t\t\t<el-upload\n\t\t\t\t\t\t  class=\"upload-demo\"\n\t\t\t\t\t\t  action=\"https://jsonplaceholder.typicode.com/posts/\"\n\t\t\t\t\t\t  :auto-upload=\"false\"\n\t\t\t\t\t\t  :file-list=\"filesList\">\n\t\t\t\t\t\t  <el-button size=\"small\" type=\"primary\" :disabled=\"filesList.length==1\">点击上传</el-button>\n\t\t\t\t\t\t</el-upload>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"排序\" prop=\"sort\" >\n\t\t\t\t\t\t<el-input v-model=\"formScopeDialog.sort\" placeholder=\"排序\"></el-input>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"是否跳转\" prop=\"is_skip\" :rules=\"{ required: true, message: '请设置是否跳转', trigger: 'blur' }\">\n\t\t\t\t\t\t<el-select v-model=\"formScopeDialog.is_skip\" placeholder=\"请设置是否跳转\" @change=\"is_skipChange\" style=\"width:150px;\">\n\t\t\t\t\t\t\t<el-option label=\"不跳转\" value=\"0\"></el-option>\n\t\t\t\t\t\t\t<el-option label=\"跳转\" value=\"1\"></el-option>\n\t\t\t\t\t\t</el-select>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"链接地址\" >\n\t\t\t\t\t\t<el-input v-model=\"formScopeDialog.app_url\" placeholder=\"请输入链接地址\"></el-input>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t\t<el-form-item label=\"备注\">\n\t\t\t\t\t\t<el-input v-model=\"formScopeDialog.remark\" placeholder=\"输入备注\"></el-input>\n\t\t\t\t\t</el-form-item>\n\t\t\t\t</el-form>\n\t\t\t</div>\n\t\t\t<div slot=\"footer\" class=\"dialog-footer\" style=\"text-align: center;\">\n\t\t\t\t<el-button type=\"primary\" @click=\"updateDialogForm\">保 存</el-button>\n\t\t\t\t<el-button @click=\"dialogFormVisible = false\">取 消</el-button>\n\t\t\t</div>\n\t\t</el-dialog>\n\t\t<el-dialog title=\"图片展示\" v-model=\"dialogImageVisible\">\n\t\t\t<div style=\"text-align: center;\">\n\t\t\t\t<img style=\"display: inline-block; margin: 0 auto; max-width: 100%;\" :src=\"BigImageUrl\">\n\t\t\t</div>\n\t\t\t<div slot=\"footer\" class=\"dialog-footer\" style=\"text-align: center;\">\n\t\t\t\t<el-button @click=\"dialogImageVisible = false\">关 闭</el-button>\n\t\t\t</div>\n\t\t</el-dialog>\n\t\t<!-- 查看与编辑弹窗 end -->\n\t</div>\n</template>\n<style>\n\t.el-dialog__body .el-form-item__content{\n\t\tline-height: 0;\n\t}\n</style>\n<script>\n\t\n\texport default {\n\t\tdata() {\n\t\t\treturn {\n\t\t\t\tpage : 1,\t\t\t\t\t\t\t//列表初始当前页码\n\t\t\t\tlimit : 10,\t\t\t\t\t\t\t//每页列表数量\n\t\t\t\tappimgList : {},\t\t\t\t\t\t//banner图列表数据缓存\n\t\t\t\tformScopeDialog:{},\t\t\t\t\t//查看或编辑时对应的数据缓存\n\t\t\t\tdialogFormVisible:false,\t\t\t\t//查看与编辑弹窗显示状态 false为隐藏 true为显示\n\t\t\t\tdialogImageVisible:false,\t\t\t//查看大图弹窗显示状态\n\t\t\t\tformLabelWidth: '90px',\t\t\t\t//弹窗中的表单label的宽度\n\t\t\t\tismodify:false,\t\t\t\t\t\t//是否为编辑状态\n\t\t\t\tloading:false,\t\t\t\t\t\t//banner列表加载状态 true为加载中 false为加载完毕\n\t\t\t\tshowtooltip : true,\t\t\t\t\t//列表项隐藏时hover显示提示\n\t\t\t\tBigImageUrl : \"\"\t,\t\t\t\t\t//查看大图，图片链接\n\t\t\t\tfilesList : []\n\t\t\t}\n\t\t},\n\t\tmounted(){\n\t\t\tdocument.title = \"后台管理系统-APP展示图片管理\";\n\t\t\tthis.getappimgList();\n\t\t},\n\t\tmethods: {\n\t\t\t//查看大图\n\t\t\tshowBigImage(url){\n\t\t\t\tthis.BigImageUrl = 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+url;\n\t\t\t\tthis.dialogImageVisible = true;\n\t\t\t},\n\t\t\t//操作－翻页\n\t\t\thandleCurrentChange(val){\n\t\t\t\tthis.$data.page = val;\n\t\t\t\tthis.getappimgList();\n\t\t\t},\n\t\t\t//选择轮播位置回调\n\t\t\ttypeChange(val){\n\t\t\t\tvar text = val == 1 ? \"首页占位图\" : (val == 0 ? \"首页banner\" : \"\");\n\t\t\t\tsetTimeout(function(){\n\t\t\t\t\tdocument.getElementsByClassName(\"el-dialog__wrapper\")[0].getElementsByClassName('el-input__inner')[0].value = text;\n\t\t\t\t},50)\n\t\t\t\t\n\t\t\t},\n\t\t\t//设置是否跳转回调\n\t\t\tis_skipChange(val){\n\t\t\t\tvar text = val == 1 ? \"跳转\" : (val == 0 ? \"不跳转\" : \"\");\n\t\t\t\tsetTimeout(function(){\n\t\t\t\t\tdocument.getElementsByClassName(\"el-dialog__wrapper\")[0].getElementsByClassName('el-input__inner')[3].value = text;\n\t\t\t\t},50)\n\t\t\t\t\n\t\t\t},\n\t\t\tbindFileChange(){\n\t\t\t\tvar that = this;\n\t\t\t\tsetTimeout(function(){\n\t\t\t\t\t$(\".el-upload__input\").off(\"change\").on(\"change\",function(){\n\t\t\t\t\t\tthat.handleFileChange(this);\n\t\t\t\t\t\t//console.log(that.$data.filesList);\n\t\t\t\t\t});\n\t\t\t\t},80);\n\t\t\t},\n\t\t\thandleFileChange(target){\n\t\t\t\tvar that = this;\n\t\t\t\tsetTimeout(function(){\n\t\t\t\t\tvar li = $(\".el-upload-list > li\");\n\t\t\t\t\tif(li.length>1){\n\t\t\t\t\t\t$(\".el-upload-list > li\").last().prev().remove();\n\t\t\t\t\t}\n\t\t\t\t\tvar file = target.files[0];\n\t\t\t\t\tvar reader = new FileReader(); \n\t\t\t\t\treader.readAsDataURL(file);\n\t\t\t\t\treader.onload = function(e){\n\t\t\t\t\t\tthat.formScopeDialog.banner_path = this.result;\n\t\t\t\t\t\t//console.log(that.$data.filesList);\n\t\t\t\t\t}\n\t\t\t\t},50)\n\t\t\t},\n\t\t\t//新增图片项\n\t\t\taddNew(){\n\t\t\t\tthis.$data.formScopeDialog = {\r\n\t\t\t\t\ttype : \"0\",\r\n\t\t\t\t\tis_skip : \"0\"\r\n\t\t\t\t};\n\t\t\t\tthis.$data.filesList= [];\n\t\t\t\tthis.$data.dialogFormVisible = true;\n\t\t\t\tthis.$data.ismodify = false;\n\t\t\t\tthis.typeChange(0);\n\t\t\t\tthis.is_skipChange(0);\n\t\t\t\tthis.bindFileChange();\n\t\t\t},\n\t\t\t//操作－编辑\n\t\t\tmodify(index,list){\n\t\t\t\tthis.$data.formScopeDialog = list[index];\n\t\t\t\tthis.$data.dialogFormVisible = true;\n\t\t\t\tthis.$data.ismodify = true;\n\t\t\t\t\n\t\t\t\tthis.typeChange(this.$data.formScopeDialog.type);\n\t\t\t\tthis.is_skipChange(this.$data.formScopeDialog.is_skip);\n\t\t\t\tthis.bindFileChange();\n\t\t\t},\n\t\t\t//操作－确认(保存)\n\t\t\tupdateDialogForm() {\n\t\t\t\tvar api = !this.ismodify ? \"./../addBanner\" : \"./../updateBanner\";\n\t\t\t\tvar that = this,form = this.$data.formScopeDialog;\n\t\t\t\tvar param = {\n\t\t\t\t\taccess_token : localStorage.access_token,\n\t\t\t\t\tapp_url : form.app_url,\n\t\t\t\t\tbanner_id : form.banner_id,\n\t\t\t\t\tbanner_path : form.banner_path,\n\t\t\t\t\tis_skip : form.is_skip,\n\t\t\t\t\tremark : form.remark,\n\t\t\t\t\tsort : form.sort,\n\t\t\t\t\ttype : form.type\n\t\t\t\t};\n\t\t\t\tthis.$http.post(api,param).then(response=>{\n\t\t\t\t\tvar jsondata = response.data;\n\t\t\t\t\t\tthat.$message({\n\t\t\t\t \t\t\ttype: jsondata.error_no == 0 ? \"success\" : \"error\",\n\t\t\t\t \t\t\tmessage: jsondata.error_no == 0 ? \"操作成功\" : jsondata.error_info\n\t\t\t\t \t\t});\n\t\t\t\t \t\tif(jsondata.error_no == 0){\n\t\t\t\t \t\t\tthat.getappimgList();\n\t\t\t\t \t\t}\n\t\t\t\t \tthat.$data.filesList= [];\n\t\t\t\t\tthat.$data.dialogFormVisible = false;\n\t\t\t\t});\n\t\t\t},\n\t\t\t//操作－启用或禁用\n\t\t\tsetStatus(index,list){\n\t\t\t\tvar status = list[index].status == \"1\" ? \"禁用\" : \"启用\",\n\t\t\t\t\t_status = list[index].status == \"1\" ? 0 : 1;\n\t\t\t\tvar that = this,\n\t\t\t\t \tparam = {\n\t\t\t\t \t\taccess_token: localStorage.access_token,\n\t\t\t\t \t\tstatus: _status,\n\t\t\t\t \t\tbanner_id: list[index].banner_id\n\t\t\t\t \t};\n\t\t\t\t this.$confirm(\"确定要\" + status + \"该用户吗？\", '', {\n\t\t\t\t \tconfirmButtonText: '确定',\n\t\t\t\t \tcancelButtonText: '取消',\n\t\t\t\t \ttype: 'warning'\n\t\t\t\t }).then(() => {\n\t\t\t\t \tthat.$http.post('./../updateBannerStatus', param).then(response => {\n\t\t\t\t \t\tresponse = response.data;\n\t\t\t\t \t\tvar type = 'error',\n\t\t\t\t \t\t\tmsg = response.error_info;\n\t\t\t\t \t\tif(response.error_no == '0') {\n\t\t\t\t \t\t\ttype = 'success';\n\t\t\t\t \t\t\tmsg = '操作成功';\n\t\t\t\t \t\t\tthat.getappimgList();\n\t\t\t\t \t\t}\n\t\t\t\t \t\tthat.$message({\n\t\t\t\t \t\t\ttype: type,\n\t\t\t\t \t\t\tmessage: msg\n\t\t\t\t \t\t});\n\t\t\t\t \t});\n\t\t\t\t });\n\t\t\t},\n\t\t\tcheckLogout(error_no){\n\t\t\t\tif(error_no==88880020){\n\t\t\t\t\tlocation.href=\"/login\";\n\t\t\t\t}\n\t\t\t},\n\t\t\t//重置时间戳格式\n\t\t\treFormatTime(ymd,hms){\n\t\t\t\tvar reg = /\\d+/;\n\t\t\t\tif(!reg.test(ymd) || !reg.test(hms)){\n\t\t\t\t\treturn '';\n\t\t\t\t}\n\t\t\t\tymd = String(ymd),hms = String(hms);\n\t\t\t\tvar y = ymd.substring(0,4),\n\t\t\t\t\tM = ymd.substring(4,6),\n\t\t\t\t\td = ymd.substring(6,8);\n\t\t\t\tvar H = hms.substring(0,2),\n\t\t\t\t\tm = hms.substring(2,4),\n\t\t\t\t\ts = hms.substring(4,6);\n\t\t\t\treturn y+\"-\"+M+\"-\"+d+\" \"+H+\":\"+m+\":\"+s;\n\t\t\t},\n\t\t \t//获取会员列表\n\t\t\tgetappimgList() {\n\t\t\t\tvar that = this;\n\t\t \t\tvar param = {\n\t\t \t\t\tpage : this.$data.page,\n\t\t \t\t\tlimit : this.$data.limit\n\t\t \t\t};\n\t \t\t\tthat.$data.loading = true;\n\t \t\t\tthis.$http.post('./../getBannerList',param).then(response => {\n\t\t\t\t\tvar jsonData=response.data;\n\t\t\t\t\tthat.checkLogout(jsonData.error_no);\n\t\t\t\t\tif(jsonData.error_no==0){\n\t\t\t\t\t\tjsonData.result_list = jsonData.result_list.map(item=>{\n\t\t\t\t\t\t\titem.initTime = that.reFormatTime(item.init_date,item.init_time);\n\t\t\t\t\t\t\titem.updateTime = that.reFormatTime(item.update_date,item.update_time);\n\t\t\t\t\t\t\treturn item;\n\t\t\t\t\t\t});\n\t\t\t\t\t\tthat.$data.appimgList = jsonData || {};\n\t\t\t\t\t}else{\n\t\t\t\t\t\tthat.$message({\n\t\t\t\t\t\t\ttype: \"warning\",\n\t\t\t\t\t\t\tmessage: jsonData.error_info\n\t\t\t\t\t\t});\n\t\t\t\t\t}\n\t\t\t\t\tthat.$data.loading = false;\n\t\t\t\t});\n\t\t\t}\n\t\t}\n\t}\n</script>\n"],"sourceRoot":"webpack://"}]);
	
	// exports


/***/ }),

/***/ 41:
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
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
	//
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
				page: 1, //列表初始当前页码
				limit: 10, //每页列表数量
				appimgList: {}, //banner图列表数据缓存
				formScopeDialog: {}, //查看或编辑时对应的数据缓存
				dialogFormVisible: false, //查看与编辑弹窗显示状态 false为隐藏 true为显示
				dialogImageVisible: false, //查看大图弹窗显示状态
				formLabelWidth: '90px', //弹窗中的表单label的宽度
				ismodify: false, //是否为编辑状态
				loading: false, //banner列表加载状态 true为加载中 false为加载完毕
				showtooltip: true, //列表项隐藏时hover显示提示
				BigImageUrl: "", //查看大图，图片链接
				filesList: []
			};
		},
		mounted: function mounted() {
			document.title = "后台管理系统-APP展示图片管理";
			this.getappimgList();
		},
	
		methods: {
			//查看大图
			showBigImage: function showBigImage(url) {
				this.BigImageUrl = 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/' + url;
				this.dialogImageVisible = true;
			},
	
			//操作－翻页
			handleCurrentChange: function handleCurrentChange(val) {
				this.$data.page = val;
				this.getappimgList();
			},
	
			//选择轮播位置回调
			typeChange: function typeChange(val) {
				var text = val == 1 ? "首页占位图" : val == 0 ? "首页banner" : "";
				setTimeout(function () {
					document.getElementsByClassName("el-dialog__wrapper")[0].getElementsByClassName('el-input__inner')[0].value = text;
				}, 50);
			},
	
			//设置是否跳转回调
			is_skipChange: function is_skipChange(val) {
				var text = val == 1 ? "跳转" : val == 0 ? "不跳转" : "";
				setTimeout(function () {
					document.getElementsByClassName("el-dialog__wrapper")[0].getElementsByClassName('el-input__inner')[3].value = text;
				}, 50);
			},
			bindFileChange: function bindFileChange() {
				var that = this;
				setTimeout(function () {
					$(".el-upload__input").off("change").on("change", function () {
						that.handleFileChange(this);
						//console.log(that.$data.filesList);
					});
				}, 80);
			},
			handleFileChange: function handleFileChange(target) {
				var that = this;
				setTimeout(function () {
					var li = $(".el-upload-list > li");
					if (li.length > 1) {
						$(".el-upload-list > li").last().prev().remove();
					}
					var file = target.files[0];
					var reader = new FileReader();
					reader.readAsDataURL(file);
					reader.onload = function (e) {
						that.formScopeDialog.banner_path = this.result;
						//console.log(that.$data.filesList);
					};
				}, 50);
			},
	
			//新增图片项
			addNew: function addNew() {
				this.$data.formScopeDialog = {
					type: "0",
					is_skip: "0"
				};
				this.$data.filesList = [];
				this.$data.dialogFormVisible = true;
				this.$data.ismodify = false;
				this.typeChange(0);
				this.is_skipChange(0);
				this.bindFileChange();
			},
	
			//操作－编辑
			modify: function modify(index, list) {
				this.$data.formScopeDialog = list[index];
				this.$data.dialogFormVisible = true;
				this.$data.ismodify = true;
	
				this.typeChange(this.$data.formScopeDialog.type);
				this.is_skipChange(this.$data.formScopeDialog.is_skip);
				this.bindFileChange();
			},
	
			//操作－确认(保存)
			updateDialogForm: function updateDialogForm() {
				var api = !this.ismodify ? "./../addBanner" : "./../updateBanner";
				var that = this,
				    form = this.$data.formScopeDialog;
				var param = {
					access_token: localStorage.access_token,
					app_url: form.app_url,
					banner_id: form.banner_id,
					banner_path: form.banner_path,
					is_skip: form.is_skip,
					remark: form.remark,
					sort: form.sort,
					type: form.type
				};
				this.$http.post(api, param).then(function (response) {
					var jsondata = response.data;
					that.$message({
						type: jsondata.error_no == 0 ? "success" : "error",
						message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
					});
					if (jsondata.error_no == 0) {
						that.getappimgList();
					}
					that.$data.filesList = [];
					that.$data.dialogFormVisible = false;
				});
			},
	
			//操作－启用或禁用
			setStatus: function setStatus(index, list) {
				var status = list[index].status == "1" ? "禁用" : "启用",
				    _status = list[index].status == "1" ? 0 : 1;
				var that = this,
				    param = {
					access_token: localStorage.access_token,
					status: _status,
					banner_id: list[index].banner_id
				};
				this.$confirm("确定要" + status + "该用户吗？", '', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(function () {
					that.$http.post('./../updateBannerStatus', param).then(function (response) {
						response = response.data;
						var type = 'error',
						    msg = response.error_info;
						if (response.error_no == '0') {
							type = 'success';
							msg = '操作成功';
							that.getappimgList();
						}
						that.$message({
							type: type,
							message: msg
						});
					});
				});
			},
			checkLogout: function checkLogout(error_no) {
				if (error_no == 88880020) {
					location.href = "/login";
				}
			},
	
			//重置时间戳格式
			reFormatTime: function reFormatTime(ymd, hms) {
				var reg = /\d+/;
				if (!reg.test(ymd) || !reg.test(hms)) {
					return '';
				}
				ymd = String(ymd), hms = String(hms);
				var y = ymd.substring(0, 4),
				    M = ymd.substring(4, 6),
				    d = ymd.substring(6, 8);
				var H = hms.substring(0, 2),
				    m = hms.substring(2, 4),
				    s = hms.substring(4, 6);
				return y + "-" + M + "-" + d + " " + H + ":" + m + ":" + s;
			},
	
			//获取会员列表
			getappimgList: function getappimgList() {
				var that = this;
				var param = {
					page: this.$data.page,
					limit: this.$data.limit
				};
				that.$data.loading = true;
				this.$http.post('./../getBannerList', param).then(function (response) {
					var jsonData = response.data;
					that.checkLogout(jsonData.error_no);
					if (jsonData.error_no == 0) {
						jsonData.result_list = jsonData.result_list.map(function (item) {
							item.initTime = that.reFormatTime(item.init_date, item.init_time);
							item.updateTime = that.reFormatTime(item.update_date, item.update_time);
							return item;
						});
						that.$data.appimgList = jsonData || {};
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
	};

/***/ }),

/***/ 42:
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
	  }, [_vm._v("用户中心")]), _vm._v(" "), _c('el-breadcrumb-item', [_vm._v("基本管理")]), _vm._v(" "), _c('el-breadcrumb-item', [_vm._v("APP展示图片管理")])], 1)], 1), _vm._v(" "), _c('div', {
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
	      "data": _vm.appimgList.result_list,
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
	      "prop": "type",
	      "label": "图片类型",
	      "width": "120"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_vm._v("首页" + _vm._s(scope.row.type == 1 ? "占位图" : "banner"))]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "sort",
	      "label": "顺序",
	      "align": "center",
	      "width": "80"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "banner_path",
	      "label": "图片",
	      "width": "210"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_c('div', {
	          on: {
	            "click": function($event) {
	              _vm.showBigImage(scope.row.banner_path)
	            }
	          }
	        }, [_c('img', {
	          attrs: {
	            "src": 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/' + scope.row.banner_path
	          }
	        }), _vm._v("点击查看大图\n\t\t\t\t\t")])]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "is_skip",
	      "label": "是否跳转",
	      "align": "center",
	      "width": "100"
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_vm._v(_vm._s(scope.row.is_skip == 1 ? "跳转" : "不跳转"))]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "app_url",
	      "label": "跳转地址",
	      "align": "center",
	      "width": "400",
	      "show-overflow-tooltip": _vm.showtooltip
	    },
	    scopedSlots: _vm._u([{
	      key: "default",
	      fn: function(scope) {
	        return [_c('a', {
	          attrs: {
	            "target": "_blank",
	            "href": scope.row.app_url
	          }
	        }, [_vm._v(_vm._s(scope.row.app_url))])]
	      }
	    }])
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "create_user_name",
	      "label": "添加人",
	      "align": "center",
	      "width": "140"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "initTime",
	      "label": "添加时间",
	      "align": "center",
	      "width": "180"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "update_user_name",
	      "label": "最后修改人",
	      "align": "center",
	      "width": "140"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "updateTime",
	      "label": "最后修改时间",
	      "align": "center",
	      "width": "180"
	    }
	  }), _vm._v(" "), _c('el-table-column', {
	    attrs: {
	      "prop": "remark",
	      "label": "备注",
	      "align": "center",
	      "width": "180"
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
	        return [_vm._v(_vm._s(scope.row.status == 0 ? "禁用" : "启用"))]
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
	              _vm.modify(scope.$index, _vm.appimgList.result_list)
	            }
	          }
	        }, [_vm._v("编辑")]), _vm._v(" "), _c('el-button', {
	          attrs: {
	            "type": "danger"
	          },
	          nativeOn: {
	            "click": function($event) {
	              $event.preventDefault();
	              _vm.setStatus(scope.$index, _vm.appimgList.result_list)
	            }
	          }
	        }, [_vm._v(_vm._s(scope.row.status == 0 ? "启用" : "禁用"))])]
	      }
	    }])
	  })], 1)], 1), _vm._v(" "), _c('div', {
	    staticClass: "block-white"
	  }, [_c('el-pagination', {
	    attrs: {
	      "current-page": _vm.page,
	      "page-size": _vm.limit,
	      "layout": "total, prev, pager, next, jumper",
	      "total": _vm.appimgList.total_num
	    },
	    on: {
	      "current-change": _vm.handleCurrentChange
	    }
	  })], 1), _vm._v(" "), _c('el-dialog', {
	    attrs: {
	      "title": _vm.ismodify ? '编辑图片展示' : '新增图片展示',
	      "size": "tiny"
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
	      "height": "390px",
	      "overflow": "hidden"
	    }
	  }, [_c('el-form', {
	    attrs: {
	      "label-position": "right",
	      "label-width": _vm.formLabelWidth,
	      "model": _vm.formScopeDialog
	    }
	  }, [_c('el-form-item', {
	    attrs: {
	      "label": "轮播位置",
	      "prop": "type",
	      "rules": {
	        required: true,
	        message: '请选择轮播位置',
	        trigger: 'blur'
	      }
	    }
	  }, [_c('el-select', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "请选择轮播位置"
	    },
	    on: {
	      "change": _vm.typeChange
	    },
	    model: {
	      value: (_vm.formScopeDialog.type),
	      callback: function($$v) {
	        _vm.formScopeDialog.type = $$v
	      },
	      expression: "formScopeDialog.type"
	    }
	  }, [_c('el-option', {
	    attrs: {
	      "label": "首页banner",
	      "value": "0"
	    }
	  }), _vm._v(" "), _c('el-option', {
	    attrs: {
	      "label": "首页占位图",
	      "value": "1"
	    }
	  })], 1)], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "图片上传",
	      "prop": "banner_path",
	      "rules": {
	        required: true,
	        message: '请选择图片',
	        trigger: 'blur'
	      }
	    }
	  }, [_c('el-input', {
	    attrs: {
	      "type": "hidden"
	    },
	    model: {
	      value: (_vm.formScopeDialog.banner_path),
	      callback: function($$v) {
	        _vm.formScopeDialog.banner_path = $$v
	      },
	      expression: "formScopeDialog.banner_path"
	    }
	  }), _vm._v(" "), _c('el-upload', {
	    staticClass: "upload-demo",
	    attrs: {
	      "action": "https://jsonplaceholder.typicode.com/posts/",
	      "auto-upload": false,
	      "file-list": _vm.filesList
	    }
	  }, [_c('el-button', {
	    attrs: {
	      "size": "small",
	      "type": "primary",
	      "disabled": _vm.filesList.length == 1
	    }
	  }, [_vm._v("点击上传")])], 1)], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "排序",
	      "prop": "sort"
	    }
	  }, [_c('el-input', {
	    attrs: {
	      "placeholder": "排序"
	    },
	    model: {
	      value: (_vm.formScopeDialog.sort),
	      callback: function($$v) {
	        _vm.formScopeDialog.sort = $$v
	      },
	      expression: "formScopeDialog.sort"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "是否跳转",
	      "prop": "is_skip",
	      "rules": {
	        required: true,
	        message: '请设置是否跳转',
	        trigger: 'blur'
	      }
	    }
	  }, [_c('el-select', {
	    staticStyle: {
	      "width": "150px"
	    },
	    attrs: {
	      "placeholder": "请设置是否跳转"
	    },
	    on: {
	      "change": _vm.is_skipChange
	    },
	    model: {
	      value: (_vm.formScopeDialog.is_skip),
	      callback: function($$v) {
	        _vm.formScopeDialog.is_skip = $$v
	      },
	      expression: "formScopeDialog.is_skip"
	    }
	  }, [_c('el-option', {
	    attrs: {
	      "label": "不跳转",
	      "value": "0"
	    }
	  }), _vm._v(" "), _c('el-option', {
	    attrs: {
	      "label": "跳转",
	      "value": "1"
	    }
	  })], 1)], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "链接地址"
	    }
	  }, [_c('el-input', {
	    attrs: {
	      "placeholder": "请输入链接地址"
	    },
	    model: {
	      value: (_vm.formScopeDialog.app_url),
	      callback: function($$v) {
	        _vm.formScopeDialog.app_url = $$v
	      },
	      expression: "formScopeDialog.app_url"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": "备注"
	    }
	  }, [_c('el-input', {
	    attrs: {
	      "placeholder": "输入备注"
	    },
	    model: {
	      value: (_vm.formScopeDialog.remark),
	      callback: function($$v) {
	        _vm.formScopeDialog.remark = $$v
	      },
	      expression: "formScopeDialog.remark"
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
	      "click": _vm.updateDialogForm
	    }
	  }, [_vm._v("保 存")]), _vm._v(" "), _c('el-button', {
	    on: {
	      "click": function($event) {
	        _vm.dialogFormVisible = false
	      }
	    }
	  }, [_vm._v("取 消")])], 1)]), _vm._v(" "), _c('el-dialog', {
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
	  }, [_vm._v("关 闭")])], 1)])], 1)
	},staticRenderFns: []}
	module.exports.render._withStripped = true
	if (false) {
	  module.hot.accept()
	  if (module.hot.data) {
	     require("vue-hot-reload-api").rerender("data-v-d6d2e232", module.exports)
	  }
	}

/***/ })

});
//# sourceMappingURL=52.js.map