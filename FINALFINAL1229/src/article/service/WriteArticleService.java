package article.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import article.model.ArticleContent;
import connection.ConnectionProvider;
import jdbc.JdbcUtil;


public class WriteArticleService {
	
	private ArticleDao articleDao = new ArticleDao();
	private ArticleContentDao contentDao = new ArticleContentDao();
	
	public Integer write(WriteRequest req) {
		Connection conn=null;
		try {
			conn=ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			Article article=toArticle(req);
			/*
			 * 공지사항 글번호, 작성자,제목, 시간, 조회수를 입력하는 Service
			 * お知らせ文番号、作成者、タイトル、時間、照会数を入力するサービス
			 */
			Article savedArticle=articleDao.insert(conn, article);
			if(savedArticle==null) {
				throw new RuntimeException("fail to insert article");
						}
			ArticleContent content = new ArticleContent(savedArticle.getNumber(),req.getContent());
			/*
			 * 공지사항 내용 작성자, 제목, 내용을 입력하는 Service
			 * お知らせ内容の作成者、タイトル、内容を入力するサービス
			 */
			ArticleContent savedContent=contentDao.insert(conn,content);
			
			/*
			 * 공지사항 내용에 아무것도 입력되지 않을 시 
			 * Exception 발생 お知らせ内容に何も入力されない場合、Exception発生
			 */
			if(savedContent==null) {
				throw new RuntimeException("fail to insert article_content");
				
			}
			conn.commit();
			return savedArticle.getNumber();
			
		}catch(SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		}catch(RuntimeException e) {
			JdbcUtil.rollback(conn);
			throw e;
			
		}finally {
			JdbcUtil.close(conn);
		}
	}

	/*
	 * 공지사항 작성시 입력 할 현재 시간을 구하기 위한 메소드 
	 * お知らせの作成時に入力する現在の時間を求めるためのメソッド
	 */
	private Article toArticle(WriteRequest req) {
		Date now=new Date();
		return new Article(null,req.getWriter(),req.getTitle(),now,now,0);
	}
	

}
