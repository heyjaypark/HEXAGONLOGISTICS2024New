package article.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.model.Writer;
import article.service.WriteArticleService;
import article.service.WriteRequest;
import auth.service.User;
import mvc.command.CommandHandler;


public class WriteArticleHandler implements CommandHandler {
	
	
	private static final String FORM_VIEW = "/WEB-INF/view/newArticleForm.jsp";
	private WriteArticleService writeService = new WriteArticleService();
	

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res){
		// TODO Auto-generated method stub
	if(req.getMethod().equalsIgnoreCase("GET")) {
		return processForm(req,res);
	}else if(req.getMethod().equalsIgnoreCase("POST")) {
		return processSubmit(req,res);
		
	}else {
		res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		return null;
	}
	}
	
	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}
	
	private String processSubmit(HttpServletRequest req, HttpServletResponse res) {
	    Map<String,Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		
		User user = (User)req.getSession(false).getAttribute("authUser");
		WriteRequest writeReq = createWriteRequest(user,req);
		writeReq.validate(errors);
		/*
		 * 공지사항 작성시 내용이 없으면 FORM_VIEW로 돌아감 
		 * お知らせ作成時、内容がなければFORM_VIEWに戻る
		 */
		if(!errors.isEmpty()) {
			return FORM_VIEW;
			
		}
		/*
		 * 공지사항 입력 
		 * お知らせ入力
		 */
		int newArticleNo = writeService.write(writeReq);
		req.setAttribute("newArticleNo", newArticleNo);
		errors.put("success",Boolean.TRUE);
		
	
		return "/WEB-INF/view/newArticleSuccess.jsp";
		
			
	
}

/*
 * 공지사항 작성시의 데이터를 담기위한 함수 
 * お知らせ作成時のデータを盛り込むための関数
 */
private WriteRequest createWriteRequest(User user, HttpServletRequest req) {
	return new WriteRequest(
		new Writer(user.getId(),user.getName()),
		req.getParameter("title"),req.getParameter("content"));
	}
}






