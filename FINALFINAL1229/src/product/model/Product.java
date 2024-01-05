package product.model;



public class Product {
	
	private int p_no;
	private String p_name;
	private int p_seoul;
	private int p_suwon;
	private int p_incheon;
	private int price;
	
	

	public Product(int p_no, String p_name, int p_seoul, int p_suwon, int p_incheon, int price) {
		this.p_no = p_no;
		this.p_name = p_name;
		this.p_seoul = p_seoul;
		this.p_suwon = p_suwon;
		this.p_incheon = p_incheon;
		this.price = price;

	}


	public int getP_no() {
		return p_no;
	}


	public void setP_no(int p_no) {
		this.p_no = p_no;
	}


	public String getP_name() {
		return p_name;
	}


	public void setP_name(String p_name) {
		this.p_name = p_name;
	}


	public int getP_seoul() {
		return p_seoul;
	}


	public void setP_seoul(int p_seoul) {
		this.p_seoul = p_seoul;
	}


	public int getP_suwon() {
		return p_suwon;
	}


	public void setP_suwon(int p_suwon) {
		this.p_suwon = p_suwon;
	}


	public int getP_incheon() {
		return p_incheon;
	}


	public void setP_incheon(int p_incheon) {
		this.p_incheon = p_incheon;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}




}
