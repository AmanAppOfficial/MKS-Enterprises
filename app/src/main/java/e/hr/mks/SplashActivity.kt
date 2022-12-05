package e.hr.mks

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import dagger.android.AndroidInjection
import e.hr.mks.auth.network.AuthRepository
import e.hr.mks.firebaseUtility.FirebaseRemoteConfig
import e.hr.mks.utils.AppConstants
import e.hr.mks.utils.NavigationHelper
import e.hr.mks.utils.Workers.SalaryCacheExpirationWorker
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    lateinit var firebaseRemoteConfig: FirebaseRemoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_splash)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                if(!getMaintenanceMode()){

                    //delete expired salary cache
                    setUpWorker()

                    //check session logged in or not
                    val user = authRepository.getCurrUser()
                    if(user!=null)
                        NavigationHelper.moveToNextScreen(AppConstants.SPLASH_SCREEN, AppConstants.HOME_SCREEN, this@SplashActivity)
                    else
                        NavigationHelper.moveToNextScreen(AppConstants.SPLASH_SCREEN, AppConstants.AUTH_SCREEN, this@SplashActivity)

                }

                finish()
            }
        }, 3000)


    }

    private fun setUpWorker() {
        val deleteSalaryCacheExpirationWorker = PeriodicWorkRequestBuilder<SalaryCacheExpirationWorker>(12, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                AppConstants.SALARY_CACHE_DELETE_WORKER,
                ExistingPeriodicWorkPolicy.KEEP,
                deleteSalaryCacheExpirationWorker
            )

    }

    fun getMaintenanceMode(): Boolean{
        return firebaseRemoteConfig.getMaintenanceMode(this)
    }


    fun getmain(t: Any){

    }
}

