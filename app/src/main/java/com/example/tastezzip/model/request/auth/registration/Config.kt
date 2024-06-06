package com.example.tastezzip.model.request.auth.registration

import com.example.tastezzip.model.enums.AgreementType

data class Config(
    val MARKETING_MESSAGE_AGREEMENT: AgreementType = AgreementType.ACCEPTED,
    val TERM_OF_GPS_AGREEMENT: AgreementType = AgreementType.ACCEPTED,
    val TERM_OF_USE_AGREEMENT: AgreementType = AgreementType.ACCEPTED
)