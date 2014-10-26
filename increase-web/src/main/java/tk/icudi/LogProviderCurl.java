package tk.icudi;

import java.io.IOException;
import java.io.InputStream;

public class LogProviderCurl implements LogProvider {

	public InputStream provideLogs() throws IOException {

		String curlCMD = "cd ; development/callForLog.sh";
		String[] cmd = { "/bin/bash", "-c", curlCMD };
		Process proc = Runtime.getRuntime().exec(cmd);

		return proc.getInputStream();
	}

}
