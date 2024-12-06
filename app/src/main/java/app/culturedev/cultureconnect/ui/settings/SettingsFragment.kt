package app.culturedev.cultureconnect.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.culturedev.cultureconnect.databinding.FragmentSettingsBinding
import app.culturedev.cultureconnect.helper.ColorUtils
import app.culturedev.cultureconnect.helper.NetworkUtil
import app.culturedev.cultureconnect.ui.auth.login.LoginActivity
import app.culturedev.cultureconnect.ui.splash.WelcomeActivity


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!NetworkUtil.isOnline(requireContext())) {
            NetworkUtil.netToast(requireContext())
        }
        ColorUtils.changeStatusBarColor(requireActivity().window, "#193D31")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.btnLogout.setOnClickListener {
            startActivity(Intent(requireContext(), WelcomeActivity::class.java))
            requireActivity().finish()
        }
    }
}