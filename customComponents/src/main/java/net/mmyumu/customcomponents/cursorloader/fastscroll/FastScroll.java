package net.mmyumu.customcomponents.cursorloader.fastscroll;

/**
 * Class to manage the customization of the fast scroll fragment
 * Created by Martin on 19/12/2014.
 */
public abstract class FastScroll {
    /**
     * The fast scroll fragment to be customized
     */
    private FastScrollFragment fastScrollFragment;

    /**
     * Get the customized fast scroll fragment
     *
     * @return the customized fast scroll fragment
     */
    public FastScrollFragment getFastScrollFragment() {
        return fastScrollFragment;
    }

    /**
     * Set the fast scroll fragment to customize
     *
     * @param fastScrollFragment the fast scroll fragment to be customized
     */
    public void setFastScrollFragment(FastScrollFragment fastScrollFragment) {
        this.fastScrollFragment = fastScrollFragment;
    }

    /**
     * The method to customize the fast scroll activity.
     * Must be called after the fast scroll is enabled.
     */
    public abstract void customize();
}
