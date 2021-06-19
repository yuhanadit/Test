package id.yuhananda.bareksatest.data.source.local

import id.yuhananda.bareksatest.data.Product
import id.yuhananda.bareksatest.data.Result
import id.yuhananda.bareksatest.data.source.ProductDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductLocalDataSource internal constructor(
    private val productDao: ProductDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ProductDataSource {
    override suspend fun getComparedProducts(): Result<List<Product>> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(productDao.getProducts())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun saveProducts(products: List<Product>) = withContext(ioDispatcher) {
        productDao.insertProducts(products)
    }

    override suspend fun deleteAllProducts() {
        productDao.deleteProducts()
    }
}