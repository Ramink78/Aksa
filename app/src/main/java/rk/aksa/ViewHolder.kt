package rk.aksa

import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rk.aksa.databinding.ImageTemBinding

class ViewHolder(
    private val binding: ImageTemBinding,
    private val clickListener: ImageClickListener?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(image: Image) {
        ViewCompat.setTransitionName(binding.image, image.id)
        Glide.with(binding.root)
            .load(image.uri)
            .into(binding.image)
        binding.image.setOnClickListener {
            clickListener?.onImageClicked(it, image)
        }
    }
}