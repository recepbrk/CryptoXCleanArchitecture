import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.recepguzel.cryptoxcleanarchitecture.R
import com.recepguzel.cryptoxcleanarchitecture.data.model.CryptoTerm
import com.recepguzel.cryptoxcleanarchitecture.databinding.DictionaryItemBinding

class DictionaryAdapter : RecyclerView.Adapter<DictionaryAdapter.DictionaryViewHolder>() {

    private val differCallBack = object : DiffUtil.ItemCallback<CryptoTerm>() {
        override fun areItemsTheSame(oldItem: CryptoTerm, newItem: CryptoTerm): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CryptoTerm, newItem: CryptoTerm): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer<CryptoTerm>(this, differCallBack)

    inner class DictionaryViewHolder(private val binding: DictionaryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var isExpanded = false

        fun bind(dictionary: CryptoTerm) {
            binding.apply {
                termTextView.text = dictionary.term
                meaningTextView.text = dictionary.meaning

                // Açılır/kapanır özelliği burada ayarlanıyor
                arrowImageView.setImageResource(if (isExpanded) R.drawable.arrow_up else R.drawable.ic_arrow_dowm)
                meaningTextView.visibility = if (isExpanded) View.VISIBLE else View.GONE

                binding.root.setOnClickListener {
                    // Durumu tersine çevir
                    isExpanded = !isExpanded

                    // Görünürlük ve ikonu güncelle
                    meaningTextView.visibility = if (isExpanded) View.VISIBLE else View.GONE
                    arrowImageView.setImageResource(if (isExpanded) R.drawable.arrow_up else R.drawable.ic_arrow_dowm)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DictionaryViewHolder {
        return DictionaryViewHolder(
            DictionaryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: DictionaryViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    private var onItemClickListener: ((CryptoTerm) -> Unit)? = null

    fun setOnItemClickListener(listener: (CryptoTerm) -> Unit) {
        onItemClickListener = listener
    }
}
