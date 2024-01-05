package productlog.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import connection.ConnectionProvider;
import productlog.dao.ProductlogDao;

public class ProductSearchLogService {

	// ProductDao 인스턴스 생성
	// ProductDaoのインスタンスを作成
	private ProductlogDao productlogDao = new ProductlogDao();
	// 한 페이지에 보여줄 상품 수
	// 1ページに表示する商品数
	private int size = 10;

	// 페이지 번호를 받아 상품 페이지 정보를 반환하는 메서드
	// ページ番号を受け取り、商品のページ情報を返すメソッド
	public ProductLogPage getProductPage(int pageNum, int p_no) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			// 전체 상품 수 조회
			// 全体の商品数を取得
			int total = productlogDao.searchCount(conn,p_no);
			System.out.println("total:" + total);
			// 현재 페이지에 해당하는 상품 목록 조회
			// 現在のページに対応する商品リストを取得

			List<ProductLogRequest> content = productlogDao.select2(conn,  p_no, (pageNum - 1) * size + 1, (pageNum) * size);
			// ProductPage 객체를 생성하여 반환
			// ProductPage オブジェクトを生成して返す
			System.out.println("search" + content);
			return new ProductLogPage(total, pageNum, size, content);
			
		} catch (SQLException e) {
			// SQLException이 발생하면 RuntimeException으로 변환
			// SQLExceptionが発生したら RuntimeException に変換
			throw new RuntimeException(e);

		}
	}

}
