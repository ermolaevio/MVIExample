package com.example.ermolaevio.mviexample.domain

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class FormInteractor {

    private var value: Int = 0
    private var total: Int = 0

    fun increment(): Observable<PartialCounterState> {
        return wrapObservable(Observable.fromCallable {
            var newValue = value

            newValue++
            if (newValue > 3) throw IllegalArgumentException("the value must not be greater than 10")
            total++
            value = newValue
            CounterState(value, total) as PartialCounterState
        })
    }

    fun decrement(): Observable<PartialCounterState> {
        return wrapObservable(Observable.fromCallable {
            var newValue = value

            newValue--
            if (newValue < 0) throw IllegalArgumentException("the value must be greater than 0")
            total++
            value = newValue
            CounterState(value, total) as PartialCounterState
        })
    }

    private fun wrapObservable(o: Observable<PartialCounterState>): Observable<PartialCounterState> {
        return o
            .delay(4000, TimeUnit.MILLISECONDS)
            .startWith(CounterLoading)
            .onErrorReturn { CounterError(it.message ?: "error has occured") }
            .subscribeOn(Schedulers.io())
    }
}

