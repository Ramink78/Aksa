package rk.aksa

import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore

data class Image(
    val id: String
) {
    val uri: Uri
        get() {
            val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            return ContentUris.withAppendedId(contentUri, id.toLong())
        }
}
