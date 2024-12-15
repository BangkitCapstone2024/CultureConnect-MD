package app.culturedev.cultureconnect.ui.auth.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import app.culturedev.cultureconnect.data.result.ResultCafe
import app.culturedev.cultureconnect.databinding.ActivityLoginBinding
import app.culturedev.cultureconnect.helper.ColorUtils
import app.culturedev.cultureconnect.helper.NetworkUtil
import app.culturedev.cultureconnect.ui.auth.register.RegisterActivity
import app.culturedev.cultureconnect.ui.recomendation.DescribeMoodActivity
import app.culturedev.cultureconnect.ui.viewmodel.LoginViewModel
import app.culturedev.cultureconnect.ui.viewmodel.factory.FactoryViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel by viewModels<LoginViewModel> {
        FactoryViewModel.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ColorUtils.changeStatusBarColor(window, "#CC444B")
        binding.progressBar.visibility = View.INVISIBLE
        login()
        if (!NetworkUtil.isOnline(this)) {
            NetworkUtil.netToast(this)
        }
        animation()
        toRegister()
    }

    private fun toRegister() {
        binding.tvLinkRegis.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }

    private fun login() {
        binding.btnLogin.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Silahkan isi dengan benar !!", Toast.LENGTH_SHORT).show()
            } else {

                loginViewModel.handleLogin(username, password).observe(this) { result ->
                    when (result) {
                        is ResultCafe.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is ResultCafe.Success -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            Toast.makeText(this, "Login Success", Toast.LENGTH_LONG).show()
                            startActivity(
                                Intent(
                                    this@LoginActivity,
                                    DescribeMoodActivity::class.java
                                )
                            )
                            finish()
                        }

                        is ResultCafe.Error -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            Toast.makeText(this, "Error :${result.error}", Toast.LENGTH_SHORT)
                                .show()
                            MaterialAlertDialogBuilder(this).apply {
                                setTitle("Alert")
                                setMessage("Do you have have an account previously ? ")
                                    .setPositiveButton("Yes ,I have") { _, _ ->

                                    }
                                    .setNegativeButton("No,Create a account") { _, _ ->
                                        startActivity(
                                            Intent(
                                                this@LoginActivity,
                                                RegisterActivity::class.java
                                            )
                                        )
                                        finish()
                                    }
                                create()
                                show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun animation() {

        val title = ObjectAnimator.ofFloat(binding.loginTitle, View.ALPHA, 1f).setDuration(400)
        val emailLayout =
            ObjectAnimator.ofFloat(binding.loginEmailLayout, View.ALPHA, 1f).setDuration(400)
        val emailEdit =
            ObjectAnimator.ofFloat(binding.edtUsername, View.ALPHA, 1f).setDuration(400)
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