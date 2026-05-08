package com.kosherstore.privateappstore.di

import com.kosherstore.privateappstore.data.repository.StoreAppRepositoryImpl
import com.kosherstore.privateappstore.domain.repository.StoreAppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindStoreAppRepository(
        impl: StoreAppRepositoryImpl
    ): StoreAppRepository
}
