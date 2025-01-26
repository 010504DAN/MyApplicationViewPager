package com.example.myapplicationviewpager

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myapplicationviewpager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
/*
        binding.bottomMenu.setupWithNavController(navController)
*/
        val pref = PreferenceHelper()
        pref.unit(this)
        if (!pref.isOnBoardShown){
            navController.navigate(R.id.pagerItemFragment)
        }

        /*navController.addOnDestinationChangedListener(){ _, destination,_ ->
            if (destination.id == R.id.pagerItemFragment){
                binding.bottomMenu.visibility = View.GONE
            } else{
                binding.bottomMenu.visibility = View.VISIBLE
            }
        }*/
    }
}