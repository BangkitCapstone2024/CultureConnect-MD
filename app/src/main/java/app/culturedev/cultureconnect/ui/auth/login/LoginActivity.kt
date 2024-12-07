package app.culturedev.cultureconnect.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
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
                            Toast.makeText(this, "Sukses Login", Toast.LENGTH_LONG).show()
                            startActivity(
                                Intent(
                                    this@LoginActivity,
                                    DescribeMoodActivity::class.java
                                )
                            )
                        }

                        is ResultCafe.Error -> {
                            binding.progressBar.visibility = View.INVISIBLE
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "CustomAuthActivity"
    }

}