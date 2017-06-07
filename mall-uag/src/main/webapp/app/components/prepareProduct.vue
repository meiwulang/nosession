<template>
	<div>
		<!-- 面包屑 -->
		<div class="hjh-breadcrumb">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item :to="{ path: '/' }">用户中心</el-breadcrumb-item>
				<el-breadcrumb-item>商品管理</el-breadcrumb-item>
				<el-breadcrumb-item>待发布商品</el-breadcrumb-item>
			</el-breadcrumb>	
		</div>
		<!-- 面包屑 end -->
		<!-- 列表查询表单 -->
		<div class="block-white">
			<el-form :inline="true" :model="searchForm" ref="searchForm" class="demo-form-inline searchForm">
				<el-form-item>
					<el-input v-model="searchForm.goods_id" placeholder="商品编号" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item >
					<el-input v-model="searchForm.goods_name" placeholder="商品标题" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item >
					<el-select v-model="searchForm.brand_name" filterable placeholder="品牌" style="width: 150px;">
						<el-option v-for="item in goodsBrandList" :key="item.brand_name" :label="item.brand_name" :value="item.brand_name">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item >
					<el-select v-model="searchForm.third_category_name" filterable placeholder="类目" style="width: 150px;">
						<el-option v-for="item in categoryList" :key="item.cname" :label="item.cname" :value="item.brand_name">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-select v-model="searchForm.create_user_name" filterable placeholder="请输入操作人" style="width: 150px;">
						<el-option v-for="item in operatorList" :key="item.operator_name" :label="item.operator_name" :value="item.operator_name">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<div style="width: 450px;">
					<el-col :span="11">
						<el-date-picker type="date" placeholder="创建开始时间" @change="initStartDateChange" format="yyyy-MM-dd HH:mm:ss"  v-model="init_start_date" style="width:100%;"></el-date-picker>
					</el-col>
					<el-col class="line" :span="2" style="text-align: center;">到</el-col>
					<el-col :span="11">
						<el-date-picker type="date" placeholder="创建结束时间" @change="initEndDateChange" format="yyyy-MM-dd HH:mm:ss" v-model="init_end_date" style="width:100%;"></el-date-picker>
					</el-col>
					</div>
				</el-form-item>
				<el-form-item >
					<el-input v-model="searchForm.metadata_name" placeholder="模糊查询规格的3个字段" style="width:170px;"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button class="searchBtn" type="primary" @click="submitForm('searchForm')">搜索</el-button>
					<el-button @click="resetForm('searchForm')">清除</el-button>
				</el-form-item>
			</el-form>
		</div>
		<!-- 列表查询表单 end -->
		<div class="block-white">
			<el-button type="primary" @click="bathGroundingGoods" :disabled="!multipleSelection.length">批量上架</el-button>
		</div>
		<div class="block-white">
			<div class="selection-tip">
				<i class="el-icon-warning"></i><span>已选择 {{multipleSelection.length}} 项数据。</span>
			</div>
		</div>
		<!-- 商品列表 -->
		<div class="block-white">
			<el-table v-loading.body="loading" 
				:data="ClientList.result_list" 
				@selection-change="handleSelectionChange" 
				ref="multipleTable"
				border stripe style="width: 100%" max-height="460">
				<el-table-column fixed type="selection" width="55" class="hjhcheckbox"></el-table-column>
				<el-table-column label="商品信息" width="450">
					<template scope="scope">
						<img :src="'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+scope.row.show_url">
						<a @click="checkProduct(scope.$index, ClientList.result_list)"><div class="">{{scope.row.goods_name}}</div></a>
						<div class="">{{scope.row.goods_id}}</div>
						<div class="">品牌：{{scope.row.brand_name}}</div>
						<div class="">三级类目：{{scope.row.third_category_name}}</div>
					</template>
				</el-table-column>
				<el-table-column prop="brand_name" label="规格/价格" align="center" width="300">
					<template scope="scope">
						<ul>
							<li v-for="item in scope.row.standardList">
							    <div class="" style="float:left">{{item.standard_must}}|{{item.optional_first}}|{{item.optional_second}}</div>
								<div class="" > &nbsp;&nbsp;&nbsp; {{item.price}}/{{item.unit_name}}</div>
							</li>
						</ul>
					</template>
				</el-table-column>
				<el-table-column  label="已售" width="100" align="center">
					<template scope="scope">
						<ul>
							<li v-for="item in scope.row.standardList">
							    <div class="">{{item.sale_num}}</div>
							</li>
						</ul>
					</template>
				</el-table-column>
				<el-table-column  label="库存" align="center" width="100">
					<template scope="scope">
						<ul>
							<li v-for="item in scope.row.standardList">
							    <div class="">{{item.store_num}}</div>
							</li>
						</ul>
					</template>
				</el-table-column>
				<el-table-column prop="createTime" label="创建日期" align="center" width="180"></el-table-column>
				<el-table-column prop="create_user_name" label="创建人" align="center" width="150"></el-table-column>
				<el-table-column prop="updateTime" label="上架日期" align="center" width="180"></el-table-column>
				<el-table-column prop="update_user_name" label="最后操作者" align="center" width="120"></el-table-column>
				<el-table-column fixed="right"  label="操作" width="320">
					<template scope="scope">
						<el-button @click.native.prevent="productView(scope.row.goods_id)" type="warning">预览</el-button>
						<el-button @click.native.prevent="modifyGoods(scope.row.goods_id)" type="warning">编辑</el-button>
						<el-button @click.native.prevent="delGoods(scope.row.goods_id)" type="warning">删除</el-button>
						<el-button @click.native.prevent="modifyGoodsStatus(scope.row.goods_id)" type="warning">上架</el-button>
					</template>
				</el-table-column>
			</el-table>
		</div>
		<!-- 会员管理列表 end -->
		<!-- 翻页组件 -->
		<div class="block-white">
			<el-pagination
		      @current-change="handleCurrentChange"
		      :current-page="page"
		      :page-size="limit"
		      layout="total, prev, pager, next, jumper"
		      :total="ClientList.total_num">
		    </el-pagination>
		</div>
		<!-- 翻页组件 end -->
				<el-dialog title="商品信息" v-model="dialogGoodsDetailVisible">
			<div class="goodsDetail" style="height: 450px; overflow: hidden; overflow-y: auto;">
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">
						<em style="color: #FF0000;"></em>商品名称
					</el-col>
					<el-col :span="20">{{goodsDetail.goods_name}}</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">
						<em style="color: #FF0000;"></em>类目
					</el-col>
					<el-col :span="20">{{goodsDetail.third_category_name}}</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">
						<em style="color: #FF0000;"></em>品牌
					</el-col>
					<el-col :span="20">{{goodsDetail.brand_name}}</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">
						<em style="color: #FF0000;"></em>计量单位
					</el-col>
					<el-col :span="20">{{goodsDetail.unit_name}}</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">
						<em style="color: #FF0000;"></em>占位图
					</el-col>
					<el-col :span="20">
						<img v-if="goodsDetail.ad_url" style="display: inline-block; width: 80px; height: 30px;" :src="ossUrl+goodsDetail.ad_url">
						<span v-if="!goodsDetail.ad_url">无</span>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">
						<em style="color: #FF0000;"></em>适用机型
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
						<em style="color: #FF0000;"></em>banner图
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
						<em style="color: #FF0000;"></em>商品详图
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
						<em style="color: #FF0000;"></em>客服电话
					</el-col>
					<el-col :span="20">{{goodsDetail.tel}}</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">
						<em style="color: #FF0000;"></em>商品参数
					</el-col>
					<el-col :span="20">
						<ul class="param-list">
							<li v-for="item in goodsDetail.infoList">
								<span><em></em>名称<span>{{item.info_title}}</span></span>
								<span><em></em>值<span>{{item.info_content}}</span></span>
							</li>
						</ul>
					</el-col>
				</el-row>
				<el-row>
					<el-col :span="1">&nbsp;</el-col>
					<el-col :span="3">
						<em style="color: #FF0000;"></em>商品规格
					</el-col>
					<el-col :span="20">
						<ul class="standard-list">
							<li v-for="item in goodsDetail.standardList">
								<span><em></em>名称<span>{{item.standard_must}}</span><span>{{item.optional_first}}</span><span>{{item.optional_second}}</span></span>
								<span><em></em>价格<span>{{item.price}}</span></span>
								<span><em></em>库存<span>{{item.store_num}}</span></span>
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
	.el-dialog__body .el-form-item__content{
		line-height: 0;
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
</style>
<script>
	import Common from './../js/common';
	export default {
		data() {
			return {
				searchForm: this.getDefaultData(),	//查询表单
				page : 1,							//列表初始当前页码
				limit : 10,							//每页列表数量
				init_start_date : '',
				init_end_date : '',
				goodsBrandList : [],						//品牌列表
				metalist : [],
				operatorList : [],
				categoryList:[],
				multipleSelection: [],
				ossUrl : "http://test-hjh.oss-cn-shanghai.aliyuncs.com/",
				dialogGoodsDetailVisible : false,
				goodsDetail : {},
				ClientList : {},						//会员列表数据缓存
				formScopeDialog:{},					//查看或编辑时对应的数据缓存
				dialogFormVisible:false,				//查看与编辑弹窗显示状态 false为隐藏 true为显示
				formLabelWidth: '110px',				//弹窗中的表单label的宽度
				activeName:"first",					//弹窗中tabs选项卡初始选中值
				labelPosition : "right",				//弹窗中表单label文字的对齐方式
				ismodify:false,						//是否为编辑状态
				loading:false,						//会员列表加载状态 true为加载中 false为加载完毕
				timers:0,
				//验证规则
				rules : {
					//机型品牌的验证规则
					brand_id : [
						{required: true, message: '请选择机型品牌', trigger: 'blur,change'}
					],
					//类型验证规则
					metadata_id : [
						{required: true, message: '请选择类型', trigger: 'blur,change'}
					],
					//型号的验证规则
					car_models_name : [
						{required: true, message: '请输入型号', trigger: 'blur'},
						{ min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
					],
					brand_logo : [
						{required: true, message: '请选择机型品牌', trigger: 'blur,change'}
					]
				},
				//参数设置表单
				addParamForm: {
		        		carParameters : []
		        },
		        //是否在APP显示
				checked : false
			}
		},
		created(){
			var that = this;
			//获取类型列表
			that.$http.post("./../queryMetadata",{limit:10000,page:1,status:1,type:1}).then(response =>{
				var data = response.data;
				if(data.error_no == 0){
					that.$data.metalist = data.result_list;
				}
			});
			//获取操作人列表
			this.$http.post("/getAllOperators",{}).then(res=>{
				var data = res.data;
				if(data.error_no == 0){
					this.$data.operatorList = data.result_list;
				}
			});
			//获取品牌列表
			this.$http.post("/goodsbrand/queryforweb",{limit:1000000,status:1}).then(res=>{
				var data = res.data;
				if(data.error_no == 0){
					this.$data.goodsBrandList = data.result_list;
				}
			});
			//获取三级类目列表
			this.$http.post("/json/900511",{limit:1000000,status:1}).then(res=>{
				var data = res.data;
				if(data.error_no == 0){
					this.$data.categoryList = data.result_list;
				}
			});
		},
		mounted(){
			document.title = "后台管理系统-待发布商品";
			this.getListBySearchData();
		},
		methods: {
			//弹框展示详情
			checkProduct(index,list){
        		var item = list[index];
				this.goodsDetail = item;
				this.dialogGoodsDetailVisible = true;
        		},
			//选择添加开始时间
			initStartDateChange(val){
				if(val)
					this.init_start_date = val.replace("00:00:00",Common.getHMSforNow());
			},
			//选择添加结束时间
			initEndDateChange(val){
				if(val)
					this.init_end_date = val.replace("00:00:00",Common.getHMSforNow());
			},
			//多选状态发生改变
			handleSelectionChange(val) {
				this.multipleSelection = val;
				
			},
			//获取查询表单初始数据
			getDefaultData() {
				return {
					access_token: localStorage.access_token,
					brand_name : "",
					create_user_name : "",
					end_date : '',		
					goods_id : '',			
					goods_name : '',	
					goods_status : '0',		
					limit : '',		
					page : '',		
					start_date : '',		
					third_category_name : ''
				}
			},
			//预览商品
			productView(val){
				
			},
			//编辑商品
			modifyGoods(val){
				location.href = "#/modifyProduct/"+val
			},
			//删除商品
			delGoods(val){
				var that = this;
				this.$confirm('确定要删除该商品吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http.post('/delGoods',{goods_id:val,access_token:localStorage.access_token}).then(response => {
						var jsondata = response.data;
							that.$message({
					 			type: jsondata.error_no == 0 ? "success" : "error",
					 			message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
					 		});
					 		if(jsondata.error_no == 0){
					 			that.getGoodsList();
					 		}
					})
				}).catch(() => {         
				});
			},
			//上架单个商品
			modifyGoodsStatus(val){
				var that = this;
				this.$confirm('确定要上架该商品吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$http.post('/groundingGoods',{goods_id:val,access_token:localStorage.access_token}).then(response => {
						var jsondata = response.data;
							that.$message({
					 			type: jsondata.error_no == 0 ? "success" : "error",
					 			message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
					 		});
					 		if(jsondata.error_no == 0){
					 			that.getGoodsList();
					 		}
					})
				}).catch(() => {         
				});
			},
			//批量上架商品
			bathGroundingGoods(val){
				var that = this;
				this.$confirm('确定要上架选中商品吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					var goods_ids = that.multipleSelection.map(item=>{ return item.goods_id; });
					this.$http.post('/bathGroundingGoods',{ids:goods_ids,access_token:localStorage.access_token}).then(response => {
						var jsondata = response.data;
							that.$message({
					 			type: jsondata.error_no == 0 ? "success" : "error",
					 			message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
					 		});
					 		if(jsondata.error_no == 0){
					 			that.getGoodsList();
					 		}
					})
				}).catch(() => {         
				});
			},
			//删除参数行
			deleteParam(item){
				var index = this.addParamForm.carParameters.indexOf(item);
		        if (index !== -1) {
		          this.addParamForm.carParameters.splice(index, 1)
		        }
			},
			//多选 启用或禁用 status = 1 启用 0禁用
			updateCarStatusBatch(status){
				var that = this;
				this.$confirm('确定要'+ (status==1 ? '启用':'禁用') +'该机型吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					var car_models_ids = that.multipleSelection.map(item=>{ return item.car_models_id; }).join(",")
					that.ajaxUpdateCarStatusBatch(status,car_models_ids,function(){
						that.multipleSelection = [];
				 		that.$refs.multipleTable.clearSelection();
					});
				}).catch(() => {         
				});
			},
			//状态变更提交
			ajaxUpdateCarStatusBatch(status,car_models_ids,handle){
				var that = this;
				var param = {
						access_token : that.searchForm.access_token,
						status : status,
						car_models_ids : car_models_ids
					};
				that.$http.post("./../updateCarStatusBatch",param).then(response=>{
						var jsondata = response.data;
						that.$message({
				 			type: jsondata.error_no == 0 ? "success" : "error",
				 			message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
				 		});
				 		if(jsondata.error_no == 0){
				 			if(typeof handle == "function"){
				 				handle();
				 			}
				 			that.getGoodsList();
				 		}
					})
			},
			//添加参数事件
			addCarTypeParameter(){
				this.$data.addParamForm.carParameters.push({
					car_params_name : '',
					car_params_value : ''
				});
			},
			//操作－查询
			submitForm() {
				this.$data.page = 1;
				this.getListBySearchData();
			},
			//操作－重置
			resetForm(formName) {
        			this.$refs[formName].resetFields();
        			this.$data.searchForm = this.getDefaultData();
        			this.init_start_date = "";
        			this.init_end_date = "";
        			this.submitForm();
			},
			//操作－翻页
			handleCurrentChange(val){
				this.$data.page = val;
				this.searchDataCache.page = val;
				this.getGoodsList();
			},
			//操作－查看
			checkView(index,list){
				this.$data.dialogFormVisible = true;
				this.$data.ismodify = false;
				this.$data.formScopeDialog = list[index];
				this.$data.checked = this.$data.formScopeDialog.app_show==1;
			},
			//操作－编辑
			modify(index,list){
				this.$data.dialogFormVisible = true;
				this.$data.ismodify = true;
				this.$data.formScopeDialog = Common.simpleClone(list[index]);
				this.$data.checked = this.$data.formScopeDialog.app_show==1;
				var that = this;
				setTimeout(function(){
					that.$refs[that.formScopeDialog].validate();
				},50);
			},
			
			//操作－启用或禁用
			setStatus(index,list){
				var status = list[index].status==1?0:1,
					id = list[index].car_models_id,
					that = this;
				 this.$confirm("确定要" + (status==1?"启用":"禁用") + "该机型吗？", '', {
				 	confirmButtonText: '确定',
				 	cancelButtonText: '取消',
				 	type: 'warning'
				 }).then(() => {
				 	that.ajaxUpdateCarStatusBatch(status,id);
				 });
			},
			checkLogout(error_no){
				if(error_no==88880020){
					location.href="/login";
				}
			},
			//重置时间戳格式
			reFormatDate(date){
				var _date = new Date(date);
				if(!date || !_date.getTime()){return "";}
				var y = _date.getFullYear(),
					M = _date.getMonth()+1,
					d = _date.getDate(),
					H = _date.getHours(),
					m = _date.getMinutes(),
					s = _date.getSeconds();
				M = M > 9 ? M : ("0"+M);
				d = d > 9 ? d : ("0"+d);
				H = H > 9 ? H : ("0"+H);
				m = m > 9 ? m : ("0"+m);
				s = s > 9 ? s : ("0"+s);
				return y+M+d+H+m+s;
			},
			getListBySearchData(){
				var param = this.searchForm;
		 		param.page = this.$data.page;
		 		param.limit = this.$data.limit;
		 		param.start_date = this.reFormatDate(this.$data.init_start_date);
		 		param.end_date = this.reFormatDate(this.$data.init_end_date);
				this.searchDataCache = Common.simpleClone(param);
				this.getGoodsList();
			},
		 	//获取商品列表
			getGoodsList() {
				if(this.loading) return;
				var that = this;
	 			that.$data.loading = true;
	 			var param = Common.filterParamByEmptyValue(this.searchDataCache);
	 			this.$http.post('./../queryGoods',param).then(response => {
					var jsonData=response.data;
					that.checkLogout(jsonData.error_no);
					if(jsonData.error_no==0){
						jsonData.result_list = jsonData.result_list.map(function(item){
							item.createTime = Common.formatDateConcat(item.init_date,item.init_time);
							item.updateTime = Common.formatDateConcat(item.update_date,item.update_time);
							return item;
						});
						that.$data.ClientList = jsonData || {};
					}else{
						that.$message({
							type: "warning",
							message: jsonData.error_info
						});
					}
					that.$data.loading = false;
				});
			}
		}
	}
</script>
