package app.culturedev.cultureconnect.helper

import android.view.View
import android.widget.ProgressBar

object Utils {
    const val EXTRA_SESSION = "extra_session"
    fun showLoading(isLoading: Boolean, progressBar: ProgressBar) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    const val EXTRA_ID = "extra_id"
}