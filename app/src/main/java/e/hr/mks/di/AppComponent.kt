package e.hr.mks.di

import dagger.Component
import dagger.android.AndroidInjectionModule
import e.hr.mks.MyApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, FragmentModule::class, ActivityModule::class, AppModule::class])
interface AppComponent {
    fun inject(appClass: MyApplication)
}