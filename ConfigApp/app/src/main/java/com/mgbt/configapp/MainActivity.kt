package com.mgbt.configapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mgbt.configapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var firstTime: Boolean = true

    companion object {
        const val VOLUME_LEVEL = "volume_level"
        const val KEY_DARK_MODE = "key_dark_mode"
        const val KEY_BLUETOOTH = "key_bluetooth"
        const val KEY_VIBRATION = "key_vibration"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CoroutineScope(Dispatchers.IO).launch {
            getSettings().filter { firstTime }.collect { settingsModel ->
                if (settingsModel != null) {
                    runOnUiThread {
                        binding.rsVolume.setValues(settingsModel.volume.toFloat())
                        binding.switchDarkMode.isChecked = settingsModel.darkMode
                        binding.switchBluetooth.isChecked = settingsModel.bluetooth
                        binding.switchVibration.isChecked = settingsModel.vibration
                        firstTime = !firstTime
                    }
                }
            }
        }
        initUI()
    }

    private fun initUI() {
        binding.rsVolume.addOnChangeListener{_, value, _ ->
            CoroutineScope(Dispatchers.IO).launch {
                saveVolume(value.toInt())
            }
        }

        binding.switchDarkMode.setOnCheckedChangeListener{_, value ->
            toggleDarkMode(value)
            CoroutineScope(Dispatchers.IO).launch {
                saveOptions(KEY_DARK_MODE, value)
            }
        }

        binding.switchBluetooth.setOnCheckedChangeListener{_, value ->
            CoroutineScope(Dispatchers.IO).launch {
                saveOptions(KEY_BLUETOOTH, value)
            }
        }

        binding.switchVibration.setOnCheckedChangeListener{_, value ->
            CoroutineScope(Dispatchers.IO).launch {
                saveOptions(KEY_VIBRATION, value)
            }
        }
    }

    private suspend fun saveVolume(value: Int) {
        dataStore.edit { preferences ->
            preferences[intPreferencesKey("volume_level")] = value
        }
    }

    private suspend fun saveOptions(key: String, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    private fun getSettings(): Flow<SettingsModel?> {
        return dataStore.data.map { preferences ->
            SettingsModel(
                volume = preferences[intPreferencesKey(VOLUME_LEVEL)] ?: 50,
                darkMode = preferences[booleanPreferencesKey(KEY_DARK_MODE)] ?: false,
                bluetooth = preferences[booleanPreferencesKey(KEY_BLUETOOTH)] ?: true,
                vibration = preferences[booleanPreferencesKey(KEY_VIBRATION)] ?: true
            )
        }
    }

    private fun toggleDarkMode(darkModeIsActivated: Boolean) {
        if (darkModeIsActivated) {
            enableDarkMode()
        } else {
            disableDarkMode()
        }
    }

    private fun enableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        delegate.applyDayNight()
    }

    private fun disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        delegate.applyDayNight()
    }
}