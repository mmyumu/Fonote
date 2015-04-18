package net.mmyumu.customcomponents.cursorloader.fastscroll.impl;

/**
 * Created by Martin on 01/01/2015.
 */
public class FastScrollOldAPI extends FastScrollReflection {
    private static final String FASTSCROLL_FIELD = "mFastScroller";

    @Override
    protected String getFastScrollFieldName() {
        return FASTSCROLL_FIELD;
    }
}
