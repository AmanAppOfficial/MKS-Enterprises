package e.hr.mks.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import dagger.android.AndroidInjection
import e.hr.mks.R
import e.hr.mks.auth.network.AuthRepository
import e.hr.mks.databinding.ActivityMainBinding
import e.hr.mks.utils.AppConstants
import e.hr.mks.utils.NavigationHelper
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var authRepository: AuthRepository

    private val navController by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDrawerLayout()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, binding.drawerLayout)
    }

    private fun setupDrawerLayout() {
        binding.navView.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()

        }
    }
    override fun onStart() {
        super.onStart()

        val user = authRepository.getCurrUser()
        if(user == null)
            NavigationHelper.moveToNextScreen(AppConstants.HOME_SCREEN, AppConstants.AUTH_SCREEN, this@MainActivity)

    }
}