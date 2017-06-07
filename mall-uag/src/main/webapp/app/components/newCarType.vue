<template>
	<div>
		<!-- 面包屑 -->
		<div class="hjh-breadcrumb">
			<el-breadcrumb separator="/">
				<el-breadcrumb-item :to="{ path: '/' }">用户中心</el-breadcrumb-item>
				<el-breadcrumb-item>机型管理</el-breadcrumb-item>
				<el-breadcrumb-item>新增机型</el-breadcrumb-item>
			</el-breadcrumb>	
		</div>
		<!-- 面包屑 end -->
		<!-- 列表查询表单 -->
		<div class="block-white">
			<div class="newCarTypeGroup">新增机型</div>
			<el-form :model="newCarTypeForm" :ref="newCarTypeForm" :rules="rules" label-width="100px" class="demo-form-inline">
				
				<el-form-item label="机型品牌"  prop="carmodelBrand" :rules="{required: true, message: '请选择机型品牌', trigger: 'blur,change'}">
					<el-select v-model="newCarTypeForm.carmodelBrand" filterable placeholder="请选择机型品牌" @change="brandChange" style="width: 200px;">
						<el-option v-for="item in brandlist" :key="item.brand_id" :label="item.brand_name" :value="item.brand_id">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="类型"  prop="carMetadata" :rules="{required: true, message: '请选择类型', trigger: 'blur,change'}">
					<el-select v-model="newCarTypeForm.carMetadata" placeholder="请选择类型" @change="metaChange" style="width: 200px;">
						<el-option v-for="item in metalist" :key="item.metadata_id" :label="item.metadata_name" :value="item.metadata_id">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="型号"  prop="carModelNams" :rules="[{required: true, message: '请输入型号', trigger: 'blur'},
						{ min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }]">
					<el-input v-model="newCarTypeForm.carModelNams" :minlength="2" :maxlength="10" placeholder="请输入型号（2-10字数限制）" style="width: 200px;"></el-input>
				</el-form-item>
				<el-form-item label="" >
					<el-checkbox v-model="checked">APP显示</el-checkbox>
				</el-form-item>
			</el-form>
			<div class="newCarTypeGroup">参数设置</div>
			
			<el-form label-width="100px" :model="addParamForm" :ref="addParamForm" class="demo-form-inline" style="width: 660px;">
				<el-row v-for="(parameter,index) in addParamForm.carParameters">
					<el-col :span="11">
						<el-form-item label="型号" :prop="'carParameters.'+index+'.car_params_name'" :rules="[
				      { required: true, message: '请输入型号', trigger: 'blur' },
				      { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
				    ]">
							<el-input v-model="parameter.car_params_name" :minlength="2" :maxlength="50" placeholder="输入（2-50字数限制）" style="width: 200px;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="10">
						<el-form-item label="值"  label-width="40px">
							<el-input v-model="parameter.car_params_value" :minlength="2" :maxlength="50" placeholder="输入（2-50字数限制）" style="width: 200px;"></el-input>
						</el-form-item>
					</el-col>
					<el-col :span="2" align="center">
						<el-button @click="deleteParam(parameter)" :plain="true" type="danger">删除</el-button>
					</el-col>
				</el-row>
				<div class="addParameterBtn" @click="addCarTypeParameter"><i class="el-icon-plus"></i> 添加参数</div>
			</el-form>
			<div class="block-white">
				<el-button type="primary" @click="addCarModel(1)">保存并启用</el-button>
				<el-button type="primary" @click="addCarModel(0)">保存并禁用</el-button>
			</div>
		</div>
	</div>
</template>
<style>
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
	
	export default {
		data() {
			return {
				//新增机型表单
				newCarTypeForm : {
					carmodelBrand : '',		//机型品牌
					carMetadata : '',		//类型
					carModelNams : ''		//型号
				},
				//保存时提交的表单
				submitForm : {
					access_token : localStorage.access_token
				},
				//是否在APP显示
				checked : false,
				//验证规则
				rules : {
					//机型品牌的验证规则
					carmodelBrand : [
						{required: true, message: '请选择机型品牌', trigger: 'blur,change'}
					],
					//类型验证规则
					carMetadata : [
						{required: true, message: '请选择类型', trigger: 'blur,change'}
					],
					//型号的验证规则
					carModelNams : [
						{required: true, message: '请输入型号', trigger: 'blur'},
						{ min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
					]
				},
				//品牌列表
				brandlist : [],
				//类型列表
				metalist : [],
				//参数设置表单
				addParamForm: {
		        		carParameters : []
		        },
		        api : "./../addCarModel"
			}
		},
		created(){
			var that = this;
			//获取品牌列表
			that.$http.post("./../carbrand/queryforweb",{limit:10000,page:1,status:1}).then(response =>{
				var data = response.data;
				if(data.error_no == 0){
					that.$data.brandlist = data.result_list;
				}
			});
			//获取类型列表
			that.$http.post("./../queryMetadata",{limit:10000,page:1,status:1,type:1}).then(response =>{
				var data = response.data;
				if(data.error_no == 0){
					that.$data.metalist = data.result_list;
				}
			});
		},
		mounted(){
			document.title = "后台管理系统-新增机型";
		},
		methods: {
			//切换机型品牌后回调
			brandChange(val){
				var obj = this.$data.brandlist.filter(function(item){
					return item.brand_id == val;
				})[0];
				document.getElementsByClassName('el-input__inner')[0].value = obj.brand_name;
				this.$data.submitForm.brand_id = obj.brand_id;
				this.$data.submitForm.brand_name = obj.brand_name;
			},
			//切换类型后回调
			metaChange(val){
				var obj = this.$data.metalist.filter(function(item){
					return item.metadata_id == val;
				})[0];
				document.getElementsByClassName('el-input__inner')[1].value = obj.metadata_name;
				this.$data.submitForm.metadata_id = obj.metadata_id;
				this.$data.submitForm.metadata_name = obj.metadata_name;
			},
			//添加参数事件
			addCarTypeParameter(){
				this.$data.addParamForm.carParameters.push({
					car_params_name : '',
					car_params_value : ''
				});
			},
			//删除参数行
			deleteParam(item){
				var index = this.addParamForm.carParameters.indexOf(item);
		        if (index !== -1) {
		          this.addParamForm.carParameters.splice(index, 1)
		        }
			},
			//保存
			addCarModel(status){
				var that = this;
				that.$refs[that.addParamForm].validate((valid) => {
					if (valid) {
						that.$refs[that.newCarTypeForm].validate((valid1) => {
							if (valid1) {
								if(!that.$data.newCarTypeForm.carmodelBrand){
									that.$message({
										type: "error",
										message: "请输入机型品牌"
									});
									return;
								}
								if(!that.$data.newCarTypeForm.carMetadata){
									that.$message({
										type: "error",
										message: "请选择类型"
									});
									return;
								}
								if(!that.$data.newCarTypeForm.carModelNams){
									that.$message({
										type: "error",
										message: "请输入型号"
									});
									return;
								}
								if(that.$data.newCarTypeForm.carModelNams.length>10){
									that.$message({
										type: "error",
										message: "型号长度为2-10个字符"
									});
									return;
								}
								that.$data.submitForm.car_models_name = that.$data.newCarTypeForm.carModelNams;
								that.$data.submitForm.carParamsList = that.$data.addParamForm.carParameters;
								that.$data.submitForm.app_show = that.$data.checked ? 1 : 0;
								that.$data.submitForm.status = status;
								that.$http.post(that.api,that.$data.submitForm).then(response => {
									var data = response.data;
									that.$message({
										type: data.error_no ==0 ? "success" : "error",
										message: data.error_no ==0 ? "保存成功" : data.error_info
									});
									if(data.error_no ==0){
										location.href = "#/carManagement";
									}
								});
							}
						});
					}
				});
				
			}
		}
	}
</script>
