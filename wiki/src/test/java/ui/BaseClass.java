package ui;

import java.net.URL;
import java.time.Duration;
import javax.net.ssl.HttpsURLConnection;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	public static WebDriver driver;
	public static String wikipediaPage = "https://en.wikipedia.org";
	public static Boolean wikipedia = false;
	public static int wikipediaStatus = 200;

	public void initialize(String browser) {
		if (browser.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			driver = new ChromeDriver(options);

		} else if (browser.equals("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	}

	public static void checkLink(String linkUrl) {
		try {

			wikipedia = false;
			URL url = new URL(linkUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);

			conn.connect();

			if (linkUrl.startsWith(wikipediaPage)) {
				System.out.println(linkUrl + " starts with Wiki, expected status code is 200");
				wikipedia = true;
			} else {
				System.err.println(linkUrl + " It is not Wiki link please provide Wiki link");
			}

			if (conn.getResponseCode() == 200) {

				if ((wikipedia == true)
						&& (linkUrl.startsWith(wikipediaPage) && (conn.getResponseCode() == wikipediaStatus))) {
					System.out.println(linkUrl + " ---> is a Wiki link");
				}

				else {
					System.err.println(linkUrl + " ---> is a not a Wiki link");
				}
			}

			conn.disconnect();
		} catch (Exception e) {
		}
	}
}
