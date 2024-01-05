package sales.model;

import java.sql.Date;

/*판매입력 테이블에 데이터를 입력할 VO생성
 * 販売入力テーブルにデータを入力するVO生成*/
public class Sales {
	
	private int s_num;
	private int p_no;
	private int s_seoul;
	private int s_suwon;
	private int s_incheon;
	private Date s_date;
	private String s_registrant;
	
	public Sales(int s_num, int p_no, int s_seoul, int s_suwon, int s_incheon, Date s_date, String s_registrant) {
		this.s_num = s_num;
		this.p_no = p_no;
		this.s_seoul = s_seoul;
		this.s_suwon = s_suwon;
		this.s_incheon = s_incheon;
		this.s_date = s_date;
		this.s_registrant = s_registrant;
	}

	public int getS_num() {
		return s_num;
	}

	public int getP_no() {
		return p_no;
	}

	public int getS_seoul() {
		return s_seoul;
	}

	public int getS_suwon() {
		return s_suwon;
	}

	public int getS_incheon() {
		return s_incheon;
	}

	public Date getS_date() {
		return s_date;
	}

	public String getS_registrant() {
		return s_registrant;
	}
	

}
