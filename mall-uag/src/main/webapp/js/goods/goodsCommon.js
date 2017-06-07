//获取所有的三级类目
function getAllThridCategory() {
	var data = {
		limit : 100000000,
		status : 1
	};
	$.ajax({
		contentType : "application/json; charset=utf-8",
		type : 'POST',
		url : "/json/900511",
		dataType : 'json',
		data : JSON.stringify(data),
		success : function(responseText, textStatus, jqXHR) {
			if (responseText.error_no == '0') {
				var resultList = responseText.result_list;
				var html = '';
				resultList.forEach(function(item) {
					html += '<div class="pattern_item" aid="' + item.cid + '">' + item.cname + '</div>';
				});
				$("#third_category_pattern_name").html(html);
			} else {
				alert(responseText.error_no);
			}
		},
		error : function(xhr, type) {

		}
	});
}

//处理机型类目的回车事件
$(".categorylistInput").on("keyup","input",function(){
	var firstcategorylist = $(this).parent().find('li');
	var _category=null;
	var parentdiv= $(this).parents('.categorylistInput');

	if(parentdiv.hasClass('shadeThreeLeft')){
		_category='shadeThreeLeft';
	}else if(parentdiv.hasClass('shadeThreeRight')){
		_category='shadeThreeRight';
	}else if(parentdiv.hasClass('shadeThreeCenter')){
		_category='shadeThreeCenter';
	}else if(parentdiv.hasClass('shadeThreeChose')){
		_category='shadeThreeChose';
	}
	var _search_text=this.value;
	setTimeout(function(){
		if(_category == 'shadeThreeLeft') {
			var firstcategorylist = $('.' + _category + ' li');
			for(var i = 0; i < firstcategorylist.length; i++) {
				var li = $(firstcategorylist[i]);

				if(li.find('span').text().indexOf(_search_text) < 0) {
					li.find('span').removeClass('addColor');
					li.hide();
				} else {
					li.show();
				}
			}
		} else if(_category == 'shadeThreeCenter') {
			var firstcategorylist = $('.' + _category + ' dt');
			for(var i = 0; i < firstcategorylist.length; i++) {
				var li = $(firstcategorylist[i]);

				if(li.text().indexOf(_search_text) < 0) {
					li.removeClass('addColor');
					li.hide();
				} else {
					li.show();
				}
			}
		} else if(_category == 'shadeThreeRight') {
			var firstcategorylist = $('.' + _category + ' dd');
			for(var i = 0; i < firstcategorylist.length; i++) {
				var li = $(firstcategorylist[i]);

				if(li.text().indexOf(_search_text) < 0) {
					li.removeClass('addColor');
					li.hide();
				} else {
					li.show();
				}
			}
		} else if(_category == 'shadeThreeChose') {
			var firstcategorylist = $('.' + _category + ' dd');
			for(var i = 0; i < firstcategorylist.length; i++) {
				var li = $(firstcategorylist[i]);

				if(li.text().indexOf(_search_text) < 0) {
					//li.removeClass('addColor');
					li.hide();
				} else {
					li.show();
				}
			}
		}
	},300);
	
})



// 点击清除时调用
function showAllThirdCategory() {
	$("#third_category_pattern_name .pattern_item").css('display', 'block');
}

// 弹出列表框
$(".third_category_name").click(function() {
	var input = $(this);
	var offset = input.offset();
	$("#third_category_pattern_name").css('display', 'block');
	$('#third_category_pattern_name').css('left', offset.left  + 'px').css('top', offset.top + input.height() + 'px').fadeIn().css('position',"fixed");
	
	return false;
});

// 隐藏列表框
$("body").click(function() {
	$("#third_category_pattern_name").css('display', 'none');
});

// 移入移出效果
$(".pattern_item").hover(function() {
	$(this).css('background-color', '#1C86EE').css('color', 'white');
}, function() {
	$(this).css('background-color', 'white').css('color', 'black');
});

// 文本框输入
$(".third_category_name").keyup(
		function() {
			$("#third_category_pattern_name").css('display', 'block'); // 只要输入就显示列表框

			if ($(".third_category_name").val().length <= 0) {
				$(".pattern_item").css('display', 'block'); // 如果什么都没填，跳出，保持全部显示状态
				return;
			}

			$("#third_category_pattern_name .pattern_item").css('display', 'none'); // 如果填了，先将所有的选项隐藏

			for (var i = 0; i < $("#third_category_pattern_name .pattern_item").length; i++) {
				// 模糊匹配，将所有匹配项显示
				if ($("#third_category_pattern_name .pattern_item").eq(i).text().indexOf($(".third_category_name").val().trim()) > -1) {
					$("#third_category_pattern_name .pattern_item").eq(i).css('display', 'block');
				}
			}
		});

// 项点击
$("#third_category_pattern_name").on('click', '.pattern_item', function() {
	$(".third_category_name").val($(this).text());
});