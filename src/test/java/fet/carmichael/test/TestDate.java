package fet.carmichael.test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDate {

	public static void main(String[] args) {
		
		String dateString = "2015-12-30 20:25:58";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(dateString);
			sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
			String strdate = sdf.format(date);
			System.out.println(date);
			System.out.println(strdate);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
