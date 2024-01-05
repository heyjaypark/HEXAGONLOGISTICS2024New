package auth.service;

import java.sql.Connection;
import java.sql.SQLException;

import connection.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;

//사용자 로그인 기능을 제공하는 서비스 클래스입니다.
//このクラスはユーザーログイン機能を提供するサービスクラスです。
public class LoginService {
	// 회원 정보를 조회하기 위한 DAO 객체
	// 会員情報を検索するためのDAOオブジェクト
	private MemberDao memberDao = new MemberDao();

	public User login(String id, String password) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			// 주어진 ID를 사용하여 회원 정보를 데이터베이스에서 조회
			// 指定されたIDを使用して会員情報をデータベースから検索
			Member member = memberDao.selectById(conn, id);
			if (member == null) {
				// 조회된 회원 정보가 없으면 로그인 실패
				// 検索された会員情報がない場合はログイン失敗
				throw new LoginFailException();
			}
			// 조회된 회원 정보의 비밀번호와 주어진 비밀번호가 일치하는지 확인
			// 検索された会員情報のパスワードと指定されたパスワードが一致するか確認
			if (!member.matchPassword(password)) {
				// 일치하지 않으면 로그인 실패
				// 一致しない場合はログイン失敗
				throw new LoginFailException();
			}
			// 로그인에 성공하면 User 객체를 생성하여 반환
			// ログインに成功した場合はUserオブジェクトを作成して返す
			return new User(member.getId(), member.getName());
		} catch (SQLException e) {
			// 데이터베이스 연결 등의 예외가 발생하면 런타임 예외로 처리
			// データベース接続などの例外が発生した場合はランタイム例外として処理
			throw new RuntimeException(e);
		}
	}

}
