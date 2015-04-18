package net.mmyumu.fonote.fragments;

import android.app.DialogFragment;
import android.app.FragmentManager;

public class UnspammableDialogFragment extends DialogFragment {
	@Override
	public void show(FragmentManager manager, String tag) {
		if (manager.findFragmentByTag(tag) == null) {
			super.show(manager, tag);
		}
	}
}
