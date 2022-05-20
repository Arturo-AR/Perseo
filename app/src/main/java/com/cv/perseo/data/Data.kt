package com.cv.perseo.data

import com.cv.perseo.model.GeneralData
import com.cv.perseo.model.Permissions
import com.cv.perseo.utils.Constants

object Data {
    val GeneralData = GeneralData(
        userId = "TECAPP",
        onWay = false,
        doing = false,
        municipality = "TEST",
        logo = Constants.LOGO_TELECABLE,
        idmMunicipality = 1
    )

    val Permissions = listOf(
        Permissions(1, 3, Constants.SERVICE_ORDERS),
        Permissions(1, 6, Constants.INVENTORY),
        Permissions(3, 5, Constants.MY_SERVICE_ORDERS),
        Permissions(1, 2, Constants.SUBSCRIBER),
        Permissions(3, 4, Constants.COMPLIANCE),
        Permissions(3, 7, Constants.SERVICES_CORDS),
    )
}

