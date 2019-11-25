package com.awesome.deux.di

import com.awesome.deux.App
import com.awesome.deux.action.post.PostActionCreator
import com.awesome.deux.data.api.PostApi
import com.awesome.deux.data.api.UserApi
import com.awesome.deux.data.repository.PostRepository
import com.awesome.deux.data.repository.UserRepository
import com.awesome.deux.flux.Dispatcher
import com.awesome.deux.ui.post.PostStore
import kotlinx.serialization.UnstableDefault
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Dependency Module for Koin.
 */
object AppModule {

    /**
     * Module Instance.
     */
    val instance = module {
        // ViewModel Module
        // This class must be subclass of AndroidX ViewModel.
        viewModel {
            PostStore(
                androidApplication() as App,
                get()
            )
        }

        // Dispatcher Module
        // This class must managed by a singleton.
        single { Dispatcher() }

        // ActionCreator Module
        // This class must managed by a factory. Factory is always recreated.
        factory { PostActionCreator(get(), get(), get()) }

        // Repository Module
        // This class must managed by a singleton.
        single { PostRepository(get()) }
        single { UserRepository(get()) }

        // Network Module
        // This class must managed by a singleton.
        single { NetworkModule.provideOkHttpClient(androidApplication()) }
        single<PostApi> { NetworkModule.provideRetrofitApi(get()) }
        single<UserApi> { NetworkModule.provideRetrofitApi(get()) }
    }
}
