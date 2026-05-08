package com.kosherstore.privateappstore.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.kosherstore.privateappstore.R
import com.kosherstore.privateappstore.data.install.InstallCoordinator
import com.kosherstore.privateappstore.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var installCoordinator: InstallCoordinator

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            Snackbar.make(binding.root, R.string.notification_permission_denied, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.navHostFragment)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.mainFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)

        observeInstallEvents()
        requestNotificationPermissionIfNeeded()
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.navHostFragment).navigateUp() || super.onSupportNavigateUp()
    }

    private fun observeInstallEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                installCoordinator.events.collect { event ->
                    when (event) {
                        is InstallCoordinator.InstallEvent.LaunchSystemInstaller -> startActivity(event.intent)
                        is InstallCoordinator.InstallEvent.Success -> showInstallSuccessDialog(event.appName, event.packageName)
                        is InstallCoordinator.InstallEvent.Failure -> Snackbar
                            .make(binding.root, event.message, Snackbar.LENGTH_LONG)
                            .show()
                        is InstallCoordinator.InstallEvent.Info -> Snackbar
                            .make(binding.root, event.message, Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

    private fun showInstallSuccessDialog(appName: String, packageName: String) {
        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.install_success_title, appName))
            .setMessage(R.string.install_success_message)
            .setPositiveButton(
                if (launchIntent != null) R.string.open_now else android.R.string.ok
            ) { _, _ ->
                launchIntent?.let(::startActivity)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return
        }
        val granted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
        if (!granted) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}
