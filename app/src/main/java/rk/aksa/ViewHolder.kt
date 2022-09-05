package rk.aksa

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rk.aksa.databinding.ImageTemBinding

class ViewHolder(
    private val binding: ImageTemBinding,
    private val clickListener: ImageClickListener?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(image: Image) {
        Glide.with(binding.root)
            .load(image.uri)
            .into(binding.image)

        clickListener?.let { listener ->
            itemView.setOnClickListener {
                listener.onImageClicked(image)
            }
        }
    }
}