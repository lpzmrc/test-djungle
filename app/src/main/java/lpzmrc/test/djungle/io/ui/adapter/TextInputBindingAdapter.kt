package lpzmrc.test.djungle.io.ui.adapter

import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

object TextInputBindingAdapter {
    @BindingAdapter("errorText")
    @JvmStatic
    fun setErrorMessageInt(view: TextInputLayout, @StringRes errorMessage: Int?) {
        if (errorMessage != null) {
            view.error = view.context.getText(errorMessage)
        } else {
            view.error = null
        }
    }
}