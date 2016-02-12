package pwa;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleDateFormat df=new SimpleDateFormat("YYYY.MMHH:mm",Locale.FRANCE);
		String s="aaalabo";
		s.substring(3);
		System.out.println(s);
		
		try {
			System.out.println("\n "+df.parse("2017.1121:00"));
			System.out.println(DateFormat.getDateInstance(DateFormat.SHORT).parse("10/02/2016"));
			System.out.println(DateFormat.getTimeInstance(DateFormat.SHORT).parse("21:00"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
