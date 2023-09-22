package com.isaquliyev.githubsearchapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.isaquliyev.githubsearchapp.R
import com.isaquliyev.githubsearchapp.databinding.FragmentHomeBinding
import com.isaquliyev.githubsearchapp.ui.adapter.RepositoryAdapter
import com.isaquliyev.githubsearchapp.viewmodel.RepositoriesViewModel

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private lateinit var spinner : Spinner
    private lateinit var repositoriesViewModel: RepositoriesViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinner = binding.spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.dateArray,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        repositoriesViewModel = ViewModelProvider(this)[RepositoriesViewModel::class.java]
        repositoriesViewModel.getRepositories("created:2023-09-15", "stars", "desc")
        repositoriesViewModel.observeRepositoryLiveData().observe(viewLifecycleOwner, Observer { repository ->
            binding.recyclerView.adapter = RepositoryAdapter(repository.items)
        })


    }
}