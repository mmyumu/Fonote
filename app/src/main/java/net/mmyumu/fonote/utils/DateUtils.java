package net.mmyumu.fonote.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.util.Log;

public class DateUtils {
	private DateUtils() {
	};

	public static String dateTo(String date, String pattern) {
		try {
			return dateToSafe(date, pattern).replaceAll("AM", "am").replaceAll(
					"PM", "pm");
		} catch (ParseException e) {
			Log.e(DateUtils.class.getName(), "Error while parsing date : "
					+ date);
		}

		return null;
	}

	private static String dateToSafe(String date, String pattern)
			throws ParseException {
		Calendar c = getCalendarFromDBDate(date);

		SimpleDateFormat timeFormat = new SimpleDateFormat(pattern, Locale.US);
		return timeFormat.format(c.getTime());
	}

	public static Calendar getCalendarFromDBDate(String date)
			throws ParseException {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
				Constants.DATETIME_DB_PATTERN, Locale.US);
		Calendar c = Calendar.getInstance();
		c.setTime(dateTimeFormat.parse(date));

		return c;
	}
}
