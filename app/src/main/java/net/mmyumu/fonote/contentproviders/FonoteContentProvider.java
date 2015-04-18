package net.mmyumu.fonote.contentproviders;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import net.mmyumu.fonote.data.FonoteContract.MatchEntry;
import net.mmyumu.fonote.data.FonoteDbHelper;

public class FonoteContentProvider extends ContentProvider {

    private static final String PROVIDER_NAME = "net.mmyumu.fonote.content.provider";

    public static final Uri CONTENT_URI_MATCH = Uri.parse("content://"
            + PROVIDER_NAME + "/fonoteMatch");

    private static final int MATCH = 1;
    private static final int MATCH_ID = 2;

    private FonoteDbHelper dbHelper;

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "fonoteMatch", MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "fonoteMatch/#", MATCH_ID);
    }

    public static Uri getContentUriMatch(long id) {
        return Uri.parse("content://"
                + PROVIDER_NAME + "/fonoteMatch/" + id);
    }



    @Override
    public boolean onCreate() {
        dbHelper = new FonoteDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        switch (uriMatcher.match(uri)) {
            case MATCH:
                return queryMatch(-1, projection, selection, selectionArgs,
                        sortOrder);
            case MATCH_ID:
                long id = getId(uri);
                return queryMatch(id, projection, selection, selectionArgs,
                        sortOrder);
            default:
                throw new SQLException("Failed to query row " + uri);
        }
    }

    @Override
    public String getType(Uri uri) {
        return FonoteDbHelper.MIME;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri insertedObjectUri;
        switch (uriMatcher.match(uri)) {
            case MATCH:
                insertedObjectUri = insertMatch(values);
                break;
            default:
                throw new SQLException("Failed to insert row into " + uri);
        }
        return insertedObjectUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        long id = getId(uri);

        switch (uriMatcher.match(uri)) {
            case MATCH:
                return updateMatch(id, values, selection, selectionArgs);
            default:
                throw new SQLException("Failed to update row " + uri);
        }
    }

    private long getId(Uri uri) {
        String lastPathSegment = uri.getLastPathSegment();
        if (lastPathSegment != null) {
            try {
                return Long.parseLong(lastPathSegment);
            } catch (NumberFormatException e) {
                Log.e(FonoteContentProvider.class.getName(),
                        "Number Format Exception : " + e);
            }
        }
        return -1;
    }

    private Uri insertMatch(ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long id = db.insert(MatchEntry.TABLE_NAME,
                FonoteDbHelper.NULLABLE_COLUMN_HACK, values);

        return getUriFromId(CONTENT_URI_MATCH, id);
    }

    private Uri getUriFromId(Uri uri, long id) {
        if (id > 0) {
            Uri insertedObjectUri = ContentUris.withAppendedId(uri, id);
            getContext().getContentResolver().notifyChange(insertedObjectUri,
                    null);

            return insertedObjectUri;
        }

        return null;
    }

    private int updateMatch(long id, ContentValues values, String selection,
                            String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (id < 0) {
            return db.update(MatchEntry.TABLE_NAME, values, selection,
                    selectionArgs);
        } else {
            return db.update(MatchEntry.TABLE_NAME, values, MatchEntry._ID
                    + "=" + id, null);
        }
    }

    private Cursor queryMatch(long id, String[] projection, String selection,
                              String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (id < 0) {
            return db.query(MatchEntry.TABLE_NAME, projection, selection,
                    selectionArgs, null, null, sortOrder);
        } else {
            return db.query(MatchEntry.TABLE_NAME, projection, MatchEntry._ID
                    + "=" + id, null, null, null, null);
        }
    }
}
