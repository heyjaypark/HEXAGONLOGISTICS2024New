package member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import jdbc.JdbcUtil;
import member.model.Member;

public class MemberDao {
	
	// 아이디를 기반으로 회원 정보를 조회하는 메소드
	// IDを基にして会員情報を検索するメソッド
	public Member selectById(Connection conn, String id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from member_list where shain_no = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			Member member = null;
			if(rs.next()) {
				member = new Member(
						rs.getString("shain_no"),
						rs.getString("shain_name"),
						rs.getString("pw_id"));
							
			}
			return member;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}

	// Timestamp를 Date로 변환하는 메소드
	// TimestampをDateに変換するメソッド
	private Date toDate(Timestamp date) {
		return date == null ? null : new Date(date.getTime());
	}
	
	// 회원 정보를 삽입하는 메소드
	// 会員情報を挿入するメソッド
	public void insert(Connection conn, Member mem) throws SQLException {
		try(PreparedStatement pstmt =
				conn.prepareStatement("insert into member_list values(?,?,?)")) {
			pstmt.setString(1, mem.getId());
			pstmt.setString(2, mem.getName());
			pstmt.setString(3, mem.getPassword());
			pstmt.executeUpdate();
		}
	}

		 // 회원 정보를 업데이트하는 메소드	
		// 会員情報を更新するメソッド	
		public void update(Connection conn, Member member) throws SQLException {
			try (PreparedStatement pstmt = conn.prepareStatement(
					"update member set name = ?, password = ? where memberid = ?")) {
				pstmt.setString(1, member.getName());
				pstmt.setString(2, member.getPassword());
				pstmt.setString(3, member.getId());
				pstmt.executeUpdate();
			}
		
	}

}
