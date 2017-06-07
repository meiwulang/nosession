<template>
	<div>
		<!-- 面包屑 -->
		<div class="hjh-breadcrumb">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item :to="{ path: '/' }">用户中心</el-breadcrumb-item>
				<el-breadcrumb-item>基本管理</el-breadcrumb-item>
				<el-breadcrumb-item>商品品牌管理</el-breadcrumb-item>
			</el-breadcrumb>	
		</div>
		<!-- 面包屑 end -->
		<!-- 列表查询表单 -->
		<div class="block-white">
			<el-form :inline="true" :model="searchForm" ref="searchForm" class="demo-form-inline searchForm">
				<el-form-item>
					<el-input v-model="searchForm.brand_id" placeholder="搜索代码" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item>
					<el-input v-model="searchForm.brand_name" placeholder="搜索品牌" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item>
					<el-select v-model="searchForm.status" placeholder="请选择状态" style="width:150px;">
						<el-option label="启用" value="1"></el-option>
						<el-option label="禁用" value="0"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-select v-model="searchForm.create_user_name" filterable placeholder="搜索创建者" style="width: 150px;">
						<el-option v-for="item in operatorList" :key="item.operator_name" :label="item.operator_name" :value="item.operator_name">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-select v-model="searchForm.is_top" placeholder="APP显示" style="width:150px;">
						<el-option label="显示" value="1"></el-option>
						<el-option label="不显示" value="0"></el-option>
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
				<el-table-column prop="brand_id" label="代码" width="200"></el-table-column>
				<el-table-column prop="brand_name" label="品牌" align="center" width="160"></el-table-column>
				<el-table-column prop="brand_logo" label="logo" width="110">
					<template scope="scope"><img :src="'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+scope.row.brand_logo"></template>
				</el-table-column>
				<el-table-column prop="model_num" label="商品数" align="center" width="150"></el-table-column>
				<el-table-column prop="sort" label="排序" align="center" width="150"></el-table-column>
				<el-table-column prop="createTime" label="创建日期" align="center"  width="180"></el-table-column>
				<el-table-column prop="create_user_name" label="创建者" align="center"  width="100"></el-table-column>
				<el-table-column prop="updateTime" label="最后操作日期" align="center" width="180"></el-table-column>
				<el-table-column prop="update_user_name" label="最后操作者" align="center" width="120"></el-table-column>
				<el-table-column prop="status" label="状态" align="center" width="80">
					<template scope="scope">{{scope.row.status==1?"启用":"禁用"}}</template>
				</el-table-column>
				<el-table-column fixed="right"  label="操作" width="250">
					<template scope="scope">
						<el-button @click.native.prevent="viewrow(scope.$index, ClientList.result_list)" type="info">查看</el-button>
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
		<el-dialog :title="ismodify?'编辑商品品牌':'新增商品品牌'" v-model="dialogFormVisible">
			<div style="height: 320px; overflow: hidden; overflow-y: auto;">
				<el-form label-position="right" :label-width="formLabelWidth" :ref="formScopeDialog" :model="formScopeDialog">
					<el-form-item label="名称" prop="brand_name" :rules="[{ required: true, message: '请输入商品品牌名称', trigger: 'blur' },{ min:1,max:8, message: '请输入1-8个字符', trigger: 'blur' }]">
						<el-input v-model="formScopeDialog.brand_name" :minlength="1" :maxlength="8" :disabled="isview" placeholder="输入商品品牌名称"></el-input>
					</el-form-item>
					<el-form-item label="拼音" prop="pinyin" v-if="ismodify" :rules="{ required: true, message: '请输入拼音' }">
						<el-input :disabled="isview" v-model="formScopeDialog.pinyin" placeholder="拼音"></el-input>
					</el-form-item>
					<el-form-item label="图标" prop="brand_logo" :rules="{ required: true, message: '请设置图标', trigger: 'blur' }">
						<el-input v-model="formScopeDialog.brand_logo" type="hidden"></el-input>
						<el-upload
						  class="upload-demo"
						  action="https://jsonplaceholder.typicode.com/posts/"
						  :auto-upload="false"
						  :on-change="handleFileChange"
						  :on-remove="handleFileRemove"
						  :disabled="isview"
						  :file-list="filesList">
						  <el-button size="small" :disabled="isview" type="primary">点击上传</el-button>
						</el-upload>
					</el-form-item>
					<el-form-item label="" >
						<el-checkbox :disabled="isview" v-model="checked" @change="checkboxChange">APP显示</el-checkbox>
					</el-form-item>
					<el-form-item label="排序" prop="sort" v-if="checked">
						<el-input type="number" :disabled="isview" v-model="formScopeDialog.sort" placeholder="排序"></el-input>
					</el-form-item>
				</el-form>
			</div>
			<div slot="footer" class="dialog-footer" style="text-align: center;">
				<el-button v-if="!isview" type="primary" @click="addModel(formScopeDialog,1)">保存并启用</el-button>
				<el-button v-if="!isview" type="primary" @click="addModel(formScopeDialog,0)">保存并禁用</el-button>
				<el-button v-if="!isview" @click="dialogFormVisible = false">关 闭</el-button>
				<el-button v-if="isview" @click="dialogFormVisible = false">确 定</el-button>
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
				ClientList : {},						//会员列表数据缓存
				formScopeDialog:{},					//查看或编辑时对应的数据缓存
				dialogFormVisible:false,				//查看与编辑弹窗显示状态 false为隐藏 true为显示
				formLabelWidth: '110px',				//弹窗中的表单label的宽度
				activeName:"first",					//弹窗中tabs选项卡初始选中值
				labelPosition : "right",				//弹窗中表单label文字的对齐方式
				ismodify:false,						//是否为编辑状态
				isview : false,
				loading:false,						//列表加载状态 true为加载中 false为加载完毕
		        //是否在APP显示
				checked : false,
				sortcache : 0,
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
			document.title = "后台管理系统-商品品牌管理";
			this.getListBySearchData();
		},
		methods: {
			addNew(){
				this.dialogFormVisible = true;
				this.filesList = [];
				this.checked = false;
				this.formScopeDialog = {};
				this.ismodify = false;
				this.isview = false;
			},
			//获取查询表单初始数据
			getDefaultData() {
				return {
					//access_token: localStorage.access_token,
					brand_id : '',			//搜索代码
					brand_name : '',			//搜索品牌
					create_user_name : '',	//搜索型号
					date_end : '',
					is_top : '',		//有无参数
					status : ''		//搜索类型
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
			checkboxChange(){
				this.sortcache = this.checked ? this.formScopeDialog.sort : 0;
			},
			//多选 启用或禁用 status = 1 启用 0禁用
			updateStatusBatch(status){
				var that = this;
				this.$confirm('确定要'+ (status==1 ? '启用':'禁用') +'该商品品牌吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					var brand_ids = that.multipleSelection.map(item=>{ return item.brand_id; }).join(",");
					that.ajaxUpdateStatusBatch(status,brand_ids,function(){
						that.multipleSelection = [];
				 		that.$refs.multipleTable.clearSelection();
					});
				}).catch(() => {         
				});
			},
			//状态变更提交
			ajaxUpdateStatusBatch(status,brand_ids,handle){
				var that = this;
				var param = {
						access_token : localStorage.access_token,
						status : status,
						brand_id : brand_ids
					};
				that.$http.post("./../goodsbrand/batchstatus",param).then(response=>{
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
			//操作－查询
			submitForm() {
				this.$data.page = 1;
				this.getListBySearchData();
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
				this.searchDataCache.page = val;
				this.getClientList();
			},
			//查看
			viewrow(index,list){
				this.modify(index,list);
				this.isview = true;
			},
			//操作－编辑
			modify(index,list){
				this.$data.dialogFormVisible = true;
				this.$data.ismodify = true;
				this.$data.isview = false;
				var item = Common.simpleClone(list[index]);
				this.filesList = [{
		          name: item.brand_logo,
		          url: 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+item.brand_logo,
		          status: 'finished'
		        }];
				this.$data.formScopeDialog = item;
				this.$data.checked = this.$data.formScopeDialog.is_top==1;
				this.sortcache = this.checked ? this.formScopeDialog.sort : 0;
			},
			//保存
			addModel(refname,status){
				var that = this;
				this.$refs[refname].validate((valid) => {
					if (valid) {
						that.validateCallback(status);
					}
				});
			},
			validateCallback(status){ console.log(this.sortcache);
				var that = this,dialog = {
					status : status,
					is_top : this.checked ? 1 : 0,
					access_token : localStorage.access_token,
					brand_name : this.$data.formScopeDialog.brand_name,
					brand_logo : this.$data.formScopeDialog.brand_logo,
					sort : this.ismodify ? this.sortcache : this.formScopeDialog.sort,
					brand_id : this.$data.formScopeDialog.brand_id,
					pinyin : this.$data.formScopeDialog.pinyin
				};
				if(dialog.brand_logo.indexOf("data:")!=0){
					delete dialog.brand_logo;
				}
				/*
				if(!this.checked && this.ismodify){
					delete dialog.sort;
				}*/
				that.$http.post("./../goodsbrand/queryexist",{is_top:dialog.is_top,sort:dialog.sort});
				var api = this.ismodify?"/../goodsbrand/brand_update":"/../goodsbrand/save";
				that.$http.post(api,dialog).then(response=>{
					var jsondata = response.data;
					that.$message({
			 			type: jsondata.error_no == 0 ? "success" : "error",
			 			message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
			 		});
			 		if(jsondata.error_no == 0){
			 			if(this.ismodify){
			 				that.getClientList();
			 			}else{
			 				that.resetForm(this.searchForm);
			 			}
			 			this.$data.dialogFormVisible = false;
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
								that.formScopeDialog.brand_logo = Common.compressImg(img,0.8,"image/jpeg");
								$(".el-upload-list > li").last().prev().remove();
							}
					}
			},
			handleFileRemove(_file,_filelist){
				if(!_filelist.length){
					this.formScopeDialog.brand_logo = "";
				}
			},
			//操作－启用或禁用
			setStatus(index,list){
				var status = list[index].status==1?0:1,
					brand_id = list[index].brand_id,
					that = this;
				 this.$confirm("确定要" + (status==1?"启用":"禁用") + "该商品品牌吗？", '', {
				 	confirmButtonText: '确定',
				 	cancelButtonText: '取消',
				 	type: 'warning'
				 }).then(() => {
				 	var param = {
				 		access_token : localStorage.access_token,
				 		brand_id : brand_id,
				 		status : status
				 	};
				 	this.$http.post('./../goodsbrand/brand_update_status',param).then(response => {
						var jsondata = response.data;
							that.$message({
					 			type: jsondata.error_no == 0 ? "success" : "error",
					 			message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
					 		});
					 		if(jsondata.error_no == 0){
					 			that.getClientList();
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
			getListBySearchData(){
				var param = this.searchForm;
		 		param.page = this.$data.page;
		 		param.limit = this.$data.limit;
		 		param.date_start = this.reFormatDate(this.$data.init_start_date);
		 		param.date_end = this.reFormatDate(this.$data.init_end_date);
				this.searchDataCache = Common.simpleClone(param);
				this.getClientList();
			},
		 	//获取会员列表
			getClientList() {
				if(this.loading) return;
				var that = this;
	 			that.$data.loading = true;
	 			this.$http.post('./../goodsbrand/queryblur',this.searchDataCache).then(response => {
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
