package com.example.instavax

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import org.json.JSONObject
import org.w3c.dom.Text


class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val apiData = intent.getStringExtra("sessions")
        val apiJSon: JSONObject = JSONObject(apiData!!)
        val appointmentList = apiJSon.getJSONArray("sessions")
        //Log.d("Rawdatatype", "${appointmentList!!::class.simpleName}")
        //Log.d("rawData",appointmentList.toString())
        if (appointmentList.length() != 0) {
            for (i in 0..appointmentList.length() - 1) {
                var tempVal = appointmentList.getJSONObject(i)  //Get data by Index
                val linParen: LinearLayout = findViewById(R.id.linParent)   //Declare Parent View
                /*  Start of Card Creation */
                var newCard: CardView = CardView(this) //add Card
                newCard.setCardElevation(10.0F)
                newCard.setContentPadding(30, 30, 30, 35)
                newCard.setCardBackgroundColor(Color.GRAY)
                newCard.setUseCompatPadding(true)
                newCard.radius = 25F

                /*  Start of LinLayout Creation */

                var linChild: LinearLayout = LinearLayout(this)
                linChild.orientation = LinearLayout.VERTICAL


                var centreNameText: TextView = TextView(this)
                var addressText: TextView = TextView(this)
                var dose1Text: TextView = TextView(this)
                var dose2Text: TextView = TextView(this)
                var age: TextView = TextView(this)

                centreNameText.text = "Centre Name : " + tempVal.getString("name")
                centreNameText.textSize = 15F
                addressText.text = "Address : " + tempVal.getString("address")

                dose1Text.text = "Dose 1 Available : " + tempVal.getString("available_capacity_dose1")

                dose2Text.text = "Dose 2 Available : " + tempVal.getString("available_capacity_dose2")


                age.text = "Age : " + tempVal.getString("min_age_limit") + "+"


                linChild.addView(centreNameText)
                linChild.addView(addressText)
                linChild.addView(age)
                linChild.addView(dose1Text)
                linChild.addView(dose2Text)

                newCard.addView(linChild)

                linParen.addView(newCard)
            }
        }
        else{
            val linParen: LinearLayout = findViewById(R.id.linParent)
            var noSessionMsg: TextView = TextView(this)
            noSessionMsg.gravity=Gravity.CENTER
            noSessionMsg.text="No Appointments Found!! Try Using a Different Date?"
            noSessionMsg.textSize=20F
            linParen.addView(noSessionMsg)


        }
    }
}
