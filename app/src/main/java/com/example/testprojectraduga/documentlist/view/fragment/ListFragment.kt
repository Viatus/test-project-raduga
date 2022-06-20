package com.example.testprojectraduga.documentlist.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testprojectraduga.databinding.FragmentListBinding
import com.example.testprojectraduga.documentlist.view.adapter.DocumentListAdapter
import com.example.testprojectraduga.documentlist.viewmodel.ListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private val listViewModel: ListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        setupUI()

        setupObservers()

        return binding.root
    }


    private fun setupUI() {
        binding.recyclerViewList.apply {
            layoutManager = LinearLayoutManager(this@ListFragment.context)
            adapter = DocumentListAdapter()
        }

        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                listViewModel.loadData()
            }
        }

        binding.buttonCopyToBeginning.setOnClickListener {
            binding.recyclerViewList.apply {
                with(adapter as DocumentListAdapter) {
                    listViewModel.copyToBeginning(selectedItemPos)
                }
            }
        }

        binding.buttonCopyToEnd.setOnClickListener {
            binding.recyclerViewList.apply {
                with(adapter as DocumentListAdapter) {
                    listViewModel.copyToEnd(selectedItemPos)
                }
            }
        }
    }

    private fun setupObservers() {
        listViewModel.data.observe(viewLifecycleOwner) { listItems ->
            listItems?.let {
                binding.recyclerViewList.apply {
                    with(adapter as DocumentListAdapter) {
                        updateItems(it)
                    }
                }
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }
    }
}