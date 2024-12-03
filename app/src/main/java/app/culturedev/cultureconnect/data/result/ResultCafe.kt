package app.culturedev.cultureconnect.data.result

sealed class ResultCafe<out R> private constructor() {
    data class Success<out T>(val data: T) : ResultCafe<T>()
    data class Error(val error: String) : ResultCafe<Nothing>()
    data object Loading:ResultCafe<Nothing>()
}