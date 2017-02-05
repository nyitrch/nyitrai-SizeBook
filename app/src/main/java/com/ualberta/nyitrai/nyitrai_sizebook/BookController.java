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

    private ListView oldRecordList;

    private ArrayList<Record> recordList;
    private ArrayAdapter<Record> adapter;

    /** File where SizeBook info is saved on device. */
    private static final String FILENAME = "SizeBook.sav";

    public BookController(SizeBook sb) { this.sb = sb; }

    public ArrayList<Record> getRecords() { return sb.getRecords(); }


    public void createRecord(String name, Date date, String comment) {

        // Create the record with the given name, date, and comment.
        Record record = new Record(name, date);
        record.setComment(comment);

        recordList.add(record);
        adapter.notifyDataSetChanged();

    }
}
