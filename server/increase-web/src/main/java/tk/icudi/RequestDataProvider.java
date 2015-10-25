package tk.icudi;

public abstract class RequestDataProvider {

	protected RequestData data;

	public RequestDataProvider(RequestData data) {
		setData(data);
	}

	public void setData(RequestData data) {
		this.data = data;
		assertInputComplete();
	}

	private void assertInputComplete() {

		if (data == null) {
			throw new IllegalArgumentException("All Input-Parameters are missing");
		}

		if (data.csrftoken == null) {
			throw new IllegalArgumentException("Input-Parameter is missing: csrftoken");
		}

		if (data.sacsid == null) {
			throw new IllegalArgumentException("Input-Parameter is missing: sacsid");
		}

		if (data.v == null) {
			throw new IllegalArgumentException("Input-Parameter is missing: v");
		}

	}

}
