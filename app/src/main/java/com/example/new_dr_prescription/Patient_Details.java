package com.example.new_dr_prescription;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Patient_Details extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details);
        Spinner spinner = findViewById(R.id.spin);
        EditText p_name=findViewById(R.id.patient_name);
        EditText mob=findViewById(R.id.mob_no);
        EditText age=findViewById(R.id.age);
        EditText prblm=findViewById(R.id.problem);
        Button proceed=findViewById(R.id.proceed);

        String[] items = {"Gender", "Male", "Female","Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle item selection
                String selectedItem = parentView.getItemAtPosition(position).toString();
                Toast.makeText(Patient_Details.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(Patient_Details.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = p_name.getText().toString();
                String mobile = mob.getText().toString();
                String ageStr = age.getText().toString();
                String problem = prblm.getText().toString();
                String gender = spinner.getSelectedItem().toString();

                Intent intent = new Intent(Patient_Details.this, Medicine_Details.class);
                intent.putExtra("name", name);
                intent.putExtra("mobile", mobile);
                intent.putExtra("age", ageStr);
                intent.putExtra("problem", problem);
                intent.putExtra("gender", gender);
                startActivity(intent);
            }
        });

    }
}