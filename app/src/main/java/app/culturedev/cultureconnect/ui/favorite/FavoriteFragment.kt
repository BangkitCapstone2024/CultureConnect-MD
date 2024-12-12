package app.culturedev.cultureconnect.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.culturedev.cultureconnect.databinding.FragmentFavoriteBinding
import app.culturedev.cultureconnect.helper.NetworkUtil

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.actionBar?.hide()

        if (!NetworkUtil.isOnline(requireContext())) {
            NetworkUtil.netToast(requireContext())
        }

//        val favoriteViewModel = ViewModelProvider(this, FactoryViewModel.getInstance(requireContext()))[FavoriteViewModel::class.java]
//        val adapter = FavoriteAdapter { dataEntity ->
//            dataEntity.id.let {
//                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
//                    putExtra(Utils.EXTRA_ID, it)
//                }
//                startActivity(intent)
//            }
//        }
//
//        binding.rvFavorite.apply {
//            layoutManager = LinearLayoutManager(context)
//            this.adapter = adapter
//        }
//
//        favoriteViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
//            binding.favoriteProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//        }
//
//        favoriteViewModel.listCafeFavorite.observe(viewLifecycleOwner) { events ->
//            if (events != null) {
//                adapter.submitList(events)
//            } else {
//                Toast.makeText(context, "No favorite cafe", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
    }
}