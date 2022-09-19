/*Name: Image Converter;
 * Author: Elena Ochkina;
 * Date: 2/7/2022;
 * Description: This PageLoader Class implements a web page loading and return a web page as a string. 
 */
package Assignment1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class PageLoader {
	public String loadWebPage(String URL) throws Exception{
		
        URLConnection busPage = new URL(URL).openConnection();
		
		busPage.setRequestProperty("user-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(busPage.getInputStream()));

        String inputLine = "";

        String text = "";
        while ((inputLine = in.readLine()) != null) {
            text += inputLine + "\n";

        }       
        in.close();		
        return text;
	}

}
