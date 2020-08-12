package com.hex.shaadi.model

import com.google.gson.annotations.SerializedName

class NetworkProfile {

    @SerializedName("gender")
    val gender: String = ""

    @SerializedName("name")
    val name = Name()

    @SerializedName("location")
    val location = Location()

    @SerializedName("login")
    val login = Login()

    @SerializedName("dob")
    val dob = DOB()

    @SerializedName("picture")
    val picture = Picture()
}

class Name {
    @SerializedName("first")
    val first = ""

    @SerializedName("last")
    val last = ""
}

class Location {
    @SerializedName("city")
    val city = ""

    @SerializedName("state")
    val state = ""
}

class Login {
    @SerializedName("uuid")
    val uuid: String = ""
}

class DOB {
    @SerializedName("age")
    val age: Int = 0
}

class Picture {
    // Using large for now as it is 128x128
    @SerializedName("large")
    val thumbnail: String = ""
}
