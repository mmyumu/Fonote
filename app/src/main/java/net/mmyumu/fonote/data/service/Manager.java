package net.mmyumu.fonote.data.service;

import net.mmyumu.fonote.data.FonoteDbHelper;
import android.content.Context;

public class Manager {
	protected FonoteDbHelper mDbHelper;

	public Manager(Context context) {
		mDbHelper = new FonoteDbHelper(context);
	}
}
