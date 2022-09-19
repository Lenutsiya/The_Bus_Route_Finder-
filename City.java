/*Name: Image Converter;
 * Author: Elena Ochkina;
 * Date: 2/7/2022;
 * Description: This City Class has a city name and a map of route and url as instance variables. 
 */

package Assignment1;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class City {
	public String cityName;
	public HashMap <String, String> RouteURL = new HashMap<String, String>();
	
	//City Class constructor that accepts a string of CitySection (a certain section of a web page) as a parameter.
	public City (String citySection) {
		Pattern patternCity = Pattern.compile("<h3>(([^<])*)</h3>");// use regex to extract a city name; group 1;
        Matcher matcherCity = patternCity.matcher(citySection);
        Pattern patternRouteURL = Pattern.compile("<a[\\s]+href=\"([^\"]+)[^>]*>(([^<])+)</a>");//use regex to extract a url and route number; group 1 for url, group 2 for route;
        Matcher matcherRouteURL = patternRouteURL.matcher(citySection);
        
        
       String firstPartURL = "https://www.communitytransit.org/busservice";
              
        while(matcherCity.find()) {
        	cityName = matcherCity.group(1);//match a city name
        }
        
        while(matcherRouteURL.find()) {
        	String route = matcherRouteURL.group(2);//match a route
        	String url = matcherRouteURL.group(1);//match a url;
            url = firstPartURL + url;
            RouteURL.put(route, url);
        	}
        	
        }
       
        
	
	
}

