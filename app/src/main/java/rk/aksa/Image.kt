package rk.aksa

import android.content.ContentUris
import android.net.Uri
import android.os.Parcelable
import android.provider.MediaStore
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Image(
    val id: String,
    val size: Int,
    val dateAdded: Int
) : Parcelable {
    val uri: Uri
        get() {
            val contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            return ContentUris.withAppendedId(contentUri, id.toLong())
        }
}
