package rk.aksa

import android.util.Log

class ImageRepository(
    private val mediaDataSource: MediaDataSource
) {
    suspend fun fetchImages(sortOrder: SortOrder = SortOrder.DateDescending): List<Image> {
        return try {
            mediaDataSource.fetchImages(sortOrder)
        } catch (e: Exception) {
            Log.i("ImageRepository", "fetchImages: ${e.message} ")
            emptyList()
        }

    }

}