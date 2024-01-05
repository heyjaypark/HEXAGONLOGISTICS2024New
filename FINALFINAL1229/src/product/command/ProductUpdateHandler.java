package product.command;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import product.model.ProductRequest;
import product.service.NoMinusException;
import product.service.ProductUpdateService;

//이 클래스는 상품 수정을 처리하는 핸들러 클래스입니다.
//このクラスは商品の更新を処理するハンドラークラスです。
public class ProductUpdateHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/Productupdates.jsp";
	private ProductUpdateService productService = new ProductUpdateService();

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
		ProductRequest productReq = new ProductRequest();
		String p_novals = req.getParameter("p_no");
		String p_seoulval = req.getParameter("p_seoul");
		String p_suwonval = req.getParameter("p_suwon");
		String p_incheonval = req.getParameter("p_incheon");
		String p_priceval = req.getParameter("price");
		String L_dateval = req.getParameter("date");
		String L_writer = req.getParameter("writer");
		int p_no = 0;
		int p_seoul = 0;
		int p_suwon = 0;
		int p_incheon = 0;
		int price = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date utilDate = null;
		java.sql.Date L_date = null;

		// 에러를 저장할 Map을 생성하고 기본값을 설정합니다.
		// エラーを保存するMapを作成し、デフォルト値を設定します。
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		errors.put("numberInsert", null);
		
		try {
			if (L_dateval == null || L_dateval.trim().isEmpty()) {
				errors.put("salesDateError", true); return FORM_VIEW;
			} else {
				utilDate = dateFormat.parse(L_dateval);
				L_date = new java.sql.Date(utilDate.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		try {
			// 입력 값을 숫자로 변환합니다.
			// 入力値を数字に変換します
			p_no = Integer.parseInt(p_novals);
			p_seoul = Integer.parseInt(p_seoulval);
			p_suwon = Integer.parseInt(p_suwonval);
			p_incheon = Integer.parseInt(p_incheonval);
			price = Integer.parseInt(p_priceval);

			productReq.setP_no(p_no);
			productReq.setP_name(req.getParameter("p_name"));
			productReq.setP_seoul(p_seoul);
			productReq.setP_suwon(p_suwon);
			productReq.setP_incheon(p_incheon);
			productReq.setPrice(price);
			productReq.setWriter(L_writer);
			productReq.setDate(L_date);
			if (p_seoul < 0 || p_suwon < 0 || p_incheon < 0 || price < 0) {
				throw new NoMinusException();
			} else {

				productService.productUpdate(productReq);
				errors.put("successUpdate", Boolean.TRUE);
				return "/WEB-INF/view/Productupdates.jsp";
			}
		} catch (NumberFormatException e) {

			// 숫자 변환 예외가 발생한 경우, 오류를 설정하고 폼 뷰로 이동합니다.
			// 数字変換例外が発生した場合、エラーを設定してフォームビューに移動します。
			errors.put("numberInsert", Boolean.TRUE);
			return FORM_VIEW;
		} catch (NoMinusException e) {
			errors.put("NoMinus", true);
			return FORM_VIEW;
		}
	}
}
