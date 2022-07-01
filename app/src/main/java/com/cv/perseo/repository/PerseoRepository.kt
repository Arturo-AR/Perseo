package com.cv.perseo.repository

import com.cv.perseo.model.*
import com.cv.perseo.network.PerseoApi
import javax.inject.Inject

class PerseoRepository @Inject constructor(private val api: PerseoApi) {
    suspend fun login(username: String, password: String): LoginResponse {
        //return api.login(0, username, password)
        return LoginResponse(
            responseCode = 200, responseMessage = "OK", responseBody = LoginBody(
                enterprises = listOf(
                    EnterpriseBody(
                        empresa = "TELECABLE",
                        ciudad = "MORELIA",
                        logo = "defwe",
                        logoIcono = "DFGERG",
                        idEmpresa = 2
                    ),
                    EnterpriseBody(
                        empresa = "MEDIACOM",
                        ciudad = "MORELIA",
                        logo = "defwe",
                        logoIcono = "DFGERG",
                        idEmpresa = 10
                    )
                ),
                permissions = listOf(
                    PermissionsBody(
                        idActivityFather = 1,
                        idActivitySon = 3,
                        icon = "wfwef"
                    ),
                    PermissionsBody(
                        idActivityFather = 1,
                        idActivitySon = 6,
                        icon = "wfwef"
                    ),
                    PermissionsBody(
                        idActivityFather = 3,
                        idActivitySon = 5,
                        icon = "wfwef"
                    ),
                    PermissionsBody(
                        idActivityFather = 1,
                        idActivitySon = 2,
                        icon = "wfwef"
                    ),
                    PermissionsBody(
                        idActivityFather = 3,
                        idActivitySon = 4,
                        icon = "wfwef"
                    ),
                    PermissionsBody(
                        idActivityFather = 3,
                        idActivitySon = 7,
                        icon = "wfwef"
                    ),
                )
            )
        )
    }
}