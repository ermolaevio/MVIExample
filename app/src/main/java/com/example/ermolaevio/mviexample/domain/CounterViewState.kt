package com.example.ermolaevio.mviexample.domain


data class CounterViewState private constructor(
    val value: Int, val totalClicks: Int,
    val error: String?, val showProgress: Boolean
) {

    companion object {
        fun builder(): Builder = Builder()
    }

    private constructor(builder: Builder) : this(
        builder.value, builder.total,
        builder.error, builder.showProgress
    )

    fun toBuilder(): Builder {
        return Builder(this)
    }

    class Builder() {
        var value: Int = 0
            private set
        var total: Int = 0
            private set
        var error: String? = null
            private set
        var showProgress: Boolean = false
            private set

        constructor(state: CounterViewState) : this() {
            value = state.value
            total = state.totalClicks
            error = state.error
            showProgress = state.showProgress
        }

        fun value(newValue: Int) = apply { value = newValue }
        fun total(newTotal: Int) = apply { total = newTotal }
        fun progress(show: Boolean) = apply { showProgress = show }
        fun error(newError: String?) = apply { error = newError }

        fun build(): CounterViewState {
            return CounterViewState(this)
        }
    }
}

sealed class PartialCounterState
object CounterLoading : PartialCounterState()
object CounterHideError : PartialCounterState()
data class CounterError(val error: String) : PartialCounterState()
data class CounterState(val value: Int, val totalCLicks: Int) : PartialCounterState()
