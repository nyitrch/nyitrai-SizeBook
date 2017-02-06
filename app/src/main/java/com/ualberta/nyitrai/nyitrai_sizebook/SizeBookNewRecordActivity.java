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

    private EditText recordName;
    private DatePicker recordDate;
    private EditText recordComment;

    private String name;
    private Date date;
    private String comment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newrecord);

        recordName = (EditText) findViewById(R.id.recordName);
        recordComment = (EditText) findViewById(R.id.recordComment);
        recordDate = (DatePicker) findViewById(R.id.recordDate);

        // Set default date for DatePicker to current date.
        setCurrentDate(recordDate);

        Button createButton = (Button) findViewById(R.id.createButton);

        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                name = recordName.getText().toString();
                comment = recordComment.getText().toString();
                date = getDateFromDatePicker(recordDate);

                BookController bc = SizeBookApplication.getBookController();
                bc.createRecord(name, date, comment);
            }
        });
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

    public void update(SizeBook sizeBook) {

    }
}
