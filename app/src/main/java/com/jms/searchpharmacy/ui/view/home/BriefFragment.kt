package com.jms.searchpharmacy.ui.view.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
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
import com.jms.searchpharmacy.util.Constants
import java.io.LineNumberReader


private const val ITEM_FIELD_NAME = 0
private const val ITEM_ROW = 1

class BriefFragment : Fragment() {

    private var _binding: FragmentBriefBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<BriefFragmentArgs>()


    private val viewModel: MainViewModel by lazy {
        (activity as MainActivity).mainViewModel
    }

    private inner class BriefAdapter(val PLList: List<PharmacyLocation>) :
        RecyclerView.Adapter<BriefAdapter.BriefViewHolder>() {

        inner class BriefViewHolder(val itemBinding: ItemInBriefBinding) :
            RecyclerView.ViewHolder(itemBinding.root) {


            fun bind(pl: PharmacyLocation) {
                itemBinding.apply {
                    roadNameAddr.text = pl.load_address
                    hospitalCnt.text = pl.hospital_count.toString()
                    pharmacyCnt.text = pl.pharmacy_count.toString()
                    ratio.text = String.format("%.2f", pl.hospital_per_pharmacy)
                    convStoreCnt.text = pl.convenience_count.toString()
                }

                itemView.setOnClickListener {

                    val action =
                        BriefFragmentDirections.actionFragmentBriefToFragmentDetail(pl)
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

        binding.floatingActionButton.setOnClickListener {
//            val action = BriefFragmentDirections.actionFragmentBriefToFragmentDetail(pl.roadNameAddr)
//            findNavController().navigate(action)
        }
        viewModel.fetchedPLs.observe(viewLifecycleOwner) {


            binding.briefInfoRecyclerView.adapter = BriefAdapter(it)
            binding.briefInfoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.fetchPLs(args.dongName ?: "")


        binding.textInputEditText.imeOptions = EditorInfo.IME_ACTION_SEARCH


        binding.textInputEditText.setOnEditorActionListener(object :
            TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, actionId: Int, p2: KeyEvent?): Boolean {
                return when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        binding.textInputEditText.text?.let {
                            if (it.isNotEmpty() && it.matches(Constants.dongNamePattern)) {
                                viewModel.fetchPLs(it.toString())
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "지역을 다시 확인해주세요\n서울 지역만 가능합니다",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        true
                    }
                    else -> {
                        false
                    }
                }

            }

        })
        binding.textInputEditText.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    binding.textInputEditText.text?.let {
                        if (it.isNotEmpty()) {
                            val result = it.matches(Constants.dongNamePattern)
                            if (result) {
                                //TODO{}
                            } else {
                                //에러 띄우기
                                binding.textInputEditText.setError("'동'으로 끝나야 합니다", null)
                            }
                        }
                    }

                }

                override fun afterTextChanged(p0: Editable?) {
                    if (!binding.textInputEditText.isFocused) {
                        binding.textInputEditText.error = null
                    }

                }

            }
        )

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}