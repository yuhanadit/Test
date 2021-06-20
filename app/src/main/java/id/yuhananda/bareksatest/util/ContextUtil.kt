package id.yuhananda.bareksatest.util

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

/*Util that need Context to make this work properly*/
class ContextUtil constructor(
    private val context: Context
) {
    fun getJsonFromAssets(fileName: String): String? {
        return try {
            val inputStream: InputStream = context.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charset.forName("UTF-8"))
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }
}