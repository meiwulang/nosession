//弹出列表框  
$(".carmodelBrand").click(function() {
	
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
$(".carmodelBrand").keyup(function() {
	$("#pattern_name").css('display', 'block'); //只要输入就显示列表框  
	$(".carmodelBrand").attr('brand_id','');
	if($(".carmodelBrand").val().length <= 0) {
		$(".pattern_item").css('display', 'block'); //如果什么都没填，跳出，保持全部显示状态  
		return;
	}

	$(".pattern_item").css('display', 'none'); //如果填了，先将所有的选项隐藏  

	for(var i = 0; i < $(".pattern_item").length; i++) {
		//模糊匹配，将所有匹配项显示  
		if($(".pattern_item").eq(i).text().indexOf($(".carmodelBrand").val().trim()) >-1 ) {
			$(".pattern_item").eq(i).css('display', 'block');
		}
	}
});

//项点击  
$("#pattern_name").on('click','.pattern_item',function() {
	
	$(".carmodelBrand").val($(this).text());
	var brand_id =$(this).eq(0).attr('value');
	$(".carmodelBrand").attr('brand_id',brand_id);
});


function queryblur(){
	
	var data={
			page:1,
			limit:10000,
			status:1
		};
	$.ajax({
		contentType: "application/json; charset=utf-8",
		type: 'POST',
		url:"/carbrand/queryforweb",
		dataType: 'json',
		data: JSON.stringify(data),
		success:function(responseText, textStatus, jqXHR){ 
			if(responseText.error_no == '0'){
				var resultList = responseText.result_list;
				var brandTypes='';
				resultList.forEach(function(item){
					brandTypes+='<div class="pattern_item" value="'+item.brand_id+'">'+item.brand_name+'</div>';
				})
				$("#pattern_name").append(brandTypes);
				
			}
		},
		error: function(xhr, type) {
			//alert("网络异常");
		}
	});

	
	
};