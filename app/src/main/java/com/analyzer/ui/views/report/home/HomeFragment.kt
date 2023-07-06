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
import com.analyzer.core.utils.CoreModule
import com.analyzer.core.utils.UtilFile
import com.analyzer.core.utils.UtilUploadFiles
import com.analyzer.data.model.WelcomeUI
import com.analyzer.databinding.FragmentHomeBinding
import com.analyzer.databinding.FragmentWelcomeBinding
import com.analyzer.ui.views.report.ReportViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject
import kotlin.math.max

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val utilUploadFiles = UtilUploadFiles()
    private val utilFile = UtilFile()

    private lateinit var binding: FragmentHomeBinding
    private var welcomeUI: WelcomeUI = WelcomeUI()
    private val viewModel: ReportViewModel by viewModels()
    private var mUri: Uri? = null

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
            ivDelete.setOnClickListener(::onDeleteImage)
        }
    }

    private fun onNext(view: View) {
        viewModel.setWelcomeUI(bindToObject())
        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_indicatorsFragment)
    }

    private fun onDeleteImage(view: View) {
//        var filePath: String? = ""
//        if(mUri != null){
//            filePath = utilFile.getImageFullFilePath(mUri!!.lastPathSegment)
//        }
        var filePath = mUri?.lastPathSegment?.let { utilFile.getImageFullFilePath(it) } ?: ""
        var deleted = utilFile.deleteFile(filePath)
        if (deleted) {
            mUri = null
            with(binding) {
                btnUpload.visibility = View.VISIBLE
                ivImage.visibility = View.GONE
                ivDelete.visibility = View.GONE
            }
        }
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) mUri = result.data!!.data!!
                val success = result.resultCode == Activity.RESULT_OK
                if (success) mUri?.let { this.onAttachmentTaken(it) }
            }
        }

    private fun attachGalleryDraw(view: View) {
        resultLauncher.launch(
            Intent.createChooser(
                utilUploadFiles.pickDrawFromGalleryIntent(),
                getString(R.string.images)
            )
        )
    }

    private fun onAttachmentTaken(uri: Uri) {
        try {
            mUri = utilFile.copyContentUriImageToDir(uri)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        showImageOnComponent()
    }


    private fun showImageOnComponent() {
        if (mUri != null) {
            with(binding) {
                btnUpload.visibility = View.GONE
                ivImage.visibility = View.VISIBLE
                ivDelete.visibility = View.VISIBLE
                Glide.with(this@HomeFragment)
                    .load(mUri)
                    .into(ivImage)
            }
        }
    }

    private fun bindToObject(): WelcomeUI {
        with(binding) {
            tvProfessionalName.text?.toString()?.let { welcomeUI.nameProfessional = it }
            tvPatientName.text?.toString()?.let { welcomeUI.namePatient = it }
            tvRegistrationNumber.text?.toString()?.let { welcomeUI.numberRegistration = it }
            welcomeUI.uri = mUri
        }
        return welcomeUI
    }
}