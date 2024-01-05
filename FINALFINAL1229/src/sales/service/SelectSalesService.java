package sales.service;

import java.sql.Connection;
import java.sql.SQLException;

import connection.ConnectionProvider;
import product.dao.ProductDao;
import product.model.Product;

public class SelectSalesService {

	private ProductDao productDao = new ProductDao();
	/*품목번호로 품목테이블을 조회한다.
	 *品目番号で品目テーブルを照会する。*/
	public Product selectProduct(int p_no) {
		try(Connection conn = ConnectionProvider.getConnection()){
			Product product = productDao.selectById1(conn, p_no);
			return product;
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
			
		}
	}
}
