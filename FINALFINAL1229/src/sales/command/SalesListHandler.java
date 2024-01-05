package sales.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sales.service.SalesPage;
import sales.service.ListSalesService;
import mvc.command.CommandHandler;

public class SalesListHandler implements CommandHandler {

	private ListSalesService listService = new ListSalesService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {

		String pageNoVal = req.getParameter("pageNo");
		/*
		 * pageNo 파라미터 값을 이용해서 읽어올 페이지 번호를 구한다.
		 * pageNoパラメータ値を用いて読み込むページ番号を求める
		 */

		int pageNo = 1;
		/* 디폴트값으로 1페이지를 보여준다. */
		/* デフォルトで1ページを表示する。 */
		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}

		/*
		 * ListSalesHandler를 이용해서 지정한 페이지 번호에 해당하는 게시글 데이터를 구한다.
		 * ListSalesHandlerを利用して指定したページ番号に該当する書き込みデータを求める。
		 */

		SalesPage salesPage = listService.getSalesPage(pageNo);
		/*
		 * SalesPage 객체를 JSP에서 사용할 수 있도록 request의 salesPage 속성에 저장한다.
		 * SalesPageオブジェクトをJSPで使用できるようにrequestのsalesPageプロパティに保存する。
		 */
		req.setAttribute("salesPage", salesPage);
		return "/WEB-INF/view/ListSales_overview.jsp";

	}

}
