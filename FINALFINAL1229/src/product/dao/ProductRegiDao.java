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

//이 클래스는 상품 데이터베이스에 액세스하는 DAO  클래스입니다.
//このクラスは、DAO クラスであり、商品データベースへのアクセスを担当しています。
public class ProductRegiDao {
	// 지정된 범위의 상품 목록을 가져옵니다.
	// 指定された範囲の商品リストを取得します
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

	// 상품 테이블의 전체 레코드 수를 가져옵니다.
	// 商品テーブルの総レコード数を取得します。
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

	// ResultSet에서 Product 객체로 변환합니다.
	// ResultSetからProductオブジェクトに変換します。
	private Product convertProduct(ResultSet rs) throws SQLException {
		return new Product(rs.getInt("p_no"), rs.getString("p_name"), rs.getInt("p_seoul"), rs.getInt("p_suwon"),
				rs.getInt("p_incheon"), rs.getInt("price"));

	}

	// 지정된 상품 코드에 해당하는 상품을 가져옵니다.
	// 指定された商品コードに対応する商品を取得します。
	public int selectById(Connection conn, int p_no) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int p_nova = 0;
		try {

			pstmt = conn.prepareStatement("select * from product_list where p_no = ?");
			pstmt.setInt(1, p_no);
			rs = pstmt.executeQuery();

			ProductRequest product = null;
			if (rs.next()) {
				product = new ProductRequest();
				p_nova = rs.getInt("p_no");

			}
			return p_nova;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}

	}

	// 새로운 상품을 데이터베이스에 등록합니다.
	// 新しい商品をデータベースに登録します。
	public void insert(Connection conn, ProductRequest mem) throws SQLException {
		try (PreparedStatement pstmt = conn
				.prepareStatement("insert into product_list values(S_PRODUCT_LIST.nextval,?,?,?,?,?)")) {

			pstmt.setString(1, mem.getP_name());
			pstmt.setInt(2, mem.getP_seoul());
			pstmt.setInt(3, mem.getP_suwon());
			pstmt.setInt(4, mem.getP_incheon());
			pstmt.setInt(5, mem.getPrice());

			pstmt.executeUpdate();
		}
	}

	public void L_insert(Connection conn, ProductRequest mem) throws SQLException {
		try (PreparedStatement pstmt = conn
				.prepareStatement("insert into product_log values(S_PRODUCT_log.nextval,?,S_PRODUCT_list.currval,?,?,?,?,?,?,?)")) {
			pstmt.setString(1, "登録");
			pstmt.setString(2, mem.getP_name());
			pstmt.setInt(3, mem.getP_seoul());
			pstmt.setInt(4, mem.getP_suwon());
			pstmt.setInt(5, mem.getP_incheon());
			pstmt.setInt(6, mem.getPrice());
			pstmt.setDate(7, mem.getDate());
			pstmt.setString(8, mem.getWriter());
			pstmt.executeUpdate();
		}
	}

	// 상품 정보를 업데이트합니다.
	// 商品情報を更新します。
	public void update(Connection conn, Product member) throws SQLException {
		try (PreparedStatement pstmt = conn
				.prepareStatement("update product_list set p_seoul = ?,p_suwon=?, p_incheon = ? where p_no = ?")) {
			pstmt.setInt(1, member.getP_seoul());
			pstmt.setInt(2, member.getP_suwon());
			pstmt.setInt(3, member.getP_incheon());
			pstmt.setInt(3, member.getP_no());

			pstmt.executeUpdate();
		}
	}

}
