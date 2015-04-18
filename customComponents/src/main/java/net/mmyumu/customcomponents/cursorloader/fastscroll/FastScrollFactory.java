package net.mmyumu.customcomponents.cursorloader.fastscroll;

import android.os.Build;

import net.mmyumu.customcomponents.cursorloader.fastscroll.impl.FastScrollLollipop;
import net.mmyumu.customcomponents.cursorloader.fastscroll.impl.FastScrollOldAPI;

/**
 * Factory to create the fast scroll activity customizer
 * Created by Martin on 19/12/2014.
 */
public class FastScrollFactory {
    /**
     * Create the fast scroll activity customizer according to the API version.
     *
     * @return the fast scroll customizer
     */
    public static FastScroll getInstance() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return new FastScrollLollipop();
        } else {
            return new FastScrollOldAPI();
        }
    }
}
