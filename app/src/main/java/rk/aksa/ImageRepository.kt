package rk.aksa

import android.content.ContentResolver
import android.provider.MediaStore
import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ImageRepository(
    private val dispatcher: CoroutineDispatcher,
    private val contentResolver: ContentResolver
) {
    suspend fun fetchImages(): List<Image> {
        val images = mutableListOf<Image>()
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val columns = arrayOf(
            MediaStore.Images.ImageColumns._ID
        )
        val selection: String? = null
        return withContext(dispatcher) {
            try {
                val cursor = contentResolver.query(
                    uri,
                    columns,
                    selection, null, null
                )
                when (cursor?.count) {
                    null -> emptyList()
                    0 -> emptyList()
                    else -> cursor.use {
                        while (it.moveToNext()) {
                            val idColumnIndex =
                                it.getColumnIndex(MediaStore.Images.ImageColumns._ID)
                            val imageId = it.getString(idColumnIndex)
                            images.add(Image(imageId))
                        }
                        images
                    }
                }
            } catch (e: Exception) {
                Log.e("ImageRepository", e.message.toString())
                emptyList()
            }
        }
    }

}