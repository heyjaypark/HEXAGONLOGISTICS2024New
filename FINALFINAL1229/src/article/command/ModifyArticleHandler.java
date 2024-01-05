package  article.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticleData;
import article.service.ArticleNotFoundException;
import article.service.ModifyArticleService;
import article.service.ModifyRequest;
import article.service.PermissionDeniedException;
import article.service.ReadArticleService;
import auth.service.User;
import mvc.command.CommandHandler;


public class ModifyArticleHandler implements CommandHandler {
	private static final String FORM_VIEW = "WEB-INF/view/modifyForm.jsp";

	private ReadArticleService readService = new ReadArticleService();
	private ModifyArticleService modifyService = new ModifyArticleService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO Auto-generated method stub
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

			return null;
		}

	}

	private String processForm(HttpServletRequest req, HttpServletResponse res)
		throws IOException{
			try {
				/*
				 * GET방식으로 수정할 공지사항의 페이지No를 받음 
				 * GET方式で修正するお知らせのページNoを受け取る
				 */
				String noVal = req.getParameter("no");
				int no = Integer.parseInt(noVal);
				
				ArticleData articleData = readService.getArticle(no, false);
				
				/*
				 * 공지사항을 수정하는 사람의 사원번호가 공지사항을 작성한 사람의 사원번호와 일치하는지 확인
				 * お知らせを修正する人の社員番号が、お知らせを作成した人の社員番号と一致しているかどうかを確認する
				 */
				User authUser = (User)req.getSession().getAttribute("authUser");
				if(!canModify(authUser,articleData)) {
					res.sendError(HttpServletResponse.SC_FORBIDDEN);
					return null;
				
				}
				ModifyRequest modReq = new ModifyRequest(authUser.getId(), no,
						articleData.getArticle().getTitle(),
						articleData.getContent());
				
				req.setAttribute("modReq", modReq);
				return FORM_VIEW;
			}catch(ArticleNotFoundException e) {
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}
				
			}
		private boolean canModify(User authUser, ArticleData articleData) {
			String writerId = articleData.getArticle().getWriter().getId();
			return authUser.getId().equals(writerId);
			
		}
		
		
		private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception{
			User authUser =(User)req.getSession().getAttribute("authUser");
			String noVal = req.getParameter("no");
			int no = Integer.parseInt(noVal);
			/*
			 * POST방식으로 수정할 제목, 내용을 받음 
			 * POST方式で修正するタイトル、内容を受け取る
			 */
			ModifyRequest modReq = new ModifyRequest(authUser.getId(),no,
					req.getParameter("title"),
					req.getParameter("content"));
			
			req.setAttribute("modReq", modReq);
			
			
			Map<String, Boolean> errors = new HashMap<>();
			req.setAttribute("errors", errors);
			/*
			 * 수정할 공지사항의 제목과 내용이 입력되지 않을 시 FORM_VIEW로 돌아감
			 * 修正するお知らせのタイトルと内容が入力されない場合、FORM_VIEWに戻る
			 */
			modReq.validate(errors);
			
			if(!errors.isEmpty()) {
				return FORM_VIEW;
				
			}try {
				/*
				 * 공지사항 수정 
				 * お知らせの修正
				 */
				modifyService.modify(modReq);
				return"/WEB-INF/view/modifySuccess.jsp";
			}catch(ArticleNotFoundException e) {
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
				return null;
			}catch(PermissionDeniedException e) {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}
		
		}
		
	}

