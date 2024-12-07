package app.culturedev.cultureconnect.ui.auth.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import app.culturedev.cultureconnect.databinding.ActivityRegisterBinding
import app.culturedev.cultureconnect.helper.ColorUtils
import app.culturedev.cultureconnect.helper.NetworkUtil
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private var customToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ColorUtils.changeStatusBarColor(window, "#CC444B")
        navigateUp()

        if (!NetworkUtil.isOnline(this)) {
            NetworkUtil.netToast(this)
        }

        auth = Firebase.auth
    }

    private fun navigateUp() {
        binding.btnBackLogin.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun startSignIn() {
        customToken?.let {
            auth.signInWithCustomToken(it)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithCustomToken:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        Log.w(TAG, "signInWithCustomToken:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        updateUI(null)
                    }
                }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
    }

    private fun animation() {
        val title = ObjectAnimator.ofFloat(binding.registerTitle, View.ALPHA, 1f).setDuration(400)
        val nameLayout =
            ObjectAnimator.ofFloat(binding.registerNameLayout, View.ALPHA, 1f).setDuration(400)
        val nameEdit =
            ObjectAnimator.ofFloat(binding.edtRegisterName, View.ALPHA, 1f).setDuration(400)
        val emailLayout =
            ObjectAnimator.ofFloat(binding.registerEmailLayout, View.ALPHA, 1f).setDuration(400)
        val emailEdit =
            ObjectAnimator.ofFloat(binding.edtRegisterEmail, View.ALPHA, 1f).setDuration(400)
        val passwordLayout =
            ObjectAnimator.ofFloat(binding.registerPasswordLayout, View.ALPHA, 1f).setDuration(400)
        val passwordEdit =
            ObjectAnimator.ofFloat(binding.edtRegisterPassword, View.ALPHA, 1f).setDuration(400)
        val confirmPasswordLayout =
            ObjectAnimator.ofFloat(binding.registerPasswordConfirmLayout, View.ALPHA, 1f).setDuration(400)
        val confirmPasswordEdit =
            ObjectAnimator.ofFloat(binding.edtRegisPasswordConfirm, View.ALPHA, 1f).setDuration(400)
        val signup = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(400)

        val usernameAnimations = AnimatorSet().apply {
            playTogether(
                nameLayout,nameEdit
            )
        }
        val emailAnimations = AnimatorSet().apply {
            playTogether(
                emailLayout, emailEdit
            )
        }

        val passwordAnimations = AnimatorSet().apply {
            playTogether(
                passwordLayout, passwordEdit
            )
        }

        val confirmPasswordAnimations = AnimatorSet().apply {
            playTogether(
                confirmPasswordLayout, confirmPasswordEdit
            )
        }

        AnimatorSet().apply {
            playSequentially(
                title,
                usernameAnimations,
                emailAnimations,
                passwordAnimations,
                confirmPasswordAnimations,
                signup
            )
            startDelay = 100
        }.start()
    }

    companion object {
        private const val TAG = "CustomAuthActivity"
    }
}