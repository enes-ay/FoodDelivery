sealed class ResultState {
    object Success : ResultState()
    data class Error(val message: String) : ResultState()
    object Loading : ResultState()
    object Idle : ResultState()
}
