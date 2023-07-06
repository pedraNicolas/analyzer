package com.analyzer.ui.views.report.home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.analyzer.R
import com.analyzer.core.utils.UtilFile
import com.analyzer.core.utils.UtilUploadFiles
import com.analyzer.data.model.WelcomeUI
import com.analyzer.databinding.FragmentHomeBinding
import com.analyzer.databinding.FragmentWelcomeBinding
import com.analyzer.ui.views.report.ReportViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val utilUploadFiles = UtilUploadFiles()
    private val utilFile = UtilFile()

    private lateinit var binding: FragmentHomeBinding
    private var welcomeUI: WelcomeUI = WelcomeUI()
    private val viewModel: ReportViewModel by viewModels()
    private lateinit var mUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setUpComponents()
        return binding.root
    }

    private fun setUpComponents() {
        with(binding) {
            btnNext.setOnClickListener(::onNext)
            btnUpload.setOnClickListener(::attachGalleryDraw)
        }
    }

    private fun onAttachmentTaken(uri: Uri) {
        try {
            mUri = utilFile.copyContentUriImageToDir(uri)
            welcomeUI.uri = mUri
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun onNext(view: View) {
        viewModel.setWelcomeUI(bindToObject())
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_indicatorsFragment)
    }

    private fun attach(view: View) {
        val fileName = utilFile.fileName(
            "${utilFile.IMAGE_FIRST_NAME_PART}${welcomeUI.namePatient}",
            utilFile.JPG_EXTENSION
        )
        val fullFilePath = utilFile.getImageFullFilePath(fileName)
        mUri = utilFile.getUri(File(fullFilePath))
        if (mUri != null) {
            attachGalleryDraw(view)
        }
    }

    private fun attachGalleryDraw(view: View) {
        var resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    if (result.data != null) mUri = result.data!!.data!!
                    val success = result.resultCode == Activity.RESULT_OK
                    if (success) this.onAttachmentTaken(mUri)
                }
            }.launch(
                Intent.createChooser(
                    utilUploadFiles.pickDrawFromGalleryIntent(),
                    getString(R.string.imagenes)
                )
            )
    }

    private fun bindToObject(): WelcomeUI {
        with(binding) {
            tvProfessionalName.text?.toString()?.let { welcomeUI.nameProfessional = it }
            tvPatientName.text?.toString()?.let { welcomeUI.namePatient = it }
            tvRegistrationNumber.text?.toString()?.let { welcomeUI.numberRegistration = it }
        }
        return welcomeUI
    }
}