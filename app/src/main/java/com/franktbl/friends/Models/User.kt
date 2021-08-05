package com.franktbl.friends.Models

class User {
    private var id = ""
    private var sl = 0
    private var name = ""
    private var country = ""

    constructor( sl:Int, id:String, name: String, country: String) {
        this.sl = sl
        this.id = id
        this.name = name
        this.country = country
    }

    fun getSL():Int{
        return sl
    }
    fun getId():String{
        return id
    }

    fun getName():String{
        return name
    }

    fun getCountry():String{
        return country
    }


}