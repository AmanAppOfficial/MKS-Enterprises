package e.hr.mks.firebaseUtility

import android.app.Activity
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import javax.inject.Inject

class FirebaseRemoteConfig @Inject constructor() {

    private var remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    init {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
    }

    /** get maintenance mode status from firebase config param
     * it will trigger update in 1 hour
     **/
    fun getMaintenanceMode(activity: Activity): Boolean{
        var isMaintenanceMode = false
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(activity) { task ->
                if(task.isSuccessful){
                    isMaintenanceMode = remoteConfig.getBoolean(FirebaseUtils.MAINTENANCE_MODE)
                }
            }
        return isMaintenanceMode
    }

}