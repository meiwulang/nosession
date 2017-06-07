<template>
	<div>
		<!-- 面包屑 -->
		<div class="hjh-breadcrumb">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item :to="{ path: '/' }">用户中心</el-breadcrumb-item>
				<el-breadcrumb-item>基本管理</el-breadcrumb-item>
				<el-breadcrumb-item>元数据管理</el-breadcrumb-item>
			</el-breadcrumb>	
		</div>
		<!-- 面包屑 end -->
		<!-- 列表查询表单 -->
		<div class="block-white">
			<el-form :inline="true" :model="searchForm" ref="searchForm" class="demo-form-inline searchForm">
				
				<el-form-item>
					<el-input v-model="searchForm.metadata_name" placeholder="请输入名称" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item>
					<el-input v-model="searchForm.alias" placeholder="请输入别名" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item>
					<el-select v-model="searchForm.type" placeholder="请选择类别"  style="width:150px;">
						<el-option label="计量单位" value="0"></el-option>
						<el-option label="机械类型" value="1"></el-option>
						<el-option label="售后电话" value="2"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-select v-model="searchForm.status" placeholder="请选择状态" style="width:150px;">
						<el-option label="启用" value="1"></el-option>
						<el-option label="禁用" value="0"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-select v-model="searchForm.update_user" filterable placeholder="请输入操作人" style="width: 150px;">
						<el-option v-for="item in operatorList" :key="item.operator_name" :label="item.operator_name" :value="item.operator_name">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<div style="width: 450px;">
					<el-col :span="11">
						<el-date-picker type="date" placeholder="最后操作开始时间" @change="initStartDateChange" format="yyyy-MM-dd HH:mm:ss" v-model="init_start_date" style="width:100%;"></el-date-picker>
					</el-col>
					<el-col class="line" :span="2" style="text-align: center;">到</el-col>
					<el-col :span="11">
						<el-date-picker type="date" placeholder="最后操作结束时间" @change="initEndDateChange" format="yyyy-MM-dd HH:mm:ss" v-model="init_end_date" style="width:100%;"></el-date-picker>
					</el-col>
					</div>
				</el-form-item>
				<el-form-item>
					<el-button class="searchBtn" type="primary" @click="submitForm('searchForm')">搜索</el-button>
					<el-button @click="resetForm('searchForm')">清除</el-button>
					<el-button @click="clearCache">刷新缓存</el-button>
				</el-form-item>
			</el-form>
		</div>
		<!-- 列表查询表单 end -->
		<div class="block-white">
			<el-button type="primary" @click="addNew">新增</el-button>
		</div>
		<!-- 会员管理列表 -->
		<div class="block-white">
			<el-table v-loading.body="loading" :data="MetadataList.result_list" border stripe style="width: 100%" max-height="460">
				<el-table-column fixed label="序号" width="70" align="center"><template scope="scope">{{scope.$index+1}}</template></el-table-column>
				<el-table-column prop="metadata_name" label="名称" width="200"></el-table-column>
				<el-table-column prop="alias" label="别名" width="200"></el-table-column>
				<el-table-column prop="type" label="类别" align="center" width="100">
					<template scope="scope">{{scope.row.type == 0 ? "计量单位":(scope.row.type == 1?"机械类型":"售后电话")}}</template>
				</el-table-column>
				<el-table-column prop="sort" label="排序" align="center" width="100"></el-table-column>
				<el-table-column prop="status" label="状态" align="center" width="100">
					<template scope="scope">{{scope.row.status == 1 ? "启用":"禁用"}}</template>
				</el-table-column>
				<el-table-column prop="initTime" label="创建日期" align="center" sortable width="180"></el-table-column>
				<el-table-column prop="create_user" label="创建者" align="center" width="140"></el-table-column>
				<el-table-column prop="updateTime" label="最后操作日期" align="center" sortable width="180"></el-table-column>
				<el-table-column prop="update_user" label="最后操作者" align="center" width="140"></el-table-column>
				<el-table-column fixed="right" label="操作" width="180">
					<template scope="scope">
						<el-button @click.native.prevent="modify(scope.$index, MetadataList.result_list)" type="info">编辑</el-button>
						<el-button @click.native.prevent="setStatus(scope.$index, MetadataList.result_list)" type="danger" >{{scope.row.status=="1"?"禁用":"启用"}}</el-button>
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
		      :total="MetadataList.total_num">
		    </el-pagination>
		</div>
		<!-- 翻页组件 end -->
		<!-- 查看与编辑弹窗 -->
		<el-dialog :title="ismodify?'编辑元数据':'新增元数据'" v-model="dialogFormVisible">
			<div style="height: 320px; overflow: hidden; overflow-y: auto;">
				<el-form :label-position="labelPosition" :label-width="formLabelWidth" :model="formScopeDialog" :ref="formScopeDialog">
					<el-form-item label="名称" prop="metadata_name" :rules="[
					      { required: true, message: '请输入名称', trigger: 'blur' },
					      { required: true, message: '请输入1-20个字符', trigger: 'blur' }
					    ]">
						<el-input v-model="formScopeDialog.metadata_name" :minlength="1" :maxlength="20" placeholder="输入名称"></el-input>
					</el-form-item>
					<el-form-item label="显示名称（别名）" prop="alias" >
						<el-input v-model="formScopeDialog.alias" placeholder="输入别名" :minlength="1" :maxlength="20"></el-input>
					</el-form-item>
					<el-form-item label="类别" prop="type" :rules="[
					      { required: true, message: '请选择类别', trigger: 'blur' }
					    ]">
						<el-select v-model="formScopeDialog.type" placeholder="请选择类别" style="width:150px;">
							<el-option label="计量单位" value="0"></el-option>
							<el-option label="机械类型" value="1"></el-option>
							<el-option label="售后电话" value="2" :disabled="(!ismodify && lenOftype2) || (lenOftype2 && ismodify && typecache!=2)"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="排序">
						<el-input type="number" v-model="formScopeDialog.sort" style="width:150px;"></el-input>
					</el-form-item>
				</el-form>
			</div>
			<div slot="footer" class="dialog-footer" style="text-align: center;">
				<el-button type="primary" @click="updateEnterpriseName(1)">保存并启用</el-button>
				<el-button type="primary" @click="updateEnterpriseName(0)">保存并禁用</el-button>
				<el-button @click="dialogFormVisible = false">关 闭</el-button>
			</div>
		</el-dialog>
		<!-- 查看与编辑弹窗 end -->
	</div>
</template>
<script>
	import Common from './../js/common';
	export default {
		data() {
			return {
				searchForm: this.getDefaultData(),	//查询表单
				init_start_date : '',				//表单项｀创建开始时间
				init_end_date : '',					//表单项｀创建结束时间
				page : 1,							//列表初始当前页码
				limit : 10,							//每页列表数量
				operatorList : [],					//创建者列表
				MetadataList : {},					//元数据列表数据缓存
				formScopeDialog:{},					//查看或编辑时对应的数据缓存
				dialogFormVisible:false,				//查看与编辑弹窗显示状态 false为隐藏 true为显示
				formLabelWidth: '150px',				//弹窗中的表单label的宽度
				labelPosition : "right",				//弹窗中表单label文字的对齐方式
				ismodify:false,						//是否为编辑状态
				lenOftype2 : false,
				loading:false						//列表加载状态 true为加载中 false为加载完毕
			}
		},
		created(){
			var that = this;
			//获取创建者列表
			that.$http.post("./../getAllOperators",{limit:10000,page:1,status:1}).then(response =>{
				var data = response.data;
				if(data.error_no == 0){
					that.$data.operatorList = data.result_list;
				}
			});
		},
		mounted(){
			document.title = "后台管理系统-元数据管理";
			this.getListBySearchData();
		},
		methods: {
			checklenOftype2(){
				this.$http.post('/queryMetadata',{page:1,limit:10,type:2}).then(response=>{
					var data = response.data;
					if(data.error_no==0){
						this.lenOftype2 = data.result_list.length > 0;
					}
				});
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
			//操作－查询
			submitForm() {
				this.$data.page = 1;
				this.getListBySearchData();
			},
			//操作－重置
			resetForm(formName) {
        			this.$refs[formName].resetFields();
        			this.$data.searchForm = this.getDefaultData();
        			this.$data.init_start_date = '';
        			this.$data.init_end_date = '';
        			this.submitForm();
			},
			//操作－刷新缓存
			clearCache() {
				var that = this;
				that.$http.post("./../reloadAllMetadata",{access_token:localStorage.access_token}).then(response=>{
					response = response.data;
					that.$message({
				 		type: response.error_no == 0 ? "success" : "error",
				 		message: response.error_no == 0 ? "缓存刷新成功" : response.error_info
				 	});
				});
			},
			//操作－翻页
			handleCurrentChange(val){
				this.$data.page = val;
				this.searchDataCache.page = val;
				this.getMetadataList();
			},
			//新增
			addNew(){
				console.log(this.lenOftype2);
				this.$data.dialogFormVisible = true;
				this.$data.ismodify = false;
				//this.$data.formScopeDialog = this.getFormDialog();
				this.$data.formScopeDialog = {
					metadata_name : "",
					alias : "",
					type : "",
					sort : ""
				}
			},
			//操作－编辑
			modify(index,list){
				this.$data.dialogFormVisible = true;
				this.$data.ismodify = true;
				//this.$data.formScopeDialog = this.getFormDialog(list[index]);
				this.$data.formScopeDialog = Common.simpleClone(list[index]);
				this.$data.formScopeDialog.type = list[index].type +"";
				this.typecache = this.$data.formScopeDialog.type;
				var that = this;
				setTimeout(function(){
					that.$refs[that.formScopeDialog].validate();
				},50);
			},
			//启用or禁用
			setStatus(index,list){
				var status = list[index].status==1?0:1,
					metadata_id = list[index].metadata_id,
					that = this;
				 this.$confirm("确定要" + (status==1?"启用":"禁用") + "该条数据吗？", '', {
				 	confirmButtonText: '确定',
				 	cancelButtonText: '取消',
				 	type: 'warning'
				 }).then(() => {
				 	var param = {access_token:that.searchForm.access_token,metadata_id:metadata_id,status:status};
				 	that.$http.post("./../updateMetadataStatus",param).then(response=>{
				 		response = response.data;
						that.$message({
					 		type: response.error_no == 0 ? "success" : "error",
					 		message: response.error_no == 0 ? "操作成功" : response.error_info
					 	});
					 	if(response.error_no == 0){
				 			that.getMetadataList();
				 		}
				 	});
				 });
			},
			//获取弹窗中表单数据 -- 初始化设置
			getFormDialog(data){
				if(!data) 
					return  {
						access_token : this.$data.searchForm.access_token,
						sort : 0,
						metadata_id : "0"
					}
				return {
					access_token : this.$data.searchForm.access_token,
					invite_code : data.invite_code,
					invite_id : data.invite_id,
					invite_customer_name : data.invite_customer_name,
					business_hotline : data.business_hotline,
					remark : data.remark
				}
			},
			//操作－确认(保存)
			updateEnterpriseName(status) {
				var api = this.$data.ismodify ? "./../updateMetadata":"./../addMetadata";
				var that = this,dialog = {};// this.$data.formScopeDialog;
				dialog = {
					access_token : localStorage.access_token,
					status : status,
					
				}
				this.$data.formScopeDialog.status = status;
				this.$data.formScopeDialog.access_token = localStorage.access_token;
				that.$refs[that.formScopeDialog].validate((valid) => {
					if(valid){
						that.$http.post(api,that.$data.formScopeDialog).then(response=>{
								response = response.data;
								that.$message({
						 			type: response.error_no == 0 ? "success" : "error",
						 			message: response.error_no == 0 ? "操作成功" : response.error_info
						 		});
						 		if(response.error_no == 0){
						 			if(this.ismodify){
						 				that.getMetadataList();
						 			}else{
						 				that.resetForm(this.searchForm);
						 			}
						 			
									that.$data.dialogFormVisible = false;
						 		}
						});
					}
				})
				
			},
			checkLogout(error_no){
				if(error_no==88880020){
					location.href="/login";
				}
			},
			getListBySearchData(){
				var param = this.searchForm;
		 		param.page = this.$data.page;
		 		param.limit = this.$data.limit;
				param.update_date_start = Common.getYMDforNowNull(this.init_start_date);
				param.update_time_start = Common.getYMDforNowNull(this.init_start_date,1);
				param.update_date_end = Common.getYMDforNowNull(this.init_end_date);
				param.update_time_end = Common.getYMDforNowNull(this.init_end_date,1);
				
				this.searchDataCache = Common.simpleClone(param);
				this.getMetadataList();
			},
		 	//获取会员列表
			getMetadataList() {
				if(this.loading) return;
				var that = this;
	 			that.$data.loading = true;
	 			this.$http.post('./../queryMetadata',this.searchDataCache).then(response => {
					var jsonData=response.data;
					that.checkLogout(jsonData.error_no);
					if(jsonData.error_no==0){
						if(jsonData.result_list){
							jsonData.result_list = jsonData.result_list.map(function(item){
								item.initTime = Common.formatDateConcat(item.init_date,item.init_time);
								item.updateTime = Common.formatDateConcat(item.update_date,item.update_time);
								return item;
							});
						}
						that.$data.MetadataList = jsonData || {};
						that.checklenOftype2();
					}else{
						that.$message({
							type: "warning",
							message: jsonData.error_info
						});
					}
					that.$data.loading = false;
				});
			},
			//获取查询表单初始数据
			getDefaultData() {
				return {
					//access_token : localStorage.access_token,
					metadata_name : '',			//名称
					alias : '',					//别名
					type : '',					//类别
					status : '',					//状态
					update_user : '',			//操作人
					update_date_start : null,	//创建开始时间 年月日
					update_time_start : null,	//创建开始时间 时分秒
					update_date_end : null,		//创建结束时间 年月日
					update_time_end : null		//创建结束时间 时分秒
				}
			}
		}
	}
</script>
