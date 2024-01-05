package sales.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import product.model.Product;
import sales.model.RegistRequest;
import sales.service.SelectSalesService;

public class SelectSalesHandler implements CommandHandler {

	private static final String FORM_VIEW = "/WEB-INF/view/RegistSales.jsp";
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
		SelectSalesService selectProduct = new SelectSalesService();
		Product prod = new Product(0, null, 0, 0, 0, 0);
		
		int p_no = 0;
		String p_noVal = req.getParameter("p_no");
		/*품목번호는 정수여야한다.
		 *品目番号は整数でなければならない。*/
	    if (p_noVal == null || p_noVal.trim().isEmpty()) {
	        Map<String, Boolean> errors = new HashMap<>();
	        errors.put("p_no", true);
	        req.setAttribute("errors", errors);
	        return FORM_VIEW;
	    }
		
	    try {
	        p_no = Integer.parseInt(p_noVal);
	    } catch (NumberFormatException e) {
	        // 숫자로 변환할 수 없는 경우에 대한 처리
	        Map<String, Boolean> errors = new HashMap<>();
	        errors.put("p_no", true);
	        req.setAttribute("errors", errors);
	        return FORM_VIEW;
	    }
		
		
		
		 /*select 메서드를 실행해 결과를 반환한다.
	     * セレクトメソッドを実行して結果を返す。*/
		prod = selectProduct.selectProduct(p_no);
		
		salesReq.setP_no(p_no);
		
		req.setAttribute("prod", prod);
		
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);

		salesReq.validate(errors);

		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		return FORM_VIEW;
		
	}
	

	

}
