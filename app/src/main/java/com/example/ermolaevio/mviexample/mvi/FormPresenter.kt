package com.example.ermolaevio.mviexample.mvi

import com.example.ermolaevio.mviexample.domain.*
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class FormPresenter(private val interactor: FormInteractor) : MviBasePresenter<FormView, CounterViewState>() {

    override fun bindIntents() {
        val increment = intent(FormView::incrementCounterIntent)
            .switchMap { interactor.increment() }
//            .observeOn(AndroidSchedulers.mainThread())

        val decrement = intent(FormView::decrementCounterIntent)
            .switchMap { interactor.decrement() }

        val all = Observable.merge(increment, decrement)
            .switchMap { state ->
                if (state is CounterError)
                    Observable.timer(2, TimeUnit.SECONDS).map {

                        CounterHideError as PartialCounterState
                    }
                        .startWith(state)
                else Observable.just(state)
            }
            .observeOn(AndroidSchedulers.mainThread())


        val initial = CounterViewState.builder().build()
        subscribeViewState(all.scan(initial, ::reducer), FormView::render)
    }

    private fun reducer(
        oldState: CounterViewState, partialState: PartialCounterState
    ): CounterViewState {

        return when (partialState) {
            is CounterLoading -> oldState.toBuilder()
                .progress(true).error(null).build()

            is CounterError -> oldState.toBuilder()
                .error(partialState.error).progress(false).build()

            is CounterState -> oldState.toBuilder()
                .value(partialState.value).total(partialState.totalCLicks)
                .error(null).progress(false).build()
            is CounterHideError -> oldState.toBuilder().error(null).build()
        }
    }

}