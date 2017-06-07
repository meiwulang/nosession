<template>
    <div>
    		<!-- 面包屑 -->
		<div class="hjh-breadcrumb">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item :to="{ path: '/' }">用户中心</el-breadcrumb-item>
				<el-breadcrumb-item>APP应用管理</el-breadcrumb-item>
				<el-breadcrumb-item>反馈信息</el-breadcrumb-item>
			</el-breadcrumb>	
		</div>
		<!-- 面包屑 end -->
		<!-- 列表查询表单 -->
		<div class="block-white">
			<el-form :inline="true" :model="searchForm" :ref="searchForm" class="demo-form-inline searchForm">
				<el-form-item>
					<el-select v-model="searchForm.status" placeholder="选择状态" style="width:150px;">
						<el-option label="用户反馈" value="1"></el-option>
						<el-option label="已回复" value="2"></el-option>
						<el-option label="删除" value="0"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-select v-model="searchForm.type" placeholder="选择类别" style="width:150px;">
						<el-option label="产品建议" value="0"></el-option>
						<el-option label="程序问题" value="1"></el-option>
						<el-option label="其它" value="2"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-input v-model="searchForm.feedback_id" placeholder="输入反馈信息ID" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item>
					<el-input v-model="searchForm.content" placeholder="输入反馈信息关键字" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item>
					<el-date-picker v-model="datepickerValue" type="datetimerange" :picker-options="pickerOptions" @change="datepickerChange" placeholder="选择反馈时间查询区间" align="right">
					</el-date-picker>
				</el-form-item>
				<el-form-item>
					<el-input v-model="searchForm.create_user_name" placeholder="输入反馈人姓名" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button class="searchBtn" type="primary" @click="submitForm('searchForm')">查询</el-button>
					<el-button @click="resetForm('searchForm')">清除</el-button>
				</el-form-item>
			</el-form>
		</div>
		<!-- 列表查询表单 end -->
		<!-- APP列表 -->
		<div class="block-white">
			<el-table v-loading.body="loading" :data="feedbackList.result_list" border stripe style="width: 100%" max-height="460">
				<el-table-column fixed label="序号" width="70" align="center"><template scope="scope">{{scope.$index+1}}</template></el-table-column>
				<el-table-column prop="feedback_id" label="ID" width="200"></el-table-column>
				<el-table-column prop="type" label="类别" width="150" align="center">
					<template scope="scope">{{scope.row.type==0?"产品建议":(scope.row.type==1?"程序问题":"其它")}}</template>
				</el-table-column>
				<el-table-column prop="mobile_tel" label="注册手机号" align="center" width="150"></el-table-column>
				<el-table-column prop="content" label="反馈内容" width="500" :show-overflow-tooltip="showtooltip"></el-table-column>
				<el-table-column prop="status" label="状态" align="center" width="100">
					<template scope="scope">{{scope.row.status==0?"删除":(scope.row.status==1?"用户反馈":"已回复")}}</template>
				</el-table-column>
				<el-table-column prop="initDate" label="反馈时间" align="center" width="180"></el-table-column>
				<el-table-column prop="create_user_name" label="反馈人" align="center" width="120"></el-table-column>
				<el-table-column fixed="right" label="操作" width="160">
					<template scope="scope">
						<el-button @click.native.prevent="reply(scope.$index, feedbackList.result_list)" type="info">回复</el-button>
						<el-button @click.native.prevent="deleteR(scope.$index, feedbackList.result_list)" type="danger">删除</el-button>
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
		      :total="feedbackList.totol_num">
		    </el-pagination>
		</div>
		<!-- 翻页组件 end -->
		<!-- 查看与编辑弹窗 -->
		<el-dialog title="回复反馈内容" v-model="dialogFormVisible">
			<div style="height: 230px; overflow: hidden; overflow-y: auto;">
				<el-input type="textarea" :rows="10" placeholder="请输入反馈内容" v-model="formScopeDialog.reply_content"></el-input>

			</div>
			<div slot="footer" class="dialog-footer" style="text-align: center;">
				<el-button type="primary" @click="publish(formScopeDialog)" :disabled="!formScopeDialog.reply_content">确定</el-button>
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
                feedbackList : {}
            }
        },
        created(){
        		document.title = "后台管理系统-反馈信息";
        		this.getListBySearchData();
        },
        methods : {
        		getDefaultSearchForm(){
        			return {
        				status : "",
        				type : "",
        				feedback_id : "",
        				content : "",
        				create_user_name : "",
        				date_start : "",
        				date_end : ""
        			}
        		},
        		getDefaultDialogData(){
        			return {
        				reply_content : ""
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
        		reply(index,list){
        			var item = list[index];
        			this.formScopeDialog = {
        				feedback_id : item.feedback_id,
        				reply_content : ""
        			};
        			this.dialogFormVisible = true;
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
        		publish(){
        			this.$http.post("/feedback/reply",this.formScopeDialog).then(response=>{
        				var response = response.data;
        					this.$message({
				 			type: response.error_no == '0'?"success" : "error",
				 			message: response.error_no == '0'?"回复成功" : response.error_info
				 		});
				 		if(response.error_no == '0') {
				 			this.getAppList();
				 			this.dialogFormVisible = false;
				 		}
        			});
        		},
        		deleteR(index,list){
        			var item = list[index];
        			var param = {
        				//access_token : localStorage.access_token,
        				feedback_id : item.feedback_id
        			};
        			this.$confirm("确定要删除该反馈信息吗？", '', {
				 	confirmButtonText: '确定',
				 	cancelButtonText: '取消',
				 	type: 'warning'
				 }).then(() => {
				 	this.$http.post('/feedback/delete', param).then(response => {
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
        			this.$http.post("/feedback/query",Common.filterParamByEmptyValue(this.searchDataCache)).then(response=>{
        				var jsondata = response.data;
        				if(jsondata.error_no ==0){
        					jsondata.result_list = jsondata.result_list.map(item=>{
        						item.initDate = Common.formatDateConcat(item.init_date,item.init_time);
        						return item;
        					});
        					this.feedbackList = jsondata;
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
