<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="tommy.spring.web.board.impl.BoardDAOMybatis" %>
<%@ page import="tommy.spring.web.board.BoardVO" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	// 데이터베이스 연동 처리
	BoardVO vo = new BoardVO();
	BoardDAOMybatis boardDAO = new BoardDAOMybatis();
	// 세션에 저장된 글 목록을 추출
	List<BoardVO> boardList = (List<BoardVO>)session.getAttribute("boardList");
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 목록</title>
</head>
<body>
<h1>게시글 목록</h1>
<h3>${userName}님! 게시판에 오신걸 환영합니다<a href="logout.do">Log-Out</a></h3>
<!-- 검색 시작 -->
<form action="getBoardList.do" method="post">
<table border="1">
<tr>
<td>
<select name="searchCondition">
<c:forEach items="${conditionMap }" var="option">
<option value="${option.value }">${option.key }</option>
</c:forEach>
</select>
<input type="text" name="searchKeyword"/>
<input type="submit" value="검색"/>
</td>
</tr>
</table>
</form><br/>
<!-- 검색 종료 -->
<table border="1">
<tr>
<th>번호</th>
<th>제목</th>
<th>작성자</th>
<th>등록일</th>
<th>조회수</th>
</tr>
<c:forEach var="board" items="${boardList }">
<tr>
<td>${board.seq }</td>
<td><a href="getBoard.do?seq=${board.seq }">${board.title }</a></td>
<td>${board.writer }</td>
<td><fmt:formatDate value="${board.regDate }" pattern="yyyy-MM-dd"/></td>
<td>${board.cnt }</td>
</tr>
</c:forEach>
</table><br/>
<a href="insertBoard.jsp">글등록</a>
</body>
</html>
