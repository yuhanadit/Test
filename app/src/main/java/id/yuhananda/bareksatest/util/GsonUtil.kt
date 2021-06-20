package id.yuhananda.bareksatest.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object GsonUtil {
    fun <T : Any> parseJsonToArrayList(json: String): List<T> {
        return try {
            val gson = Gson()
            val typeToken = object : TypeToken<Array<T>>() {}.type
            gson.fromJson(json, typeToken)
        } catch (e: Exception) {
            arrayListOf()
        }
    }
}