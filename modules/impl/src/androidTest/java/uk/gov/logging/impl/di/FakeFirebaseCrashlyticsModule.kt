package uk.gov.logging.impl.di

import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton
import org.mockito.kotlin.mock

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [FirebaseCrashlyticsModule::class]
)
object FakeFirebaseCrashlyticsModule {
    @Provides
    @Singleton
    fun providesCrashlytics(): FirebaseCrashlytics = mock()
}
