package id.yuhananda.bareksatest.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.yuhananda.bareksatest.data.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM product LIMIT 3")
    suspend fun getProducts(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)

    @Query("DELETE FROM product")
    suspend fun deleteProducts()
}