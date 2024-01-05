package productlog.service;

import java.util.List;

import product.model.Product;

public class ProductLogPage {
	
	private int total;
	private int currentPage;
	private List<ProductLogRequest> content;
	private int totalPages;
	private int startPage;
	private int endPage;
	
	
	public ProductLogPage(int total, int currentPage, int size, List<ProductLogRequest> content) {
		this.total=total;
		this.currentPage=currentPage;
		this.content=content;
	
		
		if(total==0) {
			totalPages=0;
			startPage=0;
			endPage=0;
			/* 마지막페이지를 추가하는 로직
			 * 最後のページを追加するロジック*/
		}else {
			totalPages=total/size;
			if(total%size>0) {
				totalPages++;
			}
			int modVal=currentPage %5;
			startPage = currentPage / 5* 5 + 1;
			if(modVal ==0) startPage-=5;
			/* 페이지번호를 1부터 5개씩 출력하고 마지막페이지가 총페이지보다 크지 않도록한다
			 * ページ番号を5つずつ出力し、最後のページが総ページより大きくないようにする */
			
			endPage = startPage+4;
			if(endPage>totalPages)endPage = totalPages;
			
		}
	}

	public int getTotal() {
		return total;
	}

	public int getCurrentPage() {
		return currentPage;
	}


	public int getTotalPages() {
		return totalPages;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List<ProductLogRequest> getContent() {
		return content;
	}

	public void setContent(List<ProductLogRequest> content) {
		this.content = content;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
	public boolean hasNoArticles() {
		return total==0;
	}
	public boolean hasArticles() {
		return total>0;
		
	}
	

}
