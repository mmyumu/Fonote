package net.mmyumu.customcomponents.cursorloader.fastscroll.impl;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import net.mmyumu.customcomponents.cursorloader.fastscroll.FastScroll;

import java.lang.reflect.Field;

/**
 * Customize the fast scroll with the API before Lollipop
 * Created by Martin on 19/12/2014.
 */
public abstract class FastScrollReflection extends FastScroll {

    /**
     * Field name constants
     */
    private static final String PRIMARY_TEXT_FIELD = "mPrimaryText";
    private static final String SECONDARY_TEXT_FIELD = "mSecondaryText";
    private static final String OVERLAY_FIELD = "mPreviewImage";

    protected abstract String getFastScrollFieldName();

    /**
     * Get the list view of the fast scroll fragment
     *
     * @return the list view of the fast scroll fragment
     */
    private ListView getListView() {
        return getFastScrollFragment().getListView();
    }

    /**
     * Get the application context
     *
     * @return the application context
     */
    private Context getApplicationContext() {
        return getFastScrollFragment().getActivity().getApplicationContext();
    }

    /**
     * Get the text size of the fast scroll
     *
     * @return the text size
     */
    private int getTextSizeId() {
        return getFastScrollFragment().retrieveTextSizeId();
    }

    /**
     * Get the overlay height
     *
     * @return the overlay height
     */
    private int getOverlayHeightId() {
        return getFastScrollFragment().retrieveOverlayHeightId();
    }

    /**
     * Customize the fast scroll fragment.
     */
    public void customize() {
        Object fastScroller = retrieveFastScroller();

        initFastScrollerTextView(fastScroller, PRIMARY_TEXT_FIELD);
        initFastScrollerTextView(fastScroller, SECONDARY_TEXT_FIELD);
        initFastScrollerOverlay(fastScroller);

    }

    private Object retrieveFastScroller() {
        try {
            return retrieveFastScrollerSafe();
        } catch (NoSuchFieldException e) {
            Log.e(getClass().getName(),
                    "The field mFastScroller doesn't exist, reflection is not possible (should not happen)",
                    e);
        } catch (IllegalAccessException e) {
            Log.e(getClass().getName(),
                    "Illegal access to mFastScroller in AbsListView", e);
        } catch (IllegalArgumentException e) {
            Log.e(getClass().getName(),
                    "Illegal argument exception for mFastScroller in AbsListView",
                    e);
        }

        return null;
    }

    private Object retrieveFastScrollerSafe() throws NoSuchFieldException,
            IllegalAccessException, IllegalArgumentException {
        Field fieldFastScroller = AbsListView.class
                .getDeclaredField(getFastScrollFieldName());
        fieldFastScroller.setAccessible(true);
        return fieldFastScroller.get(getListView());
    }

    private void initFastScrollerTextView(Object fastScroller, String textField) {
        try {
            initFastScrollerTextViewSafe(fastScroller, textField);
        } catch (NoSuchFieldException e) {
            Log.e(getClass().getName(),
                    "The field mFastScroller does not exist, reflection is not possible (should not happen)",
                    e);
        } catch (IllegalAccessException e) {
            Log.e(getClass().getName(),
                    "Illegal access to mFastScroller in AbsListView", e);
        } catch (IllegalArgumentException e) {
            Log.e(getClass().getName(),
                    "Illegal argument exception for mFastScroller in AbsListView",
                    e);
        }
    }

    private void initFastScrollerTextViewSafe(Object fastScroller,
                                              String textField) throws NoSuchFieldException,
            IllegalAccessException, IllegalArgumentException {
        final Resources res = getApplicationContext().getResources();

        Field fieldTextView = fastScroller.getClass().getDeclaredField(
                textField);
        fieldTextView.setAccessible(true);
        TextView textView = (TextView) fieldTextView.get(fastScroller);
        int textSize = res
                .getDimensionPixelSize(getTextSizeId());
        int textHeight = res
                .getDimensionPixelSize(getOverlayHeightId());
        textView.setTextSize(textSize);
        textView.setMinimumHeight(textHeight);
        // make the text half the normal fast scroller size
        // to make room for a short date "MMM dd"
        // fieldPrimaryText.set(primaryText, textSize);
        fieldTextView.set(fastScroller, textView);
    }

    private void initFastScrollerOverlay(Object fastScroller) {
        try {
            initFastScrollerOverlaySafe(fastScroller);
        } catch (NoSuchFieldException e) {
            Log.e(getClass().getName(),
                    "The overlay does not exist, reflection is not possible (should not happen)",
                    e);
        } catch (IllegalAccessException e) {
            Log.e(getClass().getName(),
                    "Illegal access to the overlay in AbsListView", e);
        } catch (IllegalArgumentException e) {
            Log.e(getClass().getName(),
                    "Illegal argument exception for overlay in AbsListView",
                    e);
        }
    }

    private void initFastScrollerOverlaySafe(Object fastScroller)
            throws NoSuchFieldException, IllegalAccessException,
            IllegalArgumentException {
        final Resources res = getApplicationContext().getResources();

        Field fieldOverlay = fastScroller.getClass().getDeclaredField(
                OVERLAY_FIELD);
        fieldOverlay.setAccessible(true);
        View imageView = (View) fieldOverlay.get(fastScroller);
        System.out.println(imageView.getMinimumHeight());
        int imageHeight = res
                .getDimensionPixelSize(getOverlayHeightId());
        imageView.setMinimumHeight(imageHeight);
        // make the text half the normal fast scroller size
        // to make room for a short date "MMM dd"
        // fieldPrimaryText.set(primaryText, textSize);
        fieldOverlay.set(fastScroller, imageView);
    }
}
