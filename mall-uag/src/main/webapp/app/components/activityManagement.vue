<template>
    <div>
    		<!-- 面包屑 -->
		<div class="hjh-breadcrumb">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item :to="{ path: '/' }">用户中心</el-breadcrumb-item>
				<el-breadcrumb-item>内容管理</el-breadcrumb-item>
				<el-breadcrumb-item>专题管理</el-breadcrumb-item>
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
				<el-table-column prop="title" label="专题标题" width="200" :show-overflow-tooltip="showtooltip">
					<template scope="scope">
						<a style="cursor:pointer" @click="checkActiveDetail(scope.$index,activityList.result_list)">{{scope.row.title}}</a>
					</template>
				</el-table-column>
				<el-table-column prop="sort" label="排序" width="80"></el-table-column>
				<el-table-column prop="img" label="图片" width="120">
					<template scope="scope">
						<el-button @click.native.prevent="checkImage(scope.$index, activityList.result_list)" type="text">点击查看</el-button>
					</template>
				</el-table-column>
				<el-table-column prop="goodsnum" label="商品数量" width="120"></el-table-column>
				<el-table-column prop="appDisplay" label="状态" width="100">
					<template scope="scope">
						{{scope.row.appDisplay == 1?"已上架":"未上架"}}
					</template>
				</el-table-column>
				<el-table-column prop="remark" label="备注" width="300" :show-overflow-tooltip="showtooltip"></el-table-column>
				<el-table-column prop="initUser" label="添加人" width="180"></el-table-column>
				<el-table-column prop="reinitTime" label="添加时间" width="180"></el-table-column>
				<el-table-column prop="updateUser" label="最后修改人" width="180"></el-table-column>
				<el-table-column prop="reupdateTime" label="最后修改时间" width="180"></el-table-column>
				<el-table-column fixed="right" label="操作" width="280" align="center">
					<template scope="scope">
						<el-button @click.native.prevent="checkGoods(scope.$index, activityList.result_list)" type="text" v-if="scope.row.state">查看商品</el-button>
						<el-button @click.native.prevent="offTheShelf(scope.$index, activityList.result_list)" type="text" v-if="scope.row.state">下架</el-button>
						
						<el-button @click.native.prevent="modifyActivity(scope.$index, activityList.result_list)" type="text" v-if="!scope.row.state">编辑专题</el-button>
						<el-button @click.native.prevent="goodsManagement(scope.$index, activityList.result_list)" type="text" v-if="!scope.row.state">商品管理</el-button>
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
		<el-dialog :title="ismodify?'编辑专题':'新增专题'" v-model="dialogFormVisible">
			<div style="height: 570px; overflow: hidden; overflow-y: auto; padding-right: 20px;">
				<el-form label-position="right" :label-width="formLabelWidth" :ref="formScopeDialog" :model="formScopeDialog">
					<el-form-item label="专题标题" prop="title" :rules="[{ required: true, message: '请输入专题标题' },{ min:2,max:10, message: '专题标题为2 至 10个字符' }]">
						<el-input v-model="formScopeDialog.title" :minlength="2" :maxlength="10" placeholder="请输入专题标题"></el-input>
					</el-form-item>
					<el-form-item label="图片上传" prop="img" :rules="{ required: true, message: '请上传图片' }">
						<el-input v-model="formScopeDialog.img" type="hidden"></el-input>
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
						<el-input type="number" v-model="formScopeDialog.sort" placeholder="排序" style="width:150px;"></el-input>
					</el-form-item>
					<el-form-item label="展示内容">
						<textarea id="dialogEditor"></textarea>
					</el-form-item>
					<el-form-item label="备注" prop="remark">
						<el-input v-model="formScopeDialog.remark" :minlength="1" :maxlength="10" placeholder="备注" style="width:300px;"></el-input>
					</el-form-item>
				</el-form>
			</div>
			<div slot="footer" class="dialog-footer" style="text-align: center;">
				<el-button type="primary" @click="saveModel(formScopeDialog)">保存</el-button>
				<el-button @click="dialogFormVisible = false">关 闭</el-button>
			</div>
		</el-dialog>
		<el-dialog title="查看专题" v-model="dialogFormVisibleCheck">
			<iframe :src="ossurl" style="display:block; border:2px solid #d1d1d1; width:375px; height:667px; overflow:hidden; overflow-y:auto; margin:0 auto;"></iframe>
		</el-dialog>
    </div>
</template>
<style>
	.simditor .simditor-body{
		height: 250px;
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
                //activityListApi : "/dist/data/activityList.json"	,	//品牌列表接口地址
                activityListApi : "/json/901202"	,				//品牌列表接口地址
                uploadImageApi : "/json/901210",					//上传图片接口地址
                uploadHtmlApi : "/json/901211",					//上传富文本接口地址
                addActivityApi : "/json/901201",					//添加活动接口地址
                updateActivityApi : "/json/901204",				//更新活动接口地址
                getTextDetailApi : "/json/901213",					//获取富文本详情接口地址
                showtooltip : true,								//是否当内容过长被隐藏时显示tooltip true显示 flase不显示
                resourceDomain : 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/',
                dialogImageVisible : false,
                dialogFormVisible : false,
                dialogFormVisibleCheck : false,
                BigImageUrl : "",
                formLabelWidth : "85px",
                ismodify : false,
                filesList : [],
                formScopeDialog : this.getDefaultDialogForm(),
                htmlTemplate : "",
                ossurl : ""
            }
        },
		mounted(){
			document.title = "后台管理系统-专题管理";
			this.getactivityList();
			this.$http.get("/dist/data/template.txt").then(res=>{
				this.htmlTemplate = res.data;
			});
		},
		methods : {
			getDefaultDialogForm(){
				return {
                		title : "",
                		img : "",
                		text : "",
                		sort : "",
                		remark : ""
                }
			},
			addNew(){
				var that = this;
				this.formScopeDialog = this.getDefaultDialogForm();
				this.filesList = [];
				this.dialogFormVisible = true;
				this.ismodify = false;
				setTimeout(function(){
					that.editor = new Simditor({
						textarea: $('#dialogEditor'),
						upload: {
			                //url: '/json/901210', //文件上传的接口地址
			                //params: {imgType:19,access_token:localStorage.access_token}, //键值对,指定文件上传接口的额外参数,上传的时候随文件一起提交
			                //fileKey: 'fileDataFileName', //服务器端获取文件数据的参数名
			                //connectionCount: 3,
			                //leaveConfirm: '正在上传文件'
			            }
					});
					console.log(that.htmlTemplate);
				},50);
			},
			saveModel(refname){
				var that = this;
				this.$refs[refname].validate((valid) => {
					if (valid) {
						if(that.ismodify){
							that.updateActivity();
						}else{
							that.beforeAddActivity();
						}
					}
				});
			},
			beforeAddActivity(){
				//console.log(this.formScopeDialog.img);
				var text = this.editor.getValue(),that = this;
				if(!text){
					that.$message({
						type: "error",
						message: "请编写展示内容"
					});
					return;
				}
				if(this.formScopeDialog.remark && this.formScopeDialog.remark.length>10){
					that.$message({
						type: "error",
						message: "备注过长 请输入1 至 10个字符"
					});
					return;
				}
				//console.log(text.match(/src=\"data:\S+\"/g));
				//console.log(text.replace(/src=\"data:\S+\"/g,"src=\"\""));return;
				this.replaceImage(text,function(newtext){
					newtext = newtext.replace(/width=\"\d+\"/g,"").replace(/height=\"\d+\"/g,"");
					var param={
						title : that.formScopeDialog.title,
						text : that.htmlTemplate.replace("{{}}",newtext)
					};
					that.$http.post(that.uploadHtmlApi,param).then(response =>{
						var jsondata = response.data;
						if(jsondata.error_no==0){
							that.formScopeDialog.text = jsondata.act_text_id;
							that.AddActivity();
						}
					});
				})
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
			AddActivity(){
				var that = this;
				this.formScopeDialog.access_token = localStorage.access_token;
				this.formScopeDialog.appDisplay = 0;
				that.$http.post(that.addActivityApi,this.formScopeDialog).then(response=>{
					var jsondata = response.data;
					that.$message({
						type: jsondata.error_no==0 ? "success":"error",
						message: jsondata.error_no==0 ? "专题添加成功" : jsondata.error_info
					});
					if(jsondata.error_no==0){
						that.dialogFormVisible = false;
						that.getactivityList();
						that.formScopeDialog = that.getDefaultDialogForm();
						that.filesList = [];
					}
				});
			},
			updateActivity(){
				var text = this.editor.getValue(),that = this;
				if(!text){
					that.$message({
						type: "error",
						message: "请编写展示内容"
					});
					return;
				}
				this.replaceImage(text,function(newtext){
					newtext = newtext.replace(/width=\"\d+\"/g,"").replace(/height=\"\d+\"/g,"");
					var param = {
						access_token : localStorage.access_token,
						text : that.htmlTemplate.replace("{{}}",newtext),
						textUri : that.formScopeDialog.text
					}
					that.$http.post("/json/901212",param).then(res=>{
						var _json = res.data;
						if(_json.error_no==0){
							that.formScopeDialog.access_token = localStorage.access_token;
							that.$http.post(that.updateActivityApi,that.formScopeDialog).then(response=>{
								var jsondata = response.data;
								that.$message({
									type: jsondata.error_no==0 ? "success":"error",
									message: jsondata.error_no==0 ? "操作成功" : jsondata.error_info
								});
								if(jsondata.error_no==0){
									that.dialogFormVisible = false;
									that.getactivityList();
									that.formScopeDialog = that.getDefaultDialogForm();
									that.filesList = [];
								}
							});
						}else{
							that.$message({
								type: "error",
								message: jsondata.error_info
							});
						}
					});
				});
				
				
			},
			offTheShelf(index,list){
				var that = this,item = list[index];
				this.$confirm('确定要下架该专题吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					var param = {
						activityId : item.activityId,
						access_token : localStorage.access_token,
						appDisplay : 0
					}
					that.$http.post(that.updateActivityApi,param).then(response=>{
						var jsondata = response.data;
						that.$message({
							type: jsondata.error_no==0 ? "success":"error",
							message: jsondata.error_no==0 ? "操作成功" : jsondata.error_info
						});
						that.getactivityList();
					});
				}).catch(() => {
					
				});
			},
			checkGoods(index,list){
				var activityId = list[index].activityId;
				location.href = "#/activityGoods/"+activityId+"?action=checkGoods";
			},
			onTheShelves(index,list){
				var that = this,item = list[index];
				this.$confirm('确定要上架该专题吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					var param = {
						activityId : item.activityId,
						access_token : localStorage.access_token,
						appDisplay : 1
					}
					that.$http.post(that.updateActivityApi,param).then(response=>{
						var jsondata = response.data;
						that.$message({
							type: jsondata.error_no==0 ? "success":"error",
							message: jsondata.error_no==0 ? "操作成功" : jsondata.error_info
						});
						that.getactivityList();
					});
				}).catch(() => {
					
				});
			},
			modifyActivity(index,list){
				var item = list[index];
				this.formScopeDialog = {
					activityId : item.activityId,
					title : item.title,
					img : item.img,
					sort : item.sort,
					text : item.text,
					remark : item.remark
				};
				this.dialogFormVisible = true;
				this.ismodify = true;
				var that = this;
				this.filesList = [{
		          name: item.img,
		          url: 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/'+item.img,
		          status: 'finished'
		        }];
				that.$http.post(that.getTextDetailApi,{textUri:this.formScopeDialog.text,access_token:localStorage.access_token}).then(response=>{
					
					setTimeout(function(){
						that.editor = new Simditor({
							textarea: $('#dialogEditor'),
							upload: {
				                //url: '/json/901210', //文件上传的接口地址
				               // params: {imgType:19,access_token:localStorage.access_token}, //键值对,指定文件上传接口的额外参数,上传的时候随文件一起提交
				                //fileKey: 'fileDataFileName', //服务器端获取文件数据的参数名
				                //connectionCount: 3,
				                //leaveConfirm: '正在上传文件'
				            }
						});
						var content = response.data.text,
						bool = content.indexOf("<body>") > 0 && content.indexOf("</body>") > 0;
						var _content = !bool ? content : content.split("<body>")[1].split("</body>")[0];
						that.editor.setValue(_content);
					},50);
					//that.htmlcache = response.data || "";
				}).catch(() => {
					setTimeout(function(){
						that.editor = new Simditor({
							textarea: $('#dialogEditor'),
							upload:{}
						});
					},50);
				});
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
								var result = Common.compressImg(img,0.8,"image/jpeg");
								that.uploadImage(result);
								$(".el-upload-list > li").last().prev().remove();
							}
					}
			},
			uploadImage(result){
				var that = this,
					param = {
						access_token : localStorage.access_token,
						imgType : 19,
						base64Str : result
					}
				that.$http.post(that.uploadImageApi,param).then(response => {
					var jsondata = response.data;
					that.$message({
							type: jsondata.error_no == 0 ? "success" : "error",
							message: jsondata.error_no == 0 ? "图片上传成功" : jsonData.error_info
					});
					that.formScopeDialog.img = jsondata.img_uri;
					that.$refs[that.formScopeDialog].validate();
				});
			},
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
			goodsManagement(index,list){
				var activityId = list[index].activityId;
				location.href = "#/activityGoods/"+activityId;
			},
			//翻页回调
			handleCurrentChange(val){
				this.$data.page = val;
				this.getactivityList();
			},
			checkImage(index,list){
				this.dialogImageVisible = true;
				this.BigImageUrl = this.resourceDomain + list[index].img;
			},
			checkActiveDetail(index,list){
				this.ossurl = "";
				this.ossurl = 'http://test-hjh.oss-cn-shanghai.aliyuncs.com/' + list[index].text;
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
								item.state = item.appDisplay==1;
								item.reinitTime = that.reFormatTime(item.initDate,item.initTime);
								item.reupdateTime = that.reFormatTime(item.updateDate,item.updateTime);
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
			},
			//重置时间戳格式 年月日与时分秒拆分
			reFormatDate(date,type){
				var _date = new Date(date);
				if(!date || !_date.getTime()){return '';}
				var y = date.getFullYear(),
					M = date.getMonth()+1,
					d = date.getDate(),
					H = date.getHours(),
					m = date.getMinutes(),
					s = date.getSeconds();
				M = M > 9 ? M : ("0"+M);
				d = d > 9 ? d : ("0"+d);
				H = H > 9 ? H : ("0"+H);
				m = m > 9 ? m : ("0"+m);
				s = s > 9 ? s : ("0"+s);
				return type?(H+m+s):(y+M+d);
			},
			//重置日期格式 将参数合并成 yyyy-MM-dd HH:mm:ss格式
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
			}
		}
    }
</script>
