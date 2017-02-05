package com.ualberta.nyitrai.nyitrai_sizebook;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nyitrai on 2/5/2017.
 */

public class SizeBookNewRecordActivity extends Activity implements SView<SizeBook> {

    private EditText recordName;
    private EditText recordDate;
    private EditText recordComment;

    private String name;
    private String tempDate;
    private Date date;
    private String comment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newrecord);

        recordName = (EditText) findViewById(R.id.recordName);
        recordDate = (EditText) findViewById(R.id.recordDate);
        recordComment = (EditText) findViewById(R.id.recordComment);

        Button createButton = (Button) findViewById(R.id.createButton);

        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                name = recordName.getText().toString();


                tempDate = recordDate.getText().toString();
                Calendar cal = Calendar.getInstance();
                //TODO: Convert the string gotten form the EditText into a Date object.

                BookController bc = SizeBookApplication.getBookController();
                bc.createRecord(name, date, comment);
            }
        });
    }

    public void update(SizeBook sizeBook) {

    }
}
