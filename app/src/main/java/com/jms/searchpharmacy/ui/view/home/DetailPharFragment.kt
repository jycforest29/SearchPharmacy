package com.jms.searchpharmacy.ui.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jms.searchpharmacy.R
import com.jms.searchpharmacy.databinding.FragmentDetailPharBinding
import com.jms.searchpharmacy.ui.view.MainActivity
import com.jms.searchpharmacy.ui.viewmodel.MainViewModel


class DetailPharFragment : Fragment() {
    private var _binding : FragmentDetailPharBinding? = null
    private val binding get() = _binding!!
    private val viewModel : MainViewModel by lazy {
        (activity as MainActivity).mainViewModel
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailPharBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchedPharList.observe(viewLifecycleOwner) {

        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


}