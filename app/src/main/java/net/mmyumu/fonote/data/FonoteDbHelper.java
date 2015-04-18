package net.mmyumu.fonote.data;

import net.mmyumu.fonote.data.FonoteContract.MatchEntry;
import net.mmyumu.fonote.data.FonoteContract.UserEntry;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FonoteDbHelper extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Fonote.db";
	public static final String MIME = "vnd.android.cursor.item/vnd.mmyumu.fonote.content.provider";

	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String REAL_TYPE = " REAL";
	private static final String BLOB_TYPE = " BLOB";

	private static final String NOT_NULL = " NOT NULL";

	private static final String COMMA_SEP = ",";

	public static final String NULLABLE_COLUMN_HACK = " nullableColumnHack INTEGER";

	//@formatter:off
	private static final String SQL_CREATE_USER_TABLE =  
			"CREATE TABLE " + UserEntry.TABLE_NAME + " (" 
			+ UserEntry._ID + " INTEGER PRIMARY KEY," 
			+ UserEntry.COLUMN_NAME_NICKNAME + TEXT_TYPE + COMMA_SEP 
			+ NULLABLE_COLUMN_HACK
			+ "); ";
	
	private static final String SQL_CREATE_MATCH_TABLE = 
			"CREATE TABLE " + MatchEntry.TABLE_NAME + " (" 
			+ MatchEntry._ID + " INTEGER PRIMARY KEY," 
			+ MatchEntry.COLUMN_NAME_HOME + TEXT_TYPE + COMMA_SEP 
			+ MatchEntry.COLUMN_NAME_AWAY + TEXT_TYPE + COMMA_SEP
			+ MatchEntry.COLUMN_NAME_DATE + TEXT_TYPE + COMMA_SEP
			+ NULLABLE_COLUMN_HACK
			+ "); ";
			 
	private static final String SQL_DROP_TABLES = 
			"DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME 
			+ " DROP TABLE IF EXISTS " + MatchEntry.TABLE_NAME;
	//@formatter:on

	public FonoteDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_USER_TABLE);
		db.execSQL(SQL_CREATE_MATCH_TABLE);

		initDefaultValues(db);
	}

	private void initDefaultValues(SQLiteDatabase db) {
		// TODO: add default values here
		// for(teams) {
		// db.insert(table, nullColumnHack, values)
		// }
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy
		// is
		// to simply to discard the data and start over
		// TODO: change here when you got what's happening...
		db.execSQL(SQL_DROP_TABLES);
		onCreate(db);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
}