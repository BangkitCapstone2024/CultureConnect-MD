package app.culturedev.cultureconnect.ui.auth.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import app.culturedev.cultureconnect.databinding.ActivityLoginBinding
import app.culturedev.cultureconnect.helper.ColorUtils
import app.culturedev.cultureconnect.helper.NetworkUtil
import app.culturedev.cultureconnect.ui.auth.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private var customToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toRegister()
        ColorUtils.changeStatusBarColor(window, "#CC444B")

        if (!NetworkUtil.isOnline(this)) {
            NetworkUtil.netToast(this)
        }
        auth = Firebase.auth
    }

    private fun toRegister() {
        binding.tvLinkRegis.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
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

        val title = ObjectAnimator.ofFloat(binding.loginTitle, View.ALPHA, 1f).setDuration(400)
        val emailLayout =
            ObjectAnimator.ofFloat(binding.loginEmailLayout, View.ALPHA, 1f).setDuration(400)
        val emailEdit =
            ObjectAnimator.ofFloat(binding.edtEmail, View.ALPHA, 1f).setDuration(400)
        val passwordLayout =
            ObjectAnimator.ofFloat(binding.loginPasswordLayout, View.ALPHA, 1f).setDuration(400)
        val passwordEdit =
            ObjectAnimator.ofFloat(binding.edtPassword, View.ALPHA, 1f).setDuration(400)
        val login = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(400)
        val register =
            ObjectAnimator.ofFloat(binding.tvLinkRegis, View.ALPHA, 1f).setDuration(400)

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

        AnimatorSet().apply {
            playSequentially(
                title,
                emailAnimations,
                passwordAnimations,
                login,
                register
            )
            startDelay = 100
        }.start()
    }

    companion object {
        private const val TAG = "CustomAuthActivity"
    }

}