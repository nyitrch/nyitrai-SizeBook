package com.ualberta.nyitrai.nyitrai_sizebook;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nyitrai on 2/5/2017.
 */

public class Record {
    private String name;
    private Date date;
    private String comment;

    public Record(String name) {
        this.name = name;
        this.date = new Date();
    }

    public Record(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() { return date; }
    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() { return comment; }
    public void setComment(String comment) {
        this.comment = comment;
    }

    protected ArrayList<Field> fields;

    public ArrayList<Field> getFields() {
        return fields;
    }

    public void newField(Field field) {
        fields.add(field);
    }

    @Override
    public String toString() {
        String text = "";
        for (Field field : fields) {
            text += field.getFieldName() + " : " + String.valueOf(field.getMeasurement()) + "\n";

        }
        return text;
    }

}
