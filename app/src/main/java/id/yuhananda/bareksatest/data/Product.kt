package id.yuhananda.bareksatest.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0,
)
