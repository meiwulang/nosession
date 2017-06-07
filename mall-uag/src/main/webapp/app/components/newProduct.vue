<template>
    <div>
    		<!-- 面包屑 -->
		<div class="hjh-breadcrumb">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item :to="{ path: '/' }">用户中心</el-breadcrumb-item>
				<el-breadcrumb-item>商品管理</el-breadcrumb-item>
				<el-breadcrumb-item>新增商品</el-breadcrumb-item>
			</el-breadcrumb>	
		</div>
		<!-- 面包屑 end -->
		<div class="block-white" style="padding-bottom:80px;">
			<div class="newCarTypeGroup">新增商品</div>
			<el-form :model="newProductForm" :ref="newProductForm" label-width="100px" class="demo-form-inline">
				<el-form-item label="商品名称："  prop="goods_name" :rules="[{required: true, message: '请输入商品名称', trigger: 'blur'},
						{ min: 13, max: 38, message: '长度在 13 到 38 个字符', trigger: 'blur' }]">
					<el-input v-model="newProductForm.goods_name" :minlength="13" :maxlength="38" placeholder="请输入商品名称（13-38字数限制）" style="width: 500px;"></el-input>
				</el-form-item>
				<el-form-item label="类目：" prop="third_category_id" :rules="[{required: true, message: '请选择类目'}]">
					<input type="hidden" v-model="newProductForm.third_category_id" />
					<el-cascader 
					:options="categoryOptions" 
					@change="handleCategoryChange" 
					@active-item-change="handleCategoryItemChange"
					:props="categoryProps"
					:showAllLevels="false"
					filterable
					>
					</el-cascader>
				</el-form-item>
				<el-form-item label="品牌：" prop="brand_id" :rules="[{required: true, message: '请选择商品品牌'}]">
					<el-select v-model="newProductForm.brand_id" filterable placeholder="请选择商品品牌" style="width: 150px;" @change="brandChange">
						<el-option v-for="item in brandList" :key="item.brand_name" :label="item.brand_name" :value="item.brand_id">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="计量单位：" prop="unit_id" :rules="[{required: true, message: '请选择计量单位'}]">
					<el-select v-model="newProductForm.unit_id" filterable placeholder="请选择计量单位" style="width: 150px;" @change="unitChange">
						<el-option v-for="item in unitList" :key="item.metadata_name" :label="item.metadata_name" :value="item.metadata_id">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="占位图：">
					<el-upload class="upload-demo sepecil" 
						action="" 
						:auto-upload="false" 
						:on-change="handleAdUrlChange" 
						:on-remove="handleAdUrlRemove" 
						:file-list="adFilesList"
					>
						<el-button size="small" type="primary">点击上传</el-button>
						<div class="el-upload__tip" slot="tip">建议尺寸：750*68px</div>
					</el-upload>
				</el-form-item>
				<el-form-item label="首页图：" prop="show_url" :rules="[{required: true, message: '请上传首页图'}]">
					<el-upload class="upload-demo sepecil" 
						action="" 
						:auto-upload="false" 
						:on-change="handleShowUrlChange" 
						:on-remove="handleShowUrlRemove" 
						:file-list="showFilesList"
					>
						<el-button size="small" type="primary">点击上传</el-button>
						<div class="el-upload__tip" slot="tip">建议尺寸：360*360px</div>
					</el-upload>
				</el-form-item>
				<el-form-item label="适用机型：">
					<div v-if="!adapt_all_models" >
						<el-tag 
							v-for="tag in carModelTagList" 
							:key="tag.value" 
							:closable="true" 
							:close-transition="false" 
							@close="handleTagClose(tag)"
							type="success">
							{{tag.first.label}} / {{tag.second.label}} / {{tag.third.label}}
						</el-tag>	
					</div>
					<el-cascader 
					:disabled="adapt_all_models"
					:options="carModelOptions" 
					@change="handleCarModelChange"
					:value="CarModelSelected"
					:props="carModelProps"
					:clearable="true"
					@active-item-change="handleCarModelItemChange"
					:showAllLevels="true"
					filterable
					style="width:250px"
					>
					</el-cascader>
					<el-checkbox v-model="adapt_all_models" @change="adaptChange">适配全部机型</el-checkbox>
					
				</el-form-item>
				<el-form-item label="banner图：" prop="bannerList" :rules="[{required: true, message: '请上传banner图 至少一张'}]">
					<el-upload action=""  class="upload-demo sepecil">
						<div class="el-upload__tip" slot="tip">建议尺寸：750*600px <span style="color: #f00;"> 至少一张</span></div>
					</el-upload>
					
				</el-form-item>
				<el-form-item label=" " class="uploadBanner">
					<el-upload class="upload-demo" 
						:disabled="bannerFilesList.length>=maxFileLength"
						action="" 
						list-type="picture-card"
						:multiple = "true"
						:auto-upload="false" 
						:on-preview="handleBannerFilePreview"
						:on-change="handleBannerFileChange" 
						:on-remove="handleBannerFileRemove"
						:file-list="bannerFilesList"
					>
						<i class="el-icon-plus" :disabled="bannerFilesList.length>=maxFileLength"></i>
					</el-upload>
				</el-form-item>
				<el-form-item label="商品详图：" prop="detailList" :rules="[{required: true, message: '请上传商品详图 至少一张'}]">
					<el-upload action=""  class="upload-demo sepecil">
						<div class="el-upload__tip" slot="tip"><span style="color: #f00;">至少一张</span></div>
					</el-upload>
					
				</el-form-item>
				<el-form-item label=" " class="uploadDetail">
					<el-upload class="upload-demo" 
						action="" 
						:disabled="detailFilesList.length>=maxFileLength"
						list-type="picture-card"
						:multiple = "true"
						:auto-upload="false" 
						:on-preview="handleDetailFilePreview"
						:on-change="handleDetailFileChange" 
						:on-remove="handleDetailFileRemove" 
						:file-list="detailFilesList"
					>
						<i class="el-icon-plus" :disabled="detailFilesList.length>=maxFileLength"></i>
					</el-upload>
				</el-form-item>
				<el-form-item label=" ">
					<el-input v-for="item in newProductForm.detailList" type="textarea" :rows="2" placeholder="输入描述 (2-200字数限制)" v-model="item.pic_desc" style="width:148px;margin-right:8px;"></el-input>
				</el-form-item>
				<el-row>
					<el-col :span="8">
						<el-form-item label="客服电话：" prop="tel" :rules="[{required: true, message: '请填写客服电话'},{max:20, message: '客服电话在20个字符以内'}]">
							<el-input v-model="newProductForm.tel" :minlength="1" :maxlength="20" placeholder="请填写客服电话（20字数限制）" style="width: 100%;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="3" style="text-align:right; line-height:36px;">
						<el-checkbox v-model="isPayDeposit" @change="paytypeChange">预付定金</el-checkbox>
					</el-col>
					<el-col :span="12" v-if="isPayDeposit">
						
						<el-form-item label-width="20px" prop="font_money_rate" :rules="[{required: true, message: '请填写预付定金'}]">
							<el-input v-model="newProductForm.font_money_rate" type="number" :min="1" :max="100" placeholder="定金支付比例" style="width: 150px;"></el-input>
							<span>%</span>
						</el-form-item>
					</el-col>
				</el-row>
				<div class="newCarTypeGroup">商品介绍</div>
				<el-row v-for="(item,index) in newProductForm.infoList">
					<el-col :span="8">
						<el-form-item label="名称" :prop="'infoList.'+index+'.info_title'" :rules="[
					      { required: true, message: '请输入名称', trigger: 'blur' },
					      { min: 1, max: 5, message: '长度在 1 到 5 个字符', trigger: 'blur' }
					    ]">
							<el-input v-model="item.info_title" :minlength="1" :maxlength="5" placeholder="输入（1-5字数限制）" style="width: 200px;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="14">
						<el-form-item label="值" label-width="40px" :prop="'infoList.'+index+'.info_content'" :rules="[
					      { required: true, message: '请输入介绍内容', trigger: 'blur' },
					      { min: 1, max: 200, message: '长度在 1 到 200 个字符', trigger: 'blur' }
					    ]">
							<el-input v-model="item.info_content" type="textarea" :autosize="{ minRows: 1, maxRows: 4}" :minlength="1" :maxlength="200" placeholder="输入（1-200字数限制）" style="width: 100%;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="2" align="center">
						<el-button @click="deleteInfoList(item)" :plain="true" type="danger">删除</el-button>
					</el-col>
				</el-row>
				<div class="addParameterBtn" @click="addInfoList"><i class="el-icon-plus"></i> 添加商品介绍</div>
				<div class="newCarTypeGroup">商品规格</div>
				<el-row v-for="(item,index) in newProductForm.standardList">
					<el-col :span="10">
						<el-form-item label="名称" label-width="60px" :prop="'standardList.'+index+'.standard_must'" :rules="[
					      { required: true, message: '请输入名称', trigger: 'blur' },
					      { min: 1, max: 40, message: '长度在 1 到 40 个字符', trigger: 'blur' }
					    ]">
							<el-input v-model="item.standard_must" :minlength="1" :maxlength="40" placeholder="这个规格是必填的" style="width: 30%;"></el-input>
							<el-input v-model="item.optional_first" :maxlength="40" placeholder="选填规格1" style="width: 30%;"></el-input>
							<el-input v-model="item.optional_second" :maxlength="40" placeholder="选填规格2" style="width: 30%;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="4">
						<el-form-item label="价格" label-width="60px" :prop="'standardList.'+index+'.price'" :rules="[
					      { required: true, message: '请输入价格', trigger: 'blur' },
					      { min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur' }
					    ]">
							<el-input type="number" v-model="item.price" :minlength="1" :maxlength="10" placeholder="输入（1-10字数限制）" style="width: 100%;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="5">
						<el-form-item label="最大采购量(单笔)" label-width="150px" :prop="'standardList.'+index+'.max_sale_num'" :rules="[
					      { required: true, message: '请输入采购量' }
					    ]">
							<el-input type="number" v-model="item.max_sale_num" :minlength="1" :maxlength="8" placeholder="单笔最大采购量" style="width: 100%;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="3">
						<el-form-item label="指导价" label-width="80px" :prop="'standardList.'+index+'.reference_price'">
							<el-input type="number" v-model="item.reference_price" :minlength="1" :maxlength="8" placeholder="请输入指导价" style="width: 100%;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="2" align="center">
						<el-button @click="deleteStandardList(item)" :plain="true" type="danger">删除</el-button>
					</el-col>
				</el-row>
				<div class="addParameterBtn" @click="addStandardList"><i class="el-icon-plus"></i> 添加商品规格</div>

			</el-form>
				

		</div>

		<div style="text-align:center; background:#fff; z-index:2; overflow:hidden; position:fixed; left:260px; right:0; bottom:0; padding:10px; border-top:1px solid #58B7FF;">
			<el-button type="primary" size="large" @click="prviewGoods">预览</el-button>
			<el-button type="warning" size="large" :disabled="addGoodsEnabled" @click="adUnderdGoods">待发布</el-button>
			<el-button type="success" size="large" :disabled="addGoodsEnabled" @click="addGoods">上架</el-button>
		</div>
		<el-dialog v-model="dialogPreviewVisible">
			<img width="100%" :src="dialogPreviewUrl" alt="">
		</el-dialog>
		<div class="privewGoodsDetail" v-if="isPrviewGoods">
			<el-carousel trigger="click" height="225px">
				<el-carousel-item v-for="item in newProductForm.bannerList" :key="item" indicator-position="none" >
					<img v-if="item.pic_url" :src="item.pic_url" style="width:100%">
				</el-carousel-item>
			</el-carousel>
			<div class="privewGoodsName">{{newProductForm.goods_name}}</div>
			<div style="text-align:center;">
				<img :src="newProductForm.ad_url" style="width:93%; height:35px;">
			</div>
			<div class="shopIntroduce">
				<dl>
					<dt>商品介绍</dt>
					<dd class="viewBrand"><label>品牌</label><span>{{newProductForm.brand_name}}</span></dd>
					<dd v-for="item in newProductForm.infoList"><label>{{item.info_title}}</label><span>{{item.info_content}}</span></dd>
				</dl>
			</div>
			<div class="detailsNavi">
				<div class="detailsNaviTop">
					<div class="detailsNaviLeft">图文详情</div>
				</div>
				<ul class="detailsCon">
					<li v-for="item in newProductForm.detailList"><img :src="item.pic_url"><h5>{{item.pic_desc}}</h5></li>
				</ul>
			</div>
		</div>
    </div>
</template>
<style>
.privewGoodsDetail{
	width: 335px;
	height: 568px;
	overflow: hidden;
	overflow-y: auto;
	position: fixed;
	border: 1px solid #58B7FF; box-shadow: 0 0 10px #999;
	background-color: #ffffff;
	top:114px;
	right: 30px;
}
	.newCarTypeGroup{
		height: 60px;
		line-height: 60px;
		font-family: "微软雅黑";
		font-size: 18px;
		color: #1D8CE0;
	}
	.addParameterBtn{
		height: 55px;
	    font-size: 14px;
	    margin: 20px 20px 20px 0;
	    text-align: center;
	    color: #58B7FF;
	    border: 1px dashed #58B7FF;
	    border-radius: 3px;
	    line-height: 55px;
	    cursor: pointer;
	}
	.addParameterBtn:hover{
		color: #20A0FF;
	    border: 1px dashed #20A0FF;
	}
	.sepecil .el-upload{
		float: left; margin-right: 10px;
	}
	.sepecil .el-upload__tip{
		float: left; margin: 0;
	}
	.sepecil .el-upload-list{
		clear: both;
		width: 500px;
	}
	.el-tag{ margin-right: 10px; }
	.addParameterBtn{
		height: 55px;
	    font-size: 14px;
	    margin: 20px 20px 20px 0;
	    text-align: center;
	    color: #58B7FF;
	    border: 1px dashed #58B7FF;
	    border-radius: 3px;
	    line-height: 55px;
	    cursor: pointer;
	}
	.addParameterBtn:hover{
		color: #20A0FF;
	    border: 1px dashed #20A0FF;
	}
.el-carousel__indicators{
	display: none;
}
.addBtnHide .el-upload{
	display: none;
}
.el-upload-list li:nth-child(6) + .is-ready{
	display: none;
}
</style>
<script>
	import Common from './../js/common';
    export default{
        data(){
            return{
                newProductForm : {
                	goods_name : "",			//商品名称
                	brand_id : "",				//品牌id
                	brand_name : "",			//品牌名称
                	unit_id : "",				//计量单位id
                	unit_name : "",				//计量单位name
                	ad_url : "",				//占位图 base64
                	show_url : "",				//首页图 base64
                	third_category_id : "",		//三级类目id
                	third_category_name : "",	//三级类目name
                	adapt_all_models : 0,		//适配全部机型 0为否 1为选中
                	bannerList : [],			//banner图列表
                	detailList : [],			//商品详图列表
                	infoList : [],				//商品介绍列表
                	standardList : [],			//商品规格列表
                	pay_type : 0,
                	carModelList : []
                },
                categoryProps : {		//类目 显示属性设置
                	value: 'value',
                	children: 'children'
                },
                categoryOptions : [],	//类目 级联列表
                brandList : [],			//品牌列表
                unitList : [],			//计量单位列表
                adFilesList : [],		//占位图文件列表
                showFilesList : [],		//首页图文件列表
                carModelTagList : [],	//
                carModelProps : {		//机型 显示属性设置
                	value: 'value',
                	children: 'children'
                },
                carModelOptions : [],	//机型 级联列表
                bannerFilesList : [],	//banner图文件列表
                detailFilesList : [],	//详情图文件列表

                dialogPreviewVisible : false,	//
                dialogPreviewUrl : "",			//
                isPayDeposit : false,			//
                adapt_all_models : false,		//
                isPrviewGoods : false,
                CarModelSelected : [],
                maxFileLength : 6,
                addGoodsEnabled : false
            }
        },
        created(){
        		document.title = "后台管理系统-新增商品";
        		this.getBrandList();
        		this.getUnitList();
        		this.getCategoryList();
        		this.getCarModelList();
        },
        mounted(){

        	
        },
        methods : {
        		/** 获取商品品牌列表 **/
        		getBrandList(){
        			this.$http.post("/goodsbrand/queryforweb",{status:1,limit:10000000}).then(response=>{
						var jsondata = response.data;
						this.brandList = jsondata.error_no == 0 ? jsondata.result_list : [];
					});
        		},
        		/** 获取计量单位列表 **/
        		getUnitList(){
        			this.$http.post("/queryMetadata",{status:1,limit:10000000,type:0}).then(response=>{
						var jsondata = response.data;
						this.unitList = jsondata.error_no == 0 ? jsondata.result_list : [];
					});
        		},
        		/** 获取类目列表 **/
        		getCategoryList(){
        			this.$http.post("/json/900507",{status:1,limit:10000000}).then(response=>{
						var jsondata = response.data;
						var options = jsondata.error_no == 0 ? jsondata.result_list : [];
						this.categoryOptions = options.map(item=>{
							return {
								value : item.category_id,
								label : item.category_name,
								children : []
							}
						});
						return;
						var that = this;
						this.categoryOptions.map(function(item){
							that.getCategoryChildren(item);
						});
					});
        		},
        		handleCategoryItemChange(val){ console.log(val);
        			var len = val.length,
        				that = this;
        			var param = {
			        	//fatherId : item.value,
			        	limit : 100000000,
			        	status : 1,
			        	webUse : true
			        };
        			this.categoryOptions.map(first=>{
        				if(len==1){
        					if(first.value==val[0] && !first.children.length){
        						param.fatherId = first.value;
        						that.getCategoryChildren(param,first,true);
        					}
        				}else if(len==2){
        					if(first.value==val[0]){
        						first.children.map(second=>{
        							if(second.value==val[1] && !second.children.length){
        								param.fatherId = second.value;
        								that.getCategoryChildren(param,second);
        							}
        						})
        					}
        				}
        			});
        		},
        		/** 获取类目列表下子级列表 **/
        		getCategoryChildren(param,target,bool){
        			this.$http.post("/json/900515",param).then(response=>{
        				var jsondata = response.data;
        				var list = jsondata.error_no == 0 ? jsondata.result_list : [];
        				target.children = list.map(item=>{
					        var obj = {
								value : item.category_id,
								label : item.category_name
							};
							if(bool){
								obj.children = [];
							}
							return obj;
					    });
        			});
        		},
        		/** 类目级联选择器change钩子函数 **/
        		handleCategoryChange(val){ console.log(val);
        			var first_category_id,first_category_name,second_category_id,second_category_name,
        				third_category_id,third_category_name;
        			this.categoryOptions.map(first=>{

        				if(first.value==val[0]){
        					first_category_id = val[0];
        					first_category_name = first.label;

        					if(first.children && val[1]){
        						first.children.map(second=>{

        							if(second.value==val[1]){
        								second_category_id = val[1];
        								second_category_name = second.label;
        								if(second.children && val[2]){
        									second.children.map(third=>{
        										if(third.value==val[2]){
        											third_category_id = val[2];
        											third_category_name = third.label;
        										}
        									})
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
        		getCarModelList(){
        			this.$http.post("/carbrand/queryforweb",{status:1,limit:10000000}).then(response=>{
						var jsondata = response.data;
						var options = jsondata.error_no == 0 ? jsondata.result_list : [];
						this.carModelOptions = options.map(item=>{
							return {
								value : item.brand_id,
								label : item.brand_name,
								children : []
							}
						});
					});
        		},
        		handleCarModelItemChange(val){ console.log(val);
        			var len = val.length;
        			var url = len == 1 ? "/json/901009" : "/queryCarModelListWeb",
        				param = {
	        				brand_id : val[0],
	        				limit : 100000000,
	        				status : 1
	        			};
	        		var that = this;
	        		if(len==1){
	        			this.carModelOptions.map(first=>{
	        				if(first.value == val[0] && !first.children.length){
	        					that.$http.post(url,param).then(response=>{
	        						var jsondata = response.data;
        							var list = jsondata.error_no == 0 ? jsondata.result_list : [];
        							first.children = list.map(function(item){
		        						return {
											value : item.metadata_id,
											label : item.car_type,
											children : []
										}
		        					});
	        					});
	        				}
        				})
	        		}else if(len==2){
	        			this.carModelOptions.map(first=>{
	        				if(first.value == val[0]){
	        					first.children.map(second=>{
	        						if(second.value == val[1] && !second.children.length){
	        							param.metadata_id = val[1];
	        							that.$http.post(url,param).then(response=>{
			        						var jsondata = response.data;
		        							var list = jsondata.error_no == 0 ? jsondata.result_list : [];
		        							second.children = list.map(function(item){
				        						return {
													value : item.car_model_is,
													label : item.car_model_name
												}
				        					});
			        					});
	        						}
	        					});
	        				}
        				})
	        		}
        		},
        		
        		/** 适用机型 change钩子函数 **/
        		handleCarModelChange(val){
        			this.CarModelSelected = [];
        			//console.log(val);
        			var that = this;
        			this.carModelOptions.map(first=>{
        				if(first.value == val[0]){
        					first.children.map(second=>{
        						if(second.value==val[1]){
        							second.children.map(third=>{
        								if(third.value==val[2]){
        									that.carModelTagList.push({
        										first : {label:first.label,value:first.value},
        										second : {label:second.label,value:second.value},
        										third : {label:third.label,value:third.value}
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
        		handleTagClose(tag){
        			//console.log(tag);
        			//console.log(this.carModelTagList);
        			var that = this;
        			var index = this.carModelTagList.indexOf(tag);
        			this.carModelTagList = this.carModelTagList.filter(function(item,i){
        				return index !== i;
        			});
        			this.carModelOptions.map(first=>{
        				if(first.value == tag.first.value){
        					first.children.map(second=>{
        						if(second.value==tag.second.value){
        							second.children.map(third=>{
        								if(third.value==tag.third.value){
        									delete third.disabled;
        									that.newProductForm.carModelList = that.newProductForm.carModelList.filter(item=>{
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
        		brandChange(val){
        			var result = this.brandList.filter(function(item){ return item.brand_id==val; })[0];
        			this.newProductForm.brand_name = result ? result.brand_name : "";
        		},
        		/** 计量单位 change钩子函数 **/
        		unitChange(val){
        			var result = this.unitList.filter(function(item){ return item.metadata_id==val; })[0];
        			this.newProductForm.unit_name = result ? result.metadata_name : "";
        		},
        		/** 占位图 change钩子函数 **/
        		handleAdUrlChange(_file,_filelist){
        			this.adFilesList = [_filelist[_filelist.length-1]];
        			var that = this;
        			this.compressImg(_file.raw,function(result){
						that.newProductForm.ad_url = result;
						$(".sepecil .el-upload-list > li").last().prev().remove();
        			})
        		},
        		/** 移除占位图 钩子函数 **/
        		handleAdUrlRemove(_file,_filelist){
        			this.adFilesList = _filelist;
        			if(!_filelist){
        				this.newProductForm.ad_url = "";
        			}
        		},
        		/** 首页图 change钩子函数 **/
        		handleShowUrlChange(_file,_filelist){
        			this.showFilesList = [_filelist[_filelist.length-1]];
					var that = this;
					this.compressImg(_file.raw,function(result){
						that.newProductForm.show_url = result;
						$(".sepecil .el-upload-list > li").last().prev().remove();
        			})
        		},
        		/** 首页图移除 钩子函数 **/
        		handleShowUrlRemove(_file,_filelist){
        			this.showFilesList = _filelist;
        			if(!_filelist){
        				this.newProductForm.show_url = "";
        			}
        		},
        		/** 选择或者取消选择 适配全部机型 **/
        		adaptChange(){
        			this.newProductForm.adapt_all_models = this.adapt_all_models ? 1 : 0;
        		},
        		/** 设置支付方式 0为全额支付 1为定金支付 **/
        		paytypeChange(){
        			this.newProductForm.pay_type = this.isPayDeposit ? 1 : 0;
        		},
        		/** banner图预览 **/
        		handleBannerFilePreview(file,filelist){
        			$(".uploadBanner .el-upload__input").trigger("click");
        			this.replaceBanner = true;
        			var that = this;
        			this.replaceBannerTarget = file;
        		},
        		/** 选择bannner图 **/
        		handleBannerFileChange(_file,_filelist){
        			if(this.bannerFilesList.length>=6 && !this.replaceBanner){return;}
        			var that = this;
        			if(!this.replaceBanner){
        				this.bannerFilesList.push(_file);
	        			this.newProductForm.bannerList = [];
	        			this.bannerFilesList.map((item,index)=>{
	        				that.compressImg(item.raw,function(result){
								that.newProductForm.bannerList[index] = {pic_url:result,uid:item.uid};
		        			})
	        			})
        			}else{
        				this.newProductForm.bannerList = [];
        				this.bannerFilesList = this.bannerFilesList.map((item,index)=>{
        					if(item.uid == that.replaceBannerTarget.uid){
        						item = _file;
        					}
        					that.compressImg(item.raw,function(result){
        						that.newProductForm.bannerList[index] = {pic_url:result,uid:item.uid};
        					})
        					return item;
        				})
        				this.replaceBanner = false;
        			}

        		},
        		/** 移除banner图 **/
        		handleBannerFileRemove(_file,_filelist){
        			this.bannerFilesList = _filelist;
        			this.newProductForm.bannerList = this.newProductForm.bannerList.filter(item=>{
        				return item.uid != _file.uid;
        			});
        		},
        		/** 商品详图预览 **/
        		handleDetailFilePreview(file,filelist){
        			$(".uploadDetail .el-upload__input").trigger("click");
        			this.replaceDetail = true;
        			var that = this;
        			this.replaceDetailTarget = file;
        		},
        		/** 添加商品详图 **/
        		handleDetailFileChange(file,filelist){
        			if(this.detailFilesList.length>=6 && !this.replaceDetail){return;}
        			var that = this;
        			if(!this.replaceDetail){
        				this.detailFilesList.push(file);
        				this.compressImg(file.raw,function(result){
        					that.newProductForm.detailList.push({pic_url:result,uid:file.uid,pic_desc:""});
        				});

        			}else{
        				//this.newProductForm.detailList = [];
        				this.detailFilesList = this.detailFilesList.map((item,index)=>{
        					if(item.uid == that.replaceDetailTarget.uid){
        						item = file;
        						var _item = that.newProductForm.detailList[index];
        						that.compressImg(item.raw,function(result){
	        						that.newProductForm.detailList[index] = {pic_url:result,uid:item.uid,pic_desc:_item.pic_desc};
	        					})
        					}
        					
        					return item;
        				})
        				this.replaceDetail = false;
        			}
        			//console.log(this.newProductForm.detailList.length);

        		},
        		/** 商品详图删除 **/
        		handleDetailFileRemove(file,filelist){
        			this.detailFilesList = filelist;
        			this.newProductForm.detailList = this.newProductForm.detailList.filter(item=>{
        				return item.uid != file.uid;
        			});

        		},
        		/** 添加商品介绍 **/
        		addInfoList(){
        			this.newProductForm.infoList.push({info_title:"",info_content:""});
        		},
        		/** 删除商品介绍 **/
        		deleteInfoList(item){
        			var index = this.newProductForm.infoList.indexOf(item);
			        if (index !== -1) {
			        	this.newProductForm.infoList.splice(index, 1)
			        }
        		},
        		/** 添加商品规格 **/
        		addStandardList(){
					this.newProductForm.standardList.push({
						standard_must:"",
						optional_first:"",
						optional_second:"",
						max_sale_num:"",
						price : "",
						store_num : 999999
					});
        		},
        		/** 删除商品规格 **/
        		deleteStandardList(item){
        			var index = this.newProductForm.standardList.indexOf(item);
			        if (index !== -1) {
			        	this.newProductForm.standardList.splice(index, 1)
			        }
        		},
        		/** 图片压缩 **/
        		compressImg(file,cb){
        			var reader = new FileReader(); 
					reader.readAsDataURL(file);
					reader.onload = function(e){
						var img = new Image();
							img.src = this.result;
							img.onload = function(){
								cb(Common.compressImg(img,0.8,"image/jpeg"))
							}
					}
        		},
        		/** 上架操作 **/
        		addGoods(){
        			this.submit("/addGoods");
        		},
        		/** 待发布操作 **/
        		adUnderdGoods(){
        			this.submit("/adUnderdGoods");
        		},
        		submit(api){
        			this.$refs[this.newProductForm].validate((valid) => {
        				var bool = this.validateForm();
        				if(valid && bool){
        					this.newProductForm.standardList = this.newProductForm.standardList.map(function(item){
        						item.max_sale_num = Number(item.max_sale_num);
        						return item;
        					});
        					this.addGoodsEnabled = true;
        					this.$http.post(api,this.newProductForm).then(response=>{
        						var jsondata = response.data;
        						if(jsondata.error_no==0){
        							this.$message({
										type: "success",
										message: "添加成功"
									});
									window.location.href = "#/prepareProduct";
        						}else{
        							this.$message({
										type: "error",
										message: jsondata.error_on + ":"+ jsondata.error_info
									});
        						}
								this.addGoodsEnabled = false;
        					})
        				}
        			})
        		},
        		validateForm(){
        			if(this.newProductForm.font_money_rate <0 || this.newProductForm.font_money_rate >100){
        				this.$message({
							type: "error",
							message: "预付定金数字限制1-100"
						});
						return;
        			}
        			var result = this.newProductForm.standardList.filter(item=>{
        				return (item.standard_must.length + item.optional_first.length + item.optional_second.length) > 40;
        			});
        			if(result.length){
        				this.$message({
							type: "error",
							message: "商品规格三个字段长度之和不能超过40字符"
						});
						return;
        			}
        			return true;
        		},
        		/** 预览商品 **/
        		prviewGoods(){
        			this.isPrviewGoods = !this.isPrviewGoods;
        			console.log(this.CarModelSelected);
        			console.log(this.newProductForm);
        		}
        }
    }
</script>
