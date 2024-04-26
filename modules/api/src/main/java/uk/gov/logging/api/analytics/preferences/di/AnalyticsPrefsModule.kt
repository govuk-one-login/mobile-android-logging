package uk.gov.logging.api.analytics.preferences.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import uk.gov.logging.api.analytics.preferences.AnalyticsPrefs
import uk.gov.logging.api.analytics.preferences.IAnalyticsPrefs

@InstallIn(ActivityRetainedComponent::class)
@Module
interface AnalyticsPrefsModule {
    @Binds
    @ActivityRetainedScoped
    fun bindsAnalyticsDenied(analyticsPrefs: AnalyticsPrefs): IAnalyticsPrefs
}
