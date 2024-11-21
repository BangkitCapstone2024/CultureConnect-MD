package app.culturedev.cultureconnect.ui.splash.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.culturedev.cultureconnect.databinding.FragmentThirdScreenBinding
import app.culturedev.cultureconnect.ui.auth.login.LoginActivity

class ThirdScreenFragment : Fragment() {
    private lateinit var binding: FragmentThirdScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentThirdScreenBinding.inflate(layoutInflater, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.btnFinish.setOnClickListener {
            startActivity(Intent(context, LoginActivity::class.java))
        }
    }
}