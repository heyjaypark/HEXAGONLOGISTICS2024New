package member.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import connection.ConnectionProvider;
import jdbc.JdbcUtil;
import member.dao.MemberDao;
import member.model.Member;

// 회원가입 서비스 클래스
// 会員登録 サービス クラス
public class JoinService {
	
	private MemberDao memberDao = new MemberDao();
	
	// 회원 등록 메서드
	// 会員登録メソッド
	public void join(JoinRequest joinReq) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			// 아이디로 회원 조회
			// IDで会員を検索
			Member member = memberDao.selectById(conn, joinReq.getId());
			if(member != null) {
				JdbcUtil.rollback(conn);
				// 중복 아이디 예외 발생
				// 重複したIDの例外を投げる
            
				throw new DuplicateIdException();
			}
			
			// 회원 추가
			 // 会員の追加
			memberDao.insert(conn, new Member(
					joinReq.getId(),
					joinReq.getName(),
					joinReq.getPassword()));
					conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
			// TODO: handle exception
		} finally {
			JdbcUtil.close(conn);
		}
	}

}
