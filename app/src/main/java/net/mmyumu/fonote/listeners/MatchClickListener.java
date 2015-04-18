package net.mmyumu.fonote.listeners;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import net.mmyumu.fonote.DetailsMatchActivity;
import net.mmyumu.fonote.utils.Constants;

/**
 * Listener when a click is done on a match to see the details.
 * Created by Martin on 03/01/2015.
 */
public class MatchClickListener implements AdapterView.OnItemClickListener {

    /**
     * The activity containing the clicked match
     */
    private Activity activity;

    public MatchClickListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(getClass().getName(), "POSITION=" + position);
        Log.d(getClass().getName(), "ID=" + id);
        Intent intent = new Intent(activity, DetailsMatchActivity.class);
        intent.putExtra(DetailsMatchActivity.ID, id);
        activity.startActivityForResult(intent, Constants.REQUEST_CODES.DETAILS_MATCH);
    }
}
