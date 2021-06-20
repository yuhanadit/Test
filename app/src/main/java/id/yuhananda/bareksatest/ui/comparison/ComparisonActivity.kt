package id.yuhananda.bareksatest.ui.comparison

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import id.yuhananda.bareksatest.databinding.ActivityComparisonBinding


class ComparisonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityComparisonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComparisonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        setupViewPager()
        setupTabLayout()
    }

    private fun setupActionBar() {
        supportActionBar?.title = "Perbandingan"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0F
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = ComparisonPagerAdapter(this)
    }

    private fun setupTabLayout() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            if (position == 0) {
                tab.text = "Imbal Hasil"
            } else {
                tab.text = "Dana Kelolaan"
            }
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}