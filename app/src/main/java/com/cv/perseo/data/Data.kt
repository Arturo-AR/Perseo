package com.cv.perseo.data

import com.cv.perseo.model.*
import com.cv.perseo.utils.Constants

object Data {
    val GeneralData = GeneralData(
        idUser = "TECAPP",
        onWay = false,
        doing = false,
        municipality = "TEST",
        logo = Constants.LOGO_TELECABLE,
        idMunicipality = 1
    )

    val Permissions = listOf(
        Permissions(1, 3, Constants.SERVICE_ORDERS),
        Permissions(1, 6, Constants.INVENTORY),
        Permissions(3, 5, Constants.MY_SERVICE_ORDERS),
        Permissions(1, 2, Constants.SUBSCRIBER),
        Permissions(3, 4, Constants.COMPLIANCE),
        Permissions(3, 7, Constants.SERVICES_CORDS),
    )

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
            noTvs = 0,
            estadoDesc = "ACTIVO",
            nombres = "ARTURO",
            apellidos = "ANGUIANO",
            noSolicitud = 1082,
            noContrato = 620,
            asentamiento = "CAMELINAS",
            vialidad = "PROFESOR JESUS ROMERO FLORES",
            noExterior = "213",
            noInterior = "",
            observaciones = "LA CASA ESTA PERRONA",
            zona = "PRADOS",
            paquete = "BASICO TV + INTERNET ESENCIAL",
            idMotivo = "ININTP",
            idEstado = "AC",
            detallePedido1 = "",
            detallePedido2 = "",
            sector = "SECTOR1",
            cajaTerminal = "Caja2",
            idRubro = "ININT",
            fechaPedido = "2022-04-07",
            fechaPreCumplimiento = "2022-04-11",
            celular = "",
            telefono = "",
            iconoRubro = "ININT.png"
        ),
        ServiceOrder(
            idOs = 27280,
            rubroDesc = "INSTALACIONES TV",
            motivoDesc = "INSTALAR SERVICIO TV (PAQ)",
            noTvs = 0,
            estadoDesc = "POR INSTALAR",
            nombres = "ERNESTO",
            apellidos = "GALLARDO",
            noSolicitud = 1084,
            noContrato = 622,
            asentamiento = "CAMELINAS",
            vialidad = "MARIANO DE JESUS TORRES",
            noExterior = "234",
            noInterior = "",
            observaciones = "",
            zona = "PRADOS",
            paquete = "BASICO TV + INTERNET ESENCIAL PLUS",
            idMotivo = "INP",
            idEstado = "PI",
            detallePedido1 = "",
            detallePedido2 = "",
            sector = "null",
            cajaTerminal = "null",
            idRubro = "IN",
            fechaPedido = "2022-04-11",
            fechaPreCumplimiento = "2022-05-02",
            celular = "4455538723",
            telefono = "3543564",
            iconoRubro = "IN.png"
        ),
        ServiceOrder(
            idOs = 27256,
            rubroDesc = "INSTALACIONES INTERNET",
            motivoDesc = "INSTALAR SERVICIO INTERNET CP",
            noTvs = 0,
            estadoDesc = "ACTIVO",
            nombres = "DIANA",
            apellidos = "ALVARADO SANCHEZ",
            noSolicitud = 1069,
            noContrato = 612,
            asentamiento = "FELIX IRETA",
            vialidad = "DONAJI",
            noExterior = "06",
            noInterior = "",
            observaciones = "",
            zona = "ZONA 1",
            paquete = "BASICO TV + INTERNET ESENCIAL PLUS",
            idMotivo = "ININTCP",
            idEstado = "AC",
            detallePedido1 = "",
            detallePedido2 = "",
            sector = "SECTOR1",
            cajaTerminal = "Caja2",
            idRubro = "ININT",
            fechaPedido = "2022-03-30",
            fechaPreCumplimiento = "2022-03-31",
            celular = "",
            telefono = "",
            iconoRubro = "ININT.png"
        ),
        ServiceOrder(
            idOs = 27349,
            rubroDesc = "RECONEXIONES TV",
            motivoDesc = "RECONEXION TV",
            noTvs = 2,
            estadoDesc = "POR RECONECTAR",
            nombres = "JUAN",
            apellidos = "PEREZ",
            noSolicitud = 1115,
            noContrato = 645,
            asentamiento = "ESTRELLA",
            vialidad = "JUAN DE SILVA",
            noExterior = "324",
            noInterior = "A",
            observaciones = "FRENTE A UN PARQUE",
            zona = "ZONA 1",
            paquete = "BASICO TV + INTERNET ESENCIAL",
            idMotivo = "RTV",
            idEstado = "PR",
            detallePedido1 = "",
            detallePedido2 = "",
            sector = "null",
            cajaTerminal = "",
            idRubro = "RE",
            fechaPedido = "2022-04-25",
            fechaPreCumplimiento = "",
            celular = "",
            telefono = "8661006680",
            iconoRubro = "RE.png"
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

