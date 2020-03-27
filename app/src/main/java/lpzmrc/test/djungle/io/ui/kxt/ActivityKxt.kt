package lpzmrc.test.djungle.io.ui.kxt

import android.app.Activity
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import lpzmrc.test.djungle.io.R

fun Activity.showError(messageId: Int) {
    showMessage(getString(messageId), R.color.colorMessageErrorBackground)
}

@Suppress("unused")
fun Activity.showSuccess(messageId: Int) {
    showMessage(getString(messageId), R.color.colorMessageSuccessBackground)
}

private fun Activity.showMessage(message: String, bgColorRes: Int) {
    val layout = layoutInflater.inflate(R.layout.layout_banner, null)
    val text: TextView = layout.findViewById(R.id.textView)
    text.setBackgroundResource(bgColorRes)
    text.text = message

    val toast = Toast(this)
    toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
    toast.duration = Toast.LENGTH_LONG
    toast.view = layout
    toast.show()
}