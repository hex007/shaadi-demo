package com.hex.shaadi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.hex.shaadi.utils.subscribe
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        subscribe(viewModel.loading) { progress.isVisible = it }
        subscribe(viewModel.profiles) { /* todo */ }
        subscribe(viewModel.errSnackbar) { showErrSnackbar(it) }

        viewModel.fetchData(10)
    }

    private fun showErrSnackbar(message: String) {
        Snackbar.make(activity_content, message, Snackbar.LENGTH_SHORT).show()
    }
}
