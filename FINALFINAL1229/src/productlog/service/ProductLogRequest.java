package productlog.service;

import java.sql.Date;

public class ProductLogRequest {
	
	private int p_no;
	private String l_name;
	private int l_seoul;
	private int l_suwon;
	private int l_incheon;
	private int l_price;
	private String l_writer;
	private Date l_date;
	private String l_class;
	private int l_no;
	
	public ProductLogRequest(int l_no, String l_class, int p_no, String l_name, int l_seoul, int l_suwon, int l_incheon, int l_price,
			 Date l_date,String l_writer) {
		this.l_no=l_no;
		this.l_class=l_class;
		this.p_no = p_no;
		this.l_name = l_name;
		this.l_seoul = l_seoul;
		this.l_suwon = l_suwon;
		this.l_incheon = l_incheon;
		this.l_price = l_price;
		this.l_date=l_date;
		this.l_writer= l_writer;
	}

	public int getP_no() {
		return p_no;
	}

	public String getL_name() {
		return l_name;
	}

	public int getL_seoul() {
		return l_seoul;
	}

	public int getL_suwon() {
		return l_suwon;
	}

	public int getL_incheon() {
		return l_incheon;
	}

	public int getL_price() {
		return l_price;
	}

	public String getL_writer() {
		return l_writer;
	}

	public Date getL_date() {
		return l_date;
	}

	public String getL_class() {
		return l_class;
	}

	public int getL_no() {
		return l_no;
	}

	public void setP_no(int p_no) {
		this.p_no = p_no;
	}

	public void setL_name(String l_name) {
		this.l_name = l_name;
	}

	public void setL_seoul(int l_seoul) {
		this.l_seoul = l_seoul;
	}

	public void setL_suwon(int l_suwon) {
		this.l_suwon = l_suwon;
	}

	public void setL_incheon(int l_incheon) {
		this.l_incheon = l_incheon;
	}

	public void setL_price(int l_price) {
		this.l_price = l_price;
	}

	public void setL_writer(String l_writer) {
		this.l_writer = l_writer;
	}

	public void setL_date(Date l_date) {
		this.l_date = l_date;
	}

	public void setL_class(String l_class) {
		this.l_class = l_class;
	}

	public void setL_no(int l_no) {
		this.l_no = l_no;
	}

	




}
