package com.example.myasteroidsnasaapp.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.net.toUri
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myasteroidsnasaapp.R
import com.example.myasteroidsnasaapp.adapters.OnRecyclerViewClick
import com.example.myasteroidsnasaapp.adapters.RecyclerAdapter
import com.example.myasteroidsnasaapp.databinding.FragmentMainBinding
import com.example.myasteroidsnasaapp.ui.Constants
import com.example.myasteroidsnasaapp.viewModels.MainViewModelRetrofit
import com.example.myasteroidsnasaapp.viewModels.RoomViewModel
import com.squareup.picasso.Picasso


class MainFragment : Fragment() ,OnRecyclerViewClick {

    private lateinit var binding: FragmentMainBinding
    private  val viewModel: MainViewModelRetrofit by viewModels()
    private  val roomViewModel: RoomViewModel by viewModels()
    private lateinit var adapter : RecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.roomViewModel = roomViewModel



        //To confirm that internet is available : (if not : app will show the data that saved in room database ) :
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            } else {
                TODO("VERSION.SDK_INT < M")
            }
        if (capabilities != null) {

            putDataIntoRecyclerView()
            photoOfTheDay()
            addMenu()

        }
    }


    //put the data into recycler view
    private fun putDataIntoRecyclerView() {
        roomViewModel.readAllAsteroidDetails.observe(viewLifecycleOwner) {
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.adapter = RecyclerAdapter(it,this)
            binding.recyclerView.setHasFixedSize(true)

            //Fun to Click on any items in the list to go to Detail Fragment :
            adapter = RecyclerAdapter(it,this)
            adapter.notifyDataSetChanged()


        }
    }

    //Fun to Add menu in actionbar :
    private fun addMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.show_week_asteroid -> {
                        roomViewModel.getWeekAsteroid.observe(viewLifecycleOwner) {
                            binding.recyclerView.adapter = RecyclerAdapter(it,this@MainFragment)
                        }
                        return true
                    }
                    R.id.show_today_asteroid -> {
                        roomViewModel.getTodayAsteroid.observe(viewLifecycleOwner) {
                            binding.recyclerView.adapter = RecyclerAdapter(it,this@MainFragment)
                        }
                        return true
                    }
                    R.id.show_all_asteroid -> {
                        roomViewModel.readAllAsteroidDetails.observe(viewLifecycleOwner) {
                            binding.recyclerView.adapter = RecyclerAdapter(it,this@MainFragment)
                        }
                        return true
                    }
                    else -> {
                        false
                    }
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    //Fun to Add Photo from Nasa Api To Image view in mainFragment :
    private fun photoOfTheDay() {
        viewModel.getPictureFinally(Constants.API_KEY)
        viewModel.customImage.observe(viewLifecycleOwner) {
            val mediaType = it.toData()?.mediaType
            val title = it.toData()?.title
            val url = it.toData()?.url
            if (mediaType == "image") {
                Picasso.Builder(binding.picAsteroid.context).build()
                    .load(url?.toUri()).into(binding.picAsteroid)
            }
        }
    }

    //fun when press on any item in the recyclerView
    override fun onClicked(position: Int) {
        roomViewModel.readAllAsteroidDetails.observe(viewLifecycleOwner) {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDetailFragment(
                    it[position]
                )
            )
        }
    }


}
