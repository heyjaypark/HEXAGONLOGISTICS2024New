package article.model;

/*공지사항 작성자를 담기위한 VO
お知らせ作成者を盛り込むためのVO*/
public class Writer {
	
	private String id;
	private String name;
	
	public Writer(String id, String name) {
		this.id = id;
		this.name=name;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
