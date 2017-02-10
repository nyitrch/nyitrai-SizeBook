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

/**
 * Handles the editing of an existing field. This includes changing the elements of the field,
 * or deleting it. Changes are not saved until the "Save Changes" button is pressed.<br></br>
 *
 * Called from SizeBookEditRecordActivity.
 */
public class SizeBookEditFieldActivity extends Activity implements SView<SizeBook> {

    /** Called when activity is first created. Initialization is done here instead of onStart
     * because this view doesn't save anything to the model. It just passes upwards back
     * to other views.
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editfield);

        // Initialization of Buttons and EditTexts.
        Button saveButton = (Button) findViewById(R.id.saveButton);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        EditText fieldName = (EditText) findViewById(R.id.fieldName);
        EditText fieldMeasurement = (EditText) findViewById(R.id.fieldMeasurement);

        // Get field that is passed through from SizeBookEditRecordActivity.
        String strField = getIntent().getStringExtra("field");
        Gson gson = new Gson();
        final GenericField oldField = gson.fromJson(strField, GenericField.class);

        // Sets the current values of the field to be displayed in the EditTexts.
        fieldName.setText(oldField.getFieldName());
        fieldMeasurement.setText(String.valueOf(oldField.getMeasurement()));

        // Save Button button press. Sends changes up to the SizeBookEditRecordActivity.
        saveButton.setOnClickListener(new View.OnClickListener() {
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

        // Delete Button button press. Sends delete notification to SizeBookEditRecordActivity.
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenericField deleteField = createDeleteField();
                Intent intent = new Intent();
                intent.putExtra("field", (new Gson()).toJson(deleteField));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    /** Loads variables from user input, converts them into the Field format, and returns them. */
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
    /** Creates a GenericField that notifies the above activity of delete request. I
     * know this isn't the best way to do this. */
    public GenericField createDeleteField() {
        return new GenericField("DELETE_FIELD", 6453634.856867);
    }

    /**
     * Generic update method from View class. This view doesn't display or use any information
     * from the model so there is nothing to update.
     */
    public void update(SizeBook sizeBook) {}
}
