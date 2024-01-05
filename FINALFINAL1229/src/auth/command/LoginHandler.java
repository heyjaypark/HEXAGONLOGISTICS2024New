package auth.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.LoginFailException;
import auth.service.LoginService;
import auth.service.User;
import mvc.command.CommandHandler;

//로그인 핸들러 클래스
//ログインハンドラのクラス
public class LoginHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/loginForm.jsp";
	private LoginService loginService = new LoginService();

	// 프로세스 메서드의 구현
	// プロセスメソッドの実装
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}

	}

	// 폼 표시 처리
	// フォーム表示処理
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}

	// 폼 제출 처리
	// フォーム提出処理
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String id = trim(req.getParameter("id"));
		String password = trim(req.getParameter("password"));

		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		// ID 또는 비밀번호가 비어 있는 경우, 오류를 오류 맵에 추가
		// IDまたはパスワードが空の場合、エラーをエラーマップに追加
		if (id == null || id.isEmpty())
			errors.put("id", Boolean.TRUE);
		if (password == null || password.isEmpty())
			errors.put("password", Boolean.TRUE);
		// 오류 맵이 비어 있지 않은 경우, 폼 뷰로 돌아감
		// エラーマップが空でない場合、フォームビューに戻る
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}

		try {
			// 로그인 서비스에서 로그인 처리
			// ログインサービスでログイン処理
			User user = loginService.login(id, password);
			req.getSession().setAttribute("authUser", user);
			res.sendRedirect(req.getContextPath() + "/index.jsp");
			return null;
		} catch (LoginFailException e) {
			// ID 또는 비밀번호가 일치하지 않는 경우, 오류를 오류 맵에 추가
			// IDまたはパスワードが一致しない場合、エラーをエラーマップに追加
			errors.put("idOrPwNotMatch", Boolean.TRUE);
			return FORM_VIEW;
		}
	}

	// 문자열의 트림 메서드
	// 文字列のトリムメソッド
	private String trim(String str) {
		return str == null ? null : str.trim();
	}
}
