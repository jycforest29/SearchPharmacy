package com.jms.searchpharmacy.ui.view.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator

import com.jms.a20220602_navermap.data.model.Addresse
import com.jms.searchpharmacy.R
import com.jms.searchpharmacy.data.model.server.PharmacyLocation
import com.jms.searchpharmacy.databinding.FragmentDetailBinding
import com.jms.searchpharmacy.ui.view.MainActivity
import com.jms.searchpharmacy.ui.viewmodel.MainViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.MarkerIcons
import kotlinx.coroutines.processNextEventInCurrentThread
import kotlin.properties.Delegates


class DetailFragment : Fragment(), OnMapReadyCallback {

    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailFragmentArgs>()

    private val viewModel : MainViewModel by lazy {
        (activity as MainActivity).mainViewModel
    }
    private lateinit var currentPL : PharmacyLocation

    private val detailFragmentList = arrayOf(DetailHospFragment(), DetailPharFragment(), DetailConvFragment())

    private val isExpandedLiveData : MutableLiveData<Boolean> = MutableLiveData(true)

    private var isExpanded: Boolean = true

    private var paddingHeightExpanded: Int = 0
    private var paddingHeight: Int = 0

    private lateinit var naverMap: NaverMap

    private val isFavorite get() = viewModel.isFavoritePL.value ?: false

    private var dbDelayTime = System.currentTimeMillis()

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


        viewModel.curretnPL.observe(viewLifecycleOwner) {
            currentPL = it
            viewModel.checkPLExists(it)
        }
        viewModel.isFavoritePL.observe(viewLifecycleOwner) {
            if(it) {
                binding.addFavoriteBtn.setColorFilter(resources.getColor(android.R.color.transparent))
            } else {
                binding.addFavoriteBtn.setColorFilter(resources.getColor(R.color.lightGrey))
            }
        }

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

        }

        binding.addFavoriteBtn.setOnClickListener {
            // 디비 작업 딜레이 1초
            if(System.currentTimeMillis()-dbDelayTime >= 1000) {
                Log.d("TAG","확인: $isFavorite")
                if (isFavorite) {
                    // 삭제
                    viewModel.deletePharLocationRegFavorite(currentPL)
                    Toast.makeText(requireContext(), "삭제", Toast.LENGTH_SHORT).show()
                    binding.addFavoriteBtn.setColorFilter(resources.getColor(R.color.lightGrey))

                } else {
                    // 추가
                    viewModel.savePharLocationRegFavorite(currentPL)
                    Toast.makeText(requireContext(), "저장", Toast.LENGTH_SHORT).show()
                    binding.addFavoriteBtn.setColorFilter(resources.getColor(android.R.color.transparent))
                }

            }
        }


    }


    private fun setupUISettings() {

        paddingHeightExpanded = binding.contentLayoutInDetail.height
        paddingHeight = binding.toggleButton.height + 50
        binding.toggleButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel)
        binding.toggleButton.setBackgroundResource(android.R.color.transparent)
        naverMap.setContentPadding(0,0,0,binding.contentLayoutInDetail.height)

        binding.toggleButton.setOnClickListener {


            isExpanded = !isExpanded
            binding.viewPager2WithMap.isVisible = isExpanded
            if(isExpanded) {
                binding.toggleButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel)
                binding.toggleButton.setBackgroundResource(android.R.color.transparent)
                naverMap.setContentPadding(0,0,0,paddingHeightExpanded)
            } else {
                binding.toggleButton.setImageResource(android.R.drawable.ic_menu_add)
                binding.toggleButton.setBackgroundResource(android.R.color.transparent)
                naverMap.setContentPadding(0,0,0,paddingHeight)
            }
        }

    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        this.naverMap.uiSettings.apply{
            isLocationButtonEnabled = true
            isCompassEnabled = true
        }

        setupUISettings()


        viewModel.searchPhar.observe(viewLifecycleOwner){ response->
            val addresses: List<Addresse>? = response?.addresses

            addresses?.let {

                    for(i in it.indices) {
                        val markerPhar = Marker()
                        markerPhar.apply {
                            icon = OverlayImage.fromResource(R.drawable.ic_phar_marker)
                            width= 100
                            height = 100
                            position = LatLng(it[i].y!!.toDouble(), it[i].x!!.toDouble())
                            map = naverMap
                        }
                    }

            }
        }
        viewModel.searchConv.observe(viewLifecycleOwner){ response->
            val addresses: List<Addresse>? = response?.addresses

            addresses?.let {
                for(i in it.indices) {
                    val markerConv = Marker()
                    markerConv.apply {
                        icon = OverlayImage.fromResource(R.drawable.ic_conv_marker)
                        width= 100
                        height = 100
                        position = LatLng(it[i].y!!.toDouble(), it[i].x!!.toDouble())
                        map = naverMap
                    }
                }
            }
        }
        viewModel.searchHosp.observe(viewLifecycleOwner){ response->
            val addresses: List<Addresse>? = response?.addresses

            addresses?.let { list ->
                    for(i in list.indices) {
                        val markerHosp = Marker()
                        markerHosp.apply {
                            icon = OverlayImage.fromResource(R.drawable.ic_hosp_marker)
                            width= 100
                            height = 100
                            position = LatLng(list[i].y!!.toDouble(), list[i].x!!.toDouble())
                            map = naverMap

                        }

                    }
                list[0].x?.let{
                    val cameraUpdate = CameraUpdate.scrollTo(LatLng(list[0].y!!.toDouble(), list[0].x!!.toDouble()))
                    naverMap.moveCamera(cameraUpdate)
                }

                view?.invalidate()
            }
        }




    }

    override fun onDestroyView() {
        _binding = null

        super.onDestroyView()
    }



}