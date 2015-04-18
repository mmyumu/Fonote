package net.mmyumu.fonote.fragments;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

public class DatePickerFragment extends UnspammableDialogFragment implements
		DatePickerDialog.OnDateSetListener {

	private DatePickerManager datePickerManager;

	public DatePickerFragment(DatePickerManager datePickerManager) {
		this.datePickerManager = datePickerManager;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = datePickerManager.getDate();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		datePickerManager.onDateSet(view, year, month, day);
	}
}