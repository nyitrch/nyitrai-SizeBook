package com.ualberta.nyitrai.nyitrai_sizebook;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by nyitrai on 2/5/2017.
 */

public class SizeBookNewRecordActivity extends Activity implements SView<SizeBook> {
    /**
     * Called when activity is first created. In this, the default date for the DatePicker is set,
     * the create button updates records and closes the activity, and this view is added to the
     * SizeBook model via the SizeBookApplication go-between.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newrecord);

        // Set default date for DatePicker to current date.
        DatePicker recordDate = (DatePicker) findViewById(R.id.recordDate);
        setCurrentDate(recordDate);

        // Create record button press.
        Button createButton = (Button) findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Tell controller to update the records and exit.
                updateRecords();
                finish();
            }
        });
        // Add view to our SizeBookApplication.
        SizeBook sb = SizeBookApplication.getSizeBook();
        sb.addView(this);
    }

    /**
     * Generic update method from interface. Calls updateRecords().
     */
    public void update(SizeBook sizeBook) {
        updateRecords();
    }

    /**
     * Does normal destroy stuff and also removes view from
     * SizeBook via SizeBookApplication go-between.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        SizeBook sb = SizeBookApplication.getSizeBook();
        sb.deleteView(this);
    }

    /**
     * Loads variables from user input, converts them into the format used in the model SizeBook,
     * and passes them through the controller, creating a new record for the model SizeBook.
     */
    public void updateRecords() {
        // Load variables.
        EditText recordName = (EditText) findViewById(R.id.recordName);
        EditText recordComment = (EditText) findViewById(R.id.recordComment);
        DatePicker recordDate = (DatePicker) findViewById(R.id.recordDate);

        // Convert variables.
        String name = recordName.getText().toString();
        String comment = recordComment.getText().toString();
        Date date = getDateFromDatePicker(recordDate);

        // Pass info from user through to controller with the command to create a new record.
        BookController bc = SizeBookApplication.getBookController();
        bc.createRecord(name, date, comment);
    }

    /**
     * Set default date for DatePicker to current date.
     * @param datePicker The DatePicker to set the current date to.
     */
    public void setCurrentDate(DatePicker datePicker) {
        Calendar now = Calendar.getInstance();

        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        datePicker.init(year, month, day, null);
    }

    /**
     * Retrieves date from DatePicker object into a Date object.
     * Method adapted from the following on 2/6/2017.
     * http://stackoverflow.com/questions/2592499/casting-and-getting-values-from-date-picker-and-time-picker-in-android/14590203#14590203
     * @param datePicker The DatePicker to retrieve the date from.
     * @return Date corresponding to the DatePicker.
     */
    public Date getDateFromDatePicker(DatePicker datePicker) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getDayOfMonth(), datePicker.getMonth(),
                datePicker.getYear());
        return calendar.getTime();
    }

}
