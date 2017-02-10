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

    public BookController(SizeBook sb) { this.sb = sb; }

    public ArrayList<Record> getRecords() { return sb.getRecords(); }
    public void setRecords(ArrayList<Record> records) { sb.setRecords(records); }

    public void createRecord(Record record) {
        sb.newRecord(record);
    }

    public void deleteRecord(Record record) {
        sb.deleteRecord(record);
    }
}
