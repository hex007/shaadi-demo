package com.hex.shaadi.model

import androidx.room.ColumnInfo

data class MatchStatus(

    @ColumnInfo(name = "uuid")
    val uuid: String = "",

    @ColumnInfo(name = "match_status")
    var matchStatus: Int = 0
)
