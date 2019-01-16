package com.example.ermolaevio.mviexample.domain

sealed class MainViewState

//object LoadingViewState : MainViewState()
data class DataViewState(val items: List<String>) : MainViewState()
//data class ErrorViewState(val message: String) : MainViewState()