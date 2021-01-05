package com.example.happywifeapp

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.happywifeapp.database.Event
import com.example.happywifeapp.database.EventDatabase
import com.example.happywifeapp.database.EventDatabaseDAO
import com.example.happywifeapp.databinding.FragmentAddNewEventBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*


class AddNewEventFragment : Fragment() {

    private lateinit var _binding: FragmentAddNewEventBinding
    private lateinit var database: EventDatabaseDAO
    private var calendar = Calendar.getInstance()
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    private var saveImageToInternalStorage: Uri? = null
    private var mLatitude : Double = 0.0
    private var mLongitude : Double = 0.0

    companion object {
        private const val GALLERY = 1
        private const val CAMERA = 2
        private const val IMAGE_DIRECTORY = "HappyPlacesImages"
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_new_event, container, false)

        setupToolbar(_binding)

        val application = requireNotNull(this.activity).application

        database = EventDatabase.getInstance(application).eventDatabaseDAO

        val uiScope = CoroutineScope(Dispatchers.IO)

        dateSetListener = DatePickerDialog.OnDateSetListener {
            _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }

        updateDateInView()

        _binding.editTextDate.setOnClickListener {
            DatePickerDialog(
                    requireActivity(),
                    dateSetListener,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        _binding.textViewAddImage.setOnClickListener {
            addNewImageDialogBox()
        }

        _binding.addNewEventButtonSave.setOnClickListener {
            when {
                _binding.editTextTitle.text.isNullOrEmpty() -> {
                    Toast.makeText(requireContext(), "Please enter title", Toast.LENGTH_SHORT).show()
                }
                _binding.editTextDescription.text.isNullOrEmpty() -> {
                    Toast.makeText(requireContext(), "Please enter a description", Toast.LENGTH_SHORT).show()
                }
                _binding.editTextLocation.text.isNullOrEmpty() -> {
                    Toast.makeText(requireContext(), "Please enter a location", Toast.LENGTH_SHORT).show()
                }
                saveImageToInternalStorage == null -> {
                    Toast.makeText(requireContext(), "Please select a image", Toast.LENGTH_SHORT).show()
                }
                else -> {

                    uiScope.launch {
                        val newEvent = Event(
                            0,
                            _binding.editTextTitle.text.toString(),
                            saveImageToInternalStorage.toString(),
                            _binding.editTextDescription.text.toString(),
                            _binding.editTextDate.text.toString(),
                            _binding.editTextLocation.toString(),
                            mLatitude,
                            mLongitude
                        )
                        insert(newEvent)
                    }

                    if (EventDatabase.INSTANCE != null) {
                        Toast.makeText(requireContext(), "Event added", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return _binding.root

    }



    private suspend fun insert(event: Event) {
        database.insertEvent(event)
    }

    private fun addNewImageDialogBox() {
        val pictureDialog = AlertDialog.Builder(requireContext())
        pictureDialog.setTitle(getString(R.string.Picture_dialog_select_action))
        val pictureDialogItems = arrayOf(getString(R.string.Picture_dialog_select_photo_from_gallery),
                getString(R.string.Picture_dialog_capture_photo_from_camera))

        pictureDialog.setItems(pictureDialogItems) { _, which ->
            when (which) {
                0 -> choosePhotoFromGallery()
                1 -> takePhotoFromCamera()
            }
        }.show()
    }

    private fun setupToolbar(binding: FragmentAddNewEventBinding) {
        val navController = findNavController()
        binding.toolbarAddNewEvent.setupWithNavController(navController)
        binding.toolbarAddNewEvent.title = getString(R.string.add_new_event)
        binding.toolbarAddNewEvent.setNavigationIcon(R.drawable.back_arrow_icon)
    }

    private fun updateDateInView() {
        val myFormat = "dd.MM.yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        _binding.editTextDate.setText(sdf.format(calendar.time).toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY && data != null) {
               val contentURI = data.data
               try {
                   contentURI?.let {
                       if (Build.VERSION.SDK_INT < 28) {
                           val selectedImageBitmap = MediaStore.Images.Media.getBitmap(
                               requireActivity().contentResolver, contentURI)
                           saveImageToInternalStorage = saveImageToInternalStorage(selectedImageBitmap)
                           _binding.imageViewPlaceImage.setImageBitmap(selectedImageBitmap)

                       } else {
                           val source = ImageDecoder.createSource(requireActivity().contentResolver, contentURI)
                           val bitmap = ImageDecoder.decodeBitmap(source)
                           saveImageToInternalStorage = saveImageToInternalStorage(bitmap)
                           _binding.imageViewPlaceImage.setImageBitmap(bitmap)
                       }
                   }
               } catch (e: IOException) {
                   e.printStackTrace()
                   Toast.makeText(requireContext(), "Failed to load the image", Toast.LENGTH_SHORT).show()
               }
            } else if (requestCode == CAMERA && data != null) {
                val photoFromCamera: Bitmap = data.extras?.get("data") as Bitmap
                saveImageToInternalStorage = saveImageToInternalStorage(photoFromCamera)
                _binding.imageViewPlaceImage.setImageBitmap(photoFromCamera)
            }
        }
    }

    private fun takePhotoFromCamera() {
        Dexter.withActivity(requireActivity()).withPermissions(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
        ).withListener(object: MultiplePermissionsListener {

            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {

                if (report!!.areAllPermissionsGranted()) {
                    val galleryIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(galleryIntent, CAMERA)
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>,
                token: PermissionToken) {

                showRationDialogForPermissions()
            }
        }).onSameThread().check()
    }

    private fun choosePhotoFromGallery() {
        Dexter.withActivity(requireActivity()).withPermissions(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).withListener(object: MultiplePermissionsListener {

            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {

                if (report!!.areAllPermissionsGranted()) {
                    val galleryIntent = Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(galleryIntent, GALLERY)
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>,
                    token: PermissionToken) {

                showRationDialogForPermissions()
            }
        }).onSameThread().check()
    }

    private fun showRationDialogForPermissions() {
        AlertDialog.Builder(requireContext()).setMessage("Looks like you have turned off permission " +
                    "required. It can be enabled under the Application Settings")
                .setPositiveButton("Go to settings") { _,_ ->
                        try {
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri = Uri.fromParts("package", activity?.packageName, null)
                            intent.data = uri
                            startActivity(intent)
                        } catch (e: ActivityNotFoundException) {
                            e.printStackTrace()
                        }
                }
                .setNegativeButton("Cancel") {dialog, _->
                    dialog.dismiss()
                }.show()
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap): Uri{
        val wrapper = ContextWrapper(activity?.applicationContext)
        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream)
            stream.flush()
            stream.close()
        }catch (e: IOException){
            e.printStackTrace()
        }
        return Uri.parse(file.absolutePath)
    }


}