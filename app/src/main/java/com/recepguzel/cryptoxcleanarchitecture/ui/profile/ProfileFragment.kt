package com.recepguzel.cryptoxcleanarchitecture.ui.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.recepguzel.cryptoxcleanarchitecture.databinding.FragmentProfileBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>

    companion object {
        private const val TAG = "ProfileFragment"
        private const val PREFS_NAME = "MyAppPreferences"
        private const val IMAGE_PATH_KEY = "profile_image_path"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: Fragment oluşturuluyor")
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: Fragment görünümleri oluşturuldu")

        // Kaydedilen resmi yükleyin
        loadImageFromSharedPreferences()

        // İzin talebi sonuçlarını dinlemek için launcher tanımlama
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                openGallery()
            } else {
                showPermissionDeniedDialog()
            }
        }

        // Galeri seçim işlemini dinlemek için launcher tanımlama
        galleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUri = result.data?.data
                if (selectedImageUri != null) {
                    val imagePath = saveImageToInternalStorage(selectedImageUri)
                    if (imagePath != null) {
                        binding.uploadImage.setImageURI(Uri.parse(imagePath))
                        saveImagePathToSharedPreferences(imagePath)
                    }
                }
            }
        }

        binding.uploadImage.setOnClickListener {
            Log.d(TAG, "uploadImage tıklandı")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Android 10 (API 29) ve sonrası için izin gerekmez
                openGallery()
            } else {
                // Android 10 öncesi için izin kontrolü yapın
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    Log.d(TAG, "İzin talebi yapılıyor")
                    permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                } else {
                    openGallery()
                }
            }
        }
    }

    private fun openGallery() {
        Log.d(TAG, "openGallery: Galeri açılıyor")
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        galleryLauncher.launch(intent)
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
        val editor = sharedPreferences.edit()
        editor.putString(IMAGE_PATH_KEY, imagePath)
        editor.apply()
    }

    private fun loadImageFromSharedPreferences() {
        val sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val imagePath = sharedPreferences.getString(IMAGE_PATH_KEY, null)
        if (imagePath != null) {
            val file = File(imagePath)
            if (file.exists()) {
                binding.uploadImage.setImageURI(Uri.fromFile(file))
            } else {
                Log.d(TAG, "loadImageFromSharedPreferences: Kaydedilmiş resim dosyası bulunamadı.")
            }
        } else {
            Log.d(TAG, "loadImageFromSharedPreferences: Kaydedilmiş resim yok.")
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Depolama İzni Gerekli")
            .setMessage("Depolama izni verilmediği için galeriden resim seçemezsiniz. Lütfen izni ayarlardan manuel olarak verin.")
            .setPositiveButton("Tamam") { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton("Ayarlar") { dialog, _ ->
                dialog.dismiss()
                val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", requireContext().packageName, null)
                }
                startActivity(intent)
            }
            .show()
    }
}
