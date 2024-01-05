package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import connection.ConnectionProvider;
import jdbc.JdbcUtil;

/*공지사항 수정시 사용하는 Service
お知らせの修正時に使用するサービス*/
public class ModifyArticleService {

	private ArticleDao articleDao = new ArticleDao();
	private ArticleContentDao contentDao = new ArticleContentDao();

	public void modify(ModifyRequest modReq) {
		Connection conn=null;
		
		try {
			conn=ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			/*
			 * 수정할 공지사항의 글번호가 있는지 확인 후 존재하지 않을 시 Exception 발생
			 * 修正するお知らせの書き込み番号があるか確認後、存在しない場合、Exception発生
			 */
			Article article=articleDao.selectById(conn, modReq.getArticleNumber());
		
			if(article==null) {
				throw new ArticleNotFoundException();
			}
			/*
			 * 공지사항을 작성한 사람의 사원번호와 수정하려는 사원번호가 동일한 지 확인후 미일치 시 Exception 발생
			 * お知らせ事項を作成した人の社員番号と修正しようとする社員番号が同一であることを確認した後、不一致時にException発生
			 */
			
			if(!canModify(modReq.getUserId(),article)) {
				throw new PermissionDeniedException();
				
			}
			/*
			 * Dao에서 공지사항 및 내용을 수정 
			 * Daoでお知らせと内容を修正
			 */
			articleDao.update(conn, modReq.getArticleNumber(), modReq.getTitle());
			contentDao.update(conn, modReq.getArticleNumber(), modReq.getContent());
			conn.commit();
		}catch(SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		}catch(PermissionDeniedException e)
	{
		JdbcUtil.rollback(conn);
		throw e;
		
	}finally
	{
		JdbcUtil.close(conn);
	}
}

	private boolean canModify(String modfyingUserId, Article article) {
		return article.getWriter().getId().equals(modfyingUserId);

	}
}