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
		data.csrftoken = getValue(parameter, "csrftoken");
		data.sacsid = getValue(parameter, "sacsid");
		data.v = getValue(parameter, "v");
		data.b = getValue(parameter, "b");
		data.c = getValue(parameter, "c");

		return data;
	}

	private static String getValue(Map<String, String[]> parameter, String key) {
		String[] strings = parameter.get(key);

		if (strings != null && strings.length == 1) {
			return strings[0];
		} else {
			return null;
		}
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

	public boolean isEmpty() {

		if (csrftoken != null && csrftoken.isEmpty() == false) {
			return true;
		}
		return false;
	}

}