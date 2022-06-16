package com.jms.searchpharmacy.ui.view.home

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.jms.searchpharmacy.R
import com.jms.searchpharmacy.data.model.server.PharmacyLocation
import com.jms.searchpharmacy.databinding.FragmentHomeBinding
import com.jms.searchpharmacy.databinding.ItemInBriefBinding
import com.jms.searchpharmacy.ui.view.MainActivity
import com.jms.searchpharmacy.ui.viewmodel.MainViewModel
import com.jms.searchpharmacy.util.Constants.PERMISSION_REQUEST_CODE
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.util.FusedLocationSource
import java.util.jar.Manifest


class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MainViewModel by lazy {
        (activity as MainActivity).mainViewModel
    }

    private inner class Top5Adapter(val PLList: List<PharmacyLocation>)
        : RecyclerView.Adapter<Top5Adapter.Top5ViewHolder>() {

        inner class Top5ViewHolder(val itemBinding: ItemInBriefBinding)
            : RecyclerView.ViewHolder(itemBinding.root) {

            fun bind(pl: PharmacyLocation){
                itemBinding.apply {
                    roadNameAddr.text = pl.load_address
                    hospitalCnt.text = pl.hospital_count.toString()
                    pharmacyCnt.text = pl.pharmacy_count.toString()
                    ratio.text = String.format("%.2f",pl.hospital_per_pharmacy)
                    convStoreCnt.text = pl.convenience_count.toString()
                }
                itemView.setOnClickListener{
                    viewModel.registerPL(pl)
                    val action = BriefFragmentDirections.actionFragmentBriefToFragmentDetail(pl.index)
                    findNavController().navigate(action)
                }
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Top5ViewHolder {
            val itemBinding = ItemInBriefBinding.inflate(layoutInflater, parent, false)
            return Top5ViewHolder(itemBinding)
        }

        override fun onBindViewHolder(holder: Top5ViewHolder, position: Int) {
            holder.bind(PLList[position])
        }

        override fun getItemCount(): Int = PLList.size
    }

    private lateinit var locationManager: LocationManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)


        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onResume() {
        super.onResume()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeTextInfoSingleLine.isSelected = true
        binding.searchSubway.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToSelectSubwayFragment()
            findNavController().navigate(action)
        }
        binding.searchAddr.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToBriefFragment(null)
            findNavController().navigate(action)
        }

        binding.searchMyPlace.setOnClickListener{
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                onCheckPermission()
            } else {
                locationManager =
                    requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager



                val currentLocation: Location? =
                    locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                        ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                        ?: FusedLocationSource(this, PERMISSION_REQUEST_CODE).lastLocation


                currentLocation?.let {
                    onCheckInSeoul(currentLocation)
                }

                if(currentLocation == null) {
                    Toast.makeText(requireContext(), "GPS, 인터넷 연결을 확인해주세요", Toast.LENGTH_SHORT).show()
                }

            }
        }

        viewModel.fetchedPLsTop5List.observe(viewLifecycleOwner) {
            binding.top5RecyclerView.adapter = Top5Adapter(it)
            binding.top5RecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

    }

    //현재 위치가 서울인지 파악
    private fun onCheckInSeoul(currentLocation: Location) {
        viewModel.convertCoordsToAddr(LatLng(currentLocation))
        viewModel.regionNameLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show()
        }
    }


    private fun onCheckPermission() {
        if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(requireContext(), "위치 권한을 허용해야 사용 가능합니다", Toast.LENGTH_SHORT).show()

                ActivityCompat.requestPermissions(requireActivity(),arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION),PERMISSION_REQUEST_CODE)


            } else {

                ActivityCompat.requestPermissions(requireActivity(),arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION),PERMISSION_REQUEST_CODE)

            }

        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                    Toast.makeText(requireContext(), "위치 권한이 설정되었습니다", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "위치 권한이 취소되었습니다", Toast.LENGTH_SHORT).show()
                }
            }
            else -> { }
        }

    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}