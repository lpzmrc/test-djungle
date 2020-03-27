package lpzmrc.test.djungle.io.ui.kxt

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.showError(@StringRes errorString: Int) {
    requireActivity().showError(errorString)
}
