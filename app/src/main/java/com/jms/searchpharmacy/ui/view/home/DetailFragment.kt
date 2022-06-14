package com.jms.searchpharmacy.ui.view.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
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
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.MarkerIcons


class DetailFragment : Fragment(), OnMapReadyCallback {

    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailFragmentArgs>()

    private val viewModel : MainViewModel by lazy {
        (activity as MainActivity).mainViewModel
    }


    private val latLngLiveData: MutableLiveData<LatLng> = MutableLiveData()
    private val detailFragmentList = arrayOf(DetailHospFragment(), DetailPharFragment(), DetailConvFragment())

    private val isExpandedLiveData : MutableLiveData<Boolean> = MutableLiveData(true)

    private val markerPharListLiveData: MutableLiveData<List<Marker>> = MutableLiveData()
    private val markerHopsListLiveData: MutableLiveData<List<Marker>> = MutableLiveData()
    private val markerConvListLiveData: MutableLiveData<List<Marker>> = MutableLiveData()



    private lateinit var naverMap: NaverMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment

        activity?.let {
            val fm = childFragmentManager
            val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
                ?: MapFragment.newInstance().also {
                    fm.beginTransaction()
                        .add(R.id.map, it)
                        .addToBackStack(null)
                        .commit()
                }
            mapFragment.getMapAsync(this)

        }



        return binding.root
    }

    private fun loadDetailList() {
        viewModel.apply {
            fetchConvList(args.primaryKey)
            fetchHospList(args.primaryKey)
            fetchPharList(args.primaryKey)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadDetailList()


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
            isExpandedLiveData.postValue(!isExpandedLiveData.value!!)
            //isExpanded = !isExpanded

        }
    }

    private fun requestMarkerInfo() {
        //여기서 마커리스트 만들어서 처리
        //TODO{마커리스트}
        //약국 마커
        /*
        val markerPhar = Marker()
        markerPhar.apply {
            icon = OverlayImage.fromResource(R.drawable.ic_phar_marker)
            width= 100
            height = 100
            position = it
            map = naverMap
        }

        //병원 마커
        val markerHosp = Marker()
        markerHosp.apply {
            icon = OverlayImage.fromResource(R.drawable.ic_hosp_marker)
            width= 100
            height = 100
            position = it
            map = naverMap
        }

        //편의점 마커
        val markerConv = Marker()
        markerHosp.apply {
            icon = OverlayImage.fromResource(R.drawable.ic_conv_marker)
            width= 100
            height = 100
            position = it
            map = naverMap
        }

         */

    }

    private fun setupUISettings() {

        // 옵저버
        latLngLiveData.observe(viewLifecycleOwner){
            val cameraUpdate = CameraUpdate.scrollTo(it)

            this.naverMap.moveCamera(cameraUpdate)


            //마커정보 요청하면
            requestMarkerInfo()
            //TODO{마커 정보 요청청}

        }

        isExpandedLiveData.observe(viewLifecycleOwner) {
            binding.viewPager2WithMap.isVisible = it
            if(it) {

                binding.toggleButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel)
                binding.toggleButton.setBackgroundResource(android.R.color.transparent)
                naverMap.setContentPadding(0,0,0,binding.contentLayoutInDetail.height)
            } else {


                binding.toggleButton.setImageResource(android.R.drawable.ic_menu_add)
                binding.toggleButton.setBackgroundResource(android.R.color.transparent)
                naverMap.setContentPadding(0,0,0,0)
            }
        }
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        setupUISettings()

        viewModel.searchResult.observe(viewLifecycleOwner){ response->
            val addresses: List<Addresse>? = response?.addresses

            addresses?.let {

                latLngLiveData.postValue(LatLng(addresses[0].y!!.toDouble(), addresses[0].x!!.toDouble()))

            }
        }

        //viewModel.searchGeoInfo(args.roadNameAddr?:"")


    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }



}