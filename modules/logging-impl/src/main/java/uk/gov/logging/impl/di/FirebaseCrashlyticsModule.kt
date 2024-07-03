package uk.gov.logging.impl.di

import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FirebaseCrashlyticsModule {
    @Provides
    @Singleton
    fun providesCrashlytics(): FirebaseCrashlytics = FirebaseCrashlytics.getInstance()
}
