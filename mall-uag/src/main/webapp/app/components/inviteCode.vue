<template>
	<div>
		<!-- 面包屑 -->
		<div class="hjh-breadcrumb">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item :to="{ path: '/' }">用户中心</el-breadcrumb-item>
				<el-breadcrumb-item>会员管理</el-breadcrumb-item>
				<el-breadcrumb-item>邀请码管理</el-breadcrumb-item>
			</el-breadcrumb>	
		</div>
		<!-- 面包屑 end -->
		<!-- 列表查询表单 -->
		<div class="block-white">
			<el-form :inline="true" :model="searchForm" ref="searchForm" class="demo-form-inline searchForm">
				
				<el-form-item>
					<el-input v-model="searchForm.invite_code" placeholder="邀请码" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item>
					<el-input v-model="searchForm.invite_customer_name" placeholder="请输入名称" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item>
					<el-input v-model="searchForm.business_hotline" placeholder="请输入业务咨询电话" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item>
					<el-col :span="11">
						<el-input v-model="searchForm.invite_code_start" placeholder="邀请码区间查询开始"></el-input>
					</el-col>
					<el-col class="line" :span="2" style="text-align: center;">到</el-col>
					<el-col :span="11">
						<el-input v-model="searchForm.invite_code_end" placeholder="邀请码区间查询结束"></el-input>
					</el-col>
				</el-form-item>
				<el-form-item>
					<el-input v-model="searchForm.remark" placeholder="请输入备注" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item>
					<el-select v-model="searchForm.create_user_name" filterable placeholder="请输入添加人" style="width: 150px;">
						<el-option v-for="item in operatorsList" :key="item.operator_id" :label="item.operator_name" :value="item.operator_name">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<div style="width: 450px;">
					<el-col :span="11">
						<el-date-picker type="date" placeholder="添加开始时间" @change="initStartDateChange" format="yyyy-MM-dd HH:mm:ss" v-model="init_start_date" style="width:100%;"></el-date-picker>
					</el-col>
					<el-col class="line" :span="2" style="text-align: center;">到</el-col>
					<el-col :span="11">
						<el-date-picker type="date" placeholder="添加结束时间" @change="initEndDateChange" format="yyyy-MM-dd HH:mm:ss" v-model="init_end_date" style="width:100%;"></el-date-picker>
					</el-col>
					</div>
				</el-form-item>
				<el-form-item>
					<el-button class="searchBtn" type="primary" @click="submitForm('searchForm')">搜索</el-button>
					<el-button @click="resetForm('searchForm')">清除</el-button>
					<el-button @click="exportTable">导出</el-button>
				</el-form-item>
			</el-form>
		</div>
		<!-- 列表查询表单 end -->
		<div class="block-white">
			<el-button type="primary" @click="addNew">新增</el-button>
		</div>
		<!-- 会员管理列表 -->
		<div class="block-white">
			<el-table v-loading.body="loading" :data="ClientList.result_list" border stripe style="width: 100%" max-height="460">
				<el-table-column fixed label="序号" width="70" align="center"><template scope="scope">{{scope.$index+1}}</template></el-table-column>
				<el-table-column prop="invite_code" label="邀请码" width="120"></el-table-column>
				<el-table-column prop="invite_customer_name" label="名称" width="200"></el-table-column>
				<el-table-column prop="business_hotline" label="业务咨询电话" align="center" width="180"></el-table-column>
				<el-table-column prop="remark" label="备注" width="150"></el-table-column>
				<el-table-column prop="client_num" label="会员数" align="center" width="100"></el-table-column>
				<el-table-column prop="addtime" label="添加时间" align="center" sortable width="180"></el-table-column>
				<el-table-column prop="create_user_name" label="添加人" align="center" width="140"></el-table-column>
				<el-table-column prop="updatetime" label="最后修改时间" align="center" sortable width="180"></el-table-column>
				<el-table-column prop="update_user_name" label="最后修改人" align="center" width="140"></el-table-column>
				<el-table-column fixed="right" label="操作" width="100">
					<template scope="scope">
						<el-button @click.native.prevent="modify(scope.$index, ClientList.result_list)" type="info">编辑</el-button>
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
		<el-dialog :title="ismodify?'编辑邀请码':'新增邀请码'" v-model="dialogFormVisible">
			<div style="height: 320px; overflow: hidden; overflow-y: auto;">
				<el-form :label-position="labelPosition" :label-width="formLabelWidth" :ref="formScopeDialog" :model="formScopeDialog">
					<el-form-item label="邀请码" prop="invite_code"  :rules="[{ required: true, message: '请输入邀请码', trigger: 'blur' },{ min:1,max:10, message: '邀请码长度1-10位', trigger: 'blur' }]">
						<el-input v-model="formScopeDialog.invite_code" :minlength="1" :maxlength="10" :disabled="ismodify" placeholder="输入数字"></el-input>
					</el-form-item>
					<el-form-item label="名称" prop="invite_customer_name" :rules="[{ required: true, message: '请输入名称', trigger: 'blur' },{ min:1,max:10, message: '名称长度1-10位', trigger: 'blur' }]">
						<el-input v-model="formScopeDialog.invite_customer_name" :minlength="1" :maxlength="10" placeholder="输入名称"></el-input>
					</el-form-item>
					<el-form-item label="业务咨询电话" prop="business_hotline"  :rules="[{ required: true, message: '请输入业务咨询电话', trigger: 'blur' },{ min:1,max:20, message: '咨询电话长度1-20位', trigger: 'blur' }]">
						<el-input v-model="formScopeDialog.business_hotline" :minlength="1" :maxlength="20" placeholder="输入业务咨询电话"></el-input>
					</el-form-item>
					<el-form-item label="备注">
						<el-input v-model="formScopeDialog.remark" :minlength="1" :maxlength="10" placeholder="输入备注"></el-input>
					</el-form-item>
				</el-form>
			</div>
			<div slot="footer" class="dialog-footer" style="text-align: center;">
				<el-button type="primary" @click="updateEnterpriseName(formScopeDialog)">保 存</el-button>
				<el-button @click="dialogFormVisible = false">取 消</el-button>
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
				//searchDataCache : {},
				init_start_date : '',				//添加开始时间
				init_end_date : '',					//添加结束时间
				page : 1,							//列表初始当前页码
				limit : 10,							//每页列表数量
				ClientList : {},						//邀请码列表数据缓存
				operatorsList : [],					//添加人列表
				formScopeDialog:{},					//查看或编辑时对应的数据缓存
				dialogFormVisible:false,				//查看与编辑弹窗显示状态 false为隐藏 true为显示
				formLabelWidth: '120px',				//弹窗中的表单label的宽度
				labelPosition : "right",				//弹窗中表单label文字的对齐方式
				ismodify:false,						//是否为编辑状态
				loading:false						//会员列表加载状态 true为加载中 false为加载完毕
			}
		},
		mounted(){
			document.title = "后台管理系统-邀请码管理";
			this.getSearchData();
			this.getOperators();
		},
		methods: {
			//获取添加人列表
			getOperators(){
				var that = this;
				this.$http.post("/getAllOperators",{}).then(response=>{
					var jsondata = response.data;
					that.operatorsList = jsondata.result_list || [];
				});
			},
			//操作－查询
			submitForm() {
				this.$data.page = 1;
				this.getSearchData();
			},
			//操作－重置
			resetForm(formName) {
        			//this.$refs[formName].resetFields();
        			this.$data.searchForm = this.getDefaultData();
        			this.$data.init_start_date = '';
        			this.$data.init_end_date = '';
        			this.submitForm();
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
			//操作－导出
			exportTable() {
				var d = this.$data.searchForm;
				var sd = this.reFormatDate(this.init_start_date),st = this.reFormatDate(this.init_start_date,1),
					ed = this.reFormatDate(this.init_end_date),et = this.reFormatDate(this.init_end_date,1);
				var href = '/invitation_import?invite_code='+d.invite_code+'&invite_customer_name='+d.invite_customer_name+
				'&remark='+d.remark+'&business_hotline='+d.business_hotline
				+'&invite_code_start='+d.invite_code_start+'&invite_code_end='+d.invite_code_end+
				'&start_date='+(sd || '')+'&end_date='+(ed || '')+
				'&start_time='+(st || '')+'&end_time='+(et || '');
				window.open(href,"_blank");
			},
			//操作－翻页
			handleCurrentChange(val){
				this.$data.page = val;
				this.searchDataCache.page = val;
				this.getClientList();
			},
			addNew(){
				this.$data.dialogFormVisible = true;
				this.$data.ismodify = false;
				this.$data.formScopeDialog = this.getFormDialog();
			},
			//操作－编辑
			modify(index,list){
				this.$data.dialogFormVisible = true;
				this.$data.ismodify = true;
				this.$data.formScopeDialog = this.getFormDialog(list[index]);
				var that = this;
				setTimeout(function(){
					that.$refs[that.formScopeDialog].validate();
				},50);
			},
			getFormDialog(data){
				if(!data) 
					return  {
						access_token : this.$data.searchForm.access_token
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
			updateEnterpriseName(refname) {
				var api = this.$data.ismodify ? "./../invitation_edit":"./../invitation_add";
				var that = this,dialog = this.$data.formScopeDialog;
				this.$refs[refname].validate((valid) => {
					if (valid) {
						if(!dialog.invite_code){
							that.$message({type: "error",message: "请输入邀请码"});
							return;
						}
						if(!/^\d+$/.test(dialog.invite_code)){
							that.$message({type: "error",message: "邀请码为数字格式"});
							return;
						}
						if(!dialog.invite_customer_name){
							that.$message({type: "error",message: "请输入名称"});
							return;
						}
						var regTel = /^((\+?86)|(\(\+86\)))?\d{3,4}-\d{7,8}(-\d{3,4})?$/,
							regMobile = /^((\+?86)|(\(\+86\)))?1\d{10}$/;
						if(!regTel.test(dialog.business_hotline) && !regMobile.test(dialog.business_hotline)){
							that.$message({type: "error",message: "咨询电话格式错误"});
							return;
						}
						that.$http.post(api,this.$data.formScopeDialog).then(response=>{
								response = response.data;
								that.$message({
						 			type: response.error_no == 0 ? "success" : "error",
						 			message: response.error_no == 0 ? "操作成功" : (response.error_no.match(/\d/g)?response.error_info:response.error_no)
						 		});
						 		if(response.error_no == 0){
						 			if(this.ismodify){
						 				that.getClientList();
						 			}else{
						 				this.resetForm();
						 			}
						 			
									that.$data.dialogFormVisible = false;
						 		}
						});
					}
				});
				
			},
			checkLogout(error_no){
				if(error_no==88880020){
					location.href="/login";
				}
			},
			getSearchData(){
				var param = Common.simpleClone(this.$data.searchForm);
		 		param.page = this.$data.page;
		 		param.limit = this.$data.limit;
		 		param.start_date = Common.getYMDforNowNull(this.$data.init_start_date);
		 		param.start_time = Common.getYMDforNowNull(this.$data.init_start_date,1);
		 		param.end_date = Common.getYMDforNowNull(this.$data.init_end_date);
		 		param.end_time = Common.getYMDforNowNull(this.$data.init_end_date,1);
		 		this.searchDataCache = Common.simpleClone(param);
				this.getClientList();
			},
		 	//获取会员列表
			getClientList() {
				if(this.loading) return;
				var that = this;
	 			that.$data.loading = true;
	 			this.$http.post('./../invitation_query',this.searchDataCache).then(response => {
					var jsonData=response.data;
					that.checkLogout(jsonData.error_no);
					if(jsonData.error_no==0){
						if(jsonData.result_list){
							jsonData.result_list = jsonData.result_list.map(function(item){
								item.addtime = Common.formatDateConcat(item.init_date,item.init_time);
								item.updatetime = Common.formatDateConcat(item.update_date,item.update_time);
								return item;
							});
						}
						that.$data.ClientList = jsonData || {};
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
					access_token : localStorage.access_token,
					business_hotline : '',		//业务咨询电话
					create_user_name : '',		//添加人
					invite_code : '',			//邀请码
					invite_code_end : '',		//区间结束
					invite_code_start : '',		//区间开始
					invite_customer_name : '',	//名称
					remark : ''					//备注
					//init_start_date : '',		//开始时间
					//init_end_date : ''			//结束时间
				}
			}
		}
	}
</script>
