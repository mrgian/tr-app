package im.gian.tr.producer.products

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
import im.gian.tr.databinding.FragmentProducerProductsBinding
import im.gian.tr.producer.ProducerViewModel

class ProductsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val binding = DataBindingUtil.inflate<FragmentProducerProductsBinding>(
            inflater, R.layout.fragment_producer_products, container, false)

        val producerViewModel: ProducerViewModel = ViewModelProvider(context as FragmentActivity).get(
            ProducerViewModel::class.java)

        binding.products = this
        binding.lifecycleOwner = this
        binding.producerViewModel = producerViewModel

        //Products recyclerview
        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewProducts.adapter = ProductCardAdapter(context)


        return binding.root
    }
}