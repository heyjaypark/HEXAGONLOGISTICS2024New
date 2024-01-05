package article.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import article.dao.ArticleDao;
import article.model.Article;
import connection.ConnectionProvider;

/*공지사항 목록을 페이징하여 출력하기 위한 Service
お知らせリストをページングして出力するためのサービス*/
public class ListArticleService {

	private ArticleDao articleDao = new ArticleDao();
	/*
	 * 한페이지에 10개씩 출력하기 위한 변수 정의
	 * 1ページに10個ずつ出力するための変数定義
	 */
	private int size = 10;
	
	public ArticlePage getArticlePage(int pageNum) {
		try(Connection conn = ConnectionProvider.getConnection()){
			/*
			 * 총 게시글의 수를 가져옴 
			 * 総投稿数の取得
			 */
			int total = articleDao.selectCount(conn);
			/*
			 * 게시글 1번부터 10번, 11번부터 20번...으로 가져오기위한 매개 변수입력 
			 * 投稿1~10番、11~20番にインポートするためのパラメータ入力
			 */
			List<Article> content = articleDao.select(conn, (pageNum-1)*size+1, (pageNum)*size);
			return new ArticlePage(total,pageNum,size,content);
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
			
		}
	}
	
	
}
