package app.culturedev.cultureconnect.helper

import android.view.Window
import android.view.WindowManager

object ColorUtils {
    @Suppress("DEPRECATION")
    fun changeStatusBarColor(window: Window, color: String) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = android.graphics.Color.parseColor(color)
    }
}