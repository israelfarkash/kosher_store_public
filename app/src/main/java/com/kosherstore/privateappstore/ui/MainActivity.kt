package com.kosherstore.privateappstore.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
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

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            Snackbar.make(binding.root, R.string.notification_permission_denied, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun attachBaseContext(newBase: android.content.Context) {
        val prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(newBase)
        val scaleLevel = prefs.getInt("ui_scale", 2)
        val fontScale = when(scaleLevel) {
            0 -> 0.85f
            1 -> 0.95f
            2 -> 1.0f
            3 -> 1.15f
            else -> 1.0f
        }
        val config = android.content.res.Configuration(newBase.resources.configuration)
        config.fontScale = fontScale
        config.setLayoutDirection(java.util.Locale("he", "IL"))
        val context = newBase.createConfigurationContext(config)
        super.attachBaseContext(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeInstallEvents()
        requestNotificationPermissionIfNeeded()
        requestIgnoreBatteryOptimizationsIfNeeded()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        return navHostFragment.navController.navigateUp() || super.onSupportNavigateUp()
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

    private fun requestIgnoreBatteryOptimizationsIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                MaterialAlertDialogBuilder(this)
                    .setTitle("אופטימיזציית סוללה")
                    .setMessage("כדי להבטיח הורדות תקינות ברקע, יש לאשר החרגה מאופטימיזציית סוללה.")
                    .setPositiveButton("אשר") { _, _ ->
                        try {
                            val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                                data = Uri.parse("package:$packageName")
                            }
                            startActivity(intent)
                        } catch (e: Exception) {
                            // Some devices might not support the direct intent, fallback to settings
                            val intent = Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
                            startActivity(intent)
                        }
                    }
                    .setNegativeButton("לא עכשיו", null)
                    .show()
            }
        }
    }
}
