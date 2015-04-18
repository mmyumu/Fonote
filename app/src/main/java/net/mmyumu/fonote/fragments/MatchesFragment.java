package net.mmyumu.fonote.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import net.mmyumu.customcomponents.cursorloader.fastscroll.FastScrollFragment;
import net.mmyumu.fonote.NewMatchActivity;
import net.mmyumu.fonote.R;
import net.mmyumu.fonote.adapters.FonoteSimpleCursorAdapter;
import net.mmyumu.fonote.contentproviders.FonoteContentProvider;
import net.mmyumu.fonote.data.FonoteContract;
import net.mmyumu.fonote.listeners.MatchClickListener;
import net.mmyumu.fonote.utils.Constants;

/**
 * Created by Martin on 28/01/2015.
 */
public class MatchesFragment extends FastScrollFragment {
    private static final int MATCH_LOADER = 0;

    private static final String[] PROJECTION = new String[]{FonoteContract.MatchEntry._ID,
            FonoteContract.MatchEntry.COLUMN_NAME_HOME, FonoteContract.MatchEntry.COLUMN_NAME_AWAY,
            FonoteContract.MatchEntry.COLUMN_NAME_DATE};

    private static final String SELECTION = null;

    @Override
    public int retrieveTextSizeId() {
        return R.dimen.fastscroll_overlay_text_size;
    }

    @Override
    public int retrieveOverlayHeightId() {
        return R.dimen.fastscroll_overlay_size;
    }

    @Override
    protected SimpleCursorAdapter retrieveAdapter() {
        return new FonoteSimpleCursorAdapter(getActivity(), R.layout.schedule_match,
                null, retrieveFromColumns(), retrieveToViews(), 0);
    }

    protected String[] retrieveFromColumns() {
        return new String[]{FonoteContract.MatchEntry.COLUMN_NAME_HOME,
                FonoteContract.MatchEntry.COLUMN_NAME_AWAY, FonoteContract.MatchEntry.COLUMN_NAME_DATE};
    }

    protected int[] retrieveToViews() {
        return new int[]{R.id.schedule_match_home, R.id.schedule_match_away,
                R.id.schedule_match_date};
    }

    @Override
    protected int retrieveLoaderId() {
        return MATCH_LOADER;
    }

    @Override
    protected int retrieveLayoutId() {
        return R.layout.fast_scroll;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.schedule_matches, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setOnItemClickListener(new MatchClickListener(getActivity()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_new) {
            newMatch();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void newMatch() {
        Intent intent = new Intent(getActivity(), NewMatchActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODES.NEW_MATCH);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODES.NEW_MATCH) {
            if (resultCode == Activity.RESULT_OK) {
                getLoaderManager().restartLoader(MATCH_LOADER, null, this);
                Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                        getString(R.string.match_saved), Toast.LENGTH_SHORT);
                toast.show();
            }
        } else if (requestCode == Constants.REQUEST_CODES.DETAILS_MATCH) {
            System.out.println("### RETOUR MAIN ACTIVITY");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case MATCH_LOADER:
                return new CursorLoader(getActivity(),
                        FonoteContentProvider.CONTENT_URI_MATCH, PROJECTION,
                        SELECTION, null, FonoteContract.MatchEntry.COLUMN_NAME_DATE);
            default:
                return null;
        }
    }
}
