package com.jms.searchpharmacy.ui.view.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jms.searchpharmacy.R
import com.jms.searchpharmacy.data.model.server.PharmacyLocation
import com.jms.searchpharmacy.databinding.FragmentBriefBinding
import com.jms.searchpharmacy.databinding.ItemInBriefBinding
import com.jms.searchpharmacy.databinding.ItemInBriefFieldNameBinding
import com.jms.searchpharmacy.ui.view.MainActivity
import com.jms.searchpharmacy.ui.viewmodel.MainViewModel
import java.io.LineNumberReader


private const val ITEM_FIELD_NAME = 0
private const val ITEM_ROW = 1

class BriefFragment : Fragment() {

    private var _binding : FragmentBriefBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<BriefFragmentArgs>()


    private val viewModel: MainViewModel by lazy {
        (activity as MainActivity).mainViewModel
    }

    private inner class BriefAdapter(val PLList: List<PharmacyLocation>)
        : RecyclerView.Adapter<BriefAdapter.BriefViewHolder>() {

            inner class BriefViewHolder(val itemBinding: ItemInBriefBinding): RecyclerView.ViewHolder(itemBinding.root) {


                fun bind(pl: PharmacyLocation) {
                    itemBinding.apply {
                        roadNameAddr.text = pl.load_address
                        hospitalCnt.text = pl.hospital_count.toString()
                        pharmacyCnt.text = pl.pharmacy_count.toString()
                        ratio.text = pl.hospital_per_pharmacy.toString()
                        convStoreCnt.text = pl.convenience_count.toString()
                    }

                    itemView.setOnClickListener{
                        val action = BriefFragmentDirections.actionFragmentBriefToFragmentDetail("서울 강남구 강남대로 370")
                        findNavController().navigate(action)
                    }

                }
            }



        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BriefViewHolder {
            val binding = ItemInBriefBinding.inflate(layoutInflater, parent, false)
            return BriefViewHolder(binding)

        }

        override fun onBindViewHolder(holder: BriefAdapter.BriefViewHolder, position: Int) {

            holder.bind(PLList[position])
            Log.d("TAG","몇번")


        }

        override fun getItemCount(): Int = PLList.size


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBriefBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textInputEditText.setText(args.dongName)

//        binding.briefInfoRecyclerView.adapter = BriefAdapter(listOf())
//        binding.briefInfoRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        binding.floatingActionButton.setOnClickListener{
//            val action = BriefFragmentDirections.actionFragmentBriefToFragmentDetail(pl.roadNameAddr)
//            findNavController().navigate(action)
        }
        viewModel.fetchedPLs.observe(viewLifecycleOwner) {

            //TODO{여기서 가져온게 null임 }
            for(i in it.indices) {
                Log.d("TAG","받아온정보: ${it[i].load_address}")
            }

            binding.briefInfoRecyclerView.adapter =BriefAdapter(it)
            binding.briefInfoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.fetchPLs(args.dongName ?: "")


    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}