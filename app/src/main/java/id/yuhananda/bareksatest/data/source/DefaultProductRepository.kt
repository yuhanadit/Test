package id.yuhananda.bareksatest.data.source

import android.util.Log
import id.yuhananda.bareksatest.data.Product
import id.yuhananda.bareksatest.data.Result
import id.yuhananda.bareksatest.di.ApplicationModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import javax.inject.Inject

class DefaultProductRepository @Inject constructor(
    @ApplicationModule.ProductRemoteDataSource private val productRemoteDataSource: ProductDataSource,
    @ApplicationModule.ProductLocalDataSource private val productLocalDataSource: ProductDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ProductRepository {
    private var cachedProducts: ConcurrentMap<Int, Product>? = null

    override suspend fun getComparedProducts(forceUpdate: Boolean): Result<List<Product>> =
        withContext(ioDispatcher) {
            if (!forceUpdate) {
                cachedProducts?.let { cachedProducts ->
                    return@withContext Result.Success(cachedProducts.values.toList())
                }
            }

            val newProduct = fetchProductFromRemoteOrLocal(forceUpdate)

            (newProduct as? Result.Success)?.let { refreshCache(it.data) }
            cachedProducts?.values?.let { products ->
                return@withContext Result.Success(products.toList())
            }

            (newProduct as? Result.Success)?.let {
                if (it.data.isEmpty()) {
                    return@withContext Result.Success(it.data)
                }
            }

            return@withContext Result.Error(Exception("Illegal state"))
        }

    private fun refreshCache(products: List<Product>) {
        cachedProducts?.clear()
        products.forEach {
            cacheAndPerform(it) {}
        }
    }

    private inline fun cacheAndPerform(product: Product, perform: (Product) -> Unit) {
        val cachedProduct = cacheProduct(product)
        perform(cachedProduct)
    }

    private fun cacheProduct(product: Product): Product {
        val cachedProduct = Product(
            product.id,
            product.name,
            product.productType,
            product.yield,
            product.managedFunds,
            product.minimumPurchase,
            product.timePeriod,
            product.riskLevel,
            product.launching,
            product.growth
        )
        // Create if it doesn't exist.
        if (cachedProducts == null) {
            cachedProducts = ConcurrentHashMap()
        }
        cachedProducts?.put(cachedProduct.id, cachedProduct)
        return cachedProduct
    }

    private suspend fun fetchProductFromRemoteOrLocal(forceUpdate: Boolean): Result<List<Product>> {
        when (val remoteProducts = productRemoteDataSource.getComparedProducts()) {
            is Error -> Log.e("defaultProductRepository", "Remote data source fetch failed")
            is Result.Success -> {
                refreshLocalDataSource(remoteProducts.data)
                return remoteProducts
            }
            else -> throw IllegalStateException()
        }

        if (forceUpdate) {
            return Result.Error(Exception("Can't force refresh: remote data source is unavailable"))
        }

        val localProducts = productLocalDataSource.getComparedProducts()
        if (localProducts is Result.Success) return localProducts
        return Result.Error(Exception("Error fetching from remote and local"))
    }

    private suspend fun refreshLocalDataSource(products: List<Product>) {
        productLocalDataSource.deleteAllProducts()
        productLocalDataSource.saveProducts(products)
    }
}