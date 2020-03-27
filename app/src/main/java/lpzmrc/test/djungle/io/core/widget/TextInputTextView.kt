package lpzmrc.test.djungle.io.core.widget

import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import com.google.android.material.textfield.TextInputEditText

class TextInputTextView : TextInputEditText {
    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun isEnabled() = false

    override fun setEnabled(enabled: Boolean) =
        super.setEnabled(false)

    override fun isFocused() = false

    override fun setFocusable(focusable: Boolean) =
        super.setFocusable(false)

    override fun setFocusable(focusable: Int) =
        super.setFocusable(View.NOT_FOCUSABLE)

    override fun setFocusableInTouchMode(focusableInTouchMode: Boolean) =
        super.setFocusableInTouchMode(false)

    override fun setFocusedByDefault(isFocusedByDefault: Boolean) =
        super.setFocusedByDefault(false)

    override fun getText(): Editable? =
        super.getText().let { if (TextUtils.isEmpty(it)) null else it }
            ?: Editable.Factory().newEditable(" ")

    private fun init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            focusable = View.NOT_FOCUSABLE
        }
        isFocusable = false
        isEnabled = false
    }
}