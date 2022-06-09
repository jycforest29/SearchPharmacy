package com.jms.searchpharmacy.ui.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.jms.searchpharmacy.R
import com.jms.searchpharmacy.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

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
    }



    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}