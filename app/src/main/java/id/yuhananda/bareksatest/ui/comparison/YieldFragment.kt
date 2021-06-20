package id.yuhananda.bareksatest.ui.comparison

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.github.mikephil.charting.data.Entry
import dagger.hilt.android.AndroidEntryPoint
import id.yuhananda.bareksatest.R
import id.yuhananda.bareksatest.data.Product
import id.yuhananda.bareksatest.databinding.FragmentYieldBinding

@AndroidEntryPoint
class YieldFragment : Fragment(), ProductAdapter.ItemProductListener {
    private lateinit var viewModel: YieldViewModel
    private lateinit var binding: FragmentYieldBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentYieldBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(YieldViewModel::class.java)

        setupSwipeRefreshLayout()
        setupObserver()

        viewModel.getComparedProductsData()
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getComparedProductsData()
        }
    }

    private fun setupObserver() {
        viewModel.dataLoading.observe(viewLifecycleOwner) { dataLoading ->
            binding.swipeRefreshLayout.isRefreshing = dataLoading
        }

        viewModel.products.observe(viewLifecycleOwner) { productList ->
            setupChartData(productList)
            setupProductsData(productList)
        }
    }

    private fun setupChartData(productList: List<Product>?) {
        if (productList != null && productList.isNotEmpty()) {
            val entries = ArrayList<Entry>()
            productList[0].growthObject.forEach { _ ->
                entries.add(Entry())
            }
        }
    }

    private fun setupProductsData(productList: List<Product>?) {
        binding.recyclerView.layoutManager = GridLayoutManager(requireActivity(), 3)
        binding.recyclerView.adapter =
            ProductAdapter(productList ?: arrayListOf(), requireActivity(), this)
    }

    override fun onProductButtonClick(view: View?, product: Product?) {
        /*Log.d("YieldFragment", "onProductButtonClick " +
                "\nID : ${view?.id}, ${R.id.buttonDetail} ${R.id.buttonBuy}" +
                "\nProduct : ${product.toString()}")*/
        if (view != null && product != null) {
            if (view.id == R.id.buttonDetail) {
                Log.d("YieldFragment", "OnClick Detail ${product.name}")
            } else if (view.id == R.id.buttonBuy) {
                Log.d("YieldFragment", "OnClick Beli ${product.name}")
            }
        }
    }
}