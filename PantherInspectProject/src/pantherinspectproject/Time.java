package pantherinspectproject;

import java.sql.Timestamp;

public class Time {
	Time() {
		
	}
	// setup timestamp for current moment
	public String getCurrentTimestamp() {
		String timestamp = (new Timestamp(System.currentTimeMillis())).toString();
		return timestamp;
	}
}
