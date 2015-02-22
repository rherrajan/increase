package tk.icudi.objects;

import java.io.IOException;
import java.io.InputStream;

import tk.icudi.LogProvider;

public class LogProviderFile implements LogProvider {

	private String file;

	public LogProviderFile(String file) {
		this.file = file;
	}

	public InputStream provideLogs() throws IOException {
		return this.getClass().getClassLoader().getResourceAsStream(file);
	}

}
