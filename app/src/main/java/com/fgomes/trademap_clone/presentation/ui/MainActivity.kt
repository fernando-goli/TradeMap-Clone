package com.fgomes.trademap_clone.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.fgomes.trademap_clone.R
import com.fgomes.trademap_clone.databinding.ActivityMainBinding
import com.fgomes.trademap_clone.presentation.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setupNavController()

        mainViewModel.consumirAcoes()
    }

    private fun setupNavController() {
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.loginFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.toolbar.setNavigationOnClickListener {
                val popped = navController.popBackStack()
                if (!popped) {
                    finish()
                }
            }
            when (destination.id) {
                R.id.loginFragment -> {
                    esconderActionBar()
                }
                else -> {
                    mostrarActionBar()
                }
            }
        }
    }

    fun esconderActionBar() {
        supportActionBar?.hide()
    }

    fun mostrarActionBar() {
        supportActionBar?.show()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavHostFragment.findNavController(findViewById(R.id.nav_host_fragment)).navigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.pararCosumirAcoes()
    }
}