package com.example.new_dr_prescription;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Medicine_Details extends AppCompatActivity {
    private static final int  requestCode= 123;

    String nm, mb, ag, prb, gen,selectedItem;

    ArrayList<MedicineModel> arrMedicine=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_details);

        nm = getIntent().getStringExtra("name");
        mb = getIntent().getStringExtra("mobile");
        ag = getIntent().getStringExtra("age");
        prb = getIntent().getStringExtra("problem");
        gen = getIntent().getStringExtra("gender");

        Spinner spinner=findViewById(R.id.spin);

        String[] items = {"Time", "Morn-Noon-Night", "Morn-Night","Noon-Night","Morn","Noon","Night"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle item selection
                selectedItem = parentView.getItemAtPosition(position).toString();
                Toast.makeText(Medicine_Details.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(Medicine_Details.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            }
        });


        RecyclerView recyclerView=findViewById(R.id.recyclerContact);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        EditText medname=findViewById(R.id.medname);
        EditText qunatity=findViewById(R.id.medqnty);

        Button add=findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                    arrMedicine.add(new MedicineModel(medname.getText().toString(),qunatity.getText().toString(),selectedItem));
                    medname.setText("");
                    qunatity.setText("");


            }});

        RecyclerMedicineAdapter adapter1=new RecyclerMedicineAdapter(this,arrMedicine);
        recyclerView.setAdapter(adapter1);



        Button p = findViewById(R.id.prescribe);
        p.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (nm == null || mb == null || ag == null || prb == null || gen == null) {
                    Toast.makeText(Medicine_Details.this, "Please fill in all patient details", Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuilder messageBuilder = new StringBuilder();
                messageBuilder.append("Patient Name: ").append(nm).append("\n")
                        .append("Gender: ").append(gen).append("\n")
                        .append("Age: ").append(ag).append("\n")
                        .append("Problem: ").append(prb).append("\n")
                        .append("Medicines:").append("\n");
                 int i=1;
                for (MedicineModel medicine : arrMedicine) {
                    messageBuilder.append(i+")").append(medicine.name).append(" (")
                            .append("Quantity: ").append(medicine.number).append(", ")
                            .append("Time: ").append(medicine.time).append(")").append("\n"); // Append medicine details
                    i++;
                }

                String message = messageBuilder.toString();

                if (ContextCompat.checkSelfPermission(Medicine_Details.this, Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted, request it
                    ActivityCompat.requestPermissions(Medicine_Details.this, new String[]{Manifest.permission.SEND_SMS}, requestCode);
                } else {
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(mb, "5110001", message, null, null);
                        Toast.makeText(Medicine_Details.this, "SMS sent successfully", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(Medicine_Details.this, "Failed to send SMS: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        });

    }


}
