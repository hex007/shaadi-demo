package com.hex.shaadi.model

import androidx.room.ColumnInfo

data class MatchStatus(

    @ColumnInfo(name = "uuid")
    val uuid: String = "",

    @ColumnInfo(name = "match_status")
    var matchStatus: Int = UNKNOWN
) {
    companion object {
        // Note: Do not change values as they are persisted in db
        const val UNKNOWN = 0
        const val ACCEPTED = 1
        const val DECLINED = 2

    }
}
