<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>商品管理页面</title>
</head>

<body>
		<table border="1 solid blank" cellspacing="0px">
			<tr>
				<th>商品名称</th>
				<th>市场价</th>
				<th>优惠价</th>
				<th>数量</th>
			</tr>

			<c:forEach items="${goods}" var="good">
				<tr>
					<td>${good.name}</td>
					<td>${good.marketprice}</td>
					<td>${good.estoreprice}</td>
					<td>${good.num}</td>
				</tr>
			</c:forEach>

		</table>
	
</body>
</html>
