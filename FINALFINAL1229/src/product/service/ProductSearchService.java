package product.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import connection.ConnectionProvider;
import product.dao.ProductDao;
import product.model.Product;

public class ProductSearchService {

	private ProductDao productDao = new ProductDao();
	// 지정된 상품 번호에 해당하는 상품을 검색하는 메서드
	// 指定された商品番号に対応する商品を検索するメソッド

	public Product SearchProduct2(int p_no) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			// 전체 상품 수 조회
			// 全体の商品数を取得
			
			// 현재 페이지에 해당하는 상품 목록 조회
			// 現在のページに対応する商品リストを取得

			Product product = productDao.selectById1(conn, p_no);
			// ProductPage 객체를 생성하여 반환
			// ProductPage オブジェクトを生成して返す
			return product;

		} catch (SQLException e) {
			// SQLException이 발생하면 RuntimeException으로 변환
			// SQLExceptionが発生したら RuntimeException に変換
			throw new RuntimeException(e);

		}
	}
	public ProductPage SearchProduct(int pageNum, int p_no) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			// 전체 상품 수 조회
			// 全体の商品数を取得
			
			System.out.println("productListService:" + pageNum);
			// 현재 페이지에 해당하는 상품 목록 조회
			// 現在のページに対応する商品リストを取得

			List<Product> content = productDao.selectById(conn, p_no);
			// ProductPage 객체를 생성하여 반환
			// ProductPage オブジェクトを生成して返す
			return new ProductPage(1, pageNum, size, content);

		} catch (SQLException e) {
			// SQLException이 발생하면 RuntimeException으로 변환
			// SQLExceptionが発生したら RuntimeException に変換
			throw new RuntimeException(e);

		}
	}
	int size=10;
	public ProductPage SearchProduct1(int pageNum, String p_no) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			// 전체 상품 수 조회
			// 全体の商品数を取得
			int total = productDao.searchCount(conn,p_no);
			System.out.println("productListService:" + pageNum);
			// 현재 페이지에 해당하는 상품 목록 조회
			// 現在のページに対応する商品リストを取得

			List<Product> content = productDao.select2(conn, (pageNum - 1) * size + 1, (pageNum) * size, p_no);
			// ProductPage 객체를 생성하여 반환
			// ProductPage オブジェクトを生成して返す
			return new ProductPage(total, pageNum, size, content);

		} catch (SQLException e) {
			// SQLException이 발생하면 RuntimeException으로 변환
			// SQLExceptionが発生したら RuntimeException に変換
			throw new RuntimeException(e);

		}
	}
	
	public ProductPage SearchProduct2(int pageNum, String p_no) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			// 전체 상품 수 조회
			// 全体の商品数を取得
			int total = productDao.selectCount(conn);
			System.out.println("productListService:" + pageNum);
			// 현재 페이지에 해당하는 상품 목록 조회
			// 現在のページに対応する商品リストを取得

			List<Product> content = productDao.select2(conn, 1, total, p_no);
			// ProductPage 객체를 생성하여 반환
			// ProductPage オブジェクトを生成して返す
			return new ProductPage(total, pageNum, size, content);

		} catch (SQLException e) {
			// SQLException이 발생하면 RuntimeException으로 변환
			// SQLExceptionが発生したら RuntimeException に変換
			throw new RuntimeException(e);

		}
	}

}
