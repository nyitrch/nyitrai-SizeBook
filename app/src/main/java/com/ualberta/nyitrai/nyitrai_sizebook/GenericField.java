package com.ualberta.nyitrai.nyitrai_sizebook;

/**
 * Created by nyitrai on 2/5/2017.
 */

public class GenericField implements Field {
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

    @Override
    public String toString() {
        return this.getFieldName() + ": " + this.getMeasurement();
    }

}
