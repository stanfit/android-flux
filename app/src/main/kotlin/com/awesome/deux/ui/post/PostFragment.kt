package com.awesome.deux.ui.post

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.awesome.deux.R
import com.awesome.deux.action.post.PostActionCreator
import com.awesome.deux.ext.groupAdapter
import com.awesome.deux.ext.observeNotNull
import com.awesome.deux.ext.setGroupOnItemClickListener
import com.awesome.deux.ui.component.RecyclerViewStatefulObserver
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.net.UnknownHostException

/**
 * Post List Screen. [Fragment] subclass.
 */
class PostFragment : Fragment() {

    /**
     * Post List Screen's Store
     */
    private val store: PostStore by viewModel()

    /**
     * Post Action
     */
    private val postActionCreator: PostActionCreator by inject()

    // region Lifecycle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        bindInput()
        bindOutput()
    }

    // endregion

    /**
     * View initial settings
     */
    private fun initialize() {
        recyclerView.adapter = groupAdapter.apply {
            setGroupOnItemClickListener<PostItem> { item, _ ->
                // findNavController().navigate(PostFragmentDirections.actionToPostDetail(item.post))
            }
        }
        fab.setOnClickListener {
            showActionDialog()
        }
    }

    /**
     * Instruction from View to ViewModel
     */
    private fun bindInput() {
        lifecycleScope.launchWhenCreated {
            postActionCreator.fetchPosts()
        }
    }

    /**
     * Instruction from ViewModel to View
     */
    private fun bindOutput() {
        val observer = RecyclerViewStatefulObserver(recyclerView, emptyView, progressBar)
        store.posts.observeNotNull(viewLifecycleOwner) { result ->
            observer.invalidate()
            result.onSuccess {
                val items = PostItem.from(it)
                recyclerView.groupAdapter?.update(items)
            }
            result.onFailure {
                when (it) {
                    is UnknownHostException -> Timber.e("Happen UnknownHostException!!!")
                    is TimeoutCancellationException -> Timber.e("Happen TimeoutCancellationException!!!")
                }
            }
        }
    }

    /**
     * Show dialog for debug
     */
    private fun showActionDialog() {
        val items = mapOf(
            "Submit" to DialogInterface.OnClickListener { _, _ ->
                GlobalScope.launch {
                    postActionCreator.submitPost(
                        title = "Sample Title",
                        body = "Sample Body",
                        userId = 1
                    )
                }
            },
            "Update" to DialogInterface.OnClickListener { _, _ ->
                GlobalScope.launch {
                    postActionCreator.updatePost(
                        id = 1,
                        title = "Sample Title",
                        body = "Sample Body"
                    )
                }
            },
            "Delete" to DialogInterface.OnClickListener { _, _ ->
                GlobalScope.launch {
                    postActionCreator.deletePost(
                        id = 1
                    )
                }
            }
        )
        AlertDialog.Builder(requireContext())
            .setItems(items.keys.toTypedArray()) { dialog, which ->
                items.entries.toList()[which].value.onClick(dialog, which)
            }
            .show()
    }
}
