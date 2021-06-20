package id.yuhananda.bareksatest.data.source.remote

import id.yuhananda.bareksatest.data.Product
import id.yuhananda.bareksatest.data.Result
import id.yuhananda.bareksatest.data.source.ProductDataSource
import id.yuhananda.bareksatest.util.ContextUtil
import kotlinx.coroutines.delay

class ProductRemoteDataSource constructor(
    private val contextUtil: ContextUtil
) : ProductDataSource {

    private val serviceLatencyInMillis = 2000L
    private var productServiceData = arrayListOf<Product>()

    init {
        addProducts(getComparedDummyProducts())
    }

    private fun addProducts(products: List<Product>) {
        productServiceData.addAll(products)
    }

    override suspend fun getComparedProducts(): Result<List<Product>> {
        delay(serviceLatencyInMillis)
        return Result.Success(productServiceData)
    }

    override suspend fun saveProducts(products: List<Product>) {
        productServiceData.addAll(products)
    }

    override suspend fun deleteAllProducts() {
        productServiceData.clear()
    }

    private fun getComparedDummyProducts(): List<Product> {
        return arrayListOf(
            getDummyProduct(
                0,
                "BNI-AM Inspiring Equity Fund",
                "Saham",
                "5,50% / 5 thn",
                "3,64 Miliar",
                "1 Juta",
                "5 Tahun",
                "Tinggi",
                "16 Apr 2014",
                contextUtil.getJsonFromAssets("saham_alpha.json") ?: ""
            ),
            getDummyProduct(
                1,
                "Cipta Dana Cash",
                "Pasar Uang",
                "6,29% / thn",
                "215,97 Triliun",
                "100 Ribu",
                "1 Tahun",
                "Rendah",
                "14 Jan 2016",
                contextUtil.getJsonFromAssets("saham_beta.json") ?: ""
            ),
            getDummyProduct(
                2,
                "Ascend Reksa Dana Saham Equity Fund",
                "Saham",
                "7,17% / 5 thn",
                "3,89 Triliun",
                "100 Ribu",
                "5 Tahun",
                "Tinggi",
                "20 Feb 2007",
                contextUtil.getJsonFromAssets("saham_gamma.json") ?: ""
            ),
        )
    }

    private fun getDummyProduct(
        id: Int = 0,
        name: String = "",
        productType: String = "",
        yield: String = "",
        managedFunds: String = "",
        minimumPurchase: String = "",
        timePeriod: String = "",
        riskLevel: String = "",
        launching: String = "",
        growth: String = ""
    ): Product {
        return Product(
            id,
            name,
            productType,
            `yield`,
            managedFunds,
            minimumPurchase,
            timePeriod,
            riskLevel,
            launching,
            growth
        )
    }
}