package rk.aksa

import android.content.ContentResolver
import android.database.Cursor
import android.provider.MediaStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LocalMediaDataSource(
    private val contentResolver: ContentResolver,
    private val dispatcher: CoroutineDispatcher
) : MediaDataSource {
    override suspend fun fetchImages(sortOrder: SortOrder): List<Image> {
        val images = mutableListOf<Image>()
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val columns = arrayOf(
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.DATE_ADDED,
            MediaStore.Images.ImageColumns.SIZE
        )
        val selection: String? = null
        val sortClause = sortClause(sortOrder)
        return withContext(dispatcher) {
            val cursor = contentResolver.query(
                uri,
                columns,
                selection, null, sortClause
            )
            when (cursor?.count) {
                null -> emptyList()
                0 -> emptyList()
                else -> cursor.use {
                    while (it.moveToNext()) {
                        images.add(it.toImage())
                    }
                    images
                }
            }
        }
    }

    private fun sortClause(sortOrder: SortOrder) = when (sortOrder) {
        SortOrder.DateDescending -> {
            "${MediaStore.Images.ImageColumns.DATE_ADDED} DESC"
        }
        SortOrder.DateAscending -> {
            "${MediaStore.Images.ImageColumns.DATE_ADDED} ASC"
        }
        SortOrder.SizeAscending -> {
            "${MediaStore.Images.ImageColumns.SIZE} ASC"
        }
        SortOrder.SizeDescending -> {
            "${MediaStore.Images.ImageColumns.SIZE} DESC"
        }
    }

    private fun Cursor.toImage(): Image {
        val idColumnIndex = getColumnIndex(MediaStore.Images.ImageColumns._ID)
        val sizeColumnIndex = getColumnIndex(MediaStore.Images.ImageColumns.SIZE)
        val dateAddedColumnIndex = getColumnIndex(MediaStore.Images.ImageColumns.DATE_ADDED)
        val imageId = getString(idColumnIndex)
        val imageSize = getInt(sizeColumnIndex)
        val imageDateAdded = getInt(dateAddedColumnIndex)
        return Image(
            id = imageId,
            size = imageSize,
            dateAdded = imageDateAdded
        )
    }


}