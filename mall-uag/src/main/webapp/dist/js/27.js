webpackJsonp([27],[
/* 0 */,
/* 1 */,
/* 2 */,
/* 3 */,
/* 4 */,
/* 5 */,
/* 6 */,
/* 7 */,
/* 8 */,
/* 9 */,
/* 10 */,
/* 11 */,
/* 12 */,
/* 13 */
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
/* 14 */,
/* 15 */,
/* 16 */,
/* 17 */,
/* 18 */,
/* 19 */,
/* 20 */,
/* 21 */,
/* 22 */,
/* 23 */,
/* 24 */,
/* 25 */
/***/ (function(module, exports, __webpack_require__) {

	
	/* styles */
	__webpack_require__(26)
	
	var Component = __webpack_require__(13)(
	  /* script */
	  __webpack_require__(31),
	  /* template */
	  __webpack_require__(32),
	  /* scopeId */
	  null,
	  /* cssModules */
	  null
	)
	Component.options.__file = "/Users/xiangyangli/projects/hjh/jhd/mall-uag/src/main/webapp/app/components/newCarType.vue"
	if (Component.esModule && Object.keys(Component.esModule).some(function (key) {return key !== "default" && key !== "__esModule"})) {console.error("named exports are not supported in *.vue files.")}
	if (Component.options.functional) {console.error("[vue-loader] newCarType.vue: functional components are not supported with templates, they should use render functions.")}
	
	/* hot reload */
	if (false) {(function () {
	  var hotAPI = require("vue-hot-reload-api")
	  hotAPI.install(require("vue"), false)
	  if (!hotAPI.compatible) return
	  module.hot.accept()
	  if (!module.hot.data) {
	    hotAPI.createRecord("data-v-50083965", Component.options)
	  } else {
	    hotAPI.reload("data-v-50083965", Component.options)
	  }
	})()}
	
	module.exports = Component.exports


/***/ }),
/* 26 */
/***/ (function(module, exports, __webpack_require__) {

	// style-loader: Adds some css to the DOM by adding a <style> tag
	
	// load the styles
	var content = __webpack_require__(27);
	if(typeof content === 'string') content = [[module.id, content, '']];
	if(content.locals) module.exports = content.locals;
	// add the styles to the DOM
	var update = __webpack_require__(29)("9aff15be", content, false);
	// Hot Module Replacement
	if(false) {
	 // When the styles change, update the <style> tags
	 if(!content.locals) {
	   module.hot.accept("!!../../node_modules/css-loader/index.js?sourceMap!../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-50083965!../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./newCarType.vue", function() {
	     var newContent = require("!!../../node_modules/css-loader/index.js?sourceMap!../../node_modules/vue-loader/lib/style-rewriter.js?id=data-v-50083965!../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./newCarType.vue");
	     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
	     update(newContent);
	   });
	 }
	 // When the module is disposed, remove the <style> tags
	 module.hot.dispose(function() { update(); });
	}

/***/ }),
/* 27 */
/***/ (function(module, exports, __webpack_require__) {

	exports = module.exports = __webpack_require__(28)();
	// imports
	
	
	// module
	exports.push([module.id, "\n.newCarTypeGroup{\n\theight: 60px;\n\tline-height: 60px;\n\tfont-family: \"\\5FAE\\8F6F\\96C5\\9ED1\";\n\tfont-size: 18px;\n\tcolor: #1D8CE0;\n}\n.addParameterBtn{\n\theight: 55px;\n    font-size: 14px;\n    margin: 20px 20px 20px 0;\n    text-align: center;\n    color: #58B7FF;\n    border: 1px dashed #58B7FF;\n    border-radius: 3px;\n    line-height: 55px;\n    cursor: pointer;\n}\n.addParameterBtn:hover{\n\tcolor: #20A0FF;\n    border: 1px dashed #20A0FF;\n}\n", "", {"version":3,"sources":["/./app/components/newCarType.vue?7c40b90a"],"names":[],"mappings":";AAmEA;CACA,aAAA;CACA,kBAAA;CACA,oCAAA;CACA,gBAAA;CACA,eAAA;CACA;AACA;CACA,aAAA;IACA,gBAAA;IACA,yBAAA;IACA,mBAAA;IACA,eAAA;IACA,2BAAA;IACA,mBAAA;IACA,kBAAA;IACA,gBAAA;CACA;AACA;CACA,eAAA;IACA,2BAAA;CACA","file":"newCarType.vue","sourcesContent":["<template>\n\t<div>\n\t\t<!-- 面包屑 -->\n\t\t<div class=\"hjh-breadcrumb\">\n\t\t\t<el-breadcrumb separator=\"/\">\n\t\t\t\t<el-breadcrumb-item :to=\"{ path: '/' }\">用户中心</el-breadcrumb-item>\n\t\t\t\t<el-breadcrumb-item>机型管理</el-breadcrumb-item>\n\t\t\t\t<el-breadcrumb-item>新增机型</el-breadcrumb-item>\n\t\t\t</el-breadcrumb>\t\n\t\t</div>\n\t\t<!-- 面包屑 end -->\n\t\t<!-- 列表查询表单 -->\n\t\t<div class=\"block-white\">\n\t\t\t<div class=\"newCarTypeGroup\">新增机型</div>\n\t\t\t<el-form :model=\"newCarTypeForm\" :ref=\"newCarTypeForm\" :rules=\"rules\" label-width=\"100px\" class=\"demo-form-inline\">\n\t\t\t\t\n\t\t\t\t<el-form-item label=\"机型品牌\"  prop=\"carmodelBrand\" :rules=\"{required: true, message: '请选择机型品牌', trigger: 'blur,change'}\">\n\t\t\t\t\t<el-select v-model=\"newCarTypeForm.carmodelBrand\" filterable placeholder=\"请选择机型品牌\" @change=\"brandChange\" style=\"width: 200px;\">\n\t\t\t\t\t\t<el-option v-for=\"item in brandlist\" :key=\"item.brand_id\" :label=\"item.brand_name\" :value=\"item.brand_id\">\n\t\t\t\t\t\t</el-option>\n\t\t\t\t\t</el-select>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"类型\"  prop=\"carMetadata\" :rules=\"{required: true, message: '请选择类型', trigger: 'blur,change'}\">\n\t\t\t\t\t<el-select v-model=\"newCarTypeForm.carMetadata\" placeholder=\"请选择类型\" @change=\"metaChange\" style=\"width: 200px;\">\n\t\t\t\t\t\t<el-option v-for=\"item in metalist\" :key=\"item.metadata_id\" :label=\"item.metadata_name\" :value=\"item.metadata_id\">\n\t\t\t\t\t\t</el-option>\n\t\t\t\t\t</el-select>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"型号\"  prop=\"carModelNams\" :rules=\"[{required: true, message: '请输入型号', trigger: 'blur'},\n\t\t\t\t\t\t{ min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }]\">\n\t\t\t\t\t<el-input v-model=\"newCarTypeForm.carModelNams\" :minlength=\"2\" :maxlength=\"10\" placeholder=\"请输入型号（2-10字数限制）\" style=\"width: 200px;\"></el-input>\n\t\t\t\t</el-form-item>\n\t\t\t\t<el-form-item label=\"\" >\n\t\t\t\t\t<el-checkbox v-model=\"checked\">APP显示</el-checkbox>\n\t\t\t\t</el-form-item>\n\t\t\t</el-form>\n\t\t\t<div class=\"newCarTypeGroup\">参数设置</div>\n\t\t\t\n\t\t\t<el-form label-width=\"100px\" :model=\"addParamForm\" :ref=\"addParamForm\" class=\"demo-form-inline\" style=\"width: 660px;\">\n\t\t\t\t<el-row v-for=\"(parameter,index) in addParamForm.carParameters\">\n\t\t\t\t\t<el-col :span=\"11\">\n\t\t\t\t\t\t<el-form-item label=\"型号\" :prop=\"'carParameters.'+index+'.car_params_name'\" :rules=\"[\n\t\t\t\t      { required: true, message: '请输入型号', trigger: 'blur' },\n\t\t\t\t      { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }\n\t\t\t\t    ]\">\n\t\t\t\t\t\t\t<el-input v-model=\"parameter.car_params_name\" :minlength=\"2\" :maxlength=\"50\" placeholder=\"输入（2-50字数限制）\" style=\"width: 200px;\"></el-input>\n\t\t\t\t\t\t</el-form-item>\n\t\t\t\t\t</el-col>\n\t\t\t\t\t<el-col :span=\"10\">\n\t\t\t\t\t\t<el-form-item label=\"值\"  label-width=\"40px\">\n\t\t\t\t\t\t\t<el-input v-model=\"parameter.car_params_value\" :minlength=\"2\" :maxlength=\"50\" placeholder=\"输入（2-50字数限制）\" style=\"width: 200px;\"></el-input>\n\t\t\t\t\t\t</el-form-item>\n\t\t\t\t\t</el-col>\n\t\t\t\t\t<el-col :span=\"2\" align=\"center\">\n\t\t\t\t\t\t<el-button @click=\"deleteParam(parameter)\" :plain=\"true\" type=\"danger\">删除</el-button>\n\t\t\t\t\t</el-col>\n\t\t\t\t</el-row>\n\t\t\t\t<div class=\"addParameterBtn\" @click=\"addCarTypeParameter\"><i class=\"el-icon-plus\"></i> 添加参数</div>\n\t\t\t</el-form>\n\t\t\t<div class=\"block-white\">\n\t\t\t\t<el-button type=\"primary\" @click=\"addCarModel(1)\">保存并启用</el-button>\n\t\t\t\t<el-button type=\"primary\" @click=\"addCarModel(0)\">保存并禁用</el-button>\n\t\t\t</div>\n\t\t</div>\n\t</div>\n</template>\n<style>\n\t.newCarTypeGroup{\n\t\theight: 60px;\n\t\tline-height: 60px;\n\t\tfont-family: \"微软雅黑\";\n\t\tfont-size: 18px;\n\t\tcolor: #1D8CE0;\n\t}\n\t.addParameterBtn{\n\t\theight: 55px;\n\t    font-size: 14px;\n\t    margin: 20px 20px 20px 0;\n\t    text-align: center;\n\t    color: #58B7FF;\n\t    border: 1px dashed #58B7FF;\n\t    border-radius: 3px;\n\t    line-height: 55px;\n\t    cursor: pointer;\n\t}\n\t.addParameterBtn:hover{\n\t\tcolor: #20A0FF;\n\t    border: 1px dashed #20A0FF;\n\t}\n</style>\n<script>\n\t\n\texport default {\n\t\tdata() {\n\t\t\treturn {\n\t\t\t\t//新增机型表单\n\t\t\t\tnewCarTypeForm : {\n\t\t\t\t\tcarmodelBrand : '',\t\t//机型品牌\n\t\t\t\t\tcarMetadata : '',\t\t//类型\n\t\t\t\t\tcarModelNams : ''\t\t//型号\n\t\t\t\t},\n\t\t\t\t//保存时提交的表单\n\t\t\t\tsubmitForm : {\n\t\t\t\t\taccess_token : localStorage.access_token\n\t\t\t\t},\n\t\t\t\t//是否在APP显示\n\t\t\t\tchecked : false,\n\t\t\t\t//验证规则\n\t\t\t\trules : {\n\t\t\t\t\t//机型品牌的验证规则\n\t\t\t\t\tcarmodelBrand : [\n\t\t\t\t\t\t{required: true, message: '请选择机型品牌', trigger: 'blur,change'}\n\t\t\t\t\t],\n\t\t\t\t\t//类型验证规则\n\t\t\t\t\tcarMetadata : [\n\t\t\t\t\t\t{required: true, message: '请选择类型', trigger: 'blur,change'}\n\t\t\t\t\t],\n\t\t\t\t\t//型号的验证规则\n\t\t\t\t\tcarModelNams : [\n\t\t\t\t\t\t{required: true, message: '请输入型号', trigger: 'blur'},\n\t\t\t\t\t\t{ min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }\n\t\t\t\t\t]\n\t\t\t\t},\n\t\t\t\t//品牌列表\n\t\t\t\tbrandlist : [],\n\t\t\t\t//类型列表\n\t\t\t\tmetalist : [],\n\t\t\t\t//参数设置表单\n\t\t\t\taddParamForm: {\n\t\t        \t\tcarParameters : []\n\t\t        },\n\t\t        api : \"./../addCarModel\"\n\t\t\t}\n\t\t},\n\t\tcreated(){\n\t\t\tvar that = this;\n\t\t\t//获取品牌列表\n\t\t\tthat.$http.post(\"./../carbrand/queryforweb\",{limit:10000,page:1,status:1}).then(response =>{\n\t\t\t\tvar data = response.data;\n\t\t\t\tif(data.error_no == 0){\n\t\t\t\t\tthat.$data.brandlist = data.result_list;\n\t\t\t\t}\n\t\t\t});\n\t\t\t//获取类型列表\n\t\t\tthat.$http.post(\"./../queryMetadata\",{limit:10000,page:1,status:1,type:1}).then(response =>{\n\t\t\t\tvar data = response.data;\n\t\t\t\tif(data.error_no == 0){\n\t\t\t\t\tthat.$data.metalist = data.result_list;\n\t\t\t\t}\n\t\t\t});\n\t\t},\n\t\tmounted(){\n\t\t\tdocument.title = \"后台管理系统-新增机型\";\n\t\t},\n\t\tmethods: {\n\t\t\t//切换机型品牌后回调\n\t\t\tbrandChange(val){\n\t\t\t\tvar obj = this.$data.brandlist.filter(function(item){\n\t\t\t\t\treturn item.brand_id == val;\n\t\t\t\t})[0];\n\t\t\t\tdocument.getElementsByClassName('el-input__inner')[0].value = obj.brand_name;\n\t\t\t\tthis.$data.submitForm.brand_id = obj.brand_id;\n\t\t\t\tthis.$data.submitForm.brand_name = obj.brand_name;\n\t\t\t},\n\t\t\t//切换类型后回调\n\t\t\tmetaChange(val){\n\t\t\t\tvar obj = this.$data.metalist.filter(function(item){\n\t\t\t\t\treturn item.metadata_id == val;\n\t\t\t\t})[0];\n\t\t\t\tdocument.getElementsByClassName('el-input__inner')[1].value = obj.metadata_name;\n\t\t\t\tthis.$data.submitForm.metadata_id = obj.metadata_id;\n\t\t\t\tthis.$data.submitForm.metadata_name = obj.metadata_name;\n\t\t\t},\n\t\t\t//添加参数事件\n\t\t\taddCarTypeParameter(){\n\t\t\t\tthis.$data.addParamForm.carParameters.push({\n\t\t\t\t\tcar_params_name : '',\n\t\t\t\t\tcar_params_value : ''\n\t\t\t\t});\n\t\t\t},\n\t\t\t//删除参数行\n\t\t\tdeleteParam(item){\n\t\t\t\tvar index = this.addParamForm.carParameters.indexOf(item);\n\t\t        if (index !== -1) {\n\t\t          this.addParamForm.carParameters.splice(index, 1)\n\t\t        }\n\t\t\t},\n\t\t\t//保存\n\t\t\taddCarModel(status){\n\t\t\t\tvar that = this;\n\t\t\t\tthat.$refs[that.addParamForm].validate((valid) => {\n\t\t\t\t\tif (valid) {\n\t\t\t\t\t\tthat.$refs[that.newCarTypeForm].validate((valid1) => {\n\t\t\t\t\t\t\tif (valid1) {\n\t\t\t\t\t\t\t\tif(!that.$data.newCarTypeForm.carmodelBrand){\n\t\t\t\t\t\t\t\t\tthat.$message({\n\t\t\t\t\t\t\t\t\t\ttype: \"error\",\n\t\t\t\t\t\t\t\t\t\tmessage: \"请输入机型品牌\"\n\t\t\t\t\t\t\t\t\t});\n\t\t\t\t\t\t\t\t\treturn;\n\t\t\t\t\t\t\t\t}\n\t\t\t\t\t\t\t\tif(!that.$data.newCarTypeForm.carMetadata){\n\t\t\t\t\t\t\t\t\tthat.$message({\n\t\t\t\t\t\t\t\t\t\ttype: \"error\",\n\t\t\t\t\t\t\t\t\t\tmessage: \"请选择类型\"\n\t\t\t\t\t\t\t\t\t});\n\t\t\t\t\t\t\t\t\treturn;\n\t\t\t\t\t\t\t\t}\n\t\t\t\t\t\t\t\tif(!that.$data.newCarTypeForm.carModelNams){\n\t\t\t\t\t\t\t\t\tthat.$message({\n\t\t\t\t\t\t\t\t\t\ttype: \"error\",\n\t\t\t\t\t\t\t\t\t\tmessage: \"请输入型号\"\n\t\t\t\t\t\t\t\t\t});\n\t\t\t\t\t\t\t\t\treturn;\n\t\t\t\t\t\t\t\t}\n\t\t\t\t\t\t\t\tif(that.$data.newCarTypeForm.carModelNams.length>10){\n\t\t\t\t\t\t\t\t\tthat.$message({\n\t\t\t\t\t\t\t\t\t\ttype: \"error\",\n\t\t\t\t\t\t\t\t\t\tmessage: \"型号长度为2-10个字符\"\n\t\t\t\t\t\t\t\t\t});\n\t\t\t\t\t\t\t\t\treturn;\n\t\t\t\t\t\t\t\t}\n\t\t\t\t\t\t\t\tthat.$data.submitForm.car_models_name = that.$data.newCarTypeForm.carModelNams;\n\t\t\t\t\t\t\t\tthat.$data.submitForm.carParamsList = that.$data.addParamForm.carParameters;\n\t\t\t\t\t\t\t\tthat.$data.submitForm.app_show = that.$data.checked ? 1 : 0;\n\t\t\t\t\t\t\t\tthat.$data.submitForm.status = status;\n\t\t\t\t\t\t\t\tthat.$http.post(that.api,that.$data.submitForm).then(response => {\n\t\t\t\t\t\t\t\t\tvar data = response.data;\n\t\t\t\t\t\t\t\t\tthat.$message({\n\t\t\t\t\t\t\t\t\t\ttype: data.error_no ==0 ? \"success\" : \"error\",\n\t\t\t\t\t\t\t\t\t\tmessage: data.error_no ==0 ? \"保存成功\" : data.error_info\n\t\t\t\t\t\t\t\t\t});\n\t\t\t\t\t\t\t\t\tif(data.error_no ==0){\n\t\t\t\t\t\t\t\t\t\tlocation.href = \"#/carManagement\";\n\t\t\t\t\t\t\t\t\t}\n\t\t\t\t\t\t\t\t});\n\t\t\t\t\t\t\t}\n\t\t\t\t\t\t});\n\t\t\t\t\t}\n\t\t\t\t});\n\t\t\t\t\n\t\t\t}\n\t\t}\n\t}\n</script>\n"],"sourceRoot":"webpack://"}]);
	
	// exports


/***/ }),
/* 28 */
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
/* 29 */
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
/* 30 */
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
/* 31 */
/***/ (function(module, exports) {

	'use strict';
	
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
	
	
	exports.default = {
		data: function data() {
			return {
				//新增机型表单
				newCarTypeForm: {
					carmodelBrand: '', //机型品牌
					carMetadata: '', //类型
					carModelNams: '' //型号
				},
				//保存时提交的表单
				submitForm: {
					access_token: localStorage.access_token
				},
				//是否在APP显示
				checked: false,
				//验证规则
				rules: {
					//机型品牌的验证规则
					carmodelBrand: [{ required: true, message: '请选择机型品牌', trigger: 'blur,change' }],
					//类型验证规则
					carMetadata: [{ required: true, message: '请选择类型', trigger: 'blur,change' }],
					//型号的验证规则
					carModelNams: [{ required: true, message: '请输入型号', trigger: 'blur' }, { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }]
				},
				//品牌列表
				brandlist: [],
				//类型列表
				metalist: [],
				//参数设置表单
				addParamForm: {
					carParameters: []
				},
				api: "./../addCarModel"
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
			document.title = "后台管理系统-新增机型";
		},
	
		methods: {
			//切换机型品牌后回调
			brandChange: function brandChange(val) {
				var obj = this.$data.brandlist.filter(function (item) {
					return item.brand_id == val;
				})[0];
				document.getElementsByClassName('el-input__inner')[0].value = obj.brand_name;
				this.$data.submitForm.brand_id = obj.brand_id;
				this.$data.submitForm.brand_name = obj.brand_name;
			},
	
			//切换类型后回调
			metaChange: function metaChange(val) {
				var obj = this.$data.metalist.filter(function (item) {
					return item.metadata_id == val;
				})[0];
				document.getElementsByClassName('el-input__inner')[1].value = obj.metadata_name;
				this.$data.submitForm.metadata_id = obj.metadata_id;
				this.$data.submitForm.metadata_name = obj.metadata_name;
			},
	
			//添加参数事件
			addCarTypeParameter: function addCarTypeParameter() {
				this.$data.addParamForm.carParameters.push({
					car_params_name: '',
					car_params_value: ''
				});
			},
	
			//删除参数行
			deleteParam: function deleteParam(item) {
				var index = this.addParamForm.carParameters.indexOf(item);
				if (index !== -1) {
					this.addParamForm.carParameters.splice(index, 1);
				}
			},
	
			//保存
			addCarModel: function addCarModel(status) {
				var that = this;
				that.$refs[that.addParamForm].validate(function (valid) {
					if (valid) {
						that.$refs[that.newCarTypeForm].validate(function (valid1) {
							if (valid1) {
								if (!that.$data.newCarTypeForm.carmodelBrand) {
									that.$message({
										type: "error",
										message: "请输入机型品牌"
									});
									return;
								}
								if (!that.$data.newCarTypeForm.carMetadata) {
									that.$message({
										type: "error",
										message: "请选择类型"
									});
									return;
								}
								if (!that.$data.newCarTypeForm.carModelNams) {
									that.$message({
										type: "error",
										message: "请输入型号"
									});
									return;
								}
								if (that.$data.newCarTypeForm.carModelNams.length > 10) {
									that.$message({
										type: "error",
										message: "型号长度为2-10个字符"
									});
									return;
								}
								that.$data.submitForm.car_models_name = that.$data.newCarTypeForm.carModelNams;
								that.$data.submitForm.carParamsList = that.$data.addParamForm.carParameters;
								that.$data.submitForm.app_show = that.$data.checked ? 1 : 0;
								that.$data.submitForm.status = status;
								that.$http.post(that.api, that.$data.submitForm).then(function (response) {
									var data = response.data;
									that.$message({
										type: data.error_no == 0 ? "success" : "error",
										message: data.error_no == 0 ? "保存成功" : data.error_info
									});
									if (data.error_no == 0) {
										location.href = "#/carManagement";
									}
								});
							}
						});
					}
				});
			}
		}
	};

/***/ }),
/* 32 */
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
	  }, [_vm._v("用户中心")]), _vm._v(" "), _c('el-breadcrumb-item', [_vm._v("机型管理")]), _vm._v(" "), _c('el-breadcrumb-item', [_vm._v("新增机型")])], 1)], 1), _vm._v(" "), _c('div', {
	    staticClass: "block-white"
	  }, [_c('div', {
	    staticClass: "newCarTypeGroup"
	  }, [_vm._v("新增机型")]), _vm._v(" "), _c('el-form', {
	    ref: _vm.newCarTypeForm,
	    staticClass: "demo-form-inline",
	    attrs: {
	      "model": _vm.newCarTypeForm,
	      "rules": _vm.rules,
	      "label-width": "100px"
	    }
	  }, [_c('el-form-item', {
	    attrs: {
	      "label": "机型品牌",
	      "prop": "carmodelBrand",
	      "rules": {
	        required: true,
	        message: '请选择机型品牌',
	        trigger: 'blur,change'
	      }
	    }
	  }, [_c('el-select', {
	    staticStyle: {
	      "width": "200px"
	    },
	    attrs: {
	      "filterable": "",
	      "placeholder": "请选择机型品牌"
	    },
	    on: {
	      "change": _vm.brandChange
	    },
	    model: {
	      value: (_vm.newCarTypeForm.carmodelBrand),
	      callback: function($$v) {
	        _vm.newCarTypeForm.carmodelBrand = $$v
	      },
	      expression: "newCarTypeForm.carmodelBrand"
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
	      "label": "类型",
	      "prop": "carMetadata",
	      "rules": {
	        required: true,
	        message: '请选择类型',
	        trigger: 'blur,change'
	      }
	    }
	  }, [_c('el-select', {
	    staticStyle: {
	      "width": "200px"
	    },
	    attrs: {
	      "placeholder": "请选择类型"
	    },
	    on: {
	      "change": _vm.metaChange
	    },
	    model: {
	      value: (_vm.newCarTypeForm.carMetadata),
	      callback: function($$v) {
	        _vm.newCarTypeForm.carMetadata = $$v
	      },
	      expression: "newCarTypeForm.carMetadata"
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
	      "prop": "carModelNams",
	      "rules": [{
	          required: true,
	          message: '请输入型号',
	          trigger: 'blur'
	        },
	        {
	          min: 2,
	          max: 10,
	          message: '长度在 2 到 10 个字符',
	          trigger: 'blur'
	        }
	      ]
	    }
	  }, [_c('el-input', {
	    staticStyle: {
	      "width": "200px"
	    },
	    attrs: {
	      "minlength": 2,
	      "maxlength": 10,
	      "placeholder": "请输入型号（2-10字数限制）"
	    },
	    model: {
	      value: (_vm.newCarTypeForm.carModelNams),
	      callback: function($$v) {
	        _vm.newCarTypeForm.carModelNams = $$v
	      },
	      expression: "newCarTypeForm.carModelNams"
	    }
	  })], 1), _vm._v(" "), _c('el-form-item', {
	    attrs: {
	      "label": ""
	    }
	  }, [_c('el-checkbox', {
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
	          _vm.deleteParam(parameter)
	        }
	      }
	    }, [_vm._v("删除")])], 1)], 1)
	  }), _vm._v(" "), _c('div', {
	    staticClass: "addParameterBtn",
	    on: {
	      "click": _vm.addCarTypeParameter
	    }
	  }, [_c('i', {
	    staticClass: "el-icon-plus"
	  }), _vm._v(" 添加参数")])], 2), _vm._v(" "), _c('div', {
	    staticClass: "block-white"
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
	  }, [_vm._v("保存并禁用")])], 1)], 1)])
	},staticRenderFns: []}
	module.exports.render._withStripped = true
	if (false) {
	  module.hot.accept()
	  if (module.hot.data) {
	     require("vue-hot-reload-api").rerender("data-v-50083965", module.exports)
	  }
	}

/***/ })
]);
//# sourceMappingURL=27.js.map