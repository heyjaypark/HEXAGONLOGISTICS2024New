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

//이 클래스는 상품 데이터베이스에 접근하는 DAO클래스입니다.
//このクラスは、商品データベースにアクセスするDAOクラスです。
public class ProductDao {
	
	public List<Product> select1(Connection conn, int startRow, int size) throws SQLException {
		
	// 지정된 범위 내의 상품 목록을 조회합니다.
	//指定された範囲の商品リストを取得します。
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Product> result = new ArrayList<>();
		try {

			pstmt = conn.prepareStatement("SELECT * FROM (SELECT ROWNUM AS NUM, product_list.* FROM product_list)WHERE NUM BETWEEN ? AND ? order by p_no");
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
	//상품 테이블의 전체 레코드 수를 조회합니다.
	//商品テーブルの総レコード数を取得します。
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
	//ResultSet에서 Product 객체로 변환합니다.
	//ResultSetからProductオブジェクトに変換します。
	private Product convertProduct(ResultSet rs) throws SQLException {
		return new Product(rs.getInt("p_no"),
				rs.getString("p_name"),
				rs.getInt("p_seoul"),
				rs.getInt("p_suwon"),
				rs.getInt("p_incheon"),
				rs.getInt("price")); 

	}


	
	//지정된 상품 코드에 해당하는 상품을 조회합니다.
	//指定された商品コードに対応する商品を取得します。
	public List<Product> selectById(Connection conn, int p_no) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			pstmt = conn.prepareStatement("select * from product_list where p_no = ?");
			pstmt.setInt(1, p_no);
			rs = pstmt.executeQuery();

					
			List<Product> product = new ArrayList<>();
			while (rs.next()) {
				product.add(convertProduct(rs));
			}
			return product;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	
	}
	public Product selectById1(Connection conn, int p_no) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			pstmt = conn.prepareStatement("select * from product_list where p_no = ?");
			pstmt.setInt(1, p_no);
			rs = pstmt.executeQuery();

					
			Product product = null;
			if(rs.next()) {
				product = new Product(
						rs.getInt("p_no"),
						rs.getString("p_name"),
						rs.getInt("p_seoul"),
						rs.getInt("p_suwon"),
						rs.getInt("p_incheon"),
						rs.getInt("price")
						);
							

			}
			return product;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	
	}
	
	//새로운 상품을 데이터베이스에 등록합니다.
	//新しい商品をデータベースに登録します。
	public void insert(Connection conn, Product mem) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement("insert into product_list values(?,?,?,?,?,?)")) {
			pstmt.setInt(1, mem.getP_no());
			pstmt.setString(2, mem.getP_name());
			pstmt.setInt(3, mem.getP_seoul());
			pstmt.setInt(4, mem.getP_suwon());
			pstmt.setInt(5, mem.getP_incheon());
			pstmt.setInt(6, mem.getPrice());
		}}


	

		//상품 정보를 업데이트합니다.
		//商品情報を更新します。
	public void update(Connection conn, Product member) throws SQLException {
		try (PreparedStatement pstmt = conn
				.prepareStatement("update porduct_list set p_seoul = ?,p_suwon=?, p_incheon = ? where p_no = ?")) {
			pstmt.setInt(1, member.getP_seoul());
			pstmt.setInt(2, member.getP_suwon());
			pstmt.setInt(3, member.getP_incheon());
			pstmt.setInt(3, member.getP_no());

			pstmt.executeUpdate();
		}}

	
	public List<Product> select2(Connection conn, int startRow, int size , String p_name) throws SQLException {
		
		// 지정된 범위 내의 상품 목록을 조회합니다.
		//指定された範囲の商品リストを取得します。
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<Product> result = new ArrayList<>();
			try {

				pstmt = conn.prepareStatement("SELECT * FROM (SELECT ROWNUM AS NUM, product_list.* FROM product_list where p_name like ?)WHERE NUM BETWEEN ? AND ? order by p_no");
					  pstmt.setInt(2, startRow);
					  pstmt.setInt(3, size);
					  pstmt.setString(1, "%"+p_name+"%");
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
	public int searchCount(Connection conn, String code) throws SQLException {
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			pstmt = conn.prepareStatement("select count(*) from product_list where p_name like ?");			
			pstmt.setString(1, "%"+ code +"%");
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

	

}
