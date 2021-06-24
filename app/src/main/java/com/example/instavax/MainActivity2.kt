package com.example.instavax

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val pin:EditText=findViewById(R.id.pin_data)
        val date:EditText=findViewById(R.id.date_data)

        fun searchandnext(){
            val queue = Volley.newRequestQueue(this)
            val dateFormatted = date.text.toString()
            dateFormatted.replace("/","-")
            val url = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?pincode="+pin.text.toString()+"&date="+dateFormatted
            val jsonobject = JsonObjectRequest(Request.Method.GET, url, null,
                { response ->
                    val all = response.toString()
                    val intent = Intent(this, SearchActivity::class.java)
                    intent.putExtra("sessions",all)
                    startActivity(intent)
            },
                { Log.d("error", "That didn't work!") })

            // Add the request to the RequestQueue.
            queue.add(jsonobject)
        }


        
        val searchButton: Button = findViewById(R.id.button)
        searchButton.setOnClickListener{
            searchandnext()
        }
    }
}