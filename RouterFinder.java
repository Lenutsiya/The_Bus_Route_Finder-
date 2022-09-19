/*Name: Image Converter;
 * Author: Elena Ochkina;
 * Date: 2/7/2022;
 * Description: This RouterFinder Class implements IRouterFinder interface.
 */


package Assignment1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class RouterFinder implements IRouteFinder {
	public String TRANSIT_WEB_URL = "https://www.communitytransit.org/busservice/schedules/";
	
	public Map<String, Map<String, String>> getBusRoutesUrls(final char destInitial) {
		Map<String, String> destinationBusesMap = new HashMap<String, String>();
		Map<String, Map<String, String>> getDestinationMap = new HashMap<String, Map<String, String>>();
		ScanWebPage load = new ScanWebPage();//load a web page;
		
		//throw a RunTime exception if the input is incorrect;
		if (Character.isLowerCase(destInitial) || !Character.isLetter(destInitial)) {
			throw new IllegalArgumentException("the input is incorrect");
		}
		
		//create an ArrayList of string where each string contains a part of web page that has information such as city name and bus route for this city;
		ArrayList<String> citySectionList = new ArrayList<String>();
		try {
			citySectionList = load.getCitySection(TRANSIT_WEB_URL);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		

		//iterate citySectionList to create an object of City Class which has city name and HashMap (key - route; url - value). Finally, create a hashmap that 
		//has city as a key and HashMap (key - route; url - value) as a value;
		for (int i = 0; i < citySectionList.size(); i++) {
			City city = new City(citySectionList.get(i));
			if (destInitial == city.cityName.charAt(0)) {
				destinationBusesMap = city.RouteURL;
				getDestinationMap.put(city.cityName, destinationBusesMap);
				}
							
			}				

		return getDestinationMap;			
		
	}
	
	public Map<String, List<Long>> getBusRouteTripsLengthsInMinutesToAndFromDestination(final Map<String, String> destinationBusesMap){
		Map<String, List<Long>> destinationBusRouteTripDurationMap = new HashMap<String, List<Long>>();
		
		ScanWebPage load = new ScanWebPage();
					
		Iterator<Entry<String, String>> itr = destinationBusesMap.entrySet().iterator();
		String url = "";
		String routeNumber = "";
		//iterate destinationBussesMap to get a url and a route number;
		while(itr.hasNext()) {
			Map.Entry<String, String> entry = itr.next();
			
			routeNumber = entry.getKey();
			url = entry.getValue();
			
			if (url.contains("Line")) {//skip routes such as Swift Blue Line or Swift Green Line as these routes do not have buses this time of a year;
				continue;
			}
			
			ArrayList<String> scheduleSection = new ArrayList<String>();
			try {
				scheduleSection = load.getScheduleSection(url);
			} catch (Exception e) {
				e.printStackTrace();
			}//get schedule section to access the destination for each bus route and time duration for each destination;
			
			for (int i = 0; i < scheduleSection.size(); i++) {
				try {
					RouteInfo route = new RouteInfo(scheduleSection.get(i));//create an object of RouteInfo class that contains destination and ArrayLisyt of a trip duration for each destination;
					String destination = route.destination;
					ArrayList<Long> duration = new ArrayList<Long>();
					duration = route.duration;
					destination = routeNumber +"-"+destination;//concatenate string to get a value such as "111-To Brier"
					destinationBusRouteTripDurationMap.put(destination, duration);//create hashmap of destination and a trip duration as key and value respectively;
				} catch (Exception e) {
					e.printStackTrace();
				}					
				
			}			
			
		}
		
		return destinationBusRouteTripDurationMap;
		
	}	
	
	
}	
	
