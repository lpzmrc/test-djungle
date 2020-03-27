package lpzmrc.test.djungle.io.ui.view.gallery.model

sealed class GalleryView(val type: Type) {

    data class PhotoView(
        val id: Long,
        val albumId: Long,
        val title: String,
        val url: String,
        val thumbnailUrl: String
    ) : GalleryView(Type.PHOTO)

    object GalleryEmpty : GalleryView(Type.EMPTY)

    object GalleryProgress : GalleryView(Type.PROGRESS)

    object GalleryInvalidated : GalleryView(Type.INVALIDATED)

    enum class Type {
        PHOTO, EMPTY, PROGRESS, INVALIDATED
    }
}
