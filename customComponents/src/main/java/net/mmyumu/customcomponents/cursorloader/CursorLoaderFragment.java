package net.mmyumu.customcomponents.cursorloader;

import android.app.ActionBar;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;

public abstract class CursorLoaderFragment extends android.support.v4.app.ListFragment implements
        android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {

    private SimpleCursorAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(retrieveLayoutId(), container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    /**
     * Create an empty adapter we will use to display the loaded data.
     * We pass null for the cursor, then update it in onLoadFinished()
     */
    private void initAdapter() {
        mAdapter = retrieveAdapter();
        setListAdapter(mAdapter);

        // Prepare the loader. Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(retrieveLoaderId(), null, this);
    }


    /**
     * Initialize the activity
     */
    private void init() {
        displayProgressBar();
        initAdapter();
    }

    /**
     * Display progress bar to display while the list loads
     */
    private void displayProgressBar() {
        ProgressBar progressBar = createProgressBar();

        // Must add the progress bar to the root of the layout
        ViewGroup root = (ViewGroup) getActivity().findViewById(android.R.id.content);
        root.addView(progressBar);
    }

    /**
     * Create a progress bar
     *
     * @return the created progress bar
     */
    private ProgressBar createProgressBar() {
        ProgressBar progressBar = new ProgressBar(getActivity());
        progressBar.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        progressBar.setIndeterminate(true);
        getListView().setEmptyView(progressBar);

        return progressBar;
    }


    /**
     * Retrieve the cursor adapter populating the fragment
     *
     * @return the simple cursor adapter of the fragment
     */
    protected abstract SimpleCursorAdapter retrieveAdapter();

    /**
     * Retrieve the ID of the loader of the fragment
     *
     * @return ID of the loader
     */
    protected abstract int retrieveLoaderId();

    /**
     * Retrieve the ID of the layout to be used for this fragment
     *
     * @return ID of the layout
     */
    protected abstract int retrieveLayoutId();

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    public void onListItemClick(ListView listView, View view, int position, long id) {
    }
}