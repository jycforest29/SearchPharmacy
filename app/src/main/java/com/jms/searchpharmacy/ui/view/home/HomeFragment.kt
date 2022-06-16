package com.jms.searchpharmacy.ui.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jms.searchpharmacy.R
import com.jms.searchpharmacy.data.model.server.Hospital
import com.jms.searchpharmacy.data.model.server.PharmacyLocation
import com.jms.searchpharmacy.databinding.FragmentHomeBinding
import com.jms.searchpharmacy.databinding.ItemDetailHospBinding
import com.jms.searchpharmacy.databinding.ItemInBriefBinding
import com.jms.searchpharmacy.ui.view.MainActivity
import com.jms.searchpharmacy.ui.viewmodel.MainViewModel


class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by lazy {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        // Inflate the layout for this fragment
        return binding.root
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
//        binding.top5RecyclerView.adapter = Top5Adapter(it)
        binding.top5RecyclerView.layoutManager = LinearLayoutManager(requireContext())

    }



    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}