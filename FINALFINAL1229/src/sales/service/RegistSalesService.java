package sales.service;

import java.sql.Connection;
import java.sql.SQLException;

import connection.ConnectionProvider;
import jdbc.JdbcUtil;
import sales.dao.SalesDao;
import sales.model.RegistRequest;

public class RegistSalesService {

	private SalesDao salesDao = new SalesDao();

	public void registSales(RegistRequest salesReq) throws Exception {
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			/*
			 * 판매등록과 그에 따른 보유재고량의 변경을 처리한다.
			 *  販売の登録とそれに伴う保有在庫量の変更を処理する。
			 */
			salesDao.insert(conn, salesReq);
			salesDao.update(conn, salesReq);
			conn.commit();

			/*
			 * 예외 발생시 롤백
			 *  例外が発生時ロールバック。
			 */
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}

	}

}
