<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的购物车</title>
<%@include file="inc/common_head.jsp"%>
	<script type="application/javascript">
		function _updateCartNum(gid,buynum) {
		    if(parseInt(buynum)){
		        if(buynum < 0){
                    alert("数字不合法")
                    return false;
				}
				console.log("gid:"+gid+",buynum:"+buynum);
		        //浏览器读取 url信息
				location.href = "${root}/updateCartNum?gid="+gid+"&buynum="+buynum;

            }else{
		        alert("数字不合法")
				return false;
			}
        }
	</script>
</head>
<body>
	<%@include file="inc/header.jsp"%>
	<div class="block table">
		<div class="AreaR">
			<div class="block box">
				<div class="blank"></div>
				<div id="ur_here">
					当前位置: <a href="index.jsp">首页</a><code>&gt;</code>我的购物车
				</div>
			</div>
			<div class="blank"></div>
			<div class="box">
				<div class="box_1">
					<div class="userCenterBox boxCenterList clearfix"
						style="_height:1%;">
						<h5><span>我的购物车</span></h5>
						<table width="100%" align="center" border="0" cellpadding="5"
							cellspacing="1" bgcolor="#dddddd">
							<tr>
								<th bgcolor="#ffffff">商品名称</th>
								<th bgcolor="#ffffff">市场价</th>
								<th bgcolor="#ffffff">本店价</th>
								<th bgcolor="#ffffff">购买数量</th>
								<th bgcolor="#ffffff">小计</th>
								<th bgcolor="#ffffff" width="160px">操作</th>
							</tr>

							<c:forEach items="${carts}" var="cart">
								<tr>
									<td bgcolor="#ffffff" align="center" style="width:300px;">
										<!-- 商品图片 -->
										<a href="javascript:;" target="_blank">
											<img style="width:80px; height:80px;"
												 src="${root}/${cart.good.imgurl}"
												 border="0" title="${cart.good.name}" />
										</a><br />
										<!-- 商品名称 -->
										<a href="javascript:;" target="_blank" class="f6">${cart.good.name}</a>
									</td>
									<td align="center" bgcolor="#ffffff">${cart.good.marketprice}元</td>
									<td align="center" bgcolor="#ffffff">${cart.good.estoreprice}元</td>

									<td align="center" bgcolor="#ffffff">
										<input value="${cart.buynum}" size="4" class="inputBg" style="text-align:center;" onblur="_updateCartNum('${cart.good.id}',this.value);"/>
									</td>
									<td align="center" bgcolor="#ffffff">${cart.good.estoreprice * cart.buynum}元</td>
									<td align="center" bgcolor="#ffffff">
										<a href="${root}/servletCartDelete?gid=${cart.good.id}" class="f6" >删除</a>
									</td>
								</tr>
								<%--计算总金额--%>
								<c:set var="totalPrice" value="${totalPrice + cart.good.estoreprice * cart.buynum }"></c:set>
								<%--计算节省金额:（市场价-本店价）*数量，累加--%>
								<c:set var="economyPrice" value="${economyPrice + (cart.good.marketprice-cart.good.estoreprice)* cart.buynum }"></c:set>
							</c:forEach>


							<tr>
								<td colspan="6" style="text-align:right;padding-right:10px;font-size:25px;">
									购物金额小计&nbsp;
									<span style="color: red">${totalPrice}</span>元，
									共为您节省了&nbsp;<span style="color: red">${economyPrice}</span>元
									<a href="orders_submit.jsp"><input value="去结算" type="button" class="btn" /></a>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="blank"></div>
		<div class="blank5"></div>
	</div>
	<%@include file="inc/footer.jsp"%>
</body>
</html>
