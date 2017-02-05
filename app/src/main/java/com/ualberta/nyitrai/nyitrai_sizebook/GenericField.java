package com.ualberta.nyitrai.nyitrai_sizebook;

/**
 * Created by nyitrai on 2/5/2017.
 */

public class GenericField implements Field {
    private float measurement;
    private String fieldName;

    public GenericField(String fieldName, float measurement) {
        this.measurement = measurement;
        this.fieldName = fieldName;
    }

    public float getMeasurement() {
        return measurement;
    }
    public void setMeasurement(float measurement) {
        this.measurement = measurement;
    }

    public String getFieldName() {
        return fieldName;
    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

}
