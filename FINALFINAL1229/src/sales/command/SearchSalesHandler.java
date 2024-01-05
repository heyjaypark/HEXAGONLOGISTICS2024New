package sales.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import sales.service.SalesPage;
import sales.service.SearchSalesService;

public class SearchSalesHandler implements CommandHandler {
	
	SearchSalesService searchService = new SearchSalesService();
	
	
	private static final String FORM_VIEW = "/WEB-INF/view/ListSales_Searchview.jsp";
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
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		try {
			
			String pageNoVal=req.getParameter("pageNo");
		int pageNo = 1;
		
		String search = req.getParameter("select_num");
		int val = Integer.parseInt(search);
		
		pageNo = Integer.parseInt(req.getParameter("pageNo"));
		
		/*
		 * 스트링 자료형을 int로 바꿔주는 작업
		 * */
		String codeval = req.getParameter("code");
		int code = Integer.parseInt(codeval);
		
		  if (val == 2) { 
			  SalesPage salesPage = searchService.getSalesPage(pageNo, code); 
			  req.setAttribute("salesPage", salesPage);
		  }  else if ( val == 1) {
				  SalesPage salesPage = searchService.getSalesPage2(pageNo, code);
				  req.setAttribute("salesPage", salesPage);
		 }	 
				
		return FORM_VIEW;
	} catch (NumberFormatException e) {
		errors.put("Notnum", Boolean.TRUE);
		return FORM_VIEW;
	}}
	
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) {
		
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		try {
			
		
		int pageNo = 1;
		String search = req.getParameter("select_num");
		int val = Integer.parseInt(search);
		System.out.println(val);
		
		/*
		 * 스트링 자료형을 int로 바꿔주는 작업
		 * */
		String codeval = req.getParameter("code");
		int code = Integer.parseInt(codeval);
		
		  if (val == 2) { 
			  SalesPage salesPage = searchService.getSalesPage(pageNo, code); 
			  req.setAttribute("salesPage", salesPage);
		  }  else if ( val == 1) {
				  SalesPage salesPage = searchService.getSalesPage2(pageNo, code);
				  req.setAttribute("salesPage", salesPage);
		 }	 
				
		return FORM_VIEW;
	} catch (NumberFormatException e) {
		errors.put("Notnum", Boolean.TRUE);
		return FORM_VIEW;
	}
		
	}
	

	

}
