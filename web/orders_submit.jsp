<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="Generator" content="ECSHOP v2.7.3" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<title>提交订单</title>
<%@include file="inc/common_head.jsp"%>
	<script>
        window.onload = function (ev) {
            getPCD(0,"province");
        }
        function getPCD(id,objId) {
            var xhr = _getXHR();
            xhr.open("GET","${root}/servletThreeDirectlLinkage?id="+id,true);
            xhr.send();

            xhr.onreadystatechange = function(){
                //判断，只有readyState==4 && status == 200
                //才获取响应的数据
                if(xhr.readyState == 4 && xhr.status == 200){
                    //第四步：接受响应
                    var data = xhr.responseText;
                    console.log("data:",data);
                    var pcdArr = JSON.parse(data);
                    var _pcdObj = document.getElementById(objId);
                    for(var i=0; i<pcdArr.length; i++){
                        var _optionObj = document.createElement("option");
                        var obj  = pcdArr[i];
                        _optionObj.value = obj.id;
                        _optionObj.innerText = obj.name;
                        _pcdObj.appendChild(_optionObj);
                    }

                }
            };
        }

		function _getCity(id) {
			getPCD(id,"city")
        }
        function _getDistrict(id) {
            getPCD(id,"district")
        }
	</script>
</head>
<body>
	<%@include file="inc/header.jsp"%>
	<div class="block clearfix"><div class="AreaR">
	<div class="block box"><div class="blank"></div>
		<div id="ur_here">
			当前位置: <a href="index.jsp">首页</a><code>&gt;</code>购物流程
		</div>
	</div><div class="blank"></div><div class="box"><div class="box_1">
	<div class="userCenterBox boxCenterList clearfix" style="_height:1%;">
	<form action="${root }/servletOrderAdd" method="post">
		<!---------收货人信息开始---------->
		<h5><span>收货人信息</span></h5>
		<table width="100%" align="center" border="0" cellpadding="5"
			cellspacing="1" bgcolor="#dddddd">
			<tr>
				<td bgcolor="#ffffff" align="right" width="120px">区域信息：</td>
				<td bgcolor="#ffffff">
					<!-- 省 -->
					<select id="province" onchange="_getCity(this.value);document.getElementById('ph').value = this.options[selectedIndex].innerHTML;">
						<option value="">-- 请选择省 --</option>
					</select>&nbsp;&nbsp;&nbsp;
					<%--隐藏域：省--%>
					<input type="hidden" id="ph" name="province">
					<!-- 市 -->
					<select id="city" onchange="_getDistrict(this.value);document.getElementById('ch').value = this.options[selectedIndex].innerHTML;">
						<option value="">-- 请选择市 --</option>
					</select>&nbsp;&nbsp;&nbsp;
					<%--隐藏域：市--%>
					<input type="hidden" id="ch" name="city" onchange="document.getElementById('ch').value = this.options[selectedIndex].innerHTML;">
					<!-- 县(区) -->
					<select id="district">
						<option value="">-- 请选择县(区) --</option>
					</select>
					<%--隐藏域：县--%>
					<input type="hidden" id="dh" name="district">
				</td>
			</tr>
			<tr>
				<td bgcolor="#ffffff" align="right">详细地址：</td>
				<td bgcolor="#ffffff">
					<input style="width:347px;" name="detailAddress"/>
				</td>
			</tr>
			<tr>
				<td bgcolor="#ffffff" align="right">邮政编码：</td>
				<td bgcolor="#ffffff"><input name="zipcode"/></td>
			</tr>
			<tr>
				<td bgcolor="#ffffff" align="right">收货人姓名：</td>
				<td bgcolor="#ffffff"><input name="name"/></td>
			</tr>
			<tr>
				<td bgcolor="#ffffff" align="right">联系电话：</td>
				<td bgcolor="#ffffff"><input name="telephone"/></td>
			</tr>
		</table>
		<!---------收货人信息结束---------->
		
		<!----------商品列表开始----------->
		<div class="blank"></div>
		<h5><span>商品列表</span></h5>
		<table width="100%" border="0" cellpadding="5" cellspacing="1"
			bgcolor="#dddddd">
			<tr>
				<th width="30%" align="center">商品名称</th>
				<th width="22%" align="center">市场价格</th>
				<th width="22%" align="center">商品价格</th>
				<th width="15%" align="center">购买数量</th>
				<th align="center">小计</th>
			</tr>
			<c:forEach items="${carts}" var="cart">
				<tr>
					<td>
						<a href="javascript:;" class="f6">${cart.good.name}</a>
					</td>
					<td>${cart.good.marketprice}元</td>
					<td>${cart.good.estoreprice}元</td>
					<td align="center">${cart.buynum}</td>
					<td>${cart.good.estoreprice * cart.buynum}元</td>
				</tr>

				<%--计算总金额--%>
				<c:set var="totalPrice" value="${totalPrice + cart.good.estoreprice * cart.buynum }"></c:set>
			</c:forEach>
			<tr>
				<td colspan="5" style="text-align:right;padding-right:10px;font-size:25px;">
					商品总价&nbsp;<font color="red">&yen;${totalPrice}</font>元
					<input type="hidden" name="totalPrice" value="${totalPrice}">
					<input type="submit" value="提交订单" class="btn" />
				</td>
			</tr>
		</table>
		<!----------商品列表结束----------->
	</form>
	</div></div></div></div></div>
	<%@include file="inc/footer.jsp"%>
</body>
</html>