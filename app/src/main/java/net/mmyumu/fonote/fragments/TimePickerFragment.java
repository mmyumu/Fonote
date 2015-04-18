package net.mmyumu.fonote.fragments;

import java.util.Calendar;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

public class TimePickerFragment extends UnspammableDialogFragment implements
		TimePickerDialog.OnTimeSetListener {

	private TimePickerManager timePickerManager;

	public TimePickerFragment(TimePickerManager timePickerManager) {
		this.timePickerManager = timePickerManager;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default values for the picker
		final Calendar c = timePickerManager.getDate();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute,
				DateFormat.is24HourFormat(getActivity()));
	}

	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		timePickerManager.onTimeSet(view, hourOfDay, minute);
	}
}