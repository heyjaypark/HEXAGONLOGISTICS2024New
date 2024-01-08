package sales.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import connection.ConnectionProvider;
import sales.dao.SalesDao;
import sales.model.Sales;
import sales.model.SalesList;


public class ListSalesService {
	
	
	private SalesDao salesDao = new SalesDao();
	private int size = 10;
	
	/*	pageNum에 해당하는 판매목록을 가져온 후 페이지 객체를 반환한다.
	 * 	크기는 10개 단위로, 페이지번호에 따른 위치의 행을 조회한다.
	 * 
	 * 	pageNumに該当する販売リストを取得した後、ページオブジェクトを返す。
	 * 	大きさは10個単位で、ページ番号に応じた位置の行を照会する。 */
	public SalesPage getSalesPage(int pageNum) {
		try(Connection conn = ConnectionProvider.getConnection()) {
			int total = salesDao.selectCount(conn);
			System.out.println("salesListService:" + pageNum);
			
			List<SalesList> content = salesDao.select1(conn, total-(pageNum)*(size), total-(pageNum-1)*(size)-1);
			return new SalesPage(total, pageNum, size, content);		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
