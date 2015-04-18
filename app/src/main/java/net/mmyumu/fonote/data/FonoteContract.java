package net.mmyumu.fonote.data;

import android.provider.BaseColumns;

public final class FonoteContract {
	// To prevent someone from accidentally instantiating the contract class,
	// give it an empty constructor.
	public FonoteContract() {
	}

	/* Inner class that defines the table contents */
	public static abstract class UserEntry implements BaseColumns {
		public static final String TABLE_NAME = "fonoteUser";
		public static final String COLUMN_NAME_NICKNAME = "nickname";
	}

	/* Inner class that defines the table contents */
	public static abstract class MatchEntry implements BaseColumns {
		public static final String TABLE_NAME = "fonoteMatch";
		public static final String COLUMN_NAME_HOME = "home";
		public static final String COLUMN_NAME_AWAY = "away";
		public static final String COLUMN_NAME_DATE = "matchDate";
	}
}