package com.watersystem.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.watersystem.client.R
import kotlinx.android.synthetic.main.activity_register.*

class ActivityRegister : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()
    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

//        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(Constants().users)
        btn_register.setOnClickListener {
            try{
                register()
            }catch (e: Throwable){
                //do something
            }
        }
    }

    private fun register(){

//        val name = edittext_name.text.toString()
        val username = edittext_username.text.toString()
        val password = edittext_password.text.toString()
        val confirmPassword = edittext_confirmPassword.text.toString()
        val uid = mAuth.uid


        if(isInformationValid(username, password, confirmPassword)){
            mAuth.createUserWithEmailAndPassword("$username@wsm.com", password).addOnCompleteListener(this, { task->
                if(task.isSuccessful){
//                    mDatabase.child(uid).child(Constants().name).setValue(name)
//                    mDatabase.child(uid).child(Constants().username).setValue(username)
//                    mDatabase.child(uid).child(Constants().password).setValue(password)
                    showToast("Registration successful")
                    MyPreferences(this).setUserName(username)
                    startActivity(Intent(this@ActivityRegister, ActivityMenu::class.java))
                    finish()
                }else{
                    showToast("Something Went Wrong")
                }
            })

        }else{
            return
        }
    }

    private fun isInformationValid(username: String, password: String, confirmPassword: String): Boolean{
        if(!username.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()){
            if(password == confirmPassword){
                if (!username.contains("#") && !username.contains(".") && !username.contains("$") && !username.contains("[") && !username.contains("]")) {
                    return true
                }else{
                    showToast("Username must not contain any special characters")
                    return false
                }
            }else{
                showToast("Password does not match")
                return false
            }
        }else{
            showToast("Please fill in all the fields")
            return false
        }
    }


    private fun showToast(msg: String){
        Toast.makeText(this, msg,Toast.LENGTH_LONG).show()
    }
}
