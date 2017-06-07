<template>
    <div>
    		<!-- 面包屑 -->
		<div class="hjh-breadcrumb">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item :to="{ path: '/' }">用户中心</el-breadcrumb-item>
				<el-breadcrumb-item>基础设置</el-breadcrumb-item>
				<el-breadcrumb-item>收款账号管理</el-breadcrumb-item>
			</el-breadcrumb>	
		</div>
		<!-- 面包屑 end -->
		<div class="block-white">
			<el-button type="primary" @click="addNew">新增</el-button>
		</div>
		<!-- 订单列表 -->
		<div class="block-white">
			<el-table v-loading.body="loading" :data="receiptList.result_list" border stripe style="width: 100%" max-height="460">
				<el-table-column fixed label="序号" width="70" align="center"><template scope="scope">{{scope.$index+1}}</template></el-table-column>
				<el-table-column prop="account_id" label="代码" width="200" :show-overflow-tooltip="showtooltip"></el-table-column>
				<el-table-column prop="bank_name" label="银行名称" width="150"></el-table-column>
				<el-table-column prop="account_logo" label="图标" width="120">
					<template scope="scope">
						<img :src="'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+scope.row.account_logo" />
					</template>
				</el-table-column>
				<el-table-column prop="account_name" label="开户名" width="120"></el-table-column>
				<el-table-column prop="account_bank_name" label="开户行" width="200"></el-table-column>
				<el-table-column prop="account_bank_no" label="账号" width="300" :show-overflow-tooltip="showtooltip"></el-table-column>
				<el-table-column prop="sort" label="排序" width="100" align="center"></el-table-column>
				<el-table-column prop="createTime" label="创建日期" width="180" align="center"></el-table-column>
				<el-table-column prop="create_user_name" label="创建者" width="100" align="center"></el-table-column>
				<el-table-column prop="updateTime" label="最后操作日期" width="180" align="center"></el-table-column>
				<el-table-column prop="update_user_name" label="最后操作者" width="100" align="center"></el-table-column>
				<el-table-column prop="app_display" label="状态" width="100" align="center">
					<template scope="scope">
						{{scope.row.app_display==1?"启用":"禁用"}}
					</template>
				</el-table-column>
				<el-table-column fixed="right" label="操作" width="160" align="center">
					<template scope="scope">
						<el-button @click.native.prevent="setStatus(scope.$index, 0)" type="danger" v-if="scope.row.app_display==1">禁用</el-button>
						
						<el-button @click.native.prevent="modifyAccount(scope.$index, receiptList.result_list)" type="primary" v-if="scope.row.app_display!=1">编辑</el-button>
						<el-button @click.native.prevent="setStatus(scope.$index, 1)" type="success" v-if="scope.row.app_display!=1">启用</el-button>
					</template>
				</el-table-column>
			</el-table>
		</div>
		<!-- 订单列表 end -->
		<!-- 翻页组件 -->
		<div class="block-white">
			<el-pagination
		      @current-change="handleCurrentChange"
		      :current-page="page"
		      :page-size="limit"
		      layout="total, prev, pager, next, jumper"
		      :total="receiptList.total_num">
		    </el-pagination>
		</div>
		<!-- 翻页组件 end -->
		<el-dialog :title="ismodify?'编辑收款账号':'新增收款账号'" v-model="dialogFormVisible">
			<div style="height: 450px; overflow: hidden; overflow-y: auto; padding-right: 20px;">
				<el-form label-position="right" :label-width="formLabelWidth" :ref="scopeDialogForm" :model="scopeDialogForm">
					<el-form-item label="开户名" prop="account_name" :rules="[
						{ required: true, message: '请输入开户名',trigger:'blur,change'},
						{ min:1,max:25, message: '开户名为1 至 25个字符' ,trigger:'blur,change'}
						]">
						<el-input v-model="scopeDialogForm.account_name" :minlength="1" :maxlength="25" placeholder="请输入开户名 1-25字符"></el-input>
					</el-form-item>
					<el-form-item label="银行名称" prop="bank_name" :rules="[
						{ required: true, message: '请输入银行名称' ,trigger:'blur,change'},
						{ min:1,max:10, message: '银行名称为1 至 10个字符' ,trigger:'blur,change'}
						]">
						<el-input v-model="scopeDialogForm.bank_name" :minlength="1" :maxlength="10" placeholder="请输入银行名称 1-10字符"></el-input>
					</el-form-item>
					<el-form-item label="开户行" prop="account_bank_name" :rules="[
						{ required: true, message: '请输入开户行' ,trigger:'blur,change'},
						{ min:2,max:25, message: '开户行为2 至 25个字符' ,trigger:'blur,change'}
						]">
						<el-input v-model="scopeDialogForm.account_bank_name" :minlength="2" :maxlength="25" placeholder="请输入开户行 2-25字符"></el-input>
					</el-form-item>
					<el-form-item label="账号" prop="account_bank_no" :rules="[
						{ required: true, message: '请输入账号' ,trigger:'blur,change'},
						{ min:2,max:25, message: '账号为2 至 25个字符' ,trigger:'blur,change'}
						]">
						<el-input v-model="scopeDialogForm.account_bank_no" :minlength="2" :maxlength="25" placeholder="请输入账号"></el-input>
					</el-form-item>
					<el-form-item label="排序" prop="sort" :rules="{ required: true, message: '请设置排序值' }">
						<el-input type="number" :min="1" :max="9999" v-model="scopeDialogForm.sort" placeholder="请设置排序值" style="width:150px;"></el-input>
					</el-form-item>
					<el-form-item label="状态" prop="app_display" :rules="{ required: true, message: '请设置状态' }">
						<div style="overflow: hidden; margin-top: 10px;">
						<el-radio class="radio" v-model="scopeDialogForm.app_display" label="1">启用</el-radio>
  						<el-radio class="radio" v-model="scopeDialogForm.app_display" label="0">禁用</el-radio>
  						</div>
					</el-form-item>
					
					
					<el-form-item label="图标" prop="account_logo" :rules="{ required: true, message: '请上传图标' }">
						<el-input v-model="scopeDialogForm.account_logo" type="hidden"></el-input>
						<div style="display:inline-block; width: 80px; height: 30px; float: left;" v-if="ismodify">
							<img :width="80" :height="30" :src="account_logo_cache" />
						</div>
						<div style="overflow: hidden; margin-left: 5px; float: left;">
						<el-upload
						  class="upload-demo"
						  action="https://jsonplaceholder.typicode.com/posts/"
						  :auto-upload="false"
						  :on-remove = "handleFileRemove"
						  :on-change="handleFileChange"
						  :file-list="filesList">
						  <el-button size="small" type="primary">{{ismodify?"修改":"点击上传"}}</el-button>
						</el-upload>	
						</div>
						
					</el-form-item>
				</el-form>
			</div>
			<div slot="footer" class="dialog-footer" style="text-align: center;">
				<el-button type="primary" @click="saveModel(scopeDialogForm)">保存</el-button>
				<el-button @click="dialogFormVisible = false">关 闭</el-button>
			</div>
		</el-dialog>
    </div>
</template>
<style>
	.el-dialog__body .el-form-item__content{
		line-height: 0;
	}
	.el-upload__tip{
		margin-top: 10px;
	}
</style>
<script>
	import Common from './../js/common';
    export default{
        data(){
            return{
                page : 1,
                limit : 10,
                receiptList : {},
                showtooltip : true,
                ismodify :false,
                loading : false,
                scopeDialogForm : this.getDefaultDialogForm(),
                formLabelWidth : "120px",
                dialogFormVisible : false,
                filesList : []
            }
        },
        created(){
        		document.title = "后台管理系统-收款账号管理";
        		this.getAccountList();
        },
        methods:{
        		getDefaultDialogForm(){
        			return {
        				account_name : "",
        				bank_name : "",
        				account_bank_name : "",
        				account_bank_no : "",
        				sort : "",
        				app_display : "",
        				account_logo : ""
        			}
        		},
        		filterData(data){
        			return {
        				account_id : data.account_id,
        				account_name : data.account_name,
        				bank_name : data.bank_name,
        				account_bank_name : data.account_bank_name,
        				account_bank_no : data.account_bank_no,
        				sort : data.sort,
        				app_display : data.app_display,
        				account_logo : data.account_logo
        			}
        		},
        		addNew(){
        			this.ismodify = false;
        			this.dialogFormVisible = true;
        			this.filesList = [];
        			this.scopeDialogForm = this.getDefaultDialogForm();
        		},
        		handleCurrentChange(val){
        			this.page = val;
        			this.getAccountList();
        		},
        		modifyAccount(index,list){
        			this.ismodify = true;
        			this.dialogFormVisible = true;
        			var item = list[index];
        			this.scopeDialogForm = Common.simpleClone(item);
        			this.filesList = [
        				{
        					name: item.account_logo,
						url: 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+item.account_logo,
						status: 'finished'
        				}
        			];
        			this.account_logo_cache = 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+ this.scopeDialogForm.account_logo;
        		},
        		handleFileChange(_file,_filelist){
				this.filesList = [_filelist[_filelist.length-1]];
				var that = this;
				var reader = new FileReader(); 
					reader.readAsDataURL(_file.raw);
					reader.onload = function(e){
						var img = new Image();
							img.src = this.result;
							img.onload = function(){
								var result = Common.compressImg(img,0.8,"image/jpeg");
								that.scopeDialogForm.account_logo = result;
								that.account_logo_cache = result;
								$(".el-upload-list > li").last().prev().remove();
							}
					}
			},
			handleFileRemove(_file,_filelist){
				if(!_filelist.length){
					this.scopeDialogForm.account_logo = "";
				}
			},
        		setStatus(index,status){
        			this.$confirm('确定要'+ (status==1?'启用':'禁用') +'该收款账号吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					var item = this.receiptList.result_list[index],
						param = {
							access_token : localStorage.access_token,
							account_id : item.account_id,
							app_display : status
						};
					this.$http.post("/accountreceipt/updateappdisplay",param).then(response=>{
						var data = response.data;
						this.$message({
							type: data.error_no==0 ? "success":"error",
							message: data.error_no==0 ? "操作成功" : data.error_info
						});
						if(data.error_no==0){
							this.getAccountList();
						}
					});
				});
        		},
        		saveModel(formName){
        			this.$refs[formName].validate((valid) => {
					if (valid) {
						var api = this.ismodify? "/accountreceipt/update" : "/accountreceipt/add",
							param = this.ismodify ? this.filterData(this.scopeDialogForm) : this.scopeDialogForm; 
							console.log(param.account_logo.indexOf("data:")<0);
						if(param.account_logo.indexOf("data:")<0 && this.ismodify){
							delete param.account_logo;
						}
						this.$http.post(api,param).then(response=>{
							var jsondata = response.data;
							this.$message({
								type: jsondata.error_no==0 ? "success":"error",
								message: jsondata.error_no==0 ? "操作成功" : jsondata.error_info
							});
							if(jsondata.error_no==0){
								this.getAccountList();
								this.dialogFormVisible = false;
        							this.scopeDialogForm = {};
							}
						});
					}
				});
        		},
        		getAccountList(){
        			this.loading = true;
        			this.$http.post("/accountreceipt/query",{page:this.page,limit:this.limit}).then(res=>{
        				var data = res.data;
        				data.result_list = data.result_list.map(item=>{
        					item.createTime = Common.formatDateConcat(item.create_date,item.create_time);
        					item.updateTime = Common.formatDateConcat(item.update_date,item.update_time);
        					return item;
        				});
        				this.receiptList = data;
        				if(data.error_no==0){
        					
        				}
        				this.loading = false;
        			});
        		}
        }
    }
</script>
