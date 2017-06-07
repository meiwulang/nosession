/////////////////////////////////verify////////////////////////////////////////////////////
function initLogin(){
//////////////////////////////////////////////////////////////////////////////
	$('#login input').keyup(function(event) {
        if (event.keyCode == "13") {//处理enter 事件
        	dealLogin();
        }

    });
	//点击登陆
	$('#login #btnlogin').click(function(){
		var _this = $(this);
		dealLogin();
	});
	
	
	var dealLogin = function(){
		var _data={operator_name: $('#login #loginid').val(),password:MD5($('#login #loginpwd').val())};
		var _url = '/logindo', dataStr = JSON.stringify(_data);
		$.ajax({
				type:'POST',
				url: _url,
				data:dataStr,
				contentType:'application/json;charset=UTF-8',
				beforeSend:function(){ 
				},
				success:function(result){
					if (result.error_no == "0"){
						localStorage.access_token = result.access_token;
						$('#login #error_msg').text(result.error_info);
						//window.location="/dis/memberManagement.html";
						window.location="/dist/index.html";
					}else{
						$('#login #error_msg').text(result.error_info);
						alert("登陆失败");
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					
				},
				complete: function (XHR, TS) {
					if(XHR.responseText=="logout"){ReloginFn();}
					XHR = null;
				},
				cache: false
			});
	}
	

	//点击登陆
	$('#login #btnsign').click(function(){
		window.location="signone";
	});
}

