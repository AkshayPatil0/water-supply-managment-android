package com.watersystem.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.google.firebase.database.*
import com.watersystem.client.R
import kotlinx.android.synthetic.main.activity_user_options.*
import org.json.JSONObject

class ActivityUserOptions : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_options)

        database = FirebaseDatabase.getInstance().reference

        var username: String = intent.extras["username"] as String
        var customerRef = database.child("customers").child(username);
        var customerJSON = intent.extras["customer"]
        var customer = JSONObject(customerJSON.toString())

//        Log.i("c", ""+obj["username"])


//        val customerListener = object : ChildEventListener {
//            override fun onChildChanged(dataSnapshot: DataSnapshot?, p1: String?) {
//                // Get Post object and use the values to update the UI
//                val customer= dataSnapshot.value;
//                Log.i("customer", customer.toString())
//                // ...
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Getting Post failed, log a message
////                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
//
//            }
//        }
//
//        customerRef.addChildEventListener(customerListener)
        var bottle_count = customer["bottle_count"] as Int
        var refill_count = customer["refill_count"] as Int
        var bill = customer["bill_amount"] as Int
        var bottle_price = customer["bottle_price"] as Int

        bottle_type_text.text = customer["bottle_type"].toString()
        bottle_count_text.text = bottle_count.toString()
        refill_count_text.text = refill_count.toString()
        bill_remaining_amount.text = bill.toString()

        add_bottle.setOnClickListener{
            bottle_count++
            customerRef.child("bottle_count").setValue(bottle_count)
            bottle_count_text.text = bottle_count.toString()
        }

        remove_bottle.setOnClickListener{
            bottle_count--
            customerRef.child("bottle_count").setValue(bottle_count)
            bottle_count_text.text = bottle_count.toString()
        }

        add_refills.setOnClickListener{
            refill_count++
            bill += bottle_price
            customerRef.child("refill_count").setValue(bottle_count)
            customerRef.child("bill_amount").setValue(bill)
            refill_count_text.text = refill_count.toString()
            bill_remaining_amount.text = bill.toString()
        }

        remove_refills.setOnClickListener{
            refill_count--
            bill -= bottle_price
            customerRef.child("refill_count").setValue(bottle_count)
            customerRef.child("bill_amount").setValue(bill)
            refill_count_text.text = refill_count.toString()
            bill_remaining_amount.text = bill.toString()
        }

        bill_paid_btn.setOnClickListener{
            bill -= bill_text_input.text.toString().toInt()
            bill_text_input.text?.clear()
            customerRef.child("bill_amount").setValue(bill)
            bill_remaining_amount.text = bill.toString()
            bill_text_layout.clearFocus()
        }
    }
}