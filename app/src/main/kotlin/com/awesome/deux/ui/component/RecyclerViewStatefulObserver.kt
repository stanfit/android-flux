package com.awesome.deux.ui.component

import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import timber.log.Timber

/**
 * Observe the state of RecyclerView.
 *
 * @property recyclerView RecyclerView.
 * @property emptyView View to display when RecyclerView is empty.
 * @property loadingView View to display when RecyclerView is loading.
 * @param isInitializedLoadingEnabled [loadingView] enabled initialization flag.
 */
class RecyclerViewStatefulObserver(
    private val recyclerView: RecyclerView,
    private val emptyView: View,
    private val loadingView: View,
    isInitializedLoadingEnabled: Boolean = true
) {

    init {
        recyclerView.visibility = View.GONE
        emptyView.visibility = View.GONE
        setLoading(isInitializedLoadingEnabled)
    }

    /**
     * Switch loading flag.
     *
     * @param enabled Boolean
     */
    fun setLoading(enabled: Boolean) {
        if (enabled) startLoading() else stopLoading()
    }

    /**
     * Start loading.
     */
    fun startLoading() {
        when (loadingView) {
            is SwipeRefreshLayout -> {
                loadingView.isRefreshing = true
            }
            is ProgressBar -> {
                loadingView.visibility = View.VISIBLE
            }
            else -> {
                Timber.e("Not supported loading view : ${loadingView::class.java.simpleName}")
            }
        }
    }

    /**
     * Stop loading.
     */
    fun stopLoading() {
        when (loadingView) {
            is SwipeRefreshLayout -> {
                loadingView.isRefreshing = false
            }
            is ProgressBar -> {
                loadingView.visibility = View.GONE
            }
            else -> {
                Timber.e("Not supported loading view : ${loadingView::class.java.simpleName}")
            }
        }
    }

    /**
     * Change state.
     */
    fun invalidate() {
        val adapter = recyclerView.adapter ?: return
        val itemCount = adapter.itemCount
        if (itemCount > 0) {
            recyclerView.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
        } else {
            recyclerView.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        }
        setLoading(false)
    }
}
