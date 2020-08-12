package com.hex.shaadi

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hex.shaadi.daos.AppDatabase
import com.hex.shaadi.model.Profile
import com.hex.shaadi.networking.ProfileService
import com.hex.shaadi.repos.ProfileRepo
import com.hex.shaadi.utils.rethrowIfCancellation
import com.hex.shaadi.utils.withProgress
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    val profiles = MutableLiveData<List<Profile>>()
    val errSnackbar = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

    private val profileRepo: ProfileRepo

    init {
        val profileDao = AppDatabase.getDatabase(application).profileDao()
        profileRepo = ProfileRepo(profileDao)
    }

    fun fetchData(maxProfileCount: Int = 10) {
        viewModelScope.launch {
            try {
                withProgress(loading) {
                    kotlin.runCatching {
                        // order maintained to prefer early exit on network call failure
                        val newProfiles = ProfileService.getProfiles(maxProfileCount)

                        val matchStatuses = profileRepo.getWithMatchStatus()
                        newProfiles.forEach { profile ->
                            matchStatuses[profile.uuid]?.also { profile.matchStatus = it }
                        }

                        profileRepo.deleteAll()
                        profileRepo.insertAll(newProfiles)
                        profiles.value = newProfiles
                    }.onFailure {
                        val oldProfiles = profileRepo.getAll()
                        profiles.value = oldProfiles
                    }
                }
            } catch (t: Throwable) {
                t.rethrowIfCancellation()
                errSnackbar.value = "Error fetching, showing offline profiles"
            }
        }
    }
}
