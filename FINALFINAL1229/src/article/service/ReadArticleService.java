package article.service;

import java.sql.Connection;
import java.sql.SQLException;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import article.model.ArticleContent;
import connection.ConnectionProvider;

/*공지사항의 게시글의 내용을 가져오기 위한 Service
お知らせの投稿内容を取得するためのサービス*/
public class ReadArticleService {
	
	private ArticleDao articleDao = new ArticleDao();
	private ArticleContentDao contentDao = new ArticleContentDao();
	
	public ArticleData getArticle(int articleNum, boolean increaseReadCount) {
		try(Connection conn = ConnectionProvider.getConnection()){
			
			/*
			 * 목록에서 게시글 선택 시 게시글 번호가 존재하는지 확인 후 존재하지 않을 시 Exception 발생
			 * リストから投稿を選択すると、投稿番号が存在するか確認した後、存在しない場合はExceptionが発生
			 */
			Article article = articleDao.selectById(conn, articleNum);
			if(article==null) {
				throw new ArticleNotFoundException();
				
			}
			ArticleContent content = contentDao.selectById(conn, articleNum);
			if(content==null) {
				throw new ArticleContentNotFoundException();
				
			} /*
				 * 게시글 조회 후 조회수를 1증가 
				 * 書き込み照会後、再生数を1増加
				 */
			if(increaseReadCount) {
				articleDao.increaseReadCount(conn, articleNum);
				
			}
			return new ArticleData(article,content);
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
