<template>
    <div>
    		<!-- 面包屑 -->
		<div class="hjh-breadcrumb">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item :to="{ path: '/' }">用户中心</el-breadcrumb-item>
				<el-breadcrumb-item>内容管理</el-breadcrumb-item>
				<el-breadcrumb-item>品牌介绍</el-breadcrumb-item>
			</el-breadcrumb>	
		</div>
		<!-- 面包屑 end -->
		<div class="block-white">
			<el-button type="primary" @click="addNew">新增</el-button>
		</div>
		<!-- 订单列表 -->
		<div class="block-white">
			<el-table v-loading.body="loading" :data="activityList.result_list" border stripe style="width: 100%" max-height="460">
				<el-table-column fixed label="序号" width="70" align="center"><template scope="scope">{{scope.$index+1}}</template></el-table-column>
				<el-table-column prop="brand_name" label="品牌" width="180">
					<template scope="scope">
						<a style="cursor:pointer" @click="checkBrandDetail(scope.$index,activityList.result_list)">{{scope.row.brand_name}}</a>
					</template>
				</el-table-column>
				<el-table-column prop="brand_info_img" label="图片" width="120">
					<template scope="scope">
						<el-button @click.native.prevent="checkImage(scope.$index, activityList.result_list)" type="text">点击查看</el-button>
					</template>
				</el-table-column>
				<el-table-column prop="sort" label="排序" width="80"></el-table-column>
				<el-table-column prop="app_display" label="状态" width="100">
					<template scope="scope">
						{{scope.row.app_display == 1?"已上架":"已下架"}}
					</template>
				</el-table-column>
				<el-table-column prop="remark" label="备注" width="300" :show-overflow-tooltip="showtooltip"></el-table-column>
				<el-table-column prop="reinitTime" label="添加时间" width="180" align="center"></el-table-column>
				<el-table-column prop="create_user" label="添加人" width="180" align="center"></el-table-column>
				<el-table-column prop="reupdateTime" label="最后修改时间" width="180" align="center"></el-table-column>
				<el-table-column prop="update_user" label="最后修改人" width="180" align="center"></el-table-column>
				<el-table-column fixed="right" label="操作" width="140" align="center">
					<template scope="scope">
						<el-button @click.native.prevent="offTheShelf(scope.$index, activityList.result_list)" type="text" v-if="scope.row.state">下架</el-button>
						
						<el-button @click.native.prevent="modifyActivity(scope.$index, activityList.result_list)" type="text" v-if="!scope.row.state">编辑</el-button>
						<el-button @click.native.prevent="onTheShelves(scope.$index, activityList.result_list)" type="text"  v-if="!scope.row.state">上架</el-button>
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
		      :total="activityList.total_num">
		    </el-pagination>
		</div>
		<!-- 翻页组件 end -->
		<el-dialog title="图片展示" v-model="dialogImageVisible">
			<div style="text-align: center;">
				<img style="display: inline-block; margin: 0 auto; max-width: 100%;" :src="BigImageUrl">
			</div>
			<div slot="footer" class="dialog-footer" style="text-align: center;">
				<el-button @click="dialogImageVisible = false">关 闭</el-button>
			</div>
		</el-dialog>
		<el-dialog title="新增品牌介绍" v-model="dialogFormVisibleAdd">
			<div style="height: 570px; overflow: hidden; overflow-y: auto; padding-right: 20px;">
				<el-form label-position="right" :label-width="formLabelWidth" :ref="formScopeDialogAdd" :model="formScopeDialogAdd">
					<el-form-item label="图片上传" prop="brand_info_img" :rules="{ required: true, message: '请上传图片' }">
						<el-input v-model="formScopeDialogAdd.brand_info_img" type="hidden"></el-input>
						<el-upload
						  class="upload-demo"
						  action="https://jsonplaceholder.typicode.com/posts/"
						  :auto-upload="false"
							  :on-change="handleFileChange"
						  :file-list="filesList">
						  <el-button size="small" type="primary">点击上传</el-button>
						  <div class="el-upload__tip" slot="tip">建议尺寸：750*300px 且不超过500kb</div>
						</el-upload>
					</el-form-item>
					<el-form-item label="排序" prop="sort" :rules="{ required: true, message: '请设置排序值' }">
						<el-input type="number" v-model="formScopeDialogAdd.sort" :minlength="1" :maxlength="3" placeholder="排序" style="width:150px;"></el-input>
					</el-form-item>
					<el-form-item label="品牌" prop="brand_id" :rules="{ required: true, message: '请输入品牌' }">
						<el-select v-model="formScopeDialogAdd.brand_id" filterable placeholder="搜索品牌" style="width: 200px;">
							<el-option v-for="item in brandlist" :key="item.brand_id" :label="item.brand_name" :value="item.brand_id">
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="" prop="app_display" :rules="{ required: true, message: '请设置品牌介绍的状态' }">
						<el-radio class="radio" v-model="formScopeDialogAdd.app_display" label="1">上架</el-radio>
  						<el-radio class="radio" v-model="formScopeDialogAdd.app_display" label="0">下架</el-radio>
					</el-form-item>
					<el-form-item label="展示内容">
						<textarea id="dialogEditor"></textarea>
					</el-form-item>
					<el-form-item label="备注" prop="remark">
						<el-input v-model="formScopeDialogAdd.remark" :minlength="1" :maxlength="10" placeholder="备注" style="width:300px;"></el-input>
					</el-form-item>
				</el-form>
			</div>
			<div slot="footer" class="dialog-footer" style="text-align: center;">
				<el-button type="primary" @click="saveModel(formScopeDialogAdd)">保存</el-button>
				<el-button @click="dialogFormVisibleAdd = false">关 闭</el-button>
			</div>
		</el-dialog>
		<el-dialog title="编辑品牌介绍" v-model="dialogFormVisibleUpdate">
			<div style="height: 570px; overflow: hidden; overflow-y: auto; padding-right: 20px;">
				<el-form label-position="right" :label-width="formLabelWidth" :ref="formScopeDialogUpdate" :model="formScopeDialogUpdate">
					<el-form-item label="图片上传" prop="brand_info_img" :rules="{ required: true, message: '请上传图片' }">
						<el-input v-model="formScopeDialogUpdate.brand_info_img" type="hidden"></el-input>
						<el-upload
						  class="upload-demo"
						  action="https://jsonplaceholder.typicode.com/posts/"
						  :auto-upload="false"
						  :on-change="handleFileChange"
						  :file-list="filesList">
						  <el-button size="small" type="primary">点击上传</el-button>
						  <div class="el-upload__tip" slot="tip">建议尺寸：750*300px 且不超过500kb</div>
						</el-upload>
					</el-form-item>
					<el-form-item label="排序" prop="sort" :rules="{ required: true, message: '请设置排序值' }">
						<el-input type="number" v-model="formScopeDialogUpdate.sort" :minlength="1" :maxlength="3" placeholder="排序" style="width:150px;"></el-input>
					</el-form-item>
					<el-form-item label="品牌" prop="brand_id" :rules="{ required: true, message: '请输入品牌' }">
						<el-select v-model="formScopeDialogUpdate.brand_id" filterable placeholder="搜索品牌" style="width: 200px;">
							<el-option v-for="item in brandlist" :key="item.brand_id" :label="item.brand_name" :value="item.brand_id">
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="" prop="app_display" :rules="{ required: true, message: '请设置品牌介绍的状态' }">
						<el-radio class="radio" v-model="formScopeDialogUpdate.app_display" label="1">上架</el-radio>
  						<el-radio class="radio" v-model="formScopeDialogUpdate.app_display" label="0">下架</el-radio>
					</el-form-item>
					<el-form-item label="展示内容">
						<textarea id="dialogEditor"></textarea>
					</el-form-item>
					<el-form-item label="备注" prop="remark">
						<el-input v-model="formScopeDialogUpdate.remark" :minlength="1" :maxlength="10" placeholder="备注" style="width:300px;"></el-input>
					</el-form-item>
				</el-form>
			</div>
			<div slot="footer" class="dialog-footer" style="text-align: center;">
				<el-button type="primary" @click="saveModel(formScopeDialogUpdate)">保存</el-button>
				<el-button @click="dialogFormVisibleUpdate = false">关 闭</el-button>
			</div>
		</el-dialog>
		<el-dialog title="查看品牌介绍" v-model="dialogFormVisibleCheck">
			<iframe :src="ossurl" style="display:block; border:2px solid #d1d1d1; width:375px; height:667px; overflow:hidden; overflow-y:auto; margin:0 auto;"></iframe>
		</el-dialog>
    </div>
</template>
<style>
	.simditor .simditor-body{
		height: 150px;
		min-height: 150px;
		overflow: hidden;
		overflow-y: auto;
		padding: 10px;
	}
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
                activityList : {},								//活动列表数据缓存
                page : 1,										//当前列表页码
                limit : 10,										//列表－每页条数
                loading : false,									//列表加载状态
                activityListApi : "/brandinfo/query"	,				//品牌列表接口地址
                updateappdisplayApi : "/brandinfo/updateappdisplay",//上架和下架接口
                uploadImageApi : "/json/901210",					//上传图片接口地址
                uploadHtmlApi : "/json/901211",					//上传富文本接口地址
                addActivityApi : "/brandinfo/add",					//添加活动接口地址
                updateActivityApi : "/brandinfo/update",				//更新活动接口地址
                getTextDetailApi : "/json/901213",					//获取富文本详情接口地址
                showtooltip : true,								//是否当内容过长被隐藏时显示tooltip true显示 flase不显示
                resourceDomain : 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/',
                dialogImageVisible : false,
                dialogFormVisibleAdd : false,
                dialogFormVisibleUpdate : false,
                dialogFormVisibleCheck : false,
                BigImageUrl : "",
                formLabelWidth : "85px",
                ismodify : false,
                filesList : [],
                formScopeDialogAdd : this.getDefaultDialogForm(),
                formScopeDialogUpdate : {},
                brandlist : [],
                htmlTemplate : "",
                ossurl : ""
            }
        },
        created(){
			var that = this;
			//获取品牌列表
			that.$http.post("./../goodsbrand/queryforweb",{limit:10000,page:1,status:1}).then(response =>{
				var data = response.data;
				if(data.error_no == 0){
					that.$data.brandlist = data.result_list;
				}
			});
			this.$http.get("/dist/data/template.txt").then(res=>{
				this.htmlTemplate = res.data;
			});
		},
		mounted(){
			document.title = "后台管理系统-活动管理";
			this.getactivityList();
		},
		methods : {
			//获取弹窗表单默认数据
			getDefaultDialogForm(){
				return {
                		brand_info_img : "",
                		brand_info_content : "",
                		sort : "",
                		remark : "",
                		brand_id : "",
                		app_display : ""
                }
			},
			//点击新增
			addNew(){
				var that = this;
				this.formScopeDialogAdd = this.getDefaultDialogForm();
				this.filesList = [];
				this.dialogFormVisibleAdd = true;
				this.ismodify = false;
				setTimeout(function(){
					that.editor = new Simditor({
						textarea: $('#dialogEditor'),
						upload:{}
					});
				},100);
			},
			//保存
			saveModel(refname){
				var that = this;
				this.$refs[refname].validate((valid) => {
					if (valid) {
						that.beforesaveActivity();
					}
				});
			},
			//提交前检查
			beforesaveActivity(){
				var text = this.editor.getValue(),that = this;
				var formScopeDialog = that.ismodify ? this.formScopeDialogUpdate : this.formScopeDialogAdd;
				if(!text){
					that.$message({
						type: "error",
						message: "请编写展示内容"
					});
					return;
				}
				if(formScopeDialog.remark && formScopeDialog.remark.length>10){
					that.$message({
						type: "error",
						message: "备注过长 请输入1 至 10个字符"
					});
					return;
				}
				that.replaceImage(text,function(newtext){
					newtext = newtext.replace(/width=\"\d+\"/g,"").replace(/height=\"\d+\"/g,"");
					formScopeDialog.brand_info_content = that.htmlTemplate.replace("{{}}",newtext);
					if(that.ismodify){
						that.updateActivity();
					}else{
						that.AddActivity();
					}
				});
				
			},
			replaceImage(text,callback){
				var reg = /data:\S+\"/g;
				var arr = text.match(reg);
				var count = 0,that = this;
				if(!arr || !arr.length){
					callback(text);
					return;
				}
				arr.map(function(item){
					var src = item.substr(0,item.length-1);
					var img = new Image();
						img.src = src;
						img.onload = function(){
							var result = Common.compressImg(img,0.8,"image/jpeg");
							that.uploadCompressImg(result,function(imgurl){
								count++;
								text = text.replace(src,imgurl);
								if(count == arr.length){
									callback(text);
								}
							});
						}
				});
			},
			uploadCompressImg(result,cb){
				var that = this,
					param = {
						access_token : localStorage.access_token,
						imgType : 19,
						base64Str : result
					}
				that.$http.post(that.uploadImageApi,param).then(response => {
					var jsondata = response.data;
					cb(that.resourceDomain+jsondata.img_uri);
				});
			},
			//新增品牌介绍
			AddActivity(){
				var that = this;
				this.formScopeDialogAdd.access_token = localStorage.access_token;
				that.$http.post(that.addActivityApi,this.formScopeDialogAdd).then(response=>{
					var jsondata = response.data;
					that.$message({
						type: jsondata.error_no==0 ? "success":"error",
						message: jsondata.error_no==0 ? "活动添加成功" : jsondata.error_info
					});
					if(jsondata.error_no==0){
						that.dialogFormVisibleAdd= false;
						that.getactivityList();
						that.formScopeDialogAdd = that.getDefaultDialogForm();
						that.filesList = [];
					}
				});
			},
			//更新品牌介绍
			updateActivity(){
				var that = this,index = this.formScopeDialogUpdate.brand_info_img.indexOf("data:");
				console.log(index);
				if(index<0){
					this.cache_brand_info_img = this.formScopeDialogUpdate.brand_info_img;
					delete this.formScopeDialogUpdate.brand_info_img;
				}
				this.formScopeDialogUpdate.access_token = localStorage.access_token;
				this.formScopeDialogUpdate.status = this.formScopeDialogUpdate.app_display;
				that.$http.post(that.updateActivityApi,this.formScopeDialogUpdate).then(response=>{
					var jsondata = response.data;
					that.$message({
						type: jsondata.error_no==0 ? "success":"error",
						message: jsondata.error_no==0 ? "操作成功" : jsondata.error_info
					});
					if(jsondata.error_no==0){
						that.dialogFormVisibleUpdate = false;
						that.getactivityList();
						that.formScopeDialogUpdate = that.getDefaultDialogForm();
						that.filesList = [];
					}else{
						if(this.cache_brand_info_img){
							this.formScopeDialogUpdate.brand_info_img = this.cache_brand_info_img;
							delete this.cache_brand_info_img;
						}
					}
				});
			},
			//点击下架
			offTheShelf(index,list){
				var that = this,item = list[index];
				this.updateappdisplay(0,item);
			},
			//点击上架
			onTheShelves(index,list){
				var that = this,item = list[index];
				this.updateappdisplay(1,item);
			},
			//上架或下架
			updateappdisplay(app_display,item){
				var that = this;
				this.$confirm('确定要'+ (app_display==1?'上架':'下架') +'该品牌介绍吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					var param = {
						agent_id : item.brand_id,
						access_token : localStorage.access_token,
						app_display : app_display,
						update_user : item.update_user,
						brand_info_id : item.brand_info_id
					}
					that.$http.post(that.updateappdisplayApi,param).then(response=>{
						var jsondata = response.data;
						that.$message({
							type: jsondata.error_no==0 ? "success":"error",
							message: jsondata.error_no==0 ? "操作成功" : jsondata.error_info
						});
						if(jsondata.error_no==0){
							that.getactivityList();
						}
					});
				}).catch(() => {
					
				});
			},
			//点击编辑
			modifyActivity(index,list){
				var item = list[index];
				var that = this;
				this.formScopeDialogUpdate = {
					brand_id : item.brand_id,
					sort : item.sort,
					brand_info_img : item.brand_info_img,
					brand_info_content : item.brand_info_content,
					remark : item.remark,
					app_display : item.app_display,
					brand_info_id : item.brand_info_id
				};
				this.filesList = [{
		          name: item.brand_info_img,
		          url: 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+item.brand_info_img,
		          status: 'finished'
		        }];
				this.dialogFormVisibleUpdate = true;
				this.ismodify = true;
				setTimeout(function(){
					that.editor = new Simditor({
						textarea: $('#dialogEditor'),
						upload:{}
					});
					var content = that.formScopeDialogUpdate.brand_info_content,
						bool = content.indexOf("<body>") > 0 && content.indexOf("</body>") > 0;
					var _content = !bool ? content : content.split("<body>")[1].split("</body>")[0];
					//console.log(_content);
					that.editor.setValue(_content);
				},200);
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
								if(that.ismodify){
									that.formScopeDialogUpdate.brand_info_img = Common.compressImg(img,0.8,"image/jpeg");
								}else{
									that.formScopeDialogAdd.brand_info_img = Common.compressImg(img,0.8,"image/jpeg");
								}
								
								$(".el-upload-list > li").last().prev().remove();
							}
					}
			},
			//图片压缩
			compressImg(img, rate, fileType){
				var cvs = document.createElement('canvas');
				var width=img.width;
				var height = img.height;
				var dic=height/width;
				var width_cvs=width>600?600:width;
				cvs.width = width_cvs;  //图片压缩的标准，这里是按照定款600px计算
				cvs.height = width_cvs * dic;
				var cxt=cvs.getContext("2d");
				cxt.fillStyle="#FFF";
			    cxt.fillRect(0,0,width_cvs,width_cvs*dic);   
				cxt.drawImage(img,0,0,width_cvs,width_cvs*dic);
				return cvs.toDataURL(fileType, rate);
			},
			//翻页回调
			handleCurrentChange(val){
				this.$data.page = val;
				this.getactivityList();
			},
			//点击查看
			checkImage(index,list){
				this.dialogImageVisible = true;
				this.BigImageUrl = this.resourceDomain + list[index].brand_info_img;
			},
			//查看品牌介绍页面
			checkBrandDetail(index,list){
				this.ossurl = "";
				this.ossurl = 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/' + list[index].oss_url;
				this.dialogFormVisibleCheck = true;
			},
			//获取活动列表
			getactivityList(){
				var that = this;
				that.loading = true;
				var param = {
					page : this.page,
					limit : this.limit,
					access_token : localStorage.access_token
				}
				this.$http.post(this.activityListApi,param).then(response=>{
					var jsonData = response.data;
					if(jsonData.error_no==0){
						if(jsonData.result_list){
							jsonData.result_list = jsonData.result_list.map(function(item){
								item.state = item.app_display==1;
								item.reinitTime = Common.formatDateConcat(item.init_date,item.init_time);
								item.reupdateTime = Common.formatDateConcat(item.update_date,item.update_time);
								return item;
							});
						}
						that.$data.activityList = jsonData || {};
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
