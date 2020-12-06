package com.watersystem.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.watersystem.client.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ActivityMenu extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findViewById(R.id.btn_customer_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(ActivityMenu.this, ActivityUserList.class);
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                DatabaseReference customersRef = database.child("customers");
                final ArrayList<String> customers = new ArrayList<String>();
                customersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                        Set custs = map.entrySet();

                        Iterator itr = custs.iterator();
                        while(itr.hasNext())
                        {
                            Map.Entry entry=(Map.Entry)itr.next();
                            customers.add(""+entry.getKey());
                        }

                        intent.putStringArrayListExtra("customers", customers);
                        startActivity(intent);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        findViewById(R.id.btn_new_customer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityMenu.this, ActivityNewCustomer.class));
            }
        });


        findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                new MyPreferences(ActivityMenu.this).setUserName("");
                startActivity(new Intent(ActivityMenu.this, ActivityMain.class));
                finish();
            }
        });
    }
}