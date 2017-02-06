package com.ualberta.nyitrai.nyitrai_sizebook;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by nyitrai on 2/4/2017.
 */

public class SizeBook extends SModel<SView> {

    protected ArrayList<Record> records;
    protected ArrayAdapter<Record> adapter;

    public ArrayList<Record> getRecords() {
        return records;
    }
    public ArrayAdapter<Record> getAdapter() { return adapter; }

    public void newRecord(Record record) {
        records.add(record);
        adapter.notifyDataSetChanged();
        notifyViews();
    }

    public void deleteRecord(Record record) {
        records.remove(record);
        adapter.notifyDataSetChanged();
        notifyViews();
    }

    SizeBook() {
        super();
    }

}
