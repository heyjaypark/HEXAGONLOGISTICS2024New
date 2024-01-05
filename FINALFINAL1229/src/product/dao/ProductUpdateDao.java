package product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jdbc.JdbcUtil;
import product.model.Product;
import product.model.ProductRequest;

public class ProductUpdateDao {
	// 페이지별로 상품 리스트를 조회하는 메서드
	// ページごとに商品リストを取得するメソッド
	public List<Product> select1(Connection conn, int startRow, int size) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> result = new ArrayList<>();
		try {

			pstmt = conn.prepareStatement(
					"SELECT * FROM (SELECT ROWNUM AS NUM, product_list.* FROM product_list)WHERE NUM BETWEEN ? AND ? order by p_no");
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

	// 전체 상품 수를 조회하는 메서드
	// 全商品数を取得するメソッド
	public int selectCount(Connection conn) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from product_list");

			if (rs.next()) {
				return rs.getInt(1);

			}
			return 0;

		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(stmt);

		}
	}

	// ResultSet에서 Product 객체로 변환하는 메서드
	// ResultSetからProductオブジェクトに変換するメソッド
	private Product convertProduct(ResultSet rs) throws SQLException {
		return new Product(rs.getInt("p_no"), rs.getString("p_name"), rs.getInt("p_seoul"), rs.getInt("p_suwon"),
				rs.getInt("p_incheon"), rs.getInt("price"));

	}

	// 상품 번호로 상품을 조회하는 메서드
	// 商品番号で商品を取得するメソッド
	public Product selectById(Connection conn, int p_no) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			pstmt = conn.prepareStatement("select * from product_list where p_no = ?");
			pstmt.setInt(1, p_no);
			rs = pstmt.executeQuery();

			Product product = null;
			if (rs.next()) {
				product = new Product(rs.getInt("p_no"), rs.getString("p_name"), rs.getInt("p_seoul"),
						rs.getInt("p_suwon"), rs.getInt("p_incheon"), rs.getInt("price"));

			}
			return product;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}

	}

	// 상품 정보를 업데이트하는 메서드
	// 商品情報を更新するメソッド

	public void update(Connection conn, ProductRequest member) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement(
				"update product_list set p_seoul = p_seoul + ?,p_suwon= p_suwon + ?, p_incheon = p_incheon + ?,price=? where p_no = ?")) {
			
			pstmt.setInt(1, member.getP_seoul());
			pstmt.setInt(2, member.getP_suwon());
			pstmt.setInt(3, member.getP_incheon());
			pstmt.setInt(4, member.getPrice());
			pstmt.setInt(5, member.getP_no());

			pstmt.executeUpdate();
		}
	}
	public void L_update(Connection conn, ProductRequest mem) throws SQLException {
		try (PreparedStatement pstmt = conn
				.prepareStatement("insert into product_log values(S_PRODUCT_log.nextval,?,?,?,?,?,?,?,?,?)")) {
			pstmt.setString(1, "入庫");
			pstmt.setInt(2, mem.getP_no());
			pstmt.setString(3, mem.getP_name());
			pstmt.setInt(4, mem.getP_seoul());
			pstmt.setInt(5, mem.getP_suwon());
			pstmt.setInt(6, mem.getP_incheon());
			pstmt.setInt(7, mem.getPrice());
			pstmt.setDate(8, mem.getDate());
			pstmt.setString(9, mem.getWriter());
			pstmt.executeUpdate();
		}
	}

}
