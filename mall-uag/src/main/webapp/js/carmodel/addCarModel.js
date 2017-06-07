$(document).ready(function(){
	listsReady();
	queryblur();
});	
	$('.addParameter').on('click', function() {
	var carParameter1 = $('<div class="carParameter"> <div class="leftParameter"> <label><em>*</em>  名称：</label> <input type="text" name="" id="" value="" placeholder="输入（2-50字数限制）" maxlength="50" class="inputText2 addCarParams"/> </div> <div class="rightParameter"> <label class="rightLabel">  值：</label> <input type="text" name="" id="" value="" placeholder="输入（2-50字数限制）" maxlength="50" class="inputText2 addCarValues"/><input type="button" name="" id="" value="删除" class="inputBot3 deletePara"/></div> </div>');
	$(this).before(carParameter1);
	});
	$('.saveDisable').on('click',function(){
		$('input.inputBot1.marL1.saveEnable').attr('disabled',true);
		var brandId = $('.carmodelBrand').attr('brand_id');
		var brandName=$('.carmodelBrand').val().trim(); 
		var metadataId =$(".carMetadata").val().trim();
		var metadataName=$(".carMetadata").find("option:selected").text().trim();
		var carModelName=$('.carModelNams').val().trim();
		var carParamsList=[];
		var appShow=$('input#add_first_nav_sort');
		var app_show='1';
		app_show=appShow.is(':checked')?1:0;
		if($('.addCarParams').length>0){
			for(var i=0;i<$('.addCarParams').length;i++){
				var car_params_value=$('.addCarValues')[i].value.trim();
				var car_params_name=$('input.inputText2.addCarParams')[i].value.trim();
				var carParams={
						car_params_value:car_params_value,
						car_params_name:car_params_name
				};
				if(car_params_value.length>0&&car_params_name.length>0){
					carParamsList.push(carParams);
				}else {
					alert("请完善参数信息");
					return;
				}
			}
		}
		if(brandId.trim().length=0){
			alert('请选择品牌');
			return ;
		}
		if(metadataId.length=0){
			alert('请选择类型');
			return ;
		}
		if(carModelName.length=0){
			alert('请填写型号');
			return ;
			
		}
		if(carModelName.trim().length>10||carModelName.trim().length<2){
			alert("型号名称在2-10个字数之间");
			return;
		}
		var data={};
			
		 data={
				car_models_name:carModelName,
				metadata_name:metadataName,
				metadata_id:metadataId,
				brand_id:brandId,
				brand_name:brandName,
				carParamsList:carParamsList,
				access_token:localStorage.access_token,
				status:0,
				app_show:app_show
		};
		 if(carParamsList.length>0){
				data.carParamsList=carParamsList;
			}
		$.ajax({
			contentType: "application/json; charset=utf-8",
			type: 'POST',
			url:"/addCarModel",
			dataType: 'json',
			data: JSON.stringify(data),
			success:function(responseText, textStatus, jqXHR){
				if(responseText.error_no=='0'){
					alert('保存并禁用成功！');
					location.href ='/html/carManagement.html';
				}else {
					alert(responseText.error_info);
				}
				
			},error:function(){
			}
		})
	});
	$('.list').on('click', '.carParameter .rightParameter .inputBot3', function() {
		$(this).parent().parent().remove();
	});
	$('.saveEnable').on('click',function(){
		var brandId = $('.carmodelBrand').attr('brand_id');
		var brandName=$('.carmodelBrand').val().trim();
		var metadataId =$(".carMetadata").val().trim();
		var metadataName=$(".carMetadata").find("option:selected").text().trim();
		var carModelName=$('.carModelNams').val().trim();
		var carParamsList=[];
		var appShow=$('input#add_first_nav_sort');
		var app_show='1';
		app_show=appShow.is(':checked')?1:0;
		if($('.leftParameter').length>0){
			for(var i=0;i<$('.leftParameter').length;i++){
				var car_params_value=$('.addCarValues')[i].value.trim();
				var car_params_name=$('input.inputText2.addCarParams')[i].value.trim();
				var carParams={
						car_params_value:car_params_value,
						car_params_name:car_params_name
				};
				if(car_params_value.length>0&&car_params_name.length>0){
					carParamsList.push(carParams);
				}else {
					alert("请完善参数信息");
					return;
				}
			}
		}
		if(carModelName.trim().length>10||carModelName.trim().length<2){
			alert("型号名称在2-10个字数之间");
			return;
		}
		var data={};
		
		 data={
				car_models_name:carModelName,
				metadata_name:metadataName,
				metadata_id:metadataId,
				brand_id:brandId,
				brand_name:brandName,
				access_token:localStorage.access_token,
				status:1,
				app_show:app_show
		};
		 if(carParamsList.length>0){
				data.carParamsList=carParamsList;
			}
		$.ajax({
			contentType: "application/json; charset=utf-8",
			type: 'POST',
			url:"/addCarModel",
			dataType: 'json',
			data: JSON.stringify(data),
			success:function(responseText, textStatus, jqXHR){
				if(responseText.error_no=='0'){
					
					alert('保存并启用成功！');
					location.href ='/html/carManagement.html';
				}else {
					alert(responseText.error_info);
				}
			},error:function(){
				//alert('网络异常')
			}
		})
	});




	function listsReady(){
		var data={
				page:1,
				limit:10000,
				status:1,
				type:1
			};
		$.ajax({
			contentType: "application/json; charset=utf-8",
			type: 'POST',
			url:"/queryMetadata",
			dataType: 'json',
			data: JSON.stringify(data),
			success:function(responseText, textStatus, jqXHR){ 
				if(responseText.error_no == '0'){
					var resultList = responseText.result_list;
					var carTypes='';
					resultList.forEach(function(item){
						carTypes+='<option value="'+item.metadata_id+'">'+item.metadata_name+'</option>';
					})
					$('.carMetadata').append(carTypes);
					
				}
			},
			error: function(xhr, type) {
				//alert("网络异常");
			}
		});
	};
	
	
