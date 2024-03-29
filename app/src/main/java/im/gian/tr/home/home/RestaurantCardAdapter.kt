package im.gian.tr.home.home

import android.content.Context
import android.content.Intent
import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import im.gian.tr.R
import im.gian.tr.home.HomeViewModel
import im.gian.tr.model.Restaurant
import im.gian.tr.restaurant.RestaurantActivity

class RestaurantCardAdapter(private val context: Context?, private val sortByDistance: Boolean) :
    RecyclerView.Adapter<RestaurantCardAdapter.RestaurantCardViewHolder>() {

    private val homeViewModel: HomeViewModel =
        ViewModelProvider(context as FragmentActivity).get(HomeViewModel::class.java)

    private var restaurantList: List<Restaurant>? = homeViewModel.restaurants.value
    private var savedList: List<Restaurant>? = homeViewModel.saved.value
    private var userLocation: Location? = homeViewModel.userLocation.value

    private val storage = Firebase.storage

    class RestaurantCardViewHolder(private val row: View) : RecyclerView.ViewHolder(row) {
        val cardRestaurant: MaterialCardView = row.findViewById(R.id.cardRestaurant)
        val textViewRestaurantName: TextView = row.findViewById(R.id.textViewRestaurantName)
        val textViewRestaurantCity: TextView = row.findViewById(R.id.textViewRestaurantCity)
        val textViewRestaurantDistance: TextView = row.findViewById(R.id.textViewRestaurantDistance)
        val imageViewRestaurant: ImageView = row.findViewById(R.id.imageViewRestaurant)
        val checkBoxRestaurant: CheckBox = row.findViewById(R.id.checkboxRestaurant)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantCardViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.restaurant_card_view,
            parent, false
        )
        return RestaurantCardViewHolder(layout)
    }

    override fun onBindViewHolder(holder: RestaurantCardViewHolder, position: Int) {
        //Name
        holder.textViewRestaurantName.text = restaurantList!![position].name

        //City
        holder.textViewRestaurantCity.text = restaurantList!![position].city

        //Distance
        val distance = restaurantList!![position].getDistance(userLocation) ?: "--"
        holder.textViewRestaurantDistance.text =
            context?.getString(R.string.km, distance.toString())

        //Saved
        holder.checkBoxRestaurant.isChecked = restaurantList!![position] in savedList!!

        //Image
        storage.reference.child("propics/${restaurantList!![position].id}.jpg").downloadUrl.addOnSuccessListener {
            Glide.with(holder.imageViewRestaurant).load(it).into(holder.imageViewRestaurant)
        }

        //Card
        holder.cardRestaurant.setOnClickListener {
            val intent = Intent(context, RestaurantActivity::class.java)
            intent.putExtra("restaurant",Gson().toJson(restaurantList!![position]))

            if (restaurantList!![position] in savedList!!)
                intent.putExtra("saved", true)

            context?.startActivity(intent)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        //Observe for changes in viewmodel
        homeViewModel.restaurants.observe(context as FragmentActivity, restaurantsObserver)
        homeViewModel.saved.observe(context as FragmentActivity, savedObserver)
        homeViewModel.userLocation.observe(context as FragmentActivity, locationObserver)
    }

    //Update restaurant list when viewmodel data changes (sort by location if needed)
    private val restaurantsObserver = Observer<List<Restaurant>> {
        restaurantList = if (sortByDistance) {
            val sortedRestaurantList = homeViewModel.restaurants.value?.toMutableList()
            sortedRestaurantList?.sortBy { it.getDistance(userLocation) }
            sortedRestaurantList
        } else
            homeViewModel.restaurants.value

        notifyDataSetChanged()
    }

    //Update saved list when viewmodel data changes
    private val savedObserver = Observer<List<Restaurant>> {
        savedList = homeViewModel.saved.value
        notifyDataSetChanged()
    }

    //Update user location when viewmodel data changes
    private val locationObserver = Observer<Location> {
        userLocation = homeViewModel.userLocation.value
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = restaurantList!!.size

}