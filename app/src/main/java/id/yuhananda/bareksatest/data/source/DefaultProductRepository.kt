package id.yuhananda.bareksatest.data.source

import id.yuhananda.bareksatest.data.source.local.ProductLocalDataSource
import javax.inject.Inject

class DefaultProductRepository @Inject constructor(
        private val productRemoteDataSource: ProductDataSource,
        private val productLocalDataSource: ProductDataSource
) {
}