package com.jms.searchpharmacy.ui.view.home

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jms.searchpharmacy.R
import com.jms.searchpharmacy.data.model.stations.Line
import com.jms.searchpharmacy.databinding.FragmentSelectSubwayBinding
import com.jms.searchpharmacy.databinding.ItemInDetailSubwayBinding
import com.jms.searchpharmacy.databinding.ItemInSelectSubwayBinding
import com.jms.searchpharmacy.ui.view.MainActivity
import com.jms.searchpharmacy.ui.viewmodel.MainViewModel


class SelectSubwayFragment : Fragment() {

    private var _binding : FragmentSelectSubwayBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MainViewModel by lazy {
        (activity as MainActivity).mainViewModel
    }



    private inner class DetailNameAdapter(val line: Line)
        : RecyclerView.Adapter<DetailNameAdapter.DetailNameViewHolder>() {
            inner class DetailNameViewHolder(val itemInDetailSubwayBinding: ItemInDetailSubwayBinding)
                : RecyclerView.ViewHolder(itemInDetailSubwayBinding.root) {

                    fun bind(station: String) {
                        // 역이름 받아서 배치
                        itemInDetailSubwayBinding.stationNameText.text = station
                    }

                }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailNameViewHolder {
            val binding = ItemInDetailSubwayBinding.inflate(layoutInflater, parent, false)
            return DetailNameViewHolder(binding)
        }

        override fun onBindViewHolder(holder: DetailNameViewHolder, position: Int) {
            val station = line.stationList[position]
            holder.bind(station)

        }

        override fun getItemCount(): Int = line.stationList.size
    }

    private inner class BriefNameAdapter()
        : RecyclerView.Adapter<BriefNameAdapter.BriefNameViewHolder>() {

        private val lineList = viewModel.lineList

            inner class BriefNameViewHolder(val itemInSelectSubwayBinding: ItemInSelectSubwayBinding)
                : RecyclerView.ViewHolder(itemInSelectSubwayBinding.root) {
                //여기서 어답터 지정
                val toggleButton: MutableLiveData<Boolean> = MutableLiveData(false)
                lateinit var line: Line
                init {
                    toggleButton.observe(viewLifecycleOwner){ isClicked ->
                        if(isClicked) {
                            Toast.makeText(requireContext(), "isClicked: $isClicked", Toast.LENGTH_SHORT).show()
                            itemInSelectSubwayBinding.detailOfLineRecyclerView.isVisible = true
                            itemInSelectSubwayBinding.detailOfLineRecyclerView.adapter = DetailNameAdapter(line)
                            itemInSelectSubwayBinding.detailOfLineRecyclerView.layoutManager = GridLayoutManager(requireContext(),3)
                        } else {
                            itemInSelectSubwayBinding.detailOfLineRecyclerView.isVisible = false
                        }
                    }
                }
                fun bind(line: Line) {
                    this.line = line
                    //itemInSelectSubwayBinding.colorOfLine.setBackgroundColor(Color.parseColor(line.color))
                    itemInSelectSubwayBinding.colorOfLine.circleBackgroundColor = Color.parseColor(line.color)
                    itemInSelectSubwayBinding.lineNumberText.text = line.name
                    itemInSelectSubwayBinding.lineNumberLayout.setOnClickListener {
                        toggleButton.postValue(!toggleButton.value!!)
                    }
                }
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BriefNameViewHolder {
            val binding = ItemInSelectSubwayBinding.inflate(layoutInflater, parent, false)
            return BriefNameViewHolder(binding)
        }

        override fun onBindViewHolder(holder: BriefNameViewHolder, position: Int) {
            val line = lineList[position]
            holder.bind(line)
        }

        override fun getItemCount(): Int = lineList.size

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectSubwayBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.allLinesRecyclerView.adapter = BriefNameAdapter()
        binding.allLinesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}