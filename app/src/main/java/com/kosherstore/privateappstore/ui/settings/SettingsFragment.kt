package com.kosherstore.privateappstore.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kosherstore.privateappstore.BuildConfig
import com.google.android.material.snackbar.Snackbar
import com.kosherstore.privateappstore.R
import com.kosherstore.privateappstore.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val KEY_INSTALL_TYPE = "install_type"
        const val INSTALL_TYPE_NORMAL = "normal"
        const val INSTALL_TYPE_SILENT = "silent"
        const val INSTALL_TYPE_SESSION = "session"
        
        const val KEY_UI_SCALE = "ui_scale"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupInstallTypeOptions()
        setupUiScaleOptions()
        loadCurrentSettings()
        binding.textAppVersion.text = getString(R.string.settings_about_version_fmt, BuildConfig.VERSION_NAME)
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupInstallTypeOptions() {
        binding.radioGroupInstallType.setOnCheckedChangeListener { _, checkedId ->
            val installType = when (checkedId) {
                R.id.radioNormal -> INSTALL_TYPE_NORMAL
                R.id.radioSilent -> INSTALL_TYPE_SILENT
                R.id.radioSession -> INSTALL_TYPE_SESSION
                else -> INSTALL_TYPE_NORMAL
            }
            saveInstallType(installType)
        }
    }

    private var isProgrammaticCheck = true

    private fun setupUiScaleOptions() {
        binding.radioGroupUiScale.setOnCheckedChangeListener { _, checkedId ->
            if (isProgrammaticCheck) return@setOnCheckedChangeListener
            val scale = when (checkedId) {
                R.id.radioScale0 -> 0
                R.id.radioScale1 -> 1
                R.id.radioScale2 -> 2
                R.id.radioScale3 -> 3
                else -> 2
            }
            sharedPreferences.edit().putInt(KEY_UI_SCALE, scale).apply()
            requireActivity().recreate()
        }
    }

    private fun loadCurrentSettings() {
        isProgrammaticCheck = true
        val currentType = sharedPreferences.getString(KEY_INSTALL_TYPE, INSTALL_TYPE_NORMAL)
        val checkedId = when (currentType) {
            INSTALL_TYPE_SILENT -> R.id.radioSilent
            INSTALL_TYPE_SESSION -> R.id.radioSession
            else -> R.id.radioNormal
        }
        binding.radioGroupInstallType.check(checkedId)

        val currentScale = sharedPreferences.getInt(KEY_UI_SCALE, 2)
        val scaleCheckedId = when (currentScale) {
            0 -> R.id.radioScale0
            1 -> R.id.radioScale1
            2 -> R.id.radioScale2
            3 -> R.id.radioScale3
            else -> R.id.radioScale2
        }
        binding.radioGroupUiScale.check(scaleCheckedId)
        isProgrammaticCheck = false
    }

    private fun saveInstallType(type: String) {
        sharedPreferences.edit().putString(KEY_INSTALL_TYPE, type).apply()
        val message = when (type) {
            INSTALL_TYPE_SILENT -> getString(R.string.install_type_silent)
            INSTALL_TYPE_SESSION -> getString(R.string.install_type_session)
            else -> getString(R.string.install_type_normal)
        }
        Snackbar.make(binding.root, getString(R.string.settings_install_type_updated, message), Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}