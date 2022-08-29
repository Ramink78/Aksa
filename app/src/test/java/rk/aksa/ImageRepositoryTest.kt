package rk.aksa

import com.google.common.truth.Truth.assertThat
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class ImageRepositoryTest {
    private lateinit var mockedMediaDataSource: MediaDataSource

    @Before
    fun setup() {
        mockedMediaDataSource = mockk()
    }

    @After
    fun finish() {
        clearMocks(mockedMediaDataSource)
    }

    @Test
    fun `fetch images throw exception return empty list`() {
        val imageRepository = ImageRepository(mockedMediaDataSource)
        coEvery { mockedMediaDataSource.fetchImages(SortOrder.DateDescending) } throws Exception()
        runBlocking {
            assertThat(imageRepository.fetchImages(SortOrder.DateDescending))
                .isEmpty()
        }
    }

    @Test
    fun `fetch images sort by date ascending`() {
        val imageRepository = ImageRepository(mockedMediaDataSource)
        val sortOrder = SortOrder.DateAscending
        val imagesSortedByDateAscending = listOf(
            Image("1", 3, 0),
            Image("2", 55, 1),
            Image("3", 4, 2),
            Image("4", 8, 3),
        )
        coEvery { mockedMediaDataSource.fetchImages(sortOrder) } returns imagesSortedByDateAscending
        runBlocking {
            val result = imageRepository.fetchImages(SortOrder.DateAscending)
            assertThat(result.map { it.dateAdded })
                .isInOrder()
        }
    }

}