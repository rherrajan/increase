package tk.icudi;

import java.util.Map;

public class RequestData {

	public String csrftoken;
	public String sacsid;
	public String v;
	public String b;
	public String c;

	public String toGetParameter() {
		StringBuilder builder = new StringBuilder();
		builder.append("csrftoken=").append(csrftoken);
		builder.append("&sacsid=").append(sacsid);
		builder.append("&v=").append(v);
		builder.append("&b=").append(b);
		builder.append("&c=").append(c);
		return builder.toString();
	}

	public static RequestData fromParmeter(Map<String, String[]> parameter) {
		RequestData data = new RequestData();
		data.csrftoken = parameter.get("csrftoken")[0];
		data.sacsid = parameter.get("sacsid")[0];
		data.v = parameter.get("v")[0];
		data.b = parameter.get("b")[0];
		data.c = parameter.get("c")[0];
		return data;
	}

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

	@Override
	public String toString() {
		return "RequestData [csrftoken=" + csrftoken + ", sacsid=" + sacsid + ", v=" + v + ", b=" + b + ", c=" + c + "]";
	}

}