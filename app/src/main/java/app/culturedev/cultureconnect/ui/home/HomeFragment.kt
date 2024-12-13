package app.culturedev.cultureconnect.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import app.culturedev.cultureconnect.R
import app.culturedev.cultureconnect.data.result.ResultCafe
import app.culturedev.cultureconnect.databinding.FragmentHomeBinding
import app.culturedev.cultureconnect.helper.ColorUtils
import app.culturedev.cultureconnect.helper.NetworkUtil
import app.culturedev.cultureconnect.ui.adapter.ListCafeAdapter
import app.culturedev.cultureconnect.ui.auth.login.LoginActivity
import app.culturedev.cultureconnect.ui.recomendation.DescribeMoodActivity
import app.culturedev.cultureconnect.ui.viewmodel.HomeViewModel
import app.culturedev.cultureconnect.ui.viewmodel.factory.RecommendationFactoryViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val vm by viewModels<HomeViewModel> {
        RecommendationFactoryViewModel.getInstance(
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.actionBar?.hide()
        if (!NetworkUtil.isOnline(requireContext())) {
            NetworkUtil.netToast(requireContext())
        }

        ColorUtils.changeStatusBarColor(requireActivity().window, "#1B3E3B")
        toMoodBased()
        getUsername()
        getAllCafe()
        binding.allCafeProgressBar.visibility = View.INVISIBLE
    }

    private fun toMoodBased() {
        binding.btnRecommendation.setOnClickListener {
            vm.getSession().observe(viewLifecycleOwner) {
                if (it.sessionId.isEmpty()) {
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                }
            }
            startActivity(Intent(requireContext(), DescribeMoodActivity::class.java))
        }
    }

    private fun getAllCafe() {
        val listCafeAdapter = ListCafeAdapter()
        vm.getAllCafeData().observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultCafe.Loading -> {
                    binding.allCafeProgressBar.visibility = View.VISIBLE
                }

                is ResultCafe.Success -> {
                    binding.allCafeProgressBar.visibility = View.INVISIBLE
                    listCafeAdapter.submitList(result.data.cafeData?.take(10))
                    binding.rvAllCafe.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = listCafeAdapter
                    }
                }

                is ResultCafe.Error -> {
                    binding.allCafeProgressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getUsername() {
        vm.getSession().observe(viewLifecycleOwner) {
            if (it.sessionId.isEmpty()) {
                binding.username.text = getString(R.string.username)
            } else {
                binding.username.text = it.username
            }
        }
    }
}