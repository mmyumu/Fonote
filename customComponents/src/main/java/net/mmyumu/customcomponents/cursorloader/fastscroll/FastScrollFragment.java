package net.mmyumu.customcomponents.cursorloader.fastscroll;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import net.mmyumu.customcomponents.cursorloader.CursorLoaderFragment;

/**
 * Activity managing a customizable fast scroll
 */
public abstract class FastScrollFragment extends CursorLoaderFragment {

    /**
     * The fast scroll customizer
     */
    private FastScroll fastScroll = FastScrollFactory.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fastScroll.setFastScrollFragment(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enableFastScroll();
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor cursor) {
        super.onLoadFinished(loader, cursor);
        enableFastScroll();
    }

    /**
     * Enable customized fast scroll
     */
    private void enableFastScroll() {
        getListView().setFastScrollEnabled(true);
//        getListView().setFastScrollAlwaysVisible(true);

        fastScroll.customize();
    }


    /**
     * Retrieve the text size (API < 21 [Lollipop])
     *
     * @return the text size
     */
    public abstract int retrieveTextSizeId();

    /**
     * Retrieve the overlay height (API < 21 [Lollipop])
     *
     * @return the overlay height
     */
    public abstract int retrieveOverlayHeightId();
}
