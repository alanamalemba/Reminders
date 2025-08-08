package com.example.reminders

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.example.reminders.databinding.DialogEditRemmiderBinding
import com.example.reminders.databinding.FragmentPasswordsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PasswordsFragment : Fragment() {

    private lateinit var binding: FragmentPasswordsBinding
    private val preferences: SharedPreferences by lazy {
        requireActivity().getSharedPreferences(
            FILE_NAME, Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayValues()

        binding.cardViewWifi.setOnClickListener { showEditDialog(PREF_NAME_WIFI) }
        binding.cardViewTabletPin.setOnClickListener { showEditDialog(PREF_NAME_TABLET_PIN) }
        binding.cardViewBikeLock.setOnClickListener { showEditDialog(PREF_NAME_BIKE_LOCK) }
    }

    private fun showEditDialog(prefName: String) {
        val dialogBinding: DialogEditRemmiderBinding =
            DialogEditRemmiderBinding.inflate(requireActivity().layoutInflater)

        dialogBinding.editTextValue.setText(preferences.getString(prefName,null))
        MaterialAlertDialogBuilder(requireContext()).setTitle("Update Value")
            .setView(dialogBinding.root).setPositiveButton("Save") { _, _ ->
                preferences.edit {
                    putString(
                        prefName, dialogBinding.editTextValue.text.toString()
                    )
                }
                displayValues()

            }.setNegativeButton("Cancel") { _, _ ->

            }.show()
    }

    private fun displayValues() {
        binding.textViewWifiValue.text = preferences.getString(PREF_NAME_WIFI, null)
        binding.textViewTabletPinValue.text = preferences.getString(PREF_NAME_TABLET_PIN, null)
        binding.textViewBikeLockValue.text = preferences.getString(PREF_NAME_BIKE_LOCK, null)
    }

    companion object {
        const val FILE_NAME = "passwords"
        const val PREF_NAME_WIFI = "pref_wifi"
        const val PREF_NAME_TABLET_PIN = "pref_tablet_pin"
        const val PREF_NAME_BIKE_LOCK = "pref_bike_lock"
    }
}