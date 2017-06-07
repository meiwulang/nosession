function dateFormat(date,time) {
	if(date.trim() != '' && time.trim() != ''){
		var date_format = date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" ";
		var time_format = time.substring(0,2)+":"+time.substring(2,4)+":"+time.substring(4,6);
		return date_format+time_format;
	}
	return '';
	
}
//复选框选中
$('.mainLists').on('click','.tableEdit',function(e){
	var isCheck = $(this).find('input[type="checkbox"]').is(':checked');
	if(isCheck){
		$(this).removeClass('trActive');
		$(this).find('input[type="checkbox"]').prop('checked',false);
	}else{
		$(this).addClass('trActive');
		$(this).find('input[type="checkbox"]').prop('checked',true);
	}
})
$('.mainLists').on('click','.cklist',function(){
	var that = $(this);
	var isC = that.is(':checked');
	if(isC){
		that.prop('checked',false)
	}else{
		that.prop('checked',true)
	}
})
//没有复选框选中状态
$('.mainLists').on('click','.tableEditNone',function(){
	$(this).addClass('trActive').siblings().removeClass('trActive');
})
//跳转到topage 页
function pageJump(topage){
	$('#page_now').val(topage);
//	$(".mainLists").scrollTop(0);
	$('.mainLists').animate( {scrollTop:0},300 ); 
	searchOnclick(1);
}
//选择每页条数
function reSetLimit(limit){
	$('#page_now').val(0);
	$('#page_limit').val(limit);
	searchOnclick(0);
}
//处理 跳转页面  输入狂 回车事件
function keyDown(e,_this) {
	var _this=$(_this);
	  var ev= window.event||e;
	  //13是键盘上面固定的回车键
	  if (ev.keyCode == 13) {
		  //你要执行的方法
		  pageJump(_this.val());
	  }
}
//通过  当前页（page_now），每页条数（page_size），总条数（data_size），返回分页代码 
function pageHtml(page_now,page_size,data_size){
	var page_total=data_size/page_size==Math.floor(data_size/page_size)?Math.floor(data_size/page_size):Math.floor(data_size/page_size)+1;
	var str='<div class="pageList">';
	if(page_now!=1){
		str+='<span onclick=javascript:pageJump('+(Number(page_now)-1)+')><</span>';
	}else{
		str+='<span><</span>';
	}
	
	var start=page_now-4<1?1:page_now-4;
	var end=page_now+4>page_total?page_total:page_now+4;
	for(var i=start;i<=end;i++){
		if(page_now==i){
			str+='<span class="pageListOn">'+i+'</span>';
		}else{
			str+='<span onclick=javascript:pageJump('+i+')>'+i+'</span>';
		}
		
	}
	if(page_now!=page_total){
		str+='<span onclick=javascript:pageJump('+(Number(page_now)+1)+')>></span>';
	}else{
		str+='<span>></span>';
	}
	var page_limit=Number($('#page_limit').val());
	
	str+='<select name="" class="pageSelect" onchange="javascript:reSetLimit(this.options[this.options.selectedIndex].value)">';
	if(page_limit==15){
		str+='<option value="15" selected>15条/页</option>';
	}else{
		str+='<option value="15">15条/页</option>';
	}
	if(page_limit==20){
		str+='<option value="20" selected>20条/页</option>';
	}else{
		str+='<option value="20">20条/页</option>';
	}
	if(page_limit==30){
		str+='<option value="30" selected>30条/页</option>';
	}else{
		str+='<option value="30">30条/页</option>';
	}

	str+='</select>';
	str+='<label class="pageGo">';
	str+='跳至<input type="text" value=""/ onkeydown="keyDown(event,this)">页';
	str+='</label>';
	str+='</div>';
	return str;
}

//验证网址格式
function checkUrl(url){
//	 var reg=/^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/;
	 var reg=/^([hH][tT]{2}[pP]|[hH][tT]{2}[pP][sS]):\/\/[A-Za-z0-9\.]+(:[0-9]{0,4})?[\S]+$/;

	 if(!reg.test(url)){
	 	return false;
	 }else{
		return true;
	 }
}

//获取所有的后台操作人
function getAllOperator() {
	var data = {};
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url:"/getAllOperators",
		dataType: 'json',
		data: JSON.stringify(data),
		success:function(responseText, textStatus, jqXHR){ 
			if(responseText.error_no == '0'){
				var resultList = responseText.result_list;
				var operatorHtml = '';
				resultList.forEach(function(item) {
					operatorHtml+='<div class="pattern_item" id="'+item.operator_id+'">'+item.operator_name+'</div>';
				});
				$("#pattern_name").html(operatorHtml);
			}else{
				alert(responseText.error_no);
			}
		},
		error: function(xhr, type) {
			
		}
	});
}
//获取所有商品品牌
function getAllProductBrand() {
	data={
			status:1,
			limit:10000000
	}
	$.ajax({
				contentType : "application/json; charset=utf-8",
				type : 'POST',
				url : "/goodsbrand/queryforweb",
				dataType : 'json',
				data : JSON.stringify(data),
				success : function(responseText, textStatus, jqXHR) {
					if(responseText.error_no == '0'){
						var resultList = responseText.result_list;
						var operatorHtml = '';
						resultList.forEach(function(item) {
							operatorHtml+='<div class="pattern_item" id="'+item.brand_id+'">'+item.brand_name+'</div>';
						});
						$("#pattern_productbrand").html(operatorHtml);
					}else{
						alert(responseText.error_no);
					}
				},
				error : function(xhr, type) {
					alert("网络异常");
				}
			});
};

//获取所有机型品牌
function getAllCarBrand() {
	data={
			status:1,
			limit:10000000
	}
	$.ajax({
				contentType : "application/json; charset=utf-8",
				type : 'POST',
				url : "/carbrand/queryforweb",
				dataType : 'json',
				data : JSON.stringify(data),
				success : function(responseText, textStatus, jqXHR) {
					if(responseText.error_no == '0'){
						var resultList = responseText.result_list;
						var operatorHtml = '';
						resultList.forEach(function(item) {
							operatorHtml+='<div class="pattern_item" id="'+item.brand_name+'">'+item.brand_name+'</div>';
						});
						$("#pattern_carbrand").html(operatorHtml);
					}else{
						alert(responseText.error_no);
					}
				},
				error : function(xhr, type) {
					alert("网络异常");
				}
			});
};

//点击清除时调用
function showAllOperator() {
	$("#pattern_name .pattern_item").css('display', 'block');
}

//弹出列表框  
$(".create_user_name").click(function() {
	var input = $(this);
	var offset = input.offset();
	$("#pattern_name").css('display', 'block');
	$('#pattern_name').css('left', offset.left  + 'px').css('top', offset.top + input.height() + 'px').fadeIn().css('position',"fixed");
	return false;
});
//隐藏列表框  
$("body").click(function() {
	$("#pattern_name").css('display', 'none');
});
//移入移出效果  
$(".pattern_item").hover(function() {
	$(this).css('background-color', '#1C86EE').css('color', 'white');
}, function() {
	$(this).css('background-color', 'white').css('color', 'black');
});

//文本框输入  
$(".create_user_name").keyup(function() {
	$("#pattern_name").css('display', 'block'); //只要输入就显示列表框  

	if($(".create_user_name").val().trim().length <= 0) {
		$("#pattern_name .pattern_item").css('display', 'block'); //如果什么都没填，跳出，保持全部显示状态  
		return;
	}

	$("#pattern_name .pattern_item").css('display', 'none'); //如果填了，先将所有的选项隐藏  

	for(var i = 0; i < $("#pattern_name .pattern_item").length; i++) {
		//模糊匹配，将所有匹配项显示  
		if($("#pattern_name .pattern_item").eq(i).text().indexOf( $(".create_user_name").val().trim())>-1) {
			$("#pattern_name .pattern_item").eq(i).css('display', 'block');
		}
	}
});
//查询一级类目
function queryFirstNav (){
	var json={ page:0,limit:10000,status:1}
	return $.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'POST',
		url : '/json/900507',
		dataType : 'json',
		data : JSON.stringify(json),
		async : false,
		success : function(responseText, textStatus, jqXHR) {
		},
		error : function() {
			console.log('网络错误');
		}
	})
}
//查询二级类目
function queryScdNavs(){
	return $.ajax({
		contentType: 'application/json; charset=utf-8',
		type: 'POST',
		url: '/json/900508',
		dataType: 'json',
		data: JSON.stringify({limit:5000,page:0}),
		async: false,
		success: function(responseText, textStatus, jqXHR) {
			if(responseText.error_no == '0') {
				json = responseText;
			} else {
				if(responseText.error_on) {
					alert(responseText.error_on + responseText.error_info);
				} else {
					alert(responseText.error_info);
				}
			}
		},
		error: function() {
			console.log('error') 
		}
	})
}
//按父级查询子类目
function queryChildNav(fatherId){
	//	查询子节点
	return $.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'POST',
		url : '/json/900515',
		dataType : 'json',
		data : JSON.stringify({fatherId:fatherId,status:1,webUse:true}),
		async : false,
		success : function(responseText, textStatus, jqXHR) {
		},
		error : function() {
			console.log('error') 
		}
	})
}
//查询三级类目
function queryThirdNav(){
	return $.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'POST',
		url : '/json/900511',
		dataType : 'json',
		data : JSON.stringify({page:0,limit:50000}),
		async : false,
		success : function(responseText, textStatus, jqXHR) {
		},
		error : function() {
			console.log('error')
		}
	})
}

//项点击  
$("#pattern_name").on('click','.pattern_item',function() {
	$(".create_user_name").val($(this).text());
});