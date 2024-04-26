package uk.gov.logging.testdouble.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import uk.gov.logging.api.Logger
import uk.gov.logging.testdouble.SystemLogger

@InstallIn(SingletonComponent::class)
@Module
interface AndroidLoggerModule {
    @Binds
    @Singleton
    fun bindsAndroidLogger(logger: SystemLogger): Logger
}
