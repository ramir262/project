package pantherinspectproject;

import java.sql.Timestamp;

public class Time {
	Time() {
		
	}
	
	public String getCurrentTimestamp() {
		String timestamp = (new Timestamp(System.currentTimeMillis())).toString();
		return timestamp;
	}
}
