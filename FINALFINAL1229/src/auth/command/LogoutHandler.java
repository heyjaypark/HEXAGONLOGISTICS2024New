package auth.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.command.CommandHandler;

//로그아웃 핸들러 클래스
//ログアウトハンドラクラス
public class LogoutHandler implements CommandHandler {

	// 프로세스 메소드의 구현
	// プロセスメソッドの実装
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 세션을 가져옴
		// セッションを取得
		HttpSession session = req.getSession(false);
		if (session != null) {
			// セッションを無効化
			// 세션을 무효화
			session.invalidate();
		}
		// index.jspにリダイレクト
		// index.jsp로 리다이렉트
		res.sendRedirect(req.getContextPath() + "/index.jsp");
		return null;
	}

}
