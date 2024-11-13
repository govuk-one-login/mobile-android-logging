package uk.gov.logging.testdouble.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.gov.logging.api.CrashLogger
import uk.gov.logging.testdouble.FakeCrashLogger
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface CrashLoggerModule {

    @Binds
    @Singleton
    fun bindsCrashLogger(logger: FakeCrashLogger): CrashLogger
}
