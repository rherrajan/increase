package tk.icudi;

import java.io.IOException;
import java.io.InputStream;

/**
 * @deprecated Please use
 *             <code>new LogProviderWeb(new RequestDataRherrajan())</code>
 */
@Deprecated
public class LogProviderCurl implements LogProvider {

	@Override
	public InputStream provideLogs() throws IOException {

		String curlCMD = "~/development/callForLog.sh";
		String[] cmd = { "/bin/bash", "-c", curlCMD };
		Process proc = Runtime.getRuntime().exec(cmd);

		return proc.getInputStream();
	}

}
