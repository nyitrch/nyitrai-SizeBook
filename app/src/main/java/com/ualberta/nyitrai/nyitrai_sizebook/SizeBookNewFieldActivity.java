package com.ualberta.nyitrai.nyitrai_sizebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

/**
 * Created by nyitrai on 2/10/2017.
 */

public class SizeBookNewFieldActivity extends Activity implements SView<SizeBook> {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newfield);

        Button createButton = (Button) findViewById(R.id.createButton);

        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                GenericField newField = createField();

                if (newField != null) {
                    /* Pass user entered record back to the previous activity using GSON and JSON.
                    I found out how to do this from
                    http://stackoverflow.com/questions/14292398/how-to-pass-data-from-2nd-activity-to-1st-activity-when-pressed-back-android
                    on 2/9/2017. And from this
                    http://stackoverflow.com/questions/2139134/how-to-send-an-object-from-one-android-activity-to-another-using-intents
                    on 2/9/2017. */
                    Intent intent = new Intent();
                    intent.putExtra("field", (new Gson()).toJson(newField));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        // Add view to our SizeBookApplication.
        SizeBook sb = SizeBookApplication.getSizeBook();
        sb.addView(this);
    }

    /**
     * Loads variables from user input, converts them into the Field format, and returns them.
     */
    public GenericField createField() {
        // Load variables.
        EditText fieldName = (EditText) findViewById(R.id.fieldName);
        EditText fieldMeasurement = (EditText) findViewById(R.id.fieldMeasurement);

        // Convert variables.
        String name = fieldName.getText().toString();
        String strMeasurement = fieldMeasurement.getText().toString();

        Double measurement;

        // If the measurement is not entered, set it to 0.0.
        if (strMeasurement.isEmpty()){
            measurement = 0.0;
        } else {
            measurement = Double.parseDouble(strMeasurement);
        }

        // Name is required to be entered.
        if (!name.isEmpty() && !name.trim().equals("")) {
            // Create new field.
            return new GenericField(name, measurement);
        } else {
            // If no name is entered, refuse to continue.
            fieldName.setError("Name entry is required!");
            return null;
        }
    }

    public void update(SizeBook sizeBook) {}
}
