package app.culturedev.cultureconnect.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import app.culturedev.cultureconnect.data.model.UserModel
import app.culturedev.cultureconnect.data.repository.RecommendationRepository
import app.culturedev.cultureconnect.data.response.listcafe.CafeDataItem

class AllCafeViewModel(private val repository: RecommendationRepository) : ViewModel() {

    companion object {
        private const val TAG = "AllCafeViewModel"
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listAllCafe = MutableLiveData<List<CafeDataItem>?>()
    val listAllCafe: LiveData<List<CafeDataItem>?> = _listAllCafe

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private var allCafesFromApi: List<CafeDataItem>? = null

    // Filter parameters
    private var selectedCategories: List<String> = emptyList()
    private var selectedPriceRange: String? = null
    private var selectedRatingThreshold: Double = 0.0

    // Filters cafes based on search query and current filter parameters
    fun filterCafes(query: String) {
        val filteredList = allCafesFromApi?.filter { cafe ->
            val matchesQuery = cafe.title?.contains(query, ignoreCase = true) ?: true
            val matchesCategory = selectedCategories.isEmpty() || selectedCategories.contains(cafe.category)
            val matchesPrice = selectedPriceRange == null || isPriceInRange(cafe.price)
            val matchesRating = (cafe.rating?.toDoubleOrNull() ?: 0.0) >= selectedRatingThreshold

            matchesQuery && matchesCategory && matchesPrice && matchesRating
        }
        _listAllCafe.value = filteredList
    }

    // Updates the selected category filters and re-applies the filter
    fun updateCategories(categories: List<String>) {
        selectedCategories = categories
        filterCafes("") // Apply filter with updated parameters
    }

    // Updates the selected price range filter and re-applies the filter
    fun updatePriceRange(priceRange: String?) {
        selectedPriceRange = priceRange
        filterCafes("") // Apply filter with updated parameters
    }

    // Updates the selected rating threshold and re-applies the filter
    fun updateRatingThreshold(rating: Double) {
        selectedRatingThreshold = rating
        filterCafes("") // Apply filter with updated parameters
    }

    // Checks if the given price string matches the selected price range
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

    // Returns the LiveData for All Cafe Data from the repository
    fun getAllCafeData() = repository.getAllCafeDate()
}
