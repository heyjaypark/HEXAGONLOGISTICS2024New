package productlog.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import productlog.service.ProductLogPage;
import productlog.service.ProductLogService;

//상품 목록을 처리하는 핸들러입니다.
//商品リストを処理するハンドラークラスです。
public class ProductLogHandler implements CommandHandler {

	private ProductLogService ProductLogService = new ProductLogService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO Auto-generated method stub
		String pageNoVal = req.getParameter("pageNo");
		int pageNo = 1;
		// 페이지 번호가 전달되었다면 해당 번호를 사용, 아니면 기본값 1 사용
		// ページ番号が渡された場合、その番号を使用し、そうでない場合はデフォルトで1を使用
		    if(pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		    }

			// 상품 페이지 정보를 서비스를 통해 가져옴
			// 商品ページ情報をサービスを通じて取得
			ProductLogPage ProductLogPage = ProductLogService.getProductPage(pageNo);
			req.setAttribute("productLogPage", ProductLogPage);
			System.out.println(ProductLogPage.getContent());

			// 상품 목록 페이지로 이동
			// 商品リストページに移動
			return "/WEB-INF/view/ProductLogList.jsp";
		
	}

}
