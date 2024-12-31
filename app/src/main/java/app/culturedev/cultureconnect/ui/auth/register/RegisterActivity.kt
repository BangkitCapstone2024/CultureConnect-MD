package app.culturedev.cultureconnect.ui.auth.register

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
        binding.progressBarRegister.visibility = View.INVISIBLE

        val passwordStream = RxTextView.textChanges(binding.edtRegisPassword)
            .skipInitialValue()
            .map { password ->
                password.length < 8
            }
        passwordStream.subscribe {
            showPasswordExistsAlert(it)
        }

        val emailStream = RxTextView.textChanges(binding.edtRegisEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            showEmailExistsAlert(it)
        }

        val passwordConfirmation = Observable.merge(
            RxTextView.textChanges(binding.edtRegisPassword)
                .map { password ->
                    password.toString() != binding.edtRegisPasswordConfir.text.toString()
                },
            RxTextView.textChanges(binding.edtRegisPasswordConfir)
                .map { confir ->
                    confir.toString() != binding.edtRegisPassword.text.toString()
                }
        )

        passwordConfirmation.subscribe {
            showConfirmPasswordAlert(it)
        }
    }

    private fun handleRegistration() {
        binding.btnConfirRegis.setOnClickListener {
            val username = binding.edtRegisUsername.text.toString()
            val email = binding.edtRegisEmail.text.toString()
            val password = binding.edtRegisPassword.text.toString()
            val confPassword = binding.edtRegisPasswordConfir.text.toString()

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
        binding.edtRegisEmail.error = if (isEmail) getString(R.string.email_error) else null
    }

    private fun showPasswordExistsAlert(isPassword: Boolean) {
        binding.edtRegisPassword.error =
            if (isPassword) getString(R.string.password_error) else null
    }

    private fun showUsernameExistsAlert(isValid: Boolean) {
        binding.edtRegisUsername.error = if (isValid) getString(R.string.username_error) else null
    }

    private fun showConfirmPasswordAlert(isConfirm: Boolean) {
        binding.edtRegisPasswordConfir.error =
            if (isConfirm) getString(R.string.confirpass_error) else null
    }
}