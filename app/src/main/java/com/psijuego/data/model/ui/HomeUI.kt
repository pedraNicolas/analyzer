package com.psijuego.data.model.ui

import android.net.Uri

data class HomeUI(
    var namePatient: String = "",
    var nameProfessional: String = "",
    var numberRegistration: String? = null,
    var uri: Uri? = null
)