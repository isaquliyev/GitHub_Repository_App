package com.isaquliyev.githubsearchapp.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.isaquliyev.githubsearchapp.R
import com.isaquliyev.githubsearchapp.databinding.FragmentHomeBinding
import com.isaquliyev.githubsearchapp.model.DateEnum
import com.isaquliyev.githubsearchapp.model.Item
import com.isaquliyev.githubsearchapp.model.ItemRoom
import com.isaquliyev.githubsearchapp.room.AppDatabase
import com.isaquliyev.githubsearchapp.ui.adapter.RepositoryAdapter
import com.isaquliyev.githubsearchapp.utils.DatePicker
import com.isaquliyev.githubsearchapp.utils.OnFavoriteButtonClickListener
import com.isaquliyev.githubsearchapp.utils.OnItemClickListener
import com.isaquliyev.githubsearchapp.viewmodel.FavouriteListViewModel
import com.isaquliyev.githubsearchapp.viewmodel.RepositoriesViewModel

class HomeFragment : Fragment(), OnItemSelectedListener, OnItemClickListener,
    OnFavoriteButtonClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var spinner: Spinner
    private lateinit var repositoriesViewModel: RepositoriesViewModel
    private lateinit var favListViewModel: FavouriteListViewModel
    private val lastWeekDate = DatePicker().lastWeek()
    private val lastDayDate = DatePicker().lastDay()
    private val lastMonthDate = DatePicker().lastMonth()
    private var currentDate: String = lastDayDate
    private lateinit var navController: NavController
    private var favoriteList = listOf<ItemRoom>()
    private var itemList = mutableListOf<Item>()
    private var page = 1
    private lateinit var db: AppDatabase
    private lateinit var dateOutput: String
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
        favListViewModel = ViewModelProvider(this)[FavouriteListViewModel::class.java]
        navController = Navigation.findNavController(view)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = RepositoryAdapter(itemList, this, this, favoriteList)

        db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "FavoriteItems"
        ).build()

        favListViewModel.getFavoriteList(db.itemDao())
        favListViewModel.observeFavoriteList().observe(viewLifecycleOwner, Observer {
            favoriteList = it
            (binding.recyclerView.adapter as RepositoryAdapter).updateData(itemList, favoriteList)
        })
        repositoriesViewModel.getRepositories(lastDayDate,"stars","desc","1")
        repositoriesViewModel.observeRepositoryLiveData().observe(viewLifecycleOwner, Observer {
            if (page > 1) {
                it.items.forEach {
                    itemList.add(it)
                }
            } else {
                itemList = it.items as MutableList<Item>
            }
            (binding.recyclerView.adapter as RepositoryAdapter).updateData(itemList, favoriteList)
            binding.progressBar.visibility = View.GONE
        })

        spinner = binding.spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.dateArray,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }


        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
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
                binding.favouritesButton.setOnClickListener {
                    navController.navigate(R.id.action_homeFragment_to_favouritesFragment)

                }
                binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                    }

                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        if (!binding.recyclerView.canScrollVertically(1)) {
                            page++
                            repositoriesViewModel.getRepositories(
                                dateOutput,
                                "stars",
                                "desc",
                                "$page"
                            )
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }
                })
//                repositoriesViewModel.observeRepositoryLiveData()
//                    .observe(viewLifecycleOwner, Observer { repository ->
//                        binding.recyclerView.adapter = RepositoryAdapter(repository.items, object : OnItemClickListener{
//                            override fun onCLick(item: Item) {
//                                val bundle = Bundle()
//                                bundle.putSerializable("Item",item)
//                                val fragment = DetailFragment()
//                                fragment.arguments= bundle
//                                navController.navigate(R.id.action_homeFragment_to_detailFragment, bundle)
//                            }
//                        })
//                    })
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //
            }
        }
    }

    override fun onCLick(item: Item) {
        val bundle = Bundle()
        bundle.putSerializable("Item", item)
        val fragment = DetailFragment()
        fragment.arguments = bundle
        navController.navigate(R.id.action_homeFragment_to_detailFragment, bundle)
    }

    override fun onCLick(item: ItemRoom, isAddFavorite: Boolean) {
        if (isAddFavorite) {
            favListViewModel.addToFavorites(db.itemDao(), item)
        } else {
            favListViewModel.deleteFromFavorites(db.itemDao(), item)
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        //
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        //
    }
}