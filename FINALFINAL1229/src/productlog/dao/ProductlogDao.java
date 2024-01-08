package productlog.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtil;
import product.model.Product;
import productlog.service.ProductLogRequest;
import sales.model.SalesList;

//이 클래스는 상품 데이터베이스에 접근하는 DAO클래스입니다.
//このクラスは、商品データベースにアクセスするDAOクラスです。
public class ProductlogDao {

	public List<ProductLogRequest> select1(Connection conn, int startRow, int size) throws SQLException {

		// 지정된 범위 내의 상품 목록을 조회합니다.
		// 指定された範囲の商品リストを取得します。
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ProductLogRequest> result = new ArrayList<>();
		try {

			pstmt = conn.prepareStatement(
					"SELECT * FROM (SELECT ROWNUM AS NUM, product_log.* FROM product_log)WHERE NUM BETWEEN ? AND ? order by l_no desc");
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, size);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				result.add(convertProduct(rs));
			}
			System.out.println("ProductDao3" + startRow);
			System.out.println("ProductDao3" + size);

			return result;

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);

		}
	}

	public List<ProductLogRequest> select2(Connection conn, int p_no, int startRow, int size) throws SQLException {

		// 지정된 범위 내의 상품 목록을 조회합니다.
		// 指定された範囲の商品リストを取得します。
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ProductLogRequest> result = new ArrayList<>();
		try {

			pstmt = conn.prepareStatement(
					"SELECT * FROM (SELECT ROWNUM AS NUM, product_log.* FROM product_log where p_no = ?)WHERE NUM BETWEEN ? AND ? order by l_no desc");
			pstmt.setInt(1, p_no);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, size);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				result.add(convertProduct(rs));
			}
			
			System.out.println("ProductDao" + result);

			return result;

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);

		}
	}
	
	// 상품 테이블의 전체 레코드 수를 조회합니다.
	// 商品テーブルの総レコード数を取得します。
	public int selectCount(Connection conn) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from product_Log");

			if (rs.next()) {
				return rs.getInt(1);

			}
			return 0;

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);

		}
	}

	// ResultSet에서 Product 객체로 변환합니다.
	// ResultSetからProductオブジェクトに変換します。
	private ProductLogRequest convertProduct(ResultSet rs) throws SQLException {
		return new ProductLogRequest(
				rs.getInt("l_no"),
				rs.getString("l_class"),
				rs.getInt("p_no"),
				rs.getString("l_name"),
				rs.getInt("l_seoul"),				
				rs.getInt("l_suwon"),
				rs.getInt("l_incheon"),
				rs.getInt("l_price"),	
				rs.getDate("l_date"),
				rs.getString("l_writer"));
	}
	
	public int searchCount2(Connection conn, int code) throws SQLException {
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			pstmt = conn.prepareStatement("select count(*) from product_log where l_no = ?");			
			pstmt.setInt(1, code);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {			
			
				return rs.getInt(1);
				
			}				
			
			return 0;

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);

		}
	}
	
	/*
	 * 품목코드로 검색한 결과의 총페이지수를 구하기 위한 메소드
	 * 品目コードで検索した結果の総ページ数を求めるためのメソッド
	 * */
	public int searchCount(Connection conn, int code) throws SQLException {
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			pstmt = conn.prepareStatement("select count(*) from product_log where p_no = ?");			
			pstmt.setInt(1, code);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {			
			
				return rs.getInt(1);
				
			}				
			
			return 0;

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);

		}
	}
	
	/*
	 * 판매이력을 품목코드로 검색한 결과를 페이징출력하기 위한 메소드 
	 * 販売履歴を品目コードで検索した結果をページング出力するためのメソッド
	 */
	public List<ProductLogRequest> select3(Connection conn, int l_no, int startRow, int size) throws SQLException {

		// 지정된 범위 내의 상품 목록을 조회합니다.
		// 指定された範囲の商品リストを取得します。
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ProductLogRequest> result = new ArrayList<>();
		try {

			pstmt = conn.prepareStatement(
					"SELECT * FROM (SELECT ROWNUM AS NUM, product_log.* FROM product_log where l_no = ?)WHERE NUM BETWEEN ? AND ? order by l_no desc");
			pstmt.setInt(1, l_no);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, size);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				result.add(convertProduct(rs));
			}
			System.out.println("ProductDao3" + startRow);
			System.out.println("ProductDao3" + size);

			return result;

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);

		}
	}

}
