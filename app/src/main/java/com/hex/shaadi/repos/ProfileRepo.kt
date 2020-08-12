package com.hex.shaadi.repos

import com.hex.shaadi.daos.ProfileDao
import com.hex.shaadi.model.Profile

class ProfileRepo(private val profileDao: ProfileDao) {

    suspend fun getAll() = profileDao.getAll()

    suspend fun getWithMatchStatus() = profileDao.getWithMatchStatus()
        .associateBy({ it.uuid }, { it.matchStatus })

    suspend fun updateStatus(uuid: String, newStatus: Int) =
        profileDao.updateStatus(uuid, newStatus)

    suspend fun insertAll(profiles: List<Profile>) = profileDao.insertAll(profiles)

    suspend fun deleteAll() = profileDao.deleteAll()

}
