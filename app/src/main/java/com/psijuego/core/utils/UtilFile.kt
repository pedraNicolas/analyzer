package com.psijuego.core.utils

import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date


class UtilFile {

    var IMAGE_DIRECTORY = "images"
    var IMAGE_FIRST_NAME_PART = "image"
    var JPG_EXTENSION = ".jpg"

    private val context = CoreModule.getContext()!!

    private fun fileName(firstNamePart: String, lastNamePart: String): String {
        val format = SimpleDateFormat("yyMMdd-hhmmss-SSS")
        return "$firstNamePart-${format.format(Date())}$lastNamePart"
    }

    fun deleteFile(filePath: String?): Boolean{
        return try{
            val file = File(filePath)
            var deleted = false
            if(file.exists()){
                deleted = file.delete()
            }
            deleted
        }catch (e: Exception){
            false
        }
    }

    private fun getExternalDirectory(subFolder: String): String {
        val directory = getDirectory()
        val subFolderFile = File("${directory}/$subFolder")
        if (!subFolderFile.exists()) subFolderFile.mkdirs()
        return subFolderFile.absolutePath
    }

    private fun getDirectory():String {
        val appDir = CoreModule.getContext()?.filesDir
        if (appDir != null && !appDir.exists()) appDir.mkdirs()
        return appDir!!.absolutePath
    }


    fun getImageFullFilePath(fileName: String?): String? {
        return if (fileName == null) {
            null;
        } else {
            "${getExternalDirectory(IMAGE_DIRECTORY)}/$fileName"
        }
    }

    private fun getUri(file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "${context.applicationContext.packageName}.provider",
            file
        )
    }

    fun copyContentUriImageToDir(uri: Uri): Uri {
        val inputStream = context.contentResolver.openInputStream(uri)
        val localTargetFilePath = "${getExternalDirectory(IMAGE_DIRECTORY)}/${fileName(IMAGE_FIRST_NAME_PART, JPG_EXTENSION)}"
        val outPut = FileOutputStream(localTargetFilePath)
        writeOutputStream(inputStream!!, outPut)
        return getUri(File(localTargetFilePath))
    }

    @Throws(IOException::class)
    fun writeOutputStream(input: InputStream, output: OutputStream) {
        val buffer = ByteArray(8 * 1024)
        var length: Int
        while (input.read(buffer).also { length = it } > 0) {
            output.write(buffer, 0, length)
        }
        // Cerrar los flujos
        output.flush()
        output.close()
        input.close()
    }

}