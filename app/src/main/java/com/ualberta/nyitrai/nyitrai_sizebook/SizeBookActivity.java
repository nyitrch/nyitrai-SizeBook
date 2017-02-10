package com.ualberta.nyitrai.nyitrai_sizebook;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nyitrai on 2/5/2017.
 */

public class SizeBookActivity extends Activity implements SView<SizeBook> {

    /** File where SizeBook info is saved on device. */
    private static final String FILENAME = "nyitrai-SizeBook.sav";

    private ListView oldRecords;
    private ArrayAdapter<Record> adapter;
    private ArrayList<Record> records;

    private int recordEditPosition;

    /**
     * This is called when the activity is first created.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        oldRecords = (ListView) findViewById(R.id.oldRecords);
        Button newRecordButton = (Button) findViewById(R.id.newRecordButton);

        newRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launches activity to get user input for new record.
                Intent intent = new Intent(SizeBookActivity.this,
                        SizeBookNewRecordActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        oldRecords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                recordEditPosition = position;

                // Get the record that was clicked on.
                Record record = (Record)oldRecords.getAdapter().getItem(position);

                Intent intent = new Intent(SizeBookActivity.this,
                        SizeBookEditRecordActivity.class);

                // Pass record to EditRecordActivity.
                intent.putExtra("record", (new Gson()).toJson(record));
                setResult(RESULT_OK, intent);

                // Launch EditRecordActivity
                startActivityForResult(intent, 2);
            }
        });

        // Add view to our SizeBookApplication.
        SizeBook sb = SizeBookApplication.getSizeBook();
        sb.addView(this);
    }

    /**
     * Takes data from SizeBookNewRecordActivity when it finishes. Code taken from
     * http://stackoverflow.com/questions/14292398/how-to-pass-data-from-2nd-activity-to-1st-activity-when-pressed-back-android
     * @param requestCode Determines which activity the result is coming from. 1 = NewRecordActivity
     *                    2 = EditRecordActivity
     * @param resultCode
     * @param data The actual JSON string of the Record from SizeBookNewRecordActivity.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            // requestCode 1 means a new record has been created.
            if (resultCode == RESULT_OK) {
                String strRecord = data.getStringExtra("record");
                Gson gson = new Gson();
                Record newRecord = gson.fromJson(strRecord, Record.class);

                records.add(newRecord);

                adapter.notifyDataSetChanged();
                saveInFile();
            }
        } else if (requestCode == 2) {
            // requestCode 2 means a record has been edited or deleted.
            if (resultCode == RESULT_OK) {

                String strNewRecord = data.getStringExtra("record");
                Gson gson = new Gson();
                Record newRecord = gson.fromJson(strNewRecord, Record.class);

                // If the user did not hit the Delete Record button, add the record in.
                if (!newRecord.getName().equals("DELETE_RECORD") &&
                        !newRecord.getComment().equals("DELETE_RECORD") &&
                        newRecord.getDate() != new Date(6453634)) {
                    records.add(newRecord);
                }

                // Remove the old record.
                records.remove(recordEditPosition);

                adapter.notifyDataSetChanged();
                saveInFile();
            }
        }
    }

    /**
     * Called after onCreate() or onRestart(). That is, after the activity
     * comes into view for the first time, or again after being stopped.
     * <br><br>The basic function of onStart() here is to load the recordList form the file stored
     * on the Android device.
     */
    @Override
    protected void onStart() {
        super.onStart();
        BookController bc = SizeBookApplication.getBookController();

        // If a new record has been created, create it with the controller.
        /*if (newRecordStatus) {
            Gson gson = new Gson();
            Record newRecord = gson.fromJson(strRecord, Record.class);
            bc.createRecord(newRecord);
            adapter.notifyDataSetChanged();
            saveInFile();
            newRecordStatus = false;
        }*/

        loadFromFile();

        // Get records and adapter from controller and setup adapter.
        records = bc.getRecords();
        adapter = new ArrayAdapter<Record>(this, R.layout.list_item, records);
        oldRecords.setAdapter(adapter);
    }

    /**
     * Load old records from file with GSON. Initially looks for a file on the Android
     * device. If one is found, it reads records from it. If one is not found, it creates
     * an empty ArrayList. </br></br>
     *
     * After either grabbing the old records from the file, or creating an empty list,
     * sends the ArrayList of Records to the controller.
     */
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            /* The following lines were taken on Jan 24, 2017 18:19 from:
            http://stackoverflow.com/
            questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            */
            Type listType = new TypeToken<ArrayList<Record>>(){}.getType();
            records = gson.fromJson(in, listType);

            // Send old records that were just read in to the controller.
            BookController bc = SizeBookApplication.getBookController();
            bc.setRecords(records);

            // If file is not found, create new record list.
        } catch (FileNotFoundException e) {
            // Send new empty records to controller.
            records = new ArrayList<Record>();

            BookController bc = SizeBookApplication.getBookController();
            bc.setRecords(records);
        }

    }

    private void saveInFile() {
        try {
            BookController bc = SizeBookApplication.getBookController();
            ArrayList<Record> records = bc.getRecords();

            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(records, out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO: Handle the exception later.
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO: Handle the exception later.
            throw new RuntimeException();
        }
    }

    public void update(SizeBook sizeBook) {
        loadFromFile();
    }

}
