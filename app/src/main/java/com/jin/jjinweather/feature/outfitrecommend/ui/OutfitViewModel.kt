package com.jin.jjinweather.feature.outfitrecommend.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.jjinweather.feature.outfit.domain.Outfit
import com.jin.jjinweather.feature.weather.domain.usecase.GetOutfitRecommendUseCase
import com.jin.jjinweather.feature.weather.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OutfitViewModel(
    private val getOutfitRecommendUseCase: GetOutfitRecommendUseCase,
) : ViewModel() {

    private val _outfitState = MutableStateFlow<UiState<Outfit>>(UiState.Loading)
    val outfitState: StateFlow<UiState<Outfit>> = _outfitState

    fun loadOutfitByPageNumber(pageNumber: Int) {
        viewModelScope.launch {
            _outfitState.value = getOutfitRecommendUseCase(pageNumber).fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }
}
