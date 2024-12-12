package app.culturedev.cultureconnect.ui.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import app.culturedev.cultureconnect.data.model.UserModel
import app.culturedev.cultureconnect.data.remote.api.ApiConfig
import app.culturedev.cultureconnect.data.repository.CafeRepo
import app.culturedev.cultureconnect.data.response.DataRes
import app.culturedev.cultureconnect.data.response.ListDataItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllCafeViewModel(application: Application, private val repository: CafeRepo) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "AllCafeViewModel"
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listAllCafe = MutableLiveData<List<ListDataItem>?>()
    val listAllCafe: LiveData<List<ListDataItem>?> = _listAllCafe

    private var allCafesFromApi: List<ListDataItem>? = null

    // Filter parameters
    private var selectedCategories: List<String> = emptyList()
    private var selectedPriceRange: String? = null
    private var selectedRatingThreshold: Double = 0.0

    init {
        fetchAllCafe()
    }

    private fun fetchAllCafe() {
        _isLoading.value = true
        viewModelScope.launch {
            delay(4000)
            try {
                val result = ApiConfig.getApiService().getAllCafe()
                result.enqueue(object : Callback<DataRes> {
                    override fun onResponse(call: Call<DataRes>, response: Response<DataRes>) {
                        _isLoading.value = false
                        if (response.isSuccessful) {
                            allCafesFromApi = response.body()?.listData
                            _listAllCafe.value = allCafesFromApi
                        } else {
                            launch(Dispatchers.Main) {
                                Toast.makeText(getApplication(), "Failed to Fetch API", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<DataRes>, t: Throwable) {
                        launch(Dispatchers.Main) {
                            Toast.makeText(getApplication(), "Failed to load events: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Filters the cafes based on search query and current filter parameters.
     */
    fun filterCafes(query: String) {
        val filteredList = allCafesFromApi?.filter { cafe ->
            val matchesQuery = cafe.name?.contains(query, ignoreCase = true) ?: true
            val matchesCategory = selectedCategories.isEmpty() || selectedCategories.contains(cafe.category)
            val matchesPrice = selectedPriceRange == null || isPriceInRange(cafe.price)
            val matchesRating = (cafe.rating?.toDoubleOrNull() ?: 0.0) >= selectedRatingThreshold

            matchesQuery && matchesCategory && matchesPrice && matchesRating
        }
        _listAllCafe.value = filteredList
    }

    /**
     * Updates the selected category filters.
     */
    fun updateCategories(categories: List<String>) {
        selectedCategories = categories
        filterCafes("") // Apply filter with updated parameters
    }

    /**
     * Updates the selected price range filter.
     */
    fun updatePriceRange(priceRange: String?) {
        selectedPriceRange = priceRange
        filterCafes("") // Apply filter with updated parameters
    }

    /**
     * Updates the selected rating threshold.
     */
    fun updateRatingThreshold(rating: Double) {
        selectedRatingThreshold = rating
        filterCafes("") // Apply filter with updated parameters
    }

    /**
     * Checks if the given price string matches the selected price range.
     */
    private fun isPriceInRange(price: String?): Boolean {
        return when {
            price.isNullOrEmpty() -> false
            selectedPriceRange == "1–25k" -> price.contains("1–25")
            selectedPriceRange == "25–50k" -> price.contains("25–50")
            selectedPriceRange == "50–75k" -> price.contains("50–75")
            else -> false
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}
