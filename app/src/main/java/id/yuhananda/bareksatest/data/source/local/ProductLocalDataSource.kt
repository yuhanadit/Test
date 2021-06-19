package id.yuhananda.bareksatest.data.source.local

import id.yuhananda.bareksatest.data.source.ProductDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class ProductLocalDataSource internal constructor(
    private val productDao: ProductDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ProductDataSource {

}