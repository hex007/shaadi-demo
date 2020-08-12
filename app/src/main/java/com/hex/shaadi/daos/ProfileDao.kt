package com.hex.shaadi.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hex.shaadi.model.MatchStatus
import com.hex.shaadi.model.Profile

@Dao
interface ProfileDao {

    @Query("SELECT * FROM profile_table ORDER BY `index` ASC")
    suspend fun getAll(): List<Profile>

    @Query("SELECT uuid, match_status FROM profile_table WHERE match_status != 0 ORDER BY `index` ASC")
    suspend fun getWithMatchStatus(): List<MatchStatus>

    @Query("UPDATE profile_table SET match_status = :newStatus WHERE uuid = :uuid ")
    suspend fun updateStatus(uuid: String, newStatus: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(profiles: List<Profile>)

    @Query("DELETE FROM profile_table")
    suspend fun deleteAll()
}

// Note: If the structure and requirements are different such that it is optimal to store match_status
// in a different table, then that would be ideal. eg. If profiles are generic entities and application
// has added features like messaging stored in different tables and match_status was closer to user
// being somewhat similar to a favorite/bookmarked profile. Since this is a simple application,
// assumptions are made to minimize scope of the project.
