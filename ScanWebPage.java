/*Name: Image Converter;
 * Author: Elena Ochkina;
 * Date: 2/7/2022;
 * Description: This ScanWebPage Class implements two methods for getting a certain section of a web page that will be used to extract a value of city,
 * bus route, destination, url for each bus route, and schedule. 
 */



package Assignment1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.*;

public class ScanWebPage  {
	

	/* The function returns an array list of strings that contain a certain section of a web page. This section of a web page contains the name of a city 
	 * and all bus routes that run from this city.   
	 * @param String URL such as "https://www.communitytransit.org/busservice/schedules/";
	 * @return an array list of strings where each string is a section of a web page. 
	 */
	public ArrayList<String> getCitySection(String URL) throws Exception{
		PageLoader load = new PageLoader();
		String text = load.loadWebPage(URL);
		ArrayList <String> citySectionList =new ArrayList<String>();
				
           
        Pattern pattern = Pattern.compile("<h3>([^<])*</h3>");//regex to indicate the start of a section is needed;
        Pattern endOfLastCitySection = Pattern.compile("<div id=\"RoutesByRoute\"");//regex to indicate the end of a section is needed;
        Matcher matcher = pattern.matcher(text);
        Matcher matcher1 = endOfLastCitySection.matcher(text);
        
        int prevIndexCitySection = 0;        		
        
        while(matcher.find()) {
        	int currentIndexCitySection = matcher.start();//get a start index of the match result;
        	if(prevIndexCitySection !=0) {
        		citySectionList.add(text.substring(prevIndexCitySection, currentIndexCitySection));//get a resulted section as a substring of the start index of match of each section and the end index of match of each section;
        	}
        	prevIndexCitySection = currentIndexCitySection;
        }
        
        while(matcher1.find()) {
        	int currentIndexCitySection = matcher1.start();
        	citySectionList.add(text.substring(prevIndexCitySection, currentIndexCitySection));        	
        }        
      
        return citySectionList;
		
	}
	/* The function returns an array list of strings that contain a certain section of a web page. This section of a web page contains the bus route destination and schedule 
	 * @param String URL such as https://www.communitytransit.org/busservice/schedules/route/111;
	 * @return an array list of strings where each string is a section of a web page. 
	 */
	public ArrayList<String> getScheduleSection(String URL) throws Exception{
		PageLoader load = new PageLoader();
		String text = load.loadWebPage(URL);
		ArrayList<String> scheduleSection = new ArrayList<String>();
		
		Pattern pattern = Pattern.compile("<h2>([a-zA-Z]*)<small>");//regex to indicate the start and end section of a section which is needed;
		Pattern pattern1 = Pattern.compile("</table>");
		Matcher matcher = pattern.matcher(text);
		Matcher matcher1 = pattern1.matcher(text);
		
		
		//find a match that occurs between "Weekday" header and "Saturday" header;
		while(matcher.find() && matcher1.find()) {
			int startIndexTime = matcher.start();
			int stopIndexTime = matcher1.start();
			String scheduleType = matcher.group(1); // Still Weekday or Saturday
			if (scheduleType.equals("Weekday")) {
				scheduleSection.add(text.substring(startIndexTime, stopIndexTime));
			}
			else {
				break;
			}
		}
		
		return scheduleSection;
		
	}
	
	

	
        
	

}
