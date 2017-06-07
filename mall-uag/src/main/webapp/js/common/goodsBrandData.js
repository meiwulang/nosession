//点击清除时调用
function showAllGoodsbrand() {
	$("#pattern_productbrand .pattern_item").css('display', 'block');
}

//弹出列表框  
$(".pattern_productbrand").click(function() {
	var input = $(this);
	var offset = input.offset();
	$("#pattern_productbrand").css('display', 'block');
	$('#pattern_productbrand').css('left', offset.left  + 'px').css('top', offset.top + input.height() + 'px').fadeIn().css('position',"fixed");
	return false;
});

//隐藏列表框  
$("body").click(function() {
	$("#pattern_productbrand").css('display', 'none');
});

//移入移出效果  
$("#pattern_productbrand .pattern_item").hover(function() {
	$(this).css('background-color', '#1C86EE').css('color', 'white');
}, function() {
	$(this).css('background-color', 'white').css('color', 'black');
});

//文本框输入  
$(".pattern_productbrand").keyup(function() {
	$("#pattern_productbrand").css('display', 'block'); //只要输入就显示列表框  

	if($(".pattern_productbrand").val().trim().length <= 0) {
		$("#pattern_productbrand .pattern_item").css('display', 'block'); //如果什么都没填，跳出，保持全部显示状态  
		return;
	}

	$("#pattern_productbrand .pattern_item").css('display', 'none'); //如果填了，先将所有的选项隐藏  

	for(var i = 0; i < $("#pattern_productbrand .pattern_item").length; i++) {
		//模糊匹配，将所有匹配项显示  
		if($("#pattern_productbrand .pattern_item").eq(i).text().indexOf( $(".pattern_productbrand").val().trim())>-1) {
			$("#pattern_productbrand .pattern_item").eq(i).css('display', 'block');
		}
	}
});

//项点击  
$("#pattern_productbrand").on('click','.pattern_item',function() {
	$(".pattern_productbrand").val($(this).text());
});