package id.yuhananda.bareksatest.data.source

import id.yuhananda.bareksatest.data.Product
import id.yuhananda.bareksatest.data.Result

interface ProductDataSource {
    suspend fun getComparedProducts(): Result<List<Product>>
    suspend fun saveProducts(products: List<Product>)
    suspend fun deleteAllProducts()
}