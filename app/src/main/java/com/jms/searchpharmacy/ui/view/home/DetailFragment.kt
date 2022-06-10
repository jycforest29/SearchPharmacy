package com.jms.searchpharmacy.ui.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator

import com.jms.a20220602_navermap.data.model.Addresse
import com.jms.searchpharmacy.R
import com.jms.searchpharmacy.databinding.FragmentDetailBinding
import com.jms.searchpharmacy.ui.view.MainActivity
import com.jms.searchpharmacy.ui.viewmodel.MainViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*


class DetailFragment : Fragment(), OnMapReadyCallback {

    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailFragmentArgs>()

    private val viewModel : MainViewModel by lazy {
        (activity as MainActivity).mainViewModel
    }

    private val detailFragmentList = arrayOf(DetailHospFragment(), DetailPharFragment(), DetailConvFragment())

    private var isExpanded : Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment

        activity?.let {
            val fm = it.supportFragmentManager
            val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
                ?: MapFragment.newInstance().also {
                    fm.beginTransaction().add(R.id.map, it).commit()
                }
            mapFragment.getMapAsync(this)

        }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager2WithMap.adapter = object: FragmentStateAdapter(this){
            override fun getItemCount(): Int = detailFragmentList.size

            override fun createFragment(position: Int): Fragment {
                return detailFragmentList[position]
            }
        }

        TabLayoutMediator(binding.tabLayoutInDetail,binding.viewPager2WithMap) { tab, position ->

            when(position) {
                0 -> { //병원정보 디테일
                    tab.text="병원"
                }
                1 -> { //약국정보 디테일
                    tab.text="약국"
                }
                2 -> { //편의점 정보 디테일
                    tab.text="편의점"
                }
            }

        }.attach()

        binding.toggleButton.setOnClickListener {
            isExpanded = !isExpanded
            binding.viewPager2WithMap.isVisible = isExpanded
            if(isExpanded) {
                binding.toggleButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel)
                binding.toggleButton.setBackgroundResource(android.R.color.transparent)
            } else {
                binding.toggleButton.setImageResource(android.R.drawable.ic_menu_add)
                binding.toggleButton.setBackgroundResource(android.R.color.transparent)
            }
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        viewModel.searchResult.observe(viewLifecycleOwner){ response->
            val addresses: List<Addresse>? = response?.addresses
            Toast.makeText(requireContext(), "${addresses?.get(0)?.roadAddress}", Toast.LENGTH_SHORT).show()

            addresses?.let {
                val cameraUpdate = CameraUpdate.scrollTo(LatLng(addresses[0].y!!.toDouble(), addresses[0].x!!.toDouble()))
                    .animate(CameraAnimation.Fly)
                naverMap.moveCamera(cameraUpdate)
            }
        }

        viewModel.searchGeoInfo(args.roadNameAddr?:"")


    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }



}