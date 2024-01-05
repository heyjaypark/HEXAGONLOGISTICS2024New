package article.command;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticlePage;
import article.service.ListArticleService;
import mvc.command.CommandHandler;


public class ListArticleHandler implements CommandHandler {
	
	
	
	private ListArticleService listService = new ListArticleService();
	

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res)throws Exception{

		/*
		 * GET방식으로 요청한 페이지의 번호를 가져옴 
		 * GET方式で要請したページの番号を取得
		 */
	String pageNoVal=req.getParameter("pageNo");
	/*
	 * 초기 페이지 번호가 없을 시 1페이지로 시작 
	 * 初期ページ番号がない場合は1ページからスタート
	 */
	int pageNo=1;
	if(pageNoVal!=null) {
		pageNo=Integer.parseInt(pageNoVal);
	}
	/*
	 * 해당 페이지에 출력될 공지사항 정보를 가져옴 
	 * 該当ページに出力されるお知らせ情報を取得
	 */
	ArticlePage articlePage=listService.getArticlePage(pageNo);
	
	req.setAttribute("articlePage", articlePage);
	
	Enumeration<String> attributes = req.getSession().getAttributeNames();
	while (attributes.hasMoreElements()) {
	    String attribute = (String) attributes.nextElement();
	    System.err.println(attribute+" : "+req.getSession().getAttribute(attribute));
	}
	
	return "/WEB-INF/view/listArticle.jsp";
	}
	
}






