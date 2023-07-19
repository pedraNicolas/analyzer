package com.psijuego.ui.views.report.export

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.psijuego.R
import com.psijuego.core.utils.UtilPDF
import com.psijuego.data.model.ui.CategoryUI
import com.psijuego.data.model.ui.HomeUI
import com.psijuego.databinding.FragmentExportReportBinding
import com.psijuego.ui.views.report.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ExportReportFragment : Fragment() {

    private lateinit var binding: FragmentExportReportBinding
    private val viewModel: SharedViewModel by activityViewModels<SharedViewModel>()
    private lateinit var listCategoryUI: List<CategoryUI>
    private lateinit var homeUI: HomeUI
    private lateinit var conclusion: String
    private lateinit var file: File
    private var isPdfSelect = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentExportReportBinding.inflate(inflater, container, false)
        setUpViewModel()
        return binding.root
    }

    private fun setUpViewModel() {
        listCategoryUI = viewModel.getCategoryUI() ?: emptyList()
        homeUI = viewModel.getHomeUI() ?: HomeUI()
        conclusion = viewModel.getConclusion() ?: ""
        createPdfDocument()
    }

    private fun createPdfDocument() {
        file = UtilPDF().createPdf(homeUI, listCategoryUI, conclusion)
        viewModel.uploadDocument(file)
        setUpComponent()
    }

    private fun setUpComponent() {
        with(binding) {
            if (file != null && file.exists()) {
                pdfView.fromFile(file).load()
            }

            topAppBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.new_qr -> {
                        setUpActionQrButton()
                        true
                    }

                    else -> false
                }
            }
        }
    }

    private fun setUpActionQrButton() {
        val menu = binding.topAppBar.menu
        val menuItem = menu.findItem(R.id.new_qr)
        if (isPdfSelect) {
            menuItem.setIcon(R.drawable.ic_document)
            binding.qrCode.visibility = View.VISIBLE
            binding.pdfView.visibility = View.GONE
            viewModel.pdfStorageUrl.observe(viewLifecycleOwner) {
                createQr(it)
            }
            isPdfSelect = false
        } else {
            menuItem.setIcon(R.drawable.ic_qr)
            binding.qrCode.visibility = View.GONE
            binding.pdfView.visibility = View.VISIBLE
            isPdfSelect = true
        }
    }


    private fun createQr(url: String) {
        try {
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.encodeBitmap(url, BarcodeFormat.QR_CODE, 750, 750)
            binding.qrCode.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}