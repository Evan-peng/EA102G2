<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mem.model.*"%>

<%   
    MemService memSvc = new MemService();
    List<MemVO> list = memSvc.getAll();
    pageContext.setAttribute("list",list);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Insert title here</title>
</head>
<body>


<table>
	<c:forEach var="memVO" items="${list}">	
		<tr>
			<td>${memVO.mem_id}</td>
			<td>${memVO.first_name}</td>
			<td>${memVO.last_name}</td>
			<td>${memVO.nickname}</td>
			<td>${memVO.tel}</td>
			<td>${memVO.mob}</td>
			<td>${memVO.email}</td>
			<td>${memVO.password}</td>
			<td>${memVO.shop_name}</td>
			<td>${memVO.credit_card}</td>			
			<td><fmt:formatDate value="${memVO.credit_card_expires}" pattern="yyyy-MM"/></td>
			
			<td>${memVO.credit_card_cvc}</td>
			<td>${memVO.bank_account}</td>
			<td><fmt:formatDate value="${memVO.est_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			
			<td>${memVO.address}</td>
			<td>${memVO.authority}</td>
		</tr>
	</c:forEach>
</table>


</body>
</html>