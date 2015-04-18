package net.mmyumu.fonote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import net.mmyumu.fonote.contentproviders.FonoteContentProvider;
import net.mmyumu.fonote.data.FonoteContract;
import net.mmyumu.fonote.model.Match;

/**
 * Display the details of a match
 * Created by Martin on 03/01/2015.
 */
public class DetailsMatchActivity extends Activity {

    /**
     * The identifier for the position to pass to the details activity
     */
    public static final String ID = "id";


    private Match newMatch;

    public DetailsMatchActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_match);

        newMatch = new Match();
        Intent intent = getIntent();

        long id = intent.getLongExtra(ID, -1);

//        getContentResolver().query(FonoteContentProvider.getContentUriMatch(id), PROJECTION, null, null, null);
        System.out.println("### detail activity " + id);
        initTabs();
    }

    private void initTabs() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_match, menu);
        return true;
    }
}
