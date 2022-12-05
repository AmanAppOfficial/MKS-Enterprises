package e.hr.mks

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import e.hr.mks.di.DaggerAppComponent
import javax.inject.Inject

class MyApplication: Application(), HasAndroidInjector {

    @Inject
    lateinit var mInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .build()
            .inject(this)

    }


    override fun androidInjector(): AndroidInjector<Any> {
        return mInjector
    }
}