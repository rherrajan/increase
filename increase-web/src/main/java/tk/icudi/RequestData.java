package tk.icudi;

public class RequestData {

	public String csrftoken;
	public String sacsid;
	public String v;
	public String b;
	public String c;

	public String getCsrftoken() {
		return csrftoken;
	}

	public void setCsrftoken(String csrftoken) {
		this.csrftoken = csrftoken;
	}

	public String getSacsid() {
		return sacsid;
	}

	public void setSacsid(String sacsid) {
		this.sacsid = sacsid;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

}