<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="${ contextPath }/js/jquery-3.6.0.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<c:set var="contextPath" value="${ pageContext.request.contextPath }" scope="application"/>
	<h1 id="welcome" align="center" onclick="home();">Welcome to MyBatis World!</h1>
	
	<form action="${contextPath}/login.me" method="post">
		<button id="login-btn" class="btn btn"></button>
		
		<a href="${ contextPath }/memberInsertForm">회원가입</a>
		<a href="${ contextPath }/findMemberForm.me">로그인</a>
	</form>
</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

SqlSession session = getSqlSession();
Member member = new MemberDAO().selectMember(session, m);
<mapper>
	<select id="쿼리문 고유아이디구분자명"></select>
</mapper>

SqlSession session = getSqlSession();
Member member = new MemberDAO().selectMember(session, m);
<select id= ""></select>
SqlSession session = null

	InputStream stream = Resources.getResourceAsStream("/mybatis-config.xml");
	SqlSessionFactoryBuilder ssfb = new SqlSessionFactoryBuilder();
	SqlSessionFactory ssf = ssfb.build(stream);
	session = ssf.openSession(false);
	
	return session;
	


SqlSession session = null;
	InputStream stream = Resources.getResourceAsStream("/mabatis-config.xml");
	SqlSessionFactoryBuilder ssfb = new SqlSessionFactoryBuilder();
	SqlSessionFactory ssf = ssfb.build(stream);
	session = ssf.openSession(false);//자동커밋막기
	return session
	
	InputStream stream = Resources.getResourceAsStream("/mybatis-config.xml")
	SqlSessionFactoryBuilder ssfb = new SqlSessionFactoryBuilder();
	SqlSessionFactory ssf = ssfb.build(stream);
	session = ssf.openSession(false);
	return session;
	
	InputStream stream = Resources.getResourceAsStream("/mybatis-config.xml");
	SqlSessionFactoryBuilder ssfb = new SqlSessionFactoryBuilder();
	SqlSessionFactory ssf = ssfb.build(stream);
	session = ssf.openSession(false);
	
	return session;
	
	InputStream stream = Resources.getResourceAsStream("/mabatis-config.xml");
	SqlSessionFactoryBuilder ssfb = new SqlSessionFactoryBuilder();
	SqlSessionFactory ssf = ssfb.build(stream);
	session = ssf.openSession(false);
	
	return session;
	
	SqlSession session = getSqlSession();
	SqlSession session = getSqlSession();
<configuration><!-- 작성 순서 꼭 지켜야한다. -->
	<properties resourse="/driver.properties"></properties>
	<environments default="development">
		<enviroment id="development">
			<transactionManager type="JDBC"/>
			<dataSource type="POOLED">
				<property name="driver" value="${ driver }"/>
				<property name="url"/>
				<property/>
				<property/>
			</dataSource>
		</enviroment>
	</environments>
</configuration>
	
<configuration>
	<properties resource="/driver.properties"/>
	<environments default="development">
		<enviroment id="development">
			<transactionManager type="JDBC"/>
			<dataSource type="POOLED">
				<properties name="driver" value="${ driver }"/>
				<properties name="url" value="${ url }"/>
				<properties name="username" value="${ username }"/>
				<properties name="password" value="${ password }"/>
			</dataSource>
		</enviroment>
	</environments>
</configuration>

<configuration>
	<properties resource="/driver.properties"/>
	<enviroments default="development">
		<enviroment id="development">
			<transactionManager type="JDBC"/>
			<dataSource type="POOLED">
				<properties name="driver" value="${ driver }"/>
				<properties name="url" value="${ url }"/>
				<properties name="username" value="${ username }"/>
				<properties name="password" value="${ password }"/>
			</dataSource>
		</enviroment>
	</enviroments>
</configuration>
	
<configuration>
	<properties resouce="/driver.properties"/>
	<environments default="development">
		<environment id="development">
			<transactionManager type="JDBC"/>
			<dataSource type="POOLED">
				<properties name="driver" value="${ driver }"/>
				<properties name="url" value="${ url }"/>
				<properties name="username" value="${ username }"/>
				<properties name="password" value="${ password }"/>
			</dataSource>
		</environment>
	</environments>
</configuration>
	
	
HttpSession session = request.getSession();
Member member = (Member)session.getAttribute("loginUser");
String userId = member.getUserId();
String userPwd = request.getParameter("userPwd");
String newPwd = request.getParameter("newPwd");
HashMap<String, String> map = new HashMap<String, String>();
map.put("id", userId);
map.put("oldPwd", userPwd);
map.put("newPwd", newPwd);
mew MemberService().updatePwd(map);
member.setUserPwd(newPwd);
session.setAttribute("loginUser", member);
response.sendRedirect(request.getContextPath());

request.setAttribute("message", e.getMessage());
request.getRequestDispatcher("WEB-INF/views/common/errorPage.jsp").forward(request, response);
	
BoardService sevice = new BoardService();
int currentPage = 1;
if(request.getParameter("currentPage") != null){
	currentPage = Integer.parseInt(request.getParameter("currentPage"));
}

BoardService serciver = newe BoardService();

int currentPage = 1;
if(request.getParameter("currentPage") != null){
	currentPage = Integer.parseInt(request.getParameter("currentPage"));
}

try{
	int listCount = service.getListCount();
	
	PageInfo pi = Pageination.getPageInfo(currentPage, listCount);
	
	ArrayList<Board> list = service.selectBoardList(pi);
	
	request.setAttribute("list", list);
	request.setAttribute("pi", pi);
	
	request.getRequestDispatcher("WEB-INF/views/board/boardList.jsp").forWard(request, response);

	
} catch(BoardException e){
	request.setAttribute("message", e.getMessage());
	request.getRequestDispatcher("WEB-INF/views/common/errorPage.jsp").forward(request, response);
	
}

board-mapper
<mapper namespace="boardMapper">
	<select id="selectListCount" resultType="_int">
	
	</select>

</mapper>


<select>
	select
	from board
		join member on(bwriter = user_id)
	where b_status = 'Y'
	<choose>
		<when test="condition == 'writer'">
			and nickname = #{value}
		</when>
		
	</choose>
</select>
	
	
	
	
	