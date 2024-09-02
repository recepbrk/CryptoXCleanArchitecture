package com.recepguzel.cryptoxcleanarchitecture.ui.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.recepguzel.cryptoxcleanarchitecture.databinding.FragmentProfileBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private var doubleBackToExitPressedOnce = false

    companion object {
        private const val TAG = "ProfileFragment"
        private const val PREFS_NAME = "MyAppPreferences"
        private const val IMAGE_PATH_KEY = "profile_image_path"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLaunchers()
        setupUIActions()
        loadImageFromSharedPreferences()
        handleBackPress()
    }

    private fun setupLaunchers() {
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                openGallery()
            } else {
                showPermissionDeniedDialog()
            }
        }

        galleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUri = result.data?.data
                selectedImageUri?.let {
                    handleImageSelection(it)
                }
            }
        }
    }

    private fun setupUIActions() {
        binding.uploadImage.setOnClickListener {
            handleImageUploadClick()
        }

        binding.cardLogOut.setOnClickListener {
            showLogoutDialog()
        }

        binding.cardViewResetPassword.setOnClickListener {
            navigateToResetPassword()
        }
    }

    private fun handleImageUploadClick() {
        Log.d(TAG, "uploadImage clicked")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            openGallery()
        } else {
            checkStoragePermission()
        }
    }

    private fun checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "Requesting permission")
            permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            openGallery()
        }
    }

    private fun openGallery() {
        Log.d(TAG, "Opening gallery")
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        galleryLauncher.launch(intent)
    }

    private fun handleImageSelection(uri: Uri) {
        val imagePath = saveImageToInternalStorage(uri)
        if (imagePath != null) {
            binding.uploadImage.setImageURI(Uri.parse(imagePath))
            saveImagePathToSharedPreferences(imagePath)
        }
    }

    private fun saveImageToInternalStorage(uri: Uri): String? {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val file = File(requireContext().filesDir, "profile_image.jpg")
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun saveImagePathToSharedPreferences(imagePath: String) {
        val sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString(IMAGE_PATH_KEY, imagePath)
            apply()
        }
    }

    private fun loadImageFromSharedPreferences() {
        val sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val imagePath = sharedPreferences.getString(IMAGE_PATH_KEY, null)
        imagePath?.let {
            val file = File(it)
            if (file.exists()) {
                binding.uploadImage.setImageURI(Uri.fromFile(file))
            } else {
                Log.d(TAG, "No saved image file found.")
            }
        } ?: Log.d(TAG, "No saved image.")
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Storage Permission Required")
            .setMessage("You cannot select images from the gallery as storage permission is not granted. Please grant permission manually from settings.")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .setNegativeButton("Settings") { dialog, _ ->
                dialog.dismiss()
                openAppSettings()
            }
            .show()
    }

    private fun openAppSettings() {
        val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", requireContext().packageName, null)
        }
        startActivity(intent)
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Log Out?")
            .setMessage("Are you sure you want to log out?")
            .setPositiveButton("Yes") { dialog, _ ->
                dialog.dismiss()
                logout()
            }
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun logout() {
        requireActivity().finish()
    }

    private fun navigateToResetPassword() {
        val action = ProfileFragmentDirections.actionProfileFragmentToForgotPasswordFragment()
        findNavController().navigate(action)
    }

    private fun handleBackPress() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (doubleBackToExitPressedOnce) {
                        activity?.finish()
                    } else {
                        doubleBackToExitPressedOnce = true
                        Toast.makeText(
                            requireContext(),
                            "Press back again to exit.",
                            Toast.LENGTH_SHORT
                        ).show()

                        Handler(Looper.getMainLooper()).postDelayed({
                            doubleBackToExitPressedOnce = false
                        }, 2000)
                    }
                }
            })
    }
}
