package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BbsDAO {
		
		private Connection conn;
		private ResultSet rs;
		
		//MySQL 연결
		public BbsDAO() {
			try {
				String jdbcURL= "jdbc:mysql://localhost:3306/BBS?serverTimezone=Asia/Seoul";
				String dbID = "root";
				String dbPassword = "yde866238";
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(jdbcURL, dbID, dbPassword);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		//현재시간
		public String getDate() {
			String SQL ="SELECT NOW()";
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				rs= pstmt.executeQuery();
				if(rs.next()) {
					return rs.getString(1);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return ""; //데이터베이스 오류					
		}
		
		//게시글번호(아이디)
		public int getNext() {
			String SQL ="SELECT bbsID FROM BBS ORDER BY bbsID DESC";
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				rs= pstmt.executeQuery();
				if(rs.next()) {
					return rs.getInt(1) + 1;
				} 
				return 1; //첫번째 게시물인 경우
			}catch(Exception e) {
				e.printStackTrace();
			}
			return -1; //데이터베이스 오류	
		}
		
		//게시글 작성
		public int write(String bbsTitle, String userID, String bbsContent) {
			String SQL="INSERT INTO BBS VALUE (?, ?, ?, ?, ?, ?)";
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, getNext());
				pstmt.setString(2, bbsTitle);
				pstmt.setString(3, userID);
				pstmt.setString(4, getDate());
				pstmt.setString(5, bbsContent);
				pstmt.setInt(6, 1);
				return pstmt.executeUpdate();
			} catch(Exception e) {
				e.printStackTrace();
			}
			return -1; //데이터베이스 오류
		}
		
		//게시글 목록
		public ArrayList<Bbs> getList(int pageNumber) {
			String SQL ="SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
			ArrayList<Bbs> list = new ArrayList<Bbs>();
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, getNext() - (pageNumber - 1) *10);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					Bbs bbs = new Bbs();
					bbs.setBbsID(rs.getInt(1));
					bbs.setBbsTitle(rs.getString(2));
					bbs.setUserID(rs.getString(3));
					bbs.setBbsDate(rs.getString(4));
					bbs.setBbsContent(rs.getString(5));
					bbs.setBbsAvailable(rs.getInt(6));
					list.add(bbs);
				} 
			}catch(Exception e) {
				e.printStackTrace();
			}
			return list; //데이터베이스 오류
		}
		
		//페이징 처리
		public boolean nextPage(int pageNumber) {
			String SQL ="SELECT * FROM BBS WHERE bbsID < ? AND bbsAvailable = 1";
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, getNext() - (pageNumber - 1) *10);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					return true;
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		
		//게시글 불러오기
		public Bbs getBbs(int bbsID) {
			String SQL= "SELECT * FROM BBS WHERE bbsID = ?";
			try {
				PreparedStatement psmtm = conn.prepareStatement(SQL);
				psmtm.setInt(1, bbsID);
				rs= psmtm.executeQuery();
				if(rs.next()){
					Bbs bbs = new Bbs();
					bbs.setBbsID(rs.getInt(1));
					bbs.setBbsTitle(rs.getString(2));
					bbs.setUserID(rs.getString(3));
					bbs.setBbsDate(rs.getString(4));
					bbs.setBbsContent(rs.getString(5));
					bbs.setBbsAvailable(rs.getInt(6));
					return bbs;
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		//게시글 수정하기
		public int update(int bbsID, String bbsTitle, String bbsContent) {
			String SQL="UPDATE BBS SET bbsTitle=?, bbsContent =? WHERE bbsID=?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, bbsTitle);
				pstmt.setString(2, bbsContent);
				pstmt.setInt(3, bbsID);
				return pstmt.executeUpdate();
			} catch(Exception e) {
				e.printStackTrace();
			}
			return -1; //데이터베이스 오류
		}
		//글 삭제하기
		public int delete(int bbsID) {
			String SQL="UPDATE BBS SET bbsAvailable = 0 WHERE bbsID=?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, bbsID);
				return pstmt.executeUpdate();
			} catch(Exception e) {
				e.printStackTrace();
			}
			return -1; //데이터베이스 오류
		}
}
