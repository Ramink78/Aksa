package rk.aksa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

class MainActivityViewModel(
    private val imageRepository: ImageRepository
) : ViewModel() {
    val images = liveData {
        emit(imageRepository.fetchImages())
    }
}