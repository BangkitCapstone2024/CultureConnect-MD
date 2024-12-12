package app.culturedev.cultureconnect.helper

import android.view.View
import android.widget.ProgressBar

object Utils {
    const val EXTRA_SESSION = "extra_session"
    const val PREDICT_MOOD = "predict_mood"
    const val MOOD_QUOTE = "quote"
    const val RECOMEN_PLACE= "recomen"

    const val LIST_PLACE = "list_place"
    fun showLoading(isLoading: Boolean, progressBar: ProgressBar) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    const val EXTRA_ID = "extra_id"
}