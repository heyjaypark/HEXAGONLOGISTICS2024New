package sales.command;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import sales.model.RegistRequest;
import sales.service.RegistSalesService;

/*판매내역 등록요청을 처리하는 핸들러
 *販売履歴の登録のリクエストを処理するハンドラー*/
public class RegistSalesHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/RegistSales.jsp";
	private RegistSalesService salesService = new RegistSalesService();

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

	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		
		
		return FORM_VIEW;
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) {

		RegistRequest salesReq = new RegistRequest();

		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);


		int p_no = 0;
		int s_seoul = 0;
		int s_suwon = 0;
		int s_incheon = 0;
		int p_seoul = 0;
		int p_suwon = 0;
		int p_incheon = 0;
		String s_registrant = req.getParameter("s_registrant");

		/*파라미터를 통해 전달된 문자열 값을 담는다.
		パラメータを通じて伝達された文字列の値を盛り込む。*/
		String p_noVal = req.getParameter("p_no");
		String s_seoulVal = req.getParameter("s_seoul");
		String s_suwonVal = req.getParameter("s_suwon");
		String s_incheonVal = req.getParameter("s_incheon");
		String s_dateVal = req.getParameter("s_date");
		String p_seoulVal = req.getParameter("p_seoul");
		String p_suwonVal = req.getParameter("p_suwon");
		String p_incheonVal = req.getParameter("p_incheon");


		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date utilDate = null;
		java.sql.Date s_date = null;


		/*판매량은 적어도 하나는 입력해야한다.
 		販売量は少なくとも一つは入力しなければならない。*/
		if (s_seoulVal.trim().isEmpty() && s_suwonVal.trim().isEmpty() && s_incheonVal.trim().isEmpty()) {
			errors.put("salesError", true);
			return FORM_VIEW;
		}

		/* 입력된 판매일 값이 비어있지 않다면 DATE타입으로 변환한다.
 		入力された販売日の値が空白ではなかったらDATEタイプに変換する。*/
		try {
			if (s_dateVal == null || s_dateVal.trim().isEmpty()) {
				errors.put("salesDateError", true); return FORM_VIEW;
			} else {
				utilDate = dateFormat.parse(s_dateVal);
				s_date = new java.sql.Date(utilDate.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}





		try { /* 각 문자열을 정수형으로 변환한다. 이 때, 지점별 판매량이나 재고량이 비어있을 경우엔 0으로 한다.
				各文字列を整数型に変換する。この時、支店別販売量や在庫量が空いている場合には０とする。*/
			p_no = Integer.parseInt(p_noVal);
			s_seoul = !s_seoulVal.isEmpty() ? Integer.parseInt(s_seoulVal) : 0;
			s_suwon = !s_suwonVal.isEmpty() ? Integer.parseInt(s_suwonVal) : 0;
			s_incheon = !s_incheonVal.isEmpty() ? Integer.parseInt(s_incheonVal) : 0;
			p_seoul = !p_seoulVal.isEmpty() ? Integer.parseInt(p_seoulVal) : 0;
			p_suwon = !p_suwonVal.isEmpty() ? Integer.parseInt(p_suwonVal) : 0;
			p_incheon = !p_incheonVal.isEmpty() ? Integer.parseInt(p_incheonVal) : 0;

			/* 판매량은 재고량보다 크거나 음수가 될 수 없다.
			販売量は在庫量より大きいかマイナスになることはできない。*/
			if ((s_seoul < 0 || s_seoul > p_seoul) || (s_suwon < 0 || s_suwon > p_suwon) || (s_incheon < 0 || s_incheon > p_incheon)) {
				throw new IllegalArgumentException("販売量が形式に合いません。");
			}

			/* 얻은 파라미터 값을 쿼리문 작성에 필요한 객체에 저장한다.
			得られたパラメータ値をクエリ文を作成するために必要なオブジェクトに保存する。*/
			salesReq.setP_no(p_no);
			salesReq.setS_seoul(s_seoul);
			salesReq.setS_suwon(s_suwon);
			salesReq.setS_incheon(s_incheon);
			salesReq.setS_date(s_date);
			salesReq.setS_registant(s_registrant);

			salesReq.validate(errors);

			/* 에러가 없다면 서비스를 실행하고 페이지를 반환한다.
			エラーがなし場合、サービスを実行し、ぺーじを返す。*/
			if (!errors.isEmpty()) {
				return FORM_VIEW;
			} else {
				salesService.registSales(salesReq);
				
				 errors.put("success", true); 
				return FORM_VIEW;
			}
			/*여러가지 에러가 발생할 경우의 처리
			様々なエラーが発生する場合の処理*/
		} catch (NumberFormatException e) {
			errors.put("numberFormat", true);
			return FORM_VIEW;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			errors.put("salesRateError", true);
			req.setAttribute("salesRateErrorMessage", "販売量は在庫量より大きいか負の数にすることはできません。");
			return FORM_VIEW;
		} catch (SQLException e) {//データベースのエラー 데이터베이스의 에러
			e.printStackTrace();
			errors.put("databaseError", Boolean.TRUE);
			req.setAttribute("databaseErrorMessage", "データベースの処理中にエラーが発生しました。");
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // HTTP 状態コード設定
			return FORM_VIEW;
		} catch (Exception e) { //その他の不明なエラー 그 밖의 알 수 없는 에러
			e.printStackTrace();
			errors.put("unknownError", Boolean.TRUE);
			req.setAttribute("unknownErrorMessage", "不明なエラーが発生しました。");
			return FORM_VIEW;
		}

	}

}