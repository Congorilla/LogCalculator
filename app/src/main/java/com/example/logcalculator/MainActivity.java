package com.example.logcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.Format;

public class MainActivity extends AppCompatActivity {
    private int unitswitch;
    private double densitypermoisture;
    private double drydensity;
    private EditText diameter1input;
    private EditText diameter2input;
    private EditText lengthinput;
    private EditText moistureinput;
    private String volumeunit;
    private String weightunit;
    private TextView resultoutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        diameter1input = (EditText)findViewById(R.id.diameter1);
        diameter2input = (EditText)findViewById(R.id.diameter2);
        lengthinput = (EditText)findViewById(R.id.length);
        moistureinput = (EditText)findViewById(R.id.water);
        resultoutput = findViewById(R.id.result);
        densitypermoisture = 0;
        drydensity = 0;
        unitswitch = 0;
        volumeunit = "NaN";
        weightunit = "NaN";
    }
    public void SIOn(View v){
    unitswitch = 1;
    diameter1input.setHint("Diameter 1 (m)");
    diameter2input.setHint("Diameter 2 (m)");
    lengthinput.setHint("Length (m)");
    moistureinput.setHint("Moisture Content (%)");
    resultoutput.setText("Volume (m^3)\nWeight (kg)");
    volumeunit = "m^3";
    weightunit = "kg";
    }

    public void ImpOn(View v){
    unitswitch = -1;
    diameter1input.setHint("Diameter 1 (inch)");
    diameter2input.setHint("Diameter 2 (inch)");
    lengthinput.setHint("Length (ft)");
    moistureinput.setHint("Moisture Content (%)");
    resultoutput.setText("Volume (ft^3)\nWeight (lb)");
    volumeunit = "ft^3";
    weightunit = "lb";
    }

    public void oak(View v){
        densitypermoisture = 3.8;
        drydensity = 597;
    }

    public void pine(View v){
        densitypermoisture = 2;
        drydensity = 425;
    }

    public void cherry(View v){
        densitypermoisture = 2;
        drydensity = 521;
    }

    public void ash(View v){
        densitypermoisture = 2.6;
        drydensity = 589;
    }

    public void calculate(View v){
        if(unitswitch==0)
            resultoutput.setText("Please Select a Unit System!");
        else if(densitypermoisture==0)
            resultoutput.setText("Please Select a Type of Tree!");
        else{
            double diameter1 = Double.parseDouble(diameter1input.getText().toString());
            double diameter2 = Double.parseDouble(diameter2input.getText().toString());
            if(unitswitch==-1) {
                diameter1*=0.0833333333;
                diameter2*=0.0833333333;
                densitypermoisture*=0.062428;
                drydensity*=0.062428;
            }
            double length = Double.parseDouble(lengthinput.getText().toString());
            double moisture = Double.parseDouble(moistureinput.getText().toString());
            double volume = (3.14159265359*length*(diameter1*diameter1+diameter2*diameter2+diameter1*diameter2))/3;
            double weight = volume*(drydensity+densitypermoisture*moisture);
            DecimalFormat onedeciplace = new DecimalFormat("#.#");
            resultoutput.setText("Volume: "+onedeciplace.format(volume)+" "+volumeunit+"\n"+"Weight: "+onedeciplace.format(weight)+" "+weightunit);
            }
        }
}