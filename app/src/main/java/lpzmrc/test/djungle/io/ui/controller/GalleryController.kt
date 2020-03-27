package lpzmrc.test.djungle.io.ui.controller

import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.epoxy.TypedEpoxyController
import lpzmrc.test.djungle.io.GalleryEmptyBindingModel_
import lpzmrc.test.djungle.io.GalleryInvalidatedBindingModel_
import lpzmrc.test.djungle.io.GalleryPhotoBindingModel_
import lpzmrc.test.djungle.io.GalleryProgressBindingModel_
import lpzmrc.test.djungle.io.ui.view.gallery.model.GalleryView

class GalleryController : TypedEpoxyController<List<GalleryView>>() {

    override fun buildModels(data: List<GalleryView>?) {
        data?.forEach { item ->
            when (item.type) {
                GalleryView.Type.PHOTO -> {
                    (item as? GalleryView.PhotoView)?.let { photo ->
                        GalleryPhotoBindingModel_()
                            .photo(photo)
                            .id(photo.id)
                            .spanSizeOverride { totalSpanCount, position, itemCount -> 1 }
                            .addTo(this)
                    }
                }
                GalleryView.Type.EMPTY -> (item as? GalleryView.GalleryEmpty)?.let { empty ->
                    GalleryEmptyBindingModel_()
                        .empty(empty)
                        .id(-GalleryView.Type.EMPTY.ordinal)
                        .spanSizeOverride { totalSpanCount, position, itemCount -> totalSpanCount }
                        .addTo(this)
                }
                GalleryView.Type.PROGRESS -> (item as? GalleryView.GalleryProgress)?.let { progress ->
                    GalleryProgressBindingModel_()
                        .progress(progress)
                        .id(-GalleryView.Type.PROGRESS.ordinal)
                        .spanSizeOverride { totalSpanCount, position, itemCount -> totalSpanCount }
                        .addTo(this)
                }
                GalleryView.Type.INVALIDATED -> (item as? GalleryView.GalleryInvalidated)?.let { invalidated ->
                    GalleryInvalidatedBindingModel_()
                        .invalidated(invalidated)
                        .id(-GalleryView.Type.INVALIDATED.ordinal)
                        .spanSizeOverride { totalSpanCount, position, itemCount -> totalSpanCount }
                        .addTo(this)
                }
            }
        }
    }

    override fun getSpanSizeLookup(): GridLayoutManager.SpanSizeLookup {
        return super.getSpanSizeLookup()
    }
}
