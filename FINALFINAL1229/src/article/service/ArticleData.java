package article.service;

import article.model.Article;
import article.model.ArticleContent;

/*공지사항 제목과 내용을 담기 위한 VO
お知らせのタイトルと内容を盛り込むためのVO*/
public class ArticleData {
	
	private Article article;
	private ArticleContent content;
	
	public ArticleData(Article article, ArticleContent content) {
		this.article=article;
		this.content=content;
		
	}

	public Article getArticle() {
		return article;
	}

	public String getContent() {
		return content.getContent();
	}

	
	

}
