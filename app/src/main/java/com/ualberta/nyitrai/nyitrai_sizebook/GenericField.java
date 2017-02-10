package com.ualberta.nyitrai.nyitrai_sizebook;

/**
 * Created by nyitrai on 2/5/2017.
 */

/**
 * GenericFields are what SizeBook uses to store information on a measurement that a user
 * has entered.
 * GenericFields have a text fieldName and a decimal measurement.
 */
public class GenericField {
    private double measurement;
    private String fieldName;

    public GenericField(String fieldName, double measurement) {
        this.measurement = measurement;
        this.fieldName = fieldName;
    }

    public double getMeasurement() {
        return measurement;
    }
    public void setMeasurement(double measurement) {
        this.measurement = measurement;
    }

    public String getFieldName() {
        return fieldName;
    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Printing of the fieldName and the measurement in one.
     * @return "fieldName": measurement
     */
    @Override
    public String toString() {
        return this.getFieldName() + ": " + this.getMeasurement();
    }

}
