<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	.outer{
		width:900px; min-height:50px; padding-bottom: 50px;
		margin-left:auto; margin:auto; margin-top:50px; margin-bottom: 50px; 
	}
	#boardTable{text-align:center; margin: auto; width: 900px;}
	#boardTable th{border-bottom: 3px solid lightgray;}
	#boardTable td{border-bottom: 1px solid lightgray;}
</style>
</head>
<body>
	<jsp:include page="../common/menubar.jsp"/>
		
	<!------------- 0_1.로그인한 회원만 게시물 보기를 하기 위해 다음과같은 조건 추가  -------------->
	<c:if test="${!empty loginUser }">
		<div class="outer">
			<br>
			<h1 align="center">게시판</h1>
						
			<!---------------- 1. 게시물 리스트 ------------------>
			<!-- 1_1. 게시물 리스트 보기  -->
			<table id="boardTable">
				<tr>
					<th>글 번호</th>
					<th>제목 </th>
					<th>작성자</th>
					<th>조회수</th>
					<th>작성일</th>
				</tr>
<%-- 				${list } 찍어보기 잘나오나 --%>
				<!-- forEach를 통해 request에 담겨있는 list들을 하나씩 접근하여 출력 -->
<!-- 					<방법2> 추천하지않음 -->
<%-- 				<c:forEach begin="0" end="${ list.size()-1}" var="i"> --%>
<!-- 					<tr> -->
<%-- 						<td>${list.get(i).bId }</td> --%>
<%-- 						<td>${list.get(i).bTitle} </td> --%>
<%-- 						<td>${list.get(i).bWriter}</td> --%>
<%-- 						<td>${list.get(i).bCount}</td> --%>
<%-- 						<td>${list.get(i).bCreateDate}</td> --%>
<!-- 					</tr> -->
<%-- 				</c:forEach> --%>
				
				<c:forEach items="${list}" var="b">
					<tr>
						<td>${b.bId }</td>
						<td>${b.bTitle} </td>
						<td>${b.nickName}</td>
						<td>${b.bCount}</td>
						<td>${b.bCreateDate}</td>
					</tr>
				</c:forEach>
			</table>
			
			<!-- 1_2. 게시물 리스트 페이징 부분 -->
			<div class="pagingArea" align="center">
				<!-- [이전] -->
				<c:if test="${ pi.currentPage <= 1 }">[이전]</c:if>
				<c:if test="${ pi.currentPage > 1 }">
<%-- 					<a href="${ contextPath }/selectList.bo?currentPage=${ pi.currentPage -1 }">[이전]</a> --%>
<%-- 					value="${ loc }" : 현재url을 참고하는게 있다 현재있는 url을 할거라면  --%>
					<c:url value="${ loc }" var="blistBack">
						<c:param name="currentPage" value="${ pi.currentPage - 1 }"></c:param>
						<c:if test="${ condition ne null }">
							<c:param name="searchCondition" value='${ condition }'></c:param>
							<c:if test="${ condition ne null }">
								<c:param name="searchCondition" value="${ condition }"/>
								<c:param name="searchValue" value="${ value }"/>
							</c:if>
						</c:if>
					</c:url>
					<a href="${ blistBack }">[이전]</a>
				</c:if>
				<!-- 번호 버튼 -->
				<!-- currentPage 현재페이지만 넘기고 검색에 대한 url을 넘기는게 아님 -->
				<c:forEach begin="${ pi.startPage }" end="${ pi.endPage }" var="p">
					<c:if test="${ p eq pi.currentPage }">
						<font color="red" size="4"><b>[${ p }]</b></font>
					</c:if>
					<c:if test="${ p ne pi.currentPage }">
						<c:url var="blistNum" value="${ loc }">
							<c:param name="currentPage" value="${ p }"/>
							<c:if test="${ condition ne null }">
								<c:param name="searchCondition" value="${ condition }"/>
								<c:param name="searchValue" value="${ value }"/>
							</c:if>
						</c:url>
						<a href="${ blistNum }">${ p }</a>
					</c:if>
				</c:forEach>
				
				<!-- [다음] -->
				<c:if test="${ pi.currentPage >= pi.maxPage }">[다음]</c:if>
				<c:if test="${ pi.currentPage < pi.maxPage }">
					<c:url value="${ loc }" var="blistNext">
						<c:param name="currentPage" value="${ pi.currentPage + 1 }"/>
						<c:if test="${ condition ne null }">
								<c:param name="searchCondition" value="${ condition }"/>
								<c:param name="searchValue" value="${ value }"/>
							</c:if>
					</c:url>
					<a href="${ blistNext }">[다음]</a>
				</c:if>
			</div>
			
			<!-----------  2. 상세보기 ------------->
			<script type="text/javascript">
				$(function(){
					$("#boardTable").find("td").mouseenter(function(){
						$(this).parents("tr").css({"background":"black", "color":"white", "cursor":"pointer"});
					}).mouseout(function(){
						$(this).parents("tr").css({"background":"none", "color":"black"});
					}).click(function(){
						var bId=$(this).parents().children("td").eq(0).text();
	
						location.href="selectOne.bo?bId="+bId;
					});
				});
			</script>
		</div>
	</c:if>
	
	<!-------------- 0_2. 로그인하지 않은경우 ---------------->
	<c:if test="${empty loginUser }">
		<c:set var="message" value="로그인이 필요한 서비스입니다." scope="request"/>
		<jsp:forward page="../common/errorPage.jsp"/>
	</c:if>
	
	<!---------------- 3. 게시물 검색 ------------------>
	<div id="searchArea" align="center">
		<label>검색 조건</label>
		<select id="searchCondition" name="searchCondition">
			<option>-------</option>
			<option value="writer">작성자</option>
			<option value="title">제목</option>
			<option value="content">내용</option>
		</select>
		
		<input id="searchValue" type="search">
		<button onclick="searchBoard();">검색하기</button>
	</div>
	<script type="text/javascript">
		function searchBoard(){
			var searchCondition = $("#searchCondition").val();
			var searchValue = $("#searchValue").val();
			
			location.href="search.bo?searchCondition="+searchCondition+"&searchValue="+searchValue;
		}
	</script>
</body>
</html>