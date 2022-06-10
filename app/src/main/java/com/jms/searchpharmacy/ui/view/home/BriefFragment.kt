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
import com.jms.searchpharmacy.data.model.stations.PharmacyLocation
import com.jms.searchpharmacy.databinding.FragmentBriefBinding
import com.jms.searchpharmacy.databinding.ItemInBriefBinding
import com.jms.searchpharmacy.databinding.ItemInBriefFieldNameBinding

private const val ITEM_FIELD_NAME = 0
private const val ITEM_ROW = 1

class BriefFragment : Fragment() {

    private var _binding : FragmentBriefBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<BriefFragmentArgs>()

    private val list = listOf("","서울 강남구 강남대로 370","서울 강남구 강남대로 370","서울 강남구 강남대로 370","서울 강남구 강남대로 370","서울 강남구 강남대로 370","서울 강남구 강남대로 370")


    private inner class BriefAdapter()
        : RecyclerView.Adapter<BriefAdapter.BriefViewHolder>() {

            inner class BriefViewHolder(view: View): RecyclerView.ViewHolder(view) {


                fun bind(pl: String) {
                    view?.findViewById<TextView>(R.id.roadNameAddr)?.let  {
                        it.text = pl
                    }
                    itemView.setOnClickListener{
                        //Toast.makeText(requireContext(), "확인", Toast.LENGTH_SHORT).show()
                        val action = BriefFragmentDirections.actionFragmentBriefToFragmentDetail("서울 강남구 강남대로 370")
                        findNavController().navigate(action)
                    }

                }
            }

        override fun getItemViewType(position: Int): Int {
             return if(position == 0) ITEM_FIELD_NAME
                 else ITEM_ROW

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BriefViewHolder {
            return when(viewType) {
                ITEM_FIELD_NAME -> {
                    val binding = ItemInBriefFieldNameBinding.inflate(layoutInflater, parent, false)
                    BriefViewHolder(binding.root)
                }
                else -> {
                    val binding = ItemInBriefBinding.inflate(layoutInflater, parent, false)
                    BriefViewHolder(binding.root)
                }
            }
        }

        override fun onBindViewHolder(holder: BriefAdapter.BriefViewHolder, position: Int) {
            if(position != 0) {
                holder.bind(list[position])
                Log.d("TAG","몇번")
            }

        }

        override fun getItemCount(): Int = list.size


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
        binding.textInputEditText.setText(args.stationName)

        binding.briefInfoRecyclerView.adapter = BriefAdapter()
        binding.briefInfoRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        binding.floatingActionButton.setOnClickListener{
//            val action = BriefFragmentDirections.actionFragmentBriefToFragmentDetail(pl.roadNameAddr)
//            findNavController().navigate(action)
        }
        convertToDongName(args.stationName)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun convertToDongName(stationName: String?) {
        stationName?.let {
            // null이 아니면 처리
        }
    }
}