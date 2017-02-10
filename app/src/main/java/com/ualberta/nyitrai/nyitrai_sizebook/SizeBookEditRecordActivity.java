package com.ualberta.nyitrai.nyitrai_sizebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;


/**
 * Created by nyitrai on 2/10/2017.
 */

public class SizeBookEditRecordActivity extends Activity implements SView<SizeBook> {

    private ListView oldFields;
    private ArrayAdapter<GenericField> adapter;
    private ArrayList<GenericField> fields;

    private int fieldEditPosition;
    private Record record;

    /**
     * Called when activity is first created. Much of the initialization is done in the onCreate
     * and not onStart, because we do not write to a file in between. We only write to a file
     * or store data after the "Save Changes" button is pressed.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editrecord);

        oldFields = (ListView) findViewById(R.id.oldFields);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button newFieldButton = (Button) findViewById(R.id.newFieldButton);
        EditText recordName = (EditText) findViewById(R.id.recordName);
        EditText recordComment = (EditText) findViewById(R.id.recordComment);
        TextView recordDate = (TextView) findViewById(R.id.recordDate);

        // Get record that is passed through from SizebookActivity.
        String strRecord = getIntent().getStringExtra("record");
        Gson gson = new Gson();
        record = gson.fromJson(strRecord, Record.class);

        // Get fields from the record and setup the adapter.
        fields = record.getFields();
        if (fields == null) {
            fields = new ArrayList<GenericField>();
        }
        adapter = new ArrayAdapter<GenericField>(this, R.layout.list_item, fields);
        oldFields.setAdapter(adapter);

        recordComment.setText(record.getComment());
        recordName.setText(record.getName());
        recordDate.setText(record.getDate().toString());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Record newRecord = createEditedRecord();

                if (newRecord != null) {
                    /* Pass user entered record back to the previous activity using GSON and JSON.
                    I found out how to do this from
                    http://stackoverflow.com/questions/14292398/how-to-pass-data-from-2nd-activity-to-1st-activity-when-pressed-back-android
                    on 2/9/2017. And from this
                    http://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
                    on 2/9/2017. */
                    Intent intent = new Intent();
                    intent.putExtra("record", (new Gson()).toJson(newRecord));
                    setResult(RESULT_OK, intent);
                    finish();
                }

            }
        });

        /**
         * Button to create new field.
         */
        newFieldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SizeBookEditRecordActivity.this,
                        SizeBookNewFieldActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        /**
         * Buttons on ListView items to edit fields.
         */
        oldFields.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

                // Get the field that was clicked on.
                Field field = (Field)oldFields.getAdapter().getItem(position);

                fieldEditPosition = position;

                Intent intent = new Intent(SizeBookEditRecordActivity.this,
                        SizeBookEditFieldActivity.class);

                // Pass record to EditFieldActivity.
                intent.putExtra("field", (new Gson()).toJson(field));
                setResult(RESULT_OK, intent);

                // Launch EditFieldActivity
                startActivityForResult(intent, 2);
            }
        });
        // Add view to our SizeBookApplication.
        SizeBook sb = SizeBookApplication.getSizeBook();
        sb.addView(this);
    }

    /**
     * Get the field back from the NewFieldActivity or EditFieldActivity.
     * @param requestCode Marks what activity is being returned from.
     * @param resultCode
     * @param data The field being passed back and forth between activities.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            // requestCode 1 means a new field has been created.
            if (resultCode == RESULT_OK) {
                String strField = data.getStringExtra("field");
                Gson gson = new Gson();
                GenericField newField = gson.fromJson(strField, GenericField.class);

                fields.add(newField);

                adapter.notifyDataSetChanged();
            }
        } else if (requestCode == 2) {
            // requestCode 2 means a field has been edited or deleted.
            if (resultCode == RESULT_OK) {

                String strNewField = data.getStringExtra("field");
                Gson gson = new Gson();
                GenericField newField = gson.fromJson(strNewField, GenericField.class);

                // If the user did not hit the Delete Field button, add the field in.
                if (!newField.getFieldName().equals("DELETE_FIELD") &&
                        newField.getMeasurement() != 6453634.856867) {
                    fields.add(newField);
                }

                // Remove the old measurement.
                fields.remove(fieldEditPosition);

                adapter.notifyDataSetChanged();
            }
        } else if (requestCode == 3) {
            // requestCode 3 means a field has been deleted.
            fields.remove(fieldEditPosition);
        }
    }

    /**
     * Loads variables from user input, converts them into the format used in the model SizeBook,
     * and returns it in the form of a Record.
     */
    public Record createEditedRecord() {
        // Load variables.
        EditText recordName = (EditText) findViewById(R.id.recordName);
        EditText recordComment = (EditText) findViewById(R.id.recordComment);

        // Convert variables.
        String name = recordName.getText().toString();
        String comment = recordComment.getText().toString();
        Date date = record.getDate();

        // Name is required to be entered.
        if (!name.isEmpty() && !name.trim().equals("")) {
            // Create new record, set its comment and fields.
            Record newRecord = new Record(name, date);
            newRecord.setComment(comment);

            newRecord.setFields(fields);
            return newRecord;
        } else {
            // If no name is entered, refuse to continue.
            recordName.setError("Name entry is required!");
            return null;
        }
    }

    /**
     * Generic update method from interface. This view doesn't display or deal with
     * any information from the model so there is nothing to update.
     */
    public void update(SizeBook sizeBook) {}

}
