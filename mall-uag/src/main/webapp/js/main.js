$(document).ready(function() {
	var cliWin = $('#wrap').outerWidth();
	var cliHei = $(window).outerHeight();
	var leftNav = $('.leftNavi dl dt');
	var leftNavChange = $('.leftNavi dl dd');
	var rightOperationHei = $('.mainOperation').outerHeight();
	$('#left').css('height', cliHei);
	$('#right').css('width', cliWin - 240);
	$('.leftNavi').css('max-height', cliHei - 65);
	$('.main').css('height', cliHei - 65 - 50);
	$('.leftNavi .leftNaviOn').siblings().show();
	$('.mainLists').css('height', cliHei - rightOperationHei - 65 - 50 - 60 - 50);
	leftNav.on('click', function() {
		$(this).toggleClass('leftNaviOn')
		$(this).siblings().toggle();
	})
	//回车
	document.onkeydown = function(e){ 
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
	    	if(ev.target.getAttribute('onkeydown')==null){
	    		$('.searchLists').click();
	    	}
	     }
	}
	$(window).resize(function() {
		var cliWin = $('#wrap').outerWidth();
		var cliHei = $(window).outerHeight();
		var rightOperationHei = $('.mainOperation').outerHeight();
		$('#left').css('height', cliHei);
		$('#right').css('width', cliWin - 240);
		$('.leftNavi').css('max-height', cliHei - 65)
		$('.main').css('height', cliHei - 65 - 50);
		$('.mainLists').css('height', cliHei - rightOperationHei - 65 - 50 - 60 - 50)
	});
	$('.exitBot').on('click', function() {
		if(confirm('确定退出？')){
			window.location = "/logout";
		}
	})
	$('.shade').on('click', '.deletePara', function() {
		$(this).parent().remove();
	});
	$('.inputFile1').change(function() {//空的话，不触发事件
		var thisV = $(this).val();
		if(thisV != ""){
		$('.inputFileShow').val(thisV)
		}
	})
	
	$('.inputFile').change(function() {
		var thisV = $(this).val();
		$('.inputFileShow').val(thisV)
	})
	$('.categoryStart').on('click', function() {
		var thisV = $(this).val();
		if(thisV == '启 用') {
			$(this).parent().siblings('.categoryState').text('启用');
			$(this).val('禁 用')
		} else {
			$(this).parent().siblings('.categoryState').text('禁用');
			$(this).val('启 用')
		}
	})
});