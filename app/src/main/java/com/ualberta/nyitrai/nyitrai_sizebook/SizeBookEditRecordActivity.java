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


/**
 * Created by nyitrai on 2/10/2017.
 */

public class SizeBookEditRecordActivity extends Activity implements SView<SizeBook> {

    private ListView oldFields;
    private Record record;
    private ArrayAdapter<Field> adapter;
    private ArrayList<Field> fields;

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
            fields = new ArrayList<Field>();
        }
        adapter = new ArrayAdapter<Field>(this, R.layout.list_item, fields);
        oldFields.setAdapter(adapter);

        recordComment.setText(record.getComment());
        recordName.setText(record.getName());
        recordDate.setText(record.getDate().toString());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Record newRecord = createRecord();

            }
        });

        newFieldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SizeBookEditRecordActivity.this,
                        SizeBookNewFieldActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        oldFields.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

                // Get the field that was clicked on.
                Field field = (Field)oldFields.getAdapter().getItem(position);

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
     * Get the field back from the NewFieldActivity.
     * @param requestCode
     * @param resultCode
     * @param data
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
            // requestCode 2 means a field has been edited.
            if (resultCode == RESULT_OK) {
                String strNewField = data.getStringExtra("newField");
                String strOldField = data.getStringExtra("oldField");
                Gson gson = new Gson();
                GenericField newField = gson.fromJson(strNewField, GenericField.class);
                GenericField oldField = gson.fromJson(strOldField, GenericField.class);

                fields.remove(oldField);
                fields.add(newField);

                adapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * Generic update method from interface. This view doesn't display or deal with
     * any information from the model so there is nothing to update.
     */
    public void update(SizeBook sizeBook) {}

}
