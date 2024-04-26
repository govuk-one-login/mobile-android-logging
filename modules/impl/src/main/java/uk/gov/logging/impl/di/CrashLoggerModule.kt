package uk.gov.logging.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import uk.gov.logging.api.CrashLogger
import uk.gov.logging.impl.CrashlyticsLogger

@InstallIn(SingletonComponent::class)
@Module
interface CrashLoggerModule {

    @Binds
    @Singleton
    fun bindsCrashLogger(logger: CrashlyticsLogger): CrashLogger
}
