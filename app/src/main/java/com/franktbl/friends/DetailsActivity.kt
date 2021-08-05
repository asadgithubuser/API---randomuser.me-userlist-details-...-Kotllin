package com.franktbl.friends

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_details.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class DetailsActivity : AppCompatActivity() {
    private var requestQueue: RequestQueue? = null
    private var userid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        //===== set actionbar title
        var actionBar = getSupportActionBar()
        if(actionBar != null){
            actionBar.setTitle("User Details")
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        //===== assign initialized variables
        userid = intent.getStringExtra("seedId").toString()
        requestQueue = Volley.newRequestQueue(this)

        var url = "https://randomuser.me/api/?seed="+userid
        val request = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                response ->
            try{
                var jsonarray = response.getJSONArray("results")
                var userJObj = jsonarray.getJSONObject(0)

                // ==== call function for user details
                 getUserDetailsData(userJObj)

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener { error -> error.printStackTrace() })
        requestQueue?.add(request)
    }

    private fun getUserDetailsData(userJObj: JSONObject?) {
        var locations = userJObj!!.getJSONObject("location")
        var country = locations.getString("country")
        var street = locations.getJSONObject("street").getString("number")+" / "+locations.getJSONObject("street").getString("name")
        var city_state = locations.getString("city")+", "+locations.getString("state")

        var cellphone = userJObj.getString("cell")

        var email = userJObj.getString("email")
        var userName = userJObj.getJSONObject("name")
        var fullName = userName.getString("title")+" "+userName.getString("first")+" "+
                userName.getString("last")

        //======= set data to text fields
        name_txt.setText(fullName)
        cellphone_txt.setText("Phone: "+cellphone)
        email_txt.setText("Email: "+email)
        street_name_txt.setText(street)
        city_state_txt.setText(city_state)
        country_txt.setText(country)


        //======= send mail intent
        sendMailBtn.setOnClickListener {
            var emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.setType("message/rfc822");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, email_txt.text)
            emailIntent.setData(Uri.parse("mailto:"+email_txt.text))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email Subject")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Mail from FrankTBL")

            startActivity(Intent.createChooser(emailIntent, "Send Mail"))
        }
    }
}