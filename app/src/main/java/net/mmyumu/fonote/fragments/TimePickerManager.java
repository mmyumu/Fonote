package net.mmyumu.fonote.fragments;

import android.widget.TimePicker;

public interface TimePickerManager extends PickerManager {
	void onTimeSet(TimePicker view, int hourOfDay, int minute);
}
