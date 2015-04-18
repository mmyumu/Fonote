package net.mmyumu.fonote.fragments;

import android.widget.DatePicker;

public interface DatePickerManager extends PickerManager {
	void onDateSet(DatePicker view, int year, int month, int day);
}
