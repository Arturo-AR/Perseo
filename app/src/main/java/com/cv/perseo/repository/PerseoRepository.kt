package com.cv.perseo.repository

import com.cv.perseo.model.perseoresponse.*
import com.cv.perseo.network.PerseoApi
import retrofit2.Response
import javax.inject.Inject

class PerseoRepository @Inject constructor(private val api: PerseoApi) {

    suspend fun login(userId: String, password: String): Response<LoginResponse> {
        return api.login(0, userId, password)
    }

    suspend fun getServiceOrders(userId: String, enterpriseId: Int, osId: Int = -1): Response<ServiceOrdersResponse> {
        return api.serviceOrders(1, userId, enterpriseId, osId)
    }

    suspend fun getCordsOrders(userId: String, enterpriseId: Int): Response<CordsOrdersResponse> {
        return api.cordsOrders(2, userId, enterpriseId)
    }

    suspend fun getInventory(enterpriseId: Int, userId: String): Response<InventoryResponse> {
        return api.inventory(3, enterpriseId, userId)
    }

    suspend fun motivoOrders(motivoId: String, enterpriseId: Int): Response<MotivosResponse> {
        return api.motivoOrders(4, motivoId, enterpriseId)
    }

    suspend fun validateEquipment(enterpriseId: Int, parameters: String): Response<ValidateEquipmentResponse> {
        return api.validateEquipment(5, enterpriseId, parameters)
    }

    suspend fun getRoutersCT(enterpriseId: Int): Response<RoutersCTResponse> {
        return api.getRoutersCT(6, enterpriseId)
    }
    suspend fun finalizarOrdenServicio(empresa_id: Int, ordenes_info_cumplimiento: String, fotos: String, equipos: String, orden: Int, fecha: String, materiales: String, parametros: String, info_cumplimiento: String): Response<String> {
        return api.finalizarOrdenServicio(7, empresa_id, ordenes_info_cumplimiento, fotos, equipos, orden, fecha, materiales, parametros, info_cumplimiento)
    }

    suspend fun cancelOrder(enterpriseId: Int, cancelReason:String): Response<String> {
        return api.cancelOrder(8, enterpriseId, cancelReason)
    }

}