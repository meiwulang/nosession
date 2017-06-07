document.write("<script language='javascript' src='/js/htmlOss.js'></script>");
document.write("<script language='javascript' src='/js/cookies.js'></script>");
// 方法定义
var thirdNav = {}
// 查询
thirdNav.getData = function(paramsJson) {
	var json = {};
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'POST',
		url : '/json/900511',
		dataType : 'json',
		data : JSON.stringify(paramsJson),
		async : false,
		success : function(responseText, textStatus, jqXHR) {
			if (responseText.error_no == '0') {
				json = responseText;
			} else {
				if (responseText.error_on) {
					alert(responseText.error_on + responseText.error_info);
                } else {
                	alert(responseText.error_info);
                }
			}
		},
		error : function() {
			console.log('error')
		}
	})
	return json;
}
// 绘制元素
thirdNav.drawData = function(jsonData) {
	$('.drawData').remove();
	var str = '';
	json = jsonData.result_list;
	for ( var i = 0; json.length!=null&& i < json.length; i++) {
		str += "<tr class='drawData tableEdit'><td> <input type='checkbox' class='cklist' id='thirdNavigation"
				+ i
				+ "'name='subBox' value='"
				+ json[i].cid
				+ "'> </td> <td> "
				+ (i + 1)
				+ " </td> <td> "
				+ json[i].cid
				+ " </td> <td anick_name="
				+json[i].anick_name
				+"> "
				+ json[i].aname
				+ " </td><td bnick_name="
				+json[i].bnick_name
				+">"
				+ json[i].bname
				+ " </td><td>"
				+ json[i].cname
				+ " </td><td>"
				+ json[i].nick_name
				+ "</td><td><img class='logoImg' src='"
				+ ossUrl
				+ json[i].icon
				+ "'></td> <td> "
				+ json[i].sort
				+ " </td> <td>";
		var hot_icon = '无';
		if(json[i].hot_icon!=null && json[i].hot_icon.trim() != ''){
			hot_icon = "<img src='"	+ossUrl	+ json[i].hot_icon+ "'/> "; 
		}
		str+=hot_icon+"</td> <td> "
				+ json[i].hot_sort
				+ " </td> <td> "
				+ thirdNav.formatDate(json[i].init_date, json[i].init_time)
				+ " </td> <td> "
				+ json[i].creater
				+ " </td> <td>"
				+ thirdNav.formatDate(json[i].update_date, json[i].update_time)
				+ "</td> <td>"
				+ json[i].updater
				+ " </td> <td class='categoryState'> "
				+ thirdNav.formatStatus(json[i].status)
				+ " </td> <td> <input type='button' name='' id='thirdNavigationEdit"
				+ (i + 1)
				+ "' value='编 辑' class='tableBot3 categoryEdit' onclick=thirdNav.edit(this) info='"+escape(JSON.stringify(json[i]))+"'/> <input type='button' name='' id='"
				+ json[i].cid+ "' value='"
				+ thirdNav.editFormatStatus(json[i].status)+ "' class='tableBot4 categoryStart' /> </td> </tr>"
	}
	$('#thirdNavigation').append(str);
	$('label#third_nav_total').text(jsonData.total_num);
	this.pageHtml(jsonData.total_num)
}
// 时间格式化
thirdNav.formatDate = function(date, time) {
	if (date.length != 8) {
		return ""
	}
	if (time.length != 6) {
		return ""
	}
	return result = date.substring(0, 4) + '-' + date.substring(4, 6) + '-'
			+ date.substring(6, 8) + ' ' + time.substring(0, 2) + ':'
			+ time.substring(2, 4) + ':' + time.substring(4, 6);
}
// 时间去格式化
thirdNav.delDateFormat = function(date) {
	return date.replace(/[^0-9]/ig, "")
}
// 状态格式化
thirdNav.editFormatStatus = function(str) {
	if (str == null || str.trim() == "") {
		return ""
	}
	return str == "1" ? "禁用" : str == "0" ? "启用" : "";
}
// 编辑状态格式化
thirdNav.formatStatus = function(str) {
	if (str == null || str.trim() == "") {
		return ""
	}
	return str == "0" ? "禁用" : str == "1" ? "启用" : "";
}

// 行元素编辑
$(".mainLists").on('click','.categoryEdit',function(){
	var dd=$(this).attr("info");
	var obj =  eval('(' + unescape(dd) + ')');
	$('.shade').show();
	$('.shadeCons').show();
	$("#edit_third_nav_save").attr('cid',obj.cid);
	thirdNav.editQueryFirstNav();
	
	//设置当前值
	var id=$("#edit_third_nav_save").attr('cid');
	querySecondNavForEdit(obj.aid);
	var tds=$('#'+id).parent().prevAll();
	var aname=tds[11].textContent.trim();
	$("#edit_first_name").val(obj.aid);
	$("#edit_second_name").val(obj.bid);
	var queryFatherId=$('#edit_first_name').find("option:selected").val();
	$('#edit_first_alias').val(obj.anick_name)
	$('#edit_second_alias').val(obj.bnick_name)
	$('#edit_third_name').val(obj.cname);
	$('#edit_third_alias').val(obj.nick_name);
	$('#third_nav_edit_display').val(obj.icon);
	$('#edit_third_icon_data').val(obj.icon);
	$('#third_nav_edit_display').attr('iconPath',obj.icon);
	if(obj.sort>0){
		$('#edit_third_nav_sort').click();
		$('#edit_third_nav_sort_data').val(obj.sort);
	}
	if(obj.hot_sort>0){
		$('#edit_third_nav_hotsort').click();
		$('#edit_third_nav_hotsort_data').val(obj.hot_sort);
		$('#edit_third_nav_hotdisplay').val(obj.hot_icon);
		$('#edit_third_hoticon_data').val(obj.hot_icon);
		$('#edit_third_nav_hotdisplay').attr('hotIconPath',obj.hot_icon);
	}
	return false;
})

// 行元素修改状态
$('.mainLists').on('click', '.categoryStart', function() {
	var r = confirm('确认操作？');
	var category_id=$(this).attr('id');
	if(r == true){
		var val = $(this).val();
		var json = {
			access_token : localStorage.access_token,
			categoryId : category_id
		};
		json.status = val == "禁用" ? "0" : "1";
		var result = $.ajax({
			contentType : 'application/json; charset=utf-8',
			type : 'POST',
			url : '/json/900517',
			dataType : 'json',
			data : JSON.stringify(json),
			async : false,
			success : function(responseText, textStatus, jqXHR) {
				if (responseText.error_no == '0') {
					
				} else {
					if (responseText.error_on) {
	                alert(responseText.error_on + responseText.error_info);
	                } else {
	                alert(responseText.error_info);
	                }
				}
			},
			error : function() {
				console.log('error')
			}
		})
		var str = JSON.parse(result.responseText);
		if (str.error_no != 0) {
			return;
		}
		thirdNav.search(1);
	}else{
		return false;
	}
});
//批量修改状态
	$('#edit_third_nav_save').bind('click', function() {
		var thisMin = $('#edit_third_alias').val().trim().length;
		if(thisMin==1){
			alert('别名：2-8个字符限制！');
			return
		}
		var icon=$("#edit_third_icon_data").val();
		var currentIcon=$("#third_nav_edit_display").val();
		var currentIconPath=$("#third_nav_edit_display").attr('iconpath');
		if( currentIcon==null || currentIcon.trim()==''){
			alert("请选择三级类目图标"); 
			return false; 
		}
		var editfatherId = $('#edit_second_name').val();
		 if(!editfatherId){
			 alert('请选择二级类目');
	    		return false;
		 }
		var id=$("#edit_third_nav_save").attr('cid')
		var appsort=$("#edit_third_nav_sort_data").val();
		var bnickName = $('#edit_second_alias').val();
		var categoryName = $('#edit_third_name').val();
		var nickName = $('#edit_third_alias').val();
		var sort=$("#edit_third_nav_sort_data").val();
		var grandId=$("#edit_first_name").val();
		var hoticon=$("#edit_third_hoticon_data").val();
		var hoticonDisplay=$("#edit_third_nav_hotdisplay").val();
		var hotsortNum=$("#edit_third_nav_hotsort_data").val();
		var json = {
			access_token : localStorage.access_token,
			level : "3",
			categoryName : categoryName,
			nickName : nickName,
			icon : icon,
			sort :sort,
		    categoryId:id,
		    fatherId:editfatherId,
			level:'3'
		};
		if($("#edit_third_nav_sort").is(':checked')){//app显示勾选判断
			if(!appsort||appsort<1||appsort.trim().length<1){
				alert("app排序请填写正整数");
				return;
			 }
		}else{
			json.sort = -1;
		}
		if($("#edit_third_nav_hotsort").is(':checked')){//热门排序勾选判断
			if(!hotsortNum||hotsortNum<1||hotsortNum.trim().length<1){
				alert("热门排序请填写正整数");
				return;
			}
			if(hoticonDisplay==null || hoticonDisplay.trim()==''){
				alert("请选择热门图标"); 
				return false; 
			}
			json.hotable=true;
			json.hotIcon=hoticon;
			json.hotSort=hotsortNum;
		}
		
		if(null==categoryName||categoryName.trim().length<1){
			alert('请填写类目信息');
			return;
			
		}
		if(null==editfatherId||editfatherId.trim().length<1){
			alert('请选择一级类目');
			return;
			
		}
		if(null==nickName||nickName.trim().length<1){
			delete json.nickName
			
		}
    	var result = $.ajax({
    		contentType : 'application/json; charset=utf-8',
    		type : 'POST',
    		url : '/json/900506',
    		dataType : 'json',
    		data : JSON.stringify(json),
    		async : false,
    		success : function(responseText, textStatus, jqXHR) {
    			if (responseText.error_no == '0') {
    				alert('修改成功');
    				$('#edit_third_nav_cancel').click();
    				thirdNav.search(1);
    				
    			} else {
    				if (responseText.error_on) {
    					alert(responseText.error_on + responseText.error_info);
                    } else {
                    	alert(responseText.error_info);
                    }
    			}
    		},
    		error : function() {
    			console.log('error')
    		}
    	})        
	});
	$('#edit_third_nav_cancel').on('click', function() {
		$('.shade').hide();
		$('.shadeCons').hide();
		$("#edit_third_name").val("");
		$("#edit_third_alias").val("");
		$("#edit_third_icon_data").val("");
		$("input#third_nav_edit_display").val("");
		$("input#edit_third_nav_hotdisplay").val("");
		$("#edit_third_icon").val("");
		$("#edit_third_hoticon").val("");
		
		$("#edit_second_name").html("");
		//设置当前值
		var id=$("#edit_third_nav_save").attr('cid')
		var tds=$('#'+id).parent().prevAll();
		var appSort=tds[7].textContent.trim();
		$('#edit_third_nav_sort').attr('checked',false);
		$('#edit_third_nav_sort_data').val("");
		$('#edit_third_nav_hotsort').attr('checked',false);
		$('#edit_third_nav_hotsort_data').val("");
		$('#edit_third_nav_hotdisplay').val("");
		$('#edit_third_nav_hotdisplay').attr('hotIconPath',"");
		$('#edit_third_nav_sort_data').hide();
		$('#edit_third_nav_sort_data').prev().hide();
		$('.hotSort').hide();
		$('#hotsortLi').hide();
		$('#hotsortLi').next().hide();
	})
thirdNav.addQueryFirstNav =function(){
	var json={ page:0,limit:1000,status:1}
	var result = $.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'POST',
		url : '/json/900507',
		dataType : 'json',
		data : JSON.stringify(json),
		async : false,
		success : function(responseText, textStatus, jqXHR) {
			if (responseText.error_no == '0') {
				var result=responseText.result_list;
				var str="<option value='默认选择'>请选择一级类目</option>";
				for(var i=0;result!=null&&i<result.length;i++){
					str+="<option value='"+result[i].category_id+"' nick_name="+result[i].nick_name+">"+result[i].category_name+"</option>"
				}
				$("#add_first_name").html(str);
				$('#add_first_alias').val('');
				$("#add_first_name").removeAttr('aId');
				$("#add_first_name").attr('aId',result[0].category_id);
			} else {
				if (responseText.error_on) {
					alert("查询父级类目出错："+responseText.error_on + responseText.error_info);
				} else {
					alert("查询父级类目出错："+responseText.error_info);
				}
			}
		},
		error : function() {
			console.log('网络错误');
		}
	})
}
thirdNav.editQueryFirstNav =function(){
	//请求一级类目
	var json={ page:0,limit:1000,status:1}
	var result = $.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'POST',
		url : '/json/900507',
		dataType : 'json',
		data : JSON.stringify(json),
		async : false,
		success : function(responseText, textStatus, jqXHR) {
			if (responseText.error_no == '0') {
				var result=responseText.result_list;
				var str="";
				for(var i=0;result!=null&&i<result.length;i++){
					str+="<option value='"+result[i].category_id+"' nick_name="+result[i].nick_name+">"+result[i].category_name+"</option>"
				}
				$("#edit_first_name").html(str);
				if(result.length>0){
    				$('#edit_first_alias').val(result[0].nick_name);
    				$("#edit_first_name").removeAttr('aId');
    				$("#edit_first_name").attr('aId',result[0].category_id);
				}
				
			} else {
				if (responseText.error_on) {
					alert("查询父级类目出错："+responseText.error_on + responseText.error_info);
				} else {
					alert("查询父级类目出错："+responseText.error_info);
				}
			}
		},
		error : function() {
			console.log('网络错误');
		}
	})

	
}
// 添加类目
thirdNav.createNavition = function() {
	var icon=$("#add_third_icon_data").val();
	if( !icon||icon.trim().length<1){
		alert("请选择图片文件"); 
		return false; 
	}
	var add_third_nav_sort=$("#add_third_nav_sort").is(':checked');
	var sort=$("#add_third_nav_sort_data").val();
	if(add_third_nav_sort&&(null==sort||sort<1||sort.trim().length<1)){
		alert('请填写app排序信息');
		return;
	}
	var hotsort=$("#add_third_nav_hotsort").is(':checked');
    var hoticon=$("#add_third_hoticon_data").val();
	var hotsortNum=$("#add_third_nav_hotsort_data").val();
	if(hotsort){
    	if( !hoticon||hoticon.trim().length<1){
    		alert("请选择热门图片文件"); 
    		return false; 
    	}
		if(hotsortNum==null||hotsortNum.length<1||parseInt(hotsortNum)<1){
			alert("请正确输入热门排序"); 
    		return false;
		}
	}
	var nick_name=$("#add_third_alias").val();
	var add_first_name=$("#add_first_name").val();
	if(add_first_name=="默认选择"){
		alert('请选择一级类目');
   		return false;
	}
	 var addfatherId=$("#add_second_name").val();
	 if(!addfatherId){
		 alert('请选择二级类目');
    		return false;
	 }
	 var appsort=$("#add_third_nav_sort_data").val();
	 var grandId=$("#add_first_name").val();
	var json = {
		access_token : localStorage.access_token,
		level : "3",
		categoryName : $("#add_third_name").val(),
		nickName : nick_name,
		fatherId:addfatherId,
		icon : icon,
	};
	 if(appsort&&appsort.trim().length>0){
		json.sort=Math.ceil(appsort)
	 }else{
		 json.sort=-1
	 }
	if(hotsortNum&&hotsort&&hoticon){
		json.hotable=true;
		json.hotIcon=hoticon;
		if(hotsortNum<1){
			alert('热门排序请输入正数');
			return;
		}
		json.hotSort=Math.ceil(hotsortNum);
	}
        if(!edit_third_nav_sort){
        	delete json.sort;
        }
        if(null==nick_name||nick_name.trim().length<1){
        	delete json.nickName
        }
	
	var result = $.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'POST',
		url : '/json/900505',
		dataType : 'json',
		data : JSON.stringify(json),
		async : false,
		success : function(responseText, textStatus, jqXHR) {
			if (responseText.error_no == '0') {
				localStorage.access_token=responseText.access_token;
				$('#third_nav_add_cancel').click();
				delCookie('__access_token')
				setCookie('__access_token', responseText.access_token)
				alert('添加成功');
			} else {
				if (responseText.error_on) {
					alert(responseText.error_on + responseText.error_info);
				} else {
					alert(responseText.error_info);
				}
			}
		},
		error : function() {
			console.log('网络错误');
		}
	})
	var str = JSON.parse(result.responseText);
	if (str.error_no == 0) {
		this.search();
		return;
	}
}
//转换图片内容
thirdNav.getFileData=function (target){ 
	var file = target.files[0];
	if(!/image\/\w+/.test(file.type)) {
		alert("请选择图片文件");
		return false;
	}
	var reader = new FileReader();
	reader.readAsDataURL(file);
	var image = new Image(),
	canvas = document.createElement("canvas"),     
	ctx = canvas.getContext('2d');
	reader.onload = function(e) {
		//$('input#' + target.id + "_data").val(this.result);
		var url = reader.result;//读取到的文件内容.这个属性只在读取操作完成之后才有效,并且数据的格式取决于读取操作是由哪个方法发起的.所以必须使用reader.onload，     
		image.src=url;//reader读取的文件内容是base64,利用这个url就能实现上传前预览图片
	    reader.onload=null;
	}
		image.onload = function() {     
		var w = image.naturalWidth,     
			h = image.naturalHeight;  
        var dic=h/w;
		canvas.width = w>600?600:w;     
		canvas.height = dic*canvas.width;     
		ctx.drawImage(image, 0, 0, canvas.width, canvas.height); 
		data = canvas.toDataURL("image/gif", 0.8); 
		$('input#' + target.id + "_data").val(data);
		image.onload=null;	
	};
} 
// 搜索
thirdNav.search = function(flag) {
	var grandName = $("#first_nav_name").val();
	var fatherName = $("#second_nav_name").val();
	var categoryName = $('input#third_nav_name').val()
	var status = $("#third_nav_status").val();
	var creater = $("#third_nav_creater").val();
	var appDisplay = $("#third_nav_app_display").val();
	var startTime = this.delDateFormat($("#dateFrom").val());
	var endTime = this.delDateFormat($("#dateTo").val());
	var limit = $("#page_limit").val();

	if(flag == 1) {
		var page = $("#page_now").val();
		page = page - 1;
		page = page * limit;

		var jsonParams = {
			page: page,
			limit: limit
		}
	} else if(flag == 0) {
		var page = 0;
		$("#page_now").val(1);
	
		var jsonParams = {
			page: page,
			limit: limit
		};
	}

	var jsonParams = { page:page,limit:limit};
	if (categoryName && categoryName.trim().length > 0) {
		jsonParams.categoryName = categoryName;
	}
	if (fatherName && fatherName.trim().length > 0) {
		jsonParams.fatherName = fatherName;
	}
	if (grandName && grandName.trim().length > 0) {
		jsonParams.grandName = grandName;
	}
	if (status && status.trim().length > 0) {
		jsonParams.status = status;
	}
	if (creater && creater.trim().length > 0) {
		jsonParams.creater = creater;
	}
	if (appDisplay && appDisplay.trim().length > 0) {
		jsonParams.appDisplay = appDisplay;
	}
	if (startTime && startTime.trim().length > 0) {
		jsonParams.startTime = startTime;
	}
	if (endTime && endTime.trim().length > 0) {
		jsonParams.endTime = endTime;
	}
	this.drawData(this.getData(jsonParams));

}
// 清空
thirdNav.cancel = function() {
	$("#third_nav_name").val("");
	$("#first_nav_name").val("");
	$("#second_nav_name").val("");
	$("#third_nav_status").val("");
	$("#third_nav_creater").val("");
	$("#third_nav_app_display").val("");
	$("#dateFrom").val("");
	$("#dateTo").val("");
	thirdNav.search(0);

}
//通过  当前页（page_now），每页条数（page_size），总条数（data_size），返回分页代码 
thirdNav.pageHtml=function (data_size){
	var page_now=this.delDateFormat($("#page_now").val());
	var page_size = this.delDateFormat($("#page_limit").val());
	var page_total=data_size/page_size==Math.floor(data_size/page_size)?Math.floor(data_size/page_size):Math.floor(data_size/page_size)+1;
	var str='<div class="pageList">';
	if(page_now!=1){
		str+='<span onclick=javascript:pageJump('+(Number(page_now)-1)+')>&lt;</span>';
	}else{
		str+='<span>&lt;</span>';
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
	str+='跳至<input type="text" value="" name="keyDown" onkeydown="keyDown(event,this)" />页';
	str+='</label>';
	str+='</div>';
	$(".page").html(str);
}
function pageJump(topage){
	var limit=$('#page_limit').val();
	$('#page_now').val(topage);
	$('.mainLists').animate( {scrollTop:0},300 ); 
	thirdNav.search(1);
}
//跳转到topage 页
function reSetLimit(limit){
	$('#page_now').val(1);
	$('#page_limit').val(limit);
	$('.mainLists').animate( {scrollTop:0},300 ); 
	thirdNav.search(0);
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

// 页面加载
$(document).ready(function() {
	thirdNav.search(0);
	getAllOperator();
//	初始化一级类目
	var firstNavs=queryFirstNav();
	if(firstNavs.responseJSON.error_no == '0'){
		var firstNavsHtml = '';
		firstNavs.responseJSON.result_list.forEach(function(item) {
			firstNavsHtml+='<div class="first-item" id="'+item.category_id+'">'+item.category_name+'</div>';
		});
		$(".first-nav-list").eq(0).html(firstNavsHtml);
	}
//	初始化二级类目
	var secondNavs=queryScdNavs();
	if(secondNavs.responseJSON.error_no == '0'){
		var secondNavsHtml = '';
		secondNavs.responseJSON.result_list.forEach(function(item) {
			secondNavsHtml+='<div class="second-item" id="'+item.bid+'">'+item.bname+'</div>';
		});
		$(".second-nav-list").eq(0).html(secondNavsHtml);
	}
});
// --------事件处理------------------------
//隐藏列表框  
$("body").click(function() {
	$(".first-nav-list").css('display', 'none');
	$(".second-nav-list").css('display', 'none');
	$("#pattern_name").css('display', 'none');
});
//------------------一级类目输入匹配-------------------------
//弹出一级类目列表框  
$(".fisrt-nav-input").click(function() {
	var input = $(this);
	var offset = input.offset();
	$('.first-nav-list').css('left', offset.left  + 'px').css('top', offset.top + input.height() + 'px').fadeIn().css('position',"fixed");
	$(".first-nav-list").css('display', 'block');
	return false;
});
//文本框输入  
$(".fisrt-nav-input").keyup(function() {
	$(".first-nav-list").css('display', 'block'); //只要输入就显示列表框  

	if($(".fisrt-nav-input").val().trim().length <= 0) {
		$(".first-nav-list .first-item").css('display', 'block'); //如果什么都没填，跳出，保持全部显示状态  
		return;
	}

	$(".first-item").css('display', 'none'); //如果填了，先将所有的选项隐藏  

	for(var i = 0; i < $(".first-item").length; i++) {
		//模糊匹配，将所有匹配项显示  
		if($(".first-nav-list .first-item").eq(i).text().indexOf( $(".fisrt-nav-input").val().trim())>-1) {
			$(".first-nav-list .first-item").eq(i).css('display', 'block');
		}
	}
});
$(".fisrt-nav-input").on('input propertychange',function(){
	
	if($(this).text().trim()==''||$(this).attr("first_name")!=$(this).text().trim()){
		$(this).attr('first_id','');
		$(this).attr("first_name",'')
	}
})
//项点击  
$(".first-nav-list").on('click','.first-item',function() {
	$(".fisrt-nav-input").val($(this).text());
	$(".second-nav-input").val('');
	$(".fisrt-nav-input").attr("first_id",$(this).attr('id'));
	$(".fisrt-nav-input").attr("first_name",$(this).text());
});
//------------------一级类目输入匹配---end----------------------
//------------------二级类目输入匹配-------------------------
//弹出二级类目列表框  
$(".second-nav-input").click(function() {
	var first_id=$(".fisrt-nav-input").attr('first_id');
	if(first_id==''){
		//		重新加载二级类目
		var secondNavs=queryScdNavs();
		if(secondNavs.responseJSON.error_no == '0'){
			var secondNavsHtml = '';
			secondNavs.responseJSON.result_list.forEach(function(item) {
				secondNavsHtml+='<div class="second-item" id="'+item.bid+'">'+item.bname+'</div>';
			});
			$(".second-nav-list").eq(0).html(secondNavsHtml);
		}
	}else{
		//		重新加载二级类目
		var secondNavs=queryChildNav(first_id);
		if(secondNavs.responseJSON.error_no == '0'){
			var secondNavsHtml = '';
			secondNavs.responseJSON.result_list.forEach(function(item) {
				secondNavsHtml+='<div class="second-item" id="'+item.category_id+'">'+item.category_name+'</div>';
			});
			$(".second-nav-list").eq(0).html(secondNavsHtml);
		}
	}
	var input = $(this);
	var offset = input.offset();
	$('.second-nav-list').css('left', offset.left  + 'px').css('top', offset.top + input.height() + 'px').fadeIn().css('position',"fixed");
	setTimeout($(".second-nav-list").css('display', 'block'),1000)
//	$(".second-nav-list").css('display', 'block');
	
	return false;
});
//文本框输入  
$(".second-nav-input").keyup(function() {
	$(".second-nav-list").css('display', 'block'); //只要输入就显示列表框  

	if($(".second-nav-input").val().trim().length <= 0) {
		$(".second-nav-list .second-item").css('display', 'block'); //如果什么都没填，跳出，保持全部显示状态  
		return;
	}

	$(".second-item").css('display', 'none'); //如果填了，先将所有的选项隐藏  

	for(var i = 0; i < $(".second-item").length; i++) {
		//模糊匹配，将所有匹配项显示  
		if($(".second-nav-list .second-item").eq(i).text().indexOf( $(".second-nav-input").val().trim())>-1) {
			$(".second-nav-list .second-item").eq(i).css('display', 'block');
		}
	}
});
//项点击  
$(".second-nav-list").on('click','.second-item',function() {
	$(".second-nav-input").val($(this).text());
});
//------------------二级类目输入匹配---end----------------------
// 清空事件
$("#third_nav_cancel").bind("click", function() {
	thirdNav.cancel();
});
// 搜索事件
$("#third_nav_search").bind("click", function() {
	thirdNav.search(0);
});
// 新增事件
//$("#third_nav_save").bind("click", function() {
//	
//});
$('#third_nav_add').on('click', function() {
	$('.shade').show();
	$('.shadeCon').show();
	thirdNav.addQueryFirstNav();
})

$('#third_nav_save').on('click', function() {
		var thisMin = $('#add_third_alias').val().trim().length;
		if(thisMin==1){
			alert('别名：2-8个字符限制！');
			return
		}
		thirdNav.createNavition();
	});
	$('#third_nav_add_cancel').on('click', function() {
		$('.shade').hide();
		$('.shadeCon').hide();
		$("#add_first_name").val("");
		$("#add_second_name").val("");
		$("#add_second_name").removeAttr('aId');
		$("#add_second_name").html('');
		$("#add_third_name").val("");
		$("#add_second_alias").val("");
		$("#add_third_alias").val("");
		$("#add_third_icon_data").val("");
		$("#add_third_nav_sort_data").val("");
		$("input#third_nav_display").val("");
		$("#add_third_nav_hotsort_data").val("");
		$("#third_nav_hotdisplay").val("");
		$("#add_third_hoticon_data").val("");
		$("#add_third_icon").val("");
		$("#add_third_hoticon").val("");
		if($('#add_third_nav_sort').is(':checked')){
			$('#add_third_nav_sort').click();
		}
		if($('#add_third_nav_hotsort').is(':checked')){
			$('#add_third_nav_hotsort').click();
		}
		
	})

// 批量启用事件
$("#third_nav_batch_enable").bind("click", function() {
	var _ids = [];
	var idchecklist = $("#thirdNavigation .cklist:checked");
	if(idchecklist.length == 0) {
		alert('你没有选择任何数据');
	} else {
		for(var i = 0; i < idchecklist.length; i++) {
			var obj = $(idchecklist[i]);
			_ids[i] = obj.val();
		}
		var data = {
			categoryIds: _ids,
			access_token: localStorage.access_token,
			status: 1
		}
		if(false) {
			alert('请填写信息');
		} else {
			if(idchecklist.length>1){
				r = confirm('是否批量启用');
				testV = '批量启用成功！'
			}else{
				r = confirm('是否启用');
				testV = '启用成功！'
			}
			if(r == true){
				$.ajax({
					contentType: "application/json; charset=utf-8",
					type: 'POST',
					url: '/json/900513',
					dataType: 'json',
					data: JSON.stringify(data),
					success: function(responseText, textStatus, jqXHR) {
						alert(testV);
						thirdNav.search(1);
						$("#checkAll").attr("checked",false);
					},
					error: function() {}
				})
			}else{}
		}
	}
});
//批量禁用事件
$("#third_nav_batch_disable").bind("click", function() {
	var _idsd = [];
	var idchecklistd = $("#thirdNavigation .cklist:checked");
	if(idchecklistd.length == 0) {
		alert('你没有选择任何数据');
	} else {
		for(var i = 0; i < idchecklistd.length; i++) {
			var objd = $(idchecklistd[i]);
			_idsd[i] = objd.val();
		}
		var data = {
			categoryIds: _idsd,
			access_token: localStorage.access_token,
			status: 0
		}
		if(false) {
			alert('请填写信息');
		} else {
			if(idchecklistd.length>1){
				rightTrue = confirm('是否批量禁用？');
				testV = '批量禁用成功！'
			}else{
				rightTrue = confirm('是否禁用？');
				testV = '禁用成功！'
			}
			if(rightTrue == true){
				$.ajax({
					contentType: "application/json; charset=utf-8",
					type: 'POST',
					url: '/json/900513',
					dataType: 'json',
					data: JSON.stringify(data),
					success: function(responseText, textStatus, jqXHR) {
						if(responseText.error_no == '0') {
							alert(testV);
							thirdNav.search(1);
						} else {
							if(responseText.error_on) {
								alert(responseText.error_on +
									responseText.error_info);
							} else {
								alert(responseText.error_info);
							}
						}
						$("#checkAll").attr("checked",false);
					},
					error: function() {}
				})
			}
		}
	}
});
//全选事件绑定
$("#thirdNavigation").on('click', '#checkAll', function() {
	if($(this).is(':checked')) {
		$("#thirdNavigation .cklist").prop("checked", true);
		$('.tableEdit').addClass('trActive')
	} else {
		$("#thirdNavigation .cklist").prop("checked", false);
		$('.tableEdit').removeClass('trActive')
	}
});
//添加图片
$("#add_third_icon").bind("change", function() {
	var thisV = $(this).val();
	if(thisV!=''){
		$('#third_nav_display').val(thisV)
		thirdNav.getFileData(this);
	}
});
//添加热门图片
$("input#add_third_hoticon").bind("change", function() {
	var thisV = $(this).val();
	if(thisV!=''){
		$('#third_nav_hotdisplay').val(thisV)
		thirdNav.getFileData(this);
	}
});
//编辑热门图片
$("input#edit_third_hoticon").bind("change", function() {
	var thisV = $(this).val();
	if(thisV!=''){
		$('#edit_third_nav_hotdisplay').val(thisV)
		thirdNav.getFileData(this);
	}
});
//编辑图片
$("#edit_third_icon").bind("change", function() {
	var thisV = $(this).val();
	if(thisV!=''){
		$('#third_nav_edit_display').val(thisV)
		thirdNav.getFileData(this);
	}
});
$(".sortckbox").on('click',function(){
	if($(this).is(':checked')){
		$(this).next().show();
		$('#add_third_nav_hotsort').show();
		$('#edit_third_nav_hotsort').show();
		$('#hotsortLi').show();
		$('.hotSort').hide();
		$('#edit_third_nav_hotsort_data').hide();
		$('#edithotsortLi').show();
		$(this).next().next().show();
	}else{
		$(this).next().next().hide();
		$(this).next().hide();
		$('#hotsortLi').hide();
		$('#edit_third_nav_hotsort').attr("checked",false);
		$('#edit_third_nav_hotsort_data').val("");
		$('#edit_third_nav_hotdisplay').val("");
		$('#edit_third_hoticon_data').val("");
		$('.hotList').hide();
		$('#edithotsortLi').hide();
	}
})
$("#add_third_nav_hotsort").on('click',function(){
	if($(this).is(':checked')){
		$('.hotSort').show();
		$('#edithotsortLi').next().show();
	}else{
		$('.hotList').hide();
		$('li.bor1.hotList').css("display","none")
		$('#edithotsortLi').next().hide();
	}
})
$("#edit_third_nav_hotsort").on('click',function(){
	if($(this).is(':checked')){
		$('.hotSort').show();
		$('#hotsortLi').next().show();
	}else{
		$('#edit_third_nav_hotsort_data').val("");
		$('#edit_third_nav_hotdisplay').val("");
		$('#edit_third_hoticon_data').val("");
		$('.hotSort').hide();
		$('.hotSort').next().hide();
	}
})
//查询一级可用类目

//$('#add_first_name').bind('input propertychange', function() { 
//var categoryName=this.val();	
//if(categoryName&&categoryName.trim().length>0){
//thirdNav.addQueryFirstNav(categoryName);
//}
//}); 
$("#add_first_name").change(function(){
	var seclect=$("#add_first_name").find("option:selected");
	$('#add_first_alias').val(seclect.attr('nick_name'));
	$("#add_first_name").removeAttr('aId');
	$("#add_first_name").attr('aId',seclect.val());
	
	querySecondNav(seclect.val());
})
function querySecondNav(fatherId){
    //	查询子节点
    $.ajax({
    	contentType : 'application/json; charset=utf-8',
    	type : 'POST',
    	url : '/json/900515',
    	dataType : 'json',
    	data : JSON.stringify({fatherId:fatherId,status:1,webUse:true}),
    	async : false,
    	success : function(responseText, textStatus, jqXHR) {
    		if (responseText.error_no == '0') {
    			var result=responseText.result_list;
    			var str="";
    			for(var i=0;result!=null&&i<result.length;i++){
    				str+="<option value='"+result[i].category_id+"' nick_name="+result[i].nick_name+">"+result[i].category_name+"</option>"
    			}
    			$("#add_second_name").html(str);
    			if(result.length>0){
    				$('#add_second_alias').val(result[0].nick_name);
    				$("#add_second_name").removeAttr('aId');
    				$("#add_second_name").attr('aId',result[0].category_id);
    			}
    		
    		} else {
    			if (responseText.error_on) {
    				alert(responseText.error_on + responseText.error_info);
                } else {
                	alert(responseText.error_info);
                }
    		}
    	},
    	error : function() {
    		console.log('error')
    	}
    })
}
function querySecondNavForEdit(fatherId){
	//	查询子节点
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'POST',
		url : '/json/900515',
		dataType : 'json',
		data : JSON.stringify({fatherId:fatherId,status:1,webUse:true}),
		async : false,
		success : function(responseText, textStatus, jqXHR) {
			if (responseText.error_no == '0') {
				var result=responseText.result_list;
				var str="";
				for(var i=0;result!=null&&i<result.length;i++){
					str+="<option value='"+result[i].category_id+"' nick_name="+result[i].nick_name+">"+result[i].category_name+"</option>"
				}
				$("#edit_second_name").html(str);
				
			} else {
				if (responseText.error_on) {
					alert(responseText.error_on + responseText.error_info);
				} else {
					alert(responseText.error_info);
				}
			}
		},
		error : function() {
			console.log('error')
		}
	})
}
$("#add_second_name").change(function(){
	var seclect=$("#add_second_name").find("option:selected");
	$('#add_second_alias').val(seclect.attr('nick_name'));
	$("#add_second_name").removeAttr('aId');
	$("#add_second_name").attr('aId',seclect);
});
$("#edit_first_name").change(function(){
	var seclect=$("#edit_first_name").find("option:selected");
	$('#edit_first_alias').val(seclect.attr('nick_name'));
	$("#edit_first_name").removeAttr('aId');
	$("#edit_first_name").attr('aId',seclect.val());
//	查询子节点
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		type : 'POST',
		url : '/json/900515',
		dataType : 'json',
		data : JSON.stringify({fatherId:seclect.val(),status:1,webUse:true}),
		async : false,
		success : function(responseText, textStatus, jqXHR) {
			if (responseText.error_no == '0') {
				var result=responseText.result_list;
				var str="";
				for(var i=0;result!=null&&i<result.length;i++){
					str+="<option value='"+result[i].category_id+"' nick_name="+result[i].nick_name+">"+result[i].category_name+"</option>"
				}
				$("#edit_second_name").html(str);
				if(result.length>0){
    				$('#edit_second_alias').val(result[0].nick_name);
    				$("#edit_second_name").removeAttr('aId');
    				$("#edit_second_name").attr('aId',result[0].category_id);
				}
			
			} else {
				if (responseText.error_on) {
					alert(responseText.error_on + responseText.error_info);
                } else {
                	alert(responseText.error_info);
                }
			}
		},
		error : function() {
			console.log('error')
		}
	})
})
$("#edit_second_name").change(function(){
	var seclect=$("#edit_second_name").find("option:selected");
	$('#edit_second_alias').val(seclect.attr('nick_name'));
	$("#edit_second_name").removeAttr('aId');
	$("#edit_second_name").attr('aId',seclect.val());
});
$("#edit_first_name").change(function(){
	var seclect=$("#edit_first_name").find("option:selected");
	$('#edit_first_alias').val(seclect.attr('nick_name'));
	$("#edit_first_name").removeAttr('aId');
	$("#edit_first_name").attr('aId',seclect.val());
})
$("#third_nav_export").click(function(){
	var grandName = $("#first_nav_name").val();
	var fatherName = $("input#second_nav_name").val();
	var categoryName = $("#third_nav_name").val();
	var status = $("#third_nav_status").val();
	var creater = $("#third_nav_creater").val();
	var appDisplay = $("#third_nav_app_display").val();
	var startTime = thirdNav.delDateFormat($("#dateFrom").val());
	var endTime = thirdNav.delDateFormat($("#dateTo").val());
	var page = 0;
	var limit = 1000000;
	str="";
	if (categoryName && categoryName.trim().length > 0) {
		str+='categoryName='+  categoryName;
	}
	if (fatherName && fatherName.trim().length > 0) {
		str+='fatherName='+ fatherName;
	}
	if (grandName && grandName.trim().length > 0) {
		str+='grandName='+ grandName;
	}
	if (status && status.trim().length > 0) {
		str+='status='+status;
	}
	if (creater && creater.trim().length > 0) {
		str+='creater='+creater;
	}
	if (appDisplay && appDisplay.trim().length > 0) {
		str+='appDisplay='+appDisplay;
	}
	if (startTime && startTime.trim().length > 0) {
		str+='startTime='+startTime;
	}
	if (endTime && endTime.trim().length > 0) {
		str+='endTime='+endTime;
	}
	window.location.href="/json/900510?"+str
})
$('#add_third_icon_button').bind('click',function(){
	document.getElementById('add_third_icon').click();
});
$('#add_third_hoticon_button').bind('click',function(){
	document.getElementById('add_third_hoticon').click();
});
$('#edit_upload_img').bind('click',function(){
	document.getElementById('edit_third_icon').click();
});
$('#edit_third_hoticon_button').bind('click',function(){
	document.getElementById('edit_third_hoticon').click();
});
//复选框选中
$('.mainLists').on('click','.tableEdit',function(){
	var isCheck = $(this).find('input[type="checkbox"]').is(':checked');
	if(isCheck){
		$(this).removeClass('trActive');
		$(this).find('input[type="checkbox"]').prop('checked',false);
	}else{
		$(this).addClass('trActive');
		$(this).find('input[type="checkbox"]').prop('checked',true);
	}
})
//没有复选框选中状态
$('.mainLists').on('click','.tableEditNone',function(){
	$(this).addClass('trActive').siblings().removeClass('trActive');
})

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
$('.mainLists').on('click','.cklist',function(){
	var that = $(this);
	var isC = that.is(':checked');
	if(isC){
		that.prop('checked',false)
	}else{
		that.prop('checked',true)
	}
})