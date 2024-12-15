package app.culturedev.cultureconnect.helper

import android.view.View
import android.widget.ProgressBar

object Utils {
    const val EXTRA_SESSION = "extra_session"
    const val PREDICT_MOOD = "predict_mood"
    const val MOOD_QUOTE = "quote"
    const val RECOMEN_PLACE= "recomen"
    const val EXTRA_IMAGE = "extra_image"
    const val EXTRA_CAFE_NAME = "extra_cafename"
    const val EXTRA_ADDRESS = "extra_address"
    const val EXTRA_PRICE = "extra_price"
    const val EXTRA_RATING ="extra_rating"
    const val LIST_PLACE = "list_place"
    const val EXTRA_SCHEDULE = "extra_schedule"
    const val EXTRA_MENU = "extra_menu"
    fun showLoading(isLoading: Boolean, progressBar: ProgressBar) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    const val EXTRA_ID = "extra_id"
}