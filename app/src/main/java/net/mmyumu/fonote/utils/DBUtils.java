package net.mmyumu.fonote.utils;

import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.util.Calendar;

/**
 * Created by Martin on 14/04/2015.
 */
public class DBUtils {

    private DBUtils() {
    }

    public static String getString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static Calendar getDate(Cursor cursor, String columnName) {
        String date = getString(cursor, columnName);
        try {
            return DateUtils.getCalendarFromDBDate(date);
        } catch (ParseException e) {
            Log.e(DateUtils.class.getName(), "Error while parsing date : "
                    + date);
        }

        return null;
    }

    public static String getDateAsString(Cursor cursor, String columnName) {
        int dateIndex = cursor.getColumnIndex(columnName);
        return DateUtils.dateTo(cursor.getString(dateIndex),
                Constants.TIME_PATTERN);
    }
}
