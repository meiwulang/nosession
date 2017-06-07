<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="Description" content=""/>
	<meta name="Keywords" content=""/>
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<title>机惠多，您最佳的选择</title>
	<link rel="stylesheet" type="text/css" href="css/reset_ext.css"/>
	<link rel="stylesheet" type="text/css" href="css/style_ext.css"/>
	<link rel="shortcut icon" href="images/logo.png" type="image/x-icon" />
	<script src="js/json/jsonJs.js" type="text/javascript" charset="utf-8"></script>
	<script src="js/md5.js" type="text/javascript" charset="utf-8"></script>
	<script src="js/jQuery/jquery-1.12.1.min.js" type="text/javascript" charset="utf-8"></script>
</head>
	<body>
		<div id="login">
			<div class="content">
				<h1>机惠多</h1>
				<div class="account">
					<label>账号：</label><span><input type="text" name="loginid" id="loginid" value="" /></span>
				</div>
				<div class="password">
					<label>密码：</label><span><input type="password" name="loginpwd" id="loginpwd" value="" /></span>
				</div>
				<span id="error_msg" style="color:#ff0000;"></span>
				<div class="forget"></div>
				<!-- <div class="forget">
					<label><input type="checkbox" id="forgets" value="" class="chek"/>记住密码</label><a href="javascript:;">忘记密码？</a>
				</div> -->
				<input type="button" name="btnlogin" id="btnlogin"  value="登&nbsp;&nbsp;&nbsp;陆" class="loginButton borRad"/>
				<!--<input type="button" name="btnsign" id="btnsign" value="企业注册" class="signCompany borRad" />-->
			</div>
			<div class="loginAddress">
				地址：杭州市滨江区银丰大厦1902室&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;电话：0571-86823955
			</div>
		</div>
	</body>
	<script src="js/common.js" type="text/javascript" charset="utf-8"></script>
	<script src="js/login.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			initLogin();
		});
	</script>
</html>