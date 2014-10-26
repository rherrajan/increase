package tk.icudi;

import java.io.IOException;
import java.io.InputStream;

public class LogProviderFile implements LogProvider {

	private String file;

	public LogProviderFile(String file) {
		this.file = file;
	}

	public InputStream provideLogs() throws IOException {
		return this.getClass().getClassLoader().getResourceAsStream(file);
	}

}
