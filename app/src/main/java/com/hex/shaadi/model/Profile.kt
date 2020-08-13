package com.hex.shaadi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hex.shaadi.model.MatchStatus.Companion.UNKNOWN

@Entity(tableName = "profile_table")
data class Profile(

    // note, uuid needs to be assigned after deserialization
    // not using email as PK as there is a possibility that user can change it
    @PrimaryKey
    val uuid: String = "",

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "age")
    val age: Int = 0,

    @ColumnInfo(name = "gender")
    val gender: String = "",

    @ColumnInfo(name = "city")
    val city: String = "",

    @ColumnInfo(name = "state")
    val state: String = "",

    @ColumnInfo(name = "thumbnail")
    val thumbnail: String = "",

    @ColumnInfo(name = "index")
    val index : Int = 0,

    @ColumnInfo(name = "match_status")
    var matchStatus: Int = UNKNOWN
)
