package net.mmyumu.fonote.adapters;

import net.mmyumu.fonote.R;
import net.mmyumu.fonote.data.FonoteContract.MatchEntry;
import net.mmyumu.fonote.indexers.FonoteIndexer;
import net.mmyumu.fonote.utils.Constants;
import net.mmyumu.fonote.utils.DBUtils;
import net.mmyumu.fonote.utils.DateUtils;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class FonoteSimpleCursorAdapter extends SimpleCursorAdapter implements
		SectionIndexer {

	private final static int TYPE_HEADER_MATCH = 0;
	private final static int TYPE_ROW_MATCH = 1;

	private final LayoutInflater mInflater;

	private Cursor cursor;

	private final FonoteIndexer<String> indexer;

	public FonoteSimpleCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		this.cursor = c;
		mInflater = LayoutInflater.from(context);
		indexer = new FonoteIndexer<>();
	}

	@Override
	public void setViewText(TextView v, String text) {
		if (v.getId() == R.id.schedule_match_date) {
			text = manageScheduleMatchTime(text);
		}
		super.setViewText(v, text);
	}

	private String manageScheduleMatchTime(String text) {
		return DateUtils.dateTo(text, Constants.TIME_PATTERN);
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		cursor.moveToPosition(position);
		int dateIndex = cursor.getColumnIndex(MatchEntry.COLUMN_NAME_DATE);

		String thisDate;
		thisDate = DateUtils.dateTo(cursor.getString(dateIndex),
				Constants.DATE_PATTERN);
		String prevDate = null;

		// previous row, for comparison
		if (cursor.getPosition() > 0 && cursor.moveToPrevious()) {
			prevDate = DateUtils.dateTo(cursor.getString(dateIndex),
					Constants.DATE_PATTERN);
			cursor.moveToNext();
		}

		// enable section heading if it's the first one, or
		// different from the previous one
		if (prevDate == null || !prevDate.equals(thisDate)) {
			return TYPE_HEADER_MATCH;
		} else {
			return TYPE_ROW_MATCH;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		switch (getItemViewType(position)) {
		case TYPE_HEADER_MATCH:
			return getHeaderDateView(position, convertView, parent);
		case TYPE_ROW_MATCH:
			return getRowMatchView(position, convertView, parent);
		}

		// TODO: check if I should return something else (like convertView)
		return null;
	}

	private View getHeaderDateView(int position, View convertView,
			ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.header_match, parent,
					false);
			holder = initHeaderViewHolderFrom(convertView);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		initHeaderHolderValues(holder, position);

		return convertView;
	}

	private View getRowMatchView(int position, View convertView,
			ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_match, parent, false);
			holder = initViewHolderFrom(convertView);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		initHolderValues(holder, position);

		return convertView;
	}

	private ViewHolder initViewHolderFrom(View convertView) {
		ViewHolder holder = new ViewHolder();
		holder.home = (TextView) convertView
				.findViewById(R.id.schedule_match_home);
		holder.away = (TextView) convertView
				.findViewById(R.id.schedule_match_away);
		holder.date = (TextView) convertView
				.findViewById(R.id.schedule_match_date);
		convertView.setTag(holder);

		return holder;
	}

	private ViewHolder initHeaderViewHolderFrom(View convertView) {
		ViewHolder holder = initViewHolderFrom(convertView);
		holder.headerDate = (TextView) convertView
				.findViewById(R.id.header_match_date);

		return holder;
	}

	private void initHolderValues(ViewHolder holder, int position) {
		cursor.moveToPosition(position);
		String home = DBUtils.getString(cursor, MatchEntry.COLUMN_NAME_HOME);
		String away = DBUtils.getString(cursor, MatchEntry.COLUMN_NAME_AWAY);
		String date = DBUtils.getDateAsString(cursor, MatchEntry.COLUMN_NAME_DATE);

		holder.home.setText(home);
		holder.away.setText(away);
		holder.date.setText(date);
	}

	private void initHeaderHolderValues(ViewHolder holder, int position) {
		initHolderValues(holder, position);

		int dateIndex = cursor.getColumnIndex(MatchEntry.COLUMN_NAME_DATE);
		String date = DateUtils.dateTo(cursor.getString(dateIndex),
				Constants.DATE_PATTERN);

		holder.headerDate.setText(date);
	}

	@Override
	public Cursor swapCursor(Cursor c) {
		cursor = c;

		if (c != null) {
			initializeSections();
		}

		return super.swapCursor(c);
	}

	private void initializeSections() {
		int savedPosition = cursor.getPosition();
		while (cursor.moveToNext()) {
			switch (getItemViewType(cursor.getPosition())) {
			case TYPE_HEADER_MATCH:
				int dateIndex = cursor
						.getColumnIndex(MatchEntry.COLUMN_NAME_DATE);
				String date = DateUtils.dateTo(cursor.getString(dateIndex),
						Constants.SHORT_DATE_PATTERN);
				indexer.push(date, cursor.getPosition());
				break;
			default:
				break;
			}
		}

		cursor.moveToPosition(savedPosition);
	}

	static class ViewHolder {
		TextView headerDate;

		TextView home;
		TextView away;
		TextView date;
	}

	@Override
	public Object[] getSections() {
		return indexer.getSections();
	}

	@Override
	public int getPositionForSection(int sectionIndex) {
		return indexer.getPositionByIndex(sectionIndex);
	}

	@Override
	public int getSectionForPosition(int position) {
		return indexer.getSectionForPosition(position);
	}
}
