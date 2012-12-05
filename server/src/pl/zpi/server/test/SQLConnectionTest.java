package pl.zpi.server.test;
import java.io.IOException;
import java.util.Calendar;

import pl.zpi.server.db.DBHarmonogramy;
import pl.zpi.server.utils.Config;




public class SQLConnectionTest {
	public static void main(String[] args) throws IOException{
		Calendar c = Calendar.getInstance();
		String s_time = "22:30";
		int hour = Integer.parseInt(s_time.substring(0, 2));
		int hourDiff = hour - c.get(Calendar.HOUR_OF_DAY);
		System.out.println("Hour diff "+hourDiff);
		
			int minute = Integer.parseInt(s_time.substring(3,5));
			int minuteDiff = minute - c.get(Calendar.MINUTE);
			System.out.println("Minute diff "+minuteDiff);
			int delayInSec = hourDiff * 3600 + minuteDiff * 60;
			System.out.println("In minutes: "+(hourDiff*60 + minuteDiff));
	
	}
	
}
