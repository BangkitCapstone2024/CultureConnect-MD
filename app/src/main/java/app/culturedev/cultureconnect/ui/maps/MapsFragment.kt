package app.culturedev.cultureconnect.ui.maps

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
import app.culturedev.cultureconnect.databinding.FragmentMapsBinding
import app.culturedev.cultureconnect.ui.adapter.ListCafeAdapter
import app.culturedev.cultureconnect.ui.viewmodel.MapsViewModel
import app.culturedev.cultureconnect.ui.viewmodel.factory.RecommendationFactoryViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {
    private lateinit var binding: FragmentMapsBinding
    private val vm by viewModels<MapsViewModel> {
        RecommendationFactoryViewModel.getInstance(
            requireContext()
        )
    }
    private val callback = OnMapReadyCallback { googleMap ->
        val solo = LatLng(-7.5703141, 110.8201383)
        googleMap.addMarker(MarkerOptions().position(solo).title("Marker in Solo"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(solo))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        binding.mapsProgress.visibility = View.INVISIBLE
        fetchCafeLists()
    }

    private fun fetchCafeLists() {
        val cafeListAdapter = ListCafeAdapter()
        vm.getAllCafe().observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultCafe.Loading -> {
                    binding.mapsProgress.visibility = View.VISIBLE
                }

                is ResultCafe.Success -> {
                    binding.mapsProgress.visibility = View.INVISIBLE
                    cafeListAdapter.submitList(result.data.cafeData)
                    binding.mapsRecycler.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = cafeListAdapter
                    }
                }

                is ResultCafe.Error -> {
                    binding.mapsProgress.visibility = View.INVISIBLE
                    Toast.makeText(context, "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}