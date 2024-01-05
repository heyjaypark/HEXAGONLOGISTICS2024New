package product.service;

import java.sql.Connection;
import java.sql.SQLException;

import connection.ConnectionProvider;
import jdbc.JdbcUtil;
import member.service.DuplicateIdException;
import product.dao.ProductRegiDao;
import product.model.ProductRequest;

public class ProductRegiService {
	// ProductRegiDao의 인스턴스 생성
	// ProductRegiDaoのインスタンスを生成
	private ProductRegiDao prd = new ProductRegiDao();

	// 상품 등록 서비스
	// 商品登録サービス
	public void productregi(ProductRequest pro) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			// 이미 존재하는 상품 번호인지 확인
			// 既に存在する商品番号かどうか確認
			int product = prd.selectById(conn, pro.getP_no());
			if (product != 0) {
				// 이미 존재하는 상품 번호일 경우 롤백하고 DuplicateIdException 예외 발생
				// 既に存在する商品番号の場合はロールバックし、DuplicateIdException例外を発生させる
				JdbcUtil.rollback(conn);
				throw new DuplicateIdException();
			}

			prd.insert(conn, pro);
			prd.L_insert(conn,pro);
			
			conn.commit();
		} catch (SQLException e) {
			// SQLException이 발생한 경우 롤백하고 RuntimeException 예외 발생
			// SQLExceptionが発生した場合はロールバックし、RuntimeException例外を発生させる
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);

		} finally {
			JdbcUtil.close(conn);
		}
	}

}
