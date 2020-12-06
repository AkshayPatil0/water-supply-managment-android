package com.watersystem.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.watersystem.client.R;

public class ActivityNewCustomer extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    String[] bottle_types = { "1L", "10L", "15L", "20L", "40L"};
    String selected_type = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);
        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, bottle_types);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);

        final Button button = findViewById(R.id.btn_add);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText nameInput = (EditText) findViewById(R.id.name_input);
                EditText usernameInput = (EditText) findViewById(R.id.username_input);
                EditText priceInput = (EditText) findViewById(R.id.price_input);
                Integer price = Integer.parseInt(priceInput.getText().toString());
                Customer customer = new Customer(
                        usernameInput.getText().toString(),
                        nameInput.getText().toString(),
                        selected_type,
                        price, 0, 0, 0);

                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference customerRef = databaseRef.child("customers");
                customerRef.child(customer.getUsername()).setValue(customer);

                Toast.makeText(getApplicationContext(), "Customer Added Succesfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ActivityNewCustomer.this, ActivityMenu.class));
                // Code here executes on main thread after user presses button
            }
        });

    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        selected_type = bottle_types[position];
    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
