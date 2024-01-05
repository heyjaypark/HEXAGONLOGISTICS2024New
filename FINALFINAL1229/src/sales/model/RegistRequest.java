package sales.model;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class RegistRequest {
	
	private int p_no;
	private int s_seoul;
	private int s_suwon;
	private int s_incheon;
	private Date s_date;
	private String s_registrant;
	
	public String getS_registant() {
		return s_registrant;
	}
	public void setS_registant(String s_registant) {
		this.s_registrant = s_registant;
	}
	public int getP_no() {
		return p_no;
	}
	public void setP_no(int p_no) {
		this.p_no = p_no;
	}

	public int getS_seoul() {
		return s_seoul;
	}
	public void setS_seoul(int s_seoul) {
		this.s_seoul = s_seoul;
	}
	public int getS_suwon() {
		return s_suwon;
	}
	public void setS_suwon(int s_suwon) {
		this.s_suwon = s_suwon;
	}
	public int getS_incheon() {
		return s_incheon;
	}
	public void setS_incheon(int s_incheon) {
		this.s_incheon = s_incheon;
	}
	public Date getS_date() {
		return s_date;
	}
	public void setS_date(Date s_date) {
		this.s_date = s_date;
	}
	
	public void validate(Map<String, Boolean> errors) {
		checkEmpty(errors, p_no, "p_no");
		checkEmpty(errors, s_date, "s_date");
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
