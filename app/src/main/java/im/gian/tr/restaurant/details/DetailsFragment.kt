package im.gian.tr.restaurant.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import im.gian.tr.R
import im.gian.tr.databinding.FragmentRestaurantDetailsBinding
import im.gian.tr.restaurant.RestaurantViewModel

class DetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentRestaurantDetailsBinding>(
            inflater, R.layout.fragment_restaurant_details, container, false)

        val restaurantViewModel: RestaurantViewModel = ViewModelProvider(context as FragmentActivity).get(
            RestaurantViewModel::class.java)

        binding.details = this
        binding.lifecycleOwner = this
        binding.restaurantViewModel = restaurantViewModel

        //Edit button
        if(activity?.intent?.getBooleanExtra("edit", false) == true){
            binding.buttonEditName.visibility = View.VISIBLE
            binding.buttonEditCity.visibility = View.VISIBLE
            binding.buttonEditDescription.visibility = View.VISIBLE
            binding.checkBoxRestaurant.visibility = View.GONE
        }

        //Images recyclerview
        binding.recyclerViewImages.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewImages.adapter = ImageCardAdapter(context)
        binding.recyclerViewCertifications.isNestedScrollingEnabled = false

        //Certifications recyclerview
        binding.recyclerViewCertifications.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewCertifications.adapter = CertificationCardAdapter(context)
        binding.recyclerViewCertifications.isNestedScrollingEnabled = false

        //Saved
        if(restaurantViewModel.saved.value == true)
            binding.checkBoxRestaurant.isChecked = true

        binding.checkBoxRestaurant.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) restaurantViewModel.addSaved()
            else restaurantViewModel.removeSaved()
        }

        return binding.root
    }
}