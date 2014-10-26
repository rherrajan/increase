package tk.icudi;

import java.io.IOException;
import java.io.InputStream;

public interface LogProvider {

	public InputStream provideLogs() throws IOException;

}