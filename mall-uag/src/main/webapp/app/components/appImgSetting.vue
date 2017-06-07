<template>
	<div>
		<!-- 面包屑 -->
		<div class="hjh-breadcrumb">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item :to="{ path: '/' }">用户中心</el-breadcrumb-item>
				<el-breadcrumb-item>基本管理</el-breadcrumb-item>
				<el-breadcrumb-item>APP展示图片管理</el-breadcrumb-item>
			</el-breadcrumb>	
		</div>
		<!-- 面包屑 end -->
		<!-- 列表查询表单 -->
		<div class="block-white">
			<el-button type="primary" @click="addNew">新增</el-button>
		</div>
		<!-- 列表查询表单 end -->
		<!-- 会员管理列表 -->
		<div class="block-white">
			<el-table v-loading.body="loading" :data="appimgList.result_list" border stripe style="width: 100%" max-height="460">
				<el-table-column fixed label="序号" width="70" align="center"><template scope="scope">{{scope.$index+1}}</template></el-table-column>
				<el-table-column prop="type" label="图片类型" width="120">
					<template scope="scope">首页{{scope.row.type==1?"占位图":"banner"}}</template>
				</el-table-column>
				<el-table-column prop="sort" label="顺序" align="center" width="80"></el-table-column>
				<el-table-column prop="banner_path" label="图片" width="210">
					<template scope="scope">
						<div @click="showBigImage(scope.row.banner_path)">
							<img :src="'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+scope.row.banner_path">点击查看大图
						</div>
					</template>
				</el-table-column>
				<el-table-column prop="is_skip" label="是否跳转" align="center" width="100">
					<template scope="scope">{{scope.row.is_skip==1?"跳转":"不跳转"}}</template>
				</el-table-column>
				<el-table-column prop="app_url" label="跳转地址" align="center" width="400" :show-overflow-tooltip="showtooltip">
					<template scope="scope">
						<a target="_blank" :href="scope.row.app_url">{{scope.row.app_url}}</a>
					</template>
				</el-table-column>
				<el-table-column prop="create_user_name" label="添加人" align="center" width="140"></el-table-column>
				<el-table-column prop="initTime" label="添加时间" align="center" width="180"></el-table-column>
				<el-table-column prop="update_user_name" label="最后修改人" align="center" width="140"></el-table-column>
				<el-table-column prop="updateTime" label="最后修改时间" align="center" width="180"></el-table-column>
				<el-table-column prop="remark" label="备注" align="center" width="180"></el-table-column>
				<el-table-column prop="status" label="状态" align="center" width="80">
					<template scope="scope">{{scope.row.status==0?"禁用":"启用"}}</template>
				</el-table-column>
				<el-table-column fixed="right" label="操作" width="160">
					<template scope="scope">
						<el-button @click.native.prevent="modify(scope.$index, appimgList.result_list)" type="info">编辑</el-button>
						<el-button @click.native.prevent="setStatus(scope.$index, appimgList.result_list)" type="danger" >{{scope.row.status==0?"启用":"禁用"}}</el-button>
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
		      :total="appimgList.total_num">
		    </el-pagination>
		</div>
		<!-- 翻页组件 end -->
		<!-- 查看与编辑弹窗 -->
		<el-dialog :title="ismodify?'编辑图片展示':'新增图片展示'" v-model="dialogFormVisible" size="tiny">
			<div style="height: 390px; overflow: hidden;">
				<el-form label-position="right" :label-width="formLabelWidth" :model="formScopeDialog">
					<el-form-item label="轮播位置" prop="type" :rules="{ required: true, message: '请选择轮播位置', trigger: 'blur' }">
						<el-select v-model="formScopeDialog.type" placeholder="请选择轮播位置" @change="typeChange" style="width:150px;">
							<el-option label="首页banner" value="0"></el-option>
							<el-option label="首页占位图" value="1"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="图片上传" prop="banner_path" :rules="{ required: true, message: '请选择图片', trigger: 'blur' }">
						<el-input v-model="formScopeDialog.banner_path" type="hidden"></el-input>
						<el-upload
						  class="upload-demo"
						  action="https://jsonplaceholder.typicode.com/posts/"
						  :auto-upload="false"
						  :file-list="filesList">
						  <el-button size="small" type="primary" :disabled="filesList.length==1">点击上传</el-button>
						</el-upload>
					</el-form-item>
					<el-form-item label="排序" prop="sort" >
						<el-input v-model="formScopeDialog.sort" placeholder="排序"></el-input>
					</el-form-item>
					<el-form-item label="是否跳转" prop="is_skip" :rules="{ required: true, message: '请设置是否跳转', trigger: 'blur' }">
						<el-select v-model="formScopeDialog.is_skip" placeholder="请设置是否跳转" @change="is_skipChange" style="width:150px;">
							<el-option label="不跳转" value="0"></el-option>
							<el-option label="跳转" value="1"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="链接地址" >
						<el-input v-model="formScopeDialog.app_url" placeholder="请输入链接地址"></el-input>
					</el-form-item>
					<el-form-item label="备注">
						<el-input v-model="formScopeDialog.remark" placeholder="输入备注"></el-input>
					</el-form-item>
				</el-form>
			</div>
			<div slot="footer" class="dialog-footer" style="text-align: center;">
				<el-button type="primary" @click="updateDialogForm">保 存</el-button>
				<el-button @click="dialogFormVisible = false">取 消</el-button>
			</div>
		</el-dialog>
		<el-dialog title="图片展示" v-model="dialogImageVisible">
			<div style="text-align: center;">
				<img style="display: inline-block; margin: 0 auto; max-width: 100%;" :src="BigImageUrl">
			</div>
			<div slot="footer" class="dialog-footer" style="text-align: center;">
				<el-button @click="dialogImageVisible = false">关 闭</el-button>
			</div>
		</el-dialog>
		<!-- 查看与编辑弹窗 end -->
	</div>
</template>
<style>
	.el-dialog__body .el-form-item__content{
		line-height: 0;
	}
</style>
<script>
	
	export default {
		data() {
			return {
				page : 1,							//列表初始当前页码
				limit : 10,							//每页列表数量
				appimgList : {},						//banner图列表数据缓存
				formScopeDialog:{},					//查看或编辑时对应的数据缓存
				dialogFormVisible:false,				//查看与编辑弹窗显示状态 false为隐藏 true为显示
				dialogImageVisible:false,			//查看大图弹窗显示状态
				formLabelWidth: '90px',				//弹窗中的表单label的宽度
				ismodify:false,						//是否为编辑状态
				loading:false,						//banner列表加载状态 true为加载中 false为加载完毕
				showtooltip : true,					//列表项隐藏时hover显示提示
				BigImageUrl : ""	,					//查看大图，图片链接
				filesList : []
			}
		},
		mounted(){
			document.title = "后台管理系统-APP展示图片管理";
			this.getappimgList();
		},
		methods: {
			//查看大图
			showBigImage(url){
				this.BigImageUrl = 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+url;
				this.dialogImageVisible = true;
			},
			//操作－翻页
			handleCurrentChange(val){
				this.$data.page = val;
				this.getappimgList();
			},
			//选择轮播位置回调
			typeChange(val){
				var text = val == 1 ? "首页占位图" : (val == 0 ? "首页banner" : "");
				setTimeout(function(){
					document.getElementsByClassName("el-dialog__wrapper")[0].getElementsByClassName('el-input__inner')[0].value = text;
				},50)
				
			},
			//设置是否跳转回调
			is_skipChange(val){
				var text = val == 1 ? "跳转" : (val == 0 ? "不跳转" : "");
				setTimeout(function(){
					document.getElementsByClassName("el-dialog__wrapper")[0].getElementsByClassName('el-input__inner')[3].value = text;
				},50)
				
			},
			bindFileChange(){
				var that = this;
				setTimeout(function(){
					$(".el-upload__input").off("change").on("change",function(){
						that.handleFileChange(this);
						//console.log(that.$data.filesList);
					});
				},80);
			},
			handleFileChange(target){
				var that = this;
				setTimeout(function(){
					var li = $(".el-upload-list > li");
					if(li.length>1){
						$(".el-upload-list > li").last().prev().remove();
					}
					var file = target.files[0];
					var reader = new FileReader(); 
					reader.readAsDataURL(file);
					reader.onload = function(e){
						that.formScopeDialog.banner_path = this.result;
						//console.log(that.$data.filesList);
					}
				},50)
			},
			//新增图片项
			addNew(){
				this.$data.formScopeDialog = {
					type : "0",
					is_skip : "0"
				};
				this.$data.filesList= [];
				this.$data.dialogFormVisible = true;
				this.$data.ismodify = false;
				this.typeChange(0);
				this.is_skipChange(0);
				this.bindFileChange();
			},
			//操作－编辑
			modify(index,list){
				this.$data.formScopeDialog = list[index];
				this.$data.dialogFormVisible = true;
				this.$data.ismodify = true;
				
				this.typeChange(this.$data.formScopeDialog.type);
				this.is_skipChange(this.$data.formScopeDialog.is_skip);
				this.bindFileChange();
			},
			//操作－确认(保存)
			updateDialogForm() {
				var api = !this.ismodify ? "./../addBanner" : "./../updateBanner";
				var that = this,form = this.$data.formScopeDialog;
				var param = {
					access_token : localStorage.access_token,
					app_url : form.app_url,
					banner_id : form.banner_id,
					banner_path : form.banner_path,
					is_skip : form.is_skip,
					remark : form.remark,
					sort : form.sort,
					type : form.type
				};
				this.$http.post(api,param).then(response=>{
					var jsondata = response.data;
						that.$message({
				 			type: jsondata.error_no == 0 ? "success" : "error",
				 			message: jsondata.error_no == 0 ? "操作成功" : jsondata.error_info
				 		});
				 		if(jsondata.error_no == 0){
				 			that.getappimgList();
				 		}
				 	that.$data.filesList= [];
					that.$data.dialogFormVisible = false;
				});
			},
			//操作－启用或禁用
			setStatus(index,list){
				var status = list[index].status == "1" ? "禁用" : "启用",
					_status = list[index].status == "1" ? 0 : 1;
				var that = this,
				 	param = {
				 		access_token: localStorage.access_token,
				 		status: _status,
				 		banner_id: list[index].banner_id
				 	};
				 this.$confirm("确定要" + status + "该用户吗？", '', {
				 	confirmButtonText: '确定',
				 	cancelButtonText: '取消',
				 	type: 'warning'
				 }).then(() => {
				 	that.$http.post('./../updateBannerStatus', param).then(response => {
				 		response = response.data;
				 		var type = 'error',
				 			msg = response.error_info;
				 		if(response.error_no == '0') {
				 			type = 'success';
				 			msg = '操作成功';
				 			that.getappimgList();
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
			//重置时间戳格式
			reFormatTime(ymd,hms){
				var reg = /\d+/;
				if(!reg.test(ymd) || !reg.test(hms)){
					return '';
				}
				ymd = String(ymd),hms = String(hms);
				var y = ymd.substring(0,4),
					M = ymd.substring(4,6),
					d = ymd.substring(6,8);
				var H = hms.substring(0,2),
					m = hms.substring(2,4),
					s = hms.substring(4,6);
				return y+"-"+M+"-"+d+" "+H+":"+m+":"+s;
			},
		 	//获取会员列表
			getappimgList() {
				var that = this;
		 		var param = {
		 			page : this.$data.page,
		 			limit : this.$data.limit
		 		};
	 			that.$data.loading = true;
	 			this.$http.post('./../getBannerList',param).then(response => {
					var jsonData=response.data;
					that.checkLogout(jsonData.error_no);
					if(jsonData.error_no==0){
						jsonData.result_list = jsonData.result_list.map(item=>{
							item.initTime = that.reFormatTime(item.init_date,item.init_time);
							item.updateTime = that.reFormatTime(item.update_date,item.update_time);
							return item;
						});
						that.$data.appimgList = jsonData || {};
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
