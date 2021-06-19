package id.yuhananda.bareksatest.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import id.yuhananda.bareksatest.data.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class BareksaTestDatabase : RoomDatabase() {
    abstract fun productDao() : ProductDao
}