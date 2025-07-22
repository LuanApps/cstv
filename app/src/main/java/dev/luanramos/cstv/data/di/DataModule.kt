package dev.luanramos.cstv.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.luanramos.cstv.data.datasource.CsgoApiService
import dev.luanramos.cstv.data.repository.MatchRepositoryImpl
import dev.luanramos.cstv.data.repository.PlayerRepositoryImpl
import dev.luanramos.cstv.domain.repository.MatchRepository
import dev.luanramos.cstv.domain.repository.PlayerRepository

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideMatchRepository(
        apiService: CsgoApiService): MatchRepository {
        return MatchRepositoryImpl(apiService)
    }

    @Provides
    fun providePlayerRepository(
        apiService: CsgoApiService
    ): PlayerRepository{
        return PlayerRepositoryImpl(apiService)
    }

}