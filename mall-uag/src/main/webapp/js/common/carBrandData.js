//点击清除时调用
function showAllCarbrand() {
	$("#pattern_carbrand .pattern_item").css('display', 'block');
}

//弹出列表框  
$(".pattern_carbrand").click(function() {
	var input = $(this);
	var offset = input.offset();
	$("#pattern_carbrand").css('display', 'block');
	$('#pattern_carbrand').css('left', offset.left  + 'px').css('top', offset.top + input.height() + 'px').fadeIn().css('position',"fixed");
	return false;
});

//隐藏列表框  
$("body").click(function() {
	$("#pattern_carbrand").css('display', 'none');
});

//移入移出效果  
$("#pattern_carbrand .pattern_item").hover(function() {
	$(this).css('background-color', '#1C86EE').css('color', 'white');
}, function() {
	$(this).css('background-color', 'white').css('color', 'black');
});

//文本框输入  
$(".pattern_carbrand").keyup(function() {
	$("#pattern_carbrand").css('display', 'block'); //只要输入就显示列表框  

	if($(".pattern_carbrand").val().trim().length <= 0) {
		$("#pattern_carbrand .pattern_item").css('display', 'block'); //如果什么都没填，跳出，保持全部显示状态  
		return;
	}

	$("#pattern_carbrand .pattern_item").css('display', 'none'); //如果填了，先将所有的选项隐藏  

	for(var i = 0; i < $("#pattern_carbrand .pattern_item").length; i++) {
		//模糊匹配，将所有匹配项显示  
		if($("#pattern_carbrand .pattern_item").eq(i).text().indexOf( $(".pattern_carbrand").val().trim())>-1) {
			$("#pattern_carbrand .pattern_item").eq(i).css('display', 'block');
		}
	}
});

//项点击  
$("#pattern_carbrand").on('click','.pattern_item',function() {
	$(".pattern_carbrand").val($(this).text());
});