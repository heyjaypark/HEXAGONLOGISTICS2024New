package sales.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import connection.ConnectionProvider;
import sales.dao.SalesDao;
import sales.model.Sales;
import sales.model.SalesList;


public class SearchSalesService {
	
	
	private SalesDao salesDao = new SalesDao();
	/*게시글의 개수 10개*/
	private int size = 10;
	
	/*pageNum에 해당하는 게시글 목록을 구한다. salesDao.select() 메서드의
	 * 두 번째 파라미터는 조회할 레코드의 시작 행이다.
	 * 시작 행은 0번 기준으로 (pageNum-1)*size를 시작 행 번호로 사용한다.
	 * 예를 들어, 3페이지를 요청하면 (3-1)*10인 20을 시작행 번호로 사용한다.
	 * 3페이지는 21번째 레코드부터 10개의 레코드를 구하므로, 시작행 번호는 20이 된다.
	 */
	
	/* SalesPage 객체를 리턴한다*/
	public SalesPage getSalesPage(int pageNum, int code) {
		
		try(Connection conn = ConnectionProvider.getConnection()) {
			int total = salesDao.searchCount(conn, code);
			System.out.println("SearchSalesService:" + total);
			
		
			List<SalesList> content = salesDao.select2(conn, (pageNum - 1)* size+1, size * pageNum, code);		
			
			return new SalesPage(total, pageNum, size, content);		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public SalesPage getSalesPage2(int pageNum, int code) {
		System.out.println("service = " + code);
		try(Connection conn = ConnectionProvider.getConnection()) {
			int total = salesDao.searchCount2(conn, code);
			System.out.println("SearchSalesService:" + total);
			
	
			List<SalesList> content = salesDao.select3(conn, (pageNum - 1)* size+1, size * pageNum, code);		
			
			return new SalesPage(total, pageNum, size, content);		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
