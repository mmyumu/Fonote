package net.mmyumu.customcomponents.cursorloader.fastscroll.impl;

/**
 * Customize the fast scroll with Lollipop API
 * Created by Martin on 19/12/2014.
 */
public class FastScrollLollipop extends FastScrollReflection {

    private static final String FASTSCROLL_FIELD = "mFastScroll";

    @Override
    protected String getFastScrollFieldName() {
        return FASTSCROLL_FIELD;
    }
}
