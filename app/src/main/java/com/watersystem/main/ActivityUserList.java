package com.watersystem.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.watersystem.client.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityUserList extends AppCompatActivity {

    ListView lv;
    DatabaseReference database, customerRef;
    List<String> customers = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        lv = (ListView) findViewById(R.id.user_list);

        customers.add("");
        Intent intent = getIntent();
        customers = intent.getStringArrayListExtra("customers");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, customers);

        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final Intent intent = new Intent(ActivityUserList.this, ActivityUserOptions.class);
                database = FirebaseDatabase.getInstance().getReference();
                customerRef = database.child("customers").child(""+customers.get(i));
                customerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Object customer = dataSnapshot.getValue();
                        intent.putExtra("customer", customer.toString());
                        intent.putExtra("username", customers.get(i));
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
    }

}