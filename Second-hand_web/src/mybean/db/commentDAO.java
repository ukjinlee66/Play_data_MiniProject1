package mybean.dbte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class commentDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	// Singleton
	private static commentDAO comment;

	private commentDAO() throws SQLException, ClassNotFoundException {
		conn = dbCon.getConnection();
	}

	public static commentDAO getInstance() throws SQLException, ClassNotFoundException {
		if(comment==null) {
			comment = new commentDAO();
		}
		return comment;
	}
	
	
	public void insertRecord(commentVO Cvo, noticeVO Nvo, userVO Uvo) throws SQLException {
		String sql = "insert into commentTbl(commentInfo, noticeNumber, userId) "
				+ " values(?,?,?)";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,  Cvo.getCommentInfo());
		pstmt.setInt(2,  Nvo.getNoticeNumber());
		pstmt.setString(3,  Uvo.getUserId());
		
		pstmt.executeUpdate();
	}
	
	public void updateRecord(commentVO Cvo, noticeVO Nvo, userVO Uvo) throws SQLException {
		String sql = "update commentTbl set commentInfo=? "
				+ "where commentNumber=? and noticeNumber=? and userId= ? ";
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1,  Cvo.getCommentInfo());
		pstmt.setInt(2,  Cvo.getCommentNumber());
		pstmt.setInt(3,  Nvo.getNoticeNumber());
		pstmt.setString(4,  Uvo.getUserId());
		
		pstmt.executeUpdate();
	}
	
	public void deleteRecord(commentVO Cvo, noticeVO Nvo, userVO Uvo) throws SQLException {
		String sql = "delete from noticeTbl "
				+ "where commentNumber=? and noticeNumber=? and userId= ? ";
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1,  Cvo.getCommentNumber());
		pstmt.setInt(2,  Nvo.getNoticeNumber());
		pstmt.setString(3,  Uvo.getUserId());
		
		pstmt.executeUpdate();
	}
	
	public List<commentVO> listRecord(noticeVO Nvo) throws SQLException {
		
		List<commentVO> commentList = new ArrayList<commentVO>();
		
		String sql = "select * from commentTbl where noticeNumber=?";
			
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Nvo.getNoticeNumber());
			
		rs = pstmt.executeQuery();
			
		while(rs.next()) {	
			commentList.add(new commentVO(rs.getString("userId"), rs.getString("commentInfo")));
		}	
		return commentList;
		 
	}
	
	public void disConnect() throws SQLException {
		if(rs != null) rs.close();
		if(rs != null) pstmt.close();
		if(rs != null) conn.close();
	}

}
