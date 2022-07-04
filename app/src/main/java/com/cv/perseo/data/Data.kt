package com.cv.perseo.data

import com.cv.perseo.model.*
import com.cv.perseo.model.database.ServiceOrder

object Data {

    val Material = mutableListOf(
        Material(materialDesc = "(RECICLADO) FIBRA DROP SIN SOPORTE", amount = 4),
        Material(materialDesc = "MINI NODOS", amount = 234),
        Material(materialDesc = "CONECTOR RG6", amount = 23),
        Material(materialDesc = "FLEFE", amount = 0),
        Material(materialDesc = "FIBRA DROP SIN SOPORTE", amount = 234),
        Material(materialDesc = "CABLERG6", amount = 6786)
    )

    val sectores = listOf("CIRCUITO 12", "CIRCUITO 10", "CIRCUITO 19", "CIRCUITO 4", "CIRCUITO 20")
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

    val cords = listOf(
        ServiceCords(
            2540,
            "VISTA BELLA",
            "ATZIMBA",
            "123",
            "",
            "COFAPA",
            "",
            "CAJA-1",
            "iwu9we8"
        ),
        ServiceCords(
            2548,
            "VISTA BELLA",
            "ATZIMBA",
            "14",
            "",
            "RECO",
            "",
            "",
            "iwu9we8"
        ),
        ServiceCords(
            4798,
            "RESIDENCIAL DEL SUR",
            "DE NICOLAS BRAVO",
            "56",
            "",
            "RECO",
            "",
            "",
            "iwu9we8"
        ),
        ServiceCords(
            4776,
            "ESTRELLA",
            "LUCAS DURAN",
            "987",
            "",
            "RECO",
            "",
            "",
            "iwu9we8"
        ),
        ServiceCords(
            4772,
            "VENTURA PUENTE",
            "ANDRES DEL RIO",
            "7894",
            "",
            "COFAPA",
            "",
            "",
            "iwu9we8"
        ),
        ServiceCords(
            27421,
            "VISTA BELLA",
            "EL RETAJO",
            "12",
            "A",
            "COFAPA",
            "SECTOR1",
            "S1-TB01",
            "123"
        ),
        ServiceCords(
            3052,
            "VENTURA PUENTE",
            "MIGUEL DE CERVANTES SAAVEDRA",
            "55",
            "",
            "RECO",
            "",
            "",
            "3456ERTY"
        ),
        ServiceCords(
            2540,
            "VISTA BELLA",
            "ATZIMBA",
            "123",
            "",
            "COFAPA",
            "",
            "",
            ""
        ),
        ServiceCords(
            2548,
            "VISTA BELLA",
            "ATZIMBA",
            "14",
            "",
            "RECO",
            "",
            "",
            ""
        ),
        ServiceCords(
            4798,
            "RESIDENCIAL DEL SUR",
            "DE NICOLAS BRAVO",
            "56",
            "",
            "RECO",
            "",
            "",
            ""
        ),
        ServiceCords(
            4776,
            "ESTRELLA",
            "LUCAS DURAN",
            "987",
            "",
            "COFAPA",
            "",
            "",
            ""
        ),
        ServiceCords(
            4772,
            "VENTURA PUENTE",
            "ANDRES DEL RIO",
            "7894",
            "",
            "COFAPA",
            "",
            "",
            ""
        ),
        ServiceCords(
            27421,
            "VISTA BELLA",
            "EL RETAJO",
            "12",
            "A",
            "COFAPA",
            "SECTOR1",
            "S1-TB01",
            "123"
        ),
        ServiceCords(
            3052,
            "VENTURA PUENTE",
            "MIGUEL DE CERVANTES SAAVEDRA",
            "55",
            "",
            "RECO",
            "",
            "",
            "3456ERTY"
        ),
    )

}

