package app.culturedev.cultureconnect.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import app.culturedev.cultureconnect.R
import app.culturedev.cultureconnect.databinding.FragmentSettingsBinding
import app.culturedev.cultureconnect.helper.ColorUtils
import app.culturedev.cultureconnect.helper.NetworkUtil
import app.culturedev.cultureconnect.ui.viewmodel.SettingsViewModel
import app.culturedev.cultureconnect.ui.viewmodel.factory.FactoryViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val vm by viewModels<SettingsViewModel> {
        FactoryViewModel.getInstance(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!NetworkUtil.isOnline(requireContext())) {
            NetworkUtil.netToast(requireContext())
        }
        ColorUtils.changeStatusBarColor(requireActivity().window, "#193D31")
        logout()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun logout() {
        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext()).apply {
                setTitle("Alert")
                setMessage("Are you sure to logout ? ")
                    .setPositiveButton("Yes") { _, _ ->
                        vm.logoutPreferences()
                        findNavController().navigate(R.id.action_profile_to_home)
                    }
                    .setNegativeButton("No") { _, _ ->

                    }
                create()
                show()
            }
        }
    }
}