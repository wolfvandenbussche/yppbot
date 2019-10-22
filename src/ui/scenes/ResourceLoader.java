package ui.scenes;

import java.io.InputStream;

public class ResourceLoader {
	
	public InputStream getResource(String path) {
		return getClass().getResourceAsStream(path);
	}
}
