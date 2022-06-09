package com.jms.searchpharmacy.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.jms.searchpharmacy.R
import com.jms.searchpharmacy.databinding.ActivityMainBinding
import com.jms.searchpharmacy.ui.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    lateinit var mainViewModel: MainViewModel

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setupJetpackNavigation()

    }


    private fun setupJetpackNavigation() {
        val host = supportFragmentManager.findFragmentById(R.id.search_pharmacy_host_fragment) as NavHostFragment
        navController = host.navController
        binding.bottomNavigationView.setupWithNavController(navController)

    }
}