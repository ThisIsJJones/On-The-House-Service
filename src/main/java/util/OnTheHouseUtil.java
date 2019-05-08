package util;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import backend.models.Raffle;
import backend.models.User;

public class OnTheHouseUtil {
	
	public static User getUserFromSession(HttpServletRequest request) {
		return (User) request.getSession().getAttribute("user");
	}
	
	public static boolean isUserAuthenticated(HttpServletRequest request) {
		User user = getUserFromSession(request);
		if (user == null) {
			return false;
		}
		return true;
	}

	public static boolean isRaffleRunning(Raffle raffle) {
		if(isToday(raffle) && 
				raffle.getStartTime().isBefore(LocalTime.now()) && 
				raffle.getEndTime().isAfter(LocalTime.now())) {
			
			return true;
		}
		
		return false;
	}
	
	private static boolean isToday(Raffle raffle) {
		Calendar raffleCalendar = Calendar.getInstance();
		raffleCalendar.setTime(raffle.getStartDate());
		
		Calendar rightNow = Calendar.getInstance();
		
		return (raffleCalendar.get(Calendar.YEAR) == rightNow.get(Calendar.YEAR) && 
				raffleCalendar.get(Calendar.MONTH) == rightNow.get(Calendar.MONTH)  && 
				raffleCalendar.get(Calendar.DATE) == rightNow.get(Calendar.DATE) );
	}
	
	public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		if ((lat1 == lat2) && (lon1 == lon2)) {
			return 0;
		}
		else {
			double theta = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			if (unit == "K") { //KM
				dist = dist * 1.609344;
			}
			return (dist);
		}
	}
	
}
