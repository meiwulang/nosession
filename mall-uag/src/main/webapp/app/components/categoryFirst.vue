<template>
	<div>
		<!-- 面包屑 -->
		<div class="hjh-breadcrumb">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item :to="{ path: '/' }">用户中心</el-breadcrumb-item>
				<el-breadcrumb-item>类目管理</el-breadcrumb-item>
				<el-breadcrumb-item>一级类目管理</el-breadcrumb-item>
			</el-breadcrumb>	
		</div>
		<!-- 面包屑 end -->
		<!-- 列表查询表单 -->
		<div class="block-white">
			<el-form :inline="true" :model="searchForm" ref="searchForm" class="demo-form-inline searchForm">
				<el-form-item>
					<el-input v-model="searchForm.categoryName" placeholder="一级类目" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item>
					<el-select v-model="searchForm.status" placeholder="请选择状态" style="width:150px;">
						<el-option label="启用" value="1"></el-option>
						<el-option label="禁用" value="0"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-select v-model="searchForm.creater" filterable placeholder="搜索创建者" style="width: 150px;">
						<el-option v-for="item in operatorList" :key="item.operator_name" :label="item.operator_name" :value="item.operator_name">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-select v-model="searchForm.appDisplay" placeholder="APP显示" style="width:150px;">
						<el-option label="显示" value="true"></el-option>
						<el-option label="不显示" value="false"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<div style="width: 450px;">
					<el-col :span="11">
						<el-date-picker type="date" placeholder="创建开始时间" @change="initStartDateChange" format="yyyy-MM-dd HH:mm:ss" v-model="init_start_date" style="width:100%;"></el-date-picker>
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
			<el-button type="primary" @click="addNew">新增</el-button>
			<el-button type="primary" @click="updateStatusBatch(1)" :disabled="!multipleSelection.length">启用</el-button>
			<el-button type="danger" @click="updateStatusBatch(0)" :disabled="!multipleSelection.length">禁用</el-button>
		</div>
		<div class="block-white">
			<div class="selection-tip">
				<i class="el-icon-warning"></i><span>已选择 {{multipleSelection.length}} 项数据。</span>
			</div>
		</div>
		<!-- 一级类目管理列表 -->
		<div class="block-white">
			<el-table v-loading.body="loading" 
				:data="categoryList.result_list" 
				@selection-change="handleSelectionChange" 
				ref="multipleTable"
				border stripe style="width: 100%" max-height="460">
				<el-table-column fixed type="selection" width="55" class="hjhcheckbox">
					
				</el-table-column>
				<el-table-column label="序号" width="70" align="center"><template scope="scope">{{scope.$index+1}}</template></el-table-column>
				<el-table-column prop="category_id" label="代码" width="200"></el-table-column>
				<el-table-column prop="category_name" label="一级" align="center" width="130"></el-table-column>
				<el-table-column prop="nick_name" label="一级别名" align="center" width="130"></el-table-column>
				<el-table-column prop="icon" label="logo" width="110">
					<template scope="scope"><img :src="'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+scope.row.icon"></template>
				</el-table-column>
				<el-table-column prop="sort" label="APP排序" align="center" width="150"></el-table-column>
				<el-table-column prop="createTime" label="创建日期" align="center"  width="180"></el-table-column>
				<el-table-column prop="creater" label="创建者" align="center"  width="100"></el-table-column>
				<el-table-column prop="updateTime" label="最后操作日期" align="center" width="180"></el-table-column>
				<el-table-column prop="updater" label="最后操作者" align="center" width="120"></el-table-column>
				<el-table-column prop="status" label="状态" align="center" width="80">
					<template scope="scope">{{scope.row.status==1?"启用":"禁用"}}</template>
				</el-table-column>
				<el-table-column fixed="right"  label="操作" width="160">
					<template scope="scope">
						<el-button @click.native.prevent="modify(scope.$index, categoryList.result_list)" type="info">编辑</el-button>
						<el-button @click.native.prevent="setStatus(scope.$index, categoryList.result_list)" type="danger" >{{scope.row.status=="1"?"禁用":"启用"}}</el-button>
					</template>
				</el-table-column>
			</el-table>
		</div>
		<!-- 一级类目管理列表 end -->
		<!-- 翻页组件 -->
		<div class="block-white">
			<el-pagination
		      @current-change="handleCurrentChange"
		      :current-page="page"
		      :page-size="limit"
		      layout="total, prev, pager, next, jumper"
		      :total="categoryList.total_num">
		    </el-pagination>
		</div>
		<!-- 翻页组件 end -->
		<!-- 查看与编辑弹窗 -->
		<el-dialog :title="ismodify?'编辑一级类目':'新增一级类目'" v-model="dialogFormVisible">
			<div style="height: 320px; overflow: hidden; overflow-y: auto;">
				<el-form label-position="right" :label-width="formLabelWidth" :ref="formScopeDialog" :model="formScopeDialog">
					<el-form-item label="一级类目" prop="categoryName" :rules="[{ required: true, message: '请输入一级分类', trigger: 'blur' },{ min:2,max:7, message: '一级分类长度为2 至 7个字符', trigger: 'blur' }]">
						<el-input v-model="formScopeDialog.categoryName" :minlength="2" :maxlength="7" placeholder="一级类目"></el-input>
					</el-form-item>
					<el-form-item label="别名">
						<el-input v-model="formScopeDialog.nickName" :minlength="2" :maxlength="7" placeholder="输入别名"></el-input>
					</el-form-item>
					<el-form-item label="图标" prop="icon" :rules="{ required: true, message: '请设置图标', trigger: 'blur' }">
						<el-input v-model="formScopeDialog.icon" type="hidden"></el-input>
						<el-upload
						  class="upload-demo"
						  action="https://jsonplaceholder.typicode.com/posts/"
						  :auto-upload="false"
						  :on-change="handleFileChange"
						  :on-remove="handleFileRemove"
						  :file-list="filesList">
						  <el-button size="small" type="primary">点击上传</el-button>
						</el-upload>
					</el-form-item>
					<el-form-item label="" >
						<el-checkbox v-model="checked" @change="checkedChange">APP显示</el-checkbox>
					</el-form-item>
					<el-form-item label="排序" v-if="checked">
						<el-input type="number" v-model="formScopeDialog.sort" @change="sortChange" placeholder="排序" style="width:150px;"></el-input>
					</el-form-item>
				</el-form>
			</div>
			<div slot="footer" class="dialog-footer" style="text-align: center;">
				<el-button type="primary" @click="addModel(formScopeDialog)">保存</el-button>
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
				operatorList : [],					//创建者列表
				multipleSelection: [],
				categoryList : {},					//会员列表数据缓存
				formScopeDialog:{},					//查看或编辑时对应的数据缓存
				dialogFormVisible:false,				//查看与编辑弹窗显示状态 false为隐藏 true为显示
				formLabelWidth: '110px',				//弹窗中的表单label的宽度
				activeName:"first",					//弹窗中tabs选项卡初始选中值
				labelPosition : "right",				//弹窗中表单label文字的对齐方式
				ismodify:false,						//是否为编辑状态
				loading:false,						//列表加载状态 true为加载中 false为加载完毕
		        //是否在APP显示
				checked : false,
				filesList : []
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
			document.title = "后台管理系统-一级类目管理";
			this.getSearchData();
		},
		methods: {
			addNew(){
				this.dialogFormVisible = true;
				this.sortCache = null;
				this.filesList = [];
				this.formScopeDialog = {
					categoryName : "",
					access_token : localStorage.access_token,
					icon : '',
					level : '1',
					nickName : '',
					sort : -1
				};
				this.checked = false;
				this.ismodify = false;
			},
			//获取查询表单初始数据
			getDefaultData() {
				return {
					//access_token: localStorage.access_token,
					categoryName : '',	//一级
					appDisplay : '',		//app显示
					creater : '',		//创建者
					endTime : '',		//创建结束时间
					startTime : '',		//创建开始时间
					status : ''			//状态
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
			//切换显示app
			checkedChange(){
				this.formScopeDialog.sort = this.checked ? (this.sortCache || 0) : -1;
			},
			//排序值更改
			sortChange(){
				this.sortCache = this.formScopeDialog.sort;
			},
			//多选状态发生改变
			handleSelectionChange(val) {
				this.multipleSelection = val;
				
			},
			//多选 启用或禁用 status = 1 启用 0禁用
			updateStatusBatch(status){
				var that = this;
				this.$confirm('确定要'+ (status==1 ? '启用':'禁用') +'该类目吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					var categoryIds = that.multipleSelection.map(item=>{ return item.category_id; });
					that.ajaxUpdateStatusBatch(status,categoryIds,function(){
						that.multipleSelection = [];
				 		that.$refs.multipleTable.clearSelection();
					});
				}).catch(() => {         
				});
			},
			//状态变更提交
			ajaxUpdateStatusBatch(status,categoryIds,handle){
				var that = this;
				var param = {
						access_token : localStorage.access_token,
						status : status,
						categoryIds : categoryIds
					};
				that.$http.post("./../json/900513",param).then(response=>{
						var jsondata = response.data;
						that.$message({
				 			type: jsondata.error_no == 0 ? "success" : "error",
				 			message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
				 		});
				 		if(jsondata.error_no == 0){
				 			if(typeof handle == "function"){
				 				handle();
				 			}
				 			that.getcategoryList();
				 		}
					})
			},
			//操作－查询
			submitForm() {
				this.$data.page = 1;
				this.getSearchData();
			},
			//操作－重置
			resetForm(formName) {
        			this.$refs[formName].resetFields();
        			this.$data.searchForm = this.getDefaultData();
        			this.$data.init_start_date = "";
        			this.$data.init_end_date = "";
        			this.submitForm();
			},
			//操作－翻页
			handleCurrentChange(val){
				this.$data.page = val;
				this.searchDataCache.page = (val-1)*this.$data.limit+"";
				this.getcategoryList();
			},
			//操作－编辑
			modify(index,list){
				var item = list[index];
				this.$data.dialogFormVisible = true;
				this.$data.ismodify = true;
				this.sortCache = null;
				this.filesList = [{
		          name: item.icon,
		          url: 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+item.icon,
		          status: 'finished'
		        }];
				this.formScopeDialog = {
					categoryName : item.category_name,
					categoryId : item.category_id,
					access_token : localStorage.access_token,
					icon : item.icon,
					level : '1',
					nickName : item.nick_name,
					sort : item.sort
				};
				if(item.sort > -1){
					this.checked = true;
				}
				var that = this;
				setTimeout(function(){
					that.$refs[that.formScopeDialog].validate();
				},50);
			},
			//保存
			addModel(refname){
				var that = this;
				this.$refs[refname].validate((valid) => {
					if (valid) {
						that.validateCallback(status);
					}
				});
			},
			validateCallback(status){
				this.$data.formScopeDialog.access_token = localStorage.access_token;
				var that = this,dialog = this.$data.formScopeDialog;
				var api = this.ismodify?"/../json/900506":"/../json/900505";
				that.$http.post(api,Common.filterParamByEmptyValue(dialog)).then(response=>{
					var jsondata = response.data;
					that.$message({
			 			type: jsondata.error_no == 0 ? "success" : "error",
			 			message: jsondata.error_no == 0 ? (that.ismodify?"修改成功":"添加成功") : jsondata.error_info
			 		});
			 		if(jsondata.error_no == 0){
			 			if(this.ismodify){
			 				that.getcategoryList();
			 			}else{
			 				this.resetForm(this.searchForm);
			 			}
			 			
			 			that.$data.dialogFormVisible = false;
			 			that.filesList = [];
			 			localStorage.access_token = jsondata.access_token || localStorage.access_token;
			 		}
				})
			},
			//选择文件时
			handleFileChange(_file,_filelist){
				this.filesList = [_filelist[_filelist.length-1]];
				var that = this;
				var reader = new FileReader(); 
					reader.readAsDataURL(_file.raw);
					reader.onload = function(e){
						var img = new Image();
							img.src = this.result;
							img.onload = function(){
								that.formScopeDialog.icon = Common.compressImg(img,0.8,"image/jpeg");
								$(".el-upload-list > li").last().prev().remove();
							}
					}
			},
			handleFileRemove(_file,_filelist){
				if(!_filelist.length){
					this.formScopeDialog.icon = "";
				}
			},
			//操作－启用或禁用
			setStatus(index,list){
				var status = list[index].status==1?0:1,
					categoryId = list[index].category_id,
					that = this;
				 this.$confirm("确定要" + (status==1?"启用":"禁用") + "该类目吗？", '', {
				 	confirmButtonText: '确定',
				 	cancelButtonText: '取消',
				 	type: 'warning'
				 }).then(() => {
				 	var param = {
				 		access_token : localStorage.access_token,
				 		categoryId : categoryId,
				 		status : status
				 	};
				 	this.$http.post('./../json/900517',param).then(response => {
						var jsondata = response.data;
							that.$message({
					 			type: jsondata.error_no == 0 ? "success" : "error",
					 			message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
					 		});
					 		if(jsondata.error_no == 0){
					 			that.getcategoryList();
					 		}
					});
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
			getSearchData(){
				var param = this.$data.searchForm;
		 		param.page = (this.$data.page-1)*this.$data.limit+"";
		 		param.limit = this.$data.limit;
		 		param.startTime = this.reFormatDate(this.$data.init_start_date);
		 		param.endTime = this.reFormatDate(this.$data.init_end_date).replace("000000","235959");
		 		this.searchDataCache = Common.simpleClone(param);
				this.getcategoryList();
			},
		 	//获取会员列表
			getcategoryList() {
				if(this.loading) return;
				var that = this;
	 			that.$data.loading = true;
	 			var param = Common.filterParamByEmptyValue(this.searchDataCache);
	 			this.$http.post('./../json/900507',param).then(response => {
					var jsonData=response.data;
					that.checkLogout(jsonData.error_no);
					if(jsonData.error_no==0){
						jsonData.result_list = jsonData.result_list.map(function(item){
							item.createTime = Common.formatDateConcat(item.init_date,item.init_time);
							item.updateTime = Common.formatDateConcat(item.update_date,item.update_time);
							return item;
						});
						that.$data.categoryList = jsonData || {};
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
