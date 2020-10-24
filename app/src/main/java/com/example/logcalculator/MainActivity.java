package com.example.logcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseRegistrar;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.Format;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference ref;
    private static final String TAG = MainActivity.class.getName();
    private int unitswitch;
    private double densitypermoisture;
    private double drydensity;
    private EditText diameter1input;
    private EditText diameter2input;
    private EditText lengthinput;
    private EditText moistureinput;
    private EditText woodspecieinput;
    private String volumeunit;
    private String weightunit;
    private TextView resultoutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ref = FirebaseDatabase.getInstance().getReference("message");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        diameter1input = (EditText) findViewById(R.id.diameter1);
        diameter2input = (EditText) findViewById(R.id.diameter2);
        lengthinput = (EditText) findViewById(R.id.length);
        moistureinput = (EditText) findViewById(R.id.water);
        woodspecieinput = (EditText) findViewById(R.id.woodspecie);
        resultoutput = findViewById(R.id.result);
        densitypermoisture = 0;
        drydensity = 0;
        unitswitch = 0;
        volumeunit = "NaN";
        weightunit = "NaN";
    }

    public void SIOn(View v) {
        unitswitch = 1;
        diameter1input.setHint("Diameter 1 (m)");
        diameter2input.setHint("Diameter 2 (m)");
        lengthinput.setHint("Length (m)");
        moistureinput.setHint("Moisture Content (%)");
        woodspecieinput.setHint("Wood Type");
        resultoutput.setText("Volume (m^3)\nWeight (kg)");
        volumeunit = "m^3";
        weightunit = "kg";
    }

    public void ImpOn(View v) {
        unitswitch = -1;
        diameter1input.setHint("Diameter 1 (inch)");
        diameter2input.setHint("Diameter 2 (inch)");
        lengthinput.setHint("Length (ft)");
        moistureinput.setHint("Moisture Content (%)");
        woodspecieinput.setHint("Wood Type");
        resultoutput.setText("Volume (ft^3)\nWeight (lb)");
        volumeunit = "ft^3";
        weightunit = "lb";
    }

    public void calculate(View v) {
        if (unitswitch == 0)
            resultoutput.setText("Please Select a Unit System!");
        else {
            String woodspecie = woodspecieinput.getText().toString();
            int woodspecievalid = 1;

            if (woodspecie.equalsIgnoreCase("red oak")) {
                densitypermoisture = 3.8;
                drydensity = 597;
            } else if (woodspecie.equalsIgnoreCase("lodgepole pine")) {
                densitypermoisture = 2;
                drydensity = 425;
            } else if (woodspecie.equalsIgnoreCase("black cherry")) {
                densitypermoisture = 2;
                drydensity = 521;
            } else if (woodspecie.equalsIgnoreCase("green ash")) {
                densitypermoisture = 2.6;
                drydensity = 589;
            } else if (woodspecie.equalsIgnoreCase("black walnut")) {
                densitypermoisture = 3.8;
                drydensity = 533;
            } else
                woodspecievalid = -1;

            if (woodspecievalid == 1) {
                double diameter1 = Double.parseDouble(diameter1input.getText().toString());
                double diameter2 = Double.parseDouble(diameter2input.getText().toString());
                if (unitswitch == -1) {
                    diameter1 *= 0.0833333333;
                    diameter2 *= 0.0833333333;
                    densitypermoisture *= 0.062428;
                    drydensity *= 0.062428;
                }
                double length = Double.parseDouble(lengthinput.getText().toString());
                double moisture = Double.parseDouble(moistureinput.getText().toString());
                double volume = (3.14159265359 * length * (diameter1 * diameter1 + diameter2 * diameter2 + diameter1 * diameter2)) / 3;
                double weight = volume * (drydensity + densitypermoisture * moisture);
                DecimalFormat onedeciplace = new DecimalFormat("#.#");
                resultoutput.setText("Volume: " + onedeciplace.format(volume) + " " + volumeunit + "\n" + "Weight: " + onedeciplace.format(weight) + " " + weightunit);
                ref.setValue(resultoutput.getText().toString());
            } else
                resultoutput.setText("Sorry, we cannot find the type of wood you want");
        }
    }
}
