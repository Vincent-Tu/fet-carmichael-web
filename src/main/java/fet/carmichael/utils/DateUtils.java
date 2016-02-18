package fet.carmichael.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.stereotype.Component;

@Component
public class DateUtils {
	public static String getCurrentUnixTime() {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC+8:00"));
		return String.valueOf(calendar.getTime().getTime());
	}
	
	public static String unixTimeFormat(final Date date) {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC+8:00"));
		calendar.setTime(date);
		return String.valueOf(calendar.getTime().getTime());
	}

}
