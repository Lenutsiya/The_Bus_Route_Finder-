/*Name: Image Converter;
 * Author: Elena Ochkina;
 * Date: 2/7/2022;
 * Description: This RouteInfo Class has a destination and a ArrayList of durations as instance variables. This class also contains several
 * helper methods to calculate a duration for each route and destination.
 */

package Assignment1;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RouteInfo {
	public String destination;
	public ArrayList<Long> duration = new ArrayList<Long>();
		
	//The RouteInfo constructor accepts a string schedule Section as a parameter. String schedualeSection contains a timatable block of a web page.
	public RouteInfo(String scheduleSection) throws Exception {
		Pattern patternDestination = Pattern.compile("<h2>([a-zA-Z]*)<small>((.*?)*)</small>");//group 2 to get destination;    
		Matcher matcherDestination = patternDestination.matcher(scheduleSection);
		
		while (matcherDestination.find()) {
			destination = matcherDestination.group(2);//match a destination;
		}
		
		ArrayList<String> timeSection = new ArrayList<String>();//iteration starts from index 1;
		
		timeSection = splitIntoTimeSections(scheduleSection);
		
		
		for(int i = 1; i < timeSection.size(); i++) {
			Long singleDuration = calculateTime(timeSection.get(i));
			duration.add(singleDuration);
		}
		
			
        		
	}
	//This helper method matches a block of a webpage (just a row in the timetable) that contains start time and finish time 
	//for each route/destination This method returns an ArrayList of strings. Each strings have a row from the timetable;
	public ArrayList<String> splitIntoTimeSections(String scheduleSection) throws Exception{
		
		ArrayList<String> timeSectionList = new ArrayList<String>();
		
		Pattern patternStart = Pattern.compile("<tr>");
		Matcher matcherStart = patternStart.matcher(scheduleSection);
		
		Pattern patternStop = Pattern.compile("</tr>");
		Matcher matcherStop = patternStop.matcher(scheduleSection);	
		matcherStop.find();
		
		while(matcherStart.find() && matcherStop.find()) {
			int startIndexTime = matcherStart.start();
			int stopIndexTime = matcherStop.start();
			
			timeSectionList.add(scheduleSection.substring(startIndexTime, stopIndexTime)); 		
			
		}
		
		return timeSectionList;
		
	}
	//This helper method calculateTime calculates duration for each trip. This method first match hours, minutes, and part of a day (AM/PM)
	//then converts hours to minutes using another helper method, and finally, calculate a duration for each trip;
	public Long calculateTime (String timeSection) {
		Pattern patternTime = Pattern.compile("(\\d{1,2}):(\\d\\d)\\s*([AMP]{2})");//
		Matcher matcherTime = patternTime.matcher(timeSection);
		
		String startHour = "";
		String startMinute = "";
		String startAMPM = "";
		
		String stopHour = "";
		String stopMinute = "";
		String stopAMPM = "";
		//do matching for hours, minutes, and part of a day;
		while (matcherTime.find()) {
			if (startHour.equals("") && startMinute.equals("") && startAMPM.equals("")) {
				startHour = matcherTime.group(1);
				startMinute = matcherTime.group(2);
				startAMPM = matcherTime.group(3);
			}
			
			stopHour = matcherTime.group(1);
			stopMinute = matcherTime.group(2);
			stopAMPM = matcherTime.group(3);
		}
		//conversion to minutes;
		int startTime = convertToMinutes(startHour, startMinute, startAMPM);
		int stopTime = convertToMinutes(stopHour, stopMinute, stopAMPM);
		
		
		int tripDuration = stopTime - startTime;//formula for calculating a duration
		
		if (tripDuration < 0) {//a trip ends next day (ex., 12:15 am)
			tripDuration+=1440;
		}
		
		return Long.valueOf(tripDuration);
		
	}
	//This method converts Strings to integers and then to minutes. This methods returns minutes starting from 12:00 AM.
	public int convertToMinutes (String hours, String minutes, String AMPM) {
		
		int hour = Integer.parseInt(hours);//conversion from string to integers;
		int minute = Integer.parseInt(minutes);
		//for simplicity of calculation, if hour is equal to 12 make it equal to 0;
		if(hour == 12) {
			hour = 0;
		}
		
		if (AMPM.equals("AM")) {
			return hour*60 + minute;
		}
		else {
			return hour*60 + 12*60 + minute;
		}			
		
	}
	
}



