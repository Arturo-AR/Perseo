package com.cv.perseo.data

import com.cv.perseo.model.database.ServiceOrder
import com.cv.perseo.model.perseoresponse.Inventory

object Data {

    val Material = mutableListOf(
        Inventory(materialDesc = "(RECICLADO) FIBRA DROP SIN SOPORTE", amount = 4, materialId = ""),
        Inventory(materialDesc = "MINI NODOS", amount = 234,materialId = ""),
        Inventory(materialDesc = "CONECTOR RG6", amount = 23,materialId = ""),
        Inventory(materialDesc = "FLEFE", amount = 0,materialId = ""),
        Inventory(materialDesc = "FIBRA DROP SIN SOPORTE", amount = 234,materialId = ""),
        Inventory(materialDesc = "CABLERG6", amount = 6786,materialId = "")
    )

    val colonias =
        listOf("SANTA RITA", "CENTRO", "SAN ISIDRO", "FRANCISCO I. MADERO", "ALMOLOYA", "SAN JUAN")

    val orders = listOf(
        ServiceOrder(
            idOs = 27276,
            rubroDesc = "INSTALACIONES INTERNET",
            motivoDesc = "INSTALAR SERVICIO INTERNET (PAQ)",
            vialidad = "PROFESOR JESUS ROMERO FLORES",
            noExterior = "213",
            zona = "PRADOS",
            paquete = "BASICO TV + INTERNET ESENCIAL",
            idRubro = "ININT",
            fechaPreCumplimiento = "2022-04-11",
            iconoRubro = "ININT.png",
            fecha_agenda = "",
            hora_de = "",
            hora_hasta = "",
            detalle_agenda = ""
        ),
        ServiceOrder(
            idOs = 27280,
            rubroDesc = "INSTALACIONES TV",
            motivoDesc = "INSTALAR SERVICIO TV (PAQ)",
            vialidad = "MARIANO DE JESUS TORRES",
            noExterior = "234",
            zona = "PRADOS",
            paquete = "BASICO TV + INTERNET ESENCIAL PLUS",
            idRubro = "IN",
            fechaPreCumplimiento = "2022-05-02",
            iconoRubro = "IN.png",
                    fecha_agenda = "",
            hora_de = "",
            hora_hasta = "",
            detalle_agenda = ""
        ),
        ServiceOrder(
            idOs = 27256,
            rubroDesc = "INSTALACIONES INTERNET",
            motivoDesc = "INSTALAR SERVICIO INTERNET CP",
            vialidad = "DONAJI",
            noExterior = "06",
            zona = "ZONA 1",
            paquete = "BASICO TV + INTERNET ESENCIAL PLUS",
            idRubro = "ININT",
            fechaPreCumplimiento = "2022-03-31",
            iconoRubro = "ININT.png",
            fecha_agenda = "",
            hora_de = "",
            hora_hasta = "",
            detalle_agenda = ""
        ),
        ServiceOrder(
            idOs = 27349,
            rubroDesc = "RECONEXIONES TV",
            motivoDesc = "RECONEXION TV",
            vialidad = "JUAN DE SILVA",
            noExterior = "324",
            zona = "ZONA 1",
            paquete = "BASICO TV + INTERNET ESENCIAL",
            idRubro = "RE",
            fechaPreCumplimiento = "",
            iconoRubro = "RE.png",
            fecha_agenda = "",
            hora_de = "",
            hora_hasta = "",
            detalle_agenda = ""
        ),
    )



}

