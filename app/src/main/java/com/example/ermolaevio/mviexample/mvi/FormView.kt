package com.example.ermolaevio.mviexample.mvi

import com.example.ermolaevio.mviexample.domain.CounterViewState
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable


interface FormView : MvpView {
    fun incrementCounterIntent(): Observable<Unit>
    fun decrementCounterIntent(): Observable<Unit>
    fun render(state: CounterViewState)
}