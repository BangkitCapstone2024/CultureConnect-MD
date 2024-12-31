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
import app.culturedev.cultureconnect.R
import app.culturedev.cultureconnect.databinding.FragmentFavoriteBinding
import app.culturedev.cultureconnect.helper.NetworkUtil
import app.culturedev.cultureconnect.helper.Utils
import app.culturedev.cultureconnect.ui.adapter.Adapter
import app.culturedev.cultureconnect.ui.adapter.FavoriteAdapter
import app.culturedev.cultureconnect.ui.detail.DetailActivity
import app.culturedev.cultureconnect.ui.viewmodel.FavoriteViewModel
import app.culturedev.cultureconnect.ui.viewmodel.factory.FactoryViewModel

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

        val favoriteViewModel = ViewModelProvider(this, FactoryViewModel.getInstance(requireContext()))[FavoriteViewModel::class.java]
        val adapter = FavoriteAdapter { dataEntity ->
            dataEntity.id.let {
                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra(Utils.EXTRA_ID, it)
                }
                startActivity(intent)
            }
        }

        binding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        favoriteViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.favoriteProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        favoriteViewModel.listCafeFavorite.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.submitList(it)
            } else {
                Toast.makeText(context, "No favorite cafe", Toast.LENGTH_SHORT).show()
            }
        }
    }
}