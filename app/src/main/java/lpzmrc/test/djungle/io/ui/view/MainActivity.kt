package lpzmrc.test.djungle.io.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import lpzmrc.test.djungle.io.R
import lpzmrc.test.djungle.io.data.model.AuthenticationState

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val navController by lazy { findNavController(R.id.nav_host_fragment) }
    private val navigationView by lazy { findViewById<BottomNavigationView>(R.id.nav_view) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeAuthenticationState()
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    private fun observeAuthenticationState() {
        viewModel.authenticationState.observe(this, Observer { authenticationState ->
            when (authenticationState) {
                AuthenticationState.AUTHENTICATED -> {
                    navigationView.isVisible = true
                }
                else -> {
                    navigationView.isVisible = false
                    navController.navigate(R.id.startLogin)
                }
            }
        })
    }

    private fun setupBottomNavigationBar() {
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_gallery,
                R.id.navigation_todos,
                R.id.navigation_settings,
                R.id.navigation_login
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navigationView.setupWithNavController(navController)
    }
}
