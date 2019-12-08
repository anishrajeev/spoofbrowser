import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class test {
    static String proxyType;
    static List<String> proxiesList = new ArrayList<String>();
    public static void main(String[] args) throws IOException {
        BufferedReader bfproxies = new BufferedReader(new FileReader("proxies.txt"));
        BufferedReader bftasks = new BufferedReader(new FileReader("tasks.txt"));
        StringTokenizer stk = new StringTokenizer(bftasks.readLine());
        int proxyamount = 0;
        String proxy;
        while((proxy = bfproxies.readLine()) != null){
            proxiesList.add(proxy);
            proxyamount++;
        }
        int taskAmount = Integer.parseInt(stk.nextToken());
        if(proxyamount < taskAmount && !(proxyType.equals("none"))) {
            System.out.println("Please put enough proxies in your proxies.txt file(Re-run the program after you do so)");
            System.exit(0);
        }
        String URL = bftasks.readLine();
        System.out.println(URL);
        for(int i = 0; i < taskAmount; i++){
            createWindow(URL, i);
        }
    }
    public static void createWindow(String URL, int i){
        if(proxyType.equals("none")){
            WebDriver webDriver = new ChromeDriver();
            Dimension windowSize = new Dimension(45, 45);
            webDriver.manage().window().setSize(windowSize);
            webDriver.get(URL);
        }
        if(!(proxyType.equals("none"))){
            Proxy proxy = new Proxy();
            proxy.setHttpProxy(proxiesList.get(i));
            proxy.setSslProxy(proxiesList.get(i));

            DesiredCapabilities capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability("proxy", proxy);

            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized");
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);

            WebDriver webDriver = new ChromeDriver(capabilities);
            Dimension windowSize = new Dimension(45, 45);
            webDriver.manage().window().setSize(windowSize);
            URL = URL;
            webDriver.get(URL);
        }
    }
}

