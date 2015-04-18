package net.mmyumu.fonote.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import net.mmyumu.fonote.utils.Constants;

public class Match {
	private String homeTeam;

	private String awayTeam;

	private Calendar date;

	public Match() {
		initDefaultDate();
	}

	public String getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	public String getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public String getDateAsSQLString() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				Constants.DATETIME_DB_PATTERN, Locale.US);
		return simpleDateFormat.format(date.getTime());
	}

    private void initDefaultDate() {
        date = Calendar.getInstance();
        date.set(Calendar.HOUR_OF_DAY, 21);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
    }
}
