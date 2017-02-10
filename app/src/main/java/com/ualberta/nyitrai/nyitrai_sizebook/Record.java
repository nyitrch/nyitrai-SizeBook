package com.ualberta.nyitrai.nyitrai_sizebook;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nyitrai on 2/5/2017.
 */

/**
 * Records are what SizeBook uses to store information on a person.
 * Records have a name, date, comment, and an ArrayList of GenericFields attached.
 * GenericFields describe measurements.
 */
public class Record {
    private String name;
    private Date date;
    private String comment;
    private ArrayList<GenericField> fields;

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

    /**
     * Creates a nice string for printing the Record into a ListView.
     * @return Pretty string.
     */
    @Override
    public String toString() {
        // Always show the name and date.
        String text = "Name: " + this.getName()
                + "\nDate: " +  this.getDate().toString();

        // If a comment exists, show it.
        if (!this.getComment().isEmpty()) {
            text += "\nComment: " + this.getComment();
        }

        // Nice printing of fields.
        if (fields != null) {
            text += "\n\n";
            // i is used to track how many fields we have printed. We only want a max of 5.
            int i = 0;
            for (GenericField field : fields) {
                // If the field has bust, chest, waist, or inseam in it, print it out.
                if (field.getFieldName().toLowerCase().contains("bust") ||
                        field.getFieldName().toLowerCase().contains("chest") ||
                        field.getFieldName().toLowerCase().contains("waist") ||
                        field.getFieldName().toLowerCase().contains("inseam")) {

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
