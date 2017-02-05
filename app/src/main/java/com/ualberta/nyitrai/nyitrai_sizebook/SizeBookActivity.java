package com.ualberta.nyitrai.nyitrai_sizebook;

import android.app.Activity;
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
    private static final String FILENAME = "SizeBook.sav";

    private ListView oldRecordList;

    private ArrayList<Record> recordList;
    private ArrayAdapter<Record> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button newRecordButton = (Button) findViewById(R.id.newRecordButton);
        oldRecordList = (ListView) findViewById(R.id.oldRecordsList);

        newRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SizeBookActivity.this,
                        SizeBookNewRecordActivity.class);
                startActivity(intent);
                BookController bc = SizeBookApplication.getBookController();
                
            }
        });
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
            recordList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            recordList = new ArrayList<Record>();
        }

    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(recordList, out);
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
