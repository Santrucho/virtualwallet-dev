package com.santrucho.virtualwalletdev.utils

import android.content.Context
import androidx.room.Room
import com.santrucho.virtualwalletdev.data.local.WalletDatabase
import com.santrucho.virtualwalletdev.domain.AddCardUseCase
import com.santrucho.virtualwalletdev.repository.CardRepository
import com.santrucho.virtualwalletdev.repository.DefaultCardRepository
import com.santrucho.virtualwalletdev.utils.Constants.WALLET_TABLE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideAddCardUseCase(cardRepository: CardRepository): AddCardUseCase {
        return AddCardUseCase(cardRepository)
    }

    @Provides
    fun provideCardRepository(implementation : DefaultCardRepository) : CardRepository = implementation

    @Provides
    fun provideCardDao(walletDB:WalletDatabase) = walletDB.cardDao()

    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(context,WalletDatabase::class.java,WALLET_TABLE).build()

}