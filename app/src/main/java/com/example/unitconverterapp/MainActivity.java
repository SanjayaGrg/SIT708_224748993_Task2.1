package com.example.unitconverterapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner category, spinnerFrom, spinnerTo;
    private EditText inputValue;
    private Button convertButton;
    private TextView resultText;

    private String[] cate = {"Length", "Weight", "Temperature"};
    private String[] lengthUnits = {"Inch", "Foot", "Yard", "Mile", "CM", "KM"};
    private String[] weightUnits = {"Pound", "Ounce", "Ton", "KG", "G"};
    private String[] tempUnits = {"Celsius", "Fahrenheit", "Kelvin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        category = findViewById(R.id.category);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        inputValue = findViewById(R.id.inputValue);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        // Set up Category Spinner
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cate);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(categoryAdapter);

        // Default category: Length
        setupSpinners(lengthUnits);

        // Change unit options based on category selection
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // Length
                        setupSpinners(lengthUnits);
                        break;
                    case 1: // Weight
                        setupSpinners(weightUnits);
                        break;
                    case 2: // Temperature
                        setupSpinners(tempUnits);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Default to Length if nothing is selected
                setupSpinners(lengthUnits);
            }
        });

        // Set Click Listener
        convertButton.setOnClickListener(view -> convertUnits());
    }

    // Function to set up Spinners
    private void setupSpinners(String[] unitArray) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, unitArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);
    }

    // Function to convert units
    private void convertUnits() {
        String sourceUnit = spinnerFrom.getSelectedItem().toString();
        String targetUnit = spinnerTo.getSelectedItem().toString();
        String inputVal = inputValue.getText().toString();

        // Validation: Ensure input is not empty
        if (inputVal.isEmpty()) {
            Toast.makeText(this, "Enter a value to convert!", Toast.LENGTH_SHORT).show();
            return;
        }

        double value = Double.parseDouble(inputVal);

        // Prevent converting the same unit
        if (sourceUnit.equals(targetUnit)) {
            resultText.setText("Converted Value: " + value + " " + targetUnit);
            return;
        }

        double convertedValue = performConversion(sourceUnit, targetUnit, value);
        resultText.setText("Converted Value: " + convertedValue + " " + targetUnit);
    }

    // Function to perform conversion logic
    private double performConversion(String source, String target, double value) {
        // Length Conversion
        if (source.equals("Inch") && target.equals("CM")) return value * 2.54;
        if (source.equals("Foot") && target.equals("CM")) return value * 30.48;
        if (source.equals("Yard") && target.equals("CM")) return value * 91.44;
        if (source.equals("Mile") && target.equals("KM")) return value * 1.60934;

        if (source.equals("CM") && target.equals("Inch")) return value / 2.54;
        if (source.equals("CM") && target.equals("Foot")) return value / 30.48;
        if (source.equals("CM") && target.equals("Yard")) return value / 91.44;
        if (source.equals("KM") && target.equals("Mile")) return value / 1.60934;

        // Weight Conversion
        if (source.equals("Pound") && target.equals("KG")) return value * 0.453592;
        if (source.equals("Ounce") && target.equals("G")) return value * 28.3495;
        if (source.equals("Ton") && target.equals("KG")) return value * 907.185;

        if (source.equals("KG") && target.equals("Pound")) return value / 0.453592;
        if (source.equals("G") && target.equals("Ounce")) return value / 28.3495;
        if (source.equals("KG") && target.equals("Ton")) return value / 907.185;

        // Temperature Conversion
        if (source.equals("Celsius") && target.equals("Fahrenheit")) return (value * 1.8) + 32;
        if (source.equals("Fahrenheit") && target.equals("Celsius")) return (value - 32) / 1.8;
        if (source.equals("Celsius") && target.equals("Kelvin")) return value + 273.15;
        if (source.equals("Kelvin") && target.equals("Celsius")) return value - 273.15;

        return value; // Default (shouldn't happen)
    }
}
