package com.example.postrequest

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class updateUser : AppCompatActivity() {

    lateinit var userID :EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_update_user)

        val name = findViewById<View>(R.id.editTextName) as EditText
        userID = findViewById(R.id.editTextID)

        val location = findViewById<View>(R.id.editTextLo2) as EditText
        val updateBtn = findViewById<View>(R.id.buttonUp) as Button
        val deleteeBtn = findViewById<View>(R.id.buttonDel) as Button

        updateBtn.setOnClickListener {

            val f = Users.UserDetails(
                name.text.toString(), location.text.toString(), userID.text.toString().toInt()
            )

            addSingleuser(f, onResult = {
                name.setText("")
                location.setText("")
                Toast.makeText(applicationContext, "Update Success!", Toast.LENGTH_SHORT).show();
            })
        }
        deleteeBtn.setOnClickListener {
            val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
            val progressDialog = ProgressDialog(this@updateUser)
            progressDialog.setMessage("Please wait")
            progressDialog.show()

            if (apiInterface != null) {
                apiInterface.deleteUser(userID.text.toString().toInt()).enqueue(object : Callback<Void> {
                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {


                        progressDialog.dismiss()
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {

                        Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss()

                    }
                })
            }
        }
    }

    private fun addSingleuser(f: Users.UserDetails, onResult: () -> Unit) {

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this@updateUser)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        if (apiInterface != null) {
            apiInterface.updateUser(userID.text.toString().toInt(),f).enqueue(object : Callback<Users.UserDetails> {
                override fun onResponse(
                    call: Call<Users.UserDetails>,
                    response: Response<Users.UserDetails>
                ) {

                    onResult()
                    progressDialog.dismiss()
                }

                override fun onFailure(call: Call<Users.UserDetails>, t: Throwable) {
                    onResult()
                    Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()

                }
            })
        }
    }
    fun viewusers(view: android.view.View) {
        intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }


}