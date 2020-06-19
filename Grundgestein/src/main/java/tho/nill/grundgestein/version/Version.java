package tho.nill.grundgestein.version;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class Version {
	private static final Logger logger = Logger.getLogger(Version.class.getSimpleName());


	private Properties prop;

	public Version() {
		super();
	}

	public void printVersion() {

		InputStream resourceAsStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("version.properties");
		if (resourceAsStream == null) {
			resourceAsStream = getClass().getResourceAsStream("version.properties");
		}
		if (resourceAsStream != null) {
			this.prop = new Properties();

			try {
				this.prop.load(resourceAsStream);
			} catch (IOException e) {
				logger.severe("Kann version.properties nicht laden");
			}
			System.out.println("Author information: Thomas Nill");
			System.out.println("Version information:\n");
			printProperties("groupId", "artifactId", "version");
		}
	}

	private void printProperties(String... keys) {
		for (String key : keys) {
			System.out.println(key + ": " + prop.getProperty(key));
		}
	}

	public static void main(String[] args) {
		new Version().printVersion();
	}

}
