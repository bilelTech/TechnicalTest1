package com.exercice.technicaltest.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.exercice.technicaltest.R
import com.exercice.technicaltest.databinding.FragmentFirstBinding
import com.pixelcarrot.base64image.Base64Image

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    var startResultLauncher: ActivityResultLauncher<Intent>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        startResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // result.data.data
                    val intent = result.data
                    intent?.let {
                        val bitmap: Bitmap? = intent.extras?.get("data") as Bitmap?
                        bitmap?.let {
                            photoTakenCallback(bitmap)
                        }
                    }
                }
            }
    }
    /**
     * retriver bitmap image
     */
    private fun photoTakenCallback(bitmap: Bitmap?) {
        Base64Image.encode(bitmap) { base64 ->
            base64?.let {
                // success
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.fab.setOnClickListener { view ->
            handleTakeProductPhoto()
        }
    }

    private fun handleTakeProductPhoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startResultLauncher?.launch(takePictureIntent)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}