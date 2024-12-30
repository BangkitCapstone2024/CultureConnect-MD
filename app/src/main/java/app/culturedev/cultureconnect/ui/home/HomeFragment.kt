package app.culturedev.cultureconnect.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import app.culturedev.cultureconnect.R
import app.culturedev.cultureconnect.data.response.ListDataItem
import app.culturedev.cultureconnect.data.result.ResultCafe
import app.culturedev.cultureconnect.databinding.FragmentHomeBinding
import app.culturedev.cultureconnect.helper.ColorUtils
import app.culturedev.cultureconnect.helper.NetworkUtil
import app.culturedev.cultureconnect.ui.adapter.Adapter
import app.culturedev.cultureconnect.ui.allCafe.AllCafeActivity
import app.culturedev.cultureconnect.ui.adapter.ListCafeAdapter
import app.culturedev.cultureconnect.ui.adapter.RecommenderAdapter
import app.culturedev.cultureconnect.ui.auth.login.LoginActivity
import app.culturedev.cultureconnect.ui.history.HistoryActivity
import app.culturedev.cultureconnect.ui.notification.NotificationActivity
import app.culturedev.cultureconnect.ui.recomendation.CafeResultActivity
import app.culturedev.cultureconnect.ui.recomendation.DescribeMoodActivity
import app.culturedev.cultureconnect.ui.viewmodel.HomeViewModel
import app.culturedev.cultureconnect.ui.viewmodel.SharedViewModel
import app.culturedev.cultureconnect.ui.viewmodel.factory.RecommendationFactoryViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val vm by viewModels<HomeViewModel> { RecommendationFactoryViewModel.getInstance(requireContext()) }
    private var listCafe: ArrayList<ListDataItem> = ArrayList()
    private lateinit var recommendationAdapter: RecommenderAdapter
    private lateinit var allCafeAdapter: RecommenderAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!NetworkUtil.isOnline(requireContext())) {
            NetworkUtil.netToast(requireContext())
        }

        // Observe the recommendation data
        sharedViewModel.cafeRecommendations.observe(viewLifecycleOwner) { recommendations ->
            if (recommendations != null) {
                recommendationAdapter.submitList(recommendations)
            }
        }

        recommendationAdapter = RecommenderAdapter()
        allCafeAdapter = RecommenderAdapter()

        binding.rvRecommendation.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = recommendationAdapter
        }

        binding.rvAllCafe.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = allCafeAdapter

        }

//        vm.isLoading.observe(viewLifecycleOwner) { isLoading ->
//            binding.recommendationProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//        }

        vm.listCafeRecommendation.observe(viewLifecycleOwner) {
            if (it != null) {
                recommendationAdapter.submitList(it)
            } else {
                Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show()
            }
        }

        vm.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.allCafeProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        vm.listAllCafe.observe(viewLifecycleOwner) {
            if (it != null) {
                allCafeAdapter.submitList(it)
            } else {
                Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show()
            }
        }

        ColorUtils.changeStatusBarColor(requireActivity().window, "#1B3E3B")
        setupSearchView()
        btnHistory()
        btnNotification()
        toMoodBased()
        getUsername()
        getAllCafe()
        btnSeeMoreRecommendation()
        btnSeeMoreAllCafe()
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val intent = Intent(requireContext(), AllCafeActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun btnHistory() {
        binding.btnHistory.setOnClickListener {
            vm.getSession().observe(viewLifecycleOwner) {
                if (it.sessionId.isEmpty()) {
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                }
            }
            startActivity(Intent(requireContext(), HistoryActivity::class.java))
        }
    }

    private fun btnNotification() {
        binding.btnNotification.setOnClickListener {
            vm.getSession().observe(viewLifecycleOwner) {
                if (it.sessionId.isEmpty()) {
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                }
            }
            startActivity(Intent(requireContext(), NotificationActivity::class.java))
        }
    }

    private fun toMoodBased() {
        binding.btnRecommendation.setOnClickListener {
            vm.getSession().observe(viewLifecycleOwner) {
                if (it.sessionId.isEmpty()) {
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(requireContext(), DescribeMoodActivity::class.java)
                    startActivity(intent)
                }
            }
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

    private fun btnSeeMoreRecommendation(){
        binding.seeMoreRecommendation.setOnClickListener {
            val intent = Intent(requireContext(), CafeResultActivity::class.java)
            startActivity(intent)
        }
    }

    private fun btnSeeMoreAllCafe(){
        binding.seeMoreAllCafe.setOnClickListener {
            val intent = Intent(requireContext(), AllCafeActivity::class.java)
            startActivity(intent)
        }
    }
}