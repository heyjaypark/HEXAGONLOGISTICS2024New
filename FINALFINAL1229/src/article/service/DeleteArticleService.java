package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import connection.ConnectionProvider;
import jdbc.JdbcUtil;

/*게시글 삭제를 하기 위한 Service
投稿を削除するためのサービス*/
public class DeleteArticleService {

	private ArticleDao articleDao = new ArticleDao();
	private ArticleContentDao contentDao = new ArticleContentDao();

	public void delete(ModifyRequest modReq) {
		Connection conn=null;
		
		try {
			conn=ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			/*
			 * 삭제할 게시글이 존재하는지 확인 
			 * 削除する掲示物が存在するか確認
			 */
			Article article=articleDao.selectById(conn, modReq.getArticleNumber());
			if(article==null) {
				throw new ArticleNotFoundException();
			}
			/*
			 * 삭제하는 작성자의 사원번호가 글을 쓴 작성자의 사원번호와 일치한지 확인 
			 * 削除する作成者の社員番号が書いた作成者の社員番号と一致しているか確認
			 */
			if(!canModify(modReq.getUserId(),article)) {
				throw new PermissionDeniedException();
				
			}
			
			/*
			 * 게시글삭제 Dao 
			 * 掲示文削除Dao
			 */
			articleDao.delete(conn, modReq.getArticleNumber(), modReq.getTitle());
			contentDao.delete(conn, modReq.getArticleNumber(), modReq.getContent());
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