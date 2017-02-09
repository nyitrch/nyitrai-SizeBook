package com.ualberta.nyitrai.nyitrai_sizebook;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

/**
 * Created by nyitrai on 2/5/2017.
 */

public class SizeBookActivity extends Activity implements SView<SizeBook> {

    /** File where SizeBook info is saved on device. */
    private static final String FILENAME = "nyitrai-SizeBook.sav";

    private ListView oldRecords;
    private ArrayAdapter<Record> adapter;
    private ArrayList<Record> records;

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
        // Add view to our SizeBookApplication.
        SizeBook sb = SizeBookApplication.getSizeBook();
        sb.addView(this);
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

        loadFromFile();

        // Get records from controller and setup adapter.
        BookController bc = SizeBookApplication.getBookController();
        records = bc.getRecords();
        adapter = bc.getAdapter();

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

            // The following lines were taken on Jan 24, 2017 18:19 from:
            // http://stackoverflow.com/
            // questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            Type listType = new TypeToken<ArrayList<Record>>(){}.getType();
            ArrayList<Record> records = gson.fromJson(in, listType);

            // Send old records to controller.
            BookController bc = SizeBookApplication.getBookController();
            bc.setRecords(records);
            adapter = new ArrayAdapter<Record>(this, R.layout.list_item, records);
            bc.setAdapter(adapter);

            // If file is not found, create new record list and new adapter.
        } catch (FileNotFoundException e) {

            ArrayList<Record> records = new ArrayList<Record>();

            // Send new empty records to controller.
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
