package com.vc.findpairs.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.vc.findpairs.R
import com.vc.findpairs.databinding.ActivityMainBinding
import com.vc.findpairs.presentation.menuscreen.MenuFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<MenuFragment>(R.id.fragment_container)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}