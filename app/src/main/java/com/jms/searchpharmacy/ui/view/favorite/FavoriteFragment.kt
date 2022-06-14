package com.jms.searchpharmacy.ui.view.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jms.searchpharmacy.R
import com.jms.searchpharmacy.databinding.FragmentFavoriteBinding
import com.jms.searchpharmacy.databinding.ItemFavoriteBinding
import com.jms.searchpharmacy.ui.view.MainActivity
import com.jms.searchpharmacy.ui.viewmodel.MainViewModel

class FavoriteFragment : Fragment() {

    private var _binding : FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoriteViewModel: MainViewModel

    private inner class FavoriteAdapter()
        : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

            inner class FavoriteViewHolder(binding: ItemFavoriteBinding)
                : RecyclerView.ViewHolder(binding.root) {

                    fun bind() {

                    }
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
            TODO("Not yet implemented")
        }

        override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
            TODO("Not yet implemented")
        }

        override fun getItemCount(): Int {
            TODO("Not yet implemented")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteViewModel = (activity as MainActivity).mainViewModel
        //setupRecyclerView()

    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}