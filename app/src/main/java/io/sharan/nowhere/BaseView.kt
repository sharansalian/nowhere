package io.sharan.nowhere

interface BaseView<T> {
    fun setPresenter(presenter: T)
}