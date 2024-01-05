package product.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import product.model.Product;
import product.service.ProductPage;
import product.service.ProductSearchService;


//이 클래스는 상품 검색을 처리하는 핸들러 클래스입니다.
//このクラスは商品の検索を処理するハンドラークラスです。
public class ProductSearchHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/Productlist.jsp";
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
		String p_noval = req.getParameter("code");

		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		errors.put("notnull", Boolean.FALSE);
		// 에러를 저장할 Map을 생성하고 기본값을 설정합니다.
				// エラーを格納するMapを作成し、デフォルト値を設定します。

		String pageNoVal = req.getParameter("pageNo");
		int p_no = 0;
		int pageNo = 1;

		String search = req.getParameter("select_num");
		int val = Integer.parseInt(search);

		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}/*
		 * 스트링 자료형을 int로 바꿔주는 작업
		 */
		

		

		try {

			if (val == 1) {
				p_no = Integer.parseInt(p_noval);
				ProductPage product1 = productsearch.SearchProduct(pageNo,p_no);
				req.setAttribute("productPage", product1);
				errors.put("notnull", Boolean.TRUE);
				return "/WEB-INF/view/ProductSearchlist.jsp";
			} else if (val == 2) {
				ProductPage productPage = productsearch.SearchProduct1(pageNo, p_noval);
				req.setAttribute("productPage", productPage);
				errors.put("notnull", Boolean.TRUE);
				return "/WEB-INF/view/ProductSearchlist.jsp";
			}

			return FORM_VIEW;
			
		} catch (NumberFormatException e) {
			// 숫자 변환 예외가 발생하면 에러를 설정하고 뷰로 이동합니다.
			// 数字変換例外が発生した場合、エラーを設定してビューに移動します。
			errors.put("NumberFormatException", Boolean.TRUE);
			return "/WEB-INF/view/Productlist.jsp";
		}
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) {
		String p_noval = req.getParameter("code");

		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		errors.put("notnull", Boolean.FALSE);
		// 에러를 저장할 Map을 생성하고 기본값을 설정합니다.
				// エラーを格納するMapを作成し、デフォルト値を設定します。

		String pageNoVal = req.getParameter("pageNo");
		int p_no = 0;
		int pageNo = 1;

		String search = req.getParameter("select_num");
		int val = Integer.parseInt(search);

		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}/*
		 * 스트링 자료형을 int로 바꿔주는 작업
		 */
		

		

		try {

			if (val == 1) {
				p_no = Integer.parseInt(p_noval);
				ProductPage product1 = productsearch.SearchProduct(pageNo,p_no);
				req.setAttribute("productPage", product1);
				errors.put("notnull", Boolean.TRUE);
				return "/WEB-INF/view/ProductSearchlist.jsp";
			} else if (val == 2) {
				ProductPage productPage = productsearch.SearchProduct1(pageNo, p_noval);
				req.setAttribute("productPage", productPage);
				errors.put("notnull", Boolean.TRUE);
				return "/WEB-INF/view/ProductSearchlist.jsp";
			}

			return FORM_VIEW;
			
		} catch (NumberFormatException e) {
			// 숫자 변환 예외가 발생하면 에러를 설정하고 뷰로 이동합니다.
			// 数字変換例外が発生した場合、エラーを設定してビューに移動します。
			errors.put("NumberFormatException", Boolean.TRUE);
			return "/WEB-INF/view/Productlist.jsp";
		}

	}
}
