<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="bbs.BbsDAO" %> <!-- class불러오기  -->
<%@ page import="java.io.PrintWriter" %> <!-- 자바스크립트를 사용하기 위해 -->
<% request.setCharacterEncoding("UTF-8"); %> <!-- 건너오는 모든 데이터를 utf-8로 -->
<jsp:useBean id="bbs" class="bbs.Bbs" scope="page"/> <!-- java beans활용. user라는 클래스를 beans로-->
<jsp:setProperty name="bbs" property="bbsTitle" /> <!-- 로그인페이지에 userID를 받아서 -->
<jsp:setProperty name="bbs" property="bbsContent" /> <!-- " userPassword를 받아옴 -->

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
		if(userID == null){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('로그인을 하세요.')");
			script.println("location.href='login.jsp'");
			script.println("</script>");
		} else {			
		if (bbs.getBbsTitle() == null || bbs.getBbsContent() == null) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안된 사항이 있습니다.')");
			script.println("history.back()");
			script.println("</script>");
		} else {
			BbsDAO bbsDAO = new BbsDAO();
			int result= bbsDAO.write(bbs.getBbsTitle(), userID, bbs.getBbsContent());
			if(result == -1) {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('글쓰기에 실패했습니다.')");
				script.println("history.back()");
				script.println("</script>");
			}
			else {
				/*session.setAttribute("userID", user.getUserID());*/
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("location.href='bbs.jsp'");
				script.println("</script>");
			}
	
		}
		}
	%>

</body>
</html>