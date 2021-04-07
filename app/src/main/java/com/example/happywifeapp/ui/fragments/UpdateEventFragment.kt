package com.example.happywifeapp.ui.fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.example.happywifeapp.R
import com.example.happywifeapp.database.Event
import com.example.happywifeapp.database.EventDatabase
import com.example.happywifeapp.database.EventDatabaseDAO
import com.example.happywifeapp.databinding.FragmentUpdateEventBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

class UpdateEventFragment : Fragment() {

    private var _binding: FragmentUpdateEventBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateEventFragmentArgs>()
    private lateinit var database: EventDatabaseDAO
    private var calendar = Calendar.getInstance()
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    private lateinit var saveImageToInternalStorage: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateEventBinding.inflate(layoutInflater, container, false)

        setupToolbar()
        populateTextFieldsFromArgs()
        saveImageToInternalStorage = Uri.parse(args.currentEvent.image)

        val application = requireNotNull(this.activity).application
        database = EventDatabase.getInstance(application).eventDatabaseDAO()

        val uiScope = CoroutineScope(Dispatchers.IO)

        dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }

        binding.editTextDateUpdateEvent.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.textViewAddImageUpdateEvent.setOnClickListener {
            addNewImageDialogBox()
        }

        binding.updateEventButton.setOnClickListener {
            checkUserInputAndUpdateEvent(uiScope)
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun updateDateInView() {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        binding.editTextDateUpdateEvent.setText(sdf.format(calendar.time).toString())
    }

    private fun addNewImageDialogBox() {
        val pictureDialog = AlertDialog.Builder(requireContext())
        pictureDialog.setTitle(getString(R.string.Picture_dialog_select_action))
        val pictureDialogItems = arrayOf(
            getString(R.string.Picture_dialog_select_photo_from_gallery),
            getString(R.string.Picture_dialog_capture_photo_from_camera)
        )

        pictureDialog.setItems(pictureDialogItems) { _, which ->
            when (which) {
                0 -> choosePhotoFromGallery()
                1 -> takePhotoFromCamera()
            }
        }.show()
    }


    private fun populateTextFieldsFromArgs() {
        binding.editTextTitleUpdateEvent.setText(args.currentEvent.title)
        binding.editTextDateUpdateEvent.setText(args.currentEvent.date)
        binding.editTextDescriptionUpdateEvent.setText(args.currentEvent.description)
        binding.editTextLocationUpdateEvent.setText(args.currentEvent.location)
        binding.imageViewPlaceImageUpdateEvent.setImageURI(Uri.parse(args.currentEvent.image))
    }

    private fun setupToolbar() {
        val navController = findNavController()
        binding.toolbarUpdateEvent.setupWithNavController(navController)
        binding.toolbarUpdateEvent.title = getString(R.string.update_event)
        binding.toolbarUpdateEvent.setNavigationIcon(R.drawable.back_arrow_icon)
    }

    private fun checkUserInputAndUpdateEvent(uiScope: CoroutineScope) {

        val updateTitle = binding.editTextTitleUpdateEvent.text.toString()
        val updateDate = binding.editTextDateUpdateEvent.text.toString()
        val updateDescription = binding.editTextDescriptionUpdateEvent.text.toString()
        val updateLocation = binding.editTextLocationUpdateEvent.text.toString()
        val updateImage = saveImageToInternalStorage.toString()

        when {
            binding.editTextTitleUpdateEvent.text.isNullOrEmpty() -> {
                Toast.makeText(requireContext(), "Please enter title", Toast.LENGTH_SHORT).show()
            }
            binding.editTextDescriptionUpdateEvent.text.isNullOrEmpty() -> {
                Toast.makeText(requireContext(), "Please enter a description", Toast.LENGTH_SHORT)
                    .show()
            }
            binding.editTextLocationUpdateEvent.text.isNullOrEmpty() -> {
                Toast.makeText(requireContext(), "Please enter a location", Toast.LENGTH_SHORT)
                    .show()
            }
            else -> {
                uiScope.launch {
                    val updatedEvent = Event(
                        args.currentEvent.eventId,
                        updateTitle,
                        updateImage,
                        updateDescription,
                        updateDate,
                        updateLocation,
                    )
                    update(updatedEvent)
                }

                Toast.makeText(requireContext(), "Event updated", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun update(event: Event) {
        withContext(Dispatchers.IO) {
            database.updateEvent(event)
        }
    }

    private fun takePhotoFromCamera() {
        val galleryIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(galleryIntent, AddNewEventFragment.CAMERA)
    }

    private fun choosePhotoFromGallery() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(galleryIntent, AddNewEventFragment.GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == AddNewEventFragment.GALLERY && data != null) {
                val contentURI = data.data
                try {
                    contentURI?.let {
                        if (Build.VERSION.SDK_INT < 28) {
                            val selectedImageBitmap = MediaStore.Images.Media.getBitmap(
                                requireActivity().contentResolver, contentURI
                            )
                            saveImageToInternalStorage =
                                saveImageToInternalStorage(selectedImageBitmap)
                            binding.imageViewPlaceImageUpdateEvent.setImageBitmap(
                                selectedImageBitmap
                            )

                        } else {
                            val source = ImageDecoder.createSource(
                                requireActivity().contentResolver,
                                contentURI
                            )
                            val bitmap = ImageDecoder.decodeBitmap(source)
                            saveImageToInternalStorage = saveImageToInternalStorage(bitmap)
                            binding.imageViewPlaceImageUpdateEvent.setImageBitmap(bitmap)
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Failed to load the image", Toast.LENGTH_SHORT)
                        .show()
                }
            } else if (requestCode == AddNewEventFragment.CAMERA && data != null) {
                val photoFromCamera: Bitmap = data.extras?.get("data") as Bitmap
                saveImageToInternalStorage = saveImageToInternalStorage(photoFromCamera)
                binding.imageViewPlaceImageUpdateEvent.setImageBitmap(photoFromCamera)
            }
        }
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap): Uri {
        val wrapper = ContextWrapper(activity?.applicationContext)
        var file = wrapper.getDir(AddNewEventFragment.IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return Uri.parse(file.absolutePath)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}