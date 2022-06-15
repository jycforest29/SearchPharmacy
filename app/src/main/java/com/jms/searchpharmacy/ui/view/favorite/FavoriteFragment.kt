package com.jms.searchpharmacy.ui.view.favorite

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jms.searchpharmacy.R
import com.jms.searchpharmacy.data.model.server.PharmacyLocation
import com.jms.searchpharmacy.databinding.FragmentFavoriteBinding

import com.jms.searchpharmacy.databinding.ItemFavoritePlBinding
import com.jms.searchpharmacy.ui.view.MainActivity
import com.jms.searchpharmacy.ui.viewmodel.MainViewModel

class FavoriteFragment : Fragment() {

    private var _binding : FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter
    private inner class FavoriteAdapter()
        : ListAdapter<PharmacyLocation, FavoriteAdapter.FavoriteViewHolder>(PharDiffCallback) {

            inner class FavoriteViewHolder(val itemBinding: ItemFavoritePlBinding)
                : RecyclerView.ViewHolder(itemBinding.root) {

                    fun bind(pl: PharmacyLocation) {
                        itemBinding.textView.text = pl.index.toString()
                    }
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
            val itemBinding = ItemFavoritePlBinding.inflate(layoutInflater, parent, false)
            return FavoriteViewHolder(itemBinding)
        }

        override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
            val pl = currentList[position]
            holder.bind(pl)
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
        viewModel = (activity as MainActivity).mainViewModel
        favoriteAdapter = FavoriteAdapter()


        viewModel.favoritePharLocations.observe(viewLifecycleOwner) {
            favoriteAdapter.submitList(it)
            binding.favoriteRv.adapter = favoriteAdapter
            binding.favoriteRv.layoutManager = LinearLayoutManager(requireContext())

        }
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }


    companion object {
        private val PharDiffCallback = object : DiffUtil.ItemCallback<PharmacyLocation>() {
            override fun areItemsTheSame(
                oldItem: PharmacyLocation,
                newItem: PharmacyLocation
            ): Boolean {
                return oldItem.index == newItem.index
            }

            override fun areContentsTheSame(
                oldItem: PharmacyLocation,
                newItem: PharmacyLocation
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}