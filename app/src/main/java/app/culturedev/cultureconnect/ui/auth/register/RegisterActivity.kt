package app.culturedev.cultureconnect.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import app.culturedev.cultureconnect.data.result.ResultCafe
import app.culturedev.cultureconnect.databinding.ActivityRegisterBinding
import app.culturedev.cultureconnect.helper.ColorUtils
import app.culturedev.cultureconnect.helper.NetworkUtil
import app.culturedev.cultureconnect.ui.recomendation.DescribeMoodActivity
import app.culturedev.cultureconnect.ui.viewmodel.RegisterViewModel
import app.culturedev.cultureconnect.ui.viewmodel.factory.FactoryViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val vm by viewModels<RegisterViewModel> {
        FactoryViewModel.getInstance(this)
    }

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
}