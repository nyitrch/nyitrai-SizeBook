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
    protected ArrayList<GenericField> fields;

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

    public ArrayList<GenericField> getFields() {
        return fields;
    }
    public void setFields(ArrayList<GenericField> newFields) { this.fields = newFields; }
    public void addField(GenericField field) {
        fields.add(field);
    }
    public void deleteField(GenericField field) { fields.remove(field); }

    @Override
    public String toString() {
        String text = "Name: " + this.getName()
                + "\nDate: " +  this.getDate().toString()
                + "\n\nComment: " + this.getComment();

        if (fields != null) {
            text += "\n\n";
            int i = 0;
            for (Field field : fields) {
                if (field.getFieldName().toLowerCase() == "bust" ||
                        field.getFieldName().toLowerCase() == "chest" ||
                        field.getFieldName().toLowerCase() == "waist" ||
                        field.getFieldName().toLowerCase() == "inseam") {

                    text += field.getFieldName() + ": "
                            + String.valueOf(field.getMeasurement()) + "\n";
                    i++;
                } else if (i < 5) {
                    text += field.getFieldName() + ": "
                            + String.valueOf(field.getMeasurement()) + "\n";
                    i++;
                }
            }
        }
        return text;
    }

}
