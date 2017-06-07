<template>
	<div>
		<!-- 面包屑 -->
		<div class="hjh-breadcrumb">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item :to="{ path: '/' }">用户中心</el-breadcrumb-item>
				<el-breadcrumb-item>机型管理</el-breadcrumb-item>
				<el-breadcrumb-item>机型管理</el-breadcrumb-item>
			</el-breadcrumb>	
		</div>
		<!-- 面包屑 end -->
		<!-- 列表查询表单 -->
		<div class="block-white">
			<el-form :inline="true" :model="searchForm" ref="searchForm" class="demo-form-inline searchForm">
				<el-form-item>
					<el-input v-model="searchForm.car_models_id" placeholder="搜索代码" style="width:150px;"></el-input>
				</el-form-item>
				<el-select v-model="searchForm.brand_name" filterable placeholder="搜索品牌" style="width: 200px;">
					<el-option v-for="item in brandlist" :key="item.brand_id" :label="item.brand_name" :value="item.brand_name">
					</el-option>
				</el-select>
				<el-form-item>
					<el-select v-model="searchForm.status" placeholder="状态" style="width:150px;">
						<el-option label="启用" value="1"></el-option>
						<el-option label="禁用" value="0"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="类型">
					<el-input v-model="searchForm.metadata_name" placeholder="模糊匹配" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item>
					<el-input v-model="searchForm.car_models_name" placeholder="搜索型号" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item>
					<el-select v-model="searchForm.is_noparams" placeholder="参数" style="width:150px;">
						<el-option label="有参数" value="1"></el-option>
						<el-option label="无参数" value="0"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-select v-model="searchForm.app_show" placeholder="APP显示" style="width:150px;">
						<el-option label="显示" value="1"></el-option>
						<el-option label="不显示" value="0"></el-option>
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
				<el-form-item>
					<el-button class="searchBtn" type="primary" @click="submitForm('searchForm')">搜索</el-button>
					<el-button @click="resetForm('searchForm')">清除</el-button>
				</el-form-item>
			</el-form>
		</div>
		<!-- 列表查询表单 end -->
		<div class="block-white">
			<el-button type="primary" @click="updateCarStatusBatch(1)" :disabled="!multipleSelection.length">启用</el-button>
			<el-button type="danger" @click="updateCarStatusBatch(0)" :disabled="!multipleSelection.length">禁用</el-button>
		</div>
		<div class="block-white">
			<div class="selection-tip">
				<i class="el-icon-warning"></i><span>已选择 {{multipleSelection.length}} 项数据。</span>
			</div>
		</div>
		<!-- 会员管理列表 -->
		<div class="block-white">
			<el-table v-loading.body="loading" 
				:data="ClientList.result_list" 
				@selection-change="handleSelectionChange" 
				ref="multipleTable"
				border stripe style="width: 100%" max-height="460">
				<el-table-column fixed type="selection" width="55" class="hjhcheckbox">
					
				</el-table-column>
				<el-table-column label="序号" width="70" align="center"><template scope="scope">{{scope.$index+1}}</template></el-table-column>
				<el-table-column prop="car_models_id" label="代码" width="200"></el-table-column>
				<el-table-column prop="brand_name" label="品牌" align="center" width="130"></el-table-column>
				<el-table-column prop="brand_logo" label="品牌logo" width="110">
					<template scope="scope"><img :src="'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+scope.row.brand_logo"></template>
				</el-table-column>
				<el-table-column prop="metadata_name" label="类型" align="center" width="150"></el-table-column>
				<el-table-column prop="car_models_name" label="型号" align="center" width="150"></el-table-column>
				<el-table-column prop="status" label="参数" align="center" width="70">
					<template scope="scope">{{scope.row.carParamsList.length?"有":"无"}}</template>
				</el-table-column>
				<el-table-column prop="createTime" label="创建日期" align="center"  width="180"></el-table-column>
				<el-table-column prop="create_user" label="创建者" align="center"  width="100"></el-table-column>
				<el-table-column prop="updateTime" label="最后操作日期" align="center" width="180"></el-table-column>
				<el-table-column prop="update_user_name" label="最后操作者" align="center" width="120"></el-table-column>
				<el-table-column prop="status" label="APP显示" align="center" width="120">
					<template scope="scope">APP{{scope.row.app_show==1?"":"不"}}显示</template>
				</el-table-column>
				<el-table-column prop="status" label="状态" align="center" width="80">
					<template scope="scope">{{scope.row.status==1?"启用":"禁用"}}</template>
				</el-table-column>
				<el-table-column fixed="right"  label="操作" width="250">
					<template scope="scope">
						<el-button @click.native.prevent="checkView(scope.$index, ClientList.result_list)" type="warning">查看</el-button>
						<el-button @click.native.prevent="modify(scope.$index, ClientList.result_list)" type="info">编辑</el-button>
						<el-button @click.native.prevent="setStatus(scope.$index, ClientList.result_list)" type="danger" >{{scope.row.status=="1"?"禁用":"启用"}}</el-button>
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
		<!-- 查看与编辑弹窗 -->
		<el-dialog title="查看机型" v-model="dialogFormVisible">
			<div style="height: 320px; overflow: hidden; overflow-y: auto;">
				<el-form :label-position="labelPosition" :rules="rules" :ref="formScopeDialog" :label-width="formLabelWidth" :model="formScopeDialog">
					<el-form-item label="机型品牌"  prop="brand_id" >
						<el-select :disabled="!ismodify" v-model="formScopeDialog.brand_id" filterable placeholder="请选择机型品牌" style="width: 200px;">
							<el-option v-for="item in brandlist" :key="item.brand_id" :label="item.brand_name" :value="item.brand_id">
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="品牌LOGO"  prop="brand_logo" >
						<el-input type="hidden" v-model="formScopeDialog.brand_logo"></el-input>
						<div style=" overflow: hidden; line-height: 30px;">
							<img width="80" height="30" style="display: block; float: left; margin-right: 10px;" :src="'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+formScopeDialog.brand_logo">
							<span>{{formScopeDialog.brand_logo}}</span>
						</div>
					</el-form-item>
					<el-form-item label="类型"  prop="metadata_id" >
						<el-select :disabled="!ismodify" v-model="formScopeDialog.metadata_id" placeholder="请选择类型" style="width: 200px;">
							<el-option v-for="item in metalist" :key="item.metadata_id" :label="item.metadata_name" :value="item.metadata_id">
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="型号"  prop="car_models_name" >
						<el-input :disabled="!ismodify" v-model="formScopeDialog.car_models_name" :minlength="2" :maxlength="10" placeholder="请输入型号（2-10字数限制）" style="width: 200px;"></el-input>
					</el-form-item>
					<el-form-item label="" >
						<el-checkbox :disabled="!ismodify" v-model="checked">APP显示</el-checkbox>
					</el-form-item>
					
				</el-form>
				<div class="newCarTypeGroup">参数设置</div>
				<el-form label-width="100px" :model="addParamForm" :ref="addParamForm" class="demo-form-inline" style="width: 660px;">
					<el-row v-for="(parameter,index) in addParamForm.carParameters">
						<el-col :span="11">
							<el-form-item label="型号" :prop="'carParameters.'+index+'.car_params_name'" :rules="[
					      { required: true, message: '请输入型号', trigger: 'blur' },
					      { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
					    ]">
								<el-input :disabled="!ismodify" v-model="parameter.car_params_name" :minlength="2" :maxlength="50" placeholder="输入（2-50字数限制）" style="width: 200px;"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="10">
							<el-form-item label="值"  label-width="40px">
								<el-input :disabled="!ismodify" v-model="parameter.car_params_value" :minlength="2" :maxlength="50" placeholder="输入（2-50字数限制）" style="width: 200px;"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="2" v-if="ismodify">
							<el-button @click="deleteParam(parameter)" :plain="true" type="danger">删除</el-button>
						</el-col>
					</el-row>
					<div class="addParameterBtn" @click="addCarTypeParameter" v-if="ismodify"><i class="el-icon-plus"></i> 添加参数</div>
				</el-form>
			</div>
			<div slot="footer" class="dialog-footer" style="text-align: center;" v-if="!ismodify">
				<el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
			</div>
			<div slot="footer" class="dialog-footer" style="text-align: center;" v-if="ismodify">
				<el-button type="primary" @click="addCarModel(1)">保存并启用</el-button>
				<el-button type="primary" @click="addCarModel(0)">保存并禁用</el-button>
				<el-button @click="dialogFormVisible = false">关 闭</el-button>
			</div>
		</el-dialog>
		<!-- 查看与编辑弹窗 end -->
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
				brandlist : [],						//品牌列表
				metalist : [],
				multipleSelection: [],
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
			//获取品牌列表
			that.$http.post("./../carbrand/queryforweb",{limit:10000,page:1,status:1}).then(response =>{
				var data = response.data;
				if(data.error_no == 0){
					that.$data.brandlist = data.result_list;
				}
			});
			//获取类型列表
			that.$http.post("./../queryMetadata",{limit:10000,page:1,status:1,type:1}).then(response =>{
				var data = response.data;
				if(data.error_no == 0){
					that.$data.metalist = data.result_list;
				}
			});
		},
		mounted(){
			document.title = "后台管理系统-机型管理";
			this.getListBySearchData();
		},
		methods: {
			//获取查询表单初始数据
			getDefaultData() {
				return {
					access_token: localStorage.access_token,
					status : "",
					app_show : "",
					car_models_id : '',		//搜索代码
					brand_name : '',			//搜索品牌
					car_models_name : '',	//搜索型号
					is_noparams : '',		//有无参数
					metadata_name : ''		//搜索类型
				}
			},
			//选择注册开始时间
			initStartDateChange(val){
				if(val)
					this.init_start_date = val.replace("00:00:00",Common.getHMSforNow());
			},
			//选择注册结束时间
			initEndDateChange(val){
				if(val)
					this.init_end_date = val.replace("00:00:00",Common.getHMSforNow());
			},
			//多选状态发生改变
			handleSelectionChange(val) {
				this.multipleSelection = val;
				
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
				 			that.getClientList();
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
				this.getClientList();
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
			//保存
			addCarModel(status){
				var that = this,form = that.formScopeDialog;
				var brand = this.$data.brandlist.filter(function(item){
					return item.brand_id == form.brand_id;
				})[0];
				var meta = this.$data.metalist.filter(function(item){
					return item.metadata_id == form.metadata_id;
				})[0];
				var param = {
						access_token : that.searchForm.access_token,
						status : status,
						app_show : that.checked ? 1 : 0,
						brand_id : form.brand_id,
						brand_name : brand.brand_name,
						car_models_id : form.car_models_id,
						car_models_name : form.car_models_name,
						metadata_id : form.metadata_id,
						metadata_name : meta.metadata_name,
						carParamsList : that.addParamForm.carParameters
				};
				that.$refs[that.formScopeDialog].validate((valid) => {
					if (valid) {
						that.$refs[that.addParamForm].validate((valid1) => {
							if (valid1) {
								this.$http.post('./../updateCarModel',param).then(response => {
									var jsondata = response.data;
										that.$message({
								 			type: jsondata.error_no == 0 ? "success" : "error",
								 			message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
								 		});
								 		if(jsondata.error_no == 0){
								 			that.getClientList();
								 		}
								 		that.$data.dialogFormVisible = false;
								});
							}
						})
					}
				})
				
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
				this.getClientList();
			},
		 	//获取会员列表
			getClientList() {
				if(this.loading) return;
				var that = this;
	 			that.$data.loading = true;
	 			this.$http.post('./../queryCarModelList',this.searchDataCache).then(response => {
					var jsonData=response.data;
					that.checkLogout(jsonData.error_no);
					if(jsonData.error_no==0){
						jsonData.result_list = jsonData.result_list.map(function(item){
							item.createTime = Common.formatDateConcat(item.create_date,item.create_time);
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
