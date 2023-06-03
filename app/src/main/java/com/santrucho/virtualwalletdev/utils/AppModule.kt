package com.santrucho.virtualwalletdev.utils

import android.content.Context
import androidx.room.Room
import com.santrucho.virtualwalletdev.data.local.WalletDatabase
import com.santrucho.virtualwalletdev.domain.card.AddCardUseCase
import com.santrucho.virtualwalletdev.repository.card.CardRepository
import com.santrucho.virtualwalletdev.repository.card.DefaultCardRepository
import com.santrucho.virtualwalletdev.repository.movement.DefaultMovementRepository
import com.santrucho.virtualwalletdev.repository.movement.MovementRepository
import com.santrucho.virtualwalletdev.utils.Constants.CARDS_TABLE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideAddCardUseCase(cardRepository: CardRepository): AddCardUseCase {
        return AddCardUseCase(cardRepository)
    }


    @Provides
    fun provideMovementRepository(implementation: DefaultMovementRepository) : MovementRepository = implementation

    @Provides
    fun provideCardRepository(implementation : DefaultCardRepository) : CardRepository = implementation

    @Provides
    fun provideMovementDao(walletDB: WalletDatabase) = walletDB.movementDao()

    @Provides
    fun provideCardDao(walletDB:WalletDatabase) = walletDB.cardDao()

    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(context,WalletDatabase::class.java,CARDS_TABLE).build()

}