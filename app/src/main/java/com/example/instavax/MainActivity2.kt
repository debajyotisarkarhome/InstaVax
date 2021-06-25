package com.example.instavax

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import java.lang.reflect.Constructor
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val pin:EditText=findViewById(R.id.pin_data)
        val date:EditText=findViewById(R.id.date_data)

        val c = Calendar.getInstance()
        val yearCal = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->
            var resStr:String = "" + dayOfMonth + "-" + (monthOfYear.toInt()+1).toString() + "-" + year
            date.setText(resStr)
        }, yearCal, month, day)





        fun closeKeyBoard() {
            val view = this.currentFocus
            if (view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        fun raiseSnackError(errorText: String){
            val layout: ConstraintLayout = findViewById(R.id.superParent)
            val snack: Snackbar= Snackbar.make(layout,errorText,Snackbar.LENGTH_LONG)
            snack.show()
        }

        fun searchandnext() {
            val queue = Volley.newRequestQueue(this)
            val dateFormatted = date.text.toString()
            dateFormatted.replace("/", "-")
            dateFormatted.replace(".", "-")
            val pinFinal: String = pin.text.toString()
            if (pinFinal.length != 6) {
                raiseSnackError("Please enter a valid 6 digit PIN code")
            } else {
                val url = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?pincode=" + pinFinal + "&date=" + dateFormatted
                val jsonobject = JsonObjectRequest(Request.Method.GET, url, null,
                        { response ->
                            val all = response.toString()
                            val intent = Intent(this, SearchActivity::class.java)
                            intent.putExtra("sessions", all)
                            startActivity(intent)
                        },
                        {
                            Log.d("error", "Please Check ")
                            raiseSnackError("Please Enter Valid PIN and Date 🙁")
                        })

                // Add the request to the RequestQueue.
                queue.add(jsonobject)
            }
        }

        date.setOnClickListener {
            dpd.show()
        }
        
        val searchButton: Button = findViewById(R.id.button)
        searchButton.setOnClickListener{
            closeKeyBoard()
            searchandnext()
        }
    }
}