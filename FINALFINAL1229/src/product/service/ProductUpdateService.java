package product.service;

import java.sql.Connection;
import java.sql.SQLException;

import connection.ConnectionProvider;
import jdbc.JdbcUtil;
import product.dao.ProductUpdateDao;
import product.model.Product;
import product.model.ProductRequest;

public class ProductUpdateService {

	private ProductUpdateDao prd = new ProductUpdateDao();
	// 상품 정보를 업데이트하는 메서드
	// 商品情報をアップデートするメソッド

	public void productUpdate(ProductRequest pro) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			// 업데이트할 상품 정보 조회
			// アップデートする商品情報を取得
			Product product = prd.selectById(conn, pro.getP_no());
			// 상품 정보 업데이트
			// 商品情報をアップデート
			prd.update(conn, pro);
			prd.L_update(conn,pro);
			conn.commit();
		} catch (SQLException e) {
			// 데이터베이스 롤백
			// データベースロールバック
			JdbcUtil.rollback(conn);
			// RuntimeException 데이터베이스 연결 등 예외가 발생한 경우, 실행 중에 발생하는 예외로 처리
			// RuntimeException データベース接続などの例外が発生した場合、実行中に発生する例外として処理
			throw new RuntimeException(e);
			// TODO: handle exception
		} finally {
			JdbcUtil.close(conn);
		}
	}

}
