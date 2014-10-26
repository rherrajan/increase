package tk.icudi;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class LogProviderString implements LogProvider {

	private String text;

	public LogProviderString(String text) {
		this.text = text;
	}

	public InputStream provideLogs() throws IOException {
		return new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
	}

}
