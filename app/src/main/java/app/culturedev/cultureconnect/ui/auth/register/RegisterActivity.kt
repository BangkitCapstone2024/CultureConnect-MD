package app.culturedev.cultureconnect.ui.auth.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import app.culturedev.cultureconnect.R
import app.culturedev.cultureconnect.data.result.ResultCafe
import app.culturedev.cultureconnect.databinding.ActivityRegisterBinding
import app.culturedev.cultureconnect.helper.ColorUtils
import app.culturedev.cultureconnect.helper.NetworkUtil
import app.culturedev.cultureconnect.ui.recomendation.DescribeMoodActivity
import app.culturedev.cultureconnect.ui.viewmodel.RegisterViewModel
import app.culturedev.cultureconnect.ui.viewmodel.factory.FactoryViewModel
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val vm by viewModels<RegisterViewModel> {
        FactoryViewModel.getInstance(this)
    }

    @SuppressLint("CheckResult")
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
        handleRegistration()
        animation()
        binding.progressBarRegister.visibility = View.INVISIBLE

        val passwordStream = RxTextView.textChanges(binding.edtRegisterPassword)
            .skipInitialValue()
            .map { password ->
                password.length < 8
            }
        passwordStream.subscribe {
            showPasswordExistsAlert(it)
        }

        val emailStream = RxTextView.textChanges(binding.edtRegisterEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            showEmailExistsAlert(it)
        }

        val passwordConfirmation = Observable.merge(
            RxTextView.textChanges(binding.edtRegisterPassword)
                .map { password ->
                    password.toString() != binding.edtRegisPasswordConfirm.text.toString()
                },
            RxTextView.textChanges(binding.edtRegisPasswordConfirm)
                .map { confir ->
                    confir.toString() != binding.edtRegisPasswordConfirm.text.toString()
                }
        )

        passwordConfirmation.subscribe {
            showConfirmPasswordAlert(it)
        }
    }

    private fun handleRegistration() {
        binding.btnRegister.setOnClickListener {
            val username = binding.edtRegisterName.text.toString()
            val email = binding.edtRegisterEmail.text.toString()
            val password = binding.edtRegisterPassword.text.toString()
            val confPassword = binding.edtRegisterPassword.text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confPassword.isEmpty() || confPassword != password) {
                Toast.makeText(this, "Silahkan isi data anda !!", Toast.LENGTH_SHORT).show()
            }
            vm.handleRegister(username, email, password).observe(this) { result ->
                when (result) {
                    is ResultCafe.Loading -> {
                        binding.progressBarRegister.visibility = View.VISIBLE
                    }

                    is ResultCafe.Success -> {
                        binding.progressBarRegister.visibility = View.INVISIBLE
                        Toast.makeText(
                            this,
                            "Selamat Datang di Culture Connect ",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(
                            Intent(
                                this@RegisterActivity,
                                DescribeMoodActivity::class.java
                            )
                        )
                        finish()
                    }

                    is ResultCafe.Error -> {
                        binding.progressBarRegister.visibility = View.INVISIBLE
                        Toast.makeText(this, "Error : ${result.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun navigateUp() {
        binding.btnBackLogin.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun showEmailExistsAlert(isEmail: Boolean) {
        binding.edtRegisterEmail.error = if (isEmail) getString(R.string.email_error) else null
    }

    private fun showPasswordExistsAlert(isPassword: Boolean) {
        binding.edtRegisterPassword.error =
            if (isPassword) getString(R.string.password_error) else null
    }

    private fun showUsernameExistsAlert(isValid: Boolean) {
        binding.edtRegisterName.error = if (isValid) getString(R.string.username_error) else null
    }

    private fun showConfirmPasswordAlert(isConfirm: Boolean) {
        binding.edtRegisPasswordConfirm.error =
            if (isConfirm) getString(R.string.confirpass_error) else null
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
            ObjectAnimator.ofFloat(binding.registerPasswordConfirmLayout, View.ALPHA, 1f)
                .setDuration(400)
        val confirmPasswordEdit =
            ObjectAnimator.ofFloat(binding.edtRegisPasswordConfirm, View.ALPHA, 1f).setDuration(400)
        val signup = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(400)

        val usernameAnimations = AnimatorSet().apply {
            playTogether(
                nameLayout, nameEdit
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