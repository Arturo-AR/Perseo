package com.cv.perseo.data

import com.cv.perseo.model.perseoresponse.Inventory

object Data {

    val Material = mutableListOf(
        Inventory(materialDesc = "(RECICLADO) FIBRA DROP SIN SOPORTE", amount = 4, materialId = ""),
        Inventory(materialDesc = "MINI NODOS", amount = 234, materialId = ""),
        Inventory(materialDesc = "CONECTOR RG6", amount = 23, materialId = ""),
        Inventory(materialDesc = "FLEFE", amount = 0, materialId = ""),
        Inventory(materialDesc = "FIBRA DROP SIN SOPORTE", amount = 234, materialId = ""),
        Inventory(materialDesc = "CABLERG6", amount = 6786, materialId = "")
    )

    val colonias =
        listOf("SANTA RITA", "CENTRO", "SAN ISIDRO", "FRANCISCO I. MADERO", "ALMOLOYA", "SAN JUAN")

}

