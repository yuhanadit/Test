package id.yuhananda.bareksatest.data.source

import id.yuhananda.bareksatest.data.Product
import id.yuhananda.bareksatest.data.Result

interface ProductRepository {
    suspend fun getComparedProducts(forceUpdate: Boolean = false): Result<List<Product>>
}