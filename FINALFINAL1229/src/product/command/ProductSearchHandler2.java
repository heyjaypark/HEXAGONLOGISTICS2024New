package product.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import product.model.Product;
import product.service.ProductSearchService;

//이 클래스는 재고 수정페이지의 상품 검색을 처리하는 핸들러 클래스입니다.
//このクラスは、在庫修正ページの商品検索を処理するハンドラークラスです。
public class ProductSearchHandler2 implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/Productregi.jsp";
	private ProductSearchService productsearch = new ProductSearchService();

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
		String p_noval = req.getParameter("p_no");

		int p_no = 0;

		// 에러를 저장할 Map을 만들고 기본값을 설정합니다.
		// エラーを保存するためのMapを作成し、デフォルト値を設定します。
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		errors.put("NumberFormatException", Boolean.FALSE);

		try {
			// 입력 값을 숫자로 변환합니다.
			// 入力値を数字に変換します。
			p_no = Integer.parseInt(p_noval);
			// 상품 검색 서비스를 호출합니다.
			// 商品検索サービスを呼び出します。
			Product product1 = productsearch.SearchProduct2(p_no);
			errors.put("notnull", Boolean.TRUE);
			req.setAttribute("product1", product1);
			return "/WEB-INF/view/Productupdates.jsp";
		} catch (NumberFormatException e) {
			// 숫자 변환 예외가 발생한 경우, 에러를 설정하고 뷰로 이동합니다.
			// 数字変換例外が発生した場合、エラーを設定してビューに移動します。
			errors.put("NumberFormatException", Boolean.TRUE);
			return "/WEB-INF/view/Productupdates.jsp";
		}

	}
}
