package product.model;

import java.sql.Date;
import java.util.Map;

public class ProductRequest {
	
	private int p_no;
	private String p_name;
	private int p_seoul;
	private int p_suwon;
	private int p_incheon;
	private int price;
	private String Writer;
	private Date date;
	
	

	

	


	


	public String getWriter() {
		return Writer;
	}


	public Date getDate() {
		return date;
	}


	public void setWriter(String writer) {
		Writer = writer;
	}


	public void setDate(Date date) {
		this.date = date;
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
	public void validate(Map<String, Boolean> errors) {
		checkEmpty(errors, p_no, "p_no");
		checkEmpty(errors, date, "date");
	}
	  private void checkEmpty(Map<String, Boolean> errors, Object value, String fieldName) {
	        if (value == null) {
	            errors.put(fieldName, Boolean.TRUE);
	        } else if (value instanceof String && ((String) value).isEmpty()) {
	            errors.put(fieldName, Boolean.TRUE);
	        } else if (value instanceof Integer && (Integer) value == 0) {
	            errors.put(fieldName, Boolean.TRUE);
	        }
	    }


}
