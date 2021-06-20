package id.yuhananda.bareksatest.util

import com.github.mikephil.charting.data.Entry


object DummyDataFactory {
    fun get1WDummyData(): MutableList<Entry> {
        val values = ArrayList<Entry>()
        for (i in 0 until 12) {
            val data = (Math.random() * 100)
            values.add(Entry(i.toFloat(), data.toFloat()))
        }
        return values
    }

    fun get1MDummyData(): MutableList<Entry> {
        val values = ArrayList<Entry>()
        for (i in 0 until 12) {
            val data = (Math.random() * 100)
            values.add(Entry(i.toFloat(), data.toFloat()))
        }
        return values
    }

    fun get1YDummyData(): MutableList<Entry> {
        val values = ArrayList<Entry>()
        for (i in 0 until 12) {
            val data = (Math.random() * 100)
            values.add(Entry(i.toFloat(), data.toFloat()))
        }
        return values
    }

    fun get3YDummyData(): MutableList<Entry> {
        val values = ArrayList<Entry>()
        for (i in 0 until 12) {
            val data = (Math.random() * 100)
            values.add(Entry(i.toFloat(), data.toFloat()))
        }
        return values
    }

    fun get5YDummyData(): MutableList<Entry> {
        val values = ArrayList<Entry>()
        for (i in 0 until 12) {
            val data = (Math.random() * 100)
            values.add(Entry(i.toFloat(), data.toFloat()))
        }
        return values
    }

    fun get10YDummyData(): MutableList<Entry> {
        val values = ArrayList<Entry>()
        for (i in 0 until 12) {
            val data = (Math.random() * 100)
            values.add(Entry(i.toFloat(), data.toFloat()))
        }
        return values
    }

    fun getAllDummyData(): MutableList<Entry> {
        val values = ArrayList<Entry>()
        for (i in 0 until 12) {
            val data = (Math.random() * 100)
            values.add(Entry(i.toFloat(), data.toFloat()))
        }
        return values
    }

}