package member.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.service.DuplicateIdException;
import member.service.JoinRequest;
import member.service.JoinService;
import mvc.command.CommandHandler;

public class JoinHandler implements CommandHandler {
	// 회원 가입 폼의 뷰 경로
	// メンバ登録フォームのビューのパス
	private static final String FORM_VIEW = "/WEB-INF/view/joinForm.jsp";
	// JoinService 인스턴스 생성
	// JoinServiceのインスタンスを生成
	private JoinService joinService = new JoinService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// GET 요청일 경우 폼을 보여줌
		// GETリクエストの場合、フォームを表示
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
			// POST 요청일 경우 회원 가입 처리
			// POSTリクエストの場合、メンバ登録処理を実行
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
			// 허용되지 않은 메소드일 경우 에러 상태 반환
			// 許可されていないメソッドの場合、エラーのステータスを返す
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	// 회원 가입 폼을 보여주는 메소드
	// メンバ登録フォームを表示するメソッド

	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}
	// 회원 가입 정보를 제출하고 처리하는 메소드
	// メンバ登録情報を提出して処理するメソッド

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) {
		// 회원 가입 정보를 담을 객체 생성
		// メンバ登録情報を保持するオブジェクトを生成
		JoinRequest joinReq = new JoinRequest();
		joinReq.setId(req.getParameter("id"));
		joinReq.setName(req.getParameter("name"));
		joinReq.setPassword(req.getParameter("password"));
		joinReq.setConfirmPassword(req.getParameter("confirmPassword"));

		// 오류 정보를 담을 Map 객체 생성
		// エラー情報を保持するMapオブジェクトを生成
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		// 입력값의 유효성 검사
		// 入力値のバリデーションを実行
		joinReq.validate(errors);
		// 오류가 있으면 다시 폼을 보여줌
		// エラーがあれば、再びフォームを表示
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}

		try {
			// 회원 가입 서비스 호출
			// メンバ登録サービスを呼び出す
			joinService.join(joinReq);
			// 성공 페이지로 이동
			// 成功ページに移動
			return "/WEB-INF/view/joinSuccess.jsp";
		} catch (DuplicateIdException e) {
			// 중복된 아이디 예외 발생 시 오류 정보에 추가하고 폼을 다시 보여줌
			// 重複したIDの例外が発生した場合、エラー情報に追加して再びフォームを表示
			errors.put("duplicateId", Boolean.TRUE);
			return FORM_VIEW;
		}
	}
}
