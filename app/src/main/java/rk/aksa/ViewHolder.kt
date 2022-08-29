package rk.aksa

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rk.aksa.databinding.ImageTemBinding

class ViewHolder(private val binding: ImageTemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(image: Image) {
        Glide.with(binding.root)
            .load(image.uri)
            .into(binding.image)
    }
}