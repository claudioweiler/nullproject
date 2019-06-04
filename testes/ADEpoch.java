package testes;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class ADEpoch {
	public static void main(final String[] args) {
		final Calendar msEpochCalender = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		msEpochCalender.clear();
		msEpochCalender.set(1601, 0, 1, 0, 0);
		System.out.println("" + msEpochCalender.getTime().getTime());
	}
}
