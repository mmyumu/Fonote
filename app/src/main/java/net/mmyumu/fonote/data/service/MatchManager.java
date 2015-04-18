package net.mmyumu.fonote.data.service;

import net.mmyumu.fonote.data.FonoteContract.MatchEntry;
import net.mmyumu.fonote.model.Match;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MatchManager extends Manager {

	public MatchManager(Context context) {
		super(context);
	}

	public long insertMatch(Match match) {
		// Gets the data repository in write mode
		SQLiteDatabase db = mDbHelper.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(MatchEntry.COLUMN_NAME_HOME, match.getHomeTeam());
		values.put(MatchEntry.COLUMN_NAME_AWAY, match.getAwayTeam());
		values.put(MatchEntry.COLUMN_NAME_DATE, match.getDateAsSQLString());

		return db.insert(MatchEntry.TABLE_NAME, MatchEntry.COLUMN_NAME_AWAY, values);
	}

	public Cursor getMatches() {
		SQLiteDatabase db = mDbHelper.getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = { MatchEntry._ID, MatchEntry.COLUMN_NAME_HOME,
				MatchEntry.COLUMN_NAME_AWAY, MatchEntry.COLUMN_NAME_DATE };

		// How you want the results sorted in the resulting Cursor
		String sortOrder = MatchEntry.COLUMN_NAME_DATE + " ASC";
		Cursor c = db.query(MatchEntry.TABLE_NAME, // The table to query
				projection, // The columns to return
				null, // The columns for the WHERE clause
				null, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				sortOrder // The sort order
				);

		return c;
	}
}
