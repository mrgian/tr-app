package im.gian.tr.home.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import im.gian.tr.R
import im.gian.tr.databinding.FragmentHomeBinding
import im.gian.tr.home.HomeViewModel
import im.gian.tr.home.model.Restaurant

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater, R.layout.fragment_home, container, false)

        val homeViewModel: HomeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        val rests = mutableListOf(Restaurant("Caricamento...","Caricamento..."))

        binding.home = this
        binding.lifecycleOwner = this
        binding.homeViewModel = homeViewModel

        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = RestaurantCardAdapter(rests)

        binding.recyclerView1.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView1.adapter = RestaurantCardAdapter(rests)

        val restaurantsObserver = Observer<List<Restaurant>> { value ->
            binding.recyclerView.adapter = RestaurantCardAdapter(value)
            binding.recyclerView1.adapter = RestaurantCardAdapter(value)
        }
        homeViewModel.restaurants.observe(viewLifecycleOwner, restaurantsObserver)

        homeViewModel.fetchRestaurants()

        return binding.root
    }



}