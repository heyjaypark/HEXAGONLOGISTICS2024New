package article.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import  article.model.Article;
import article.model.Writer;
import jdbc.JdbcUtil;

public class ArticleDao {
	
	/*
	 * 게시글 정보를 입력하기 위한 메소드 
	 * 投稿情報を入力するためのメソッド
	 */
	public Article insert(Connection conn, Article article) throws SQLException {
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(
					"insert into article (article_no,writer_id, writer_name, title, regdate, moddate, read_cnt) values (s_article_list.nextval,?,?,?,?,?,0)");
			//게시글 read오류 시 sequence s_article_list와 ArticleContentDao안의 s_article_list_content가 일치하는지 반드시 확인후 삭제 및 새로생성!!
			pstmt.setString(1, article.getWriter().getId());
			pstmt.setString(2, article.getWriter().getName());
			pstmt.setString(3, article.getTitle());
			pstmt.setTimestamp(4, toTimestamp(article.getRegDate()));
			pstmt.setTimestamp(5, toTimestamp(article.getModifiedDate()));
			int insertedCount = pstmt.executeUpdate();

			if (insertedCount > 0) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select MAX(article_no) from article");
				if (rs.next()) {
					Integer newNum = rs.getInt(1);
					return new Article(newNum, article.getWriter(), article.getTitle(), article.getRegDate(),
							article.getModifiedDate(), 0);
				}

			}
			return null;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);
			JdbcUtil.close(pstmt);
		}
	}

	private Timestamp toTimestamp(Date date) {
		return new Timestamp(date.getTime());

	}

	/*
	 * 게시글 총페이지수를 가져오기 위한 메소드(전체 게시글 수)
	 * 投稿の総ページ数を取得するためのメソッド（全体投稿数
	 */
	public int selectCount(Connection conn) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from article");

			if (rs.next()) {
				return rs.getInt(1);

			}
			return 0;

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);

		}
	}

	/*
	 * 게시글 목록을 페이징하여 출력하기 위한 메소드 
	 * 投稿リストをページングして出力するためのメソッド
	 */
	public List<Article> select(Connection conn, int startRow, int size) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("SELECT * FROM (SELECT ROWNUM AS NUM, article.* FROM article)WHERE NUM BETWEEN ? AND ? order by article_no");
			
			  pstmt.setInt(1, startRow); 
			  pstmt.setInt(2, size);
			 
			rs = pstmt.executeQuery();
			List<Article> result = new ArrayList<>();
			while (rs.next()) {
				result.add(convertArticle(rs));
				
			}
		
			return result;
           
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);

		}
	}

	private Article convertArticle(ResultSet rs) throws SQLException {
		return new Article(rs.getInt("article_no"), new Writer(rs.getString("writer_id"), rs.getString("writer_name")),
				rs.getString("title"), toDate(rs.getTimestamp("regdate")), toDate(rs.getTimestamp("moddate")),
				rs.getInt("read_cnt"));

	}

	private Date toDate(Timestamp timestamp) {
		return new Date(timestamp.getTime());
	}


	/*
	 * 게시글이 존재하는지 확인하기 위한 메소드 
	 * 投稿が存在するかどうかを確認するためのメソッド
	 */
	public Article selectById(Connection conn, int no) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from article where article_no = ?");
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			Article article = null;

			if (rs.next()) {
				article = convertArticle(rs);

			}
			System.out.println(article);
			return article;

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);

		}
	}

	/*
	 * 공지사항 조회시 조회수를 증가시키기 위한 메소드 
	 * お知らせ照会時に再生数を増加させるためのメソッド
	 */
public void increaseReadCount(Connection conn, int no) throws SQLException{
	try(PreparedStatement pstmt= conn.prepareStatement("update article set read_cnt=read_cnt+1 where article_no=?")){
		pstmt.setInt(1, no);
		pstmt.executeUpdate();
		
	}
}

/*
 * 공지사항 수정시 사용하는 메소드 
 * お知らせを修正する際に使用するメソッド
 */
public int update(Connection conn, int no, String title) throws SQLException{
	try(PreparedStatement pstmt= conn.prepareStatement("update article set title = ?, moddate=sysdate where article_no = ?")){
		pstmt.setString(1, title);
		pstmt.setInt(2, no);
		return pstmt.executeUpdate();
		
			
	}
}

/*
 * 공지사항 삭제시 사용하는 메소드 
 * お知らせ削除時に使用するメソッド
 */
public int delete(Connection conn, int no, String title) throws SQLException{
	try(PreparedStatement pstmt= conn.prepareStatement("delete from article where article_no = ?")){
		pstmt.setInt(1, no);
		return pstmt.executeUpdate();
		
			
	}
}


}