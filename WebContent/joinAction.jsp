<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO" %> <!-- class불러오기  -->
<%@ page import="java.io.PrintWriter" %> <!-- 자바스크립트를 사용하기 위해 -->
<% request.setCharacterEncoding("UTF-8"); %> <!-- 건너오는 모든 데이터를 utf-8로 -->
<jsp:useBean id="user" class="user.User" scope="page"/> <!-- java beans활용. user라는 클래스를 beans로-->
<jsp:setProperty name="user" property="userID" /> <!-- 로그인페이지에 userID를 받아서 -->
<jsp:setProperty name="user" property="userPassword" /> <!-- " userPassword를 받아옴 -->
<jsp:setProperty name="user" property="userName" />
<jsp:setProperty name="user" property="userGender" />
<jsp:setProperty name="user" property="userEmail" />
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>JSP 게시판 웹 페이지</title>
</head>
<body>
	<% 
	
		String userID = null;
		if(session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
		if(userID != null){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('이미 로그인이 되어있습니다.')");
			script.println("location.href='main.jsp'");
			script.println("</script>");
		}
		if (user.getUserID() == null || user.getUserPassword() == null || user.getUserName() == null ||
			user.getUserGender() == null || user.getUserEmail() == null) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안된 사항이 있습니다.')");
			script.println("history.back()");
			script.println("</script>");
		}else {
			UserDAO userDAO = new UserDAO();
			int result= userDAO.join(user);
			if(result == -1) {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('이미 존재하는 아이디입니다.')");
				script.println("history.back()");
				script.println("</script>");
			}
			else {
				session.setAttribute("userID", user.getUserID());
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("location.href='main.jsp'");
				script.println("</script>");
			}
	
		}
	%>

</body>
</html>