package com.isaquliyev.githubsearchapp.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.isaquliyev.githubsearchapp.R
import com.isaquliyev.githubsearchapp.databinding.FragmentHomeBinding
import com.isaquliyev.githubsearchapp.model.DateEnum
import com.isaquliyev.githubsearchapp.model.Item
import com.isaquliyev.githubsearchapp.ui.adapter.RepositoryAdapter
import com.isaquliyev.githubsearchapp.utils.DatePicker
import com.isaquliyev.githubsearchapp.utils.OnItemClickListener
import com.isaquliyev.githubsearchapp.viewmodel.RepositoriesViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var spinner: Spinner
    private lateinit var repositoriesViewModel: RepositoriesViewModel
    private var temp : DateEnum? = null
    private val lastWeekDate = DatePicker().lastWeek()
    private val lastDayDate = DatePicker().lastDay()
    private val lastMonthDate = DatePicker().lastMonth()
    private var currentDate : String = lastDayDate
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repositoriesViewModel = ViewModelProvider(this)[RepositoriesViewModel::class.java]
        navController = Navigation.findNavController(view)

        spinner = binding.spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.dateArray,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                when(position) {
                    0 -> {
                        currentDate = lastDayDate
                    }
                    1 -> {
                        currentDate = lastWeekDate
                    }
                    2 -> {
                        currentDate = lastMonthDate
                    }
                }
                repositoriesViewModel.getRepositories(currentDate, "stars", "desc")
                repositoriesViewModel.observeRepositoryLiveData()
                    .observe(viewLifecycleOwner, Observer { repository ->
                        binding.recyclerView.adapter = RepositoryAdapter(repository.items, object : OnItemClickListener{
                            override fun onCLick(item: Item) {
                                val bundle = Bundle()
                                bundle.putSerializable("Item",item)
                                val fragment = DetailFragment()
                                fragment.arguments= bundle
                                navController.navigate(R.id.action_homeFragment_to_detailFragment, bundle)
                            }
                        })
                    })
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                //
            }
        }
    }
}