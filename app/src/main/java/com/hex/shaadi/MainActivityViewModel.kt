package com.hex.shaadi

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hex.shaadi.daos.AppDatabase
import com.hex.shaadi.model.Profile
import com.hex.shaadi.networking.ProfileService
import com.hex.shaadi.repos.ProfileRepo
import com.hex.shaadi.ui.MatchesActionsInterface
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
        if (profiles.value != null) {
            // View recreated, data reload not needed
            return
        }

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

                        // Note: Currently not using LiveData from Room itself since it will emit
                        // twice, once on clearing and once on insertion. There are ways to counter
                        // that by adding a delay to emit only the latest state.
                        profileRepo.deleteAll()
                        profileRepo.insertAll(newProfiles)
                        profiles.value = newProfiles
                    }.onFailure {
                        val oldProfiles = profileRepo.getAll()

                        if (oldProfiles.isNotEmpty()) {
                            profiles.value = oldProfiles
                        } else {
                            errSnackbar.value = "No profiles found, please ensure you are connected to the internet"
                        }
                    }
                }
            } catch (t: Throwable) {
                t.rethrowIfCancellation()
                errSnackbar.value = "Unexpected error fetching profiles, please try again"
            }
        }
    }

    suspend fun setStatus(uuid: String, newStatus: Int) {
        // if caller scope is cancelled, this will still update the status in the db
        val job = viewModelScope.launch {
            profileRepo.updateStatus(uuid, newStatus)
        }
        job.join()
    }
}
