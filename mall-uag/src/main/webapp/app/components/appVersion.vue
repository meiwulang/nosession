<template>
    <div>
    		<!-- 面包屑 -->
		<div class="hjh-breadcrumb">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item :to="{ path: '/' }">用户中心</el-breadcrumb-item>
				<el-breadcrumb-item>APP应用管理</el-breadcrumb-item>
				<el-breadcrumb-item>版本控制</el-breadcrumb-item>
			</el-breadcrumb>	
		</div>
		<!-- 面包屑 end -->
		<!-- 列表查询表单 -->
		<div class="block-white">
			<el-form :inline="true" :model="searchForm" :ref="searchForm" class="demo-form-inline searchForm">
				<el-form-item>
					<el-select v-model="searchForm.version_status" placeholder="请选择版本状态" style="width:150px;">
						<el-option label="启用" value="1"></el-option>
						<el-option label="禁用" value="0"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-select v-model="searchForm.app_type" placeholder="选择APP类型" style="width:150px;">
						<el-option label="IOS" value="1"></el-option>
						<el-option label="Android" value="0"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-select v-model="searchForm.force_update" placeholder="选择更新类型" style="width:150px;">
						<el-option label="强制更新" value="1"></el-option>
						<el-option label="选择更新" value="0"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-input v-model="searchForm.version_title" placeholder="请输入版本名称" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item>
					<el-input v-model="searchForm.version_no" placeholder="请输入版本号" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item>
					<el-date-picker v-model="datepickerValue" type="datetimerange" :picker-options="pickerOptions" @change="datepickerChange" placeholder="选择发布时间范围" align="right">
					</el-date-picker>
				</el-form-item>
				<el-form-item>
					<el-input v-model="searchForm.creater_name" placeholder="请输入发布人" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button class="searchBtn" type="primary" @click="submitForm('searchForm')">查询</el-button>
					<el-button @click="resetForm('searchForm')">清除</el-button>
				</el-form-item>
			</el-form>
		</div>
		<!-- 列表查询表单 end -->
		<div class="block-white">
			<el-button @click="addNewVersion" type="info">发布</el-button>
		</div>
		<!-- APP列表 -->
		<div class="block-white">
			<el-table v-loading.body="loading" :data="appList.result_list" border stripe style="width: 100%" max-height="460">
				<el-table-column fixed label="序号" width="70" align="center"><template scope="scope">{{scope.$index+1}}</template></el-table-column>
				<el-table-column prop="app_type" label="APP类型" width="150"></el-table-column>
				<el-table-column prop="app_name" label="APP名称" width="150">
					<template scope="scope">{{scope.row.app_name==0?"集客":(scope.row.app_name==1?"全知道":"机惠多")}}</template>
				</el-table-column>
				<el-table-column prop="version_title" label="版本名称" align="center" width="150"></el-table-column>
				<el-table-column prop="version_no" label="版本号" align="center" width="150"></el-table-column>
				<el-table-column prop="version_status" label="版本状态" width="150"></el-table-column>
				<el-table-column prop="version_info" label="版本描述" width="350" :show-overflow-tooltip="showtooltip"></el-table-column>
				<el-table-column prop="force_update" label="更新类型" align="center" width="100"></el-table-column>
				<el-table-column prop="package_url" label="APP发布地址" width="300" :show-overflow-tooltip="showtooltip"></el-table-column>
				<el-table-column prop="initDate" label="发布时间" align="center" width="180"></el-table-column>
				<el-table-column prop="update_user" label="发布人" align="center" width="120"></el-table-column>
				<el-table-column fixed="right" label="操作" width="250">
					<template scope="scope">
						<el-button @click.native.prevent="setStatus(scope.$index, appList.result_list)" type="warning" >{{scope.row.version_status=="生效中"?"禁用":"启用"}}</el-button>
						<el-button @click.native.prevent="modify(scope.$index, appList.result_list)" type="info">编辑</el-button>
						<el-button @click.native.prevent="deleteApp(scope.$index, appList.result_list)" type="danger">删除</el-button>
						
					</template>
				</el-table-column>
			</el-table>
		</div>
		<!-- APP列表 end -->
		<!-- 翻页组件 -->
		<div class="block-white">
			<el-pagination
		      @current-change="handleCurrentChange"
		      :current-page="page"
		      :page-size="limit"
		      layout="total, prev, pager, next, jumper"
		      :total="appList.total_num">
		    </el-pagination>
		</div>
		<!-- 翻页组件 end -->
		<!-- 查看与编辑弹窗 -->
		<el-dialog :title="ismodify?'编辑版本':'新增版本'" v-model="dialogFormVisible">
			<div style="height: 450px; overflow: hidden; overflow-y: auto;">
				<el-form label-position="right" :label-width="formLabelWidth" :ref="formScopeDialog" :model="formScopeDialog">
					<el-form-item label="APP类型" prop="app_type" :rules="{ required: true, message: '请选择APP类型' }">
						<el-select v-model="formScopeDialog.app_type" placeholder="请选择APP类型">
							<el-option label="IOS" value="1"></el-option>
							<el-option label="Android" value="0"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="APP名称" prop="app_name" :rules="{ required: true, message: '请选择APP名称' }">
						<el-select v-model="formScopeDialog.app_name" placeholder="请选择APP名称">
							<el-option label="集客" value="0"></el-option>
							<el-option label="全知道" value="1"></el-option>
							<el-option label="机惠多" value="2"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="版本名称" prop="version_title" :rules="[{ required: true, message: '请输入版本名称' },{min:1,max:10,message: '请输入1-10个字符'}]">
						<el-input v-model="formScopeDialog.version_title" :minlength="1" :maxlength="10" placeholder="请输入1-10个字符，可由字母、数字、符号组成"></el-input>
					</el-form-item>
					<el-form-item label="版本号" prop="version_no" :rules="[{ required: true, message: '请输入版本号' },{min:1,max:5,message: '请输入1-5个字符'}]">
						<el-input v-model="formScopeDialog.version_no" :minlength="1" :maxlength="5" placeholder="请输入1-5个字符，可由数字组成"></el-input>
					</el-form-item>
					<el-form-item label="版本描述">
						<el-input type="textarea" v-model="formScopeDialog.version_info" :autosize="{ minRows: 2, maxRows: 4}" :minlength="0" :maxlength="200" placeholder="请输入0-200字符，可由汉字、字母、数字、符号组成"></el-input>
					</el-form-item>
					<el-form-item label="发布地址" prop="package_url" :rules="[{ required: true, message: '请输入发布地址' },{min:1,max:200,message: '请输入1-200个字符'}]">
						<el-input v-model="formScopeDialog.package_url" :minlength="1" :maxlength="200" placeholder="请输入1-200个字符，可由字母、数字、符号组成，并以http://或https://开头"></el-input>
					</el-form-item>
					<el-form-item label="更新类型" prop="force_update" :rules="{ required: true, message: '请选择更新类型' }">
						<el-select v-model="formScopeDialog.force_update" placeholder="请选择更新类型">
							<el-option label="强制更新" value="1"></el-option>
							<el-option label="选择更新" value="0"></el-option>
						</el-select>
					</el-form-item>
				</el-form>
			</div>
			<div slot="footer" class="dialog-footer" style="text-align: center;">
				<el-button type="primary" @click="publish(formScopeDialog)">发布</el-button>
				<el-button @click="dialogFormVisible = false">取消</el-button>
			</div>
		</el-dialog>
		<!-- 查看与编辑弹窗 end -->
    </div>
</template>
<script>
	import Common from './../js/common';
    export default{
        data(){
            return{
            		ismodify:false,
            		dialogFormVisible : false,
            		formLabelWidth : "100px",
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
				},
				showtooltip : true,
                searchForm : this.getDefaultSearchForm(),
                formScopeDialog : this.getDefaultDialogData(),
                datepickerValue : "",
                page : 1,
                limit :10,
                loading : false,
                appList : {}
            }
        },
        created(){
        		document.title = "后台管理系统-版本控制";
        		this.getListBySearchData();
        },
        methods : {
        		getDefaultSearchForm(){
        			return {
        				version_status : "",
        				app_type : "",
        				force_update : "",
        				version_title : "",
        				version_no : "",
        				creater_name : "",
        				date_start : "",
        				date_end : ""
        			}
        		},
        		getDefaultDialogData(){
        			return {
        				app_type : "",
        				app_name : "",
        				version_title : "",
        				version_no : "",
        				version_info : "",
        				package_url : "",
        				force_update : ""
        			}
        		},
        		datepickerChange(val){
        			if(!val){
        				this.searchForm.date_start = "";
        				this.searchForm.date_end = "";
        			}else{
        				var arr = val.split(" - ");
					this.searchForm.date_start = arr[0].match(/\d/g).join("");
					this.searchForm.date_end = arr[1].match(/\d/g).join("");	
        			}
        		},
        		addNewVersion(){
        			this.ismodify = false;
        			this.dialogFormVisible = true;
        			this.formScopeDialog = this.getDefaultDialogData();
        		},
        		modify(index,list){
        			var item = list[index];
        			this.formScopeDialog = {
        				app_type : item.app_type == "Android" ? "0": "1",
        				app_name : item.app_name,
        				version_title : item.version_title,
        				version_no : item.version_no,
        				version_info : item.version_info,
        				package_url : item.package_url,
        				version_id : item.version_id,
        				force_update : item.force_update == "强制更新" ? "1" : "0"
        			};
        			this.ismodify = true;
        			this.dialogFormVisible = true;
        		},
        		publish(formName){
        			var api = this.ismodify ? "/appversion/update":"/appversion/add";
        			this.$refs[formName].validate((valid) => {
        				if(valid){
        					if(this.ismodify){
        						this.formScopeDialog.access_token = localStorage.access_token;
        					}
        					this.$http.post(api,this.formScopeDialog).then(response=>{
        						var jsondata = response.data;
        						this.$message({
					 			type: jsondata.error_no == '0'?"success" : "error",
					 			message: jsondata.error_no == '0'?"操作成功" : jsondata.error_info
					 		});
					 		if(jsondata.error_no == '0') {
					 			if(this.ismodify){
					 				this.getAppList();
					 			}else{
					 				this.resetForm(this.searchForm);
					 			}
					 			
					 			this.dialogFormVisible = false;
					 		}
        					});
        				}
        			})
        		},
        		submitForm(){
        			this.page = 1;
				this.getListBySearchData();
        		},
        		resetForm(formName){
        			this.searchForm = this.getDefaultSearchForm();
        			this.datepickerValue = "";
        			this.submitForm();
        		},
        		handleCurrentChange(val){
        			this.page = val;
				this.searchDataCache.page = val;
        			this.getAppList();
        		},
        		setStatus(index,list){
        			var item = list[index],status = item.version_status == "生效中" ? "禁用" : "启用";
        			var param = {
        				access_token : localStorage.access_token,
        				version_id : item.version_id,
        				version_status : item.version_status == "生效中" ? 0 : 1
        			};
        			this.$confirm("确定要" + status + "该APP版本吗？", '', {
				 	confirmButtonText: '确定',
				 	cancelButtonText: '取消',
				 	type: 'warning'
				 }).then(() => {
				 	this.$http.post('/appversion/updatestatus', param).then(response => {
				 		response = response.data;
				 		this.$message({
				 			type: response.error_no == '0'?"success" : "error",
				 			message: response.error_no == '0'?"操作成功" : response.error_info
				 		});
				 		if(response.error_no == '0') {
				 			this.getAppList();
				 		}
				 	});
				 });
        		},
        		deleteApp(index,list){
        			var item = list[index];
        			var param = {
        				access_token : localStorage.access_token,
        				version_id : item.version_id,
        				status : 0
        			};
        			this.$confirm("确定要删除该APP版本吗？", '', {
				 	confirmButtonText: '确定',
				 	cancelButtonText: '取消',
				 	type: 'warning'
				 }).then(() => {
				 	this.$http.post('/appversion/updatestatus', param).then(response => {
				 		response = response.data;
				 		this.$message({
				 			type: response.error_no == '0'?"success" : "error",
				 			message: response.error_no == '0'?"操作成功" : response.error_info
				 		});
				 		if(response.error_no == '0') {
				 			this.getAppList();
				 		}
				 	});
				 });
        		},
        		getListBySearchData(){
        			var param = Common.deepClone({
        				page : this.page,
        				limit : this.limit,
        				access_token : localStorage.access_token
        			},this.searchForm);
        			this.searchDataCache = Common.simpleClone(param);
				this.getAppList();
        		},
        		getAppList(){
        			if(this.loading) return;
        			this.loading = true;
        			this.$http.post("/appversion/query",Common.filterParamByEmptyValue(this.searchDataCache)).then(response=>{
        				var jsondata = response.data;
        				if(jsondata.error_no ==0){
        					jsondata.result_list = jsondata.result_list.map(item=>{
        						item.initDate = Common.formatDateConcat(item.init_date,item.init_time);
        						return item;
        					});
        					this.appList = jsondata;
        				}else{
        					this.$message({
							type: "warning",
							message: jsonData.error_info
						});
        				}
        				this.loading = false;
        			});
        		}
        }
    }
</script>
