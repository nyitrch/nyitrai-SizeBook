package com.ualberta.nyitrai.nyitrai_sizebook;

import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nyitrai on 2/5/2017.
 */

public interface SController {

    public ArrayList<Record> getRecords();
    public void setRecords(ArrayList<Record> records);

    public void createRecord(String name, Date date, String comment);
    public void deleteRecord(String name, Date date, String comment);
}
