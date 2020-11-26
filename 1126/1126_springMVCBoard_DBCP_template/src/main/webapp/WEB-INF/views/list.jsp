<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	a {	color: black;text-decoration: none;}
	a:hover {background-color: cyan;text-decoration: none;}
</style>
</head>
<body>
<table width="1000" align="center" border="1" cellpadding="5" cellspacing="0">
		<tr> <th colspan="5"> 게시판 보기 </th> </tr>
		<tr>
			<td colspan="5" align="right">
				${mvcboardList.totalCount}(${mvcboardList.currentPage} / ${mvcboardList.totalPage})Page
			</td> 
		</tr>
		<tr> 
			<th width="80">글번호</th>
			<th width="620">제목</th>
			<th width="100">이름</th>
			<th width="120">작성일</th>
			<th width="80">조회수</th>
		</tr>
			
		<jsp:useBean id="date" class="java.util.Date"/>		
		
		<c:set var="list" value="${mvcboardList.list}"/>

		<c:if test="${list.size() == 0}">
		<tr>
			<td colspan="5" >
				<marquee>테이블에 저장된 글이 없습니다.</marquee>
			</td>
		</tr>
		</c:if>
		
		<c:if test="${list.size() != 0}">
		
		<c:forEach var="vo" items="${list}">
   		<tr>
   			<td align="center"> ${vo.idx}</td>
   			<td>
   			<c:set var="subject" value="${fn:replace(fn:trim(vo.subject),'<','&lt;') }"/>
					<c:set var="subject" value="${fn:replace(subject,'>','&gt;') }"/>
			
			<c:if test="${vo.lev > 0 }">
				<c:forEach var="i" begin="1" end="${vo.lev}">
					&nbsp;&nbsp;&nbsp;
				</c:forEach>
			┗RE
			</c:if>
				
			<a href="increment?idx=${vo.idx}&currentPage=${mvcboardList.currentPage}">${subject}</a>
			<!-- 조회수가 10번을 넘어가면 hot을 표시한다. -->
			<c:if test="${vo.hit > 10 }">
				<img alt="" src="images/hot.gif">
			</c:if>
   			</td>
   			<td align="center">
				<c:set var="name" value="${fn:replace(fn:trim(vo.name),'<','&lt;') }"/>
				<c:set var="name" value="${fn:replace(name,'>','&gt;') }"/>
				${name} 
			</td>
			<td align="center">
				<c:if test="${date.year == vo.writeDate.year && date.month == vo.writeDate.month && date.date == vo.writeDate.date}">
					<fmt:formatDate value="${vo.writeDate}" pattern="HH:mm:ss"/>
				</c:if>
				<c:if test="${date.year != vo.writeDate.year || date.month != vo.writeDate.month || date.date != vo.writeDate.date}">
					<fmt:formatDate value="${vo.writeDate}" pattern="yyyy-MM-dd(E) HH:mm:ss"/>
				</c:if>
			</td>
			<td align="center">${vo.hit} </td>
   		</tr>
   		</c:forEach>
   		</c:if>
		<tr>
		<td align="center" colspan="5">
		
			
		<!-- 처음으로 -->
			<c:if test="${mvcboardList.currentPage > 1}">
				<input type="button" class="button button2" value="맨앞" onclick="location.href='?currentPage=1'" title="첫 페이지로 이동하기"/>
			</c:if>	
			<c:if test="${mvcboardList.currentPage <= 1}">
				<input type="button" class="button button3" value="맨앞" disabled="disabled" onclick="location.href='?currentPage=1'" title="첫 페이지 입니다."/>
			</c:if>	
		<!-- 10페이지 앞으로 -->
			<c:if test="${mvcboardList.startPage > 1 }">
				<input type="button" class="button button2" value="이전" onclick="location.href='?currentPage=${mvcboardList.startPage - 1 }'" title="이전 페이지 목록으로 이동하기"/>
			</c:if>
			<c:if test="${mvcboardList.startPage <= 1 }">
				<input type="button" class="button button3" value="이전" disabled="disabled" title="이미 첫 페이지 목록 입니다."/>
			</c:if>
		<!-- 페이지 이동버튼 출력 -->
		
		<c:forEach var="i" begin="${mvcboardList.startPage}" end="${mvcboardList.endPage}">
			<c:if test="${mvcboardList.currentPage == i}">
				<input type="button" class="button button1" value="${i}" disabled="disabled" title="현재 페이지입니다.">
			</c:if>
			
			<c:if test="${mvcboardList.currentPage != i}">
				<input type="button" class="button button2" value="${i}" onclick="location.href='?currentPage=${i}'"
				title="${i} 페이지로 이동하기" >
			</c:if>
		</c:forEach>
			
		<!-- 10페이지 뒤로 -->
			<c:if test="${mvcboardList.endPage < mvcboardList.totalPage }">
				<input type="button" class="button button2" value="다음" onclick="location.href='?currentPage=${mvcboardList.endPage + 1 }'" title="다음 페이지 목록으로 이동하기"/>
			</c:if>
			<c:if test="${mvcboardList.endPage >= mvcboardList.totalPage }">
				<input type="button" class="button button3" value="다음" disabled="disabled" title="이미 마지막 페이지 목록 입니다."/>
			</c:if>
		
		<!-- 마지막 페이지로 -->
			<c:if test="${mvcboardList.currentPage < mvcboardList.totalPage }">
				<input type="button" class="button button2" value="맨뒤" onclick="location.href='?currentPage=${ mvcboardList.totalPage }'" title="마지막 페이지로 이동하기"/>
			</c:if>
			<c:if test="${mvcboardList.currentPage >= mvcboardList.totalPage }">
				<input type="button" class="button button3" value="맨뒤" disabled="disabled"  title="이미 마지막 페이지 입니다."/>
			</c:if>
		</td>
	</tr>

	<tr> 
	<td colspan="5" align="right"> <input type="button" value="글쓰기" onclick="location.href='insert'"> </td> 
	</tr>
</table>
</body>
</html>