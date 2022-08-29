package rk.aksa

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers

class MainActivityViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            val imageRepository =
                ImageRepository(LocalMediaDataSource(context.contentResolver, Dispatchers.IO))
            MainActivityViewModel(imageRepository) as T
        } else throw IllegalArgumentException("Unknown ViewModel class")
    }
}