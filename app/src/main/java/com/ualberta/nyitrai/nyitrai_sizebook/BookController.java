package com.ualberta.nyitrai.nyitrai_sizebook;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nyitrai on 2/5/2017.
 */

public class BookController implements SController {
    SizeBook sb = null;

    // File where SizeBook info is saved on device.
    private static final String FILENAME = "nyitrai-SizeBook.sav";

    public BookController(SizeBook sb) { this.sb = sb; }

    public ArrayList<Record> getRecords() { return sb.getRecords(); }
    public void setRecords(ArrayList<Record> records) { sb.records = records; }

    public ArrayAdapter<Record> getAdapter() { return sb.getAdapter(); }
    public void setAdapter(ArrayAdapter<Record> adapter) { sb.adapter = adapter; }

    public void createRecord(String name, Date date, String comment) {

        // Create the record with the given name, date, and comment.
        Record record = new Record(name, date);
        record.setComment(comment);

        sb.newRecord(record);
    }

    public void deleteRecord(String name, Date date, String comment) {

        // Delete the given record.
        Record record = new Record(name, date);
        record.setComment(comment);

        sb.deleteRecord(record);
    }
}
