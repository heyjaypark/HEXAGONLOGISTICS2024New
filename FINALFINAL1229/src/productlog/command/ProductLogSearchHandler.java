package productlog.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.command.CommandHandler;
import productlog.service.ProductLogPage;
import productlog.service.ProductSearchLogService;

//상품 목록을 처리하는 핸들러입니다.
//商品リストを処理するハンドラークラスです。
public class ProductLogSearchHandler implements CommandHandler {

	private ProductSearchLogService ProductSearchLogService = new ProductSearchLogService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO Auto-generated method stub
		String pageNoVal = req.getParameter("pageNo");
		String p_noVal = req.getParameter("p_no");
		int p_no = 0;
		System.out.println("p_noVal" + p_noVal);
		int pageNo = 1;
		// 페이지 번호가 전달되었다면 해당 번호를 사용, 아니면 기본값 1 사용
		// ページ番号が渡された場合、その番号を使用し、そうでない場合はデフォルトで1を使用
		    if(pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		    }

			// 상품 페이지 정보를 서비스를 통해 가져옴
			// 商品ページ情報をサービスを通じて取得

			Map<String, Boolean> errors = new HashMap<>();
			req.setAttribute("errors", errors);
			
			
			try {
				p_no = Integer.parseInt(p_noVal);
				System.out.println("p_no");
				ProductLogPage ProductLogPage = ProductSearchLogService.getProductPage(pageNo,p_no);
				req.setAttribute("productLogPage", ProductLogPage);
				// 상품 목록 페이지로 이동
				// 商品リストページに移動
				return "/WEB-INF/view/ProductLogSearchList.jsp";
				
				
				
			}catch (NumberFormatException e) {
				// 숫자 변환 예외가 발생하면 에러를 설정하고 뷰로 이동합니다.
				// 数字変換例外が発生した場合、エラーを設定してビューに移動します。
				errors.put("NumberFormatException", Boolean.TRUE);
				return "/WEB-INF/view/ProductLogSearchList.jsp";
			}


		
	}

}
