package id.yuhananda.bareksatest.ui.comparison

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.yuhananda.bareksatest.R
import id.yuhananda.bareksatest.data.Product
import id.yuhananda.bareksatest.databinding.ComponentProductBinding


class ProductAdapter constructor(
    private val products: List<Product> = arrayListOf(),
    private val context: Context,
    private val itemProductListener: ItemProductListener
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val binding = ComponentProductBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ProductAdapter.ViewHolder, position: Int) {
        viewHolder.setData(products[position], position)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    inner class ViewHolder(private val binding: ComponentProductBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        private var product: Product? = null

        init {
            binding.buttonDetail.setOnClickListener(this)
            binding.buttonBuy.setOnClickListener(this)
        }

        fun setData(product: Product, position: Int) {
            this.product = product
            setProductLogo(position, product.name)
            setCardBackgroundColor(position)

            binding.textViewProductName.text = product.name
            binding.textViewProductType.text = product.productType
            binding.textViewYield.text = product.yield
            binding.textViewManagedFunds.text = product.managedFunds
            binding.textViewMinimumPurchase.text = product.minimumPurchase
            binding.textViewTimePeriod.text = product.timePeriod
            binding.textViewRiskLevel.text = product.riskLevel
            binding.textViewLaunching.text = product.launching
        }

        private fun setProductLogo(position: Int, name: String) {
            val resource: Int = when {
                position % 3 == 0 -> {
                    R.drawable.ic_logo_product_1
                }
                position % 3 == 1 -> {
                    R.drawable.ic_logo_product_2
                }
                else -> {
                    R.drawable.ic_logo_product_3
                }
            }
            Glide.with(context.applicationContext).load(resource).into(binding.ivLogo)

            val firstCharacter = name[0]
            binding.textViewInitials.text = firstCharacter.toUpperCase().toString()
        }

        private fun setCardBackgroundColor(position: Int) {
            val resource: Int = when {
                position % 3 == 0 -> {
                    ContextCompat.getColor(context, R.color.green_50)
                }
                position % 3 == 1 -> {
                    ContextCompat.getColor(context, R.color.violet_50)
                }
                else -> {
                    ContextCompat.getColor(context, R.color.navy_50)
                }
            }
            binding.cardView1.setCardBackgroundColor(resource)
            binding.cardView2.setCardBackgroundColor(resource)
            binding.cardView3.setCardBackgroundColor(resource)
            binding.cardView4.setCardBackgroundColor(resource)
            binding.cardView5.setCardBackgroundColor(resource)
            binding.cardView6.setCardBackgroundColor(resource)
            binding.cardView7.setCardBackgroundColor(resource)
            binding.cardView8.setCardBackgroundColor(resource)
        }

        override fun onClick(view: View?) {
            itemProductListener.onProductButtonClick(view, product)
        }
    }

    interface ItemProductListener {
        fun onProductButtonClick(view: View?, product: Product?)
    }
}