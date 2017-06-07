<template>
    <div>
    		<!-- 面包屑 -->
		<div class="hjh-breadcrumb">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item :to="{ path: '/' }">用户中心</el-breadcrumb-item>
				<el-breadcrumb-item>内容管理</el-breadcrumb-item>
				<el-breadcrumb-item :to="{ path: '/activityManagement' }">专题管理</el-breadcrumb-item>
				<el-breadcrumb-item>商品管理</el-breadcrumb-item>
			</el-breadcrumb>	
		</div>
		<!-- 面包屑 end -->
		<div class="block-white">
			<el-tabs v-model="activeName" @tab-click="handleTabClick">
				<el-tab-pane label="已选商品" name="first"></el-tab-pane>
				<el-tab-pane label="新增商品" name="second" v-if="!action"></el-tab-pane>
			</el-tabs>
		</div>
		<!-- 查询表单 -->
		<div class="block-white">
			<el-form :inline="true" :model="searchForm" ref="searchForm" class="demo-form-inline searchForm">
				<el-form-item label="商品编号:">
					<el-input v-model="searchForm.goods_id" placeholder="商品编号" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item label="商品品牌:">
					<el-select v-model="searchForm.brand_name" filterable placeholder="商品品牌" style="width: 200px;">
						<el-option v-for="item in brandlist" :key="item.brand_id" :label="item.brand_name" :value="item.brand_name">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="商品类目:">
					<el-select v-model="searchForm.third_category_id" filterable placeholder="商品类目" style="width: 200px;">
						<el-option v-for="item in categorylist" :key="item.cid" :label="item.cname" :value="item.cid">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="商品标题:">
					<el-input v-model="searchForm.goods_name" placeholder="商品标题" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item label="上架时间:">
					<el-date-picker v-model="datepickerValue" type="datetimerange" :picker-options="pickerOptions" @change="datepickerchange" placeholder="选择时间范围" align="right">
					</el-date-picker>
				</el-form-item>
				<el-form-item label="规格:">
					<el-input placeholder="模糊匹配" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button class="searchBtn" type="primary" @click="submitForm('searchForm')">搜索</el-button>
					<el-button @click="resetForm('searchForm')">清除</el-button>
				</el-form-item>
			</el-form>
		</div>
		<!-- 查询表单 end -->
		<div class="block-white">
			<el-button type="danger" @click="deleteGoods()" :disabled="!selectcount" v-if="isnotnew && !action">删除商品</el-button>
			<el-button type="primary" @click="addGoods()" :disabled="!selectcount" v-if="!isnotnew">新增商品</el-button>
		</div>
		<!-- 订单列表 -->
		<div class="block-white">
			<el-table v-loading.body="loading" :data="GoodsList.result_list" 
				@selection-change="handleSelectionChange" 
				ref="multipleTable"
				border stripe style="width: 100%" max-height="460">
				<el-table-column fixed type="selection" width="55" class="hjhcheckbox" v-if="!action">
					
				</el-table-column>
				<el-table-column label="序号" width="70" align="center"><template scope="scope">{{scope.$index+1}}</template></el-table-column>
				<el-table-column label="商品信息" align="center">
					<el-table-column prop="show_url" label="商品图片" width="150">
						<template scope="scope">
							<img :src="'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+scope.row.show_url" />
						</template>
					</el-table-column>
					<el-table-column prop="goods_name" label="商品名称" width="250" :show-overflow-tooltip="showtooltip">
						<template scope="scope">
							<a @click="checkProduct(scope.$index, GoodsList.result_list)">{{scope.row.goods_name}}</a>
						</template>
					</el-table-column>
					<el-table-column prop="goods_id" label="商品编号" width="200" :show-overflow-tooltip="showtooltip"></el-table-column>
					<el-table-column prop="brand_name" label="品牌" width="150" :show-overflow-tooltip="showtooltip"></el-table-column>
					<el-table-column prop="third_category_name" label="类目" width="150" :show-overflow-tooltip="showtooltip"></el-table-column>
				</el-table-column>
				<el-table-column prop="standardList" label="规格/价格" width="300" :show-overflow-tooltip="showtooltip">
					<template scope="scope">
						<div v-for="item in scope.row.standardList">
							<div style="overflow: hidden;">
								<span style="float: left;">{{item.standard_must}}|{{item.optional_first}}|{{item.optional_second}}</span>
								<span style="float: right;"><span style="color: #FD8F00;">{{item.price}}</span>/{{item.unit_name}}</span>
							</div>
						</div>
					</template>
				</el-table-column>
				<el-table-column prop="standardList" label="已售" width="100" align="center" :show-overflow-tooltip="showtooltip">
					<template scope="scope">
						<div v-for="item in scope.row.standardList">{{item.sale_num || 0}}</div>
					</template>
				</el-table-column>
				<el-table-column prop="standardList" label="库存" width="100" align="center" :show-overflow-tooltip="showtooltip">
					<template scope="scope">
						<div v-for="item in scope.row.standardList">{{item.store_num || 0}}</div>
					</template>
				</el-table-column>
				<el-table-column prop="initTime" label="创建时间" width="180" align="center" :show-overflow-tooltip="showtooltip">
				</el-table-column>
				<el-table-column prop="create_user_name" label="创建人" width="120" align="center" :show-overflow-tooltip="showtooltip">
					
				</el-table-column>
				<el-table-column prop="updateTime" label="上架时间" width="180" align="center" :show-overflow-tooltip="showtooltip">
				</el-table-column>
				<el-table-column prop="update_user_name" label="最后操作人" width="120" :show-overflow-tooltip="showtooltip"></el-table-column>
			</el-table>
		</div>
		<!-- 订单列表 end -->
		<!-- 翻页组件 -->
		<div class="block-white">
			<el-pagination
		      @current-change="handleCurrentChange"
		      :current-page="page"
		      :page-size="limit"
		      layout="total, prev, pager, next, jumper"
		      :total="GoodsList.total_num">
		    </el-pagination>
		</div>
		<!-- 翻页组件 end -->
		<el-dialog title="图片展示" v-model="dialogImageVisible">
			<div style="text-align: center;">
				<img style="display: inline-block; margin: 0 auto; max-width: 100%;" :src="BigImageUrl">
			</div>
			<div slot="footer" class="dialog-footer" style="text-align: center;">
				<el-button @click="dialogImageVisible = false">关 闭</el-button>
			</div>
		</el-dialog>
		<el-dialog title="商品信息" v-model="dialogGoodsDetailVisible">
			<div class="goodsDetail" style="height: 450px; overflow: hidden; overflow-y: auto;">
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">
						<em style="color: #FF0000;">*</em>商品名称
					</el-col>
					<el-col :span="20">{{goodsDetail.goods_name}}</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">
						<em style="color: #FF0000;">*</em>类目
					</el-col>
					<el-col :span="20">{{goodsDetail.third_category_name}}</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">
						<em style="color: #FF0000;">*</em>品牌
					</el-col>
					<el-col :span="20">{{goodsDetail.brand_name}}</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">
						<em style="color: #FF0000;">*</em>计量单位
					</el-col>
					<el-col :span="20">{{goodsDetail.unit_name}}</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">
						<em style="color: #FF0000;">*</em>占位图
					</el-col>
					<el-col :span="20">
						<img v-if="goodsDetail.ad_url" style="display: inline-block; width: 80px; height: 30px;" :src="ossUrl+goodsDetail.ad_url">
						<span v-if="!goodsDetail.ad_url">无</span>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">
						<em style="color: #FF0000;">*</em>适用机型
					</el-col>
					<el-col :span="20">
						<span class="carModelList" v-for="item in goodsDetail.carModelList">
							{{item.car_brand_name}} | {{item.car_type}} | {{item.car_models_name}}
						</span>
						<div v-if="goodsDetail.adapt_all_models==1">适用全部机型</div>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">
						<em style="color: #FF0000;">*</em>banner图
					</el-col>
					<el-col :span="20">
						<ul class="banner-list">
							<li v-for="item in goodsDetail.bannerList">
								<img :src="ossUrl+item.banner_url">
							</li>
						</ul>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">
						<em style="color: #FF0000;">*</em>商品详图
					</el-col>
					<el-col :span="20">
						<ul class="detail-list">
							<li v-for="item in goodsDetail.detailList">
								<img :src="ossUrl+item.detail_url">
								<span>{{item.pic_desc}}</span>
							</li>
						</ul>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">
						<em style="color: #FF0000;">*</em>客服电话
					</el-col>
					<el-col :span="20">{{goodsDetail.tel}}</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">
						<em style="color: #FF0000;">*</em>商品参数
					</el-col>
					<el-col :span="20">
						<ul class="param-list">
							<li v-for="item in goodsDetail.infoList">
								<span><em>*</em>名称<span>{{item.info_title}}</span></span>
								<span><em>*</em>值<span>{{item.info_content}}</span></span>
							</li>
						</ul>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">
						<em style="color: #FF0000;">*</em>商品规格
					</el-col>
					<el-col :span="20">
						<ul class="standard-list">
							<li v-for="item in goodsDetail.standardList">
								<span><em>*</em>名称<span>{{item.standard_must}}</span><span>{{item.optional_first}}</span><span>{{item.optional_second}}</span></span>
								<span><em>*</em>价格<span>{{item.price}}</span></span>
								<span><em>*</em>库存<span>{{item.store_num}}</span></span>
							</li>
						</ul>
					</el-col>
				</el-row>
			</div>
			<div slot="footer" class="dialog-footer" style="text-align: center;">
				<el-button @click="dialogGoodsDetailVisible = false">关 闭</el-button>
			</div>
		</el-dialog>
		
    </div>
</template>
<style>
	.cell a{
		cursor: pointer;
	}
	.goodsDetail .el-row{
		padding: 5px 0;
		overflow: hidden;
		border: 1px solid #58B7FF;
		margin: 10px 0;
		line-height: 24px;
	}
	.goodsDetail .el-row em{
		color: #FF0000;
	}
	.carModelList{
		width: 33.33%; float: left; line-height: 24px;
	}
	ol,ul,li{
		list-style: none; padding: 0; margin: 0;
	}
	.banner-list li{
		float: left; margin-right: 10px; width: 80px; height: 80px; border-radius: 4px; overflow: hidden;
	}
	
	.banner-list li img{
		width: 100%; height: 100%; display: block;
	}
	.detail-list li{
		float: left; margin-right: 10px; width: 80px;overflow: hidden;
	}
	.detail-list li img{
		width: 80px; height: 80px; display: block; border-radius: 4px;
	}
	.detail-list li span{
		display: block; line-height: 20px; font-size: 12px;
	}
	.param-list li >span{
		display: inline-block; width: 48%;
	}
	.param-list li >span span{
		margin-left: 10px;
	}
	.standard-list li >span{
		display: inline-block; width: 28%;
	}
	.standard-list li >span:first-child{
		width: 42%;
	}
	.standard-list li >span span{
		margin-left: 10px;
	}
</style>
<script>
	import Common from './../js/common';
    export default{
        data(){
            return{
                activeName : "first",						//tab标签页定位标识
				searchForm: this.getDeaultSearchForm(),		//搜索选项表单信息
				activityId: this.$route.params.id,			//活动id
				getGoodsListApi : "/json/901208",			//获取已选商品列表api
				BigImageUrl : "",							//查看大图图片链接
				action : "",
				datepickerValue: [],								//上架之间 [开始时间，结束时间]
				brandlist: [],								//品牌列表
				GoodsList : {},								//商品列表
				isnotnew :true,								//true为已选 false为新增
				loading : false,								//列表加载状态
				showtooltip :true,							//表格项文字隐藏显示提示
				dialogImageVisible : false,					//查看大图弹窗显示状态
				dialogGoodsDetailVisible : false,
				goodsDetail : {},
				ossUrl : "http://test-hjh.oss-cn-shanghai.aliyuncs.com/",
				categorylist: [],							//类目列表
				multipleSelection: {},						//已选列集合
				selectcount : 0,
				page : 1,									//列表当前页码
				limit : 10,									//列表每页条数
				pickerOptions: {
					shortcuts: [{
						text: '最近一周',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近一个月',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近三个月',
						onClick(picker) {
							const end = new Date();
							const start = new Date();
							start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
							picker.$emit('pick', [start, end]);
						}
					}]
				}
			}
		},
		mounted(){
			document.title = "后台管理系统-活动商品管理";
			//console.log(this.activityId);
			this.getListBySearchData();
		},
		created(){
			this.action = location.hash.indexOf("action=checkGoods") > -1;
			var that = this;
			//获取品牌列表
			that.$http.post("/goodsbrand/queryforweb",{limit:10000,page:1}).then(response =>{
				var data = response.data;
				if(data.error_no == 0){
					that.$data.brandlist = data.result_list;
				}
			});
			//获取类型列表
			that.$http.post("/json/900511",{limit:10000,page:0}).then(response =>{
				var data = response.data;
				if(data.error_no == 0){
					that.$data.categorylist = data.result_list;
				}
			});
		},
		methods: {
			getDeaultSearchForm() {
				return {
					goods_id:"",
        				brand_name : "",
        				third_category_id : "",
        				goods_name : "",
        				start_date :"",
        				end_date : ""
        			}
        		},
        		handleTabClick(tab,event){
        			this.getGoodsListApi = this.activeName=="first" ? "/json/901208" : "/json/901214";
        			this.isnotnew = this.activeName=="first";
        			this.resetForm();
        			this.multipleSelection = {};
        			this.selectcount = 0;
        		},
        		datepickerchange(val){
        			if(!val){
        				this.searchForm.start_date = "";
        				this.searchForm.end_date = "";
        			}else{
        				var arr = val.split(" - ");
        				this.searchForm.start_date = arr[0].match(/\d/g).join("");
        				this.searchForm.end_date =  arr[1].match(/\d/g).join("");
        			}
        		},
        		submitForm(){
        			this.$data.page = 1;
				this.getListBySearchData();
        		},
        		resetForm(){
        			this.searchForm = this.getDeaultSearchForm();
        			this.datepickerValue = [];
        			this.submitForm();
        		},
        		handleCurrentChange(val){
        			this.$data.page = val;
					this.searchDataCache.page = val;
					this.getGoodsList();
        		},
        		checkProduct(index,list){
        			var item = list[index];
        			//this.BigImageUrl = 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+item.show_url;
				//this.dialogImageVisible = true;
				this.goodsDetail = item;
				this.dialogGoodsDetailVisible = true;
        		},
        		deleteGoods(){
        			var that = this;
        			this.$confirm('确定要删除该商品吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					//var actGoodIds = that.multipleSelection.map(item=>{ return item.goods_id; });
					var actGoodIds = [];
					for(var i in that.multipleSelection){
						actGoodIds = actGoodIds.concat(that.multipleSelection[i].map(item=>{ return item.goods_id; }));
					}
					this.$http.post("/json/901206",{
						access_token:localStorage.access_token,
						actGoodIds:actGoodIds,
						activityId : this.activityId
					}).then(response=>{
						var jsondata = response.data;
						that.$message({
				 			type: jsondata.error_no == 0 ? "success" : "error",
				 			message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
				 		});
				 		if(jsondata.error_no == 0){
				 			that.getGoodsList();
				 			that.multipleSelection = {};
				 			that.selectcount = 0;
				 		}
					});
				}).catch(() => {         
				});
        		},
        		addGoods(){
        			var that = this;
        			this.$confirm('确定要添加该商品吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					//var actGoodIds = that.multipleSelection.map(item=>{ return item.goods_id; });
					var actGoodIds = [];
					for(var i in that.multipleSelection){
						actGoodIds = actGoodIds.concat(that.multipleSelection[i].map(item=>{ return item.goods_id; }));
					}
					this.$http.post("/json/901205",{access_token:localStorage.access_token,goodIds:actGoodIds,activityId:this.activityId}).then(response=>{
						var jsondata = response.data;
						that.$message({
				 			type: jsondata.error_no == 0 ? "success" : "error",
				 			message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
				 		});
				 		if(jsondata.error_no == 0){
				 			that.getGoodsList();
				 			that.multipleSelection = {};
				 			this.selectcount = 0;
				 		}
					});
				}).catch(() => {         
				});
        		},
        		
        		handleSelectionChange(val) {
        			this.multipleSelection[this.page] = val;
        			var count = 0;
        			for(var i in this.multipleSelection){
        				count += this.multipleSelection[i].length;
        			}
        			this.selectcount = count;
				//this.multipleSelection = val;
			},
			getListBySearchData(){
        			var param = {
        				activityId : this.activityId,
        				page : this.page,
					limit : this.limit,
					access_token : localStorage.access_token,
					goods_id:this.searchForm.goods_id,
        				brand_name : this.searchForm.brand_name,
        				third_category_id : this.searchForm.third_category_id,
        				goods_name : this.searchForm.goods_name,
        				update_start_date :this.searchForm.start_date,
        				update_end_date : this.searchForm.end_date
        			};
        			this.searchDataCache = Common.simpleClone(param);
				this.getGoodsList();
			},
			toggleSelection() {
				var rows = this.multipleSelection[this.page];
				//this.$refs.multipleTable.toggleRowSelection(this.GoodsList.result_list[1],true);
				//this.$refs.multipleTable.toggleRowSelection(rows[1],true);
				//return;
		        if (rows) {
		          rows.forEach(row => {
		            this.$refs.multipleTable.toggleRowSelection(row,true);
		          });
		        } else {
		          this.$refs.multipleTable.clearSelection();
		        }
		      },
        		getGoodsList(){
        			if(this.loading) return;
        			var that = this,param = this.searchDataCache;
        			this.loading = true;
        			
        			if(!this.isnotnew){
        				param.goods_status = 1;
        			}
        			param = Common.filterParamByEmptyValue(param);
        			that.$http.post(this.getGoodsListApi,param).then(response => {
        				that.GoodsList = response.data;
        				if(that.GoodsList.error_no==0){
	        				that.GoodsList.result_list = that.GoodsList.result_list.map((item,index)=>{
	        					item.initTime = Common.formatDateConcat(item.init_date,item.init_time);
	        					item.updateTime = Common.formatDateConcat(item.update_date,item.update_time);
	        					return item;
	        				});
	        				this.toggleSelection();
        				}else{
        					that.$message({
        						type: "error",
				 				message: that.GoodsList.error_info
        					})
        				}
        				that.loading = false;
        			});
        		}
        }
    }
</script>
