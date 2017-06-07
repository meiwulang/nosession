<template>
	<div>
		<!-- 面包屑 -->
		<div class="hjh-breadcrumb">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item :to="{ path: '/' }">用户中心</el-breadcrumb-item>
				<el-breadcrumb-item>类目管理</el-breadcrumb-item>
				<el-breadcrumb-item>三级类目管理</el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<!-- 面包屑 end -->
		<!-- 列表查询表单 -->
		<div class="block-white">
			<el-form :inline="true" :model="searchForm" :ref="searchForm" class="demo-form-inline searchForm">
				<el-form-item label="一级">
					<el-select v-model="searchForm.grandName" filterable placeholder="输入匹配" style="width: 150px;" @change="grandChange">
						<el-option v-for="item in grandList" :key="item.category_id" :label="item.category_name" :value="item.category_name">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="二级">
					<el-select v-model="searchForm.fatherName" filterable placeholder="输入匹配" style="width: 150px;">
						<el-option v-for="item in fatherList" :key="item.bid" :label="item.bname" :value="item.bname">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="三级">
					<el-input v-model="searchForm.categoryName" placeholder="模糊查询" style="width:150px;"></el-input>
				</el-form-item>
				<el-form-item label="创建者">
					<el-select v-model="searchForm.creater" filterable placeholder="输入匹配" style="width: 150px;">
						<el-option v-for="item in operatorList" :key="item.operator_name" :label="item.operator_name" :value="item.operator_name">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-select v-model="searchForm.status" placeholder="请选择状态" style="width:150px;">
						<el-option label="启用" value="1"></el-option>
						<el-option label="禁用" value="0"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="APP显示">
					<el-select v-model="searchForm.appDisplay" placeholder="请选择APP状态" style="width:150px;">
						<el-option label="显示" value="true"></el-option>
						<el-option label="不显示" value="false"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="创建日期">
					<el-date-picker v-model="datepickerValue" type="datetimerange" :picker-options="pickerOptions" @change="datepickerChange" placeholder="选择时间范围" align="right">
					</el-date-picker>
				</el-form-item>
				<el-form-item>
					<el-button class="searchBtn" type="primary" @click="submitForm(searchForm)">搜索</el-button>
					<el-button @click="resetForm(searchForm)">清除</el-button>
				</el-form-item>
			</el-form>
		</div>
		<!-- 列表查询表单 end -->
		<div class="block-white">
			<el-button @click="addNewCategory" type="info">新增</el-button>
			<el-button @click="setStatusBySelection(1)" :disabled="!multipleSelection.length" type="info">启用</el-button>
			<el-button @click="setStatusBySelection(0)" :disabled="!multipleSelection.length" type="danger">禁用</el-button>
		</div>
		<div class="block-white">
			<div class="selection-tip">
				<i class="el-icon-warning"></i><span>已选择 {{multipleSelection.length}} 项数据。</span>
			</div>
		</div>
		<!-- 二级类目管理列表 -->
		<div class="block-white">
			<el-table v-loading.body="loading" 
				:data="categoryList.result_list" 
				@selection-change="handleSelectionChange" 
				ref="multipleTable"
				border stripe style="width: 100%" max-height="460">
				<el-table-column fixed type="selection" width="55" class="hjhcheckbox">
					
				</el-table-column>
				<el-table-column label="序号" width="70" align="center"><template scope="scope">{{scope.$index+1}}</template></el-table-column>
				<el-table-column prop="cid" label="代码" width="200"></el-table-column>
				<el-table-column prop="aname" label="一级" align="center" width="130"></el-table-column>
				<el-table-column prop="bname" label="二级" align="center" width="130"></el-table-column>
				<el-table-column prop="cname" label="三级" align="center" width="130"></el-table-column>
				<el-table-column prop="nick_name" label="三级别名" align="center" width="130"></el-table-column>
				<el-table-column prop="icon" label="图片" width="110" align="center">
					<template scope="scope"><img :src="'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+scope.row.icon"></template>
				</el-table-column>
				<el-table-column prop="sort" label="APP排序" align="center" width="110"></el-table-column>
				<el-table-column prop="hot_sort" label="热门排序" width="110" align="center"></el-table-column>
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
		<!-- 二级类目管理列表 end -->
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
		<el-dialog :title="ismodify?'编辑三级类目':'新增三级类目'" v-model="dialogFormVisible">
			<div style="height: 320px; overflow: hidden; overflow-y: auto;">
				<el-form label-position="right" :label-width="formLabelWidth" :ref="formScopeDialog" :model="formScopeDialog">
					<el-row>
						<el-col :span="12">
							<el-form-item label="一级类目" prop="dialogGrandId" :rules="[{ required: true, message: '请选择一级类目', trigger: 'blur' }]">
								<el-select v-model="formScopeDialog.dialogGrandId" @change="grandIdChange" filterable placeholder="输入匹配" style="width: 200px;">
									<el-option v-for="item in grandList" :key="item.category_id" :label="item.category_name" :value="item.category_id">
									</el-option>
								</el-select>
							</el-form-item>
						</el-col>
						<el-col class="line" :span="2" style="text-align: right; line-height: 36px; padding-right: 10px;">别名 </el-col>
						<el-col :span="8">
							<el-input v-model="grandNickName" :readonly="true" :minlength="2" :maxlength="7" placeholder="输入别名"></el-input>
						</el-col>
					</el-row>
					<el-row>
						<el-col :span="12">
							<el-form-item label="二级类目" prop="fatherId" :rules="[{ required: true, message: '请选择二级类目', trigger: 'blur' }]">
								<el-select v-model="formScopeDialog.fatherId" @change="fatherIdChange" filterable placeholder="输入匹配" style="width: 200px;">
									<el-option v-for="item in dialogFatherList" :key="item.category_id" :label="item.category_name" :value="item.category_id">
									</el-option>
								</el-select>
							</el-form-item>
						</el-col>
						<el-col class="line" :span="2" style="text-align: right; line-height: 36px; padding-right: 10px;">别名 </el-col>
						<el-col :span="8">
							<el-input v-model="fatherNickName" :readonly="true" :minlength="2" :maxlength="7" placeholder="输入别名"></el-input>
						</el-col>
					</el-row>
					<el-row>
						<el-col :span="12">
							<el-form-item label="三级类目" :label-width="formLabelWidth" prop="categoryName" :rules="[{ required: true, message: '请输入三级类目', trigger: 'blur' },{ min:2,max:7, message: '二级类目长度为2 至 7个字符', trigger: 'blur' }]">
								<el-input v-model="formScopeDialog.categoryName" :minlength="2" :maxlength="7" placeholder="输入三级类目" style="width: 200px;"></el-input>
							</el-form-item>
						</el-col>
						<el-col class="line" :span="2" style="text-align: right; line-height: 36px; padding-right: 10px;">别名</el-col>
						<el-col :span="8">
							<el-input v-model="formScopeDialog.nickName" :minlength="2" :maxlength="7" placeholder="输入别名"></el-input>
						</el-col>
					</el-row>
					
					<el-form-item :label-width="formLabelWidth" label="图标" prop="icon" :rules="{ required: true, message: '请设置图标', trigger: 'blur' }">
							<el-input v-model="formScopeDialog.icon" type="hidden"></el-input>
							<el-upload
							  class="upload-demo"
							  action="https://jsonplaceholder.typicode.com/posts/"
							  :auto-upload="false"
							  :on-change="handleFileChange"
							  :on-remove="handleRemoveFile"
							  :file-list="filesList">
							  <el-button size="small" type="primary">点击上传</el-button>
							  <div slot="tip" class="el-upload__tip">建议尺寸：750*270px</div>
							</el-upload>
					</el-form-item>
					<el-form-item :label-width="formLabelWidth">
						<el-col :span="4">
							<el-checkbox v-model="checked">APP显示</el-checkbox>
						</el-col>
						<el-col class="line" v-if="checked" :span="4" style="text-align: right; line-height: 20px; padding-right: 10px;">排序 </el-col>
						<el-col :span="8" v-if="checked" style="margin-top: -10px;">
							<el-input type="number" v-model="formScopeDialog.sort" placeholder="排序" style="width:150px;"></el-input>
						</el-col>
					</el-form-item>
					
					<el-form-item v-if="checked" :label-width="formLabelWidth">
						<el-col :span="4">
							<el-checkbox v-model="formScopeDialog.hotable">热门排序</el-checkbox>
						</el-col>
						<el-col class="line" v-if="formScopeDialog.hotable" :span="4" style="text-align: right; line-height: 20px; padding-right: 10px;">热门排序 </el-col>
						<el-col :span="8" v-if="formScopeDialog.hotable" style="margin-top: -10px;">
							<el-input type="number" v-model="formScopeDialog.hotSort" placeholder="排序" style="width:150px;"></el-input>
						</el-col>
					</el-form-item>
					
					<el-form-item :label-width="formLabelWidth" label="热门图标" prop="hotIcon" :rules="{ required: true, message: '请设置图标', trigger: 'blur' }" v-if="checked && formScopeDialog.hotable">
							<el-input v-model="formScopeDialog.hotIcon" type="hidden"></el-input>
							<el-upload
							  class="upload-demo"
							  action="https://jsonplaceholder.typicode.com/posts/"
							  :auto-upload="false"
							  :on-change="handleFileChangehot"
							  :on-remove="handleRemoveFileHot"
							  :file-list="filesListhot">
							  <el-button size="small" type="primary">点击上传</el-button>
							  <div slot="tip" class="el-upload__tip">建议尺寸：750*270px</div>
							</el-upload>
					</el-form-item>
					
				</el-form>
			</div>
			<div slot="footer" class="dialog-footer" style="text-align: center;">
				<el-button type="primary" v-if="!ismodify" @click="saveNewCategory(formScopeDialog)">保存</el-button>
				<el-button type="primary" v-if="ismodify" @click="modifyCategory(formScopeDialog)">保存</el-button>
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
	.el-form-item__content{
		/*position: static;*/
	}
</style>
<script>
	import Common from './../js/common';
	export default {
		data() {
			return {
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
				datepickerValue : [],						//日期区间
				searchForm : this.getDefaultSearchForm(),	//搜索表单
				grandList : [],								//一级类目列表
				fatherList : [],								//二级类目列表
				dialogFatherList:[],							//弹窗中二级类目列表
				operatorList : [],							//创建者列表
				page : 1,									//页码
				limit : 10,									//每页条数
				loading : false,								//加载
				ismodify : false,							//编辑状态
				checked : false,								//app显示
				multipleSelection: [],						//多选集合
				categoryList : {},							//二级类目集合
				dialogFormVisible : false,					//弹窗显示状态
				formLabelWidth : "120px",					//label宽度
				filesList : [],								//上传图片列表
				filesListhot : [],								//上传图片列表
				grandNickName : "",							//一级类目别名
				fatherNickName : "",							//二级类目别名
				formScopeDialog : this.getEmptyDialogForm()	//弹窗表单
			}
		},
		mounted(){
			document.title = "后台管理系统-三级类目管理";
			this.getOperatorList();
			this.getGrandList();
			this.getFatherList();
			this.getListBySearchData();
		},
		methods : {
			//搜索表单初始化
			getDefaultSearchForm(){
				return {
					grandName : "",
					fatherName : "",
					categoryName : "",
					status : "",
					appDisplay : "",
					creater : "",
					startTime : "",
					endTime : ""
				}
			},
			//新建时获取空表单
			getEmptyDialogForm(){
				return {
					dialogGrandId : "",
					access_token : localStorage.access_token,
					categoryName : "",
					fatherId : "",
					hotIcon : "",
					hotSort : -1,
					hotable : false,
					icon : "",
					level : "3",
					nickName : "",
					sort : -1
				}
			},
			//获取创建者列表
			getOperatorList(){
				this.$http.post("/getAllOperators",{}).then(response =>{
					this.operatorList = response.data.result_list;
				});
			},
			//获取一级类目列表
			getGrandList(){
				this.$http.post("/json/900507",{page:0,status:1,limit:10000}).then(response =>{
					this.grandList = response.data.result_list;
				});
			},
			//获取二级类目列表
			getFatherList(){
				this.$http.post("/json/900508",{page:0,limit:10000}).then(response =>{
					this.fatherList = response.data.result_list;
				});
			},
			//获取二级类目列表
			getFatherListById(id){
				this.$http.post("/json/900515",{fatherId : id, status : 1, webUse : true}).then(response =>{
					this.fatherList = response.data.result_list.map(function(item){
						return {
							bid : item.category_id,
							bname :item.category_name
						}
					});
				});
			},
			//选择一级类目
			grandChange(val){
				if(!val){
					this.getFatherList();
					return;
				}
				var id = this.grandList.filter(function(item){
					return item.category_name == val;
				})[0].category_id;
				this.getFatherListById(id);
			},
			//重置
			resetForm(formName){
				this.$refs[formName].resetFields();
				this.searchForm = this.getDefaultSearchForm();
				this.datepickerValue = [];
				this.submitForm();
			},
			//搜索
			submitForm(formName){
				this.$data.page = 1;	
				this.getListBySearchData();
			},
			//日期范围变化
			datepickerChange(val){
				if(val){
					var arr = val.split(" - ");
					this.searchForm.startTime = arr[0].match(/\d/g).join("");
					this.searchForm.endTime = arr[1].match(/\d/g).join("");
				}else{
					this.searchForm.startTime = "";
					this.searchForm.endTime = "";
				}
			},
			//多选状态改变时
			handleSelectionChange(val){
				this.multipleSelection = val;
			},
			//翻页
			handleCurrentChange(val){
				this.$data.page = val;
				this.searchDataCache.page = (val-1)*this.limit;
				this.getcategoryList();
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
			//选择文件时
			handleFileChangehot(_file,_filelist){
				this.filesListhot = [_filelist[_filelist.length-1]];
				var that = this;
				var reader = new FileReader(); 
					reader.readAsDataURL(_file.raw);
					reader.onload = function(e){
						var img = new Image();
							img.src = this.result;
							img.onload = function(){
								that.formScopeDialog.hotIcon = Common.compressImg(img,0.8,"image/jpeg");
								$(".el-upload-list > li").last().prev().remove();
							}
					}
			},
			handleRemoveFile(_file,_filelist){
				if(!_filelist.length){
					this.formScopeDialog.icon = "";
				}
			},
			handleRemoveFileHot(_file,_filelist){
				if(!_filelist.length){
					this.formScopeDialog.hotIcon = "";
				}
			},
			//选择一级类目
			grandIdChange(id){
				this.grandNickName = !id ? "" : (this.grandList.filter(function(item){ return item.category_id == id; })[0].nick_name);
				if(!id){return;}
				this.$http.post("/json/900515",{fatherId:id,status:1,webUse:true}).then(response=>{
					if(response.data.error_no ==0){
						this.dialogFatherList = response.data.result_list;
					}
				});
			},
			//选择二类类目
			fatherIdChange(val){
				if(!val){
					this.fatherNickName = "";
					return;
				}
				var obj = this.dialogFatherList.filter((item)=>{
					return item.category_id == val;
				})[0];
				if(obj){
					this.fatherNickName = this.fatherNickName || obj.nick_name;
				}
			},
			//保存－新建类目
			saveNewCategory(formName){
				this.formScopeDialog.access_token = localStorage.access_token;
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.$http.post("/json/900505",Common.filterParamByEmptyValue(this.formScopeDialog)).then(response=>{
							var jsondata = response.data;
							this.$message({
					 			type: jsondata.error_no == 0 ? "success" : "error",
					 			message: jsondata.error_no == 0 ? "添加成功" : jsondata.error_info
					 		});
					 		if(jsondata.error_no == 0){
					 			this.resetForm(this.searchForm);
					 			this.dialogFormVisible = false;
					 			localStorage.access_token = jsondata.access_token || localStorage.access_token;
					 		}
						});
					}
				});
			},
			//点击编辑
			modify(index,list){
				var item = list[index];
				this.formScopeDialog = {
					access_token : localStorage.access_token,
					dialogGrandId : item.aid,
					categoryId : item.cid,
					categoryName : item.cname,
					//fatherId : item.bid,
					icon : item.icon,
					hotIcon : item.hot_icon,
					level : "3",
					nickName : item.nick_name,
					sort : item.sort,
					hotSort : item.hot_sort,
					hotable : item.hot_sort > -1
				};
				this.grandIdChange(item.aid);
				this.formScopeDialog.fatherId = item.bid;
				this.grandNickName = item.anick_name;
				this.fatherNickName = item.bnick_name;
				this.filesList = [{
		          name: item.icon,
		          url: 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+item.icon,
		          status: 'finished'
		        }];
		        this.filesListhot = [{
		          name: item.hot_icon,
		          url: 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+item.hot_icon,
		          status: 'finished'
		        }];
				this.checked = item.sort > -1;
				this.dialogFormVisible = true;
				this.ismodify = true;
				var that = this;
				setTimeout(function(){
					that.$refs[that.formScopeDialog].validate();
				},50);
			},
			//编辑状态保存
			modifyCategory(formName){
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.$http.post("/json/900506",this.formScopeDialog).then(response=>{
							var jsondata = response.data;
							this.$message({
					 			type: jsondata.error_no == 0 ? "success" : "error",
					 			message: jsondata.error_no == 0 ? "保存成功" : jsondata.error_info
					 		});
					 		if(jsondata.error_no == 0){
					 			this.getcategoryList();
					 			this.dialogFormVisible = false;
					 		}
						});
					}
				});
			},
			//启用禁用
			setStatus(index,list){
				var item = list[index], status = item.status==1?0:1,
					categoryId = item.cid;
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
							this.$message({
					 			type: jsondata.error_no == 0 ? "success" : "error",
					 			message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
					 		});
					 		if(jsondata.error_no == 0){
					 			this.getcategoryList();
					 		}
					});
				 });
			},
			//多选启用禁用
			setStatusBySelection(status){
				var categoryIds = this.multipleSelection.map(function(item){
					return item.cid;
				});
				 this.$confirm("确定要批量" + (status==1?"启用":"禁用"), '', {
				 	confirmButtonText: '确定',
				 	cancelButtonText: '取消',
				 	type: 'warning'
				 }).then(() => {
				 	var param = {
				 		access_token : localStorage.access_token,
				 		categoryIds : categoryIds,
				 		status : status
				 	};
				 	this.$http.post('./../json/900513',param).then(response => {
						var jsondata = response.data;
							this.$message({
					 			type: jsondata.error_no == 0 ? "success" : "error",
					 			message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
					 		});
					 		if(jsondata.error_no == 0){
					 			this.getcategoryList();
					 		}
					});
				 });
			},
			//新建
			addNewCategory(){
				this.dialogFormVisible = true;
				this.ismodify = false;
				this.checked = false;
				//this.dialogFatherList = this.fatherList;
				this.filesList = [];
				this.filesListhot = [];
				this.formScopeDialog = this.getEmptyDialogForm();
			},
			getListBySearchData(){
				var param = Common.deepClone(this.searchForm,{
					page : (this.page-1)*this.limit,
					limit : this.limit
				});
				this.searchDataCache = Common.simpleClone(param);
				this.getcategoryList();
			},
			//获取类目列表
			getcategoryList(){
				if(this.loading) return;
				this.loading = true;
				this.$http.post("/json/900511",Common.filterParamByEmptyValue(this.searchDataCache)).then(response => {
					var jsondata = response.data;
					if(jsondata.error_no !=0){
						this.$message({
							type: "warning",
							message: jsondata.error_info
						});
					}else{	
						jsondata.result_list = jsondata.result_list.map((item)=>{
							item.createTime = Common.formatDateConcat(item.init_date,item.init_time);
							item.updateTime = Common.formatDateConcat(item.update_date,item.update_time);
							return item;
						});
						this.categoryList = jsondata;
					}
					
					this.loading = false;
					
				});
			}
		}
	}
</script>