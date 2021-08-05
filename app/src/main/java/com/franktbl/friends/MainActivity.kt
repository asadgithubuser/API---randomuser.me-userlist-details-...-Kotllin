package com.franktbl.friends

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.franktbl.friends.Adapters.UserAdapter
import com.franktbl.friends.Data.Data
import com.franktbl.friends.Models.User
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException
import java.util.*

class MainActivity : AppCompatActivity() {
    private var usersList: ArrayList<User>? = null
    private var requestQueue: RequestQueue? = null
    private var userAdapter: UserAdapter? = null
    private var dataClass: Data? = null
    private var seedids: ArrayList<String>? = null
    val indx = 0
    var jsonArrayData: JSONArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //== assign initialized variables
        requestQueue = Volley.newRequestQueue(this)
        dataClass = Data()
        usersList = ArrayList()
        listRecyclerView.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter(this, usersList!!)
        listRecyclerView.adapter = userAdapter

        // ===== retrive 10 users from rendomuser.me
        for(i in 0..9) {
            val request = JsonObjectRequest(Request.Method.GET, dataClass!!.url, null, Response.Listener {
                response ->
                try {
                    var seeid = response.getJSONObject("info").getString("seed")

                    jsonArrayData = response.getJSONArray("results")

                    var userJObj = jsonArrayData!!.getJSONObject(indx)
                    var locations = userJObj.getJSONObject("location")
                    var country = locations.getString("country")
                    var userName = userJObj.getJSONObject("name")
                    var fullName = userName.getString("title") + " " + userName.getString("first") + " " +
                            userName.getString("last")

                    usersList!!.add(User(i+1, seeid, fullName, country))

                    userAdapter!!.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error ->
                error.printStackTrace()
            })
            requestQueue?.add(request)
        }

    }

    //====== onSave and orRestore for ViewModel data handle for do not lose data when screen rotate.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("REQUEST_JSON_RESULT", jsonArrayData.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        try {
            var backData = savedInstanceState.getStringArrayList("REQUEST_JSON_RESULT")
            jsonArrayData = JSONArray(backData)
            jsonArrayData!!.getJSONObject(indx)
        } catch (e: JSONException) {
            e.printStackTrace()
        }


    }

}