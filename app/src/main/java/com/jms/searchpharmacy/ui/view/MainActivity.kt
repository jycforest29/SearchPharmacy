package com.jms.searchpharmacy.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.jms.searchpharmacy.R
import com.jms.searchpharmacy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val fragmentList = arrayOf(SearchFragment(), MapFragment())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupBottomNavigationViewPager()

        if(savedInstanceState == null) {
            //앱을 처음 열었을 때 지정해 줄 프래그먼트 화면
            binding.bottomNavigationView.selectedItemId = R.id.fragment_search
        }




    }

    private fun setupBottomNavigationViewPager() {

        binding.viewPager.adapter = object: FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getCount(): Int = fragmentList.size

            override fun getItem(position: Int): Fragment {
                return when(position){
                    0 -> SearchFragment()
                    1 -> MapFragment()
                    else -> SearchFragment()
                }
            }

        }
        binding.viewPager.offscreenPageLimit = fragmentList.size


        binding.viewPager.addOnPageChangeListener(
            object: ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {}

                override fun onPageSelected(position: Int) {
                    binding.bottomNavigationView.menu.getItem(position).isChecked = true
                }

                override fun onPageScrollStateChanged(state: Int) {}

            }
        )


        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.fragment_search -> {
                    binding.viewPager.currentItem = 0
                    true
                }
                R.id.fragment_map -> {
                    binding.viewPager.currentItem = 1
                    true
                }

                else -> false
            }

        }
    }
}