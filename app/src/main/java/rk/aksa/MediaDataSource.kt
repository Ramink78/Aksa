package rk.aksa

interface MediaDataSource {
    suspend fun fetchImages(sortOrder: SortOrder): List<Image>
}