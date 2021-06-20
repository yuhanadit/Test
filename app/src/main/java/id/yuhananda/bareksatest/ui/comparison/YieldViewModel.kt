package id.yuhananda.bareksatest.ui.comparison

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.yuhananda.bareksatest.data.Product
import id.yuhananda.bareksatest.data.ProductGrowth
import id.yuhananda.bareksatest.data.Result
import id.yuhananda.bareksatest.data.source.ProductRepository
import id.yuhananda.bareksatest.util.GsonUtil
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class YieldViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    fun getComparedProductsData() {
        if (_dataLoading.value == true) {
            return
        }

        _dataLoading.value = true

        viewModelScope.launch {
            productRepository.getComparedProducts(false).let { result ->
                if (result is Result.Success) {
                    onComparedProductsDataLoaded(result.data)
                } else {
                    onComparedProductsDataError()
                }
            }
            _dataLoading.value = false
        }
    }

    private fun onComparedProductsDataLoaded(products: List<Product>) {
        setProducts(products)
    }

    private fun onComparedProductsDataError() {
        _products.value = arrayListOf()
    }

    private fun setProducts(products: List<Product>) {
        val newProducts = ArrayList<Product>()
        products.forEach { product ->
            val growthObject = GsonUtil.parseJsonToArrayList<ProductGrowth>(product.growth)
            product.growthObject = growthObject
            newProducts.add(product)
        }

        _products.value = newProducts
    }
}