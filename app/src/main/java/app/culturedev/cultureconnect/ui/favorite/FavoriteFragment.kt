package app.culturedev.cultureconnect.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.culturedev.cultureconnect.databinding.FragmentFavoriteBinding
import app.culturedev.cultureconnect.helper.NetworkUtil
import app.culturedev.cultureconnect.helper.Utils
import app.culturedev.cultureconnect.ui.adapter.FavoriteAndHistoryAdapter
import app.culturedev.cultureconnect.ui.detail.DetailActivity
import app.culturedev.cultureconnect.ui.viewmodel.FavoriteViewModel
import app.culturedev.cultureconnect.ui.viewmodel.factory.FactoryViewModel

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!


    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAndHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!NetworkUtil.isOnline(requireContext())) {
            NetworkUtil.netToast(requireContext())
        }

        // Initialize Adapter with favorite action
        adapter = FavoriteAndHistoryAdapter(
            onItemClick = { cafe ->
                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra(Utils.EXTRA_ID, cafe.id)
                }
                startActivity(intent)
            },
            onFavoriteClick = { cafe ->
                favoriteViewModel.toggleFavorite(cafe) // Toggle favorite status in ViewModel
            }
        )

        // Setup RecyclerView
        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

//        // Observe favorite cafes list
//        favoriteViewModel.listCafeFavorite.observe(viewLifecycleOwner) { cafeList ->
//            if (!cafeList.isNullOrEmpty()) {
//                adapter.submitList(cafeList)
//            } else {
//                Toast.makeText(context, "No favorite cafes", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}