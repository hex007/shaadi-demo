package com.hex.shaadi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hex.shaadi.model.Profile
import com.hex.shaadi.networking.ProfileService
import com.hex.shaadi.utils.rethrowIfCancellation
import com.hex.shaadi.utils.withProgress
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    val profiles = MutableLiveData<List<Profile>>()
    val errSnackbar = MutableLiveData<String>()
    val loading = MutableLiveData<Boolean>()

    fun fetchData(maxProfileCount: Int = 10) {
        viewModelScope.launch {
            try {
                withProgress(loading) {
                    val newProfiles = ProfileService.getProfiles(maxProfileCount)
                    // todo : Store profiles in DB, ensure user selection/preference not overridden
                    // todo : Fetch all profiles in db, sorted, and emmit.
                    profiles.value = newProfiles
                }
            } catch (t: Throwable) {
                t.rethrowIfCancellation()
                t.printStackTrace() // todo, takeout
                errSnackbar.value = "Error fetching, showing offline profiles"
            }
        }
    }
}
