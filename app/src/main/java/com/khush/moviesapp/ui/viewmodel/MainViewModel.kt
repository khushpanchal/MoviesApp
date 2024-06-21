package com.khush.moviesapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khush.moviesapp.common.UIState
import com.khush.moviesapp.data.model.MainData
import com.khush.moviesapp.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _mainItem = MutableStateFlow<UIState<List<MainData>>>(UIState.Loading)
    val mainItem: StateFlow<UIState<List<MainData>>> = _mainItem

    init {
        fetchItems()
    }

    private fun fetchItems() {
        viewModelScope.launch {
            _mainItem.emit(UIState.Loading)
            mainRepository
                .getMainData()
                .flowOn(Dispatchers.IO)
                .catch {
                    _mainItem.emit(UIState.Failure(it))
                }
                .collect {
                    if(it.isEmpty()) _mainItem.emit(UIState.Failure(RuntimeException("Empty Data")))
                    else _mainItem.emit(UIState.Success(it))
                }
        }
    }

}