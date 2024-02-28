package com.exercice.technicaltest.di

import com.exercice.technicaltest.data.local.dao.ProductDao
import com.exercice.technicaltest.data.remote.RemoteApi
import com.exercice.technicaltest.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideProductRepositoryImpl(
        remoteApi: RemoteApi,
        productDao: ProductDao,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): ProductRepository {
        return provideProductRepositoryImpl(remoteApi, productDao, ioDispatcher)
    }
}