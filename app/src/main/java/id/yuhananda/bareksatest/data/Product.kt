package id.yuhananda.bareksatest.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "product_type")
    val productType: String = "",
    @ColumnInfo(name = "yield")
    val yield: String = "",
    @ColumnInfo(name = "managed_funds")
    val managedFunds: String = "",
    @ColumnInfo(name = "minimum_purchase")
    val minimumPurchase: String = "",
    @ColumnInfo(name = "time_period")
    val timePeriod: String = "",
    @ColumnInfo(name = "risk_level")
    val riskLevel: String = "",
    @ColumnInfo(name = "launching")
    val launching: String = "",
    @ColumnInfo(name = "growth")
    val growth: String = ""
)
