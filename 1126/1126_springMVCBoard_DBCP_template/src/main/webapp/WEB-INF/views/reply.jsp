<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>질문글 보기</title>
</head>
<body>
<!-- 질문글을 보여주는 테이블 -->
<table width="600" align="center" border="1" cellpadding="5" cellspacing="0"> 
	<tr> 
		<th colspan="4">질문글 보기</th>
	</tr>
	<tr> 
		<th width="80">글번호</th>
		<th width="120">이름</th>
		<th width="320">작성일</th>
		<th width="80">조회수</th>
	</tr>
	
	<jsp:useBean id="date" class="java.util.Date"/>
	
	<tr> 
		<td align="center"> ${vo.idx}</td>
		
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
	<tr>
		<td align="right">제목</td>
		<td colspan="3">
		
			<c:set var="subject" value="${fn:replace(fn:trim(vo.subject),'<','&lt;') }"/>
			<c:set var="subject" value="${fn:replace(subject,'>','&gt;') }"/>
			${subject}
		
		</td>
	</tr>
	<tr>
		<td align="right">내용</td>
		<td colspan="3">
		
			<c:set var="content" value="${fn:replace(fn:trim(vo.content),'<','&lt;') }"/>
			<c:set var="content" value="${fn:replace(content,'>','&gt;') }"/>
			<c:set var="content" value="${fn:replace(content,enter,'<br/>') }"/>
			${content}
		
		</td>
	</tr>
</table>
<hr color="red" size="3"/>
<!-- 답글을 입력하는 테이블 -->
<form action="replyInsert" method="post">
	<input type="hidden" size="10" name="idx" value="${vo.idx}"/>			<!-- 질문글 글번호 -->
	<input type="hidden" size="10" name="ref" value="${vo.ref}"/>			<!-- 1개의 질문글과 그 답글 그룹 -->
	<input type="hidden" size="10" name="lev" value="${vo.lev}"/>			<!-- 질문글과 답글 레벨 -->
	<input type="hidden" size="10" name="seq" value="${vo.seq}"/>			<!-- 같은 답글 그룹에서 답글 출력 순서 --> 
	<input type="text" size="10" name="currentPage" value="${currentPage}"/>		<!-- 답글 입력 후 돌아갈 페이지번호 --> 

	<table width="600" align="center" border="1" cellpadding="5" cellspacing="0">
		<tr>
			<th colspan="4">답글쓰기</th>
		</tr>
	 	<tr>
            <td width="100" align="right">이름</td>
            <td width="400"><input type="text" name="name" id=""></td>
        </tr>
        <tr>
            <td align="right">제목</td>
            <td ><input type="text" name="subject" id=""></td>
        </tr>
        <tr>
            <td align="right">내용</td>
            <td ><textarea name="content" id="" cols="50" rows="10" style="resize: none;"></textarea></td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="저장하기"/>
                <input type="reset" value="다시쓰기"/>
                <input type="button" value="돌아가기" onclick="history.back()"/>
                <input type="button" value="목록보기" onclick="location.href='list?currentPage=${currentPage}'"/>
            </td>
        </tr>
	</table>
</form>
</body>
</html>