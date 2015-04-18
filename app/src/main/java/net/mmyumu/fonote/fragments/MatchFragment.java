package net.mmyumu.fonote.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import net.mmyumu.fonote.DetailsMatchActivity;
import net.mmyumu.fonote.R;
import net.mmyumu.fonote.contentproviders.FonoteContentProvider;
import net.mmyumu.fonote.data.FonoteContract;
import net.mmyumu.fonote.model.Match;
import net.mmyumu.fonote.utils.Constants;
import net.mmyumu.fonote.utils.DBUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Martin on 02/03/2015.
 */
public class MatchFragment extends Fragment implements DatePickerManager,
        TimePickerManager, View.OnClickListener {
    private static final String[] PROJECTION = {
            FonoteContract.MatchEntry._ID,
            FonoteContract.MatchEntry.COLUMN_NAME_HOME,
            FonoteContract.MatchEntry.COLUMN_NAME_AWAY,
            FonoteContract.MatchEntry.COLUMN_NAME_DATE,
    };

    private Match newMatch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        readExtras(savedInstanceState);
    }

    private void readExtras(Bundle savedInstanceState) {
        Long id;
        if (savedInstanceState == null) {
            Bundle extras = getActivity().getIntent().getExtras();
            if (extras == null) {
                id = null;
            } else {
                id = extras.getLong(DetailsMatchActivity.ID);
            }
        } else {
            id = (Long) savedInstanceState.getSerializable(DetailsMatchActivity.ID);
        }

        Log.d(getClass().getName(), "Read extra ID of match for details=" + id);

        Cursor c = getActivity().getContentResolver().query(FonoteContentProvider.getContentUriMatch(id), PROJECTION, null, null, null);
        while (c.moveToNext()) {
            newMatch = new Match();
            newMatch.setHomeTeam(DBUtils.getString(c, FonoteContract.MatchEntry.COLUMN_NAME_HOME));
            newMatch.setAwayTeam(DBUtils.getString(c, FonoteContract.MatchEntry.COLUMN_NAME_AWAY));
            System.out.println(DBUtils.getString(c, FonoteContract.MatchEntry.COLUMN_NAME_DATE));
            newMatch.setDate(DBUtils.getDate(c, FonoteContract.MatchEntry.COLUMN_NAME_DATE));
            System.out.println("newMatch.date="+newMatch.getDate());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_match, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(newMatch == null) {
            newMatch = new Match();
        }

        initDateText();
        initTimeText();

        initListeners();
    }

    private void initListeners() {
        addOnClickListener(R.id.edit_date);
        addOnClickListener(R.id.edit_time);
    }

    private void addOnClickListener(int id) {
        TextView textView = (TextView) getView().findViewById(id);
        textView.setOnClickListener(this);
    }

    private void initDateText() {
        Calendar c = newMatch.getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                Constants.DATE_PATTERN, Locale.US);
        String text = simpleDateFormat.format(c.getTime());

        initText(R.id.edit_date, text);
    }

    private void initTimeText() {
        Calendar c = newMatch.getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                Constants.TIME_PATTERN, Locale.US);
        String text = simpleDateFormat.format(c.getTime());

        initText(R.id.edit_time, text);
    }

    private void initText(int id, String text) {
        TextView textView = (TextView) getView().findViewById(id);
        textView.setText(text);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = newMatch.getDate();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);

        initDateText();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = newMatch.getDate();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);

        initTimeText();
    }

    @Override
    public Calendar getDate() {
        return newMatch.getDate();
    }

    private void saveMatch() {
        EditText home = (EditText) getView().findViewById(R.id.edit_home);
        EditText away = (EditText) getView().findViewById(R.id.edit_away);

        newMatch.setHomeTeam(home.getText().toString());
        newMatch.setAwayTeam(away.getText().toString());

        ContentValues values = initValues();

        getActivity().getContentResolver().insert(FonoteContentProvider.CONTENT_URI_MATCH,
                values);

        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    private ContentValues initValues() {
        ContentValues values = new ContentValues();
        values.put(FonoteContract.MatchEntry.COLUMN_NAME_HOME, newMatch.getHomeTeam());
        values.put(FonoteContract.MatchEntry.COLUMN_NAME_AWAY, newMatch.getAwayTeam());
        values.put(FonoteContract.MatchEntry.COLUMN_NAME_DATE, newMatch.getDateAsSQLString());

        return values;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        System.out.println("###ITEM CLICK");
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_done) {

            saveMatch();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_date:
                showDatePicker();
                break;
            case R.id.edit_time:
                showTimePicker();
                break;
        }
    }

    private void showDatePicker() {
        DialogFragment newFragment = new DatePickerFragment(this);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    private void showTimePicker() {
        DialogFragment newFragment = new TimePickerFragment(this);
        newFragment.show(getFragmentManager(), "timePicker");
    }
}
