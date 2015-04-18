package net.mmyumu.fonote.data.service;

import net.mmyumu.fonote.data.FonoteContract.UserEntry;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserManager extends Manager {
	public UserManager(Context context) {
		super(context);
	}

	public void getUsers() {
		SQLiteDatabase db = mDbHelper.getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = { UserEntry._ID, UserEntry.COLUMN_NAME_NICKNAME };

		// How you want the results sorted in the resulting Cursor
		String sortOrder = UserEntry.COLUMN_NAME_NICKNAME + " DESC";

		Cursor c = db.query(UserEntry.TABLE_NAME, // The table to query
				projection, // The columns to return
				null, // The columns for the WHERE clause
				null, // The values for the WHERE clause
				null, // don't group the rows
				null, // don't filter by row groups
				sortOrder // The sort order
				);

		c.moveToFirst();

		while (c.moveToNext()) {
			c.getString(c.getColumnIndex(UserEntry.COLUMN_NAME_NICKNAME));
		}
	}
}
