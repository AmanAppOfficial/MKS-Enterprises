package e.hr.mks.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import e.hr.mks.SplashActivity
import e.hr.mks.home.MainActivity

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeSplashActivityInjector(): SplashActivity

    @ContributesAndroidInjector
    abstract fun contributeMainActivityInjector(): MainActivity

}