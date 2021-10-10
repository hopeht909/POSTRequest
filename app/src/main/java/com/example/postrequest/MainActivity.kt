package com.example.postrequest

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val responseText = findViewById<View>(R.id.textView) as TextView
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Please wait")
        progressDialog.show()
        if (apiInterface != null) {
            apiInterface.getUser()?.enqueue(object : Callback<List<Users.UserDetails>> {
                override fun onResponse(
                    call: Call<List<Users.UserDetails>>,
                    response: Response<List<Users.UserDetails>>
                ) {
                    progressDialog.dismiss()
                    var stringToBePritined: String? = "";
                    for (User in response.body()!!) {
                        stringToBePritined =
                            stringToBePritined + User.name + "\n" + User.location + "\n" + "\n"
                    }
                    responseText.text = stringToBePritined
                }

                override fun onFailure(call: Call<List<Users.UserDetails>>, t: Throwable) {
                    //  onResult(null)
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "" + t.message, Toast.LENGTH_SHORT).show();
                }
            })
        }

    }

    fun addnew(view: android.view.View) {
        intent = Intent(applicationContext, NewUser::class.java)
        startActivity(intent)
    }
}