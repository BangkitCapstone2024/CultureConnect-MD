package app.culturedev.cultureconnect.ui.splash.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.culturedev.cultureconnect.databinding.FragmentSecondScreenBinding
import app.culturedev.cultureconnect.ui.MainActivity

class SecondScreenFragment : Fragment() {
    private lateinit var binding: FragmentSecondScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondScreenBinding.inflate(layoutInflater, container, false)
        afterOnBoarding()
        return binding.root
    }

    private fun afterOnBoarding() {
        binding.btnFinish.setOnClickListener {
            startActivity(Intent(context, MainActivity::class.java))
            onBoardingFinished()
        }
    }

    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        editor?.putBoolean("Finished", true)
        editor?.apply()
    }
}