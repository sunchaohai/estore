<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册</title>
<%@include file="inc/common_head.jsp"%>
	
	<script>
		function _checkUser(username) {
            var flag = false;

            //发送ajax请求
			var xhr = _getXHR();

			xhr.open("post","${root}/servletAjax",true);
			xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			xhr.send("username="+username)

			xhr.onreadystatechange = function(){
                var spanObj = document.getElementById("username_notice");
                //判断，只有readyState==4 && status == 200
				//才获取响应的数据
				if(xhr.readyState == 4 && xhr.status == 200){
					//第四步：接受响应:重复-2，为空-1，正常使用0
					var data = xhr.responseText;
					if(data == -2){
						spanObj.innerText = "用户名不可重复";
                    }else if(data == -1){
                        spanObj.innerText = "用户名不可为空";
                    }else if(data == 0){
                        spanObj.innerText = "用户名可以正常使用";
                        spanObj.setAttribute("style","color:green");
                        flag = true;
                    }else {
                        spanObj.innerText = "服务器忙，请稍后再试";
                    }

				}else{
                    spanObj.innerText = "服务正在处理请求，请耐心等待";
                }
			};

            return flag;
        }
	</script>
</head>
<body>
	<%@include file="inc/header.jsp"%>
	<div class="block block1">
		<div class="blank"></div>
		<div class="usBox">
			<div class="usBox_1">
				<div class="login_tab">
					<ul>
						<li onclick="location.href='login.jsp';">
							<a href="javascript:;">用户登录</a>
						</li>
						<li class="active">用户注册</li>
					</ul>
				</div>
				<form id="registForm" action="${root}/servletRegister" method="post" name="formUser"
					onsubmit="return register();">
					<table width="100%" border="0" align="left" cellpadding="5"
						cellspacing="3">
						<caption id="registerMes" style="color: red">${msg}</caption>
						<tr>
							<td width="25%" align="right">用户名</td>
							<td width="65%"><input name="username" type="text"
								id="username" onblur="is_registered(this.value);_checkUser(this.value)"
								class="inputBg" /> <span id="username_notice"
								style="color:#FF0000"> *</span></td>
						</tr>
						<tr>
							<td align="right">昵称</td>
							<td><input name="nickname" type="text"
								id="nickname" onblur="check_nickname(this.value);"
								class="inputBg" /> <span id="nickname_notice"
								style="color:#FF0000"> *</span></td>
						</tr>
						<tr>
							<td align="right">密码</td>
							<td><input name="password" type="password" id="password1"
								onblur="check_password(this.value);"
								onkeyup="checkIntensity(this.value)" class="inputBg" />
								<span style="color:#FF0000"
								id="password_notice"> *</span></td>
						</tr>
						<tr>
							<td align="right">密码强度</td>
							<td>
								<table width="145" border="0" cellspacing="0" cellpadding="1">
									<tr align="center">
										<td width="33%" style="border-bottom:2px solid #ccc;" id="pwd_lower">弱</td>
										<td width="33%" style="border-bottom:2px solid #ccc;" id="pwd_middle">中</td>
										<td width="33%" style="border-bottom:2px solid #ccc;" id="pwd_high">强</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="right">确认密码</td>
							<td><input name="confirm_password" type="password" 
								id="conform_password"
								onblur="check_conform_password(this.value);" class="inputBg" />
								<span style="color:#FF0000"
								id="conform_password_notice"> *</span></td>
						</tr>
						<tr>
							<td align="right">验证码</td>
							<td><input type="text" size="8" name="captcha" id="captcha"
								class="inputBg" onblur="check_captcha(this.value);" /> <span style="color:#FF0000"
								id="captcha_notice"> *</span></td>
						</tr>
						<tr>
							<td align="right"></td>
							<td><img src="validatecode.jsp"
								style="vertical-align:middle;cursor:pointer;width:130px;height:35px;margin-top:-2px;"
								onClick="src='validatecode.jsp?'+Math.random()" /></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><label> <input name="agreement" type="checkbox" id="ifAgree"
									value="1" checked="checked" /> 我已看过并接受《<a
									href="javascript:;" style="color:blue" target="_blank">用户协议</a>》
							</label></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td align="left">
								<input name="Submit" type="submit" value="" class="us_Submit_reg">
							</td>
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
					</table>
				</form>
				<div class="blank"></div>
			</div>
		</div>
	</div>
	<%@include file="inc/footer.jsp"%>
</body>
</html>