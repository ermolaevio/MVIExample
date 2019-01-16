package com.example.ermolaevio.mviexample.mvi

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.example.ermolaevio.mviexample.R
import com.example.ermolaevio.mviexample.domain.CounterViewState
import com.example.ermolaevio.mviexample.domain.FormInteractor
import com.hannesdorfmann.mosby3.mvi.MviActivity
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.form_activity.*

class FormActivity : MviActivity<FormView, FormPresenter>(), FormView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_activity)
        incrementCounterIntent()
    }

    override fun createPresenter(): FormPresenter {
        return FormPresenter(FormInteractor())
    }

    override fun incrementCounterIntent(): Observable<Unit> {
        return RxView.clicks(increment).map { Unit }
    }

    override fun decrementCounterIntent(): Observable<Unit> {
        return RxView.clicks(decrement).map { Unit }
    }

    override fun render(state: CounterViewState) {
        counter.text = state.value.toString()
        total.text = state.totalClicks.toString()

        if (state.showProgress.not()) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            progress.visibility = View.GONE
        } else {
            progress.visibility = View.VISIBLE
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        }

        state.error?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
    }

}