package pl.zpi.server.utils;

/**
 * Pomocnicza klasa reprezentująca czas zegarowy z dokładnością do minuty
 * @author mm
 *
 */
public class Time {
	private int hour;
	private int minute;
	/**
	 * Konstruktor przyjmujący czas w formacie hh:mm:[ss]
	 * @param time
	 */
	public Time(String time){
		hour = Integer.parseInt(time.substring(0, 2));
		minute  = Integer.parseInt(time.substring(4,6));
	}
	
	
	@Override
	public String toString(){
		return String.format("%d:%d", hour, minute);
	}

}
