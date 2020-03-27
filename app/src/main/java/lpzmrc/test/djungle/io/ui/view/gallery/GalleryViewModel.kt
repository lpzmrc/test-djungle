package lpzmrc.test.djungle.io.ui.view.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lpzmrc.test.djungle.io.R
import lpzmrc.test.djungle.io.core.api.dto.Photo
import lpzmrc.test.djungle.io.data.repository.GalleryRepository
import lpzmrc.test.djungle.io.data.storage.AuthStorage
import lpzmrc.test.djungle.io.ui.view.gallery.model.GalleryResult
import lpzmrc.test.djungle.io.ui.view.gallery.model.GalleryView
import org.koin.core.KoinComponent
import org.koin.core.inject

class GalleryViewModel : ViewModel(), KoinComponent {

    private val repository: GalleryRepository by inject()
    private val authStorage: AuthStorage by inject()
    val authenticationState = authStorage.authenticationState

    private val _galleryResult = MutableLiveData<GalleryResult>()

    val galleryResult: LiveData<GalleryResult> = _galleryResult
    val progress = MutableLiveData(false)

    fun fetchData() {
        viewModelScope.launch {
            progress.value = true
            _galleryResult.value = GalleryResult(listOf(GalleryView.GalleryProgress))
            val result = repository.fetchPhotos()

            if (result.isSuccess) {
                _galleryResult.value = GalleryResult(result.data.toGalleryList())
            } else {
                _galleryResult.value = GalleryResult(
                    error = R.string.error_occurred
                )
            }
            progress.value = false
        }
    }

    fun clearData() {
        viewModelScope.launch {
            _galleryResult.value = GalleryResult(listOf(GalleryView.GalleryInvalidated))
        }
    }

    fun hasNoData(): Boolean {
        return galleryResult.value?.data?.contains(GalleryView.GalleryInvalidated) ?: true
    }
}

private fun List<Photo>?.toGalleryList(): List<GalleryView> = when {
    this == null || this.isEmpty() -> listOf(GalleryView.GalleryEmpty)
    else -> map { GalleryView.PhotoView(it.id, it.albumId, it.title, it.url, it.thumbnailUrl) }
}
