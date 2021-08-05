package com.franktbl.friends.Adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.franktbl.friends.DetailsActivity
import com.franktbl.friends.Models.User
import com.franktbl.friends.R
import kotlinx.android.synthetic.main.list_item.view.*

class UserAdapter(private var mContext: Context, private var userList: List<User>)
    :RecyclerView.Adapter<UserAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        var user = userList[position]

        // ==== set data to list_item for views
        holder.sl_item.text = user.getSL().toString()
        holder.name.text = user.getName()
        holder.country.text = user.getCountry()

        // ======= tap on itemview then open DetailsActivity with user details
        holder.itemView.setOnClickListener{
            var intent = Intent(mContext, DetailsActivity::class.java)
            intent.putExtra("seedId", user.getId())
            mContext.startActivity(intent)
        }

    }

    inner class ViewHolder(@NonNull view: View): RecyclerView.ViewHolder(view){
        var sl_item: TextView = view.sl_item
        var name: TextView = view.name
        var country: TextView = view.country
    }

}