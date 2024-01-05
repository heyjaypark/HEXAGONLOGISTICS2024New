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
import product.service.ProductRegiService;

//이 클래스는 상품 등록을 처리하는 핸들러 클래스입니다.
//このクラスは商品の登録を処理するハンドラークラスです。
public class ProductRegiHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/Productregi.jsp";
	private ProductRegiService productService = new ProductRegiService();

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
		 String p_noval = req.getParameter("p_no"); 
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

		// 에러를 담을 맵을 생성하고 기본값으로 설정합니다.
		// エラーを保持するためのMapを作成し、デフォルト値を設定します。
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		errors.put("numberInsert", null);
		errors.put("duplicateno", Boolean.FALSE);
		
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
			// 입력값을 숫자로 변환합니다.
			// 入力値を数字に変換します。
			
			p_seoul = Integer.parseInt(p_seoulval);
			p_suwon = Integer.parseInt(p_suwonval);
			p_incheon = Integer.parseInt(p_incheonval);
			price = Integer.parseInt(p_priceval);
			// 음수가 입력된 경우 예외를 발생시킵니다.
			// 負の値が入力された場合、例外を発生させます。
			if (p_seoul < 0 || p_suwon < 0 || p_incheon < 0) {
				throw new NoMinusException();

			} else {
				// ProductRequest 객체에 값을 설정합니다.
				// ProductRequestオブジェクトに値を設定します。
				productReq.setP_no(p_no);
				productReq.setP_name(req.getParameter("p_name"));
				productReq.setP_seoul(p_seoul);
				productReq.setP_suwon(p_suwon);
				productReq.setP_incheon(p_incheon);
				productReq.setPrice(price);
				productReq.setWriter(L_writer);
				productReq.setDate(L_date);
				System.out.println("reghandler"+productReq.getP_name());

				productService.productregi(productReq);
				errors.put("successRegi", Boolean.TRUE);
				return "/WEB-INF/view/Productregi.jsp";

			}
		} catch (NoMinusException e) {
			// 음수 예외가 발생한 경우 에러를 설정하고 폼 뷰로 이동합니다
			// 負の数の例外が発生した場合、エラーを設定してフォームビューに移動します。
			errors.put("NoMinus", true);
			return FORM_VIEW;
		} catch (NumberFormatException e) {
			/*
			 * 입력값이 포맷과 맞지않을 경우 에러를 설정하고 폼 뷰로 이동합니다. 
			 * 入力値がフォーマットと合わない場合は、エラーを設定してフォームビューに移動します。
			 */
			errors.put("numberInsert", Boolean.TRUE);
			return FORM_VIEW;
		} /*
			 * catch (Exception e) { // TODO: handle exception e.printStackTrace(); return
			 * FORM_VIEW; }
			 */
	}
}
