<template>
	<div>
		<!-- 面包屑 -->
		<div class="hjh-breadcrumb">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item :to="{ path: '/' }">用户中心</el-breadcrumb-item>
				<el-breadcrumb-item>会员管理</el-breadcrumb-item>
				<el-breadcrumb-item>会员管理</el-breadcrumb-item>
			</el-breadcrumb>	
		</div>
		<!-- 面包屑 end -->
		<!-- 列表查询表单 -->
		<div class="block-white">
			<el-form :inline="true" :model="searchForm" :ref="searchForm" class="demo-form-inline searchForm">
				<el-form-item>
					<el-select v-model="searchForm.status" placeholder="请选择账号状态" style="width:150px;">
						<el-option label="启用" value="1"></el-option>
						<el-option label="禁用" value="0"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-input v-model="searchForm.nick_name" placeholder="请输入昵称" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item>
					<el-input v-model="searchForm.mobile_tel" placeholder="请输入注册手机号" style="width:150px;"></el-input>
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
					<el-input v-model="searchForm.invite_code" placeholder="邀请码" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item>
					<div style="width: 450px;">
					<el-col :span="11">
						<el-date-picker type="date" placeholder="注册开始时间" @change="initStartDateChange" format="yyyy-MM-dd HH:mm:ss" v-model="init_start_date" style="width:100%;"></el-date-picker>
					</el-col>
					<el-col class="line" :span="2" style="text-align: center;">到</el-col>
					<el-col :span="11">
						<el-date-picker type="date" placeholder="注册结束时间" @change="initEndDateChange" format="yyyy-MM-dd HH:mm:ss" v-model="init_end_date" style="width:100%;"></el-date-picker>
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
		<!-- 会员管理列表 -->
		<div class="block-white">
			<el-table v-loading.body="loading" :data="ClientList.result_list" border stripe style="width: 100%" max-height="460">
				<el-table-column fixed label="序号" width="70" align="center"><template scope="scope">{{scope.$index+1}}</template></el-table-column>
				<el-table-column prop="client_code" label="用户编码" width="150"></el-table-column>
				<el-table-column prop="nick_name" label="昵称" width="150"></el-table-column>
				<el-table-column prop="mobile_tel" label="注册手机号" align="center" width="130"></el-table-column>
				<el-table-column prop="enterprise_short_name" label="单位名称(简称)" width="150"></el-table-column>
				<el-table-column prop="enterprise_address" label="单位地址" width="350"></el-table-column>
				<el-table-column prop="enterprise_linkman" label="单位联系人" align="center" width="140"></el-table-column>
				<el-table-column prop="enterprise_tel" label="联系人电话" align="center" width="140"></el-table-column>
				<el-table-column prop="invite_code" label="邀请码" width="120"></el-table-column>
				<el-table-column prop="init_date" label="注册时间" align="center" sortable width="180"></el-table-column>
				<el-table-column prop="status" label="状态" align="center" width="80">
					<template scope="scope">
						{{scope.row.status == "启用" ? "启用":"禁用"}}
					</template>
				</el-table-column>
				<el-table-column fixed="right" label="操作" width="250">
					<template scope="scope">
						<el-button @click.native.prevent="checkView(scope.$index, ClientList.result_list)" type="warning">查看</el-button>
						<el-button @click.native.prevent="modify(scope.$index, ClientList.result_list)" type="info">编辑</el-button>
						<el-button @click.native.prevent="setStatus(scope.$index, ClientList.result_list)" type="danger" >{{scope.row.status=="启用"?"禁用":"启用"}}</el-button>
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
		<el-dialog title="会员信息" v-model="dialogFormVisible">
			<div style="height: 320px; overflow: hidden; overflow-y: auto;">
				<el-form :label-position="labelPosition" :label-width="formLabelWidth" :model="formScopeDialog">
					<el-tabs v-model="activeName" @tab-click="handleClick">
						<el-tab-pane label="会员信息" name="first">
							<el-form-item label="用户编码">
								<el-input v-model="formScopeDialog.client_code" disabled="disabled"></el-input>
							</el-form-item>
							<el-form-item label="邀请码">
								<el-input v-model="formScopeDialog.invite_code" disabled="disabled"></el-input>
							</el-form-item>
						</el-tab-pane>
						<el-tab-pane label="单位信息" name="second">
							<el-form-item label="单位名称">
								<el-input v-model="formScopeDialog.enterprise_name" disabled="disabled"></el-input>
							</el-form-item>
							<el-form-item label="单位简称">
								<el-input v-model="formScopeDialog.enterprise_short_name" :minlength="1" :maxlength="6" :disabled="!ismodify"></el-input>
							</el-form-item>
							<el-form-item label="单位地址">
								<el-input v-model="formScopeDialog.enterprise_address" disabled="disabled"></el-input>
							</el-form-item>
							<el-form-item label="单位主营">
								<el-input v-model="formScopeDialog.major_business" disabled="disabled"></el-input>
							</el-form-item>
							<el-form-item label="单位联系人">
								<el-input v-model="formScopeDialog.enterprise_linkman" disabled="disabled"></el-input>
							</el-form-item>
							<el-form-item label="联系人电话">
								<el-input v-model="formScopeDialog.enterprise_tel" disabled="disabled"></el-input>
							</el-form-item>
						</el-tab-pane>
						<el-tab-pane label="个人资料" name="third">
							<el-form-item label="昵称">
								<el-input v-model="formScopeDialog.nick_name" disabled="disabled"></el-input>
							</el-form-item>
							<el-form-item label="性别">
								<el-input v-model="formScopeDialog.sex" disabled="disabled"></el-input>
							</el-form-item>
							<el-form-item label="用户地区">
								<el-input v-model="formScopeDialog.address" disabled="disabled"></el-input>
							</el-form-item>
						</el-tab-pane>
						<el-tab-pane label="注册信息" name="fourth">
							<el-form-item label="注册时间">
								<el-input v-model="formScopeDialog.init_date" disabled="disabled"></el-input>
							</el-form-item>
							<el-form-item label="账号状态">
								<el-input v-model="formScopeDialog.status" disabled="disabled"></el-input>
							</el-form-item>
							<el-form-item label="注册手机">
								<el-input v-model="formScopeDialog.mobile_tel" disabled="disabled"></el-input>
							</el-form-item>
						</el-tab-pane>
					</el-tabs>
				</el-form>
			</div>
			<div slot="footer" class="dialog-footer" style="text-align: center;">
				<el-button @click="dialogFormVisible = false" v-if="ismodify">取 消</el-button>
				<el-button type="primary" @click="updateEnterpriseName" v-if="ismodify">确 定</el-button>
				<el-button type="primary" @click="dialogFormVisible = false" v-if="!ismodify">确 定</el-button>
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
				page : 1,							//列表初始当前页码
				limit : 10,							//每页列表数量
				init_start_date : "",				//时间控件－开始时间初始值
				init_end_date : "",					//时间控件－结束时间初始值
				ClientList : {},						//会员列表数据缓存
				formScopeDialog:{},					//查看或编辑时对应的数据缓存
				dialogFormVisible:false,				//查看与编辑弹窗显示状态 false为隐藏 true为显示
				formLabelWidth: '90px',				//弹窗中的表单label的宽度
				activeName:"first",					//弹窗中tabs选项卡初始选中值
				labelPosition : "right",				//弹窗中表单label文字的对齐方式
				ismodify:false,						//是否为编辑状态
				loading:false						//会员列表加载状态 true为加载中 false为加载完毕
			}
		},
		mounted(){
			document.title = "后台管理系统-会员管理";
			this.getSearchData();
		},
		methods: {
			//操作－弹窗中tabs切换
			handleClick(){},
			//操作－查询
			submitForm() {
//				if(this.$data.page==1){
//					this.getClientList();
//				}else{
//					this.$data.page = 1;		//查询从第一页开始
//				}
				this.$data.page = 1;
				this.getSearchData();
			},
			//操作－重置
			resetForm(formName) {
        			//this.$refs[formName].resetFields();
        			this.$data.searchForm = this.getDefaultData();
        			this.init_start_date = "";
        			this.init_end_date = "";
        			this.submitForm();
			},
			getSearchData(){
				var param = this.$data.searchForm;
		 		param.page = this.$data.page;
		 		param.limit = this.$data.limit;
		 		param.start_date = Common.formatDateToNumber(this.$data.init_start_date);
		 		param.end_date = Common.formatDateToNumber(this.$data.init_end_date);
				this.searchDataCache = Common.simpleClone(param);
				this.getClientList();
			},
			//操作－导出
			exportTable() {
				var d = this.$data.searchForm;
				var href = '/export_client_excel?access_token='+localStorage.access_token+'&invite_code='
				+d.invite_code+'&nick_name='+d.nick_name+'&status='+d.status+'&mobile_tel='+d.mobile_tel
				+'&invite_code_start='+d.invite_code_start+'&invite_code_end='+d.invite_code_end+
				'&start_date='+Common.formatDateToNumber(this.init_start_date)+'&end_date='+Common.formatDateToNumber(this.init_end_date)+'&limit=10000&page=1';
				window.open(href,"_blank");
			},
			//操作－翻页
			handleCurrentChange(val){
				this.$data.page = val;
				this.searchDataCache.page = val;
				this.getClientList();
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
			//操作－查看
			checkView(index,list){
				this.$data.dialogFormVisible = true;
				this.$data.ismodify = false;
				this.$data.formScopeDialog = list[index];
				this.$data.activeName = "first";
			},
			//操作－编辑
			modify(index,list){
				this.$data.dialogFormVisible = true;
				this.$data.ismodify = true;
				this.$data.formScopeDialog = Common.simpleClone(list[index]);
				this.$data.activeName = "first";
			},
			//操作－确认(保存)
			updateEnterpriseName() {
				if(!this.formScopeDialog.enterprise_short_name){
					this.$message({
				 		type: "error",
				 		message: "单位简称不能为空"
				 	});
				 	return;
				}
				//非编辑状态 或者 编辑状态下单位简称为空 则不进行提交操作
				if(this.$data.ismodify && this.formScopeDialog.enterprise_short_name){
					var param = {
						access_token : localStorage.access_token,
						client_enterprise_id : this.formScopeDialog.client_enterprise_id,
						enterprise_short_name : this.formScopeDialog.enterprise_short_name
					};
					var that = this;
					this.$http.post("./../updateEnterpriseName",param).then(response=>{
						response = response.data;
						that.$message({
				 			type: response.error_no == 0 ? "success" : "error",
				 			message: response.error_no == 0 ? "操作成功" : response.error_info
				 		});
				 		if(response.error_no == 0){
				 			that.getClientList();
							that.$data.dialogFormVisible = false;
				 		}
					});
				}
			},
			//操作－启用或禁用
			setStatus(index,list){
				var status = list[index].status == "启用" ? "禁用" : "启用",
					_status = list[index].status == "启用" ? 0: 1;
				 var that = this,
				 	param = {
				 		access_token: localStorage.access_token,
				 		status: _status,
				 		client_id: list[index].client_id,
				 		mobile_tel: list[index].mobile_tel
				 	};
				 this.$confirm("确定要" + status + "该用户吗？", '', {
				 	confirmButtonText: '确定',
				 	cancelButtonText: '取消',
				 	type: 'warning'
				 }).then(() => {
				 	that.$http.post('./../updateClientStatus', param).then(response => {
				 		response = response.data;
				 		var type = 'error',
				 			msg = response.error_info;
				 		if(response.error_no == '0') {
				 			type = 'success';
				 			msg = '操作成功';
				 			that.getClientList();
				 		}
				 		that.$message({
				 			type: type,
				 			message: msg
				 		});
				 	});
				 });
			},
			checkLogout(error_no){
				if(error_no==88880020){
					location.href="/login";
				}
			},
		 	//获取会员列表
			getClientList() {
				if(this.loading) return;
				var that = this;
	 			that.$data.loading = true;
	 			this.$http.post('./../getClientList',Common.filterParamByEmptyValue(this.searchDataCache)).then(response => {
					var jsonData=response.data;
					that.checkLogout(jsonData.error_no);
					if(jsonData.error_no==0){
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
					status : '',		//会员状态
					nick_name : '',			//昵称
					mobile_tel : '',			//注册手机号
					invite_code_start : '',	//区间开始
					invite_code_end : '',	//区间结束
					invite_code : ''			//邀请码
				}
			}
		}
	}
</script>
