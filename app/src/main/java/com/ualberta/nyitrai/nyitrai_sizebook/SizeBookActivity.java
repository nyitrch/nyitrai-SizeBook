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

    /**
     * This is called when the activity is first created.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        oldRecords = (ListView) findViewById(R.id.oldRecords);

        // New Record Button Pressed
        Button newRecordButton = (Button) findViewById(R.id.newRecordButton);
        newRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // User pressed button. Hey Controller, user told me he wants to add a new record.
                // Get records from controller.
                BookController bc = SizeBookApplication.getBookController();
                ArrayList<Record> records = bc.getRecords();

                Intent intent = new Intent(SizeBookActivity.this,
                        SizeBookNewRecordActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Called after onCreate() or onRestart(). That is, after the activity
     * comes into view for the first time, or again after being stopped.
     * <br><br>The basic function of onStart() here is to load the recordList form the file stored
     * on the Android device.
     */
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

        loadFromFile();
    }

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
            ArrayAdapter<Record> adapter = new ArrayAdapter<Record>(this,
                    R.layout.list_item, records);

            // Send records and adapter to controller.
            BookController bc = SizeBookApplication.getBookController();
            bc.setRecords(records);
            bc.setAdapter(adapter);

            // If file is not found, create new record list and new adapter.
        } catch (FileNotFoundException e) {

            ArrayList<Record> records = new ArrayList<Record>();
            ArrayAdapter<Record> adapter = new ArrayAdapter<Record>(this,
                    R.layout.list_item, records);

            // Send new empty records and adapter to controller.
            BookController bc = SizeBookApplication.getBookController();
            bc.setRecords(records);
            bc.setAdapter(adapter);
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

    }

}
