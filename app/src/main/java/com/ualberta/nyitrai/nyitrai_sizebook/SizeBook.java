package com.ualberta.nyitrai.nyitrai_sizebook;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by nyitrai on 2/4/2017.
 */

public class SizeBook extends SModel<SView> {

    protected ArrayList<Record> records;
    public ArrayList<Record> getRecords() {
        return records;
    }
    public void setRecords(ArrayList<Record> newRecords) { this.records = newRecords; }

    public void newRecord(Record record) {
        records.add(record);
        notifyViews();
    }

    public void deleteRecord(Record record) {
        records.remove(record);
        notifyViews();
    }

    SizeBook() {
        super();
    }

}
