$(document).ready(function(){
	var wid = $(window).width();
	$('#right').css('width',wid - 300);
	$(window).resize(function(){
		var wid = $(window).width();
		$('#right').css('width',wid - 300);
	});
})
//获取自定义表单数据	
	function getCustomizeData(selector){
		var _data = [], _field = $(selector+' ul li.field');
		_field.each(function(){
			 var _this = $(this), _type = _this.attr('data-type'), _column = _this.attr('data-column'), _table = _this.attr('data-table');
					_data.push(_this.find('input').attr('name')+'":"'+_this.find('input').val());
					//_data.push(_this.find('input').attr('name'));
		});
		return _data;
	}
