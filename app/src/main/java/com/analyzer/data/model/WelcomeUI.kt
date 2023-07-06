package com.analyzer.data.model

import android.net.Uri

data class WelcomeUI(
    var namePatient: String = "",
    var nameProfessional: String = "",
    var numberRegistration: String? = null,
    var uri: Uri? = null
)