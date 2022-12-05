package e.hr.mks.utils

import android.app.Activity
import android.content.Intent
import androidx.navigation.findNavController
import e.hr.mks.R
import e.hr.mks.auth.view.AuthActivity
import e.hr.mks.home.MainActivity

object NavigationHelper {

    fun moveToNextScreen(from: String, to: String, context: Activity){
        when(from){
            AppConstants.SPLASH_SCREEN -> when(to){
                AppConstants.HOME_SCREEN -> {
                    val newIntent = Intent(context, MainActivity::class.java)
                    context.startActivity(newIntent)
                }
                AppConstants.AUTH_SCREEN -> {
                    val newIntent = Intent(context, AuthActivity::class.java)
                    context.startActivity(newIntent)
                }
            }
            AppConstants.AUTH_SCREEN -> when(to){
                AppConstants.HOME_SCREEN -> {
                    val newIntent = Intent(context, MainActivity::class.java)
                    context.startActivity(newIntent)
                }
            }
            AppConstants.HOME_SCREEN -> when(to){
                AppConstants.AUTH_SCREEN -> {
                    val newIntent = Intent(context, AuthActivity::class.java)
                    context.startActivity(newIntent)
                }
            }
        }

    }

    fun navigateToDest(context: Activity, navFragId: Int, to: Int){
        context.findNavController(navFragId).navigate(to)
    }
}