package com.jms.searchpharmacy.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.jms.searchpharmacy.R
import com.jms.searchpharmacy.databinding.ActivityMainBinding
import com.jms.searchpharmacy.repository.NaverMapSearchRepository
import com.jms.searchpharmacy.ui.viewmodel.MainViewModel
import com.jms.searchpharmacy.ui.viewmodel.MainViewModelFactory


class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    val mainViewModel: MainViewModel by lazy {
        val naverMapSearchRepository = NaverMapSearchRepository()
        val factory = MainViewModelFactory(naverMapSearchRepository)
        ViewModelProvider(this, factory)[MainViewModel::class.java]

    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupJetpackNavigation()

    }


    private fun setupJetpackNavigation() {
        val host = supportFragmentManager.findFragmentById(R.id.search_pharmacy_host_fragment) as NavHostFragment
        navController = host.navController
        binding.bottomNavigationView.setupWithNavController(navController)

    }
}